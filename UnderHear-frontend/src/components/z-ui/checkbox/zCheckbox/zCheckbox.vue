<template>
  <label
    class="z-checkbox"
    :class="[
      {
        'is-disabled': isDisabled,
        'is-bordered': border,
        'is-checked': isChecked,
        'is-focus': focus,
        'is-indeterminate': indeterminate
      },
      size ? `z-checkbox--${size}` : ''
    ]"
    :id="id"
  >
    <span
      class="z-checkbox__input"
      :class="{
        'is-disabled': isDisabled,
        'is-checked': isChecked,
        'is-indeterminate': indeterminate,
        'is-focus': focus
      }"
      :tabindex="indeterminate ? 0 : undefined"
      :role="indeterminate ? 'checkbox' : undefined"
      :aria-checked="indeterminate ? 'mixed' : false"
    >
      <span class="z-checkbox__inner"></span>
      <input
        v-if="trueLabel || falseLabel"
        ref="checkbox"
        class="z-checkbox__original"
        type="checkbox"
        :aria-hidden="indeterminate ? 'true' : 'false'"
        :name="name"
        :disabled="isDisabled"
        :true-value="trueLabel"
        :false-value="falseLabel"
        v-model="model"
        @change="handleChange"
        @focus="focus = true"
        @blur="focus = false"
      >
      <input
        v-else
        ref="checkbox"
        class="z-checkbox__original"
        type="checkbox"
        :aria-hidden="indeterminate ? 'true' : 'false'"
        :disabled="isDisabled"
        :value="label"
        :name="name"
        v-model="model"
        @change="handleChange"
        @focus="focus = true"
        @blur="focus = false"
      >
    </span>
    <span class="z-checkbox__label" v-if="$slots.default || label">
      <slot>{{ label }}</slot>
    </span>
  </label>
</template>

<script setup lang="ts">
import { computed, inject, nextTick, ref, watch } from 'vue'
import type { CheckboxGroupContext, CheckboxModelValue, CheckboxValueType } from '../types'

interface CheckboxProps {
  modelValue?: CheckboxModelValue
  label?: CheckboxValueType | object
  indeterminate?: boolean
  disabled?: boolean
  checked?: boolean
  name?: string
  trueLabel?: CheckboxValueType
  falseLabel?: CheckboxValueType
  id?: string
  size?: 'large' | 'medium' | 'small' | 'mini'
  border?: boolean
}

interface CheckboxEmits {
  (e: 'update:modelValue', value: CheckboxModelValue): void
  (e: 'change', value: CheckboxModelValue): void
}

const props = withDefaults(defineProps<CheckboxProps>(), {
  indeterminate: false,
  disabled: false,
  checked: false,
  border: false
})

const emit = defineEmits<CheckboxEmits>()

// 注入 checkbox-group 上下文
const checkboxGroup = inject<CheckboxGroupContext | null>('CheckboxGroup', null)

// 响应式数据
const focus = ref(false)
const selfModel = ref(false)
const checkbox = ref<HTMLInputElement>()

// 计算属性
const isGroup = computed(() => !!checkboxGroup)

const isDisabled = computed(() => {
  return props.disabled || (checkboxGroup?.disabled?.value ?? false)
})

const size = computed(() => {
  return props.size || checkboxGroup?.size
})

const model = computed({
  get() {
    return isGroup.value
      ? checkboxGroup!.modelValue.value
      : props.modelValue !== undefined
      ? props.modelValue
      : selfModel.value
  },
  set(val) {
    if (isGroup.value) {
      // 确保val是正确的类型
      if (Array.isArray(val)) {
        checkboxGroup!.changeEvent(val)
      } else {
        // 对于单选框，我们需要将值转换为数组
        checkboxGroup!.changeEvent([val as CheckboxValueType])
      }
    } else {
      emit('update:modelValue', val)
      selfModel.value = val as boolean
    }
  }
})

