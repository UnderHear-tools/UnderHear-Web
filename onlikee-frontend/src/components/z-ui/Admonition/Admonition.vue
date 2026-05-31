<template>
  <div
    class="admonition"
    :class="[`admonition-${type}`]"
    role="note"
    :aria-label="ariaLabel ?? title ?? LABEL_BY_TYPE[type]"
  >
    <div
      class="admonition-icon"
      aria-hidden="true"
    >
      <svg
        viewBox="0 0 16 16"
        width="16"
        height="16"
        fill="currentColor"
      >
        <path :d="iconPath ?? ICON_PATH[type]" />
      </svg>
    </div>
    <div class="admonition-body">
      <div
        v-if="title || $slots.title"
        class="admonition-title"
      >
        <slot name="title">
          {{ title }}
        </slot>
      </div>
      <div class="admonition-content">
        <slot />
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
export type AdmonitionType = 'note' | 'tip' | 'important' | 'warning' | 'caution'

withDefaults(defineProps<{
  type?: AdmonitionType
  title?: string
  ariaLabel?: string
  /** custom SVG path `d` string to override the default icon */
  iconPath?: string
}>(), {
  type: 'note',
  title: undefined,
  ariaLabel: undefined,
  iconPath: undefined,
})

const LABEL_BY_TYPE: Record<AdmonitionType, string> = {
  note: 'Note',
  tip: 'Tip',
  important: 'Important',
  warning: 'Warning',
  caution: 'Caution',
}

const ICON_PATH: Record<AdmonitionType, string> = {
  note: 'M0 8a8 8 0 1 1 16 0A8 8 0 0 1 0 8Zm8-6.5a6.5 6.5 0 1 0 0 13 6.5 6.5 0 0 0 0-13ZM6.5 7.75A.75.75 0 0 1 7.25 7h1a.75.75 0 0 1 .75.75v2.75h.25a.75.75 0 0 1 0 1.5h-2a.75.75 0 0 1 0-1.5h.25v-2h-.25a.75.75 0 0 1-.75-.75ZM8 6a1 1 0 1 1 0-2 1 1 0 0 1 0 2Z',
  tip: 'M8 1.5c-2.363 0-4.278.546-5.166 1.412a.75.75 0 0 0-.084.108c-.804 1.016-.804 2.758.31 4.506C4.183 9.154 6.36 10.958 8 12.633c1.64-1.675 3.817-3.48 4.94-5.107 1.114-1.748 1.114-3.49.31-4.506a.75.75 0 0 0-.084-.108C12.278 2.046 10.363 1.5 8 1.5ZM0 8a8 8 0 1 0 16 0A8 8 0 0 0 0 8Z',
  important: 'M0 1.75C0 .784.784 0 1.75 0h12.5C15.216 0 16 .784 16 1.75v9.5A1.75 1.75 0 0 1 14.25 13H8.06l-2.573 2.573A1.458 1.458 0 0 1 3 14.543V13H1.75A1.75 1.75 0 0 1 0 11.25Zm1.75-.25a.25.25 0 0 0-.25.25v9.5c0 .138.112.25.25.25h2a.75.75 0 0 1 .75.75v2.19l2.72-2.72a.749.749 0 0 1 .53-.22h6.5a.25.25 0 0 0 .25-.25v-9.5a.25.25 0 0 0-.25-.25Z',
  warning: 'M6.457 1.047c.659-1.234 2.427-1.234 3.086 0l6.082 11.378A1.75 1.75 0 0 1 14.082 15H1.918a1.75 1.75 0 0 1-1.543-2.575Zm1.763.707a.25.25 0 0 0-.44 0L1.698 13.132a.25.25 0 0 0 .22.368h12.164a.25.25 0 0 0 .22-.368Zm.53 3.996v2.5a.75.75 0 0 1-1.5 0v-2.5a.75.75 0 0 1 1.5 0ZM9 11a1 1 0 1 1-2 0 1 1 0 0 1 2 0Z',
  caution: 'M4.47.22A.749.749 0 0 1 5 0h6c.199 0 .389.079.53.22l4.25 4.25c.141.14.22.331.22.53v6a.749.749 0 0 1-.22.53l-4.25 4.25A.749.749 0 0 1 11 16H5a.749.749 0 0 1-.53-.22L.22 11.53A.749.749 0 0 1 0 11V5c0-.199.079-.389.22-.53Zm.84 1.28L1.5 5.31v5.38l3.81 3.81h5.38l3.81-3.81V5.31L10.69 1.5ZM8 4a.75.75 0 0 1 .75.75v3.5a.75.75 0 0 1-1.5 0v-3.5A.75.75 0 0 1 8 4Zm0 8a1 1 0 1 1 0-2 1 1 0 0 1 0 2Z',
}
</script>

<style scoped>
.admonition {
  display: flex;
  gap: 8px;
  padding: 12px 16px;
  border-radius: 6px;
  border-left: 4px solid;
  font-size: 14px;
  line-height: 1.5;
}

/* --- type colors --- */
.admonition-note {
  border-left-color: var(--fgColor-accent, #0969da);
  background: color-mix(in srgb, var(--fgColor-accent, #0969da) 8%, transparent);
  color: var(--fgColor-default, #1f2328);
}
.admonition-tip {
  border-left-color: var(--fgColor-success, #1a7f37);
  background: color-mix(in srgb, var(--fgColor-success, #1a7f37) 8%, transparent);
  color: var(--fgColor-default, #1f2328);
}
.admonition-important {
  border-left-color: var(--fgColor-done, #8250df);
  background: color-mix(in srgb, var(--fgColor-done, #8250df) 8%, transparent);
  color: var(--fgColor-default, #1f2328);
}
.admonition-warning {
  border-left-color: var(--fgColor-attention, #9a6700);
  background: color-mix(in srgb, var(--fgColor-attention, #9a6700) 8%, transparent);
  color: var(--fgColor-default, #1f2328);
}
.admonition-caution {
  border-left-color: var(--fgColor-danger, #d1242f);
  background: color-mix(in srgb, var(--fgColor-danger, #d1242f) 8%, transparent);
  color: var(--fgColor-default, #1f2328);
}

/* --- icon --- */
.admonition-icon {
  flex-shrink: 0;
  display: flex;
  align-items: flex-start;
  padding-top: 1px;
}
.admonition-note .admonition-icon { color: var(--fgColor-accent, #0969da); }
.admonition-tip .admonition-icon { color: var(--fgColor-success, #1a7f37); }
.admonition-important .admonition-icon { color: var(--fgColor-done, #8250df); }
.admonition-warning .admonition-icon { color: var(--fgColor-attention, #9a6700); }
.admonition-caution .admonition-icon { color: var(--fgColor-danger, #d1242f); }

/* --- body --- */
.admonition-body {
  flex: 1;
  min-width: 0;
}

.admonition-title {
  font-weight: 600;
  margin-bottom: 4px;
}

.admonition-content {
  color: var(--fgColor-muted, #656d76);
}

/* when no title, reduce gap */
.admonition-body:not(:has(.admonition-title)) .admonition-content {
  color: var(--fgColor-default, #1f2328);
}
</style>