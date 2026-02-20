<template>
  <div class="api-block">
    <h3 class="api-subtitle">{{ title }}</h3>
    <div class="api-table-wrapper">
      <table class="api-table">
        <thead>
          <tr>
            <th v-for="column in columns" :key="column">{{ column }}</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(row, rowIndex) in rows" :key="rowIndex">
            <td v-for="(cell, cellIndex) in row" :key="cellIndex">
              <code v-if="cell.code">{{ cell.text }}</code>
              <span v-else>{{ cell.text }}</span>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script setup lang="ts">
defineProps<{
  title: string
  columns: string[]
  rows: Array<Array<{ text: string; code?: boolean }>>
}>()
</script>

<style scoped>
.api-subtitle {
  font-size: 1.25rem;
  font-weight: 600;
  color: var(--fgColor-default);
  margin-bottom: 1rem;
}

.api-table-wrapper {
  overflow-x: auto;
}

.api-table {
  width: 100%;
  border-collapse: collapse;
  border: 1px solid #d1d9e0;
  border-radius: 6px;
  overflow: hidden;
  background: #ffffff;
}

.api-table thead {
  background: #f6f8fa;
}

.api-table th {
  padding: 0.75rem 1rem;
  text-align: left;
  font-weight: 600;
  font-size: 0.875rem;
  color: var(--fgColor-default);
  border-bottom: 1px solid #d1d9e0;
}

.api-table td {
  padding: 0.75rem 1rem;
  font-size: 0.875rem;
  color: var(--fgColor-muted);
  border-bottom: 1px solid #d1d9e0;
}

.api-table tbody tr:last-child td {
  border-bottom: none;
}

.api-table code {
  padding: 0.125rem 0.375rem;
  background: #f6f8fa;
  border: 1px solid #d1d9e0;
  border-radius: 3px;
  font-family: 'Consolas', 'Monaco', 'Courier New', monospace;
  font-size: 0.875em;
  color: #d1242f;
  white-space: nowrap;
}

@media (max-width: 768px) {
  .api-subtitle {
    font-size: 1.125rem;
  }

  .api-table {
    font-size: 0.75rem;
  }

  .api-table th,
  .api-table td {
    padding: 0.5rem 0.75rem;
    font-size: 0.75rem;
  }

  .api-table code {
    font-size: 0.75em;
    padding: 0.1rem 0.25rem;
  }
}
</style>
