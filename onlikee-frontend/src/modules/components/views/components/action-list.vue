<template>
  <ComponentDocsPage>
    <ComponentDocsHeader
      title="ActionList 操作列表"
      description="用于下拉菜单、选择列表和分组操作菜单的列表组件。"
    />

    <ComponentDocsSection title="基础用法">
      <template #description>
        <code>ActionList</code> 渲染列表容器，<code>ActionList.Item</code> 渲染操作项。
      </template>

      <ComponentDocsDemoBlock :code="demo1Code">
        <ActionList role="menu">
          <ActionList.Item role="menuitem">
            新建文件
          </ActionList.Item>
          <ActionList.Item role="menuitem">
            打开文件
          </ActionList.Item>
          <ActionList.Item role="menuitem">
            保存文件
          </ActionList.Item>
        </ActionList>
      </ComponentDocsDemoBlock>
    </ComponentDocsSection>

    <ComponentDocsSection title="视觉区域和说明">
      <template #description>
        leading、description 和 trailing 区域与 Primer ActionList 保持一致。
      </template>

      <ComponentDocsDemoBlock :code="demo2Code">
        <ActionList class="demo-list">
          <ActionList.Item description-variant="block">
            <template #leadingVisual>
              <FileIcon />
            </template>
            README.md
            <template #description>
              最近更新于 2 小时前
            </template>
            <template #trailingVisual>
              <LinkExternalIcon />
            </template>
          </ActionList.Item>
          <ActionList.Item
            description-variant="inline"
            truncate-description
          >
            <template #leadingVisual>
              <RepoIcon />
            </template>
            onlikee
            <template #description>
              Vue 组件库中的操作列表菜单
            </template>
            <template #trailingVisual>
              <ChevronRightIcon />
            </template>
          </ActionList.Item>
        </ActionList>
      </ComponentDocsDemoBlock>
    </ComponentDocsSection>

    <ComponentDocsSection title="选择状态">
      <template #description>
        在列表上设置 <code>selectionVariant</code>，再由每个列表项的 <code>selected</code> 控制选中状态。
      </template>

      <ComponentDocsDemoBlock :code="demo3Code">
        <div class="demo-grid">
          <ActionList
            selection-variant="single"
            role="listbox"
            class="demo-list"
          >
            <ActionList.Item
              v-for="item in singleOptions"
              :key="item"
              role="option"
              :selected="selectedSingle === item"
              @select="selectedSingle = item"
            >
              {{ item }}
            </ActionList.Item>
          </ActionList>

          <ActionList
            selection-variant="multiple"
            role="listbox"
            class="demo-list"
          >
            <ActionList.Item
              v-for="item in multiOptions"
              :key="item"
              role="option"
              :selected="selectedMultiple.includes(item)"
              @select="toggleMultiple(item)"
            >
              {{ item }}
            </ActionList.Item>
          </ActionList>
        </div>
      </ComponentDocsDemoBlock>
    </ComponentDocsSection>

    <ComponentDocsSection title="状态">
      <template #description>
        支持 active、disabled、loading 和 danger 状态。
      </template>

      <ComponentDocsDemoBlock :code="demo4Code">
        <ActionList
          role="menu"
          class="demo-list"
        >
          <ActionList.Item
            role="menuitem"
            active
          >
            当前页面
          </ActionList.Item>
          <ActionList.Item
            role="menuitem"
            disabled
          >
            暂不可用
          </ActionList.Item>
          <ActionList.Item
            role="menuitem"
            loading
          >
            正在同步
          </ActionList.Item>
          <ActionList.Divider />
          <ActionList.Item
            role="menuitem"
            variant="danger"
          >
            <template #leadingVisual>
              <TrashIcon />
            </template>
            删除项目
          </ActionList.Item>
        </ActionList>
      </ComponentDocsDemoBlock>
    </ComponentDocsSection>

    <ComponentDocsSection title="分组和分隔线">
      <template #description>
        使用 <code>ActionList.Group</code> 和 <code>ActionList.Divider</code> 组织菜单结构。
      </template>

      <ComponentDocsDemoBlock :code="demo5Code">
        <ActionList class="demo-list">
          <ActionList.Group title="账户">
            <ActionList.Item>
              <template #leadingVisual>
                <PersonIcon />
              </template>
              个人资料
            </ActionList.Item>
            <ActionList.Item>
              <template #leadingVisual>
                <GearIcon />
              </template>
              设置
            </ActionList.Item>
          </ActionList.Group>
          <ActionList.Divider />
          <ActionList.Group
            title="危险区域"
            heading-variant="filled"
          >
            <ActionList.Item variant="danger">
              <template #leadingVisual>
                <SignOutIcon />
              </template>
              退出登录
            </ActionList.Item>
          </ActionList.Group>
        </ActionList>
      </ComponentDocsDemoBlock>
    </ComponentDocsSection>

    <ComponentDocsSection title="配合 Dropdown 使用">
      <template #description>
        在 <code>Dropdown</code> 内容区使用 ActionList 时，列表项的外观会与 Primer 菜单保持一致。
      </template>

      <ComponentDocsDemoBlock :code="demo6Code">
        <Dropdown>
          <template #trigger>
            <Button>
              更多操作
              <template #trailingVisual>
                <TriangleDownIcon />
              </template>
            </Button>
          </template>
          <template #content>
            <ActionList role="menu">
              <ActionList.Item role="menuitem">
                <template #leadingVisual>
                  <PencilIcon />
                </template>
                编辑
              </ActionList.Item>
              <ActionList.LinkItem
                href="https://github.com"
                new-tab
                role="menuitem"
              >
                <template #leadingVisual>
                  <MarkGithubIcon />
                </template>
                GitHub
              </ActionList.LinkItem>
            </ActionList>
          </template>
        </Dropdown>
      </ComponentDocsDemoBlock>
    </ComponentDocsSection>

    <ComponentDocsSection
      title="API"
      variant="api"
    >
      <h4>ActionList Props</h4>
      <Table
        :columns="apiTableColumns"
        :data="actionListPropsRows"
        row-key="name"
        compact
        :hoverable="false"
      />

      <h4>ActionList.Item Props</h4>
      <Table
        :columns="apiTableColumns"
        :data="actionListItemPropsRows"
        row-key="name"
        compact
        :hoverable="false"
      />

      <h4>ActionList.LinkItem Props</h4>
      <Table
        :columns="apiTableColumns"
        :data="actionListLinkItemPropsRows"
        row-key="name"
        compact
        :hoverable="false"
      />

      <h4>Events</h4>
      <Table
        :columns="eventsTableColumns"
        :data="eventsRows"
        row-key="name"
        compact
        :hoverable="false"
      />

      <h4>Slots</h4>
      <Table
        :columns="slotsTableColumns"
        :data="slotsRows"
        row-key="name"
        compact
        :hoverable="false"
      />
    </ComponentDocsSection>
  </ComponentDocsPage>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { ActionList } from '@/components/z-ui/action-list'
