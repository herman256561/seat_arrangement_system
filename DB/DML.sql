-- Updated DML for testing the new Seat Arrangement System
USE seat_arrangement;

-- Clear previous data
SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE Employee;
TRUNCATE TABLE SeatingChart;
SET FOREIGN_KEY_CHECKS = 1;

-- Table 2: SeatingChart (Floor 1 & 2)
INSERT INTO SeatingChart (floor_no, seat_no) VALUES (1, '1A'), (1, '1B'), (1, '1C'), (1, '1D');
INSERT INTO SeatingChart (floor_no, seat_no) VALUES (2, '2A'), (2, '2B'), (2, '2C'), (2, '2D');

-- Table 1: Employee
INSERT INTO Employee (emp_id, name, email, floor_seat_seq) VALUES ('10001', 'John Doe', 'john@example.com', 1); -- Taken Floor 1 (1A)
INSERT INTO Employee (emp_id, name, email, floor_seat_seq) VALUES ('10002', 'Jane Smith', 'jane@example.com', 2); -- Taken Floor 1 (1B)
INSERT INTO Employee (emp_id, name, email, floor_seat_seq) VALUES ('10003', 'Alice Johnson', 'alice@example.com', 8); -- Taken Floor 2 (2D)
INSERT INTO Employee (emp_id, name, email, floor_seat_seq) VALUES ('10004', 'Bob Wilson', 'bob@example.com', NULL);
INSERT INTO Employee (emp_id, name, email, floor_seat_seq) VALUES ('10005', 'Charlie Brown', 'charlie@example.com', NULL);
