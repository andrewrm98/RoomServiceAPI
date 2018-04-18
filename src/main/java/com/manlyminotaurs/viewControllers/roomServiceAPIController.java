package com.manlyminotaurs.viewControllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.manlyminotaurs.databases.DataModelI;
import com.manlyminotaurs.databases.InventoryDBUtil;
import com.manlyminotaurs.databases.UserDBUtil;
import com.manlyminotaurs.messaging.Inventory;
import com.manlyminotaurs.messaging.Message;
import com.manlyminotaurs.messaging.Request;
import com.manlyminotaurs.users.User;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import javax.swing.*;
import java.net.URL;
import java.util.*;

//import static com.sun.tools.corba.se.idl.Util.capitalize;
import static com.sun.xml.internal.ws.util.StringUtils.capitalize;


public class roomServiceAPIController implements Initializable{

	// General Objects
	@FXML
	Label lblTitle;

	@FXML
	Label lblSubtitle;

	@FXML
	JFXButton btnRequestRoomService;

	@FXML
	JFXButton btnManageRequests;

	@FXML
	JFXButton btnManageInventory;

	@FXML
	JFXButton btnManageEmployees;

	@FXML
	HBox boxRequestRoomService;

	@FXML
	HBox boxManageRequests;

	@FXML
	HBox boxManageInventory;

	@FXML
	HBox boxManageEmployees;

	@FXML
	JFXButton btnExit;


	// Request Room Service Objects

	@FXML
	JFXTextField txtRoomNumberRequestRoomService;

	@FXML
	JFXComboBox<String> cmboItemRequestRoomService;

	@FXML
	JFXTextField txtQuantityRequestRoomService;

	@FXML
	JFXButton btnAddSelectionToCart;

	@FXML
	JFXButton btnDeleteItemRequestRoomService;

	@FXML
	JFXButton btnSubmitRoomServiceRequest;

	@FXML
	TableView<String> tblCart;

	@FXML
	TableView<InventoryItem> tblInventoryMenu;


	// Manage Requests

	@FXML
	TableView<String> tblOpenRequests;

	@FXML
	TableView<String> tblOpenRequestDetails;

	@FXML
	TableView<String> tblClosedRequests;

	@FXML
	TableView<String> tblClosedRequestDetails;

	@FXML
	JFXComboBox<String> cmboAssignEmployee;

	@FXML
	JFXButton btnAssign;

	@FXML
	JFXButton btnCompleteRequest;

	@FXML
	JFXButton btnDeleteRequest;


	// Manage Inventory

	@FXML
	JFXTextField txtItemInventory;

	@FXML
	JFXTextField txtQuantityInventory;

	@FXML
	JFXButton btnAddItem;

	@FXML
	JFXButton btnModifyItem;

	@FXML
	JFXButton btnDeleteItem;

	@FXML
	TableView<InventoryItem> tblInventory;


	// Manage Employees

	@FXML
	JFXTextField txtFirstName;

	@FXML
	JFXTextField txtMiddleName;

	@FXML
	JFXTextField txtLastName;

	@FXML
	JFXTextField txtEmployeeID;

	@FXML
	JFXComboBox<String> cmboEmployeeType;

	@FXML
	JFXButton btnAddUser;

	@FXML
	JFXButton btnModifyUser;

	@FXML
	JFXButton btnDeleteUser;

	@FXML
	TableView<Employee> tblEmployeeDatabase;

    UserDBUtil empDB = new UserDBUtil();
    InventoryDBUtil invDB = new InventoryDBUtil();
	ObservableList<String> employeeTypes = FXCollections.observableArrayList("Doctor", "Nurse", "Admin", "Janitor", "Interpreter", "Patient", "Security");
	final static ObservableList<String> currentItems = FXCollections.observableArrayList("Blanket", "Towel", "Pillow");
    ObservableList<Employee> employeeList =  FXCollections.observableArrayList();
    ObservableList<String> employeeNames = FXCollections.observableArrayList();
    ObservableList<InventoryItem> inventoryList = FXCollections.observableArrayList();
    //ObservableList<InventoryItem> inventoryMenuList = FXCollections.observableArrayList();
    ObservableList<String> emptyList = FXCollections.observableArrayList();
	String firstName;
	String middleName;
	String lastName;
	List<String> languages;
	String language;
	String type;
	String username;
	String password;
	String userID;
	User user;

