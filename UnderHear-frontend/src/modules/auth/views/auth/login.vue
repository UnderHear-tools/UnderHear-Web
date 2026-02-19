<template>
  <div class="login-page">
    <div class="login-card">
      <div class="login-header">
        <h1 class="login-title">登录</h1>
        <p class="login-subtitle">使用 第三方 账号登录</p>
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
          <LogoGitee size="20" color="#c71d23" />
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
import { useRoute } from 'vue-router'
import { useUserStore } from '@/stores/user'
import { zBanner } from '@/components/z-ui/banner'
import { LogoGitee, MarkGithub } from '@/components/z-ui/icon/Octicons-vue'
import { getOAuthRenderUrl, loginWithOAuthCallback } from '../../api/login'

const userStore = useUserStore()
const route = useRoute()

const loading = ref(false)
const currentProvider = ref<string | null>(null)
const oauthProviderKey = 'oauth_provider'
const LoginReturnToKey = 'login_return_to'
// 从登录页 query 中读取登录成功后的回跳地址
const getReturnTo = () => {
  const returnTo = (route.query.return_to as string)
  if (route.query.return_to) {
    sessionStorage.setItem(LoginReturnToKey, returnTo)
  }
  return returnTo
}

// 点击第三方登录时，先缓存 provider 和回跳地址，再跳转授权页
const startLogin = (provider: string) => {
  loading.value = true
  currentProvider.value = provider
  sessionStorage.setItem(oauthProviderKey, provider)
  window.location.href = getOAuthRenderUrl(provider)
}

const getButtonLabel = (provider: string) => {
  if (!loading.value || currentProvider.value !== provider) {
    return `使用 ${provider} 登录`
  }
  return '登录中...'
}

const login = async () => {
  const url = new URL(window.location.href)

  // 第三方授权失败，提示后结束
  if (url.searchParams.get('error')) {
    zBanner.warning('第三方授权失败')
    sessionStorage.removeItem(oauthProviderKey)
    return
  }

  const code = url.searchParams.get('code')
  const state = url.searchParams.get('state')
  // 没有 code/state 说明当前不是 OAuth 回调
  if (!code || !state) return
  // 清理回调参数，避免刷新时重复处理
  window.history.replaceState({}, document.title, url.pathname)

  const provider = sessionStorage.getItem(oauthProviderKey)
  if (!provider) return
  loading.value = true
  currentProvider.value = provider
  try {
    // 用回调参数换取登录态
    const response = await loginWithOAuthCallback(provider, { code, state })
    userStore.setUserInfo(response.userInfo)
    // 登录成功后跳回登录前页面
    const returnTo = sessionStorage.getItem(LoginReturnToKey) || '/'
    window.location.href = returnTo
    sessionStorage.removeItem(LoginReturnToKey)
  } catch (error) {
    zBanner.error(error instanceof Error ? error.message : '登录失败，请稍后重试。')
  } finally {
    sessionStorage.removeItem(oauthProviderKey)
    loading.value = false
    currentProvider.value = null
  }
}

onMounted(() => {
  getReturnTo()
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
