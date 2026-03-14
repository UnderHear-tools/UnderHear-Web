<template>
  <ComponentDocsPage>
    <ComponentDocsHeader
      title="ActionList 操作列表"
      description="用于下拉菜单、导航菜单等场景的操作列表组件。"
    />

    <ComponentDocsSection title="基础用法">
      <template #description>
        使用 <code>ActionList</code> 作为容器，<code>ActionList.Item</code> 作为列表项。
      </template>

      <ComponentDocsDemoBlock :code="demo1Code">
        <ActionList>
          <ActionList.Item>新建文件</ActionList.Item>
          <ActionList.Item>打开文件</ActionList.Item>
          <ActionList.Item>保存文件</ActionList.Item>
        </ActionList>
      </ComponentDocsDemoBlock>
    </ComponentDocsSection>

    <ComponentDocsSection title="带链接的列表项">
      <template #description>
        通过 <code>href</code> 属性可以将列表项渲染为链接。
      </template>

      <ComponentDocsDemoBlock :code="demo2Code">
        <ActionList>
          <ActionList.Item
            href="https://github.com"
            new-tab
          >
            GitHub (新标签页)
          </ActionList.Item>
          <ActionList.Item href="https://vuejs.org">
            Vue.js
          </ActionList.Item>
          <ActionList.Item
            href="https://vitejs.dev"
            new-tab
          >
            Vite (新标签页)
          </ActionList.Item>
        </ActionList>
      </ComponentDocsDemoBlock>
    </ComponentDocsSection>

    <ComponentDocsSection title="配合分割线">
      <template #description>
        使用 <code>zDivider</code> 分隔不同类别的列表项。
      </template>

      <ComponentDocsDemoBlock :code="demo3Code">
        <ActionList>
          <ActionList.Item>个人资料</ActionList.Item>
          <ActionList.Item>账户设置</ActionList.Item>
          <zDivider />
          <ActionList.Item>退出登录</ActionList.Item>
        </ActionList>
      </ComponentDocsDemoBlock>
    </ComponentDocsSection>

    <ComponentDocsSection title="配合 Dropdown 使用 keep-open">
      <template #description>
        在 <code>zDropdown</code> 内使用时，点击列表项默认会关闭下拉菜单。为某个 <code>ActionList.Item</code> 添加
        <code>keep-open</code> 属性，点击后不会收起内容区。
      </template>

      <ComponentDocsDemoBlock :code="demo4Code">
        <zDropdown>
          <template #trigger>
            <button class="demo-trigger">
              点击展开
            </button>
          </template>
          <template #content>
            <ActionList>
              <ActionList.Item>点击后关闭</ActionList.Item>
              <ActionList.Item keep-open>keep-open：点击不关闭</ActionList.Item>
            </ActionList>
          </template>
        </zDropdown>
      </ComponentDocsDemoBlock>
    </ComponentDocsSection>

    <ComponentDocsSection
      title="API"
      variant="api"
    >
      <h4>ActionList</h4>
      <zTable
        :columns="slotsTableColumns"
        :data="actionListSlotsRows"
        row-key="name"
        compact
        :hoverable="false"
      />

      <h4 style="margin-top: 24px;">
        ActionList.Item Props
      </h4>
      <zTable
        :columns="apiTableColumns"
        :data="actionListItemPropsRows"
        row-key="name"
        compact
        :hoverable="false"
      />

      <h4 style="margin-top: 24px;">
        ActionList.Item Events
      </h4>
      <zTable
        :columns="eventsTableColumns"
        :data="actionListItemEventsRows"
        row-key="name"
        compact
        :hoverable="false"
      />
    </ComponentDocsSection>
  </ComponentDocsPage>
</template>

<script setup lang="ts">
import { zDivider } from '@/components/z-ui/divider'
import { ActionList } from '@/components/z-ui/action-list'
import { zDropdown } from '@/components/z-ui/dropdown'
import { zTable, type ZTableColumn } from '@/components/z-ui/table'
import ComponentDocsDemoBlock from '@/modules/components/components/ComponentDocsPage/ComponentDocsDemoBlock.vue'
import ComponentDocsHeader from '@/modules/components/components/ComponentDocsPage/ComponentDocsHeader.vue'
import ComponentDocsPage from '@/modules/components/components/ComponentDocsPage/ComponentDocsPage.vue'
import ComponentDocsSection from '@/modules/components/components/ComponentDocsPage/ComponentDocsSection.vue'

