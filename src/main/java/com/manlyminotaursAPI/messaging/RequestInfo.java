package com.manlyminotaursAPI.messaging;

import javafx.collections.ObservableList;

public class RequestInfo {
    String requestID;
    String room;
    String employee;
    ObservableList<InventoryItem> items;

    public RequestInfo(String requestID, String room, String employee, ObservableList<InventoryItem> items) {
        this.requestID = requestID;
        this.room = room;
        this.employee = employee;
        this.items = items;
    }

    public String getRoom() {
        return room;
    }

    public String getEmployee() {
        return employee;
    }

    public ObservableList<InventoryItem> getItems() {
        return items;
    }

    public String getRequestID() {
        return requestID;
    }

    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public void setItems(ObservableList<InventoryItem> items) {
        this.items = items;
    }
}