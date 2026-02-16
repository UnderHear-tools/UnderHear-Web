import type { RouteRecordRaw } from 'vue-router'

export const authRoutes: Array<RouteRecordRaw> = [
  {
    path: '/auth/login',
    name: 'Login',
    component: () => import('../views/auth/login.vue')
  },
  {
    path: '/auth/logout',
    name: 'Logout',
    component: () => import('../views/auth/logout.vue')
  }
]

