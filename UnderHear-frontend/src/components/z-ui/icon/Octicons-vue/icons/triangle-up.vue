<template>
  <svg
    aria-hidden="true"
    focusable="false"
    :width="size"
    :height="size"
    viewBox="0 0 16 16"
    fill="currentColor"
  >
    <path d="m4.427 9.573 3.396-3.396a.25.25 0 0 1 .354 0l3.396 3.396a.25.25 0 0 1-.177.427H4.604a.25.25 0 0 1-.177-.427Z"></path>
  </svg>
</template>

<script setup lang="ts">
withDefaults(defineProps<{ size?: number | string }>(), {
  size: 16,
})
</script>
