<script setup lang="ts">
import { ref, onMounted } from 'vue'
import seatService from './services/seatService'
import type { Seat, Employee } from './types'

const seats = ref<Seat[]>([])
const employees = ref<Employee[]>([])
const selectedEmpId = ref<string>('')
const selectedFloorSeatSeq = ref<number | null>(null)
const currentFloorNo = ref<number>(1) // Default to floor 1
const message = ref<string>('')

// Fetch initial data
onMounted(async () => {
  console.log('App mounted, fetching data...')
  await fetchSeats()
  await fetchEmployees()
})

const fetchSeats = async () => {
  try {
    const response = await seatService.getSeatsByFloor(currentFloorNo.value)
    console.log('Fetch seats response:', response.data)
    seats.value = response.data
  } catch (err: any) {
    console.error('Error fetching seats:', err)
    message.value = 'Error fetching seats: ' + (err.response?.data || err.message)
  }
}

const fetchEmployees = async () => {
  try {
    const response = await seatService.getEmployees()
    employees.value = response.data
  } catch (err: any) {
    message.value = 'Error fetching employees'
  }
}

// Seat Interaction
const selectSeat = (seat: Seat) => {
  if (seat.status === 1) {
    // If seat is taken, select it to allow clearing
    selectedFloorSeatSeq.value = seat.floorSeatSeq
    message.value = `Seat ${seat.seatNo} is currently taken by ${seat.empName}.`
  } else {
    // If seat is available
    if (selectedEmpId.value) {
      selectedFloorSeatSeq.value = seat.floorSeatSeq
      message.value = `Seat ${seat.seatNo} selected for employee ${selectedEmpId.value}.`
    } else {
      message.value = 'Please select an employee first.'
    }
  }
}

const getSeatColor = (seat: Seat) => {
  if (seat.status === 1) return 'red' // Taken
  if (selectedFloorSeatSeq.value === seat.floorSeatSeq) return 'green' // Selected (Can be assigned)
  return 'grey' // Available
}

const submitAssignment = async () => {
  if (!selectedFloorSeatSeq.value || !selectedEmpId.value) {
    message.value = 'Please select both an employee and an available seat.'
    return
  }

  try {
    await seatService.assignSeat(selectedFloorSeatSeq.value, selectedEmpId.value)
    message.value = 'Seat assigned successfully!'
    resetSelection()
    await fetchSeats()
    await fetchEmployees() // Refresh employees to see updated assignments
  } catch (err: any) {
    message.value = err.response?.data || 'Failed to assign seat.'
  }
}

const clearSeat = async (floorSeatSeq: number) => {
  try {
    await seatService.clearSeat(floorSeatSeq)
    message.value = 'Seat cleared successfully!'
    resetSelection()
    await fetchSeats()
    await fetchEmployees()
  } catch (err: any) {
    message.value = 'Failed to clear seat.'
  }
}

const resetSelection = () => {
  selectedFloorSeatSeq.value = null
  selectedEmpId.value = ''
}
</script>

<template>
  <div class="container">
    <h1>HR Seat Arrangement System</h1>

    <div class="controls">
      <div class="field">
        <label>Select Floor: </label>
        <select v-model="currentFloorNo" @change="fetchSeats">
          <option :value="1">Floor 1</option>
          <option :value="2">Floor 2</option>
        </select>
      </div>

      <div class="field">
        <label>Select Employee: </label>
        <select v-model="selectedEmpId">
          <option value="">-- Choose Employee --</option>
          <option v-for="emp in employees" :key="emp.empId" :value="emp.empId">
            {{ emp.empId }} - {{ emp.name }}
          </option>
        </select>
      </div>

      <button @click="submitAssignment" :disabled="!selectedFloorSeatSeq || !selectedEmpId">Submit Assignment</button>
      <button @click="resetSelection" class="btn-secondary">Reset</button>
    </div>

    <div v-if="message" class="message-box">{{ message }}</div>

    <div class="floor-plan">
      <h3>Floor {{ currentFloorNo }} Map</h3>
      <div class="seat-grid">
        <div 
          v-for="seat in seats" 
          :key="seat.floorSeatSeq"
          :class="['seat', getSeatColor(seat)]"
          @click="selectSeat(seat)"
        >
          <div class="seat-code">{{ seat.seatNo }}</div>
          <div class="seat-info" v-if="seat.status === 1">
            {{ seat.empId }}
            <button class="clear-btn" @click.stop="clearSeat(seat.floorSeatSeq)">×</button>
          </div>
        </div>
      </div>
    </div>

    <div class="legend">
      <div class="legend-item"><span class="box grey"></span> Available</div>
      <div class="legend-item"><span class="box green"></span> Can be selected</div>
      <div class="legend-item"><span class="box red"></span> Taken</div>
    </div>
  </div>
</template>

<style scoped>
.container {
  max-width: 900px;
  margin: 0 auto;
  padding: 20px;
  font-family: Arial, sans-serif;
}

.controls {
  background: #f4f4f4;
  padding: 15px;
  border-radius: 8px;
  margin-bottom: 20px;
  display: flex;
  gap: 15px;
  align-items: center;
}

.field {
  display: flex;
  flex-direction: column;
}

.floor-plan {
  border: 2px solid #ddd;
  padding: 20px;
  border-radius: 8px;
  background: white;
}

.seat-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(80px, 1fr));
  gap: 15px;
  margin-top: 20px;
}

.seat {
  width: 80px;
  height: 80px;
  border-radius: 4px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  color: white;
  font-weight: bold;
  position: relative;
  transition: transform 0.2s;
}

.seat:hover {
  transform: scale(1.05);
}

.seat-code {
  font-size: 1.2em;
}

.seat-info {
  font-size: 0.7em;
  margin-top: 5px;
}

.grey { background-color: #9e9e9e; }
.red { background-color: #f44336; }
.green { background-color: #4caf50; }

.legend {
  display: flex;
  gap: 20px;
  margin-top: 20px;
}

.legend-item {
  display: flex;
  align-items: center;
  gap: 8px;
}

.box {
  width: 20px;
  height: 20px;
  border-radius: 2px;
}

.message-box {
  padding: 10px;
  margin-bottom: 10px;
  background: #e3f2fd;
  border-left: 5px solid #2196f3;
}

.clear-btn {
  position: absolute;
  top: 2px;
  right: 2px;
  background: rgba(0,0,0,0.3);
  border: none;
  color: white;
  border-radius: 50%;
  width: 18px;
  height: 18px;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 12px;
}

.clear-btn:hover {
  background: rgba(0,0,0,0.6);
}

button {
  padding: 8px 16px;
  background-color: #2196f3;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

button:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}

.btn-secondary {
  background-color: #757575;
}
</style>
