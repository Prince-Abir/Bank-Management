package MoneyManager;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionReceiver {

    public static String url = "jdbc:mysql://Your Hostname:Your port/Your DatabaseName";
    public static String userName = "Your Database UserName";
    public static String password = "Your Database Password";


    private static Connection connection;


    public static Connection getConnection(){

        try{
            if(connection == null){

                connection = DriverManager.getConnection(url,userName,password);

            }

        }
        catch (Exception e){

            e.printStackTrace();

        }
        return connection;


    }

}
