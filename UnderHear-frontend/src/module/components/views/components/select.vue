<template>
  <div class="select-page">
    <div class="page-header">
      <h1 class="page-title">Select 选择器</h1>
      <p class="page-description">当选项过多时,使用下拉菜单展示并选择内容。支持键盘导航和单选功能。</p>
    </div>

    <div class="demo-section">
      <h2 class="section-title">基础用法</h2>
      <div class="demo-container">
        <div class="demo-row">
          <div class="demo-item">
            <label>选择城市:</label>
            <zSelect 
              v-model="selectedCity" 
              :options="cityOptions" 
              placeholder="请选择城市"
              style="width: 200px;"
            />
          </div>
          <div class="demo-value">已选择: {{ selectedCity || '(未选择)' }}</div>
        </div>
      </div>
      <div class="code-example">
        <pre><code>&lt;zSelect 
  v-model="value" 
  :options="options" 
  placeholder="请选择"
/&gt;

&lt;script setup&gt;
import { ref } from 'vue'

const value = ref('')
const options = [
  { value: 'beijing', label: '北京' },
  { value: 'shanghai', label: '上海' },
  { value: 'guangzhou', label: '广州' },
  { value: 'shenzhen', label: '深圳' }
]
&lt;/script&gt;</code></pre>
      </div>
    </div>

    <div class="demo-section">
      <h2 class="section-title">禁用状态</h2>
      <div class="demo-container">
        <div class="demo-row">
          <zSelect 
            v-model="disabledValue" 
            :options="cityOptions" 
            placeholder="已禁用的选择器"
            :disabled="true"
            style="width: 200px;"
          />
        </div>
      </div>
      <div class="code-example">
        <pre><code>&lt;zSelect 
  v-model="value" 
  :options="options" 
  :disabled="true"
/&gt;</code></pre>
      </div>
    </div>

    <div class="demo-section">
      <h2 class="section-title">不同场景</h2>
      <div class="demo-container">
        <div class="demo-column">
          <div class="demo-item">
            <label>选择语言:</label>
            <zSelect 
              v-model="selectedLang" 
              :options="langOptions" 
              placeholder="请选择编程语言"
              style="width: 250px;"
            />
          </div>
          <div class="demo-item">
            <label>选择框架:</label>
            <zSelect 
              v-model="selectedFramework" 
              :options="frameworkOptions" 
              placeholder="请选择前端框架"
              style="width: 250px;"
            />
          </div>
          <div class="form-result">
            <h4>选择结果:</h4>
            <p>语言: {{ getLangLabel(selectedLang) }}</p>
            <p>框架: {{ getFrameworkLabel(selectedFramework) }}</p>
          </div>
        </div>
      </div>
      <div class="code-example">
        <pre><code>&lt;zSelect v-model="lang" :options="langOptions" /&gt;
&lt;zSelect v-model="framework" :options="frameworkOptions" /&gt;</code></pre>
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
              <td>v-model</td>
              <td>绑定值</td>
              <td>string</td>
              <td>—</td>
              <td>—</td>
            </tr>
            <tr>
              <td>options</td>
              <td>选项数据</td>
              <td>Array&lt;{value: string, label: string}&gt;</td>
              <td>—</td>
              <td>[]</td>
            </tr>
            <tr>
              <td>placeholder</td>
              <td>占位文本</td>
              <td>string</td>
              <td>—</td>
              <td>请选择</td>
            </tr>
            <tr>
              <td>disabled</td>
              <td>是否禁用</td>
              <td>boolean</td>
              <td>true / false</td>
              <td>false</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>

    <div class="api-section">
      <h2 class="section-title">事件</h2>
      <div class="api-table">
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
              <td>下拉框打开时触发</td>
              <td>—</td>
            </tr>
            <tr>
              <td>close</td>
              <td>下拉框关闭时触发</td>
              <td>—</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>

    <div class="tip-section">
      <h3>💡 使用提示</h3>
      <ul>
        <li>支持键盘方向键 (↑/↓) 导航选项</li>
        <li>支持 Enter 键选择当前高亮选项</li>
        <li>支持 Escape 键关闭下拉框</li>
        <li>鼠标移动时会自动高亮对应选项</li>
      </ul>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import { zSelect } from '@/components/z-ui/select/zSelect'

const selectedCity = ref('')
const disabledValue = ref('beijing')
const selectedLang = ref('')
const selectedFramework = ref('')

const cityOptions = [
  { value: 'beijing', label: '北京' },
  { value: 'shanghai', label: '上海' },
  { value: 'guangzhou', label: '广州' },
  { value: 'shenzhen', label: '深圳' },
  { value: 'hangzhou', label: '杭州' },
  { value: 'chengdu', label: '成都' }
]

const langOptions = [
  { value: 'javascript', label: 'JavaScript' },
  { value: 'typescript', label: 'TypeScript' },
  { value: 'python', label: 'Python' },
  { value: 'java', label: 'Java' },
  { value: 'go', label: 'Go' },
  { value: 'rust', label: 'Rust' }
]

const frameworkOptions = [
  { value: 'vue', label: 'Vue.js' },
  { value: 'react', label: 'React' },
  { value: 'angular', label: 'Angular' },
  { value: 'svelte', label: 'Svelte' },
  { value: 'solid', label: 'Solid.js' }
]

const getLangLabel = (value) => {
  return langOptions.find(o => o.value === value)?.label || '(未选择)'
}

const getFrameworkLabel = (value) => {
  return frameworkOptions.find(o => o.value === value)?.label || '(未选择)'
}
</script>

<style scoped>
.select-page {
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

.demo-row {
  display: flex;
  gap: 2rem;
  align-items: center;
}

.demo-column {
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
}

.demo-item {
  display: flex;
  align-items: center;
  gap: 1rem;
}

.demo-item label {
  min-width: 100px;
  font-weight: 500;
  color: var(--font-black);
}

.demo-value {
  padding: 0.5rem 1rem;
  background: #fff;
  border-radius: 6px;
  color: var(--font-gray);
  font-size: 0.875rem;
  border: 1px solid #e1e4e8;
}

.form-result {
  margin-top: 1rem;
  padding: 1rem;
  background: #fff;
  border-radius: 6px;
  border: 1px solid #e1e4e8;
}

.form-result h4 {
  font-size: 1rem;
  font-weight: 600;
  color: var(--font-black);
  margin-bottom: 0.75rem;
}

.form-result p {
  margin: 0.25rem 0;
  color: var(--font-gray);
  font-size: 0.875rem;
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
  margin-bottom: 3rem;
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

.tip-section {
  background: #f6f8fa;
  border-radius: 8px;
  padding: 1.5rem;
  border-left: 3px solid var(--font-blue);
}

.tip-section h3 {
  font-size: 1.1rem;
  font-weight: 600;
  color: var(--font-black);
  margin-bottom: 1rem;
}

.tip-section ul {
  margin: 0;
  padding-left: 1.5rem;
}

.tip-section li {
  color: var(--font-gray);
  line-height: 1.8;
  font-size: 0.95rem;
}
</style>