    public class InventoryItem{

        String itemName;
        int quantity;

        InventoryItem(String itemName, int quantity){
            this.itemName = itemName;
            this.quantity = quantity;
        }

        public String getItemName() {
            return itemName;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int num) {
            this.quantity = num;
        }

    }

    public class Employee{

        String firstName;
        String middleName;
        String lastName;
        String employeeID;
        String employeeType;


        Employee(String firstName, String middleName, String lastName, String employeeID, String employeeType){
            this.firstName = firstName;
            this.middleName = middleName;
            this.lastName = lastName;
            this.employeeID = employeeID;
            this.employeeType = employeeType;
        }


        public String getFirstName() {
            return firstName;
        }

        public String getMiddleName() {
            return middleName;
        }

        public String getLastName() {
            return lastName;
        }

        public String getEmployeeID() {
            return employeeID;
        }

        public String getEmployeeType() {
            return employeeType;
        }
    }

    public class Request {
        String room;
        String time;
        String employee;
        String status;
        Cart cart;

        public Request(String room, String time, String employee, String status, Cart cart) {
            this.room = room;
            this.time = time;
            this.employee = employee;
            this.status = status;
            this.cart = cart;
        }

        public String getRoom() {
            return room;
        }

        public String getTime() {
            return time;
        }

        public String getEmployee() {
            return employee;
        }

        public String getStatus() {
            return status;
        }

        public Cart getCart() {
            return cart;
        }
    }

    public class Cart {

        String item;
        int quantity;


        public Cart(String item, int quantity) {
            this.item = item;
            this.quantity = quantity;
        }

        public String getItem() {
            return item;
        }

        public int getQuantity() {
            return quantity;
        }
    }

	public void initialize(URL location, ResourceBundle resources) {

		// Hide Screens
		boxManageRequests.setVisible(false);
		boxManageInventory.setVisible(false);
		boxManageEmployees.setVisible(false);

		// Show Screen
		boxRequestRoomService.setVisible(true);

		// Set button color
		btnRequestRoomService.setStyle("-fx-background-color: #edbf54");
		btnManageRequests.setStyle("-fx-background-color: #2b65ac");
		btnManageInventory.setStyle("-fx-background-color: #2b65ac");
		btnManageEmployees.setStyle("-fx-background-color: #2b65ac");

		// Change Subtitle
		lblSubtitle.setText("Request Room Service");

		// Clean fields
		cleanRequestRoomService();

		// Create Employee Database Table
		TableColumn fName = new TableColumn("First Name");
		TableColumn mName = new TableColumn("Middle Name");
		TableColumn lName = new TableColumn("Last Name");
		TableColumn empID = new TableColumn("Employee ID");
		TableColumn empType = new TableColumn("Employee Type");

		tblEmployeeDatabase.getColumns().addAll(fName, mName, lName, empID, empType);

		fName.setCellValueFactory(new PropertyValueFactory<Employee, String>("firstName"));
		mName.setCellValueFactory(new PropertyValueFactory<Employee, String>("middleName"));
		lName.setCellValueFactory(new PropertyValueFactory<Employee, String>("lastName"));
		empID.setCellValueFactory(new PropertyValueFactory<Employee, String>("employeeID"));
		empType.setCellValueFactory(new PropertyValueFactory<Employee, String>("employeeType"));

		tblEmployeeDatabase.setColumnResizePolicy(tblInventory.CONSTRAINED_RESIZE_POLICY);


		// Create Inventory Table
        TableColumn itemName = new TableColumn("Item");
        TableColumn quantity = new TableColumn("Quantity");

        tblInventory.getColumns().addAll(itemName, quantity);

        itemName.setCellValueFactory(new PropertyValueFactory<InventoryItem, String>("itemName"));
        quantity.setCellValueFactory(new PropertyValueFactory<InventoryItem, String>("quantity"));

		tblInventory.setColumnResizePolicy(tblInventory.CONSTRAINED_RESIZE_POLICY);

        // Create Inventory Menu Table
        TableColumn itemNameMenu = new TableColumn("Item");
        TableColumn quantityMenu = new TableColumn("Quantity");

        tblInventoryMenu.getColumns().addAll(itemNameMenu, quantityMenu);

        itemNameMenu.setCellValueFactory(new PropertyValueFactory<InventoryItem, String>("itemName"));
        quantityMenu.setCellValueFactory(new PropertyValueFactory<InventoryItem, String>("quantity"));

		tblInventoryMenu.setColumnResizePolicy(tblInventory.CONSTRAINED_RESIZE_POLICY);


		// Update ComboBoxes
        cmboEmployeeType.setItems(employeeTypes);
		cmboItemRequestRoomService.setItems(currentItems);


		// Update Tables
        updateTablesInventory();
        updateTablesRoomServiceRequest();
        updateTablesInventoryMenu();
        updateTablesEmployee();
	}

