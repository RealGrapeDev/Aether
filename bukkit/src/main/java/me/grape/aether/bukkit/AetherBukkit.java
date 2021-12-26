package me.grape.aether.bukkit;

import io.github.nosequel.command.CommandHandler;
import io.github.nosequel.command.bukkit.BukkitCommandHandler;
import lombok.Getter;
import me.grape.aether.bukkit.commands.SyncCommands;
import me.grape.aether.bukkit.config.DatabaseConfig;
import me.grape.aether.shared.Aether;
import me.grape.aether.shared.credentials.MongoCredentials;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author Grape (grape#9876)
 * 10/27/2021 / 12:23 AM
 * Aether / me.grape.aether.bukkit
 */
public class AetherBukkit extends JavaPlugin {

    @Getter
    private Aether aether;

    @Override
    public void onEnable() {
        /* Load our database configuration */
        new DatabaseConfig(this);
        /* Register our shared instance */
        loadAether();
        /* Register our Command Handler & Commands */
        final CommandHandler commandHandler = new BukkitCommandHandler("aether");
        commandHandler.registerCommand(new SyncCommands(aether));

    }

    private void loadAether() {
        aether = new Aether(new MongoCredentials(
                DatabaseConfig.MONGO_HOSTNAME,
                DatabaseConfig.MONGO_PORT,
                DatabaseConfig.MONGO_DATABASE,
                DatabaseConfig.MONGO_AUTH,
                DatabaseConfig.MONGO_USERNAME,
                DatabaseConfig.MONGO_PASSWORD
        ));
    }
}
