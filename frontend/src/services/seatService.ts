import axios from 'axios';
import type { Seat, Employee } from '../types';

const API_BASE_URL = 'http://localhost:8080/api';

const apiClient = axios.create({
    baseURL: API_BASE_URL,
    headers: {
        'Content-Type': 'application/json',
    },
});

export default {
    getSeatsByFloor(floorNo: number) {
        return apiClient.get<Seat[]>(`/floors/${floorNo}/seats`);
    },
    getEmployees() {
        return apiClient.get<Employee[]>('/employees');
    },
    assignSeat(floorSeatSeq: number, empId: string) {
        return apiClient.post('/seats/assign', { floorSeatSeq, empId });
    },
    clearSeat(floorSeatSeq: number) {
        return apiClient.post('/seats/clear', { floorSeatSeq });
    },
};
