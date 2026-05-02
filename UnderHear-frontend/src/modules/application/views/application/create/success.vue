<template>
  <zContainer
    style="max-width: 720px;"
  >
    <div class="success-message">
      <CheckCircle :size="24" />
      <h2>
        应用创建成功！
      </h2>
    </div>
  </zContainer>
</template>

<script setup lang="ts">
import { zContainer } from '@/components/z-ui/container'
import { CheckCircle } from '@/components/z-ui/icon/Octicons-vue'
</script>

<style scoped>
.success-message {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 0.5rem;
  color: var(--success-fgColor, #1a7f37);
}
</style>
