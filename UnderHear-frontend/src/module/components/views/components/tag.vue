<template>
  <ComponentDocsPage>
    <ComponentDocsHeader title="Tag 标签" description="用于标记和选择。" />

    <ComponentDocsSection title="基础用法">
      <template #description>直接使用 <code>zTag</code> 组件即可。</template>
      <ComponentDocsDemoBlock :code="demo1Code">
        <div class="tag-row">
          <zTag>Tag</zTag>
        </div>
      </ComponentDocsDemoBlock>
    </ComponentDocsSection>

    <ComponentDocsSection title="不同尺寸">
      <template #description>通过 <code>size</code> 属性设置标签尺寸。</template>
      <ComponentDocsDemoBlock :code="demo2Code">
        <div class="tag-row">
          <zTag size="small">Small</zTag>
          <zTag size="medium">Medium</zTag>
          <zTag size="large">Large</zTag>
          <zTag size="xlarge">X-Large</zTag>
        </div>
      </ComponentDocsDemoBlock>
    </ComponentDocsSection>

    <ComponentDocsSection title="自定义颜色">
      <template #description>通过 <code>color</code> 和 <code>bgColor</code> 属性自定义字体颜色和背景色。</template>
      <ComponentDocsDemoBlock :code="demo3Code">
        <div class="tag-row">
          <zTag>默认</zTag>
          <zTag color="#1a7f37" bgColor="#dafbe1">成功</zTag>
          <zTag color="#9a6700" bgColor="#fff8c5">警告</zTag>
          <zTag color="#cf222e" bgColor="#ffebe9">危险</zTag>
          <zTag color="#6639ba" bgColor="#fbefff">紫色</zTag>
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
import { zTag } from '@/components/z-ui/tag'
import { zTable, type ZTableColumn } from '@/components/z-ui/table'
import ComponentDocsDemoBlock from '@/module/components/components/ComponentDocsPage/ComponentDocsDemoBlock.vue'
import ComponentDocsHeader from '@/module/components/components/ComponentDocsPage/ComponentDocsHeader.vue'
import ComponentDocsPage from '@/module/components/components/ComponentDocsPage/ComponentDocsPage.vue'
import ComponentDocsSection from '@/module/components/components/ComponentDocsPage/ComponentDocsSection.vue'

const demo1Code = `<template>
  <div class="tag-row">
    <zTag>Tag</zTag>
  </div>
</template>

<style scoped>
.tag-row {
  display: flex;
  gap: 20px;
}
</style>`

const demo2Code = `<template>
  <div class="tag-row">
    <zTag size="small">Small</zTag>
    <zTag size="medium">Medium</zTag>
    <zTag size="large">Large</zTag>
    <zTag size="xlarge">X-Large</zTag>
  </div>
</template>

<style scoped>
.tag-row {
  display: flex;
  align-items: center;
  gap: 20px;
}
</style>`

const demo3Code = `<template>
  <div class="tag-row">
    <zTag>默认</zTag>
    <zTag color="#1a7f37" bgColor="#dafbe1">成功</zTag>
    <zTag color="#9a6700" bgColor="#fff8c5">警告</zTag>
    <zTag color="#cf222e" bgColor="#ffebe9">危险</zTag>
    <zTag color="#6639ba" bgColor="#fbefff">紫色</zTag>
  </div>
</template>

<style scoped>
.tag-row {
  display: flex;
  align-items: center;
  gap: 20px;
}
</style>`

const apiTableColumns: ZTableColumn[] = [
  { key: 'name', label: '属性名', rowHeader: true, minWidth: '140px' },
  { key: 'description', label: '说明', minWidth: '200px', wrap: true },
  { key: 'type', label: '类型', minWidth: '200px', wrap: true },
  { key: 'default', label: '默认值', minWidth: '100px' }
]

const apiTableRows = [
  {
    name: 'size',
    description: '标签尺寸',
    type: "'small' | 'medium' | 'large' | 'xlarge'",
    default: "'medium'"
  },
  {
    name: 'color',
    description: '字体颜色',
    type: 'string',
    default: "'#0969da'"
  },
  {
    name: 'bgColor',
    description: '背景颜色',
    type: 'string',
    default: "'#ddf4ff'"
  }
]
</script>

<style scoped>
.tag-row {
  display: flex;
  align-items: center;
  gap: 20px;
}
</style>
