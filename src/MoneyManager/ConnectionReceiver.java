package MoneyManager;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionReceiver {

    public static String url = "jdbc:mysql://localhost:3306/money manager";
    public static String userName = "root";
    public static String password = "prince";


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
