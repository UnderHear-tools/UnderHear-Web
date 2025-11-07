<template>
  <div 
    class="z-cascader"
    :class="[
      `z-cascader--${size}`,
      {
        'z-cascader--disabled': disabled,
        'z-cascader--clearable': clearable && !disabled,
        'z-cascader--filterable': filterable,
        'z-cascader--multiple': isMultiple,
        'z-cascader--focus': visible
      }
    ]"
    @click="handleClick"
    @mouseenter="handleMouseEnter"
    @mouseleave="handleMouseLeave"
  >
    <div class="z-cascader__tags" v-if="isMultiple && selectedNodes.length">
      <span 
        v-for="node in displayTags"
        :key="node.uid"
        class="z-cascader__tag"
      >
        {{ getNodeText(node) }}
        <i 
          class="z-cascader__tag-close"
          @click.stop="deleteTag(node)"
        >×</i>
      </span>
      <span 
        v-if="collapseTags && selectedNodes.length > 1"
        class="z-cascader__tag z-cascader__tag--info"
      >
        +{{ selectedNodes.length - 1 }}
      </span>
    </div>
    
    <div class="z-cascader__input-wrapper">
      <input
        ref="inputRef"
        class="z-cascader__input"
        :class="{
          'z-cascader__input--readonly': !filterable,
          'z-cascader__input--focus': visible
        }"
        :value="displayValue"
        :placeholder="currentPlaceholder"
        :readonly="!filterable"
        :disabled="disabled"
        @input="handleInput"
        @focus="handleFocus"
        @blur="handleBlur"
        @keydown="handleKeyDown"
        autocomplete="off"
      />
      
      <div class="z-cascader__suffix">
        <i 
          v-if="clearable && !disabled && hasValue && showClearIcon"
          class="z-cascader__clear-icon"
          @click.stop="handleClear"
        >×</i>
        <i 
          class="z-cascader__arrow-icon"
          :class="{ 'z-cascader__arrow-icon--reverse': visible }"
        >▼</i>
      </div>
    </div>
    
    <transition name="z-cascader-dropdown">
      <div 
        v-show="visible"
        class="z-cascader__dropdown"
        :class="popperClass"
      >
        <div class="z-cascader__panel">
          <div 
            v-for="(menu, menuIndex) in menus"
            :key="menuIndex"
            class="z-cascader__menu"
          >
            <div class="z-cascader__menu-list">
              <div 
                v-for="node in menu"
                :key="node.uid"
                class="z-cascader__node"
                :class="{
                  'z-cascader__node--selectable': !node.disabled,
                  'z-cascader__node--disabled': node.disabled,
                  'z-cascader__node--active': node.checked,
                  'z-cascader__node--expanded': node.expanded
                }"
                @click="handleNodeClick(node)"
                @mouseenter="handleNodeHover(node)"
              >
                <div class="z-cascader__node-content">
                  <label v-if="isMultiple" class="z-cascader__node-checkbox">
                    <input
                      type="checkbox"
                      :checked="node.checked"
                      :indeterminate="node.indeterminate"
                      :disabled="node.disabled"
                      @change="handleCheckChange(node, $event)"
                    />
                    <span class="z-cascader__checkbox-inner"></span>
                  </label>
                  
                  <slot :node="node" :data="node.data">
                    <span class="z-cascader__node-label">{{ node.label }}</span>
                  </slot>
                  
                  <i 
                    v-if="!node.isLeaf"
                    class="z-cascader__node-arrow"
                  >▶</i>
                </div>
              </div>
              
              <div v-if="menu.length === 0" class="z-cascader__empty">
                <slot name="empty">暂无数据</slot>
              </div>
            </div>
          </div>
        </div>
        
        <div v-if="filterable && filterText" class="z-cascader__suggestion-panel">
          <div class="z-cascader__suggestion-list">
            <div 
              v-for="node in filteredNodes"
              :key="node.uid"
              class="z-cascader__suggestion-item"
              :class="{
                'z-cascader__suggestion-item--active': node.checked
              }"
              @click="handleSuggestionClick(node)"
            >
              <span class="z-cascader__suggestion-text">{{ getSuggestionText(node) }}</span>
            </div>
            
            <div v-if="filteredNodes.length === 0" class="z-cascader__empty">
              <slot name="empty">无匹配数据</slot>
            </div>
          </div>
        </div>
      </div>
    </transition>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, watch, nextTick, onMounted, onUnmounted } from 'vue'

