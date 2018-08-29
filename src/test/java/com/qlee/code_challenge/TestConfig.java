package com.qlee.code_challenge;

import static com.qlee.code_challenge.TestConstants.*;

import org.bson.Document;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.MongoClient;
import com.qlee.code_challenge.repository.LocationRepository;
import com.qlee.code_challenge.repository.LocationRepositoryImpl;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;

@Configuration
public class TestConfig {

    @Primary
    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        MongodExecutable mongodExecutable;
        MongoTemplate mongoTemplate;
        String ip = "localhost";
        int port = 27019;

        IMongodConfig mongodConfig = new MongodConfigBuilder().version(Version.Main.PRODUCTION)
                .net(new Net(ip, port, Network.localhostIsIPv6()))
                .build();

        MongodStarter starter = MongodStarter.getDefaultInstance();
        mongodExecutable = starter.prepare(mongodConfig);
        mongodExecutable.start();
        mongoTemplate = new MongoTemplate(new MongoClient(ip, port), "test");

        createDataSet(mongoTemplate);
        return mongoTemplate;
    }

    @Bean
    public LocationRepository locationRepository() {
        return new LocationRepositoryImpl();
    }

    public void createDataSet(MongoTemplate mongoTemplate) {
        mongoTemplate.createCollection("locations");
        mongoTemplate.getDb().getCollection("locations").insertOne(Document.parse(JSON_RECORD_1));
        mongoTemplate.getDb().getCollection("locations").insertOne(Document.parse(JSON_RECORD_2));
    }

}
