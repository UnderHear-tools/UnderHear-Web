<template>
  <header
    class="sticky top-0 z-50 w-full bg-[#F5F5F5]/70 backdrop-blur-2xl 
    shadow-[0_1px_1px_rgba(220,220,220,1)] md:top-0 max-md:top-4 max-md:w-fit 
    max-md:mx-auto max-md:rounded-[24px] max-md:shadow-none">
    <div class="flex h-20 items-center justify-center max-md:h-fit py-[6px] px-[8px]">
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
    </div>
  </header>
</template>

<script setup lang="ts">
import { ref, watch } from 'vue'
import { useRouter, useRoute } from 'vue-router'

// Types
interface NavigationItem {
  name: string
  href: string
  active: boolean
}

const router = useRouter()
const route = useRoute()

// Default navigation data
const navigationItems = ref<NavigationItem[]>([
  { name: '首页', href: '/', active: true },
  { name: '工具', href: '/tool', active: false },
  { name: '组件', href: '/component', active: false },
  { name: '名片', href: '/namecard', active: false }
])

// Update active state based on current route
const updateActiveState = () => {
  navigationItems.value.forEach(navItem => {
    navItem.active = navItem.href === route.path
  })
}

// Methods
const navigateToPage = (item: NavigationItem) => {
  router.push(item.href)
}

// Watch for route changes
watch(() => route.path, () => {
  updateActiveState()
})
</script>

<style scoped></style>