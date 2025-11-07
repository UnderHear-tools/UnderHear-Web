<template>
  <aside class="z-aside" :style="asideStyle">
    <slot></slot>
  </aside>
</template>

<script setup lang="ts">
import { computed } from 'vue'

interface Props {
  width?: string
}

const props = withDefaults(defineProps<Props>(), {
  width: '300px'
})

const asideStyle = computed(() => {
  return {
    width: props.width
  }
})
</script>

<style scoped>
.z-aside {
  display: flex;
  flex-direction: column;
  padding: 1rem;
  background-color: #f8f9fa;
  border-right: 1px solid #e5e7eb;
  flex-shrink: 0;
  color: #374151;
}
</style>