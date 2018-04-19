package com.manlyminotaurs.databases;

import com.manlyminotaurs.messaging.InventoryItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InventoryDBUtil {

	//------------------------------------------------------------------------------------------------------------------
	//
	//								Retrieve InventoryItem
	//
	//------------------------------------------------------------------------------------------------------------------
	/**
	 * Creates a list of objects and stores them in the global variable dataModelI.getMessageList()
	 */
	public List<InventoryItem> retrieveInventory() {
		// Connection
		Connection connection = DataModelI.getInstance().getNewConnection();

		// Variables
		InventoryItem inventory = null;
		String ID;
		String type;
		int quantity;
		String location;
		List<InventoryItem> listOfInventory = new ArrayList<>();

		try {
			Statement stmt = connection.createStatement();
			String str = "SELECT * FROM INVENTORY";
			ResultSet rset = stmt.executeQuery(str);

			if (rset.next()) {
				ID = rset.getString("ID");
				type = rset.getString("type");
				quantity = rset.getInt("quantity");

				// Add the new edge to the list
				inventory = new InventoryItem(ID, type, quantity);
				listOfInventory.add(inventory);
				System.out.println("InventoryItem added to the list: "+ ID);
			}
			rset.close();
			stmt.close();
			System.out.println("Done adding inventories");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DataModelI.getInstance().closeConnection();
		}
		return listOfInventory;
	}

	//------------------------------------------------------------------------------------------------------------------
	//
	//								Add/edit/remove inventory
	//
	//------------------------------------------------------------------------------------------------------------------
	private static int inventoryIDCounter = 0;

	public static void setInventoryIDCounter(int inventoryIDCounter) {
		InventoryDBUtil.inventoryIDCounter = inventoryIDCounter;
	}
	
	private String generateInventoryID(){
		inventoryIDCounter++;
		return Integer.toString(inventoryIDCounter);
	}

	public InventoryItem addInventory(InventoryItem inventory){
		System.out.println("addInventory");
		if(inventory.getID() == null || inventory.getID().equals("")) {
			inventory.setID(generateInventoryID());
		}

		Connection connection = DataModelI.getInstance().getNewConnection();
		try {
			String str = "INSERT INTO inventory(ID, type, quantity) VALUES (?,?,?)";

			// Create the prepared statement
			PreparedStatement statement = connection.prepareStatement(str);
			statement.setString(1, inventory.getID());
			statement.setString(2, inventory.getItemName());
			statement.setInt(3, inventory.getQuantity());
			System.out.println("Prepared statement created...");
			statement.executeUpdate();
			statement.close();
			System.out.println("Node added to database");
		} catch (SQLException e)
		{
			System.out.println("inventory already in the database");
			e.printStackTrace();
		} finally {
			DataModelI.getInstance().closeConnection();
		}
		return inventory;
	}

	public boolean removeInventory(InventoryItem inventory){
		boolean isSuccess = false;
		Connection connection = DataModelI.getInstance().getNewConnection();
		try {
			Statement stmt = connection.createStatement();
			String str = "DELETE FROM inventory WHERE ID = '" + inventory.getID() + "'";
			stmt.executeUpdate(str);
			stmt.close();
			System.out.println("Node removed from database");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DataModelI.getInstance().closeConnection();
			isSuccess = true;
		}
		return isSuccess;
	}

	public boolean modifyInventory(InventoryItem inventory) {
		Connection connection = DataModelI.getInstance().getNewConnection();
		boolean isSuccess = false;
		try {
			String str = "UPDATE inventory SET type = ?, quantity = ? WHERE ID = '"+ inventory.getID() +"'" ;

			// Create the prepared statement
			PreparedStatement statement = connection.prepareStatement(str);
			statement.setString(1, inventory.getItemName());
			statement.setInt(2, inventory.getQuantity());
			statement.executeUpdate();
			System.out.println("inventory added to database");
			statement.close();
			isSuccess = true;
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally {
			DataModelI.getInstance().closeConnection();
		}
		return isSuccess;
	}

	//------------------------------------------------------------------------------------------------------------------
	//
	//								Get inventory by ID
	//
	//------------------------------------------------------------------------------------------------------------------
	public InventoryItem getInventoryByID(String ID){
		// Connection
		Connection connection = DataModelI.getInstance().getNewConnection();

		// Variables
		InventoryItem inventory = null;
		String type;
		int quantity;
		String location;

		List<InventoryItem> listOfInventory = new ArrayList<>();

		try {
			Statement stmt = connection.createStatement();
			String str = "SELECT * FROM Inventory Where ID = '" + ID + "'";
			ResultSet rset = stmt.executeQuery(str);

			if (rset.next()) {
				type = rset.getString("type");
				quantity = rset.getInt("quantity");

				// Add the new edge to the list
				inventory = new InventoryItem(ID, type, quantity);
				listOfInventory.add(inventory);
				System.out.println("InventoryItem added to the list: "+ ID);
			}
			//rset.close();
		//	stmt.close();
			System.out.println("Done adding inventories");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		//	DataModelI.getInstance().closeConnection();
		}
		return inventory;
	}
	
}
