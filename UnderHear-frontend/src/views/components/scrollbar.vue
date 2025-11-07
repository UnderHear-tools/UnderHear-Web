<template>
  <div class="scrollbar-page">
    <div class="page-header">
      <h1>Scrollbar 滚动条</h1>
      <p>用于替换浏览器原生滚动条。</p>
    </div>

    <div class="demo-section">
      <h2>基础用法</h2>
      <div class="demo-item">
        <div class="demo-content">
          <ZScrollbar height="200px">
            <div class="scrollbar-demo-content">
              <p>这是一段很长的内容，用来演示滚动条的效果。</p>
              <p>当内容超出容器高度时，会显示自定义滚动条。</p>
              <p>滚动条的样式可以自定义，比浏览器原生滚动条更美观。</p>
              <p>支持垂直和水平滚动。</p>
              <p>滚动条会在鼠标悬停时显示，不使用时自动隐藏。</p>
              <p>这样可以节省空间，提供更好的用户体验。</p>
              <p>滚动条的颜色和尺寸都可以通过CSS变量进行自定义。</p>
              <p>支持触摸设备的滚动操作。</p>
              <p>兼容性良好，支持主流浏览器。</p>
              <p>性能优化，滚动流畅不卡顿。</p>
              <p>这是最后一行内容。</p>
            </div>
          </ZScrollbar>
        </div>
        <div class="demo-code">
          <pre><code>&lt;ZScrollbar height="200px"&gt;
  &lt;div&gt;
    &lt;p&gt;这是一段很长的内容...&lt;/p&gt;
    &lt;!-- 更多内容 --&gt;
  &lt;/div&gt;
&lt;/ZScrollbar&gt;</code></pre>
        </div>
      </div>
    </div>

    <div class="demo-section">
      <h2>横向滚动</h2>
      <div class="demo-item">
        <div class="demo-content">
          <ZScrollbar>
            <div class="horizontal-demo">
              <div class="item">项目 1</div>
              <div class="item">项目 2</div>
              <div class="item">项目 3</div>
              <div class="item">项目 4</div>
              <div class="item">项目 5</div>
              <div class="item">项目 6</div>
              <div class="item">项目 7</div>
              <div class="item">项目 8</div>
              <div class="item">项目 9</div>
              <div class="item">项目 10</div>
            </div>
          </ZScrollbar>
        </div>
        <div class="demo-code">
          <pre><code>&lt;ZScrollbar&gt;
  &lt;div class="horizontal-demo"&gt;
    &lt;div class="item"&gt;项目 1&lt;/div&gt;
    &lt;div class="item"&gt;项目 2&lt;/div&gt;
    &lt;!-- 更多项目 --&gt;
  &lt;/div&gt;
&lt;/ZScrollbar&gt;</code></pre>
        </div>
      </div>
    </div>

    <div class="demo-section">
      <h2>最大高度</h2>
      <div class="demo-item">
        <div class="demo-content">
          <ZScrollbar max-height="150px">
            <div class="scrollbar-demo-content">
              <p>设置最大高度后，内容不足时不会显示滚动条。</p>
              <p>只有当内容超出最大高度时才会显示滚动条。</p>
              <p>这样可以根据内容动态调整容器高度。</p>
              <p>提供更好的用户体验。</p>
              <p>适用于动态内容的场景。</p>
            </div>
          </ZScrollbar>
        </div>
        <div class="demo-code">
          <pre><code>&lt;ZScrollbar max-height="150px"&gt;
  &lt;div&gt;
    &lt;p&gt;动态内容...&lt;/p&gt;
  &lt;/div&gt;
