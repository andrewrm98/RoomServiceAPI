package com.manlyminotaurs.databases;

import com.manlyminotaurs.messaging.InventoryItem;
import com.manlyminotaurs.messaging.Request;
import com.manlyminotaurs.users.Employee;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.util.List;

//
//    ____  ____    ___       _             __
//   |  _ \| __ )  |_ _|_ __ | |_ ___ _ __ / _| __ _  ___ ___
//   | | | |  _ \   | || '_ \| __/ _ \ '__| |_ / _` |/ __/ _ \
//   | |_| | |_) |  | || | | | ||  __/ |  |  _| (_| | (_|  __/
//   |____/|____/  |___|_| |_|\__\___|_|  |_|  \__,_|\___\___|
//
//
//

public interface IDataModel {

    void startDB();

    Connection getNewConnection();
    boolean closeConnection();
    /*----------------------------------------- InventoryItem -------------------------------------------------------------*/
    /*------------------------------ Add / Modify / Remove InventoryItem ---------------------------------------------------*/
    boolean modifyInventory(InventoryItem inventory);
    boolean removeInventory(InventoryItem inventory);
    InventoryItem addInventory(InventoryItem inventory);
    /*------------------------------ Retrieve/get ---------------------------------------------------*/
    List<InventoryItem> retrieveInventory();
    InventoryItem getInventoryByID(String ID);

    /*----------------------------------------- Requests ------------------------------------------------------------*/
    void addRequest(Request requestObject);
    boolean removeRequest(Request oldRequest);
    boolean modifyRequest(Request newRequest);
    public List<Request> retrieveRequests();
    String getNextRequestID();
    Request getRequestByID(String ID);

    /*------------------------------------------ Users -------------------------------------------------------------*/
    /*-------------------------------- Add / Modify / Remove Employee --------------------------------------------------*/
    Employee addUser(String firstName, String middleName, String lastName, List<String> languages, String userType, String userName, String password);
    boolean removeUser(Employee oldUser);
	boolean removeUserByID(String userID);
    boolean modifyUser(Employee newUser);
    /*------------------------ Retrieve List of Users / All or by Attribute ----------------------------------------*/
    List<Employee> retrieveUsers();
    Employee getUserByID(String ID);
    String getLanguageString(List<String> languages);
    List<String> getLanguageList ( String languagesConcat);


    //---------------------------------------UPDATE CSV FIles--------------------------------
    void updateAllCSVFiles();

    ObservableList<InventoryItem> getItemList (String itemConcat);
    String getItemString(ObservableList<InventoryItem> items);

}
