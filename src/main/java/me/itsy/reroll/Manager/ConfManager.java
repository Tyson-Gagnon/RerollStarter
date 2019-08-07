package me.itsy.reroll.Manager;

import info.pixelmon.repack.ninja.leaping.configurate.commented.CommentedConfigurationNode;
import info.pixelmon.repack.ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import info.pixelmon.repack.ninja.leaping.configurate.loader.ConfigurationLoader;
import me.itsy.reroll.ReRoll;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ConfManager {

    private static Path dir, config;
    private static ConfigurationLoader<info.pixelmon.repack.ninja.leaping.configurate.commented.CommentedConfigurationNode> configurationLoader;
    private static info.pixelmon.repack.ninja.leaping.configurate.commented.CommentedConfigurationNode configurationNode;
    private static final String[] FILES = {"Starters.conf"};

    public static void setup(Path folder) {
        dir = folder;
        config = dir.resolve(FILES[0]);
        load();
    }

    public static void load() {
        try {
            if (!Files.exists(dir)) {
                Files.createDirectory(dir);

                configurationLoader = HoconConfigurationLoader.builder().setPath(config).build();
                configurationNode = configurationLoader.load();


                configurationNode.getNode("enabled").setValue(true);
                save();
            }else{
                configurationLoader = HoconConfigurationLoader.builder().setPath(config).build();
                configurationNode = configurationLoader.load();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void save() {
        try {
            configurationLoader.save(configurationNode);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static CommentedConfigurationNode getConfNode(Object... node) {
        return configurationNode.getNode(node);
    }
}
