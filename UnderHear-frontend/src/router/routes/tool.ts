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
    },
    {
        path: '/tool/img-to-svg',
        name: 'ImgToSvg',
        component: () => import('@/views/tool/img-to-svg/img-to-svg.vue')
    }
]