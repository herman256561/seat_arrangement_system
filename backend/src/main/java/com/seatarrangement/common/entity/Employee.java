package com.seatarrangement.common.entity;

public class Employee {
    private String empId;
    private String name;
    private String email;
    private Integer floorSeatSeq;

    public Employee() {}

    public Employee(String empId, String name, String email, Integer floorSeatSeq) {
        this.empId = empId;
        this.name = name;
        this.email = email;
        this.floorSeatSeq = floorSeatSeq;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getFloorSeatSeq() {
        return floorSeatSeq;
    }

    public void setFloorSeatSeq(Integer floorSeatSeq) {
        this.floorSeatSeq = floorSeatSeq;
    }
}
