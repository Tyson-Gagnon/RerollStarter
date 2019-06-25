package me.itsy.Commands;

import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.pokemon.PokemonFactory;
import com.pixelmonmod.pixelmon.api.pokemon.PokemonSpec;
import com.pixelmonmod.pixelmon.api.storage.PartyStorage;
import com.pixelmonmod.pixelmon.config.PixelmonEntityList;
import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;
import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import me.itsy.Manager.Storage;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.util.UUID;

public class ReRollCommand implements CommandExecutor {
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

        Player target = args.<Player>getOne("targer").get();
        UUID pokeUUID = Storage.getUUID(target.getUniqueId());

        PartyStorage party = Pixelmon.storageManager.getParty((EntityPlayerMP)target);

        boolean flag = false;

        EnumSpecies species = null;

        int slot = 0;

        for(int i = 0; i < 6; i++){
            if(party.get(i) != null){
                if(party.get(i).getUUID().equals(pokeUUID)){
                    species = party.get(i).getSpecies();
                    party.set(i,null);
                    slot = i;
                }
            }
        }

        if(species != null){
            PokemonSpec spec = PokemonSpec.from(species.name);
            spec.level = 15;
            World world = DimensionManager.getWorld(0);
            Pokemon pokemon = Pixelmon.pokemonFactory.create(species);
            spec.apply(pokemon);
            party.set(slot,pokemon);

            target.sendMessage(Text.of(TextColors.GREEN,"Your starter has been re-rolled!"));
            src.sendMessage(Text.of(TextColors.GREEN,"Successfully re-rolled ", target.getName(),"'s starter!"));
        }else {
            target.sendMessage(Text.of(TextColors.RED,"You cannot re-roll your started unless you have it in your party!"));
        }



        return CommandResult.success();
    }
}
