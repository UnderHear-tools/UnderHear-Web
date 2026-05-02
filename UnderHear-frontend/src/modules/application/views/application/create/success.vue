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
import confetti from 'canvas-confetti'
import { onMounted } from 'vue'
import { zContainer } from '@/components/z-ui/container'
import { CheckCircle } from '@/components/z-ui/icon/Octicons-vue'

function fireSuccessConfetti() {
  const sharedOptions = {
    particleCount: 280,
    spread: 90,
    startVelocity: 100,
    ticks: 260,
    gravity: 0.75,
    scalar: 1,
    zIndex: 1000,
    disableForReducedMotion: true
  }

  void confetti({
    ...sharedOptions,
    angle: 55,
    origin: { x: 0.08, y: 0.72 }
  })
  void confetti({
    ...sharedOptions,
    angle: 125,
    origin: { x: 0.92, y: 0.72 }
  })
}

onMounted(() => {
  fireSuccessConfetti()
})
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
