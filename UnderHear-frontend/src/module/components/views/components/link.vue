<template>
  <div class="link-docs">
    <h1 class="page-title">Link 链接</h1>
    <p class="page-description">常用的文本超链接。</p>

    <section class="demo-section">
      <h2 class="section-title">基础用法</h2>
      <p class="section-description">使用 <code>variant</code> 来定义链接的样式。</p>
      
      <div class="demo-block">
        <div class="demo-content">
          <div class="link-row">
            <zLink href="#" link-text="Primary Link" variant="primary" />
            <zLink href="#" link-text="Secondary Link" variant="secondary" />
            <zLink href="#" link-text="Danger Link" variant="danger" />
          </div>
        </div>
        
        <div class="demo-actions">
          <zTooltip :content="demo1Copied ? '已复制' : '复制代码'" placement="bottom">
            <button class="action-btn" @click="copyDemo1Code">
              <svg width="16" height="16" viewBox="0 0 16 16" fill="currentColor">
                <path d="M0 6.75C0 5.784.784 5 1.75 5h1.5a.75.75 0 0 1 0 1.5h-1.5a.25.25 0 0 0-.25.25v7.5c0 .138.112.25.25.25h7.5a.25.25 0 0 0 .25-.25v-1.5a.75.75 0 0 1 1.5 0v1.5A1.75 1.75 0 0 1 9.25 16h-7.5A1.75 1.75 0 0 1 0 14.25Z"/>
                <path d="M5 1.75C5 .784 5.784 0 6.75 0h7.5C15.216 0 16 .784 16 1.75v7.5A1.75 1.75 0 0 1 14.25 11h-7.5A1.75 1.75 0 0 1 5 9.25Zm1.75-.25a.25.25 0 0 0-.25.25v7.5c0 .138.112.25.25.25h7.5a.25.25 0 0 0 .25-.25v-7.5a.25.25 0 0 0-.25-.25Z"/>
              </svg>
            </button>
          </zTooltip>
          <zTooltip :content="demo1Visible ? '隐藏代码' : '查看代码'" placement="bottom">
            <button class="action-btn" @click="demo1Visible = !demo1Visible">
              <svg width="16" height="16" viewBox="0 0 16 16" fill="currentColor">
                <path d="M4.72 3.22a.75.75 0 0 1 1.06 1.06L2.06 8l3.72 3.72a.75.75 0 1 1-1.06 1.06L.47 8.53a.75.75 0 0 1 0-1.06l4.25-4.25Zm6.56 0a.75.75 0 1 0-1.06 1.06L13.94 8l-3.72 3.72a.75.75 0 1 0 1.06 1.06l4.25-4.25a.75.75 0 0 0 0-1.06l-4.25-4.25Z"/>
              </svg>
            </button>
          </zTooltip>
        </div>
        
        <transition name="code-expand">
          <div v-if="demo1Visible" class="demo-code">
            <pre class="language-markup">
              <code class="language-markup" v-html="demo1Highlighted"></code>
            </pre>
            <div class="code-footer">
              <button class="hide-code-btn" @click="demo1Visible = false">
                隐藏源代码
              </button>
            </div>
          </div>
        </transition>
      </div>
    </section>

    <section class="demo-section">
      <h2 class="section-title">外部链接</h2>
      <p class="section-description">设置 <code>external</code> 属性可以在链接后显示外部链接图标。</p>
      
      <div class="demo-block">
        <div class="demo-content">
          <div class="link-row">
            <zLink href="https://github.com" link-text="GitHub" :external="true" target="_blank" />
            <zLink href="https://vuejs.org" link-text="Vue.js" :external="true" target="_blank" variant="secondary" />
          </div>
        </div>
        
        <div class="demo-actions">
          <zTooltip :content="demo2Copied ? '已复制' : '复制代码'" placement="bottom">
            <button class="action-btn" @click="copyDemo2Code">
              <svg width="16" height="16" viewBox="0 0 16 16" fill="currentColor">
                <path d="M0 6.75C0 5.784.784 5 1.75 5h1.5a.75.75 0 0 1 0 1.5h-1.5a.25.25 0 0 0-.25.25v7.5c0 .138.112.25.25.25h7.5a.25.25 0 0 0 .25-.25v-1.5a.75.75 0 0 1 1.5 0v1.5A1.75 1.75 0 0 1 9.25 16h-7.5A1.75 1.75 0 0 1 0 14.25Z"/>
                <path d="M5 1.75C5 .784 5.784 0 6.75 0h7.5C15.216 0 16 .784 16 1.75v7.5A1.75 1.75 0 0 1 14.25 11h-7.5A1.75 1.75 0 0 1 5 9.25Zm1.75-.25a.25.25 0 0 0-.25.25v7.5c0 .138.112.25.25.25h7.5a.25.25 0 0 0 .25-.25v-7.5a.25.25 0 0 0-.25-.25Z"/>
              </svg>
            </button>
          </zTooltip>
          <zTooltip :content="demo2Visible ? '隐藏代码' : '查看代码'" placement="bottom">
            <button class="action-btn" @click="demo2Visible = !demo2Visible">
              <svg width="16" height="16" viewBox="0 0 16 16" fill="currentColor">
                <path d="M4.72 3.22a.75.75 0 0 1 1.06 1.06L2.06 8l3.72 3.72a.75.75 0 1 1-1.06 1.06L.47 8.53a.75.75 0 0 1 0-1.06l4.25-4.25Zm6.56 0a.75.75 0 1 0-1.06 1.06L13.94 8l-3.72 3.72a.75.75 0 1 0 1.06 1.06l4.25-4.25a.75.75 0 0 0 0-1.06l-4.25-4.25Z"/>
              </svg>
            </button>
          </zTooltip>
        </div>
        
        <transition name="code-expand">
          <div v-if="demo2Visible" class="demo-code">
            <pre class="language-markup">
              <code class="language-markup" v-html="demo2Highlighted"></code>
            </pre>
            <div class="code-footer">
              <button class="hide-code-btn" @click="demo2Visible = false">
                隐藏源代码
              </button>
            </div>
          </div>
        </transition>
      </div>
    </section>

    <section class="demo-section">
      <h2 class="section-title">前后缀文本</h2>
      <p class="section-description">使用 <code>prefix-text</code> 和 <code>suffix-text</code> 在链接前后添加文本。</p>
      
      <div class="demo-block">
        <div class="demo-content">
          <div class="link-column">
            <zLink href="/component" link-text="组件文档" prefix-text="查看" />
            <zLink href="/component" link-text="组件文档" suffix-text="了解更多" />
            <zLink href="/component" link-text="组件文档" prefix-text="前往" suffix-text="查看详情" />
          </div>
        </div>
        
        <div class="demo-actions">
          <zTooltip :content="demo3Copied ? '已复制' : '复制代码'" placement="bottom">
            <button class="action-btn" @click="copyDemo3Code">
              <svg width="16" height="16" viewBox="0 0 16 16" fill="currentColor">
                <path d="M0 6.75C0 5.784.784 5 1.75 5h1.5a.75.75 0 0 1 0 1.5h-1.5a.25.25 0 0 0-.25.25v7.5c0 .138.112.25.25.25h7.5a.25.25 0 0 0 .25-.25v-1.5a.75.75 0 0 1 1.5 0v1.5A1.75 1.75 0 0 1 9.25 16h-7.5A1.75 1.75 0 0 1 0 14.25Z"/>
                <path d="M5 1.75C5 .784 5.784 0 6.75 0h7.5C15.216 0 16 .784 16 1.75v7.5A1.75 1.75 0 0 1 14.25 11h-7.5A1.75 1.75 0 0 1 5 9.25Zm1.75-.25a.25.25 0 0 0-.25.25v7.5c0 .138.112.25.25.25h7.5a.25.25 0 0 0 .25-.25v-7.5a.25.25 0 0 0-.25-.25Z"/>
              </svg>
            </button>
          </zTooltip>
          <zTooltip :content="demo3Visible ? '隐藏代码' : '查看代码'" placement="bottom">
            <button class="action-btn" @click="demo3Visible = !demo3Visible">
              <svg width="16" height="16" viewBox="0 0 16 16" fill="currentColor">
                <path d="M4.72 3.22a.75.75 0 0 1 1.06 1.06L2.06 8l3.72 3.72a.75.75 0 1 1-1.06 1.06L.47 8.53a.75.75 0 0 1 0-1.06l4.25-4.25Zm6.56 0a.75.75 0 1 0-1.06 1.06L13.94 8l-3.72 3.72a.75.75 0 1 0 1.06 1.06l4.25-4.25a.75.75 0 0 0 0-1.06l-4.25-4.25Z"/>
              </svg>
            </button>
          </zTooltip>
        </div>
        
        <transition name="code-expand">
          <div v-if="demo3Visible" class="demo-code">
            <pre class="language-markup">
              <code class="language-markup" v-html="demo3Highlighted"></code>
            </pre>
            <div class="code-footer">
              <button class="hide-code-btn" @click="demo3Visible = false">
                隐藏源代码
              </button>
            </div>
          </div>
        </transition>
      </div>
    </section>

    <section class="api-section">
      <h2 class="section-title">API</h2>
      
      <h3 class="api-subtitle">属性</h3>
      <div class="api-table-wrapper">
        <table class="api-table">
          <thead>
            <tr>
              <th>属性名</th>
              <th>说明</th>
              <th>类型</th>
              <th>可选值</th>
              <th>默认值</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td><code>href</code></td>
              <td>链接地址</td>
              <td><code>string</code></td>
              <td>—</td>
              <td>—</td>
            </tr>
            <tr>
              <td><code>link-text</code></td>
              <td>链接文本</td>
              <td><code>string</code></td>
              <td>—</td>
              <td>—</td>
            </tr>
            <tr>
              <td><code>prefix-text</code></td>
              <td>前置文本</td>
              <td><code>string</code></td>
              <td>—</td>
              <td>—</td>
            </tr>
            <tr>
              <td><code>suffix-text</code></td>
              <td>后置文本</td>
              <td><code>string</code></td>
              <td>—</td>
              <td>—</td>
            </tr>
            <tr>
              <td><code>external</code></td>
              <td>是否为外部链接</td>
              <td><code>boolean</code></td>
              <td>—</td>
              <td><code>false</code></td>
            </tr>
            <tr>
              <td><code>target</code></td>
              <td>链接打开方式</td>
              <td><code>string</code></td>
              <td>_blank / _self / _parent / _top</td>
              <td><code>_self</code></td>
            </tr>
            <tr>
              <td><code>variant</code></td>
              <td>链接样式变体</td>
              <td><code>string</code></td>
              <td>primary / secondary / danger / default</td>
              <td><code>primary</code></td>
            </tr>
          </tbody>
        </table>
      </div>
    </section>
  </div>
