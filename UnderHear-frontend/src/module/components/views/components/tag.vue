<template>
  <ComponentDocsPage>
    <ComponentDocsHeader title="Tag 标签" description="用于标记和选择。" />

    <ComponentDocsSection title="基础用法">
      <template #description>直接使用 <code>zTag</code> 组件即可。</template>
      <ComponentDocsDemoBlock :code="demo1Code">
        <div class="tag-row">
          <zTag>Tag</zTag>
        </div>
      </ComponentDocsDemoBlock>
    </ComponentDocsSection>

    <ComponentDocsSection title="标签大小">
      <template #description>通过 <code>size</code> 属性设置标签大小。</template>
      <ComponentDocsDemoBlock :code="demo2Code">
        <div class="tag-row">
          <zTag size="small">Small</zTag>
          <zTag size="medium">Medium</zTag>
          <zTag size="large">Large</zTag>
          <zTag size="x-large">X-Large</zTag>
        </div>
      </ComponentDocsDemoBlock>
    </ComponentDocsSection>

    <ComponentDocsSection title="可删除标签">
      <template #description>通过 <code>action</code> 属性设置删除图标，通过监听 <code>actionClick</code> 事件来删除标签。</template>
      <ComponentDocsDemoBlock :code="demo3Code">
        <div class="tag-row">
          <zTag :action="X" @actionClick="handleActionClick">Tag</zTag>
        </div>
      </ComponentDocsDemoBlock>
    </ComponentDocsSection>

    <ComponentDocsSection title="视觉图标">
      <template #description>通过 <code>visual</code> 属性设置视觉图标。</template>
      <ComponentDocsDemoBlock :code="demo4Code">
        <div class="tag-row">
          <zTag :visual="MarkGithub">Tag</zTag>
        </div>
      </ComponentDocsDemoBlock>
    </ComponentDocsSection>

    <ComponentDocsSection title="自定义内容">
      <template #description>通过插槽自定义标签内容。</template>
      <ComponentDocsDemoBlock :code="demo5Code">
        <div class="tag-row">
          <zTag>
            Tag
            <template #visual>
              <svg aria-label="Google logo" width="12" height="12" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512"><g><path fill="#34a853" d="M153 292c30 82 118 95 171 60h62v48A192 192 0 0190 341"></path><path fill="#4285f4" d="m386 400a140 175 0 0053-179H260v74h102q-7 37-38 57"></path><path fill="#fbbc02" d="m90 341a208 200 0 010-171l63 49q-12 37 0 73"></path><path fill="#ea4335" d="m153 219c22-69 116-109 179-50l55-54c-78-75-230-72-297 55"></path></g></svg>
            </template>
            <template #action>
              <svg xmlns="http://www.w3.org/2000/svg" width="12" height="12" viewBox="0 0 24 24"><path fill="currentColor" d="m12 13.4l-4.9 4.9q-.275.275-.7.275t-.7-.275t-.275-.7t.275-.7l4.9-4.9l-4.9-4.9q-.275-.275-.275-.7t.275-.7t.7-.275t.7.275l4.9 4.9l4.9-4.9q.275-.275.7-.275t.7.275t.275.7t-.275.7L13.4 12l4.9 4.9q.275.275.275.7t-.275.7t-.7.275t-.7-.275z"/></svg>
            </template>
          </zTag>
        </div>
      </ComponentDocsDemoBlock>
    </ComponentDocsSection>

    <ComponentDocsSection title="API" variant="api">
      <h4>Slots 插槽</h4>
      <zTable
        :columns="apiTableColumns"
        :data="apiTableRows"
        row-key="name"
        compact
        :hoverable="false"
      />
      <h4 style="margin-top: 24px;">Props 属性</h4>
      <zTable
        :columns="propsTableColumns"
        :data="propsTableRows"
        row-key="name"
        compact
        :hoverable="false"
      />
      <h4 style="margin-top: 24px;">Events 事件</h4>
      <zTable
        :columns="eventsTableColumns"
        :data="eventsTableRows"
        row-key="name"
        compact
        :hoverable="false"
      />
    </ComponentDocsSection>
  </ComponentDocsPage>
</template>

<script setup lang="ts">
import { zTag } from '@/components/z-ui/tag'
import { zTable, type ZTableColumn } from '@/components/z-ui/table'
import ComponentDocsDemoBlock from '@/module/components/components/ComponentDocsPage/ComponentDocsDemoBlock.vue'
import ComponentDocsHeader from '@/module/components/components/ComponentDocsPage/ComponentDocsHeader.vue'
import ComponentDocsPage from '@/module/components/components/ComponentDocsPage/ComponentDocsPage.vue'
import ComponentDocsSection from '@/module/components/components/ComponentDocsPage/ComponentDocsSection.vue'
import { X, MarkGithub } from '@/components/z-ui/icon/Octicons-vue/index.ts'

