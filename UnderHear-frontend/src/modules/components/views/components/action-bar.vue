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
        <code>Octicons-vue</code>
        组合而成的操作栏组件。
      </template>
      <ComponentDocsDemoBlock :code="demo1Code">
        <div class="demo-actions">
          <zTooltip
            content="Bold"
            placement="bottom"
          >
            <zButton variant="invisible">
              <template #leadingVisual>
                <Bold />
              </template>
            </zButton>
          </zTooltip>
          <zTooltip
            content="Italic"
            placement="bottom"
          >
            <zButton variant="invisible">
              <template #leadingVisual>
                <Italic />
              </template>
            </zButton>
          </zTooltip>
          <zTooltip
            content="Code"
            placement="bottom"
          >
            <zButton variant="invisible">
              <template #leadingVisual>
                <Code />
              </template>
            </zButton>
          </zTooltip>
          <zTooltip
            content="Link"
            placement="bottom"
          >
            <zButton variant="invisible">
              <template #leadingVisual>
                <Link />
              </template>
            </zButton>
          </zTooltip>
          <zDivider
            direction="vertical"
            height="20px"
          />
          <zTooltip
            content="File Added"
            placement="bottom"
          >
            <zButton variant="invisible">
              <template #leadingVisual>
                <FileAdded />
              </template>
            </zButton>
          </zTooltip>
          <zTooltip
            content="Search"
            placement="bottom"
          >
            <zButton variant="invisible">
              <template #leadingVisual>
                <Search />
              </template>
            </zButton>
          </zTooltip>
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
import { zTooltip } from '@/components/z-ui/tooltip'
import { zDivider } from '@/components/z-ui/divider'
import { Bold, Italic, Code, Link, FileAdded, Search } from '@/components/z-ui/icon/Octicons-vue'
import { zButton } from '@/components/z-ui/button'

const demo1Code = `<template>
  <div class="demo-actions">
    <zTooltip content="Bold" placement="bottom">
      <zButton variant="invisible">
        <template #leadingVisual><Bold /></template>
      </zButton>
    </zTooltip>
    <zTooltip content="Italic" placement="bottom">
      <zButton variant="invisible">
        <template #leadingVisual><Italic /></template>
      </zButton>
    </zTooltip>
    <zTooltip content="Code" placement="bottom">
      <zButton variant="invisible">
        <template #leadingVisual><Code /></template>
      </zButton>
    </zTooltip>
    <zTooltip content="Link" placement="bottom">
      <zButton variant="invisible">
        <template #leadingVisual><Link /></template>
      </zButton>
    </zTooltip>

    <zDivider direction="vertical" height="20px" />

    <zTooltip content="File Added" placement="bottom">
      <zButton variant="invisible">
        <template #leadingVisual><FileAdded /></template>
      </zButton>
    </zTooltip>
    <zTooltip content="Search" placement="bottom">
      <zButton variant="invisible">
        <template #leadingVisual><Search /></template>
      </zButton>
    </zTooltip>
  </div>
</template>

<script setup lang="ts">
import { zButton } from '@/components/z-ui/button'
import { zTooltip } from '@/components/z-ui/tooltip'
import { zDivider } from '@/components/z-ui/divider'
import { Bold, Italic, Code, Link, FileAdded, Search } from '@/components/z-ui/icon/Octicons-vue'
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