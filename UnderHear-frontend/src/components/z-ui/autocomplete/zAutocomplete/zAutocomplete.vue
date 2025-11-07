<template>
  <div 
    :class="autocompleteClass"
    @click="handleClick"
  >
    <div class="z-autocomplete-input-wrapper">
      <!-- 前缀插槽 -->
      <div v-if="$slots.prefix" class="z-autocomplete-prefix">
        <slot name="prefix" />
      </div>
      
      <!-- 输入框 -->
      <input
        ref="inputRef"
        v-model="inputValue"
        :class="inputClass"
        :placeholder="placeholder"
        :disabled="disabled"
        :readonly="readonly"
        @input="handleInput"
        @focus="handleFocus"
        @blur="handleBlur"
        @keydown="handleKeydown"
        @compositionstart="handleCompositionStart"
        @compositionend="handleCompositionEnd"
      />
      
      <!-- 加载图标 -->
      <div v-if="loading" class="z-autocomplete-loading">
        <svg class="z-autocomplete-loading-icon" viewBox="0 0 24 24">
          <circle cx="12" cy="12" r="10" stroke="currentColor" stroke-width="2" fill="none" stroke-dasharray="31.416" stroke-dashoffset="31.416">
            <animate attributeName="stroke-dasharray" dur="2s" values="0 31.416;15.708 15.708;0 31.416" repeatCount="indefinite"/>
            <animate attributeName="stroke-dashoffset" dur="2s" values="0;-15.708;-31.416" repeatCount="indefinite"/>
          </circle>
        </svg>
      </div>
      
      <!-- 清空按钮 -->
      <button
        v-if="clearable && inputValue && !disabled"
        class="z-autocomplete-clear"
        type="button"
        @click="handleClear"
      >
        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
          <circle cx="12" cy="12" r="10"/>
          <path d="m15 9-6 6"/>
          <path d="m9 9 6 6"/>
        </svg>
      </button>
      
      <!-- 后缀插槽 -->
      <div v-if="$slots.suffix" class="z-autocomplete-suffix">
        <slot name="suffix" />
      </div>
    </div>
    
    <!-- 下拉面板 -->
    <Transition name="z-autocomplete-dropdown">
      <div
        v-if="showDropdown && (filteredOptions.length > 0 || $slots.empty)"
        ref="dropdownRef"
        :class="dropdownClass"
        :style="dropdownStyle"
      >
        <div class="z-autocomplete-options">
          <!-- 选项列表 -->
          <div
            v-for="(option, index) in filteredOptions"
            :key="getOptionKey(option, index)"
            :class="getOptionClass(index)"
            @click="handleSelect(option, index)"
            @mouseenter="highlightedIndex = index"
          >
            <slot name="option" :option="option" :index="index">
              {{ getOptionLabel(option) }}
            </slot>
          </div>
          
          <!-- 无数据 -->
          <div v-if="filteredOptions.length === 0" class="z-autocomplete-empty">
            <slot name="empty">
              <div class="z-autocomplete-empty-content">
                <svg class="z-autocomplete-empty-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                  <circle cx="11" cy="11" r="8"/>
                  <path d="m21 21-4.35-4.35"/>
                </svg>
                <span>暂无数据</span>
              </div>
            </slot>
          </div>
        </div>
      </div>
    </Transition>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, watch, nextTick, onMounted, onUnmounted } from 'vue'
import { addUnit } from '@/lib/utils'

// 类型定义
interface AutocompleteOption {
  label?: string
  value?: string
  disabled?: boolean
  [key: string]: any
}

type OptionType = string | AutocompleteOption

// Props 定义
interface Props {
  modelValue?: string
  options?: OptionType[]
  placeholder?: string
  size?: 'small' | 'medium' | 'large'
  disabled?: boolean
  readonly?: boolean
  clearable?: boolean
  loading?: boolean
  remote?: boolean
  filterMethod?: (query: string, option: OptionType) => boolean
  maxHeight?: string | number
  popperClass?: string
  placement?: 'top' | 'bottom'
  debounce?: number
}

const props = withDefaults(defineProps<Props>(), {
  modelValue: '',
  options: () => [],
  placeholder: '',
  size: 'medium',
  disabled: false,
  readonly: false,
  clearable: false,
  loading: false,
  remote: false,
  maxHeight: '200px',
  placement: 'bottom',
  debounce: 300
})

// Emits 定义
interface Emits {
  'update:modelValue': [value: string]
  select: [option: OptionType]
  search: [query: string]
  focus: [event: FocusEvent]
  blur: [event: FocusEvent]
  clear: []
}