const demo1Code = `<template>
  <div class="tag-row">
    <zTag>Tag</zTag>
  </div>
</template>

<style scoped>
.tag-row {
  display: flex;
  align-items: center;
  gap: 20px;
}
</style>`

const demo2Code = `<template>
  <div class="tag-row">
    <zTag size="small">Small</zTag>
    <zTag size="medium">Medium</zTag>
    <zTag size="large">Large</zTag>
    <zTag size="x-large">X-Large</zTag>
  </div>
</template>

<style scoped>
.tag-row {
  display: flex;
  align-items: center;
  gap: 20px;
}
</style>`

const demo3Code = `<template>
  <div class="tag-row">
    <zTag :action="X" @actionClick="handleActionClick">Tag</zTag>
  </div>
</template>

<style scoped>
.tag-row {
  display: flex;
  align-items: center;
  gap: 20px;
}
</style>`

const demo4Code = `<template>
  <div class="tag-row">
    <zTag :visual="MarkGithub">Tag</zTag>
  </div>
</template>

<style scoped>
.tag-row {
  display: flex;
  align-items: center;
  gap: 20px;
}
</style>`

const demo5Code = `<template>
  <div class="tag-row">
    <zTag>
      Tag
      <template #visual>
        <svg aria-label="Google logo" width="12" height="12" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512"><g><path fill="#34a853" d="M153 292c30 82 118 95 171 60h62v48A192 192 0 0190 341"></path><path fill="#4285f4" d="m386 400a140 175 0 0053-179H260v74h102q-7 37-38 57"></path><path fill="#fbbc02" d="m90 341a208 200 0 010-171l63 49q-12 37 0 73"></path><path fill="#ea4335" d="m153 219c22-69 116-109 179-50l55-54c-78-75-230-72-297 55"></path></g></svg>
      </template>
      <template #action>
        <svg xmlns="http://www.w3.org/2000/svg" width="12" height="12" viewBox="0 0 24 24"><path fill="currentColor" d="m12 13.4l-4.9 4.9q-.275.275-.7.275t-.7-.275t-.275-.7t.275-.7l4.9-4.9l-4.9-4.9q-.275-.275-.275-.7t.275-.7t.7-.275t.7.275l4.9 4.9l4.9-4.9q.275-.275.7-.275t.7.275t.275.7t-.275.7L13.4 12l4.9 4.9q.275.275.275.7t-.275.7t-.7.275t-.7-.275z"/></svg>
      </template>
    </zTag>
  </div>
</template>

<style scoped>
.tag-row {
  display: flex;
  align-items: center;
  gap: 20px;
}
</style>`

const apiTableColumns: ZTableColumn[] = [
  { key: 'name', label: '插槽名', rowHeader: true, minWidth: '140px' },
  { key: 'description', label: '说明', minWidth: '300px', wrap: true },
]

const apiTableRows = [
  {
    name: 'default',
    description: '标签的内容',
  },
  {
    name: 'visual',
    description: '视觉图标',
  },
  {
    name: 'action',
    description: '操作图标',
  }
]

const propsTableColumns: ZTableColumn[] = [
  { key: 'name', label: '属性名', rowHeader: true, minWidth: '140px' },
  { key: 'description', label: '说明', minWidth: '300px', wrap: true },
  { key: 'type', label: '类型', minWidth: '200px', wrap: true },
  { key: 'options', label: '可选值', minWidth: '200px', wrap: true },
  { key: 'default', label: '默认值', minWidth: '140px' },
]

const propsTableRows = [
  {
    name: 'size',
    description: '标签大小',
    type: 'string',
    options: 'small / medium / large / x-large',
    default: 'medium',
  },
  {
    name: 'action',
    description: '操作图标',
    type: 'Component',
    options: '—',
    default: '—',
  },
  {
    name: 'visual',
    description: '视觉图标',
    type: 'Component',
    options: '—',
    default: '—',
  },
]

const eventsTableColumns: ZTableColumn[] = [
  { key: 'name', label: '事件名', rowHeader: true, minWidth: '140px' },
  { key: 'description', label: '说明', minWidth: '300px', wrap: true },
]

const eventsTableRows = [
  {
    name: 'actionClick',
    description: '操作图标点击事件，可使用 event.stopPropagation() 阻止冒泡。',
  },
]

function handleActionClick(event: MouseEvent) {
  event.stopPropagation()
  console.log('操作图标被点击')
}
</script>

<style scoped>
.tag-row {
  display: flex;
  align-items: center;
  gap: 20px;
}
</style>
