<template>
  <div class="application-container">
    <div class="application-header">
      <h1 class="application-title">一些SPA应用</h1>
      <p class="application-subtitle">希望这些应用能够帮到你🐱。</p>
    </div>

    <div class="applications-grid">
      <div v-for="tool in tools" :key="tool.id" class="application-card">
        <div class="application-content">
          <div class="application-header-info">
            <h3 class="application-name">{{ tool.title }}</h3>
            <span class="application-category">{{ tool.category }}</span>
          </div>
          <div class="application-description">
            <p>{{ tool.description.zh }}</p>
            <p class="description-en">{{ tool.description.en }}</p>
          </div>
          <div class="application-actions">
            <div class="author-info" v-if="tool.isOriginal && tool.author">
              <img :src="tool.authorAvatar" :alt="tool.author" class="author-avatar" />
              <a :href="tool.authorLink" class="author-link">{{ tool.author }}</a>
            </div>
            <div class="collected-badge" v-if="!tool.isOriginal">
              该应用为本站收录
            </div>
            <a :href="tool.link" class="try-button" v-if="tool.isOriginal">
              Try it
            </a>
            <a :href="tool.link" v-else target="_blank" class="try-button">
              Try it
            </a>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'

// 引入数据
import toolData from '@/views/application/Applicationcard.json'

interface ToolDescription {
  zh: string
  en: string
}

interface Tool {
  id: number
  title: string
  description: ToolDescription
  category: string
  link: string
  isOriginal: boolean
  author: string
  authorLink: string
  authorAvatar: string
}

const tools = ref<Tool[]>([])

onMounted(() => {
  tools.value = toolData as any
})
</script>

<style scoped>
.application-container {
  max-width: 1150px;
  margin: 0 auto;
  padding: 2rem;
  background-color: #fff;
}

.application-header {
  text-align: center;
  margin-bottom: 3rem;
}

.application-title {
  font-size: 2.5rem;
  font-weight: 700;
  color: #24292f;
  margin-bottom: 1rem;
  line-height: 1.2;
}

.application-subtitle {
  font-size: 1.1rem;
  color: #656d76;
  line-height: 1.6;
  max-width: 600px;
  margin: 0 auto;
}

.applications-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 2rem;
}

.application-card {
  background: white;
  border: 1px solid #d1d9e1;
  border-radius: 6px;
  padding: 2rem;
  display: flex;
  transition: all 0.2s ease;
  min-height: 180px;
}

.application-card:hover {
  border-color: #0969da;
}

.application-content {
  flex: 1;
  display: flex;
  flex-direction: column;
}

.application-header-info {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  margin-bottom: 1rem;
  gap: 1rem;
}

.application-name {
  margin: 0;
  font-size: 1.25rem;
  font-weight: 600;
  color: #24292f;
  line-height: 1.3;
  flex: 1;
}

.application-category {
  font-size: 0.75rem;
  color: #656d76;
  background-color: #f6f8fa;
  padding: 0.25rem 0.5rem;
  border-radius: 12px;
  font-weight: 500;
  white-space: nowrap;
}

.application-description {
  flex: 1;
}

.application-description p {
  margin: 0 0 0.75rem 0;
  font-size: 0.95rem;
  line-height: 1.6;
  color: #24292f;
}

.description-en {
  color: #656d76 !important;
  font-style: italic;
  font-size: 0.9rem !important;
}

.application-actions {
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 1rem;
}

.author-info {
  display: flex;
  align-items: center;
  gap: 0.5rem;
}

.author-avatar {
  width: 30px;
  height: 30px;
  border-radius: 30px;
  border: 1px solid #ccc;
  object-fit: cover;
}

.author-link {
  font-size: 1rem;
  font-weight: 600;
  color: #0969da;
  text-decoration: underline;
}

.author-link:hover {
  text-decoration: underline;
}

.collected-badge {
  display: inline-flex;
  align-items: center;
  font-size: 0.875rem;
  color: #656d76;
  background-color: #f6f8fa;
  padding: 0.25rem 0.75rem;
  border-radius: 12px;
  font-weight: 500;
}



.try-button {
  display: inline-flex;
  align-items: center;
  padding: 0.5rem 1rem;
  background-color: #0969da;
  color: white;
  text-decoration: none;
  border-radius: 6px;
  font-weight: 500;
  font-size: 0.875rem;
  transition: background-color 0.2s ease;
  cursor: pointer;
}

.try-button:hover {
  background-color: #0860ca;
}

@media (max-width: 768px) {
  .application-container {
    padding: 1rem;
  }

  .application-title {
    font-size: 2rem;
  }

  .application-subtitle {
    font-size: 1rem;
  }

  .applications-grid {
    grid-template-columns: 1fr;
    gap: 1.5rem;
  }

  .application-card {
    padding: 1.5rem;
    flex-direction: column;
    gap: 1rem;
    min-height: auto;
  }



  .application-header-info {
    flex-direction: column;
    align-items: flex-start;
    gap: 0.5rem;
  }

  .application-actions {
    flex-direction: column;
    align-items: flex-start;
    gap: 0.75rem;
  }
  .author-info { order: 2; }
  .try-button { order: 1; align-self: flex-end; }

  .try-button {
    align-self: flex-end;
  }
}
</style>
