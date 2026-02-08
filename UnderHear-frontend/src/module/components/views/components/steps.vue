<script setup lang="ts">
import { zSteps } from '@/components/z-ui/steps'
import { zStep } from '@/components/z-ui/step'
import { zTable, type ZTableColumn } from '@/components/z-ui/table'
import ComponentDocsDemoBlock from '@/module/components/components/ComponentDocsPage/ComponentDocsDemoBlock.vue'
import ComponentDocsHeader from '@/module/components/components/ComponentDocsPage/ComponentDocsHeader.vue'
import ComponentDocsPage from '@/module/components/components/ComponentDocsPage/ComponentDocsPage.vue'
import ComponentDocsSection from '@/module/components/components/ComponentDocsPage/ComponentDocsSection.vue'
import { ref } from 'vue'

const activeStep = ref<number>(0)

const next = () => {
  if (activeStep.value++ > 3) {
    activeStep.value = 0
  }
}

const demo1Code = `<script setup lang="ts">
import { zSteps } from '@/components/z-ui/steps'
import { zStep } from '@/components/z-ui/step'
import { ref } from 'vue'

const activeStep = ref<number>(0)

const next = () => {
  if (activeStep.value++ > 3) {
    activeStep.value = 0
  }
}
<\/script>

<template>
  <div style="width: 100%">
    <div style="max-width: 600px">
      <zSteps :active="activeStep">
        <zStep title="步骤1" />
        <zStep title="步骤2" />
        <zStep title="步骤3" />
        <zStep title="步骤4" />
      </zSteps>
    </div>
    <button class="btn-next" @click="next">下一步</button>
  </div>
</template>

<style scoped>
.btn-next {
  margin-top: 20px;
  border: 1px solid gray;
  border-radius: 4px;
  padding: 6px 12px;
  cursor: pointer;
  font-size: 14px;
}
</style>`

const demo2Code = `<script setup lang="ts">
import { zSteps } from '@/components/z-ui/steps'
import { zStep } from '@/components/z-ui/step'
<\/script>

<template>
  <div style="width: 100%">
    <div style="max-width: 600px">
      <zSteps :active="2" align-center>
        <zStep title="步骤1" />
        <zStep title="步骤2" />
        <zStep title="步骤3" />
        <zStep title="步骤4" />
      </zSteps>
    </div>
  </div>
</template>`

const demo3Code = `<script setup lang="ts">
import { zSteps } from '@/components/z-ui/steps'
import { zStep } from '@/components/z-ui/step'
<\/script>

<template>
  <div style="width: 100%">
    <div style="max-width: 600px">
      <zSteps :active="1">
        <zStep title="步骤1" description="步骤1描述" />
        <zStep title="步骤2" description="步骤2描述" />
        <zStep title="步骤3" description="步骤3描述" />
        <zStep title="步骤4" description="步骤4描述" />
      </zSteps>
    </div>
  </div>
</template>`

const demo4Code = `<script setup lang="ts">
import { zSteps } from '@/components/z-ui/steps'
import { zStep } from '@/components/z-ui/step'
<\/script>

<template>
  <div style="width: 100%">
    <div style="height: 400px">
      <zSteps :active="1" direction="vertical">
        <zStep title="步骤1" description="步骤1描述" />
        <zStep title="步骤2" description="步骤2描述" />
        <zStep title="步骤3" description="步骤3描述" />
        <zStep title="步骤4" description="步骤4描述" />
      </zSteps>
    </div>
  </div>
</template>`

const zStepsApiTableColumns: ZTableColumn[] = [
  { key: 'name', label: '属性名', rowHeader: true, minWidth: '140px' },
  { key: 'description', label: '说明', minWidth: '200px', wrap: true },
  { key: 'type', label: '类型', minWidth: '200px', wrap: true },
  { key: 'default', label: '默认值', minWidth: '100px' }
]

const zStepsApiTableRows = [
  {
    name: 'active',
    description: '设置当前激活步骤',
    type: 'number',
    default: 0
  },
  {
    name: 'direction',
    description: '设置步骤条方向',
    type: "'horizontal' | 'vertical'",
    default: 'horizontal'
  },
  {
    name: 'space',
    description: '设置每个步骤的间距，不填写将自适应间距。 支持百分比',
    type: 'number | string',
    default: "''"
  },
  {
    name: 'align-center',
    description: '设置步骤条居中对齐',
    type: 'boolean',
    default: '—'
  }
]

const zStepsSlotTableColumns: ZTableColumn[] = [
  { key: 'name', label: '插槽名', rowHeader: true, minWidth: '140px' },
  { key: 'description', label: '说明', minWidth: '300px', wrap: true }
]

