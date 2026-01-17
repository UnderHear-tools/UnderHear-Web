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
                path: 'button',
                name: 'Button',
                component: () => import('../views/components/button.vue')
            },
            {
                path: 'border',
                name: 'Border',
                component: () => import('../views/components/border.vue')
            },
            {
                path: 'color',
                name: 'Color',
                component: () => import('../views/components/color.vue')
            },
            {
                path: 'container',
                name: 'Container',
                component: () => import('../views/components/container.vue')
            },
            {
                path: 'icon',
                name: 'Icon',
                component: () => import('../views/components/icon.vue')
            },
            {
                path: 'layout',
                name: 'Layout',
                component: () => import('../views/components/layout.vue')
            },
            {
                path: 'link',
                name: 'Link',
                component: () => import('../views/components/link.vue')
            },
            {
                path: 'text',
                name: 'Text',
                component: () => import('../views/components/text.vue')
            },
            {
                path: 'scrollbar',
                name: 'Scrollbar',
                component: () => import('../views/components/scrollbar.vue')
            },
            {
                path: 'space',
                name: 'Space',
                component: () => import('../views/components/space.vue')
            },
            {
                path: 'splitter',
                name: 'Splitter',
                component: () => import('../views/components/splitter.vue')
            },
            {
                path: 'typography',
                name: 'Typography',
                component: () => import('../views/components/typography.vue')
            },
            // 配置组件
            {
                path: 'config-provider',
                name: 'ConfigProvider',
                component: () => import('../views/components/config-provider.vue')
            },
            // Form 表单组件
            {
                path: 'autocomplete',
                name: 'Autocomplete',
                component: () => import('../views/components/autocomplete.vue')
            },
            {
                path: 'cascader',
                name: 'Cascader',
                component: () => import('../views/components/cascader.vue')
            },
            {
                path: 'checkbox',
                name: 'Checkbox',
                component: () => import('../views/components/checkbox.vue')
            },
            {
                path: 'select',
                name: 'Select',
                component: () => import('../views/components/select.vue')
            }
        ]
    }
]