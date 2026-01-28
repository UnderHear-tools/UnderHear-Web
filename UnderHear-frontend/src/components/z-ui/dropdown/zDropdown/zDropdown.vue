<template>
  <div class="z-dropdown" ref="dropdownRef">
    <div class="z-dropdown-trigger" @click="isOpen = !isOpen">
      <slot name="trigger" />
    </div>
    <Transition name="z-dropdown-fade">
      <div v-if="isOpen" class="z-dropdown-content">
        <slot />
      </div>
    </Transition>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'

const isOpen = ref(false)
const dropdownRef = ref<HTMLElement>()

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
  top: calc(100% + 8px);
  right: 0;
  z-index: 1000;
  border: 1px solid #d0d7de;
  border-radius: 6px;
  background: #fff;
  box-shadow: 0 8px 24px rgba(140, 149, 159, 0.2);
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
