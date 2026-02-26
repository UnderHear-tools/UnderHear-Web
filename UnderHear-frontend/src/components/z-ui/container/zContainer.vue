<template>
  <div class="z-container">
    <slot></slot>
  </div>
</template>


<script setup lang="ts">

</script>

<style scoped>
.z-container {
  max-width: 1150px;
  margin: 0 auto;
  padding: 2rem;
  background-color: var(--bgColor-default, #ffffff);
}

@media (max-width: 768px) {
  .z-container {
    padding: 1rem;
    padding-top: 4rem;
  }
}
</style>
