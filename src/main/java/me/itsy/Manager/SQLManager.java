package me.itsy.Manager;

import me.itsy.ReRoll;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.service.sql.SqlService;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLManager {

    private static SqlService sql;
    private static final String URI = "jdbc:h2:" + ReRoll.getDir().toString() + "/players.db";

    public static void load(){
        try{
            sql = Sponge.getServiceManager().provide(SqlService.class).get();
            Connection connection = getConnection();
            Statement stmt = connection.createStatement();

            stmt.execute("CREATE TABLE IF NOT EXISTS PLAYERSSTARTERS (" +
                    "PLAYER UUID UNSIGNED NOT NULL," +
                    "POKEMON UUID UNSIGNED," +
                    "PRIMARY KEY (PLAYER))");

            stmt.close();
            connection.close();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    protected static Connection getConnection(){
        try {
            return sql.getDataSource(URI).getConnection();
        } catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

}
