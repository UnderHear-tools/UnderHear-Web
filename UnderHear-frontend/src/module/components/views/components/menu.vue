<template>
  <ComponentDocsPage>
    <ComponentDocsHeader title="Menu 菜单" description="用于下拉菜单、导航菜单等场景的菜单组件。" />

    <ComponentDocsSection title="基础用法">
      <template #description>使用 <code>zMenu</code> 作为容器，<code>zMenuItem</code> 作为菜单项。</template>

      <ComponentDocsDemoBlock :code="demo1Code">
        <zMenu>
          <zMenuItem>新建文件</zMenuItem>
          <zMenuItem>打开文件</zMenuItem>
          <zMenuItem>保存文件</zMenuItem>
        </zMenu>
      </ComponentDocsDemoBlock>
    </ComponentDocsSection>

    <ComponentDocsSection title="带链接的菜单项">
      <template #description>通过 <code>href</code> 属性可以将菜单项渲染为链接。</template>

      <ComponentDocsDemoBlock :code="demo2Code">
        <zMenu>
          <zMenuItem href="https://github.com">GitHub</zMenuItem>
          <zMenuItem href="https://vuejs.org">Vue.js</zMenuItem>
          <zMenuItem href="https://vitejs.dev">Vite</zMenuItem>
        </zMenu>
      </ComponentDocsDemoBlock>
    </ComponentDocsSection>

    <ComponentDocsSection title="配合分割线">
      <template #description>使用 <code>zDivider</code> 分隔不同类别的菜单项。</template>

      <ComponentDocsDemoBlock :code="demo3Code">
        <zMenu>
          <zMenuItem>个人资料</zMenuItem>
          <zMenuItem>账户设置</zMenuItem>
          <zDivider />
          <zMenuItem>退出登录</zMenuItem>
        </zMenu>
      </ComponentDocsDemoBlock>
    </ComponentDocsSection>

    <ComponentDocsSection title="API" variant="api">
      <h4>zMenu</h4>
      <zTable
        :columns="slotsTableColumns"
        :data="menuSlotsRows"
        row-key="name"
        compact
        :hoverable="false"
      />

      <h4 style="margin-top: 24px;">zMenuItem Props</h4>
      <zTable
        :columns="apiTableColumns"
        :data="menuItemPropsRows"
        row-key="name"
        compact
        :hoverable="false"
      />

      <h4 style="margin-top: 24px;">zMenuItem Events</h4>
      <zTable
        :columns="eventsTableColumns"
        :data="menuItemEventsRows"
        row-key="name"
        compact
        :hoverable="false"
      />
    </ComponentDocsSection>
  </ComponentDocsPage>
</template>

<script setup lang="ts">
import { zDivider } from '@/components/z-ui/divider'
import { zMenu, zMenuItem } from '@/components/z-ui/menu'
import { zTable, type ZTableColumn } from '@/components/z-ui/table'
import ComponentDocsDemoBlock from '@/module/components/components/ComponentDocsPage/ComponentDocsDemoBlock.vue'
import ComponentDocsHeader from '@/module/components/components/ComponentDocsPage/ComponentDocsHeader.vue'
import ComponentDocsPage from '@/module/components/components/ComponentDocsPage/ComponentDocsPage.vue'
import ComponentDocsSection from '@/module/components/components/ComponentDocsPage/ComponentDocsSection.vue'

const demo1Code = `<template>
  <zMenu>
    <zMenuItem>新建文件</zMenuItem>
    <zMenuItem>打开文件</zMenuItem>
    <zMenuItem>保存文件</zMenuItem>
  </zMenu>
</template>

<script setup lang="ts">
import { zMenu, zMenuItem } from '@/components/z-ui/menu'
<\/script>`

const demo2Code = `<template>
  <zMenu>
    <zMenuItem href="https://github.com">GitHub</zMenuItem>
    <zMenuItem href="https://vuejs.org">Vue.js</zMenuItem>
    <zMenuItem href="https://vitejs.dev">Vite</zMenuItem>
  </zMenu>
</template>

<script setup lang="ts">
import { zMenu, zMenuItem } from '@/components/z-ui/menu'
<\/script>`

const demo3Code = `<template>
  <zMenu>
    <zMenuItem>个人资料</zMenuItem>
    <zMenuItem>账户设置</zMenuItem>
    <zDivider />
    <zMenuItem>退出登录</zMenuItem>
  </zMenu>
</template>

<script setup lang="ts">
import { zDivider } from '@/components/z-ui/divider'
import { zMenu, zMenuItem } from '@/components/z-ui/menu'
<\/script>`

const slotsTableColumns: ZTableColumn[] = [
  { key: 'name', label: '插槽名', rowHeader: true, minWidth: '140px' },
  { key: 'description', label: '说明', minWidth: '300px', wrap: true }
]

const menuSlotsRows = [
  {
    name: 'default',
    description: '菜单内容，通常放置 zMenuItem 组件'
  }
]

const apiTableColumns: ZTableColumn[] = [
  { key: 'name', label: '属性名', rowHeader: true, minWidth: '140px' },
  { key: 'description', label: '说明', minWidth: '200px', wrap: true },
  { key: 'type', label: '类型', minWidth: '160px' },
  { key: 'default', label: '默认值', minWidth: '120px' }
]

const menuItemPropsRows = [
  {
    name: 'href',
    description: '链接地址，设置后菜单项会渲染为 <a> 标签',
    type: 'string',
    default: '—'
  }
]

const eventsTableColumns: ZTableColumn[] = [
  { key: 'name', label: '事件名', rowHeader: true, minWidth: '140px' },
  { key: 'description', label: '说明', minWidth: '300px', wrap: true }
]

const menuItemEventsRows = [
  {
    name: 'click',
    description: '点击菜单项时触发'
  }
]
</script>

<style scoped>
h4 {
  margin: 0 0 12px;
  font-size: 14px;
  font-weight: 600;
  color: #1f2328;
}
</style>
