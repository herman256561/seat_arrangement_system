package com.seatarrangement.business.service.impl;

import com.seatarrangement.common.entity.Seat;
import com.seatarrangement.common.entity.Employee;
import com.seatarrangement.business.service.SeatService;
import com.seatarrangement.data.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

@Service
public class SeatServiceImpl implements SeatService {

    @Autowired
    private SeatRepository seatRepository;

    @Override
    public List<Seat> getSeatsByFloor(Integer floorNo) {
        return seatRepository.getSeatsByFloor(floorNo);
    }

    @Override
    @Transactional
    public void assignSeat(Integer floorSeatSeq, String empId) {
        // XSS Defense: Sanitize input
        String sanitizedEmpId = HtmlUtils.htmlEscape(empId);
        
        // Business logic: Ensure employee ID is 5 digits
        if (sanitizedEmpId == null || !sanitizedEmpId.matches("\\d{5}")) {
            throw new IllegalArgumentException("Employee ID must be exactly 5 digits.");
        }
        
        seatRepository.assignSeat(floorSeatSeq, sanitizedEmpId);
    }

    @Override
    @Transactional
    public void clearSeat(Integer floorSeatSeq) {
        seatRepository.clearSeat(floorSeatSeq);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return seatRepository.findAllEmployees();
    }
}