&lt;/ZScrollbar&gt;</code></pre>
        </div>
      </div>
    </div>

    <div class="demo-section">
      <h2>原生滚动条</h2>
      <div class="demo-item">
        <div class="demo-content">
          <ZScrollbar native>
            <div class="scrollbar-demo-content">
              <p>使用原生滚动条，保持浏览器默认行为。</p>
              <p>在某些场景下可能需要使用原生滚动条。</p>
              <p>比如在移动设备上，原生滚动条可能有更好的性能。</p>
              <p>或者需要保持与系统一致的外观。</p>
              <p>通过 native 属性可以轻松切换。</p>
            </div>
          </ZScrollbar>
        </div>
        <div class="demo-code">
          <pre><code>&lt;ZScrollbar native&gt;
  &lt;div&gt;
    &lt;p&gt;使用原生滚动条...&lt;/p&gt;
  &lt;/div&gt;
&lt;/ZScrollbar&gt;</code></pre>
        </div>
      </div>
    </div>

    <div class="api-section">
      <h2>API</h2>
      <div class="api-table">
        <h3>Scrollbar Props</h3>
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
              <td>height</td>
              <td>滚动条高度</td>
              <td>string / number</td>
              <td>—</td>
              <td>—</td>
            </tr>
            <tr>
              <td>max-height</td>
              <td>滚动条最大高度</td>
              <td>string / number</td>
              <td>—</td>
              <td>—</td>
            </tr>
            <tr>
              <td>native</td>
              <td>是否使用原生滚动条</td>
              <td>boolean</td>
              <td>—</td>
              <td>false</td>
            </tr>
            <tr>
              <td>wrap-style</td>
              <td>包裹容器的自定义样式</td>
              <td>string / object</td>
              <td>—</td>
              <td>—</td>
            </tr>
            <tr>
              <td>wrap-class</td>
              <td>包裹容器的自定义类名</td>
              <td>string</td>
              <td>—</td>
              <td>—</td>
            </tr>
            <tr>
              <td>view-style</td>
              <td>视图的自定义样式</td>
              <td>string / object</td>
              <td>—</td>
              <td>—</td>
            </tr>
            <tr>
              <td>view-class</td>
              <td>视图的自定义类名</td>
              <td>string</td>
              <td>—</td>
              <td>—</td>
            </tr>
            <tr>
              <td>noresize</td>
              <td>不响应容器尺寸变化</td>
              <td>boolean</td>
              <td>—</td>
              <td>false</td>
            </tr>
            <tr>
              <td>tag</td>
              <td>视图的元素标签</td>
              <td>string</td>
              <td>—</td>
              <td>div</td>
            </tr>
          </tbody>
        </table>
      </div>

      <div class="api-table">
        <h3>Scrollbar Events</h3>
        <table>
          <thead>
            <tr>
              <th>事件名</th>
              <th>说明</th>
              <th>回调参数</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>scroll</td>
              <td>滚动时触发</td>
              <td>滚动距离 { scrollLeft, scrollTop }</td>
            </tr>
          </tbody>
        </table>
      </div>

      <div class="api-table">
        <h3>Scrollbar Methods</h3>
        <table>
          <thead>
            <tr>
              <th>方法名</th>
              <th>说明</th>
              <th>参数</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>setScrollTop</td>
              <td>设置滚动条到顶部的距离</td>
              <td>scrollTop</td>
            </tr>
            <tr>
              <td>setScrollLeft</td>
              <td>设置滚动条到左边的距离</td>
              <td>scrollLeft</td>
            </tr>
            <tr>
              <td>update</td>
              <td>手动更新滚动条</td>
              <td>—</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import ZScrollbar from '@/components/z-ui/scrollbar/zScrollbar/zScrollbar.vue'
</script>

<style scoped>
.scrollbar-page {
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

.scrollbar-demo-content {
  padding: 16px;
}

.scrollbar-demo-content p {
  margin: 0 0 12px 0;
  line-height: 1.6;
  color: #374151;
}

.scrollbar-demo-content p:last-child {
  margin-bottom: 0;
}

.horizontal-demo {
  display: flex;
  gap: 16px;
  padding: 16px;
  width: 800px;
}

.horizontal-demo .item {
  flex-shrink: 0;
  width: 120px;
  height: 80px;
  background: #f3f4f6;
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #374151;
  font-weight: 500;
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