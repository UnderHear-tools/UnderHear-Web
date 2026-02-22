<template>
  <div class="z-tooltip-wrapper" @mouseenter="show" @mouseleave="hide">
    <!-- 触发元素插槽 -->
    <slot></slot>
    
    <!-- Tooltip 内容 -->
    <div 
      v-if="visible" 
      class="z-tooltip"
      :class="[
        `z-tooltip--${placement}`,
        { 'z-tooltip--dark': effect === 'dark', 'z-tooltip--light': effect === 'light' }
      ]"
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
  placement?: 'top' | 'bottom' | 'left' | 'right'
  /** Tooltip 主题 */
  effect?: 'dark' | 'light'
  /** 延迟显示时间（毫秒） */
  showDelay?: number
  /** 延迟隐藏时间（毫秒） */
  hideDelay?: number
  /** 是否禁用 */
  disabled?: boolean
}

const props = withDefaults(defineProps<Props>(), {
  placement: 'top',
  effect: 'dark',
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
  position: absolute;
  z-index: 1000;
  padding: 0.375rem 0.625rem;
  font-size: 0.75rem;
  line-height: 1.4;
  white-space: nowrap;
  border-radius: 4px;
  pointer-events: none;
}

/* 深色主题 */
.z-tooltip--dark {
  background: var(--bgColor-emphasis, #25292e);
  color: var(--fgColor-onEmphasis, #ffffff);
}

/* 浅色主题 */
.z-tooltip--light {
  background: var(--bgColor-default, #ffffff);
  color: var(--fgColor-default, #1f2328);
  border: 1px solid var(--borderColor-default, #d1d9e0);
}

/* 顶部位置 */
.z-tooltip--top {
  bottom: 100%;
  left: 50%;
  transform: translateX(-50%) translateY(-2px);
  margin-bottom: 2px;
}

/* 底部位置 */
.z-tooltip--bottom {
  top: 100%;
  left: 50%;
  transform: translateX(-50%) translateY(2px);
  margin-top: 2px;
}

/* 左侧位置 */
.z-tooltip--left {
  right: 100%;
  top: 50%;
  transform: translateY(-50%) translateX(-2px);
  margin-right: 2px;
}

/* 右侧位置 */
.z-tooltip--right {
  left: 100%;
  top: 50%;
  transform: translateY(-50%) translateX(2px);
  margin-left: 2px;
}


</style>