import { Button } from '@/components/z-ui/button'
import { Dropdown } from '@/components/z-ui/dropdown'
import { Table, type TableColumn } from '@/components/z-ui/table'
import {
  ChevronRightIcon,
  FileIcon,
  GearIcon,
  LinkExternalIcon,
  MarkGithubIcon,
  PencilIcon,
  PersonIcon,
  RepoIcon,
  SignOutIcon,
  TrashIcon,
  TriangleDownIcon
} from '@/components/octicons-vue3'
import ComponentDocsDemoBlock from '@/modules/components/components/ComponentDocsPage/ComponentDocsDemoBlock.vue'
import ComponentDocsHeader from '@/modules/components/components/ComponentDocsPage/ComponentDocsHeader.vue'
import ComponentDocsPage from '@/modules/components/components/ComponentDocsPage/ComponentDocsPage.vue'
import ComponentDocsSection from '@/modules/components/components/ComponentDocsPage/ComponentDocsSection.vue'

const singleOptions = ['公开的', '私有的', '内部可见']
const multiOptions = ['Issues', 'Pull requests', 'Discussions']
const selectedSingle = ref(singleOptions[0])
const selectedMultiple = ref<string[]>(['Issues'])

function toggleMultiple(value: string) {
  selectedMultiple.value = selectedMultiple.value.includes(value)
    ? selectedMultiple.value.filter(item => item !== value)
    : [...selectedMultiple.value, value]
}

