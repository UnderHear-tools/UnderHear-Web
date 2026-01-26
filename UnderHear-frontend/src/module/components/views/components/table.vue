<template>
  <ComponentDocsPage>
    <ComponentDocsHeader
      title="Table 表格"
      description="用于展示结构化数据的表格组件。"
    />

    <ComponentDocsSection title="基础用法">
      <template #description>
        通过 <code>columns</code> 定义列，通过 <code>data</code> 提供数据。单元格默认不换行，必要时可在列配置中设置 <code>wrap</code> 允许换行。
      </template>

      <ComponentDocsDemoBlock :code="basicDemoCode" v-bind="demoLabels">
        <div class="table-demo">
          <zTable
            caption="Repository metadata"
            :columns="columns"
            :data="repoRows"
            row-key="id"
          />
        </div>
      </ComponentDocsDemoBlock>
    </ComponentDocsSection>

    <ComponentDocsSection title="紧凑与无边框">
      <template #description>
        通过 <code>compact</code> 与 <code>bordered</code> 调整表格密度和边框，可同时关闭 <code>hoverable</code>。
      </template>

      <ComponentDocsDemoBlock :code="compactDemoCode" v-bind="demoLabels">
        <div class="table-demo table-demo--split">
          <div class="table-demo__panel">
            <div class="table-demo__label">默认</div>
            <zTable
              caption="Default table"
              :columns="columns"
              :data="repoRows"
              row-key="id"
            />
          </div>
          <div class="table-demo__panel">
            <div class="table-demo__label">紧凑 / 无边框 / 关闭悬浮</div>
            <zTable
              caption="Compact table"
              :columns="columns"
              :data="repoRows"
              row-key="id"
              compact
              :bordered="false"
              :hoverable="false"
            />
          </div>
        </div>
      </ComponentDocsDemoBlock>
    </ComponentDocsSection>

    <ComponentDocsSection title="行点击">
      <template #description>
        开启 <code>row-clickable</code> 并监听 <code>row-click</code> 获取当前行。
      </template>

      <ComponentDocsDemoBlock :code="clickableDemoCode" v-bind="demoLabels">
        <div class="table-demo">
          <div class="status-bar" v-if="selectedName">
            最近点击：<strong>{{ selectedName }}</strong>
          </div>
          <zTable
            caption="Clickable rows"
            :columns="columns"
            :data="repoRows"
            row-clickable
            row-key="id"
            @row-click="handleRowClick"
          />
        </div>
      </ComponentDocsDemoBlock>
    </ComponentDocsSection>

    <ComponentDocsSection title="空状态与占位符">
      <template #description>
        通过 <code>empty-text</code> 设置空数据提示，通过 <code>placeholder-text</code> 指定空值占位符。
      </template>

      <ComponentDocsDemoBlock :code="emptyDemoCode" v-bind="demoLabels">
        <div class="table-demo table-demo--split">
          <div class="table-demo__panel">
            <div class="table-demo__label">空状态</div>
            <zTable
              caption="Empty table"
              :columns="columns"
              :data="emptyRows"
              empty-text="暂无记录"
            />
          </div>
          <div class="table-demo__panel">
            <div class="table-demo__label">空值占位符</div>
            <zTable
              caption="Placeholder table"
              :columns="columns"
              :data="placeholderRows"
              row-key="id"
              placeholder-text="—"
            />
          </div>
        </div>
      </ComponentDocsDemoBlock>
    </ComponentDocsSection>

    <ComponentDocsSection title="长文本换行">
      <template #description>
        列配置设置 <code>wrap</code> 后允许单元格内容换行，适合长文本。
      </template>

      <ComponentDocsDemoBlock :code="wrapDemoCode" v-bind="demoLabels">
        <div class="table-demo">
          <zTable
            caption="Wrapped content"
            :columns="wrapColumns"
            :data="wrapRows"
            row-key="id"
          />
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
import { ref } from 'vue'
import { zTable, type RowData, type ZTableColumn } from '@/components/z-ui/table/zTable'
import ComponentDocsDemoBlock from '@/module/components/components/ComponentDocsPage/ComponentDocsDemoBlock.vue'
import ComponentDocsHeader from '@/module/components/components/ComponentDocsPage/ComponentDocsHeader.vue'
import ComponentDocsPage from '@/module/components/components/ComponentDocsPage/ComponentDocsPage.vue'
import ComponentDocsSection from '@/module/components/components/ComponentDocsPage/ComponentDocsSection.vue'

