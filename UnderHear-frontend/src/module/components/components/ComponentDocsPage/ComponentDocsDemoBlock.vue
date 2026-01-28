<template>
  <div class="demo-block">
    <div class="demo-content">
      <slot />
    </div>
    <div class="demo-actions">
      <zTooltip :content="codeCopied ? copiedLabel : copyLabel" placement="bottom">
        <button class="action-btn" @click="copyCode">
          <Copy />
        </button>
      </zTooltip>
      <zTooltip :content="codeVisible ? hideCodeLabel : showCodeLabel" placement="bottom">
        <button class="action-btn" @click="codeVisible = !codeVisible">
          <Code />
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
import { zTooltip } from '@/components/z-ui/tooltip'
import { Copy, Code } from '@/components/z-ui/icon/Octicons-vue/index.ts'

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
  min-height: 160px;
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
  color: var(--font-gray);
  cursor: pointer;
  border-radius: 4px;
}

.action-btn:hover {
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
