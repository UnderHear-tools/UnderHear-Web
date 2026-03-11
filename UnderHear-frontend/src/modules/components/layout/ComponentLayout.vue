<template>
  <div class="component-layout">
    <!-- 移动端菜单按钮 -->
    <button
      class="mobile-menu-btn"
      aria-label="切换菜单"
      @click="toggleSidebar"
    >
      <svg
        width="24"
        height="24"
        viewBox="0 0 24 24"
        fill="none"
      >
        <path
          d="M3 12h18M3 6h18M3 18h18"
          stroke="#d1d9e0"
          stroke-width="2"
          stroke-linecap="round"
        />
      </svg>
    </button>

    <!-- 遮罩层 -->
    <div
      v-if="sidebarOpen"
      class="sidebar-overlay"
      @click="closeSidebar"
    />

    <!-- 左侧导航栏 -->
    <aside
      class="sidebar"
      :class="{ open: sidebarOpen }"
    >
      <!-- 可滚动的导航内容区域 -->
      <div class="sidebar-content">
        <div
          v-for="section in navSections"
          :key="section.title"
          class="nav-section"
        >
          <div class="section-title">
            {{ section.title }}
          </div>
          <RouterLink
            v-for="item in section.items"
            :key="item.path"
            :to="item.path"
            class="nav-item"
            :class="{ active: $route.path === item.path }"
            @click="closeSidebar"
          >
            {{ item.label }}
          </RouterLink>
        </div>
      </div>
    </aside>

    <!-- 主内容区域 -->
    <main
      ref="mainContentRef"
      class="main-content"
    >
      <router-view />
    </main>

    <!-- 右侧目录区 -->
    <aside class="toc-sidebar">
      <div class="toc-content">
        <div class="toc-title">
          目录
        </div>
        <nav class="toc-nav">
          <a
            v-for="item in tocItems"
            :key="item.id"
            class="toc-link"
            :class="[`toc-${item.level}`, { active: activeTocId === item.id }]"
            :href="`#${item.id}`"
            @click="handleTocClick(item.id)"
          >
            {{ item.text }}
          </a>
        </nav>
      </div>
    </aside>
  </div>
</template>

<script setup>
import { nextTick, onBeforeUnmount, onMounted, ref, watch } from 'vue'
import { useRoute } from 'vue-router'

const sidebarOpen = ref(false)
const mainContentRef = ref(null)
const tocItems = ref([])
const tocHeadings = ref([])
const activeTocId = ref('')
const lockedTocId = ref('')
const scrollTicking = ref(false)
const route = useRoute()

const toggleSidebar = () => {
  sidebarOpen.value = !sidebarOpen.value
}

const closeSidebar = () => {
  sidebarOpen.value = false
}

const handleTocClick = (id) => {
  lockedTocId.value = id
  activeTocId.value = id
}

const setActiveFromScroll = () => {
  const headings = tocHeadings.value
  if (!headings.length) {
    activeTocId.value = ''
    return
  }

  if (lockedTocId.value) {
    const lockedHeading = headings.find((heading) => heading.id === lockedTocId.value)
    if (lockedHeading) {
      const rect = lockedHeading.getBoundingClientRect()
      const isVisible = rect.bottom > 0 && rect.top < window.innerHeight
      if (isVisible) {
        activeTocId.value = lockedTocId.value
        return
      }
    }
    lockedTocId.value = ''
  }

  const offset = 120
  let current = headings[0]

  for (const heading of headings) {
    const top = heading.getBoundingClientRect().top - offset
    if (top <= 0) {
      current = heading
    } else {
      break
    }
  }

  activeTocId.value = current.id
}

const handleScroll = () => {
  if (scrollTicking.value) {
    return
  }

  scrollTicking.value = true
  requestAnimationFrame(() => {
    setActiveFromScroll()
    scrollTicking.value = false
  })
}

const updateToc = () => {
  const container = mainContentRef.value
  if (!container) {
    tocItems.value = []
    tocHeadings.value = []
    activeTocId.value = ''
    return
  }

  const headings = Array.from(container.querySelectorAll('h2, h3'))
  const items = headings.map((heading) => {
    const text = heading.textContent
    const id = text
    heading.id = id

    return {
      id,
      text,
      level: heading.tagName.toLowerCase()
    }
  })

  tocHeadings.value = headings
  tocItems.value = items
  setActiveFromScroll()
}

const refreshToc = async () => {
  await nextTick()
  updateToc()
}

onMounted(() => {
  window.addEventListener('scroll', handleScroll, { passive: true })
  refreshToc()
})

onBeforeUnmount(() => {
  window.removeEventListener('scroll', handleScroll)
})

watch(
  () => route.fullPath,
  () => {
    refreshToc()
  }
)

