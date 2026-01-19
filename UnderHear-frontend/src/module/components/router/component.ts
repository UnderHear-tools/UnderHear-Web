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
            // Icon 图标组件
            {
                path: 'octicons-vue',
                name: 'OcticonsVue',
                component: () => import('../views/components/octicons-vue.vue')
            },
            // Basic 基础组件
            {
                path: 'container',
                name: 'Container',
                component: () => import('../views/components/container.vue')
            },
            {
                path: 'link',
                name: 'Link',
                component: () => import('../views/components/link.vue')
            },
            {
                path: 'divider',
                name: 'Divider',
                component: () => import('../views/components/divider.vue')
            },
            {
                path: 'tag',
                name: 'Tag',
                component: () => import('../views/components/tag.vue')
            },
            // Form 表单组件
            {
                path: 'input',
                name: 'Input',
                component: () => import('../views/components/input.vue')
            },
            {
                path: 'select',
                name: 'Select',
                component: () => import('../views/components/select.vue')
            }
        ]
    }
]