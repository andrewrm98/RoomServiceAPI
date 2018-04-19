package com.manlyminotaurs.databases;

import com.manlyminotaurs.messaging.InventoryItem;
import com.manlyminotaurs.messaging.Request;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//   __   __          ___          ___
//  |  \ |__)    |  |  |  | |    |  |  \ /
//  |__/ |__)    \__/  |  | |___ |  |   |
//
//         __   ___  __        ___  __  ___  __
//     __ |__) |__  /  \ |  | |__  /__`  |  /__`
//        |  \ |___ \__X \__/ |___ .__/  |  .__/
//

class RequestsDBUtil {

    /*------------------------------------------------ Variables -----------------------------------------------------*/
    //public static List<Request> requestList = new ArrayList<>();
    private static int requestIDCounter = 0;

    public static void setRequestIDCounter(int requestIDCounter) {
        RequestsDBUtil.requestIDCounter = requestIDCounter;
    }

    public String generateRequestID(){
        requestIDCounter++;
        return Integer.toString(requestIDCounter);
    }
    /*------------------------------------------------ Add/Remove Request -------------------------------------------------------*/

    public void addRequest(String room, String employee, ObservableList<InventoryItem> itemList) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:derby:requestDB;create=true");
            String str = "INSERT INTO Request(requestID,requestType,priority,isComplete,adminConfirm,startTime,endTime,nodeID,messageID,password) VALUES (?,?,?,?,?,?,?,?,?,?)";
            String requestID = generateRequestID();
            // Create the prepared statement
            PreparedStatement statement = connection.prepareStatement(str);
            statement.setString(1, requestID);
            statement.setString(2, "Room Service");
            statement.setInt(3, 1);
            statement.setBoolean(4, false);
            statement.setBoolean(5, false);
            statement.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
            statement.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
            statement.setString(8, room);
            statement.setString(9, employee);
            statement.setString(10, getItemString(itemList));
            statement.executeUpdate();
            statement.close();
            System.out.println("Request added to database");
        } catch (SQLException e)
        {
            e.printStackTrace();
        } finally {
            DataModelI.getInstance().closeConnection();
        }
    }

    boolean removeRequest(Request request) {
        Connection connection = DataModelI.getInstance().getNewConnection();
        boolean isSucessful = true;
        try {
            Statement stmt = connection.createStatement();
            String str = "DELETE FROM REQUEST WHERE requestID = '" + request.getRequestID() + "'";
            stmt.executeUpdate(str);
            stmt.close();
            System.out.println("Node removed from database");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DataModelI.getInstance().closeConnection();
        }
        return isSucessful;
    }

    public boolean modifyRequest(Request newRequest) {
        Connection connection = DataModelI.getInstance().getNewConnection();
        boolean isSuccess = false;
        try {
            String str = "UPDATE Request SET requestType= ?,priority = ?,isComplete= ?,adminConfirm= ?,startTime =?, endTime = ?,nodeID= ?,messageID= ?,password= ? WHERE requestID = '"+ newRequest.getRequestID()+ "'";

            // Create the prepared statement
            PreparedStatement statement = connection.prepareStatement(str);
            statement.setString(1, newRequest.getRequestType());
            statement.setInt(2, newRequest.getPriority());
            statement.setBoolean(3, newRequest.getComplete());
            statement.setBoolean(4, newRequest.getAdminConfirm());
            statement.setTimestamp(5, Timestamp.valueOf(newRequest.getStartTime()));
            statement.setTimestamp(6, Timestamp.valueOf(newRequest.getEndTime()));
            statement.setString(7, newRequest.getRequestInfo().getRoom());
            statement.setString(8, newRequest.getRequestInfo().getEmployee());
            statement.setString(9, getItemString(newRequest.getRequestInfo().getItems()));
            System.out.println("Prepared statement created...");
            statement.executeUpdate();
            statement.close();
            isSuccess = true;
        } catch (SQLException e)
        {
            System.out.println("Request already in the database");
        } finally {
            DataModelI.getInstance().closeConnection();
        }
        return isSuccess;
    }


    public List<Request> retrieveRequests() {
        // Connection
        Connection connection = DataModelI.getInstance().getNewConnection();

        // Variables
        Request requestObject;
        String requestID;
        String requestType;
        int priority;
        Boolean isComplete;
        Boolean adminConfirm;
        Timestamp startTime;
        Timestamp endTime;
        String nodeID;
        String messageID;
        String password;
        List<Request> listOfRequest = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            String str = "SELECT * FROM Request";
            ResultSet rset = stmt.executeQuery(str);

            while (rset.next()) {
                requestID = rset.getString("requestID");
                requestType = rset.getString("requestType");
                priority = rset.getInt("priority");
                isComplete =rset.getBoolean("isComplete");
                adminConfirm = rset.getBoolean("adminConfirm");
                startTime = rset.getTimestamp("startTime");
                endTime = rset.getTimestamp("endTime");
                nodeID = rset.getString("nodeID"); //room number
                messageID = rset.getString("messageID"); // employee
                password = rset.getString("password"); // list of itemID
                // Add the new edge to the list
                ObservableList<InventoryItem> items = DataModelI.getInstance().getItemList(password);
                requestObject = new Request(requestID, requestType, priority, isComplete, adminConfirm, startTime.toLocalDateTime(), endTime.toLocalDateTime(), nodeID, messageID, items);
                listOfRequest.add(requestObject);
            }
            rset.close();
            stmt.close();
            System.out.println("Done adding Requests");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DataModelI.getInstance().closeConnection();
        }
        return listOfRequest;
    }

    /*------------------------------------ Set status Complete/Admin Confirm -------------------------------------------------*/
    void setIsAdminConfim(Request request, boolean newConfirmStatus){
        Connection connection = DataModelI.getInstance().getNewConnection();
        try {
            Statement stmt = connection.createStatement();
            String sql = "UPDATE Request SET ADMINCONFIRM = '" + newConfirmStatus + "'" + " WHERE requestID = '" + request.getRequestID() + "'";
            stmt.executeUpdate(sql);
            stmt.close();
            System.out.println("Modification successful");
        }catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } finally {
            DataModelI.getInstance().closeConnection();
        }
    }

    void setIsComplete(Request request, boolean newCompleteStatus){
        Connection connection = DataModelI.getInstance().getNewConnection();
        request.setComplete(newCompleteStatus);
        try {
            Statement stmt = connection.createStatement();
            String sql = "UPDATE Request SET ISCOMPLETE = '" + newCompleteStatus + "'" + " WHERE requestID = '" + request.getRequestID() + "'";
            stmt.executeUpdate(sql);
            stmt.close();
            System.out.println("Modification successful");
        }catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } finally {
            DataModelI.getInstance().closeConnection();
        }
    }

    /*------------------------------------------------ Retrieve Request / Search by ID / Print all -------------------------------------------------------*/


	Request getRequestByID(String requestID){
        // Connection
        Connection connection = DataModelI.getInstance().getNewConnection();

        // Variables
        Request requestObject = null;
        String requestType;
        int priority;
        Boolean isComplete;
        Boolean adminConfirm;
        Timestamp startTime;
        Timestamp endTime;
        String nodeID;
        String messageID;
        String password;
        try {
            Statement stmt = connection.createStatement();
            String str = "SELECT * FROM Request WHERE requestID = '" + requestID + "'";
            ResultSet rset = stmt.executeQuery(str);

            if (rset.next()) {
                requestID = rset.getString("requestID");
                requestType = rset.getString("requestType");
                priority = rset.getInt("priority");
                isComplete =rset.getBoolean("isComplete");
                adminConfirm = rset.getBoolean("adminConfirm");
                startTime = rset.getTimestamp("startTime");
                endTime = rset.getTimestamp("endTime");
                nodeID = rset.getString("nodeID");
                messageID = rset.getString("messageID");
                password = rset.getString("password");

                ObservableList<InventoryItem> items = getItemList(password);
                requestObject = new Request(requestID, requestType, priority, isComplete, adminConfirm, startTime.toLocalDateTime(), endTime.toLocalDateTime(), nodeID, messageID, items);

                System.out.println("Request added to the list: " + requestID);
            }
            rset.close();
            stmt.close();
            System.out.println("Done adding Request");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DataModelI.getInstance().closeConnection();
        }
        return requestObject;
    }

    //------------------------------------------HElper---------------------------------------

    ObservableList<InventoryItem> getItemList ( String itemConcat){
        List<String> itemList = new ArrayList<String>(Arrays.asList(itemConcat.split("/")));
        List<InventoryItem> inventoryItemList = new ArrayList<>();
        for(String itemID : itemList) {
            inventoryItemList.add(DataModelI.getInstance().getInventoryByID(itemID));
        }
        return FXCollections.observableArrayList(inventoryItemList);
    }

    String getItemString(ObservableList<InventoryItem> items){
        String listOfItemID = items.get(0).getID();
        for(int i = 1; i<items.size(); i++){
            listOfItemID = listOfItemID + "/" + items.get(i).getID();
        }
        return listOfItemID;
    }
}
