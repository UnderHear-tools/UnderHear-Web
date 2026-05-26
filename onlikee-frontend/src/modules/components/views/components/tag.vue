<template>
  <ComponentDocsPage>
    <ComponentDocsHeader
      title="Tag 标签"
      description="用于标记和选择。"
    />

    <ComponentDocsSection title="基础用法">
      <template #description>
        直接使用 <code>Tag</code> 组件即可。
      </template>
      <ComponentDocsDemoBlock :code="demo1Code">
        <div class="tag-row">
          <Tag>Tag</Tag>
        </div>
      </ComponentDocsDemoBlock>
    </ComponentDocsSection>

    <ComponentDocsSection title="不同尺寸">
      <template #description>
        通过 <code>size</code> 属性设置标签尺寸。
      </template>
      <ComponentDocsDemoBlock :code="demo2Code">
        <div class="tag-row">
          <Tag size="small">
            small
          </Tag>
          <Tag size="medium">
            medium
          </Tag>
          <Tag size="large">
            large
          </Tag>
          <Tag size="xlarge">
            xlarge
          </Tag>
        </div>
      </ComponentDocsDemoBlock>
    </ComponentDocsSection>

    <ComponentDocsSection title="自定义颜色">
      <template #description>
        通过 <code>color</code> 和 <code>background</code> 属性自定义字体颜色和背景色。
      </template>
      <ComponentDocsDemoBlock :code="demo3Code">
        <div class="tag-row">
          <Tag>默认</Tag>
          <Tag
            color="#1a7f37"
            background="#dafbe1"
          >
            成功
          </Tag>
          <Tag
            color="#9a6700"
            background="#fff8c5"
          >
            警告
          </Tag>
          <Tag
            color="#cf222e"
            background="#ffebe9"
          >
            危险
          </Tag>
          <Tag
            color="#6639ba"
            background="#fbefff"
          >
            紫色
          </Tag>
        </div>
      </ComponentDocsDemoBlock>
    </ComponentDocsSection>

    <ComponentDocsSection title="带图标">
      <template #description>
        通过 <code>visual</code> 插槽添加前置图标。
      </template>
      <ComponentDocsDemoBlock :code="demo4Code">
        <div class="tag-row">
          <Tag>
            <template #visual>
              <StarIcon />
            </template>
            StarIcon
          </Tag>
          <Tag
            color="#1a7f37"
            background="#dafbe1"
          >
            <template #visual>
              <CheckIcon />
            </template>
            已完成
          </Tag>
          <Tag
            color="#cf222e"
            background="#ffebe9"
          >
            <template #visual>
              <AlertIcon />
            </template>
            错误
          </Tag>
        </div>
      </ComponentDocsDemoBlock>
    </ComponentDocsSection>

    <ComponentDocsSection title="可删除">
      <template #description>
        设置 <code>removable</code> 属性后，标签右侧会显示删除按钮；点击时触发 <code>remove</code> 事件。
      </template>
      <ComponentDocsDemoBlock :code="demo5Code">
        <div class="tag-row">
          <Tag removable>
            Vue
          </Tag>
          <Tag
            removable
            @remove="() => {}"
          >
            <template #visual>
              <StarIcon />
            </template>
            收藏
          </Tag>
          <Tag
            color="#1a7f37"
            background="#dafbe1"
            removable
            @remove="() => {}"
          >
            <template #visual>
              <CheckIcon />
            </template>
            已完成
          </Tag>
        </div>
      </ComponentDocsDemoBlock>
    </ComponentDocsSection>

    <ComponentDocsSection
      title="API"
      variant="api"
    >
      <h3>属性</h3>
      <Table
        :columns="apiTableColumns"
        :data="apiTableRows"
        row-key="name"
        compact
        :hoverable="false"
      />
      <h3>插槽</h3>
      <Table
        :columns="slotTableColumns"
        :data="slotTableRows"
        row-key="name"
        compact
        :hoverable="false"
      />
      <h3>事件</h3>
      <Table
        :columns="eventTableColumns"
        :data="eventTableRows"
        row-key="name"
        compact
        :hoverable="false"
      />
    </ComponentDocsSection>
  </ComponentDocsPage>
</template>

<script setup lang="ts">
import { Tag } from '@/components/z-ui/Tag'
import { Table, type TableColumn } from '@/components/z-ui/Table'
import { StarIcon, CheckIcon, AlertIcon } from '@/components/octicons-vue3'
import ComponentDocsDemoBlock from '@/modules/components/components/ComponentDocsPage/ComponentDocsDemoBlock.vue'
import ComponentDocsHeader from '@/modules/components/components/ComponentDocsPage/ComponentDocsHeader.vue'
import ComponentDocsPage from '@/modules/components/components/ComponentDocsPage/ComponentDocsPage.vue'
import ComponentDocsSection from '@/modules/components/components/ComponentDocsPage/ComponentDocsSection.vue'

