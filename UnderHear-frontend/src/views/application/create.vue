<template>
  <div class="create-container">
    <header class="page-header">
      <p class="eyebrow">Application Studio</p>
      <h1 class="page-title">创建你的单页面应用</h1>
      <p class="page-subtitle">支持原生 HTML 代码，左侧编辑右侧实时预览，保持本站风格的同时快速验证你的灵感。</p>
      <div class="page-pills">
        <span class="pill">安全隔离的 iframe 预览</span>
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
          <div class="tools">
            <label class="toggle">
              <input v-model="autoPreview" type="checkbox" />
              <span>自动预览</span>
            </label>
          </div>
        </div>

        <div class="field">
          <div class="field-head">
            <span>代码内容</span>
            <span class="muted">支持直接粘贴完整 HTML 文档</span>
          </div>
          <div class="meta-bar">
            <div class="meta-left">
              <span class="pill soft">
                <span class="status-dot tiny" :class="{ online: autoPreview }"></span>
                {{ syncModeText }}
              </span>
              <span class="pill neutral">字符数：{{ charCount }}</span>
            </div>
            <button class="ghost-button subtle" type="button" :disabled="autoPreview" @click="renderPreview">
              立即同步
            </button>
          </div>
          <textarea
            v-model="htmlCode"
            class="code-editor"
            spellcheck="false"
            placeholder="在此粘贴或编写你的 HTML..."
          ></textarea>
        </div>

        <div class="helper-row">
          <div class="helper">
            <p>你可以写入 script / style 标签，左侧输入即时同步到右侧 iframe。</p>
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
            <h3 class="section-title">HTML 片段渲染效果</h3>
            <p class="muted">预览在独立 iframe 中运行，避免影响站点本身。</p>
          </div>
          <div class="preview-status">
            <span class="status-dot" :class="{ online: autoPreview }"></span>
            <span class="muted">{{ autoPreview ? '自动同步' : '手动刷新' }}</span>
          </div>
        </div>

        <div class="preview-shell">
          <div class="preview-bar">
            <span class="dot red"></span>
            <span class="dot amber"></span>
            <span class="dot green"></span>
            <span class="preview-url">iframe 预览 · 隔离环境</span>
          </div>
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
          <li>预览隔离于 iframe，不会影响本站运行。</li>
          <li>移动端自适应，与本站其他页面保持一致的留白。</li>
        </ul>
      </section>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed, ref, watch } from 'vue'

const title = ref('')
const summary = ref('')
const autoPreview = ref(true)
const previewContent = ref('')
const previewKey = ref(0)

const htmlCode = ref('')
const charCount = computed(() => htmlCode.value.length)
const syncModeText = computed(() => (autoPreview.value ? '自动同步开启' : '手动刷新模式'))

const renderPreview = () => {
  previewContent.value = htmlCode.value || ''
  previewKey.value += 1
}

const resetEditor = () => {
  htmlCode.value = ''
  renderPreview()
}

watch(
  () => htmlCode.value,
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
  max-width: 1200px;
  margin: 0 auto;
  padding: 2.25rem 1.5rem 2.5rem;
  background: linear-gradient(180deg, #f8fafc 0%, #ffffff 16%);
}

.page-header {
  text-align: center;
  margin-bottom: 2.25rem;
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
  margin-top: 0.85rem;
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
  padding: 1.75rem;
  background: #fff;
  box-shadow: 0 16px 60px -42px rgba(15, 23, 42, 0.5);
}

.info-card {
  margin-bottom: 1.5rem;
}

.section-head {
  display: flex;
  align-items: center;
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

.meta-bar {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 1rem;
  margin: 0.35rem 0 0.5rem;
  flex-wrap: wrap;
}

.meta-left {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  flex-wrap: wrap;
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
  grid-template-columns: minmax(0, 1.05fr) minmax(0, 0.95fr);
  gap: 1.25rem;
  align-items: start;
}

.editor-card {
  display: flex;
  flex-direction: column;
  gap: 1rem;
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
  background: linear-gradient(180deg, #0d1117 0%, #0b1221 100%);
  color: #e8edf3;
  border: 1px solid #1e2a3a;
  box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.04), 0 18px 60px -45px rgba(0, 0, 0, 0.7);
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

.ghost-button.subtle {
  border-style: dashed;
  color: #3a4a60;
}

.muted {
  color: #6b7280;
}

.preview-card {
  display: flex;
  flex-direction: column;
  gap: 1rem;
  position: sticky;
  top: 18px;
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

.status-dot.tiny {
  width: 8px;
  height: 8px;
  box-shadow: none;
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

.preview-bar {
  display: flex;
  align-items: center;
  gap: 0.4rem;
  padding: 0.6rem 0.9rem;
  background: #f9fafb;
  border-bottom: 1px solid #e5e7eb;
  font-size: 0.9rem;
  color: #64748b;
}

.dot {
  width: 10px;
  height: 10px;
  border-radius: 50%;
  display: inline-block;
}

.dot.red { background: #ef4444; }
.dot.amber { background: #f59e0b; }
.dot.green { background: #22c55e; }

.preview-url {
  margin-left: 0.5rem;
  color: #475569;
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

  .preview-card {
    position: static;
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
