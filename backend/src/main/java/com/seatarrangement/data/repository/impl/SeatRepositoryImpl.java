package com.seatarrangement.data.repository.impl;

import com.seatarrangement.common.entity.Seat;
import com.seatarrangement.common.entity.Employee;
import com.seatarrangement.data.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class SeatRepositoryImpl implements SeatRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Seat> getSeatsByFloor(Integer floorId) {
        String sql = "{CALL sp_get_seats_by_floor(?)}";
        return jdbcTemplate.query(sql, new SeatRowMapper(), floorId);
    }

    @Override
    public void assignSeat(Integer floorSeatSeq, String empId) {
        String sql = "{CALL sp_assign_seat(?, ?)}";
        jdbcTemplate.update(sql, floorSeatSeq, empId);
    }

    @Override
    public void clearSeat(Integer floorSeatSeq) {
        String sql = "{CALL sp_clear_seat(?)}";
        jdbcTemplate.update(sql, floorSeatSeq);
    }

    @Override
    public List<Employee> findAllEmployees() {
        String sql = "SELECT emp_id, name, email, floor_seat_seq FROM Employee";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Employee(
                rs.getString("emp_id"),
                rs.getString("name"),
                rs.getString("email"),
                (Integer) rs.getObject("floor_seat_seq")
        ));
    }

    private static class SeatRowMapper implements RowMapper<Seat> {
        @Override
        public Seat mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Seat(
                    rs.getInt("floor_seat_seq"),
                    rs.getInt("floor_no"),
                    rs.getString("seat_no"),
                    rs.getString("emp_id"),
                    rs.getString("emp_name"),
                    rs.getInt("status")
            );
        }
    }
}