const isChecked = computed(() => {
  if (typeof model.value === 'boolean') {
    return model.value
  } else if (Array.isArray(model.value)) {
    return model.value.includes(props.label as string | number | boolean)
  } else if (model.value !== null && model.value !== undefined) {
    return model.value === props.trueLabel
  }
  return false
})

const isLimitExceeded = computed(() => {
  if (!checkboxGroup || !checkboxGroup.max || !checkboxGroup.max.value || !checkboxGroup.min || !checkboxGroup.min.value || !checkboxGroup.modelValue) return false
  
  const maxValue = checkboxGroup.max.value
  const minValue = checkboxGroup.min.value
  const modelValue = checkboxGroup.modelValue.value
  
  return (
    (modelValue.length >= maxValue && !isChecked.value) ||
    (modelValue.length <= minValue && isChecked.value)
  )
})

// 方法
const addToStore = () => {
  if (Array.isArray(model.value)) {
    const modelArr = model.value as CheckboxValueType[]
    const labelValue = props.label as CheckboxValueType
    
    if (!modelArr.includes(labelValue)) {
      modelArr.push(labelValue)
    }
  } else {
    model.value = props.trueLabel !== undefined ? props.trueLabel : true
  }
}

const handleChange = (evt: Event) => {
  if (isLimitExceeded.value) return
  
  const target = evt.target as HTMLInputElement
  let value: CheckboxModelValue = target.checked
  
  if (Array.isArray(model.value)) {
    const newValue = [...(model.value as CheckboxValueType[])]
    const labelValue = props.label as CheckboxValueType
    
    if (target.checked) {
      if (!newValue.includes(labelValue)) {
        newValue.push(labelValue)
      }
    } else {
      const index = newValue.indexOf(labelValue)
      if (index > -1) {
        newValue.splice(index, 1)
      }
    }
    value = newValue
  } else {
    value = target.checked 
      ? (props.trueLabel !== undefined ? props.trueLabel : true) 
      : (props.falseLabel !== undefined ? props.falseLabel : false)
  }
  
  emit('change', value)
  
  nextTick(() => {
    if (checkbox.value) {
      checkbox.value.checked = isChecked.value
    }
  })
}

// 监听器
watch(
  () => props.modelValue,
  (val) => {
    emit('change', val!)
  }
)

watch(
  () => props.checked,
  (val) => {
    if (val) {
      addToStore()
    }
  },
  { immediate: true }
)
</script>

<style scoped>
.z-checkbox {
  color: #606266;
  font-weight: 500;
  font-size: 14px;
  position: relative;
  cursor: pointer;
  display: inline-flex;
  align-items: center;
  white-space: nowrap;
  user-select: none;
  margin-right: 30px;
  line-height: 1;
}

.z-checkbox:last-of-type {
  margin-right: 0;
}

.z-checkbox.is-bordered {
  padding: 9px 20px 9px 10px;
  border-radius: 4px;
  border: 1px solid #dcdfe6;
  box-sizing: border-box;
  height: 40px;
}

.z-checkbox.is-bordered.is-checked {
  border-color: #409eff;
}

.z-checkbox.is-bordered.is-disabled {
  border-color: #ebeef5;
  cursor: not-allowed;
}

.z-checkbox.is-bordered.z-checkbox--medium {
  padding: 7px 20px 7px 10px;
  border-radius: 4px;
  height: 36px;
}

.z-checkbox.is-bordered.z-checkbox--medium .z-checkbox__label {
  line-height: 17px;
  font-size: 14px;
}

.z-checkbox.is-bordered.z-checkbox--medium .z-checkbox__inner {
  height: 14px;
  width: 14px;
}

.z-checkbox.is-bordered.z-checkbox--small {
  padding: 5px 15px 5px 10px;
  border-radius: 3px;
  height: 32px;
}

.z-checkbox.is-bordered.z-checkbox--small .z-checkbox__label {
  line-height: 15px;
  font-size: 12px;
}

.z-checkbox.is-bordered.z-checkbox--small .z-checkbox__inner {
  height: 12px;
  width: 12px;
}

.z-checkbox.is-bordered.z-checkbox--small .z-checkbox__inner::after {
  height: 6px;
  width: 2px;
}

