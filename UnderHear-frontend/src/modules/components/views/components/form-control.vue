<template>
  <ComponentDocsPage>
    <ComponentDocsHeader
      title="FormControl 表单控件"
      description="使用复合组件组织标签、说明文案和校验信息。"
    />

    <ComponentDocsSection title="基础用法">
      <template #description>
        使用 <code>FormControl</code> 与其子组件显式描述表单项结构。
      </template>
      <ComponentDocsDemoBlock :code="demo1Code">
        <FormControl>
          <FormControl.Label>Username</FormControl.Label>
          <zInput
            v-model="username"
            placeholder="请输入用户名"
          />
          <FormControl.Caption>
            仅支持字母、数字和下划线
          </FormControl.Caption>
        </FormControl>
      </ComponentDocsDemoBlock>
    </ComponentDocsSection>

    <ComponentDocsSection title="校验状态">
      <template #description>
        通过 <code>FormControl.Validation</code> 展示错误或成功信息，根容器会同步输入框样式。
      </template>
      <ComponentDocsDemoBlock :code="demo2Code">
        <FormControl>
          <FormControl.Label>Display Name</FormControl.Label>
          <zInput v-model="displayName" />
          <FormControl.Caption>
            This will be publicly visible
          </FormControl.Caption>
          <FormControl.Validation :variant="hasInvalidChars ? 'error' : 'success'">
            {{ hasInvalidChars ? 'Names may not contain symbols' : 'Looks good' }}
          </FormControl.Validation>
        </FormControl>
      </ComponentDocsDemoBlock>
    </ComponentDocsSection>

    <ComponentDocsSection title="组合结构">
      <template #description>
        子组件都是结构块，你可以在中间插入自定义内容，而不依赖旧的 prop 和具名插槽。
      </template>
      <ComponentDocsDemoBlock :code="demo3Code">
        <FormControl>
          <FormControl.Label>
            联系邮箱 <span class="required-mark">*</span>
          </FormControl.Label>
          <zInput
            v-model="email"
            placeholder="you@example.com"
          />
          <FormControl.Caption>
            我们仅用于通知，不会公开展示。
          </FormControl.Caption>
          <FormControl.Validation
            v-if="isEmailInvalid"
            variant="error"
          >
            请输入有效的邮箱地址
          </FormControl.Validation>
        </FormControl>
      </ComponentDocsDemoBlock>
    </ComponentDocsSection>

    <ComponentDocsSection
      title="API"
      variant="api"
    >
      <h3>组件</h3>
      <zTable
        :columns="apiCols"
        :data="componentRows"
        row-key="name"
        compact
        :hoverable="false"
      />
      <h3>Validation Props</h3>
      <zTable
        :columns="apiCols"
        :data="validationRows"
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

const username = ref('')
const displayName = ref('Mona L!$a')
const email = ref('')

const hasInvalidChars = computed(() => /[^a-zA-Z\s]/.test(displayName.value))
const isEmailInvalid = computed(() => email.value.length > 0 && !/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email.value))

const demo1Code = `<template>
  <FormControl>
    <FormControl.Label>Username</FormControl.Label>
    <zInput v-model="username" placeholder="请输入用户名" />
    <FormControl.Caption>仅支持字母、数字和下划线</FormControl.Caption>
  </FormControl>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { FormControl } from '@/components/z-ui/form-control'
import { zInput } from '@/components/z-ui/input'

const username = ref('')
<\/script>`

const demo2Code = `<template>
  <FormControl>
    <FormControl.Label>Display Name</FormControl.Label>
    <zInput v-model="displayName" />
    <FormControl.Caption>This will be publicly visible</FormControl.Caption>
    <FormControl.Validation :variant="hasInvalidChars ? 'error' : 'success'">
      {{ hasInvalidChars ? 'Names may not contain symbols' : 'Looks good' }}
    </FormControl.Validation>
  </FormControl>
</template>

<script setup lang="ts">
import { computed, ref } from 'vue'
import { FormControl } from '@/components/z-ui/form-control'
import { zInput } from '@/components/z-ui/input'

const displayName = ref('Mona L!$a')
const hasInvalidChars = computed(() => /[^a-zA-Z\\s]/.test(displayName.value))
<\/script>`

const demo3Code = `<template>
  <FormControl>
    <FormControl.Label>联系邮箱 <span>*</span></FormControl.Label>
    <zInput v-model="email" placeholder="you@example.com" />
    <FormControl.Caption>我们仅用于通知，不会公开展示。</FormControl.Caption>
    <FormControl.Validation v-if="isEmailInvalid" variant="error">
      请输入有效的邮箱地址
    </FormControl.Validation>
  </FormControl>
</template>

<script setup lang="ts">
import { computed, ref } from 'vue'
import { FormControl } from '@/components/z-ui/form-control'
import { zInput } from '@/components/z-ui/input'

const email = ref('')
const isEmailInvalid = computed(() => email.value.length > 0 && !/^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$/.test(email.value))
<\/script>`

const apiCols: ZTableColumn[] = [
  { key: 'name', label: '名称', rowHeader: true, minWidth: '180px' },
  { key: 'description', label: '说明', minWidth: '240px', wrap: true },
  { key: 'type', label: '类型', minWidth: '220px', wrap: true },
  { key: 'default', label: '默认值', minWidth: '120px' }
]

const componentRows = [
  { name: 'FormControl', description: '表单项根容器，负责排列子组件并根据 Validation.variant 同步输入样式。', type: 'component', default: '-' },
  { name: 'FormControl.Label', description: '标签区域，内容通过默认插槽传入。', type: 'slot', default: '-' },
  { name: 'FormControl.Caption', description: '辅助说明区域，通常放计数、说明或提示。', type: 'slot', default: '-' },
  { name: 'FormControl.Validation', description: '校验信息区域，会显示图标并向根容器同步 variant。', type: 'slot', default: '-' }
]

const validationRows = [
  { name: 'variant', description: '校验信息样式，同时影响输入框边框颜色。', type: "'error' | 'success'", default: "'error'" }
]
</script>

<style scoped>
.required-mark {
  color: var(--fgColor-danger, #d1242f);
}
</style>
