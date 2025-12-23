<template>
  <div class="checkbox-demo">
    <div class="demo-header">
      <h1>Checkbox 多选框</h1>
      <p>在一组可选项中进行多项选择时使用。</p>
    </div>

    <div class="demo-section">
      <h2>基础用法</h2>
      <p>单独使用可以表示两种状态之间的切换。</p>
      <div class="demo-block">
        <ZCheckbox v-model="checked1">选项</ZCheckbox>
      </div>
      <div class="demo-code">
        <pre><code>&lt;ZCheckbox v-model="checked"&gt;选项&lt;/ZCheckbox&gt;</code></pre>
      </div>
    </div>

    <div class="demo-section">
      <h2>禁用状态</h2>
      <p>多选框不可用状态。</p>
      <div class="demo-block">
        <ZCheckbox v-model="checked2" disabled>备选项</ZCheckbox>
        <ZCheckbox v-model="checked3" disabled>选中且禁用</ZCheckbox>
      </div>
    </div>

    <div class="demo-section">
      <h2>多选框组</h2>
      <p>适用于多个勾选框绑定到同一个数组的情景。</p>
      <div class="demo-block">
        <ZCheckboxGroup v-model="checkList1">
          <ZCheckbox label="复选框 A">复选框 A</ZCheckbox>
          <ZCheckbox label="复选框 B">复选框 B</ZCheckbox>
          <ZCheckbox label="复选框 C">复选框 C</ZCheckbox>
          <ZCheckbox label="禁用" disabled>禁用</ZCheckbox>
          <ZCheckbox label="选中且禁用" disabled>选中且禁用</ZCheckbox>
        </ZCheckboxGroup>
      </div>
      <div class="demo-result">
        <p>当前选中：{{ checkList1 }}</p>
      </div>
    </div>

    <div class="demo-section">
      <h2>不确定状态</h2>
      <p>indeterminate 属性用以表示 checkbox 的不确定状态。</p>
      <div class="demo-block">
        <ZCheckbox
          v-model="checkAll"
          :indeterminate="isIndeterminate"
          @change="handleCheckAllChange"
        >
          全选
        </ZCheckbox>
        <div style="margin: 15px 0;"></div>
        <ZCheckboxGroup
          v-model="checkedCities"
          @change="handleCheckedCitiesChange"
        >
          <ZCheckbox v-for="city in cities" :key="city" :label="city">
            {{ city }}
          </ZCheckbox>
        </ZCheckboxGroup>
      </div>
    </div>

    <div class="demo-section">
      <h2>可选项目数量的限制</h2>
      <p>使用 min 和 max 属性能够限制可以被勾选的项目的数量。</p>
      <div class="demo-block">
        <ZCheckboxGroup v-model="checkedCities2" :min="1" :max="2">
          <ZCheckbox v-for="city in cities" :key="city" :label="city">
            {{ city }}
          </ZCheckbox>
        </ZCheckboxGroup>
      </div>
    </div>

    <div class="demo-section">
      <h2>按钮样式</h2>
      <p>按钮样式的多选组合。</p>
      <div class="demo-block">
        <ZCheckboxGroup v-model="checkList2">
          <ZCheckboxButton v-for="city in cities" :key="city" :label="city">
            {{ city }}
          </ZCheckboxButton>
        </ZCheckboxGroup>
      </div>
      <div style="margin: 20px 0;"></div>
      <div class="demo-block">
        <ZCheckboxGroup v-model="checkList3" size="medium">
          <ZCheckboxButton v-for="city in cities" :key="city" :label="city">
            {{ city }}
          </ZCheckboxButton>
        </ZCheckboxGroup>
      </div>
      <div style="margin: 20px 0;"></div>
      <div class="demo-block">
        <ZCheckboxGroup v-model="checkList4" size="small" disabled>
          <ZCheckboxButton v-for="city in cities" :key="city" :label="city">
            {{ city }}
          </ZCheckboxButton>
        </ZCheckboxGroup>
      </div>
    </div>

    <div class="demo-section">
      <h2>带有边框</h2>
      <p>设置 border 属性可以渲染为带有边框的多选框。</p>
      <div class="demo-block">
        <ZCheckbox v-model="checked4" border>备选项1</ZCheckbox>
        <ZCheckbox v-model="checked5" border>备选项2</ZCheckbox>
      </div>
      <div style="margin: 20px 0;"></div>
      <div class="demo-block">
        <ZCheckbox v-model="checked6" border size="medium">备选项1</ZCheckbox>
        <ZCheckbox v-model="checked7" border size="medium">备选项2</ZCheckbox>
      </div>
      <div style="margin: 20px 0;"></div>
      <div class="demo-block">
        <ZCheckboxGroup v-model="checkList5" size="small">
          <ZCheckbox label="备选项1" border>备选项1</ZCheckbox>
          <ZCheckbox label="备选项2" border disabled>备选项2</ZCheckbox>
        </ZCheckboxGroup>
      </div>
    </div>

    <!-- API 文档 -->
    <div class="api-section">
      <h2>API</h2>
      
      <h3>Checkbox Attributes</h3>
      <table class="api-table">
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
            <td>string / number / boolean</td>
            <td>—</td>
            <td>—</td>
          </tr>
          <tr>
            <td>label</td>
            <td>选中状态的值（只有在checkbox-group或者绑定对象类型为array时有效）</td>
            <td>string / number / boolean / object</td>
            <td>—</td>
            <td>—</td>
          </tr>
          <tr>
            <td>true-label</td>
            <td>选中时的值</td>
            <td>string / number</td>
            <td>—</td>
            <td>—</td>
          </tr>
          <tr>
            <td>false-label</td>
            <td>没有选中时的值</td>
            <td>string / number</td>
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
          <tr>
            <td>border</td>
            <td>是否显示边框</td>
            <td>boolean</td>
            <td>—</td>
            <td>false</td>
          </tr>
          <tr>
            <td>size</td>
            <td>Checkbox 的尺寸，仅在 border 为真时有效</td>
            <td>string</td>
            <td>medium / small / mini</td>
            <td>—</td>
          </tr>
          <tr>
            <td>name</td>
            <td>原生 name 属性</td>
            <td>string</td>
            <td>—</td>
            <td>—</td>
          </tr>
          <tr>
            <td>checked</td>
            <td>当前是否勾选</td>
            <td>boolean</td>
            <td>—</td>
            <td>false</td>
          </tr>
          <tr>
            <td>indeterminate</td>
            <td>设置 indeterminate 状态，只负责样式控制</td>
            <td>boolean</td>
            <td>—</td>
            <td>false</td>
          </tr>
        </tbody>
      </table>

      <h3>Checkbox Events</h3>
      <table class="api-table">
        <thead>
          <tr>
            <th>事件名称</th>
            <th>说明</th>
            <th>回调参数</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td>change</td>
            <td>当绑定值变化时触发的事件</td>
            <td>更新后的值</td>
          </tr>
        </tbody>
      </table>

      <h3>Checkbox-group Attributes</h3>
      <table class="api-table">
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
            <td>array</td>
            <td>—</td>
            <td>—</td>
          </tr>
          <tr>
            <td>size</td>
            <td>多选框组尺寸，仅对按钮形式的 Checkbox 或带有边框的 Checkbox 有效</td>
            <td>string</td>
            <td>medium / small / mini</td>
            <td>—</td>
          </tr>
          <tr>
            <td>disabled</td>
            <td>是否禁用</td>
            <td>boolean</td>
            <td>—</td>
            <td>false</td>
          </tr>
          <tr>
            <td>min</td>
            <td>可被勾选的 checkbox 的最小数量</td>
            <td>number</td>
            <td>—</td>
            <td>—</td>
          </tr>
          <tr>
            <td>max</td>
            <td>可被勾选的 checkbox 的最大数量</td>
            <td>number</td>
            <td>—</td>
            <td>—</td>
          </tr>
          <tr>
            <td>text-color</td>
            <td>按钮形式的 Checkbox 激活时的文本颜色</td>
            <td>string</td>
            <td>—</td>
            <td>#ffffff</td>
          </tr>
          <tr>
            <td>fill</td>
            <td>按钮形式的 Checkbox 激活时的填充色和边框色</td>
            <td>string</td>
            <td>—</td>
            <td>#409EFF</td>
          </tr>
        </tbody>
      </table>

      <h3>Checkbox-group Events</h3>
      <table class="api-table">
        <thead>
          <tr>
            <th>事件名称</th>
            <th>说明</th>
            <th>回调参数</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td>change</td>
            <td>当绑定值变化时触发的事件</td>
            <td>更新后的值</td>
          </tr>
        </tbody>
      </table>

      <h3>Checkbox-button Attributes</h3>
      <table class="api-table">
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
            <td>label</td>
            <td>选中状态的值（只有在checkbox-group或者绑定对象类型为array时有效）</td>
            <td>string / number / boolean / object</td>
            <td>—</td>
            <td>—</td>
          </tr>
          <tr>
            <td>true-label</td>
            <td>选中时的值</td>
            <td>string / number</td>
            <td>—</td>
            <td>—</td>
          </tr>
          <tr>
            <td>false-label</td>
            <td>没有选中时的值</td>
            <td>string / number</td>
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
          <tr>
            <td>name</td>
            <td>原生 name 属性</td>
            <td>string</td>
            <td>—</td>
            <td>—</td>
          </tr>
          <tr>
            <td>checked</td>
            <td>当前是否勾选</td>
            <td>boolean</td>
            <td>—</td>
            <td>false</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import ZCheckbox from '@/components/z-ui/checkbox/zCheckbox/zCheckbox.vue'