.z-checkbox.is-bordered.z-checkbox--mini {
  padding: 3px 15px 3px 10px;
  border-radius: 3px;
  height: 28px;
}

.z-checkbox.is-bordered.z-checkbox--mini .z-checkbox__label {
  line-height: 12px;
  font-size: 12px;
}

.z-checkbox.is-bordered.z-checkbox--mini .z-checkbox__inner {
  height: 12px;
  width: 12px;
}

.z-checkbox.is-bordered.z-checkbox--mini .z-checkbox__inner::after {
  height: 6px;
  width: 2px;
}

.z-checkbox.is-disabled {
  cursor: not-allowed;
}

.z-checkbox__input {
  white-space: nowrap;
  cursor: pointer;
  outline: none;
  display: inline-flex;
  position: relative;
  line-height: 1;
  vertical-align: middle;
}

.z-checkbox__input.is-disabled {
  cursor: not-allowed;
}

.z-checkbox__input.is-disabled .z-checkbox__inner {
  background-color: #edf2fc;
  border-color: #dcdfe6;
  cursor: not-allowed;
}

.z-checkbox__input.is-disabled .z-checkbox__inner::after {
  cursor: not-allowed;
  border-color: #c0c4cc;
}

.z-checkbox__input.is-disabled .z-checkbox__inner + .z-checkbox__label {
  cursor: not-allowed;
}

.z-checkbox__input.is-disabled.is-checked .z-checkbox__inner {
  background-color: #f2f6fc;
  border-color: #dcdfe6;
}

.z-checkbox__input.is-disabled.is-checked .z-checkbox__inner::after {
  border-color: #c0c4cc;
}

.z-checkbox__input.is-disabled.is-indeterminate .z-checkbox__inner {
  background-color: #f2f6fc;
  border-color: #dcdfe6;
}

.z-checkbox__input.is-disabled.is-indeterminate .z-checkbox__inner::before {
  background-color: #c0c4cc;
  border-color: #c0c4cc;
}

.z-checkbox__input.is-checked .z-checkbox__inner,
.z-checkbox__input.is-indeterminate .z-checkbox__inner {
  background-color: #409eff;
  border-color: #409eff;
}

.z-checkbox__input.is-checked .z-checkbox__inner::after {
  transform: rotate(45deg) scaleY(1);
}

.z-checkbox__input.is-checked + .z-checkbox__label {
  color: #409eff;
}

.z-checkbox__input.is-focus .z-checkbox__inner {
  border-color: #409eff;
}

.z-checkbox__input.is-indeterminate .z-checkbox__inner::before {
  content: '';
  position: absolute;
  display: block;
  background-color: #fff;
  height: 2px;
  transform: scale(0.5);
  left: 0;
  right: 0;
  top: 5px;
}

.z-checkbox__input.is-indeterminate .z-checkbox__inner::after {
  display: none;
}

.z-checkbox__inner {
  display: inline-block;
  position: relative;
  border: 1px solid #dcdfe6;
  border-radius: 2px;
  box-sizing: border-box;
  width: 14px;
  height: 14px;
  background-color: #fff;
  z-index: 1;
  transition: border-color 0.25s cubic-bezier(0.71, -0.46, 0.29, 1.46),
    background-color 0.25s cubic-bezier(0.71, -0.46, 0.29, 1.46);
}

.z-checkbox__inner:hover {
  border-color: #409eff;
}

.z-checkbox__inner::after {
  box-sizing: content-box;
  content: '';
  border: 1px solid #fff;
  border-left: 0;
  border-top: 0;
  height: 7px;
  left: 4px;
  position: absolute;
  top: 1px;
  transform: rotate(45deg) scaleY(0);
  width: 3px;
  transition: transform 0.15s ease-in 0.05s;
  transform-origin: center;
}

.z-checkbox__original {
  opacity: 0;
  outline: none;
  position: absolute;
  margin: 0;
  width: 0;
  height: 0;
  z-index: -1;
}

