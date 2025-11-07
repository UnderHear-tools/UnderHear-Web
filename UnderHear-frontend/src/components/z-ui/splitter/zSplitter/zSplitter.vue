<template>
  <div
    ref="splitterRef"
    :class="splitterClasses"
    :style="splitterStyles"
  >
    <div
      class="z-splitter-pane z-splitter-pane1"
      :style="pane1Styles"
    >
      <slot name="pane1" />
    </div>
    
    <div
      v-if="!disabled"
      ref="resizerRef"
      :class="resizerClasses"
      :style="resizerStyles"
      @mousedown="handleMouseDown"
      @touchstart="handleTouchStart"
    >
      <div class="z-splitter-resizer-bar" />
    </div>
    
    <div
      class="z-splitter-pane z-splitter-pane2"
      :style="pane2Styles"
    >
      <slot name="pane2" />
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted, onUnmounted } from 'vue'

interface Props {
  split?: 'vertical' | 'horizontal'
  defaultPercent?: number
  minPercent?: number
  maxPercent?: number
  disabled?: boolean
  resizerStyle?: Record<string, any>
}

interface Emits {
  resize: [percent: number]
  resizeStart: []
  resizeEnd: [percent: number]
}

const props = withDefaults(defineProps<Props>(), {
  split: 'vertical',
  defaultPercent: 50,
  minPercent: 10,
  maxPercent: 90,
  disabled: false
})

const emit = defineEmits<Emits>()

const splitterRef = ref<HTMLElement>()
const resizerRef = ref<HTMLElement>()
const currentPercent = ref(props.defaultPercent)
const isResizing = ref(false)

const splitterClasses = computed(() => {
  return [
    'z-splitter',
    `z-splitter--${props.split}`,
    {
      'z-splitter--disabled': props.disabled,
      'z-splitter--resizing': isResizing.value
    }
  ]
})

const splitterStyles = computed(() => {
  return {
    flexDirection: props.split === 'vertical' ? 'row' as const : 'column' as const
  }
})

const resizerClasses = computed(() => {
  return [
    'z-splitter-resizer',
    `z-splitter-resizer--${props.split}`
  ]
})

const resizerStyles = computed(() => {
  return {
    ...props.resizerStyle
  }
})

const pane1Styles = computed(() => {
  const percent = currentPercent.value
  if (props.split === 'vertical') {
    return {
      width: `${percent}%`,
      height: '100%'
    }
  } else {
    return {
      width: '100%',
      height: `${percent}%`
    }
  }
})

const pane2Styles = computed(() => {
  const percent = 100 - currentPercent.value
  if (props.split === 'vertical') {
    return {
      width: `${percent}%`,
      height: '100%'
    }
  } else {
    return {
      width: '100%',
      height: `${percent}%`
    }
  }
})

const handleMouseDown = (event: MouseEvent) => {
  if (props.disabled) return
  
  event.preventDefault()
  startResize(event.clientX, event.clientY)
  
  document.addEventListener('mousemove', handleMouseMove)
  document.addEventListener('mouseup', handleMouseUp)
}

const handleTouchStart = (event: TouchEvent) => {
  if (props.disabled) return
  
  event.preventDefault()
  const touch = event.touches[0]
  startResize(touch.clientX, touch.clientY)
  
  document.addEventListener('touchmove', handleTouchMove)
  document.addEventListener('touchend', handleTouchEnd)
}

const startResize = (_clientX: number, _clientY: number) => {
  isResizing.value = true
  emit('resizeStart')
  
  // 添加全局样式防止选择文本
  document.body.style.userSelect = 'none'
  document.body.style.cursor = props.split === 'vertical' ? 'col-resize' : 'row-resize'
}

const handleMouseMove = (event: MouseEvent) => {
  if (!isResizing.value) return
  updatePercent(event.clientX, event.clientY)
}

const handleTouchMove = (event: TouchEvent) => {
  if (!isResizing.value) return
  const touch = event.touches[0]
  updatePercent(touch.clientX, touch.clientY)
}

