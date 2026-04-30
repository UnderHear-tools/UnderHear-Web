import { get } from '@/api'
import type { UserInfo } from '@/stores/user'

export const getPublicUserProfile = (nickname: string) =>
  get<UserInfo>(`/users/${encodeURIComponent(nickname)}`)
