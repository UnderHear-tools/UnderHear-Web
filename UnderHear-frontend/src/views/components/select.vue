<template>
  <div class="select-page">
    <div class="page-header">
      <h1>Select 选择器</h1>
      <p>当选项过多时，使用下拉菜单展示并选择内容。</p>
    </div>

    <div class="demo-section">
      <h2>基础用法</h2>
      <div class="demo-item">
        <div class="demo-content">
          <div class="demo-row">
            <span class="demo-label">选择水果：</span>
            <zSelect 
              v-model="selectedFruit" 
              :options="fruitOptions" 
              placeholder="请选择水果"
            />
            <span class="demo-result">已选择：{{ selectedFruit || '未选择' }}</span>
          </div>
        </div>
        <div class="demo-code">
          <pre><code>&lt;zSelect 
  v-model="selectedFruit" 
  :options="fruitOptions" 
  placeholder="请选择水果"
/&gt;

// 数据定义
const selectedFruit = ref('')
const fruitOptions = [
  { value: 'apple', label: '苹果' },
  { value: 'banana', label: '香蕉' },
  { value: 'orange', label: '橙子' },
  { value: 'grape', label: '葡萄' },
  { value: 'watermelon', label: '西瓜' }
]</code></pre>
        </div>
      </div>
    </div>

    <div class="demo-section">
      <h2>禁用状态</h2>
      <div class="demo-item">
        <div class="demo-content">
          <div class="demo-row">
            <span class="demo-label">禁用选择器：</span>
            <zSelect 
              v-model="selectedCity" 
              :options="cityOptions" 
              placeholder="请选择城市"
              :disabled="true"
            />
          </div>
        </div>
        <div class="demo-code">
          <pre><code>&lt;zSelect 
  v-model="selectedCity" 
  :options="cityOptions" 
  placeholder="请选择城市"
  :disabled="true"
/&gt;</code></pre>
        </div>
      </div>
    </div>

    <div class="demo-section">
      <h2>默认值</h2>
      <div class="demo-item">
        <div class="demo-content">
          <div class="demo-row">
            <span class="demo-label">默认选中：</span>
            <zSelect 
              v-model="selectedLanguage" 
              :options="languageOptions" 
              placeholder="请选择语言"
            />
            <span class="demo-result">已选择：{{ selectedLanguage }}</span>
          </div>
        </div>
        <div class="demo-code">
          <pre><code>&lt;zSelect 
  v-model="selectedLanguage" 
  :options="languageOptions" 
  placeholder="请选择语言"
/&gt;

// 设置默认值
const selectedLanguage = ref('javascript')</code></pre>
        </div>
      </div>
    </div>

    <div class="demo-section">
      <h2>多个选择器（互斥展开）</h2>
      <div class="demo-item">
        <div class="demo-content">
          <div class="demo-row" style="flex-wrap: wrap; gap: 16px;">
            <div style="display: flex; align-items: center; gap: 8px;">
              <span class="demo-label">颜色：</span>
              <zSelect 
                v-model="selectedColor" 
                :options="colorOptions" 
                placeholder="选择颜色"
              />
            </div>
            <div style="display: flex; align-items: center; gap: 8px;">
              <span class="demo-label">尺寸：</span>
              <zSelect 
                v-model="selectedSize" 
                :options="sizeOptions" 
                placeholder="选择尺寸"
              />
            </div>
            <div style="display: flex; align-items: center; gap: 8px;">
              <span class="demo-label">品牌：</span>
              <zSelect 
                v-model="selectedBrand" 
                :options="brandOptions" 
                placeholder="选择品牌"
              />
            </div>
          </div>
          <div style="margin-top: 16px; padding: 12px; background: #f6f8fa; border-radius: 6px;">
            <strong>选择结果：</strong>
            颜色: {{ selectedColor || '未选择' }} | 
            尺寸: {{ selectedSize || '未选择' }} | 
            品牌: {{ selectedBrand || '未选择' }}
          </div>
        </div>
        <div class="demo-code">
          <pre><code>&lt;zSelect v-model="selectedColor" :options="colorOptions" placeholder="选择颜色" /&gt;
&lt;zSelect v-model="selectedSize" :options="sizeOptions" placeholder="选择尺寸" /&gt;
&lt;zSelect v-model="selectedBrand" :options="brandOptions" placeholder="选择品牌" /&gt;

