<template>
  <ComponentDocsPage>
    <ComponentDocsHeader
      title="Upload 上传"
      description="通过拖拽或点击选择文件，也可通过 directory 模式选择文件夹。"
    />

    <ComponentDocsSection title="基础用法">
      <template #description>
        通过 <code>v-model</code> 绑定选中的文件数组，默认保持单文件交互。
      </template>
      <ComponentDocsDemoBlock :code="demo1Code">
        <Upload v-model="files1" />
        <p
          v-if="files1.length"
          class="demo-info"
        >
          {{ formatSelectedInfo(files1) }}
        </p>
      </ComponentDocsDemoBlock>
    </ComponentDocsSection>

    <ComponentDocsSection title="限制文件类型">
      <template #description>
        通过 <code>accept</code> 属性限制可选文件类型，格式与原生 <code>&lt;input accept&gt;</code> 一致。
      </template>
      <ComponentDocsDemoBlock :code="demo2Code">
        <Upload
          v-model="files2"
          accept=".zip,.html"
          hint="仅支持 .zip 和 .html 文件"
        />
        <p
          v-if="files2.length"
          class="demo-info"
        >
          {{ formatSelectedInfo(files2) }}
        </p>
      </ComponentDocsDemoBlock>
    </ComponentDocsSection>

    <ComponentDocsSection title="文件夹模式">
      <template #description>
        设置 <code>directory</code> 后启用文件夹上传。组件会返回扁平化后的文件数组。
      </template>
      <ComponentDocsDemoBlock :code="demo3Code">
        <Upload
          v-model="files3"
          directory
          hint="点击选择文件夹，或拖拽文件夹到此处"
        />
        <p
          v-if="files3.length"
          class="demo-info"
        >
          {{ formatSelectedInfo(files3) }}
        </p>
      </ComponentDocsDemoBlock>
    </ComponentDocsSection>

    <ComponentDocsSection title="提示文案">
      <template #description>
        通过 <code>hint</code> 属性在上传区域底部展示辅助说明。
      </template>
      <ComponentDocsDemoBlock :code="demo4Code">
        <Upload
          v-model="files4"
          hint="支持 .zip 格式的 dist 构建包或 .html 文件"
        />
      </ComponentDocsDemoBlock>
    </ComponentDocsSection>

    <ComponentDocsSection title="自定义插槽">
      <template #description>
        使用默认插槽替换上传区域的内部内容。
      </template>
      <ComponentDocsDemoBlock :code="demo5Code">
        <Upload v-model="files5">
          <div class="custom-upload-content">
            <UploadIcon />
            <p class="custom-upload-text">
              点击或拖拽文件到这里
            </p>
            <p class="custom-upload-hint">
              最大支持 10 MB
            </p>
          </div>
        </Upload>
      </ComponentDocsDemoBlock>
    </ComponentDocsSection>

    <ComponentDocsSection
      title="API"
      variant="api"
    >
      <h3>属性</h3>
      <Table
        :columns="apiCols"
        :data="apiRows"
        row-key="name"
        compact
        :hoverable="false"
      />
      <h3>事件</h3>
      <Table
        :columns="eventCols"
        :data="eventRows"
        row-key="name"
        compact
        :hoverable="false"
      />
      <h3>插槽</h3>
      <Table
        :columns="slotCols"
        :data="slotRows"
        row-key="name"
        compact
        :hoverable="false"
      />
    </ComponentDocsSection>
  </ComponentDocsPage>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { Upload } from '@/components/z-ui/upload'
import { Table, type TableColumn } from '@/components/z-ui/table'
import { Upload as UploadIcon } from '@/components/octicons-vue3'
import ComponentDocsDemoBlock from '@/modules/components/components/ComponentDocsPage/ComponentDocsDemoBlock.vue'
import ComponentDocsHeader from '@/modules/components/components/ComponentDocsPage/ComponentDocsHeader.vue'
import ComponentDocsPage from '@/modules/components/components/ComponentDocsPage/ComponentDocsPage.vue'
import ComponentDocsSection from '@/modules/components/components/ComponentDocsPage/ComponentDocsSection.vue'

const files1 = ref<File[]>([])
const files2 = ref<File[]>([])
const files3 = ref<File[]>([])
const files4 = ref<File[]>([])
const files5 = ref<File[]>([])

