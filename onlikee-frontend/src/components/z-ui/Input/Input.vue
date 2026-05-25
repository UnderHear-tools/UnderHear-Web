<template>
  <input
    class="input"
    :data-size="size"
    :value="modelValue"
    @input="onInput"
    @compositionstart="onCompositionStart"
    @compositionend="onCompositionEnd"
  >
</template>

<script setup lang="ts">
import { ref } from 'vue'

interface Props {
  modelValue?: string
  size?: 'small' | 'medium' | 'large'
}

withDefaults(defineProps<Props>(), {
  modelValue: '',
  size: 'medium'
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
.input {
  color: var(--fgColor-default, #1f2328);
  vertical-align: middle;
  background-color: var(--bgColor-default, #fff);
  border: 1px solid var(--control-borderColor-rest, #d0d7de);
  box-shadow: var(--shadow-inset, inset 0px 1px 0px 0px #1f23280a);
  background-position: right 8px center;
  background-repeat: no-repeat;
  border-radius: 6px;
  padding: 5px 12px;
  box-sizing: border-box;
  font-family: inherit;
  line-height: 20px;
}

.input[data-size="small"] {
  font-size: 12px;
  height: 29.6px;
  line-height: 18px;
  padding-top: 4.8px;
  padding-bottom: 4.8px;
}

.input[data-size="medium"] {
  font-size: 14px;
  height: 32px;
}

.input[data-size="large"] {
  font-size: 14px;
  height: 40px;
  padding-top: 9px;
  padding-bottom: 9px;
}

.input:focus {
  outline: 2px solid var(--focus-outlineColor, #0969da);
  outline-offset: -1px;
}

.input:disabled {
  cursor: not-allowed;
  background: var(--bgColor-muted, #f6f8fa);
}
</style>
