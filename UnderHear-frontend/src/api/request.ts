import axios, { type AxiosRequestConfig } from 'axios'

import { API_BASE_URL } from './config'
import { unwrapApiResponse } from './response'
import type { ApiResponse } from './types'

const request = axios.create({
  baseURL: API_BASE_URL,
  timeout: 15000,
})

request.interceptors.response.use((response) => {
  response.data = unwrapApiResponse(response.data as ApiResponse<unknown>)
  return response
})

export const setAuthToken = (token?: string) => {
  if (token) {
    request.defaults.headers.common.Authorization = `Bearer ${token}`
    return
  }
  delete request.defaults.headers.common.Authorization
}

export const get = async <T>(url: string, config?: AxiosRequestConfig) => {
  const response = await request.get<T>(url, config)
  return response.data
}

export const post = async <T>(url: string, data?: unknown, config?: AxiosRequestConfig) => {
  const response = await request.post<T>(url, data, config)
  return response.data
}

export default request
