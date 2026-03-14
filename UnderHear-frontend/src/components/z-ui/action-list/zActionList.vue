<template>
  <div class="z-action-list">
    <slot />
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { createContext, provideContext, type ActionListSelectionMode } from './context'

const props = withDefaults(
  defineProps<{
    selectionMode?: ActionListSelectionMode
    modelValue?: string
  }>(),
  {
    selectionMode: 'none',
    modelValue: ''
  }
)

const emit = defineEmits<{
  'update:modelValue': [value: string]
  change: [value: string]
}>()

const selectionMode = computed<ActionListSelectionMode>(() => props.selectionMode)
const modelValue = computed<string>(() => props.modelValue)

const select = (value: string) => {
  if (selectionMode.value !== 'single') return
  if (modelValue.value === value) return

  emit('update:modelValue', value)
  emit('change', value)
}

provideContext(createContext({
  selectionMode,
  modelValue,
  select
}))
</script>

<style scoped>
.z-action-list {
  padding: 8px;
}
</style>
