<template>
  <svg
    aria-hidden="true"
    focusable="false"
    :width="size"
    :height="size"
    viewBox="0 0 16 16"
    :fill="color || 'currentColor'"
  >
    <path d="M8 9.5a1.5 1.5 0 1 0 0-3 1.5 1.5 0 0 0 0 3Z" />
    <path d="M8 0a8 8 0 1 1 0 16A8 8 0 0 1 8 0ZM1.5 8a6.5 6.5 0 1 0 13 0 6.5 6.5 0 0 0-13 0Z" />
  </svg>
</template>

<script setup lang="ts">
withDefaults(defineProps<{ size?: number | string; color?: string }>(), {
  size: 16,
})
</script>
