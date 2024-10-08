import { RouterLink } from 'vue-router';
<script setup lang="ts">
</script>

<template>
  <!-- <header></header>

  <main>
    <TodoList endpoint="http://127.0.0.1:8080/todo" />
  </main> -->

    <header>
    <div class="wrapper">
      <nav>
        <RouterLink to="/add-activities">test</RouterLink>
        <RouterLink to="/test-view">Login</RouterLink>
      </nav>
    </div>
  </header>

  <RouterView />
</template>

<style scoped></style>
