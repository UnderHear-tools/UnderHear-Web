<template>
  <div class="logout-page">
    <section class="logout-panel">
      <h2 class="logout-title">Select account to sign out</h2>

      <div class="account-card">
        <div class="account-meta">
          <zAvatar :src="userStore.userInfo?.avatarUrl" :size="32" />

          <div>
            <p class="account-label">Signed in as</p>
            <p class="account-name">{{ userStore.userInfo?.nickname }}</p>
          </div>
        </div>

        <button type="button" class="action-button">Sign out</button>
      </div>

      <button type="button" class="action-button action-button--danger action-button--full">
        Sign out from all accounts
      </button>
    </section>
  </div>
</template>

<script setup lang="ts">
import { zAvatar } from '@/components/z-ui/avatar';
import { useUserStore } from '@/stores/user'

const userStore = useUserStore()
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
