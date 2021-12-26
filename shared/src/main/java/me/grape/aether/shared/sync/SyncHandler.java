package me.grape.aether.shared.sync;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import me.grape.aether.shared.Aether;
import org.apache.commons.lang3.RandomStringUtils;
import org.bson.Document;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * @author Grape (grape#9876)
 * 10/29/2021 / 10:26 PM
 * Aether / me.grape.aether.shared.sync
 */
public class SyncHandler {

    private final MongoCollection<Document> sync;
    private final Aether aether;

    public SyncHandler(Aether aether) {
        this.aether = aether;
        sync = aether.getMongoHandler().getMongoDatabase().getCollection("sync");
    }

    public boolean hasSynced(UUID uuid) {
        Document document = sync.find(Filters.eq("uuid", uuid.toString())).first();
        if (document != null)
            return document.getBoolean("hasSynced");
        return false;
    }

    public boolean hasSynced(Long discordId) {
        Document document = sync.find(Filters.eq("discordId", discordId.toString())).first();
        if (document != null)
            return document.getBoolean("hasSynced");
        return false;
    }

    public boolean hasRequested(UUID uuid) {
        Document document = sync.find(Filters.eq("uuid", uuid.toString())).first();
        if (document != null)
            return document.getBoolean("hasRequested");
        return false;
    }

    public String getCode(UUID uuid) {
        Document document = sync.find(Filters.eq("uuid", uuid.toString())).first();
        if (document != null)
            return document.getString("code");
        return null;
    }

    public String getMinecraftName(Long discordId) {
        Document document = sync.find(Filters.eq("discordId", discordId.toString())).first();
        if (document != null)
            return document.getString("name");
        return null;
    }

    public boolean doesCodeExist(String code) {
        Document document = sync.find(Filters.eq("code", code)).first();
        return document != null;
    }

    public boolean isCodeUsed(String code) {
        Document document = sync.find(Filters.eq("code", code)).first();
        return document != null && !document.getBoolean("hasRequested");
    }

    public void unSync(UUID uuid) {
        Document document = sync.find(Filters.eq("uuid", uuid.toString())).first();
        sync.deleteOne(document);
    }

    public void unSync(Long discordId) {
        Document document = sync.find(Filters.eq("discordId", discordId.toString())).first();
        sync.deleteOne(document);
    }

    public CompletableFuture<Void> acceptSync(Long discordID, String code) {
        return CompletableFuture.runAsync(() -> {
            Document document = sync.find(Filters.eq("code", code)).first();
            if (document == null) {
                return;
            }
            Document update = new Document("discordId", discordID.toString());
            update.append("hasRequested", false);
            update.append("hasSynced", true);
            sync.updateOne(document, new Document("$set", update));
        });
    }

    private String generateCode() {
        String code = RandomStringUtils.randomAlphanumeric(6);
        Document document = sync.find(Filters.eq("code", code)).first();
        if (document == null) {
            return code;
        }
        return this.generateCode();
    }

    public String sendRequest(UUID uuid, String name) {
        String value = generateCode();
        aether.getExecutorService().execute(() -> {
            Document document = new Document("uuid", uuid.toString());
            document.append("name", name);
            document.append("discordId", null);
            document.append("hasRequested", true);
            document.append("hasSynced", false);
            document.append("code", value);
            sync.insertOne(document);
        });
        return value;
    }


}