.z-checkbox__label {
  display: inline-block;
  padding-left: 10px;
  line-height: 19px;
  font-size: 14px;
}

/* 尺寸变体 */
.z-checkbox--large .z-checkbox__inner {
  width: 16px;
  height: 16px;
}

.z-checkbox--large .z-checkbox__inner::after {
  height: 8px;
  width: 3px;
  left: 5px;
  top: 2px;
}

.z-checkbox--large .z-checkbox__label {
  font-size: 16px;
  line-height: 21px;
}

.z-checkbox--medium .z-checkbox__inner {
  width: 14px;
  height: 14px;
}

.z-checkbox--medium .z-checkbox__inner::after {
  height: 7px;
  width: 3px;
  left: 4px;
  top: 1px;
}

.z-checkbox--medium .z-checkbox__label {
  font-size: 14px;
  line-height: 19px;
}

.z-checkbox--small .z-checkbox__inner {
  width: 12px;
  height: 12px;
}

.z-checkbox--small .z-checkbox__inner::after {
  height: 6px;
  width: 2px;
  left: 3px;
  top: 1px;
}

.z-checkbox--small .z-checkbox__label {
  font-size: 12px;
  line-height: 17px;
}

.z-checkbox--mini .z-checkbox__inner {
  width: 12px;
  height: 12px;
}

.z-checkbox--mini .z-checkbox__inner::after {
  height: 6px;
  width: 2px;
  left: 3px;
  top: 1px;
}

.z-checkbox--mini .z-checkbox__label {
  font-size: 12px;
  line-height: 15px;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .z-checkbox {
    margin-right: 20px;
    font-size: 13px;
  }
  
  .z-checkbox__label {
    padding-left: 8px;
    font-size: 13px;
  }
  
  .z-checkbox.is-bordered {
    padding: 8px 15px 8px 8px;
    height: 36px;
  }
}

/* 高对比度模式支持 */
@media (prefers-contrast: high) {
  .z-checkbox__inner {
    border-width: 2px;
  }
  
  .z-checkbox__input.is-checked .z-checkbox__inner,
  .z-checkbox__input.is-indeterminate .z-checkbox__inner {
    background-color: #000;
    border-color: #000;
  }
  
  .z-checkbox__input.is-disabled .z-checkbox__inner {
    background-color: #f0f0f0;
    border-color: #999;
  }
}

/* 深色主题支持 */
@media (prefers-color-scheme: dark) {
  .z-checkbox {
    color: #e0e0e0;
  }
  
  .z-checkbox__inner {
    background-color: #2a2a2a;
    border-color: #555;
  }
  
  .z-checkbox__inner:hover {
    border-color: #409eff;
  }
  
  .z-checkbox__input.is-checked .z-checkbox__inner,
  .z-checkbox__input.is-indeterminate .z-checkbox__inner {
    background-color: #409eff;
    border-color: #409eff;
  }
  
  .z-checkbox__input.is-disabled .z-checkbox__inner {
    background-color: #1a1a1a;
    border-color: #404040;
  }
  
  .z-checkbox.is-bordered {
    border-color: #555;
  }
  
  .z-checkbox.is-bordered.is-checked {
    border-color: #409eff;
  }
  
  .z-checkbox.is-bordered.is-disabled {
    border-color: #404040;
  }
}

/* 动画效果 */
@keyframes checkboxCheck {
  0% {
    transform: rotate(45deg) scaleY(0);
  }
  100% {
    transform: rotate(45deg) scaleY(1);
  }
}

.z-checkbox__input.is-checked .z-checkbox__inner::after {
  animation: checkboxCheck 0.15s ease-in 0.05s;
}

/* 无障碍支持 */
.z-checkbox:focus-within .z-checkbox__inner {
  outline: 2px solid #409eff;
  outline-offset: 2px;
}

@media (prefers-reduced-motion: reduce) {
  .z-checkbox__inner,
  .z-checkbox__inner::after {
    transition: none;
  }
  
  .z-checkbox__input.is-checked .z-checkbox__inner::after {
    animation: none;
  }
}
</style>