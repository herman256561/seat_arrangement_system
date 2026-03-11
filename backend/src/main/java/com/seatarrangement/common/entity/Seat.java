package com.seatarrangement.common.entity;

public class Seat {
    private Integer floorSeatSeq;
    private Integer floorNo;
    private String seatNo;
    private String empId;
    private String empName;
    private Integer status; // 0: Available, 1: Taken

    public Seat() {}

    public Seat(Integer floorSeatSeq, Integer floorNo, String seatNo, String empId, String empName, Integer status) {
        this.floorSeatSeq = floorSeatSeq;
        this.floorNo = floorNo;
        this.seatNo = seatNo;
        this.empId = empId;
        this.empName = empName;
        this.status = status;
    }

    public Integer getFloorSeatSeq() {
        return floorSeatSeq;
    }

    public void setFloorSeatSeq(Integer floorSeatSeq) {
        this.floorSeatSeq = floorSeatSeq;
    }

    public Integer getFloorNo() {
        return floorNo;
    }

    public void setFloorNo(Integer floorNo) {
        this.floorNo = floorNo;
    }

    public String getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(String seatNo) {
        this.seatNo = seatNo;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
