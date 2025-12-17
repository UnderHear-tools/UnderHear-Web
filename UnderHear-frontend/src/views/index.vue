<template>
  <div class="index-container">
    <h1>Welcome to UnderHear Frontend</h1>
    <p>This is the main index view of the UnderHear frontend application.</p>
  </div>
</template>

<script setup lang="ts">
</script>

<style scoped>
.index-container {
  max-width: 1150px;
  margin: 0 auto;
  padding: 2rem;
  background: #fff;
}

@media (max-width: 768px) {
  .index-container {
    padding: 1rem;
  }
}
</style>