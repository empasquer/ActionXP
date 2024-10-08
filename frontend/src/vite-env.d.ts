/// <reference types="vite/client" />

interface ImportMeta {
  env: {
    VITE_API_URL: string // Add your custom environment variables here
    [key: string]: string | undefined // Allow for any additional environment variables
  }
}
