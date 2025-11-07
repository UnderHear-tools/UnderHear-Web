<template>
  <div class="container-page">
    <div class="page-header">
      <h1 class="page-title">Container 布局容器</h1>
      <p class="page-description">用于布局的容器组件，方便快速搭建页面的基本结构。</p>
    </div>

    <div class="demo-section">
      <h2 class="section-title">常见页面布局</h2>
      <div class="demo-container">
        <z-container class="layout-demo">
          <z-header>Header</z-header>
          <z-main>Main</z-main>
        </z-container>
      </div>
      <div class="code-example">
        <pre><code>&lt;z-container&gt;
  &lt;z-header&gt;Header&lt;/z-header&gt;
  &lt;z-main&gt;Main&lt;/z-main&gt;
&lt;/z-container&gt;</code></pre>
      </div>
    </div>

    <div class="demo-section">
      <h2 class="section-title">带侧边栏</h2>
      <div class="demo-container">
        <z-container class="layout-demo" direction="horizontal">
          <z-aside width="200px">Aside</z-aside>
          <z-container>
            <z-header>Header</z-header>
            <z-main>Main</z-main>
          </z-container>
        </z-container>
      </div>
      <div class="code-example">
        <pre><code>&lt;z-container direction="horizontal"&gt;
  &lt;z-aside width="200px"&gt;Aside&lt;/z-aside&gt;
  &lt;z-container&gt;
    &lt;z-header&gt;Header&lt;/z-header&gt;
    &lt;z-main&gt;Main&lt;/z-main&gt;
  &lt;/z-container&gt;
&lt;/z-container&gt;</code></pre>
      </div>
    </div>

    <div class="demo-section">
      <h2 class="section-title">带底部</h2>
      <div class="demo-container">
        <z-container class="layout-demo">
          <z-header>Header</z-header>
          <z-main>Main</z-main>
          <z-footer>Footer</z-footer>
        </z-container>
      </div>
      <div class="code-example">
        <pre><code>&lt;z-container&gt;
  &lt;z-header&gt;Header&lt;/z-header&gt;
  &lt;z-main&gt;Main&lt;/z-main&gt;
  &lt;z-footer&gt;Footer&lt;/z-footer&gt;
&lt;/z-container&gt;</code></pre>
      </div>
    </div>

    <div class="api-section">
      <h2 class="section-title">Container API</h2>
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
              <td>direction</td>
              <td>子元素的排列方向</td>
              <td>string</td>
              <td>horizontal / vertical</td>
              <td>vertical</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>

    <div class="api-section">
      <h2 class="section-title">Header API</h2>
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
              <td>height</td>
              <td>顶栏高度</td>
              <td>string</td>
              <td>—</td>
              <td>60px</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>

    <div class="api-section">
      <h2 class="section-title">Aside API</h2>
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
              <td>width</td>
              <td>侧边栏宽度</td>
              <td>string</td>
              <td>—</td>
              <td>300px</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>

    <div class="api-section">
      <h2 class="section-title">Footer API</h2>
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
              <td>height</td>
              <td>底栏高度</td>
              <td>string</td>
              <td>—</td>
              <td>60px</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import ZContainer from '@/components/z-ui/container/zContainer/zContainer.vue'
import ZHeader from '@/components/z-ui/container/zHeader/zHeader.vue'
import ZAside from '@/components/z-ui/container/zAside/zAside.vue'
import ZMain from '@/components/z-ui/container/zMain/zMain.vue'
import ZFooter from '@/components/z-ui/container/zFooter/zFooter.vue'
</script>

<style scoped>
.container-page {
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

.layout-demo {
  height: 200px;
  border: 1px solid #e5e7eb;
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
  margin-bottom: 2rem;
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