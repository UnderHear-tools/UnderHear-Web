import type { RouteRecordRaw } from 'vue-router'
import { homeRoutes } from './routes/home'
import { applicationRoutes } from './routes/application'
import { componentRoutes } from './routes/component'
import { namecardRoutes } from './routes/namecard'
import { authRoutes } from './routes/auth'

export const routes: Array<RouteRecordRaw> = [
    ...homeRoutes,
    ...applicationRoutes,
    ...componentRoutes,
    ...namecardRoutes,
    ...authRoutes
]
