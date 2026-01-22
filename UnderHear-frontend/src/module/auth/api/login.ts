import { API_BASE_URL, get } from '@/api'

export type OAuthProvider = 'github' | 'gitee'

export type OAuthLoginResponse = {
  token: string
  loginSource: string
  userInfo: {
    uuid: string
    nickname: string
    avatarUrl: string
  }
}

export type OAuthCallbackParams = {
  code: string
  state?: string
}

export const getOAuthRenderUrl = (provider: OAuthProvider) =>
  new URL(`/oauth/${provider}/render`, API_BASE_URL).toString()

export const loginWithOAuthCallback = (provider: OAuthProvider, params: OAuthCallbackParams) => {
  const query: Record<string, string> = { code: params.code }
  if (params.state) {
    query.state = params.state
  }
  return get<OAuthLoginResponse>(`/oauth/${provider}/callback`, { params: query })
}
