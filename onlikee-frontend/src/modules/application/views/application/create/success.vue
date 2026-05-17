<template>
  <Container>
    <div class="success-message">
      <div class="success-info">
        <CheckCircleIcon :size="32" />
        <h2>
          应用创建成功
        </h2>
      </div>
      <Link
        :href="appUrl"
        :link-text="appUrl"
        target="_blank"
      />
    </div>
  </Container>
</template>

<script setup lang="ts">
import confetti from 'canvas-confetti'
import { ref, onMounted } from 'vue'
import { Container } from '@/components/z-ui/container'
import { CheckCircleIcon } from '@/components/octicons-vue3'
import { Link } from '@/components/z-ui/link'
import { useRoute } from 'vue-router'

const route = useRoute()
const appUrl = ref('')

function getAppUrlQuery() {
  const value = route.query.appUrl

  if (typeof value === 'string') {
    appUrl.value = value
  } else if (Array.isArray(value) && typeof value[0] === 'string') {
    appUrl.value = value[0]
  } else {
    appUrl.value = ''
  }
}

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
  getAppUrlQuery()
  fireSuccessConfetti()
})
</script>

<style scoped>
.success-message {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 64px;
}

.success-info {
  display: flex;
  flex-direction: column;
  align-items: center;
  color: var(--success-fgColor, #1a7f37);
}
</style>
