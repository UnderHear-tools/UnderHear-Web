<template>
  <header class="header">
    <div class="header-inner">

      <a class="logo" href="/">
        <LogoUnderHear size="32" />
        <span>UnderHear</span>
      </a>

      <nav>
        <ul class="nav-list">
          <li
            v-for="item in navigationItems"
            :key="item.name"
          >
            <div
              :class="['nav-link', { active: item.active }]"
              @click="navigateToPage(item)"
            >
              {{ item.name }}
            </div>
          </li>
        </ul>
      </nav>

      <div class="user-menu">
        <div
          v-if="!userStore.userInfo"
          class="nav-link"
          @click="goToLogin"
        >
          登录
        </div>
        <zDropdown
          v-else
          side="outside-bottom"
        >
          <template #trigger>
            <zAvatar
              :src="userStore.userInfo?.avatarUrl"
              :size="32"
            />
          </template>
          <template #content>
            <div class="user-header">
              <zAvatar
                :src="userStore.userInfo?.avatarUrl"
                :size="36"
              />
              <div class="user-info">
                <div class="user-name">
                  {{ userStore.userInfo?.nickname }}
                </div>
                <div class="user-email">
                  {{ userStore.userInfo?.email ?? '暂未设置邮箱' }}
                </div>
              </div>
            </div>
            <ActionList>
              <zDivider />
              <ActionList.Item>
                <Person class="menu-icon" />
                个人资料
              </ActionList.Item>
              <ActionList.Item
                href="https://github.com/underhear"
                new-tab
              >
                <MarkGithub class="menu-icon" />
                前往 GitHub
              </ActionList.Item>
              <zDivider />
              <ActionList.Item href="/auth/logout">
                <SignOut class="menu-icon" />
                退出登录
              </ActionList.Item>
            </ActionList>
          </template>
        </zDropdown>
      </div>
    </div>
  </header>
</template>

<script setup lang="ts">
import { onMounted, ref, watch } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { useUserStore } from '@/stores/user'
import { zDropdown } from '@/components/z-ui/dropdown'
import { zAvatar } from '@/components/z-ui/avatar'
import { ActionList } from '@/components/z-ui/action-list'
import { zDivider } from '@/components/z-ui/divider'
import { Person, MarkGithub, SignOut, LogoUnderHear } from '@/components/z-ui/icon/Octicons-vue'

interface NavigationItem {
  name: string
  href: string
  active: boolean
}

const router = useRouter()
const route = useRoute()
const userStore = useUserStore()

// 导航队列，确保导航操作按顺序执行，避免重复点击导致的竞态问题
// 如跳转到登录页后快速返回并切换导航项再次跳转登录会丢失?return_to=...参数
let navigationQueue: Promise<void> = Promise.resolve()

// 队列函数
const enqueueNavigation = (task: () => Promise<void> | void) => {
  navigationQueue = navigationQueue
    .catch(() => undefined)
    .then(async () => {
      await task()
    })

  return navigationQueue
}

const goToLogin = () => {
  //加入队列
  void enqueueNavigation(async () => {
    const returnTo = encodeURIComponent(window.location.href)
    window.location.href = `/auth/login?return_to=${returnTo}`
  })
}

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
  //加入队列
  void enqueueNavigation(async () => {
    if (route.path === item.href) return
    await router.push(item.href)
  })
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
  z-index: 999;
  width: 100%;
  background: var(--bgColor-muted);
  backdrop-filter: blur(40px);
  -webkit-backdrop-filter: blur(40px);
  box-shadow: inset 0 calc(var(--borderWidth-thin, 1px) * -1) var(--borderColor-default);
}

.header-inner {
  position: relative;
  display: flex;
  height: 4rem;
  padding: 6px 8px;
  align-items: center;
  justify-content: center;
}

.logo {
  position: absolute;
  left: 2rem;
  color: var(--fgColor-default);
  display: flex;
  align-items: center;
  gap: 10px;
  font-weight: bold;
  cursor: pointer;
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
  color: var(--fgColor-muted);
  cursor: pointer;
  transition: background-color 0.2s ease, color 0.2s ease;
}

.nav-link.active {
  background-color: var(--bgColor-neutral-muted);
  color: var(--fgColor-default);
}

.nav-link:active{
  background-color: var(--bgColor-neutral-muted);
  color: var(--fgColor-default);
}

@media (hover: hover) and (pointer: fine) {
  .nav-link:hover {
    background-color: var(--bgColor-neutral-muted);
    color: var(--fgColor-default);
  }
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
  min-width: 128px;
}

.user-name {
  font-size: 14px;
  font-weight: 600;
  color: var(--fgColor-default);
}

.user-email {
  font-size: 12px;
  color: var(--fgColor-muted);
  white-space: nowrap;
}

.menu-icon {
  color: var(--fgColor-muted);
}

@media (max-width: 768px) {
  .header {
    position: fixed;
    top: 1rem;
    left: 0;
    right: 0;
    width: max-content;
    margin-inline: auto;
    transform: none;
    border-radius: 24px;
    box-shadow: none;
  }

  .header-inner {
    height: auto;
  }

  .logo{
    display: none;
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
