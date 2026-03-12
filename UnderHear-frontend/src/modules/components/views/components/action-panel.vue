<template>
  <ComponentDocsPage>
    <ComponentDocsHeader
      title="ActionPanel 操作面板"
      description="由 Dropdown 和 ActionList 组合而成的操作面板组件。"
    />

    <ComponentDocsSection title="基础用法">
      <template #description>
        使用
        <code>Dropdown</code>、
        <code>ActionList</code>、<code>ActionList.Item</code>
        组合而成的操作面板。点击按钮弹出操作列表。
      </template>
      <ComponentDocsDemoBlock :code="demo1Code">
        <zDropdown>
          <template #trigger>
            <zButton>
              <template #leadingVisual>
                <Plus />
              </template>
              新建
              <template #trailingVisual>
                <TriangleDown />
              </template>
            </zButton>
          </template>
          <template #content>
            <ActionList>
              <ActionList.Item @click="handleClick('新建文件')">
                <File class="action-icon" />
                新建文件
              </ActionList.Item>
              <ActionList.Item @click="handleClick('新建文件夹')">
                <Repo class="action-icon" />
                新建文件夹
              </ActionList.Item>
              <zDivider />
              <ActionList.Item @click="handleClick('从模板创建')">
                <Copy class="action-icon" />
                从模板创建
              </ActionList.Item>
            </ActionList>
          </template>
        </zDropdown>
      </ComponentDocsDemoBlock>
    </ComponentDocsSection>

    <ComponentDocsSection title="更多操作">
      <template #description>
        使用图标按钮作为触发器，适用于表格行尾的操作菜单。
      </template>
      <ComponentDocsDemoBlock :code="demo2Code">
        <zDropdown placement="left-bottom">
          <template #trigger>
            <zButton variant="invisible">
              <template #leadingVisual>
                <KebabHorizontal />
              </template>
            </zButton>
          </template>
          <template #content>
            <ActionList>
              <ActionList.Item>
                <Pencil class="action-icon" />
                编辑
              </ActionList.Item>
              <ActionList.Item>
                <Copy class="action-icon" />
                复制
              </ActionList.Item>
              <zDivider />
              <ActionList.Item>
                <Trash class="action-icon danger" />
                <span class="danger">删除</span>
              </ActionList.Item>
            </ActionList>
          </template>
        </zDropdown>
      </ComponentDocsDemoBlock>
    </ComponentDocsSection>

    <ComponentDocsSection title="带链接的操作面板">
      <template #description>
        通过 <code>href</code> 属性将操作项渲染为链接。
      </template>
      <ComponentDocsDemoBlock :code="demo3Code">
        <zDropdown>
          <template #trigger>
            <zButton>
              导航
              <template #trailingVisual>
                <TriangleDown />
              </template>
            </zButton>
          </template>
          <template #content>
            <ActionList>
              <ActionList.Item
                href="https://github.com"
                new-tab
              >
                GitHub
              </ActionList.Item>
              <ActionList.Item
                href="https://vuejs.org"
                new-tab
              >
                Vue.js
              </ActionList.Item>
              <ActionList.Item
                href="https://vitejs.dev"
                new-tab
              >
                Vite
              </ActionList.Item>
            </ActionList>
          </template>
        </zDropdown>
      </ComponentDocsDemoBlock>
    </ComponentDocsSection>
  </ComponentDocsPage>
</template>

<script setup lang="ts">
import ComponentDocsDemoBlock from '@/modules/components/components/ComponentDocsPage/ComponentDocsDemoBlock.vue'
import ComponentDocsHeader from '@/modules/components/components/ComponentDocsPage/ComponentDocsHeader.vue'
import ComponentDocsPage from '@/modules/components/components/ComponentDocsPage/ComponentDocsPage.vue'
import ComponentDocsSection from '@/modules/components/components/ComponentDocsPage/ComponentDocsSection.vue'
import { zDropdown } from '@/components/z-ui/dropdown'
import { zButton } from '@/components/z-ui/button'
import { ActionList } from '@/components/z-ui/action-list'
import { zDivider } from '@/components/z-ui/divider'
import {
  Plus,
  TriangleDown,
  File,
  Repo,
  Copy,
  KebabHorizontal,
  Pencil,
  Trash
} from '@/components/z-ui/icon/Octicons-vue'

