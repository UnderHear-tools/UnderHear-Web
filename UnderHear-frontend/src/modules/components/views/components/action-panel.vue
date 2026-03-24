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
        <zDropdown side="outside-bottom">
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
    <ComponentDocsSection title="选中状态同步">
      <template #description>
        通过 <code>@click</code> 更新响应式变量，将选中项的文字同步显示到触发按钮上。
      </template>
      <ComponentDocsDemoBlock :code="demo4Code">
        <zDropdown>
          <template #trigger>
            <zButton>
              <template #leadingVisual>
                <Repo class="action-icon" />
              </template>
              {{ selectedLabel }}
              <template #trailingVisual>
                <TriangleDown />
              </template>
            </zButton>
          </template>
          <template #content>
            <ActionList>
              <ActionList.Item @click="selectedLabel = '新建文件'">
                新建文件
              </ActionList.Item>
              <ActionList.Item @click="selectedLabel = '打开文件'">
                打开文件
              </ActionList.Item>
              <ActionList.Item @click="selectedLabel = '保存文件'">
                保存文件
              </ActionList.Item>
            </ActionList>
          </template>
        </zDropdown>
      </ComponentDocsDemoBlock>
    </ComponentDocsSection>

    <ComponentDocsSection title="嵌套二级菜单">
      <template #description>
        直接将某个 <code>ActionList.Item</code> 作为嵌套 <code>zDropdown</code> 的触发器，实现多级菜单效果。
      </template>
      <ComponentDocsDemoBlock :code="demo5Code">
        <zDropdown>
          <template #trigger>
            <zButton>
              打开菜单
              <template #trailingVisual>
                <TriangleDown />
              </template>
            </zButton>
          </template>
          <template #content>
            <ActionList>
              <ActionList.Item>选项1</ActionList.Item>
              <ActionList.Item>选项2</ActionList.Item>
              <zDropdown side="outside-right">
                <template #trigger>
                  <ActionList.Item class="submenu-trigger">
                    选项3 <ChevronRight color="#59636e" />
                  </ActionList.Item>
                </template>
                <template #content>
                  <ActionList>
                    <ActionList.Item>子选项1</ActionList.Item>
                    <ActionList.Item>子选项2</ActionList.Item>
                    <ActionList.Item>子选项3</ActionList.Item>
                  </ActionList>
                </template>
              </zDropdown>
            </ActionList>
          </template>
        </zDropdown>
      </ComponentDocsDemoBlock>
    </ComponentDocsSection>
  </ComponentDocsPage>
</template>

<script setup lang="ts">
import { ref } from 'vue'
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
  Trash,
  ChevronRight
} from '@/components/z-ui/icon/Octicons-vue'

const handleClick = (action: string) => {
  alert(`执行操作：${action}`)
}

const selectedLabel = ref('新建文件')

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
  <zDropdown side="outside-bottom">
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

const demo4Code = `<template>
  <zDropdown>
    <template #trigger>
      <zButton>
        <template #leadingVisual><Repo /></template>
        {{ label }}
        <template #trailingVisual><TriangleDown /></template>
      </zButton>
    </template>
    <template #content>
      <ActionList>
        <ActionList.Item @click="label = '新建文件'">新建文件</ActionList.Item>
        <ActionList.Item @click="label = '打开文件'">打开文件</ActionList.Item>
        <ActionList.Item @click="label = '保存文件'">保存文件</ActionList.Item>
      </ActionList>
    </template>
  </zDropdown>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { zDropdown } from '@/components/z-ui/dropdown'
import { zButton } from '@/components/z-ui/button'
import { ActionList } from '@/components/z-ui/action-list'
import { Repo, TriangleDown } from '@/components/z-ui/icon/Octicons-vue'

const label = ref('新建文件')
<\/script>`

const demo5Code = `<template>
  <zDropdown>
    <template #trigger>
      <zButton>
        打开菜单
        <template #trailingVisual><TriangleDown /></template>
      </zButton>
    </template>
    <template #content>
      <ActionList>
        <ActionList.Item>选项1</ActionList.Item>
        <ActionList.Item>选项2</ActionList.Item>
        <zDropdown side="outside-right">
          <template #trigger>
            <ActionList.Item class="submenu-trigger">
              选项3 <ChevronRight color="#59636e" />
            </ActionList.Item>
          </template>
          <template #content>
            <ActionList>
              <ActionList.Item>子选项1</ActionList.Item>
              <ActionList.Item>子选项2</ActionList.Item>
              <ActionList.Item>子选项3</ActionList.Item>
            </ActionList>
          </template>
        </zDropdown>
      </ActionList>
    </template>
  </zDropdown>
</template>

<script setup lang="ts">
import { zDropdown } from '@/components/z-ui/dropdown'
import { zButton } from '@/components/z-ui/button'
import { ActionList } from '@/components/z-ui/action-list'
import { TriangleDown, ChevronRight } from '@/components/z-ui/icon/Octicons-vue'
<\/script>

<style scoped>
.submenu-trigger {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
}
<\/style>`
</script>

<style scoped>
.action-icon {
  color: var(--fgColor-muted);
}

.danger {
  color: var(--fgColor-danger);
}

.submenu-trigger {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
}
</style>
