<template>
  <div
    class="z-checkbox-group"
    :class="[
      {
        'is-disabled': disabled
      },
      size ? `z-checkbox-group--${size}` : ''
    ]"
    role="group"
    :aria-label="ariaLabel"
  >
    <slot></slot>
  </div>
</template>

<script setup lang="ts">
import { computed, nextTick, provide, toRefs, watch } from 'vue'
import type { CheckboxGroupContext, CheckboxModelValue, CheckboxValueType } from '../types'

interface CheckboxGroupProps {
  modelValue?: CheckboxModelValue
  disabled?: boolean
  min?: number
  max?: number
  size?: 'large' | 'medium' | 'small' | 'mini'
  textColor?: string
  fill?: string
  ariaLabel?: string
}

interface CheckboxGroupEmits {
  (e: 'update:modelValue', value: CheckboxModelValue): void
  (e: 'change', value: CheckboxModelValue): void
}

const props = withDefaults(defineProps<CheckboxGroupProps>(), {
  modelValue: () => [],
  disabled: false,
  textColor: '#ffffff',
  fill: '#409EFF'
})

const emit = defineEmits<CheckboxGroupEmits>()

// 解构 props
const { modelValue, disabled, min, max, size, textColor, fill } = toRefs(props)

// 方法
const changeEvent = (value: CheckboxValueType[]) => {
  emit('update:modelValue', value)
  nextTick(() => {
    emit('change', value)
  })
}

// 提供给子组件的上下文
const checkboxGroupContext: CheckboxGroupContext = {
  modelValue: computed(() => modelValue.value as CheckboxValueType[]),
  disabled: computed(() => disabled.value),
  min: computed(() => min?.value),
  max: computed(() => max?.value),
  size: computed(() => size?.value),
  textColor: computed(() => textColor.value),
  fill: computed(() => fill.value),
  changeEvent
}

provide('CheckboxGroup', checkboxGroupContext)

// 监听器
watch(
  () => props.modelValue,
  (val) => {
    emit('change', val)
  }
)
</script>

<style scoped>
.z-checkbox-group {
  font-size: 0;
  line-height: 1;
}

.z-checkbox-group.is-disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

/* 尺寸变体 */
.z-checkbox-group--large {
  font-size: 16px;
}

.z-checkbox-group--medium {
  font-size: 14px;
}

.z-checkbox-group--small {
  font-size: 12px;
}

.z-checkbox-group--mini {
  font-size: 12px;
}

/* 子元素间距 */
.z-checkbox-group :deep(.z-checkbox) {
  margin-right: 30px;
}

.z-checkbox-group :deep(.z-checkbox:last-child) {
  margin-right: 0;
}

.z-checkbox-group :deep(.z-checkbox-button) {
  margin: 0;
}

.z-checkbox-group :deep(.z-checkbox-button:first-child .z-checkbox-button__inner) {
  border-left: 1px solid #dcdfe6;
  border-radius: 4px 0 0 4px;
  box-shadow: none !important;
}

.z-checkbox-group :deep(.z-checkbox-button:last-child .z-checkbox-button__inner) {
  border-radius: 0 4px 4px 0;
}

.z-checkbox-group :deep(.z-checkbox-button:first-child:last-child .z-checkbox-button__inner) {
  border-radius: 4px;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .z-checkbox-group :deep(.z-checkbox) {
    margin-right: 20px;
    margin-bottom: 10px;
  }
  
  .z-checkbox-group :deep(.z-checkbox-button) {
    margin-bottom: 5px;
  }
}

/* 深色主题支持 */
@media (prefers-color-scheme: dark) {
  .z-checkbox-group :deep(.z-checkbox-button:first-child .z-checkbox-button__inner) {
    border-left-color: #555;
  }
}

/* 无障碍支持 */
.z-checkbox-group:focus-within {
  outline: 2px solid #409eff;
  outline-offset: 2px;
  border-radius: 4px;
}

@media (prefers-reduced-motion: reduce) {
  .z-checkbox-group * {
    transition: none !important;
    animation: none !important;
  }
}
</style>