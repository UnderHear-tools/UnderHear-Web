<template>
  <svg
    aria-hidden="true"
    focusable="false"
    :width="size"
    :height="size"
    viewBox="0 0 16 16"
    :fill="color || 'currentColor'"
  >
    <circle
      cx="8"
      cy="8"
      r="7"
      fill="none"
      :stroke="color || 'currentColor'"
      stroke-opacity="0.25"
      stroke-width="2"
      vector-effect="non-scaling-stroke"
    />
    <path
      d="M15 8a7.002 7.002 0 0 0-7-7"
      fill="none"
      :stroke="color || 'currentColor'"
      stroke-linecap="round"
      stroke-width="2"
      vector-effect="non-scaling-stroke"
    />
  </svg>
</template>

<script setup lang="ts">
withDefaults(defineProps<{ size?: number | string; color?: string }>(), {
  size: 16,
})
</script>
