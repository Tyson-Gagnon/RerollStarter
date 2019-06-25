package me.itsy.Manager;

import me.itsy.ReRoll;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConfManager {

    private static Path dir;
    private static ConfigurationLoader<CommentedConfigurationNode> configLoader;
    private static CommentedConfigurationNode configNode;

    public static void setup(Path file) {

        dir = file;
        dir.resolve("Reroll.conf");
        load();
    }

    private static void load() {
        try{
            if(!Files.exists(dir)){
                Files.createDirectories(dir);
                configLoader = HoconConfigurationLoader.builder().setPath(dir).build();
                configNode = configLoader.load();
                configNode.getNode("Enabled").setValue(true);
                save();
            }
        }catch (IOException e){
            ReRoll.getLogger().info("Could not create default directory");
            e.printStackTrace();
        }
    }

    private static void save(){
        try {
            configLoader.save(configNode);
        }catch (IOException e){
            e.printStackTrace();
            ReRoll.getLogger().info("Could not save node!");
        }
    }

    public static CommentedConfigurationNode getConfNode(Object... node){
        return configNode.getNode(node);
    }


}
