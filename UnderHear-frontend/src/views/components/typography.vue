<template>
  <div class="typography-demo">
    <div class="demo-header">
      <h1>Typography 排版</h1>
      <p>用于展示文本内容的排版组件，提供丰富的文本样式和层次结构。</p>
    </div>

    <div class="demo-section">
      <h2>标题</h2>
      <div class="demo-container">
        <ZTypography tag="h1" type="title" :level="1">
          一级标题 H1
        </ZTypography>
        <ZTypography tag="h2" type="title" :level="2">
          二级标题 H2
        </ZTypography>
        <ZTypography tag="h3" type="title" :level="3">
          三级标题 H3
        </ZTypography>
        <ZTypography tag="h4" type="title" :level="4">
          四级标题 H4
        </ZTypography>
        <ZTypography tag="h5" type="title" :level="5">
          五级标题 H5
        </ZTypography>
        <ZTypography tag="h6" type="title" :level="6">
          六级标题 H6
        </ZTypography>
      </div>
    </div>

    <div class="demo-section">
      <h2>正文</h2>
      <div class="demo-container">
        <ZTypography type="body" size="large">
          这是大号正文文本。适用于重要的内容描述，具有良好的可读性和视觉层次。
        </ZTypography>
        <ZTypography type="body" size="medium">
          这是中号正文文本。这是最常用的文本大小，适合大部分内容展示场景。
        </ZTypography>
        <ZTypography type="body" size="small">
          这是小号正文文本。适用于辅助信息、说明文字等次要内容。
        </ZTypography>
      </div>
    </div>

    <div class="demo-section">
      <h2>文本状态</h2>
      <div class="demo-container">
        <ZTypography type="body" state="default">
          默认状态文本
        </ZTypography>
        <ZTypography type="body" state="secondary">
          次要状态文本
        </ZTypography>
        <ZTypography type="body" state="success">
          成功状态文本
        </ZTypography>
        <ZTypography type="body" state="warning">
          警告状态文本
        </ZTypography>
        <ZTypography type="body" state="error">
          错误状态文本
        </ZTypography>
        <ZTypography type="body" state="disabled">
          禁用状态文本
        </ZTypography>
      </div>
    </div>

    <div class="demo-section">
      <h2>文本装饰</h2>
      <div class="demo-container">
        <ZTypography type="body" :bold="true">
          粗体文本
        </ZTypography>
        <ZTypography type="body" :italic="true">
          斜体文本
        </ZTypography>
        <ZTypography type="body" :underline="true">
          下划线文本
        </ZTypography>
        <ZTypography type="body" :strikethrough="true">
          删除线文本
        </ZTypography>
        <ZTypography type="body" :code="true">
          代码文本
        </ZTypography>
      </div>
    </div>

    <div class="demo-section">
      <h2>文本省略</h2>
      <div class="demo-container">
        <div style="width: 200px; border: 1px solid #e5e7eb; padding: 12px; margin-bottom: 16px;">
          <ZTypography type="body" :ellipsis="true">
            这是一段很长的文本内容，当容器宽度不足时会自动省略显示
          </ZTypography>
        </div>
        <div style="width: 300px; height: 60px; border: 1px solid #e5e7eb; padding: 12px;">
          <ZTypography type="body" :ellipsis="{ rows: 2 }">
            这是一段很长的文本内容，支持多行省略。当文本内容超过指定行数时，会在末尾显示省略号。这个功能对于卡片布局和列表展示非常有用。
          </ZTypography>
        </div>
      </div>
    </div>

    <div class="demo-section">
      <h2>可复制文本</h2>
      <div class="demo-container">
        <ZTypography type="body" :copyable="true">
          这段文本可以复制
        </ZTypography>
        <ZTypography type="body" :copyable="{ text: '自定义复制内容' }">
          点击复制按钮会复制自定义内容
        </ZTypography>
      </div>
    </div>

    <div class="demo-section">
      <h2>可编辑文本</h2>
      <div class="demo-container">
        <ZTypography type="body" :editable="true" v-model:content="editableText1">
          {{ editableText1 }}
        </ZTypography>
        <ZTypography type="body" :editable="{ maxLength: 50 }" v-model:content="editableText2">
          {{ editableText2 }}
        </ZTypography>
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
              <td>tag</td>
              <td>HTML 标签</td>
              <td>string</td>
              <td>'span'</td>
            </tr>
            <tr>
              <td>type</td>
              <td>文本类型</td>
              <td>'title' | 'body' | 'caption'</td>
              <td>'body'</td>
            </tr>
            <tr>
              <td>level</td>
              <td>标题级别（type为title时有效）</td>
              <td>1 | 2 | 3 | 4 | 5 | 6</td>
              <td>1</td>
            </tr>
            <tr>
              <td>size</td>
              <td>文本大小</td>
              <td>'small' | 'medium' | 'large'</td>
              <td>'medium'</td>
            </tr>
            <tr>
              <td>state</td>
              <td>文本状态</td>
              <td>'default' | 'secondary' | 'success' | 'warning' | 'error' | 'disabled'</td>
              <td>'default'</td>
            </tr>
            <tr>
              <td>bold</td>
              <td>是否粗体</td>
              <td>boolean</td>
              <td>false</td>
            </tr>
            <tr>
              <td>italic</td>
              <td>是否斜体</td>
              <td>boolean</td>
              <td>false</td>
            </tr>
            <tr>
              <td>underline</td>
              <td>是否下划线</td>
              <td>boolean</td>
              <td>false</td>
            </tr>
            <tr>
              <td>strikethrough</td>
              <td>是否删除线</td>
              <td>boolean</td>
              <td>false</td>
            </tr>
            <tr>
              <td>code</td>
              <td>是否代码样式</td>
              <td>boolean</td>
              <td>false</td>
            </tr>
            <tr>
              <td>ellipsis</td>
              <td>文本省略</td>
              <td>boolean | { rows: number }</td>
              <td>false</td>
            </tr>
            <tr>
              <td>copyable</td>
              <td>是否可复制</td>
              <td>boolean | { text: string }</td>
              <td>false</td>
            </tr>
            <tr>
              <td>editable</td>
              <td>是否可编辑</td>
              <td>boolean | { maxLength: number }</td>
              <td>false</td>
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
              <td>copy</td>
              <td>复制成功时触发</td>
              <td>(text: string)</td>
            </tr>
            <tr>
              <td>edit</td>
              <td>编辑完成时触发</td>
              <td>(text: string)</td>
            </tr>
            <tr>
              <td>update:content</td>
              <td>内容更新时触发</td>
              <td>(text: string)</td>
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
              <td>default</td>
              <td>文本内容</td>
            </tr>
            <tr>
              <td>copyIcon</td>
              <td>自定义复制图标</td>
            </tr>
            <tr>
              <td>editIcon</td>
              <td>自定义编辑图标</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import ZTypography from '@/components/z-ui/typography/zTypography/zTypography.vue'

const editableText1 = ref('这是可编辑的文本，点击编辑图标进行修改')
const editableText2 = ref('这是有长度限制的可编辑文本')
</script>

<style scoped>
.typography-demo {
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
  padding: 24px;
  background: #ffffff;
}

.demo-container > * {
  margin-bottom: 16px;
}

.demo-container > *:last-child {
  margin-bottom: 0;
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
  .typography-demo {
    padding: 16px;
  }
  
  .demo-container {
    padding: 16px;
  }
  
  .api-table {
    overflow-x: auto;
  }
  
  .api-table table {
    min-width: 600px;
  }
}
</style>