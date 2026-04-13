<template>
  <ComponentDocsPage>
    <ComponentDocsHeader
      title="Button 按钮"
      description="用于触发操作、提交表单，以及在文本前后承载轻量视觉元素。"
    />

    <ComponentDocsSection title="基础用法">
      <template #description>
        使用默认插槽承载按钮文案。<code>zButton</code> 默认渲染为
        <code>type=&quot;button&quot;</code> 的原生按钮。
      </template>

      <ComponentDocsDemoBlock :code="basicDemoCode">
        <div class="button-row">
          <zButton>保存更改</zButton>
        </div>
      </ComponentDocsDemoBlock>
    </ComponentDocsSection>

    <ComponentDocsSection title="不同变体">
      <template #description>
        通过 <code>variant</code> 调整按钮的视觉强调层级，覆盖默认、主操作、危险操作、透明和链接样式。
      </template>

      <ComponentDocsDemoBlock :code="variantDemoCode">
        <div class="button-row">
          <zButton variant="default">
            Default
          </zButton>
          <zButton variant="primary">
            Primary
          </zButton>
          <zButton variant="danger">
            Danger
          </zButton>
          <zButton variant="invisible">
            Invisible
          </zButton>
          <zButton variant="link">
            Link
          </zButton>
        </div>
      </ComponentDocsDemoBlock>
    </ComponentDocsSection>

    <ComponentDocsSection title="不同尺寸">
      <template #description>
        使用 <code>size</code> 控制按钮高度与内边距，适配紧凑、默认和更高强调度的操作场景。
      </template>

      <ComponentDocsDemoBlock :code="sizeDemoCode">
        <div class="button-row button-row--align-end">
          <zButton size="small">
            Small
          </zButton>
          <zButton size="medium">
            Medium
          </zButton>
          <zButton size="large">
            Large
          </zButton>
        </div>
      </ComponentDocsDemoBlock>
    </ComponentDocsSection>

    <ComponentDocsSection title="状态">
      <template #description>
        设置 <code>loading</code> 时会禁用交互、隐藏原内容并显示居中的 spinner；设置 <code>disabled</code> 时保留禁用态样式。
      </template>

      <ComponentDocsDemoBlock :code="stateDemoCode">
        <div class="button-row">
          <zButton loading>
            Saving
          </zButton>
          <zButton
            variant="primary"
            loading
          >
            Publishing
          </zButton>
          <zButton disabled>
            Disabled
          </zButton>
          <zButton
            variant="danger"
            disabled
          >
            Disabled Danger
          </zButton>
        </div>
      </ComponentDocsDemoBlock>
    </ComponentDocsSection>

    <ComponentDocsSection title="前后视觉元素">
      <template #description>
        通过 <code>#leadingVisual</code> 和 <code>#trailingVisual</code> 在文本前后放置图标。按钮内容保持单行排列，适合轻量视觉引导。
      </template>

      <ComponentDocsDemoBlock :code="visualDemoCode">
        <div class="button-row">
          <zButton>
            <template #leadingVisual>
              <Bell />
            </template>
            通知设置
          </zButton>
          <zButton variant="primary">
            继续前往
            <template #trailingVisual>
              <ArrowUpRight />
            </template>
          </zButton>
          <zButton variant="invisible">
            <template #leadingVisual>
              <Search />
            </template>
            搜索项目
            <template #trailingVisual>
              <Check />
            </template>
          </zButton>
        </div>
      </ComponentDocsDemoBlock>
    </ComponentDocsSection>

    <ComponentDocsSection title="原生按钮类型">
      <template #description>
        通过 <code>type</code> 透传原生按钮类型，在表单中分别用于普通操作、提交和重置。
      </template>

      <ComponentDocsDemoBlock :code="typeDemoCode">
        <div class="button-type-demo">
          <div class="button-row">
            <zButton type="button">
              普通操作
            </zButton>
            <zButton
              type="submit"
              variant="primary"
            >
              提交表单
            </zButton>
            <zButton
              type="reset"
              variant="invisible"
            >
              重置输入
            </zButton>
          </div>
          <p class="button-note">
            默认值为 <code>button</code>，在表单上下文中可直接切换为 <code>submit</code> 或 <code>reset</code>。
          </p>
        </div>
      </ComponentDocsDemoBlock>
    </ComponentDocsSection>

    <ComponentDocsSection
      title="API"
      variant="api"
    >
      <h3>属性</h3>
      <zTable
        :columns="apiTableColumns"
        :data="apiTableRows"
        row-key="name"
        compact
        :hoverable="false"
      />

      <h3>插槽</h3>
      <zTable
        :columns="slotTableColumns"
        :data="slotTableRows"
        row-key="name"
        compact
        :hoverable="false"
      />
    </ComponentDocsSection>
  </ComponentDocsPage>
