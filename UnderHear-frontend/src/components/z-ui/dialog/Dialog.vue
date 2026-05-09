<script lang="ts">
let activeDialogScrollLocks = 0

function lockDocumentScroll() {
  activeDialogScrollLocks += 1
  document.documentElement.dataset.dialogScrollLocked = 'true'
}

function unlockDocumentScroll() {
  if (activeDialogScrollLocks === 0) return

  activeDialogScrollLocks -= 1
  if (activeDialogScrollLocks === 0) {
    delete document.documentElement.dataset.dialogScrollLocked
  }
}
</script>

<script setup lang="ts">
import { computed, nextTick, onBeforeUnmount, ref, watch } from 'vue'
import { X } from '@/components/z-ui/icon/Octicons-vue'

export type DialogCloseGesture = 'close-button' | 'escape' | 'backdrop'
export type DialogSize = 'small' | 'medium' | 'large' | 'xlarge'
export type DialogHeight = 'auto' | 'small' | 'large'

const props = withDefaults(
  defineProps<{
    open?: boolean
    title: string
    subtitle?: string
    size?: DialogSize
    height?: DialogHeight
    role?: 'dialog' | 'alertdialog'
    closeOnEscape?: boolean
    closeOnBackdrop?: boolean
  }>(),
  {
    open: false,
    subtitle: '',
    size: 'medium',
    height: 'auto',
    role: 'dialog',
    closeOnEscape: true,
    closeOnBackdrop: true
  }
)

const emit = defineEmits<{
  'update:open': [open: boolean]
  close: [gesture: DialogCloseGesture]
}>()

const dialogRef = ref<HTMLElement | null>(null)
const closeButtonRef = ref<HTMLButtonElement | null>(null)
const dialogId = `dialog-${Math.random().toString(36).slice(2, 10)}`
const titleId = `${dialogId}-title`
const subtitleId = computed(() => (props.subtitle ? `${dialogId}-subtitle` : undefined))

let previousFocus: HTMLElement | null = null
let hasScrollLock = false

const FOCUSABLE_SELECTOR = [
  'a[href]',
  'button:not([disabled])',
  'input:not([disabled])',
  'select:not([disabled])',
  'textarea:not([disabled])',
  '[tabindex]:not([tabindex="-1"])',
  '[contenteditable="true"]'
].join(',')

function getFocusableElements() {
  const dialog = dialogRef.value
  if (!dialog) return []

  return Array.from(dialog.querySelectorAll<HTMLElement>(FOCUSABLE_SELECTOR))
    .filter(element => element.getAttribute('aria-hidden') !== 'true' && element.getClientRects().length > 0)
}

function focusInitialElement() {
  const dialog = dialogRef.value
  if (!dialog) return

  const autofocusElement = dialog.querySelector<HTMLElement>('[autofocus]')
  if (autofocusElement && !autofocusElement.hasAttribute('disabled')) {
    autofocusElement.focus()
    return
  }

  const initialElement = closeButtonRef.value ?? getFocusableElements()[0] ?? dialog
  initialElement.focus()
}

function lockPageScroll() {
  if (hasScrollLock) return

  lockDocumentScroll()
  hasScrollLock = true
}

function unlockPageScroll() {
  if (!hasScrollLock) return

  unlockDocumentScroll()
  hasScrollLock = false
}

async function handleOpenChange(open: boolean) {
  if (open) {
    lockPageScroll()
    previousFocus = document.activeElement instanceof HTMLElement ? document.activeElement : null
    await nextTick()
    focusInitialElement()
    return
  }

  unlockPageScroll()

  if (previousFocus?.isConnected) {
    previousFocus.focus()
  }
  previousFocus = null
}

function requestClose(gesture: DialogCloseGesture) {
  emit('close', gesture)
  emit('update:open', false)
}

function handleBackdropClick() {
  if (props.closeOnBackdrop) {
    requestClose('backdrop')
  }
}

function handleKeydown(event: KeyboardEvent) {
  if (event.key === 'Escape' && props.closeOnEscape) {
    event.stopPropagation()
    requestClose('escape')
    return
  }

  if (event.key !== 'Tab') return

  const focusableElements = getFocusableElements()
  if (!focusableElements.length) {
    event.preventDefault()
    dialogRef.value?.focus()
    return
  }

  const firstElement = focusableElements[0]
  const lastElement = focusableElements[focusableElements.length - 1]
  const activeElement = document.activeElement

  if (event.shiftKey && activeElement === firstElement) {
    event.preventDefault()
    lastElement.focus()
    return
  }

  if (!event.shiftKey && activeElement === lastElement) {
    event.preventDefault()
    firstElement.focus()
  }
}

watch(
  () => props.open,
  open => {
    void handleOpenChange(open)
  },
  { immediate: true }
)

onBeforeUnmount(unlockPageScroll)
</script>

