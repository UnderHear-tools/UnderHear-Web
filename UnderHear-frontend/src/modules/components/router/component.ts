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
            // Icon
            {
                path: 'octicons-vue',
                name: 'OcticonsVue',
                component: () => import('../views/components/octicons-vue.vue')
            },
            // Blocks
            {
                path: 'user-menu',
                name: 'UserMenu',
                component: () => import('../views/components/user-menu.vue')
            },
            // Basic
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
                path: 'avatar',
                name: 'Avatar',
                component: () => import('../views/components/avatar.vue')
            },
            {
                path: 'dropdown',
                name: 'Dropdown',
                component: () => import('../views/components/dropdown.vue')
            },
            {
                path: 'menu',
                name: 'Menu',
                component: () => import('../views/components/menu.vue')
            },
            {
                path: 'tag',
                name: 'Tag',
                component: () => import('../views/components/tag.vue')
            },
            // Form
            {
                path: 'input',
                name: 'Input',
                component: () => import('../views/components/input.vue')
            },
            {
                path: 'select',
                name: 'Select',
                component: () => import('../views/components/select.vue')
            },
            {
                path: 'upload',
                name: 'Upload',
                component: () => import('../views/components/upload.vue')
            },
            // Data
            {
                path: 'table',
                name: 'Table',
                component: () => import('../views/components/table.vue')
            },
            // Navigation
            {
                path: 'steps',
                name: 'Steps',
                component: () => import('../views/components/steps.vue')
            },
            // Feedback 反馈组件
            {
                path: 'banner',
                name: 'Banner',
                component: () => import('../views/components/banner.vue')
            }
        ]
    }
]
