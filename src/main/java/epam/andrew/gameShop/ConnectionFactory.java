package epam.andrew.gameShop;

import epam.andrew.gameShop.connection.ConnectionPool;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ConnectionFactory {
    public static void main(String[] args) {
        Connection connection = ConnectionPool.getConnection();
        try {

            Statement myStatement = connection.createStatement();

            ResultSet rSet = myStatement.executeQuery("select * from user");

            while (rSet.next()) {
                System.out.print(rSet.getString("id") + " \t "
                        + rSet.getString("name") + "\n"
                        + rSet.getString("surname") + "\n"
                        + rSet.getString("phone") + "\n"
                        + rSet.getString("gender") + "\t"
                        + rSet.getString("country") + "\t");
            }

            connection.close();


        } catch (Exception e) {
            System.out.println(e);
        }
    }
}