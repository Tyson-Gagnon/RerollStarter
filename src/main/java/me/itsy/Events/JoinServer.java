package me.itsy.Events;

import me.itsy.Manager.SQLManager;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.network.ClientConnectionEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JoinServer extends SQLManager {

    @Listener
    public void onJoin(ClientConnectionEvent.Join e){
        Player player = e.getTargetEntity();

        if(!player.hasPlayedBefore()){
           try{
               Connection connection = getConnection();
               PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO " +
                       "PLAYERSSTARTERS (PLAYER) " +
                       "VALUES (?)");

               preparedStatement.setString(1,player.getUniqueId().toString());
               preparedStatement.execute();

               preparedStatement.close();
               connection.close();
           }catch (SQLException ex){
               ex.printStackTrace();
           }


        }
    }

}
