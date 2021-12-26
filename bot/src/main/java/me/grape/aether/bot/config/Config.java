package me.grape.aether.bot.config;

import lombok.Getter;

/**
 * @author Grape (grape#9876)
 * 10/30/2021 / 12:26 AM
 * Aether / me.grape.aether.bot.config
 */
@Getter
public class Config {

    private String token;
    private String guild;
    private String role;

    private String mongo_host;
    private int mongo_port;
    private String mongo_database;
    private boolean mongo_auth;
    private String mongo_username;
    private String mongo_password;

    private EmbedObject alreadySynced;
    private EmbedObject invalidCode;
    private EmbedObject codeUsed;
    private EmbedObject successful;
    private EmbedObject successfulUnsync;
    private EmbedObject notSynced;

}
