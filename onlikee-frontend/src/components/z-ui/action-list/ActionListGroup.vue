<template>
  <li class="action-list-group">
    <slot name="heading">
      <ActionListGroupHeading
        v-if="title"
        :variant="headingVariant"
      >
        {{ title }}
      </ActionListGroupHeading>
    </slot>
    <ul class="action-list-list action-list-group-list">
      <slot />
    </ul>
  </li>
</template>

<script setup lang="ts">
import ActionListGroupHeading from './ActionListGroupHeading.vue'

withDefaults(
  defineProps<{
    title?: string
    headingVariant?: 'subtle' | 'filled'
  }>(),
  {
    title: '',
    headingVariant: 'subtle'
  }
)
</script>

<style scoped>
.action-list-group {
  list-style: none;
}

.action-list-group:not(:first-child) {
  margin-block-start: 8px;
}

.action-list-group-list {
  padding: 0;
  margin: 0;
  list-style: none;
}
</style>
