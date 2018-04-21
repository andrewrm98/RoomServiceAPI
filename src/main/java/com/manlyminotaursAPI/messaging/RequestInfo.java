package com.manlyminotaursAPI.messaging;

import javafx.collections.ObservableList;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestInfo that = (RequestInfo) o;
        return Objects.equals(requestID, that.requestID) &&
                Objects.equals(room, that.room) &&
                Objects.equals(employee, that.employee);
    }

    @Override
    public int hashCode() {

        return Objects.hash(requestID, room, employee);
    }
}