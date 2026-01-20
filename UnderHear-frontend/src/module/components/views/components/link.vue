<template>
  <ComponentDocsPage>
    <ComponentDocsHeader title="Link 链接" description="常用的文本超链接。" />

    <ComponentDocsSection title="基础用法">
      <template #description>使用 <code>variant</code> 来定义链接的样式。</template>

      <ComponentDocsDemoBlock :code="demo1Code" v-bind="demoLabels">
        <div class="link-row">
          <zLink href="#" link-text="Primary Link" variant="primary" />
          <zLink href="#" link-text="Secondary Link" variant="secondary" />
          <zLink href="#" link-text="Danger Link" variant="danger" />
        </div>
      </ComponentDocsDemoBlock>
    </ComponentDocsSection>

    <ComponentDocsSection title="外部链接">
      <template #description>设置 <code>external</code> 属性可以在链接后显示外部链接图标。</template>

      <ComponentDocsDemoBlock :code="demo2Code" v-bind="demoLabels">
        <div class="link-row">
          <zLink href="https://github.com" link-text="GitHub" :external="true" target="_blank" />
          <zLink
            href="https://vuejs.org"
            link-text="Vue.js"
            :external="true"
            target="_blank"
            variant="secondary"
          />
        </div>
      </ComponentDocsDemoBlock>
    </ComponentDocsSection>

    <ComponentDocsSection title="前后缀文本">
      <template #description>
        使用 <code>prefix-text</code> 和 <code>suffix-text</code> 在链接前后添加文本。
      </template>

      <ComponentDocsDemoBlock :code="demo3Code" v-bind="demoLabels">
        <div class="link-column">
          <zLink href="/component" link-text="组件文档" prefix-text="查看" />
          <zLink href="/component" link-text="组件文档" suffix-text="了解更多" />
          <zLink href="/component" link-text="组件文档" prefix-text="前往" suffix-text="查看详情" />
        </div>
      </ComponentDocsDemoBlock>
    </ComponentDocsSection>

    <ComponentDocsSection title="API" variant="api">
      <ComponentDocsApiTable title="属性" :columns="apiColumns" :rows="apiRows" />
    </ComponentDocsSection>
  </ComponentDocsPage>
</template>

<script setup lang="ts">
import { zLink } from '@/components/z-ui/link/zlink'
import ComponentDocsApiTable from '@/module/components/components/ComponentDocsPage/ComponentDocsApiTable.vue'
import ComponentDocsDemoBlock from '@/module/components/components/ComponentDocsPage/ComponentDocsDemoBlock.vue'
import ComponentDocsHeader from '@/module/components/components/ComponentDocsPage/ComponentDocsHeader.vue'
import ComponentDocsPage from '@/module/components/components/ComponentDocsPage/ComponentDocsPage.vue'
import ComponentDocsSection from '@/module/components/components/ComponentDocsPage/ComponentDocsSection.vue'

const demoLabels = {
  copyLabel: '复制代码',
  copiedLabel: '已复制',
  showCodeLabel: '查看代码',
  hideCodeLabel: '隐藏代码',
  hideSourceLabel: '隐藏源代码'
}

const demo1Code = `<template>
  <div style="max-width: 600px">
    <zLink href="#" link-text="Primary Link" variant="primary" />
    <zLink href="#" link-text="Secondary Link" variant="secondary" />
    <zLink href="#" link-text="Danger Link" variant="danger" />
  </div>
</template>

<style scoped>
.link-row {
  display: flex;
  gap: 20px;
  align-items: center;
}
</style>`

const demo2Code = `<template>
  <div style="max-width: 600px">
    <zLink href="https://github.com" link-text="GitHub" :external="true" target="_blank" />
    <zLink href="https://vuejs.org" link-text="Vue.js" :external="true" target="_blank" variant="secondary" />
  </div>
</template>

<style scoped>
.link-row {
  display: flex;
  gap: 20px;
  align-items: center;
}
</style>`

const demo3Code = `<template>
  <div style="max-width: 600px">
    <zLink href="/component" link-text="组件文档" prefix-text="查看" />
    <zLink href="/component" link-text="组件文档" suffix-text="了解更多" />
    <zLink href="/component" link-text="组件文档" prefix-text="前往" suffix-text="查看详情" />
  </div>
</template>

<style scoped>
.link-column {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}
</style>`

const apiColumns = ['属性名', '说明', '类型', '可选值', '默认值']
const apiRows: Array<Array<{ text: string; code?: boolean }>> = [
  [
    { text: 'href', code: true },
    { text: '链接地址' },
    { text: 'string', code: true },
    { text: '—' },
    { text: '—' }
  ],
  [
    { text: 'link-text', code: true },
    { text: '链接文本' },
    { text: 'string', code: true },
    { text: '—' },
    { text: '—' }
  ],
  [
    { text: 'prefix-text', code: true },
    { text: '前置文本' },
    { text: 'string', code: true },
    { text: '—' },
    { text: '—' }
  ],
  [
    { text: 'suffix-text', code: true },
    { text: '后置文本' },
    { text: 'string', code: true },
    { text: '—' },
    { text: '—' }
  ],
  [
    { text: 'external', code: true },
    { text: '是否为外部链接' },
    { text: 'boolean', code: true },
    { text: '—' },
    { text: 'false', code: true }
  ],
  [
    { text: 'target', code: true },
    { text: '链接打开方式' },
    { text: 'string', code: true },
    { text: '_blank / _self / _parent / _top' },
    { text: '_self', code: true }
  ],
  [
    { text: 'variant', code: true },
    { text: '链接样式变体' },
    { text: 'string', code: true },
    { text: 'primary / secondary / danger / default' },
    { text: 'primary', code: true }
  ]
]
</script>

<style scoped>
.link-row {
  display: flex;
  gap: 1.5rem;
  align-items: center;
  flex-wrap: wrap;
}

.link-column {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

@media (max-width: 768px) {
  .link-row {
    flex-direction: column;
    align-items: flex-start;
    gap: 1rem;
  }

  .link-column {
    gap: 0.75rem;
  }
}
</style>
