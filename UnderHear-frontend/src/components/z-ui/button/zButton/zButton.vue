<template>
  <button 
    :class="buttonClass" 
    :disabled="disabled"
    @click="handleClick"
  >
    <slot></slot>
  </button>
</template>

<script setup lang="ts">
import { computed } from 'vue'

interface Props {
  type?: 'primary' | 'success' | 'warning' | 'danger'
  size?: 'large' | 'small'
  disabled?: boolean
}

const props = withDefaults(defineProps<Props>(), {
  type: undefined,
  size: undefined,
  disabled: false
})

const emit = defineEmits<{
  click: [event: MouseEvent]
}>()

const buttonClass = computed(() => {
  const classes = ['z-button']
  
  if (props.type) {
    classes.push(`z-button--${props.type}`)
  }
  
  if (props.size) {
    classes.push(`z-button--${props.size}`)
  }
  
  if (props.disabled) {
    classes.push('z-button--disabled')
  }
  
  return classes
})

const handleClick = (event: MouseEvent) => {
  if (!props.disabled) {
    emit('click', event)
  }
}
</script>

<style scoped>
.z-button {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  padding: 0.5rem 1rem;
  font-size: 0.875rem;
  font-weight: 500;
  line-height: 1.25rem;
  border-radius: 0.375rem;
  border: 1px solid #d1d5db;
  background-color: #fff;
  color: #374151;
  cursor: pointer;
  transition: all 0.2s ease-in-out;
  outline: none;
  user-select: none;
}

.z-button:hover {
  background-color: #f9fafb;
  border-color: #9ca3af;
}

.z-button:focus {
  box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.1);
  border-color: #3b82f6;
}

.z-button:active {
  transform: translateY(1px);
}

/* 按钮类型样式 */
.z-button--primary {
  background-color: #3b82f6;
  border-color: #3b82f6;
  color: #fff;
}

.z-button--primary:hover {
  background-color: #2563eb;
  border-color: #2563eb;
}

.z-button--success {
  background-color: #10b981;
  border-color: #10b981;
  color: #fff;
}

.z-button--success:hover {
  background-color: #059669;
  border-color: #059669;
}

.z-button--warning {
  background-color: #f59e0b;
  border-color: #f59e0b;
  color: #fff;
}

.z-button--warning:hover {
  background-color: #d97706;
  border-color: #d97706;
}

.z-button--danger {
  background-color: #ef4444;
  border-color: #ef4444;
  color: #fff;
}

.z-button--danger:hover {
  background-color: #dc2626;
  border-color: #dc2626;
}

/* 按钮尺寸样式 */
.z-button--large {
  padding: 0.75rem 1.5rem;
  font-size: 1rem;
}

.z-button--small {
  padding: 0.375rem 0.75rem;
  font-size: 0.75rem;
}

/* 禁用状态 */
.z-button--disabled {
  opacity: 0.5;
  cursor: not-allowed;
  pointer-events: none;
}

.z-button--disabled:hover {
  transform: none;
}
</style>