interface CascaderOption {
  value: string | number
  label: string
  children?: CascaderOption[]
  disabled?: boolean
  isLeaf?: boolean
  [key: string]: any
}

interface CascaderProps {
  expandTrigger?: 'click' | 'hover'
  multiple?: boolean
  checkStrictly?: boolean
  lazy?: boolean
  lazyLoad?: (node: any, resolve: (data: CascaderOption[]) => void) => void
  value?: string
  label?: string
  children?: string
  disabled?: string
  leaf?: string
}

interface CascaderNode {
  uid: string
  level: number
  value: string | number
  label: string
  data: CascaderOption
  parent?: CascaderNode
  children?: CascaderNode[]
  isLeaf: boolean
  disabled: boolean
  checked: boolean
  indeterminate: boolean
  expanded: boolean
  loading: boolean
}

interface Props {
  modelValue?: any
  options?: CascaderOption[]
  props?: CascaderProps
  size?: 'large' | 'default' | 'small'
  placeholder?: string
  disabled?: boolean
  clearable?: boolean
  showAllLevels?: boolean
  collapseTags?: boolean
  separator?: string
  filterable?: boolean
  filterMethod?: (node: CascaderNode, keyword: string) => boolean
  debounce?: number
  beforeFilter?: (value: string) => boolean | Promise<boolean>
  popperClass?: string
  expandTrigger?: 'click' | 'hover'
}

interface Emits {
  (e: 'update:modelValue', value: any): void
  (e: 'change', value: any): void
  (e: 'expandChange', value: any[]): void
  (e: 'blur', event: Event): void
  (e: 'focus', event: Event): void
  (e: 'visibleChange', visible: boolean): void
  (e: 'removeTag', value: any): void
}

interface Slots {
  default(props: { node: CascaderNode; data: CascaderOption }): any
  empty(): any
}

const props = withDefaults(defineProps<Props>(), {
  options: () => [],
  props: () => ({}),
  size: 'default',
  placeholder: '请选择',
  disabled: false,
  clearable: false,
  showAllLevels: true,
  collapseTags: false,
  separator: ' / ',
  filterable: false,
  debounce: 300,
  popperClass: '',
  expandTrigger: 'click'
})

const emit = defineEmits<Emits>()
defineSlots<Slots>()

// Refs
const inputRef = ref<HTMLInputElement>()
const visible = ref(false)
const filterText = ref('')
const showClearIcon = ref(false)
const debounceTimer = ref<number>()

// Computed
const cascaderProps = computed(() => ({
  expandTrigger: props.expandTrigger,
  multiple: false,
  checkStrictly: false,
  lazy: false,
  value: 'value',
  label: 'label',
  children: 'children',
  disabled: 'disabled',
  leaf: 'leaf',
  ...props.props
}))

const isMultiple = computed(() => cascaderProps.value.multiple)

const currentPlaceholder = computed(() => {
  if (isMultiple.value && selectedNodes.value.length > 0) {
    return ''
  }
  return props.placeholder
})

const hasValue = computed(() => {
  if (isMultiple.value) {
    return selectedNodes.value.length > 0
  }
  return props.modelValue !== undefined && props.modelValue !== null && props.modelValue !== ''
})

// State
const allNodes = ref<CascaderNode[]>([])
const selectedNodes = ref<CascaderNode[]>([])
const expandedNodes = ref<CascaderNode[]>([])
const menus = ref<CascaderNode[][]>([])
const filteredNodes = ref<CascaderNode[]>([])

// Node management
let nodeIdSeed = 0

