package me.itsy.reroll.Events;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.item.inventory.ClickInventoryEvent;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.translation.Translatable;
import org.spongepowered.api.text.translation.Translation;

public class InventoryClickEvent {

    @Listener
    public void inventoryClickEvent(ClickInventoryEvent e){

        Player player = e.getCause().first(Player.class).get();
        Inventory inventory = e.getTargetInventory();

        if(inventory.getName().get().contains("Starter")){
            e.setCancelled(true);
            if(e.getCursorTransaction().getFinal().get(Keys.DISPLAY_NAME).get().toPlain().contains("CONFIRM")){
                Sponge.getCommandManager().process(Sponge.getServer().getConsole(),"reroll " + player.getName());
                player.closeInventory();
            }else if(e.getCursorTransaction().getFinal().get(Keys.DISPLAY_NAME).get().toPlain().contains("DECLINE")){
                player.closeInventory();
            }
        }
    }

}
