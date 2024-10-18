package com.studentapp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
//@ComponentScan("com.studentapp.Utill")
public class AwsConfig {

	@Value("${aws.s3.access_key}")
	private String awsS3AccessKey;

	@Value("${aws.s3.secret_key}")
	private String awsS3SecretKey;
	
	@Value("${aws.region}")
	private String awsRegion;
	
	@Bean
	public AmazonS3 s3Client() {

		return AmazonS3ClientBuilder
				.standard()
				.withCredentials(
						new AWSStaticCredentialsProvider(
								new BasicAWSCredentials(
										awsS3AccessKey, awsS3SecretKey
										)
								)
						)
				.withRegion(awsRegion)
				.build();
	}

}
