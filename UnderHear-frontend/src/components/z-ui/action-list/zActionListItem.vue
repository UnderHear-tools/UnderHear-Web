<template>
  <component
    :is="props.href ? 'a' : 'button'"
    :href="props.href || undefined"
    :target="props.href ? (props.newTab ? '_blank' : '_self') : undefined"
    :rel="props.href && props.newTab ? 'noopener noreferrer' : undefined"
    :type="props.href ? undefined : 'button'"
    class="z-action-list-item"
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
    href: '',
    newTab: false
  }
)
defineEmits<{ click: [] }>()
</script>

<style scoped>
.z-action-list-item {
  display: flex;
  align-items: center;
  gap: 8px;
  width: 100%;
  padding: 6px 8px;
  border: none;
  border-radius: 6px;
  background: var(--control-transparent-bgColor-rest, #ffffff00);
  color: var(--fgColor-default, #1f2328);
  font-size: 14px;
  text-align: left;
  text-decoration: none;
  cursor: pointer;
}

.z-action-list-item:hover {
  background: var(--control-transparent-bgColor-hover, #818b981a);
}
</style>
