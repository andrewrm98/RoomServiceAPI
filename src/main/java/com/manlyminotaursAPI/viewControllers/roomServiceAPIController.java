package com.manlyminotaursAPI.viewControllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.manlyminotaursAPI.databases.DataModelIAPI;
import com.manlyminotaursAPI.databases.InventoryDBUtil;
import com.manlyminotaursAPI.databases.UserDBUtil;
import com.manlyminotaursAPI.messaging.InventoryItem;
import com.manlyminotaursAPI.messaging.Request;
import com.manlyminotaursAPI.messaging.RequestInfo;
import com.manlyminotaursAPI.users.Employee;
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
    static ObservableList<InventoryItem> openDetailsList = FXCollections.observableArrayList();
	ObservableList<InventoryItem> tempOpenDetailsList = FXCollections.observableArrayList();
    static ObservableList<RequestInfo> closedList = FXCollections.observableArrayList(); //4
    static ObservableList<InventoryItem> closedDetailsList = FXCollections.observableArrayList();
	ObservableList<InventoryItem> tempClosedDetailsList = FXCollections.observableArrayList();

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

	public ObservableList<InventoryItem> getOpenDetailsList() { return openDetailsList; }

	public ObservableList<InventoryItem> getClosedDetailsList() { return closedDetailsList; }

	String firstName;
	String middleName;
	String lastName;
	List<String> languages;
	String language;
	String type;
	String username;
	String password;
	String userID;
	com.manlyminotaursAPI.users.Employee user;

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


        //---------------------------------POPULATE LISTS From DATABASE--------------------------------------
        System.out.println("----------------Populating lists from DATABASE----------------");


		List<Request> listOfRequest = DataModelIAPI.getInstance().retrieveRequests();
		closedList.clear();
		openList.clear();
		for(Request currReq : listOfRequest) {
			RequestInfo aRequestInfo = new RequestInfo(currReq.getRequestID(), currReq.getRequestInfo().getRoom(), currReq.getRequestInfo().getEmployee(), currReq.getRequestInfo().getItems());
			if (!currReq.getComplete()) {
				openList.add(aRequestInfo);
			} else {
				closedList.add(aRequestInfo);
			}
		}

        List<InventoryItem> itemList = DataModelIAPI.getInstance().retrieveInventory();
		inventoryList.clear();
		for(InventoryItem aItem: itemList){
			if(aItem.getRequestID().equals("null") || aItem.getRequestID().equals("") || aItem.getRequestID() == null) {
				inventoryList.add(aItem);
			}
			else {
				Request aRequest = DataModelIAPI.getInstance().getRequestByID(aItem.getRequestID());
				if (openList != null && openList.contains(aRequest.getRequestInfo())) {
					openDetailsList.add(aItem);
				}
				else {
					closedDetailsList.add(aItem);
				}
			}
		}

        employeeList.clear();
		for(com.manlyminotaursAPI.users.Employee aEmployee : DataModelIAPI.getInstance().retrieveUsers()){
			employeeList.add(aEmployee);
		}



        System.out.println("------------------DONE POPULATING------------------------------");
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
        tblOpenRequestDetails.setItems(tempOpenDetailsList);
        tblClosedRequestDetails.setItems(tempClosedDetailsList);
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
		lblSubtitle.setText("Manage Inventory Item");
		lblInventoryWarning.setVisible(false);

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
		lblEmployeeWarning.setVisible(false);

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
        int itemInCart = 0;
        int sameItemInCartIndex = -1;
        int newQuantity = 0;


        if ((txtRoomNumberRequestRoomService.getText().equals("")) || (txtQuantityRequestRoomService.getText().equals("")) || (cmboItemRequestRoomService.getValue() == null) || (isNotNumber(txtQuantityRequestRoomService.getText()))) {
            System.out.print("Error: Not a valid selection");
			lblRoomServiceWarning.setVisible(true);
			lblRoomServiceWarning.setText("Please set a room, item, and quantity to add an item to the cart");

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
				lblRoomServiceWarning.setVisible(true);
				lblRoomServiceWarning.setText("Quantity too large for selected item");

            } else {


            	for (int y=0;y<cartList.size();y++) {
            		if(cartList.get(y).getItemName().equals(cmboItemRequestRoomService.getValue())) {

            			sameItemInCartIndex = y;
            			newQuantity = cartList.get(y).getQuantity() + subtractedQuantity;
						InventoryItem newItemInCart = new InventoryItem("", cmboItemRequestRoomService.getValue(),newQuantity,null);
						cartList.remove(sameItemInCartIndex);
            			cartList.add(sameItemInCartIndex, newItemInCart);

						itemInCart = 1;
						break;
					}
				}

				InventoryItem returnedItemInInventory = new InventoryItem("",cmboItemRequestRoomService.getValue(),currentQuantity,null);//
				inventoryList.remove(sameItemIndex);
				inventoryList.add(sameItemIndex,returnedItemInInventory);

				if (itemInCart != 1) {
            		cartList.add(new InventoryItem("", cmboItemRequestRoomService.getValue(),subtractedQuantity,null));
				}

				lblRoomServiceWarning.setVisible(false);

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
            InventoryItem renewedItem = new InventoryItem("", tblCart.getSelectionModel().getSelectedItem().getItemName(), currentQuantity, null);
            inventoryList.remove(sameItemIndex);
            cartList.remove(tblCart.getSelectionModel().getSelectedItem());
            inventoryList.add(sameItemIndex,renewedItem);

            cleanManageInventory();

            lblRoomServiceWarning.setVisible(false);

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
			lblRoomServiceWarning.setVisible(true);
			lblRoomServiceWarning.setText("Not a valid room service request. Please add an item and a room number to add.");
		} else {
			RequestInfo newReq = new RequestInfo("", txtRoomNumberRequestRoomService.getText(), null, newCart);
			String requestID = DataModelIAPI.getInstance().addRequest("",false,newReq);
			for(InventoryItem aItem: newCart) {
               aItem.setRequestID(requestID);
               openDetailsList.add(aItem);
            }


			openList.add(newReq);
			cartList.clear();
			cmboItemRequestRoomService.setValue(null);
			txtQuantityRequestRoomService.clear();
			txtRoomNumberRequestRoomService.clear();
			System.out.println(openList.size());
			lblRoomServiceWarning.setVisible(false);

			lblRoomServiceWarning.setVisible(false);

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
			com.manlyminotaursAPI.messaging.Request actualRequest = dBUtil.getRequestByID(selectedRequest.requestID);
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
        RequestInfo getReqInfo = tblOpenRequests.getSelectionModel().getSelectedItem();
        String requestID = getReqInfo.getRequestID();
        String requestRoom = getReqInfo.getRoom();
        String employee = cmboAssignEmployee.getValue();
        ObservableList<InventoryItem> items = getReqInfo.getItems();

	    if (tblOpenRequests.getSelectionModel() == null) {
        } else {
            RequestInfo replacedRequestInfo = new RequestInfo(requestID, requestRoom, employee, items);
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

	    tempOpenDetailsList.clear();

        if(tblOpenRequests.getSelectionModel().getSelectedItem() == null){
            System.out.println("touched open request");
        }
        else {
            System.out.println("loadRequestDetails");
			String selectedRequestID = tblOpenRequests.getSelectionModel().getSelectedItem().getRequestID();
			for(InventoryItem aItem: openDetailsList) {
				if(selectedRequestID.equals(aItem.getRequestID())) {
					tempOpenDetailsList.add(aItem);
				}
			}

//            for(int x=0; x<tblOpenRequests.getSelectionModel().getSelectedItem().getItems().size();x++) {
//                openDetailsList.add(tblOpenRequests.getSelectionModel().getSelectedItem().getItems().get(x));
//            }
        }
    }

    public void loadRequestDetailsClosed() {

        tempClosedDetailsList.clear();

        if(tblClosedRequests.getSelectionModel().getSelectedItem() == null){
            System.out.println("touched open request");
        }
        else {
            System.out.println("loadRequestDetails closed");
			String selectedRequestID = tblClosedRequests.getSelectionModel().getSelectedItem().getRequestID();
			List<InventoryItem> newList = new ArrayList<>(openDetailsList);
			newList.addAll(closedDetailsList);
			for(InventoryItem aItem: newList) {
				if(selectedRequestID.equals(aItem.getRequestID())) {
					tempClosedDetailsList.add(aItem);
				}
			}

//            for(int x=0; x<tblClosedRequests.getSelectionModel().getSelectedItem().getItems().size();x++) {
//                closedDetailsList.add(tblClosedRequests.getSelectionModel().getSelectedItem().getItems().get(x));
//            }
        }
    }

	//------------------------------------------------------------------------------------------------------------------
	//
	// Manage inventory
	//
	//------------------------------------------------------------------------------------------------------------------
	public void addItemToInventory(ActionEvent event) {
		//InventoryItem inventory = new InventoryItem(null, txtItemInventory.getText(), );
		//DataModelIAPI.getInstance().addInventory(inventory);

        int sameItemIndex = -1;
        int currentQuantity = 0;

        if ((txtItemInventory.getText().equals("")) || (isNotNumber(txtQuantityInventory.getText()))) {
            System.out.print("Error: Not a valid item");
            lblInventoryWarning.setVisible(true);
            lblInventoryWarning.setText("Not a valid item. Please add a quantity greater than or equal to 0");
        } else {
            for (InventoryItem item: inventoryList) {
                if(item.getItemName().toLowerCase().equals(txtItemInventory.getText().toLowerCase())) {
                    sameItemIndex = inventoryList.indexOf(item);
                    currentQuantity = item.getQuantity();
                    System.out.println("There's something that is the same");
                    break;
                }

            }

            if (sameItemIndex == -1) { // not same item

                inventoryList.add(new InventoryItem("",txtItemInventory.getText(), Integer.parseInt(txtQuantityInventory.getText()),null));

				lblInventoryWarning.setVisible(false);
				cleanManageInventory();

            } else {

                int addedQuantity = Integer.parseInt(txtQuantityInventory.getText());
                currentQuantity = currentQuantity + addedQuantity;
                InventoryItem replacedItem = new InventoryItem("",inventoryList.get(sameItemIndex).getItemName(), currentQuantity, null);
                inventoryList.remove(sameItemIndex);
                inventoryList.add(sameItemIndex,replacedItem);

                lblInventoryWarning.setVisible(false);
                cleanManageInventory();

            }

        }
    }

	public void modifyItemToInventory(ActionEvent event) {
        if((tblInventory.getSelectionModel().getSelectedItem() == null) || (txtItemInventory.getText().equals("")) || (isNotNumber(txtQuantityInventory.getText()))) {
			System.out.print("Error: Not a valid quantity");
			lblInventoryWarning.setVisible(true);
			lblInventoryWarning.setText("Not a valid item. Please add a quantity greater than or equal to 0");
		} else {
            int inventoryIndex = tblInventory.getSelectionModel().getSelectedIndex();
            InventoryItem item = new InventoryItem("",txtItemInventory.getText(), Integer.parseInt(txtQuantityInventory.getText()), null);
            inventoryList.remove(tblInventory.getSelectionModel().getSelectedItem());
            inventoryList.add(inventoryIndex, item);
            cleanManageInventory();
            tblInventory.getSelectionModel().clearSelection();
            lblInventoryWarning.setVisible(false);

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
			com.manlyminotaursAPI.messaging.Request actualRequest = dBUtil.getRequestByID(selectedRequest.requestID);
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
		DataModelIAPI.getInstance().getUserByID(userID).setUserType(cmboEmployeeType.getValue());*/
	}

	public void addUser(ActionEvent event) {

		/*firstName = txtFirstName.getText();
		middleName = txtMiddleName.getText();
		lastName = txtLastName.getText();
		type = cmboEmployeeType.getValue();
		DataModelIAPI.getInstance().addUser(firstName,middleName,lastName,languages,type,null,null); */
		//UserDBUtil db = new UserDBUtil();

		if ((txtFirstName.getText().equals("")) || (txtLastName.getText().equals("")) || (txtEmployeeID.getText().equals("")) || (cmboEmployeeType.getValue() == (null)) ) {
			System.out.print("Error: Not a valid Employee");
			lblEmployeeWarning.setVisible(true);
			lblEmployeeWarning.setText("Not a valid employee - an employee requires a first name, last name, id, and type");
		} else {
			System.out.println(cmboEmployeeType.getValue());
			employeeList.add(new Employee(txtFirstName.getText(), txtMiddleName.getText(), txtLastName.getText(), txtEmployeeID.getText(), cmboEmployeeType.getValue()));
			cleanManageEmployees();
			updateAssignEmployee();
			lblEmployeeWarning.setVisible(false);
		}
	}

	public void modifyUser(ActionEvent event) {

    	/*firstName = txtFirstName.getText();
		middleName = txtMiddleName.getText();
		lastName = txtLastName.getText();
		type = cmboEmployeeType.getValue().toString();
		userID = txtEmployeeID.getText();

		DataModelIAPI.getInstance().getUserByID(userID).setFirstName(firstName);
		DataModelIAPI.getInstance().getUserByID(userID).setLastName(lastName);
		DataModelIAPI.getInstance().getUserByID(userID).setMiddleName(middleName);
		DataModelIAPI.getInstance().getUserByID(userID).setUserType(type); */

		if(tblEmployeeDatabase.getSelectionModel().getSelectedItem() == null){
		} else {
			int employeeIndex = tblEmployeeDatabase.getSelectionModel().getSelectedIndex();
			Employee emp = new Employee(txtFirstName.getText(), txtMiddleName.getText(), txtLastName.getText(), txtEmployeeID.getText(), cmboEmployeeType.getValue());
			employeeList.remove(tblEmployeeDatabase.getSelectionModel().getSelectedItem());
			employeeList.add(employeeIndex, emp);
			cleanManageEmployees();
			updateAssignEmployee();
			tblEmployeeDatabase.getSelectionModel().clearSelection();
			lblEmployeeWarning.setVisible(false);
		}


	}

	public void deleteUser(ActionEvent event) {
		/*
		DataModelIAPI.getInstance().removeUserByID(txtEmployeeID.getText());
		 */

		if(tblEmployeeDatabase.getSelectionModel().getSelectedItem() == null){
		} else {
			employeeList.remove(tblEmployeeDatabase.getSelectionModel().getSelectedItem());
			cleanManageEmployees();
			updateAssignEmployee();
			tblEmployeeDatabase.getSelectionModel().clearSelection();
			lblEmployeeWarning.setVisible(false);
		}

	}

	public void updateTablesEmployee() {

        // Populate List
        List<com.manlyminotaursAPI.users.Employee> userList = DataModelIAPI.getInstance().retrieveUsers();

		employeeList.removeAll();
		employeeList.clear();
		tblEmployeeDatabase.setItems(employeeList);

        for(com.manlyminotaursAPI.users.Employee user : userList) {
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
			txtFirstName.setText(tblEmployeeDatabase.getSelectionModel().getSelectedItem().getFirstName());
			txtMiddleName.setText(tblEmployeeDatabase.getSelectionModel().getSelectedItem().getMiddleName());
			txtLastName.setText(tblEmployeeDatabase.getSelectionModel().getSelectedItem().getLastName());
			txtEmployeeID.setText(tblEmployeeDatabase.getSelectionModel().getSelectedItem().getEmployeeID());
			cmboEmployeeType.getSelectionModel().select(tblEmployeeDatabase.getSelectionModel().getSelectedItem().getEmployeeType());
		}
	}

	public boolean isNotNumber(String supposedlyNumber) {
		String numbers = "0123456789";
		int countRight;

		for(int x=0; x<supposedlyNumber.length();x++) {

			countRight = 0;

			for(int y=0; y<numbers.length();y++) {

				if(supposedlyNumber.charAt(x) == numbers.charAt(y)) {
					countRight = 1;
					break;
				}
			} if(countRight!=1) {
				return true;
			}
		}
		return false;
	}


}