<template>
  <div class="min-h-[calc(100vh-80px)] bg-[#F5F5F5] flex items-center justify-center px-4 py-12">
    <div class="w-full max-w-lg rounded-[28px] border border-[#E6E6E6] bg-white/60 backdrop-blur-2xl shadow-[0_20px_45px_rgba(0,0,0,0.05)] p-10">
      <div class="mb-8 text-center space-y-2">
        <h1 class="text-3xl font-semibold text-[#111111]">欢迎回来</h1>
        <p class="text-[#727272] text-sm">使用 GitHub 账号登录，解锁 UnderHear 的更多功能</p>
      </div>

      <div
        v-if="statusMessage"
        :class="[
          'mb-6 rounded-[16px] px-4 py-3 text-sm border',
          statusType === 'success'
            ? 'border-[#85D996] bg-[#E9F8ED] text-[#217A38]'
            : 'border-[#F5C2C7] bg-[#FCECEF] text-[#a61d24]'
        ]"
      >
        {{ statusMessage }}
      </div>

      <div class="space-y-6">
        <button
          class="w-full flex items-center justify-center gap-3 rounded-[18px] border border-[#111111] bg-[#111111] text-white py-3 text-base font-medium transition-all hover:bg-transparent hover:text-[#111111] disabled:cursor-not-allowed disabled:opacity-60"
          @click="startGithubLogin"
          :disabled="loading"
        >
          <svg
            class="h-5 w-5"
            viewBox="0 0 24 24"
            fill="currentColor"
            aria-hidden="true"
          >
            <path
              fill-rule="evenodd"
              clip-rule="evenodd"
              d="M12 .5C5.65.5.5 5.65.5 12c0 5.1 3.3 9.4 7.9 10.9.6.1.8-.2.8-.5v-2c-3.3.7-4-1.6-4-1.6-.5-1.2-1.1-1.5-1.1-1.5-.9-.6.1-.6.1-.6 1 .1 1.5 1 1.5 1 .9 1.5 2.4 1.1 3 .9.1-.6.4-1.1.7-1.3-2.7-.3-5.5-1.3-5.5-5.9 0-1.3.5-2.4 1.1-3.2-.1-.3-.5-1.5.1-3.1 0 0 .9-.3 3.2 1.1a11 11 0 0 1 5.8 0c2.3-1.4 3.2-1.1 3.2-1.1.6 1.6.2 2.8.1 3.1.7.8 1.1 1.9 1.1 3.2 0 4.6-2.9 5.6-5.6 5.9.4.3.7.9.7 1.8v2.6c0 .3.2.6.8.5A10.9 10.9 0 0 0 23.5 12C23.5 5.65 18.35.5 12 .5Z"
            />
          </svg>
          通过 GitHub 登录
        </button>

        <p class="text-center text-xs text-[#999999]">
          登录即表示同意 UnderHear 的
          <a href="#" class="text-[#111111] underline-offset-4 hover:underline">服务条款</a>
          和
          <a href="#" class="text-[#111111] underline-offset-4 hover:underline">隐私政策</a>
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

<style scoped></style>

