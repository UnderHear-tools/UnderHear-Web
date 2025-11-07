<template>
  <i :class="iconClass" :style="iconStyle">
    {{ iconContent }}
  </i>
</template>

<script setup lang="ts">
import { computed } from 'vue'

interface Props {
  name: string
  size?: 'small' | 'large'
  color?: string
}

const props = withDefaults(defineProps<Props>(), {
  size: undefined,
  color: undefined
})

const iconMap: Record<string, string> = {
  home: '🏠',
  user: '👤',
  setting: '⚙️',
  search: '🔍',
  edit: '✏️',
  delete: '🗑️',
  add: '➕',
  close: '❌'
}

const iconClass = computed(() => {
  const classes = ['z-icon']
  
  if (props.size) {
    classes.push(`z-icon--${props.size}`)
  }
  
  return classes
})

const iconStyle = computed(() => {
  const style: Record<string, string> = {}
  
  if (props.color) {
    style.color = props.color
  }
  
  return style
})

const iconContent = computed(() => {
  return iconMap[props.name] || '❓'
})
</script>

<style scoped>
.z-icon {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  font-size: 1rem;
  line-height: 1;
  user-select: none;
  transition: all 0.2s ease;
}

.z-icon--small {
  font-size: 0.75rem;
}

.z-icon--large {
  font-size: 1.5rem;
}
</style>