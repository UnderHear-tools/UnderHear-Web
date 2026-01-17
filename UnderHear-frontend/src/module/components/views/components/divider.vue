<template>
  <div class="divider-page">
    <div class="page-header">
      <h1 class="page-title">Divider 分隔线</h1>
      <p class="page-description">区隔内容的分隔线组件,用于对不同章节的内容进行分割。</p>
    </div>

    <div class="demo-section">
      <h2 class="section-title">基础用法</h2>
      <div class="demo-container">
        <div class="demo-content">
          <p>这是第一段内容</p>
          <zDivider />
          <p>这是第二段内容</p>
          <zDivider />
          <p>这是第三段内容</p>
        </div>
      </div>
      <div class="code-example">
        <pre><code>&lt;p&gt;这是第一段内容&lt;/p&gt;
&lt;zDivider /&gt;
&lt;p&gt;这是第二段内容&lt;/p&gt;
&lt;zDivider /&gt;
&lt;p&gt;这是第三段内容&lt;/p&gt;</code></pre>
      </div>
    </div>

    <div class="demo-section">
      <h2 class="section-title">使用场景</h2>
      <div class="demo-container">
        <div class="demo-content">
          <div class="info-card">
            <h3>用户信息</h3>
            <p>姓名: 张三</p>
            <p>邮箱: zhangsan@example.com</p>
          </div>
          <zDivider />
          <div class="info-card">
            <h3>账号设置</h3>
            <p>注册时间: 2024-01-01</p>
            <p>最后登录: 2024-01-17</p>
          </div>
        </div>
      </div>
      <div class="code-example">
        <pre><code>&lt;div class="info-card"&gt;
  &lt;h3&gt;用户信息&lt;/h3&gt;
  &lt;p&gt;姓名: 张三&lt;/p&gt;
  &lt;p&gt;邮箱: zhangsan@example.com&lt;/p&gt;
&lt;/div&gt;
&lt;zDivider /&gt;
&lt;div class="info-card"&gt;
  &lt;h3&gt;账号设置&lt;/h3&gt;
  &lt;p&gt;注册时间: 2024-01-01&lt;/p&gt;
  &lt;p&gt;最后登录: 2024-01-17&lt;/p&gt;
&lt;/div&gt;</code></pre>
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
              <td colspan="5" style="text-align: center; color: var(--font-gray);">暂无可配置属性</td>
            </tr>
          </tbody>
        </table>
      </div>
      <p class="api-note">Divider 组件为纯样式组件,不接受任何属性配置,仅用于视觉上的内容分隔。</p>
    </div>
  </div>
</template>

<script setup>
import { zDivider } from '@/components/z-ui/divider/zDivider'
</script>

<style scoped>
.divider-page {
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

.demo-content {
  max-width: 600px;
  margin: 0 auto;
}

.demo-content p {
  margin: 0.5rem 0;
  color: var(--font-gray);
  line-height: 1.6;
}

.info-card {
  padding: 1rem 0;
}

.info-card h3 {
  font-size: 1.1rem;
  font-weight: 600;
  color: var(--font-black);
  margin-bottom: 0.75rem;
}

.info-card p {
  margin: 0.25rem 0;
  color: var(--font-gray);
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

.api-note {
  margin-top: 1rem;
  padding: 0.75rem 1rem;
  background: #f6f8fa;
  border-left: 3px solid var(--font-blue);
  color: var(--font-gray);
  font-size: 0.875rem;
  line-height: 1.6;
}
</style>
