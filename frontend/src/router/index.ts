import { createRouter, createWebHistory } from 'vue-router'
import HomePageView from '@/views/HomePageView.vue'
import TestDatabaseView from '@/views/TestDatabaseView.vue'
import Testview from '@/views/test-view.vue'
import CalendarView from '@/views/CalendarView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomePageView
    },
    {
      path: '/calendar',
      name: 'calendar',
      component: CalendarView
    },
    {
      path: '/test-database',
      name: 'database',
      component: TestDatabaseView
    },
    {
      path: '/add-activity',
      name: 'add-activity',
      component: Testview
    }
  ]
})

export default router
