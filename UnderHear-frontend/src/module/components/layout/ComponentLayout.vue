<template>
  <div class="component-layout">
    <!-- 移动端菜单按钮 -->
    <button class="mobile-menu-btn" @click="toggleSidebar" aria-label="切换菜单">
      <svg width="24" height="24" viewBox="0 0 24 24" fill="none">
        <path d="M3 12h18M3 6h18M3 18h18" stroke="#d1d9e0" stroke-width="2" stroke-linecap="round" />
      </svg>
    </button>

    <!-- 遮罩层 -->
    <div v-if="sidebarOpen" class="sidebar-overlay" @click="closeSidebar"></div>

    <!-- 左侧导航栏 -->
    <aside class="sidebar" :class="{ open: sidebarOpen }">
      <!-- 可滚动的导航内容区域 -->
      <div class="sidebar-content">
        <div v-for="section in navSections" :key="section.title" class="nav-section">
          <div class="section-title">{{ section.title }}</div>
          <RouterLink v-for="item in section.items" :key="item.path" :to="item.path" class="nav-item"
            :class="{ active: $route.path === item.path }" @click="closeSidebar">
            {{ item.label }}
          </RouterLink>
        </div>
      </div>
    </aside>

    <!-- 主内容区域 -->
    <main class="main-content">
      <router-view />
    </main>
  </div>
</template>

<script setup>
import { ref } from 'vue'

const sidebarOpen = ref(false)

const toggleSidebar = () => {
  sidebarOpen.value = !sidebarOpen.value
}

const closeSidebar = () => {
  sidebarOpen.value = false
}

const navSections = ref([
  {
    title: 'Overview 组件总览',
    items: [
      { path: '/component/guide', label: 'Guide 指南' },
      { path: '/component/overview', label: 'Overview 组件总览' }
    ]
  },
  {
    title: 'Basic 基础组件',
    items: [
      { path: '/component/container', label: 'Container 布局容器' },
      { path: '/component/link', label: 'Link 链接' },
      { path: '/component/divider', label: 'Divider 分隔线' },
      { path: '/component/tag', label: 'Tag 标签' }
    ]
  },
  {
    title: 'Form 表单组件',
    items: [
      { path: '/component/input', label: 'Input 输入框' },
      { path: '/component/select', label: 'Select 选择器' }
    ]
  }
])
</script>

<style scoped>
.component-layout {
  display: flex;
}

.sidebar {
  width: 300px;
  position: fixed;
  left: 0;
  height: calc(100vh - 64px);
  display: flex;
  flex-direction: column;
}

.sidebar-content {
  flex: 1;
  overflow-y: auto;
  scrollbar-color: rgb(192, 192, 192) #ffffff;
  padding: 2rem;
}

.nav-section {
  display: grid;
  gap: 10px;
}

.section-title {
  font-size: 1rem;
  font-weight: 600;
  color: var(--font-black);
  margin-top: 1.5rem;
  margin-bottom: 0.5rem;
}

.nav-item {
  display: block;
  padding: 0.5rem 1rem;
  color: var(--font-gray);
  text-decoration: none;
  font-size: 0.875rem;
  transition: all 0.2s ease;
  position: relative;
  border-radius: 6px;
}

.nav-item:hover {
  background-color: #f0f0f0;
  color: var(--font-black);
}

.nav-item.active {
  background-color: #e2e2e2;
  color: #000000;
}

.main-content {
  flex: 1;
  margin-left: 300px;
  margin-right: 300px;
  padding: 2rem;
  min-height: calc(100vh - 64px);
}

/* 默认隐藏移动端菜单按钮 */
.mobile-menu-btn {
  display: none;
}

.sidebar-overlay {
  display: none;
}

/* 响应式设计 */
@media (max-width: 1420px) {
  .main-content {
    margin-right: 0px;
  }
}

@media (max-width: 768px) {
  .sidebar {
    top: 0;
    transform: translateX(-100%);
    transition: transform 0.3s ease;
    z-index: 1000;
    height: 100vh;
  }

  .sidebar-content{
    scrollbar-width: none;
    background-color: #fff;
  }

  /* 移动端菜单按钮样式 */
  .mobile-menu-btn {
    display: flex;
    position: fixed;
    top: 50%;
    height: 3rem;
    width: 3rem;
    border-radius: 50%;
    transform: translate(-50%,-50%);
    cursor: pointer;
    align-items: center;
    justify-content: center;
    transition: all 0.2s ease;
    border: 1px solid var(--border-gray);
  }

  /* 遮罩层 */
  .sidebar-overlay {
    display: block;
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    height: 100vh;
    width: 100vw;
    background: rgba(0, 0, 0, 0.5);
    z-index: 999;
    animation: fadeIn 0.3s ease;
  }

  @keyframes fadeIn {
    from {
      opacity: 0;
    }

    to {
      opacity: 1;
    }
  }

  .sidebar.open {
    transform: translateX(0);
  }

  .main-content {
    margin-left: 0;
  }
}
</style>