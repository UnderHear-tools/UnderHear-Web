<template>
  <footer class="z-footer" :style="footerStyle">
    <slot></slot>
  </footer>
</template>

<script setup lang="ts">
import { computed } from 'vue'

interface Props {
  height?: string
}

const props = withDefaults(defineProps<Props>(), {
  height: '60px'
})

const footerStyle = computed(() => {
  return {
    height: props.height
  }
})
</script>

<style scoped>
.z-footer {
  display: flex;
  align-items: center;
  padding: 0 1rem;
  background-color: #f8f9fa;
  border-top: 1px solid #e5e7eb;
  flex-shrink: 0;
  color: #374151;
}
</style>