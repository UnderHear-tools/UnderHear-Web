<template>
  <div class="application-create-container">
    <div class="application-create-header">
      <h1 class="title">上传单页应用</h1>
      <p class="subtitle">在这里提交你的单页应用信息。</p>
      <p class="subtitle">
        返回列表：<zLink href="/application" link-text="应用广场" />
      </p>
    </div>
        
    <div class="create-grid">
      <div class="panel panel--editor">
        <div class="panel-header">
          <h2 class="panel-title">页面代码（可选）</h2>
          <button type="button" class="panel-tip preview-button" @click="openPreview">预览</button>
        </div>
        <div ref="editorContainer" class="editor"></div>
        <p class="hint">后续可将这里的内容用于预览、存档或发布流程。</p>
      </div>

      <div class="panel panel--form">
        <h2 class="panel-title">基本信息</h2>
        <form class="form" @submit.prevent="handleSubmit">
          <div class="field">
            <label class="label">标题</label>
            <zInput v-model="form.title" placeholder="例如：番茄钟 / 小工具合集" />
          </div>

          <div class="field">
            <label class="label">分类</label>
            <zInput v-model="form.category" placeholder="例如：工具 / 游戏 / 学习" />
          </div>

          <div class="field">
            <label class="label">描述（中文）</label>
            <zTextarea v-model="form.descriptionZh" rows="3" placeholder="一句话介绍你的应用" />
          </div>

          <div class="field">
            <label class="label">Description (EN)</label>
            <zTextarea v-model="form.descriptionEn" rows="3" placeholder="A short description in English" />
          </div>

          <div class="field">
            <label class="label">访问链接</label>
            <zInput v-model="form.link" placeholder="https://..." />
          </div>

          <div class="divider"></div>

          <h3 class="sub-title">作者信息（可选）</h3>

          <div class="field">
            <label class="label">作者昵称</label>
            <zInput v-model="form.author" placeholder="例如：UnderHear" />
          </div>

          <div class="field">
            <label class="label">作者主页</label>
            <zInput v-model="form.authorLink" placeholder="https://github.com/..." />
          </div>

          <div class="field">
            <label class="label">头像链接</label>
            <zInput v-model="form.authorAvatar" placeholder="https://.../avatar.png" />
          </div>

          <button type="submit" class="submit">提交（占位）</button>
        </form>
      </div>
    </div>

    <teleport to="body">
      <div v-if="isPreviewOpen" class="preview-overlay" @click.self="closePreview">
        <iframe class="preview-frame" :srcdoc="previewHtml" sandbox="allow-scripts"></iframe>
      </div>
    </teleport>
  </div>
</template>

<script setup lang="ts">
import { onBeforeUnmount, onMounted, ref } from 'vue'
import * as monaco from 'monaco-editor'

import { zInput } from '@/components/z-ui/input/zInput'
import { zTextarea } from '@/components/z-ui/input/zTextarea'
import { zLink } from '@/components/z-ui/link/zlink'

const editorContainer = ref<HTMLElement | null>(null)
let editor: monaco.editor.IStandaloneCodeEditor | undefined

const isPreviewOpen = ref(false)
const previewHtml = ref('')
let previousBodyOverflow: string | null = null

const form = ref({
  title: '',
  category: '',
  descriptionZh: '',
  descriptionEn: '',
  link: '',
  author: '',
  authorLink: '',
  authorAvatar: ''
})

const defaultTemplate = `<!-- 在这里粘贴/编写你的单页应用代码（占位） -->
<main style="font-family: ui-sans-serif, system-ui; padding: 24px;">
  <h1>Hello UnderHear</h1>
  <p>Write something awesome.</p>
</main>
`

function handleSubmit() {
  console.log('[application/create] submit (placeholder)', form.value)
}

function lockBodyScroll() {
  if (previousBodyOverflow !== null) return
  previousBodyOverflow = document.body.style.overflow
  document.body.style.overflow = 'hidden'
}

function unlockBodyScroll() {
  if (previousBodyOverflow === null) return
  document.body.style.overflow = previousBodyOverflow
  previousBodyOverflow = null
}

function refreshPreview() {
  const code = editor?.getValue() ?? defaultTemplate
  previewHtml.value = `<!doctype html><html><body style="margin:0">${code}</body></html>`
}

function openPreview() {
  refreshPreview()
  isPreviewOpen.value = true
  lockBodyScroll()
}

function closePreview() {
  isPreviewOpen.value = false
  unlockBodyScroll()
}

