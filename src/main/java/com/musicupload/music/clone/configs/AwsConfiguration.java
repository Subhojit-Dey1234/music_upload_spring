package com.musicupload.music.clone.configs;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
Adding the localstack setup here

Change the configs in the aws to support the cloud development

1. Creating the s3 bucket command
    aws s3 mb s3://my-local-music-bucket --endpoint-url=http://localhost:4566

2. Listing the s3 buckets
    aws s3 ls s3://my-local-music-bucket  --endpoint-url=http://localhost:4566

 */
@Configuration
public class AwsConfiguration {

    @Bean
    public AmazonS3 amazonS3(){
        // TODO - change it to support cloud development also
        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(
                        new BasicAWSCredentials("test", "test"))
                )
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration("http://localhost:4566", "us-east-1")
                )
                .withPathStyleAccessEnabled(true)
                .build();
    }
}
