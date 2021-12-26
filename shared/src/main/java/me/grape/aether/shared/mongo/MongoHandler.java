package me.grape.aether.shared.mongo;

import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import me.grape.aether.shared.credentials.MongoCredentials;
import org.bson.Document;

import java.util.Collections;

/**
 * @author Grape (grape#9876)
 * 10/29/2021 / 12:50 AM
 * Aether / me.grape.aether.shared.mongo
 */
public class MongoHandler {

    @Getter
    private final MongoDatabase mongoDatabase;

    public MongoHandler(final MongoCredentials mongoCredentials) {
        final MongoClient mongoClient;
        if (mongoCredentials.isAuth()) {
            mongoClient = new MongoClient(new ServerAddress(mongoCredentials.getHostname(),
                    mongoCredentials.getPort()),
                    Collections.singletonList(MongoCredential.createCredential(mongoCredentials.getUsername(),
                            mongoCredentials.getDatabase(),
                            mongoCredentials.getPassword().toCharArray())));
        } else {
            mongoClient = new MongoClient(mongoCredentials.getHostname(), mongoCredentials.getPort());
        }
        mongoDatabase = mongoClient.getDatabase("Aether");
    }


}
