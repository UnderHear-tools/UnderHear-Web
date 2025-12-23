<template>
  <div class="splitter-demo">
    <div class="demo-header">
      <h1>Splitter 分割面板</h1>
      <p>可调整大小的分割面板组件，支持水平和垂直分割。</p>
    </div>

    <div class="demo-section">
      <h2>基础用法</h2>
      <div class="demo-container">
        <ZSplitter :split="'vertical'" :min-percent="20" :default-percent="30">
          <template #pane1>
            <div class="pane-content">
              <h3>左侧面板</h3>
              <p>这是左侧的内容区域，可以放置任何组件。</p>
            </div>
          </template>
          <template #pane2>
            <div class="pane-content">
              <h3>右侧面板</h3>
              <p>这是右侧的内容区域，支持拖拽调整大小。</p>
            </div>
          </template>
        </ZSplitter>
      </div>
    </div>

    <div class="demo-section">
      <h2>水平分割</h2>
      <div class="demo-container" style="height: 300px;">
        <ZSplitter :split="'horizontal'" :min-percent="20" :default-percent="40">
          <template #pane1>
            <div class="pane-content">
              <h3>上方面板</h3>
              <p>水平分割的上方内容区域。</p>
            </div>
          </template>
          <template #pane2>
            <div class="pane-content">
              <h3>下方面板</h3>
              <p>水平分割的下方内容区域。</p>
            </div>
          </template>
        </ZSplitter>
      </div>
    </div>

    <div class="demo-section">
      <h2>嵌套分割</h2>
      <div class="demo-container" style="height: 400px;">
        <ZSplitter :split="'vertical'" :default-percent="30">
          <template #pane1>
            <div class="pane-content">
              <h3>侧边栏</h3>
              <p>固定宽度的侧边栏内容。</p>
            </div>
          </template>
          <template #pane2>
            <ZSplitter :split="'horizontal'" :default-percent="60">
              <template #pane1>
                <div class="pane-content">
                  <h3>主内容区</h3>
                  <p>这是主要的内容展示区域。</p>
                </div>
              </template>
              <template #pane2>
                <div class="pane-content">
                  <h3>底部面板</h3>
                  <p>底部的工具栏或状态栏。</p>
                </div>
              </template>
            </ZSplitter>
          </template>
        </ZSplitter>
      </div>
    </div>

    <div class="demo-section">
      <h2>禁用调整</h2>
      <div class="demo-container">
        <ZSplitter :split="'vertical'" :default-percent="50" :disabled="true">
          <template #pane1>
            <div class="pane-content">
              <h3>固定面板 1</h3>
              <p>禁用调整大小的分割面板。</p>
            </div>
          </template>
          <template #pane2>
            <div class="pane-content">
              <h3>固定面板 2</h3>
              <p>无法拖拽调整大小。</p>
            </div>
          </template>
        </ZSplitter>
      </div>
    </div>

    <div class="api-section">
      <h2>API</h2>
      <div class="api-table">
        <h3>Props</h3>
        <table>
          <thead>
            <tr>
              <th>参数</th>
              <th>说明</th>
              <th>类型</th>
              <th>默认值</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>split</td>
              <td>分割方向</td>
              <td>'vertical' | 'horizontal'</td>
              <td>'vertical'</td>
            </tr>
            <tr>
              <td>defaultPercent</td>
              <td>默认分割比例</td>
              <td>number</td>
              <td>50</td>
            </tr>
            <tr>
              <td>minPercent</td>
              <td>最小分割比例</td>
              <td>number</td>
              <td>10</td>
            </tr>
            <tr>
              <td>maxPercent</td>
              <td>最大分割比例</td>
              <td>number</td>
              <td>90</td>
            </tr>
            <tr>
              <td>disabled</td>
              <td>是否禁用调整</td>
              <td>boolean</td>
              <td>false</td>
            </tr>
            <tr>
              <td>resizerStyle</td>
              <td>分割条样式</td>
              <td>object</td>
              <td>-</td>
            </tr>
          </tbody>
        </table>

        <h3>Events</h3>
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
              <td>resize</td>
              <td>分割比例改变时触发</td>
              <td>(percent: number)</td>
            </tr>
            <tr>
              <td>resizeStart</td>
              <td>开始拖拽时触发</td>
              <td>-</td>
            </tr>
            <tr>
              <td>resizeEnd</td>
              <td>结束拖拽时触发</td>
              <td>(percent: number)</td>
            </tr>
          </tbody>
        </table>

        <h3>Slots</h3>
        <table>
          <thead>
            <tr>
              <th>名称</th>
              <th>说明</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>pane1</td>
              <td>第一个面板内容</td>
            </tr>
            <tr>
              <td>pane2</td>
              <td>第二个面板内容</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import ZSplitter from '@/components/z-ui/splitter/zSplitter/zSplitter.vue'
</script>

<style scoped>
.splitter-demo {
  padding: 24px;
  max-width: 1200px;
  margin: 0 auto;
}

.demo-header {
  margin-bottom: 32px;
}

.demo-header h1 {
  font-size: 28px;
  font-weight: 600;
  color: #1f2937;
  margin-bottom: 8px;
}

.demo-header p {
  font-size: 16px;
  color: #6b7280;
  margin: 0;
}

.demo-section {
  margin-bottom: 32px;
}

.demo-section h2 {
  font-size: 20px;
  font-weight: 600;
  color: #374151;
  margin-bottom: 16px;
}

.demo-container {
  border: 1px solid #e5e7eb;
  border-radius: 8px;
  height: 250px;
  overflow: hidden;
}

.pane-content {
  padding: 16px;
  height: 100%;
  background: #f9fafb;
  display: flex;
  flex-direction: column;
}

.pane-content h3 {
  font-size: 16px;
  font-weight: 600;
  color: #374151;
  margin: 0 0 8px 0;
}

.pane-content p {
  font-size: 14px;
  color: #6b7280;
  margin: 0;
  line-height: 1.5;
}

.api-section {
  margin-top: 48px;
}

.api-section h2 {
  font-size: 24px;
  font-weight: 600;
  color: #1f2937;
  margin-bottom: 24px;
}

.api-section h3 {
  font-size: 18px;
  font-weight: 600;
  color: #374151;
  margin: 24px 0 16px 0;
}

.api-table table {
  width: 100%;
  border-collapse: collapse;
  margin-bottom: 24px;
  font-size: 14px;
}

.api-table th,
.api-table td {
  padding: 12px;
  text-align: left;
  border-bottom: 1px solid #e5e7eb;
}

.api-table th {
  background-color: #f9fafb;
  font-weight: 600;
  color: #374151;
}

.api-table td {
  color: #6b7280;
}

.api-table code {
  background-color: #f3f4f6;
  padding: 2px 6px;
  border-radius: 4px;
  font-family: 'Monaco', 'Menlo', monospace;
  font-size: 12px;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .splitter-demo {
    padding: 16px;
  }
  
  .demo-container {
    height: 200px;
  }
  
  .api-table {
    overflow-x: auto;
  }
  
  .api-table table {
    min-width: 600px;
  }
}
</style>