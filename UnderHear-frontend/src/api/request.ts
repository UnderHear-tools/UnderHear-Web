import axios, { type AxiosRequestConfig } from 'axios'

import { API_BASE_URL } from './config'

const request = axios.create({
  baseURL: API_BASE_URL,
  timeout: 15000,
})

request.interceptors.response.use(
  (response) => {
    const responseData = response.data as { code?: string; message?: string; data?: unknown }
    if (responseData && responseData.code && responseData.code !== 'OK') {
      const message = responseData.message ?? '请求失败'
      return Promise.reject(new Error(message))
    }
    return responseData?.data ?? response.data
  },
  (error) => {
    if (axios.isAxiosError(error)) {
      const responseData = error.response?.data as { message?: string } | undefined
      const message = responseData?.message ?? error.message
      return Promise.reject(new Error(message))
    }
    return Promise.reject(error)
  }
)

export const setAuthToken = (token?: string) => {
  if (token) {
    request.defaults.headers.common.Authorization = `Bearer ${token}`
    return
  }
  delete request.defaults.headers.common.Authorization
}

export const get = <T>(url: string, config?: AxiosRequestConfig) =>
  request.get<T, T>(url, config)

export const post = <T>(url: string, data?: unknown, config?: AxiosRequestConfig) =>
  request.post<T, T>(url, data, config)

export default request