import ZCheckboxGroup from '@/components/z-ui/checkbox/zCheckboxGroup/zCheckboxGroup.vue'
import ZCheckboxButton from '@/components/z-ui/checkbox/zCheckboxButton/zCheckboxButton.vue'
import type { CheckboxModelValue } from '@/components/z-ui/checkbox/types'

// 基础用法
const checked1 = ref(true)

// 禁用状态
const checked2 = ref(false)
const checked3 = ref(true)

// 多选框组
const checkList1 = ref(['选中且禁用', '复选框 A'])

// 不确定状态
const checkAll = ref(false)
const checkedCities = ref(['上海', '北京'])
const cities = ['上海', '北京', '广州', '深圳']
const isIndeterminate = ref(true)

const handleCheckAllChange = (value: any) => {
  // 确保value被视为布尔值
  const checked = !!value
  checkedCities.value = checked ? cities : []
  isIndeterminate.value = false
}

const handleCheckedCitiesChange = (value: CheckboxModelValue) => {
  const checkedCount = Array.isArray(value) ? value.length : 0
  checkAll.value = checkedCount === cities.length
  isIndeterminate.value = checkedCount > 0 && checkedCount < cities.length
}

// 数量限制
const checkedCities2 = ref(['上海', '北京'])

// 按钮样式
const checkList2 = ref(['上海'])
const checkList3 = ref(['上海'])
const checkList4 = ref(['上海'])

