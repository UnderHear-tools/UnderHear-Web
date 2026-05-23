<template>
  <div
    class="action-list-group-heading-wrap"
    :data-variant="variant"
    :data-has-trailing-action="$slots.trailingAction ? 'true' : undefined"
  >
    <h3 class="action-list-group-heading">
      <slot />
    </h3>
    <span
      v-if="$slots.trailingAction"
      class="action-list-group-heading-action"
    >
      <slot name="trailingAction" />
    </span>
  </div>
</template>

<script setup lang="ts">
withDefaults(
  defineProps<{
    variant?: 'subtle' | 'filled'
  }>(),
  {
    variant: 'subtle'
  }
)
</script>

<style scoped>
.action-list-group-heading-wrap {
  display: flex;
  flex-direction: column;
  padding: 6px 16px;
  color: var(--fgColor-muted, #59636e);
  font-size: 12px;
  font-weight: 600;
  line-height: 18px;
}

.action-list-group-heading-wrap[data-variant='filled'] {
  margin-block: 7px 8px;
  background: var(--bgColor-muted, #f6f8fa);
  border-block: 1px solid var(--borderColor-muted, #d1d9e0b3);
}

.action-list-group-heading {
  align-self: flex-start;
  margin: 0;
  color: inherit;
  font: inherit;
}

.action-list-group-heading-wrap[data-has-trailing-action='true'] {
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
  gap: 8px;
}

.action-list-group-heading-action {
  display: inline-flex;
  align-items: center;
  margin-inline-start: auto;
}
</style>
