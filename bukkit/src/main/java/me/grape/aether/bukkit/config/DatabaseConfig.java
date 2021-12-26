package me.grape.aether.bukkit.config;

import com.sun.java.accessibility.util.TopLevelWindowListener;
import me.grape.aether.bukkit.AetherBukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

/**
 * @author Grape (grape#9876)
 * 10/29/2021 / 12:32 AM
 * Aether / me.grape.aether.bukkit.config
 */
public class DatabaseConfig {

    private AetherBukkit aetherBukkit;
    private File configFile;
    private FileConfiguration config;

    public DatabaseConfig(AetherBukkit aetherBukkit) {
        this.aetherBukkit = aetherBukkit;
        if (!aetherBukkit.getDataFolder().exists()) {
            if (aetherBukkit.getDataFolder().mkdir()) {
                aetherBukkit.getLogger().log(Level.INFO, "Created plugin datafolder.");
            }
        }
        this.configFile = new File(aetherBukkit.getDataFolder(), "database.yml");
        this.config = YamlConfiguration.loadConfiguration(this.configFile);
        addDefaults();
        loadDefaults();
    }

    public static String MONGO_HOSTNAME;
    public static int MONGO_PORT;
    public static boolean MONGO_AUTH;
    public static String MONGO_USERNAME;
    public static String MONGO_PASSWORD;
    public static String MONGO_DATABASE;



    public void addDefaults() {
        this.config.options().copyDefaults(true);

        this.config.addDefault("MONGO.HOSTNAME", "localhost");
        this.config.addDefault("MONGO.PORT", 27017);
        this.config.addDefault("MONGO.AUTH.ENABLED", false);
        this.config.addDefault("MONGO.AUTH.USERNAME", "Username");
        this.config.addDefault("MONGO.AUTH.PASSWORD", "Password");
        this.config.addDefault("MONGO.AUTH.DATABASE", "Aether");

        save();
    }

    public void loadDefaults() {
        MONGO_HOSTNAME = this.config.getString("MONGO.HOSTNAME");
        MONGO_PORT = this.config.getInt("MONGO.PORT");
        MONGO_AUTH = this.config.getBoolean("MONGO.AUTH.ENABLED");
        MONGO_USERNAME = this.config.getString("MONGO.AUTH.USERNAME");
        MONGO_PASSWORD = this.config.getString("MONGO.AUTH.PASSWORD");
        MONGO_DATABASE = this.config.getString("MONGO.AUTH.DATABASE");

    }

    public void save() {
        try {
            this.config.save(this.configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
