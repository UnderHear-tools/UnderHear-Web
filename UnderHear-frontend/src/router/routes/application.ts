import type { RouteRecordRaw } from 'vue-router'

export const applicationRoutes: Array<RouteRecordRaw> = [
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