<template>
  <div class="border-page">
    <div class="page-header">
      <h1 class="page-title">Border 边框</h1>
      <p class="page-description">我们对边框进行统一规范，可用于按钮、卡片、弹窗等组件里。</p>
    </div>

    <div class="demo-section">
      <h2 class="section-title">边框</h2>
      <div class="demo-container">
        <div class="border-demo-grid">
          <div class="border-item">
            <div class="border-box border-solid"></div>
            <p>实线边框</p>
          </div>
          <div class="border-item">
            <div class="border-box border-dashed"></div>
            <p>虚线边框</p>
          </div>
          <div class="border-item">
            <div class="border-box border-dotted"></div>
            <p>点线边框</p>
          </div>
        </div>
      </div>
      <div class="code-example">
        <pre><code>.border-solid {
  border: 1px solid #dcdfe6;
}

.border-dashed {
  border: 1px dashed #dcdfe6;
}

.border-dotted {
  border: 1px dotted #dcdfe6;
}</code></pre>
      </div>
    </div>

    <div class="demo-section">
      <h2 class="section-title">圆角</h2>
      <div class="demo-container">
        <div class="border-demo-grid">
          <div class="border-item">
            <div class="border-box border-radius-none"></div>
            <p>无圆角</p>
          </div>
          <div class="border-item">
            <div class="border-box border-radius-small"></div>
            <p>小圆角</p>
          </div>
          <div class="border-item">
            <div class="border-box border-radius-medium"></div>
            <p>中圆角</p>
          </div>
          <div class="border-item">
            <div class="border-box border-radius-large"></div>
            <p>大圆角</p>
          </div>
          <div class="border-item">
            <div class="border-box border-radius-circle"></div>
            <p>圆形</p>
          </div>
        </div>
      </div>
      <div class="code-example">
        <pre><code>.border-radius-none {
  border-radius: 0;
}

.border-radius-small {
  border-radius: 2px;
}

.border-radius-medium {
  border-radius: 4px;
}

.border-radius-large {
  border-radius: 8px;
}

.border-radius-circle {
  border-radius: 50%;
}</code></pre>
      </div>
    </div>

    <div class="demo-section">
      <h2 class="section-title">阴影</h2>
      <div class="demo-container">
        <div class="border-demo-grid">
          <div class="border-item">
            <div class="border-box shadow-small"></div>
            <p>基础投影</p>
          </div>
          <div class="border-item">
            <div class="border-box shadow-medium"></div>
            <p>浅色投影</p>
          </div>
          <div class="border-item">
            <div class="border-box shadow-large"></div>
            <p>重色投影</p>
          </div>
        </div>
      </div>
      <div class="code-example">
        <pre><code>.shadow-small {
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
}

.shadow-medium {
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.15);
}

.shadow-large {
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
}</code></pre>
      </div>
    </div>

    <div class="api-section">
      <h2 class="section-title">设计规范</h2>
      <div class="api-table">
        <table>
          <thead>
            <tr>
              <th>类型</th>
              <th>属性</th>
              <th>值</th>
              <th>说明</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td rowspan="3">边框</td>
              <td>实线</td>
              <td>1px solid #dcdfe6</td>
              <td>常用于分割线、边框</td>
            </tr>
            <tr>
              <td>虚线</td>
              <td>1px dashed #dcdfe6</td>
              <td>用于表示临时或可选状态</td>
            </tr>
            <tr>
              <td>点线</td>
              <td>1px dotted #dcdfe6</td>
              <td>用于辅助线或引导线</td>
            </tr>
            <tr>
              <td rowspan="5">圆角</td>
              <td>无圆角</td>
              <td>0</td>
              <td>严肃、正式的场景</td>
            </tr>
            <tr>
              <td>小圆角</td>
              <td>2px</td>
              <td>轻微圆润，保持简洁</td>
            </tr>
            <tr>
              <td>中圆角</td>
              <td>4px</td>
              <td>常用圆角，平衡美观与实用</td>
            </tr>
            <tr>
              <td>大圆角</td>
              <td>8px</td>
              <td>友好、现代的视觉效果</td>
            </tr>
            <tr>
              <td>圆形</td>
              <td>50%</td>
              <td>头像、图标等圆形元素</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<style scoped>
.border-page {
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

.border-demo-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(120px, 1fr));
  gap: 2rem;
  align-items: center;
}

.border-item {
  text-align: center;
}

.border-box {
  width: 80px;
  height: 80px;
  background: #f8f9fa;
  margin: 0 auto 0.5rem;
  display: flex;
  align-items: center;
  justify-content: center;
}

.border-item p {
  margin: 0;
  font-size: 0.875rem;
  color: #6b7280;
}

/* 边框样式 */
.border-solid {
  border: 1px solid #dcdfe6;
}

.border-dashed {
  border: 1px dashed #dcdfe6;
}

.border-dotted {
  border: 1px dotted #dcdfe6;
}

/* 圆角样式 */
.border-radius-none {
  border: 1px solid #dcdfe6;
  border-radius: 0;
}

.border-radius-small {
  border: 1px solid #dcdfe6;
  border-radius: 2px;
}

.border-radius-medium {
  border: 1px solid #dcdfe6;
  border-radius: 4px;
}

.border-radius-large {
  border: 1px solid #dcdfe6;
  border-radius: 8px;
}

.border-radius-circle {
  border: 1px solid #dcdfe6;
  border-radius: 50%;
}

/* 阴影样式 */
.shadow-small {
  border: 1px solid #dcdfe6;
  border-radius: 4px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
}

.shadow-medium {
  border: 1px solid #dcdfe6;
  border-radius: 4px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.15);
}

.shadow-large {
  border: 1px solid #dcdfe6;
  border-radius: 4px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
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