package me.itsy.reroll.Commands;

import me.itsy.reroll.Manager.ConfManager;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.config.ConfigManager;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class Toggle implements CommandExecutor {
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

        if(ConfManager.getConfNode("enabled").getBoolean()){
            ConfManager.getConfNode("enabled").setValue(false);
            ConfManager.save();
            src.sendMessage(Text.of(TextColors.GOLD,"Successfully ",TextColors.RED,"Disabled",TextColors.GOLD," starter re-rolling!"));
        }else{
            ConfManager.getConfNode("enabled").setValue(true);
            ConfManager.save();
            src.sendMessage(Text.of(TextColors.GOLD,"Successfully ",TextColors.GREEN,"Enabled",TextColors.GOLD," starter re-rolling!"));
        }

        return CommandResult.success();
    }
}