const demo1Code = `<template>
  <ActionList role="menu">
    <ActionList.Item role="menuitem">新建文件</ActionList.Item>
    <ActionList.Item role="menuitem">打开文件</ActionList.Item>
    <ActionList.Item role="menuitem">保存文件</ActionList.Item>
  </ActionList>
</template>

<script setup lang="ts">
import { ActionList } from '@/components/z-ui/action-list'
<\/script>`

const demo2Code = `<template>
  <ActionList>
    <ActionList.Item description-variant="block">
      <template #leadingVisual><FileIcon /></template>
      README.md
      <template #description>最近更新于 2 小时前</template>
      <template #trailingVisual><LinkExternalIcon /></template>
    </ActionList.Item>
    <ActionList.Item description-variant="inline" truncate-description>
      <template #leadingVisual><RepoIcon /></template>
      onlikee
      <template #description>Vue 组件库中的操作列表菜单</template>
      <template #trailingVisual><ChevronRightIcon /></template>
    </ActionList.Item>
  </ActionList>
</template>

<script setup lang="ts">
import { ActionList } from '@/components/z-ui/action-list'
import { ChevronRightIcon, FileIcon, LinkExternalIcon, RepoIcon } from '@/components/octicons-vue3'
<\/script>`

const demo3Code = `<template>
  <ActionList selection-variant="single" role="listbox">
    <ActionList.Item
      v-for="item in options"
      :key="item"
      role="option"
      :selected="selected === item"
      @select="selected = item"
    >
      {{ item }}
    </ActionList.Item>
  </ActionList>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { ActionList } from '@/components/z-ui/action-list'

const options = ['公开的', '私有的', '内部可见']
const selected = ref(options[0])
<\/script>`

const demo4Code = `<template>
  <ActionList role="menu">
    <ActionList.Item role="menuitem" active>当前页面</ActionList.Item>
    <ActionList.Item role="menuitem" disabled>暂不可用</ActionList.Item>
    <ActionList.Item role="menuitem" loading>正在同步</ActionList.Item>
    <ActionList.Divider />
    <ActionList.Item role="menuitem" variant="danger">
      <template #leadingVisual><TrashIcon /></template>
      删除项目
    </ActionList.Item>
  </ActionList>
</template>

<script setup lang="ts">
import { ActionList } from '@/components/z-ui/action-list'
import { TrashIcon } from '@/components/octicons-vue3'
<\/script>`

const demo5Code = `<template>
  <ActionList>
    <ActionList.Group title="账户">
      <ActionList.Item>
        <template #leadingVisual><PersonIcon /></template>
        个人资料
      </ActionList.Item>
      <ActionList.Item>
        <template #leadingVisual><GearIcon /></template>
        设置
      </ActionList.Item>
    </ActionList.Group>
    <ActionList.Divider />
    <ActionList.Group title="危险区域" heading-variant="filled">
      <ActionList.Item variant="danger">
        <template #leadingVisual><SignOutIcon /></template>
        退出登录
      </ActionList.Item>
    </ActionList.Group>
  </ActionList>
</template>

<script setup lang="ts">
import { ActionList } from '@/components/z-ui/action-list'
import { GearIcon, PersonIcon, SignOutIcon } from '@/components/octicons-vue3'
<\/script>`

const demo6Code = `<template>
  <Dropdown>
    <template #trigger>
      <Button>
        更多操作
        <template #trailingVisual><TriangleDownIcon /></template>
      </Button>
    </template>
    <template #content>
      <ActionList role="menu">
        <ActionList.Item role="menuitem">
          <template #leadingVisual><PencilIcon /></template>
          编辑
        </ActionList.Item>
        <ActionList.LinkItem href="https://github.com" new-tab role="menuitem">
          <template #leadingVisual><MarkGithubIcon /></template>
          GitHub
        </ActionList.LinkItem>
      </ActionList>
    </template>
  </Dropdown>
</template>

<script setup lang="ts">
import { ActionList } from '@/components/z-ui/action-list'
import { Button } from '@/components/z-ui/button'
import { Dropdown } from '@/components/z-ui/dropdown'
import { MarkGithubIcon, PencilIcon, TriangleDownIcon } from '@/components/octicons-vue3'
<\/script>`