const emit = defineEmits<Emits>()

// 响应式数据
const inputRef = ref<HTMLInputElement>()
const dropdownRef = ref<HTMLElement>()
const inputValue = ref(props.modelValue)
const showDropdown = ref(false)
const highlightedIndex = ref(-1)
const isComposing = ref(false)
const searchTimer = ref<number>()

// 监听 modelValue 变化
watch(() => props.modelValue, (newValue) => {
  inputValue.value = newValue
})

// 监听输入值变化
watch(inputValue, (newValue) => {
  emit('update:modelValue', newValue)
  
  if (props.remote) {
    handleRemoteSearch(newValue)
  }
})

// 计算属性
const autocompleteClass = computed(() => [
  'z-autocomplete',
  `z-autocomplete--${props.size}`,
  {
    'z-autocomplete--disabled': props.disabled,
    'z-autocomplete--focus': showDropdown.value
  }
])

const inputClass = computed(() => [
  'z-autocomplete-input',
  {
    'z-autocomplete-input--disabled': props.disabled,
    'z-autocomplete-input--readonly': props.readonly
  }
])

const dropdownClass = computed(() => [
  'z-autocomplete-dropdown',
  `z-autocomplete-dropdown--${props.placement}`,
  props.popperClass
])

const dropdownStyle = computed(() => ({
  maxHeight: addUnit(props.maxHeight)
}))

// 筛选选项
const filteredOptions = computed(() => {
  if (props.remote) {
    return props.options
  }
  
  const query = inputValue.value.trim()
  if (!query) {
    return props.options
  }
  
  return props.options.filter(option => {
    if (props.filterMethod) {
      return props.filterMethod(query, option)
    }
    
    const label = getOptionLabel(option).toLowerCase()
    return label.includes(query.toLowerCase())
  })
})

// 获取选项标签
const getOptionLabel = (option: OptionType): string => {
  if (typeof option === 'string') {
    return option
  }
  return option.label || option.value || String(option)
}

// 获取选项值
const getOptionValue = (option: OptionType): string => {
  if (typeof option === 'string') {
    return option
  }
  return option.value || option.label || String(option)
}

// 获取选项键
const getOptionKey = (option: OptionType, index: number): string => {
  return getOptionValue(option) + index
}

// 获取选项样式类
const getOptionClass = (index: number) => [
  'z-autocomplete-option',
  {
    'z-autocomplete-option--highlighted': index === highlightedIndex.value,
    'z-autocomplete-option--disabled': isOptionDisabled(filteredOptions.value[index])
  }
]

// 判断选项是否禁用
const isOptionDisabled = (option: OptionType): boolean => {
  if (typeof option === 'string') {
    return false
  }
  return option.disabled || false
}

// 远程搜索
const handleRemoteSearch = (query: string) => {
  if (searchTimer.value) {
    clearTimeout(searchTimer.value)
  }
  
  searchTimer.value = window.setTimeout(() => {
    emit('search', query)
  }, props.debounce)
}

// 事件处理
const handleClick = () => {
  if (!props.disabled && !props.readonly) {
    inputRef.value?.focus()
  }
}

const handleInput = (event: Event) => {
  if (isComposing.value) return
  
  const target = event.target as HTMLInputElement
  inputValue.value = target.value
  
  if (!showDropdown.value) {
    showDropdown.value = true
  }
  
  highlightedIndex.value = -1
}

const handleFocus = (event: FocusEvent) => {
  if (!props.disabled && !props.readonly) {
    showDropdown.value = true
    emit('focus', event)
  }
}

const handleBlur = (event: FocusEvent) => {
  // 延迟隐藏下拉面板，以便处理选项点击
  setTimeout(() => {
    showDropdown.value = false
    highlightedIndex.value = -1
    emit('blur', event)
  }, 200)
}

const handleKeydown = (event: KeyboardEvent) => {
  if (props.disabled || props.readonly) return
  
  const { key } = event
  const optionsLength = filteredOptions.value.length
  
  switch (key) {
    case 'ArrowDown':
      event.preventDefault()
      if (!showDropdown.value) {
        showDropdown.value = true
      } else {
        highlightedIndex.value = highlightedIndex.value < optionsLength - 1 
          ? highlightedIndex.value + 1 
          : 0
      }
      break
      
    case 'ArrowUp':
      event.preventDefault()
      if (showDropdown.value) {
        highlightedIndex.value = highlightedIndex.value > 0 
          ? highlightedIndex.value - 1 
          : optionsLength - 1
      }
      break
      
    case 'Enter':
      event.preventDefault()
      if (showDropdown.value && highlightedIndex.value >= 0) {
        const option = filteredOptions.value[highlightedIndex.value]
        if (!isOptionDisabled(option)) {
          handleSelect(option, highlightedIndex.value)
        }
      }
      break
      
    case 'Escape':
      showDropdown.value = false
      highlightedIndex.value = -1
      inputRef.value?.blur()
      break
  }
}

