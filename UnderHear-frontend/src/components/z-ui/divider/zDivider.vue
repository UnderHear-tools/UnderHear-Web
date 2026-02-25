<template>
  <hr
    class="z-divider"
    :class="{ 'z-divider--vertical': direction === 'vertical' }"
    :style="direction === 'vertical' ? (height ? { height } : undefined) : (width ? { width } : undefined)"
  />
</template>

<script setup lang="ts">
withDefaults(defineProps<{
  direction?: 'horizontal' | 'vertical'
  height?: string
  width?: string
}>(), {
  direction: 'horizontal'
})
</script>

<style scoped>
.z-divider {
  color: var(--borderColor-muted, #d1d9e0b3);
  margin-block-end: 0.5rem;
  margin-block-start: calc(0.5rem - 0.0625rem);
}

.z-divider--vertical {
  width: 1px;
  height: 1em;
  background: var(--borderColor-muted, #d1d9e0b3);
}
</style>
