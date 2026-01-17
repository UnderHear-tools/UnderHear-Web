<template>
  <div class="guide-wrapper">
    <div class="guide-header">
      <h1 class="title">使用指南</h1>
      <p class="subtitle">欢迎使用 UnderHear 组件库,本指南将帮助您快速上手并充分利用我们提供的组件。</p>
    </div>

    <div class="guide-content">
      <!-- 快速开始 -->
      <section class="guide-section">
        <h2 class="section-title">快速开始</h2>
        <p class="section-desc">按照以下步骤快速集成组件库到您的项目中。</p>
        
        <div class="step-card">
          <div class="step-number">1</div>
          <div class="step-content">
            <h3 class="step-title">安装依赖</h3>
            <p class="step-desc">使用 npm 或 yarn 安装组件库:</p>
            <div class="code-block">
              <code>npm install @underhear/ui-components</code>
            </div>
            <div class="code-block">
              <code>yarn add @underhear/ui-components</code>
            </div>
          </div>
        </div>

        <div class="step-card">
          <div class="step-number">2</div>
          <div class="step-content">
            <h3 class="step-title">全局引入</h3>
            <p class="step-desc">在 main.js 或 main.ts 中引入组件库:</p>
            <div class="code-block">
              <pre><code>import { createApp } from 'vue'
import UnderHearUI from '@underhear/ui-components'
import '@underhear/ui-components/dist/style.css'

const app = createApp(App)
app.use(UnderHearUI)
app.mount('#app')</code></pre>
            </div>
          </div>
        </div>

        <div class="step-card">
          <div class="step-number">3</div>
          <div class="step-content">
            <h3 class="step-title">开始使用</h3>
            <p class="step-desc">现在您可以在任何组件中使用我们的 UI 组件了:</p>
            <div class="code-block">
              <pre><code>&lt;template&gt;
  &lt;z-button type="primary"&gt;点击我&lt;/z-button&gt;
  &lt;z-input placeholder="请输入内容" /&gt;
&lt;/template&gt;</code></pre>
            </div>
          </div>
        </div>
      </section>

      <!-- 按需引入 -->
      <section class="guide-section">
        <h2 class="section-title">按需引入</h2>
        <p class="section-desc">为了减小打包体积,您也可以按需引入所需的组件。</p>
        
        <div class="info-card">
          <div class="code-block">
            <pre><code>import { zButton, zInput } from '@underhear/ui-components'

export default {
  components: {
    zButton,
    zInput
  }
}</code></pre>
          </div>
        </div>
      </section>

      <!-- 主题定制 -->
      <section class="guide-section">
        <h2 class="section-title">主题定制</h2>
        <p class="section-desc">通过 CSS 变量自定义组件库的主题样式。</p>
        
        <div class="info-card">
          <p class="info-desc">在您的全局样式文件中覆盖以下 CSS 变量:</p>
          <div class="code-block">
            <pre><code>:root {
  --font-black: #1a1a1a;
  --font-gray: #666666;
  --font-blue: #3b82f6;
  --primary-color: #3b82f6;
  --success-color: #10b981;
  --warning-color: #f59e0b;
  --danger-color: #ef4444;
}</code></pre>
          </div>
        </div>
      </section>

      <!-- 浏览组件 -->
      <section class="guide-section">
        <h2 class="section-title">浏览组件</h2>
        <p class="section-desc">我们提供了丰富的组件供您选择,点击左侧导航栏浏览各个组件的详细文档和示例。</p>
        
        <div class="category-grid">
          <div class="category-card">
            <h3 class="category-title">基础组件</h3>
            <p class="category-desc">包含按钮、图标、链接等基础 UI 元素</p>
          </div>
          <div class="category-card">
            <h3 class="category-title">表单组件</h3>
            <p class="category-desc">输入框、选择器、多选框等表单控件</p>
          </div>
          <div class="category-card">
            <h3 class="category-title">布局组件</h3>
            <p class="category-desc">容器、间距、分隔面板等布局工具</p>
          </div>
          <div class="category-card">
            <h3 class="category-title">配置组件</h3>
            <p class="category-desc">全局配置提供器,统一管理应用配置</p>
          </div>
        </div>
      </section>

      <!-- 技术支持 -->
      <section class="guide-section">
        <h2 class="section-title">技术支持</h2>
        <p class="section-desc">遇到问题?我们随时为您提供帮助。</p>
        
        <div class="support-grid">
          <div class="support-card">
            <div class="support-icon">📚</div>
            <h3 class="support-title">文档</h3>
            <p class="support-desc">查看详细的组件文档和 API 说明</p>
          </div>
          <div class="support-card">
            <div class="support-icon">💬</div>
            <h3 class="support-title">社区</h3>
            <p class="support-desc">加入我们的社区讨论和分享经验</p>
          </div>
          <div class="support-card">
            <div class="support-icon">🐛</div>
            <h3 class="support-title">反馈</h3>
            <p class="support-desc">提交 Bug 报告或功能建议</p>
          </div>
        </div>
      </section>
    </div>
  </div>
