<template>
  <div class="z-user-menu" ref="menuRef">
    <!-- Avatar Button -->
    <button
      type="button"
      class="z-user-menu-trigger"
      :class="{ active: isOpen }"
      @click="toggleMenu"
      aria-haspopup="true"
      :aria-expanded="isOpen"
    >
      <img
        v-if="user?.avatarUrl"
        :src="user.avatarUrl"
        :alt="user.login"
        class="z-user-menu-avatar"
        referrerpolicy="no-referrer"
      />
      <span v-else class="z-user-menu-placeholder">
        {{ user?.login?.charAt(0).toUpperCase() || '?' }}
      </span>
    </button>

    <!-- Dropdown Menu -->
    <Transition name="z-user-menu-fade">
      <div v-if="isOpen" class="z-user-menu-dropdown" role="dialog" aria-label="User navigation">
        <!-- User Info -->
        <div class="z-user-menu-header">
          <img
            v-if="user?.avatarUrl"
            :src="user.avatarUrl"
            :alt="user?.login"
            class="z-user-menu-avatar-large"
            referrerpolicy="no-referrer"
          />
          <div class="z-user-menu-info">
            <div class="z-user-menu-name">{{ user?.name || user?.login }}</div>
            <div class="z-user-menu-email">{{ user?.email }}</div>
          </div>
        </div>

        <!-- Menu Items -->
        <div class="z-user-menu-content">
          <div class="z-user-menu-divider"></div>
          <template v-for="(item, index) in menuItems" :key="index">
            <div v-if="item.type === 'divider'" class="z-user-menu-divider"></div>
            <component
              v-else
              :is="item.href ? 'a' : 'button'"
              :href="item.href"
              :target="item.external ? '_blank' : undefined"
              :rel="item.external ? 'noopener noreferrer' : undefined"
              class="z-user-menu-link"
              @click="handleItemClick(item)"
            >
              <span v-if="item.icon" class="z-user-menu-icon" v-html="item.icon"></span>
              <span class="z-user-menu-label">{{ item.label }}</span>
              <span v-if="item.badge" class="z-user-menu-badge">{{ item.badge }}</span>
            </component>
          </template>
        </div>
      </div>
    </Transition>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue'

export interface UserProfile {
  id?: number
  githubId?: number
  login: string
  name?: string
  avatarUrl?: string
  email?: string
  bio?: string
  htmlUrl?: string
}

export interface MenuItem {
  label: string
  icon?: string
  href?: string
  external?: boolean
  badge?: string
  type?: 'item' | 'divider'
  onClick?: () => void
}

interface Props {
  user?: UserProfile | null
  menuItems?: MenuItem[]
}

withDefaults(defineProps<Props>(), {
  user: null,
  menuItems: () => []
})

const emit = defineEmits<{
  itemClick: [item: MenuItem]
}>()

const isOpen = ref(false)
const menuRef = ref<HTMLElement | null>(null)

const toggleMenu = () => {
  isOpen.value = !isOpen.value
}

const handleItemClick = (item: MenuItem) => {
  if (item.onClick) {
    item.onClick()
  }
  emit('itemClick', item)
  if (!item.href || item.href.startsWith('#')) {
    isOpen.value = false
  }
}

const handleClickOutside = (event: MouseEvent) => {
  if (menuRef.value && !menuRef.value.contains(event.target as Node)) {
    isOpen.value = false
  }
}

const handleEscape = (event: KeyboardEvent) => {
  if (event.key === 'Escape' && isOpen.value) {
    isOpen.value = false
  }
}

onMounted(() => {
  document.addEventListener('click', handleClickOutside)
  document.addEventListener('keydown', handleEscape)
})

onUnmounted(() => {
  document.removeEventListener('click', handleClickOutside)
  document.removeEventListener('keydown', handleEscape)
})
</script>

<style scoped>
.z-user-menu {
  position: relative;
  display: inline-block;
}

.z-user-menu-trigger {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 40px;
  height: 40px;
  padding: 0;
  overflow: hidden;
  border: 1px solid #d9d9d9;
  border-radius: 50%;
  background: rgba(255, 255, 255, 0.8);
  backdrop-filter: blur(8px);
  cursor: pointer;
  transition: all 0.2s;
  outline: none;
}

.z-user-menu-trigger:hover,
.z-user-menu-trigger.active {
  background: #ffffff;
  box-shadow: 0 0 0 2px rgba(0, 0, 0, 0.05);
}

.z-user-menu-trigger.active {
  box-shadow: 0 0 0 2px rgba(0, 0, 0, 0.1);
}

.z-user-menu-avatar {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.z-user-menu-placeholder {
  font-size: 14px;
  font-weight: 600;
  color: #111111;
}

.z-user-menu-dropdown {
  position: absolute;
  top: calc(100% + 8px);
  right: 0;
  z-index: 1000;
  max-height: calc(100vh - 80px);
  overflow: auto;
  border: 1px solid #d0d7de;
  border-radius: 6px;
  background: #ffffff;
  box-shadow: 0 8px 24px rgba(140, 149, 159, 0.2);
}

.z-user-menu-fade-enter-active,
.z-user-menu-fade-leave-active {
  transition: opacity 0.15s ease, transform 0.15s ease;
}

.z-user-menu-fade-enter-from {
  opacity: 0;
  transform: translateY(-8px);
}

.z-user-menu-fade-leave-to {
  opacity: 0;
  transform: translateY(-4px);
}

.z-user-menu-header {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 12px 12px 0;
}

.z-user-menu-avatar-large {
  width: 32px;
  height: 32px;
  border-radius: 50%;
  flex-shrink: 0;
}

.z-user-menu-info {
  flex: 1;
  min-width: 0;
  display: flex;
  flex-direction: column;
  gap: 2px;
}

.z-user-menu-name {
  font-size: 14px;
  font-weight: 600;
  color: #1f2328;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.z-user-menu-email {
  font-size: 12px;
  color: #656d76;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.z-user-menu-content {
  padding: 8px;
}

.z-user-menu-divider {
  border-bottom: 1px solid #d1d9e0b3;
  margin: 8px 0;
}

.z-user-menu-link {
  display: flex;
  align-items: center;
  gap: 12px;
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
  transition: background-color 0.1s;
}

.z-user-menu-link:hover {
  background: #f6f8fa;
}

.z-user-menu-link:active {
  background: #eaeef2;
}

.z-user-menu-icon {
  display: flex;
  align-items: center;
  width: 16px;
  height: 16px;
  flex-shrink: 0;
  color: #656d76;
}

.z-user-menu-icon :deep(svg) {
  width: 16px;
  height: 16px;
  fill: currentColor;
}

.z-user-menu-label {
  flex: 1;
  min-width: 0;
}

.z-user-menu-badge {
  padding: 2px 6px;
  border-radius: 12px;
  background: #0969da;
  color: #ffffff;
  font-size: 12px;
  font-weight: 500;
}

.z-user-menu-dropdown::-webkit-scrollbar {
  width: 8px;
}

.z-user-menu-dropdown::-webkit-scrollbar-track {
  background: transparent;
}

.z-user-menu-dropdown::-webkit-scrollbar-thumb {
  background: #d0d7de;
  border-radius: 4px;
}

.z-user-menu-dropdown::-webkit-scrollbar-thumb:hover {
  background: #b4bcc5;
}

@media (max-width: 767px) {
  .z-user-menu-trigger{
    width: 32px;
    height: 32px;
  }
}
</style>

