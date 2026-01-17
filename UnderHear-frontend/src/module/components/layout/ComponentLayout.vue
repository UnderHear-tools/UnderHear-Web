<template>
  <div class="component-layout">
    <!-- 左侧导航栏 -->
    <aside class="sidebar">
      <!-- 可滚动的导航内容区域 -->
      <div class="sidebar-content">
        <!-- Overview 组件总览 -->
        <div class="nav-section">
          <div class="section-title">Overview 组件总览</div>
          <router-link 
            to="/component/overview" 
            class="nav-item"
            :class="{ active: $route.path === '/component/overview' }"
          >
            Overview 组件总览
          </router-link>
        </div>

        <!-- Basic 基础组件 -->
        <div class="nav-section">
          <div class="section-title">Basic 基础组件</div>
          <router-link 
            to="/component/button" 
            class="nav-item"
            :class="{ active: $route.path === '/component/button' }"
          >
            Button 按钮
          </router-link>
          <router-link 
            to="/component/border" 
            class="nav-item"
            :class="{ active: $route.path === '/component/border' }"
          >
            Border 边框
          </router-link>
          <router-link 
            to="/component/color" 
            class="nav-item"
            :class="{ active: $route.path === '/component/color' }"
          >
            Color 色彩
          </router-link>
          <router-link 
            to="/component/container" 
            class="nav-item"
            :class="{ active: $route.path === '/component/container' }"
          >
            Container 布局容器
          </router-link>
          <router-link 
            to="/component/icon" 
            class="nav-item"
            :class="{ active: $route.path === '/component/icon' }"
          >
            Icon 图标
          </router-link>
          <router-link 
            to="/component/layout" 
            class="nav-item"
            :class="{ active: $route.path === '/component/layout' }"
          >
            Layout 布局
          </router-link>
          <router-link 
            to="/component/link" 
            class="nav-item"
            :class="{ active: $route.path === '/component/link' }"
          >
            Link 链接
          </router-link>
          <div class="nav-item">
            <router-link 
              to="/component/text" 
              :class="{ active: $route.path === '/component/text' }"
            >
              Text 文字
            </router-link>
          </div>
          <router-link 
            to="/component/scrollbar" 
            class="nav-item"
            :class="{ active: $route.path === '/component/scrollbar' }"
          >
            Scrollbar 滚动条
          </router-link>
          <router-link 
            to="/component/space" 
            class="nav-item"
            :class="{ active: $route.path === '/component/space' }"
          >
            Space 间距
          </router-link>
          <div class="nav-item">
            <router-link 
              to="/component/splitter" 
              :class="{ active: $route.path === '/component/splitter' }"
            >
              Splitter 分隔面板
            </router-link>
          </div>
          <router-link 
            to="/component/typography" 
            class="nav-item"
            :class="{ active: $route.path === '/component/typography' }"
          >
            Typography 排版
          </router-link>
        </div>

        <!-- 配置组件 -->
        <div class="nav-section">
          <div class="section-title">配置组件</div>
          <router-link 
            to="/component/config-provider" 
            class="nav-item"
            :class="{ active: $route.path === '/component/config-provider' }"
          >
            Config Provider 全局配置
          </router-link>
        </div>

        <!-- Form 表单组件 -->
        <div class="nav-section">
          <div class="section-title">Form 表单组件</div>
          <router-link 
            to="/component/autocomplete" 
            class="nav-item"
            :class="{ active: $route.path === '/component/autocomplete' }"
          >
            Autocomplete 自动补全输入框
          </router-link>
          <router-link 
            to="/component/cascader" 
            class="nav-item"
            :class="{ active: $route.path === '/component/cascader' }"
          >
            Cascader 级联选择器
          </router-link>
          <router-link 
            to="/component/checkbox" 
            class="nav-item"
            :class="{ active: $route.path === '/component/checkbox' }"
          >
            Checkbox 多选框
          </router-link>
          <router-link 
            to="/component/select" 
            class="nav-item"
            :class="{ active: $route.path === '/component/select' }"
          >
            Select 选择器
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
  gap: 4px;
}

.section-title {
  font-size: 1rem;
  font-weight: 600;
  color: var(--font-black);
  margin-top: 10px;
  margin-bottom: 6px;
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