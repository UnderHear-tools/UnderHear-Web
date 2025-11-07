<template>
  <div class="text-page">
    <div class="page-header">
      <h1>Text 文本</h1>
      <p>文本的常见操作。</p>
    </div>

    <div class="demo-section">
      <h2>基础文本</h2>
      <div class="demo-item">
        <div class="demo-content">
          <ZText>默认文本</ZText>
          <ZText type="primary">主要文本</ZText>
          <ZText type="success">成功文本</ZText>
          <ZText type="warning">警告文本</ZText>
          <ZText type="danger">危险文本</ZText>
          <ZText type="info">信息文本</ZText>
        </div>
        <div class="demo-code">
          <pre><code>&lt;ZText&gt;默认文本&lt;/ZText&gt;
&lt;ZText type="primary"&gt;主要文本&lt;/ZText&gt;
&lt;ZText type="success"&gt;成功文本&lt;/ZText&gt;
&lt;ZText type="warning"&gt;警告文本&lt;/ZText&gt;
&lt;ZText type="danger"&gt;危险文本&lt;/ZText&gt;
&lt;ZText type="info"&gt;信息文本&lt;/ZText&gt;</code></pre>
        </div>
      </div>
    </div>

    <div class="demo-section">
      <h2>文本尺寸</h2>
      <div class="demo-item">
        <div class="demo-content">
          <ZText size="large">大号文本</ZText>
          <ZText size="default">默认文本</ZText>
          <ZText size="small">小号文本</ZText>
        </div>
        <div class="demo-code">
          <pre><code>&lt;ZText size="large"&gt;大号文本&lt;/ZText&gt;
&lt;ZText size="default"&gt;默认文本&lt;/ZText&gt;
&lt;ZText size="small"&gt;小号文本&lt;/ZText&gt;</code></pre>
        </div>
      </div>
    </div>

    <div class="demo-section">
      <h2>文本标签</h2>
      <div class="demo-item">
        <div class="demo-content">
          <ZText tag="h1">H1 标题</ZText>
          <ZText tag="h2">H2 标题</ZText>
          <ZText tag="p">段落文本</ZText>
          <ZText tag="span">行内文本</ZText>
        </div>
        <div class="demo-code">
          <pre><code>&lt;ZText tag="h1"&gt;H1 标题&lt;/ZText&gt;
&lt;ZText tag="h2"&gt;H2 标题&lt;/ZText&gt;
&lt;ZText tag="p"&gt;段落文本&lt;/ZText&gt;
&lt;ZText tag="span"&gt;行内文本&lt;/ZText&gt;</code></pre>
        </div>
      </div>
    </div>

    <div class="demo-section">
      <h2>文本省略</h2>
      <div class="demo-item">
        <div class="demo-content">
          <div style="width: 200px;">
            <ZText truncated>这是一段很长的文本，会被截断显示省略号</ZText>
          </div>
          <div style="width: 200px; margin-top: 16px;">
            <ZText :line-clamp="2">
              这是一段很长的文本内容，超过两行会被截断显示省略号。这里是更多的文本内容用来演示多行截断的效果。
            </ZText>
          </div>
        </div>
        <div class="demo-code">
          <pre><code>&lt;ZText truncated&gt;这是一段很长的文本，会被截断显示省略号&lt;/ZText&gt;
&lt;ZText :line-clamp="2"&gt;
  这是一段很长的文本内容，超过两行会被截断显示省略号。
&lt;/ZText&gt;</code></pre>
        </div>
      </div>
    </div>

    <div class="demo-section">
      <h2>文本装饰</h2>
      <div class="demo-item">
        <div class="demo-content">
          <ZText underline>下划线文本</ZText>
          <ZText line-through>删除线文本</ZText>
          <ZText strong>粗体文本</ZText>
          <ZText italic>斜体文本</ZText>
          <ZText code>代码文本</ZText>
        </div>
        <div class="demo-code">
          <pre><code>&lt;ZText underline&gt;下划线文本&lt;/ZText&gt;
&lt;ZText line-through&gt;删除线文本&lt;/ZText&gt;
&lt;ZText strong&gt;粗体文本&lt;/ZText&gt;
&lt;ZText italic&gt;斜体文本&lt;/ZText&gt;
&lt;ZText code&gt;代码文本&lt;/ZText&gt;</code></pre>
        </div>
      </div>
    </div>

    <div class="api-section">
      <h2>API</h2>
      <div class="api-table">
        <h3>Text Props</h3>
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
              <td>type</td>
              <td>文本类型</td>
              <td>string</td>
              <td>primary / success / warning / danger / info</td>
              <td>default</td>
            </tr>
            <tr>
              <td>size</td>
              <td>文本尺寸</td>
              <td>string</td>
              <td>large / default / small</td>
              <td>default</td>
            </tr>
            <tr>
              <td>tag</td>
              <td>自定义元素标签</td>
              <td>string</td>
              <td>—</td>
              <td>span</td>
            </tr>
            <tr>
              <td>truncated</td>
              <td>显示省略号</td>
              <td>boolean</td>
              <td>—</td>
              <td>false</td>
            </tr>
            <tr>
              <td>line-clamp</td>
              <td>最大行数</td>
              <td>number</td>
              <td>—</td>
              <td>—</td>
            </tr>
            <tr>
              <td>underline</td>
              <td>下划线</td>
              <td>boolean</td>
              <td>—</td>
              <td>false</td>
            </tr>
            <tr>
              <td>line-through</td>
              <td>删除线</td>
              <td>boolean</td>
              <td>—</td>
              <td>false</td>
            </tr>
            <tr>
              <td>strong</td>
              <td>粗体</td>
              <td>boolean</td>
              <td>—</td>
              <td>false</td>
            </tr>
            <tr>
              <td>italic</td>
              <td>斜体</td>
              <td>boolean</td>
              <td>—</td>
              <td>false</td>
            </tr>
            <tr>
              <td>code</td>
              <td>代码样式</td>
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
import ZText from '@/components/z-ui/text/zText/zText.vue'
</script>

<style scoped>
.text-page {
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

.demo-content > div {
  display: flex;
  flex-direction: column;
  gap: 8px;
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