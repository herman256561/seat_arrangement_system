package com.seatarrangement.business.service;

import com.seatarrangement.common.entity.Seat;
import com.seatarrangement.common.entity.Employee;
import java.util.List;

public interface SeatService {
    List<Seat> getSeatsByFloor(Integer floorNo);
    void assignSeat(Integer floorSeatSeq, String empId);
    void clearSeat(Integer floorSeatSeq);
    List<Employee> getAllEmployees();
}
