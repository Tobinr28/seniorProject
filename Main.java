package com.company;
import com.sun.security.jgss.GSSUtil;

import java.sql.*;
import java.util.Scanner;

public class Main {
    public static final String DB_NAME = "SP.db";
    public static final String CONNECTION_STRING = "jdbc:sqlite:D:\\databases\\" + DB_NAME;

    public static final String TABLE_USERS = "users";
    public static final String COLUMN_USER_ID = "User_id";
    public static final String COLUMN_USERNAME = "Username";
    public static final String COLUMN_PASSWORD = "Password";

    public static final String TABLE_RESTAURANT = "restaurant";
    public static final String COLUMN_RESTAURANT_ID   = "Restaurant_id";
    public static final String COLUMN_RESTAURANT_NAME   = "RestaurantName";
    public static final String COLUMN_RESTAURANT_PHONE_NUMBER   = "PhoneNumber";
    public static final String COLUMN_RESTAURANT_LOCATION = "Location";
    public static final String COLUMN_RESTAURANT_ZIPCODE   = "ZipCode";
    public static final String COLUMN_RESTAURANT_CUISINE   = "Cuisine";
    public static final String COLUMN_RESTAURANT_FOODSELECTION   = "FoodSelection";
    public static final String COLUMN_RESTAURANT_OWNER = "Owner";

