package com.languagePartner.common;


import com.languagePartner.exception.BusinessException;
import com.languagePartner.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ProjectExceptionHandler {
    @ExceptionHandler(SystemException.class)
    public R<String> doSystemException(SystemException exception){
        //记录日志
        log.info("SystemError");
        //发消息给运维

        //发邮件给开发人员

        return R.errorWithCode(exception.getCode(), exception.getMessage());
    }

    @ExceptionHandler(BusinessException.class)
    public R<String> doBusinessException(BusinessException exception){
        //记录日志
        log.info("BusinessError");
        //发消息给运维,让他把ip封了


        return R.errorWithCode(exception.getCode(), exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public R<String> OtherException(Exception exception){
        exception.printStackTrace();
        //记录日志
        log.info("gg");
        //发消息给运维

        //发邮件给开发人员

        return R.errorWithCode(CODE.UNKNOWN_ERROR, "系统寄了");
    }
}
