import { API_BASE_URL, get } from '@/api'

export type OAuthLoginResponse = {
  loginSource: string
  userInfo: {
    uuid: string
    nickname: string
    email: string
    avatarUrl: string
  }
}

export type OAuthCallbackParams = {
  code: string
  state: string
}

export const getOAuthRenderUrl = (provider: string) =>
  new URL(`/oauth/${provider}/render`, API_BASE_URL).toString()

export const loginWithOAuthCallback = (provider: string, params: OAuthCallbackParams) => {
  const query = { code: params.code, state: params.state }
  return get<OAuthLoginResponse>(`/oauth/${provider}/callback`, { params: query, withCredentials: true })
}
