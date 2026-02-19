import { post } from '@/api'

export const logout = () => post<void>('/auth/logout', undefined, { withCredentials: true })