<template>
  <ActionListItemBase
    v-bind="{ ...props, ...$attrs }"
    as="a"
    @select="event => emit('select', event)"
  >
    <slot />
    <template
      v-if="$slots.leadingVisual"
      #leadingVisual
    >
      <slot name="leadingVisual" />
    </template>
    <template
      v-if="$slots.description"
      #description
    >
      <slot name="description" />
    </template>
    <template
      v-if="$slots.trailingVisual"
      #trailingVisual
    >
      <slot name="trailingVisual" />
    </template>
  </ActionListItemBase>
</template>

<script setup lang="ts">
import ActionListItemBase from './ActionListItemBase.vue'
import type { ActionListDescriptionVariant, ActionListItemSize, ActionListItemVariant } from './context'

defineOptions({ inheritAttrs: false })

const props = withDefaults(
  defineProps<{
    href: string
    newTab?: boolean
    target?: string
    rel?: string
    selected?: boolean
    active?: boolean
    variant?: ActionListItemVariant
    disabled?: boolean
    loading?: boolean
    size?: ActionListItemSize
    role?: string
    descriptionVariant?: ActionListDescriptionVariant
    truncateDescription?: boolean
  }>(),
  {
    newTab: false,
    target: undefined,
    rel: undefined,
    selected: false,
    active: false,
    variant: 'default',
    disabled: false,
    loading: false,
    size: 'medium',
    role: undefined,
    descriptionVariant: 'inline',
    truncateDescription: false
  }
)

const emit = defineEmits<{
  select: [event: MouseEvent]
}>()
</script>
