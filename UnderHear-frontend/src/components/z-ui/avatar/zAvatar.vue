<template>
  <div class="z-avatar" :class="[`z-avatar--${size}`]">
    <img v-if="src" :src="src" :alt="alt" class="z-avatar-img" />
    <span v-else class="z-avatar-placeholder">{{ placeholder }}</span>
  </div>
</template>

<script setup lang="ts">
defineProps<{
  src?: string
  alt?: string
  placeholder?: string
  size?: 'sm' | 'md' | 'lg'
}>()
</script>

<style scoped>
.z-avatar {
  display: flex;
  align-items: center;
  justify-content: center;
  border: 1px solid #d9d9d9;
  border-radius: 50%;
  background: rgba(255, 255, 255, 0.8);
  backdrop-filter: blur(8px);
  font-weight: 600;
  overflow: hidden;
}

.z-avatar--sm { width: 32px; height: 32px; font-size: 12px; }
.z-avatar--md { width: 40px; height: 40px; font-size: 14px; }
.z-avatar--lg { width: 48px; height: 48px; font-size: 16px; }

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
