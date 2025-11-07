import type { RouteRecordRaw } from 'vue-router'

export const namecardRoutes: Array<RouteRecordRaw> = [
    {
        path: '/namecard',
        name: 'Namecard',
        component: () => import('@/views/namecard.vue')
    },
    {
        path: '/namecard/UnderHear',
        name: 'UnderHear',
        component: () => import('@/views/namecard/UnderHear/index.vue')
    },
    {
        path: '/namecard/UnderHear/project',
        name: 'Project',
        component: () => import('@/views/namecard/UnderHear/project.vue')
    }
]