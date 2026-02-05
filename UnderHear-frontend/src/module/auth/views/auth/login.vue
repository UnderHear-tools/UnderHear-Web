<template>
  <div class="login-page">
    <div class="login-card">
      <div class="login-header">
        <h1 class="login-title">登录</h1>
        <p class="login-subtitle">使用 GitHub 账号登录</p>
      </div>

      <div v-if="statusMessage" :class="[
        'status-banner',
        statusType === 'success'
          ? 'status-banner--success'
          : 'status-banner--error'
      ]">
        {{ statusMessage }}
      </div>

      <div class="login-actions">
        <button
          class="github-button"
          @click="startLogin('github')"
          :disabled="loading">
          <MarkGithub size="20" />
          <span>{{ getButtonLabel('github') }}</span>
        </button>
        <button
          class="github-button"
          @click="startLogin('gitee')"
          :disabled="loading">
          <svg xmlns="http://www.w3.org/2000/svg" fill="#c71d23" width="20px" height="20px" viewBox="0 0 24 24" role="img"><path d="M11.984 0A12 12 0 0 0 0 12a12 12 0 0 0 12 12 12 12 0 0 0 12-12A12 12 0 0 0 12 0a12 12 0 0 0-.016 0zm6.09 5.333c.328 0 .593.266.592.593v1.482a.594.594 0 0 1-.593.592H9.777c-.982 0-1.778.796-1.778 1.778v5.63c0 .327.266.592.593.592h5.63c.982 0 1.778-.796 1.778-1.778v-.296a.593.593 0 0 0-.592-.593h-4.15a.592.592 0 0 1-.592-.592v-1.482a.593.593 0 0 1 .593-.592h6.815c.327 0 .593.265.593.592v3.408a4 4 0 0 1-4 4H5.926a.593.593 0 0 1-.593-.593V9.778a4.444 4.444 0 0 1 4.445-4.444h8.296z"/></svg>
          <span>{{ getButtonLabel('gitee') }}</span>
        </button>

        <p class="login-terms">
          登录即表示同意
          <a href="#" class="login-link">服务条款</a>
          和
          <a href="#" class="login-link">隐私政策</a>
        </p>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { MarkGithub } from '@/components/z-ui/icon/Octicons-vue'
import { setAuthToken } from '@/api/request'
import { getOAuthRenderUrl, loginWithOAuthCallback } from '../../api/login'

const loading = ref(false)
const loadingStage = ref<'redirecting' | 'loggingIn' | null>(null)
const currentProvider = ref<string | null>(null)
const statusMessage = ref('')
const statusType = ref<'success' | 'error'>('success')
const oauthProviderKey = 'oauth_provider'

const setStatus = (message: string, type: 'success' | 'error') => {
  statusMessage.value = message
  statusType.value = type
}

const startLogin = (provider: string) => {
  loading.value = true
  loadingStage.value = 'redirecting'
  currentProvider.value = provider
  sessionStorage.setItem(oauthProviderKey, provider)
  window.location.href = getOAuthRenderUrl(provider)
}

const getButtonLabel = (provider: string) => {
  if (!loading.value || currentProvider.value !== provider) {
    return `使用 ${provider} 登录`
  }
  return loadingStage.value === 'redirecting' ? '跳转中...' : '登录中...'
}

const login = async () => {
  const url = new URL(window.location.href)
  const code = url.searchParams.get('code')
  const state = url.searchParams.get('state')
  if (!code || !state) return

  const provider = sessionStorage.getItem(oauthProviderKey)
  if (!provider) return
  loading.value = true
  loadingStage.value = 'loggingIn'
  currentProvider.value = provider
  try {
    const response = await loginWithOAuthCallback(provider, { code, state })
    setAuthToken(response.token)
    console.log('登录成功，Token:', response.token)
    setStatus('登录成功！正在跳转...', 'success')
  } finally {
    loading.value = false
    loadingStage.value = null
    sessionStorage.removeItem(oauthProviderKey)
    window.history.replaceState({}, document.title, url.pathname)
  }
}

onMounted(() => {
  login()
})
</script>

<style scoped>
.login-page {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 48px 16px;
}

.login-card {
  width: 100%;
  max-width: 28rem;
  border: 1px solid var(--border-gray);
  background: #ffffff;
  border-radius: 8px;
  padding: 32px;
}

.login-header {
  margin-bottom: 32px;
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.login-title {
  font-size: 24px;
  font-weight: 500;
  color: #111111;
}

.login-subtitle {
  color: #6b7280;
  font-size: 14px;
}

.status-banner {
  margin-bottom: 24px;
  border-radius: 6px;
  padding: 10px 16px;
  font-size: 14px;
  border: 1px solid transparent;
}

.status-banner--success {
  background: #f0fdf4;
  color: #15803d;
  border-color: #bbf7d0;
}

.status-banner--error {
  background: #fef2f2;
  color: #b91c1c;
  border-color: #fecaca;
}

.login-actions {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.github-button {
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
  border-radius: 6px;
  border: 1px solid var(--border-gray);
  background: #f6f8fa;
  color: #111111;
  padding: 10px 12px;
  font-size: 14px;
  font-weight: 500;
  cursor: pointer;
  transition: background-color 0.2s ease, border-color 0.2s ease, color 0.2s ease;
}

.github-button:hover {
  background: #eff2f5;
}

.github-button:disabled {
  opacity: 0.7;
  cursor: not-allowed;
}

.github-icon {
  width: 20px;
  height: 20px;
}

.login-terms {
  text-align: center;
  font-size: 12px;
  color: #9ca3af;
  line-height: 1.6;
}

.login-link {
  color: #4b5563;
  text-decoration: none;
  transition: color 0.2s ease;
}

.login-link:hover {
  color: #111111;
}
</style>
