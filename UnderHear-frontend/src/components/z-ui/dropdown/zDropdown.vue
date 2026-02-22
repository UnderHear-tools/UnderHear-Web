<template>
  <div class="z-dropdown" ref="dropdownRef">
    <div class="z-dropdown-trigger" @click="isOpen = !isOpen">
      <slot name="trigger" />
    </div>
    <Transition name="z-dropdown-fade">
      <div v-if="isOpen" class="z-dropdown-content" :class="placementClass">
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
  border: 1px solid var(--borderColor-default, #d1d9e0);
  border-radius: 6px;
  background: var(--bgColor-default, #ffffff);
  box-shadow: 0 8px 24px color-mix(in srgb, var(--fgColor-muted, #59636e) 20%, var(--bgColor-transparent, #ffffff00));
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

.z-dropdown-fade-enter-active,
.z-dropdown-fade-leave-active {
  transition: opacity 0.15s, transform 0.15s;
}

.z-dropdown-fade-enter-from,
.z-dropdown-fade-leave-to {
  opacity: 0;
  transform: translateY(-6px);
}
</style>
