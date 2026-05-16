import { get, post } from '@/api'

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

export type SaveCurrentUserMarkdownRequest = {
  content: string
}

export type CurrentUserProfile = {
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
}

export type SaveCurrentUserProfileRequest = {
  bio: string
  pronoun: string
  location: string
  socialAccount0: string
  socialAccount1: string
  socialAccount2: string
}

export const getPublicUserProfile = (nickname: string) =>
  get<PublicUserProfile>(`/users/${encodeURIComponent(nickname)}`)

export const saveCurrentUserMarkdown = (request: SaveCurrentUserMarkdownRequest) =>
  post<void>('/users/me/markdown', request, { withCredentials: true })

export const saveCurrentUserProfile = (request: SaveCurrentUserProfileRequest) =>
  post<CurrentUserProfile>('/users/me/profile', request, { withCredentials: true })
