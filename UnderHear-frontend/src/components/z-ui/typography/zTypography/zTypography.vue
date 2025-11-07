<template>
  <component
    :is="tag"
    :class="typographyClasses"
    :style="typographyStyles"
    @click="handleClick"
  >
    <template v-if="!isEditing">
      <span
        v-if="ellipsis"
        :class="ellipsisClasses"
        :style="ellipsisStyles"
        :title="typeof ellipsis === 'object' ? undefined : content"
      >
        <slot>{{ content }}</slot>
      </span>
      <span v-else>
        <slot>{{ content }}</slot>
      </span>
      
      <!-- 复制按钮 -->
      <button
        v-if="copyable"
        class="z-typography-copy-btn"
        @click="handleCopy"
        :title="'复制'"
      >
        <slot name="copyIcon">
          <svg width="14" height="14" viewBox="0 0 24 24" fill="currentColor">
            <path d="M16 1H4c-1.1 0-2 .9-2 2v14h2V3h12V1zm3 4H8c-1.1 0-2 .9-2 2v14c0 1.1.9 2 2 2h11c1.1 0 2-.9 2-2V7c0-1.1-.9-2-2-2zm0 16H8V7h11v14z"/>
          </svg>
        </slot>
      </button>
      
      <!-- 编辑按钮 -->
      <button
        v-if="editable"
        class="z-typography-edit-btn"
        @click="startEdit"
        :title="'编辑'"
      >
        <slot name="editIcon">
          <svg width="14" height="14" viewBox="0 0 24 24" fill="currentColor">
            <path d="M3 17.25V21h3.75L17.81 9.94l-3.75-3.75L3 17.25zM20.71 7.04c.39-.39.39-1.02 0-1.41l-2.34-2.34c-.39-.39-1.02-.39-1.41 0l-1.83 1.83 3.75 3.75 1.83-1.83z"/>
          </svg>
        </slot>
      </button>
    </template>
    
    <!-- 编辑模式 -->
    <template v-else>
      <input
        v-if="tag === 'span' || tag === 'p'"
        ref="editInputRef"
        v-model="editingContent"
        class="z-typography-edit-input"
        :maxlength="editableMaxLength"
        @blur="finishEdit"
        @keydown.enter="finishEdit"
        @keydown.esc="cancelEdit"
      />
      <textarea
        v-else
        ref="editTextareaRef"
        v-model="editingContent"
        class="z-typography-edit-textarea"
        :maxlength="editableMaxLength"
        @blur="finishEdit"
        @keydown.esc="cancelEdit"
      />
    </template>
  </component>
</template>

<script setup lang="ts">
import { ref, computed, nextTick, watch } from 'vue'

interface Props {
  tag?: string
  type?: 'title' | 'body' | 'caption'
  level?: 1 | 2 | 3 | 4 | 5 | 6
  size?: 'small' | 'medium' | 'large'
  state?: 'default' | 'secondary' | 'success' | 'warning' | 'error' | 'disabled'
  bold?: boolean
  italic?: boolean
  underline?: boolean
  strikethrough?: boolean
  code?: boolean
  ellipsis?: boolean | { rows: number }
  copyable?: boolean | { text: string }
  editable?: boolean | { maxLength: number }
  content?: string
}

interface Emits {
  copy: [text: string]
  edit: [text: string]
  'update:content': [text: string]
}

const props = withDefaults(defineProps<Props>(), {
  tag: 'span',
  type: 'body',
  level: 1,
  size: 'medium',
  state: 'default',
  bold: false,
  italic: false,
  underline: false,
  strikethrough: false,
  code: false,
  ellipsis: false,
  copyable: false,
  editable: false,
  content: ''
})

const emit = defineEmits<Emits>()

const isEditing = ref(false)
const editingContent = ref('')
const editInputRef = ref<HTMLInputElement>()
const editTextareaRef = ref<HTMLTextAreaElement>()

const typographyClasses = computed(() => {
  return [
    'z-typography',
    `z-typography--${props.type}`,
    `z-typography--${props.size}`,
    `z-typography--${props.state}`,
    {
      'z-typography--bold': props.bold,
      'z-typography--italic': props.italic,
      'z-typography--underline': props.underline,
      'z-typography--strikethrough': props.strikethrough,
      'z-typography--code': props.code,
      'z-typography--copyable': props.copyable,
      'z-typography--editable': props.editable,
      'z-typography--editing': isEditing.value,
      [`z-typography--level-${props.level}`]: props.type === 'title'
    }
  ]
})

const typographyStyles = computed(() => {
  const styles: Record<string, any> = {}
  
  if (props.type === 'title') {
    const fontSizes = {
      1: '32px',
      2: '24px',
      3: '20px',
      4: '18px',
      5: '16px',
      6: '14px'
    }
    styles.fontSize = fontSizes[props.level]
  }
  
  return styles
})

const ellipsisClasses = computed(() => {
  if (!props.ellipsis) return []
  
  return [
    'z-typography-ellipsis',
    {
      'z-typography-ellipsis--single': props.ellipsis === true,
      'z-typography-ellipsis--multiple': typeof props.ellipsis === 'object'
    }
  ]
})

const ellipsisStyles = computed(() => {
  if (!props.ellipsis || props.ellipsis === true) return {}
  
  if (typeof props.ellipsis === 'object') {
    return {
      '-webkit-line-clamp': props.ellipsis.rows,
      'line-clamp': props.ellipsis.rows
    }
  }
  
  return {}
})

const copyableText = computed(() => {
  if (!props.copyable) return ''
  
  if (typeof props.copyable === 'object') {
    return props.copyable.text
  }
  
  return props.content
})

