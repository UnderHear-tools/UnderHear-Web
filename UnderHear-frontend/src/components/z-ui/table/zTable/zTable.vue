<template>
  <div class="z-table" :class="tableClasses">
    <div v-if="showHeader" class="z-table__header">
      <div class="z-table__title-group">
        <div v-if="title" class="z-table__title">{{ title }}</div>
        <div v-if="description" class="z-table__description">{{ description }}</div>
      </div>
      <div v-if="hasActionsSlot" class="z-table__actions">
        <slot name="actions" />
      </div>
    </div>

    <div class="z-table__container" :style="containerStyle">
      <table class="z-table__table" :aria-label="ariaLabel">
        <caption v-if="caption" class="z-table__sr-caption">{{ caption }}</caption>
        <thead class="z-table__head">
          <tr class="z-table__row z-table__row--head">
            <th
              v-for="column in columns"
              :key="column.key"
              class="z-table__cell z-table__cell--head"
              :class="[alignClass(column.align), column.headerClassName]"
              :style="columnStyle(column)"
              scope="col"
            >
              <span class="z-table__header-label">
                <slot :name="headerSlotName(column)" :column="column">
                  {{ column.label }}
                </slot>
              </span>
            </th>
          </tr>
        </thead>

        <tbody v-if="displayRows.length > 0" class="z-table__body">
          <tr
            v-for="(row, rowIndex) in displayRows"
            :key="resolveRowKey(row, rowIndex)"
            class="z-table__row z-table__row--body"
            :class="{ 'is-clickable': rowClickable }"
            @click="onRowClick(row, rowIndex)"
          >
            <template v-for="column in columns" :key="column.key">
              <component
                :is="column.rowHeader ? 'th' : 'td'"
                class="z-table__cell z-table__cell--body"
                :class="[alignClass(column.align), column.className, { 'is-row-header': column.rowHeader }]"
                :style="columnStyle(column)"
                :scope="column.rowHeader ? 'row' : undefined"
              >
                <slot
                  :name="cellSlotName(column)"
                  :row="row"
                  :row-index="rowIndex"
                  :column="column"
                  :value="getValue(row, column)"
                >
                  {{ formatValue(getValue(row, column), row, column, rowIndex) }}
                </slot>
              </component>
            </template>
          </tr>
        </tbody>

        <tbody v-else class="z-table__body">
          <tr class="z-table__row z-table__row--empty">
            <td class="z-table__cell z-table__cell--empty" :colspan="colspan">
              <slot name="empty">{{ emptyText }}</slot>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed, useSlots } from 'vue'

export type RowData = object
export type ZTableAlign = 'left' | 'center' | 'right'

export interface ZTableColumn {
  key: string
  label: string
  align?: ZTableAlign
  width?: string
  minWidth?: string
  rowHeader?: boolean
  className?: string
  headerClassName?: string
  formatter?: (value: unknown, row: RowData, column: ZTableColumn, rowIndex: number) => unknown
}

const props = withDefaults(
  defineProps<{
    columns: ZTableColumn[]
    data: RowData[]
    rowKey?: string | ((row: RowData, index: number) => string | number)
    title?: string
    description?: string
    caption?: string
    ariaLabel?: string
    emptyText?: string
    placeholderText?: string
    hoverable?: boolean
    bordered?: boolean
    compact?: boolean
    stickyHeader?: boolean
    maxHeight?: string
    rowClickable?: boolean
  }>(),
  {
    rowKey: 'id',
    title: '',
    description: '',
    caption: '',
    ariaLabel: 'Data table',
    emptyText: '暂无数据',
    placeholderText: '—',
    hoverable: true,
    bordered: true,
    compact: false,
    stickyHeader: false,
    maxHeight: '',
    rowClickable: false
  }
)

const emit = defineEmits<{
  'row-click': [{ row: RowData; rowIndex: number }]
}>()

const slots = useSlots()

const hasActionsSlot = computed(() => Boolean(slots.actions))
const showHeader = computed(() => Boolean(props.title || props.description || hasActionsSlot.value))

const colspan = computed(() => Math.max(props.columns.length, 1))

const tableClasses = computed(() => ({
  'is-bordered': props.bordered,
  'is-hoverable': props.hoverable,
  'is-compact': props.compact,
  'is-sticky': props.stickyHeader,
  'has-header': showHeader.value
}))

const containerStyle = computed(() => {
  const resolvedMaxHeight = props.stickyHeader ? props.maxHeight || '420px' : props.maxHeight
  if (!resolvedMaxHeight) return undefined
  return {
    maxHeight: resolvedMaxHeight,
    overflow: 'auto'
  }
})

const displayRows = computed(() => props.data.slice())

function asRecord(row: RowData): Record<string, unknown> {
  return row as Record<string, unknown>
}

