package com.manlyminotaurs.databases;

import com.manlyminotaurs.messaging.Inventory;
import com.manlyminotaurs.messaging.Message;
import com.manlyminotaurs.messaging.Request;
import com.manlyminotaurs.users.User;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//
//  .oPYo. .oPYo. o     o   .oPYo.                o               8 8
//  8    8 8      8     8   8    8                8               8 8
//  8      `Yooo. 8     8   8      .oPYo. odYo.  o8P oPYo. .oPYo. 8 8 .oPYo. oPYo.
//  8          `8 `b   d'   8      8    8 8' `8   8  8  `' 8    8 8 8 8oooo8 8  `'
//  8    8      8  `b d'    8    8 8    8 8   8   8  8     8    8 8 8 8.     8
//  `YooP' `YooP'   `8'     `YooP' `YooP' 8   8   8  8     `YooP' 8 8 `Yooo' 8
//  :.....::.....::::..::::::.....::.....:..::..::..:..:::::.....:....:.....:..::::
//  :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
//  :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
//

public class CsvFileController {

    /*---------------------------------- Parse CSV File --------------------------------------------------*/
    /**
     * http://www.avajava.com/tutorials/lessons/how-do-i-read-a-string-from-a-file-line-by-line.html
     * https://www.mkyong.com/java/how-to-read-and-parse-csv-file-in-java/
     * @param csv_file_name the name of the csv file
     * @return arrayList of columns from the csv
     */
    public List<String[]> parseCsvFile(String csv_file_name) {
        System.out.println("Parsing csv file: "+csv_file_name);
        List<String[]> list_of_rows = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(csv_file_name);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                // use comma as separator
                String[] node_row = line.split(",");
                list_of_rows.add(node_row);
            }
            fileReader.close();
            System.out.println("csv file parsed");
/*
            InputStream inputStream = getClass().getResourceAsStream(csv_file_name);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                // use comma as separator
                String[] node_row = line.split(",");
                list_of_rows.add(node_row);
            }
            inputStream.close();*/
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return list_of_rows;
    } // parseCsvFile() ends


    /*---------------------------------- Update CSV Files --------------------------------------------------*/

    public void updateAllCSVFiles(){
        updateMessageCSVFile("./MessageTable.csv");
        updateRequestCSVFile("./RequestTable.csv");
        updateUserCSVFile("./UserAccountTable.csv");
        updateInventoryCSVFile("./InventoryTable.csv");
    }
    /*---------------------------------- Messages --------------------------------------------------*/
    /**
     * Write formatted String to CSVFile using PrintWriter class
     * @param csvFileName the csv file to be updated
     */
    private void updateMessageCSVFile(String csvFileName) {
        Iterator<Message> iterator = DataModelI.getInstance().retrieveMessages().iterator();
        System.out.println("Updating message csv file...");
        try {
            FileWriter fileWriter = new FileWriter(csvFileName);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.print("messageID,message,isRead,sentTime,senderID,receiverID\n");
            while (iterator.hasNext()) {
                Message a_message = iterator.next();
                printWriter.printf("%s,%s,%b,%s,%s,%s\n", a_message.getMessageID(), a_message.getMessage(), a_message.getRead(), a_message.getSentDate().toString(), a_message.getSenderID(), a_message.getReceiverID());
            }
            printWriter.close();
            System.out.println("csv file updated");
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }//updateMessageCSVFile ends


    /*---------------------------------- Requests --------------------------------------------------*/
    /**
     * Write formatted String to CSVFile using PrintWriter class
     * @param csvFileName the csv file to be updated
     */
    private void updateRequestCSVFile(String csvFileName) {
        Iterator<Request> iterator = DataModelI.getInstance().retrieveRequests().iterator();
        System.out.println("Updating request csv file...");
        try {
            FileWriter fileWriter = new FileWriter(csvFileName);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.print("requestID,requestType,priority,isComplete,adminConfirm,startTime,endTime,nodeID,messageID,password\n");
            while (iterator.hasNext()) {
                Request a_request = iterator.next();
                printWriter.printf("%s,%s,%d,%b,%b,%s,%s,%s,%s,%s\n", a_request.getRequestID(),a_request.getRequestType(),a_request.getPriority(),a_request.getComplete(),a_request.getAdminConfirm(), a_request.getStartTime().toString().replace("T"," ").replace(".",":"), a_request.getEndTime().toString().replace("T"," ").replace(".",":"),a_request.getNodeID(),a_request.getMessageID(),a_request.getPassword());
            }
            printWriter.close();
            System.out.println("csv file updated");
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }//updateRequestCSVFile ends


    /*---------------------------------- Users --------------------------------------------------*/
    /**
     * Write formatted String to CSVFile using PrintWriter class
     * @param csvFileName the csv file to be updated
     */
    private void updateUserCSVFile(String csvFileName) {
        Iterator<User> iterator = DataModelI.getInstance().retrieveUsers().iterator();
        System.out.println("Updating user csv file...");
        try {
            FileWriter fileWriter = new FileWriter(csvFileName);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.print("userID,firstName,middleName,lastName,language, userType\n");
            while (iterator.hasNext()) {
                User a_user = iterator.next();
                printWriter.printf("%s,%s,%s,%s,%s,%s\n", a_user.getUserID(), a_user.getFirstName(),a_user.getMiddleName(),a_user.getLastName(),DataModelI.getInstance().getLanguageString(a_user.getLanguages()),a_user.getUserType());
            }
            printWriter.close();
            System.out.println("csv file updated");
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }//updateUserCSVFile ends

    /*---------------------------------- Inventory --------------------------------------------------*/
    private void updateInventoryCSVFile(String csvFileName) {
        Iterator<Inventory> iterator = DataModelI.getInstance().retrieveInventory().iterator();
        System.out.println("Updating request csv file...");
        try {
            FileWriter fileWriter = new FileWriter(csvFileName);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.print("ID,type,quantity, ocation\n");
            while (iterator.hasNext()) {
                Inventory a_inventory = iterator.next();
                printWriter.printf("%s,%s,%d,%s\n", a_inventory.getID(), a_inventory.getType(), a_inventory.getQuantity(),a_inventory.getLocation());
            }
            printWriter.close();
            System.out.println("csv file updated");
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }//updateRequestCSVFile ends
}