    public static void main(String[] args) {
        try{
            Connection conn = DriverManager.getConnection(CONNECTION_STRING);
            Statement statement = conn.createStatement();

            /***************************************************************
             * Start login prompt
             **************************************************************/
            int choice = 5;
            String loginName = "";
            Scanner scan = new Scanner(System.in);
            while(choice != 0) {
                while (choice != 1 && choice != 2 && choice != -1) {
                    System.out.println("Enter 1 if you already a user, or enter 2 to become a user");
                    System.out.println();

                    try {
                        choice = Integer.parseInt(scan.nextLine());
                    } catch (Exception e) {
                        System.out.println("Invalid input, try again.");
                    }
                }
                if (choice == 1) {
                    System.out.println("Enter your username");  // This code gets the users username
                    String userName = scan.nextLine();          //
                    System.out.println("Enter your password");  //  This code gets the password
                    String password = scan.nextLine();          //
                    try {
                        ResultSet results = statement.executeQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USERNAME + " = '" + userName + "'");

                        //System.out.println("this is the results.getString(COLUMN_USERNAME): " + results.getString(COLUMN_USERNAME));  this displays the username from the table, only use for testing
                        //System.out.println("this is the results.getString(COLUMN_PASSWORD): "  + results.getString(COLUMN_PASSWORD)); this displays the password from the table, ^

                        if(results.getString(COLUMN_USERNAME).equals(userName) && results.getString(COLUMN_PASSWORD).equals(password)){ //This compares the string inputted, to the string from the select statement,
                            System.out.println("You have logged in");                                                                   // Successful login message
                            loginName = userName;                                                                                        // This saves the login information into a variable we can use later
                            choice = 0;                                                                                                // if equals it continues the code out of the login prompt
                            results.close();                                                                                            //
                        }
                    } catch (Exception e) {
                        System.out.println("Invalid input, try again");

                    }                                                                                                   //




//                    while(results.next()){ //This cycles through the results of the SELECT query and keeps going until there are no more lines
//                        System.out.println(results.getString(COLUMN_USERNAME));
//                        System.out.println(results.getString(COLUMN_PASSWORD));
//                    }



                }
                if (choice == 2) {
                    System.out.println("Enter your NEW username");  // This code gets the users new username
                    String newUserName = scan.nextLine();       //
                    System.out.println("Enter your NEW password");  //  This code gets the new password
                    String newPassWord = scan.nextLine();       //
                    insertNewUsernameAndPassword(statement, newUserName, newPassWord); //creates a new username and password
                    System.out.println("Your new account has been created! " +
                            "You will be navigated back to the login screen, " +
                            "try to login with your newly created credentials. ");
                    choice = 0;
                } //creates a new username and password codeblock

                if(choice == -1){
                    System.exit(0);
                }
            }
            /**********************************************************
             * end login prompt
            *********************************************************/

            int menuChoice = 0;



            while(menuChoice != -1){
                menuChoice = chooseFromMainMenu();
                switch(menuChoice) {

                    case 1:
                        System.out.println("You chose 1");
                        searchRestaurants(statement);
                        break;
                    case 2:
                        System.out.println("You chose 2");
                        insertNewRestaurant(statement,loginName);
                        break;
                    case 3:
                        System.out.println("You chose 3");
                        break;
                    case 4:
                        System.out.println("You chose 4");
                        break;
                    case 5:
                        System.out.println("You chose 5");
                        break;
                    case 6:
                        System.out.println("You chose 6");
                        break;
                    case 7:
                        System.out.println("You chose 7");
                        break;
                    case 8:
                        System.out.println("You chose 8");
                        break;
                    case 9:
                        System.out.println("You chose 9");
                        break;
                    case 10:
                        System.out.println("You chose 10");
                        break;
                    case -1:
                        System.out.println("logging off");
                        System.exit(0);

                    default:
                        System.out.println("You did not choose a valid option, please try again");
                        menuChoice = 0;
                        break;

                }






            }





            /*
            This is how you get results from a statement and display them on the screen

            ResultSet results = statement.executeQuery("SELECT * FROM " + TABLE_USERS);
            while(results.next()){
                System.out.println(results.getString(COLUMN_USERNAME));
                System.out.println(results.getString(COLUMN_PASSWORD));
            }*/
            //results.close();
            statement.close();
            conn.close();
        } catch (SQLException e){
            System.out.println("Something in SQL went wrong: " + e.getMessage());
        } catch(Exception e) {
            System.out.println("Some other error: " + e.getMessage());
        }




    }
    public static void displayMainMenu(){
        System.out.println("WELCOME TO FOODFINDER!");
        System.out.println();
        System.out.println("Choose your option");
        System.out.println("1 - Search for restaurants");
        System.out.println("2 - Add your restaurant to our database");
        System.out.println("3 - Place an order");
        System.out.println("4 - See MY FAVORITES");
        System.out.println("5 - Edit my Account");
        System.out.println("Enter -1 to quit.");

    }
    public static int chooseFromMainMenu(){
        displayMainMenu();
        int choice = 0 ;
        Scanner scan = new Scanner(System.in);
        int valid = 0;

            while(valid == 0)
            try {
                choice = Integer.parseInt(scan.nextLine());
                valid = 1;
            }
            catch (Exception e){
                System.out.println("Invalid input, try again.");
                e.printStackTrace();
            }

        return choice;
    }
    public static void insertNewUsernameAndPassword(Statement statement, String username, String password) throws SQLException{
        statement.execute("INSERT INTO " + TABLE_USERS +
                " (" + COLUMN_USERNAME + "," +
                COLUMN_PASSWORD +
                ") " +
                "VALUES('" + username + "','" + password + "')");
    }
    public static void insertNewRestaurant(Statement statement, String username) throws SQLException{

        String restaurantName = "", phoneNumber = "",location = "", zipCode = "",cuisine = "";
        Scanner scan = new Scanner(System.in);
        try{
            System.out.println("Enter your restaurants name: ");
            restaurantName = scan.nextLine();
            System.out.println("Enter your phone number name: ");
            phoneNumber = scan.nextLine();
            System.out.println("Enter your address: ");
            location = scan.nextLine();
            System.out.println("Enter your zipcode: ");
            zipCode = scan.nextLine();
            System.out.println("Enter your restaurants cuisine: ");
            cuisine = scan.nextLine();
//            statement.execute("INSERT INTO " + TABLE_USERS +
//                    " (" + COLUMN_USERNAME + "," +
//                    COLUMN_PASSWORD +
//                    ") " +
//                    "VALUES('" + username + "','" + password + "')");
   statement.execute("INSERT INTO " + TABLE_RESTAURANT +
           " (" + COLUMN_RESTAURANT_NAME + "," +
                COLUMN_RESTAURANT_PHONE_NUMBER + "," +
                COLUMN_RESTAURANT_LOCATION + "," +
                COLUMN_RESTAURANT_ZIPCODE + "," +
                COLUMN_RESTAURANT_CUISINE + "," +
                COLUMN_RESTAURANT_OWNER +
                ") " +
                "VALUES('" + restaurantName + "','" + phoneNumber + "','" + location + "','" + zipCode + "','" + cuisine + "','" + username + "')");

        } catch (SQLException e) {
            System.out.println("SQL exception my  message : " + e.getMessage());
            e.printStackTrace();
        }
        catch (Exception e){
            System.out.println("Error : " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void searchRestaurants(Statement statement) throws SQLException{
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the zip code that you want to search for restaurants in.");
        String zip = scan.nextLine();

        ResultSet results = statement.executeQuery("SELECT * FROM " + TABLE_RESTAURANT + " WHERE " + COLUMN_RESTAURANT_ZIPCODE + " = " + zip);
        while(results.next()){
            System.out.print("Name: " + results.getString(COLUMN_RESTAURANT_NAME)+ " ,");
            System.out.print("Address: " + results.getString(COLUMN_RESTAURANT_LOCATION)+ " ,");
            System.out.print("Cuisine: " + results.getString(COLUMN_RESTAURANT_CUISINE)+ " ,");
            System.out.print("Phone #: " + results.getString(COLUMN_RESTAURANT_PHONE_NUMBER)+ " ,");
            System.out.println();
        }
        System.out.println("Press enter to return to menu");
    }
}
