<template>
  <div class="index-page">
    <Hero />
  </div>
  <div class="second-page">
    
  </div>
</template>

<script setup lang="ts">
import Hero from '../components/Hero.vue'
</script>

<style scoped>
.index-page {
  height: calc(100vh - var(--header-height));
}

.second-page {
  height: calc(100vh - var(--header-height));
}

@media (max-width: 768px) {
  .index-page {
    height: 100vh;
  }
  .second-page {
    height: 100vh;
  }
}
</style>