const createNode = (option: CascaderOption, parent?: CascaderNode): CascaderNode => {
  const node: CascaderNode = {
    uid: `cascader-node-${++nodeIdSeed}`,
    level: parent ? parent.level + 1 : 0,
    value: option[cascaderProps.value.value as keyof CascaderOption] as string | number,
    label: option[cascaderProps.value.label as keyof CascaderOption] as string,
    data: option,
    parent,
    children: [],
    isLeaf: false,
    disabled: !!option[cascaderProps.value.disabled as keyof CascaderOption],
    checked: false,
    indeterminate: false,
    expanded: false,
    loading: false
  }

  // Determine if node is leaf
  if (cascaderProps.value.lazy) {
    node.isLeaf = !!option[cascaderProps.value.leaf as keyof CascaderOption]
  } else {
    const children = option[cascaderProps.value.children as keyof CascaderOption] as CascaderOption[]
    node.isLeaf = !children || children.length === 0
  }

  return node
}

const buildNodeTree = (options: CascaderOption[], parent?: CascaderNode): CascaderNode[] => {
  return options.map(option => {
    const node = createNode(option, parent)
    
    if (!node.isLeaf && !cascaderProps.value.lazy) {
      const children = option[cascaderProps.value.children as keyof CascaderOption] as CascaderOption[]
      if (children && children.length > 0) {
        node.children = buildNodeTree(children, node)
      }
    }
    
    allNodes.value.push(node)
    return node
  })
}

const initNodes = () => {
  allNodes.value = []
  nodeIdSeed = 0
  buildNodeTree(props.options)
  updateMenus()
}

const updateMenus = () => {
  const newMenus: CascaderNode[][] = []
  
  // First level
  const rootNodes = allNodes.value.filter(node => node.level === 0)
  if (rootNodes.length > 0) {
    newMenus.push(rootNodes)
  }
  
  // Expanded levels
  let currentLevel = 0
  while (currentLevel < 10) { // Prevent infinite loop
    const expandedAtLevel = expandedNodes.value.find(node => node.level === currentLevel)
    if (!expandedAtLevel || !expandedAtLevel.children || expandedAtLevel.children.length === 0) {
      break
    }
    newMenus.push(expandedAtLevel.children)
    currentLevel++
  }
  
  menus.value = newMenus
}

// Value handling
const getNodePath = (node: CascaderNode): (string | number)[] => {
  const path: (string | number)[] = []
  let current: CascaderNode | undefined = node
  
  while (current) {
    path.unshift(current.value)
    current = current.parent
  }
  
  return path
}

const getNodeByPath = (path: (string | number)[]): CascaderNode | undefined => {
  let current = allNodes.value.find(node => node.level === 0 && node.value === path[0])
  
  for (let i = 1; i < path.length && current; i++) {
    current = current.children?.find(child => child.value === path[i])
  }
  
  return current
}

const syncFromValue = () => {
  if (!props.modelValue) {
    selectedNodes.value = []
    expandedNodes.value = []
    updateMenus()
    return
  }
  
  if (isMultiple.value) {
    const paths = Array.isArray(props.modelValue) ? props.modelValue : [props.modelValue]
    selectedNodes.value = paths.map(path => getNodeByPath(path)).filter(Boolean) as CascaderNode[]
  } else {
    const path = Array.isArray(props.modelValue) ? props.modelValue : [props.modelValue]
    const node = getNodeByPath(path)
    selectedNodes.value = node ? [node] : []
    
    // Expand path to selected node
    if (node) {
      const expandPath: CascaderNode[] = []
      let current: CascaderNode | undefined = node.parent
      while (current) {
        expandPath.unshift(current)
        current = current.parent
      }
      expandedNodes.value = expandPath
    }
  }
  
  updateMenus()
}

const syncToValue = () => {
  if (isMultiple.value) {
    const paths = selectedNodes.value.map(node => getNodePath(node))
    emit('update:modelValue', paths)
    emit('change', paths)
  } else {
    const path = selectedNodes.value.length > 0 ? getNodePath(selectedNodes.value[0]) : []
    emit('update:modelValue', path)
    emit('change', path)
  }
}

