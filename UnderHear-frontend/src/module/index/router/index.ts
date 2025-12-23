import type { RouteRecordRaw } from 'vue-router'

export const indexRoutes: Array<RouteRecordRaw> = [
    {
        path: '/',
        name: 'Index',
        component: () => import('../views/index.vue')
    }
]