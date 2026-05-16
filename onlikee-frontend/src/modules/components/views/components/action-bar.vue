<template>
  <ComponentDocsPage>
    <ComponentDocsHeader
      title="ActionBar 操作栏"
      description="由多个组件组合而成的工具栏，常用于编辑器顶部。"
    />

    <ComponentDocsSection title="基础用法">
      <template #description>
        使用
        <code>Button</code>、
        <code>Tooltip</code>、
        <code>Divider</code>、
        <code>Octicons-vue3</code>
        组合而成的操作栏组件。
      </template>
      <ComponentDocsDemoBlock :code="demo1Code">
        <div class="demo-actions">
          <Tooltip
            content="Bold"
            placement="bottom"
          >
            <Button variant="invisible">
              <template #leadingVisual>
                <Bold />
              </template>
            </Button>
          </Tooltip>
          <Tooltip
            content="Italic"
            placement="bottom"
          >
            <Button variant="invisible">
              <template #leadingVisual>
                <Italic />
              </template>
            </Button>
          </Tooltip>
          <Tooltip
            content="Code"
            placement="bottom"
          >
            <Button variant="invisible">
              <template #leadingVisual>
                <Code />
              </template>
            </Button>
          </Tooltip>
          <Tooltip
            content="Link"
            placement="bottom"
          >
            <Button variant="invisible">
              <template #leadingVisual>
                <Link />
              </template>
            </Button>
          </Tooltip>
          <Divider
            direction="vertical"
            height="20px"
          />
          <Tooltip
            content="File Added"
            placement="bottom"
          >
            <Button variant="invisible">
              <template #leadingVisual>
                <FileAdded />
              </template>
            </Button>
          </Tooltip>
          <Tooltip
            content="Search"
            placement="bottom"
          >
            <Button variant="invisible">
              <template #leadingVisual>
                <Search />
              </template>
            </Button>
          </Tooltip>
        </div>
      </ComponentDocsDemoBlock>
    </ComponentDocsSection>
  </ComponentDocsPage>
</template>

<script setup lang="ts">
import ComponentDocsDemoBlock from '@/modules/components/components/ComponentDocsPage/ComponentDocsDemoBlock.vue'
import ComponentDocsHeader from '@/modules/components/components/ComponentDocsPage/ComponentDocsHeader.vue'
import ComponentDocsPage from '@/modules/components/components/ComponentDocsPage/ComponentDocsPage.vue'
import ComponentDocsSection from '@/modules/components/components/ComponentDocsPage/ComponentDocsSection.vue'
import { Tooltip } from '@/components/z-ui/tooltip'
import { Divider } from '@/components/z-ui/divider'
import { Bold, Italic, Code, Link, FileAdded, Search } from '@/components/octicons-vue3'
import { Button } from '@/components/z-ui/button'

const demo1Code = `<template>
  <div class="demo-actions">
    <Tooltip content="Bold" placement="bottom">
      <Button variant="invisible">
        <template #leadingVisual><Bold /></template>
      </Button>
    </Tooltip>
    <Tooltip content="Italic" placement="bottom">
      <Button variant="invisible">
        <template #leadingVisual><Italic /></template>
      </Button>
    </Tooltip>
    <Tooltip content="Code" placement="bottom">
      <Button variant="invisible">
        <template #leadingVisual><Code /></template>
      </Button>
    </Tooltip>
    <Tooltip content="Link" placement="bottom">
      <Button variant="invisible">
        <template #leadingVisual><Link /></template>
      </Button>
    </Tooltip>

    <Divider direction="vertical" height="20px" />

    <Tooltip content="File Added" placement="bottom">
      <Button variant="invisible">
        <template #leadingVisual><FileAdded /></template>
      </Button>
    </Tooltip>
    <Tooltip content="Search" placement="bottom">
      <Button variant="invisible">
        <template #leadingVisual><Search /></template>
      </Button>
    </Tooltip>
  </div>
</template>

<script setup lang="ts">
import { Button } from '@/components/z-ui/button'
import { Tooltip } from '@/components/z-ui/tooltip'
import { Divider } from '@/components/z-ui/divider'
import { Bold, Italic, Code, Link, FileAdded, Search } from '@/components/octicons-vue3'
<\/script>

<style scoped>
.demo-actions {
  display: flex;
  gap: 8px;
  justify-content: flex-end;
  align-items: center;
}
</style>`
</script>

<style scoped>
.demo-actions {
  display: flex;
  gap: 8px;
  justify-content: flex-end;
  align-items: center;
}
</style>