// Display value
const getNodeText = (node: CascaderNode): string => {
  if (props.showAllLevels) {
    const path: string[] = []
    let current: CascaderNode | undefined = node
    
    while (current) {
      path.unshift(current.label)
      current = current.parent
    }
    
    return path.join(props.separator)
  }
  
  return node.label
}

const displayValue = computed(() => {
  if (props.filterable && filterText.value) {
    return filterText.value
  }
  
  if (isMultiple.value) {
    return ''
  }
  
  if (selectedNodes.value.length > 0) {
    return getNodeText(selectedNodes.value[0])
  }
  
  return ''
})

const displayTags = computed(() => {
  if (!isMultiple.value) return []
  
  if (props.collapseTags && selectedNodes.value.length > 1) {
    return [selectedNodes.value[0]]
  }
  
  return selectedNodes.value
})

// Event handlers
const handleClick = () => {
  if (props.disabled) return
  
  if (props.filterable) {
    inputRef.value?.focus()
  } else {
    toggleDropdown()
  }
}

const handleFocus = (event: Event) => {
  if (!visible.value) {
    toggleDropdown(true)
  }
  emit('focus', event)
}

const handleBlur = (event: Event) => {
  // Don't close dropdown immediately to allow for clicks
  setTimeout(() => {
    if (!visible.value) return
    toggleDropdown(false)
  }, 200)
  emit('blur', event)
}

const handleInput = (event: Event) => {
  const target = event.target as HTMLInputElement
  const value = target.value
  
  if (debounceTimer.value) {
    clearTimeout(debounceTimer.value)
  }
  
  debounceTimer.value = setTimeout(() => {
    handleFilter(value)
  }, props.debounce) as unknown as number
}

const handleFilter = async (value: string) => {
  filterText.value = value
  
  if (!value) {
    filteredNodes.value = []
    return
  }
  
  if (props.beforeFilter) {
    try {
      const result = await props.beforeFilter(value)
      if (!result) return
    } catch {
      return
    }
  }
  
  const filterMethod = props.filterMethod || defaultFilterMethod
  filteredNodes.value = allNodes.value.filter(node => filterMethod(node, value))
}

const defaultFilterMethod = (node: CascaderNode, keyword: string): boolean => {
  return node.label.toLowerCase().includes(keyword.toLowerCase())
}

const handleKeyDown = (event: KeyboardEvent) => {
  // Handle keyboard navigation
  switch (event.key) {
    case 'Escape':
      toggleDropdown(false)
      break
    case 'Enter':
      event.preventDefault()
      // Handle enter key
      break
    case 'ArrowDown':
    case 'ArrowUp':
      event.preventDefault()
      // Handle arrow navigation
      break
  }
}

const handleNodeClick = (node: CascaderNode) => {
  if (node.disabled) return
  
  if (isMultiple.value) {
    handleCheckChange(node, { target: { checked: !node.checked } } as unknown as Event)
  } else {
    if (cascaderProps.value.checkStrictly || node.isLeaf) {
      selectedNodes.value = [node]
      syncToValue()
      toggleDropdown(false)
    } else {
      expandNode(node)
    }
  }
}

const handleNodeHover = (node: CascaderNode) => {
  if (cascaderProps.value.expandTrigger === 'hover' && !node.isLeaf && !node.disabled) {
    expandNode(node)
  }
}

const expandNode = (node: CascaderNode) => {
  if (node.isLeaf || node.disabled) return
  
  // Remove expanded nodes at same or deeper level
  expandedNodes.value = expandedNodes.value.filter(n => n.level < node.level)
  
  // Add current node to expanded
  expandedNodes.value.push(node)
  node.expanded = true
  
  // Load children if lazy
  if (cascaderProps.value.lazy && (!node.children || node.children.length === 0)) {
    loadChildren(node)
  }
  
  updateMenus()
  
  // Emit expand change
  const expandedValues = expandedNodes.value.map(n => getNodePath(n))
  emit('expandChange', expandedValues)
}

