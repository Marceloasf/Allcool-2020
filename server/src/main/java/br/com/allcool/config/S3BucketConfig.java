package br.com.allcool.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Must add S3 application properties to work properly
@Configuration
public class S3BucketConfig {

    @Value("${s3.credentials.accessKey}")
    private String accessKey;

    @Value("${s3.credentials.secretKey}")
    private String secretKey;

    @Value("${s3.endpoint}")
    private String bucketEndpoint;

    @Value("${s3.region}")
    private String bucketRegion;

    @Bean
    public AmazonS3 createAmazonS3() {

        AWSCredentials credentials = new BasicAWSCredentials("SCWFQ105Z1KRPBWD8NGQ", "930ee515-d107-45f9-b8ab-6cfcc957f245");

        return AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("https://s3.fr-par.scw.cloud/", "fr-par")).build();
    }
}
