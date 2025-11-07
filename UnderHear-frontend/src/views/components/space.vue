<template>
  <div class="space-page">
    <div class="page-header">
      <h1>Space 间距</h1>
      <p>设置组件之间的间距。</p>
    </div>

    <div class="demo-section">
      <h2>基础用法</h2>
      <div class="demo-item">
        <div class="demo-content">
          <ZSpace>
            <ZButton type="primary">按钮1</ZButton>
            <ZButton>按钮2</ZButton>
            <ZButton type="success">按钮3</ZButton>
          </ZSpace>
        </div>
        <div class="demo-code">
          <pre><code>&lt;ZSpace&gt;
  &lt;ZButton type="primary"&gt;按钮1&lt;/ZButton&gt;
  &lt;ZButton&gt;按钮2&lt;/ZButton&gt;
  &lt;ZButton type="success"&gt;按钮3&lt;/ZButton&gt;
&lt;/ZSpace&gt;</code></pre>
        </div>
      </div>
    </div>

    <div class="demo-section">
      <h2>垂直间距</h2>
      <div class="demo-item">
        <div class="demo-content">
          <ZSpace direction="vertical">
            <ZButton type="primary">按钮1</ZButton>
            <ZButton>按钮2</ZButton>
            <ZButton type="success">按钮3</ZButton>
          </ZSpace>
        </div>
        <div class="demo-code">
          <pre><code>&lt;ZSpace direction="vertical"&gt;
  &lt;ZButton type="primary"&gt;按钮1&lt;/ZButton&gt;
  &lt;ZButton&gt;按钮2&lt;/ZButton&gt;
  &lt;ZButton type="success"&gt;按钮3&lt;/ZButton&gt;
&lt;/ZSpace&gt;</code></pre>
        </div>
      </div>
    </div>

    <div class="demo-section">
      <h2>间距大小</h2>
      <div class="demo-item">
        <div class="demo-content">
          <div class="size-demo">
            <h4>小间距</h4>
            <ZSpace size="small">
              <ZButton>按钮1</ZButton>
              <ZButton>按钮2</ZButton>
              <ZButton>按钮3</ZButton>
            </ZSpace>
          </div>
          <div class="size-demo">
            <h4>中等间距</h4>
            <ZSpace size="medium">
              <ZButton>按钮1</ZButton>
              <ZButton>按钮2</ZButton>
              <ZButton>按钮3</ZButton>
            </ZSpace>
          </div>
          <div class="size-demo">
            <h4>大间距</h4>
            <ZSpace size="large">
              <ZButton>按钮1</ZButton>
              <ZButton>按钮2</ZButton>
              <ZButton>按钮3</ZButton>
            </ZSpace>
          </div>
        </div>
        <div class="demo-code">
          <pre><code>&lt;ZSpace size="small"&gt;
  &lt;ZButton&gt;按钮1&lt;/ZButton&gt;
  &lt;ZButton&gt;按钮2&lt;/ZButton&gt;
  &lt;ZButton&gt;按钮3&lt;/ZButton&gt;
&lt;/ZSpace&gt;

&lt;ZSpace size="medium"&gt;
  &lt;ZButton&gt;按钮1&lt;/ZButton&gt;
  &lt;ZButton&gt;按钮2&lt;/ZButton&gt;
  &lt;ZButton&gt;按钮3&lt;/ZButton&gt;
&lt;/ZSpace&gt;

&lt;ZSpace size="large"&gt;
  &lt;ZButton&gt;按钮1&lt;/ZButton&gt;
  &lt;ZButton&gt;按钮2&lt;/ZButton&gt;
  &lt;ZButton&gt;按钮3&lt;/ZButton&gt;
&lt;/ZSpace&gt;</code></pre>
        </div>
      </div>
    </div>

    <div class="demo-section">
      <h2>自定义间距</h2>
      <div class="demo-item">
        <div class="demo-content">
          <ZSpace :size="30">
            <ZButton>按钮1</ZButton>
            <ZButton>按钮2</ZButton>
            <ZButton>按钮3</ZButton>
          </ZSpace>
        </div>
        <div class="demo-code">
          <pre><code>&lt;ZSpace :size="30"&gt;
  &lt;ZButton&gt;按钮1&lt;/ZButton&gt;
  &lt;ZButton&gt;按钮2&lt;/ZButton&gt;
  &lt;ZButton&gt;按钮3&lt;/ZButton&gt;