function resolveRowKey(row: RowData, index: number): string | number {
  if (typeof props.rowKey === 'function') {
    return props.rowKey(row, index)
  }

  const key = props.rowKey
  const record = asRecord(row)
  const value = key ? record[key] : undefined
  if (typeof value === 'string' || typeof value === 'number') {
    return value
  }

  return index
}

function getValue(row: RowData, column: ZTableColumn): unknown {
  return getValueByPath(row, column.key)
}

function formatValue(value: unknown, row: RowData, column: ZTableColumn, rowIndex: number): unknown {
  if (column.formatter) {
    return column.formatter(value, row, column, rowIndex)
  }
  if (value === null || value === undefined || value === '') {
    return props.placeholderText
  }
  return value
}

function getValueByPath(row: RowData, path: string): unknown {
  const record = asRecord(row)
  if (!path.includes('.')) {
    return record[path]
  }
  return path.split('.').reduce<unknown>((acc, segment) => {
    if (acc && typeof acc === 'object') {
      return (acc as Record<string, unknown>)[segment]
    }
    return undefined
  }, record)
}

function onRowClick(row: RowData, rowIndex: number) {
  if (!props.rowClickable) return
  emit('row-click', { row, rowIndex })
}

function alignClass(align: ZTableAlign | undefined) {
  return align ? `is-align-${align}` : 'is-align-left'
}

function columnStyle(column: ZTableColumn) {
  const style: Record<string, string> = {}
  if (column.width) style.width = column.width
  if (column.minWidth) style.minWidth = column.minWidth
  return style
}

function headerSlotName(column: ZTableColumn) {
  return `header-${column.key}`
}

function cellSlotName(column: ZTableColumn) {
  return `cell-${column.key}`
}
</script>

<style scoped>
.z-table {
  border: 1px solid var(--border-gray);
  border-radius: 8px;
  background: #ffffff;
  color: var(--font-black);
  overflow: hidden;
}

.z-table:not(.is-bordered) {
  border: none;
  border-radius: 0;
}

.z-table__header {
  display: flex;
  align-items: flex-start;
  justify-content: space-between;
  gap: 1rem;
  padding: 1rem 1.25rem;
  border-bottom: 1px solid var(--border-gray);
  background: #ffffff;
}

.z-table__title {
  font-size: 1rem;
  font-weight: 600;
  color: var(--font-black);
}

.z-table__description {
  margin-top: 0.25rem;
  color: var(--font-gray);
  font-size: 0.875rem;
}

.z-table__actions {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  flex-shrink: 0;
}

.z-table__container {
  width: 100%;
  overflow-x: auto;
  background: #ffffff;
}

.z-table__table {
  width: 100%;
  min-width: 100%;
  border-collapse: separate;
  border-spacing: 0;
  font-size: 0.875rem;
}

.z-table__sr-caption {
  position: absolute;
  width: 1px;
  height: 1px;
  padding: 0;
  margin: -1px;
  overflow: hidden;
  clip: rect(0, 0, 0, 0);
  white-space: nowrap;
  border: 0;
}

.z-table__head {
  background: #f6f8fa;
}

.z-table__cell {
  padding: 0.6rem 0.9rem;
  border-bottom: 1px solid var(--border-gray);
  vertical-align: middle;
  background-clip: padding-box;
}

.z-table__cell--head {
  font-weight: 600;
  color: var(--font-black);
  background: #f6f8fa;
  position: relative;
}

.z-table.is-sticky .z-table__cell--head {
  position: sticky;
  top: 0;
  z-index: 2;
}

.z-table__row--body:last-child .z-table__cell {
  border-bottom: none;
}

.z-table.is-hoverable .z-table__row--body:hover {
  background: #f6f8fa;
}

.z-table__row.is-clickable {
  cursor: pointer;
}

.z-table__row.is-clickable:active {
  background: #eef3f8;
}

.z-table__cell--empty {
  text-align: center;
  padding: 2rem 1rem;
  color: var(--font-gray);
  font-weight: 500;
}

.z-table.is-compact .z-table__header {
  padding: 0.75rem 1rem;
}

.z-table.is-compact .z-table__cell {
  padding: 0.6rem 0.75rem;
  font-size: 0.8125rem;
}

.z-table__header-label {
  display: inline-flex;
  align-items: center;
  gap: 0.35rem;
  min-width: 0;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.is-row-header {
  font-weight: 600;
  color: var(--font-black);
}

.is-align-left {
  text-align: left;
}

.is-align-center {
  text-align: center;
}

.is-align-right {
  text-align: right;
}

@media (max-width: 768px) {
  .z-table__header {
    padding: 0.85rem 1rem;
    flex-direction: column;
    align-items: flex-start;
  }

  .z-table__cell {
    padding: 0.65rem 0.85rem;
  }
}
</style>
