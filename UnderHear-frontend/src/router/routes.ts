import type { RouteRecordRaw } from 'vue-router'
import { indexRoutes } from '../modules/index/router'
import { applicationRoutes } from '../modules/application/router/application'
import { componentRoutes } from '../modules/components/router/component'
import { namecardRoutes } from '../modules/namecard/router/namecard'
import { authRoutes } from '../modules/auth/router/auth'

export const routes: Array<RouteRecordRaw> = [
    ...indexRoutes,
    ...applicationRoutes,
    ...componentRoutes,
    ...namecardRoutes,
    ...authRoutes
]
