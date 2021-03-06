package com.manlyminotaursAPI.databases;

import com.manlyminotaursAPI.messaging.InventoryItem;
import com.manlyminotaursAPI.messaging.Request;
import com.manlyminotaursAPI.messaging.RequestInfo;
import com.manlyminotaursAPI.users.Employee;
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

public interface IDataModelAPI {

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
    String addRequest(String requestID, boolean isComplete, RequestInfo aInfo);
    boolean removeRequest(Request oldRequest);
    boolean modifyRequest(Request newRequest);
    public List<Request> retrieveRequests();
    String getNextRequestID();
    Request getRequestByID(String ID);

    /*------------------------------------------ Users -------------------------------------------------------------*/
    /*-------------------------------- Add / Modify / Remove Employee --------------------------------------------------*/
    Employee addUser(String employeeID, String firstName, String middleName, String lastName, String userType);
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
    void updateAllDatabase(List<InventoryItem> inventoryList, List<RequestInfo> openList, List<RequestInfo> closedList, List<Employee> employeeList, List<InventoryItem> openDetailsList,  List<InventoryItem> closedDetailsList);

    ObservableList<InventoryItem> getItemList (String itemConcat);
    String getItemString(ObservableList<InventoryItem> items);

}
