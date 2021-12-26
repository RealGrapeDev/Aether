package me.grape.aether.bot.config;

import lombok.Getter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;

/**
 * @author Grape (grape#9876)
 * 11/7/2021 / 10:42 AM
 * Aether / me.grape.aether.bot.config
 */
@Getter
public class EmbedObject {

    private String title;
    private String color;
    private String description;
    private String footer;
    private String thumbnail;

    public MessageEmbed build(String player) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        if (title.isEmpty()) embedBuilder.setTitle("Empty Title"); else embedBuilder.setTitle(title.replace("{player}", player));
        if (color.isEmpty()) embedBuilder.setColor(Color.BLACK); else embedBuilder.setColor(Color.decode(color));
        if (description.isEmpty()) embedBuilder.setDescription("Empty Description"); else embedBuilder.setDescription(description.replace("{player}", player));
        if (footer.isEmpty()) embedBuilder.setFooter("Empty Footer"); else embedBuilder.setFooter(footer.replace("{player}", player));
        if (!thumbnail.isEmpty()) embedBuilder.setThumbnail(thumbnail.replace("{player}", player));
        return embedBuilder.build();
    }

}
