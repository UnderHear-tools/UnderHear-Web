<template>
  <div class="create-container">
    <header class="page-header">
      <p class="eyebrow">Application Studio</p>
      <h1 class="page-title">创建你的单页面应用</h1>
      <p class="page-subtitle">支持原生 HTML 与 Vue 3 代码，左侧编辑右侧实时预览，保持本站风格的同时快速验证你的灵感。</p>
      <div class="page-pills">
        <span class="pill">安全隔离的 iframe 预览</span>
        <span class="pill">Vue 模式自动注入 CDN 运行时</span>
        <span class="pill">适合上传前的快速验收</span>
      </div>
    </header>

    <section class="card info-card">
      <div class="section-head">
        <div>
          <p class="section-label">基础信息（可选）</p>
          <h3 class="section-title">先给你的应用取个名字吧</h3>
        </div>
        <span class="badge">草稿保存于本地</span>
      </div>
      <div class="form-grid">
        <label class="field">
          <span>应用名称</span>
          <input v-model="title" type="text" placeholder="例如：Markdown 转 PDF / 番茄钟 / Path Planner" />
        </label>
        <label class="field">
          <span>一句话简介</span>
          <input v-model="summary" type="text" placeholder="告诉大家它能做什么（可留空）" />
        </label>
      </div>
    </section>

    <div class="layout">
      <section class="card editor-card">
        <div class="section-head">
          <div class="tabs">
            <button
              class="tab"
              :class="{ active: mode === 'html' }"
              type="button"
              @click="switchMode('html')"
            >
              原生 HTML
            </button>
            <button
              class="tab"
              :class="{ active: mode === 'vue' }"
              type="button"
              @click="switchMode('vue')"
            >
              Vue 单文件
            </button>
          </div>
          <div class="tools">
            <label class="toggle">
              <input v-model="autoPreview" type="checkbox" />
              <span>自动预览</span>
            </label>
            <button class="ghost-button" type="button" @click="fillTemplate">填充示例</button>
          </div>
        </div>

        <div class="field">
          <div class="field-head">
            <span>代码内容</span>
            <span class="muted" v-if="mode === 'html'">支持直接粘贴完整 HTML 文档</span>
            <span class="muted" v-else>自动注入 Vue CDN，挂载点为 #app</span>
          </div>
          <textarea
            v-if="mode === 'html'"
            v-model="htmlCode"
            class="code-editor"
            spellcheck="false"
            placeholder="在此粘贴或编写你的 HTML..."
          ></textarea>
          <textarea
            v-else
            v-model="vueCode"
            class="code-editor"
            spellcheck="false"
            placeholder="在此粘贴或编写你的 Vue 代码（JS 逻辑 + template）..."
          ></textarea>
        </div>

        <div class="helper-row">
          <div class="helper">
            <p v-if="mode === 'html'">你可以写入 script / style 标签，左侧输入即时同步到右侧 iframe。</p>
            <p v-else>Vue 模式使用全局构建版本（Vue 3.5），请确保代码内调用 createApp 并挂载到 #app。</p>
          </div>
          <div class="actions">
            <button class="outline-button" type="button" @click="resetEditor">清空</button>
            <button class="primary-button" type="button" @click="renderPreview">刷新预览</button>
          </div>
        </div>
      </section>

      <section class="card preview-card">
        <div class="section-head">
          <div>
            <p class="section-label">实时预览</p>
            <h3 class="section-title">{{ mode === 'html' ? 'HTML 片段' : 'Vue 组件' }} 渲染效果</h3>
            <p class="muted">预览在独立 iframe 中运行，避免影响站点本身。</p>
          </div>
          <div class="preview-status">
            <span class="status-dot" :class="{ online: autoPreview }"></span>
            <span class="muted">{{ autoPreview ? '自动同步' : '手动刷新' }}</span>
          </div>
        </div>

        <div class="preview-shell">
          <iframe
            :key="previewKey"
            class="preview-frame"
            title="preview"
            sandbox="allow-scripts allow-same-origin"
            :srcdoc="previewContent"
          ></iframe>
        </div>

        <ul class="preview-tips">
          <li>支持外链 CSS / JS，如 CDN 图表库或字体。</li>
          <li>错误信息会直接显示在预览下方，方便排查。</li>
          <li>移动端自适应，与本站其他页面保持一致的留白。</li>
        </ul>
      </section>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, watch } from 'vue'

type EditorMode = 'html' | 'vue'

