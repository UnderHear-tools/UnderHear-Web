<template>
  <ComponentDocsPage>
    <ComponentDocsHeader title="UserMenu 用户菜单" description="由多个组件组成的用户菜单。" />

    <ComponentDocsSection title="基础用法">
      <template #description>使用 <code>variant</code> 来定义链接的样式。</template>

      <ComponentDocsDemoBlock :code="demo1Code">
        <zDropdown>
          <template #trigger>
            <zAvatar src="https://avatars.githubusercontent.com/u/131276691?v=4" :size="32" />
          </template>
          <div class="user-header">
            <zAvatar src="https://avatars.githubusercontent.com/u/131276691?v=4" :size="36" />
            <div class="user-info">
              <div class="user-name">UnderHear Studio</div>
              <div class="user-email">hello@underhear.audio</div>
            </div>
          </div>
          <zMenu>
            <zDivider />
            <zMenuItem>
              <Person class="menu-icon" />
              个人资料
            </zMenuItem>
            <zMenuItem href="https://github.com/underhear">
              <MarkGithub class="menu-icon" />
              前往 GitHub
            </zMenuItem>
            <zDivider />
            <zMenuItem>
              <SignOut class="menu-icon" />
              退出登录
            </zMenuItem>
          </zMenu>
        </zDropdown>
      </ComponentDocsDemoBlock>
    </ComponentDocsSection>

    <ComponentDocsSection title="API" variant="api">
      <zTable
        :columns=""
        :data=""
        row-key="name"
        compact
        :hoverable="false"
      />
    </ComponentDocsSection>
  </ComponentDocsPage>
</template>

<script setup lang="ts">
import ComponentDocsDemoBlock from '@/module/components/components/ComponentDocsPage/ComponentDocsDemoBlock.vue'
import ComponentDocsHeader from '@/module/components/components/ComponentDocsPage/ComponentDocsHeader.vue'
import ComponentDocsPage from '@/module/components/components/ComponentDocsPage/ComponentDocsPage.vue'
import ComponentDocsSection from '@/module/components/components/ComponentDocsPage/ComponentDocsSection.vue'
</script>

<style scoped>

</style>
