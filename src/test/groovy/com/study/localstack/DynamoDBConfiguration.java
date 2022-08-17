package com.study.localstack;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DynamoDBConfiguration {


    @Bean(name = "amazonDynamoDBClient")
    public DynamoDB amazonDynamoDBClient() {
        AwsClientBuilder.EndpointConfiguration endpointConfig = new AwsClientBuilder.EndpointConfiguration("http://localhost:4566",
                Regions.EU_WEST_1.getName());

        AmazonDynamoDB client =  AmazonDynamoDBClientBuilder.standard()
                .withEndpointConfiguration(endpointConfig)
                .build();
        return new DynamoDB(client);
    }

    @Bean
    public DynamoDBService dynamoDBService(DynamoDB amazonDynamoDBClient){
        return new DynamoDBService(amazonDynamoDBClient);

    }
}