const loadChildren = async (node: CascaderNode) => {
  if (!cascaderProps.value.lazyLoad) return
  
  node.loading = true
  
  return new Promise<void>((resolve) => {
    cascaderProps.value.lazyLoad!(node, (children: CascaderOption[]) => {
      node.children = buildNodeTree(children, node)
      node.loading = false
      updateMenus()
      resolve()
    })
  })
}

const handleCheckChange = (node: CascaderNode, event: Event) => {
  if (!isMultiple.value) return
  
  const target = event.target as HTMLInputElement
  const checked = target.checked
  
  if (checked) {
    if (!selectedNodes.value.includes(node)) {
      selectedNodes.value.push(node)
    }
  } else {
    const index = selectedNodes.value.indexOf(node)
    if (index > -1) {
      selectedNodes.value.splice(index, 1)
    }
  }
  
  node.checked = checked
  syncToValue()
}

const handleSuggestionClick = (node: CascaderNode) => {
  if (isMultiple.value) {
    handleCheckChange(node, { target: { checked: !node.checked } } as unknown as Event)
  } else {
    selectedNodes.value = [node]
    syncToValue()
    toggleDropdown(false)
    filterText.value = ''
  }
}

const getSuggestionText = (node: CascaderNode): string => {
  return getNodeText(node)
}

const deleteTag = (node: CascaderNode) => {
  const index = selectedNodes.value.indexOf(node)
  if (index > -1) {
    selectedNodes.value.splice(index, 1)
    node.checked = false
    syncToValue()
    emit('removeTag', getNodePath(node))
  }
}

const handleClear = () => {
  selectedNodes.value = []
  expandedNodes.value = []
  filterText.value = ''
  
  // Reset all node states
  allNodes.value.forEach(node => {
    node.checked = false
    node.expanded = false
  })
  
  syncToValue()
  updateMenus()
}

const handleMouseEnter = () => {
  if (props.clearable && !props.disabled && hasValue.value) {
    showClearIcon.value = true
  }
}

const handleMouseLeave = () => {
  showClearIcon.value = false
}

const toggleDropdown = (show?: boolean) => {
  const newVisible = show !== undefined ? show : !visible.value
  
  if (newVisible === visible.value) return
  
  visible.value = newVisible
  emit('visibleChange', newVisible)
  
  if (newVisible) {
    nextTick(() => {
      // Focus input if filterable
      if (props.filterable) {
        inputRef.value?.focus()
      }
    })
  } else {
    // Clear filter when closing
    if (props.filterable) {
      filterText.value = ''
      filteredNodes.value = []
    }
  }
}

// Public methods
const getCheckedNodes = (leafOnly = false) => {
  if (leafOnly) {
    return selectedNodes.value.filter(node => node.isLeaf)
  }
  return selectedNodes.value
}

// Expose public methods
defineExpose({
  getCheckedNodes
})

// Watchers
watch(() => props.options, initNodes, { immediate: true, deep: true })
watch(() => props.modelValue, syncFromValue, { immediate: true })

// Click outside to close
const handleClickOutside = (event: Event) => {
  const target = event.target as Element
  const cascaderEl = (event.currentTarget as Element)?.closest('.z-cascader')
  
  if (!cascaderEl?.contains(target)) {
    toggleDropdown(false)
  }
}

onMounted(() => {
  document.addEventListener('click', handleClickOutside)
})

onUnmounted(() => {
  document.removeEventListener('click', handleClickOutside)
  if (debounceTimer.value) {
    clearTimeout(debounceTimer.value)
  }
})
</script>

<style scoped>
.z-cascader {
  position: relative;
  display: inline-block;
  width: 100%;
  max-width: 300px;
  font-size: 14px;
  line-height: 1.5;
  color: #606266;
}

.z-cascader--large {
  font-size: 16px;
}

.z-cascader--small {
  font-size: 12px;
}

.z-cascader--disabled {
  cursor: not-allowed;
  opacity: 0.6;
}

