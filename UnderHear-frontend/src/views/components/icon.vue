<template>
  <div class="icon-page">
    <div class="page-header">
      <h1 class="page-title">Icon 图标</h1>
      <p class="page-description">提供了一套常用的图标集合。</p>
    </div>

    <div class="demo-section">
      <h2 class="section-title">基础用法</h2>
      <div class="demo-container">
        <div class="icon-grid">
          <div class="icon-item">
            <z-icon name="home" />
            <span>home</span>
          </div>
          <div class="icon-item">
            <z-icon name="user" />
            <span>user</span>
          </div>
          <div class="icon-item">
            <z-icon name="setting" />
            <span>setting</span>
          </div>
          <div class="icon-item">
            <z-icon name="search" />
            <span>search</span>
          </div>
        </div>
      </div>
      <div class="code-example">
        <pre><code>&lt;z-icon name="home" /&gt;
&lt;z-icon name="user" /&gt;
&lt;z-icon name="setting" /&gt;
&lt;z-icon name="search" /&gt;</code></pre>
      </div>
    </div>

    <div class="demo-section">
      <h2 class="section-title">图标尺寸</h2>
      <div class="demo-container">
        <div class="icon-size-demo">
          <z-icon name="home" size="small" />
          <z-icon name="home" />
          <z-icon name="home" size="large" />
        </div>
      </div>
      <div class="code-example">
        <pre><code>&lt;z-icon name="home" size="small" /&gt;
&lt;z-icon name="home" /&gt;
&lt;z-icon name="home" size="large" /&gt;</code></pre>
      </div>
    </div>

    <div class="api-section">
      <h2 class="section-title">API</h2>
      <div class="api-table">
        <table>
          <thead>
            <tr>
              <th>属性</th>
              <th>说明</th>
              <th>类型</th>
              <th>可选值</th>
              <th>默认值</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>name</td>
              <td>图标名称</td>
              <td>string</td>
              <td>—</td>
              <td>—</td>
            </tr>
            <tr>
              <td>size</td>
              <td>图标尺寸</td>
              <td>string</td>
              <td>small / large</td>
              <td>—</td>
            </tr>
            <tr>
              <td>color</td>
              <td>图标颜色</td>
              <td>string</td>
              <td>—</td>
              <td>—</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import ZIcon from '@/components/z-ui/icon/zIcon/zIcon.vue'
</script>

<style scoped>
.icon-page {
  padding: 2rem;
  max-width: 1200px;
  margin: 0 auto;
}

.page-header {
  margin-bottom: 2rem;
  border-bottom: 1px solid #e5e7eb;
  padding-bottom: 1rem;
}

.page-title {
  font-size: 2rem;
  font-weight: 600;
  color: #1f2937;
  margin: 0 0 0.5rem 0;
}

.page-description {
  font-size: 1rem;
  color: #6b7280;
  margin: 0;
}

.demo-section {
  margin-bottom: 3rem;
}

.section-title {
  font-size: 1.25rem;
  font-weight: 600;
  color: #1f2937;
  margin: 0 0 1rem 0;
}

.demo-container {
  padding: 1.5rem;
  border: 1px solid #e5e7eb;
  border-radius: 8px;
  background: #fff;
  margin-bottom: 1rem;
}

.icon-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(100px, 1fr));
  gap: 1rem;
}

.icon-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 0.5rem;
  padding: 1rem;
  border: 1px solid #e5e7eb;
  border-radius: 8px;
  transition: all 0.2s ease;
}

.icon-item:hover {
  background: #f9fafb;
  border-color: #3b82f6;
}

.icon-item span {
  font-size: 0.75rem;
  color: #6b7280;
}

.icon-size-demo {
  display: flex;
  align-items: center;
  gap: 2rem;
}

.code-example {
  background: #f8f9fa;
  border: 1px solid #e5e7eb;
  border-radius: 8px;
  overflow: hidden;
}

.code-example pre {
  margin: 0;
  padding: 1rem;
  overflow-x: auto;
}

.code-example code {
  font-family: 'Monaco', 'Menlo', 'Ubuntu Mono', monospace;
  font-size: 0.875rem;
  color: #374151;
}

.api-section {
  margin-top: 3rem;
}

.api-table {
  overflow-x: auto;
}

.api-table table {
  width: 100%;
  border-collapse: collapse;
  border: 1px solid #e5e7eb;
  border-radius: 8px;
  overflow: hidden;
}

.api-table th,
.api-table td {
  padding: 0.75rem 1rem;
  text-align: left;
  border-bottom: 1px solid #e5e7eb;
}

.api-table th {
  background: #f8f9fa;
  font-weight: 600;
  color: #374151;
}

.api-table td {
  color: #6b7280;
}

.api-table tbody tr:last-child td {
  border-bottom: none;
}
</style>