<template>
  <component
    :is="href ? 'a' : 'button'"
    :href="href"
    :target="href ? '_blank' : undefined"
    class="z-menu-item"
    @click="$emit('click')"
  >
    <slot />
  </component>
</template>

<script setup lang="ts">
defineProps<{ href?: string }>()
defineEmits<{ click: [] }>()
</script>

<style scoped>
.z-menu-item {
  display: block;
  width: 100%;
  padding: 6px 8px;
  border: none;
  border-radius: 6px;
  background: transparent;
  color: #1f2328;
  font-size: 14px;
  text-align: left;
  text-decoration: none;
  cursor: pointer;
}

.z-menu-item:hover {
  background: #f6f8fa;
}
</style>
