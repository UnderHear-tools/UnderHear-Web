import type { ApiResponse } from './types'

export const unwrapApiResponse = <T>(payload: ApiResponse<T>): T => {
  if (payload.code !== 'OK') {
    throw new Error(payload.message)
  }
  return payload.data
}