.z-cascader--focus {
  box-shadow: 0 0 0 2px rgba(64, 158, 255, 0.2);
}

.z-cascader__tags {
  display: flex;
  flex-wrap: wrap;
  gap: 4px;
  margin-bottom: 4px;
}

.z-cascader__tag {
  display: inline-flex;
  align-items: center;
  padding: 2px 8px;
  background-color: #f0f2f5;
  border: 1px solid #d9d9d9;
  border-radius: 4px;
  font-size: 12px;
  color: #666;
  max-width: 150px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.z-cascader__tag--info {
  background-color: #909399;
  color: #fff;
  border-color: #909399;
}

.z-cascader__tag-close {
  margin-left: 4px;
  cursor: pointer;
  font-style: normal;
  font-weight: bold;
  opacity: 0.7;
  transition: opacity 0.2s;
}

.z-cascader__tag-close:hover {
  opacity: 1;
}

.z-cascader__input-wrapper {
  position: relative;
  display: flex;
  align-items: center;
}

.z-cascader__input {
  width: 100%;
  padding: 8px 32px 8px 12px;
  border: 1px solid #dcdfe6;
  border-radius: 4px;
  font-size: inherit;
  color: #606266;
  background-color: #fff;
  transition: border-color 0.2s;
  outline: none;
}

.z-cascader--large .z-cascader__input {
  padding: 10px 36px 10px 16px;
}

.z-cascader--small .z-cascader__input {
  padding: 6px 28px 6px 8px;
}

.z-cascader__input:hover {
  border-color: #c0c4cc;
}

.z-cascader__input--focus,
.z-cascader__input:focus {
  border-color: #409eff;
}

.z-cascader__input--readonly {
  cursor: pointer;
}

.z-cascader__input:disabled {
  background-color: #f5f7fa;
  border-color: #e4e7ed;
  color: #c0c4cc;
  cursor: not-allowed;
}

.z-cascader__suffix {
  position: absolute;
  right: 8px;
  display: flex;
  align-items: center;
  gap: 4px;
}

.z-cascader__clear-icon,
.z-cascader__arrow-icon {
  cursor: pointer;
  font-style: normal;
  color: #c0c4cc;
  transition: color 0.2s, transform 0.2s;
}

.z-cascader__clear-icon:hover {
  color: #909399;
}

.z-cascader__arrow-icon--reverse {
  transform: rotate(180deg);
}

.z-cascader__dropdown {
  position: absolute;
  top: 100%;
  left: 0;
  right: 0;
  z-index: 1000;
  margin-top: 4px;
  background: #fff;
  border: 1px solid #e4e7ed;
  border-radius: 4px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  max-height: 300px;
  overflow: hidden;
}

.z-cascader__panel {
  display: flex;
  min-height: 200px;
}

.z-cascader__menu {
  flex: 1;
  min-width: 160px;
  border-right: 1px solid #e4e7ed;
}

.z-cascader__menu:last-child {
  border-right: none;
}

.z-cascader__menu-list {
  max-height: 300px;
  overflow-y: auto;
}

.z-cascader__node {
  display: flex;
  align-items: center;
  padding: 8px 12px;
  cursor: pointer;
  transition: background-color 0.2s;
  border-bottom: 1px solid #f5f7fa;
}

.z-cascader__node:last-child {
  border-bottom: none;
}

.z-cascader__node:hover {
  background-color: #f5f7fa;
}

.z-cascader__node--active {
  background-color: #ecf5ff;
  color: #409eff;
  font-weight: 600;
}

.z-cascader__node--disabled {
  color: #c0c4cc;
  cursor: not-allowed;
  background-color: transparent;
}

.z-cascader__node--disabled:hover {
  background-color: transparent;
}

.z-cascader__node-content {
  display: flex;
  align-items: center;
  width: 100%;
  gap: 8px;
}

.z-cascader__node-checkbox {
  display: flex;
  align-items: center;
  cursor: pointer;
}

.z-cascader__node-checkbox input[type="checkbox"] {
  position: absolute;
  opacity: 0;
  width: 0;
  height: 0;
}

.z-cascader__checkbox-inner {
  display: inline-block;
  width: 14px;
  height: 14px;
  border: 1px solid #dcdfe6;
  border-radius: 2px;
  background-color: #fff;
  position: relative;
  transition: all 0.2s;
}

.z-cascader__checkbox-inner::after {
  content: '';
  position: absolute;
  left: 4px;
  top: 1px;
  width: 4px;
  height: 8px;
  border: 2px solid #fff;
  border-left: 0;
  border-top: 0;
  transform: rotate(45deg) scale(0);
  transition: transform 0.2s;
}

.z-cascader__node-checkbox input:checked + .z-cascader__checkbox-inner {
  background-color: #409eff;
  border-color: #409eff;
}

.z-cascader__node-checkbox input:checked + .z-cascader__checkbox-inner::after {
  transform: rotate(45deg) scale(1);
}

.z-cascader__node-checkbox input:indeterminate + .z-cascader__checkbox-inner {
  background-color: #409eff;
  border-color: #409eff;
}

.z-cascader__node-checkbox input:indeterminate + .z-cascader__checkbox-inner::after {
  content: '';
  position: absolute;
  left: 2px;
  top: 5px;
  width: 8px;
  height: 2px;
  background-color: #fff;
  border: none;
  transform: scale(1);
}

.z-cascader__node-label {
  flex: 1;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.z-cascader__node-arrow {
  color: #c0c4cc;
  font-style: normal;
  font-size: 12px;
}

.z-cascader__suggestion-panel {
  border-top: 1px solid #e4e7ed;
}

.z-cascader__suggestion-list {
  max-height: 200px;
  overflow-y: auto;
}

.z-cascader__suggestion-item {
  padding: 8px 12px;
  cursor: pointer;
  transition: background-color 0.2s;
  border-bottom: 1px solid #f5f7fa;
}

.z-cascader__suggestion-item:last-child {
  border-bottom: none;
}

.z-cascader__suggestion-item:hover {
  background-color: #f5f7fa;
}

.z-cascader__suggestion-item--active {
  background-color: #ecf5ff;
  color: #409eff;
}

.z-cascader__suggestion-text {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.z-cascader__empty {
  padding: 20px;
  text-align: center;
  color: #909399;
  font-size: 12px;
}

/* Transitions */
.z-cascader-dropdown-enter-active,
.z-cascader-dropdown-leave-active {
  transition: opacity 0.2s, transform 0.2s;
}

.z-cascader-dropdown-enter-from,
.z-cascader-dropdown-leave-to {
  opacity: 0;
  transform: translateY(-4px);
}

/* Responsive */
@media (max-width: 768px) {
  .z-cascader {
    max-width: 100%;
  }
  
  .z-cascader__dropdown {
    left: -10px;
    right: -10px;
  }
  
  .z-cascader__menu {
    min-width: 120px;
  }
  
  .z-cascader__node {
    padding: 10px 8px;
  }
}

/* High contrast mode */
@media (prefers-contrast: high) {
  .z-cascader__input {
    border-width: 2px;
  }
  
  .z-cascader__node--active {
    background-color: #0066cc;
    color: #fff;
  }
}

/* Dark theme support */
@media (prefers-color-scheme: dark) {
  .z-cascader {
    color: #e4e7ed;
  }
  
  .z-cascader__input {
    background-color: #2d2d2d;
    border-color: #4c4d4f;
    color: #e4e7ed;
  }
  
  .z-cascader__input:hover {
    border-color: #606266;
  }
  
  .z-cascader__dropdown {
    background-color: #2d2d2d;
    border-color: #4c4d4f;
  }
  
  .z-cascader__menu {
    border-color: #4c4d4f;
  }
  
  .z-cascader__node {
    border-color: #363637;
  }
  
  .z-cascader__node:hover {
    background-color: #363637;
  }
  
  .z-cascader__node--active {
    background-color: #337ecc;
  }
}
</style>