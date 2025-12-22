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
          <button class="preview-button" @click="openPreview">点击预览</button>
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

function refreshPreview() {
  const code = editor?.getValue() ?? defaultTemplate
  return `<!doctype html><head><meta charset="utf-8"></head><html><body style="margin:0">${code}</body></html>`
}

function openPreview() {
  const previewWindow = window.open('about:blank', '_blank')
  if (!previewWindow) return

  const html = refreshPreview()
  const blob = new Blob([html], { type: 'text/html' })
  const url = URL.createObjectURL(blob)

  previewWindow.location.replace(url)
  previewWindow.addEventListener(
    'load',
    () => {
      URL.revokeObjectURL(url)
    },
    { once: true }
  )
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

})

onBeforeUnmount(() => {
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
