import type { RouteRecordRaw } from 'vue-router'
import { indexRoutes } from '../module/index/router'
import { applicationRoutes } from '../module/application/router/application'
import { componentRoutes } from '../module/components/router/component'
import { namecardRoutes } from '../module/namecard/router/namecard'
import { authRoutes } from '../module/auth/router/auth'

export const routes: Array<RouteRecordRaw> = [
    ...indexRoutes,
    ...applicationRoutes,
    ...componentRoutes,
    ...namecardRoutes,
    ...authRoutes
]
