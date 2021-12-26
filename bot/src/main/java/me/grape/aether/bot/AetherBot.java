package me.grape.aether.bot;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.SneakyThrows;
import me.grape.aether.bot.commands.SyncCommand;
import me.grape.aether.bot.config.Config;
import me.grape.aether.bot.tasks.MemberCheckTask;
import me.grape.aether.shared.Aether;
import me.grape.aether.shared.credentials.MongoCredentials;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.commands.OptionType;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

/**
 * @author Grape (grape#9876)
 * 10/27/2021 / 12:23 AM
 * Aether / me.grape.aether.bot
 */
public class AetherBot {

    @Getter private Guild guild;
    @Getter private final Config config;
    @Getter private Aether aether;

    public static void main(String[] args) {
        new AetherBot();
    }

    @SneakyThrows
    public AetherBot() {
        ObjectMapper objectMapper = new ObjectMapper();
        /* ---- Creates the config.json and/or loads it. ---- */
        File file = getResourceAsFile("config.json");
        String userDirectory = System.getProperty("user.dir");
        File newFile = new File(userDirectory + "/config.json");

        if (!newFile.exists())
            Files.move(file.getAbsoluteFile().toPath(), newFile.getAbsoluteFile().toPath());

        Path path = newFile.getAbsoluteFile().toPath();
        byte[] data = Files.readAllBytes(path);
        config = objectMapper.readValue(data, Config.class);
        /* ---------------------------------------------------*/
        if (config.getToken().isEmpty()) {
            System.out.println("Token is invalid");
            System.exit(0);
            return;
        }
        JDA jda = JDABuilder.createDefault(config.getToken()).build();
        aether = new Aether(new MongoCredentials(config.getMongo_host(),
                config.getMongo_port(),
                config.getMongo_database(),
                config.isMongo_auth(),
                config.getMongo_username(),
                config.getMongo_password()));
        try {
            jda.awaitReady();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (config.getGuild().isEmpty()) {
            System.out.println("Guild is invalid");
            System.exit(0);
            return;
        }
        guild = jda.getGuildById(config.getGuild());
        if (guild == null) {
            System.out.println("Guild is null");
            System.exit(0);
            return;
        }
        if (config.getRole().isEmpty() || (guild.getRoleById(config.getRole()) == null)) {
            System.out.println("Role is invalid");
            System.exit(0);
            return;
        }
        guild.upsertCommand("sync", "Sync your Discord account to Minecraft")
                .addOption(OptionType.STRING, "code", "Your sync code", true).queue();
        guild.upsertCommand("unsync", "Unsync your Discord account to Minecraft").queue();
        jda.addEventListener(new SyncCommand(this));
        aether.getExecutorService().scheduleAtFixedRate(new MemberCheckTask(this), 0, 1, TimeUnit.MINUTES);
    }


    private File getResourceAsFile(String resourcePath) {
        try {
            InputStream in = ClassLoader.getSystemClassLoader().getResourceAsStream(resourcePath);
            if (in == null) {
                return null;
            }

            File tempFile = File.createTempFile(String.valueOf(in.hashCode()), ".tmp");
            tempFile.deleteOnExit();

            try (FileOutputStream out = new FileOutputStream(tempFile)) {
                //copy stream
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
            }
            return tempFile;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
