<template>
  <div
    ref="dropdownRef"
    class="z-dropdown"
  >
    <div
      class="z-dropdown-trigger"
      :data-open="isOpen || undefined"
      @click="isOpen = !isOpen"
    >
      <slot name="trigger" />
    </div>
    <Transition name="z-dropdown-fade">
      <div
        v-if="isOpen"
        ref="contentRef"
        class="z-dropdown-content"
        :class="sideClass"
        :style="contentStyle"
        @click="onContentClick"
      >
        <slot name="content" />
      </div>
    </Transition>
  </div>
</template>

<script setup lang="ts">
import { computed, ref, onMounted, onBeforeUnmount, watch, nextTick } from 'vue'

type InsideSide =
  | 'inside-top'
  | 'inside-bottom'
  | 'inside-left'
  | 'inside-right'
  | 'inside-center'

type OutsideSide = 'outside-top' | 'outside-bottom' | 'outside-left' | 'outside-right'

type DropdownSide =
  | InsideSide
  | 'outside-top'
  | 'outside-bottom'
  | 'outside-left'
  | 'outside-right'

type OutsideAlign = 'start' | 'end'
type OutsidePlacement = {
  side: OutsideSide
  align: OutsideAlign
}

const INSIDE_TO_OUTSIDE: Record<InsideSide, OutsideSide> = {
  'inside-top': 'outside-top',
  'inside-bottom': 'outside-bottom',
  'inside-left': 'outside-left',
  'inside-right': 'outside-right',
  'inside-center': 'outside-bottom'
}
const OPPOSITE_SIDE: Record<OutsideSide, OutsideSide> = {
  'outside-top': 'outside-bottom',
  'outside-bottom': 'outside-top',
  'outside-left': 'outside-right',
  'outside-right': 'outside-left'
}
const CROSS_AXIS_SIDES: Record<OutsideSide, OutsideSide[]> = {
  'outside-top': ['outside-right', 'outside-left'],
  'outside-bottom': ['outside-right', 'outside-left'],
  'outside-left': ['outside-bottom', 'outside-top'],
  'outside-right': ['outside-bottom', 'outside-top']
}
const OFFSET = 4

const props = defineProps<{
  side?: DropdownSide
}>()

const isOpen = ref(false)
const dropdownRef = ref<HTMLElement>()
const contentRef = ref<HTMLElement>()
const autoPlacement = ref<OutsidePlacement>({ side: 'outside-bottom', align: 'start' })
const isInsideSide = computed(() => props.side?.startsWith('inside-') ?? false)

function isOutsideSide(side: DropdownSide): side is OutsideSide {
  return side.startsWith('outside-')
}

const preferredOutsideSide = computed<OutsideSide>(() => {
  if (!props.side) return 'outside-bottom'
  if (isOutsideSide(props.side)) return props.side
  return INSIDE_TO_OUTSIDE[props.side]
})

const effectiveSide = computed<DropdownSide>(() => {
  if (isInsideSide.value && props.side) {
    return props.side
  }

  return autoPlacement.value.side
})
const sideClass = computed(() => `z-dropdown-content--${effectiveSide.value}`)
const contentStyle = computed(() => {
  if (isInsideSide.value) return undefined

  const { side, align } = autoPlacement.value
  if (side === 'outside-bottom' || side === 'outside-top') {
    return align === 'end' ? { left: 'auto', right: '0' } : { left: '0', right: 'auto' }
  }

  return align === 'end' ? { top: 'auto', bottom: '0' } : { top: '0', bottom: 'auto' }
})

function getOutsidePosition(
  placement: OutsidePlacement,
  anchorLeft: number,
  anchorTop: number,
  anchorRight: number,
  anchorBottom: number,
  contentWidth: number,
  contentHeight: number
) {
  const { side, align } = placement

  if (side === 'outside-top') {
    return {
      x: align === 'end' ? anchorRight - contentWidth : anchorLeft,
      y: anchorTop - contentHeight - OFFSET
    }
  }

  if (side === 'outside-left') {
    return {
      x: anchorLeft - contentWidth - OFFSET,
      y: align === 'end' ? anchorBottom - contentHeight : anchorTop
    }
  }

  if (side === 'outside-right') {
    return {
      x: anchorRight + OFFSET,
      y: align === 'end' ? anchorBottom - contentHeight : anchorTop
    }
  }

  return {
    x: align === 'end' ? anchorRight - contentWidth : anchorLeft,
    y: anchorBottom + OFFSET
  }
}

