<template>
  <main class="z-main">
    <slot></slot>
  </main>
</template>

<style scoped>
.z-main {
  flex: 1;
  padding: 1rem;
  background-color: #fff;
  color: #374151;
  overflow: auto;
}
</style>