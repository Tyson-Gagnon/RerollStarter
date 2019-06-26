package me.itsy.reroll.Events;

import com.pixelmonmod.pixelmon.api.enums.ReceiveType;
import com.pixelmonmod.pixelmon.api.events.PixelmonReceivedEvent;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import me.itsy.reroll.Manager.ConfManager;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.UUID;

public class PixelreceivedEvent {


    @SubscribeEvent
    public void onPIxel(PixelmonReceivedEvent e){

        ReceiveType receiveType = ReceiveType.Starter;

        if(e.receiveType.equals(receiveType)){
            Pokemon pokemon = e.pokemon;
            UUID pokemonUUID = pokemon.getUUID();
            ConfManager.getConfNode(e.player.getName()).setValue(pokemonUUID.toString());
            ConfManager.save();
        }
    }
}
