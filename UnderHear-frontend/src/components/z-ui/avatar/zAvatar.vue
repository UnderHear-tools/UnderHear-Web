<template>
  <div class="z-avatar" :style="avatarStyle">
    <img v-if="src" :src="src" :alt="alt" class="z-avatar-img" />
    <span v-else class="z-avatar-placeholder">{{ placeholder }}</span>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue'

const props = defineProps<{
  src?: string
  alt?: string
  placeholder?: string
  size?: number | string
}>()

const avatarStyle = computed(() => {
  const s = props.size ?? 40
  const px = typeof s === 'number' ? `${s}px` : s
  return { width: px, height: px, fontSize: `${parseInt(String(s)) * 0.35}px` }
})
</script>

<style scoped>
.z-avatar {
  box-sizing: content-box;
  display: flex;
  align-items: center;
  justify-content: center;
  outline: 2px solid #d9d9d9;
  border-radius: 50%;
  background: rgba(255, 255, 255, 0.8);
  backdrop-filter: blur(8px);
  font-weight: 600;
  overflow: hidden;
}

.z-avatar:hover {
  background: #fff;
  box-shadow: 0 0 0 2px rgba(0, 0, 0, 0.08);
}

.z-avatar-img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.z-avatar-placeholder {
  color: #111;
}
</style>
