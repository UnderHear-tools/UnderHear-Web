export type ApiResponse<T = unknown> = {
  code: string
  message: string
  data: T
}
