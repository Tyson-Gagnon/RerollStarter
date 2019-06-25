package me.itsy.Manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class Storage extends SQLManager {

    public static UUID getUUID(UUID player){
        String pokemonUUID = player.toString();

        try{

            Connection connection = getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM PLAYERSSTARTERS WHERE PLAYER=?");
            preparedStatement.setString(1,player.toString());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                pokemonUUID = resultSet.getString("POKEMON");
            }


        }catch (SQLException e){
            e.printStackTrace();
        }

        return UUID.fromString(pokemonUUID);
    }

    public static void setUUID(UUID player, UUID pokemon){
        try{
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("MERGE INTO PLAYERSSTARTERS (PLAYER,POKEMON) KEY (PLAYER) VALUES (??)");

            preparedStatement.setString(1,player.toString());
            preparedStatement.setString(2,pokemon.toString());

            preparedStatement.execute();

            preparedStatement.close();
            connection.close();;


        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }
}
