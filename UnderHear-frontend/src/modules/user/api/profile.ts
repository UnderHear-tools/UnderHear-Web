import { get } from '@/api'

export type PublicUserProfile = {
  uuid: string
  nickname: string
  email: string
  avatarUrl: string
  bio: string | null
  pronoun: string | null
  location: string | null
  socialAccount0: string | null
  socialAccount1: string | null
  socialAccount2: string | null
  markdown: string | null
}

export const getPublicUserProfile = (nickname: string) =>
  get<PublicUserProfile>(`/users/${encodeURIComponent(nickname)}`)