</template>

<script setup lang="ts">
import { zButton } from '@/components/z-ui/button'
import { Bell, ArrowUpRight, Search, Check } from '@/components/z-ui/icon/Octicons-vue'
import { zTable, type ZTableColumn } from '@/components/z-ui/table'
import ComponentDocsDemoBlock from '@/modules/components/components/ComponentDocsPage/ComponentDocsDemoBlock.vue'
import ComponentDocsHeader from '@/modules/components/components/ComponentDocsPage/ComponentDocsHeader.vue'
import ComponentDocsPage from '@/modules/components/components/ComponentDocsPage/ComponentDocsPage.vue'
import ComponentDocsSection from '@/modules/components/components/ComponentDocsPage/ComponentDocsSection.vue'

const basicDemoCode = `<script setup lang="ts">
import { zButton } from '@/components/z-ui/button'
<\/script>

<template>
  <zButton>保存更改</zButton>
</template>`

const variantDemoCode = `<script setup lang="ts">
import { zButton } from '@/components/z-ui/button'
<\/script>

<template>
  <div class="button-row">
    <zButton variant="default">Default</zButton>
    <zButton variant="primary">Primary</zButton>
    <zButton variant="danger">Danger</zButton>
    <zButton variant="invisible">Invisible</zButton>
    <zButton variant="link">Link</zButton>
  </div>
</template>

<style scoped>
.button-row {
  display: flex;
  flex-wrap: wrap;
  gap: 1rem;
  align-items: center;
}
</style>`

const sizeDemoCode = `<script setup lang="ts">
import { zButton } from '@/components/z-ui/button'
<\/script>

<template>
  <div class="button-row button-row--align-end">
    <zButton size="small">Small</zButton>
    <zButton size="medium">Medium</zButton>
    <zButton size="large">Large</zButton>
  </div>
</template>

<style scoped>
.button-row {
  display: flex;
  gap: 1rem;
  align-items: flex-end;
}
</style>`

const stateDemoCode = `<script setup lang="ts">
import { zButton } from '@/components/z-ui/button'
<\/script>

<template>
  <div class="button-row">
    <zButton loading>Saving</zButton>
    <zButton variant="primary" loading>Publishing</zButton>
    <zButton disabled>Disabled</zButton>
    <zButton variant="danger" disabled>Disabled Danger</zButton>
  </div>
</template>

<style scoped>
.button-row {
  display: flex;
  flex-wrap: wrap;
  gap: 1rem;
  align-items: center;
}
</style>`

const visualDemoCode = `<script setup lang="ts">
import { zButton } from '@/components/z-ui/button'
import { Bell, ArrowUpRight, Search, Check } from '@/components/z-ui/icon/Octicons-vue'
<\/script>

<template>
  <div class="button-row">
    <zButton>
      <template #leadingVisual>
        <Bell />
      </template>
      通知设置
    </zButton>

    <zButton variant="primary">
      继续前往
      <template #trailingVisual>
        <ArrowUpRight />
      </template>
    </zButton>

    <zButton variant="invisible">
      <template #leadingVisual>
        <Search />
      </template>
      搜索项目
      <template #trailingVisual>
        <Check />
      </template>
    </zButton>
  </div>
</template>

<style scoped>
.button-row {
  display: flex;
  flex-wrap: wrap;
  gap: 1rem;
  align-items: center;
}
</style>`