interface RepoRow {
  id: number
  name: string
  visibility: 'Public' | 'Private' | 'Internal'
  updatedAt: string
  stars: number
  owner: string
}

const repoRows: RepoRow[] = [
  { id: 1, name: 'underhear-web', visibility: 'Public', updatedAt: '2026-01-20', stars: 128, owner: 'UnderHear' },
  { id: 2, name: 'audio-pipeline', visibility: 'Internal', updatedAt: '2026-01-18', stars: 76, owner: 'UnderHear' },
  { id: 3, name: 'sound-lab', visibility: 'Private', updatedAt: '2026-01-16', stars: 51, owner: 'Studio' },
  { id: 4, name: 'waveforms', visibility: 'Public', updatedAt: '2026-01-12', stars: 214, owner: 'OpenAudio' },
  { id: 5, name: 'mix-console', visibility: 'Internal', updatedAt: '2026-01-08', stars: 33, owner: 'UnderHear' },
  { id: 6, name: 'voice-notes', visibility: 'Public', updatedAt: '2026-01-04', stars: 89, owner: 'Community' }
]

const emptyRows: RepoRow[] = []

const placeholderRows: RepoRow[] = repoRows.map((row, index) => ({
  ...row,
  updatedAt: index % 2 === 0 ? '' : row.updatedAt,
  owner: index % 3 === 0 ? '' : row.owner
}))

const columns: ZTableColumn[] = [
  {
    key: 'name',
    label: 'Repository',
    rowHeader: true,
    minWidth: '240px'
  },
  {
    key: 'visibility',
    label: 'Visibility',
    width: '120px'
  },
  {
    key: 'updatedAt',
    label: 'Updated',
    width: '150px'
  },
  {
    key: 'stars',
    label: 'Stars',
    align: 'right',
    width: '110px'
  },
  {
    key: 'owner',
    label: 'Owner',
    minWidth: '140px'
  }
]

const wrapColumns: ZTableColumn[] = [
  {
    key: 'name',
    label: 'Repository',
    rowHeader: true,
    minWidth: '240px',
    wrap: true
  },
  {
    key: 'visibility',
    label: 'Visibility',
    width: '120px'
  },
  {
    key: 'updatedAt',
    label: 'Updated',
    width: '150px'
  },
  {
    key: 'stars',
    label: 'Stars',
    align: 'right',
    width: '110px'
  },
  {
    key: 'owner',
    label: 'Owner',
    minWidth: '200px',
    wrap: true
  }
]

const wrapRows: RepoRow[] = [
  {
    id: 101,
    name: 'underhear-audio-research-platform-with-extended-metadata',
    visibility: 'Public',
    updatedAt: '2026-01-21',
    stars: 321,
    owner: 'UnderHear Research Team'
  },
  {
    id: 102,
    name: 'interactive-sound-experiments-for-community-creators',
    visibility: 'Internal',
    updatedAt: '2026-01-14',
    stars: 188,
    owner: 'OpenAudio Collaboration Group'
  },
  {
    id: 103,
    name: 'waveform-visualization-library-with-extended-guides',
    visibility: 'Public',
    updatedAt: '2026-01-09',
    stars: 97,
    owner: 'Studio Tools Initiative'
  }
]

const selectedName = ref('')

function handleRowClick(payload: { row: RowData; rowIndex: number }) {
  const row = payload.row as RepoRow
  selectedName.value = row.name
}

const demoLabels = {
  copyLabel: '复制代码',
  copiedLabel: '已复制',
  showCodeLabel: '查看代码',
  hideCodeLabel: '隐藏代码',
  hideSourceLabel: '隐藏源代码'
}

