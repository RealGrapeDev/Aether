package me.grape.aether.bot.tasks;

import lombok.RequiredArgsConstructor;
import me.grape.aether.bot.AetherBot;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;

/**
 * @author Grape (grape#9876)
 * 11/3/2021 / 9:54 PM
 * Aether / me.grape.aether.bot.tasks
 */
@RequiredArgsConstructor
public class MemberCheckTask implements Runnable {

    private final AetherBot aetherBot;

    @Override
    public void run() {
        for (Member member : this.aetherBot.getGuild().getMembers()) {
            if (!aetherBot.getAether().getSyncHandler().hasSynced(member.getIdLong())) {
                Role role = aetherBot.getGuild().getRoleById(aetherBot.getConfig().getRole());
                if (member.getRoles().contains(role)) {
                    aetherBot.getGuild().removeRoleFromMember(member, role).queue();
                }
            }
        }
    }
}
