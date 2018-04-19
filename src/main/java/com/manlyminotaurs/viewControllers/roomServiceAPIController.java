package com.manlyminotaurs.viewControllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.manlyminotaurs.databases.DataModelI;
import com.manlyminotaurs.databases.InventoryDBUtil;
import com.manlyminotaurs.databases.UserDBUtil;
import com.manlyminotaurs.messaging.InventoryItem;
import com.manlyminotaurs.messaging.Request;
import com.manlyminotaurs.messaging.RequestInfo;
import com.manlyminotaurs.users.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

//import static com.sun.tools.corba.se.idl.Util.capitalize;
//import static com.sun.xml.internal.ws.util.StringUtils.capitalize;


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
	TableView<InventoryItem> tblCart;

	@FXML
	TableView<InventoryItem> tblInventoryMenu;

    @FXML
    Label lblRoomServiceWarning;


	// Manage Requests

	@FXML
	TableView<RequestInfo> tblOpenRequests;

	@FXML
	TableView<InventoryItem> tblOpenRequestDetails;

	@FXML
	TableView<RequestInfo> tblClosedRequests;

	@FXML
	TableView<InventoryItem> tblClosedRequestDetails;

	@FXML
	JFXComboBox<String> cmboAssignEmployee;

	@FXML
	JFXButton btnAssign;

	@FXML
	JFXButton btnCompleteRequest;

	@FXML
	JFXButton btnDeleteRequest;


	// Manage InventoryItem

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

	@FXML
    Label lblInventoryWarning;


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

	@FXML
    Label lblEmployeeWarning;

    InventoryDBUtil invDB = new InventoryDBUtil();
	ObservableList<String> employeeTypes = FXCollections.observableArrayList("Doctor", "Nurse", "Admin", "Janitor", "Interpreter", "Patient", "Security");
	final static ObservableList<String> currentItems = FXCollections.observableArrayList();
    static ObservableList<Employee> employeeList =  FXCollections.observableArrayList(); //1
    ObservableList<String> employeeNames = FXCollections.observableArrayList();
    static ObservableList<InventoryItem> inventoryList = FXCollections.observableArrayList(); //2
    ObservableList<InventoryItem> cartList = FXCollections.observableArrayList();
    ObservableList<String> emptyList = FXCollections.observableArrayList();
    static ObservableList<RequestInfo> openList = FXCollections.observableArrayList(); //3
    ObservableList<InventoryItem> openDetailsList = FXCollections.observableArrayList();
    static ObservableList<RequestInfo> closedList = FXCollections.observableArrayList(); //4
    ObservableList<InventoryItem> closedDetailsList = FXCollections.observableArrayList();

	public ObservableList<Employee> getEmployeeList() {
		return employeeList;
	}

	public ObservableList<InventoryItem> getInventoryList() {
		return inventoryList;
	}

	public ObservableList<RequestInfo> getOpenList() {
		return openList;
	}

	public ObservableList<RequestInfo> getClosedList() {
		return closedList;
	}

	String firstName;
	String middleName;
	String lastName;
	List<String> languages;
	String language;
	String type;
	String username;
	String password;
	String userID;
	com.manlyminotaurs.users.Employee user;

