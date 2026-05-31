<template>
  <ComponentDocsPage>
    <ComponentDocsHeader
      title="Admonition 提示框"
      description="用于在文档或页面中展示提示、警告、注意事项等不同级别的信息块。"
    />

    <ComponentDocsSection title="基础用法">
      <template #description>
        通过 <code>type</code> 属性切换五种语义变体：<code>note</code>、<code>tip</code>、<code>important</code>、<code>warning</code>、<code>caution</code>。
      </template>
      <ComponentDocsDemoBlock :code="demoBasicCode">
        <div class="admonition-demo-stack">
          <Admonition>
            这是一条普通注释信息。
          </Admonition>
          <Admonition type="tip">
            这是一个有用的提示或建议。
          </Admonition>
          <Admonition type="important">
            这是一条需要你关注的重要信息。
          </Admonition>
          <Admonition type="warning">
            注意：此操作可能存在风险，建议备份后再继续。
          </Admonition>
          <Admonition type="caution">
            危险：此操作不可逆，请确认后再执行。
          </Admonition>
        </div>
      </ComponentDocsDemoBlock>
    </ComponentDocsSection>

    <ComponentDocsSection title="带标题">
      <template #description>
        通过 <code>title</code> 属性设置标题，也可使用 <code>#title</code> 插槽自定义标题内容。
      </template>
      <ComponentDocsDemoBlock :code="demoTitleCode">
        <div class="admonition-demo-stack">
          <Admonition
            type="note"
            title="你知道吗？"
          >
            Markdown 文件中的 Admonition 语法会被渲染为此组件样式。
          </Admonition>
          <Admonition type="warning">
            <template #title>
              <strong>⚠️ 版本兼容性</strong>
            </template>
            此 API 在 v2.0 中已弃用，请迁移至新接口。
          </Admonition>
        </div>
      </ComponentDocsDemoBlock>
    </ComponentDocsSection>

    <ComponentDocsSection title="自定义图标">
      <template #description>
        通过 <code>iconPath</code> 属性传入自定义 SVG path <code>d</code> 值来覆盖默认图标。
      </template>
      <ComponentDocsDemoBlock :code="demoIconCode">
        <div class="admonition-demo-stack">
          <Admonition
            type="note"
            title="自定义图标"
            :icon-path="CUSTOM_ICON_PATH"
          >
            使用自定义 SVG 路径覆盖默认图标。
          </Admonition>
        </div>
      </ComponentDocsDemoBlock>
    </ComponentDocsSection>

    <ComponentDocsSection
      title="API"
      variant="api"
    >
      <h4>Props</h4>
      <Table
        :columns="apiTableColumns"
        :data="apiTableRows"
        row-key="name"
        compact
        :hoverable="false"
      />
      <h4 style="margin-top: 24px;">
        Slots
      </h4>
      <Table
        :columns="slotTableColumns"
        :data="slotTableRows"
        row-key="name"
        compact
        :hoverable="false"
      />
    </ComponentDocsSection>
  </ComponentDocsPage>
</template>

<script setup lang="ts">
import { Admonition } from '@/components/z-ui/Admonition'
import { Table, type TableColumn } from '@/components/z-ui/Table'
import ComponentDocsDemoBlock from '@/modules/components/components/ComponentDocsPage/ComponentDocsDemoBlock.vue'
import ComponentDocsHeader from '@/modules/components/components/ComponentDocsPage/ComponentDocsHeader.vue'
import ComponentDocsPage from '@/modules/components/components/ComponentDocsPage/ComponentDocsPage.vue'
import ComponentDocsSection from '@/modules/components/components/ComponentDocsPage/ComponentDocsSection.vue'

const CUSTOM_ICON_PATH = 'M8 0a8 8 0 1 1 0 16A8 8 0 0 1 8 0ZM4 7.25a.75.75 0 0 0 0 1.5h5.75v2.5a.751.751 0 0 0 1.288.522L13.83 9.03a.75.75 0 0 0 0-1.06l-2.792-2.743a.748.748 0 0 0-1.288.523v2.5H4Z'

// ---------- demo code strings ----------

const demoBasicCode = `<script setup lang="ts">
import { Admonition } from '@/components/z-ui/Admonition'
<\/script>

<template>
  <Admonition>这是一条普通注释信息。</Admonition>
  <Admonition type="tip">这是一个有用的提示或建议。</Admonition>
  <Admonition type="important">这是一条需要你关注的重要信息。</Admonition>
  <Admonition type="warning">注意：此操作可能存在风险，建议备份后再继续。</Admonition>
  <Admonition type="caution">危险：此操作不可逆，请确认后再执行。</Admonition>
</template>`

const demoTitleCode = `<script setup lang="ts">
import { Admonition } from '@/components/z-ui/Admonition'
<\/script>

<template>
  <Admonition type="note" title="你知道吗？">
    Markdown 文件中的 Admonition 语法会被渲染为此组件样式。
  </Admonition>

  <Admonition type="warning">
    <template #title>
      <strong>⚠️ 版本兼容性</strong>
    </template>
    此 API 在 v2.0 中已弃用，请迁移至新接口。
  </Admonition>
</template>`

const demoIconCode = `<script setup lang="ts">
import { Admonition } from '@/components/z-ui/Admonition'

const CUSTOM_ICON_PATH = 'M8 0a8 8 0 1 1 0 16A8 8 0 0 1 8 0ZM4 7.25a.75.75...'
<\/script>

<template>
  <Admonition
    type="note"
    title="自定义图标"
    :icon-path="CUSTOM_ICON_PATH"
  >
    使用自定义 SVG 路径覆盖默认图标。
  </Admonition>
</template>`

// ---------- API Tables ----------

const apiTableColumns: TableColumn[] = [
  { key: 'name', label: '属性名', rowHeader: true, minWidth: '140px' },
  { key: 'default', label: '默认值', minWidth: '100px' },
  { key: 'type', label: '类型', minWidth: '220px', wrap: true },
  { key: 'description', label: '说明', minWidth: '200px', wrap: true }
]

const apiTableRows = [
  {
    name: 'type',
    description: '语义变体类型',
    type: "'note' | 'tip' | 'important' | 'warning' | 'caution'",
    default: "'note'"
  },
  {
    name: 'title',
    description: '标题文本，也可通过 #title 插槽设置',
    type: 'string',
    default: '—'
  },
  {
    name: 'ariaLabel',
    description: '无障碍标签，默认根据 type 自动生成',
    type: 'string',
    default: '—'
  },
  {
    name: 'iconPath',
    description: '自定义 SVG path 的 d 属性值，覆盖默认图标',
    type: 'string',
    default: '—'
  }
]

const slotTableColumns: TableColumn[] = [
  { key: 'name', label: '插槽名', rowHeader: true, minWidth: '140px' },
  { key: 'description', label: '说明', minWidth: '300px', wrap: true }
]

const slotTableRows = [
  {
    name: 'default',
    description: '提示框正文内容'
  },
  {
    name: 'title',
    description: '自定义标题区域，优先级高于 title 属性'
  }
]
</script>

<style scoped>
.admonition-demo-stack {
  display: flex;
  flex-direction: column;
  gap: 12px;
}
</style>
