<template>
  <div class="button-page">
    <div class="page-header">
      <h1 class="page-title">Button 按钮</h1>
      <p class="page-description">按钮用于开始一个即时操作。</p>
    </div>

    <div class="demo-section">
      <h2 class="section-title">基础用法</h2>
      <div class="demo-container">
        <z-button>默认按钮</z-button>
        <z-button type="primary">主要按钮</z-button>
        <z-button type="success">成功按钮</z-button>
        <z-button type="warning">警告按钮</z-button>
        <z-button type="danger">危险按钮</z-button>
      </div>
      <div class="code-example">
        <pre><code>&lt;z-button&gt;默认按钮&lt;/z-button&gt;
&lt;z-button type="primary"&gt;主要按钮&lt;/z-button&gt;
&lt;z-button type="success"&gt;成功按钮&lt;/z-button&gt;
&lt;z-button type="warning"&gt;警告按钮&lt;/z-button&gt;
&lt;z-button type="danger"&gt;危险按钮&lt;/z-button&gt;</code></pre>
      </div>
    </div>

    <div class="demo-section">
      <h2 class="section-title">按钮尺寸</h2>
      <div class="demo-container">
        <z-button size="large" type="primary">大型按钮</z-button>
        <z-button type="primary">默认按钮</z-button>
        <z-button size="small" type="primary">小型按钮</z-button>
      </div>
      <div class="code-example">
        <pre><code>&lt;z-button size="large" type="primary"&gt;大型按钮&lt;/z-button&gt;
&lt;z-button type="primary"&gt;默认按钮&lt;/z-button&gt;
&lt;z-button size="small" type="primary"&gt;小型按钮&lt;/z-button&gt;</code></pre>
      </div>
    </div>

    <div class="demo-section">
      <h2 class="section-title">禁用状态</h2>
      <div class="demo-container">
        <z-button disabled>禁用按钮</z-button>
        <z-button type="primary" disabled>禁用主要按钮</z-button>
      </div>
      <div class="code-example">
        <pre><code>&lt;z-button disabled&gt;禁用按钮&lt;/z-button&gt;
&lt;z-button type="primary" disabled&gt;禁用主要按钮&lt;/z-button&gt;</code></pre>
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
              <td>type</td>
              <td>按钮类型</td>
              <td>string</td>
              <td>primary / success / warning / danger</td>
              <td>—</td>
            </tr>
            <tr>
              <td>size</td>
              <td>按钮尺寸</td>
              <td>string</td>
              <td>large / small</td>
              <td>—</td>
            </tr>
            <tr>
              <td>disabled</td>
              <td>是否禁用</td>
              <td>boolean</td>
              <td>—</td>
              <td>false</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import ZButton from '@/components/z-ui/button/zButton/zButton.vue'
</script>

<style scoped>
.button-page {
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
  display: flex;
  gap: 1rem;
  align-items: center;
  flex-wrap: wrap;
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