const basicDemoCode = `<template>
  <zTable
    :columns="columns"
    :data="rows"
    row-key="id"
  />
</template>

<script setup lang="ts">
import { type ZTableColumn } from '@/components/z-ui/table/zTable'

interface Row {
  id: number
  name: string
  visibility: string
  updatedAt: string
  stars: number
  owner: string
}

const columns: ZTableColumn[] = [
  { key: 'name', label: 'Repository', rowHeader: true, minWidth: '240px' },
  { key: 'visibility', label: 'Visibility', width: '120px' },
  { key: 'updatedAt', label: 'Updated', width: '150px' },
  { key: 'stars', label: 'Stars', align: 'right', width: '110px' },
  { key: 'owner', label: 'Owner', minWidth: '140px' }
]

const rows: Row[] = [
  { id: 1, name: 'underhear-web', visibility: 'Public', updatedAt: '2026-01-20', stars: 128, owner: 'UnderHear' },
  { id: 2, name: 'audio-pipeline', visibility: 'Internal', updatedAt: '2026-01-18', stars: 76, owner: 'UnderHear' }
]
<\/script>`

const compactDemoCode = `<template>
  <div class="table-demo table-demo--split">
    <zTable :columns="columns" :data="rows" row-key="id" />
    <zTable
      :columns="columns"
      :data="rows"
      row-key="id"
      compact
      :bordered="false"
      :hoverable="false"
    />
  </div>
</template>

<script setup lang="ts">
import { type ZTableColumn } from '@/components/z-ui/table/zTable'

interface Row {
  id: number
  name: string
  visibility: string
  updatedAt: string
  stars: number
  owner: string
}

const columns: ZTableColumn[] = [
  { key: 'name', label: 'Repository', rowHeader: true, minWidth: '240px' },
  { key: 'visibility', label: 'Visibility', width: '120px' },
  { key: 'updatedAt', label: 'Updated', width: '150px' },
  { key: 'stars', label: 'Stars', align: 'right', width: '110px' },
  { key: 'owner', label: 'Owner', minWidth: '140px' }
]

const rows: Row[] = [
  { id: 1, name: 'underhear-web', visibility: 'Public', updatedAt: '2026-01-20', stars: 128, owner: 'UnderHear' },
  { id: 2, name: 'audio-pipeline', visibility: 'Internal', updatedAt: '2026-01-18', stars: 76, owner: 'UnderHear' }
]
<\/script>`

const clickableDemoCode = `<template>
  <div class="status-bar" v-if="selectedName">
    最近点击：<strong>{{ selectedName }}</strong>
  </div>
  <zTable
    :columns="columns"
    :data="rows"
    row-clickable
    @row-click="handleRowClick"
  />
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { type RowData, type ZTableColumn } from '@/components/z-ui/table/zTable'

interface Row {
  id: number
  name: string
  visibility: string
  updatedAt: string
  stars: number
  owner: string
}

const selectedName = ref('')

const columns: ZTableColumn[] = [
  { key: 'name', label: 'Repository', rowHeader: true, minWidth: '240px' },
  { key: 'visibility', label: 'Visibility', width: '120px' },
  { key: 'updatedAt', label: 'Updated', width: '150px' },
  { key: 'stars', label: 'Stars', align: 'right', width: '110px' },
  { key: 'owner', label: 'Owner', minWidth: '140px' }
]

const rows: Row[] = [
  { id: 1, name: 'underhear-web', visibility: 'Public', updatedAt: '2026-01-20', stars: 128, owner: 'UnderHear' },
  { id: 2, name: 'audio-pipeline', visibility: 'Internal', updatedAt: '2026-01-18', stars: 76, owner: 'UnderHear' }
]

function handleRowClick(payload: { row: RowData; rowIndex: number }) {
  const row = payload.row as Row
  selectedName.value = row.name
}
<\/script>`

const emptyDemoCode = `<template>
  <div class="table-demo table-demo--split">
    <zTable :columns="columns" :data="emptyRows" empty-text="暂无记录" />
    <zTable :columns="columns" :data="placeholderRows" placeholder-text="—" />
  </div>
</template>

<script setup lang="ts">
import { type ZTableColumn } from '@/components/z-ui/table/zTable'

interface Row {
  id: number
  name: string
  visibility: string
  updatedAt: string
  stars: number
  owner: string
}

const columns: ZTableColumn[] = [
  { key: 'name', label: 'Repository', rowHeader: true, minWidth: '240px' },
  { key: 'visibility', label: 'Visibility', width: '120px' },
  { key: 'updatedAt', label: 'Updated', width: '150px' },
  { key: 'stars', label: 'Stars', align: 'right', width: '110px' },
  { key: 'owner', label: 'Owner', minWidth: '140px' }
]

const placeholderRows: Row[] = [
  { id: 1, name: 'underhear-web', visibility: 'Public', updatedAt: '', stars: 128, owner: 'UnderHear' },
  { id: 2, name: 'audio-pipeline', visibility: 'Internal', updatedAt: '2026-01-18', stars: 76, owner: '' }
]

const emptyRows: Row[] = []
<\/script>`

