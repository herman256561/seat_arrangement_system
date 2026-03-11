-- Updated DDL for Seat Arrangement System
CREATE DATABASE IF NOT EXISTS seat_arrangement;
USE seat_arrangement;

-- Table 2: SeatingChart (Define first due to Foreign Key)
CREATE TABLE IF NOT EXISTS SeatingChart (
    floor_seat_seq INT AUTO_INCREMENT PRIMARY KEY,
    floor_no INT NOT NULL,
    seat_no VARCHAR(20) NOT NULL,
    UNIQUE(floor_no, seat_no)
);

-- Table 1: Employee
CREATE TABLE IF NOT EXISTS Employee (
    emp_id CHAR(5) PRIMARY KEY, -- 5-digit employee ID
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    floor_seat_seq INT DEFAULT NULL,
    FOREIGN KEY (floor_seat_seq) REFERENCES SeatingChart(floor_seat_seq)
);

-- Stored Procedures

DELIMITER //

-- 1. Get seats by floor
DROP PROCEDURE IF EXISTS sp_get_seats_by_floor //
CREATE PROCEDURE sp_get_seats_by_floor(IN p_floor_no INT)
BEGIN
    SELECT 
        s.floor_seat_seq, 
        s.floor_no, 
        s.seat_no, 
        e.emp_id, 
        e.name AS emp_name,
        CASE WHEN e.emp_id IS NOT NULL THEN 1 ELSE 0 END AS status
    FROM SeatingChart s
    LEFT JOIN Employee e ON s.floor_seat_seq = e.floor_seat_seq
    WHERE s.floor_no = p_floor_no;
END //

-- 2. Assign seat
DROP PROCEDURE IF EXISTS sp_assign_seat //
CREATE PROCEDURE sp_assign_seat(
    IN p_floor_seat_seq INT,
    IN p_emp_id CHAR(5)
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        RESIGNAL;
    END;

    START TRANSACTION;
        UPDATE Employee SET floor_seat_seq = NULL WHERE floor_seat_seq = p_floor_seat_seq;
        UPDATE Employee SET floor_seat_seq = p_floor_seat_seq WHERE emp_id = p_emp_id;
    COMMIT;
END //

-- 3. Clear seat
DROP PROCEDURE IF EXISTS sp_clear_seat //
CREATE PROCEDURE sp_clear_seat(
    IN p_floor_seat_seq INT
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        RESIGNAL;
    END;

    START TRANSACTION;
        UPDATE Employee SET floor_seat_seq = NULL WHERE floor_seat_seq = p_floor_seat_seq;
    COMMIT;
END //

DELIMITER ;
