const API_BASE_URL = 'http://localhost:8080';

export interface GithubAuthorizeResponse {
  authorizeUrl: string
  state: string
}

export interface UserProfile {
  id: number
  githubId: number
  login: string
  name?: string
  avatarUrl?: string
  email?: string
  bio?: string
  htmlUrl?: string
}

export interface AuthResponse {
  token: string
  user: UserProfile
}

export async function requestGithubAuthorizeUrl(state?: string): Promise<GithubAuthorizeResponse> {
  const url = new URL('/api/auth/github/authorize', API_BASE_URL)
  if (state) {
    url.searchParams.set('state', state)
  }

  const response = await fetch(url.toString(), {
    method: 'GET'
  })

  if (!response.ok) {
    throw new Error('无法获取 GitHub 授权地址')
  }

  return response.json()
}

export async function loginWithGithub(code: string, state?: string): Promise<AuthResponse> {
  const response = await fetch(`${API_BASE_URL}/api/auth/github`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({ code, state })
  })

  if (!response.ok) {
    const errorBody = await response.json().catch(() => ({}))
    throw new Error(errorBody.message ?? 'GitHub 登录失败')
  }

  return response.json()
}

export function persistAuth(auth: AuthResponse) {
  localStorage.setItem('underhear_token', auth.token)
  localStorage.setItem('underhear_user', JSON.stringify(auth.user))
}

export function clearAuth() {
  localStorage.removeItem('underhear_token')
  localStorage.removeItem('underhear_user')
}

export function getStoredUser(): UserProfile | null {
  const raw = localStorage.getItem('underhear_user')
  if (!raw) return null
  try {
    return JSON.parse(raw) as UserProfile
  } catch (error) {
    clearAuth()
    return null
  }
}

export function logout() {
  clearAuth()
  // 触发 storage 事件通知其他窗口/标签页
  window.dispatchEvent(new StorageEvent('storage', {
    key: 'underhear_user',
    oldValue: localStorage.getItem('underhear_user'),
    newValue: null,
    url: window.location.href
  }))
}