</template>

<style scoped>
.guide-wrapper {
  max-width: 1000px;
  margin: 0 auto;
  padding: 0;
}

.guide-header {
  text-align: center;
  margin-bottom: 3rem;
}

.title {
  font-size: 2.5rem;
  font-weight: 700;
  color: var(--font-black);
  margin-bottom: 1rem;
  line-height: 1.2;
}

.subtitle {
  font-size: 1.05rem;
  color: var(--font-gray);
  line-height: 1.6;
  max-width: 720px;
  margin: 0 auto;
}

.guide-content {
  display: flex;
  flex-direction: column;
  gap: 3rem;
}

.guide-section {
  background: #fff;
  border-radius: 8px;
  padding: 2rem;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.05);
}

.section-title {
  font-size: 1.75rem;
  font-weight: 600;
  color: var(--font-black);
  margin-bottom: 0.75rem;
}

.section-desc {
  font-size: 1rem;
  color: var(--font-gray);
  margin-bottom: 1.5rem;
  line-height: 1.6;
}

.step-card {
  display: flex;
  gap: 1.5rem;
  margin-bottom: 2rem;
  padding: 1.5rem;
  background: #f8f9fa;
  border-radius: 8px;
}

.step-card:last-child {
  margin-bottom: 0;
}

.step-number {
  flex-shrink: 0;
  width: 40px;
  height: 40px;
  background: var(--font-blue);
  color: white;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 1.25rem;
  font-weight: 600;
}

.step-content {
  flex: 1;
}

.step-title {
  font-size: 1.25rem;
  font-weight: 600;
  color: var(--font-black);
  margin-bottom: 0.5rem;
}

.step-desc {
  font-size: 0.95rem;
  color: var(--font-gray);
  margin-bottom: 1rem;
  line-height: 1.6;
}

.code-block {
  background: #1e1e1e;
  border-radius: 6px;
  padding: 1rem;
  margin-bottom: 0.75rem;
  overflow-x: auto;
}

.code-block:last-child {
  margin-bottom: 0;
}

.code-block code,
.code-block pre {
  font-family: 'Consolas', 'Monaco', 'Courier New', monospace;
  font-size: 0.875rem;
  color: #d4d4d4;
  line-height: 1.6;
  margin: 0;
}

.code-block pre {
  white-space: pre;
}

.info-card {
  background: #f8f9fa;
  border-radius: 8px;
  padding: 1.5rem;
}

.info-desc {
  font-size: 0.95rem;
  color: var(--font-gray);
  margin-bottom: 1rem;
  line-height: 1.6;
}

.category-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(220px, 1fr));
  gap: 1rem;
  margin-top: 1.5rem;
}

.category-card {
  background: #f8f9fa;
  border-radius: 8px;
  padding: 1.5rem;
  transition: transform 0.2s ease, box-shadow 0.2s ease;
}

.category-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
}

.category-title {
  font-size: 1.1rem;
  font-weight: 600;
  color: var(--font-black);
  margin-bottom: 0.5rem;
}

.category-desc {
  font-size: 0.875rem;
  color: var(--font-gray);
  line-height: 1.5;
  margin: 0;
}

.support-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 1.5rem;
  margin-top: 1.5rem;
}

.support-card {
  text-align: center;
  padding: 2rem 1.5rem;
  background: #f8f9fa;
  border-radius: 8px;
  transition: transform 0.2s ease, box-shadow 0.2s ease;
}

.support-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
}

.support-icon {
  font-size: 2.5rem;
  margin-bottom: 1rem;
}

.support-title {
  font-size: 1.1rem;
  font-weight: 600;
  color: var(--font-black);
  margin-bottom: 0.5rem;
}

.support-desc {
  font-size: 0.875rem;
  color: var(--font-gray);
  line-height: 1.5;
  margin: 0;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .guide-section {
    padding: 1.5rem;
  }

  .step-card {
    flex-direction: column;
    align-items: center;
    text-align: center;
  }

  .category-grid,
  .support-grid {
    grid-template-columns: 1fr;
  }
}
</style>