</template>

<script setup lang="ts">
import { computed, ref } from 'vue'
import * as Prism from 'prismjs'
import 'prismjs/themes/prism.css'
import { zLink } from '@/components/z-ui/link/zlink'
import { zTooltip } from '@/components/z-ui/tooltip/zTooltip'

const demo1Visible = ref(false)
const demo2Visible = ref(false)
const demo3Visible = ref(false)
const demo1Copied = ref(false)
const demo2Copied = ref(false)
const demo3Copied = ref(false)

const demo1Code = `<template>
  <div style="max-width: 600px">
    <zLink href="#" link-text="Primary Link" variant="primary" />
    <zLink href="#" link-text="Secondary Link" variant="secondary" />
    <zLink href="#" link-text="Danger Link" variant="danger" />
  </div>
</template>

<style scoped>
.link-row {
  display: flex;
  gap: 20px;
  align-items: center;
}
</style>`

const demo2Code = `<template>
  <div style="max-width: 600px">
    <zLink href="https://github.com" link-text="GitHub" :external="true" target="_blank" />
    <zLink href="https://vuejs.org" link-text="Vue.js" :external="true" target="_blank" variant="secondary" />
  </div>
</template>

<style scoped>
.link-row {
  display: flex;
  gap: 20px;
  align-items: center;
}
</style>`

