import type { RouteRecordRaw } from 'vue-router'

export const applicationRoutes: Array<RouteRecordRaw> = [
    {
        path: '/application/create',
        name: 'ApplicationCreateMethod',
        component: () => import('../views/application/create.vue')
    },
    {
        path: '/application/create/new',
        name: 'ApplicationCreateNew',
        component: () => import('../views/application/create/new.vue')
    },
    {
        path: '/application/create/website',
        name: 'ApplicationCreateWebsite',
        component: () => import('../views/application/create/website.vue')
    },
    {
        path: '/application/create/collect',
        name: 'ApplicationCreateCollect',
        component: () => import('../views/application/create/collect.vue')
    },
    {
        path: '/application',
        name: 'Application',
        component: () => import('../views/application.vue')
    },
    {
        path: '/application/pathplanner',
        name: 'PathPlanner',
        component: () => import('../views/application/pathplanner/pathplanner.vue')
    }
]
