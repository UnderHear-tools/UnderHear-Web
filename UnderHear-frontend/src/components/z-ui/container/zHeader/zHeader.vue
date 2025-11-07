<template>
  <header class="z-header" :style="headerStyle">
    <slot></slot>
  </header>
</template>

<script setup lang="ts">
import { computed } from 'vue'

interface Props {
  height?: string
}

const props = withDefaults(defineProps<Props>(), {
  height: '60px'
})

const headerStyle = computed(() => {
  return {
    height: props.height
  }
})
</script>

<style scoped>
.z-header {
  display: flex;
  align-items: center;
  padding: 0 1rem;
  background-color: #f8f9fa;
  border-bottom: 1px solid #e5e7eb;
  flex-shrink: 0;
  font-weight: 500;
  color: #374151;
}
</style>