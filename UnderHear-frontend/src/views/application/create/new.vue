<template>
  <zContainer>
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
          <h4 class="panel-title">页面代码（可选）</h4>
          <button class="preview-button" @click="openPreview($event)">点击预览</button>
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

          <zDivider />

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
        <div class="preview-reveal" :style="previewOverlayStyle">
          <iframe class="preview-frame" :srcdoc="previewHtml" sandbox="allow-scripts"></iframe>
        </div>
      </div>
      </teleport>
  </zContainer>
</template>

<script setup lang="ts">
import { onBeforeUnmount, onMounted, ref } from 'vue'
import * as monaco from 'monaco-editor'

import { zContainer } from '@/components/z-ui/container/zContainer'
import { zInput } from '@/components/z-ui/input/zInput'
import { zTextarea } from '@/components/z-ui/input/zTextarea'
import { zDivider } from '@/components/z-ui/divider/zDivider'
import { zLink } from '@/components/z-ui/link/zlink'

const editorContainer = ref<HTMLElement | null>(null)
let editor: monaco.editor.IStandaloneCodeEditor | undefined

const isPreviewOpen = ref(false)
const previewHtml = ref('')
const previewOverlayStyle = ref<Record<string, string>>({
  '--preview-origin-x': '50vw',
  '--preview-origin-y': '50vh'
})
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

function openPreview(event?: MouseEvent) {
  const fallbackX = window.innerWidth / 2
  const fallbackY = window.innerHeight / 2

  const clientX = event?.clientX ?? fallbackX
  const clientY = event?.clientY ?? fallbackY

  const originX = clientX === 0 ? fallbackX : clientX
  const originY = clientY === 0 ? fallbackY : clientY

  previewOverlayStyle.value = {
    '--preview-origin-x': `${Math.round(originX)}px`,
    '--preview-origin-y': `${Math.round(originY)}px`
  }

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
.application-create-header {
  text-align: center;
  margin-bottom: 3rem;
}

.title {
  font-size: 2.5rem;
  font-weight: 700;
  color: var(--font-black);
  margin-bottom: 1rem;
  line-height: 1.2;
}

.subtitle {
  font-size: 1.05rem;
  color: var(--font-gray);
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
  font-weight: 700;
  color: var(--font-black);
}

.preview-button {
  font-size: 0.9rem;
  color: var(--font-blue);
  font-weight: 600;
  cursor: pointer;
}

.panel-header .panel-title {
  margin-bottom: 0;
}

.preview-overlay {
  position: fixed;
  inset: 0;
  background: transparent;
  z-index: 9999;
  overflow: hidden;
}

.preview-reveal {
  --preview-origin-x: 50vw;
  --preview-origin-y: 50vh;
  position: absolute;
  inset: 0;
  display: flex;
  flex-direction: column;
  pointer-events: none;
  -webkit-clip-path: circle(150vmax at var(--preview-origin-x) var(--preview-origin-y));
  clip-path: circle(150vmax at var(--preview-origin-x) var(--preview-origin-y));
  animation: preview-overlay-reveal 3000ms cubic-bezier(0.2, 0.9, 0.2, 1) both;
  will-change: clip-path;
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
  color: var(--font-black);
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
  pointer-events: auto;
}

@keyframes preview-overlay-reveal {
  from {
    -webkit-clip-path: circle(0px at var(--preview-origin-x) var(--preview-origin-y));
    clip-path: circle(0px at var(--preview-origin-x) var(--preview-origin-y));
  }

  to {
    -webkit-clip-path: circle(150vmax at var(--preview-origin-x) var(--preview-origin-y));
    clip-path: circle(150vmax at var(--preview-origin-x) var(--preview-origin-y));
  }
}

@media (prefers-reduced-motion: reduce) {
  .preview-reveal {
    animation: none;
    -webkit-clip-path: none;
    clip-path: none;
  }
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
  color: var(--font-black);
}

.sub-title {
  margin: 0.2rem 0 0.1rem;
  font-size: 0.95rem;
  font-weight: 700;
  color: var(--font-black);
}

.submit {
  display: inline-flex;
  justify-content: center;
  align-items: center;
  padding: 0.55rem 1rem;
  background-color: var(--font-blue);
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
  color: var(--font-gray);
  font-size: 0.9rem;
  line-height: 1.6;
}

@media (max-width: 920px) {
  .editor {
    height: 480px;
  }
}

@media (max-width: 768px) {
  .title {
    font-size: 2rem;
  }

  .subtitle {
    font-size: 1rem;
  }
}
</style>