&lt;/ZSpace&gt;</code></pre>
        </div>
      </div>
    </div>

    <div class="demo-section">
      <h2>对齐方式</h2>
      <div class="demo-item">
        <div class="demo-content">
          <div class="align-demo">
            <h4>居中对齐</h4>
            <ZSpace align="center">
              <ZButton>短按钮</ZButton>
              <ZButton style="height: 60px;">高按钮</ZButton>
              <ZButton>短按钮</ZButton>
            </ZSpace>
          </div>
          <div class="align-demo">
            <h4>底部对齐</h4>
            <ZSpace align="end">
              <ZButton>短按钮</ZButton>
              <ZButton style="height: 60px;">高按钮</ZButton>
              <ZButton>短按钮</ZButton>
            </ZSpace>
          </div>
          <div class="align-demo">
            <h4>基线对齐</h4>
            <ZSpace align="baseline">
              <ZButton>短按钮</ZButton>
              <ZButton style="height: 60px;">高按钮</ZButton>
              <ZButton>短按钮</ZButton>
            </ZSpace>
          </div>
        </div>
        <div class="demo-code">
          <pre><code>&lt;ZSpace align="center"&gt;
  &lt;ZButton&gt;短按钮&lt;/ZButton&gt;
  &lt;ZButton style="height: 60px;"&gt;高按钮&lt;/ZButton&gt;
  &lt;ZButton&gt;短按钮&lt;/ZButton&gt;
&lt;/ZSpace&gt;

&lt;ZSpace align="end"&gt;
  &lt;ZButton&gt;短按钮&lt;/ZButton&gt;
  &lt;ZButton style="height: 60px;"&gt;高按钮&lt;/ZButton&gt;
  &lt;ZButton&gt;短按钮&lt;/ZButton&gt;
&lt;/ZSpace&gt;</code></pre>
        </div>
      </div>
    </div>

    <div class="demo-section">
      <h2>自动换行</h2>
      <div class="demo-item">
        <div class="demo-content">
          <ZSpace wrap>
            <ZButton v-for="i in 20" :key="i">按钮{{ i }}</ZButton>
          </ZSpace>
        </div>
        <div class="demo-code">
          <pre><code v-pre>&lt;ZSpace wrap&gt;
  &lt;ZButton v-for="i in 20" :key="i"&gt;按钮{{ i }}&lt;/ZButton&gt;
&lt;/ZSpace&gt;</code></pre>
        </div>
      </div>
    </div>

    <div class="demo-section">
      <h2>分隔符</h2>
      <div class="demo-item">
        <div class="demo-content">
          <ZSpace separator="|">
            <ZText>首页</ZText>
            <ZText>产品</ZText>
            <ZText>关于我们</ZText>
            <ZText>联系我们</ZText>
          </ZSpace>
        </div>
        <div class="demo-code">
          <pre><code>&lt;ZSpace separator="|"&gt;
  &lt;ZText&gt;首页&lt;/ZText&gt;
  &lt;ZText&gt;产品&lt;/ZText&gt;
  &lt;ZText&gt;关于我们&lt;/ZText&gt;
  &lt;ZText&gt;联系我们&lt;/ZText&gt;
&lt;/ZSpace&gt;</code></pre>
        </div>
      </div>
    </div>

    <div class="api-section">
      <h2>API</h2>
      <div class="api-table">
        <h3>Space Props</h3>
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
              <td>direction</td>
              <td>间距方向</td>
              <td>string</td>
              <td>horizontal / vertical</td>
              <td>horizontal</td>
            </tr>
            <tr>
              <td>size</td>
              <td>间距大小</td>
              <td>string / number / [number, number]</td>
              <td>small / medium / large</td>
              <td>small</td>
            </tr>
            <tr>
              <td>align</td>
              <td>对齐方式</td>
              <td>string</td>
              <td>start / end / center / baseline</td>
              <td>—</td>
            </tr>
            <tr>
              <td>wrap</td>
              <td>是否自动换行</td>
              <td>boolean</td>
              <td>—</td>
              <td>false</td>
            </tr>
            <tr>
              <td>separator</td>
              <td>分隔符</td>
              <td>string / VNode</td>
              <td>—</td>
              <td>—</td>
            </tr>
          </tbody>
        </table>
      </div>

      <div class="api-table">
        <h3>Space Slots</h3>
        <table>
          <thead>
            <tr>
              <th>名称</th>
              <th>说明</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>default</td>
              <td>默认插槽</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import ZSpace from '@/components/z-ui/space/zSpace/zSpace.vue'
import ZButton from '@/components/z-ui/button/zButton/zButton.vue'
import ZText from '@/components/z-ui/text/zText/zText.vue'
</script>

<style scoped>
.space-page {
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

.size-demo {
  margin-bottom: 24px;
}

.size-demo:last-child {
  margin-bottom: 0;
}

.size-demo h4 {
  font-size: 16px;
  font-weight: 500;
  color: #374151;
  margin: 0 0 12px 0;
}

.align-demo {
  margin-bottom: 24px;
}

.align-demo:last-child {
  margin-bottom: 0;
}

.align-demo h4 {
  font-size: 16px;
  font-weight: 500;
  color: #374151;
  margin: 0 0 12px 0;
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