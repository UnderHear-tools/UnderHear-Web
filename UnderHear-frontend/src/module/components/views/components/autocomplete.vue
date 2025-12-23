<template>
  <div class="autocomplete-demo">
    <div class="demo-header">
      <h1>Autocomplete 自动完成</h1>
      <p>输入框自动完成功能。</p>
    </div>

    <div class="demo-section">
      <h2>基础用法</h2>
      <div class="demo-item">
        <ZAutocomplete
          v-model="basicValue"
          :options="basicOptions"
          placeholder="请输入内容"
          style="width: 300px;"
        />
        <p class="demo-description">当前值: {{ basicValue }}</p>
      </div>
    </div>

    <div class="demo-section">
      <h2>自定义筛选</h2>
      <div class="demo-item">
        <ZAutocomplete
          v-model="filterValue"
          :options="filterOptions"
          :filter-method="customFilter"
          placeholder="自定义筛选逻辑"
          style="width: 300px;"
        />
        <p class="demo-description">支持模糊匹配和拼音搜索</p>
      </div>
    </div>

    <div class="demo-section">
      <h2>远程搜索</h2>
      <div class="demo-item">
        <ZAutocomplete
          v-model="remoteValue"
          :options="remoteOptions"
          :loading="remoteLoading"
          :remote="true"
          @search="handleRemoteSearch"
          placeholder="远程搜索"
          style="width: 300px;"
        />
        <p class="demo-description">输入关键词进行远程搜索</p>
      </div>
    </div>

    <div class="demo-section">
      <h2>自定义模板</h2>
      <div class="demo-item">
        <ZAutocomplete
          v-model="templateValue"
          :options="templateOptions"
          placeholder="自定义选项模板"
          style="width: 400px;"
        >
          <template #option="{ option }">
            <div class="custom-option">
              <div class="option-avatar">
                {{ typeof option === 'object' ? option.name.charAt(0) : option.charAt(0) }}
              </div>
              <div class="option-content">
                <div class="option-name">{{ typeof option === 'object' ? option.name : option }}</div>
                <div class="option-email">{{ typeof option === 'object' ? option.email : '' }}</div>
              </div>
            </div>
          </template>
        </ZAutocomplete>
      </div>
    </div>

    <div class="demo-section">
      <h2>不同尺寸</h2>
      <div class="demo-item">
        <div class="size-group">
          <ZAutocomplete
            v-model="sizeValue1"
            :options="basicOptions"
            size="small"
            placeholder="小尺寸"
            style="width: 200px;"
          />
          <ZAutocomplete
            v-model="sizeValue2"
            :options="basicOptions"
            size="medium"
            placeholder="中等尺寸"
            style="width: 250px;"
          />
          <ZAutocomplete
            v-model="sizeValue3"
            :options="basicOptions"
            size="large"
            placeholder="大尺寸"
            style="width: 300px;"
          />
        </div>
      </div>
    </div>

    <div class="demo-section">
      <h2>禁用状态</h2>
      <div class="demo-item">
        <ZAutocomplete
          v-model="disabledValue"
          :options="basicOptions"
          disabled
          placeholder="禁用状态"
          style="width: 300px;"
        />
      </div>
    </div>

    <div class="demo-section">
      <h2>清空功能</h2>
      <div class="demo-item">
        <ZAutocomplete
          v-model="clearableValue"
          :options="basicOptions"
          clearable
          placeholder="可清空"
          style="width: 300px;"
        />
      </div>
    </div>

    <!-- API 说明 -->
    <div class="api-section">
      <h2>API</h2>
      
      <h3>Autocomplete Props</h3>
      <table class="api-table">
        <thead>
          <tr>
            <th>属性</th>
            <th>说明</th>
            <th>类型</th>
            <th>默认值</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td>modelValue</td>
            <td>绑定值</td>
            <td>string</td>
            <td>-</td>
          </tr>
          <tr>
            <td>options</td>
            <td>选项数据</td>
            <td>Array&lt;string | AutocompleteOption&gt;</td>
            <td>[]</td>
          </tr>
          <tr>
            <td>placeholder</td>
            <td>占位文本</td>
            <td>string</td>
            <td>-</td>
          </tr>
          <tr>
            <td>size</td>
            <td>尺寸</td>
            <td>'small' | 'medium' | 'large'</td>
            <td>'medium'</td>
          </tr>
          <tr>
            <td>disabled</td>
            <td>是否禁用</td>
            <td>boolean</td>
            <td>false</td>
          </tr>
          <tr>
            <td>clearable</td>
            <td>是否可清空</td>
            <td>boolean</td>
            <td>false</td>
          </tr>
          <tr>
            <td>loading</td>
            <td>是否加载中</td>
            <td>boolean</td>
            <td>false</td>
          </tr>
          <tr>
            <td>remote</td>
            <td>是否远程搜索</td>
            <td>boolean</td>
            <td>false</td>
          </tr>
          <tr>
            <td>filterMethod</td>
            <td>自定义筛选方法</td>
            <td>Function</td>
            <td>-</td>
          </tr>
          <tr>
            <td>maxHeight</td>
            <td>下拉面板最大高度</td>
            <td>string | number</td>
            <td>'200px'</td>
          </tr>
        </tbody>
      </table>

      <h3>Autocomplete Events</h3>
      <table class="api-table">
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
            <td>值改变时触发</td>
            <td>(value: string)</td>
          </tr>
          <tr>
            <td>select</td>
            <td>选择选项时触发</td>
            <td>(option: AutocompleteOption)</td>
          </tr>
          <tr>
            <td>search</td>
            <td>搜索时触发</td>
            <td>(query: string)</td>
          </tr>
          <tr>
            <td>focus</td>
            <td>获得焦点时触发</td>
            <td>(event: FocusEvent)</td>
          </tr>
          <tr>
            <td>blur</td>
            <td>失去焦点时触发</td>
            <td>(event: FocusEvent)</td>
          </tr>
          <tr>
            <td>clear</td>
            <td>清空时触发</td>
            <td>-</td>
          </tr>
        </tbody>
      </table>

      <h3>Autocomplete Slots</h3>
      <table class="api-table">
        <thead>
          <tr>
            <th>插槽名</th>
            <th>说明</th>
            <th>参数</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td>option</td>
            <td>自定义选项内容</td>
            <td>{ option, index }</td>
          </tr>
          <tr>
            <td>prefix</td>
            <td>输入框前缀</td>
            <td>-</td>
          </tr>
          <tr>
            <td>suffix</td>
            <td>输入框后缀</td>
            <td>-</td>
          </tr>
          <tr>
            <td>empty</td>
            <td>无数据时的内容</td>
            <td>-</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import ZAutocomplete from '@/components/z-ui/autocomplete/zAutocomplete/zAutocomplete.vue'

