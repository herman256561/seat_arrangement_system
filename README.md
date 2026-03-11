# HR Seat Arrangement System

**Demo Video:** [https://youtu.be/85ZfzgFBW1k](https://youtu.be/85ZfzgFBW1k)

This project is a full-stack seat management system designed for HR departments to arrange employee seating across multiple floors, utilizing a modern 3-layer architecture.

## 🚀 How it Meets Requirements

### 1. Required Functionalities

- **Displaying Seats:**
  - Fetches seat data from the `SeatingChart` table via REST API.
  - **Color Coding:** Grey (Available), Green (Selected/Can be assigned), and Red (Taken).
- **Seats Arrangement:**
  - **One Seat Rule:** The system ensures each employee only takes one seat by updating the `floor_seat_seq` in the `Employee` table.
  - **Employee Selection:** A dropdown list displays employees with their **5-digit IDs** and names.
  - **Interactive Selection:** Available (Grey) seats turn Green once an employee is selected from the dropdown.
  - **Clearing Seats:** Taken (Red) seats can be cleared via a "×" button, resetting them to Available (Grey).
  - **Submission:** The "Submit Assignment" button persists the arrangement to the database.

### 2. System Structure

- **3-Layer Architecture:**
  1. **Web Server:** Vue.js 3 Frontend.
  2. **Application Server:** Spring Boot 3 Backend.
  3. **Relational Database:** MySQL.
- **Backend 4-Layer Design:**
  - **Presentation Layer:** `SeatController` (RESTful API).
  - **Business Layer:** `SeatService` (Business logic & Sanitization).
  - **Data Layer:** `SeatRepository` (JDBC & Stored Procedures).
  - **Common Layer:** Entity models (`Seat`, `Employee`).

### 3. Technical Implementation & Security

- **Frontend:** Vue.js 3 (Composition API) with TypeScript and Vite.
- **Backend:** Spring Boot 3.x.
- **Database Schema:**
  - **Table 1 (Employee):** `emp_id`, `name`, `email`, `floor_seat_seq`.
  - **Table 2 (SeatingChart):** `floor_seat_seq`, `floor_no`, `seat_no`.
- **Data Access:** All operations are performed via **Stored Procedures**:
  - `sp_get_seats_by_floor`: Retrieves floor layouts and assignments.
  - `sp_assign_seat`: Atomic transaction to assign a seat.
  - `sp_clear_seat`: Atomic transaction to clear a seat.
- **Transactions:** Implemented within Stored Procedures and enforced with Spring's `@Transactional`.
- **Database Scripts:** Located in the `/DB` folder (`DDL.sql` and `DML.sql`).
- **Security:**
  - **SQL Injection:** Defended by using **Parameterized Stored Procedures** (inputs are treated as data, never code).
  - **XSS Attack:** Defended via backend sanitization (`HtmlUtils.htmlEscape`) and Vue's automatic HTML escaping.

---

## 🛠 Setup & Testing

### 1. Database

- **Location:** Scripts are in `/DB`.
- **Port:** Default configured for `3307`.
- **Instructions:** Execute `DDL.sql` followed by `DML.sql`.

### 2. Backend

```bash
cd backend
mvn spring-boot:run
```

- API Base: `http://localhost:8080/api`

### 3. Frontend

```bash
cd frontend
npm install
npm run dev
```

- Access: `http://localhost:5173`

## 🎨 UI Logic Summary

- **Grey → Green:** Employee selected + Available seat clicked.
- **Green → Red:** "Submit" button clicked (Success).
- **Red → Grey:** "Clear" (×) button clicked on a taken seat.
