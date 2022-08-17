package com.study.localstack

import com.amazonaws.services.dynamodbv2.model.AttributeDefinition
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement
import com.amazonaws.services.dynamodbv2.model.KeyType
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest(classes = [DynamoDBConfiguration.class])
class LocalStackApplicationTest extends Specification {

    @Autowired
    private DynamoDBService dynamoDBService;


    def "context load test"(){
        expect:
            print("Context is loaded")
    }

    def "test table creation"(){
        given:
            def tableName = "TEST"
            def hashKey = new KeySchemaElement("year", KeyType.HASH)
            def rangeKey = new KeySchemaElement("title", KeyType.RANGE)
            def yearAttribute = new AttributeDefinition("year", ScalarAttributeType.N)
            def titleAttribute = new AttributeDefinition("title", ScalarAttributeType.S)
            def provisioning = new ProvisionedThroughput(10L, 10L)

        when:
            def result = dynamoDBService.createTable(
                    tableName,
                    [hashKey, rangeKey],
                    [yearAttribute, titleAttribute],
                    provisioning
            )

        and:
            result
        then:
            dynamoDBService.getTable("TEST").getTableName() == tableName
    }

    def "test table deleting"(){
        when:
            def result = dynamoDBService.deleteTable("TEST")

        then:
            result
    }

}
