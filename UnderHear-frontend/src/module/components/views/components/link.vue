<template>
  <div class="link-page">
    <div class="page-header">
      <h1>Link 链接</h1>
      <p>文字超链接组件，支持多种样式变体和外部链接标识。</p>
    </div>

    <div class="demo-section">
      <h2>基础用法</h2>
      <div class="demo-item">
        <div class="demo-content">
          <zLink href="https://www.example.com" link-text="默认链接" />
          <zLink href="https://www.example.com" link-text="主要链接" variant="primary" />
          <zLink href="https://www.example.com" link-text="次要链接" variant="secondary" />
          <zLink href="https://www.example.com" link-text="危险链接" variant="danger" />
        </div>
        <div class="demo-code">
          <pre><code>&lt;zLink href="https://www.example.com" link-text="默认链接" /&gt;
&lt;zLink href="https://www.example.com" link-text="主要链接" variant="primary" /&gt;
&lt;zLink href="https://www.example.com" link-text="次要链接" variant="secondary" /&gt;
&lt;zLink href="https://www.example.com" link-text="危险链接" variant="danger" /&gt;</code></pre>
        </div>
      </div>
    </div>

    <div class="demo-section">
      <h2>外部链接</h2>
      <div class="demo-item">
        <div class="demo-content">
          <zLink 
            href="https://www.github.com" 
            link-text="GitHub" 
            :external="true" 
            target="_blank" 
          />
          <zLink 
            href="https://www.google.com" 
            link-text="Google" 
            :external="true" 
            target="_blank"
            variant="secondary"
          />
        </div>
        <div class="demo-code">
          <pre><code>&lt;zLink 
  href="https://www.github.com" 
  link-text="GitHub" 
  :external="true" 
  target="_blank" 
/&gt;
&lt;zLink 
  href="https://www.google.com" 
  link-text="Google" 
  :external="true" 
  target="_blank"
  variant="secondary"
/&gt;</code></pre>
        </div>
      </div>
    </div>

    <div class="demo-section">
      <h2>带前后缀文本</h2>
      <div class="demo-item">
        <div class="demo-content">
          <zLink 
            href="https://www.example.com" 
            link-text="示例网站" 
            prefix-text="访问" 
          />
          <zLink 
            href="https://www.example.com" 
            link-text="更多内容" 
            suffix-text="→" 
          />
          <zLink 
            href="https://www.example.com" 
            link-text="官方文档" 
            prefix-text="查看" 
            suffix-text="了解详情"
            variant="primary"
          />
        </div>
        <div class="demo-code">
          <pre><code>&lt;zLink 
  href="https://www.example.com" 
  link-text="示例网站" 
  prefix-text="访问" 
/&gt;
&lt;zLink 
  href="https://www.example.com" 
  link-text="更多内容" 
  suffix-text="→" 
/&gt;
&lt;zLink 
  href="https://www.example.com" 
  link-text="官方文档" 
  prefix-text="查看" 
  suffix-text="了解详情"
  variant="primary"
/&gt;</code></pre>
        </div>
      </div>
    </div>

    <div class="demo-section">
      <h2>打开方式</h2>
      <div class="demo-item">
        <div class="demo-content">
          <zLink 
            href="https://www.example.com" 
            link-text="当前页面打开" 
            target="_self" 
          />
          <zLink 
            href="https://www.example.com" 
            link-text="新标签页打开" 
            target="_blank" 
          />
          <zLink 
            href="https://www.example.com" 
            link-text="父级框架打开" 
            target="_parent" 
          />
        </div>
        <div class="demo-code">
          <pre><code>&lt;zLink 
  href="https://www.example.com" 
  link-text="当前页面打开" 
  target="_self" 
/&gt;
&lt;zLink 
  href="https://www.example.com" 
  link-text="新标签页打开" 
  target="_blank" 
/&gt;
&lt;zLink 
  href="https://www.example.com" 
  link-text="父级框架打开" 
  target="_parent" 
