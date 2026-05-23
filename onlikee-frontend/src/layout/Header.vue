<template>
  <header class="header">
    <div class="header-inner">
      <a
        class="logo"
        href="/"
      >
        <LogoOnlikeeIcon size="32" />
        <span>onlikee</span>
      </a>

      <div class="nav-list">
        <div
          v-for="item in navigationItems"
          :key="item.name"
        >
          <div
            :class="['nav-link', { active: item.active }]"
            @click="navigateToPage(item)"
          >
            {{ item.name }}
          </div>
        </div>
      </div>

      <div class="user-menu">
        <div
          v-if="!userStore.userInfo"
          class="nav-link"
          @click="goToLogin"
        >
          登录
        </div>
        <Dropdown
          v-else
          side="outside-bottom"
        >
          <template #trigger>
            <Avatar
              :src="userStore.userInfo?.avatarUrl"
              :size="32"
            />
          </template>
          <template #content>
            <div class="user-header">
              <Avatar
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
            <ActionList role="menu">
              <ActionList.Divider />
              <ActionList.Item
                role="menuitem"
                @select="goToProfile"
              >
                <template #leadingVisual>
                  <PersonIcon />
                </template>
                个人资料
              </ActionList.Item>
              <ActionList.LinkItem
                href="/application"
                role="menuitem"
              >
                <template #leadingVisual>
                  <AppsIcon />
                </template>
                我的应用
              </ActionList.LinkItem>
              <ActionList.Divider />
              <ActionList.LinkItem
                href="/auth/logout"
                role="menuitem"
              >
                <template #leadingVisual>
                  <SignOutIcon />
                </template>
                退出登录
              </ActionList.LinkItem>
            </ActionList>
          </template>
        </Dropdown>
      </div>
    </div>
  </header>
</template>

<script setup lang="ts">
import { onMounted, ref, watch } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { useUserStore } from '@/stores/user'
import { Dropdown } from '@/components/z-ui/dropdown'
import { Avatar } from '@/components/z-ui/avatar'
import { ActionList } from '@/components/z-ui/action-list'
import { PersonIcon, AppsIcon, SignOutIcon, LogoOnlikeeIcon } from '@/components/octicons-vue3'

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

const goToProfile = () => {
  void enqueueNavigation(async () => {
    const nickname = userStore.userInfo?.nickname?.trim()
    if (!nickname) return

    const profilePath = `/@${encodeURIComponent(nickname)}`
    window.location.href = profilePath
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
  z-index: 9999;
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
  font-family: 'Arial Black', sans-serif;
  font-size: 24px;
  text-decoration: none;
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
    padding: 6px 8px;
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