const demo3Code = `<template>
  <div style="max-width: 600px">
    <zLink href="/component" link-text="组件文档" prefix-text="查看" />
    <zLink href="/component" link-text="组件文档" suffix-text="了解更多" />
    <zLink href="/component" link-text="组件文档" prefix-text="前往" suffix-text="查看详情" />
  </div>
</template>

<style scoped>
.link-column {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}
</style>`

const highlightCode = (code: string) =>
  Prism.highlight(code, Prism.languages.markup, 'markup')
const demo1Highlighted = computed(() => highlightCode(demo1Code))
const demo2Highlighted = computed(() => highlightCode(demo2Code))
const demo3Highlighted = computed(() => highlightCode(demo3Code))

function copyDemo1Code() {
  navigator.clipboard.writeText(demo1Code).then(() => {
    demo1Copied.value = true
    setTimeout(() => {
      demo1Copied.value = false
    }, 2000)
  })
}

function copyDemo2Code() {
  navigator.clipboard.writeText(demo2Code).then(() => {
    demo2Copied.value = true
    setTimeout(() => {
      demo2Copied.value = false
    }, 2000)
  })
}

function copyDemo3Code() {
  navigator.clipboard.writeText(demo3Code).then(() => {
    demo3Copied.value = true
    setTimeout(() => {
      demo3Copied.value = false
    }, 2000)
  })
}
</script>

