<template>
  <label class="z-form-control__label">
    <slot />
  </label>
</template>

<script setup lang="ts">
defineOptions({
  name: 'FormControlLabel'
})
</script>

<style scoped>
.z-form-control__label {
  display: inline-flex;
  align-items: center;
  gap: 4px;
  font-size: 14px;
  line-height: 1.4;
  font-weight: 600;
  color: var(--fgColor-default, #1f2328);
}
</style>
