<template>
  <div class="container">
    <slot />
  </div>
</template>


<script setup lang="ts">

</script>

<style scoped>
.container {
  max-width: 1150px;
  margin: 0 auto;
  padding: 2rem;
  background-color: var(--bgColor-default, #ffffff);
}

@media (max-width: 768px) {
  .container {
    padding: 1rem;
    padding-top: 4rem;
  }
}
</style>