const handleClick = (action: string) => {
  alert(`执行操作：${action}`)
}

const demo1Code = `<template>
  <zDropdown>
    <template #trigger>
      <zButton>
        <template #leadingVisual><Plus /></template>
        新建
        <template #trailingVisual><TriangleDown /></template>
      </zButton>
    </template>
    <template #content>
      <ActionList>
        <ActionList.Item @click="handleClick('新建文件')">
          <File class="action-icon" />
          新建文件
        </ActionList.Item>
        <ActionList.Item @click="handleClick('新建文件夹')">
          <Repo class="action-icon" />
          新建文件夹
        </ActionList.Item>
        <zDivider />
        <ActionList.Item @click="handleClick('从模板创建')">
          <Copy class="action-icon" />
          从模板创建
        </ActionList.Item>
      </ActionList>
    </template>
  </zDropdown>
</template>

<script setup lang="ts">
import { zDropdown } from '@/components/z-ui/dropdown'
import { zButton } from '@/components/z-ui/button'
import { ActionList } from '@/components/z-ui/action-list'
import { zDivider } from '@/components/z-ui/divider'
import { Plus, TriangleDown, File, Repo, Copy } from '@/components/z-ui/icon/Octicons-vue'

const handleClick = (action: string) => {
  alert(\`执行操作：\${action}\`)
}
<\/script>`

const demo2Code = `<template>
  <zDropdown placement="left-bottom">
    <template #trigger>
      <zButton variant="invisible">
        <template #leadingVisual><KebabHorizontal /></template>
      </zButton>
    </template>
    <template #content>
      <ActionList>
        <ActionList.Item>
          <Pencil class="action-icon" />
          编辑
        </ActionList.Item>
        <ActionList.Item>
          <Copy class="action-icon" />
          复制
        </ActionList.Item>
        <zDivider />
        <ActionList.Item>
          <Trash class="action-icon danger" />
          <span class="danger">删除</span>
        </ActionList.Item>
      </ActionList>
    </template>
  </zDropdown>
</template>

<script setup lang="ts">
import { zDropdown } from '@/components/z-ui/dropdown'
import { zButton } from '@/components/z-ui/button'
import { ActionList } from '@/components/z-ui/action-list'
import { zDivider } from '@/components/z-ui/divider'
import { KebabHorizontal, Pencil, Copy, Trash } from '@/components/z-ui/icon/Octicons-vue'
<\/script>`

const demo3Code = `<template>
  <zDropdown>
    <template #trigger>
      <zButton>
        导航
        <template #trailingVisual><TriangleDown /></template>
      </zButton>
    </template>
    <template #content>
      <ActionList>
        <ActionList.Item href="https://github.com" new-tab>GitHub</ActionList.Item>
        <ActionList.Item href="https://vuejs.org" new-tab>Vue.js</ActionList.Item>
        <ActionList.Item href="https://vitejs.dev" new-tab>Vite</ActionList.Item>
      </ActionList>
    </template>
  </zDropdown>
</template>

<script setup lang="ts">
import { zDropdown } from '@/components/z-ui/dropdown'
import { zButton } from '@/components/z-ui/button'
import { ActionList } from '@/components/z-ui/action-list'
import { TriangleDown } from '@/components/z-ui/icon/Octicons-vue'
<\/script>`
</script>

<style scoped>
.action-icon {
  color: var(--fgColor-muted);
}

.danger {
  color: var(--fgColor-danger);
}
</style>
