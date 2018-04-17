package com.manlyminotaurs.messaging;

public class Inventory {

	String ID;
	String type;
	int quantity;
	String location;

	public Inventory(String ID, String type, int quantity, String location) {
		this.type = type;
		this.quantity = quantity;
		this.location = location;
		this.ID = ID;
	}

	public String getID() {
		return ID;
	}

	public void setID(String ID) {
		this.ID = ID;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
}
