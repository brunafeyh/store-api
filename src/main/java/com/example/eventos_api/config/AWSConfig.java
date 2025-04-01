package com.example.eventos_api.config;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//usar a a aws ou o google par as imagens
@Configuration
public class AWSConfig {
    @Value("${aws.region}")
    private String awsRegion;

    @Bean
    public AmazonS3 amazonS3Instance() {
        return AmazonS3ClientBuilder.standard().withRegion(awsRegion).build();
    }
}
