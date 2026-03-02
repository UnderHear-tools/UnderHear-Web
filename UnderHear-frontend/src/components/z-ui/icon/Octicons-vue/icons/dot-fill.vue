<template>
  <svg
    aria-hidden="true"
    focusable="false"
    :width="size"
    :height="size"
    viewBox="0 0 16 16"
    :fill="color || 'currentColor'"
  >
    <path d="M8 4a4 4 0 1 1 0 8 4 4 0 0 1 0-8Z" />
  </svg>
</template>

<script setup lang="ts">
withDefaults(defineProps<{ size?: number | string; color?: string }>(), {
  size: 16,
})
</script>
