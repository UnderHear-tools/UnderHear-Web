<template>
  <component
    :is="props.href ? 'a' : 'button'"
    :href="props.href || undefined"
    :target="props.href ? (props.newTab ? '_blank' : '_self') : undefined"
    :rel="props.href && props.newTab ? 'noopener noreferrer' : undefined"
    :type="props.href ? undefined : 'button'"
    class="action-list-item"
    :aria-checked="isSingleSelect ? (isSelected ? 'true' : 'false') : undefined"
    @click="handleClick"
  >
    <span
      v-if="isSingleSelect"
      class="action-list-check"
      :data-checked="isSelected || undefined"
      aria-hidden="true"
    >
      <svg
        data-v-459898ce=""
        aria-hidden="true"
        focusable="false"
        width="24"
        height="24"
        viewBox="0 0 16 16"
        fill="currentColor"
        class="icon-svg"
      ><path d="M13.78 4.22a.75.75 0 0 1 0 1.06l-7.25 7.25a.75.75 0 0 1-1.06 0L2.22 9.28a.751.751 0 0 1 .018-1.042.751.751 0 0 1 1.042-.018L6 10.94l6.72-6.72a.75.75 0 0 1 1.06 0Z" /></svg>
    </span>
    <slot />
  </component>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { useContext } from './context'

const props = withDefaults(
  defineProps<{
    href?: string
    newTab?: boolean
    value?: string
  }>(),
  {
    href: '',
    newTab: false,
    value: ''
  }
)

const emit = defineEmits<{ click: [] }>()
const actionListContext = useContext()

const isSingleSelect = computed(() => actionListContext?.selectionMode.value === 'single')
const isSelectable = computed(() => isSingleSelect.value && Boolean(props.value))
const isSelected = computed(() => isSelectable.value && actionListContext?.modelValue.value === props.value)

const handleClick = () => {
  if (isSelectable.value && props.value) {
    actionListContext?.select(props.value)
  }

  emit('click')
}
</script>

<style scoped>
.action-list-item {
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

.action-list-check {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  width: 16px;
  height: 16px;
  flex-shrink: 0;
  color: var(--fgColor-muted, #59636e);
}

.action-list-check svg {
  opacity: 0;
  transition: opacity 0.15s ease;
}

.action-list-check[data-checked] svg {
  opacity: 1;
}

.action-list-item:hover {
  background: var(--control-transparent-bgColor-hover, #818b981a);
}
</style>