const editableMaxLength = computed(() => {
  if (!props.editable || props.editable === true) return undefined
  
  return props.editable.maxLength
})

const handleClick = (event: Event) => {
  // 阻止编辑和复制按钮的点击事件冒泡
  if ((event.target as HTMLElement).closest('.z-typography-copy-btn, .z-typography-edit-btn')) {
    event.stopPropagation()
  }
}

const handleCopy = async () => {
  try {
    await navigator.clipboard.writeText(copyableText.value)
    emit('copy', copyableText.value)
  } catch (err) {
    // 降级方案
    const textArea = document.createElement('textarea')
    textArea.value = copyableText.value
    document.body.appendChild(textArea)
    textArea.select()
    document.execCommand('copy')
    document.body.removeChild(textArea)
    emit('copy', copyableText.value)
  }
}

const startEdit = () => {
  if (!props.editable) return
  
  isEditing.value = true
  editingContent.value = props.content
  
  nextTick(() => {
    const input = editInputRef.value || editTextareaRef.value
    if (input) {
      input.focus()
      input.select()
    }
  })
}

const finishEdit = () => {
  if (!isEditing.value) return
  
  isEditing.value = false
  emit('edit', editingContent.value)
  emit('update:content', editingContent.value)
}

const cancelEdit = () => {
  isEditing.value = false
  editingContent.value = props.content
}

// 监听content变化
watch(() => props.content, (newContent) => {
  if (!isEditing.value) {
    editingContent.value = newContent
  }
})
</script>

<style scoped>
.z-typography {
  position: relative;
  display: inline;
  line-height: 1.5;
  color: #374151;
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif;
}

/* 类型样式 */
.z-typography--title {
  display: block;
  font-weight: 600;
  margin: 0;
}

.z-typography--body {
  font-weight: 400;
}

.z-typography--caption {
  font-weight: 400;
  font-size: 12px;
  color: #6b7280;
}

/* 大小样式 */
.z-typography--small {
  font-size: 12px;
}

.z-typography--medium {
  font-size: 14px;
}

.z-typography--large {
  font-size: 16px;
}

/* 状态样式 */
.z-typography--default {
  color: #374151;
}

.z-typography--secondary {
  color: #6b7280;
}

.z-typography--success {
  color: #059669;
}

.z-typography--warning {
  color: #d97706;
}

.z-typography--error {
  color: #dc2626;
}

.z-typography--disabled {
  color: #9ca3af;
  cursor: not-allowed;
}

/* 装饰样式 */
.z-typography--bold {
  font-weight: 600;
}

.z-typography--italic {
  font-style: italic;
}

.z-typography--underline {
  text-decoration: underline;
}

.z-typography--strikethrough {
  text-decoration: line-through;
}

.z-typography--code {
  font-family: 'Monaco', 'Menlo', 'Consolas', monospace;
  background-color: #f3f4f6;
  padding: 2px 6px;
  border-radius: 4px;
  font-size: 0.9em;
}

/* 省略样式 */
.z-typography-ellipsis--single {
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
}

.z-typography-ellipsis--multiple {
  display: -webkit-box;
  -webkit-box-orient: vertical;
  overflow: hidden;
  text-overflow: ellipsis;
}

/* 交互按钮 */
.z-typography--copyable,
.z-typography--editable {
  padding-right: 24px;
}

.z-typography-copy-btn,
.z-typography-edit-btn {
  position: absolute;
  right: 0;
  top: 50%;
  transform: translateY(-50%);
  background: none;
  border: none;
  cursor: pointer;
  color: #6b7280;
  padding: 2px;
  border-radius: 4px;
  transition: all 0.2s ease;
  opacity: 0;
}

.z-typography:hover .z-typography-copy-btn,
.z-typography:hover .z-typography-edit-btn {
  opacity: 1;
}

.z-typography-copy-btn:hover,
.z-typography-edit-btn:hover {
  color: #374151;
  background-color: #f3f4f6;
}

.z-typography-edit-btn {
  right: 20px;
}

/* 编辑模式 */
.z-typography-edit-input,
.z-typography-edit-textarea {
  width: 100%;
  border: 1px solid #d1d5db;
  border-radius: 4px;
  padding: 4px 8px;
  font-size: inherit;
  font-family: inherit;
  color: inherit;
  background: #ffffff;
  outline: none;
  transition: border-color 0.2s ease;
}

.z-typography-edit-input:focus,
.z-typography-edit-textarea:focus {
  border-color: #3b82f6;
  box-shadow: 0 0 0 2px rgba(59, 130, 246, 0.1);
}

.z-typography-edit-textarea {
  resize: vertical;
  min-height: 60px;
}

/* 标题级别样式 */
.z-typography--level-1 {
  font-size: 32px;
  line-height: 1.2;
}

.z-typography--level-2 {
  font-size: 24px;
  line-height: 1.3;
}

.z-typography--level-3 {
  font-size: 20px;
  line-height: 1.4;
}

.z-typography--level-4 {
  font-size: 18px;
  line-height: 1.4;
}

.z-typography--level-5 {
  font-size: 16px;
  line-height: 1.5;
}

.z-typography--level-6 {
  font-size: 14px;
  line-height: 1.5;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .z-typography--level-1 {
    font-size: 28px;
  }
  
  .z-typography--level-2 {
    font-size: 22px;
  }
  
  .z-typography--level-3 {
    font-size: 18px;
  }
}

/* 高对比度模式支持 */
@media (prefers-contrast: high) {
  .z-typography--secondary {
    color: #374151;
  }
  
  .z-typography--code {
    background-color: #e5e7eb;
    border: 1px solid #9ca3af;
  }
}
</style>