<template>
  <ComponentDocsPage>
    <ComponentDocsHeader
      title="FormControl 表单控件"
      description="用于为输入类组件提供统一的标签、校验提示和辅助文案。"
    />

    <ComponentDocsSection title="基础用法">
      <template #description>
        使用<code>FormControl</code>、<code>Input</code>组合而成的操作栏组件。通过<code>label</code>、<code>html-for</code>、<code>caption</code>快速为输入框补齐语义信息。
      </template>
      <ComponentDocsDemoBlock :code="demo1Code">
        <zFormControl
          label="Username"
          html-for="form-control-demo-username"
          required
          caption="仅支持字母、数字和下划线"
        >
          <zInput
            id="form-control-demo-username"
            v-model="username"
            placeholder="请输入用户名"
          />
        </zFormControl>
      </ComponentDocsDemoBlock>
    </ComponentDocsSection>

    <ComponentDocsSection title="校验状态">
      <template #description>
        当 <code>invalid</code> 为 <code>true</code> 时显示校验文案，并高亮子输入组件。
      </template>
      <ComponentDocsDemoBlock :code="demo2Code">
        <zFormControl
          label="Display Name"
          html-for="form-control-demo-name"
          :invalid="hasInvalidChars"
          validation="Names may not contain symbols"
          caption="This will be publicly visible"
        >
          <zInput
            id="form-control-demo-name"
            v-model="displayName"
          />
        </zFormControl>
      </ComponentDocsDemoBlock>
    </ComponentDocsSection>

    <ComponentDocsSection title="插槽自定义">
      <template #description>
        通过 <code>label</code>、<code>validation</code>、<code>caption</code> 具名插槽覆盖默认内容。
      </template>
      <ComponentDocsDemoBlock :code="demo3Code">
        <zFormControl
          html-for="form-control-demo-email"
          :invalid="isEmailInvalid"
        >
          <template #label>
            联系邮箱 <span>*</span>
          </template>
          <zInput
            id="form-control-demo-email"
            v-model="email"
            placeholder="you@example.com"
          />
          <template #validation>
            请输入有效的邮箱地址
          </template>
          <template #caption>
            我们仅用于通知，不会公开展示。
          </template>
        </zFormControl>
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
      <h3>插槽</h3>
      <zTable
        :columns="apiCols"
        :data="slotRows"
        row-key="name"
        compact
        :hoverable="false"
      />
    </ComponentDocsSection>
  </ComponentDocsPage>
</template>

<script setup lang="ts">
import { computed, ref } from 'vue'
import { zFormControl } from '@/components/z-ui/formControl'
import { zInput } from '@/components/z-ui/input'
import { zTable, type ZTableColumn } from '@/components/z-ui/table'
import ComponentDocsDemoBlock from '@/modules/components/components/ComponentDocsPage/ComponentDocsDemoBlock.vue'
import ComponentDocsHeader from '@/modules/components/components/ComponentDocsPage/ComponentDocsHeader.vue'
import ComponentDocsPage from '@/modules/components/components/ComponentDocsPage/ComponentDocsPage.vue'
import ComponentDocsSection from '@/modules/components/components/ComponentDocsPage/ComponentDocsSection.vue'

const username = ref('')
const displayName = ref('Mona L!$a')
const email = ref('')

const hasInvalidChars = computed(() => /[^a-zA-Z\s]/.test(displayName.value))
const isEmailInvalid = computed(() => email.value.length > 0 && !/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email.value))

const demo1Code = `<template>
  <zFormControl
    label="Username"
    html-for="username"
    required
    caption="仅支持字母、数字和下划线"
  >
    <zInput id="username" v-model="username" placeholder="请输入用户名" />
  </zFormControl>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { zFormControl } from '@/components/z-ui/formControl'
import { zInput } from '@/components/z-ui/input'

const username = ref('')
<\/script>`

const demo2Code = `<template>
  <zFormControl
    label="Display Name"
    html-for="display-name"
    :invalid="hasInvalidChars"
    validation="Names may not contain symbols"
    caption="This will be publicly visible"
  >
    <zInput id="display-name" v-model="displayName" />
  </zFormControl>
</template>

<script setup lang="ts">
import { computed, ref } from 'vue'
import { zFormControl } from '@/components/z-ui/formControl'
import { zInput } from '@/components/z-ui/input'

const displayName = ref('Mona L!$a')
const hasInvalidChars = computed(() => /[^a-zA-Z\\s]/.test(displayName.value))
<\/script>`

const demo3Code = `<template>
  <zFormControl html-for="email" :invalid="isEmailInvalid">
    <template #label>联系邮箱 <span>*</span></template>
    <zInput id="email" v-model="email" placeholder="you@example.com" />
    <template #validation>请输入有效的邮箱地址</template>
    <template #caption>我们仅用于通知，不会公开展示。</template>
  </zFormControl>
</template>

<script setup lang="ts">
import { computed, ref } from 'vue'
import { zFormControl } from '@/components/z-ui/formControl'
import { zInput } from '@/components/z-ui/input'

const email = ref('')
const isEmailInvalid = computed(() => email.value.length > 0 && !/^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$/.test(email.value))
<\/script>`

const apiCols: ZTableColumn[] = [
  { key: 'name', label: '属性名', rowHeader: true, minWidth: '140px' },
  { key: 'description', label: '说明', minWidth: '220px', wrap: true },
  { key: 'type', label: '类型', minWidth: '220px', wrap: true },
  { key: 'default', label: '默认值', minWidth: '120px' }
]

const apiRows = [
  { name: 'label', description: '标签文本', type: 'string', default: "''" },
  { name: 'htmlFor', description: '绑定标签的表单控件 id', type: 'string', default: "''" },
  { name: 'required', description: '是否显示必填标记', type: 'boolean', default: 'false' },
  { name: 'requiredIndicator', description: '必填标记字符', type: 'string', default: "'*'" },
  { name: 'invalid', description: '是否处于错误状态', type: 'boolean', default: 'false' },
  { name: 'validation', description: '错误提示文案（invalid=true 时显示）', type: 'string', default: "''" },
  { name: 'caption', description: '辅助说明文案', type: 'string', default: "''" }
]

const slotRows = [
  { name: 'default', description: '表单控件主体内容', type: 'slot', default: '—' },
  { name: 'label', description: '自定义标签区域', type: 'slot', default: '—' },
  { name: 'validation', description: '自定义校验提示区域', type: 'slot', default: '—' },
  { name: 'caption', description: '自定义辅助文案区域', type: 'slot', default: '—' }
]
</script>

<style scoped>
</style>
