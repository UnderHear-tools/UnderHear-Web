import type { ComputedRef } from 'vue'

// Checkbox 组件的基础类型
export type CheckboxValueType = string | number | boolean

// Checkbox 组件的 modelValue 类型
export type CheckboxModelValue = CheckboxValueType | CheckboxValueType[]

// 尺寸类型
export type CheckboxSize = 'large' | 'medium' | 'small' | 'mini'

// CheckboxGroup 上下文接口
export interface CheckboxGroupContext {
  modelValue: ComputedRef<CheckboxValueType[]>
  disabled: ComputedRef<boolean>
  min?: ComputedRef<number | undefined>
  max?: ComputedRef<number | undefined>
  size?: ComputedRef<CheckboxSize | undefined>
  textColor: ComputedRef<string>
  fill: ComputedRef<string>
  changeEvent: (value: CheckboxValueType[]) => void
}

// Checkbox 组件 Props 接口
export interface CheckboxProps {
  modelValue?: CheckboxModelValue
  label?: CheckboxValueType | object
  indeterminate?: boolean
  disabled?: boolean
  checked?: boolean
  name?: string
  trueLabel?: CheckboxValueType
  falseLabel?: CheckboxValueType
  id?: string
  size?: CheckboxSize
  border?: boolean
}

// CheckboxGroup 组件 Props 接口
export interface CheckboxGroupProps {
  modelValue?: CheckboxValueType[]
  disabled?: boolean
  min?: number
  max?: number
  size?: CheckboxSize
  textColor?: string
  fill?: string
  ariaLabel?: string
}

// CheckboxButton 组件 Props 接口
export interface CheckboxButtonProps {
  modelValue?: CheckboxModelValue
  label?: CheckboxValueType | object
  disabled?: boolean
  checked?: boolean
  name?: string
  trueLabel?: CheckboxValueType
  falseLabel?: CheckboxValueType
}

// Checkbox 组件 Emits 接口
export interface CheckboxEmits {
  (e: 'update:modelValue', value: CheckboxModelValue): void
  (e: 'change', value: CheckboxModelValue): void
}

// CheckboxGroup 组件 Emits 接口
export interface CheckboxGroupEmits {
  (e: 'update:modelValue', value: CheckboxValueType[]): void
  (e: 'change', value: CheckboxValueType[]): void
}

// CheckboxButton 组件 Emits 接口
export interface CheckboxButtonEmits {
  (e: 'update:modelValue', value: CheckboxModelValue): void
  (e: 'change', value: CheckboxModelValue): void
}

// Checkbox 组件实例接口
export interface CheckboxInstance {
  checked: boolean
  indeterminate: boolean
  focus: () => void
  blur: () => void
}

// CheckboxGroup 组件实例接口
export interface CheckboxGroupInstance {
  checkAll: () => void
  uncheckAll: () => void
  isAllChecked: () => boolean
  isIndeterminate: () => boolean
}

// 事件处理器类型
export type CheckboxChangeHandler = (value: CheckboxModelValue) => void
export type CheckboxGroupChangeHandler = (value: CheckboxValueType[]) => void

// 验证函数类型
export type CheckboxValidator = (value: CheckboxModelValue) => boolean
export type CheckboxGroupValidator = (value: CheckboxValueType[]) => boolean

// 选项配置接口
export interface CheckboxOption {
  label: CheckboxValueType
  value: CheckboxValueType
  disabled?: boolean
  indeterminate?: boolean
}

// 组件配置接口
export interface CheckboxConfig {
  size?: CheckboxSize
  disabled?: boolean
  border?: boolean
  textColor?: string
  fill?: string
}

// 主题配置接口
export interface CheckboxTheme {
  primaryColor?: string
  borderColor?: string
  disabledColor?: string
  backgroundColor?: string
  textColor?: string
  hoverColor?: string
  activeColor?: string
}

// 样式类名映射
export interface CheckboxClasses {
  root: string
  input: string
  inner: string
  label: string
  disabled: string
  checked: string
  indeterminate: string
  bordered: string
  focus: string
}

// 组件状态接口
export interface CheckboxState {
  checked: boolean
  indeterminate: boolean
  disabled: boolean
  focus: boolean
  hover: boolean
}

// 组件方法接口
export interface CheckboxMethods {
  toggle: () => void
  check: () => void
  uncheck: () => void
  focus: () => void
  blur: () => void
  validate: () => boolean
}

// 组件事件接口
export interface CheckboxEvents {
  change: CheckboxChangeHandler
  focus: () => void
  blur: () => void
  click: (event: MouseEvent) => void
  keydown: (event: KeyboardEvent) => void
}

// 导出所有类型
export type {
  CheckboxValueType as Value,
  CheckboxModelValue as ModelValue,
  CheckboxSize as Size,
  CheckboxProps as Props,
  CheckboxEmits as Emits,
  CheckboxInstance as Instance,
  CheckboxChangeHandler as ChangeHandler,
  CheckboxValidator as Validator,
  CheckboxOption as Option,
  CheckboxConfig as Config,
  CheckboxTheme as Theme,
  CheckboxClasses as Classes,
  CheckboxState as State,
  CheckboxMethods as Methods,
  CheckboxEvents as Events
}