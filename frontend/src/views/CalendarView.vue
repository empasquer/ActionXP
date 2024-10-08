<script setup lang="ts">
import { ref } from 'vue'
import DayCalendar from '@/components/DayCalendar.vue'
import MonthCalendar from '@/components/MonthCalendar.vue'
import CreateActivityForm from '@/components/CreateActivityForm.vue'
import TestHeader from '.././components/TestHeader.vue'
import LoginForm from '@/components/LoginForm.vue'

//Placeholder for bookings
interface Event {
  title: string
  startTime: string
  endTime: string
}

//Define refs for the selected day and events
const selectedDay = ref<Date | null>(null)
const selectedDayEvents = ref<Event[]>([])
const isDayViewVisible = ref(false)

//Methods to show and close the day view
function showDayView(day: Date) {
  selectedDay.value = day
  selectedDayEvents.value = [] // Fetch events for the selected day if needed
  isDayViewVisible.value = true
  ;(document.querySelector('.month-view') as HTMLElement).classList.add('blurred')
}

function closeDayView() {
  isDayViewVisible.value = false
  ;(document.querySelector('.month-view') as HTMLElement).classList.remove('blurred')
}

//Add-activities things:
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
  <!-- <LoginForm/> -->
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
