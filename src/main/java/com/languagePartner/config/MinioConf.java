package com.languagePartner.config;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class MinioConf {
    @Value("${minio.endpoint}")
    String endpoint;

    @Value("${minio.accesskey}")
    String accesskey;

    @Value("${minio.secretkey}")
    String sercretkey;

    @Bean
    public MinioClient minioClient(){
        return MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accesskey, sercretkey)
                .build();
    }
}
