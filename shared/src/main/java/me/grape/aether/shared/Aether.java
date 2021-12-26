package me.grape.aether.shared;

import lombok.Getter;
import me.grape.aether.shared.credentials.MongoCredentials;
import me.grape.aether.shared.mongo.MongoHandler;
import me.grape.aether.shared.sync.SyncHandler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @author Grape (grape#9876)
 * 10/27/2021 / 12:21 AM
 * Aether / me.grape.aether.shared
 */
public class Aether {

    @Getter
    private final MongoHandler mongoHandler;
    @Getter
    private final ScheduledExecutorService executorService;
    @Getter
    private final SyncHandler syncHandler;

    public Aether(final MongoCredentials mongoCredentials) {
        executorService = Executors.newScheduledThreadPool(4);
        mongoHandler = new MongoHandler(mongoCredentials);
        syncHandler = new SyncHandler(this);

    }

}