//-------------------------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------------------

	private static int requestIDCounter = 0;

	public static void setRequestIDCounter(int requestIDCounter_in) {
		requestIDCounter = requestIDCounter_in;
	}

	public String generateRequestID() {
		requestIDCounter++;
		return Integer.toString(requestIDCounter);
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
		tblEmployeeDatabase.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		fName.setCellValueFactory(new PropertyValueFactory<Employee, String>("firstName"));
		mName.setCellValueFactory(new PropertyValueFactory<Employee, String>("middleName"));
		lName.setCellValueFactory(new PropertyValueFactory<Employee, String>("lastName"));
		empID.setCellValueFactory(new PropertyValueFactory<Employee, String>("employeeID"));
		empType.setCellValueFactory(new PropertyValueFactory<Employee, String>("employeeType"));

		tblEmployeeDatabase.setColumnResizePolicy(tblInventory.CONSTRAINED_RESIZE_POLICY);


		// Create InventoryItem Table
        TableColumn itemName = new TableColumn("Item");
        TableColumn quantity = new TableColumn("Quantity");

        tblInventory.getColumns().addAll(itemName, quantity);
        tblInventory.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        itemName.setCellValueFactory(new PropertyValueFactory<InventoryItem, String>("itemName"));
        quantity.setCellValueFactory(new PropertyValueFactory<InventoryItem, String>("quantity"));

		tblInventory.setColumnResizePolicy(tblInventory.CONSTRAINED_RESIZE_POLICY);

        // Create InventoryItem Menu Table
        TableColumn itemNameMenu = new TableColumn("Item");
        TableColumn quantityMenu = new TableColumn("Quantity");

        tblInventoryMenu.getColumns().addAll(itemNameMenu, quantityMenu);
        tblInventoryMenu.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        itemNameMenu.setCellValueFactory(new PropertyValueFactory<InventoryItem, String>("itemName"));
        quantityMenu.setCellValueFactory(new PropertyValueFactory<InventoryItem, String>("quantity"));

        // Create Cart Table
        TableColumn itemCart = new TableColumn("Item");
        TableColumn quantityCart = new TableColumn("Quantity");

        tblCart.getColumns().addAll(itemCart, quantityCart);
        tblCart.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        itemCart.setCellValueFactory(new PropertyValueFactory<InventoryItem, String>("itemName"));
        quantityCart.setCellValueFactory(new PropertyValueFactory<InventoryItem, String>("quantity"));

        // Open List Table
        TableColumn roomNumberOpen = new TableColumn("Room Number");
        TableColumn employeeAssignedOpen = new TableColumn("Employee Assigned");

        tblOpenRequests.getColumns().addAll(roomNumberOpen, employeeAssignedOpen);
        tblOpenRequests.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        roomNumberOpen.setCellValueFactory(new PropertyValueFactory<RequestInfo, String>("room"));
        employeeAssignedOpen.setCellValueFactory(new PropertyValueFactory<RequestInfo, String>("employee"));

        // Closed List Table
        TableColumn roomNumberClosed = new TableColumn("Room Number");
        TableColumn employeeAssignedClosed = new TableColumn("Employee Assigned");

        tblClosedRequests.getColumns().addAll(roomNumberClosed, employeeAssignedClosed);
        tblClosedRequests.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        roomNumberClosed.setCellValueFactory(new PropertyValueFactory<RequestInfo, String>("room"));
        employeeAssignedClosed.setCellValueFactory(new PropertyValueFactory<RequestInfo, String>("employee"));

        // Open Request Details
        TableColumn itemDetailsOpen = new TableColumn("Item");
        TableColumn quantityDetailsOpen = new TableColumn("Quantity");

        tblOpenRequestDetails.getColumns().addAll(itemDetailsOpen, quantityDetailsOpen);
        tblOpenRequestDetails.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        itemDetailsOpen.setCellValueFactory(new PropertyValueFactory<InventoryItem, String>("itemName"));
        quantityDetailsOpen.setCellValueFactory(new PropertyValueFactory<InventoryItem, String>("quantity"));

        // Closed Request Details
        TableColumn itemDetailsClosed = new TableColumn("Item");
        TableColumn quantityDetailsClosed = new TableColumn("Quantity");

        tblClosedRequestDetails.getColumns().addAll(itemDetailsClosed, quantityDetailsClosed);
        tblClosedRequestDetails.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        itemDetailsClosed.setCellValueFactory(new PropertyValueFactory<InventoryItem, String>("itemName"));
        quantityDetailsClosed.setCellValueFactory(new PropertyValueFactory<InventoryItem, String>("quantity"));


        //---------------------------------POPULATE LISTS From DATABASE------------------------
		List<InventoryItem> itemList = DataModelI.getInstance().retrieveInventory();
		for(InventoryItem aItem: itemList){
			inventoryList.add(aItem);
		}
		List<Request> listOfRequest = DataModelI.getInstance().retrieveRequests();
		for(Request currReq : listOfRequest) {
			RequestInfo aRequestInfo = new RequestInfo(currReq.getRequestInfo().getRoom(), currReq.getRequestInfo().getEmployee(), currReq.getRequestInfo().getItems());
			if (!currReq.getComplete()) {
				openList.add(aRequestInfo);
            } else {
				closedList.add(aRequestInfo);
            }
        }

		for(com.manlyminotaurs.users.Employee aEmployee : DataModelI.getInstance().retrieveUsers()){
			employeeList.add(aEmployee);
		}



		System.out.println("hello checkpoint");
        //-----------------------------------POPULATE LISTS ENDED----------------------------------

		// Update Tables
        //updateTablesInventory();
        updateTablesRoomServiceRequest();
        //updateTablesEmployee();

        // Update ComboBoxes
        cmboEmployeeType.setItems(employeeTypes);
        updateCurrentItems();

        tblCart.setItems(cartList);
        tblOpenRequests.setItems(openList);
        tblClosedRequests.setItems(closedList);
        tblOpenRequestDetails.setItems(openDetailsList);
        tblClosedRequestDetails.setItems(closedDetailsList);
        tblInventory.setItems(inventoryList);
        tblEmployeeDatabase.setItems(employeeList);

		// Populate Employee Combobox
		updateAssignEmployee();

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
		//cleanRequestRoomService();
        lblRoomServiceWarning.setVisible(false);

		// Update ComboBoxes
		updateCurrentItems();
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
		lblSubtitle.setText("Manage InventoryItem");

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
		//clearTablesCart();

		//cartList.removeAll();
		cartList.clear();
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

        int sameItemIndex = -1;
        int currentQuantity = 0;


        if ((txtRoomNumberRequestRoomService.getText().equals("")) || (txtQuantityRequestRoomService.getText().equals("")) || (cmboItemRequestRoomService.getValue() == null)) {
            System.out.print("Error: Not a valid selection");

        } else {

            for (InventoryItem item: inventoryList) {
                if(item.getItemName().equals(cmboItemRequestRoomService.getValue())) {
                    sameItemIndex = inventoryList.indexOf(item);
                    currentQuantity = item.getQuantity();
                    break;
                }
            }


            int subtractedQuantity = Integer.parseInt(txtQuantityRequestRoomService.getText());
            currentQuantity = currentQuantity - subtractedQuantity;

            if (currentQuantity < 0) {

                System.out.print("Error: Quantity not large enough for selected item");

            } else {

                InventoryItem newItemInCart = new InventoryItem("", cmboItemRequestRoomService.getValue(),subtractedQuantity);
                InventoryItem returnedItemInInventory = new InventoryItem("",cmboItemRequestRoomService.getValue(),currentQuantity);
                inventoryList.remove(sameItemIndex);
                inventoryList.add(sameItemIndex,returnedItemInInventory);
                cartList.add(newItemInCart);

            }

        }
	}

	public void deleteRoomServiceRequest(ActionEvent event) {

        int sameItemIndex = -1;
        int currentQuantity = 0;

        if(tblCart.getSelectionModel().getSelectedItem() == null){

        }
        else {

            for (InventoryItem item: inventoryList) {
                if(item.getItemName().equals(tblCart.getSelectionModel().getSelectedItem().getItemName())) {
                    sameItemIndex = inventoryList.indexOf(item);
                    currentQuantity = item.getQuantity();
                    break;
                }
            }

            int addedQuantity = tblCart.getSelectionModel().getSelectedItem().getQuantity();
            currentQuantity = currentQuantity + addedQuantity;
            InventoryItem renewedItem = new InventoryItem("", tblCart.getSelectionModel().getSelectedItem().getItemName(), currentQuantity);
            inventoryList.remove(sameItemIndex);
            cartList.remove(tblCart.getSelectionModel().getSelectedItem());
            inventoryList.add(sameItemIndex,renewedItem);

            cleanManageInventory();

        }
	}

	public void submitRoomServiceRequest(ActionEvent event) {

		System.out.println(cartList.size());
		ObservableList<InventoryItem> newCart = FXCollections.observableArrayList();

		for (int x = 0; x < cartList.size(); x++) {
			newCart.add(cartList.get(x));
		}

		if(txtRoomNumberRequestRoomService.getText().equals("") || (newCart.size() == 0)) {
			System.out.println("Not a valid room service request");

		} else {
			RequestInfo newReq = new RequestInfo(txtRoomNumberRequestRoomService.getText(), null, newCart);
			openList.add(newReq);
			cartList.clear();
			cmboItemRequestRoomService.setValue(null);
			txtQuantityRequestRoomService.clear();
			txtRoomNumberRequestRoomService.clear();
			System.out.println(openList.size());
		}

	}

	public void updateTablesRoomServiceRequest() {
        updateTablesInventoryMenu();
        clearTablesCart();
	}

    public void updateTablesInventoryMenu() {

        // Populate List Based on InventoryItem
        tblInventoryMenu.setItems(inventoryList);
    }

    public void clearTablesCart() {
        for ( int i = 0; i<tblCart.getItems().size(); i++) {
            tblCart.getItems().clear();
        }
    }

    public void cartClicked() {
        if(tblCart.getSelectionModel().getSelectedItem() == null){
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
            //tblInventory.getSelectionModel().getSelectedItem()
            //txtQuantityInventory.setText(Integer.toString(tblInventory.getSelectionModel().getSelectedItem().getQuantity()));
        }
    }

    public void updateCurrentItems() {

        currentItems.clear();

        for(int x=0; x<inventoryList.size(); x++) {
            currentItems.add(inventoryList.get(x).getItemName());
        }

        cmboItemRequestRoomService.setItems(currentItems);
    }



    //------------------------------------------------------------------------------------------------------------------
	//
	// Manage requests
	//
	//------------------------------------------------------------------------------------------------------------------
	public void assignEmployee(ActionEvent event) {
	    if (tblOpenRequests.getSelectionModel() == null) {
        } else {
            RequestInfo replacedRequestInfo = new RequestInfo(tblOpenRequests.getSelectionModel().getSelectedItem().getRoom(), cmboAssignEmployee.getValue(), tblOpenRequests.getSelectionModel().getSelectedItem().getItems());
            openList.remove(tblOpenRequests.getSelectionModel().getSelectedItem());
            openList.add(replacedRequestInfo);
        }
	}

	public void completeRequest(ActionEvent event) {

		closedList.add(tblOpenRequests.getSelectionModel().getSelectedItem());
		openList.remove(tblOpenRequests.getSelectionModel().getSelectedItem());
	}

	public void deleteRequest(ActionEvent event) {

        openList.remove(tblOpenRequests.getSelectionModel().getSelectedItem());

    }

	public void updateTablesManageRequest() {

	}

	public void loadRequestDetails() {

	    openDetailsList.clear();

        if(tblOpenRequests.getSelectionModel().getSelectedItem() == null){
            System.out.println("touched open request");
        }
        else {
            System.out.println("entered else");
            System.out.println(tblOpenRequests.getSelectionModel().getSelectedItem().getItems().get(0).getItemName());
            //openDetailsList = tblOpenRequests.getSelectionModel().getSelectedItem().getItems();

            for(int x=0; x<tblOpenRequests.getSelectionModel().getSelectedItem().getItems().size();x++) {
                openDetailsList.add(tblOpenRequests.getSelectionModel().getSelectedItem().getItems().get(x));
            }
        }
    }

    public void loadRequestDetailsClosed() {

        closedDetailsList.clear();

        if(tblClosedRequests.getSelectionModel().getSelectedItem() == null){
            System.out.println("touched open request");
        }
        else {
            System.out.println("entered else");
            //System.out.println(tblClosedRequests.getSelectionModel().getSelectedItem().getItems().get(0).getItemName());
            //openDetailsList = tblOpenRequests.getSelectionModel().getSelectedItem().getItems();

            for(int x=0; x<tblClosedRequests.getSelectionModel().getSelectedItem().getItems().size();x++) {
                closedDetailsList.add(tblClosedRequests.getSelectionModel().getSelectedItem().getItems().get(x));
            }
        }
    }

	//------------------------------------------------------------------------------------------------------------------
	//
	// Manage inventory
	//
	//------------------------------------------------------------------------------------------------------------------
	public void addItemToInventory(ActionEvent event) {
		//InventoryItem inventory = new InventoryItem(null, txtItemInventory.getText(), );
		//DataModelI.getInstance().addInventory(inventory);

        int sameItemIndex = -1;
        int currentQuantity = 0;

        if ((txtItemInventory.getText().equals("")) || (txtQuantityInventory.getText().equals(""))) {
            System.out.print("Error: Not a valid item");
        } else {
            for (InventoryItem item: inventoryList) {
                if(item.getItemName().equals(txtItemInventory.getText())) {
                    sameItemIndex = inventoryList.indexOf(item);
                    currentQuantity = item.getQuantity();
                    System.out.println("There's something that is the same");
                    break;
                }

            }

            if (sameItemIndex == -1) { // not same item

                inventoryList.add(new InventoryItem("",txtItemInventory.getText(), Integer.parseInt(txtQuantityInventory.getText())));
                cleanManageInventory();

            } else {

                int addedQuantity = Integer.parseInt(txtQuantityInventory.getText());
                currentQuantity = currentQuantity + addedQuantity;
                InventoryItem replacedItem = new InventoryItem("",txtItemInventory.getText(), currentQuantity);
                inventoryList.remove(sameItemIndex);
                inventoryList.add(sameItemIndex,replacedItem);
                cleanManageInventory();

            }

        }
    }

	public void modifyItemToInventory(ActionEvent event) {
        if(tblInventory.getSelectionModel().getSelectedItem() == null){
        } else {
            int inventoryIndex = tblInventory.getSelectionModel().getSelectedIndex();
            InventoryItem item = new InventoryItem("",txtItemInventory.getText(), Integer.parseInt(txtQuantityInventory.getText()));
            inventoryList.remove(tblInventory.getSelectionModel().getSelectedItem());
            inventoryList.add(inventoryIndex, item);
            cleanManageInventory();
            tblInventory.getSelectionModel().clearSelection();
        }
	}

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
        /*List<InventoryItem> inventory = invDB.retrieveInventory();

        inventoryList.clear(); // !!!
        tblInventory.setItems(inventoryList);

        for(InventoryItem inv : inventory) {
            inventoryList.add(new InventoryItem(inv.getType(), inv.getQuantity()));
        }*/



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
	public void setType(ActionEvent event) { // not used
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
        List<com.manlyminotaurs.users.Employee> userList = DataModelI.getInstance().retrieveUsers();

		employeeList.removeAll();
		employeeList.clear();
		tblEmployeeDatabase.setItems(employeeList);

        for(com.manlyminotaurs.users.Employee user : userList) {
            employeeList.add(new Employee(user.getFirstName(),user.getMiddleName(), user.getLastName(), user.getEmployeeID(), user.getEmployeeType()));
        }

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
			txtFirstName.setText(tblEmployeeDatabase.getSelectionModel().getSelectedItem().getFirstName());
			txtMiddleName.setText(tblEmployeeDatabase.getSelectionModel().getSelectedItem().getMiddleName());
			txtLastName.setText(tblEmployeeDatabase.getSelectionModel().getSelectedItem().getLastName());
			txtEmployeeID.setText(tblEmployeeDatabase.getSelectionModel().getSelectedItem().getEmployeeID());
			cmboEmployeeType.getSelectionModel().select(tblEmployeeDatabase.getSelectionModel().getSelectedItem().getEmployeeType());
		}
	}


}