<style scoped>
.link-docs {
  max-width: 1200px;
  margin: 0 auto;
  width: 100%;
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

.link-row {
  display: flex;
  gap: 1.5rem;
  align-items: center;
  flex-wrap: wrap;
}

.link-column {
  display: flex;
  flex-direction: column;
  gap: 1rem;
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

.api-section {
  margin-top: 3rem;
}

.api-subtitle {
  font-size: 1.25rem;
  font-weight: 600;
  color: var(--font-black);
  margin-bottom: 1rem;
}

.api-table-wrapper {
  overflow-x: auto;
}

.api-table {
  width: 100%;
  border-collapse: collapse;
  border: 1px solid #d1d9e0;
  border-radius: 6px;
  overflow: hidden;
  background: #ffffff;
}

.api-table thead {
  background: #f6f8fa;
}

.api-table th {
  padding: 0.75rem 1rem;
  text-align: left;
  font-weight: 600;
  font-size: 0.875rem;
  color: var(--font-black);
  border-bottom: 1px solid #d1d9e0;
}

.api-table td {
  padding: 0.75rem 1rem;
  font-size: 0.875rem;
  color: var(--font-gray);
  border-bottom: 1px solid #d1d9e0;
}

.api-table tbody tr:last-child td {
  border-bottom: none;
}

.api-table code {
  padding: 0.125rem 0.375rem;
  background: #f6f8fa;
  border: 1px solid #d1d9e0;
  border-radius: 3px;
  font-family: 'Consolas', 'Monaco', 'Courier New', monospace;
  font-size: 0.875em;
  color: #d1242f;
  white-space: nowrap;
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

  .link-row {
    flex-direction: column;
    align-items: flex-start;
    gap: 1rem;
  }

  .link-column {
    gap: 0.75rem;
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

  .api-section {
    margin-top: 2rem;
  }

  .api-subtitle {
    font-size: 1.125rem;
  }

  .api-table {
    font-size: 0.75rem;
  }

  .api-table th,
  .api-table td {
    padding: 0.5rem 0.75rem;
    font-size: 0.75rem;
  }

  .api-table code {
    font-size: 0.75em;
    padding: 0.1rem 0.25rem;
  }
}
</style>
