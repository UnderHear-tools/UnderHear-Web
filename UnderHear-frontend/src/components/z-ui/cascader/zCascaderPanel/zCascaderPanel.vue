<template>
  <div class="z-cascader-panel">
    <div 
      v-for="(menu, menuIndex) in menus"
      :key="menuIndex"
      class="z-cascader-panel__menu"
    >
      <div class="z-cascader-panel__menu-list">
        <div 
          v-for="node in menu"
          :key="node.uid"
          class="z-cascader-panel__node"
          :class="{
            'z-cascader-panel__node--selectable': !node.disabled,
            'z-cascader-panel__node--disabled': node.disabled,
            'z-cascader-panel__node--active': node.checked,
            'z-cascader-panel__node--expanded': node.expanded
          }"
          @click="handleNodeClick(node)"
          @mouseenter="handleNodeHover(node)"
        >
          <div class="z-cascader-panel__node-content">
            <label v-if="isMultiple" class="z-cascader-panel__node-checkbox">
              <input
                type="checkbox"
                :checked="node.checked"
                :indeterminate="node.indeterminate"
                :disabled="node.disabled"
                @change="handleCheckChange(node, $event)"
              />
              <span class="z-cascader-panel__checkbox-inner"></span>
            </label>
            
            <slot :node="node" :data="node.data">
              <span class="z-cascader-panel__node-label">{{ node.label }}</span>
            </slot>
            
            <i 
              v-if="!node.isLeaf"
              class="z-cascader-panel__node-arrow"
            >▶</i>
          </div>
        </div>
        
        <div v-if="menu.length === 0" class="z-cascader-panel__empty">
          <slot name="empty">暂无数据</slot>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, watch, onMounted } from 'vue'

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
  expandTrigger?: 'click' | 'hover'
}

interface Emits {
  (e: 'update:modelValue', value: any): void
  (e: 'change', value: any): void
  (e: 'expandChange', value: any[]): void
}

const props = withDefaults(defineProps<Props>(), {
  options: () => [],
  props: () => ({}),
  expandTrigger: 'click'
})

const emit = defineEmits<Emits>()

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

// State
const allNodes = ref<CascaderNode[]>([])
const selectedNodes = ref<CascaderNode[]>([])
const expandedNodes = ref<CascaderNode[]>([])
const menus = ref<CascaderNode[][]>([])

// Node management
let nodeIdSeed = 0

const createNode = (option: CascaderOption, parent?: CascaderNode): CascaderNode => {
  const node: CascaderNode = {
    uid: `cascader-panel-node-${++nodeIdSeed}`,
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

// Event handlers
const handleNodeClick = (node: CascaderNode) => {
  if (node.disabled) return
  
  if (isMultiple.value) {
    handleCheckChange(node, { target: { checked: !node.checked } } as any)
  } else {
    if (cascaderProps.value.checkStrictly || node.isLeaf) {
      selectedNodes.value = [node]
      syncToValue()
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
  const target = event.target as HTMLInputElement
  const checked = target.checked
  if (!isMultiple.value) return
  
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

// Watchers
watch(() => props.options, initNodes, { immediate: true, deep: true })
watch(() => props.modelValue, syncFromValue, { immediate: true })

// Initialize
onMounted(() => {
  initNodes()
})
</script>

<style scoped>
.z-cascader-panel {
  display: flex;
  min-height: 200px;
  border: 1px solid #e4e7ed;
  border-radius: 4px;
  background: #fff;
  font-size: 14px;
  line-height: 1.5;
  color: #606266;
}

.z-cascader-panel__menu {
  flex: 1;
  min-width: 160px;
  border-right: 1px solid #e4e7ed;
}

.z-cascader-panel__menu:last-child {
  border-right: none;
}

.z-cascader-panel__menu-list {
  max-height: 300px;
  overflow-y: auto;
}

.z-cascader-panel__node {
  display: flex;
  align-items: center;
  padding: 8px 12px;
  cursor: pointer;
  transition: background-color 0.2s;
  border-bottom: 1px solid #f5f7fa;
}

.z-cascader-panel__node:last-child {
  border-bottom: none;
}

.z-cascader-panel__node:hover {
  background-color: #f5f7fa;
}

.z-cascader-panel__node--active {
  background-color: #ecf5ff;
  color: #409eff;
  font-weight: 600;
}

.z-cascader-panel__node--disabled {
  color: #c0c4cc;
  cursor: not-allowed;
  background-color: transparent;
}

.z-cascader-panel__node--disabled:hover {
  background-color: transparent;
}

.z-cascader-panel__node-content {
  display: flex;
  align-items: center;
  width: 100%;
  gap: 8px;
}

.z-cascader-panel__node-checkbox {
  display: flex;
  align-items: center;
  cursor: pointer;
}

.z-cascader-panel__node-checkbox input[type="checkbox"] {
  position: absolute;
  opacity: 0;
  width: 0;
  height: 0;
}

.z-cascader-panel__checkbox-inner {
  display: inline-block;
  width: 14px;
  height: 14px;
  border: 1px solid #dcdfe6;
  border-radius: 2px;
  background-color: #fff;
  position: relative;
  transition: all 0.2s;
}

.z-cascader-panel__checkbox-inner::after {
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

.z-cascader-panel__node-checkbox input:checked + .z-cascader-panel__checkbox-inner {
  background-color: #409eff;
  border-color: #409eff;
}

.z-cascader-panel__node-checkbox input:checked + .z-cascader-panel__checkbox-inner::after {
  transform: rotate(45deg) scale(1);
}

.z-cascader-panel__node-checkbox input:indeterminate + .z-cascader-panel__checkbox-inner {
  background-color: #409eff;
  border-color: #409eff;
}

.z-cascader-panel__node-checkbox input:indeterminate + .z-cascader-panel__checkbox-inner::after {
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

.z-cascader-panel__node-label {
  flex: 1;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.z-cascader-panel__node-arrow {
  color: #c0c4cc;
  font-style: normal;
  font-size: 12px;
}

.z-cascader-panel__empty {
  padding: 20px;
  text-align: center;
  color: #909399;
  font-size: 12px;
}

/* Responsive */
@media (max-width: 768px) {
  .z-cascader-panel {
    flex-direction: column;
  }
  
  .z-cascader-panel__menu {
    min-width: auto;
    border-right: none;
    border-bottom: 1px solid #e4e7ed;
  }
  
  .z-cascader-panel__menu:last-child {
    border-bottom: none;
  }
  
  .z-cascader-panel__node {
    padding: 10px 8px;
  }
}

/* High contrast mode */
@media (prefers-contrast: high) {
  .z-cascader-panel {
    border-width: 2px;
  }
  
  .z-cascader-panel__node--active {
    background-color: #0066cc;
    color: #fff;
  }
}

/* Dark theme support */
@media (prefers-color-scheme: dark) {
  .z-cascader-panel {
    background-color: #2d2d2d;
    border-color: #4c4d4f;
    color: #e4e7ed;
  }
  
  .z-cascader-panel__menu {
    border-color: #4c4d4f;
  }
  
  .z-cascader-panel__node {
    border-color: #363637;
  }
  
  .z-cascader-panel__node:hover {
    background-color: #363637;
  }
  
  .z-cascader-panel__node--active {
    background-color: #337ecc;
  }
}
</style>