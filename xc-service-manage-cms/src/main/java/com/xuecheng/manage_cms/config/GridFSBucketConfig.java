package com.xuecheng.manage_cms.config;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 蔡闯王
 * @date 2019/1/29 19:11
 */
@Configuration
public class GridFSBucketConfig {

    @Value("${spring.data.mongodb.database}")
    String mongodb;

    @Bean
    public GridFSBucket getGridFSBucket(MongoClient mongoClient) {
        MongoDatabase database = mongoClient.getDatabase(mongodb);
        GridFSBucket bucket = GridFSBuckets.create(database);
        return bucket;
    }
}
