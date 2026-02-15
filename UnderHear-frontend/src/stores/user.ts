import { computed, ref } from 'vue'
import { defineStore } from 'pinia'
import { get } from '@/api'

export type UserInfo = {
  uuid: string
  nickname: string
  avatarUrl: string
}

export const useUserStore = defineStore('user', () => {
  const userInfo = ref<UserInfo | null>(null)
  const isHydrated = ref(false)
  const isLoggedIn = computed(() => userInfo.value !== null)

  const setUserInfo = (nextUserInfo: UserInfo) => {
    userInfo.value = nextUserInfo
  }

  const clearUserInfo = () => {
    userInfo.value = null
  }

  const markHydrated = () => {
    isHydrated.value = true
  }

  const hydrateUser = async () => {
    try {
      const currentUser = await get<UserInfo>('/auth/user', { withCredentials: true })
      setUserInfo(currentUser)
    } catch {
      clearUserInfo()
    } finally {
      markHydrated()
    }
  }

  return {
    userInfo,
    isHydrated,
    isLoggedIn,
    setUserInfo,
    clearUserInfo,
    markHydrated,
    hydrateUser,
  }
})