const handleCompositionStart = () => {
  isComposing.value = true
}

const handleCompositionEnd = (event: CompositionEvent) => {
  isComposing.value = false
  handleInput(event)
}

const handleSelect = (option: OptionType, _index: number) => {
  if (isOptionDisabled(option)) return
  
  const value = getOptionValue(option)
  inputValue.value = value
  showDropdown.value = false
  highlightedIndex.value = -1
  
  emit('select', option)
  
  nextTick(() => {
    inputRef.value?.focus()
  })
}

const handleClear = () => {
  inputValue.value = ''
  showDropdown.value = false
  highlightedIndex.value = -1
  emit('clear')
  
  nextTick(() => {
    inputRef.value?.focus()
  })
}

// 点击外部关闭下拉面板
const handleClickOutside = (event: Event) => {
  const target = event.target as Node
  const autocompleteEl = inputRef.value?.closest('.z-autocomplete')
  
  if (autocompleteEl && !autocompleteEl.contains(target)) {
    showDropdown.value = false
    highlightedIndex.value = -1
  }
}

// 生命周期
onMounted(() => {
  document.addEventListener('click', handleClickOutside)
})

onUnmounted(() => {
  document.removeEventListener('click', handleClickOutside)
  if (searchTimer.value) {
    clearTimeout(searchTimer.value)
  }
})

// 暴露方法
defineExpose({
  focus: () => inputRef.value?.focus(),
  blur: () => inputRef.value?.blur(),
  clear: handleClear
})
</script>

<style scoped>
.z-autocomplete {
  position: relative;
  display: inline-block;
  width: 100%;
}

