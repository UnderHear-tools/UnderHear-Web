<template>
  <ComponentDocsPage>
    <ComponentDocsHeader
      title="Input 输入框"
      description="基础文本输入组件，支持 v-model 与原生 input 属性透传。"
    />

    <ComponentDocsSection title="基础用法">
      <template #description>
        通过 <code>v-model</code> 双向绑定输入值。
      </template>
      <ComponentDocsDemoBlock :code="demo1Code">
        <zInput
          v-model="val1"
          placeholder="请输入内容"
        />
        <p class="demo-info">
          当前值：{{ val1 || '未输入' }}
        </p>
      </ComponentDocsDemoBlock>
    </ComponentDocsSection>

    <ComponentDocsSection title="禁用状态">
      <template #description>
        设置原生属性 <code>disabled</code> 后输入框不可编辑。
      </template>
      <ComponentDocsDemoBlock :code="demo2Code">
        <zInput
          v-model="val2"
          disabled
        />
      </ComponentDocsDemoBlock>
    </ComponentDocsSection>

    <ComponentDocsSection title="输入类型">
      <template #description>
        可通过透传原生属性设置 <code>type</code>、<code>autocomplete</code> 等参数。
      </template>
      <ComponentDocsDemoBlock :code="demo3Code">
        <div class="demo-row">
          <zInput
            v-model="email"
            type="email"
            placeholder="you@example.com"
            autocomplete="email"
          />
          <zInput
            v-model="password"
            type="password"
            placeholder="请输入密码"
            autocomplete="current-password"
          />
        </div>
      </ComponentDocsDemoBlock>
    </ComponentDocsSection>

    <ComponentDocsSection title="结合 FormControl">
      <template #description>
        与 <code>FormControl</code> 组合，可快速添加标签、校验信息和辅助文案。
      </template>
      <ComponentDocsDemoBlock :code="demo4Code">
        <FormControl>
          <FormControl.Label>
            Name <span class="required-mark">*</span>
          </FormControl.Label>
          <zInput v-model="profileName" />
          <FormControl.Validation
            v-if="hasInvalidChars"
            variant="error"
          >
            Names may not contain symbols
          </FormControl.Validation>
          <FormControl.Caption>
            This will be publicly visible
          </FormControl.Caption>
        </FormControl>
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
      <h3>原生属性透传（常用）</h3>
      <zTable
        :columns="apiCols"
        :data="nativeRows"
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
import { computed, ref } from 'vue'
import { FormControl } from '@/components/z-ui/form-control'
import { zInput } from '@/components/z-ui/input'
import { zTable, type ZTableColumn } from '@/components/z-ui/table'
import ComponentDocsDemoBlock from '@/modules/components/components/ComponentDocsPage/ComponentDocsDemoBlock.vue'
import ComponentDocsHeader from '@/modules/components/components/ComponentDocsPage/ComponentDocsHeader.vue'
import ComponentDocsPage from '@/modules/components/components/ComponentDocsPage/ComponentDocsPage.vue'
import ComponentDocsSection from '@/modules/components/components/ComponentDocsPage/ComponentDocsSection.vue'

const val1 = ref('')
const val2 = ref('Disabled value')
const email = ref('')
const password = ref('')
const profileName = ref('Mona L!$a')

const hasInvalidChars = computed(() => /[^a-zA-Z\s]/.test(profileName.value))

const demo1Code = `<template>
  <zInput v-model="val" placeholder="请输入内容" />
  <p>当前值：{{ val || '未输入' }}</p>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { zInput } from '@/components/z-ui/input'

const val = ref('')
<\/script>`

const demo2Code = `<template>
  <zInput v-model="val" disabled />
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { zInput } from '@/components/z-ui/input'

const val = ref('Disabled value')
<\/script>`

const demo3Code = `<template>
  <div style="display: flex; gap: 12px; flex-wrap: wrap;">
    <zInput v-model="email" type="email" placeholder="you@example.com" autocomplete="email" />
    <zInput v-model="password" type="password" placeholder="请输入密码" autocomplete="current-password" />
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { zInput } from '@/components/z-ui/input'

const email = ref('')
const password = ref('')
<\/script>`

const demo4Code = `<template>
  <FormControl>
    <FormControl.Label>Name <span>*</span></FormControl.Label>
    <zInput v-model="name" />
    <FormControl.Validation v-if="hasInvalidChars" variant="error">
      Names may not contain symbols
    </FormControl.Validation>
    <FormControl.Caption>This will be publicly visible</FormControl.Caption>
  </FormControl>
</template>

<script setup lang="ts">
import { computed, ref } from 'vue'
import { FormControl } from '@/components/z-ui/form-control'
import { zInput } from '@/components/z-ui/input'

const name = ref('Mona L!$a')
const hasInvalidChars = computed(() => /[^a-zA-Z\\s]/.test(name.value))
<\/script>`

const apiCols: ZTableColumn[] = [
  { key: 'name', label: '属性名', rowHeader: true, minWidth: '140px' },
  { key: 'default', label: '默认值', minWidth: '120px' },
  { key: 'type', label: '类型', minWidth: '220px', wrap: true },
  { key: 'description', label: '说明', minWidth: '220px', wrap: true }
]

const apiRows = [
  { name: 'modelValue', description: '绑定值（v-model）', type: 'string', default: "''" }
]

const nativeRows = [
  { name: 'type', description: '输入类型', type: 'string', default: "'text'" },
  { name: 'placeholder', description: '占位文本', type: 'string', default: '-' },
  { name: 'disabled', description: '禁用输入', type: 'boolean', default: 'false' },
  { name: 'maxlength', description: '最大输入长度', type: 'number | string', default: '-' },
  { name: 'autocomplete', description: '自动填充提示', type: 'string', default: '-' },
  { name: 'id / name / aria-*', description: '其他原生属性会透传到 input 元素', type: 'string', default: '-' }
]

const eventCols: ZTableColumn[] = [
  { key: 'name', label: '事件名', rowHeader: true, minWidth: '180px' },
  { key: 'description', label: '说明', minWidth: '240px', wrap: true },
  { key: 'type', label: '回调参数', minWidth: '200px', wrap: true }
]

const eventRows = [
  { name: 'update:modelValue', description: '输入值变化时触发（IME 组合输入在 compositionend 后更新）', type: 'string' }
]
</script>

<style scoped>
.demo-info {
  margin-top: 8px;
  font-size: 0.85rem;
  color: var(--fgColor-muted);
}

.demo-row {
  display: grid;
  grid-template-columns: repeat(2, minmax(0, 1fr));
  gap: 12px;
}

.required-mark {
  color: var(--fgColor-danger, #d1242f);
}

@media (max-width: 768px) {
  .demo-row {
    grid-template-columns: 1fr;
  }
}
</style>