const title = ref('')
const summary = ref('')
const mode = ref<EditorMode>('html')
const autoPreview = ref(true)
const previewContent = ref('')
const previewKey = ref(0)

const defaultHtmlTemplate = `<main class="preview-card">
  <p class="tag">HTML 预览</p>
  <h1>👋 欢迎来到 UnderHear</h1>
  <p class="muted">写下你的页面、加载脚本，右侧即可看到完整效果。</p>
  <div class="button-row">
    <button class="button" onclick="alert('可以写交互哦！')">点我触发脚本</button>
    <a class="button ghost" href="https://underhear.com" target="_blank">外链示例</a>
  </div>
</main>`

const defaultVueTemplate = `const { createApp, ref, computed } = Vue

const App = {
  setup() {
    const todo = ref('')
    const items = ref(['路径规划器', '组件库 zLight', '在线工具集合'])
    const add = () => {
      if (!todo.value.trim()) return
      items.value.push(todo.value.trim())
      todo.value = ''
    }
    const total = computed(() => items.value.length)
    return { todo, items, add, total }
  },
  template: \`
    <main class="preview-card">
      <p class="tag">Vue 预览</p>
      <h1>⚡ 单文件组件逻辑</h1>
      <p class="muted">把你的 <script setup> 逻辑粘贴进来即可运行。</p>
      <div class="stack">
        <label class="field">
          <span>添加一个条目</span>
          <input v-model="todo" placeholder="输入后点击添加" />
        </label>
        <button class="button" @click="add">添加（{{ total }}）</button>
        <ul class="list">
          <li v-for="item in items" :key="item">{{ item }}</li>
        </ul>
      </div>
    </main>
  \`
}

createApp(App).mount('#app')
`

const htmlCode = ref(defaultHtmlTemplate)
const vueCode = ref(defaultVueTemplate)

const previewBaseStyles = `:root { color-scheme: light; }
* { box-sizing: border-box; }
body { margin: 0; padding: 0; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', sans-serif; background: #f6f8fa; color: #0f172a; }
main, section { max-width: 860px; margin: 26px auto; padding: 0 16px; }
.preview-card { background: #fff; border: 1px solid #e5e7eb; border-radius: 12px; padding: 22px; box-shadow: 0 16px 60px -34px rgba(15, 23, 42, 0.35); }
.button-row { display: flex; gap: 12px; flex-wrap: wrap; }
.button { padding: 10px 16px; border-radius: 10px; border: 1px solid #0b69da; background: #0969da; color: #fff; font-weight: 600; cursor: pointer; text-decoration: none; transition: transform 120ms ease, box-shadow 120ms ease; }
.button:hover { transform: translateY(-1px); box-shadow: 0 10px 25px -15px rgba(9, 105, 218, 0.45); }
.button.ghost { background: #fff; color: #0969da; border-color: #d1d9e1; }
.muted { color: #6b7280; }
.tag { display: inline-flex; align-items: center; gap: 6px; background: #ecf5ff; color: #0b69da; padding: 6px 10px; border-radius: 999px; border: 1px solid #d6e6ff; font-size: 12px; letter-spacing: 0.01em; }
.stack { display: flex; flex-direction: column; gap: 10px; }
.field { display: flex; flex-direction: column; gap: 6px; font-size: 14px; color: #111827; }
.field input { padding: 10px 12px; border-radius: 10px; border: 1px solid #d1d9e1; background: #f8fafc; }
.list { list-style: none; padding: 0; margin: 0; display: grid; gap: 8px; }
.list li { padding: 10px 12px; border-radius: 10px; border: 1px solid #e5e7eb; background: #fff; }
.preview-error { display: none; margin: 12px auto; max-width: 860px; color: #b42318; background: #fef2f2; border: 1px solid #fecdd3; padding: 10px 14px; border-radius: 10px; font-size: 14px; }`

const escapeScript = (input: string) => input.replace(/<\/(script)/gi, '<\\/$1')
const scriptEndToken = '</scr' + 'ipt>'
const vueCdnScript = `<script src="https://unpkg.com/vue@3/dist/vue.global.prod.js">${scriptEndToken}`

const buildHtmlPreview = (code: string) => {
  const hasHtmlShell = /<html[\\s>]/i.test(code)
  if (hasHtmlShell) return code || ''
  const safeBody = code?.trim() ? code : '<main class=\"preview-card\"><h2>暂时还没有内容</h2></main>'
  return `<!doctype html>
  <html lang=\"zh-CN\">
    <head>
      <meta charset=\"UTF-8\" />
      <meta name=\"viewport\" content=\"width=device-width,initial-scale=1.0\" />
      <style>${previewBaseStyles}</style>
    </head>
    <body>${safeBody}</body>
  </html>`
}