function getOverflowScore(x: number, y: number, width: number, height: number, viewportWidth: number, documentHeight: number) {
  const overflowLeft = Math.max(0, -x)
  const overflowTop = Math.max(0, -y)
  const overflowRight = Math.max(0, x + width - viewportWidth)
  const overflowBottom = Math.max(0, y + height - documentHeight)
  return overflowLeft + overflowTop + overflowRight + overflowBottom
}

function getPlacementCandidates(preferred: OutsideSide): OutsidePlacement[] {
  const sideOrder = [preferred, OPPOSITE_SIDE[preferred], ...CROSS_AXIS_SIDES[preferred]]
  return sideOrder.flatMap(side => [
    { side, align: 'start' as const },
    { side, align: 'end' as const }
  ])
}

function chooseBestOutsidePlacement(
  anchorLeft: number,
  anchorTop: number,
  anchorRight: number,
  anchorBottom: number,
  contentWidth: number,
  contentHeight: number,
  viewportWidth: number,
  documentHeight: number,
  preferred: OutsideSide
) {
  const candidates = getPlacementCandidates(preferred)
  let bestPlacement = candidates[0]
  let bestScore = Number.POSITIVE_INFINITY

  for (const placement of candidates) {
    const { x, y } = getOutsidePosition(placement, anchorLeft, anchorTop, anchorRight, anchorBottom, contentWidth, contentHeight)
    const score = getOverflowScore(x, y, contentWidth, contentHeight, viewportWidth, documentHeight)

    if (score < bestScore) {
      bestScore = score
      bestPlacement = placement
    }
  }

  return bestPlacement
}

function updateAutoSide() {
  if (isInsideSide.value) return
  if (!isOpen.value) return

  const anchor = dropdownRef.value
  const content = contentRef.value
  if (!anchor || !content) return

  const anchorRect = anchor.getBoundingClientRect()
  const scrollY = window.scrollY
  const doc = document.documentElement
  const body = document.body
  const anchorLeft = anchorRect.left
  const anchorTop = anchorRect.top + scrollY
  const anchorRight = anchorRect.right
  const anchorBottom = anchorRect.bottom + scrollY
  const contentWidth = content.offsetWidth
  const contentHeight = content.offsetHeight
  const viewportWidth = window.innerWidth
  const documentHeight = Math.max(
    doc.scrollHeight,
    doc.clientHeight,
    body?.scrollHeight ?? 0,
    body?.clientHeight ?? 0
  )

  autoPlacement.value = chooseBestOutsidePlacement(
    anchorLeft,
    anchorTop,
    anchorRight,
    anchorBottom,
    contentWidth,
    contentHeight,
    viewportWidth,
    documentHeight,
    preferredOutsideSide.value
  )
}

function handleDocumentClick(e: MouseEvent) {
  if (!dropdownRef.value?.contains(e.target as Node)) isOpen.value = false
}

function handleViewportChange() {
  updateAutoSide()
}

function addViewportListeners() {
  window.addEventListener('resize', handleViewportChange)
  window.addEventListener('scroll', handleViewportChange, true)
}

function removeViewportListeners() {
  window.removeEventListener('resize', handleViewportChange)
  window.removeEventListener('scroll', handleViewportChange, true)
}

onMounted(() => {
  document.addEventListener('click', handleDocumentClick)
})

onBeforeUnmount(() => {
  document.removeEventListener('click', handleDocumentClick)
  removeViewportListeners()
})

watch(isOpen, async open => {
  if (!open) {
    removeViewportListeners()
    return
  }

  addViewportListeners()
  await nextTick()
  updateAutoSide()
}, { flush: 'post' })

watch(() => props.side, async () => {
  const open = isOpen.value
  if (!open) return
  await nextTick()
  updateAutoSide()
}, { flush: 'post' })

function onContentClick(e: MouseEvent) {
  if (!(e.target as HTMLElement).closest('[data-keep-open]')) {
    isOpen.value = false
  }
}

