<template>
  <ComponentDocsPage>
    <ComponentDocsHeader title="Octicons Vue 图标">
      <template #description>
        该图标库基于开源的
        <zLink href="https://primer.style/octicons/" link-text="Octicons" variant="primary" />
        图标库构建，为 Vue 项目提供可直接使用的图标组件。
      </template>
    </ComponentDocsHeader>

    <ComponentDocsSection title="基础用法">
      <template #description>
        通过具名导入图标组件并在模板中直接使用，可用 <code>size</code> 控制图标大小。
      </template>

      <ComponentDocsDemoBlock :code="demo1Code">
        <div class="icon-demo-row">
          <component
            v-for="icon in basicIconEntries"
            :key="icon.name"
            :is="icon.component"
            :size="32"
            class="demo-icon"
          />
        </div>
      </ComponentDocsDemoBlock>
    </ComponentDocsSection>

    <ComponentDocsSection title="尺寸与颜色">
      <template #description>
        <code>size</code> 支持数字或字符串，<code>color</code> 可直接设置颜色值。
      </template>

      <ComponentDocsDemoBlock :code="demo2Code">
        <div class="icon-demo-row">
          <component
            v-for="icon in colorDemoEntries"
            :key="icon.name"
            :is="icon.component"
            :size="icon.size"
            :color="icon.color"
          />
        </div>
      </ComponentDocsDemoBlock>
    </ComponentDocsSection>

    <ComponentDocsSection title="图标展示">
      <template #description>
        当前共 {{ iconEntries.length }} 个图标，展示预览尺寸为 {{ iconSize }}px。
      </template>

      <div class="icon-search">
        <input
          v-model="searchQuery"
          type="text"
          class="icon-search-input"
          placeholder="Search icons..."
          aria-label="搜索图标名称"
        />
      </div>

      <div class="icon-grid">
        <div v-for="icon in filteredIconEntries" :key="icon.name" class="icon-card">
          <component :is="icon.component" :size="iconSize" class="icon-svg" />
          <span class="icon-name">{{ icon.name }}</span>
        </div>
      </div>
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
import { computed, ref } from 'vue'
import type { Component } from 'vue'
import * as octicons from '@/components/z-ui/icon/Octicons-vue/index.ts'
import { zLink } from '@/components/z-ui/link'
import { zTable, type ZTableColumn } from '@/components/z-ui/table'
import ComponentDocsDemoBlock from '@/modules/components/components/ComponentDocsPage/ComponentDocsDemoBlock.vue'
import ComponentDocsHeader from '@/modules/components/components/ComponentDocsPage/ComponentDocsHeader.vue'
import ComponentDocsPage from '@/modules/components/components/ComponentDocsPage/ComponentDocsPage.vue'
import ComponentDocsSection from '@/modules/components/components/ComponentDocsPage/ComponentDocsSection.vue'

type IconEntry = {
  name: string
  component: Component
}

type ColorDemoEntry = IconEntry & {
  size: number
  color: string
}

const iconSize = 24

const demo1Code = `<script setup lang="ts">
import { TelescopeFill, MarkGithub, HeartFill } from '@/components/z-ui/icon/Octicons-vue/index.ts'
<\/script>

<template>
  <div class="icon-demo-row">
    <TelescopeFill :size="32" />
    <MarkGithub :size="32" />
    <HeartFill :size="32" />
  </div>
</template>

<style scoped>
.icon-demo-row {
  display: flex;
  gap: 1.5rem;
  align-items: center;
  flex-wrap: wrap;
}
</style>`

const demo2Code = `<script setup lang="ts">
import { TelescopeFill, MarkGithub, HeartFill } from '@/components/z-ui/icon/Octicons-vue/index.ts'
<\/script>

<template>
  <div class="icon-demo-row">
    <TelescopeFill :size="16" color="#656d76" />
    <MarkGithub :size="24" color="#0969da" />
    <HeartFill :size="32" color="#d1242f" />
  </div>
</template>

<style scoped>
.icon-demo-row {
  display: flex;
  gap: 1.5rem;
  align-items: center;
  flex-wrap: wrap;
}
</style>`

