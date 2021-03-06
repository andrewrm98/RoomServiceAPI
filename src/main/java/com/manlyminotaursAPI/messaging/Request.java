package com.manlyminotaursAPI.messaging;

import com.manlyminotaursAPI.databases.DataModelIAPI;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;
import java.util.Objects;

public class Request {
	DataModelIAPI dataModelIAPI = DataModelIAPI.getInstance();
    String requestID;
    String requestType;
    int priority;
    Boolean isComplete;
    Boolean adminConfirm;
    LocalDateTime startTime;
    LocalDateTime endTime;
    RequestInfo requestInfo;

    public Request(String requestID, String requestType, int priority, Boolean isComplete, Boolean adminConfirm, LocalDateTime startTime, LocalDateTime endTime, String room, String employee, ObservableList<InventoryItem> items) {
        this.requestID = requestID;
        this.requestType = requestType;
        this.priority = priority;
        this.isComplete = isComplete;
        this.adminConfirm = adminConfirm;
        this.startTime = startTime;
        this.endTime = endTime;
        this.requestInfo = new RequestInfo(requestID, room, employee, items);
    }

    public String getRequestID() {
        return requestID;
    }

    public String getRequestType() {
        return requestType;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Boolean getComplete() {
        return isComplete;
    }

    public void setComplete(Boolean complete) {
        isComplete = complete;
    }

    public Boolean getAdminConfirm() {
        return adminConfirm;
    }

    public void setAdminConfirm(Boolean adminConfirm) {
        this.adminConfirm = adminConfirm;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public RequestInfo getRequestInfo() {
        return requestInfo;
    }

    public void setRequestInfo(RequestInfo requestInfo) {
        this.requestInfo = requestInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return priority == request.priority &&
                Objects.equals(requestID, request.requestID) &&
                Objects.equals(requestType, request.requestType) &&
                Objects.equals(isComplete, request.isComplete) &&
                Objects.equals(adminConfirm, request.adminConfirm);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestID, requestType, priority, isComplete, adminConfirm);
    }
}