.z-autocomplete-input-wrapper {
  position: relative;
  display: flex;
  align-items: center;
  background: var(--z-bg-primary, #ffffff);
  border: 1px solid var(--z-border-primary, #e5e7eb);
  border-radius: var(--z-border-radius, 6px);
  transition: all var(--z-transition-duration, 0.2s) var(--z-transition-timing, cubic-bezier(0.4, 0, 0.2, 1));
}

.z-autocomplete-input-wrapper:hover {
  border-color: var(--z-border-secondary, #d1d5db);
}

.z-autocomplete--focus .z-autocomplete-input-wrapper {
  border-color: var(--z-primary-color, #3b82f6);
  box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.1);
}

.z-autocomplete-input {
  flex: 1;
  padding: var(--z-spacing-md, 12px);
  border: none;
  outline: none;
  background: transparent;
  font-size: var(--z-font-size-base, 16px);
  color: var(--z-text-primary, #1f2937);
  line-height: 1.5;
}

.z-autocomplete-input::placeholder {
  color: var(--z-text-secondary, #6b7280);
}

.z-autocomplete-input--disabled {
  color: var(--z-text-disabled, #9ca3af);
  cursor: not-allowed;
}

.z-autocomplete-input--readonly {
  cursor: default;
}

.z-autocomplete-prefix,
.z-autocomplete-suffix {
  display: flex;
  align-items: center;
  padding: 0 var(--z-spacing-sm, 8px);
  color: var(--z-text-secondary, #6b7280);
}

.z-autocomplete-loading {
  display: flex;
  align-items: center;
  padding: 0 var(--z-spacing-sm, 8px);
  color: var(--z-primary-color, #3b82f6);
}

.z-autocomplete-loading-icon {
  width: 16px;
  height: 16px;
}

.z-autocomplete-clear {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 20px;
  height: 20px;
  margin-right: var(--z-spacing-sm, 8px);
  padding: 0;
  border: none;
  border-radius: 50%;
  background: var(--z-bg-tertiary, #f3f4f6);
  color: var(--z-text-secondary, #6b7280);
  cursor: pointer;
  transition: all var(--z-transition-duration, 0.2s) var(--z-transition-timing, cubic-bezier(0.4, 0, 0.2, 1));
}

.z-autocomplete-clear:hover {
  background: var(--z-bg-disabled, #e5e7eb);
  color: var(--z-text-primary, #1f2937);
}

.z-autocomplete-clear svg {
  width: 12px;
  height: 12px;
}

.z-autocomplete-dropdown {
  position: absolute;
  top: 100%;
  left: 0;
  right: 0;
  z-index: var(--z-z-index-tooltip, 600);
  margin-top: 4px;
  background: var(--z-bg-primary, #ffffff);
  border: 1px solid var(--z-border-primary, #e5e7eb);
  border-radius: var(--z-border-radius, 6px);
  box-shadow: var(--z-shadow-lg, 0 10px 15px -3px rgba(0, 0, 0, 0.1), 0 4px 6px -2px rgba(0, 0, 0, 0.05));
  overflow: hidden;
}

.z-autocomplete-dropdown--top {
  top: auto;
  bottom: 100%;
  margin-top: 0;
  margin-bottom: 4px;
}

.z-autocomplete-options {
  max-height: inherit;
  overflow-y: auto;
}

.z-autocomplete-option {
  padding: var(--z-spacing-md, 12px);
  cursor: pointer;
  transition: all var(--z-transition-duration, 0.2s) var(--z-transition-timing, cubic-bezier(0.4, 0, 0.2, 1));
  border-bottom: 1px solid var(--z-border-primary, #e5e7eb);
}

.z-autocomplete-option:last-child {
  border-bottom: none;
}

.z-autocomplete-option:hover,
.z-autocomplete-option--highlighted {
  background: var(--z-bg-secondary, #f9fafb);
}

.z-autocomplete-option--disabled {
  color: var(--z-text-disabled, #9ca3af);
  cursor: not-allowed;
  background: transparent !important;
}

.z-autocomplete-empty {
  padding: var(--z-spacing-xl, 24px);
  text-align: center;
  color: var(--z-text-secondary, #6b7280);
}

.z-autocomplete-empty-content {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: var(--z-spacing-sm, 8px);
}

.z-autocomplete-empty-icon {
  width: 24px;
  height: 24px;
  opacity: 0.5;
}

/* 尺寸变体 */
.z-autocomplete--small .z-autocomplete-input {
  padding: var(--z-spacing-sm, 8px);
  font-size: var(--z-font-size-sm, 14px);
}

.z-autocomplete--small .z-autocomplete-option {
  padding: var(--z-spacing-sm, 8px);
  font-size: var(--z-font-size-sm, 14px);
}

.z-autocomplete--large .z-autocomplete-input {
  padding: var(--z-spacing-lg, 16px);
  font-size: var(--z-font-size-lg, 18px);
}

.z-autocomplete--large .z-autocomplete-option {
  padding: var(--z-spacing-lg, 16px);
  font-size: var(--z-font-size-lg, 18px);
}

/* 禁用状态 */
.z-autocomplete--disabled .z-autocomplete-input-wrapper {
  background: var(--z-bg-disabled, #e5e7eb);
  border-color: var(--z-border-primary, #e5e7eb);
  cursor: not-allowed;
}

.z-autocomplete--disabled .z-autocomplete-input-wrapper:hover {
  border-color: var(--z-border-primary, #e5e7eb);
}

/* 过渡动画 */
.z-autocomplete-dropdown-enter-active,
.z-autocomplete-dropdown-leave-active {
  transition: all var(--z-transition-duration, 0.2s) var(--z-transition-timing, cubic-bezier(0.4, 0, 0.2, 1));
}

.z-autocomplete-dropdown-enter-from,
.z-autocomplete-dropdown-leave-to {
  opacity: 0;
  transform: translateY(-8px);
}

.z-autocomplete-dropdown--top.z-autocomplete-dropdown-enter-from,
.z-autocomplete-dropdown--top.z-autocomplete-dropdown-leave-to {
  transform: translateY(8px);
}

/* 响应式设计 */
@media (max-width: 768px) {
  .z-autocomplete-input {
    font-size: 16px; /* 防止 iOS Safari 缩放 */
  }
  
  .z-autocomplete-dropdown {
    left: -8px;
    right: -8px;
  }
}

/* 高对比度模式支持 */
@media (prefers-contrast: high) {
  .z-autocomplete-input-wrapper {
    border-width: 2px;
  }
  
  .z-autocomplete--focus .z-autocomplete-input-wrapper {
    border-color: #000000;
  }
}

/* 减少动画模式支持 */
@media (prefers-reduced-motion: reduce) {
  .z-autocomplete-input-wrapper,
  .z-autocomplete-option,
  .z-autocomplete-clear,
  .z-autocomplete-dropdown-enter-active,
  .z-autocomplete-dropdown-leave-active {
    transition: none;
  }
}
</style>