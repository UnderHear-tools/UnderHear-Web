import type { RouteRecordRaw } from 'vue-router'

export const toolRoutes: Array<RouteRecordRaw> = [
    {
        path: '/tool',
        name: 'Tool',
        component: () => import('@/views/tool.vue')
    },
    {
        path: '/tool/pathplanner',
        name: 'PathPlanner',
        component: () => import('@/views/tool/pathplanner/pathplanner.vue')
    }
]