<template>
  <Teleport to="body">
    <Transition name="dialog-fade">
      <div
        v-if="open"
        class="dialog-layer"
        @click.self="handleBackdropClick"
      >
        <section
          ref="dialogRef"
          class="dialog"
          :data-size="size"
          :data-height="height"
          :role="role"
          aria-modal="true"
          :aria-labelledby="titleId"
          :aria-describedby="subtitleId"
          tabindex="-1"
          @keydown="handleKeydown"
        >
          <header class="dialog__header">
            <div class="dialog__header-content">
              <div
                :id="titleId"
                class="dialog__title"
              >
                {{ title }}
              </div>
              <p
                v-if="subtitle"
                :id="subtitleId"
                class="dialog__subtitle"
              >
                {{ subtitle }}
              </p>
            </div>
            <button
              ref="closeButtonRef"
              class="dialog__close"
              type="button"
              aria-label="关闭对话框"
              @click="requestClose('close-button')"
            >
              <X />
            </button>
          </header>

          <slot />
        </section>
      </div>
    </Transition>
  </Teleport>
</template>

<style scoped>
:global(html[data-dialog-scroll-locked='true']) {
  overflow: hidden;
  overscroll-behavior: contain;
}

.dialog-layer {
  align-items: center;
  background: var(--overlay-backdrop-bgColor, rgba(140, 149, 159, 0.32));
  display: flex;
  inset: 0;
  justify-content: center;
  padding: 1rem;
  position: fixed;
  z-index: 10000;
}

.dialog {
  --dialog-width: 20rem;
  background: var(--overlay-bgColor, var(--bgColor-default, #ffffff));
  border-radius: var(--borderRadius-large, 0.75rem);
  box-shadow: var(--shadow-floating-small, 0 0 0 1px #d1d9e080,0 6px 12px -3px #25292e0a,0 6px 18px 0 #25292e1f);
  color: var(--fgColor-default, #1f2328);
  display: flex;
  flex-direction: column;
  max-height: calc(100dvh - 2rem);
  min-height: 0;
  outline: none;
  overflow: hidden;
  transform-origin: center;
  width: min(var(--dialog-width), calc(100vw - 2rem));
}

.dialog[data-size='small'] {
  --dialog-width: 18.5rem;
}

.dialog[data-size='large'] {
  --dialog-width: 30rem;
}

.dialog[data-size='xlarge'] {
  --dialog-width: 40rem;
}

.dialog[data-height='small'] {
  height: min(30rem, calc(100dvh - 2rem));
}

.dialog[data-height='large'] {
  height: min(40rem, calc(100dvh - 2rem));
}

.dialog__header {
  align-items: start;
  border-bottom: 1px solid var(--borderColor-default, #d1d9e0);
  display: grid;
  gap: 0.75rem;
  grid-template-columns: minmax(0, 1fr) auto;
  padding: 0.5rem;
}

.dialog__header-content {
  display: grid;
  gap: 0.25rem;
  padding: 6px 8px;
  min-width: 0;
}

.dialog__title {
  color: var(--fgColor-default, #1f2328);
  font-size: 14px;
  font-weight: 600;
  line-height: 1.5;
  overflow-wrap: anywhere;
}

.dialog__subtitle {
  color: var(--fgColor-muted, #59636e);
  font-size: 12px;
  line-height: 1.5;
  margin: 0;
  overflow-wrap: anywhere;
}

.dialog__close {
  align-items: center;
  appearance: none;
  background: var(--button-invisible-bgColor-rest, transparent);
  border: 1px solid var(--button-invisible-borderColor-rest, transparent);
  border-radius: var(--borderRadius-medium, 0.375rem);
  color: var(--fgColor-muted, #59636e);
  cursor: pointer;
  display: inline-flex;
  height: var(--control-medium-size, 2rem);
  justify-content: center;
  padding: 0;
  width: var(--control-medium-size, 2rem);
}

.dialog__close:hover {
  background: var(--button-invisible-bgColor-hover, var(--control-transparent-bgColor-hover, #818b981a));
  color: var(--fgColor-default, #1f2328);
}

.dialog__close:active {
  background: var(--button-invisible-bgColor-active, var(--control-transparent-bgColor-active, #818b9826));
}

.dialog__close:focus-visible {
  outline: 2px solid var(--focus-outlineColor, #0969da);
  outline-offset: -2px;
}

.dialog-fade-enter-active {
  transition: opacity 160ms cubic-bezier(0.65, 0, 0.35, 1);
}

.dialog-fade-enter-active .dialog {
  transition: transform 160ms cubic-bezier(0.65, 0, 0.35, 1);
}

.dialog-fade-enter-from {
  opacity: 0;
}

.dialog-fade-enter-from .dialog {
  transform: scale(0.96);
}

@media (pointer: fine) {
  :global(html[data-dialog-scroll-locked='true'] body) {
    padding-right: 15px;
  }
}
</style>
