import { createVNode, render } from 'vue'
import ZBannerView, {
  type zBannerExposed,
  type zBannerOptions,
  type zBannerResult,
  type zBannerType
} from './zBanner.vue'

type zBannerFn = ((message: string, type?: zBannerType, options?: zBannerOptions) => zBannerResult) & {
  info: (message: string, options?: zBannerOptions) => zBannerResult
  success: (message: string, options?: zBannerOptions) => zBannerResult
  warning: (message: string, options?: zBannerOptions) => zBannerResult
  critical: (message: string, options?: zBannerOptions) => zBannerResult
  upsell: (message: string, options?: zBannerOptions) => zBannerResult
  error: (message: string, options?: zBannerOptions) => zBannerResult
  close: () => void
}

const HOST_ID = 'z-banner-host'
let service: zBannerExposed | null = null

function getService() {
  if (service) return service

  let host = document.getElementById(HOST_ID)
  if (!host) {
    host = document.createElement('div')
    host.id = HOST_ID
    document.body.appendChild(host)
  }

  const vnode = createVNode(ZBannerView)
  render(vnode, host)
  service = vnode.component!.exposed as zBannerExposed
  return service
}

const zBanner = ((message: string, type: zBannerType = 'info', options: zBannerOptions = {}) => {
  return getService().show(message, type, options)
}) as zBannerFn

zBanner.info = (message, options) => zBanner(message, 'info', options)
zBanner.success = (message, options) => zBanner(message, 'success', options)
zBanner.warning = (message, options) => zBanner(message, 'warning', options)
zBanner.critical = (message, options) => zBanner(message, 'critical', options)
zBanner.upsell = (message, options) => zBanner(message, 'upsell', options)
zBanner.error = (message, options) => zBanner(message, 'critical', options)
zBanner.close = () => getService().close()

export type { zBannerOptions, zBannerResult, zBannerType } from './zBanner.vue'
export default zBanner
