package com.manlyminotaurs.viewControllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.manlyminotaurs.databases.DataModelI;
import com.manlyminotaurs.users.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;


public class roomServiceAPIController {

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
	TableView<String> tblInventoryMenu;


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
	TableView<String> tblInventory;


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
	TableView<String> tblEmployeeDatabase;

	final static ObservableList<String> UserTypes = FXCollections.observableArrayList("Doctor", "Nurse", "Visitor", "Admin", "Janitor", "Interpreter", "Patient", "Security");
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

		// Update Tables

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

		// Update Tables

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

		// Update Tables

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

		// Update Tables

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
	}

	public void cleanManageRequests() {
		//cmboAssignEmployee.setItems(); !!!
	}

	public void cleanManageInventory() {
		txtItemInventory.clear();
		txtQuantityInventory.clear();
	}

	public void cleanManageEmployees() {
		txtFirstName.clear();
		txtMiddleName.clear();
		txtLastName.clear();
		txtEmployeeID.clear();
		//cmboEmployeeType.set();
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

		// Update Tables
	}

	public void modifyItemToInventory(ActionEvent event) {

		// Update Tables
	}

	public void deleteItemToInventory(ActionEvent event) {

		// Update Tables
	}

	public void updateTablesInventory() {

	}


	//------------------------------------------------------------------------------------------------------------------
	//
	// Manage employee
	//
	//------------------------------------------------------------------------------------------------------------------
	public void setType(ActionEvent event) {

	}

	public void addUser(ActionEvent event) {
		firstName = txtFirstName.getText();
		middleName = txtMiddleName.getText();
		lastName = txtLastName.getText();
		type = cmboEmployeeType.getValue();
		DataModelI.getInstance().addUser(firstName,middleName,lastName,languages,type,null,null);

	}

	public void modifyUser(ActionEvent event) {
		firstName = txtFirstName.getText();
		middleName = txtMiddleName.getText();
		lastName = txtLastName.getText();
		type = cmboEmployeeType.getValue().toString();

		DataModelI.getInstance().getUserByID(userID).setFirstName(firstName);
		DataModelI.getInstance().getUserByID(userID).setLastName(lastName);
		DataModelI.getInstance().getUserByID(userID).setMiddleName(middleName);
		DataModelI.getInstance().getUserByID(userID).setUserType(type);
	}

	public void deleteUser(ActionEvent event) {
		DataModelI.getInstance().removeUserByID(txtEmployeeID.getText());
	}

	public void updateTablesEmployee() {
		
	}



}