package me.itsy.reroll.Commands;

import me.itsy.reroll.Manager.ConfManager;
import me.itsy.reroll.ReRoll;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.type.DyeColors;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.InventoryArchetypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.property.InventoryDimension;
import org.spongepowered.api.item.inventory.property.InventoryTitle;
import org.spongepowered.api.item.inventory.property.SlotPos;
import org.spongepowered.api.item.inventory.type.GridInventory;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

public class Prompt implements CommandExecutor {
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        Player target = args.<Player>getOne("target").get();

        if (ConfManager.getConfNode("enabled").getBoolean()) {
            if (!ConfManager.getConfNode(target.getName()).isVirtual()) {

                Inventory custom = Inventory.builder()
                        .of(InventoryArchetypes.MENU_GRID)
                        .property(InventoryDimension.of(9, 3))
                        .property(InventoryTitle.PROPERTY_NAME, InventoryTitle.of(Text.of(TextColors.DARK_GRAY, TextStyles.BOLD, "Confirm Starter Reroll?")))
                        .build(ReRoll.getInstance());

                ItemStack confirmButton = ItemStack.builder().itemType(ItemTypes.STAINED_GLASS_PANE).build();
                confirmButton.offer(Keys.DYE_COLOR, DyeColors.GREEN);
                confirmButton.offer(Keys.DISPLAY_NAME,Text.of(TextColors.GREEN,TextStyles.BOLD,"CONFIRM"));
                ItemStack declineButton = ItemStack.builder().itemType(ItemTypes.STAINED_GLASS_PANE).build();
                declineButton.offer(Keys.DYE_COLOR, DyeColors.RED);
                declineButton.offer(Keys.DISPLAY_NAME,Text.of(TextColors.RED,TextStyles.BOLD,"DECLINE"));
                ItemStack filler = ItemStack.builder().itemType(ItemTypes.STAINED_GLASS_PANE).build();
                filler.offer(Keys.DYE_COLOR, DyeColors.BLACK);

                custom.query(GridInventory.class)
                        .query(SlotPos.of(2,1))
                        .offer(confirmButton);
                custom.query(GridInventory.class)
                        .query(SlotPos.of(6,1))
                        .offer(declineButton);

                target.openInventory(custom);

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
