import './assets/main.css'

import { createApp } from 'vue'
import App from './App.vue'
import router from './router' // Import your router

// Create the Vue app and mount it with the router
createApp(App)
  .use(router) // Use the router
  .mount('#app')