defineExpose({ close: () => { isOpen.value = false } })
</script>

<style scoped>
.z-dropdown {
  position: relative;
}

.z-dropdown-trigger {
  cursor: pointer;
}

.z-dropdown-content {
  position: absolute;
  z-index: 1000;
  --z-dropdown-enter-x: 0;
  --z-dropdown-enter-y: 0;
  min-width: 192px;
  max-width: calc(100vw - 80px);
  max-height: 100vh;
  width: auto;
  border-radius: 12px;
  background: var(--overlay-bgColor, #ffffff);
  box-shadow: var(--shadow-floating-small, 0px 0px 0px 1px #d1d9e080, 0px 6px 12px -3px #25292e0a, 0px 6px 18px 0px #25292e1f);
}

.z-dropdown-content--outside-bottom {
  top: calc(100% + 4px);
  --z-dropdown-enter-y: -8px;
}

.z-dropdown-content--outside-top {
  bottom: calc(100% + 4px);
  --z-dropdown-enter-y: 8px;
}

.z-dropdown-content--outside-left {
  right: calc(100% + 4px);
  --z-dropdown-enter-x: 8px;
}

.z-dropdown-content--outside-right {
  left: calc(100% + 4px);
  --z-dropdown-enter-x: -8px;
}

.z-dropdown-content--inside-top {
  top: 4px;
  left: 4px;
  --z-dropdown-enter-y: 8px;
}

.z-dropdown-content--inside-bottom {
  bottom: 4px;
  left: 4px;
  --z-dropdown-enter-y: -8px;
}

.z-dropdown-content--inside-left {
  top: 4px;
  left: 4px;
  --z-dropdown-enter-x: 8px;
}

.z-dropdown-content--inside-right {
  top: 4px;
  right: 4px;
  --z-dropdown-enter-x: -8px;
}

.z-dropdown-content--inside-center {
  top: 4px;
  left: 50%;
  --z-dropdown-enter-x: -50%;
  --z-dropdown-enter-y: -8px;
  transform: translate(-50%, 0);
}

.z-dropdown-fade-enter-active {
  transition: opacity 0.15s, transform 0.15s;
}

.z-dropdown-content--inside-center.z-dropdown-fade-enter-active {
  transition: none;
}

.z-dropdown-fade-enter-from {
  opacity: 0;
  transform: translate(var(--z-dropdown-enter-x), var(--z-dropdown-enter-y));
}

.z-dropdown-content--inside-center.z-dropdown-fade-enter-from {
  opacity: 1;
  transform: translate(-50%, 0);
}

/* 弹出时自动给 trigger 内的 Button 添加激活样式 */
.z-dropdown-trigger[data-open] :deep(.z-button[data-variant='default']) {
  background-color: var(--button-default-bgColor-active, var(--control-bgColor-active, #ebecf0));
  border-color: var(--button-default-borderColor-active, var(--button-default-borderColor-rest, #d0d7de));
}

.z-dropdown-trigger[data-open] :deep(.z-button[data-variant='primary']) {
  background-color: var(--button-primary-bgColor-active, #197935);
  border-color: var(--button-primary-borderColor-active, var(--button-primary-borderColor-rest, #1f232826));
  box-shadow: var(--button-primary-shadow-selected, var(--shadow-resting-small, 0 1px 0 0 #1f23280a));
}

.z-dropdown-trigger[data-open] :deep(.z-button[data-variant='danger']) {
  background-color: var(--button-danger-bgColor-active, var(--bgColor-danger-emphasis, #cf222e));
  border-color: var(--button-danger-borderColor-active, var(--button-danger-borderColor-hover, #1f232826));
  box-shadow: var(--button-danger-shadow-selected, var(--shadow-resting-small, 0 1px 0 0 #1f23280a));
  color: var(--button-danger-fgColor-active, #ffffff);
}

.z-dropdown-trigger[data-open] :deep(.z-button[data-variant='invisible']) {
  background-color: var(--button-invisible-bgColor-active, var(--control-transparent-bgColor-active, rgba(129, 139, 152, 0.16)));
}

.z-dropdown-trigger[data-open] :deep(.z-button[data-variant='link']) {
  text-decoration: underline;
}
</style>
