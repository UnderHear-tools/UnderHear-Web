<template>
  <div class="layout-page">
    <div class="page-header">
      <h1>Layout 布局</h1>
      <p>协助进行页面级整体布局。</p>
    </div>

    <div class="demo-section">
      <h2>基础布局</h2>
      <div class="demo-item">
        <h3>上下布局</h3>
        <div class="layout-demo">
          <div class="layout-header">Header</div>
          <div class="layout-content">Content</div>
          <div class="layout-footer">Footer</div>
        </div>
      </div>

      <div class="demo-item">
        <h3>左右布局</h3>
        <div class="layout-demo horizontal">
          <div class="layout-sidebar">Sidebar</div>
          <div class="layout-main">Main Content</div>
        </div>
      </div>

      <div class="demo-item">
        <h3>经典布局</h3>
        <div class="layout-demo classic">
          <div class="layout-header">Header</div>
          <div class="layout-body">
            <div class="layout-sidebar">Sidebar</div>
            <div class="layout-main">Main Content</div>
          </div>
          <div class="layout-footer">Footer</div>
        </div>
      </div>
    </div>

    <div class="api-section">
      <h2>设计规范</h2>
      <div class="spec-table">
        <table>
          <thead>
            <tr>
              <th>组件</th>
              <th>建议高度</th>
              <th>用途</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>Header</td>
              <td>60px</td>
              <td>页面顶部导航区域</td>
            </tr>
            <tr>
              <td>Sidebar</td>
              <td>auto</td>
              <td>侧边栏导航或功能区</td>
            </tr>
            <tr>
              <td>Main</td>
              <td>auto</td>
              <td>主要内容展示区域</td>
            </tr>
            <tr>
              <td>Footer</td>
              <td>60px</td>
              <td>页面底部信息区域</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>

    <div class="usage-section">
      <h2>使用建议</h2>
      <div class="usage-tips">
        <div class="tip-item">
          <h4>响应式设计</h4>
          <p>在移动端建议使用上下布局，PC端可使用左右或经典布局</p>
        </div>
        <div class="tip-item">
          <h4>内容优先</h4>
          <p>确保主要内容区域有足够的空间展示核心信息</p>
        </div>
        <div class="tip-item">
          <h4>导航清晰</h4>
          <p>保持导航结构简单明了，避免过深的层级</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
// Layout 布局组件页面
</script>

<style scoped>
.layout-page {
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
  margin-bottom: 32px;
}

.demo-item h3 {
  font-size: 18px;
  font-weight: 500;
  color: #374151;
  margin: 0 0 16px 0;
}

.layout-demo {
  border: 1px solid #e5e7eb;
  border-radius: 8px;
  overflow: hidden;
  min-height: 200px;
  display: flex;
  flex-direction: column;
}

.layout-demo.horizontal {
  flex-direction: row;
}

.layout-demo.classic .layout-body {
  display: flex;
  flex: 1;
}

.layout-header,
.layout-footer {
  background: #f3f4f6;
  padding: 16px;
  text-align: center;
  font-weight: 500;
  color: #374151;
  border-bottom: 1px solid #e5e7eb;
}

.layout-footer {
  border-bottom: none;
  border-top: 1px solid #e5e7eb;
}

.layout-sidebar {
  background: #f9fafb;
  padding: 16px;
  width: 200px;
  min-width: 200px;
  text-align: center;
  font-weight: 500;
  color: #374151;
  border-right: 1px solid #e5e7eb;
}

.layout-content,
.layout-main {
  background: #ffffff;
  padding: 16px;
  flex: 1;
  text-align: center;
  font-weight: 500;
  color: #374151;
  display: flex;
  align-items: center;
  justify-content: center;
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

.spec-table table {
  width: 100%;
  border-collapse: collapse;
  border: 1px solid #e5e7eb;
  border-radius: 8px;
  overflow: hidden;
}

.spec-table th,
.spec-table td {
  padding: 12px 16px;
  text-align: left;
  border-bottom: 1px solid #e5e7eb;
}

.spec-table th {
  background: #f9fafb;
  font-weight: 600;
  color: #374151;
}

.spec-table td {
  color: #6b7280;
}

.spec-table tr:last-child td {
  border-bottom: none;
}

.usage-section h2 {
  font-size: 24px;
  font-weight: 600;
  color: #1f2937;
  margin: 0 0 24px 0;
}

.usage-tips {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 24px;
}

.tip-item {
  padding: 20px;
  background: #f9fafb;
  border-radius: 8px;
  border-left: 4px solid #3b82f6;
}

.tip-item h4 {
  font-size: 16px;
  font-weight: 600;
  color: #1f2937;
  margin: 0 0 8px 0;
}

.tip-item p {
  font-size: 14px;
  color: #6b7280;
  margin: 0;
  line-height: 1.5;
}

@media (max-width: 768px) {
  .layout-demo.horizontal {
    flex-direction: column;
  }
  
  .layout-demo.classic .layout-body {
    flex-direction: column;
  }
  
  .layout-sidebar {
    width: 100%;
    min-width: auto;
    border-right: none;
    border-bottom: 1px solid #e5e7eb;
  }
  
  .usage-tips {
    grid-template-columns: 1fr;
  }
}
</style>