const demo1Code = `<template>
  <Upload v-model="files" />
  <p v-if="files.length">{{ files[0].name }}</p>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { Upload } from '@/components/z-ui/upload'

const files = ref<File[]>([])
<\/script>`

const demo2Code = `<template>
  <Upload
    v-model="files"
    accept=".zip,.html"
    hint="仅支持 .zip 和 .html 文件"
  />
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { Upload } from '@/components/z-ui/upload'

const files = ref<File[]>([])
<\/script>`

const demo3Code = `<template>
  <Upload
    v-model="files"
    directory
    hint="点击选择文件夹，或拖拽文件夹到此处"
  />
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { Upload } from '@/components/z-ui/upload'

const files = ref<File[]>([])
<\/script>`

const demo4Code = `<template>
  <Upload v-model="files" hint="支持 .zip 格式的 dist 构建包或 .html 文件" />
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { Upload } from '@/components/z-ui/upload'

const files = ref<File[]>([])
<\/script>`

const demo5Code = `<template>
  <Upload v-model="files">
    <div class="custom-upload-content">
      <UploadIcon />
      <p>点击或拖拽文件到这里</p>
      <p>最大支持 10 MB</p>
    </div>
  </Upload>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { Upload } from '@/components/z-ui/upload'
import { Upload as UploadIcon } from '@/components/octicons-vue3'

const files = ref<File[]>([])
<\/script>`

const apiCols: TableColumn[] = [
  { key: 'name', label: '属性名', rowHeader: true, minWidth: '140px' },
  { key: 'default', label: '默认值', minWidth: '100px' },
  { key: 'type', label: '类型', minWidth: '200px', wrap: true },
  { key: 'description', label: '说明', minWidth: '200px', wrap: true }
]

const apiRows = [
  { name: 'modelValue', description: '绑定的文件数组（v-model）', type: 'File[]', default: '[]' },
  { name: 'accept', description: '接受的文件类型，格式同原生 input accept', type: 'string', default: "''" },
  { name: 'hint', description: '上传区域底部的提示文案', type: 'string', default: "''" },
  { name: 'directory', description: '启用文件夹上传模式，并返回扁平化后的文件数组', type: 'boolean', default: 'false' }
]

const eventCols: TableColumn[] = [
  { key: 'name', label: '事件名', rowHeader: true, minWidth: '180px' },
  { key: 'description', label: '说明', minWidth: '200px', wrap: true },
  { key: 'type', label: '回调参数', minWidth: '200px', wrap: true }
]

const eventRows = [
  { name: 'update:modelValue', description: '选择文件、文件夹或移除文件时触发', type: 'File[]' }
]

const slotCols: TableColumn[] = [
  { key: 'name', label: '插槽名', rowHeader: true, minWidth: '140px' },
  { key: 'description', label: '说明', minWidth: '300px', wrap: true }
]

const slotRows = [
  { name: 'default', description: '自定义上传区域的内部内容（替换默认图标和文字）' }
]

function formatSelectedInfo(files: File[]): string {
  if (files.length === 1) {
    return `已选择：${files[0].name}（${formatFileSize(files[0].size)}）`
  }

  const totalSize = files.reduce((sum, file) => sum + file.size, 0)
  return `已选择：${files.length} 个文件（${formatFileSize(totalSize)}）`
}

function formatFileSize(size: number): string {
  if (size < 1024) {
    return `${size} B`
  }
  if (size < 1024 * 1024) {
    return `${(size / 1024).toFixed(1)} KB`
  }
  if (size < 1024 * 1024 * 1024) {
    return `${(size / (1024 * 1024)).toFixed(1)} MB`
  }

  return `${(size / (1024 * 1024 * 1024)).toFixed(1)} GB`
}
</script>

<style scoped>
.demo-info {
  margin-top: 8px;
  font-size: 0.85rem;
  color: var(--fgColor-muted);
}

.custom-upload-content {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 4px;
  color: var(--fgColor-muted);
}

.custom-upload-text {
  margin: 0;
  font-size: 1rem;
  color: var(--fgColor-default);
}

.custom-upload-hint {
  margin: 0;
  font-size: 0.85rem;
  color: var(--fgColor-muted);
}
</style>
