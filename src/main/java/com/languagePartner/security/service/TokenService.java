package com.languagePartner.security.service;

import ch.qos.logback.core.util.TimeUtil;
import com.alibaba.fastjson2.JSON;
import com.languagePartner.common.CacheConstants;
import com.languagePartner.common.Constants;
import com.languagePartner.entity.LoginUser;
import com.languagePartner.util.IdUtils;
import com.languagePartner.util.IpUtils;
import com.languagePartner.util.RedisCache;
import com.languagePartner.util.ServletUtils;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import eu.bitwalker.useragentutils.UserAgent;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class TokenService {
    // 令牌自定义标识
    @Value("${token.header}")
    private String header;

    // 令牌秘钥
    @Value("${token.secret}")
    private String secret;

    // 令牌有效期（默认30分钟）
    @Value("${token.expireTime}")
    private int expireTime;

    protected static final long MILLIS_SECOND = 1000;

    protected static final long MILLIS_MINUTE = 60 * MILLIS_SECOND;

    private static final Long MILLIS_MINUTE_TEN = 20 * 60 * 1000L;

    @Autowired
    private RedisCache redisCache;

    /**
     * 通过请求头获取token, 会把前缀prefix标识去掉
     */
    private String getToken(HttpServletRequest request){
        String token = request.getHeader(header);
        if(StringUtils.isNotEmpty(token) && token.startsWith(Constants.TOKEN_PREFIX)){
            token = token.replace(Constants.TOKEN_PREFIX, "");
        }
        return token;
    }

    /**
     * 拿到缓存的tokenKey
     * @param uuid
     * @return
     */
    private String getTokenKey(String uuid){return CacheConstants.LOGIN_TOKEN_KEY + uuid;}


    /**
     * 从令牌中获取数据声明
     */
    private Claims parseToken(String token){
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 获取用户信息
     */
    public LoginUser getLoginUser(HttpServletRequest request){
        String token = getToken(request);

        if(StringUtils.isNotEmpty(token)){
            try{
                Claims claims = parseToken(token);
                String uuid = (String) claims.get(Constants.LOGIN_USER_KEY);
                String userKey = getTokenKey(uuid);
                Object user = redisCache.getCacheObject(userKey);
                //先将object转成json
                String userJsonString = JSON.toJSONString(user);
                //转成LoginUser
                return JSON.parseObject(userJsonString, LoginUser.class);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

        return null;
    }

    /**
     * 刷新token
     */
    public void refreshToken(LoginUser loginUser){
        loginUser.setLoginTime(System.currentTimeMillis());
        loginUser.setExpireTime(loginUser.getLoginTime() + expireTime * MILLIS_MINUTE);
        String userKey = getTokenKey(loginUser.getToken());
        redisCache.setCacheObject(userKey, loginUser, expireTime, TimeUnit.MINUTES);
    }

    /**
     * 设置用户
     */
    public void setLoginUser(LoginUser loginUser){
        if(loginUser != null && StringUtils.isNotEmpty(loginUser.getToken())){
            refreshToken(loginUser);
        }
    }

    /**
     * 删除用户信息
     */
    public void delLoginUser(String token){
        if(StringUtils.isNotEmpty(token)){
            String userKey = getTokenKey(token);
            redisCache.deleteObject(userKey);
        }
    }

    /**
     * 创建令牌
     */
    public String createToken(LoginUser loginUser){

        String token = IdUtils.fastUUID();
        //这是没携带数据的token
        loginUser.setToken(token);

        setUserAgent(loginUser);

        refreshToken(loginUser);
        //生成返回的是带数据的token
        Map<String, Object> claims = new HashMap<>();
        claims.put(Constants.LOGIN_USER_KEY, token);
        return createToken(claims);
    }

    /**
     * 根据数据创建token
     */
    private String createToken(Map<String, Object> claims){
        String token = Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret).compact();
        return token;
    }

    /**
     * 设置用户代理信息
     */
    public void setUserAgent(LoginUser loginUser){
        UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
        String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
    }

    /**
     * 验证令牌有效期，差20分钟以内的进行刷新
     */
    public void verifyToken(LoginUser loginUser){
        long expireTime = loginUser.getExpireTime();
        long currentTime = System.currentTimeMillis();
        if(expireTime - currentTime <= MILLIS_MINUTE_TEN){
            refreshToken(loginUser);
        }
    }
}