const apiTableColumns: TableColumn[] = [
  { key: 'name', label: '属性名', rowHeader: true, minWidth: '160px' },
  { key: 'default', label: '默认值', minWidth: '120px' },
  { key: 'type', label: '类型', minWidth: '220px' },
  { key: 'description', label: '说明', minWidth: '260px', wrap: true }
]

const actionListPropsRows = [
  { name: 'variant', type: "'inset' | 'horizontal-inset' | 'full'", default: "'inset'", description: '控制列表边距样式' },
  { name: 'selectionVariant', type: "'single' | 'multiple'", default: '—', description: '显示单选勾选或多选 checkbox' },
  { name: 'showDividers', type: 'boolean', default: 'false', description: '在连续列表项之间显示细分隔线' },
  { name: 'role', type: 'string', default: '—', description: '列表语义角色，例如 menu 或 listbox' }
]

const actionListItemPropsRows = [
  { name: 'selected', type: 'boolean', default: 'false', description: '是否处于选中状态，需要配合 selectionVariant 使用' },
  { name: 'active', type: 'boolean', default: 'false', description: '当前项状态，会显示选中背景和左侧强调线' },
  { name: 'variant', type: "'default' | 'danger'", default: "'default'", description: '默认或危险操作样式' },
  { name: 'disabled', type: 'boolean', default: 'false', description: '禁用操作项' },
  { name: 'loading', type: 'boolean', default: 'false', description: '显示加载状态并禁用操作' },
  { name: 'size', type: "'medium' | 'large'", default: "'medium'", description: '控制列表项垂直间距' },
  { name: 'role', type: 'string', default: '—', description: '列表项语义角色，例如 menuitem 或 option' },
  { name: 'descriptionVariant', type: "'inline' | 'block'", default: "'inline'", description: '说明文字显示在同一行或下一行' },
  { name: 'truncateDescription', type: 'boolean', default: 'false', description: '单行截断说明文字' }
]

const actionListLinkItemPropsRows = [
  { name: 'href', type: 'string', default: '—', description: '链接地址' },
  { name: 'newTab', type: 'boolean', default: 'false', description: '是否在新标签页打开链接' },
  { name: 'target', type: 'string', default: '—', description: '链接 target，会覆盖 newTab 推导值' },
  { name: 'rel', type: 'string', default: '—', description: '链接 rel，会覆盖 newTab 推导值' },
  { name: '其他 Item Props', type: 'ActionList.Item props', default: '—', description: 'LinkItem 支持 Item 的视觉状态 props' }
]

const eventsTableColumns: TableColumn[] = [
  { key: 'name', label: '事件名', rowHeader: true, minWidth: '160px' },
  { key: 'description', label: '说明', minWidth: '300px', wrap: true }
]

const eventsRows = [
  { name: 'select', description: '启用状态下点击列表项时触发，disabled 和 loading 状态不会触发' }
]

const slotsTableColumns: TableColumn[] = [
  { key: 'name', label: '插槽名', rowHeader: true, minWidth: '160px' },
  { key: 'description', label: '说明', minWidth: '300px', wrap: true }
]

const slotsRows = [
  { name: 'default', description: '列表项主标签，或 ActionList 的子项' },
  { name: 'leadingVisual', description: '列表项左侧图标区域' },
  { name: 'description', description: '列表项说明文字' },
  { name: 'trailingVisual', description: '列表项右侧图标或辅助信息区域' },
  { name: 'heading', description: 'ActionList.Group 的自定义标题区域' },
  { name: 'trailingAction', description: 'ActionList.GroupHeading 的右侧操作区域' }
]
</script>

<style scoped>
h4 {
  margin: 24px 0 12px;
  color: var(--fgColor-default);
  font-size: 14px;
  font-weight: 600;
}

h4:first-child {
  margin-top: 0;
}

.demo-list {
  width: 280px;
}

.demo-grid {
  display: grid;
  grid-template-columns: repeat(2, minmax(220px, 1fr));
  gap: 24px;
  width: min(100%, 600px);
}

@media (max-width: 768px) {
  .demo-grid {
    grid-template-columns: 1fr;
  }
}
</style>
