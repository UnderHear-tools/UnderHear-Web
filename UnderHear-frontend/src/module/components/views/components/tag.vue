<template>
  <div class="tag-page">
    <div class="page-header">
      <h1 class="page-title">Tag 标签</h1>
      <p class="page-description">用于标记和分类的小型标签组件,常用于技术栈展示、关键词标注等场景。</p>
    </div>

    <div class="demo-section">
      <h2 class="section-title">基础用法</h2>
      <div class="demo-container">
        <div class="demo-row">
          <zTechTag>Vue.js</zTechTag>
          <zTechTag>TypeScript</zTechTag>
          <zTechTag>JavaScript</zTechTag>
        </div>
      </div>
      <div class="code-example">
        <pre><code>&lt;zTechTag&gt;Vue.js&lt;/zTechTag&gt;
&lt;zTechTag&gt;TypeScript&lt;/zTechTag&gt;
&lt;zTechTag&gt;JavaScript&lt;/zTechTag&gt;</code></pre>
      </div>
    </div>

    <div class="demo-section">
      <h2 class="section-title">技术栈展示</h2>
      <div class="demo-container">
        <div class="demo-group">
          <h3>前端技术</h3>
          <div class="tag-list">
            <zTechTag v-for="tech in frontendTechs" :key="tech">{{ tech }}</zTechTag>
          </div>
        </div>
        <div class="demo-group">
          <h3>后端技术</h3>
          <div class="tag-list">
            <zTechTag v-for="tech in backendTechs" :key="tech">{{ tech }}</zTechTag>
          </div>
        </div>
        <div class="demo-group">
          <h3>数据库</h3>
          <div class="tag-list">
            <zTechTag v-for="tech in databaseTechs" :key="tech">{{ tech }}</zTechTag>
          </div>
        </div>
      </div>
      <div class="code-example">
        <pre><code>&lt;div class="tag-list"&gt;
  &lt;zTechTag v-for="tech in techs" :key="tech"&gt;
    {{ tech }}
  &lt;/zTechTag&gt;
&lt;/div&gt;

&lt;script setup&gt;
const techs = ['Vue.js', 'React', 'Angular']
&lt;/script&gt;</code></pre>
      </div>
    </div>

    <div class="demo-section">
      <h2 class="section-title">项目标签示例</h2>
      <div class="demo-container">
        <div class="project-card">
          <h3>UnderHear Web</h3>
          <p class="project-desc">一个现代化的 Web 应用平台</p>
          <div class="tag-list">
            <zTechTag>Vue 3</zTechTag>
            <zTechTag>TypeScript</zTechTag>
            <zTechTag>Vite</zTechTag>
            <zTechTag>Spring Boot</zTechTag>
            <zTechTag>MySQL</zTechTag>
          </div>
        </div>
        <div class="project-card">
          <h3>组件库系统</h3>
          <p class="project-desc">自研 UI 组件库</p>
          <div class="tag-list">
            <zTechTag>Vue 3</zTechTag>
            <zTechTag>Composition API</zTechTag>
            <zTechTag>CSS3</zTechTag>
          </div>
        </div>
      </div>
      <div class="code-example">
        <pre><code>&lt;div class="project-card"&gt;
  &lt;h3&gt;项目名称&lt;/h3&gt;
  &lt;p&gt;项目描述&lt;/p&gt;
  &lt;div class="tag-list"&gt;
    &lt;zTechTag&gt;Vue 3&lt;/zTechTag&gt;
    &lt;zTechTag&gt;TypeScript&lt;/zTechTag&gt;
    &lt;zTechTag&gt;Vite&lt;/zTechTag&gt;
  &lt;/div&gt;
