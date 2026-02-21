<script setup lang="ts">
import { ref } from 'vue'
import type { Component } from 'vue'
import { Alert, CheckCircle, Info, Stop, X } from '@/components/z-ui/icon/Octicons-vue'

export type zBannerType = 'critical' | 'info' | 'success' | 'upsell' | 'warning'
export type zBannerActionsLayout = 'default' | 'inline' | 'stacked'
export type zBannerResult = { close: () => void }

export interface zBannerAction {
  label: string
  href?: string
  onClick?: () => void
}

export interface zBannerOptions {
  title?: string
  description?: string
  ariaLabel?: string
  hideTitle?: boolean
  dismissible?: boolean
  primaryAction?: zBannerAction
  secondaryAction?: zBannerAction
  actionsLayout?: zBannerActionsLayout
  flush?: boolean
}

export interface zBannerExposed {
  show: (message: string, type?: zBannerType, options?: zBannerOptions) => zBannerResult
  close: () => void
}

interface BannerState {
  type: zBannerType
  title: string
  description: string
  ariaLabel?: string
  hideTitle: boolean
  dismissible: boolean
  primaryAction?: zBannerAction
  secondaryAction?: zBannerAction
  actionsLayout: zBannerActionsLayout
  flush: boolean
}

const LABEL_BY_TYPE: Record<zBannerType, string> = {
  critical: 'Critical',
  info: 'Information',
  success: 'Success',
  upsell: 'Recommendation',
  warning: 'Warning'
}

const ICON_BY_TYPE: Record<zBannerType, Component> = {
  critical: Stop,
  info: Info,
  success: CheckCircle,
  upsell: Info,
  warning: Alert
}

const banner = ref<BannerState | null>(null)

function close() {
  banner.value = null
}

function runAction(action: zBannerAction) {
  action.onClick?.()
}

function show(message: string, type: zBannerType = 'info', options: zBannerOptions = {}): zBannerResult {
  banner.value = {
    type,
    title: options.title ?? LABEL_BY_TYPE[type],
    description: options.description ?? message,
    ariaLabel: options.ariaLabel,
    hideTitle: options.hideTitle ?? false,
    dismissible: options.dismissible ?? true,
    primaryAction: options.primaryAction,
    secondaryAction: options.secondaryAction,
    actionsLayout: options.actionsLayout ?? 'default',
    flush: options.flush ?? false
  }

  return { close }
}

defineExpose<zBannerExposed>({
  show,
  close
})
</script>

<template>
  <div v-if="banner" class="z-banner-layer">
    <section
      class="z-banner"
      :class="[
        `z-banner--${banner.type}`,
        {
          'z-banner--flush': banner.flush,
          'z-banner--title-hidden': banner.hideTitle
        }
      ]"
      :data-actions-layout="banner.actionsLayout"
      role="region"
      :aria-label="banner.ariaLabel ?? LABEL_BY_TYPE[banner.type]"
      tabindex="-1"
    >
      <div class="z-banner__icon" aria-hidden="true">
        <component :is="ICON_BY_TYPE[banner.type]" :size="16" />
      </div>

      <div class="z-banner__container">
        <div class="z-banner__content">
          <h2 v-if="!banner.hideTitle" class="z-banner__title">{{ banner.title }}</h2>
          <span v-else class="z-banner__sr-only">{{ banner.title }}</span>
          <p class="z-banner__description">{{ banner.description }}</p>
        </div>

        <div v-if="banner.primaryAction || banner.secondaryAction" class="z-banner__actions">
          <div class="z-banner__actions-row z-banner__actions-row--trailing">
            <template v-if="banner.secondaryAction">
              <a
                v-if="banner.secondaryAction.href"
                :href="banner.secondaryAction.href"
                class="z-banner__action z-banner__action--secondary"
              >
                {{ banner.secondaryAction.label }}
              </a>
              <button
                v-else
                type="button"
                class="z-banner__action z-banner__action--secondary"
                @click="runAction(banner.secondaryAction)"
              >
                {{ banner.secondaryAction.label }}
              </button>
            </template>

            <template v-if="banner.primaryAction">
              <a
                v-if="banner.primaryAction.href"
                :href="banner.primaryAction.href"
                class="z-banner__action z-banner__action--primary"
              >
                {{ banner.primaryAction.label }}
              </a>
              <button
                v-else
                type="button"
                class="z-banner__action z-banner__action--primary"
                @click="runAction(banner.primaryAction)"
              >
                {{ banner.primaryAction.label }}
              </button>
            </template>
          </div>

          <div class="z-banner__actions-row z-banner__actions-row--leading">
            <template v-if="banner.primaryAction">
              <a
                v-if="banner.primaryAction.href"
                :href="banner.primaryAction.href"
                class="z-banner__action z-banner__action--primary"
              >
                {{ banner.primaryAction.label }}
              </a>
              <button
                v-else
                type="button"
                class="z-banner__action z-banner__action--primary"
                @click="runAction(banner.primaryAction)"
              >
                {{ banner.primaryAction.label }}
              </button>
            </template>

            <template v-if="banner.secondaryAction">
              <a
                v-if="banner.secondaryAction.href"
                :href="banner.secondaryAction.href"
                class="z-banner__action z-banner__action--secondary"
              >
                {{ banner.secondaryAction.label }}
              </a>
              <button
                v-else
                type="button"
                class="z-banner__action z-banner__action--secondary"
                @click="runAction(banner.secondaryAction)"
              >
                {{ banner.secondaryAction.label }}
              </button>
            </template>
          </div>
        </div>
      </div>

      <button
        v-if="banner.dismissible"
        type="button"
        class="z-banner__dismiss"
        aria-label="Dismiss banner"
        @click="close"
      >
        <X :size="16" />
      </button>
    </section>
  </div>
