<template>
  <ComponentDocsPage>
    <ComponentDocsHeader title="Divider 分割线" description="用于分隔内容的水平分割线。" />

    <ComponentDocsSection title="基础用法">
      <template #description>最简单的分割线。</template>

      <ComponentDocsDemoBlock :code="demo1Code">
        <div class="demo-container">
          <p>上方内容</p>
          <zDivider />
          <p>下方内容</p>
        </div>
      </ComponentDocsDemoBlock>
    </ComponentDocsSection>

    <ComponentDocsSection title="多内容分隔">
      <template #description>分割线可以用于分隔多个段落或区块。</template>

      <ComponentDocsDemoBlock :code="demo2Code">
        <div class="demo-container">
          <p>第一段内容</p>
          <zDivider />
          <p>第二段内容</p>
          <zDivider />
          <p>第三段内容</p>
        </div>
      </ComponentDocsDemoBlock>
    </ComponentDocsSection>

    <ComponentDocsSection title="API" variant="api">
      <zTable
        :columns="apiTableColumns"
        :data="apiTableRows"
        row-key="name"
        compact
        :hoverable="false"
      />
    </ComponentDocsSection>
  </ComponentDocsPage>
</template>

<script setup lang="ts">
import { zDivider } from '@/components/z-ui/divider'
import { zTable, type ZTableColumn } from '@/components/z-ui/table'
import ComponentDocsDemoBlock from '@/module/components/components/ComponentDocsPage/ComponentDocsDemoBlock.vue'
import ComponentDocsHeader from '@/module/components/components/ComponentDocsPage/ComponentDocsHeader.vue'
import ComponentDocsPage from '@/module/components/components/ComponentDocsPage/ComponentDocsPage.vue'
import ComponentDocsSection from '@/module/components/components/ComponentDocsPage/ComponentDocsSection.vue'

const demo1Code = `<template>
  <p>上方内容</p>
  <zDivider />
  <p>下方内容</p>
</template>

<script setup lang="ts">
import { zDivider } from '@/components/z-ui/divider'
<\/script>`

const demo2Code = `<template>
  <p>第一段内容</p>
  <zDivider />
  <p>第二段内容</p>
  <zDivider />
  <p>第三段内容</p>
</template>

<script setup lang="ts">
import { zDivider } from '@/components/z-ui/divider'
<\/script>`

const apiTableColumns: ZTableColumn[] = [
  { key: 'name', label: '属性名', rowHeader: true, minWidth: '140px' },
  { key: 'description', label: '说明', minWidth: '200px', wrap: true },
  { key: 'type', label: '类型', minWidth: '160px', wrap: true },
  { key: 'default', label: '默认值', minWidth: '120px' }
]

const apiTableRows = [
  {
    name: '—',
    description: '该组件暂无属性',
    type: '—',
    default: '—'
  }
]
</script>

<style scoped>
.demo-container {
  max-width: 400px;
}

.demo-container p {
  margin: 0;
  color: #1f2328;
  font-size: 14px;
}
</style>
