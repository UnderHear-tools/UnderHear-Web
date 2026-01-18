<template>
  <div class="component-layout">
    <!-- 左侧导航栏 -->
    <aside class="sidebar">
      <!-- 可滚动的导航内容区域 -->
      <div class="sidebar-content">
        <div 
          v-for="section in navSections" 
          :key="section.title" 
          class="nav-section"
        >
          <div class="section-title">{{ section.title }}</div>
          <router-link 
            v-for="item in section.items" 
            :key="item.path"
            :to="item.path" 
            class="nav-item"
            :class="{ active: $route.path === item.path }"
          >
            {{ item.label }}
          </router-link>
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

/* 底部导航 */
.bottom-nav {
  flex-shrink: 0;
  padding: 1rem 0;
  border-top: 1px solid #e5e7eb;
  background-color: #fff;
  display: flex;
  justify-content: center;
  gap: 0.5rem;
}

/* 响应式设计 */
@media (max-width: 1420px) {
  .main-content {
    margin-right: 0px;
  }
}
@media (max-width: 768px) {
  .sidebar {
    transform: translateX(-100%);
    transition: transform 0.3s ease;
  }
  
  .main-content {
    margin-left: 0;
  }
}
</style>