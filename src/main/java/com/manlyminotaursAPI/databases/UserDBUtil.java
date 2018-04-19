package com.manlyminotaursAPI.databases;

import com.manlyminotaursAPI.users.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserDBUtil {

    private static int userIDCounter = 0;

    public static void setUserIDCounter(int userIDCounter) {
        UserDBUtil.userIDCounter = userIDCounter;
    }

    /*------------------------------------ Add / Remove / Modify Employee -------------------------------------------------*/
    Employee addUser(String userID, String firstName, String middleName, String lastName, String userType){
        if(userID == null || userID.equals("")) {
            userID = generateUserID();
        }
        Employee userObject = userBuilder(userID,firstName,middleName,lastName, userType);
        Connection connection = DataModelIAPI.getInstance().getNewConnection();
        try {
            String str = "INSERT INTO UserAccount(userID,firstName,middleName,lastName,userType) VALUES (?,?,?,?,?)";

            // Create the prepared statement
            PreparedStatement statement = connection.prepareStatement(str);
            statement.setString(1, userObject.getEmployeeID());
            statement.setString(2, userObject.getFirstName());
            statement.setString(3, userObject.getMiddleName());
            statement.setString(4, userObject.getLastName());
            statement.setString(5, userObject.getEmployeeType());
            System.out.println("Prepared statement created...");
            statement.executeUpdate();
            System.out.println("Employee added to database");
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return userObject;
    }

    boolean removeUser(Employee oldUser){
        boolean isSuccess = false;
        Connection connection = DataModelIAPI.getInstance().getNewConnection();
        try {
            Statement stmt = connection.createStatement();
            String str = "DELETE FROM UserAccount WHERE userID = '" + oldUser.getEmployeeID() + "'";
            stmt.executeUpdate(str);
            stmt.close();
            System.out.println("Node removed from database");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DataModelIAPI.getInstance().closeConnection();
        }
        return isSuccess;
    }

    boolean removeUserByID(String userID){
        boolean isSuccess = false;
        Connection connection = DataModelIAPI.getInstance().getNewConnection();
        try {
            Statement stmt = connection.createStatement();
            String str = "DELETE FROM UserAccount WHERE userID = '" + userID + "'";
            stmt.executeUpdate(str);
            stmt.close();
            System.out.println("Node removed from database");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DataModelIAPI.getInstance().closeConnection();
        }
        return isSuccess;
    }

    boolean removeUserByFields(String firstName, String middleName, String lastName, String userType) {
        Connection connection = DataModelIAPI.getInstance().getNewConnection();
        boolean isSuccess = false;
        try {
            String str = "DELETE FROM UserAccount WHERE firstName = ? AND middleName = ? AND lastName = ? AND userType =?";

            // Create the prepared statement
            PreparedStatement statement = connection.prepareStatement(str);
            statement.setString(1, firstName);
            statement.setString(2, middleName);
            statement.setString(3, lastName);
            statement.setString(4, userType);
            statement.executeUpdate();
            statement.close();
            System.out.println("Employee added to database");
            isSuccess = true;
        } catch (SQLException e)
        {
            e.printStackTrace();
        } finally {
            DataModelIAPI.getInstance().closeConnection();
        }
        return isSuccess;
    }

    public boolean modifyUser(Employee newUser) {
        Connection connection = DataModelIAPI.getInstance().getNewConnection();
        boolean isSuccess = false;
        try {
            String str = "UPDATE UserAccount SET firstName = ?,middleName = ?,lastName = ?,language = ?,userType =? WHERE userID = '"+ newUser.getEmployeeID() +"'" ;

            // Create the prepared statement
            PreparedStatement statement = connection.prepareStatement(str);
            statement.setString(1, newUser.getFirstName());
            statement.setString(2, newUser.getMiddleName());
            statement.setString(3, newUser.getLastName());
            statement.setString(4, newUser.getEmployeeType());
            statement.executeUpdate();
            statement.close();
            System.out.println("Employee added to database");
            isSuccess = true;
        } catch (SQLException e)
        {
            e.printStackTrace();
        } finally {
            DataModelIAPI.getInstance().closeConnection();
        }
        return isSuccess;
    }
    /*------------------------------------ List all users / find a user -------------------------------------------------*/
    /**
     *
     *  get data from UserAccount table in database and put them into the list of request objects
     */
    public List<Employee> retrieveUsers() {
        // Connection
        Connection connection = DataModelIAPI.getInstance().getNewConnection();

        // Variables
        Employee userObject;
        String userID;
        String firstName;
        String middleName;
        String lastName;
        String userType;
        List<Employee> listOfUsers = new ArrayList<>();

        try {
            Statement stmt = connection.createStatement();
            String str = "SELECT * FROM UserAccount";
            ResultSet rset = stmt.executeQuery(str);

            while (rset.next()) {
                userID = rset.getString("userID");
                firstName = rset.getString("firstName");
                middleName = rset.getString("middleName");
                lastName = rset.getString("lastName");
                userType = rset.getString("userType");

                // Add the new edge to the list
                userObject = userBuilder(userID, firstName, middleName, lastName, userType);
                listOfUsers.add(userObject);
                System.out.println("Employee added to the list: " + userID);
            }
            rset.close();
            stmt.close();
            System.out.println("Done adding users");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DataModelIAPI.getInstance().closeConnection();
        }

        return listOfUsers;
    } // retrieveUsers() ends

    List<String> getLanguageList ( String languagesConcat){
        // Input: String languageConcat = "English/Spanish"
        // Output: List <String> languages = [ "English" , "Spanish" ]
        List<String> languages = new ArrayList<String>(Arrays.asList(languagesConcat.split("/")));
        return languages;
    }

    String getLanguageString(List<String> languages){
        String full_language = languages.get(0);
        for(int i = 1; i<languages.size(); i++){
            full_language = full_language + "/" + languages.get(i);
        }
        return full_language;
    }

    Employee getUserByID(String userID){
        // Connection
        Connection connection = DataModelIAPI.getInstance().getNewConnection();

        // Variables
        Employee userObject = null;
        String firstName;
        String middleName;
        String lastName;
        String userType;

        try {
            Statement stmt = connection.createStatement();
            String str = "SELECT * FROM UserAccount WHERE userID = '" + userID + "'";
            ResultSet rset = stmt.executeQuery(str);

            if (rset.next()) {
                firstName = rset.getString("firstName");
                middleName = rset.getString("middleName");
                lastName = rset.getString("lastName");
                userType = rset.getString("userType");

                // Add the new edge to the list
                userObject = userBuilder(userID, firstName, middleName, lastName, userType);
                System.out.println("Employee added to the list: " + userID);
            }
            rset.close();
            stmt.close();
            System.out.println("Done adding users");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DataModelIAPI.getInstance().closeConnection();
        }
        return userObject;
    }

    public static Employee userBuilder(String userID, String firstName, String middleName, String lastName, String userType){
        Employee userObject = new Employee(firstName, middleName, lastName, userID, userType);
        return userObject;
    }

    private String generateUserID(){
        userIDCounter++;
        return Integer.toString(userIDCounter);
    }
}
