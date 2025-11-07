import type { RouteRecordRaw } from 'vue-router'
import { homeRoutes } from './routes/home'
import { toolRoutes } from './routes/tool'
import { componentRoutes } from './routes/component'
import { namecardRoutes } from './routes/namecard'

export const routes: Array<RouteRecordRaw> = [
    ...homeRoutes,
    ...toolRoutes,
    ...componentRoutes,
    ...namecardRoutes
]
