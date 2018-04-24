package com.manlyminotaursAPI.databases;

import com.manlyminotaursAPI.messaging.InventoryItem;
import com.manlyminotaursAPI.messaging.Request;
import com.manlyminotaursAPI.users.Employee;

import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss:S");
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
                String[] node_row = line.split(",",-1);
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
        File file = new File("./roomServiceCsv");
        if (!file.exists()) {
            if (file.mkdir()) {
                System.out.println("Directory is created!");
            } else {
                System.out.println("Failed to create directory!");
            }
        }

        updateRequestCSVFile("./roomServiceCsv/RequestTableAPI.csv");
        updateUserCSVFile("./roomServiceCsv/UserAccountTableAPI.csv");
        updateInventoryCSVFile("./roomServiceCsv/InventoryTableAPI.csv");
    }


    /*---------------------------------- Requests --------------------------------------------------*/
    /**
     * Write formatted String to CSVFile using PrintWriter class
     * @param csvFileName the csv file to be updated
     */
    private void updateRequestCSVFile(String csvFileName) {
        Iterator<Request> iterator = DataModelIAPI.getInstance().retrieveRequests().iterator();
        System.out.println("Updating request csv file...");
        try {
            FileWriter fileWriter = new FileWriter(csvFileName);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.print("requestID,requestType,priority,isComplete,adminConfirm,startTime,endTime,nodeID,messageID,password\n");
            while (iterator.hasNext()) {
                Request a_request = iterator.next();
                printWriter.printf("%s,%s,%d,%b,%b,%s,%s,%s,%s,%s\n", a_request.getRequestID(),a_request.getRequestType(),a_request.getPriority(),a_request.getComplete(),a_request.getAdminConfirm(), a_request.getStartTime().format(dateTimeFormatter), a_request.getEndTime().format(dateTimeFormatter),a_request.getRequestInfo().getRoom(),a_request.getRequestInfo().getEmployee(),DataModelIAPI.getInstance().getItemString(a_request.getRequestInfo().getItems()));
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
        Iterator<Employee> iterator = DataModelIAPI.getInstance().retrieveUsers().iterator();
        System.out.println("Updating user csv file...");
        try {
            FileWriter fileWriter = new FileWriter(csvFileName);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.print("userID,firstName,middleName,lastName,userType\n");
            while (iterator.hasNext()) {
                Employee a_user = iterator.next();
                printWriter.printf("%s,%s,%s,%s,%s\n", a_user.getEmployeeID(), a_user.getFirstName(),a_user.getMiddleName(),a_user.getLastName(),a_user.getEmployeeType());
            }
            printWriter.close();
            System.out.println("csv file updated");
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }//updateUserCSVFile ends

    /*---------------------------------- InventoryItem --------------------------------------------------*/
    private void updateInventoryCSVFile(String csvFileName) {
        Iterator<InventoryItem> iterator = DataModelIAPI.getInstance().retrieveInventory().iterator();
        System.out.println("Updating request csv file...");
        try {
            FileWriter fileWriter = new FileWriter(csvFileName);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.print("ID,type,quantity,requestInventoryID\n");
            while (iterator.hasNext()) {
                InventoryItem a_inventory = iterator.next();
                printWriter.printf("%s,%s,%d,%s\n", a_inventory.getID(), a_inventory.getItemName(), a_inventory.getQuantity(),a_inventory.getRequestID());
            }
            printWriter.close();
            System.out.println("csv file updated");
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }//updateRequestCSVFile ends
}
