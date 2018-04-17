package com.manlyminotaurs.databases;

import com.manlyminotaurs.messaging.Message;
import com.manlyminotaurs.messaging.Request;
import com.manlyminotaurs.users.User;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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


    /*----------------------------------------- Messages -------------------------------------------------------------*/
    /*------------------------------ Add / Modify / Remove Message ---------------------------------------------------*/
    Message addMessage(Message messageObject);
    boolean removeMessage(Message oldMessage);
    boolean modifyMessage(Message newMessage);
    String getNextMessageID();
    /*--------------------- Retrieve List of Messages / All or by Attribute ------------------------------------------*/
    List<Message> retrieveMessages();
    List<Message> getMessageBySender(String senderID);
    List<Message> getMessageByReceiver(String receiverID);
    Message getMessageByID(String ID);

    /*----------------------------------------- Requests ------------------------------------------------------------*/
    /*------------------------------ Add / Modify / Remove Request --------------------------------------------------*/
    Request addRequest(Request requestObject, Message messageObject);
    boolean removeRequest(Request oldRequest);
    boolean modifyRequest(Request newRequest);
    String getNextRequestID();
    /*-------------------------- Retrieve List of Requests / All or by Attribute ------------------------------------*/
    List<Request> retrieveRequests();
    List<Request> getRequestBySender(String senderID);
    List<Request> getRequestByReceiver(String receiverID);
    Request getRequestByID(String ID);


    /*------------------------------------------ Users -------------------------------------------------------------*/
    /*-------------------------------- Add / Modify / Remove User --------------------------------------------------*/
    User addUser(String firstName, String middleName, String lastName, List<String> languages, String userType, String userName, String password);
    boolean removeUser(User oldUser);
    boolean modifyUser(User newUser);
    /*------------------------ Retrieve List of Users / All or by Attribute ----------------------------------------*/
    List<User> retrieveUsers();
    User getUserByID(String ID);
    String getLanguageString(List<String> languages);
    List<String> getLanguageList ( String languagesConcat);


    //---------------------------------------UPDATE CSV FIles--------------------------------
    void updateAllCSVFiles();

}
