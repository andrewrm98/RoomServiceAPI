package com.manlyminotaurs.databases;

import com.manlyminotaurs.messaging.Message;
import com.manlyminotaurs.messaging.Request;
import com.manlyminotaurs.users.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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
	private MessagesDBUtil messagesDBUtil;
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
		messagesDBUtil = new MessagesDBUtil();
		requestsDBUtil = new RequestsDBUtil();
		userDBUtil = new UserDBUtil();
		tableInitializer = new TableInitializer();
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


	/*------------------------------------------------ Messages -------------------------------------------------------*/
	@Override
	public Message addMessage(Message messageObject) {
		Message tempMessage = messagesDBUtil.addMessage(messageObject);
		return tempMessage;
	}

	@Override
	public boolean removeMessage(Message oldMessage) {
		boolean tempBool = messagesDBUtil.removeMessage(oldMessage);
		return tempBool;
	}

	@Override
	public boolean modifyMessage(Message newMessage) {
		boolean tempBool = messagesDBUtil.modifyMessage(newMessage);
		return tempBool;
	}

	@Override
	public String getNextMessageID() {
		return messagesDBUtil.generateMessageID();
	}

	@Override
	public List<Message> retrieveMessages() {
		return messagesDBUtil.retrieveMessages();
	}

	@Override
	public List<Message> getMessageBySender(String senderID) {
		return messagesDBUtil.searchMessageBySender(senderID);
	}

	@Override
	public List<Message> getMessageByReceiver(String receiverID) {
		return messagesDBUtil.searchMessageByReceiver(receiverID);
	}

	@Override
	public Message getMessageByID(String ID) {
		return messagesDBUtil.getMessageByID(ID);
	}


	/*------------------------------------------------ Requests -------------------------------------------------------*/
	@Override
	public Request addRequest(Request requestObject, Message messageObject) {
		Request newRequest = requestsDBUtil.addRequest(requestObject, messageObject);
		return newRequest;
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

	@Override
	public String getNextRequestID() {
		return requestsDBUtil.generateRequestID();
	}

	@Override
	public List<Request> retrieveRequests() {
		return requestsDBUtil.retrieveRequests();
	}

	@Override
	public List<Request> getRequestBySender(String senderID) {
		return requestsDBUtil.searchRequestsBySender(senderID);
	}

	@Override
	public List<Request> getRequestByReceiver(String receiverID) {
		return requestsDBUtil.searchRequestsByReceiver(receiverID);
	}

	@Override
	public Request getRequestByID(String requestID) {
		return requestsDBUtil.getRequestByID(requestID);
	}

	/*------------------------------------------------ Users -------------------------------------------------------*/

	@Override
	public User addUser(String firstName, String middleName, String lastName, List<String> languages, String userType, String userName, String password) {
		User newUser = userDBUtil.addUser(firstName, middleName, lastName, languages, userType, userName, password);
		return newUser;
	}

	@Override
	public boolean removeUser(User oldUser) {
		boolean tempBool = userDBUtil.removeUser(oldUser);
		return tempBool;
	}

	@Override
	public boolean modifyUser(User newUser) {
		boolean tempBool = userDBUtil.modifyUser(newUser);
		return tempBool;
	}

	@Override
	public List<User> retrieveUsers() {
		return userDBUtil.retrieveUsers();
	}

	@Override
	public User getUserByID(String userID) {
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
	public void updateAllCSVFiles() {
		new CsvFileController().updateAllCSVFiles();
	}

}