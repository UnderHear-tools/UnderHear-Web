<template>
  <ComponentDocsPage>
    <ComponentDocsHeader title="ActionBar 操作栏" description="由多个组件组合而成的工具栏，常用于编辑器顶部。" />

    <ComponentDocsSection title="基础用法">
      <template #description>
        使用
        <code>Tooltip</code>、
        <code>Divider</code>、
        <code>Octicons-vue</code>
        组合而成的操作栏组件。
      </template>
      <ComponentDocsDemoBlock :code="demo1Code">
        <div class="demo-actions">
          <zTooltip content="Bold" placement="bottom">
            <button class="action-btn"><Bold /></button>
          </zTooltip>
          <zTooltip content="Italic" placement="bottom">
            <button class="action-btn"><Italic /></button>
          </zTooltip>
          <zTooltip content="Code" placement="bottom">
            <button class="action-btn"><Code /></button>
          </zTooltip>
          <zTooltip content="Link" placement="bottom">
            <button class="action-btn"><Link /></button>
          </zTooltip>
          <zDivider direction="vertical" height="20px" />
          <zTooltip content="File Added" placement="bottom">
            <button class="action-btn"><FileAdded /></button>
          </zTooltip>
          <zTooltip content="Search" placement="bottom">
            <button class="action-btn"><Search /></button>
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

const demo1Code = `<template>
  <div class="demo-actions">
    <zTooltip content="Bold" placement="bottom">
      <button class="action-btn"><Bold /></button>
    </zTooltip>
    <zTooltip content="Italic" placement="bottom">
      <button class="action-btn"><Italic /></button>
    </zTooltip>
    <zTooltip content="Code" placement="bottom">
      <button class="action-btn"><Code /></button>
    </zTooltip>
    <zTooltip content="Link" placement="bottom">
      <button class="action-btn"><Link /></button>
    </zTooltip>

    <zDivider direction="vertical" height="20px" />

    <zTooltip content="File Added" placement="bottom">
      <button class="action-btn"><FileAdded /></button>
    </zTooltip>
    <zTooltip content="Search" placement="bottom">
      <button class="action-btn"><Search /></button>
    </zTooltip>
  </div>
</template>

<script setup lang="ts">
import { zTooltip } from '@/components/z-ui/tooltip'
import { zDivider } from '@/components/z-ui/divider'
import { Bold, Italic, Code, Link, FileAdded, Search } from '@/components/z-ui/icon/Octicons-vue'
<\\/script>

<style scoped>
.demo-actions {
  display: flex;
  gap: 8px;
  justify-content: flex-end;
  align-items: center;
}

.action-btn {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 32px;
  height: 32px;
  border: none;
  color: var(--fgColor-muted);
  cursor: pointer;
  border-radius: 4px;
}

.action-btn:hover {
  background-color: var(--button-invisible-bgColor-hover);
}
<\\/style>`
</script>

<style scoped>
.demo-actions {
  display: flex;
  gap: 8px;
  justify-content: flex-end;
  align-items: center;
}

.action-btn {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 32px;
  height: 32px;
  border: none;
  color: var(--fgColor-muted);
  cursor: pointer;
  border-radius: 4px;
}

.action-btn:hover {
  background-color: var(--button-invisible-bgColor-hover);
}
</style>