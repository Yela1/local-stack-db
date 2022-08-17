package com.study.localstack;

import com.amazonaws.SDKGlobalConfiguration;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.TableCollection;
import com.amazonaws.services.dynamodbv2.model.*;

import java.util.List;


public class DynamoDBService {
    private final DynamoDB amazonDynamoDBClient;

    public DynamoDBService(DynamoDB amazonDynamoDBClient){
        this.amazonDynamoDBClient = amazonDynamoDBClient;

        String disableSSLVerification = String.format("%b",true);
        System.setProperty(SDKGlobalConfiguration.DISABLE_CERT_CHECKING_SYSTEM_PROPERTY, disableSSLVerification);
    }


    public boolean createTable(String tableName,
                            List<KeySchemaElement> keySchemaElementList,
                            List<AttributeDefinition> attributeDefinitionsList,
                            ProvisionedThroughput provisionedThroughput){
        try {
            Table table = amazonDynamoDBClient.createTable(tableName, keySchemaElementList, attributeDefinitionsList, provisionedThroughput);
            table.waitForActive();

        }catch (Exception e){
            return false;
        }

        return true;
    }

    public boolean deleteTable(String tableName){
        try {
            amazonDynamoDBClient.getTable(tableName).delete();
        }catch (Exception e){
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }

    public Table getTable(String tableName){
        try {
            for(Table table : this.getTables()){
                if(table.getTableName().equals(tableName)){
                    return table;
                }
            }
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
        return null;
    }

    public DynamoDB getClient(){
        return amazonDynamoDBClient;
    }

    public TableCollection<ListTablesResult> getTables(){
        return this.amazonDynamoDBClient.listTables();
    }

}
