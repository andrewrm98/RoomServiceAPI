package com.manlyminotaursAPI.databases;


import com.manlyminotaursAPI.messaging.InventoryItem;
import com.manlyminotaursAPI.messaging.RequestInfo;
import com.manlyminotaursAPI.viewControllers.roomServiceAPIController;

import java.io.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class TableInitializer {
    /*------------------------------------------------ Initialize Tables -----------------------------------------------------*/
    /**
     * Delete any pre-existing tables and create new tables in the database
     */
    public void initTables(){
        TableInitializer tableInit = new TableInitializer();
        // Get the database connection
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = DriverManager.getConnection("jdbc:derby:requestDB;create=true");
            stmt = connection.createStatement();
            tableInit.executeDBScripts("/DropTablesAPI.sql", stmt);
            tableInit.executeDBScripts("/CreateTablesAPI.sql", stmt);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { stmt.close();connection.close(); } catch (Exception e) { /* ignored */ }

        }
    }

    void setupDatabase(){
        System.out.println("Registering Oracle Driver");
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }
        System.out.println("Oracle JDBC Driver Registered Successfully !");


        TableInitializer initializer = new TableInitializer();

        initializer.initTables();

        UserDBUtil.setUserIDCounter(initializer.populateUserAccountTable("./roomServiceCsv/UserAccountTableAPI.csv"));
        initializer.populateRequestTable("./roomServiceCsv/RequestTableAPI.csv");
        initializer.populateInventoryTable("./roomServiceCsv/InventoryTableAPI.csv");

        System.out.println("-----------------------------");
        System.out.println("-----------------------------");
        System.out.println("Finished Setting up Database");
        System.out.println("-----------------------------");
        System.out.println("-----------------------------");

    //    InventoryItem inventoryItem = new InventoryItem("","junbong",13,"2");
    //    DataModelIAPI.getInstance().addInventory(inventoryItem);

    //    RequestInfo aInfo = new RequestInfo("", "helloRoom","5",null);
   //     DataModelIAPI.getInstance().addRequest("",false,aInfo);
        //initializer.populateExitTable("./NodeExitTable.csv");
        //initializer.populateHallwayTable("./NodeHallwayTable.csv");
        ;
        //initializer.populateTransportTable(null);
    }


    /*------------------------------------------------ Populate Tables -----------------------------------------------------*/


    private int populateUserAccountTable(String CsvFileName) {
        Connection connection = DataModelIAPI.getInstance().getNewConnection();
        int userIDCounter = 0;
        try {
            // parse UserTable.csv file
            CsvFileController csvFileControl = new CsvFileController();
            List<String[]> userAccountList = csvFileControl.parseCsvFile(CsvFileName);
            if(userAccountList == null){
                return 0;
            }

            Statement stmt = connection.createStatement();

            Iterator<String[]> iterator = userAccountList.iterator();
            iterator.next(); // get rid of the header

            //insert rows
            while (iterator.hasNext()) {
                userIDCounter++;
                String[] node_row = iterator.next();
                String str = "INSERT INTO UserAccount(userID,firstName,middleName,lastName, userType) VALUES (?,?,?,?,?)";
                PreparedStatement statement = connection.prepareStatement(str);
                statement.setString(1, node_row[0]);
                statement.setString(2, node_row[1]);
                statement.setString(3, node_row[2]);
                statement.setString(4, node_row[3]);
                statement.setString(5, node_row[4]);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DataModelIAPI.getInstance().closeConnection();
        }
        return userIDCounter;
    }

    public int populateRequestTable(String CsvFileName) {
        Connection connection = DataModelIAPI.getInstance().getNewConnection();
        int requestIDCounter = 0;
        try {
            // parse UserTable.csv file
            CsvFileController csvFileControl = new CsvFileController();
            List<String[]> requestList = csvFileControl.parseCsvFile(CsvFileName);
            if(requestList == null){
                return 0;
            }

            Statement stmt = connection.createStatement();

            Iterator<String[]> iterator = requestList.iterator();
            iterator.next(); // get rid of the header

            //insert rows
            String[] node_row = null;
            while (iterator.hasNext()) {
                node_row = iterator.next();
                String str = "INSERT INTO Request(requestID,requestType,priority,isComplete,adminConfirm,startTime,endTime,nodeID,messageID,PASSWORD) VALUES (?,?,?,?,?,?,?,?,?,?)";
                PreparedStatement statement = connection.prepareStatement(str);
                statement.setString(1, node_row[0]);
                statement.setString(2, node_row[1]);
                statement.setInt(3, Integer.parseInt(node_row[2]));
                statement.setBoolean(4, Boolean.valueOf(node_row[3]));
                statement.setBoolean(5, Boolean.valueOf(node_row[4]));
                statement.setTimestamp(6, convertStringToTimestamp(node_row[5]));
                statement.setTimestamp(7, convertStringToTimestamp(node_row[6]));
                statement.setString(8, node_row[7]);
                statement.setString(9, node_row[8]);
                statement.setString(10, node_row[9]);
                statement.executeUpdate();
            }
            if(node_row != null){
                requestIDCounter = Integer.parseInt(node_row[0]) + 5;
                RequestsDBUtil.setRequestIDCounter(requestIDCounter);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DataModelIAPI.getInstance().closeConnection();
        }
        System.out.println("request ID Counter: " + requestIDCounter);
        return requestIDCounter;
    }

    private int populateInventoryTable(String CsvFileName) {
        Connection connection = DataModelIAPI.getInstance().getNewConnection();
        int inventoryIDCounter = 0;
        try {
            // parse UserTable.csv file
            CsvFileController csvFileControl = new CsvFileController();
            List<String[]> requestList = csvFileControl.parseCsvFile(CsvFileName);
            if(requestList == null){
                return 0;
            }

            Statement stmt = connection.createStatement();
            Iterator<String[]> iterator = requestList.iterator();
            iterator.next(); // get rid of the header

            //insert rows
            String[] node_row = null;
            while (iterator.hasNext()) {
                node_row = iterator.next();
                String str = "INSERT INTO INVENTORY(ID, TYPE, QUANTITY, REQUESTINVENTORYID) VALUES (?,?,?,?)";
                PreparedStatement statement = connection.prepareStatement(str);
                statement.setString(1, node_row[0]);
                statement.setString(2, node_row[1]);
                statement.setInt(3, Integer.parseInt(node_row[2]));
                statement.setString(4, node_row[3]);
                statement.executeUpdate();
            }
            if(node_row != null){
                inventoryIDCounter = (Integer.parseInt(node_row[0]) + 5);
                InventoryDBUtil.setInventoryIDCounter(inventoryIDCounter);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DataModelIAPI.getInstance().closeConnection();
        }
        System.out.println("inventoryID Counter: " + inventoryIDCounter);
        return inventoryIDCounter;
    }


    /*------------------------------------------------ Execute Database Scripts -----------------------------------------------------*/
    /**
     *
     * @param aSQLScriptFilePath path to the sql file to run
     * @param stmt statement object passed from callee
     * @return true if sql file is executed successfully.
     * @throws IOException
     * @throws SQLException
     */
    private boolean executeDBScripts(String aSQLScriptFilePath, Statement stmt) throws IOException,SQLException {
        boolean isScriptExecuted = false;
        InputStream inputStream = null;
        try {
            System.out.println("executeDBScripts: "+ getClass().getName());
            inputStream = getClass().getResourceAsStream(aSQLScriptFilePath);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String str;
            StringBuffer sb;
            sb = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                if (str.contains(";")) {
                    sb.append(str.replace(";",""));
                    try {
                        stmt.executeUpdate(sb.toString());
                    }
                    catch(SQLException e){
                        e.printStackTrace();
                    }
                    sb.delete(0,sb.length());
                }
                else {
                    sb.append(str + "\n ");
                }
            }

            bufferedReader.close();
            isScriptExecuted = true;
        } catch (Exception e) {
            System.err.println("Failed to Execute " + aSQLScriptFilePath +". The error is "+ e.getMessage());
            if(inputStream == null){
                System.err.println("in is null");
            }
        }
        return isScriptExecuted;
    }

    public Timestamp convertStringToTimestamp(String timeString) {/*
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-mm-dd hh:mm:ss");
       // formatter = formatter.withLocale( putAppropriateLocaleHere );  // Locale specifies human language for translating, and cultural norms for lowercase/uppercase and abbreviations and such. Example: Locale.US or Locale.CANADA_FRENCH
        LocalDateTime date = LocalDateTime.parse(timeString, formatter);
        return Timestamp.valueOf(date);*/

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:S");
        Date parsedTimeStamp = null;
        try {
            parsedTimeStamp = dateFormat.parse(timeString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Timestamp(parsedTimeStamp.getTime());
    }

    public java.sql.Date convertStringToDate(String timeString) {/*
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-mm-dd hh:mm:ss");
       // formatter = formatter.withLocale( putAppropriateLocaleHere );  // Locale specifies human language for translating, and cultural norms for lowercase/uppercase and abbreviations and such. Example: Locale.US or Locale.CANADA_FRENCH
        LocalDateTime date = LocalDateTime.parse(timeString, formatter);
        return Timestamp.valueOf(date);*/

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf1.parse(timeString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        java.sql.Date sqlStartDate = new java.sql.Date(date.getTime());
        return sqlStartDate;
    }
}