const typeDemoCode = `<script setup lang="ts">
import { zButton } from '@/components/z-ui/button'
<\/script>

<template>
  <div class="button-type-demo">
    <div class="button-row">
      <zButton type="button">普通操作</zButton>
      <zButton type="submit" variant="primary">提交表单</zButton>
      <zButton type="reset" variant="invisible">重置输入</zButton>
    </div>
    <p class="button-note">
      默认值为 button，在表单上下文中可直接切换为 submit 或 reset。
    </p>
  </div>
</template>

<style scoped>
.button-type-demo {
  display: grid;
  gap: 0.75rem;
}

.button-row {
  display: flex;
  flex-wrap: wrap;
  gap: 1rem;
  align-items: center;
}

.button-note {
  margin: 0;
  color: var(--fgColor-muted);
  font-size: 0.875rem;
}
</style>`

const apiTableColumns: ZTableColumn[] = [
  { key: 'name', label: '属性名', rowHeader: true, minWidth: '140px' },
  { key: 'default', label: '默认值', minWidth: '120px' },
  { key: 'type', label: '类型', minWidth: '200px', wrap: true },
  { key: 'options', label: '可选值', minWidth: '240px', wrap: true },
  { key: 'description', label: '说明', minWidth: '220px', wrap: true }
]

const apiTableRows = [
  {
    name: 'type',
    description: '透传原生 button 的 type。',
    type: "'button' | 'submit' | 'reset'",
    options: 'button / submit / reset',
    default: "'button'"
  },
  {
    name: 'variant',
    description: '控制按钮的视觉变体。',
    type: "'default' | 'primary' | 'invisible' | 'danger' | 'link'",
    options: 'default / primary / invisible / danger / link',
    default: "'default'"
  },
  {
    name: 'size',
    description: '控制按钮高度与内边距。',
    type: "'small' | 'medium' | 'large'",
    options: 'small / medium / large',
    default: "'medium'"
  },
  {
    name: 'loading',
    description: '进入加载状态后禁用交互，隐藏内容并显示居中的 spinner。',
    type: 'boolean',
    options: 'true / false',
    default: 'false'
  },
  {
    name: 'disabled',
    description: '禁用按钮交互并展示禁用态样式。',
    type: 'boolean',
    options: 'true / false',
    default: 'false'
  }
]

const slotTableColumns: ZTableColumn[] = [
  { key: 'name', label: '插槽名', rowHeader: true, minWidth: '140px' },
  { key: 'description', label: '说明', minWidth: '320px', wrap: true }
]

const slotTableRows = [
  {
    name: 'default',
    description: '按钮的主文案内容。'
  },
  {
    name: 'leadingVisual',
    description: '前置视觉元素，通常用于放置图标或状态标记。'
  },
  {
    name: 'trailingVisual',
    description: '后置视觉元素，通常用于放置方向、完成态等辅助图标。'
  }
]
</script>

<style scoped>
.button-row {
  display: flex;
  flex-wrap: wrap;
  gap: 1rem;
  align-items: center;
}

.button-row--align-end {
  align-items: flex-end;
}

.button-type-demo {
  display: grid;
  gap: 0.75rem;
}

.button-note {
  margin: 0;
  color: var(--fgColor-muted);
  font-size: 0.875rem;
  line-height: 1.6;
}

.button-note code {
  padding: 0.125rem 0.375rem;
  background: var(--bgColor-muted);
  border: 1px solid var(--borderColor-default);
  border-radius: 3px;
  font-family: 'Consolas', 'Monaco', 'Courier New', monospace;
  font-size: 0.875em;
  color: var(--fgColor-default);
}

@media (max-width: 768px) {
  .button-row {
    gap: 0.75rem;
  }
}
</style>
