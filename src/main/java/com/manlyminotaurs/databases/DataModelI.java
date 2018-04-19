package com.manlyminotaurs.databases;

import com.manlyminotaurs.messaging.InventoryItem;
import com.manlyminotaurs.messaging.Request;
import com.manlyminotaurs.messaging.RequestInfo;
import com.manlyminotaurs.users.Employee;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

//
//  '||''|.             .              '||    ||'              '||          '||
//   ||   ||   ....   .||.   ....       |||  |||    ...      .. ||    ....   ||
//   ||    || '' .||   ||   '' .||      |'|..'||  .|  '|.  .'  '||  .|...||  ||
//   ||    || .|' ||   ||   .|' ||      | '|' ||  ||   ||  |.   ||  ||       ||
//  .||...|'  '|..'|'  '|.' '|..'|'    .|. | .||.  '|..|'  '|..'||.  '|...' .||.
//
//

public class DataModelI implements IDataModel{

	/*---------------------------------------------- Variables -------------------------------------------------------*/

	// all the utils
	private RequestsDBUtil requestsDBUtil;
	private UserDBUtil userDBUtil;
	private InventoryDBUtil inventoryDBUtil;
	private TableInitializer tableInitializer;

	// list of all objects

	private static DataModelI dataModelI = null;
	private Connection connection = null;

	/*------------------------------------------------ Methods -------------------------------------------------------*/

	public static void main(String[] args){
		DataModelI.getInstance().startDB();
		DataModelI.getInstance().updateAllCSVFiles();

		TableInitializer tableInitializer = new TableInitializer();
		//    System.out.println(tableInitializer.convertStringToDate("2018-04-06"));
		//   System.out.println(tableInitializer.convertStringToTimestamp("2018-04-06 07:43:10:2").toLocalDateTime().toString().replace("T"," "));
	}

	private DataModelI() {
		requestsDBUtil = new RequestsDBUtil();
		userDBUtil = new UserDBUtil();
		tableInitializer = new TableInitializer();
		inventoryDBUtil = new InventoryDBUtil();
	}

	public static DataModelI getInstance(){
		if(dataModelI == null) {
			dataModelI = new DataModelI();
		}
		return dataModelI;
	}

	@Override
	public void startDB() {
		tableInitializer.setupDatabase();
		// System.out.println(Timestamp.valueOf("0000-00-00 00:00:00").toLocalDateTime());
		//System.out.println(tableInitializer.convertStringToDate("12-04-2017"));
	}

	@Override
	public Connection getNewConnection() {
		try {
			if(DataModelI.getInstance().connection == null || DataModelI.getInstance().connection.isClosed()) {
				DataModelI.getInstance().connection = DriverManager.getConnection("jdbc:derby:requestDB;create=true");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return DataModelI.getInstance().connection;
	}

	@Override
	public boolean closeConnection() {
		try {
			if(DataModelI.getInstance().connection != null) {
				DataModelI.getInstance().connection.close();
				DataModelI.getInstance().connection = null;
				return true;
			}
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} catch (NullPointerException en){
			en.printStackTrace();
			return false;
		}
	}

	/*------------------------------------------------ Requests -------------------------------------------------------*/
	@Override
	public void addRequest(boolean openRequest,RequestInfo aInfo) {
		requestsDBUtil.addRequest(openRequest, aInfo.getRoom(),aInfo.getEmployee(),aInfo.getItems());
	}

	@Override
	public boolean removeRequest(Request oldRequest) {
		boolean tempBool = requestsDBUtil.removeRequest(oldRequest);
		return tempBool;
	}

	@Override
	public boolean modifyRequest(Request newRequest) {
		boolean tempBool = requestsDBUtil.modifyRequest(newRequest);
		return tempBool;
	}

	public List<Request> retrieveRequests(){
		return requestsDBUtil.retrieveRequests();
	}

	@Override
	public String getNextRequestID() {
		return requestsDBUtil.generateRequestID();
	}

	@Override
	public Request getRequestByID(String requestID) {
		return requestsDBUtil.getRequestByID(requestID);
	}

	/*------------------------------------------------ Users -------------------------------------------------------*/

	@Override
	public Employee addUser(String employeeID, String firstName, String middleName, String lastName, String userType) {
		Employee newUser = userDBUtil.addUser(employeeID, firstName, middleName, lastName, userType);
		return newUser;
	}

	@Override
	public boolean removeUser(Employee oldUser) {
		boolean tempBool = userDBUtil.removeUser(oldUser);
		return tempBool;
	}

	@Override
	public boolean removeUserByID(String userID) {
		return userDBUtil.removeUserByID(userID);
	}

	@Override
	public boolean modifyUser(Employee newUser) {
		boolean tempBool = userDBUtil.modifyUser(newUser);
		return tempBool;
	}

	@Override
	public List<Employee> retrieveUsers() {
		return userDBUtil.retrieveUsers();
	}

	@Override
	public Employee getUserByID(String userID) {
		return userDBUtil.getUserByID(userID);
	}

	@Override
	public String getLanguageString(List<String> languages) {
		return userDBUtil.getLanguageString(languages);
	}

	@Override
	public List<String> getLanguageList(String languagesConcat) {
		return userDBUtil.getLanguageList(languagesConcat);
	}

	//--------------------------------------CSV stuffs------------------------------------------

    @Override
    public void updateAllDatabase(List<InventoryItem> inventoryList, List<RequestInfo> openList, List<RequestInfo> closedList,List<Employee> employeeList){
        for(InventoryItem aItem: inventoryList){
            DataModelI.getInstance().addInventory(aItem);
        }

        for(RequestInfo aRequest: openList){
            DataModelI.getInstance().addRequest(true,aRequest);
        }

        for(RequestInfo aRequest: closedList){
            DataModelI.getInstance().addRequest(false,aRequest);
        }

        for(Employee employee: employeeList){
            DataModelI.getInstance().addUser(employee.getEmployeeID(),employee.getFirstName(),employee.getMiddleName(),employee.getLastName(),employee.getEmployeeType());
        }
    }

	@Override
	public void updateAllCSVFiles() {
		new CsvFileController().updateAllCSVFiles();
	}


	@Override
	public ObservableList<InventoryItem> getItemList(String itemConcat){
		return requestsDBUtil.getItemList(itemConcat);
	}

	@Override
	public String getItemString(ObservableList<InventoryItem> items) {
		return requestsDBUtil.getItemString(items);
	}

	/*------------------------------------------------ InventoryItem -------------------------------------------------------*/

	@Override
	public boolean modifyInventory(InventoryItem inventory) {
		return inventoryDBUtil.modifyInventory(inventory);
	}

	@Override
	public boolean removeInventory(InventoryItem inventory) {
		return inventoryDBUtil.removeInventory(inventory);
	}

	@Override
	public InventoryItem addInventory(InventoryItem inventory) {
		return inventoryDBUtil.addInventory(inventory);
	}

	@Override
	public List<InventoryItem> retrieveInventory() {
		return inventoryDBUtil.retrieveInventory();
	}

	@Override
	public InventoryItem getInventoryByID(String ID) {
		return inventoryDBUtil.getInventoryByID(ID);
	}
}