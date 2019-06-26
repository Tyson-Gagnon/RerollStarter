package me.itsy.reroll;

import com.google.inject.Inject;
import com.pixelmonmod.pixelmon.Pixelmon;
import me.itsy.reroll.Commands.Prompt;
import me.itsy.reroll.Commands.ReRollCommand;
import me.itsy.reroll.Commands.Toggle;
import me.itsy.reroll.Events.InventoryClickEvent;
import me.itsy.reroll.Events.PixelreceivedEvent;
import me.itsy.reroll.Manager.ConfManager;
import org.spongepowered.api.Game;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.game.state.GameStoppedServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Text;
import scala.tools.nsc.backend.icode.analysis.TypeFlowAnalysis;

import java.nio.file.Path;

@Plugin(name = "ReRoller",
        version = "0.1,",
        id = "rerollpixel",
        authors = "itsyxd"
)
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
    public void onPreInitialization(GamePreInitializationEvent e) {

        instance = this;
        ConfManager.setup(dir);


    }

    @Listener
    public void onEnable(GameInitializationEvent e){


        regiseterCommands();
        registerListeners();

    }

    private void regiseterCommands(){


        CommandSpec rePrompt = CommandSpec.builder()
                .arguments(GenericArguments.player(Text.of("target")))
                .executor(new Prompt())
                .permission("reroll.prompt")
                .build();

        CommandSpec reToggle = CommandSpec.builder()
                .executor(new Toggle())
                .permission("reroll.toggle")
                .build();

        CommandSpec reRolleCMD = CommandSpec.builder()
                .arguments(GenericArguments.player(Text.of("target")))
                .executor(new ReRollCommand())
                .permission("reroll.reroll")
                .child(reToggle,"toggle")
                .child(rePrompt,"prompt")
                .build();

        game.getCommandManager().register(this, reRolleCMD,"Reroll");
    }

    private void registerListeners(){
        game.getEventManager().registerListeners(this, new InventoryClickEvent());
        Pixelmon.EVENT_BUS.register(new PixelreceivedEvent());

    }

    @Listener
    public void onDisable(GameStoppedServerEvent e) {

    }




}