// 基础用法
const basicValue = ref('')
const basicOptions = [
  'Apple',
  'Banana',
  'Cherry',
  'Date',
  'Elderberry',
  'Fig',
  'Grape',
  'Honeydew'
]

// 自定义筛选
const filterValue = ref('')
const filterOptions = [
  'JavaScript',
  'TypeScript',
  'Vue.js',
  'React',
  'Angular',
  'Node.js',
  'Python',
  'Java'
]

const customFilter = (query: string, option: any) => {
  // 确保option被转换为字符串进行比较
  const optionStr = typeof option === 'string' ? option : option.label || option.value || String(option)
  return optionStr.toLowerCase().includes(query.toLowerCase())
}

// 远程搜索
const remoteValue = ref('')
const remoteOptions = ref<string[]>([])
const remoteLoading = ref(false)

const handleRemoteSearch = async (query: string) => {
  if (!query) {
    remoteOptions.value = []
    return
  }
  
  remoteLoading.value = true
  
  // 模拟远程搜索
  setTimeout(() => {
    const mockData = [
      'GitHub',
      'GitLab',
      'Bitbucket',
      'SourceForge',
      'CodePen',
      'JSFiddle',
      'Stack Overflow',
      'MDN Web Docs'
    ]
    
    remoteOptions.value = mockData.filter(item => 
      item.toLowerCase().includes(query.toLowerCase())
    )
    remoteLoading.value = false
  }, 500)
}