const updatePercent = (clientX: number, clientY: number) => {
  if (!splitterRef.value) return
  
  const rect = splitterRef.value.getBoundingClientRect()
  let percent: number
  
  if (props.split === 'vertical') {
    const offset = clientX - rect.left
    percent = (offset / rect.width) * 100
  } else {
    const offset = clientY - rect.top
    percent = (offset / rect.height) * 100
  }
  
  // 限制在最小和最大值之间
  percent = Math.max(props.minPercent, Math.min(props.maxPercent, percent))
  
  currentPercent.value = percent
  emit('resize', percent)
}

const handleMouseUp = () => {
  endResize()
  document.removeEventListener('mousemove', handleMouseMove)
  document.removeEventListener('mouseup', handleMouseUp)
}

const handleTouchEnd = () => {
  endResize()
  document.removeEventListener('touchmove', handleTouchMove)
  document.removeEventListener('touchend', handleTouchEnd)
}

const endResize = () => {
  isResizing.value = false
  emit('resizeEnd', currentPercent.value)
  
  // 恢复全局样式
  document.body.style.userSelect = ''
  document.body.style.cursor = ''
}

onMounted(() => {
  // 确保初始值在有效范围内
  currentPercent.value = Math.max(
    props.minPercent,
    Math.min(props.maxPercent, props.defaultPercent)
  )
})

onUnmounted(() => {
  // 清理事件监听器
  document.removeEventListener('mousemove', handleMouseMove)
  document.removeEventListener('mouseup', handleMouseUp)
  document.removeEventListener('touchmove', handleTouchMove)
  document.removeEventListener('touchend', handleTouchEnd)
})
</script>

<style scoped>
.z-splitter {
  display: flex;
  width: 100%;
  height: 100%;
  position: relative;
}

.z-splitter-pane {
  overflow: hidden;
  position: relative;
}

.z-splitter-resizer {
  background-color: #e5e7eb;
  position: relative;
  z-index: 1;
  transition: background-color 0.2s ease;
}

.z-splitter-resizer:hover {
  background-color: #d1d5db;
}

.z-splitter-resizer--vertical {
  width: 4px;
  cursor: col-resize;
  min-width: 4px;
}

.z-splitter-resizer--horizontal {
  height: 4px;
  cursor: row-resize;
  min-height: 4px;
}

.z-splitter-resizer-bar {
  position: absolute;
  background-color: #9ca3af;
  border-radius: 2px;
  opacity: 0;
  transition: opacity 0.2s ease;
}

.z-splitter-resizer--vertical .z-splitter-resizer-bar {
  width: 2px;
  height: 20px;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
}

.z-splitter-resizer--horizontal .z-splitter-resizer-bar {
  width: 20px;
  height: 2px;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
}

.z-splitter-resizer:hover .z-splitter-resizer-bar {
  opacity: 1;
}

.z-splitter--resizing {
  user-select: none;
}

.z-splitter--resizing .z-splitter-resizer {
  background-color: #3b82f6;
}

.z-splitter--resizing .z-splitter-resizer-bar {
  opacity: 1;
  background-color: #ffffff;
}

.z-splitter--disabled .z-splitter-resizer {
  display: none;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .z-splitter-resizer--vertical {
    width: 6px;
    min-width: 6px;
  }
  
  .z-splitter-resizer--horizontal {
    height: 6px;
    min-height: 6px;
  }
  
  .z-splitter-resizer--vertical .z-splitter-resizer-bar {
    width: 3px;
    height: 30px;
  }
  
  .z-splitter-resizer--horizontal .z-splitter-resizer-bar {
    width: 30px;
    height: 3px;
  }
}

/* 高对比度模式支持 */
@media (prefers-contrast: high) {
  .z-splitter-resizer {
    background-color: #000000;
  }
  
  .z-splitter-resizer:hover {
    background-color: #333333;
  }
}
</style>