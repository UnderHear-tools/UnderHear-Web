<template>
  <component
    :is="tag"
    :class="textClasses"
    :style="textStyles"
  >
    <slot />
  </component>
</template>

<script setup lang="ts">
import { computed } from 'vue'

interface Props {
  type?: 'default' | 'primary' | 'success' | 'warning' | 'danger' | 'info'
  size?: 'large' | 'default' | 'small'
  tag?: string
  truncated?: boolean
  lineClamp?: number
  underline?: boolean
  lineThrough?: boolean
  strong?: boolean
  italic?: boolean
  code?: boolean
}

const props = withDefaults(defineProps<Props>(), {
  type: 'default',
  size: 'default',
  tag: 'span',
  truncated: false,
  underline: false,
  lineThrough: false,
  strong: false,
  italic: false,
  code: false
})

const textClasses = computed(() => {
  return [
    'z-text',
    `z-text--${props.type}`,
    `z-text--${props.size}`,
    {
      'z-text--truncated': props.truncated,
      'z-text--line-clamp': props.lineClamp,
      'z-text--underline': props.underline,
      'z-text--line-through': props.lineThrough,
      'z-text--strong': props.strong,
      'z-text--italic': props.italic,
      'z-text--code': props.code
    }
  ]
})

const textStyles = computed(() => {
  const styles: Record<string, any> = {}
  
  if (props.lineClamp) {
    styles.display = '-webkit-box'
    styles.webkitBoxOrient = 'vertical'
    styles.webkitLineClamp = props.lineClamp
    styles.overflow = 'hidden'
  }
  
  return styles
})
</script>

<style scoped>
.z-text {
  display: inline;
  font-size: 14px;
  line-height: 1.5;
  color: #374151;
  transition: all 0.2s ease;
}

/* 类型样式 */
.z-text--default {
  color: #374151;
}

.z-text--primary {
  color: #3b82f6;
}

.z-text--success {
  color: #10b981;
}

.z-text--warning {
  color: #f59e0b;
}

.z-text--danger {
  color: #ef4444;
}

.z-text--info {
  color: #6b7280;
}

/* 尺寸样式 */
.z-text--large {
  font-size: 16px;
}

.z-text--default {
  font-size: 14px;
}

.z-text--small {
  font-size: 12px;
}

/* 截断样式 */
.z-text--truncated {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.z-text--line-clamp {
  word-break: break-word;
}

/* 装饰样式 */
.z-text--underline {
  text-decoration: underline;
}

.z-text--line-through {
  text-decoration: line-through;
}

.z-text--strong {
  font-weight: 600;
}

.z-text--italic {
  font-style: italic;
}

.z-text--code {
  font-family: 'Monaco', 'Menlo', 'Ubuntu Mono', monospace;
  background: #f3f4f6;
  padding: 2px 6px;
  border-radius: 4px;
  font-size: 0.9em;
  color: #dc2626;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .z-text--large {
    font-size: 15px;
  }
  
  .z-text--default {
    font-size: 13px;
  }
  
  .z-text--small {
    font-size: 11px;
  }
}
</style>