<template>
  <zContainer>
    <div class="components-header">
      <h1 class="title">UI 组件库 zLight</h1>
      <p class="subtitle">这里展示本站自研的 UI 组件库，提供现代化、美观且易用的组件。</p>
      <p class="subtitle">所有组件都经过精心设计，支持 TypeScript 并遵循最佳实践。
        <zLink href="/component/overview" link-text="立即前往" :external="true" />
      </p>
      <img src="@/assets/components.svg" alt="Components Preview" class="components-preview" />
    </div>
  </zContainer>
</template>

<script setup lang="ts">
import { zContainer } from '@/components/z-ui/container/zContainer'
import { zLink } from '@/components/z-ui/link/zlink'
</script>

<style scoped>
.components-header {
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
  max-width: 620px;
  margin: 0 auto;
}

.components-preview {
  margin: 0 auto;
  width: 800px;
}

@media (max-width: 768px) {
  .title {
    font-size: 2rem;
  }

  .subtitle {
    font-size: 1rem;
  }

}
</style>
