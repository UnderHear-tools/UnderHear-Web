<template>
  <div class="component-layout">
    <!-- 左侧导航栏 -->
    <aside class="sidebar">
      <!-- 可滚动的导航内容区域 -->
      <div class="sidebar-content">
        <!-- Overview 组件总览 -->
        <div class="nav-section">
          <h3 class="section-title">Overview 组件总览</h3>
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
          <h3 class="section-title">Basic 基础组件</h3>
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
          <div class="nav-item with-version">
            <router-link 
              to="/component/text" 
              :class="{ active: $route.path === '/component/text' }"
            >
              Text 文字
            </router-link>
            <span class="version-tag">2.3.0</span>
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
          <div class="nav-item with-version">
            <router-link 
              to="/component/splitter" 
              :class="{ active: $route.path === '/component/splitter' }"
            >
              Splitter 分隔面板
            </router-link>
            <span class="version-tag">2.10.0</span>
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
          <h3 class="section-title">配置组件</h3>
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
          <h3 class="section-title">Form 表单组件</h3>
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
      
      <!-- 固定在底部的导航 -->
      <nav class="bottom-nav">
        <router-link to="/" class="bottom-nav-item">
          <span class="nav-text">首页</span>
        </router-link>
        <router-link to="/tool" class="bottom-nav-item">
          <span class="nav-text">工具</span>
        </router-link>
        <router-link to="/component" class="bottom-nav-item">
          <span class="nav-text">组件</span>
        </router-link>
        <router-link to="/namecard" class="bottom-nav-item">
          <span class="nav-text">名片</span>
        </router-link>
      </nav>
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
  min-height: 100vh;
  background-color: #f8f9fa;
}

.sidebar {
  width: 280px;
  background-color: #fff;
  border-right: 1px solid #e1e4e8;
  position: fixed;
  top: 0;
  left: 0;
  height: 100vh;
  z-index: 100;
  display: flex;
  flex-direction: column;
}

.sidebar-content {
  flex: 1;
  overflow-y: auto;
  padding: 1.5rem 0;
}

.nav-section {
  margin-bottom: 2rem;
}

.section-title {
  font-size: 1rem;
  font-weight: 600;
  color: #24292f;
  margin: 0 0 0.75rem 1.5rem;
  padding: 0;
}

.nav-item {
  display: block;
  padding: 0.5rem 1.5rem;
  color: #656d76;
  text-decoration: none;
  font-size: 0.875rem;
  transition: all 0.2s ease;
  border-left: 3px solid transparent;
  position: relative;
}

.nav-item:hover {
  background-color: #f6f8fa;
  color: #24292f;
}

.nav-item.active {
  background-color: #dbeafe;
  color: #1e40af;
  border-left-color: #3b82f6;
  font-weight: 500;
}

.nav-item.with-version {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0;
}

.nav-item.with-version a {
  flex: 1;
  padding: 0.5rem 0 0.5rem 1.5rem;
  color: inherit;
  text-decoration: none;
}

.version-tag {
  background-color: #0969da;
  color: white;
  font-size: 0.75rem;
  padding: 0.125rem 0.375rem;
  border-radius: 0.25rem;
  margin-right: 1rem;
  font-weight: 500;
}

.main-content {
  flex: 1;
  margin-left: 280px;
  padding: 2rem;
  background-color: #fff;
  min-height: 100vh;
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

.bottom-nav-item {
  display: flex;
  align-items: center;
  padding: 0.3rem 0.5rem;
  text-decoration: none;
  color: #656d76;
  transition: all 0.2s ease;
  border-left: 3px solid transparent;
  border-radius: 4px;
}

.bottom-nav-item:hover {
  background-color: #E6E6E6;
  color: #24292f;
}

.bottom-nav-item.router-link-active {
  background-color: #dbeafe;
  color: #1e40af;
  border-left-color: #3b82f6;
  font-weight: 500;
}

.nav-text {
  font-size: 14px;
  font-weight: 500;
}

/* 响应式设计 */
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