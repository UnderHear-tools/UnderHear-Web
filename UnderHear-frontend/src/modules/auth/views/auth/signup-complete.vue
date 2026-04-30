<template>
  <div class="signup-page">
    <form
      class="signup-card"
      @submit.prevent="submit"
    >
      <div class="signup-header">
        <div class="signup-avatar">
          <img
            v-if="signupContext?.avatarUrl"
            :src="signupContext.avatarUrl"
            alt=""
          >
          <span v-else>{{ avatarPlaceholder }}</span>
        </div>
        <div>
          <h1 class="signup-title">
            完善资料
          </h1>
          <p class="signup-subtitle">
            {{ providerLabel }} 登录
          </p>
        </div>
      </div>

      <div class="signup-fields">
        <FormControl required>
          <FormControl.Label>用户名</FormControl.Label>
          <zInput
            v-model="nickname"
            autocomplete="username"
            placeholder="underhear"
          />
          <FormControl.Validation
            v-if="nicknameError"
            variant="error"
          >
            {{ nicknameError }}
          </FormControl.Validation>
          <FormControl.Caption>
            仅支持字母、数字、下划线和连字符，最长 20 个字符。
          </FormControl.Caption>
        </FormControl>

        <FormControl required>
          <FormControl.Label>邮箱</FormControl.Label>
          <zInput
            v-model="email"
            type="email"
            autocomplete="email"
            placeholder="you@example.com"
          />
          <FormControl.Validation
            v-if="emailError"
            variant="error"
          >
            {{ emailError }}
          </FormControl.Validation>
        </FormControl>
      </div>

      <div class="signup-actions">
        <zButton
          type="submit"
          variant="primary"
          :loading="loading"
          :disabled="!canSubmit"
        >
          完成注册
        </zButton>
      </div>
    </form>
  </div>
</template>

<script setup lang="ts">
import { computed, onMounted, ref } from 'vue'
import { useUserStore } from '@/stores/user'
import { FormControl } from '@/components/z-ui/form-control'
import { zInput } from '@/components/z-ui/input'
import { zButton } from '@/components/z-ui/button'
import { zBanner } from '@/components/z-ui/banner'
import { completeOAuthSignup } from '../../api/login'
import {
  clearOAuthSignupContext,
  loginReturnToKey,
  readOAuthSignupContext,
  type OAuthSignupContext
} from '../../utils/session'

const userStore = useUserStore()
const signupContext = ref<OAuthSignupContext | null>(null)
const nickname = ref('')
const email = ref('')
const loading = ref(false)

const nicknamePattern = /^[A-Za-z0-9_-]{1,20}$/
const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/

const providerLabel = computed(() => {
  if (signupContext.value?.provider === 'github') return 'GitHub'
  if (signupContext.value?.provider === 'gitee') return 'Gitee'
  return 'OAuth'
})

const avatarPlaceholder = computed(() => providerLabel.value.slice(0, 2).toUpperCase())

const nicknameError = computed(() => {
  const value = nickname.value.trim()
  if (!value) return '请输入用户名'
  if (!nicknamePattern.test(value)) return '用户名格式不正确'
  return ''
})

const emailError = computed(() => {
  const value = email.value.trim()
  if (!value) return '请输入邮箱'
  if (value.length > 50 || !emailPattern.test(value)) return '邮箱格式不正确'
  return ''
})

const canSubmit = computed(() => {
  return Boolean(signupContext.value?.pendingSignupToken) && !nicknameError.value && !emailError.value && !loading.value
})

const submit = async () => {
  if (!canSubmit.value || !signupContext.value) {
    zBanner.warning('请检查资料后再提交。')
    return
  }
  loading.value = true
  try {
    const response = await completeOAuthSignup({
      pendingSignupToken: signupContext.value.pendingSignupToken,
      nickname: nickname.value.trim(),
      email: email.value.trim()
    })
    userStore.setUserInfo(response.userInfo)
    const returnTo = sessionStorage.getItem(loginReturnToKey) || '/'
    sessionStorage.removeItem(loginReturnToKey)
    clearOAuthSignupContext()
    window.location.href = returnTo
  } catch (error) {
    zBanner.error(error instanceof Error ? error.message : '注册失败，请稍后重试。')
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  const context = readOAuthSignupContext()
  if (!context) {
    zBanner.warning('注册会话已失效，请重新登录。')
    window.location.href = '/auth/login'
    return
  }
  signupContext.value = context
  nickname.value = context.suggestedNickname ?? ''
  email.value = context.email ?? ''
})
</script>

<style scoped>
.signup-page {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 48px 16px;
}

.signup-card {
  width: 100%;
  max-width: 28rem;
  border: 1px solid var(--borderColor-default);
  background: var(--bgColor-default);
  border-radius: 8px;
  padding: 32px;
  display: grid;
  gap: 28px;
}

.signup-header {
  display: flex;
  align-items: center;
  gap: 12px;
}

.signup-avatar {
  width: 48px;
  height: 48px;
  border-radius: 50%;
  overflow: hidden;
  display: grid;
  place-items: center;
  border: 1px solid var(--borderColor-default);
  background: var(--bgColor-muted);
  color: var(--fgColor-muted);
  font-size: 14px;
  font-weight: 600;
  flex: 0 0 auto;
}

.signup-avatar img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.signup-title {
  font-size: 24px;
  font-weight: 500;
  color: var(--fgColor-default);
}

.signup-subtitle {
  color: var(--fgColor-muted);
  font-size: 14px;
  margin-top: 4px;
}

.signup-fields {
  display: grid;
  gap: 18px;
}

.signup-fields :deep(.z-input) {
  width: 100%;
}

.signup-actions {
  display: flex;
  justify-content: flex-end;
}

@media (max-width: 480px) {
  .signup-card {
    padding: 24px;
  }

  .signup-actions :deep(.z-button) {
    width: 100%;
  }
}
</style>
