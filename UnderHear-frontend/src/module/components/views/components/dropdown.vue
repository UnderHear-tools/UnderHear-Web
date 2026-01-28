<template>
  <ComponentDocsPage>
    <ComponentDocsHeader title="Dropdown 下拉菜单" description="向下弹出的菜单容器。" />

    <ComponentDocsSection title="基础用法">
      <template #description>通过 <code>trigger</code> 插槽定义触发元素，默认插槽放置下拉内容。</template>

      <ComponentDocsDemoBlock :code="demo1Code" v-bind="demoLabels">
        <div class="dropdown-demo">
          <zDropdown>
            <template #trigger>
              <button class="demo-trigger">点击展开</button>
            </template>
            <div class="demo-content">
              <div class="demo-item">选项一</div>
              <div class="demo-item">选项二</div>
              <div class="demo-item">选项三</div>
            </div>
          </zDropdown>
        </div>
      </ComponentDocsDemoBlock>
    </ComponentDocsSection>

    <ComponentDocsSection title="API" variant="api">
      <h4>Slots 插槽</h4>
      <zTable
        :columns="slotsTableColumns"
        :data="slotsTableRows"
        row-key="name"
        compact
        :hoverable="false"
      />
    </ComponentDocsSection>
  </ComponentDocsPage>
</template>

<script setup lang="ts">
import { zDropdown } from '@/components/z-ui/dropdown'
import { zTable, type ZTableColumn } from '@/components/z-ui/table'
import ComponentDocsDemoBlock from '@/module/components/components/ComponentDocsPage/ComponentDocsDemoBlock.vue'
import ComponentDocsHeader from '@/module/components/components/ComponentDocsPage/ComponentDocsHeader.vue'
import ComponentDocsPage from '@/module/components/components/ComponentDocsPage/ComponentDocsPage.vue'
import ComponentDocsSection from '@/module/components/components/ComponentDocsPage/ComponentDocsSection.vue'

const demoLabels = {
  copyLabel: '复制代码',
  copiedLabel: '已复制',
  showCodeLabel: '查看代码',
  hideCodeLabel: '隐藏代码',
  hideSourceLabel: '隐藏源代码'
}

const demo1Code = `<template>
  <div class="dropdown-demo">
    <zDropdown>
      <template #trigger>
        <button class="demo-trigger">点击展开</button>
      </template>
      <div class="demo-content">
        <div class="demo-item">选项一</div>
        <div class="demo-item">选项二</div>
        <div class="demo-item">选项三</div>
      </div>
    </zDropdown>
  </div>
</template>

<script setup lang="ts">
import { zDropdown } from '@/components/z-ui/dropdown'
<\/script>

<style scoped>
.dropdown-demo {
  position: absolute;
}

.demo-trigger {
  padding: 8px 16px;
  border: 1px solid #d0d7de;
  border-radius: 6px;
  background: #fff;
  font-size: 14px;
  cursor: pointer;
}

.demo-trigger:hover {
  background: #f6f8fa;
}

.demo-content {
  padding: 8px;
  min-width: 120px;
}

.demo-item {
  padding: 6px 12px;
  border-radius: 4px;
  font-size: 14px;
  cursor: pointer;
}

.demo-item:hover {
  background: #f6f8fa;
}
<\/style>`

const slotsTableColumns: ZTableColumn[] = [
  { key: 'name', label: '插槽名', rowHeader: true, minWidth: '140px' },
  { key: 'description', label: '说明', minWidth: '300px', wrap: true }
]

const slotsTableRows = [
  {
    name: 'trigger',
    description: '触发下拉的元素，点击该元素会展开/收起下拉内容'
  },
  {
    name: 'default',
    description: '下拉菜单的内容'
  }
]
</script>

<style scoped>
.dropdown-demo {
  position: absolute;
}

.demo-trigger {
  padding: 8px 16px;
  border: 1px solid #d0d7de;
  border-radius: 6px;
  background: #fff;
  font-size: 14px;
  cursor: pointer;
}

.demo-trigger:hover {
  background: #f6f8fa;
}

.demo-content {
  padding: 8px;
  min-width: 120px;
}

.demo-item {
  padding: 6px 12px;
  border-radius: 4px;
  font-size: 14px;
  cursor: pointer;
}

.demo-item:hover {
  background: #f6f8fa;
}

h4 {
  margin: 0 0 12px;
  font-size: 14px;
  font-weight: 600;
  color: #1f2328;
}
</style>
