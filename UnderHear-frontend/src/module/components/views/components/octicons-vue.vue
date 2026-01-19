<template>
  <div class="octicons-docs">
    <header class="page-header">
      <div>
        <h1 class="page-title">Octicons Vue 图标</h1>
        <p class="page-description">
          该图标库基于开源的
          <zLink href="https://primer.style/octicons/" link-text="Octicons" variant="primary" />
          图标库构建，为 Vue 项目提供可直接使用的图标组件。
        </p>
      </div>
      <div class="page-meta">
        <div class="meta-card">
          <span class="meta-label">图标总数</span>
          <span class="meta-value">{{ iconEntries.length }}</span>
        </div>
        <div class="meta-card">
          <span class="meta-label">预览尺寸</span>
          <span class="meta-value">{{ iconSize }}px</span>
        </div>
      </div>
    </header>

    <section class="demo-section">
      <h2 class="section-title">基础用法</h2>
      <p class="section-description">
        通过具名导入图标组件并在模板中直接使用，可用 <code>size</code> 控制图标大小。
      </p>
      <div class="demo-block">
        <div class="demo-content">
          <div class="icon-demo-row">
            <component
              v-for="icon in basicIconEntries"
              :key="icon.name"
              :is="icon.component"
              :size="32"
              class="demo-icon"
            />
          </div>
        </div>
        <div class="demo-actions">
          <zTooltip :content="demoCopied ? '已复制' : '复制代码'" placement="bottom">
            <button class="action-btn" @click="copyUsageCode">
              <svg width="16" height="16" viewBox="0 0 16 16" fill="currentColor">
                <path d="M0 6.75C0 5.784.784 5 1.75 5h1.5a.75.75 0 0 1 0 1.5h-1.5a.25.25 0 0 0-.25.25v7.5c0 .138.112.25.25.25h7.5a.25.25 0 0 0 .25-.25v-1.5a.75.75 0 0 1 1.5 0v1.5A1.75 1.75 0 0 1 9.25 16h-7.5A1.75 1.75 0 0 1 0 14.25Z"/>
                <path d="M5 1.75C5 .784 5.784 0 6.75 0h7.5C15.216 0 16 .784 16 1.75v7.5A1.75 1.75 0 0 1 14.25 11h-7.5A1.75 1.75 0 0 1 5 9.25Zm1.75-.25a.25.25 0 0 0-.25.25v7.5c0 .138.112.25.25.25h7.5a.25.25 0 0 0 .25-.25v-7.5a.25.25 0 0 0-.25-.25Z"/>
              </svg>
            </button>
          </zTooltip>
          <zTooltip :content="demoVisible ? '隐藏代码' : '查看代码'" placement="bottom">
            <button class="action-btn" @click="demoVisible = !demoVisible">
              <svg width="16" height="16" viewBox="0 0 16 16" fill="currentColor">
                <path d="M4.72 3.22a.75.75 0 0 1 1.06 1.06L2.06 8l3.72 3.72a.75.75 0 1 1-1.06 1.06L.47 8.53a.75.75 0 0 1 0-1.06l4.25-4.25Zm6.56 0a.75.75 0 1 0-1.06 1.06L13.94 8l-3.72 3.72a.75.75 0 1 0 1.06 1.06l4.25-4.25a.75.75 0 0 0 0-1.06l-4.25-4.25Z"/>
              </svg>
            </button>
          </zTooltip>
        </div>
        <transition name="code-expand">
          <div v-if="demoVisible" class="demo-code">
            <pre class="language-markup">
              <code class="language-markup" v-html="demoHighlighted"></code>
            </pre>
            <div class="code-footer">
              <button class="hide-code-btn" @click="demoVisible = false">
                隐藏源代码
              </button>
            </div>
          </div>
        </transition>
      </div>
    </section>

    <section class="icon-section">
      <h2 class="section-title">图标展示</h2>
      <div class="icon-grid">
        <div v-for="icon in iconEntries" :key="icon.name" class="icon-card">
          <component :is="icon.component" :size="iconSize" class="icon-svg" />
          <span class="icon-name">{{ icon.name }}</span>
        </div>
      </div>
    </section>
  </div>
</template>

<script setup lang="ts">
import { computed, ref } from 'vue'
import type { Component } from 'vue'
import * as Prism from 'prismjs'
import 'prismjs/themes/prism.css'
import * as octicons from '@/components/z-ui/icon/Octicons-vue/index.ts'
import { zLink } from '@/components/z-ui/link/zlink'
import { zTooltip } from '@/components/z-ui/tooltip/zTooltip'

type IconEntry = {
  name: string
  component: Component
}

