<template>
  <div class="z-user-menu" ref="menuRef">
    <!-- Avatar Button -->
    <button
      type="button"
      class="z-user-menu-trigger"
      :class="{ active: isOpen }"
      @click="isOpen = !isOpen"
    >
      <span class="z-user-menu-placeholder">U</span>
    </button>

    <!-- Dropdown Menu -->
    <Transition name="z-user-menu-fade">
      <div v-if="isOpen" class="z-user-menu-dropdown">
        <!-- User Info -->
        <div class="z-user-menu-header">
          <div class="z-user-menu-info">
            <div class="z-user-menu-name">UnderHear Studio</div>
            <div class="z-user-menu-email">hello@underhear.audio</div>
          </div>
        </div>

        <!-- Menu Items -->
        <div class="z-user-menu-content">
          <div class="z-user-menu-divider"></div>
          <a href="https://github.com/underhear" target="_blank" class="z-user-menu-link">
            <span class="z-user-menu-label">前往 GitHub</span>
          </a>
          <div class="z-user-menu-divider"></div>
          <button class="z-user-menu-link" @click="isOpen = false">
            <span class="z-user-menu-label">退出登录</span>
          </button>
        </div>
      </div>
    </Transition>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'

const isOpen = ref(false)
const menuRef = ref<HTMLElement>()

onMounted(() => {
  document.addEventListener('click', (e: MouseEvent) => {
    if (!menuRef.value?.contains(e.target as Node)) isOpen.value = false
  })
})
</script>

<style scoped>
.z-user-menu {
  position: relative;
}

.z-user-menu-trigger {
  width: 40px;
  height: 40px;
  border: 1px solid #d9d9d9;
  border-radius: 50%;
  background: rgba(255, 255, 255, 0.8);
  backdrop-filter: blur(8px);
  cursor: pointer;
  font-weight: 600;
}

.z-user-menu-trigger:hover,
.z-user-menu-trigger.active {
  background: #fff;
  box-shadow: 0 0 0 2px rgba(0, 0, 0, 0.08);
}

.z-user-menu-dropdown {
  position: absolute;
  top: calc(100% + 8px);
  right: 0;
  z-index: 1000;
  border: 1px solid #d0d7de;
  border-radius: 6px;
  background: #fff;
  box-shadow: 0 8px 24px rgba(140, 149, 159, 0.2);
}

.z-user-menu-fade-enter-active,
.z-user-menu-fade-leave-active {
  transition: opacity 0.15s, transform 0.15s;
}

.z-user-menu-fade-enter-from,
.z-user-menu-fade-leave-to {
  opacity: 0;
  transform: translateY(-6px);
}

.z-user-menu-header {
  padding: 12px 12px 0;
}

.z-user-menu-name {
  font-size: 14px;
  font-weight: 600;
  color: #1f2328;
}

.z-user-menu-email {
  font-size: 12px;
  color: #656d76;
}

.z-user-menu-content {
  padding: 8px;
}

.z-user-menu-divider {
  border-bottom: 1px solid #d1d9e0b3;
  margin: 8px 0;
}

.z-user-menu-link {
  display: block;
  width: 100%;
  padding: 6px 8px;
  border: none;
  border-radius: 6px;
  background: transparent;
  color: #1f2328;
  font-size: 14px;
  text-align: left;
  text-decoration: none;
  cursor: pointer;
}

.z-user-menu-link:hover {
  background: #f6f8fa;
}

@media (max-width: 767px) {
  .z-user-menu-trigger {
    width: 32px;
    height: 32px;
  }
}
</style>
