import { createVNode, render } from 'vue'
import ZBannerView, {
  type ZBannerExposed,
  type ZBannerOptions,
  type ZBannerResult,
  type ZBannerType
} from './zBanner.vue'

type ZBanner = ((message: string, type?: ZBannerType, options?: ZBannerOptions) => ZBannerResult) & {
  info: (message: string, options?: ZBannerOptions) => ZBannerResult
  success: (message: string, options?: ZBannerOptions) => ZBannerResult
  warning: (message: string, options?: ZBannerOptions) => ZBannerResult
  critical: (message: string, options?: ZBannerOptions) => ZBannerResult
  upsell: (message: string, options?: ZBannerOptions) => ZBannerResult
  error: (message: string, options?: ZBannerOptions) => ZBannerResult
  close: () => void
}

const HOST_ID = 'z-banner-host'
let service: ZBannerExposed | null = null

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
  service = vnode.component!.exposed as ZBannerExposed
  return service
}

const zBanner = ((message: string, type: ZBannerType = 'info', options: ZBannerOptions = {}) => {
  return getService().show(message, type, options)
}) as ZBanner

zBanner.info = (message, options) => zBanner(message, 'info', options)
zBanner.success = (message, options) => zBanner(message, 'success', options)
zBanner.warning = (message, options) => zBanner(message, 'warning', options)
zBanner.critical = (message, options) => zBanner(message, 'critical', options)
zBanner.upsell = (message, options) => zBanner(message, 'upsell', options)
zBanner.error = (message, options) => zBanner(message, 'critical', options)
zBanner.close = () => getService().close()

export type { ZBannerOptions, ZBannerResult, ZBannerType } from './zBanner.vue'
export default zBanner