const basicIconEntries: IconEntry[] = [
  { name: 'TelescopeFill', component: octicons.TelescopeFill as Component },
  { name: 'MarkGithub', component: octicons.MarkGithub as Component },
  { name: 'HeartFill', component: octicons.HeartFill as Component }
]

const colorDemoEntries: ColorDemoEntry[] = [
  {
    name: 'TelescopeFill',
    component: octicons.TelescopeFill as Component,
    size: 16,
    color: '#656d76'
  },
  {
    name: 'MarkGithub',
    component: octicons.MarkGithub as Component,
    size: 24,
    color: '#0969da'
  },
  {
    name: 'HeartFill',
    component: octicons.HeartFill as Component,
    size: 32,
    color: '#d1242f'
  }
]

const iconEntries: IconEntry[] = Object.entries(octicons)
  .filter(([name]) => name !== 'default')
  .map(([name, component]) => ({
    name,
    component: component as Component
  }))
  .sort((a, b) => a.name.localeCompare(b.name))

const searchQuery = ref('')
const filteredIconEntries = computed(() => {
  const query = searchQuery.value.trim().toLowerCase()
  if (!query) {
    return iconEntries
  }

  return iconEntries.filter((icon) => icon.name.toLowerCase().includes(query))
})

const apiTableColumns: ZTableColumn[] = [
  { key: 'name', label: '属性名', rowHeader: true, minWidth: '140px' },
  { key: 'description', label: '说明', minWidth: '200px', wrap: true },
  { key: 'type', label: '类型', minWidth: '160px', wrap: true },
  { key: 'options', label: '可选值', minWidth: '140px', wrap: true },
  { key: 'default', label: '默认值', minWidth: '100px' }
]

const apiTableRows = [
  {
    name: 'size',
    description: '图标尺寸',
    type: 'number | string',
    options: '—',
    default: '16'
  },
  {
    name: 'color',
    description: '图标颜色',
    type: 'string',
    options: '—',
    default: 'currentColor'
  }
]
</script>

<style scoped>
.icon-search {
  display: flex;
  align-items: center;
  margin-bottom: 2rem;
}

.icon-search-input {
  width: 100%;
  padding: 0.5rem 0.75rem;
  height: 40px;
  border: 1px solid #d1d9e0;
  border-radius: 6px;
  font-size: 0.875rem;
  color: var(--fgColor-default);
  background: #ffffff;
  box-shadow: inset 0px 1px 0px 0px #1f23280a;
}

.icon-search-input::placeholder {
  color: var(--fgColor-muted);
}

.icon-search-input:focus {
  outline: 2px solid var(--focus-outlineColor);
  outline-offset: -1px;
}

.icon-demo-row {
  display: flex;
  gap: 1.5rem;
  align-items: center;
  flex-wrap: wrap;
}

.demo-icon {
  color: var(--fgColor-default);
}

.icon-muted {
  color: var(--fgColor-muted);
}

.icon-primary {
  color: var(--fgColor-accent);
}

.icon-danger {
  color: #d1242f;
}

.icon-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(120px, 1fr));
  gap: 1rem;
}

.icon-card {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 0.5rem;
  padding: 0.9rem 0.75rem;
  border: 1px solid #d1d9e0;
  border-radius: 6px;
  background: #ffffff;
}

.icon-svg {
  color: var(--fgColor-default);
}

.icon-name {
  font-size: 0.75rem;
  color: var(--fgColor-muted);
  text-align: center;
  word-break: break-word;
  line-height: 1.4;
}

@media (max-width: 768px) {
  .icon-search {
    margin-bottom: 0.75rem;
  }

  .icon-demo-row {
    flex-direction: column;
    align-items: flex-start;
    gap: 1rem;
  }

  .icon-grid {
    grid-template-columns: repeat(auto-fill, minmax(96px, 1fr));
    gap: 0.75rem;
  }

  .icon-card {
    padding: 0.75rem 0.5rem;
  }
}
</style>
