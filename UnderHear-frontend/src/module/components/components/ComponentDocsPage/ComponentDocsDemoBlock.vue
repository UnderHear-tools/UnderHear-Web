<template>
  <div class="demo-block">
    <div class="demo-content">
      <slot />
    </div>
    <div class="demo-actions">
      <zTooltip :content="codeCopied ? copiedLabel : copyLabel" placement="bottom">
        <button class="action-btn" @click="copyCode">
          <svg width="16" height="16" viewBox="0 0 16 16" fill="currentColor">
            <path d="M0 6.75C0 5.784.784 5 1.75 5h1.5a.75.75 0 0 1 0 1.5h-1.5a.25.25 0 0 0-.25.25v7.5c0 .138.112.25.25.25h7.5a.25.25 0 0 0 .25-.25v-1.5a.75.75 0 0 1 1.5 0v1.5A1.75 1.75 0 0 1 9.25 16h-7.5A1.75 1.75 0 0 1 0 14.25Z"/>
            <path d="M5 1.75C5 .784 5.784 0 6.75 0h7.5C15.216 0 16 .784 16 1.75v7.5A1.75 1.75 0 0 1 14.25 11h-7.5A1.75 1.75 0 0 1 5 9.25Zm1.75-.25a.25.25 0 0 0-.25.25v7.5c0 .138.112.25.25.25h7.5a.25.25 0 0 0 .25-.25v-7.5a.25.25 0 0 0-.25-.25Z"/>
          </svg>
        </button>
      </zTooltip>
      <zTooltip :content="codeVisible ? hideCodeLabel : showCodeLabel" placement="bottom">
        <button class="action-btn" @click="codeVisible = !codeVisible">
          <svg width="16" height="16" viewBox="0 0 16 16" fill="currentColor">
            <path d="M4.72 3.22a.75.75 0 0 1 1.06 1.06L2.06 8l3.72 3.72a.75.75 0 1 1-1.06 1.06L.47 8.53a.75.75 0 0 1 0-1.06l4.25-4.25Zm6.56 0a.75.75 0 1 0-1.06 1.06L13.94 8l-3.72 3.72a.75.75 0 1 0 1.06 1.06l4.25-4.25a.75.75 0 0 0 0-1.06l-4.25-4.25Z"/>
          </svg>
        </button>
      </zTooltip>
    </div>
    <transition name="code-expand">
      <div v-if="codeVisible" class="demo-code">
        <pre :class="`language-${language}`">
          <code :class="`language-${language}`" v-html="highlightedCode"></code>
        </pre>
        <div class="code-footer">
          <button class="hide-code-btn" @click="codeVisible = false">
            {{ hideSourceLabel }}
          </button>
        </div>
      </div>
    </transition>
  </div>
</template>

<script setup lang="ts">
import { computed, ref } from 'vue'
import * as Prism from 'prismjs'
import 'prismjs/themes/prism.css'
import { zTooltip } from '@/components/z-ui/tooltip/zTooltip'

const props = withDefaults(
  defineProps<{
    code: string
    language?: string
    copyLabel?: string
    copiedLabel?: string
    showCodeLabel?: string
    hideCodeLabel?: string
    hideSourceLabel?: string
  }>(),
  {
    language: 'markup',
    copyLabel: 'Copy code',
    copiedLabel: 'Copied',
    showCodeLabel: 'Show code',
    hideCodeLabel: 'Hide code',
    hideSourceLabel: 'Hide source'
  }
)

const codeVisible = ref(false)
const codeCopied = ref(false)
const language = computed(() => props.language)
const highlightedCode = computed(() =>
  Prism.highlight(
    props.code,
    Prism.languages[props.language] || Prism.languages.markup,
    props.language
  )
)

const copyCode = () => {
  navigator.clipboard.writeText(props.code).then(() => {
    codeCopied.value = true
    window.setTimeout(() => {
      codeCopied.value = false
    }, 2000)
  })
}
</script>

<style scoped>
.demo-block {
  border: 1px solid #d1d9e0;
  border-radius: 6px;
}

.demo-content {
  padding: 2rem;
}

.demo-actions {
  display: flex;
  gap: 0;
  border-top: 1px solid #d1d9e0;
  justify-content: flex-end;
}

.action-btn {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 32px;
  height: 32px;
  border: none;
  background: transparent;
  color: var(--font-gray);
  cursor: pointer;
  border-radius: 4px;
  transition: all 0.2s ease;
}

.action-btn:hover {
  background: #e1e4e8;
  color: var(--font-black);
}

.demo-code {
  background: #f6f8fa;
  border-radius: 0 0 6px 6px;
  overflow: hidden;
}

.code-expand-enter-active,
.code-expand-leave-active {
  transition: all 0.3s ease-in-out;
  max-height: 1000px;
}

.code-expand-enter-from,
.code-expand-leave-to {
  max-height: 0;
  opacity: 0;
}

.code-footer {
  display: flex;
  justify-content: center;
  padding: 0.5rem;
  border-top: 1px solid #d1d9e0;
  background: #ffffff;
  border-radius: 0 0 6px 6px;
}

.hide-code-btn {
  display: flex;
  align-items: center;
  gap: 0.375rem;
  background: transparent;
  border: none;
  color: var(--font-gray);
  font-size: 0.875rem;
  cursor: pointer;
  border-radius: 4px;
  transition: all 0.2s ease;
}

.hide-code-btn:hover {
  color: var(--font-blue);
}

.demo-code pre[class*='language-'] {
  margin: 0;
  padding: 1.5rem;
  overflow-x: auto;
  background: transparent;
}

.demo-code code[class*='language-'] {
  display: block;
  font-family: 'Consolas', 'Monaco', 'Courier New', monospace;
  font-size: 0.875rem;
  line-height: 1.6;
  color: var(--font-black);
  white-space: pre;
  background: transparent;
}

@media (max-width: 768px) {
  .demo-content {
    padding: 1.25rem;
  }

  .action-btn {
    width: 36px;
    height: 36px;
  }

  .demo-code pre[class*='language-'] {
    padding: 1rem;
    font-size: 0.75rem;
  }

  .demo-code code[class*='language-'] {
    font-size: 0.75rem;
    line-height: 1.5;
  }

  .code-footer {
    padding: 0.625rem;
  }

  .hide-code-btn {
    font-size: 0.8125rem;
  }
}
</style>