	//------------------------------------------------------------------------------------------------------------------
	//
	// General Functions
	//
	//------------------------------------------------------------------------------------------------------------------
	public void setScreenToRequestRoomService(ActionEvent event) {

		// Hide Screens
		boxManageRequests.setVisible(false);
		boxManageInventory.setVisible(false);
		boxManageEmployees.setVisible(false);

		// Show Screen
		boxRequestRoomService.setVisible(true);

		// Set button color
		btnRequestRoomService.setStyle("-fx-background-color: #edbf54");
		btnManageRequests.setStyle("-fx-background-color: #2b65ac");
		btnManageInventory.setStyle("-fx-background-color: #2b65ac");
		btnManageEmployees.setStyle("-fx-background-color: #2b65ac");

		// Change Subtitle
		lblSubtitle.setText("Request Room Service");

		// Clean fields
		cleanRequestRoomService();

		// Update ComboBoxes
		cmboItemRequestRoomService.setItems(currentItems);

	}

	public void setScreenToManageRequests(ActionEvent event) {

		// Hide Screens
		boxRequestRoomService.setVisible(false);
		boxManageInventory.setVisible(false);
		boxManageEmployees.setVisible(false);

		// Show Screen
		boxManageRequests.setVisible(true);

		// Set button color
		btnRequestRoomService.setStyle("-fx-background-color: #2b65ac");
		btnManageRequests.setStyle("-fx-background-color: #edbf54");
		btnManageInventory.setStyle("-fx-background-color: #2b65ac");
		btnManageEmployees.setStyle("-fx-background-color: #2b65ac");

		// Change Subtitle
		lblSubtitle.setText("Manage Requests");

		// Clean fields
		cleanManageRequests();

	}

	public void setScreenToManageInventory(ActionEvent event) {

		// Hide Screens
		boxRequestRoomService.setVisible(false);
		boxManageRequests.setVisible(false);
		boxManageEmployees.setVisible(false);

		// Show Screen
		boxManageInventory.setVisible(true);

		// Set button color
		btnRequestRoomService.setStyle("-fx-background-color: #2b65ac");
		btnManageRequests.setStyle("-fx-background-color: #2b65ac");
		btnManageInventory.setStyle("-fx-background-color: #edbf54");
		btnManageEmployees.setStyle("-fx-background-color: #2b65ac");

		// Change Subtitle
		lblSubtitle.setText("Manage Inventory");

		// Clean fields
		cleanManageInventory();

	}

	public void setScreenToManageEmployees(ActionEvent event) {

		// Hide Screens
		boxRequestRoomService.setVisible(false);
		boxManageRequests.setVisible(false);
		boxManageInventory.setVisible(false);

		// Show Screen
		boxManageEmployees.setVisible(true);

		// Set button color
		btnRequestRoomService.setStyle("-fx-background-color: #2b65ac");
		btnManageRequests.setStyle("-fx-background-color: #2b65ac");
		btnManageInventory.setStyle("-fx-background-color: #2b65ac");
		btnManageEmployees.setStyle("-fx-background-color: #edbf54");

		// Change Subtitle
		lblSubtitle.setText("Manage Employees");

		// Clean fields
		cleanManageEmployees();

	}

