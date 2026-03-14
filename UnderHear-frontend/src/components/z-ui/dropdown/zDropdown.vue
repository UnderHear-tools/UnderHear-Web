<template>
  <div
    ref="dropdownRef"
    class="z-dropdown"
  >
    <div
      class="z-dropdown-trigger"
      :data-open="isOpen || undefined"
      @click="isOpen = !isOpen"
    >
      <slot name="trigger" />
    </div>
    <Transition name="z-dropdown-fade">
      <div
        v-if="isOpen"
        class="z-dropdown-content"
        :class="placementClass"
        @click="onContentClick"
      >
        <slot name="content" />
      </div>
    </Transition>
  </div>
</template>

<script setup lang="ts">
import { computed, ref, onMounted } from 'vue'

const props = withDefaults(
  defineProps<{
    placement?: 'left-top' | 'right-top' | 'left-bottom' | 'right-bottom'
  }>(),
  {
    placement: 'right-bottom'
  }
)

const isOpen = ref(false)
const dropdownRef = ref<HTMLElement>()

const placementClass = computed(() => `z-dropdown-content--${props.placement}`)

onMounted(() => {
  document.addEventListener('click', (e: MouseEvent) => {
    if (!dropdownRef.value?.contains(e.target as Node)) isOpen.value = false
  })
})

function onContentClick(e: MouseEvent) {
  if (!(e.target as HTMLElement).closest('[data-keep-open]')) {
    isOpen.value = false
  }
}

defineExpose({ close: () => { isOpen.value = false } })
</script>

<style scoped>
.z-dropdown {
  position: relative;
}

.z-dropdown-trigger {
  cursor: pointer;
}

.z-dropdown-content {
  position: absolute;
  z-index: 1000;
  min-width: 192px;
  max-width: calc(100vw - 2rem);
  max-height: 100vh;
  width: auto;
  border-radius: 12px;
  background: var(--overlay-bgColor, #ffffff);
  box-shadow: var(--shadow-floating-small, 0px 0px 0px 1px #d1d9e080, 0px 6px 12px -3px #25292e0a, 0px 6px 18px 0px #25292e1f);
}

.z-dropdown-content--left-bottom {
  top: calc(100% + 4px);
  right: 0;
}

.z-dropdown-content--right-bottom {
  top: calc(100% + 4px);
  left: 0;
}

.z-dropdown-content--left-top {
  bottom: calc(100% + 4px);
  right: 0;
}

.z-dropdown-content--right-top {
  bottom: calc(100% + 4px);
  left: 0;
}

.z-dropdown-fade-enter-active {
  transition: transform 0.15s;
}

.z-dropdown-fade-enter-from {
  transform: translateY(-8px);
}

/* 弹出时自动给 trigger 内的 Button 添加激活样式 */
.z-dropdown-trigger[data-open] :deep(.z-button[data-variant='default']) {
  background-color: var(--button-default-bgColor-active, var(--control-bgColor-active, #ebecf0));
  border-color: var(--button-default-borderColor-active, var(--button-default-borderColor-rest, #d0d7de));
}

.z-dropdown-trigger[data-open] :deep(.z-button[data-variant='primary']) {
  background-color: var(--button-primary-bgColor-active, #197935);
  border-color: var(--button-primary-borderColor-active, var(--button-primary-borderColor-rest, #1f232826));
  box-shadow: var(--button-primary-shadow-selected, var(--shadow-resting-small, 0 1px 0 0 #1f23280a));
}

.z-dropdown-trigger[data-open] :deep(.z-button[data-variant='danger']) {
  background-color: var(--button-danger-bgColor-active, var(--bgColor-danger-emphasis, #cf222e));
  border-color: var(--button-danger-borderColor-active, var(--button-danger-borderColor-hover, #1f232826));
  box-shadow: var(--button-danger-shadow-selected, var(--shadow-resting-small, 0 1px 0 0 #1f23280a));
  color: var(--button-danger-fgColor-active, #ffffff);
}

.z-dropdown-trigger[data-open] :deep(.z-button[data-variant='invisible']) {
  background-color: var(--button-invisible-bgColor-active, var(--control-transparent-bgColor-active, rgba(129, 139, 152, 0.16)));
}

.z-dropdown-trigger[data-open] :deep(.z-button[data-variant='link']) {
  text-decoration: underline;
}
</style>
