package me.itsy;

import com.pixelmonmod.pixelmon.Pixelmon;
import me.itsy.Commands.ReRollCommand;
import me.itsy.Events.JoinServer;
import me.itsy.Events.PixelreceivedEvent;
import me.itsy.Manager.ConfManager;
import me.itsy.Manager.SQLManager;
import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Text;
import sun.rmi.runtime.Log;

import javax.inject.Inject;
import java.nio.file.Path;


@Plugin(name = "ReRoller",
        version = "0.1,",
        id = "rerollpixel",
        authors = "itsyxd")
public class ReRoll {

    @Inject
    @ConfigDir(sharedRoot = false)
    private Path dir;

    private static ReRoll instance;
    public static ReRoll getInstance(){return instance;}

    public static Path getDir() {
        return instance.dir;
    }

    @Inject
    Game game;

    @Listener
    public void onPreInit(GamePreInitializationEvent e){
        instance = this;
        ConfManager.setup(dir);
        SQLManager.load();

    }

    @Listener
    public void onEnable(GameInitializationEvent e){


        regiseterCommands();
        registerListeners();

    }

    public void regiseterCommands(){

        CommandSpec reRolleCMD = CommandSpec.builder()
                .arguments(GenericArguments.player(Text.of("target")))
                .executor(new ReRollCommand())
                .permission("reroll.reroll")
                .build();

        game.getCommandManager().register(this, reRolleCMD,"Reroll");
    }

    public void registerListeners(){

        //game.getEventManager().registerListeners(this, new JoinServer());
        Pixelmon.EVENT_BUS.register(new PixelreceivedEvent());

    }





}