	public void exit(ActionEvent event) {

	}

	//------------------------------------------------------------------------------------------------------------------
	//
	// Clean functions
	//
	//------------------------------------------------------------------------------------------------------------------
	public void cleanRequestRoomService() {
		txtRoomNumberRequestRoomService.clear();
		//cmboItemRequestRoomService.setItems(); !!!
		txtQuantityRequestRoomService.clear();
		tblInventoryMenu.getSelectionModel().clearSelection();
		tblCart.getSelectionModel().clearSelection();
	}

	public void cleanManageRequests() {
		//cmboAssignEmployee.setItems(); !!!
        tblOpenRequests.getSelectionModel().clearSelection();
        tblOpenRequestDetails.getSelectionModel().clearSelection();
        tblClosedRequests.getSelectionModel().clearSelection();
        tblClosedRequestDetails.getSelectionModel().clearSelection();

    }

	public void cleanManageInventory() {
		txtItemInventory.clear();
		txtQuantityInventory.clear();
        tblInventory.getSelectionModel().clearSelection();
    }

	public void cleanManageEmployees() {
		txtFirstName.clear();
		txtMiddleName.clear();
		txtLastName.clear();
		txtEmployeeID.clear();
		cmboEmployeeType.setItems(employeeTypes);
		cmboEmployeeType.getSelectionModel().clearSelection();
        tblEmployeeDatabase.getSelectionModel().clearSelection();

    }

	//------------------------------------------------------------------------------------------------------------------
	//
	// Request room service functions
	//
	//------------------------------------------------------------------------------------------------------------------
	public void addSelectionToCart(ActionEvent event) {

		// Update Tables
	}

	public void deleteRoomServiceRequest(ActionEvent event) {

		// Update Tables
	}

	public void submitRoomServiceRequest(ActionEvent event) {

		// Update Tables
	}

	public void updateTablesRoomServiceRequest() {

	}

    public void updateTablesInventoryMenu() {

        // Populate List Based on Inventory
        tblInventoryMenu.setItems(inventoryList);
    }



    //------------------------------------------------------------------------------------------------------------------
	//
	// Manage requests
	//
	//------------------------------------------------------------------------------------------------------------------
	public void assignEmployee(ActionEvent event) {

		// Update Tables
	}

	public void completeRequest(ActionEvent event) {

		// Update Tables

	}

	public void deleteRequest(ActionEvent event) {

		// Update Tables

	}

	public void updateTablesManageRequest() {

	}

	//------------------------------------------------------------------------------------------------------------------
	//
	// Manage inventory
	//
	//------------------------------------------------------------------------------------------------------------------
	public void addItemToInventory(ActionEvent event) {
		//Inventory inventory = new Inventory(null, txtItemInventory.getText(), );
		//DataModelI.getInstance().addinventory(inventory);

        int sameItemIndex = -1;
        int currentQuantity = 0;

        if ((txtItemInventory.getText().equals("")) || (txtQuantityInventory.getText().equals(""))) {
            System.out.print("Error: Not a valid item");
        } else {
            for (InventoryItem item: inventoryList) {
                if(item.itemName.equals(txtItemInventory.getText())) {
                    sameItemIndex = inventoryList.indexOf(item);
                    currentQuantity = item.quantity;
                    System.out.println("There's something that is the same");
                    break;
                }

            }

            if (sameItemIndex == -1) { // not same item

                inventoryList.add(new InventoryItem(txtItemInventory.getText(), Integer.parseInt(txtQuantityInventory.getText())));
                cleanManageInventory();

            } else {

                int addedQuantity = Integer.parseInt(txtQuantityInventory.getText());
                currentQuantity = currentQuantity + addedQuantity;
                InventoryItem replacedItem = new InventoryItem(txtItemInventory.getText(), currentQuantity);
                inventoryList.remove(sameItemIndex);
                inventoryList.add(sameItemIndex,replacedItem);
                cleanManageInventory();

            }

        }
    }


