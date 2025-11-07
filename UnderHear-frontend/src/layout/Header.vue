<template>
  <header
    class="sticky top-0 z-50 w-full bg-[#F5F5F5]/70 backdrop-blur-2xl 
    shadow-[0_1px_1px_rgba(220,220,220,1)] md:top-0 max-md:top-4 max-md:w-fit 
    max-md:mx-auto max-md:rounded-[24px] max-md:shadow-none">
    <div class="relative flex h-20 items-center justify-center max-md:h-fit py-[6px] px-[8px] md:px-8">
      <nav>
        <ul class="flex space-x-6 max-md:space-x-3">
          <li v-for="item in navigationItems" :key="item.name">
            <div :class="[
              'px-2.5 py-2 rounded-md text-[16px] font-medium leading-none flex items-center max-md:rounded-[16px] max-md:px-3 cursor-pointer',
              item.active
                ? 'bg-[#E6E6E6] text-[#000000]'
                : 'text-[#727272] hover:bg-[#E6E6E6] hover:text-[#000000]'
            ]" @click="navigateToPage(item)">
              {{ item.name }}
          </div>
          </li>
        </ul>
      </nav>

      <div v-if="currentUser" class="absolute right-8 max-md:relative max-md:right-0 max-md:ml-4">
        <zUserMenu :user="currentUser" :menu-items="userMenuItems" @item-click="handleMenuItemClick" />
      </div>

      <button
        v-else
        type="button"
        class="absolute right-8 max-md:relative max-md:right-0 max-md:ml-4 text-sm font-medium text-[#111111] transition-opacity hover:opacity-70"
        @click="handleSignIn"
      >
        登录
      </button>
    </div>
  </header>
</template>

<script setup lang="ts">
import { onMounted, onUnmounted, ref, watch, computed } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { getStoredUser, logout, type UserProfile } from '@/api/auth'
import { zUserMenu } from '@/components/z-ui/user-menu/zUserMenu'
import type { MenuItem } from '@/components/z-ui/user-menu/zUserMenu'
import { icons } from '@/components/z-ui/user-menu/icons'

interface NavigationItem {
  name: string
  href: string
  active: boolean
}

const router = useRouter()
const route = useRoute()
const currentUser = ref<UserProfile | null>(getStoredUser())

const navigationItems = ref<NavigationItem[]>([
  { name: '首页', href: '/', active: true },
  { name: '工具', href: '/tool', active: false },
  { name: '组件', href: '/component', active: false },
  { name: '名片', href: '/namecard', active: false }
])

const userMenuItems = computed<MenuItem[]>(() => [
  {
    label: 'GitHub 主页',
    icon: icons.person,
    href: currentUser.value?.htmlUrl || '',
    external: true
  },
  { type: 'divider' } as MenuItem,
  {
    label: '设置',
    icon: icons.gear,
    onClick: () => {
      // 可以后续添加设置页面
      console.log('设置')
    }
  },
  {
    label: '外观',
    icon: icons.paintbrush,
    onClick: () => {
      // 可以后续添加主题切换功能
      console.log('外观设置')
    }
  },
  { type: 'divider' } as MenuItem,
  {
    label: '退出登录',
    icon: icons.signOut,
    onClick: () => {
      handleLogout()
    }
  }
])

const updateActiveState = () => {
  navigationItems.value.forEach(navItem => {
    navItem.active = navItem.href === route.path
  })
}

const refreshUser = () => {
  currentUser.value = getStoredUser()
}

const navigateToPage = (item: NavigationItem) => {
  router.push(item.href)
}

const handleSignIn = () => {
  router.push('/auth/login')
}

const handleLogout = () => {
  logout()
  refreshUser()
  router.push('/')
}

const handleMenuItemClick = (item: MenuItem) => {
  // 菜单项点击事件已在 menuItems 的 onClick 中处理
  console.log('Menu item clicked:', item.label)
}

const handleStorageChange = (event: StorageEvent) => {
  if (event.key === 'underhear_user') {
    refreshUser()
  }
}

onMounted(() => {
  refreshUser()
  window.addEventListener('storage', handleStorageChange)
})

onUnmounted(() => {
  window.removeEventListener('storage', handleStorageChange)
})

watch(() => route.path, () => {
  updateActiveState()
  refreshUser()
})
</script>

<style scoped></style>