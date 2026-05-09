<template>
  <Container>
    <div class="components-header">
      <h1 class="title">
        UI 组件库 Light
      </h1>
      <p class="subtitle">
        这里展示本站自研的 UI 组件库，提供现代化、美观且易用的组件。
      </p>
      <p class="subtitle">
        所有组件都经过精心设计，支持 TypeScript 并遵循最佳实践。
      </p>
      <RouterLink
        to="/component/overview"
        class="link"
      >
        &nbsp;&nbsp;探索一下！
      </RouterLink>
      <img
        src="@/modules/components/assets/components.svg"
        alt="Components Preview"
        class="components-preview"
      >
    </div>
  </Container>
</template>

<script setup lang="ts">
import { Container } from '@/components/z-ui/container'
</script>

<style scoped>
.components-header {
  text-align: center;
  margin-bottom: 3rem;
}

.title {
  font-size: 2.5rem;
  font-weight: 700;
  color: var(--fgColor-default);
  margin-bottom: 1rem;
  line-height: 1.2;
}

.subtitle {
  font-size: 1.05rem;
  color: var(--fgColor-muted);
  line-height: 1.6;
  max-width: 620px;
  margin: 0 auto;
}

.link {
  display: inline-block;
  margin-top: 2rem;
  padding: 0.75rem 2rem;
  background-color: var(--bgColor-accent-emphasis);
  color: var(--fgColor-onEmphasis);
  border-radius: 6px;
}

.link:hover {
  background-color: var(--bgColor-accent-emphasis);
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
