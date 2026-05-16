import type { RouteRecordRaw } from 'vue-router'

export const namecardRoutes: Array<RouteRecordRaw> = [
    {
        path: '/namecard',
        name: 'Namecard',
        component: () => import('../views/namecard.vue')
    },
    {
        path: '/namecard/onlikee',
        name: 'onlikee',
        component: () => import('../views/namecard/onlikee/index.vue')
    },
    {
        path: '/namecard/onlikee/project',
        name: 'Project',
        component: () => import('../views/namecard/onlikee/project.vue')
    }
]