// 多个选择器会自动实现互斥展开，点击一个时其他会自动关闭</code></pre>
        </div>
      </div>
    </div>

    <div class="demo-section">
      <h2>键盘操作</h2>
      <div class="demo-item">
        <div class="demo-content">
          <div class="demo-row">
            <span class="demo-label">支持键盘操作：</span>
            <zSelect 
              v-model="selectedCountry" 
              :options="countryOptions" 
              placeholder="请选择国家"
            />
          </div>
          <div style="margin-top: 16px; padding: 12px; background: #fff5e6; border-radius: 6px; font-size: 14px;">
            <strong>键盘快捷键：</strong>
            <ul style="margin: 8px 0; padding-left: 20px;">
              <li><code>↑</code> / <code>↓</code>：上下移动选项</li>
              <li><code>Enter</code> / <code>Space</code>：选择当前高亮项</li>
              <li><code>Esc</code>：关闭下拉菜单</li>
              <li><code>Tab</code>：关闭下拉菜单并移动焦点</li>
            </ul>
          </div>
        </div>
        <div class="demo-code">
          <pre><code>&lt;zSelect 
  v-model="selectedCountry" 
  :options="countryOptions" 
  placeholder="请选择国家"
/&gt;

// 选择器支持完整的键盘导航功能</code></pre>
        </div>
      </div>
    </div>

    <div class="demo-section">
      <h2>事件监听</h2>
      <div class="demo-item">
        <div class="demo-content">
          <div class="demo-row">
            <span class="demo-label">事件监听：</span>
            <zSelect 
              v-model="selectedEvent" 
              :options="eventOptions" 
              placeholder="请选择选项"
              @open="onOpen"
              @close="onClose"
            />
          </div>
          <div style="margin-top: 16px; padding: 12px; background: #f6f8fa; border-radius: 6px;">
            <div><strong>事件日志：</strong></div>
            <div v-for="(log, index) in eventLogs" :key="index" style="font-size: 13px; color: var(--font-gray); margin-top: 4px;">
              {{ log }}
            </div>
          </div>
        </div>
        <div class="demo-code">
          <pre><code>&lt;zSelect 
  v-model="selectedEvent" 
  :options="eventOptions" 
  placeholder="请选择选项"
  @open="onOpen"
  @close="onClose"
/&gt;

// 事件处理
const onOpen = () => {
  console.log('下拉菜单打开')
}

const onClose = () => {
  console.log('下拉菜单关闭')
}</code></pre>
        </div>
      </div>
    </div>

    <div class="api-section">
      <h2>API</h2>
      <div class="api-table">
        <h3>Select Props</h3>
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
              <td>model-value / v-model</td>
              <td>绑定值</td>
              <td>string</td>
              <td>—</td>
              <td>—</td>
            </tr>
            <tr>
              <td>options</td>
              <td>选项数据</td>
              <td>Option[]</td>
              <td>—</td>
              <td>[]</td>
            </tr>
            <tr>
              <td>placeholder</td>
              <td>占位符文本</td>
              <td>string</td>
              <td>—</td>
              <td>—</td>
            </tr>
            <tr>
              <td>disabled</td>
              <td>是否禁用</td>
              <td>boolean</td>
              <td>—</td>
              <td>false</td>
            </tr>
          </tbody>
        </table>
      </div>

      <div class="api-table">
        <h3>Option 类型</h3>
        <table>
          <thead>
            <tr>
              <th>属性</th>
              <th>说明</th>
              <th>类型</th>
              <th>必填</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>value</td>
              <td>选项的值</td>
              <td>string</td>
              <td>是</td>
            </tr>
            <tr>
              <td>label</td>
              <td>选项的标签</td>
              <td>string</td>
              <td>是</td>
            </tr>
          </tbody>
        </table>
      </div>

      <div class="api-table">
        <h3>Select Events</h3>
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
              <td>update:modelValue</td>
              <td>选中值发生变化时触发</td>
              <td>(value: string)</td>
            </tr>
            <tr>
              <td>open</td>
              <td>下拉菜单打开时触发</td>
              <td>—</td>
            </tr>
            <tr>
              <td>close</td>
              <td>下拉菜单关闭时触发</td>
              <td>—</td>
            </tr>
          </tbody>
        </table>
      </div>

      <div class="api-table">
        <h3>特性说明</h3>
        <ul style="margin: 16px 0; padding-left: 20px; color: #6b7280; line-height: 1.8;">
          <li><strong>键盘导航：</strong>支持方向键、Enter、Space、Esc、Tab 等键盘操作</li>
          <li><strong>无障碍支持：</strong>包含完整的 ARIA 属性，支持屏幕阅读器</li>
          <li><strong>互斥展开：</strong>多个选择器同时存在时，只能展开一个下拉菜单</li>
          <li><strong>自动滚动：</strong>键盘导航时自动滚动到可视区域</li>
          <li><strong>点击外部关闭：</strong>点击选择器外部区域自动关闭下拉菜单</li>
          <li><strong>自适应宽度：</strong>下拉菜单宽度自动适应最长选项，但不小于触发器宽度</li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { zSelect } from '@/components/z-ui/select/zSelect'

