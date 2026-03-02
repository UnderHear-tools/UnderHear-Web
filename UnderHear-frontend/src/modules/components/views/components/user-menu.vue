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
        <code>Menu</code>、<code>MenuItem</code>、
        <code>Divider</code>、
        <code>Octicons-vue</code>
        组合而成的用户菜单组件。
      </template>
      <ComponentDocsDemoBlock :code="demo1Code">
        <div class="user-menu">
          <zDropdown>
            <template #trigger>
              <zAvatar
                src="https://avatars.githubusercontent.com/u/131276691?v=4"
                :size="42"
              />
            </template>
            <template #content>
              <div class="user-header">
                <zAvatar
                  src="https://avatars.githubusercontent.com/u/131276691?v=4"
                  :size="36"
                />
                <div class="user-info">
                  <div class="user-name">
                    UnderHear Studio
                  </div>
                  <div class="user-email">
                    hello@underhear.audio
                  </div>
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
                  <Gear class="menu-icon" />
                  设置
                </zMenuItem>
                <zDivider />
                <zMenuItem>
                  <SignOut class="menu-icon" />
                  退出登录
                </zMenuItem>
              </zMenu>
            </template>
          </zDropdown>
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
import { zDropdown } from '@/components/z-ui/dropdown'
import { zAvatar } from '@/components/z-ui/avatar'
import { zMenu, zMenuItem } from '@/components/z-ui/menu'
import { zDivider } from '@/components/z-ui/divider'
import { Person, MarkGithub, SignOut, Gear } from '@/components/z-ui/icon/Octicons-vue'

const demo1Code = `<template>
  <div class="user-menu">
    <zDropdown>
      <template #trigger>
        <zAvatar src="https://avatars.githubusercontent.com/u/131276691?v=4" :size="42" />
      </template>
      <template #content>
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
            <Gear class="menu-icon" />
            设置
          </zMenuItem>
          <zDivider />
          <zMenuItem>
            <SignOut class="menu-icon" />
            退出登录
          </zMenuItem>
        </zMenu>
      </template>
    </zDropdown>
  </div>
</template>

<script setup lang="ts">
import { zDropdown } from '@/components/z-ui/dropdown'
import { zAvatar } from '@/components/z-ui/avatar'
import { zMenu, zMenuItem } from '@/components/z-ui/menu'
import { zDivider } from '@/components/z-ui/divider'
import { Person, MarkGithub, SignOut, Gear } from '@/components/z-ui/icon/Octicons-vue'
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
