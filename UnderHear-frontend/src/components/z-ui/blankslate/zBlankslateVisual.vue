<script setup lang="ts">
</script>

<template>
  <div
    class="z-blankslate-visual"
    aria-hidden="true"
  >
    <slot />
  </div>
</template>

<style scoped>
.z-blankslate-visual {
  align-items: center;
  color: var(--fgColor-muted, #59636e);
  display: inline-flex;
  height: var(--z-blankslate-visual-size, 32px);
  justify-content: center;
  margin-bottom: 0.25rem;
  width: var(--z-blankslate-visual-size, 32px);
}

.z-blankslate-visual :deep(svg) {
  height: var(--z-blankslate-visual-size, 32px);
  width: var(--z-blankslate-visual-size, 32px);
}
</style>
