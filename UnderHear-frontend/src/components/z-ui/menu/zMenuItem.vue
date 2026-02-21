<template>
  <component
    :is="props.href ? 'a' : 'button'"
    :href="props.href"
    :target="props.href ? (props.newTab ? '_blank' : '_self') : undefined"
    :rel="props.href && props.newTab ? 'noopener noreferrer' : undefined"
    :type="props.href ? undefined : 'button'"
    class="z-menu-item"
    @click="$emit('click')"
  >
    <slot />
  </component>
</template>

<script setup lang="ts">
const props = withDefaults(
  defineProps<{
    href?: string
    newTab?: boolean
  }>(),
  {
    newTab: false
  }
)
defineEmits<{ click: [] }>()
</script>

<style scoped>
.z-menu-item {
  display: flex;
  align-items: center;
  gap: 8px;
  width: 100%;
  padding: 6px 8px;
  border: none;
  border-radius: 6px;
  background: var(--bgColor-transparent);
  color: var(--fgColor-default);
  font-size: 14px;
  text-align: left;
  text-decoration: none;
  cursor: pointer;
}

.z-menu-item:hover {
  background: var(--control-bgColor-hover);
}
</style>
