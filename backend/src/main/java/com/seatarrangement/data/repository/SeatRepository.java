package com.seatarrangement.data.repository;

import com.seatarrangement.common.entity.Seat;
import com.seatarrangement.common.entity.Employee;
import java.util.List;

public interface SeatRepository {
    List<Seat> getSeatsByFloor(Integer floorNo);
    void assignSeat(Integer floorSeatSeq, String empId);
    void clearSeat(Integer floorSeatSeq);
    List<Employee> findAllEmployees();
}