/&gt;</code></pre>
        </div>
      </div>
    </div>

    <div class="demo-section">
      <h2>不同场景应用</h2>
      <div class="demo-item">
        <div class="demo-content" style="flex-direction: column; align-items: flex-start;">
          <p style="margin: 0;">
            这是一段文本，其中包含一个
            <zLink 
              href="https://www.example.com" 
              link-text="内联链接" 
              variant="primary"
            />
            ，可以无缝融入段落中。
          </p>
          <p style="margin: 16px 0 0 0;">
            查看我们的
            <zLink 
              href="https://docs.example.com" 
              link-text="开发文档" 
              :external="true"
              target="_blank"
              variant="primary"
            />
            以获取更多信息。
          </p>
          <div style="margin-top: 16px;">
            <zLink 
              href="https://support.example.com" 
              link-text="联系支持团队" 
              prefix-text="需要帮助？" 
              variant="secondary"
            />
          </div>
        </div>
        <div class="demo-code">
          <pre><code>&lt;p&gt;
  这是一段文本，其中包含一个
  &lt;zLink 
    href="https://www.example.com" 
    link-text="内联链接" 
    variant="primary"
  /&gt;
  ，可以无缝融入段落中。
&lt;/p&gt;</code></pre>
        </div>
      </div>
    </div>

    <div class="api-section">
      <h2>API</h2>
      <div class="api-table">
        <h3>Link Props</h3>
        <table>
          <thead>
            <tr>
              <th>参数</th>
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
              <td>是否为外部链接（显示外部链接图标）</td>
              <td>boolean</td>
              <td>—</td>
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

<script setup lang="ts">
import { zLink } from '@/components/z-ui/link/zlink'
</script>

<style scoped>
.link-page {
  padding: 24px;
  max-width: 1200px;
  margin: 0 auto;
}

.page-header {
  margin-bottom: 32px;
}

.page-header h1 {
  font-size: 28px;
  font-weight: 600;
  color: #1f2937;
  margin: 0 0 8px 0;
}

.page-header p {
  font-size: 16px;
  color: #6b7280;
  margin: 0;
}

.demo-section {
  margin-bottom: 48px;
}

.demo-section h2 {
  font-size: 24px;
  font-weight: 600;
  color: #1f2937;
  margin: 0 0 24px 0;
}

.demo-item {
  border: 1px solid #e5e7eb;
  border-radius: 8px;
  overflow: hidden;
  margin-bottom: 24px;
}

.demo-content {
  padding: 24px;
  background: #ffffff;
  display: flex;
  align-items: center;
  gap: 16px;
  flex-wrap: wrap;
}

.demo-code {
  background: #f8fafc;
  border-top: 1px solid #e5e7eb;
  padding: 16px;
}

.demo-code pre {
  margin: 0;
  font-family: 'Monaco', 'Menlo', 'Ubuntu Mono', monospace;
  font-size: 14px;
  line-height: 1.5;
  color: #374151;
  overflow-x: auto;
}

.demo-code code {
  background: none;
  padding: 0;
  font-size: inherit;
  color: inherit;
}

.api-section {
  margin-bottom: 48px;
}

.api-section h2 {
  font-size: 24px;
  font-weight: 600;
  color: #1f2937;
  margin: 0 0 24px 0;
}

.api-table {
  margin-bottom: 32px;
}

.api-table h3 {
  font-size: 18px;
  font-weight: 600;
  color: #374151;
  margin: 0 0 16px 0;
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
  padding: 12px 16px;
  text-align: left;
  border-bottom: 1px solid #e5e7eb;
}

.api-table th {
  background: #f9fafb;
  font-weight: 600;
  color: #374151;
}

.api-table td {
  color: #6b7280;
}

.api-table tr:last-child td {
  border-bottom: none;
}

.api-table td:first-child {
  font-family: 'Monaco', 'Menlo', 'Ubuntu Mono', monospace;
  font-size: 13px;
  color: #dc2626;
  background: #fef2f2;
}
</style>