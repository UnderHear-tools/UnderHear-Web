<template>
  <p class="form-control__caption">
    <slot />
  </p>
</template>

<script setup lang="ts">
defineOptions({
  name: 'FormControlCaption'
})
</script>

<style scoped>
.form-control__caption {
  margin: 0;
  font-size: 12px;
  line-height: 1.4;
  color: var(--fgColor-muted, #656d76);
}
</style>
