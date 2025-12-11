import type { RouteRecordRaw } from 'vue-router'

export const applicationRoutes: Array<RouteRecordRaw> = [
    {
        path: '/application/create',
        name: 'ApplicationCreate',
        component: () => import('@/views/application/create.vue')
    },
    {
        path: '/application',
        name: 'Application',
        component: () => import('@/views/application.vue')
    },
    {
        path: '/application/pathplanner',
        name: 'PathPlanner',
        component: () => import('@/views/application/pathplanner/pathplanner.vue')
    }
]