<template>
  <div class="z-tooltip-wrapper" @mouseenter="show" @mouseleave="hide">
    <!-- 触发元素插槽 -->
    <slot></slot>
    
    <!-- Tooltip 内容 -->
    <div 
      v-if="visible" 
      class="z-tooltip"
      :class="`z-tooltip--${placement}`"
    >
      <div class="z-tooltip-content">{{ content }}</div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'

interface Props {
  /** Tooltip 显示的内容 */
  content: string
  /** Tooltip 显示位置 */
  placement?:
    | 'top'
    | 'bottom'
    | 'left'
    | 'right'
    | 'left-top'
    | 'left-bottom'
    | 'right-top'
    | 'right-bottom'
  /** 延迟显示时间（毫秒） */
  showDelay?: number
  /** 延迟隐藏时间（毫秒） */
  hideDelay?: number
  /** 是否禁用 */
  disabled?: boolean
}

const props = withDefaults(defineProps<Props>(), {
  placement: 'top',
  showDelay: 0,
  hideDelay: 0,
  disabled: false
})

const visible = ref(false)
let showTimer: number | undefined
let hideTimer: number | undefined

function show() {
  if (props.disabled) return
  
  clearTimeout(hideTimer)
  
  if (props.showDelay > 0) {
    showTimer = window.setTimeout(() => {
      visible.value = true
    }, props.showDelay)
  } else {
    visible.value = true
  }
}

function hide() {
  clearTimeout(showTimer)
  
  if (props.hideDelay > 0) {
    hideTimer = window.setTimeout(() => {
      visible.value = false
    }, props.hideDelay)
  } else {
    visible.value = false
  }
}
</script>

<style scoped>
.z-tooltip-wrapper {
  display: inline-flex;
  position: relative;
}

.z-tooltip {
  --z-tooltip-gap: 4px;
  position: absolute;
  z-index: 1000;
  padding: 0.375rem 0.625rem;
  font-size: 0.75rem;
  line-height: 1.4;
  white-space: nowrap;
  border-radius: 4px;
  pointer-events: auto;
  background: var(--bgColor-emphasis, #25292e);
  color: var(--fgColor-onEmphasis, #ffffff);
}

/* 顶部位置 */
.z-tooltip--top {
  bottom: calc(100% + var(--z-tooltip-gap));
  left: 50%;
  transform: translateX(-50%);
}

/* 底部位置 */
.z-tooltip--bottom {
  top: calc(100% + var(--z-tooltip-gap));
  left: 50%;
  transform: translateX(-50%);
}

/* 左侧位置 */
.z-tooltip--left {
  right: calc(100% + var(--z-tooltip-gap));
  top: 50%;
  transform: translateY(-50%);
}

/* 右侧位置 */
.z-tooltip--right {
  left: calc(100% + var(--z-tooltip-gap));
  top: 50%;
  transform: translateY(-50%);
}

/* 宸︿笂 */
.z-tooltip--left-top {
  bottom: calc(100% + var(--z-tooltip-gap));
  right: 0;
}

/* 宸︿笅 */
.z-tooltip--left-bottom {
  top: calc(100% + var(--z-tooltip-gap));
  right: 0;
}

/* 鍙充笂 */
.z-tooltip--right-top {
  bottom: calc(100% + var(--z-tooltip-gap));
  left: 0;
}

/* 鍙充笅 */
.z-tooltip--right-bottom {
  top: calc(100% + var(--z-tooltip-gap));
  left: 0;
}

/* 透明桥接区：跨过间隙，避免鼠标移动时触发隐藏 */
.z-tooltip--top::after,
.z-tooltip--bottom::after,
.z-tooltip--left::after,
.z-tooltip--right::after,
.z-tooltip--left-top::after,
.z-tooltip--left-bottom::after,
.z-tooltip--right-top::after,
.z-tooltip--right-bottom::after {
  content: '';
  position: absolute;
}

.z-tooltip--top::after,
.z-tooltip--left-top::after,
.z-tooltip--right-top::after {
  top: 100%;
  left: 0;
  right: 0;
  height: var(--z-tooltip-gap);
}

.z-tooltip--bottom::after,
.z-tooltip--left-bottom::after,
.z-tooltip--right-bottom::after {
  bottom: 100%;
  left: 0;
  right: 0;
  height: var(--z-tooltip-gap);
}

.z-tooltip--left::after {
  left: 100%;
  top: 0;
  bottom: 0;
  width: var(--z-tooltip-gap);
}

.z-tooltip--right::after {
  right: 100%;
  top: 0;
  bottom: 0;
  width: var(--z-tooltip-gap);
}


</style>