const iconSize = 24
const demoVisible = ref(false)
const demoCopied = ref(false)
const usageCode = `<script setup lang="ts">
import { telescopeFill, markGithub, heartFill } from '@/components/z-ui/icon/Octicons-vue/index.ts'
</scr` + `ipt>
<template>
  <telescopeFill :size="32" />
  <markGithub :size="32" />
  <heartFill :size="32" />
</template>`
const basicIconEntries: IconEntry[] = [
  { name: 'telescopeFill', component: octicons.telescopeFill as Component },
  { name: 'markGithub', component: octicons.markGithub as Component },
  { name: 'heartFill', component: octicons.heartFill as Component }
]
const highlightCode = (code: string) =>
  Prism.highlight(code, Prism.languages.markup, 'markup')
const demoHighlighted = computed(() => highlightCode(usageCode))
const iconEntries: IconEntry[] = Object.entries(octicons)
  .filter(([name]) => name !== 'default')
  .map(([name, component]) => ({
    name,
    component: component as Component
  }))
  .sort((a, b) => a.name.localeCompare(b.name))

function copyUsageCode() {
  navigator.clipboard.writeText(usageCode).then(() => {
    demoCopied.value = true
    setTimeout(() => {
      demoCopied.value = false
    }, 2000)
  })
}
</script>

<style scoped>
.octicons-docs {
  max-width: 1200px;
  margin: 0 auto;
  width: 100%;
}

.page-header {
  display: flex;
  flex-wrap: wrap;
  gap: 1.5rem;
  align-items: flex-end;
  justify-content: space-between;
  padding-bottom: 1.5rem;
  border-bottom: 1px solid #d1d9e0;
  margin-bottom: 2rem;
}

.page-title {
  font-size: 2rem;
  font-weight: 600;
  color: var(--font-black);
  margin-bottom: 0.5rem;
}

.page-description {
  font-size: 1rem;
  color: var(--font-gray);
  margin-bottom: 2rem;
}

.page-meta {
  display: flex;
  gap: 0.75rem;
  align-items: center;
}

.meta-card {
  display: flex;
  flex-direction: column;
  gap: 0.25rem;
  padding: 0.75rem 1rem;
  border: 1px solid #d1d9e0;
  border-radius: 6px;
  background: #ffffff;
  min-width: 120px;
}

.meta-label {
  font-size: 0.75rem;
  color: var(--font-gray);
  text-transform: uppercase;
  letter-spacing: 0.06em;
}

.meta-value {
  font-size: 1.25rem;
  font-weight: 600;
  color: var(--font-black);
}

.demo-section {
  margin-bottom: 3rem;
}

.section-title {
  font-size: 1.5rem;
  font-weight: 600;
  color: var(--font-black);
  margin-bottom: 0.75rem;
}

.section-description {
  font-size: 0.875rem;
  color: var(--font-gray);
  margin-bottom: 1rem;
  line-height: 1.6;
}

.section-description code {
  padding: 0.125rem 0.375rem;
  background: #f6f8fa;
  border: 1px solid #d1d9e0;
  border-radius: 3px;
  font-family: 'Consolas', 'Monaco', 'Courier New', monospace;
  font-size: 0.875em;
  color: var(--font-black);
}

.demo-block {
  border: 1px solid #d1d9e0;
  border-radius: 6px;
}

.demo-content {
  padding: 2rem;
}

.icon-demo-row {
  display: flex;
  gap: 1.5rem;
  align-items: center;
  flex-wrap: wrap;
}

.demo-icon {
  color: var(--font-black);
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

.icon-section {
  margin-bottom: 2.5rem;
}

.icon-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(120px, 1fr));
  gap: 1rem;
}

.icon-card {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 0.5rem;
  padding: 0.9rem 0.75rem;
  border: 1px solid #d1d9e0;
  border-radius: 6px;
  background: #ffffff;
}

.icon-svg {
  color: var(--font-black);
}

.icon-name {
  font-size: 0.75rem;
  color: var(--font-gray);
  text-align: center;
  word-break: break-word;
  line-height: 1.4;
}

@media (max-width: 768px) {
  .page-title {
    font-size: 1.5rem;
    margin-bottom: 0.375rem;
  }

  .page-description {
    font-size: 0.875rem;
    margin-bottom: 1.5rem;
  }

  .demo-section {
    margin-bottom: 2rem;
  }

  .section-title {
    font-size: 1.25rem;
    margin-bottom: 0.5rem;
  }

  .section-description {
    font-size: 0.8125rem;
  }

  .demo-content {
    padding: 1.25rem;
  }

  .icon-demo-row {
    flex-direction: column;
    align-items: flex-start;
    gap: 1rem;
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

  .icon-grid {
    grid-template-columns: repeat(auto-fill, minmax(96px, 1fr));
    gap: 0.75rem;
  }

  .icon-card {
    padding: 0.75rem 0.5rem;
  }
}
</style>
