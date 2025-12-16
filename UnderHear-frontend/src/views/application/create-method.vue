<template>
  <div class="publish-method-container">
    <div class="publish-method-header">
      <h1 class="title">选择发布方式</h1>
      <p class="subtitle">进入发布页面前，请先选择发布来源</p>
      <p class="subtitle">
        返回列表：<zLink href="/application" link-text="应用广场" />
      </p>
    </div>

    <div class="options-grid">
      <button type="button" class="option-card" @click="goNew">
        <h2 class="option-title">新建</h2>
        <p class="option-desc">在本站编辑/粘贴页面代码并发布</p>
      </button>

      <button type="button" class="option-card" @click="goWebsite">
        <h2 class="option-title">已有自己的网站！</h2>
        <p class="option-desc">提交站点链接与基本信息，直接发布</p>
      </button>

      <button type="button" class="option-card" @click="goCollect">
        <h2 class="option-title">收录</h2>
        <p class="option-desc">收录优质站点到应用广场</p>
      </button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'

import { zLink } from '@/components/z-ui/link/zlink'

const router = useRouter()

function goNew() {
  router.push('/application/create/new')
}

function goWebsite() {
  router.push('/application/create/website')
}

function goCollect() {
  router.push('/application/create/collect')
}
</script>

<style scoped>
.publish-method-container {
  max-width: 1150px;
  margin: 0 auto;
  padding: 2rem;
  background: #fff;
}

.publish-method-header {
  text-align: center;
  margin-bottom: 3rem;
}

.title {
  font-size: 2.5rem;
  font-weight: 700;
  color: #24292f;
  margin-bottom: 1rem;
  line-height: 1.2;
}

.subtitle {
  font-size: 1.05rem;
  color: #656d76;
  line-height: 1.6;
  max-width: 620px;
  margin: 0.35rem auto 0;
}

.options-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 1.25rem;
  align-items: stretch;
}

.option-card {
  appearance: none;
  width: 100%;
  text-align: left;
  border: 1px solid #d1d9e1;
  border-radius: 10px;
  background: #fff;
  padding: 1.5rem;
  cursor: pointer;
  transition: border-color 0.2s ease, transform 0.2s ease, box-shadow 0.2s ease;
}

.option-card:hover {
  border-color: #0969da;
  transform: translateY(-2px);
  box-shadow: 0 8px 22px rgba(9, 105, 218, 0.14);
}

.option-title {
  margin: 0 0 0.6rem 0;
  font-size: 1.15rem;
  font-weight: 700;
  color: #24292f;
}

.option-desc {
  margin: 0;
  font-size: 0.95rem;
  line-height: 1.6;
  color: #656d76;
}

@media (max-width: 900px) {
  .options-grid {
    grid-template-columns: 1fr;
  }
}
</style>

