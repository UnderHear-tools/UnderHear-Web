import type { RouteRecordRaw } from 'vue-router'

export const homeRoutes: Array<RouteRecordRaw> = [
    {
        path: '/',
        name: 'Index',
        component: () => import('@/views/index.vue')
    }
]