// 带有边框
const checked4 = ref(true)
const checked5 = ref(false)
const checked6 = ref(false)
const checked7 = ref(true)
const checkList5 = ref([])
</script>

<style scoped>
.checkbox-demo {
  padding: 20px;
  max-width: 1200px;
  margin: 0 auto;
}

.demo-header {
  margin-bottom: 40px;
  padding-bottom: 20px;
  border-bottom: 1px solid #ebeef5;
}

.demo-header h1 {
  margin: 0 0 10px 0;
  font-size: 28px;
  font-weight: 400;
  color: #1f2328;
}

.demo-header p {
  margin: 0;
  font-size: 14px;
  color: #646a73;
}

.demo-section {
  margin-bottom: 40px;
}

.demo-section h2 {
  margin: 0 0 20px 0;
  font-size: 20px;
  font-weight: 500;
  color: #1f2328;
}

.demo-section p {
  margin: 0 0 15px 0;
  font-size: 14px;
  color: #646a73;
  line-height: 1.5;
}

.demo-block {
  padding: 20px;
  border: 1px solid #ebebeb;
  border-radius: 3px;
  margin-bottom: 20px;
}

.demo-block .z-checkbox {
  margin-right: 30px;
}

.demo-block .z-checkbox-group .z-checkbox {
  margin-right: 20px;
  margin-bottom: 10px;
}

