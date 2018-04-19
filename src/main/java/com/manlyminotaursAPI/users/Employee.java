package com.manlyminotaursAPI.users;


public class Employee {
    String firstName;
    String middleName;
    String lastName;
    String employeeID;
    String employeeType;


    public Employee(String firstName, String middleName, String lastName, String employeeID, String employeeType){
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