	//TODO get info from table view
	public void modifyItemToInventory(ActionEvent event) {
        if(tblInventory.getSelectionModel().getSelectedItem() == null){
        } else {
            int inventoryIndex = tblInventory.getSelectionModel().getSelectedIndex();
            InventoryItem item = new InventoryItem(txtItemInventory.getText(), Integer.parseInt(txtQuantityInventory.getText()));
            inventoryList.remove(tblInventory.getSelectionModel().getSelectedItem());
            inventoryList.add(inventoryIndex, item);
            cleanManageInventory();
            tblInventory.getSelectionModel().clearSelection();
        }
	}

	//TODO get info from table view
	public void deleteItemToInventory(ActionEvent event) {
        if(tblInventory.getSelectionModel().getSelectedItem() == null){
        } else {
            inventoryList.remove(tblInventory.getSelectionModel().getSelectedItem());
            cleanManageInventory();
            tblInventory.getSelectionModel().clearSelection();
        }
	}

	public void updateTablesInventory() {
        // Populate List
        List<Inventory> inventory = invDB.retrieveInventory();

        inventoryList.removeAll();
        inventoryList.clear();
        tblInventory.setItems(inventoryList);

        for(Inventory inv : inventory) {
            inventoryList.add(new InventoryItem(inv.getType(), inv.getQuantity()));
        }

        tblInventory.setItems(inventoryList);

	}


	public void inventoryClicked() {
        if(tblInventory.getSelectionModel().getSelectedItem() == null){
        }
        else {
			/*
			requestInfo selectedRequest = (requestInfo) tblOpenRequests.getSelectionModel().getSelectedItem();
			com.manlyminotaurs.messaging.Request actualRequest = dBUtil.getRequestByID(selectedRequest.requestID);
			lblRequestDetails.setText("SenderID: " + dBUtil.getMessageByID(actualRequest.getMessageID()).getSenderID() + "\n" +
					"Priority: " + dBUtil.getRequestByID(selectedRequest.requestID).getPriority() + "\n" +
					"Location: " + dBUtil.getNodeByIDFromList(actualRequest.getNodeID(), dBUtil.retrieveNodes()).getLongName() + "\n" +
					"Message: " + dBUtil.getMessageByID(actualRequest.getMessageID()).getMessage());
			*/
            txtItemInventory.setText(tblInventory.getSelectionModel().getSelectedItem().getItemName());
            txtQuantityInventory.setText(Integer.toString(tblInventory.getSelectionModel().getSelectedItem().getQuantity()));
        }
    }


	//------------------------------------------------------------------------------------------------------------------
	//
	// Manage employee
	//
	//------------------------------------------------------------------------------------------------------------------
	public void setType(ActionEvent event) {
    	/*
		userID = txtEmployeeID.getText();
		DataModelI.getInstance().getUserByID(userID).setUserType(cmboEmployeeType.getValue());*/
	}

	public void addUser(ActionEvent event) {

		/*firstName = txtFirstName.getText();
		middleName = txtMiddleName.getText();
		lastName = txtLastName.getText();
		type = cmboEmployeeType.getValue();
		DataModelI.getInstance().addUser(firstName,middleName,lastName,languages,type,null,null); */
		UserDBUtil db = new UserDBUtil();

		if ((txtFirstName.getText().equals("")) || (txtLastName.getText().equals("")) || (txtEmployeeID.getText().equals("")) || (cmboEmployeeType.getValue() == (null)) ) {
			System.out.print("Error: Not a valid Employee");
		} else {
			System.out.println(cmboEmployeeType.getValue());
			employeeList.add(new Employee(txtFirstName.getText(), txtMiddleName.getText(), txtLastName.getText(), txtEmployeeID.getText(), cmboEmployeeType.getValue()));
			cleanManageEmployees();
			updateAssignEmployee();
		}
	}

