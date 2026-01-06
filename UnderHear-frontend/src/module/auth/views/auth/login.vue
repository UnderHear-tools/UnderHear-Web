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
          @click="startGithubLogin" :disabled="loading">
          <svg class="github-icon" viewBox="0 0 24 24" fill="currentColor" aria-hidden="true">
            <path
              d="M12 1C5.923 1 1 5.923 1 12c0 4.867 3.149 8.979 7.521 10.436.55.096.756-.233.756-.522 0-.262-.013-1.128-.013-2.049-2.764.509-3.479-.674-3.699-1.292-.124-.317-.66-1.293-1.127-1.554-.385-.207-.936-.715-.014-.729.866-.014 1.485.797 1.691 1.128.99 1.663 2.571 1.196 3.204.907.096-.715.385-1.196.701-1.471-2.448-.275-5.005-1.224-5.005-5.432 0-1.196.426-2.186 1.128-2.956-.111-.275-.496-1.402.11-2.915 0 0 .921-.288 3.024 1.128a10.193 10.193 0 0 1 2.75-.371c.936 0 1.871.123 2.75.371 2.104-1.43 3.025-1.128 3.025-1.128.605 1.513.221 2.64.111 2.915.701.77 1.127 1.747 1.127 2.956 0 4.222-2.571 5.157-5.019 5.432.399.344.743 1.004.743 2.035 0 1.471-.014 2.654-.014 3.025 0 .289.206.632.756.522C19.851 20.979 23 16.854 23 12c0-6.077-4.922-11-11-11Z">
            </path>
          </svg>
          <span>{{ loading ? '登录中...' : '通过 GitHub 登录' }}</span>
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
import { useRoute, useRouter } from 'vue-router'
import {
  type AuthResponse,
  loginWithGithub,
  persistAuth,
  requestGithubAuthorizeUrl
} from '@/api/auth'

const route = useRoute()
const router = useRouter()
const loading = ref(false)
const statusMessage = ref('')
const statusType = ref<'success' | 'error'>('success')

const GITHUB_STATE_KEY = 'underhear_github_state'

const generateState = () => {
  if (typeof crypto !== 'undefined' && 'randomUUID' in crypto) {
    return crypto.randomUUID()
  }
  return Math.random().toString(36).slice(2)
}

const resetStatus = () => {
  statusMessage.value = ''
}

const setStatus = (message: string, type: 'success' | 'error') => {
  statusMessage.value = message
  statusType.value = type
}

const startGithubLogin = async () => {
  if (loading.value) return
  resetStatus()
  loading.value = true

  try {
    const generatedState = generateState()
    sessionStorage.setItem(GITHUB_STATE_KEY, generatedState)
    const response = await requestGithubAuthorizeUrl(generatedState)
    sessionStorage.setItem(GITHUB_STATE_KEY, response.state)
    window.location.href = response.authorizeUrl
  } catch (error) {
    console.error(error)
    setStatus('无法跳转至 GitHub 登录，请稍后再试。', 'error')
    loading.value = false
  }
}

const finalizeLogin = async (code: string, state?: string) => {
  const storedState = sessionStorage.getItem(GITHUB_STATE_KEY)
  if (storedState && state && storedState !== state) {
    setStatus('登录状态校验失败，请重试。', 'error')
    sessionStorage.removeItem(GITHUB_STATE_KEY)
    await router.replace({ path: route.path, query: {} })
    loading.value = false
    return
  }

  loading.value = true
  try {
    const authResponse: AuthResponse = await loginWithGithub(code, state)
    persistAuth(authResponse)
    setStatus('登录成功，即将跳转...', 'success')
    sessionStorage.removeItem(GITHUB_STATE_KEY)
    await router.replace({ path: route.path, query: {} })
    setTimeout(() => {
      router.push('/tool')
    }, 1200)
  } catch (error) {
    console.error(error)
    setStatus(error instanceof Error ? error.message : '登录失败，请稍后再试。', 'error')
    await router.replace({ path: route.path, query: {} })
    sessionStorage.removeItem(GITHUB_STATE_KEY)
  } finally {
    loading.value = false
  }
}

onMounted(async () => {
  const code = route.query.code as string | undefined
  const state = route.query.state as string | undefined
  if (code) {
    await finalizeLogin(code, state)
  }
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
  border: 1px solid #d1d9e6;
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
  border: 1px solid #d1d5db;
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
