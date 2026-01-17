<template>
  <div class="link-page">
    <div class="page-header">
      <h1 class="page-title">Link 链接</h1>
      <p class="page-description">文字超链接组件,支持多种样式和外部链接标识。</p>
    </div>

    <div class="demo-section">
      <h2 class="section-title">基础用法</h2>
      <div class="demo-container">
        <div class="demo-row">
          <zLink href="https://github.com" link-text="默认链接" />
        </div>
      </div>
      <div class="code-example">
        <pre><code>&lt;zLink href="https://github.com" link-text="默认链接" /&gt;</code></pre>
      </div>
    </div>

    <div class="demo-section">
      <h2 class="section-title">不同样式</h2>
      <div class="demo-container">
        <div class="demo-row">
          <zLink href="#" link-text="Primary 链接" variant="primary" />
          <zLink href="#" link-text="Secondary 链接" variant="secondary" />
          <zLink href="#" link-text="Danger 链接" variant="danger" />
        </div>
      </div>
      <div class="code-example">
        <pre><code>&lt;zLink href="#" link-text="Primary 链接" variant="primary" /&gt;
&lt;zLink href="#" link-text="Secondary 链接" variant="secondary" /&gt;
&lt;zLink href="#" link-text="Danger 链接" variant="danger" /&gt;</code></pre>
      </div>
    </div>

    <div class="demo-section">
      <h2 class="section-title">外部链接</h2>
      <div class="demo-container">
        <div class="demo-row">
          <zLink href="https://github.com" link-text="GitHub" :external="true" target="_blank" />
        </div>
      </div>
      <div class="code-example">
        <pre><code>&lt;zLink 
  href="https://github.com" 
  link-text="GitHub" 
  :external="true" 
  target="_blank" 
/&gt;</code></pre>
      </div>
    </div>

    <div class="demo-section">
      <h2 class="section-title">带前后缀文本</h2>
      <div class="demo-container">
        <div class="demo-row">
          <zLink 
            href="/component" 
            link-text="组件文档" 
            prefix-text="查看" 
            suffix-text="了解更多" 
          />
        </div>
      </div>
      <div class="code-example">
        <pre><code>&lt;zLink 
  href="/component" 
  link-text="组件文档" 
  prefix-text="查看" 
  suffix-text="了解更多" 
/&gt;</code></pre>
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
              <td>href</td>
              <td>链接地址</td>
              <td>string</td>
              <td>—</td>
              <td>—</td>
            </tr>
            <tr>
              <td>link-text</td>
              <td>链接文本</td>
              <td>string</td>
              <td>—</td>
              <td>—</td>
            </tr>
            <tr>
              <td>prefix-text</td>
              <td>前置文本</td>
              <td>string</td>
              <td>—</td>
              <td>—</td>
            </tr>
            <tr>
              <td>suffix-text</td>
              <td>后置文本</td>
              <td>string</td>
              <td>—</td>
              <td>—</td>
            </tr>
            <tr>
              <td>external</td>
              <td>是否为外部链接</td>
              <td>boolean</td>
              <td>true / false</td>
              <td>false</td>
            </tr>
            <tr>
              <td>target</td>
              <td>链接打开方式</td>
              <td>string</td>
              <td>_blank / _self / _parent / _top</td>
              <td>_self</td>
            </tr>
            <tr>
              <td>variant</td>
              <td>链接样式变体</td>
              <td>string</td>
              <td>primary / secondary / danger / default</td>
              <td>primary</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script setup>
import { zLink } from '@/components/z-ui/link/zlink'
</script>

<style scoped>
.link-page {
  max-width: 1000px;
  margin: 0 auto;
}

.page-header {
  margin-bottom: 2rem;
}

.page-title {
  font-size: 2rem;
  font-weight: 600;
  color: var(--font-black);
  margin-bottom: 0.5rem;
}

.page-description {
  font-size: 1rem;
  color: var(--font-gray);
  line-height: 1.6;
}

.demo-section {
  margin-bottom: 3rem;
}

.section-title {
  font-size: 1.5rem;
  font-weight: 600;
  color: var(--font-black);
  margin-bottom: 1rem;
}

.demo-container {
  background: #f8f9fa;
  border-radius: 8px;
  padding: 2rem;
  margin-bottom: 1rem;
}

.demo-row {
  display: flex;
  gap: 1.5rem;
  align-items: center;
}

.code-example {
  background: #1e1e1e;
  border-radius: 6px;
  padding: 1rem;
  overflow-x: auto;
}

.code-example code {
  font-family: 'Consolas', 'Monaco', 'Courier New', monospace;
  font-size: 0.875rem;
  color: #d4d4d4;
  line-height: 1.6;
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
  background: #fff;
  border: 1px solid #e1e4e8;
  border-radius: 6px;
}

.api-table th,
.api-table td {
  padding: 0.75rem 1rem;
  text-align: left;
  border-bottom: 1px solid #e1e4e8;
}

.api-table th {
  background: #f6f8fa;
  font-weight: 600;
  color: var(--font-black);
}

.api-table td {
  color: var(--font-gray);
  font-size: 0.875rem;
}

.api-table tr:last-child td {
  border-bottom: none;
}
</style>
