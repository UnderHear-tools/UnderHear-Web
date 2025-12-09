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
        <zUserMenu />
      </div>
    </div>
  </header>
</template>

<script setup lang="ts">
import { onMounted, ref, watch } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { zUserMenu } from '@/components/z-ui/user-menu/zUserMenu'

interface NavigationItem {
  name: string
  href: string
  active: boolean
}

const router = useRouter()
const route = useRoute()

const navigationItems = ref<NavigationItem[]>([
  { name: '首页', href: '/', active: false },
  { name: '工具', href: '/tool', active: false },
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
  background: rgba(245, 245, 245, 0.7);
  backdrop-filter: blur(40px);
  -webkit-backdrop-filter: blur(40px);
  box-shadow: 0 1px 1px rgba(220, 220, 220, 1);
}

.header-inner {
  position: relative;
  display: flex;
  height: 5rem;
  align-items: center;
  justify-content: center;
  padding: 6px 8px;
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

@media (max-width: 767px) {
  .header {
    top: 1rem;
    width: fit-content;
    margin: 0 auto;
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
