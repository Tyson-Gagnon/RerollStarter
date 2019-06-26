package me.itsy.reroll.Commands;

import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.pokemon.PokemonSpec;
import com.pixelmonmod.pixelmon.api.storage.PartyStorage;
import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import me.itsy.reroll.Manager.ConfManager;
import me.itsy.reroll.ReRoll;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.type.DyeColors;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.InventoryArchetypes;
import org.spongepowered.api.item.inventory.InventoryProperty;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.property.InventoryDimension;
import org.spongepowered.api.item.inventory.property.InventoryTitle;
import org.spongepowered.api.item.inventory.property.SlotPos;
import org.spongepowered.api.item.inventory.type.GridInventory;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

import java.util.UUID;
import java.util.logging.Logger;

public class ReRollCommand implements CommandExecutor {
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

        Player target = args.<Player>getOne("target").get();

        if (ConfManager.getConfNode("enabled").getBoolean()) {
            if (!ConfManager.getConfNode(target.getName()).isVirtual()) {
                UUID pokeUUID = UUID.fromString(ConfManager.getConfNode(target.getName()).getString());

                PartyStorage party = Pixelmon.storageManager.getParty((EntityPlayerMP) target);

                boolean flag = false;

                EnumSpecies species = null;

                int slot = 0;

                for (int i = 0; i < 6; i++) {
                    if (party.get(i) != null) {
                        if (party.get(i).getUUID().equals(pokeUUID)) {
                            species = party.get(i).getSpecies();
                            slot = i;
                        }
                    }
                }

                if (species != null) {
                    PokemonSpec spec = PokemonSpec.from(species.name);
                    spec.level = 15;
                    World world = DimensionManager.getWorld(0);
                    Pokemon pokemon = Pixelmon.pokemonFactory.create(species);
                    spec.apply(pokemon);
                    party.set(slot, pokemon);

                    target.sendMessage(Text.of(TextColors.GREEN, "Your starter has been re-rolled!"));
                    src.sendMessage(Text.of(TextColors.GREEN, "Successfully re-rolled ", target.getName(), "'s starter!"));
                    ConfManager.getConfNode(target.getName()).setValue(null);
                    ConfManager.save();
                } else {
                    target.sendMessage(Text.of(TextColors.RED, "You cannot re-roll your starter unless you have it in your party!"));
                }


                return CommandResult.success();

            } else {
                target.sendMessage(Text.of(TextColors.RED, "You have either already rerolled your starter or have not picked one yet!"));
            }
        } else {
            src.sendMessage(Text.of(TextColors.RED, "Re-rolling is currently disabled!"));
            target.sendMessage(Text.of(TextColors.RED, "Re-rolling is currently disabled! Try again later!"));
        }

        return CommandResult.success();
    }
}