const zStepsSlotTableRows = [
  {
    name: 'default',
    description: '默认插槽,用于放置zStep组件'
  }
]

const zStepApiTableColumns: ZTableColumn[] = [
  { key: 'name', label: '属性名', rowHeader: true, minWidth: '140px' },
  { key: 'description', label: '说明', minWidth: '200px', wrap: true },
  { key: 'type', label: '类型', minWidth: '200px', wrap: true },
  { key: 'default', label: '默认值', minWidth: '100px' }
]

const zStepApiTableRows = [
  {
    name: 'title',
    description: '步骤标题',
    type: 'string',
    default: "''"
  },
  {
    name: 'description',
    description: '步骤描述',
    type: 'string',
    default: "''"
  }
]
</script>

<template>
  <ComponentDocsPage>
    <ComponentDocsHeader title="Steps 步骤条" description="用于引导用户完成任务的步骤条组件。" />

    <ComponentDocsSection title="基础用法">
      <template #description>设置 <code>active</code> 属性，接受一个 <code>Number</code>，表明步骤的 index，从 0 开始。 需要定宽的步骤条时，设置 <code>space</code> 属性即可，它接受 <code>Number</code>， 单位为 px，如果不设置，则为自适应。</template>
      <ComponentDocsDemoBlock :code="demo1Code">
        <div style="width: 100%">
          <div style="max-width: 600px">
            <zSteps :active="activeStep">
              <zStep title="步骤1" />
              <zStep title="步骤2" />
              <zStep title="步骤3" />
              <zStep title="步骤4" />
            </zSteps>
          </div>
          <button class="btn-next" @click="next">下一步</button>
        </div>
      </ComponentDocsDemoBlock>
    </ComponentDocsSection>

    <ComponentDocsSection title="居中的步骤条">
      <template #description>设置 <code>align-center</code> 属性，使步骤条居中。</template>
      <ComponentDocsDemoBlock :code="demo2Code">
        <div style="width: 100%">
          <div style="max-width: 600px">
            <zSteps :active="2" align-center>
              <zStep title="步骤1" />
              <zStep title="步骤2" />
              <zStep title="步骤3" />
              <zStep title="步骤4" />
            </zSteps>
          </div>
        </div>
      </ComponentDocsDemoBlock>
    </ComponentDocsSection>

    <ComponentDocsSection title="带描述的步骤条">
      <template #description>每一项zStep组件都可以设置 <code>description</code> 属性，可以显示描述信息。</template>
      <ComponentDocsDemoBlock :code="demo3Code">
        <div style="width: 100%">
          <div style="max-width: 600px">
            <zSteps :active="1">
              <zStep title="步骤1" description="步骤1描述" />
              <zStep title="步骤2" description="步骤2描述" />
              <zStep title="步骤3" description="步骤3描述" />
              <zStep title="步骤4" description="步骤4描述" />
            </zSteps>
          </div>
        </div>
      </ComponentDocsDemoBlock>
    </ComponentDocsSection>

    <ComponentDocsSection title="垂直步骤条">
      <template #description>设置 <code>direction</code> 属性为 <code>vertical</code>，使步骤条垂直。</template>
      <ComponentDocsDemoBlock :code="demo4Code">
        <div style="width: 100%">
          <div style="height: 400px">
            <zSteps :active="1" direction="vertical">
              <zStep title="步骤1" description="步骤1描述" />
              <zStep title="步骤2" description="步骤2描述" />
              <zStep title="步骤3" description="步骤3描述" />
              <zStep title="步骤4" description="步骤4描述" />
            </zSteps>
          </div>
        </div>
      </ComponentDocsDemoBlock>
    </ComponentDocsSection>

    <ComponentDocsSection title="zSteps API" variant="api">
      <h3>zSteps 属性</h3>
      <zTable
        :columns="zStepsApiTableColumns"
        :data="zStepsApiTableRows"
        row-key="name"
        compact
        :hoverable="false"
      />
      <h3>zSteps 插槽</h3>
      <zTable
        :columns="zStepsSlotTableColumns"
        :data="zStepsSlotTableRows"
        row-key="name"
        compact
        :hoverable="false"
      />
    </ComponentDocsSection>

    <ComponentDocsSection title="zStep API" variant="api">
      <h3>zStep 属性</h3>
      <zTable
        :columns="zStepApiTableColumns"
        :data="zStepApiTableRows"
        row-key="name"
        compact
        :hoverable="false"
      />
    </ComponentDocsSection>
  </ComponentDocsPage>

</template>

<style scoped>
.btn-next {
  margin-top: 20px;
  border: 1px solid gray;
  border-radius: 4px;
  padding: 6px 12px;
  cursor: pointer;
  font-size: 14px;
}
</style>
