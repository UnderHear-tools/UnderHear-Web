<template>
  <div class="logout-page">
    <section class="logout-panel">
      <h2 class="logout-title">Select account to sign out</h2>

      <div class="account-card">
        <div class="account-meta">
          <zAvatar :src="userStore.userInfo?.avatarUrl" :size="32" />

          <div>
            <p class="account-label">当前登录</p>
            <p class="account-name">{{ userStore.userInfo?.nickname }}</p>
          </div>
        </div>

        <button
          type="button"
          class="action-button"
          :disabled="isSubmitting"
          @click="handleLogout">
          退出登录
        </button>
      </div>

      <button type="button" class="action-button action-button--danger action-button--full">
        从所有设备上登出
      </button>
    </section>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { zAvatar } from '@/components/z-ui/avatar'
import { zBanner } from '@/components/z-ui/banner'
import { useUserStore } from '@/stores/user'
import { logout } from '../../api/login'

const userStore = useUserStore()
const isSubmitting = ref(false)

const handleLogout = async () => {
  if (isSubmitting.value) return

  isSubmitting.value = true
  try {
    await logout()
    window.location.href = '/'
  } catch (error) {
    zBanner.error(error instanceof Error ? error.message : '退出失败，请稍后重试。')
  } finally {
    isSubmitting.value = false
  }
}
</script>

<style scoped>
.logout-page {
  min-height: 100vh;
  display: flex;
  align-items: flex-start;
  justify-content: center;
  padding: 56px 16px;
}

.logout-panel {
  padding: 16px;
}

.logout-title {
  margin: 0 0 24px;
  color: #1f2328;
  font-weight: 300;
  text-align: center;
}

.account-card {
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: 100%;
  gap: 64px;
  padding: 16px;
  border: 1px solid #c9d1da;
  border-radius: 6px;
}

.account-meta {
  display: flex;
  align-items: center;
  gap: 16px;
  min-width: 0;
}

.account-label,
.account-name {
  margin: 0;
  line-height: 1.3;
}

.account-label {
  font-size: 14px;
  color: #3f4b58;
}

.account-name {
  font-size: 14px;
  font-weight: 500;
  color: #1f2328;
  white-space: nowrap;
}

.action-button {
  padding: 8px 16px;
  border: 1px solid #c9d1da;
  border-radius: 6px;
  background: #f6f8fa;
  color: #1f2937;
  font-size: 12px;
  font-weight: 500;
  line-height: 1;
  cursor: pointer;
  white-space: nowrap;
}

.action-button:hover {
  background: #eff2f5;
}

.action-button:disabled {
  opacity: 0.7;
  cursor: not-allowed;
}

.action-button--danger {
  color: #d1242f;
}

.action-button--full {
  width: 100%;
  margin-top: 24px;
  padding: 12px 24px;
  font-size: 14px;
  line-height: 1.5;
}
</style>