function onWindowKeydown(event: KeyboardEvent) {
  if (event.key === 'Escape' && isPreviewOpen.value) {
    closePreview()
  }
}

onMounted(() => {
  if (!editorContainer.value) return

  editor = monaco.editor.create(editorContainer.value, {
    value: defaultTemplate,
    language: 'html',
    theme: 'vs-light',
    automaticLayout: true,
    minimap: { enabled: false }
  })

  window.addEventListener('keydown', onWindowKeydown)
})

onBeforeUnmount(() => {
  window.removeEventListener('keydown', onWindowKeydown)
  unlockBodyScroll()
  editor?.dispose()
})
</script>

<style scoped>
.application-create-container {
  max-width: 1150px;
  margin: 0 auto;
  padding: 2rem;
  background: #fff;
}

.application-create-header {
  text-align: center;
  margin-bottom: 3rem;
}

.title {
  font-size: 2.5rem;
  font-weight: 700;
  color: #24292f;
  margin-bottom: 1rem;
  line-height: 1.2;
}

.subtitle {
  font-size: 1.05rem;
  color: #656d76;
  line-height: 1.6;
  max-width: 620px;
  margin: 0 auto;
}

.create-grid {
  display: grid;
  grid-template-columns: 1fr;
  gap: 1.6rem;
  align-items: start;
}

.panel {
  background: #fff;
  border: 1px solid #d1d9e1;
  border-radius: 6px;
  padding: 1.5rem;
}

.panel-header {
  display: flex;
  justify-content: space-between;
  align-items: baseline;
  gap: 1rem;
  margin-bottom: 0.75rem;
}

.panel-title {
  margin: 0 0 1rem 0;
  font-size: 1.1rem;
  font-weight: 700;
  color: #24292f;
}

.panel-header .panel-title {
  margin-bottom: 0;
}

.panel-tip {
  font-size: 0.75rem;
  color: #656d76;
  background: #f6f8fa;
  border-radius: 12px;
  padding: 0.2rem 0.55rem;
  font-weight: 600;
}

.preview-button {
  border: none;
  cursor: pointer;
}

.preview-button:hover {
  background: #eaeef2;
}

.preview-overlay {
  position: fixed;
  inset: 0;
  background: rgba(0, 0, 0, 0.55);
  z-index: 9999;
  display: flex;
  flex-direction: column;
}

.preview-toolbar {
  height: 56px;
  background: #fff;
  border-bottom: 1px solid #d1d9e1;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 16px;
}

.preview-title {
  font-size: 0.95rem;
  font-weight: 700;
  color: #24292f;
}

.preview-actions {
  display: flex;
  gap: 10px;
}

.preview-refresh,
.preview-close {
  cursor: pointer;
  border: 1px solid #d1d9e1;
  background: #fff;
  border-radius: 8px;
  padding: 8px 12px;
  font-weight: 600;
}

.preview-refresh:hover,
.preview-close:hover {
  background: #f6f8fa;
}

.preview-frame {
  flex: 1 1 auto;
  width: 100%;
  border: 0;
  background: #fff;
}

.form {
  display: grid;
  gap: 0.9rem;
}

.field {
  display: grid;
  gap: 0.4rem;
}

.label {
  font-size: 0.9rem;
  font-weight: 600;
  color: #24292f;
}

.divider {
  height: 1px;
  background: #d1d9e1;
  opacity: 0.6;
  margin: 0.4rem 0;
}

.sub-title {
  margin: 0.2rem 0 0.1rem;
  font-size: 0.95rem;
  font-weight: 700;
  color: #24292f;
}

.submit {
  display: inline-flex;
  justify-content: center;
  align-items: center;
  padding: 0.55rem 1rem;
  background-color: #0969da;
  color: #fff;
  border: none;
  border-radius: 6px;
  font-weight: 600;
  font-size: 0.9rem;
  cursor: pointer;
  transition: background-color 0.2s ease;
}

.submit:hover {
  background-color: #0860ca;
}

.editor {
  width: 100%;
  height: 520px;
  border: 1px solid #d1d9e1;
  overflow: hidden;
}

.hint {
  margin: 0.75rem 0 0;
  color: #656d76;
  font-size: 0.9rem;
  line-height: 1.6;
}

@media (max-width: 920px) {
  .editor {
    height: 480px;
  }
}

@media (max-width: 768px) {
  .application-create-container {
    padding: 1rem;
  }

  .title {
    font-size: 2rem;
  }

  .subtitle {
    font-size: 1rem;
  }
}
</style>
