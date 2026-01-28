<template>
  <header class="header">
    <div class="header-inner">
      <nav>
        <ul class="nav-list">
          <li v-for="item in navigationItems" :key="item.name">
            <div :class="['nav-link', { active: item.active }]" @click="navigateToPage(item)">
              {{ item.name }}
            </div>
          </li>
        </ul>
      </nav>

      <div class="user-menu">
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
      </div>
    </div>
  </header>
</template>

<script setup lang="ts">
import { onMounted, ref, watch } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { zDropdown } from '@/components/z-ui/dropdown'
import { zAvatar } from '@/components/z-ui/avatar'
import { zMenu, zMenuItem } from '@/components/z-ui/menu'
import { zDivider } from '@/components/z-ui/divider'
import { MarkGithub, SignOut } from '@/components/z-ui/icon/Octicons-vue'

interface NavigationItem {
  name: string
  href: string
  active: boolean
}

const router = useRouter()
const route = useRoute()

const navigationItems = ref<NavigationItem[]>([
  { name: '首页', href: '/', active: false },
  { name: '应用', href: '/application', active: false },
  { name: '组件', href: '/component', active: false },
  { name: '名片', href: '/namecard', active: false }
])

const updateActiveState = () => {
  navigationItems.value.forEach(navItem => {
    navItem.active = navItem.href === route.path
  })
}

const navigateToPage = (item: NavigationItem) => {
  router.push(item.href)
}

onMounted(() => {
  updateActiveState()
})

watch(() => route.path, () => {
  updateActiveState()
})
</script>

<style scoped>
.header {
  position: sticky;
  top: 0;
  z-index: 50;
  width: 100%;
  background: #f8f8f8;
  backdrop-filter: blur(40px);
  -webkit-backdrop-filter: blur(40px);
  box-shadow: 0 1px 1px rgba(220, 220, 220, 1);
}

.header-inner {
  position: relative;
  display: flex;
  height: 4rem;
  padding: 6px 8px;
  align-items: center;
  justify-content: center;
}

.nav-list {
  display: flex;
  gap: 1.5rem;
}

.nav-link {
  display: flex;
  align-items: center;
  padding: 8px 10px;
  border-radius: 6px;
  font-size: 16px;
  font-weight: 500;
  line-height: 1;
  color: #727272;
  cursor: pointer;
  transition: background-color 0.2s ease, color 0.2s ease;
}

.nav-link:hover,
.nav-link.active {
  background-color: #e6e6e6;
  color: #000000;
}

.user-menu {
  position: absolute;
  right: 2rem;
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
  color: #1f2328;
}

.user-email {
  font-size: 12px;
  color: #656d76;
}

.menu-icon {
  color: #59636e;
}

@media (max-width: 767px) {
  .header {
    top: 1rem;
    width: fit-content;
    margin: 0 auto;
    margin-top: 1rem;
    border-radius: 24px;
    box-shadow: none;
  }

  .header-inner {
    height: auto;
  }

  .nav-list {
    gap: 0.5rem;
  }

  .nav-link {
    padding-left: 12px;
    padding-right: 12px;
    border-radius: 16px;
  }

  .user-menu {
    position: relative;
    right: 0;
    margin-left: 0.5rem;
    height: 32px;
  }
}
</style>