const navSections = ref([
  {
    title: 'Overview 组件总览',
    items: [
      { path: '/component/guide', label: 'Guide 指南' },
      { path: '/component/overview', label: 'Overview 组件总览' }
    ]
  },
  {
    title: 'Icon 图标',
    items: [{ path: '/component/octicons-vue', label: 'Octicons-vue 图标' }]
  },
  {
    title: 'Blocks 组合式组件',
    items: [
      { path: '/component/action-bar', label: 'ActionBar 操作栏' },
      { path: '/component/user-menu', label: 'UserMenu 用户菜单' }
    ]
  },
  {
    title: 'Basic 基础组件',
    items: [
      { path: '/component/container', label: 'Container 布局容器' },
      { path: '/component/button', label: 'Button 按钮' },
      { path: '/component/link', label: 'Link 链接' },
      { path: '/component/divider', label: 'Divider 分隔线' },
      { path: '/component/avatar', label: 'Avatar 头像' },
      { path: '/component/dropdown', label: 'Dropdown 下拉菜单' },
      { path: '/component/menu', label: 'Menu 菜单' },
      { path: '/component/tag', label: 'Tag 标签' }
    ]
  },
  {
    title: 'Form 表单组件',
    items: [
      { path: '/component/form-control', label: 'FormControl 表单控件' },
      { path: '/component/input', label: 'Input 输入框' },
      { path: '/component/textarea', label: 'Textarea 文本域' },
      { path: '/component/select', label: 'Select 选择器' },
      { path: '/component/upload', label: 'Upload 上传' }
    ]
  },
  {
    title: 'Data 数据展示',
    items: [
      { path: '/component/table', label: 'Table 表格' },
      { path: '/component/timeline', label: 'TimeLine 时间线' }
    ]
  },
  {
    title: 'Navigation 导航',
    items: [{ path: '/component/steps', label: 'Steps 步骤条' }]
  },
  {
    title: 'Feedback 反馈组件',
    items: [
      { path: '/component/banner', label: 'Banner 横幅提示' },
      { path: '/component/tooltip', label: 'Tooltip 文字提示' }
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
  height: calc(100vh - var(--header-height));
  display: flex;
  flex-direction: column;
  border-right: 1px solid var(--borderColor-default);
}

.sidebar-content {
  flex: 1;
  overflow-y: auto;
  padding: 2rem;
}

.nav-section {
  display: grid;
  gap: 10px;
}

.section-title {
  font-size: 1rem;
  font-weight: 600;
  color: var(--fgColor-default);
  margin-top: 1.5rem;
  margin-bottom: 0.5rem;
}

.nav-item {
  display: block;
  padding: 0.5rem 1rem;
  color: var(--fgColor-muted);
  text-decoration: none;
  font-size: 0.875rem;
  transition: all 0.2s ease;
  position: relative;
  border-radius: 6px;
}

.nav-item:hover {
  background-color: var(--control-bgColor-hover);
  color: var(--fgColor-default);
}

.nav-item.active {
  background-color: var(--control-bgColor-active);
  color: var(--fgColor-default);
}

.nav-item.active::before {
  content: '';
  position: absolute;
  left: -8px;
  top: 50%;
  bottom: 0;
  width: 4px;
  height: 30px;
  transform: translate(0, -50%);
  background-color: var(--bgColor-accent-emphasis);
  border-radius: 2px;
}

.main-content {
  flex: 1;
  margin-left: 300px;
  margin-right: 300px;
  padding: 2rem;
  min-height: calc(100vh - var(--header-height));
  min-width: 0;
}

/* 右侧目录区 */
.toc-sidebar {
  width: 300px;
  position: fixed;
  right: 0;
  height: calc(100vh - var(--header-height));
  display: flex;
  flex-direction: column;
  border-left: 1px solid var(--borderColor-default);
  background-color: var(--bgColor-default);
}

.toc-content {
  flex: 1;
  overflow-y: auto;
  padding: 2rem 1.5rem;
  scrollbar-width: thin;
  scrollbar-color: var(--borderColor-emphasis) var(--bgColor-default);
}

.toc-title {
  font-weight: bold;
  color: var(--fgColor-default);
  margin-bottom: 1rem;
  text-transform: uppercase;
  letter-spacing: 0.05em;
}

.toc-nav {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.toc-link {
  color: var(--fgColor-muted);
  text-decoration: none;
  font-size: 0.875rem;
  line-height: 1.5;
  padding: 0.25rem 0 0.25rem 0.75rem;
  border-left: 2px solid var(--borderColor-transparent);
  transition: all 0.2s ease;
}

.toc-link:hover {
  color: var(--fgColor-default);
  border-left-color: var(--borderColor-default);
}

.toc-link.active {
  color: var(--fgColor-link);
  border-left-color: var(--borderColor-accent-emphasis);
}

.toc-h3 {
  padding-left: 1.5rem;
  font-size: 0.8125rem;
}

.main-content :deep(h1),
.main-content :deep(h2),
.main-content :deep(h3) {
  scroll-margin-top: 90px;
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

  .toc-sidebar {
    display: none;
  }
}

@media (max-width: 768px) {
  .main-content {
    padding: 1.5rem;
    padding-top: 4rem;
  }

  .sidebar {
    top: 0;
    transform: translateX(-100%);
    transition: transform 0.3s ease;
    z-index: 1000;
    height: 100vh;
  }

  .sidebar-content {
    scrollbar-width: none;
    background-color: var(--bgColor-default);
  }

  /* 移动端菜单按钮样式 */
  .mobile-menu-btn {
    display: flex;
    position: fixed;
    top: 50%;
    height: 3rem;
    width: 3rem;
    border-radius: 50%;
    transform: translate(-50%, -50%);
    cursor: pointer;
    align-items: center;
    justify-content: center;
    transition: all 0.2s ease;
    border: 1px solid var(--borderColor-default);
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
    background: color-mix(in srgb, var(--fgColor-default) 50%, var(--bgColor-transparent));
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
