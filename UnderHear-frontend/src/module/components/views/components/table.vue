<template>
  <ComponentDocsPage>
    <ComponentDocsHeader
      title="Table 表格"
      description="用于展示结构化数据的表格组件。"
    />

    <ComponentDocsSection title="基础用法">
      <template #description>
        通过 <code>columns</code> 定义列，通过 <code>data</code> 提供数据。
      </template>

      <ComponentDocsDemoBlock :code="basicDemoCode" v-bind="demoLabels">
        <div class="table-demo">
          <zTable
            title="Repositories"
            description="A clean, Primer-inspired data table."
            caption="Repository metadata"
            :columns="columns"
            :data="repoRows"
            row-key="id"
          />
        </div>
      </ComponentDocsDemoBlock>
    </ComponentDocsSection>

    <ComponentDocsSection title="粘性表头与插槽">
      <template #description>
        开启 <code>sticky-header</code> 并设置 <code>max-height</code>，可在滚动时固定表头。
      </template>

      <ComponentDocsDemoBlock :code="advancedDemoCode" v-bind="demoLabels">
        <div class="table-demo">
          <div class="status-bar" v-if="selectedName">
            最近点击：<strong>{{ selectedName }}</strong>
          </div>

          <zTable
            title="Repositories"
            description="Header stays visible while scrolling."
            caption="Repository table"
            :columns="columns"
            :data="extendedRows"
            sticky-header
            max-height="360px"
            compact
            row-clickable
            row-key="id"
            @row-click="handleRowClick"
          >
            <template #cell-stars="{ value }">
              <span class="star-cell">
                <span class="star-icon">★</span>
                {{ value }}
              </span>
            </template>
          </zTable>
        </div>
      </ComponentDocsDemoBlock>
    </ComponentDocsSection>

    <ComponentDocsSection title="API" variant="api">
      <ComponentDocsApiTable title="属性" :columns="apiColumns" :rows="apiRows" />
    </ComponentDocsSection>
  </ComponentDocsPage>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { zTable, type RowData, type ZTableColumn } from '@/components/z-ui/table/zTable'
import ComponentDocsApiTable from '@/module/components/components/ComponentDocsPage/ComponentDocsApiTable.vue'
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

const extendedRows: RepoRow[] = Array.from({ length: 24 }, (_, index) => {
  const base = repoRows[index % repoRows.length]
  return {
    ...base,
    id: index + 1,
    name: `${base.name}-${index + 1}`,
    stars: base.stars + index * 3
  }
})

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

const selectedName = ref('')

function handleRowClick(payload: { row: RowData; rowIndex: number }) {
  const record = payload.row as Record<string, unknown>
  selectedName.value = String(record.name ?? '')
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
    title="Repositories"
    :columns="columns"
    :data="rows"
    row-key="id"
  />
</template>`

const advancedDemoCode = `<template>
  <zTable
    :columns="columns"
    :data="rows"
    sticky-header
    max-height="360px"
    compact
    row-clickable
    @row-click="handleRowClick"
  >
    <template #cell-stars="{ value }">
      <span class="star-cell">★ {{ value }}</span>
    </template>
  </zTable>
</template>`

const apiColumns = ['属性名', '说明', '类型', '可选值', '默认值']
const apiRows: Array<Array<{ text: string; code?: boolean }>> = [
  [
    { text: 'columns', code: true },
    { text: '列配置数组' },
    { text: 'ZTableColumn[]', code: true },
    { text: '—' },
    { text: '[]', code: true }
  ],
  [
    { text: 'data', code: true },
    { text: '表格数据源' },
    { text: 'RowData[]', code: true },
    { text: '—' },
    { text: '[]', code: true }
  ],
  [
    { text: 'row-key', code: true },
    { text: '行唯一键，支持字段名或函数' },
    { text: 'string | (row, index) => string | number', code: true },
    { text: '—' },
    { text: 'id', code: true }
  ],
  [
    { text: 'title / description', code: true },
    { text: '表格标题与描述' },
    { text: 'string', code: true },
    { text: '—' },
    { text: '""', code: true }
  ],
  [
    { text: 'hoverable', code: true },
    { text: '行悬浮高亮' },
    { text: 'boolean', code: true },
    { text: 'true / false' },
    { text: 'true', code: true }
  ],
  [
    { text: 'sticky-header', code: true },
    { text: '是否启用粘性表头' },
    { text: 'boolean', code: true },
    { text: 'true / false' },
    { text: 'false', code: true }
  ],
  [
    { text: 'max-height', code: true },
    { text: '表格容器最大高度（用于滚动）' },
    { text: 'string', code: true },
    { text: '如 360px / 50vh' },
    { text: '""', code: true }
  ],
  [
    { text: 'row-clickable', code: true },
    { text: '是否启用行点击事件' },
    { text: 'boolean', code: true },
    { text: 'true / false' },
    { text: 'false', code: true }
  ],
  [
    { text: 'empty-text / placeholder-text', code: true },
    { text: '空状态文本与空值占位符' },
    { text: 'string', code: true },
    { text: '—' },
    { text: '暂无数据 / —', code: true }
  ]
]
</script>

<style scoped>
.table-demo {
  display: grid;
  gap: 1rem;
}

.status-bar {
  font-size: 0.9rem;
  color: var(--font-gray);
}

.status-bar strong {
  color: var(--font-black);
}

.star-cell {
  display: inline-flex;
  align-items: center;
  justify-content: flex-end;
  gap: 0.35rem;
  font-weight: 600;
  color: var(--font-black);
}

.star-icon {
  color: #e3b341;
}
</style>
