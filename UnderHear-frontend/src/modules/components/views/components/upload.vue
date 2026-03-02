<template>
  <ComponentDocsPage>
    <ComponentDocsHeader
      title="Upload 上传"
      description="通过拖拽或点击选择文件进行上传。"
    />

    <ComponentDocsSection title="基础用法">
      <template #description>
        通过 <code>v-model</code> 绑定选中的文件对象，支持点击与拖拽两种方式。
      </template>
      <ComponentDocsDemoBlock :code="demo1Code">
        <zUpload v-model="file1" />
        <p
          v-if="file1"
          class="demo-info"
        >
          已选择：{{ file1.name }}（{{ (file1.size / 1024).toFixed(1) }} KB）
        </p>
      </ComponentDocsDemoBlock>
    </ComponentDocsSection>

    <ComponentDocsSection title="限制文件类型">
      <template #description>
        通过 <code>accept</code> 属性限制可选文件类型，格式与原生 <code>&lt;input accept&gt;</code> 一致。
      </template>
      <ComponentDocsDemoBlock :code="demo2Code">
        <zUpload
          v-model="file2"
          accept=".zip,.html"
          hint="仅支持 .zip 和 .html 文件"
        />
      </ComponentDocsDemoBlock>
    </ComponentDocsSection>

    <ComponentDocsSection title="提示文案">
      <template #description>
        通过 <code>hint</code> 属性在上传区域底部展示辅助说明。
      </template>
      <ComponentDocsDemoBlock :code="demo3Code">
        <zUpload
          v-model="file3"
          hint="支持 .zip 格式的 dist 构建包或 .html 文件"
        />
      </ComponentDocsDemoBlock>
    </ComponentDocsSection>

    <ComponentDocsSection title="自定义插槽">
      <template #description>
        使用默认插槽替换上传区域的内部内容。
      </template>
      <ComponentDocsDemoBlock :code="demo4Code">
        <zUpload v-model="file4">
          <div class="custom-upload-content">
            <Upload />
            <p class="custom-upload-text">
              点击或拖拽文件到这里
            </p>
            <p class="custom-upload-hint">
              最大支持 10 MB
            </p>
          </div>
        </zUpload>
      </ComponentDocsDemoBlock>
    </ComponentDocsSection>

    <ComponentDocsSection
      title="API"
      variant="api"
    >
      <h3>属性</h3>
      <zTable
        :columns="apiCols"
        :data="apiRows"
        row-key="name"
        compact
        :hoverable="false"
      />
      <h3>事件</h3>
      <zTable
        :columns="eventCols"
        :data="eventRows"
        row-key="name"
        compact
        :hoverable="false"
      />
      <h3>插槽</h3>
      <zTable
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
import { zUpload } from '@/components/z-ui/upload'
import { zTable, type ZTableColumn } from '@/components/z-ui/table'
import { Upload } from '@/components/z-ui/icon/Octicons-vue'
import ComponentDocsDemoBlock from '@/modules/components/components/ComponentDocsPage/ComponentDocsDemoBlock.vue'
import ComponentDocsHeader from '@/modules/components/components/ComponentDocsPage/ComponentDocsHeader.vue'
import ComponentDocsPage from '@/modules/components/components/ComponentDocsPage/ComponentDocsPage.vue'
import ComponentDocsSection from '@/modules/components/components/ComponentDocsPage/ComponentDocsSection.vue'

const file1 = ref<File | null>(null)
const file2 = ref<File | null>(null)
const file3 = ref<File | null>(null)
const file4 = ref<File | null>(null)

const demo1Code = `<template>
  <zUpload v-model="file" />
  <p v-if="file">已选择：{{ file.name }}（{{ (file.size / 1024).toFixed(1) }} KB）</p>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { zUpload } from '@/components/z-ui/upload'

const file = ref<File | null>(null)
<\/script>`

const demo2Code = `<template>
  <zUpload v-model="file" accept=".zip,.html" hint="仅支持 .zip 和 .html 文件" />
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { zUpload } from '@/components/z-ui/upload'

const file = ref<File | null>(null)
<\/script>`

const demo3Code = `<template>
  <zUpload v-model="file" hint="支持 .zip 格式的 dist 构建包或 .html 文件" />
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { zUpload } from '@/components/z-ui/upload'

const file = ref<File | null>(null)
<\/script>`

const demo4Code = `<template>
  <zUpload v-model="file">
    <div class="custom-upload-content">
      <Upload />
      <p>点击或拖拽文件到这里</p>
      <p>最大支持 10 MB</p>
    </div>
  </zUpload>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { zUpload } from '@/components/z-ui/upload'
import { Upload } from '@/components/z-ui/icon/Octicons-vue'

const file = ref<File | null>(null)
<\/script>`

const apiCols: ZTableColumn[] = [
  { key: 'name', label: '属性名', rowHeader: true, minWidth: '140px' },
  { key: 'description', label: '说明', minWidth: '200px', wrap: true },
  { key: 'type', label: '类型', minWidth: '200px', wrap: true },
  { key: 'default', label: '默认值', minWidth: '100px' }
]

const apiRows = [
  { name: 'modelValue', description: '绑定的文件对象（v-model）', type: 'File | null', default: 'null' },
  { name: 'accept', description: '接受的文件类型，格式同原生 input accept', type: 'string', default: "''" },
  { name: 'hint', description: '上传区域底部的提示文案', type: 'string', default: "''" }
]

const eventCols: ZTableColumn[] = [
  { key: 'name', label: '事件名', rowHeader: true, minWidth: '180px' },
  { key: 'description', label: '说明', minWidth: '200px', wrap: true },
  { key: 'type', label: '回调参数', minWidth: '200px', wrap: true }
]

const eventRows = [
  { name: 'update:modelValue', description: '选择文件或移除文件时触发', type: 'File | null' }
]

const slotCols: ZTableColumn[] = [
  { key: 'name', label: '插槽名', rowHeader: true, minWidth: '140px' },
  { key: 'description', label: '说明', minWidth: '300px', wrap: true }
]

const slotRows = [
  { name: 'default', description: '自定义上传区域的内部内容（替换默认图标和文字）' }
]
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
