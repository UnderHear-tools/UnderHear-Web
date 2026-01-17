<template>
  <div class="input-page">
    <div class="page-header">
      <h1 class="page-title">Input 输入框</h1>
      <p class="page-description">通过鼠标或键盘输入内容,是最基础的表单组件。支持文本输入框和多行文本域。</p>
    </div>

    <div class="demo-section">
      <h2 class="section-title">基础用法</h2>
      <div class="demo-container">
        <div class="demo-row">
          <zInput v-model="basicInput" placeholder="请输入内容" style="width: 300px;" />
          <span class="demo-value">输入值: {{ basicInput }}</span>
        </div>
      </div>
      <div class="code-example">
        <pre><code>&lt;zInput v-model="value" placeholder="请输入内容" /&gt;</code></pre>
      </div>
    </div>

    <div class="demo-section">
      <h2 class="section-title">多行文本域</h2>
      <div class="demo-container">
        <div class="demo-column">
          <zTextarea 
            v-model="textareaValue" 
            placeholder="请输入多行内容" 
            rows="4"
            style="width: 100%; max-width: 500px;"
          />
          <div class="demo-value" style="margin-top: 1rem;">
            输入值: {{ textareaValue || '(空)' }}
          </div>
        </div>
      </div>
      <div class="code-example">
        <pre><code>&lt;zTextarea 
  v-model="value" 
  placeholder="请输入多行内容" 
  rows="4"
/&gt;</code></pre>
      </div>
    </div>

    <div class="demo-section">
      <h2 class="section-title">表单示例</h2>
      <div class="demo-container">
        <div class="form-demo">
          <div class="form-item">
            <label>用户名:</label>
            <zInput v-model="formData.username" placeholder="请输入用户名" />
          </div>
          <div class="form-item">
            <label>邮箱:</label>
            <zInput v-model="formData.email" placeholder="请输入邮箱地址" />
          </div>
          <div class="form-item">
            <label>个人简介:</label>
            <zTextarea 
              v-model="formData.bio" 
              placeholder="请输入个人简介" 
              rows="3"
            />
          </div>
          <div class="form-result">
            <h4>表单数据:</h4>
            <pre>{{ JSON.stringify(formData, null, 2) }}</pre>
          </div>
        </div>
      </div>
      <div class="code-example">
        <pre><code>&lt;div class="form-item"&gt;
  &lt;label&gt;用户名:&lt;/label&gt;
  &lt;zInput v-model="formData.username" placeholder="请输入用户名" /&gt;
&lt;/div&gt;
&lt;div class="form-item"&gt;
  &lt;label&gt;邮箱:&lt;/label&gt;
  &lt;zInput v-model="formData.email" placeholder="请输入邮箱地址" /&gt;
&lt;/div&gt;
&lt;div class="form-item"&gt;
  &lt;label&gt;个人简介:&lt;/label&gt;
  &lt;zTextarea v-model="formData.bio" placeholder="请输入个人简介" rows="3" /&gt;
&lt;/div&gt;</code></pre>
      </div>
    </div>

    <div class="api-section">
      <h2 class="section-title">Input API</h2>
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
              <td>placeholder</td>
              <td>输入框占位文本</td>
              <td>string</td>
              <td>—</td>
              <td>—</td>
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
      <h2 class="section-title">Textarea API</h2>
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
              <td>placeholder</td>
              <td>输入框占位文本</td>
              <td>string</td>
              <td>—</td>
              <td>—</td>
            </tr>
            <tr>
              <td>rows</td>
              <td>输入框行数</td>
              <td>number</td>
              <td>—</td>
              <td>3</td>
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
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { zInput } from '@/components/z-ui/input/zInput'
import { zTextarea } from '@/components/z-ui/input/zTextarea'

const basicInput = ref('')
const textareaValue = ref('')
const formData = ref({
  username: '',
  email: '',
  bio: ''
})
</script>

<style scoped>
.input-page {
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
  gap: 1.5rem;
  align-items: center;
}

.demo-column {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.demo-value {
  color: var(--font-gray);
  font-size: 0.875rem;
}

.form-demo {
  max-width: 500px;
  margin: 0 auto;
}

.form-item {
  margin-bottom: 1.5rem;
}

.form-item label {
  display: block;
  margin-bottom: 0.5rem;
  font-weight: 500;
  color: var(--font-black);
}

.form-result {
  margin-top: 2rem;
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

.form-result pre {
  font-family: 'Consolas', 'Monaco', 'Courier New', monospace;
  font-size: 0.875rem;
  color: var(--font-gray);
  line-height: 1.6;
  margin: 0;
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
}

.api-table {
  overflow-x: auto;
  margin-bottom: 2rem;
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
</style>
