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
    >
      <zFormControlValidation variant="error">
        {{ validation }}
      </zFormControlValidation>
    </p>
  </div>
</template>

<script setup lang="ts">
import { HtmlMonacoEditor } from '@/components/monaco-editor'
import LightBulb from '@/components/z-ui/icon/Octicons-vue/icons/light-bulb.vue'
import { zUpload } from '@/components/z-ui/upload'
import type { FrameworkValue } from './useCreateApplicationForm'
import { zFormControlValidation } from '@/components/z-ui/form-control'

interface Props {
  selectedFramework: FrameworkValue | null
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
  'update:html-source': [value: string]
}>()

function updateFile(value: File | null) {
  emit('update:file', value)
}

function updateHtmlSource(value: string) {
  emit('update:html-source', value)
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

.icon-svg {
  margin-top: 2px;
  margin-right: 4px;
  flex-shrink: 0;
}
</style>
