<script setup lang="ts">
import { ref, computed } from 'vue'
import LeftArrowIcon from '@/components/icons/LeftArrowIcon.vue'
import RightArrowIcon from '@/components/icons/RightArrowIcon.vue'

const emit = defineEmits(['day-selected'])

const currentDate = ref(new Date())
const currentMonth = ref(currentDate.value.getMonth())
const currentYear = ref(currentDate.value.getFullYear())

const currentMonthName = computed(() => {
  return new Date(currentYear.value, currentMonth.value).toLocaleString('default', {
    month: 'long'
  })
})

const dayNames = ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']

const daysInMonth = computed(() => {
  const firstDayOfMonth = new Date(currentYear.value, currentMonth.value, 1)
  const lastDayOfMonth = new Date(currentYear.value, currentMonth.value + 1, 0)

  const firstDayIndex = (firstDayOfMonth.getDay() + 6) % 7

  const days = []

  for (let i = 0; i < firstDayIndex; i++) {
    days.push(null)
  }

  for (let i = 1; i <= lastDayOfMonth.getDate(); i++) {
    days.push(new Date(currentYear.value, currentMonth.value, i))
  }

  return days
})

const weekNumbers = computed(() => {
  const weeks = []
  let currentWeekNumber = getWeekNumber(
    daysInMonth.value[daysInMonth.value.findIndex((day) => day !== null)]
  )

  for (let i = 0; i < Math.ceil(daysInMonth.value.length / 7); i++) {
    weeks.push(currentWeekNumber++)
  }

  return weeks
})

function getWeekNumber(date: Date | null): number {
  if (!date) return 0
  const startDate = new Date(date.getFullYear(), 0, 1)
  const daysDifference = Math.floor((date.getTime() - startDate.getTime()) / (24 * 60 * 60 * 1000))
  return Math.ceil((daysDifference + startDate.getDay() + 1) / 7)
}

function isToday(day: Date | null): boolean {
  if (!day) return false

  const today = new Date()
  return (
    today.getDate() === day.getDate() &&
    today.getMonth() === day.getMonth() &&
    today.getFullYear() === day.getFullYear()
  )
}

function prevMonth() {
  currentMonth.value--
  if (currentMonth.value < 0) {
    currentMonth.value = 11
    currentYear.value--
  }
}

function nextMonth() {
  currentMonth.value++
  if (currentMonth.value > 11) {
    currentMonth.value = 0
    currentYear.value++
  }
}

function selectDay(day: Date) {
  emit('day-selected', day)
}
</script>

<template>
  <div class="month-view">
    <div class="calender-header">
      <LeftArrowIcon src="path/to/left-arrow.svg" @click="prevMonth" />
      <h2>{{ currentMonthName }} {{ currentYear }}</h2>
      <RightArrowIcon src="path/to/right-arrow.svg" @click="nextMonth" />
    </div>

    <div class="calender-day-names">
      <div v-for="(day, index) in dayNames" :key="index" class="calender-day-name">{{ day }}</div>
    </div>

    <div class="calender-grid">
      <div v-for="(weekNumber, weekIndex) in weekNumbers" :key="weekIndex" class="week-row">
        <div class="week-number">{{ weekNumber }}</div>

        <div
          v-for="(day, dayIndex) in daysInMonth.slice(weekIndex * 7, (weekIndex + 1) * 7)"
          :key="dayIndex"
          class="calender-day"
          :class="{ 'current-day': isToday(day), 'empty-day': day === null }"
          @click="day && selectDay(day)"
        >
          {{ day ? day.getDate() : '' }}
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.month-view {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  width: 90vw;
  height: 90vh;
  padding: 20px;
  box-sizing: border-box;
}

.calender-header {
  display: flex;
  justify-content: space-between;
  width: 80%;
  margin-bottom: 20px;
}

.calender-day-names {
  display: grid;
  grid-template-columns: repeat(7, 1fr);
  width: 75%;
  text-align: center;
  font-weight: bold;
  margin-bottom: 20px;
  margin-left: 50px;
}

.calender-grid {
  display: grid;
  grid-template-columns: auto repeat(7, 1fr);
  width: 80%;
  gap: 10px;
}

.week-row {
  display: contents;
}

.week-number {
  display: flex;
  align-items: center;
  justify-content: center;
  font-weight: bold;
  width: 40px;
}

.calender-day {
  width: 100%;
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: center;
  border: 1px solid #ccc;
  cursor: pointer;
}

.empty-day {
  border: none;
}

.current-day {
  border: 2px solid #0056b3;
}

button {
  background: none;
  color: #f0f0f0;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: xx-large;
}

button:hover {
  color: #0056b3;
}
</style>
