<template>
  <ComponentDocsPage>
    <ComponentDocsHeader
      title="UserMenu 用户菜单"
      description="由多个组件组成的用户菜单。"
    />

    <ComponentDocsSection title="基础用法">
      <template #description>
        使用 
        <code>Dropdown</code>、
        <code>Avatar</code>、
        <code>ActionList</code>、<code>ActionList.Item</code>、
        <code>Divider</code>、
        <code>Octicons-vue3</code>
        组合而成的用户菜单组件。
      </template>
      <ComponentDocsDemoBlock :code="demo1Code">
        <div class="user-menu">
          <Dropdown>
            <template #trigger>
              <Avatar
                src="https://avatars.githubusercontent.com/u/131276691?v=4"
                :size="42"
              />
            </template>
            <template #content>
              <div class="user-header">
                <Avatar
                  src="https://avatars.githubusercontent.com/u/131276691?v=4"
                  :size="36"
                />
                <div class="user-info">
                  <div class="user-name">
                    onlikee Studio
                  </div>
                  <div class="user-email">
                    hello@onlikee.audio
                  </div>
                </div>
              </div>
              <ActionList>
                <Divider />
                <ActionList.Item>
                  <Person class="menu-icon" />
                  个人资料
                </ActionList.Item>
                <ActionList.Item href="https://github.com/UnderHear">
                  <MarkGithub class="menu-icon" />
                  前往 GitHub
                </ActionList.Item>
                <Divider />
                <ActionList.Item>
                  <Gear class="menu-icon" />
                  设置
                </ActionList.Item>
                <Divider />
                <ActionList.Item>
                  <SignOut class="menu-icon" />
                  退出登录
                </ActionList.Item>
              </ActionList>
            </template>
          </Dropdown>
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
import { Dropdown } from '@/components/z-ui/dropdown'
import { Avatar } from '@/components/z-ui/avatar'
import { ActionList } from '@/components/z-ui/action-list'
import { Divider } from '@/components/z-ui/divider'
import { Person, MarkGithub, SignOut, Gear } from '@/components/octicons-vue3'

const demo1Code = `<template>
  <div class="user-menu">
    <Dropdown>
      <template #trigger>
        <Avatar src="https://avatars.githubusercontent.com/u/131276691?v=4" :size="42" />
      </template>
      <template #content>
        <div class="user-header">
          <Avatar src="https://avatars.githubusercontent.com/u/131276691?v=4" :size="36" />
          <div class="user-info">
            <div class="user-name">onlikee Studio</div>
            <div class="user-email">hello@onlikee.audio</div>
          </div>
        </div>
        <ActionList>
          <Divider />
          <ActionList.Item>
            <Person class="menu-icon" />
            个人资料
          </ActionList.Item>
          <ActionList.Item href="https://github.com/UnderHear">
            <MarkGithub class="menu-icon" />
            前往 GitHub
          </ActionList.Item>
          <Divider />
          <ActionList.Item>
            <Gear class="menu-icon" />
            设置
          </ActionList.Item>
          <Divider />
          <ActionList.Item>
            <SignOut class="menu-icon" />
            退出登录
          </ActionList.Item>
        </ActionList>
      </template>
    </Dropdown>
  </div>
</template>

<script setup lang="ts">
import { Dropdown } from '@/components/z-ui/dropdown'
import { Avatar } from '@/components/z-ui/avatar'
import { ActionList } from '@/components/z-ui/action-list'
import { Divider } from '@/components/z-ui/divider'
import { Person, MarkGithub, SignOut, Gear } from '@/components/octicons-vue3'
<\/script>

<style scoped>
.user-menu {
  position: absolute;
}

.user-header {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 12px 12px 0px 12px;
}

.user-info {
  display: flex;
  flex-direction: column;
}

.user-name {
  font-size: 14px;
  font-weight: 600;
  color: var(--fgColor-default);
}

.user-email {
  font-size: 12px;
  color: var(--fgColor-muted);
}

.menu-icon {
  color: var(--fgColor-muted);
}

@media (max-width: 767px) {
  .user-menu {
    position: relative;
    right: 0;
    margin-left: 0.5rem;
    height: 32px;
  }
}
</style>`

</script>

<style scoped>
.user-menu {
  position: absolute;
}

.user-header {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 12px 12px 0px 12px;
}

.user-info {
  display: flex;
  flex-direction: column;
}

.user-name {
  font-size: 14px;
  font-weight: 600;
  color: var(--fgColor-default);
}

.user-email {
  font-size: 12px;
  color: var(--fgColor-muted);
}

.menu-icon {
  color: var(--fgColor-muted);
}

@media (max-width: 767px) {
  .user-menu {
    position: relative;
    right: 0;
    margin-left: 0.5rem;
    height: 32px;
  }
}
</style>