</template>

<style scoped>
.z-banner-layer {
  position: fixed;
  top: 16px;
  left: 50%;
  transform: translateX(-50%);
  width: min(92vw, 760px);
  z-index: 9999;
  pointer-events: none;
}

.z-banner {
  --banner-bg: var(--bgColor-accent-muted);
  --banner-border: var(--borderColor-accent-muted);
  --banner-icon: var(--fgColor-link);
  pointer-events: auto;
  display: grid;
  grid-template-columns: auto minmax(0, 1fr) auto;
  align-items: start;
  padding: 8px;
  border: 1px solid var(--banner-border);
  border-radius: 6px;
  background: var(--banner-bg);
  color: var(--fgColor-default);
}

.z-banner--flush {
  border-left: 0;
  border-right: 0;
  border-radius: 0;
}

.z-banner--critical {
  --banner-bg: var(--bgColor-danger-muted);
  --banner-border: var(--borderColor-danger-muted);
  --banner-icon: var(--fgColor-danger);
}

.z-banner--info {
  --banner-bg: var(--bgColor-accent-muted);
  --banner-border: var(--borderColor-accent-muted);
  --banner-icon: var(--fgColor-link);
}

.z-banner--success {
  --banner-bg: var(--bgColor-success-muted);
  --banner-border: var(--borderColor-success-muted);
  --banner-icon: var(--fgColor-success);
}

.z-banner--upsell {
  --banner-bg: var(--bgColor-done-muted);
  --banner-border: var(--borderColor-done-muted);
  --banner-icon: var(--fgColor-done);
}

.z-banner--warning {
  --banner-bg: var(--bgColor-attention-muted);
  --banner-border: var(--borderColor-attention-muted);
  --banner-icon: var(--fgColor-attention);
}

.z-banner__icon {
  display: grid;
  place-items: center;
  padding: 10px 8px;
  color: var(--banner-icon);
}

.z-banner__container {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
  align-items: start;
  row-gap: 8px;
  column-gap: 12px;
  min-width: 0;
  font-size: 14px;
  line-height: 1.43;
}

.z-banner__content {
  flex: 1 1 0%;
  min-width: 0;
  display: grid;
  row-gap: 4px;
  margin-block: 8px;
}

.z-banner--title-hidden .z-banner__content {
  margin-block: 6px;
}

.z-banner__title {
  margin: 0;
  font-size: inherit;
  font-weight: 500;
}

.z-banner__description {
  margin: 0;
  word-break: break-word;
}

.z-banner__actions {
  display: flex;
}

.z-banner__actions-row {
  display: flex;
  align-items: center;
  column-gap: 12px;
}

.z-banner__actions-row--trailing {
  display: none;
}

.z-banner__action {
  font: inherit;
  border: 0;
  background: var(--bgColor-transparent);
  color: var(--fgColor-link);
  text-decoration: none;
  cursor: pointer;
  padding: 0;
}

.z-banner__action--secondary:hover {
  text-decoration: underline;
}

.z-banner__action--primary {
  color: var(--fgColor-default);
  font-weight: 500;
  border: 1px solid var(--borderColor-default);
  background: var(--bgColor-muted);
  border-radius: 6px;
  line-height: 20px;
  padding: 5px 12px;
}

.z-banner__dismiss {
  display: grid;
  place-items: center;
  width: 32px;
  height: 32px;
  margin-left: 4px;
  border: 0;
  border-radius: 6px;
  background: var(--bgColor-transparent);
  color: var(--banner-icon);
  cursor: pointer;
}

.z-banner__dismiss:hover {
  background: var(--control-transparent-bgColor-hover);
}

.z-banner__dismiss:active {
  background: var(--control-transparent-bgColor-active);
}

.z-banner__dismiss:focus-visible,
.z-banner__action:focus-visible {
  outline: 2px solid var(--focus-outlineColor);
  outline-offset: 2px;
}

.z-banner__sr-only {
  position: absolute;
  width: 1px;
  height: 1px;
  padding: 0;
  margin: -1px;
  overflow: hidden;
  clip: rect(0, 0, 0, 0);
  white-space: nowrap;
  border: 0;
}

.z-banner[data-actions-layout='stacked'] .z-banner__container {
  flex-direction: column;
}

.z-banner[data-actions-layout='stacked'] .z-banner__actions-row--trailing {
  display: none;
}

.z-banner[data-actions-layout='stacked'] .z-banner__actions-row--leading {
  display: flex;
}

.z-banner[data-actions-layout='inline'] .z-banner__container {
  flex-wrap: nowrap;
}

.z-banner[data-actions-layout='inline'] .z-banner__actions-row--trailing {
  display: flex;
}

.z-banner[data-actions-layout='inline'] .z-banner__actions-row--leading {
  display: none;
}

.z-banner[data-actions-layout='default'] .z-banner__actions-row--leading {
  display: flex;
}

@media (min-width: 500px) {
  .z-banner[data-actions-layout='default'] .z-banner__container {
    display: grid;
    grid-template-columns: minmax(0, 1fr) auto;
  }

  .z-banner[data-actions-layout='default'] .z-banner__actions-row--trailing {
    display: flex;
    min-height: 32px;
  }

  .z-banner[data-actions-layout='default'] .z-banner__actions-row--leading {
    display: none;
  }
}

@media (max-width: 640px) {
  .z-banner-layer {
    width: 100vw;
    left: 0;
    transform: none;
  }
}
</style>
