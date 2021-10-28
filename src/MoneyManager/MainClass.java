package MoneyManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;



public class MainClass {

    public static void main(String[] args) {

        System.out.println("\n\n------------------|Welcome to Money Manager|----------------------");

        All_Functionality allFunctionality = new All_Functionality();
        allFunctionality.welcomeScreen();


    }
}



class All_Functionality {

    Connection connection = ConnectionReceiver.getConnection();

    String query1 = "insert userdata (name,email,password,confirmPassword) values(?,?,?,?)";
    String query2 = "select * from userdata";

    public String name;
    public String email;
    public String password;
    public String confirmPassword;
    public String LogEmail;
    public String LogPass;

    public String DatabaseName;
    public String DatabaseEmail;
    public String DatabasePassword;

    public double mainBalance = 0;
    public double money;



    protected void welcomeScreen(){

        try {
            System.out.println("1. Sign Up" );
            System.out.println("2. Sign In" );
            System.out.print("Enter any Number: ");
            Scanner input = new Scanner(System.in);

            int InputNumber = input.nextInt();

            if (InputNumber == 1){

                Registration();
            }

            else if(InputNumber == 2)
            {
                Login();
            }
        }
        catch (InputMismatchException e){
            System.out.println("Invalid Input! Enter Number 1 or 2");
            welcomeScreen();
        }
    }




    void Registration() {

        try{

            System.out.println("-------------------------# SIGN UP #-----------------------------");


            System.out.print("\n\nEnter Your Name:  ");
            Scanner scanner1 = new Scanner(System.in);
            name = scanner1.nextLine();


            System.out.print("\nEnter Your Email:  ");
            Scanner scanner2 = new Scanner(System.in);
            email = scanner2.nextLine();

            System.out.print("\nEnter Your Password:  ");
            Scanner scanner3 = new Scanner(System.in);
            password = scanner3.nextLine();

            System.out.print("\nEnter Your Confirm Password:  ");
            Scanner scanner4 = new Scanner(System.in);
            confirmPassword = scanner4.nextLine();

            PreparedStatement preStmt = connection.prepareStatement(query1);



                if (name.isEmpty()){
                    System.out.println("\nSorry ): Name can not be Empty\n\n");
                    Registration();
                }
                else {
                    if (email.contains("@gmail.com") || email.contains("@yahoo.com") || email.contains("@hotmail.com")) {

                        if (password.equals(confirmPassword)) {

                            preStmt.setString(1, name);
                            preStmt.setString(2, email);
                            preStmt.setString(3, password);
                            preStmt.setString(4, confirmPassword);

                            preStmt.executeUpdate();
                            System.out.println("\nYou Have Registered Successfully :)!");
                        } else {

                            System.out.println("\nSorry ): Password Does not Match.Try Again!\n\n");
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                System.out.println("Thread Exception Occurs");
                            }
                            Registration();

                        }

                    }
                    else{
                        System.out.println("\nSorry ): Email Extension is not Correct!\n\n");
                        Registration();
                    }

                }

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Login();


        }
        catch (SQLException e){
            System.out.println("MySQL Database Problem");
        }

    }




    void Login() {

       try {

           System.out.println("\n\n-------------------------** SIGN IN **------------------------------");

           System.out.print("\nEnter Your Email:  ");
           BufferedReader readEmail = new BufferedReader(new InputStreamReader(System.in));
           LogEmail = readEmail.readLine();

           System.out.print("\nEnter Your Password:  ");
           BufferedReader readPass = new BufferedReader(new InputStreamReader(System.in));
           LogPass = readPass.readLine();


           Statement stmt = connection.createStatement();
           ResultSet result = stmt.executeQuery(query2);

           while (result.next()) {

               DatabaseName = result.getString("name");

               DatabaseEmail = result.getString("email");

               DatabasePassword = result.getString("password");

               if (DatabaseEmail.equals(LogEmail) && DatabasePassword.equals(LogPass)) {
                   System.out.println("\n\nWelcome Back! "+DatabaseName);
                   Display();
               }

           }
           System.out.println("\nSorry ): Email or Password Does not Match!\n\n");
           welcomeScreen();
       }



        catch (InputMismatchException e){

            System.out.println("\n\nInvalid Input! Please Enter Your Correct Email and Password");

        }

       catch (SQLException sqlException){

           System.out.println("\n\nSorry You have not Registered yet!");
           welcomeScreen();

       }

       catch (IOException ioException){
           System.out.println("\n\nBufferReader readLine Problem");
       }

    }





    void AddMoney() {

        System.out.println("\n\n-----------------------$Deposit$----------------------------");


        System.out.print("\n\nEnter the Amount you want to Deposit : ");
        Scanner scanDepositBalance = new Scanner(System.in);
        money = scanDepositBalance.nextDouble();

        mainBalance = mainBalance + money;

        System.out.println("\n\nYour New Account Balance is : " + mainBalance);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Display();

    }





    void WidthDraw() {

        System.out.println("\n\n-----------------------$Withdraw$-------------------------");

        try {
            System.out.print("\n\nEnter the Amount you want to Withdraw: ");
            Scanner scanWithdrawBalance = new Scanner(System.in);
            money = scanWithdrawBalance.nextDouble();
        }
        catch (InputMismatchException e){
            System.out.println("Invalid Input! Try Again ):");
            WidthDraw();
        }

        if (mainBalance < money) {
            System.out.println("\nSorry You have insufficient Balance ): ");
            Display();
        } else {
            mainBalance = mainBalance - money;
        }


        System.out.println("\nYour New Account Balance is : " + mainBalance);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Display();

    }




    void BalanceStatus() {
        System.out.println("\n\nYour Current Account Balance is : " + mainBalance);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Display();
    }


    void Display() {

        int choice;

        System.out.println("\n\n1. Deposit Money");
        System.out.println("2. Withdraw Money");
        System.out.println("3. Check Balance");


        try {

            System.out.print("\nChoice Any Option Above(1/2/3) :  ");
            Scanner numChecker = new Scanner(System.in);
            choice = numChecker.nextInt();

            if (choice == 1) {
                AddMoney();
            } else if (choice == 2) {
                WidthDraw();
            } else if (choice == 3) {
                BalanceStatus();
            } else {
                System.out.print("\nSorry ): Invalid Input\n\n");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Display();
            }
        } catch (InputMismatchException rep) {
            System.out.print("\nSorry ): Invalid Input\n\n");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Display();

        }
    }


}