// 自定义模板
const templateValue = ref('')
const templateOptions = [
  { name: 'John Doe', email: 'john@example.com' },
  { name: 'Jane Smith', email: 'jane@example.com' },
  { name: 'Bob Johnson', email: 'bob@example.com' },
  { name: 'Alice Brown', email: 'alice@example.com' },
  { name: 'Charlie Wilson', email: 'charlie@example.com' }
]

// 不同尺寸
const sizeValue1 = ref('')
const sizeValue2 = ref('')
const sizeValue3 = ref('')

// 禁用状态
const disabledValue = ref('禁用状态')

// 清空功能
const clearableValue = ref('')
</script>

<style scoped>
.autocomplete-demo {
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
  margin: 0 0 8px 0;
}

.demo-header p {
  font-size: 16px;
  color: #6b7280;
  margin: 0;
}

.demo-section {
  margin-bottom: 32px;
  padding: 24px;
  background: #ffffff;
  border-radius: 8px;
  border: 1px solid #e5e7eb;
}

.demo-section h2 {
  font-size: 20px;
  font-weight: 600;
  color: #1f2937;
  margin: 0 0 16px 0;
}

.demo-item {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.demo-description {
  font-size: 14px;
  color: #6b7280;
  margin: 0;
}

.size-group {
  display: flex;
  gap: 16px;
  align-items: center;
  flex-wrap: wrap;
}

.custom-option {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 8px 0;
}

.option-avatar {
  width: 32px;
  height: 32px;
  border-radius: 50%;
  background: #3b82f6;
  color: white;
  display: flex;
  align-items: center;
  justify-content: center;
  font-weight: 600;
  font-size: 14px;
}

.option-content {
  flex: 1;
}

.option-name {
  font-weight: 500;
  color: #1f2937;
  font-size: 14px;
}

.option-email {
  font-size: 12px;
  color: #6b7280;
  margin-top: 2px;
}

.api-section {
  margin-top: 48px;
  padding: 24px;
  background: #ffffff;
  border-radius: 8px;
  border: 1px solid #e5e7eb;
}

.api-section h2 {
  font-size: 24px;
  font-weight: 600;
  color: #1f2937;
  margin: 0 0 24px 0;
}

.api-section h3 {
  font-size: 18px;
  font-weight: 600;
  color: #374151;
  margin: 24px 0 16px 0;
}

.api-section h3:first-of-type {
  margin-top: 0;
}

.api-table {
  width: 100%;
  border-collapse: collapse;
  margin-bottom: 24px;
}

.api-table th,
.api-table td {
  padding: 12px;
  text-align: left;
  border-bottom: 1px solid #e5e7eb;
}

.api-table th {
  background: #f9fafb;
  font-weight: 600;
  color: #374151;
}

.api-table td {
  color: #4b5563;
}

.api-table code {
  background: #f3f4f6;
  padding: 2px 6px;
  border-radius: 4px;
  font-family: 'Monaco', 'Menlo', monospace;
  font-size: 13px;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .autocomplete-demo {
    padding: 16px;
  }
  
  .demo-section {
    padding: 16px;
  }
  
  .size-group {
    flex-direction: column;
    align-items: stretch;
  }
  
  .size-group > * {
    width: 100% !important;
  }
  
  .api-table {
    font-size: 14px;
  }
  
  .api-table th,
  .api-table td {
    padding: 8px;
  }
}

/* 高对比度模式支持 */
@media (prefers-contrast: high) {
  .demo-section {
    border-color: #000000;
  }
  
  .option-avatar {
    border: 2px solid #000000;
  }
}
</style>