<template>
  <div class="octicons-docs">
    <header class="page-header">
      <div>
        <h1 class="page-title">Octicons Vue 图标</h1>
        <p class="page-description">
          该图标库基于开源的 <zLink href="https://primer.style/octicons/" link-text="Octicons" variant="primary" /> 图标库构建，为 Vue 项目提供可直接使用的图标组件。
        </p>
      </div>
      <div class="page-meta">
        <div class="meta-card">
          <span class="meta-label">图标总数</span>
          <span class="meta-value">{{ iconEntries.length }}</span>
        </div>
        <div class="meta-card">
          <span class="meta-label">预览尺寸</span>
          <span class="meta-value">{{ iconSize }}px</span>
        </div>
      </div>
    </header>

    <section class="icon-section">
      <h2 class="section-title">图标展示</h2>
      <div class="icon-grid">
        <div v-for="icon in iconEntries" :key="icon.name" class="icon-card">
          <component :is="icon.component" :size="iconSize" class="icon-svg" />
          <span class="icon-name">{{ icon.name }}</span>
        </div>
      </div>
    </section>
  </div>
</template>

<script setup lang="ts">
import type { Component } from 'vue'
import * as octicons from '@/components/z-ui/icon/Octicons-vue/index.ts'
import { zLink } from '@/components/z-ui/link/zlink'

type IconEntry = {
  name: string
  component: Component
}

const iconSize = 24
const iconEntries: IconEntry[] = Object.entries(octicons)
  .filter(([name]) => name !== 'default')
  .map(([name, component]) => ({
    name,
    component: component as Component
  }))
  .sort((a, b) => a.name.localeCompare(b.name))
</script>

<style scoped>
.octicons-docs {
  max-width: 1200px;
  margin: 0 auto;
  width: 100%;
}

.page-header {
  display: flex;
  flex-wrap: wrap;
  gap: 1.5rem;
  align-items: flex-end;
  justify-content: space-between;
  padding-bottom: 1.5rem;
  border-bottom: 1px solid #e1e4e8;
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
  max-width: 640px;
}

.page-meta {
  display: flex;
  gap: 0.75rem;
  align-items: center;
}

.meta-card {
  display: flex;
  flex-direction: column;
  gap: 0.25rem;
  padding: 0.75rem 1rem;
  border: 1px solid #e1e4e8;
  border-radius: 8px;
  background: #ffffff;
  min-width: 120px;
}

.meta-label {
  font-size: 0.75rem;
  color: var(--font-gray);
  text-transform: uppercase;
  letter-spacing: 0.06em;
}

.meta-value {
  font-size: 1.25rem;
  font-weight: 600;
  color: var(--font-black);
}

.icon-section {
  margin-bottom: 2.5rem;
}

.section-title {
  font-size: 1.5rem;
  font-weight: 600;
  color: var(--font-black);
  margin-bottom: 1rem;
}

.icon-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(120px, 1fr));
  gap: 1rem;
}

.icon-card {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 0.5rem;
  padding: 0.9rem 0.75rem;
  border: 1px solid #e1e4e8;
  border-radius: 10px;
  background: #ffffff;
}

.icon-svg {
  color: var(--font-black);
}

.icon-name {
  font-size: 0.75rem;
  color: var(--font-gray);
  text-align: center;
  word-break: break-word;
  line-height: 1.4;
}

@media (max-width: 768px) {
  .page-title {
    font-size: 1.6rem;
  }

  .page-description {
    font-size: 0.9rem;
  }

  .page-meta {
    width: 100%;
  }

  .meta-card {
    flex: 1;
    min-width: 0;
  }

  .section-title {
    font-size: 1.25rem;
  }

  .icon-grid {
    grid-template-columns: repeat(auto-fill, minmax(96px, 1fr));
    gap: 0.75rem;
  }

  .icon-card {
    padding: 0.75rem 0.5rem;
  }
}
</style>
