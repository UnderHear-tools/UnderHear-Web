<template>
  <div
    ref="dropdownRef"
    class="z-dropdown"
  >
    <div
      class="z-dropdown-trigger"
      @click="isOpen = !isOpen"
    >
      <slot name="trigger" />
    </div>
    <Transition name="z-dropdown-fade">
      <div
        v-if="isOpen"
        class="z-dropdown-content"
        :class="placementClass"
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
  top: calc(100% + 8px);
  right: 0;
}

.z-dropdown-content--right-bottom {
  top: calc(100% + 8px);
  left: 0;
}

.z-dropdown-content--left-top {
  bottom: calc(100% + 8px);
  right: 0;
}

.z-dropdown-content--right-top {
  bottom: calc(100% + 8px);
  left: 0;
}

.z-dropdown-fade-enter-active {
  transition: transform 0.15s;
}

.z-dropdown-fade-enter-from {
  transform: translateY(-8px);
}
</style>
