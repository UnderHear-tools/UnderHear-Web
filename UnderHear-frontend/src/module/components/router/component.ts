import type { RouteRecordRaw } from 'vue-router'

export const componentRoutes: Array<RouteRecordRaw> = [
    {
        path: '/component',
        name: 'Component',
        component: () => import('../views/components.vue')
    },
    {
        path: '/component',
        component: () => import('../layout/ComponentLayout.vue'),
        children: [
            {
                path: 'guide',
                name: 'Guide',
                component: () => import('../views/components/guide.vue')
            },
            {
                path: 'overview',
                name: 'Overview',
                component: () => import('../views/components/overview.vue')
            },
            // Basic 基础组件
            {
                path: 'container',
                name: 'Container',
                component: () => import('../views/components/container.vue')
            }
        ]
    }
]