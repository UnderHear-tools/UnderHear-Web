<template>
  <ComponentDocsPage>
    <ComponentDocsHeader
      title="Select 选择器"
      description="下拉选择组件，支持键盘导航与搜索。"
    />

    <ComponentDocsSection title="基础用法">
      <template #description>
        通过 <code>v-model</code> 绑定选中值，<code>options</code> 传入选项数组。
      </template>
      <ComponentDocsDemoBlock :code="demo1Code">
        <zSelect
          v-model="val1"
          :options="fruitOptions"
          placeholder="请选择水果"
        />
        <p class="demo-info">
          当前值：{{ val1 || '未选择' }}
        </p>
      </ComponentDocsDemoBlock>
    </ComponentDocsSection>

    <ComponentDocsSection title="禁用状态">
      <template #description>
        设置 <code>disabled</code> 属性禁用选择器。
      </template>
      <ComponentDocsDemoBlock :code="demo2Code">
        <zSelect
          v-model="val2"
          :options="fruitOptions"
          placeholder="禁用状态"
          disabled
        />
      </ComponentDocsDemoBlock>
    </ComponentDocsSection>

    <ComponentDocsSection title="多个选择器">
      <template #description>
        多个 <code>zSelect</code> 并列时，展开一个会自动关闭其他已展开的实例。
      </template>
      <ComponentDocsDemoBlock :code="demo3Code">
        <div class="demo-row">
          <zSelect
            v-model="val3a"
            :options="fruitOptions"
            placeholder="选择水果"
          />
          <zSelect
            v-model="val3b"
            :options="colorOptions"
            placeholder="选择颜色"
          />
          <zSelect
            v-model="val3c"
            :options="sizeOptions"
            placeholder="选择尺寸"
          />
        </div>
      </ComponentDocsDemoBlock>
    </ComponentDocsSection>

    <ComponentDocsSection title="键盘导航">
      <template #description>
        聚焦后支持键盘操作：<code>↑</code> <code>↓</code> 移动高亮、<code>Enter</code> / <code>Space</code> 选中、<code>Esc</code> 关闭。
      </template>
      <ComponentDocsDemoBlock :code="demo4Code">
        <zSelect
          v-model="val4"
          :options="fruitOptions"
          placeholder="试试键盘操作"
        />
      </ComponentDocsDemoBlock>
    </ComponentDocsSection>

    <ComponentDocsSection
      title="API"
      variant="api"
    >
      <h3>属性</h3>
      <zTable
        :columns="apiCols"
        :data="apiRows"
        row-key="name"
        compact
        :hoverable="false"
      />
      <h3>Option</h3>
      <zTable
        :columns="apiCols"
        :data="optionRows"
        row-key="name"
        compact
        :hoverable="false"
      />
      <h3>事件</h3>
      <zTable
        :columns="eventCols"
        :data="eventRows"
        row-key="name"
        compact
        :hoverable="false"
      />
    </ComponentDocsSection>
  </ComponentDocsPage>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { zSelect } from '@/components/z-ui/select'
import { zTable, type ZTableColumn } from '@/components/z-ui/table'
import ComponentDocsDemoBlock from '@/modules/components/components/ComponentDocsPage/ComponentDocsDemoBlock.vue'
import ComponentDocsHeader from '@/modules/components/components/ComponentDocsPage/ComponentDocsHeader.vue'
import ComponentDocsPage from '@/modules/components/components/ComponentDocsPage/ComponentDocsPage.vue'
import ComponentDocsSection from '@/modules/components/components/ComponentDocsPage/ComponentDocsSection.vue'

const val1 = ref('')
const val2 = ref('apple')
const val3a = ref('')
const val3b = ref('')
const val3c = ref('')
const val4 = ref('')

const fruitOptions = [
  { value: 'apple', label: '苹果' },
  { value: 'banana', label: '香蕉' },
  { value: 'cherry', label: '樱桃' },
  { value: 'grape', label: '葡萄' },
  { value: 'mango', label: '芒果' }
]