const demo1Code = `<template>
  <ActionList>
    <ActionList.Item>新建文件</ActionList.Item>
    <ActionList.Item>打开文件</ActionList.Item>
    <ActionList.Item>保存文件</ActionList.Item>
  </ActionList>
</template>

<script setup lang="ts">
import { ActionList } from '@/components/z-ui/action-list'
<\/script>`

const demo2Code = `<template>
  <ActionList>
    <ActionList.Item href="https://github.com" new-tab>GitHub (新标签页)</ActionList.Item>
    <ActionList.Item href="https://vuejs.org">Vue.js</ActionList.Item>
    <ActionList.Item href="https://vitejs.dev" new-tab>Vite (新标签页)</ActionList.Item>
  </ActionList>
</template>

<script setup lang="ts">
import { ActionList } from '@/components/z-ui/action-list'
<\/script>`

const demo3Code = `<template>
  <ActionList>
    <ActionList.Item>个人资料</ActionList.Item>
    <ActionList.Item>账户设置</ActionList.Item>
    <zDivider />
    <ActionList.Item>退出登录</ActionList.Item>
  </ActionList>
</template>

<script setup lang="ts">
import { zDivider } from '@/components/z-ui/divider'
import { ActionList } from '@/components/z-ui/action-list'
<\/script>`

const demo4Code = `<template>
  <zDropdown>
    <template #trigger>
      <button>点击展开</button>
    </template>
    <template #content>
      <ActionList>
        <ActionList.Item>点击后关闭</ActionList.Item>
        <ActionList.Item keep-open>keep-open：点击不关闭</ActionList.Item>
      </ActionList>
    </template>
  </zDropdown>
</template>

<script setup lang="ts">
import { zDropdown } from '@/components/z-ui/dropdown'
import { ActionList } from '@/components/z-ui/action-list'
<\/script>`

const slotsTableColumns: ZTableColumn[] = [
  { key: 'name', label: '插槽名', rowHeader: true, minWidth: '140px' },
  { key: 'description', label: '说明', minWidth: '300px', wrap: true }
]

const actionListSlotsRows = [
  {
    name: 'default',
    description: '列表内容，通常放置 ActionList.Item 组件'
  }
]

const apiTableColumns: ZTableColumn[] = [
  { key: 'name', label: '属性名', rowHeader: true, minWidth: '140px' },
  { key: 'description', label: '说明', minWidth: '200px', wrap: true },
  { key: 'type', label: '类型', minWidth: '160px' },
  { key: 'default', label: '默认值', minWidth: '120px' }
]

const actionListItemPropsRows = [
  {
    name: 'href',
    description: '链接地址，设置后列表项会渲染为 <a> 标签',
    type: 'string',
    default: '—'
  },
  {
    name: 'newTab',
    description: '是否在新标签页打开链接（仅在设置 href 时生效）',
    type: 'boolean',
    default: 'false'
  },
  {
    name: 'keepOpen',
    description: '在 zDropdown 内使用时，点击该列表项后不会收起下拉内容',
    type: 'boolean',
    default: 'false'
  }
]

const eventsTableColumns: ZTableColumn[] = [
  { key: 'name', label: '事件名', rowHeader: true, minWidth: '140px' },
  { key: 'description', label: '说明', minWidth: '300px', wrap: true }
]

const actionListItemEventsRows = [
  {
    name: 'click',
    description: '点击列表项时触发'
  }
]
</script>

<style scoped>
h4 {
  margin: 0 0 12px;
  font-size: 14px;
  font-weight: 600;
  color: var(--fgColor-default);
}

.demo-trigger {
  padding: 8px 16px;
  border: 1px solid var(--borderColor-default);
  border-radius: 6px;
  background: var(--control-transparent-bgColor-rest, #ffffff00);
  font-size: 14px;
  cursor: pointer;
}

.demo-trigger:hover {
  background: var(--control-transparent-bgColor-hover, #818b981a);
}
</style>
