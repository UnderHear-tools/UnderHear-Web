<template>
  <div class="second-step">
    <div
      v-if="selectedFramework === null"
      class="upload-tip"
    >
      <LightBulb />
      请先选择一个框架
    </div>

    <div
      v-else
      class="step-panel"
      :class="{ 'step-panel--invalid': invalid }"
    >
      <HtmlMonacoEditor
        v-if="selectedFramework === 'html'"
        :model-value="htmlSource"
        @update:model-value="updateHtmlSource"
      />

      <zUpload
        v-if="selectedFramework === 'vue' || selectedFramework === 'react'"
        :model-value="file"
        accept=".zip,.html"
        hint="支持 .zip 格式的 dist 构建包或 .html 文件"
        @update:model-value="updateFile"
      />
    </div>
    <p
      v-if="invalid && validation"
      class="step-validation"
      role="alert"
    >
      <svg
        aria-hidden="true"
        focusable="false"
        width="12"
        height="12"
        viewBox="0 0 16 16"
        fill="currentColor"
        class="icon-svg"
      ><path d="M6.457 1.047c.659-1.234 2.427-1.234 3.086 0l6.082 11.378A1.75 1.75 0 0 1 14.082 15H1.918a1.75 1.75 0 0 1-1.543-2.575ZM8 5a.75.75 0 0 0-.75.75v2.5a.75.75 0 0 0 1.5 0v-2.5A.75.75 0 0 0 8 5Zm1 6a1 1 0 1 0-2 0 1 1 0 0 0 2 0Z" /></svg>
      {{ validation }}
    </p>
  </div>
</template>

<script setup lang="ts">
import { HtmlMonacoEditor } from '@/components/monaco-editor'
import LightBulb from '@/components/z-ui/icon/Octicons-vue/icons/light-bulb.vue'
import { zUpload } from '@/components/z-ui/upload'

type FrameworkType = 'html' | 'vue' | 'react'

interface Props {
  selectedFramework: FrameworkType | null
  file: File | null
  htmlSource: string
  invalid?: boolean
  validation?: string
}

withDefaults(defineProps<Props>(), {
  invalid: false,
  validation: ''
})

const emit = defineEmits<{
  'update:file': [value: File | null]
  'update:htmlSource': [value: string]
}>()

function updateFile(value: File | null) {
  emit('update:file', value)
}

function updateHtmlSource(value: string) {
  emit('update:htmlSource', value)
}
</script>

<style scoped>
.second-step {
  display: grid;
  gap: 8px;
}

.step-panel--invalid :deep(.html-editor-wrapper),
.step-panel--invalid :deep(.z-upload-drop) {
  border-color: var(--borderColor-danger-emphasis, #cf222e);
}

.step-panel--invalid :deep(.html-editor-wrapper:focus-within) {
  outline-color: var(--borderColor-danger-emphasis, #cf222e);
}

.upload-tip {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 10px;
  width: 100%;
  height: 100px;
  color: var(--fgColor-muted);
  font-size: 14px;
  line-height: 1.6;
}

.step-validation {
  display: flex;
  font-size: 12px;
  line-height: 1.3333333333;
  font-weight: 600;
  color: var(--fgColor-danger, #d1242f);
}

.icon-svg {
  margin-top: 2px;
  margin-right: 4px;
  flex-shrink: 0;
}
</style>
