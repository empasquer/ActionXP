<script setup lang="ts">
import type { CSSProperties } from 'vue';
import type {Booking} from "@/models/Booking";
import BookingDetails from '@/components/BookingDetails.vue';
import {ref} from "vue";

const props = defineProps<{
  activityName: string;
  bookings: Booking[];
  activityColor: string;
}>();

const selectedBooking = ref<Booking | null>(null);
const isModalVisible = ref(false);

function isWeekend(date: Date): boolean {
  const dayOfWeek = date.getDay();
  return dayOfWeek === 0 || dayOfWeek === 6; // Sunday = 0, Saturday = 6
}

function getEventStyle(booking: Booking): CSSProperties {
  //So TS can read it
  const startDateTime = new Date(`${booking.date}T${booking.startTime}`);
  const endDateTime = new Date(`${booking.date}T${booking.endTime}`);

  const startHour = startDateTime.getHours();
  const startMinutes = startDateTime.getMinutes();
  const endHour = endDateTime.getHours();
  const endMinutes = endDateTime.getMinutes();

  //Check if booking date is weekend
  const isBookingOnWeekend = isWeekend(startDateTime);
  const openingHour = isBookingOnWeekend ? 12 : 10;
  const closingHour = 20;
  const totalHours = closingHour - openingHour;

  const top = ((startHour - openingHour) * 60 + startMinutes) * 100 / (totalHours * 60);
  const height = ((endHour - startHour) * 60 + (endMinutes - startMinutes)) * 100 / (totalHours * 60);

  return {
    top: `${top}%`,
    height: `${height}%`,
    backgroundColor: props.activityColor || 'darkgray',
    position: 'absolute',
    width: '100%',
  };
}

function formatTime(date: string, time: string) {
  const dateTimeString = `${date}T${time}`;
  return new Date(dateTimeString).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
}

function openBookingDetails(booking: Booking) {
  selectedBooking.value = booking;
  isModalVisible.value = true;
}

function closeModal() {
  isModalVisible.value = false;
}
</script>

<template>
  <div class="activity-column">
    <div class="event-grid">
      <div
          v-for="booking in bookings"
          :key="booking.id"
          :style="getEventStyle(booking)"
          class="event-block"
          @click="openBookingDetails(booking)"
      >
        {{ formatTime(booking.date, booking.startTime) }} -
        {{ formatTime(booking.date, booking.endTime) }}
        <br />
        {{ booking.customerName }}
      </div>
    </div>

    <BookingDetails
        :isVisible="isModalVisible"
        :booking="selectedBooking"
        @close="closeModal"
    />
  </div>
</template>

<style scoped>
.activity-column {
  flex: 1;
  position: relative;
  margin-right: 20px;
}

.event-grid {
  position: relative;
  height: 80%;
}

.event-block {
  background-color: rgba(255, 182, 193, 0.7);
  border-radius: 5px;
  padding: 5px;
  color: white;
  font-size: 12px;
  font-weight: bold;
}
</style>