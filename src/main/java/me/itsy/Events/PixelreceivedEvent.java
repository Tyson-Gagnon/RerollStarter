package me.itsy.Events;

import com.pixelmonmod.pixelmon.api.enums.ReceiveType;
import com.pixelmonmod.pixelmon.api.events.PixelmonReceivedEvent;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import me.itsy.Manager.Storage;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.spongepowered.api.entity.living.player.Player;

import java.util.UUID;

public class PixelreceivedEvent {


    @SubscribeEvent
    public void onPIxel(PixelmonReceivedEvent e){

        ReceiveType receiveType = ReceiveType.Starter;

        if(e.receiveType.equals(receiveType)){
            Pokemon pokemon = e.pokemon;
            UUID pokemonUUID = pokemon.getUUID();
            Storage.setUUID(e.player.getUniqueID(),pokemonUUID);
        }
    }
}
