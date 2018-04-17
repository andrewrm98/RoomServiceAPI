package com.manlyminotaurs.databases;


import com.manlyminotaurs.messaging.Inventory;

import java.io.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

class TableInitializer {
    /*------------------------------------------------ Initialize Tables -----------------------------------------------------*/
    /**
     * Delete any pre-existing tables and create new tables in the database
     */
    private void initTables(){
        TableInitializer tableInit = new TableInitializer();
        // Get the database connection
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = DriverManager.getConnection("jdbc:derby:requestDB;create=true");
            stmt = connection.createStatement();
            tableInit.executeDBScripts("/DropTables.sql", stmt);
            tableInit.executeDBScripts("/CreateTables.sql", stmt);
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

        UserDBUtil.setUserIDCounter(initializer.populateUserAccountTable("./UserAccountTable.csv"));
        MessagesDBUtil.setMessageIDCounter(initializer.populateMessageTable("./MessageTable.csv"));
        RequestsDBUtil.setRequestIDCounter(initializer.populateRequestTable("./RequestTable.csv"));
        InventoryDBUtil.setInventoryIDCounter(initializer.populateInventoryTable("./InventoryTable.csv"));

        System.out.println("-----------------------------");
        System.out.println("-----------------------------");
        System.out.println("Finished Setting up Database");
        System.out.println("-----------------------------");
        System.out.println("-----------------------------");
        //initializer.populateExitTable("./NodeExitTable.csv");
        //initializer.populateHallwayTable("./NodeHallwayTable.csv");
        ;
        //initializer.populateTransportTable(null);
    }


    /*------------------------------------------------ Populate Tables -----------------------------------------------------*/


    private int populateUserAccountTable(String CsvFileName) {
        Connection connection = DataModelI.getInstance().getNewConnection();
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
                String str = "INSERT INTO UserAccount(userID,firstName,middleName,lastName,language, userType) VALUES (?,?,?,?,?,?)";
                PreparedStatement statement = connection.prepareStatement(str);
                statement.setString(1, node_row[0]);
                statement.setString(2, node_row[1]);
                statement.setString(3, node_row[2]);
                statement.setString(4, node_row[3]);
                statement.setString(5, node_row[4]);
                statement.setString(6, node_row[5]);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DataModelI.getInstance().closeConnection();
        }
        return userIDCounter;
    }

    private void populateUserPasswordTable(String CsvFileName) {
        Connection connection = DataModelI.getInstance().getNewConnection();
        try {
            // parse UserTable.csv file
            CsvFileController csvFileControl = new CsvFileController();
            List<String[]> userAccountList = csvFileControl.parseCsvFile(CsvFileName);

            Statement stmt = connection.createStatement();

            Iterator<String[]> iterator = userAccountList.iterator();
            iterator.next(); // get rid of the header

            //insert rows
            while (iterator.hasNext()) {
                String[] node_row = iterator.next();
                String str = "INSERT INTO UserPassword(userName, password, userID) VALUES (?,?,?)";
                PreparedStatement statement = connection.prepareStatement(str);
                statement.setString(1, node_row[0]);
                statement.setString(2, node_row[1]);
                statement.setString(3, node_row[2]);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DataModelI.getInstance().closeConnection();
        }
    }

    private int populateMessageTable(String CsvFileName) {
        int messageIDCounter = 0;
        Connection connection = DataModelI.getInstance().getNewConnection();
        try {
            // parse MessageTable.csv file
            CsvFileController csvFileControl = new CsvFileController();
            List<String[]> messageList = csvFileControl.parseCsvFile(CsvFileName);
            if(messageList == null){
                return 0;
            }

            Statement stmt = connection.createStatement();

            Iterator<String[]> iterator = messageList.iterator();
            iterator.next(); // get rid of the header

            //insert rows
            while (iterator.hasNext()) {
                messageIDCounter++;
                String[] node_row = iterator.next();
                String str = "INSERT INTO message(messageID,message,isRead,sentDate,senderID,receiverID) VALUES (?,?,?,?,?,?)";
                PreparedStatement statement = connection.prepareStatement(str);
                statement.setString(1, node_row[0]);
                statement.setString(2, node_row[1]);
                statement.setBoolean(3, Boolean.valueOf(node_row[2]));
                statement.setDate(4,convertStringToDate(node_row[3]));
                statement.setString(5, node_row[4]);
                statement.setString(6, node_row[5]);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DataModelI.getInstance().closeConnection();
        }
        return messageIDCounter;
    }

    private int populateRequestTable(String CsvFileName) {
        Connection connection = DataModelI.getInstance().getNewConnection();
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
            while (iterator.hasNext()) {
                requestIDCounter++;
                String[] node_row = iterator.next();
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
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DataModelI.getInstance().closeConnection();
        }
        return requestIDCounter;
    }

    private int populateInventoryTable(String CsvFileName) {
        Connection connection = DataModelI.getInstance().getNewConnection();
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
            while (iterator.hasNext()) {
                inventoryIDCounter++;
                String[] node_row = iterator.next();
                String str = "INSERT INTO INVENTORY(ID, TYPE, QUANTITY, LOCATION) VALUES (?,?,?,?)";
                PreparedStatement statement = connection.prepareStatement(str);
                statement.setString(1, node_row[0]);
                statement.setString(2, node_row[1]);
                statement.setInt(3, Integer.parseInt(node_row[2]));
                statement.setBoolean(4, Boolean.valueOf(node_row[3]));
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DataModelI.getInstance().closeConnection();
        }
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