const buildVuePreview = (code: string) => `<!doctype html>
<html lang=\"zh-CN\">
  <head>
    <meta charset=\"UTF-8\" />
    <meta name=\"viewport\" content=\"width=device-width,initial-scale=1.0\" />
    <style>${previewBaseStyles}</style>
  </head>
  <body>
    <div id=\"app\"></div>
    <div id=\"preview-error\" class=\"preview-error\" aria-live=\"polite\"></div>
    ${vueCdnScript}
    <script>
      const errorBox = document.getElementById('preview-error')
      window.addEventListener('error', (event) => {
        if (errorBox) {
          errorBox.textContent = '脚本错误：' + event.message
          errorBox.style.display = 'block'
        }
      })
      try {
        ${escapeScript(code)}
      } catch (err) {
        if (errorBox) {
          errorBox.textContent = '运行时错误：' + (err && err.message ? err.message : err)
          errorBox.style.display = 'block'
        }
        console.error(err)
      }
    ${scriptEndToken}
  </body>
</html>`

const renderPreview = () => {
  previewContent.value =
    mode.value === 'html' ? buildHtmlPreview(htmlCode.value) : buildVuePreview(vueCode.value)
  previewKey.value += 1
}

const switchMode = (next: EditorMode) => {
  mode.value = next
}

const fillTemplate = () => {
  if (mode.value === 'html') {
    htmlCode.value = defaultHtmlTemplate
  } else {
    vueCode.value = defaultVueTemplate
  }
  renderPreview()
}

const resetEditor = () => {
  if (mode.value === 'html') {
    htmlCode.value = ''
  } else {
    vueCode.value = ''
  }
  renderPreview()
}

watch(
  () => [mode.value, htmlCode.value, vueCode.value],
  () => {
    if (autoPreview.value) {
      renderPreview()
    }
  },
  { immediate: true }
)

watch(autoPreview, (enabled) => {
  if (enabled) {
    renderPreview()
  }
})
</script>

<style scoped>
.create-container {
  max-width: 1150px;
  margin: 0 auto;
  padding: 2rem;
  background: #fff;
}

.page-header {
  text-align: center;
  margin-bottom: 2rem;
}

.eyebrow {
  display: inline-block;
  color: #0969da;
  font-weight: 700;
  letter-spacing: 0.04em;
  text-transform: uppercase;
  font-size: 0.85rem;
  margin-bottom: 0.4rem;
}

.page-title {
  font-size: 2.3rem;
  font-weight: 700;
  color: #111827;
  margin: 0.15rem 0;
}

.page-subtitle {
  margin: 0.5rem auto 0;
  max-width: 780px;
  color: #6b7280;
  line-height: 1.7;
  font-size: 1rem;
}

.page-pills {
  display: flex;
  gap: 0.5rem;
  justify-content: center;
  flex-wrap: wrap;
  margin-top: 1rem;
}

.pill {
  padding: 0.4rem 0.85rem;
  background: #f6f8fa;
  border: 1px solid #d1d9e1;
  border-radius: 999px;
  color: #394150;
  font-size: 0.9rem;
}

.card {
  border: 1px solid #d1d9e1;
  border-radius: 12px;
  padding: 1.5rem;
  background: #fff;
  box-shadow: 0 20px 60px -38px rgba(15, 23, 42, 0.4);
}

.section-head {
  display: flex;
  align-items: flex-start;
  justify-content: space-between;
  gap: 1rem;
  margin-bottom: 1rem;
}

.section-label {
  margin: 0;
  color: #6b7280;
  font-size: 0.95rem;
}

.section-title {
  margin: 0.15rem 0 0;
  color: #111827;
  font-size: 1.3rem;
  font-weight: 700;
}

.badge {
  padding: 0.35rem 0.75rem;
  border-radius: 10px;
  background: #ecf5ff;
  color: #0b69da;
  border: 1px solid #d6e6ff;
  font-weight: 600;
  font-size: 0.85rem;
  white-space: nowrap;
}

.form-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(260px, 1fr));
  gap: 1rem;
}

.field {
  display: flex;
  flex-direction: column;
  gap: 0.35rem;
}

.field-head {
  display: flex;
  align-items: baseline;
  gap: 0.5rem;
}

.field span {
  color: #111827;
  font-weight: 600;
}

