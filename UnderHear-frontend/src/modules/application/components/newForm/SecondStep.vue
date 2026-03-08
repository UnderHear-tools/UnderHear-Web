<template>
  <div>
    <div
      v-if="selectedFramework === null"
      class="upload-tip"
    >
      <LightBulb />
      请先选择一个框架
    </div>

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
}

defineProps<Props>()

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
</style>
