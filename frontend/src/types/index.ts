export interface Employee {
    empId: string;
    name: string;
    email?: string;
    floorSeatSeq?: number | null;
}

export interface Seat {
    floorSeatSeq: number;
    floorNo: number;
    seatNo: string;
    empId: string | null;
    empName?: string;
    status: number; // 0: Available, 1: Taken
}