const wrapDemoCode = `<template>
  <zTable
    :columns="wrapColumns"
    :data="rows"
    row-key="id"
  />
</template>

<script setup lang="ts">
import { type ZTableColumn } from '@/components/z-ui/table/zTable'

interface Row {
  id: number
  name: string
  visibility: string
  updatedAt: string
  stars: number
  owner: string
}

const wrapColumns: ZTableColumn[] = [
  { key: 'name', label: 'Repository', rowHeader: true, minWidth: '240px', wrap: true },
  { key: 'visibility', label: 'Visibility', width: '120px' },
  { key: 'updatedAt', label: 'Updated', width: '150px' },
  { key: 'stars', label: 'Stars', align: 'right', width: '110px' },
  { key: 'owner', label: 'Owner', minWidth: '200px', wrap: true }
]

const rows: Row[] = [
  {
    id: 101,
    name: 'underhear-audio-research-platform-with-extended-metadata',
    visibility: 'Public',
    updatedAt: '2026-01-21',
    stars: 321,
    owner: 'UnderHear Research Team'
  },
  {
    id: 102,
    name: 'interactive-sound-experiments-for-community-creators',
    visibility: 'Internal',
    updatedAt: '2026-01-14',
    stars: 188,
    owner: 'OpenAudio Collaboration Group'
  }
]
<\/script>`

const apiTableColumns: ZTableColumn[] = [
  { key: 'name', label: '属性名', rowHeader: true, minWidth: '160px' },
  { key: 'description', label: '说明', minWidth: '240px', wrap: true },
  { key: 'type', label: '类型', minWidth: '200px', wrap: true },
  { key: 'options', label: '可选值', minWidth: '200px', wrap: true },
  { key: 'default', label: '默认值', minWidth: '140px' }
]

const apiTableRows = [
  {
    name: 'columns',
    description: '列配置数组',
    type: 'ZTableColumn[]',
    options: '—',
    default: '[]'
  },
  {
    name: 'data',
    description: '表格数据源',
    type: 'RowData[]',
    options: '—',
    default: '[]'
  },
  {
    name: 'row-key',
    description: '行唯一键，支持字段名或函数',
    type: 'string | (row, index) => string | number',
    options: '—',
    default: 'id'
  },
  {
    name: 'caption / aria-label',
    description: '表格描述与无障碍标签',
    type: 'string',
    options: '—',
    default: '空字符串 / Data table'
  },
  {
    name: 'hoverable',
    description: '行悬浮高亮',
    type: 'boolean',
    options: 'true / false',
    default: 'true'
  },
  {
    name: 'bordered',
    description: '是否显示外边框',
    type: 'boolean',
    options: 'true / false',
    default: 'true'
  },
  {
    name: 'compact',
    description: '是否使用紧凑密度',
    type: 'boolean',
    options: 'true / false',
    default: 'false'
  },
  {
    name: 'row-clickable',
    description: '是否启用行点击事件',
    type: 'boolean',
    options: 'true / false',
    default: 'false'
  },
  {
    name: 'empty-text / placeholder-text',
    description: '空状态文本与空值占位符',
    type: 'string',
    options: '—',
    default: '暂无数据 / 空字符串'
  }
]
</script>

<style scoped>
.table-demo {
  display: grid;
  gap: 1rem;
}

.table-demo--split {
  grid-template-columns: 1fr;
  align-items: start;
}

.table-demo__panel {
  display: grid;
  gap: 0.5rem;
}

.table-demo__label {
  font-size: 0.8125rem;
  color: var(--font-gray);
}

.status-bar {
  font-size: 0.9rem;
  color: var(--font-gray);
}

.status-bar strong {
  color: var(--font-black);
}
</style>