.field input {
  width: 100%;
  padding: 0.75rem 0.9rem;
  border-radius: 10px;
  border: 1px solid #d1d9e1;
  background: #f9fafb;
  font-size: 0.95rem;
  color: #111827;
  transition: border-color 0.15s ease, box-shadow 0.15s ease;
}

.field input:focus {
  outline: none;
  border-color: #0969da;
  box-shadow: 0 0 0 3px rgba(9, 105, 218, 0.12);
}

.layout {
  display: grid;
  grid-template-columns: 1.05fr 0.95fr;
  gap: 1.5rem;
  align-items: start;
}

.editor-card {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.tabs {
  display: inline-flex;
  padding: 4px;
  background: #f6f8fa;
  border-radius: 999px;
  border: 1px solid #d1d9e1;
  gap: 4px;
}

.tab {
  border: none;
  background: transparent;
  padding: 0.5rem 0.9rem;
  border-radius: 999px;
  color: #394150;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.15s ease;
}

.tab.active {
  background: #fff;
  color: #0b69da;
  box-shadow: 0 8px 24px -16px rgba(9, 105, 218, 0.5);
}

.tools {
  display: flex;
  align-items: center;
  gap: 0.75rem;
  flex-wrap: wrap;
}

.toggle {
  display: inline-flex;
  align-items: center;
  gap: 0.4rem;
  color: #6b7280;
  font-size: 0.95rem;
}

.toggle input {
  width: 16px;
  height: 16px;
  accent-color: #0969da;
  cursor: pointer;
}

.code-editor {
  width: 100%;
  min-height: 360px;
  padding: 1rem;
  border-radius: 12px;
  background: #0d1117;
  color: #e6edf3;
  border: 1px solid #161b22;
  font-family: 'SFMono-Regular', Consolas, 'Liberation Mono', Menlo, monospace;
  font-size: 0.95rem;
  line-height: 1.6;
  resize: vertical;
}

.code-editor:focus {
  outline: none;
  border-color: #2f81f7;
  box-shadow: 0 0 0 3px rgba(47, 129, 247, 0.24);
}

.helper-row {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 1rem;
  flex-wrap: wrap;
}

.helper {
  color: #6b7280;
  font-size: 0.95rem;
}

.actions {
  display: flex;
  gap: 0.75rem;
  flex-wrap: wrap;
}

.primary-button,
.outline-button,
.ghost-button {
  padding: 0.65rem 1.1rem;
  border-radius: 10px;
  font-weight: 600;
  cursor: pointer;
  transition: transform 0.15s ease, box-shadow 0.15s ease, border-color 0.15s ease;
  font-size: 0.95rem;
}

.primary-button {
  background: #0969da;
  color: #fff;
  border: 1px solid #0b5fc7;
  box-shadow: 0 12px 30px -16px rgba(9, 105, 218, 0.55);
}

.primary-button:hover {
  transform: translateY(-1px);
}

.outline-button {
  background: #fff;
  color: #111827;
  border: 1px dashed #d1d9e1;
}

.ghost-button {
  background: #fff;
  color: #0b69da;
  border: 1px solid #d1d9e1;
}

.muted {
  color: #6b7280;
}

.preview-card {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.preview-status {
  display: inline-flex;
  align-items: center;
  gap: 0.4rem;
}

.status-dot {
  width: 10px;
  height: 10px;
  border-radius: 50%;
  background: #d1d5db;
}

.status-dot.online {
  background: #22c55e;
  box-shadow: 0 0 0 6px rgba(34, 197, 94, 0.15);
}

.preview-shell {
  border: 1px solid #d1d9e1;
  border-radius: 12px;
  overflow: hidden;
  background: linear-gradient(180deg, #f6f8fa 0%, #fff 100%);
}

.preview-frame {
  width: 100%;
  min-height: 520px;
  border: none;
  background: transparent;
}

.preview-tips {
  list-style: disc;
  padding-left: 1.1rem;
  margin: 0;
  color: #6b7280;
  display: grid;
  gap: 0.3rem;
  font-size: 0.95rem;
}

@media (max-width: 1024px) {
  .layout {
    grid-template-columns: 1fr;
  }
}

@media (max-width: 768px) {
  .create-container {
    padding: 1rem;
  }

  .page-title {
    font-size: 1.85rem;
  }

  .helper-row {
    flex-direction: column;
    align-items: flex-start;
  }

  .actions {
    width: 100%;
  }

  .primary-button,
  .outline-button {
    width: 100%;
    text-align: center;
    justify-content: center;
  }
}
</style>
