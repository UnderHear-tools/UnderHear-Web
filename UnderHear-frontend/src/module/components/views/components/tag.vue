<template>
  <ComponentDocsPage>
    <ComponentDocsHeader title="Tag 标签" description="用于标记和选择。" />

    <ComponentDocsSection title="基础用法">
      <template #description>直接使用 <code>zTechTag</code> 组件即可。</template>
      <ComponentDocsDemoBlock :code="demo1Code">
        <div class="tag-row">
          <zTechTag>Tag</zTechTag>
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
import { zTechTag } from '@/components/z-ui/tag'
import { zTable, type ZTableColumn } from '@/components/z-ui/table'
import ComponentDocsDemoBlock from '@/module/components/components/ComponentDocsPage/ComponentDocsDemoBlock.vue'
import ComponentDocsHeader from '@/module/components/components/ComponentDocsPage/ComponentDocsHeader.vue'
import ComponentDocsPage from '@/module/components/components/ComponentDocsPage/ComponentDocsPage.vue'
import ComponentDocsSection from '@/module/components/components/ComponentDocsPage/ComponentDocsSection.vue'

const demo1Code = `<template>
  <div class="tag-row">
    <zTechTag>Tag</zTechTag>
  </div>
</template>

<style scoped>
.tag-row {
  display: flex;
  gap: 20px;
}
</style>`

const apiTableColumns: ZTableColumn[] = [
  { key: 'name', label: '插槽名', rowHeader: true, minWidth: '140px' },
  { key: 'description', label: '说明', minWidth: '300px', wrap: true },
]

const apiTableRows = [
  {
    name: 'default',
    description: '标签的内容',
  }
]
</script>

<style scoped>
.tag-row {
  display: flex;
  gap: 20px;
}
</style>