&lt;/div&gt;</code></pre>
      </div>
    </div>

    <div class="api-section">
      <h2 class="section-title">API</h2>
      <div class="api-table">
        <table>
          <thead>
            <tr>
              <th>属性</th>
              <th>说明</th>
              <th>类型</th>
              <th>可选值</th>
              <th>默认值</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td colspan="5" style="text-align: center; color: var(--font-gray);">暂无可配置属性,通过默认插槽传入标签内容</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>

    <div class="api-section">
      <h2 class="section-title">插槽</h2>
      <div class="api-table">
        <table>
          <thead>
            <tr>
              <th>插槽名</th>
              <th>说明</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>default</td>
              <td>标签的内容</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>

    <div class="tip-section">
      <h3>💡 设计说明</h3>
      <ul>
        <li>标签采用圆角胶囊形状设计,视觉效果更加柔和</li>
        <li>使用蓝色系配色方案,与整体 UI 风格保持一致</li>
        <li>字体大小为 12px,适合作为辅助信息展示</li>
        <li>建议配合 flex 布局使用,实现标签的自动换行</li>
      </ul>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { zTechTag } from '@/components/z-ui/tag/zTechTag'

const frontendTechs = ref(['Vue.js', 'React', 'Angular', 'TypeScript', 'Vite', 'Webpack'])
const backendTechs = ref(['Spring Boot', 'Node.js', 'Express', 'Nest.js', 'FastAPI'])
const databaseTechs = ref(['MySQL', 'PostgreSQL', 'MongoDB', 'Redis'])
</script>

<style scoped>
.tag-page {
  max-width: 1000px;
  margin: 0 auto;
}

.page-header {
  margin-bottom: 2rem;
}

.page-title {
  font-size: 2rem;
  font-weight: 600;
  color: var(--font-black);
  margin-bottom: 0.5rem;
}

.page-description {
  font-size: 1rem;
  color: var(--font-gray);
  line-height: 1.6;
}

.demo-section {
  margin-bottom: 3rem;
}

.section-title {
  font-size: 1.5rem;
  font-weight: 600;
  color: var(--font-black);
  margin-bottom: 1rem;
}

.demo-container {
  background: #f8f9fa;
  border-radius: 8px;
  padding: 2rem;
  margin-bottom: 1rem;
}

.demo-row {
  display: flex;
  gap: 0.75rem;
  flex-wrap: wrap;
}

.demo-group {
  margin-bottom: 1.5rem;
}

.demo-group:last-child {
  margin-bottom: 0;
}

.demo-group h3 {
  font-size: 1.1rem;
  font-weight: 600;
  color: var(--font-black);
  margin-bottom: 0.75rem;
}

.tag-list {
  display: flex;
  gap: 0.5rem;
  flex-wrap: wrap;
}

.project-card {
  background: #fff;
  border-radius: 8px;
  padding: 1.5rem;
  margin-bottom: 1rem;
  border: 1px solid #e1e4e8;
}

.project-card:last-child {
  margin-bottom: 0;
}

.project-card h3 {
  font-size: 1.25rem;
  font-weight: 600;
  color: var(--font-black);
  margin-bottom: 0.5rem;
}

.project-desc {
  color: var(--font-gray);
  margin-bottom: 1rem;
  line-height: 1.6;
}

.code-example {
  background: #1e1e1e;
  border-radius: 6px;
  padding: 1rem;
  overflow-x: auto;
}

.code-example code {
  font-family: 'Consolas', 'Monaco', 'Courier New', monospace;
  font-size: 0.875rem;
  color: #d4d4d4;
  line-height: 1.6;
}

.api-section {
  margin-top: 3rem;
  margin-bottom: 3rem;
}

.api-table {
  overflow-x: auto;
}

.api-table table {
  width: 100%;
  border-collapse: collapse;
  background: #fff;
  border: 1px solid #e1e4e8;
  border-radius: 6px;
}

.api-table th,
.api-table td {
  padding: 0.75rem 1rem;
  text-align: left;
  border-bottom: 1px solid #e1e4e8;
}

.api-table th {
  background: #f6f8fa;
  font-weight: 600;
  color: var(--font-black);
}

.api-table td {
  color: var(--font-gray);
  font-size: 0.875rem;
}

.api-table tr:last-child td {
  border-bottom: none;
}

.tip-section {
  background: #f6f8fa;
  border-radius: 8px;
  padding: 1.5rem;
  border-left: 3px solid var(--font-blue);
}

.tip-section h3 {
  font-size: 1.1rem;
  font-weight: 600;
  color: var(--font-black);
  margin-bottom: 1rem;
}

.tip-section ul {
  margin: 0;
  padding-left: 1.5rem;
}

.tip-section li {
  color: var(--font-gray);
  line-height: 1.8;
  font-size: 0.95rem;
}
</style>