// 基础用法
const selectedFruit = ref('')
const fruitOptions = [
  { value: 'apple', label: '苹果' },
  { value: 'banana', label: '香蕉' },
  { value: 'orange', label: '橙子' },
  { value: 'grape', label: '葡萄' },
  { value: 'watermelon', label: '西瓜' }
]

// 禁用状态
const selectedCity = ref('')
const cityOptions = [
  { value: 'beijing', label: '北京' },
  { value: 'shanghai', label: '上海' },
  { value: 'guangzhou', label: '广州' },
  { value: 'shenzhen', label: '深圳' }
]

// 默认值
const selectedLanguage = ref('javascript')
const languageOptions = [
  { value: 'javascript', label: 'JavaScript' },
  { value: 'typescript', label: 'TypeScript' },
  { value: 'python', label: 'Python' },
  { value: 'java', label: 'Java' },
  { value: 'go', label: 'Go' }
]

// 多个选择器
const selectedColor = ref('')
const colorOptions = [
  { value: 'red', label: '红色' },
  { value: 'blue', label: '蓝色' },
  { value: 'green', label: '绿色' },
  { value: 'yellow', label: '黄色' }
]

const selectedSize = ref('')
const sizeOptions = [
  { value: 'xs', label: 'XS' },
  { value: 's', label: 'S' },
  { value: 'm', label: 'M' },
  { value: 'l', label: 'L' },
  { value: 'xl', label: 'XL' }
]

const selectedBrand = ref('')
const brandOptions = [
  { value: 'nike', label: 'Nike' },
  { value: 'adidas', label: 'Adidas' },
  { value: 'puma', label: 'Puma' },
  { value: 'newbalance', label: 'New Balance' }
]

// 键盘操作
const selectedCountry = ref('')
const countryOptions = [
  { value: 'china', label: '中国' },
  { value: 'usa', label: '美国' },
  { value: 'japan', label: '日本' },
  { value: 'korea', label: '韩国' },
  { value: 'uk', label: '英国' },
  { value: 'france', label: '法国' },
  { value: 'germany', label: '德国' },
  { value: 'canada', label: '加拿大' }
]

// 事件监听
const selectedEvent = ref('')
const eventOptions = [
  { value: 'option1', label: '选项1' },
  { value: 'option2', label: '选项2' },
  { value: 'option3', label: '选项3' }
]
const eventLogs = ref<string[]>([])

const onOpen = () => {
  const time = new Date().toLocaleTimeString()
  eventLogs.value.unshift(`[${time}] 下拉菜单打开`)
  if (eventLogs.value.length > 5) eventLogs.value.pop()
}

const onClose = () => {
  const time = new Date().toLocaleTimeString()
  eventLogs.value.unshift(`[${time}] 下拉菜单关闭`)
  if (eventLogs.value.length > 5) eventLogs.value.pop()
}
</script>

<style scoped>
.select-page {
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

.demo-row {
  display: flex;
  align-items: center;
  gap: 12px;
}

.demo-label {
  font-size: 14px;
  color: #374151;
  font-weight: 500;
  white-space: nowrap;
}

.demo-result {
  font-size: 14px;
  color: var(--font-blue);
  font-weight: 500;
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

code {
  background: #f6f8fa;
  padding: 2px 6px;
  border-radius: 3px;
  font-family: 'Monaco', 'Menlo', 'Ubuntu Mono', monospace;
  font-size: 13px;
  color: #d1242f;
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