.demo-block .z-checkbox-button {
  margin-right: 10px;
  margin-bottom: 10px;
}

.demo-code {
  background-color: #f8f9fa;
  border: 1px solid #e9ecef;
  border-radius: 4px;
  padding: 15px;
  margin-bottom: 20px;
}

.demo-code pre {
  margin: 0;
  font-family: 'Monaco', 'Menlo', 'Ubuntu Mono', monospace;
  font-size: 13px;
  line-height: 1.4;
}

.demo-code code {
  color: #e83e8c;
  background: transparent;
}

.demo-result {
  padding: 15px;
  background-color: #f8f9fa;
  border-radius: 4px;
  margin-top: 15px;
}

.demo-result p {
  margin: 0;
  font-size: 14px;
  color: #495057;
}

.api-section {
  margin-top: 60px;
  padding-top: 40px;
  border-top: 1px solid #ebeef5;
}

.api-section h2 {
  margin: 0 0 30px 0;
  font-size: 24px;
  font-weight: 500;
  color: #1f2328;
}

.api-section h3 {
  margin: 30px 0 15px 0;
  font-size: 18px;
  font-weight: 500;
  color: #1f2328;
}

.api-table {
  width: 100%;
  border-collapse: collapse;
  margin-bottom: 30px;
  font-size: 14px;
}

.api-table th,
.api-table td {
  border: 1px solid #dcdfe6;
  padding: 12px 8px;
  text-align: left;
}

.api-table th {
  background-color: #f5f7fa;
  font-weight: 600;
  color: #909399;
}

.api-table td {
  color: #606266;
}

.api-table tr:nth-child(even) {
  background-color: #fafafa;
}

.api-table code {
  background-color: #f2f4f5;
  padding: 2px 4px;
  border-radius: 3px;
  font-size: 12px;
  color: #5e6d82;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .checkbox-demo {
    padding: 15px;
  }
  
  .demo-header h1 {
    font-size: 24px;
  }
  
  .demo-section h2 {
    font-size: 18px;
  }
  
  .demo-block {
    padding: 15px;
  }
  
  .demo-block .z-checkbox {
    margin-right: 15px;
    margin-bottom: 10px;
  }
  
  .api-table {
    font-size: 12px;
  }
  
  .api-table th,
  .api-table td {
    padding: 8px 6px;
  }
}

/* 高对比度模式支持 */
@media (prefers-contrast: high) {
  .demo-header {
    border-bottom-color: #000;
  }
  
  .demo-block {
    border-color: #000;
  }
  
  .api-table th,
  .api-table td {
    border-color: #000;
  }
}

/* 深色主题支持 */
@media (prefers-color-scheme: dark) {
  .checkbox-demo {
    background-color: #1a1a1a;
    color: #e0e0e0;
  }
  
  .demo-header {
    border-bottom-color: #404040;
  }
  
  .demo-header h1 {
    color: #ffffff;
  }
  
  .demo-header p {
    color: #b0b0b0;
  }
  
  .demo-section h2 {
    color: #ffffff;
  }
  
  .demo-section p {
    color: #b0b0b0;
  }
  
  .demo-block {
    background-color: #2a2a2a;
    border-color: #404040;
  }
  
  .demo-code {
    background-color: #2a2a2a;
    border-color: #404040;
  }
  
  .demo-result {
    background-color: #2a2a2a;
  }
  
  .api-section {
    border-top-color: #404040;
  }
  
  .api-section h2,
  .api-section h3 {
    color: #ffffff;
  }
  
  .api-table th {
    background-color: #3a3a3a;
    color: #e0e0e0;
  }
  
  .api-table th,
  .api-table td {
    border-color: #404040;
  }
  
  .api-table td {
    color: #d0d0d0;
  }
  
  .api-table tr:nth-child(even) {
    background-color: #2a2a2a;
  }
  
  .api-table code {
    background-color: #3a3a3a;
    color: #e0e0e0;
  }
}
</style>