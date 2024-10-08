<script setup lang="ts">
import { ref } from 'vue'
import DayCalendar from '@/components/DayCalendar.vue'
import MonthCalendar from '@/components/MonthCalendar.vue'
import CreateActivityForm from '@/components/CreateActivityForm.vue'
import TestHeader from '.././components/TestHeader.vue'
import axios from 'axios'

// Placeholder for bookings
interface Event {
  title: string
  startTime: string
  endTime: string
}

// Define refs for the selected day and events
const selectedDay = ref<Date | null>(null)
const selectedDayEvents = ref<Event[]>([])
const isDayViewVisible = ref(false)

// Methods to show and close the day view
async function showDayView(day: Date | null) {
  if (!day) {
    console.error('Invalid date passed to showDayView.')
    return
  }

  selectedDay.value = day
  isDayViewVisible.value = true

  // Add a check to ensure this element exists before trying to manipulate it
  const monthViewElement = document.querySelector('.month-view') as HTMLElement
  if (monthViewElement) {
    monthViewElement.classList.add('blurred')
  }

  // Fetch events for the selected day
  try {
    const formattedDate = `${day.getFullYear()}-${String(day.getMonth() + 1).padStart(2, '0')}-${String(day.getDate()).padStart(2, '0')}`
    console.log('Fetching events for:', formattedDate)
    const response = await axios.get(`http://localhost:8080/api/bookings/day`, {
      params: { date: formattedDate }
    })
    selectedDayEvents.value = response.data // Assuming the response is an array of Event objects
  } catch (error) {
    console.error('Error fetching events:', error)
  }
}

function closeDayView() {
  isDayViewVisible.value = false
  const monthViewElement = document.querySelector('.month-view') as HTMLElement
  if (monthViewElement) {
    monthViewElement.classList.remove('blurred')
  }
}

// Add-activities things:
// Reactive reference for form visibility
const formVisible = ref(false) // Initially set to false

// Handle dropdown selection
const handleDropdownSelect = (value: string) => {
  if (value === 'add-activity') {
    formVisible.value = true
  }
}

// Close the form
const closeForm = () => {
  formVisible.value = false
}
</script>

<template>
  <header>
    <TestHeader @dropdown-select="handleDropdownSelect" />
  </header>
  <div class="Activity-form">
    <CreateActivityForm v-if="formVisible" @exitForm="closeForm" />
  </div>

  <div class="calendar-container" :class="{ blurred: formVisible }">
    <div :class="{ blurred: isDayViewVisible }">
      <MonthCalendar @day-selected="showDayView" />
    </div>
    <DayCalendar
      v-if="isDayViewVisible"
      :day="selectedDay"
      :events="selectedDayEvents"
      :isVisible="isDayViewVisible"
      @close-day-view="closeDayView"
    />
    <div v-if="isDayViewVisible" class="overlay" @click="closeDayView"></div>
  </div>
</template>

<style scoped>
.calendar-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  position: relative;
  width: fit-content;
  margin: 0 auto;
  margin-top: 20px;
}

.blurred {
  filter: blur(5px);
}

.overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  z-index: 9;
}

.Activity-form {
  display: flex;
  justify-content: center;
  align-items: center;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  z-index: 200;
}

header {
  width: 100vh;
  height: auto;
}

template {
  display: flex;
  flex-direction: column;
}
</style>