const colorOptions = [
  { value: 'red', label: '红色' },
  { value: 'blue', label: '蓝色' },
  { value: 'green', label: '绿色' }
]

const sizeOptions = [
  { value: 'sm', label: '小号' },
  { value: 'md', label: '中号' },
  { value: 'lg', label: '大号' }
]

const demo1Code = `<template>
  <zSelect v-model="val" :options="options" placeholder="请选择水果" />
  <p>当前值：{{ val || '未选择' }}</p>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { zSelect } from '@/components/z-ui/select'

const val = ref('')
const options = [
  { value: 'apple', label: '苹果' },
  { value: 'banana', label: '香蕉' },
  { value: 'cherry', label: '樱桃' },
  { value: 'grape', label: '葡萄' },
  { value: 'mango', label: '芒果' }
]
<\/script>`

const demo2Code = `<template>
  <zSelect v-model="val" :options="options" placeholder="禁用状态" disabled />
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { zSelect } from '@/components/z-ui/select'

const val = ref('apple')
const options = [
  { value: 'apple', label: '苹果' },
  { value: 'banana', label: '香蕉' }
]
<\/script>`

const demo3Code = `<template>
  <div style="display: flex; gap: 12px; flex-wrap: wrap;">
    <zSelect v-model="a" :options="fruits" placeholder="选择水果" />
    <zSelect v-model="b" :options="colors" placeholder="选择颜色" />
    <zSelect v-model="c" :options="sizes" placeholder="选择尺寸" />
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { zSelect } from '@/components/z-ui/select'

const a = ref('')
const b = ref('')
const c = ref('')

const fruits = [
  { value: 'apple', label: '苹果' },
  { value: 'banana', label: '香蕉' }
]
const colors = [
  { value: 'red', label: '红色' },
  { value: 'blue', label: '蓝色' }
]
const sizes = [
  { value: 'sm', label: '小号' },
  { value: 'lg', label: '大号' }
]
<\/script>`

const demo4Code = `<!-- 聚焦后使用 ↑ ↓ Enter Space Esc 进行键盘操作 -->
<template>
  <zSelect v-model="val" :options="options" placeholder="试试键盘操作" />
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { zSelect } from '@/components/z-ui/select'

const val = ref('')
const options = [
  { value: 'apple', label: '苹果' },
  { value: 'banana', label: '香蕉' },
  { value: 'cherry', label: '樱桃' }
]
<\/script>`

const apiCols: ZTableColumn[] = [
  { key: 'name', label: '属性名', rowHeader: true, minWidth: '140px' },
  { key: 'default', label: '默认值', minWidth: '100px' },
  { key: 'type', label: '类型', minWidth: '200px', wrap: true },
  { key: 'description', label: '说明', minWidth: '200px', wrap: true }
]

const apiRows = [
  { name: 'modelValue', description: '绑定值（v-model）', type: 'string', default: '—' },
  { name: 'options', description: '选项数组', type: 'Option[]', default: '—' },
  { name: 'placeholder', description: '未选时的占位文字', type: 'string', default: '—' },
  { name: 'disabled', description: '是否禁用', type: 'boolean', default: 'false' }
]

const optionRows = [
  { name: 'value', description: '选项值', type: 'string', default: '—' },
  { name: 'label', description: '选项显示文本', type: 'string', default: '—' }
]

const eventCols: ZTableColumn[] = [
  { key: 'name', label: '事件名', rowHeader: true, minWidth: '180px' },
  { key: 'description', label: '说明', minWidth: '200px', wrap: true },
  { key: 'type', label: '回调参数', minWidth: '200px', wrap: true }
]

const eventRows = [
  { name: 'update:modelValue', description: '选中值变化时触发', type: 'string' },
  { name: 'open', description: '下拉面板展开时触发', type: '—' },
  { name: 'close', description: '下拉面板关闭时触发', type: '—' }
]
</script>

<style scoped>
.demo-info {
  margin-top: 8px;
  font-size: 0.85rem;
  color: var(--fgColor-muted);
}

.demo-row {
  display: flex;
  gap: 12px;
  flex-wrap: wrap;
}
</style>
