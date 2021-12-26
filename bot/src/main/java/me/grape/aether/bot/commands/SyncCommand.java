package me.grape.aether.bot.commands;

import me.grape.aether.bot.AetherBot;
import me.grape.aether.shared.sync.SyncHandler;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

/**
 * @author Grape (grape#9876)
 * 10/30/2021 / 12:32 AM
 * Aether / me.grape.aether.bot.commands
 */
public class SyncCommand extends ListenerAdapter {

    private final AetherBot aetherBot;
    private final SyncHandler syncHandler;

    public SyncCommand(AetherBot aetherBot) {
        this.aetherBot = aetherBot;
        this.syncHandler = aetherBot.getAether().getSyncHandler();
    }

    @Override
    public void onSlashCommand(@NotNull SlashCommandEvent event) {
        if (event.getMember() == null) {
            return;
        }
        if (event.getCommandPath().equalsIgnoreCase("sync")) {
            String code = event.getOptionsByName("code").get(0).getAsString();
            if (syncHandler.hasSynced(event.getMember().getIdLong())) {
                String minecraftName = syncHandler.getMinecraftName(event.getMember().getIdLong());
                event.replyEmbeds(aetherBot.getConfig().getAlreadySynced()
                        .build(minecraftName)).setEphemeral(true).queue();
                return;
            }
            if (!syncHandler.doesCodeExist(code)) {
                event.replyEmbeds(aetherBot.getConfig().getInvalidCode().build("")).setEphemeral(true).queue();
                return;
            }
            if (syncHandler.isCodeUsed(code)) {
                event.replyEmbeds(aetherBot.getConfig().getCodeUsed().build("")).setEphemeral(true).queue();
                return;
            }
            Role role = aetherBot.getGuild().getRoleById(aetherBot.getConfig().getRole());
            if (role != null && aetherBot.getGuild() != null) {
                aetherBot.getGuild().addRoleToMember(event.getMember(), role).queue();
            }
            syncHandler.acceptSync(event.getMember().getIdLong(), code).thenRunAsync(() ->
                    event.replyEmbeds(aetherBot.getConfig().getSuccessful().build(syncHandler.getMinecraftName(event.getMember().getIdLong()))).setEphemeral(true).queue());
            return;
        }
        if (event.getCommandPath().equalsIgnoreCase("unsync")) {
           if (!syncHandler.hasSynced(event.getMember().getIdLong())) {
               event.replyEmbeds(aetherBot.getConfig().getNotSynced().build("")).setEphemeral(true).queue();
               return;
           }
           syncHandler.unSync(event.getMember().getIdLong());
           event.replyEmbeds(aetherBot.getConfig().getSuccessfulUnsync().build("")).setEphemeral(true).queue();
        }
    }
}
