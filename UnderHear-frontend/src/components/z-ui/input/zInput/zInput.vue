<template>
  <input
    class="z-input"
    :value="modelValue"
    @input="onInput"
    @compositionstart="onCompositionStart"
    @compositionend="onCompositionEnd"
  />
</template>

<script setup lang="ts">
import { ref } from 'vue'

interface Props {
  modelValue?: string
}

withDefaults(defineProps<Props>(), {
  modelValue: ''
})

const emit = defineEmits<{
  'update:modelValue': [value: string]
}>()

const isComposing = ref(false)

function onCompositionStart() {
  isComposing.value = true
}

function onCompositionEnd(event: CompositionEvent) {
  isComposing.value = false
  emit('update:modelValue', (event.target as HTMLInputElement).value)
}

function onInput(event: Event) {
  if (isComposing.value) return
  emit('update:modelValue', (event.target as HTMLInputElement).value)
}
</script>

<style scoped>
.z-input {
  width: 100%;
  border: 1px solid #d1d9e1;
  border-radius: 6px;
  padding: 0.6rem 0.7rem;
  font-size: 0.95rem;
  outline: none;
  transition: border-color 0.2s ease, box-shadow 0.2s ease;
}

.z-input:focus {
  outline: 2px solid #0969da;
  outline-offset: -1px;
}

.z-input:disabled {
  cursor: not-allowed;
  opacity: 0.7;
  background: #f6f8fa;
}
</style>
