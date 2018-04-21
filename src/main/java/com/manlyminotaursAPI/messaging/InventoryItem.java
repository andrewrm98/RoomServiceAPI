package com.manlyminotaursAPI.messaging;

public class InventoryItem {

	String ID;
	String itemName;
	int quantity;
	String requestID;

	public InventoryItem(String ID, String itemName, int quantity, String requestID) {
		this.ID = ID;
		this.itemName = itemName;
		this.quantity = quantity;
		this.requestID = requestID;
	}

	public String getID() {
		return ID;
	}

	public void setID(String ID) {
		this.ID = ID;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getRequestID() {
		return requestID;
	}

	public void setRequestID(String requestID) {
		this.requestID = requestID;
	}
}
