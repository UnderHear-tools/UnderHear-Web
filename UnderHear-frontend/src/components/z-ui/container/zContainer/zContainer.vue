<template>
  <div :class="containerClass">
    <slot></slot>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue'

interface Props {
  direction?: 'horizontal' | 'vertical'
}

const props = withDefaults(defineProps<Props>(), {
  direction: 'vertical'
})

const containerClass = computed(() => {
  return [
    'z-container',
    `z-container--${props.direction}`
  ]
})
</script>

<style scoped>
.z-container {
  display: flex;
  width: 100%;
  height: 100%;
}

.z-container--vertical {
  flex-direction: column;
}

.z-container--horizontal {
  flex-direction: row;
}
</style>