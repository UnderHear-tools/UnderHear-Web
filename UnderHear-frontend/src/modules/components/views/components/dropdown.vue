<template>
  <ComponentDocsPage>
    <ComponentDocsHeader title="Dropdown 下拉菜单" description="向下弹出的菜单容器。" />

    <ComponentDocsSection title="基础用法">
      <template #description>通过 <code>trigger</code> 插槽定义触发元素，content 插槽放置下拉内容。</template>

      <ComponentDocsDemoBlock :code="demo1Code">
        <div class="dropdown-demo">
          <zDropdown>
            <template #trigger>
              <button class="demo-trigger">点击展开</button>
            </template>
            <template #content>
              <div class="demo-content">
                <div class="demo-item">选项一</div>
                <div class="demo-item">选项二</div>
                <div class="demo-item">选项三</div>
              </div>
            </template>
          </zDropdown>
        </div>
      </ComponentDocsDemoBlock>
    </ComponentDocsSection>

    <ComponentDocsSection title="弹出方向">
      <template #description>通过 <code>placement</code> 设置下拉菜单弹出方向。</template>

      <ComponentDocsDemoBlock :code="demoPlacementCode">
        <div class="dropdown-placement-demo">
          <zDropdown placement="left-top">
            <template #trigger>
              <button class="demo-trigger">左上</button>
            </template>
            <template #content>
              <div class="demo-content">左上内容</div>
            </template>
          </zDropdown>
          <zDropdown placement="right-top">
            <template #trigger>
              <button class="demo-trigger">右上</button>
            </template>
            <template #content>
              <div class="demo-content">右上内容</div>
            </template>
          </zDropdown>
          <zDropdown placement="left-bottom">
            <template #trigger>
              <button class="demo-trigger">左下</button>
            </template>
            <template #content>
              <div class="demo-content">左下内容</div>
            </template>
          </zDropdown>
          <zDropdown placement="right-bottom">
            <template #trigger>
              <button class="demo-trigger">右下</button>
            </template>
            <template #content>
              <div class="demo-content">右下内容</div>
            </template>
          </zDropdown>
        </div>
      </ComponentDocsDemoBlock>
    </ComponentDocsSection>

    <ComponentDocsSection title="API" variant="api">
      <h4>Props 属性</h4>
      <zTable
        :columns="propsTableColumns"
        :data="propsTableRows"
        row-key="name"
        compact
        :hoverable="false"
      />
      <h4 style="margin-top: 24px;">Slots 插槽</h4>
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
import ComponentDocsDemoBlock from '@/modules/components/components/ComponentDocsPage/ComponentDocsDemoBlock.vue'
import ComponentDocsHeader from '@/modules/components/components/ComponentDocsPage/ComponentDocsHeader.vue'
import ComponentDocsPage from '@/modules/components/components/ComponentDocsPage/ComponentDocsPage.vue'
import ComponentDocsSection from '@/modules/components/components/ComponentDocsPage/ComponentDocsSection.vue'

const demo1Code = `<template>
  <div class="dropdown-demo">
    <zDropdown>
      <template #trigger>
        <button class="demo-trigger">点击展开</button>
      </template>
      <template #content>
        <div class="demo-content">
          <div class="demo-item">选项一</div>
          <div class="demo-item">选项二</div>
          <div class="demo-item">选项三</div>
        </div>
      </template>
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

const demoPlacementCode = `<template>
  <div class="dropdown-placement-demo">
    <zDropdown placement="left-top">
      <template #trigger>
        <button class="demo-trigger">左上</button>
      </template>
      <template #content>
        <div class="demo-content">左上内容</div>
      </template>
    </zDropdown>
    <zDropdown placement="right-top">
      <template #trigger>
        <button class="demo-trigger">右上</button>
      </template>
      <template #content>
        <div class="demo-content">右上内容</div>
      </template>
    </zDropdown>
    <zDropdown placement="left-bottom">
      <template #trigger>
        <button class="demo-trigger">左下</button>
      </template>
      <template #content>
        <div class="demo-content">左下内容</div>
      </template>
    </zDropdown>
    <zDropdown placement="right-bottom">
      <template #trigger>
        <button class="demo-trigger">右下</button>
      </template>
      <template #content>
        <div class="demo-content">右下内容</div>
      </template>
    </zDropdown>
  </div>
</template>

<script setup lang="ts">
import { zDropdown } from '@/components/z-ui/dropdown'
<\/script>

<style scoped>
.dropdown-placement-demo {
  display: flex;
  gap: 20px;
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
<\/style>`

const propsTableColumns: ZTableColumn[] = [
  { key: 'name', label: '属性名', rowHeader: true, minWidth: '140px' },
  { key: 'description', label: '说明', minWidth: '220px', wrap: true },
  { key: 'type', label: '类型', minWidth: '220px', wrap: true },
  { key: 'default', label: '默认值', minWidth: '120px' }
]

const propsTableRows = [
  {
    name: 'placement',
    description: '下拉菜单弹出方向',
    type: `'left-top' | 'right-top' | 'left-bottom' | 'right-bottom'`,
    default: 'right-bottom'
  }
]

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
    name: 'content',
    description: '下拉菜单的内容'
  }
]
</script>

<style scoped>
.dropdown-demo {
  position: absolute;
}

.dropdown-placement-demo {
  display: flex;
  gap: 20px;
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