const demo1Code = `<template>
  <div class="tag-row">
    <Tag>Tag</Tag>
  </div>
</template>

<style scoped>
.tag-row {
  display: flex;
  gap: 20px;
}
</style>`

const demo2Code = `<template>
  <div class="tag-row">
    <Tag size="small">small</Tag>
    <Tag size="medium">medium</Tag>
    <Tag size="large">large</Tag>
    <Tag size="xlarge">xlarge</Tag>
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
    <Tag>默认</Tag>
    <Tag color="#1a7f37" background="#dafbe1">成功</Tag>
    <Tag color="#9a6700" background="#fff8c5">警告</Tag>
    <Tag color="#cf222e" background="#ffebe9">危险</Tag>
    <Tag color="#6639ba" background="#fbefff">紫色</Tag>
  </div>
</template>

<style scoped>
.tag-row {
  display: flex;
  align-items: center;
  gap: 20px;
}
</style>`

const demo4Code = `<script setup lang="ts">
import { Tag } from '@/components/z-ui/Tag'
import { StarIcon, CheckIcon, AlertIcon } from '@/components/octicons-vue3'
<\/script>

<template>
  <div class="tag-row">
    <Tag>
      <template #visual><StarIcon /></template>
      StarIcon
    </Tag>
    <Tag color="#1a7f37" background="#dafbe1">
      <template #visual><CheckIcon /></template>
      已完成
    </Tag>
    <Tag color="#cf222e" background="#ffebe9">
      <template #visual><AlertIcon /></template>
      错误
    </Tag>
  </div>
</template>

<style scoped>
.tag-row {
  display: flex;
  align-items: center;
  gap: 20px;
}
</style>`

const demo5Code = `<script setup lang="ts">
import { Tag } from '@/components/z-ui/Tag'
import { StarIcon, CheckIcon } from '@/components/octicons-vue3'

const handleRemove = (label: string) => {
  console.log(label, 'removed')
}
<\/script>

<template>
  <div class="tag-row">
    <Tag removable @remove="handleRemove('Vue')">
      Vue
    </Tag>
    <Tag removable @remove="handleRemove('收藏')">
      <template #visual><StarIcon /></template>
      收藏
    </Tag>
    <Tag color="#1a7f37" background="#dafbe1" removable @remove="handleRemove('已完成')">
      <template #visual><CheckIcon /></template>
      已完成
    </Tag>
  </div>
</template>

<style scoped>
.tag-row {
  display: flex;
  align-items: center;
  gap: 20px;
}
</style>`

const apiTableColumns: TableColumn[] = [
  { key: 'name', label: '属性名', rowHeader: true, minWidth: '140px' },
  { key: 'default', label: '默认值', minWidth: '100px' },
  { key: 'type', label: '类型', minWidth: '200px', wrap: true },
  { key: 'description', label: '说明', minWidth: '200px', wrap: true }
]

const apiTableRows = [
  {
    name: 'size',
    description: '标签尺寸',
    type: "'small' | 'medium' | 'large' | 'xlarge'",
    default: "'medium'"
  },
  {
    name: 'color',
    description: '字体颜色',
    type: 'string',
    default: "''"
  },
  {
    name: 'background',
    description: '背景颜色',
    type: 'string',
    default: "''"
  },
  {
    name: 'removable',
    description: '是否显示删除按钮',
    type: 'boolean',
    default: 'false'
  }
]

const slotTableColumns: TableColumn[] = [
  { key: 'name', label: '插槽名', rowHeader: true, minWidth: '140px' },
  { key: 'description', label: '说明', minWidth: '300px', wrap: true }
]

const slotTableRows = [
  {
    name: 'default',
    description: '标签的内容'
  },
  {
    name: 'visual',
    description: '前置视觉引导，通常用于放置图标'
  }
]

const eventTableColumns: TableColumn[] = [
  { key: 'name', label: '事件名', rowHeader: true, minWidth: '140px' },
  { key: 'params', label: '参数', minWidth: '140px' },
  { key: 'description', label: '说明', minWidth: '200px', wrap: true }
]

const eventTableRows = [
  {
    name: 'remove',
    params: '无',
    description: '点击删除按钮时触发'
  }
]
</script>

<style scoped>
.tag-row {
  display: flex;
  align-items: center;
  gap: 20px;
}
</style>