	public void modifyUser(ActionEvent event) {

    	/*firstName = txtFirstName.getText();
		middleName = txtMiddleName.getText();
		lastName = txtLastName.getText();
		type = cmboEmployeeType.getValue().toString();
		userID = txtEmployeeID.getText();

		DataModelI.getInstance().getUserByID(userID).setFirstName(firstName);
		DataModelI.getInstance().getUserByID(userID).setLastName(lastName);
		DataModelI.getInstance().getUserByID(userID).setMiddleName(middleName);
		DataModelI.getInstance().getUserByID(userID).setUserType(type); */

		if(tblEmployeeDatabase.getSelectionModel().getSelectedItem() == null){
		} else {
			int employeeIndex = tblEmployeeDatabase.getSelectionModel().getSelectedIndex();
			Employee emp = new Employee(txtFirstName.getText(), txtMiddleName.getText(), txtLastName.getText(), txtEmployeeID.getText(), cmboEmployeeType.getValue());
			employeeList.remove(tblEmployeeDatabase.getSelectionModel().getSelectedItem());
			employeeList.add(employeeIndex, emp);
			cleanManageEmployees();
			updateAssignEmployee();
			tblEmployeeDatabase.getSelectionModel().clearSelection();
		}


	}

	public void deleteUser(ActionEvent event) {
		/*
		DataModelI.getInstance().removeUserByID(txtEmployeeID.getText());
		 */

		if(tblEmployeeDatabase.getSelectionModel().getSelectedItem() == null){
		} else {
			employeeList.remove(tblEmployeeDatabase.getSelectionModel().getSelectedItem());
			cleanManageEmployees();
			updateAssignEmployee();
			tblEmployeeDatabase.getSelectionModel().clearSelection();

		}

	}

	public void updateTablesEmployee() {

        // Populate List
        List<User> userList = empDB.retrieveUsers();

		employeeList.removeAll();
		employeeList.clear();
		tblEmployeeDatabase.setItems(employeeList);

        for(User user : userList) {
            employeeList.add(new Employee(user.getFirstName(),user.getMiddleName(), user.getLastName(), user.getUserID(), user.getUserType()));
        }

        tblEmployeeDatabase.setItems(employeeList);
	}

	public void updateAssignEmployee() {
		// Update assign button
		employeeNames.clear();
		for (Employee emp: employeeList) {
			String first = emp.getFirstName();
			String last = emp.getLastName();
			String type = emp.getEmployeeType();
			employeeNames.add(first + " " + last + ": " + type);
		}

		cmboAssignEmployee.setItems(employeeNames);
	}

	public void employeeListClicked(){
		if(tblEmployeeDatabase.getSelectionModel().getSelectedItem() == null){
		}
		else {
			/*
			requestInfo selectedRequest = (requestInfo) tblOpenRequests.getSelectionModel().getSelectedItem();
			com.manlyminotaurs.messaging.Request actualRequest = dBUtil.getRequestByID(selectedRequest.requestID);
			lblRequestDetails.setText("SenderID: " + dBUtil.getMessageByID(actualRequest.getMessageID()).getSenderID() + "\n" +
					"Priority: " + dBUtil.getRequestByID(selectedRequest.requestID).getPriority() + "\n" +
					"Location: " + dBUtil.getNodeByIDFromList(actualRequest.getNodeID(), dBUtil.retrieveNodes()).getLongName() + "\n" +
					"Message: " + dBUtil.getMessageByID(actualRequest.getMessageID()).getMessage());
			*/
			txtFirstName.setText(tblEmployeeDatabase.getSelectionModel().getSelectedItem().firstName);
			txtMiddleName.setText(tblEmployeeDatabase.getSelectionModel().getSelectedItem().middleName);
			txtLastName.setText(tblEmployeeDatabase.getSelectionModel().getSelectedItem().lastName);
			txtEmployeeID.setText(tblEmployeeDatabase.getSelectionModel().getSelectedItem().employeeID);
			cmboEmployeeType.getSelectionModel().select(capitalize(tblEmployeeDatabase.getSelectionModel().getSelectedItem().employeeType));
		}
	}


}