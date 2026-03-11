package com.seatarrangement.presentation;

import com.seatarrangement.common.entity.Seat;
import com.seatarrangement.common.entity.Employee;
import com.seatarrangement.business.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class SeatController {

    @Autowired
    private SeatService seatService;

    @GetMapping("/floors/{floorNo}/seats")
    public ResponseEntity<List<Seat>> getSeats(@PathVariable Integer floorNo) {
        return ResponseEntity.ok(seatService.getSeatsByFloor(floorNo));
    }

    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getEmployees() {
        return ResponseEntity.ok(seatService.getAllEmployees());
    }

    @PostMapping("/seats/assign")
    public ResponseEntity<String> assignSeat(@RequestBody Map<String, Object> payload) {
        try {
            Integer floorSeatSeq = (Integer) payload.get("floorSeatSeq");
            String empId = (String) payload.get("empId");
            seatService.assignSeat(floorSeatSeq, empId);
            return ResponseEntity.ok("Seat assigned successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error assigning seat: " + e.getMessage());
        }
    }

    @PostMapping("/seats/clear")
    public ResponseEntity<String> clearSeat(@RequestBody Map<String, Object> payload) {
        try {
            Integer floorSeatSeq = (Integer) payload.get("floorSeatSeq");
            seatService.clearSeat(floorSeatSeq);
            return ResponseEntity.ok("Seat cleared successfully.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error clearing seat: " + e.getMessage());
        }
    }
}
