<template>
  <div
    ref="dropdownRef"
    class="dropdown"
  >
    <div
      class="dropdown-trigger"
      :data-open="isOpen || undefined"
      @click="isOpen = !isOpen"
    >
      <RenderNodes :nodes="parsedChildren.trigger" />
    </div>
    <Transition name="dropdown-fade">
      <div
        v-if="isOpen"
        ref="contentRef"
        class="dropdown-content"
        :class="sideClass"
        :style="contentStyle"
      >
        <RenderNodes :nodes="parsedChildren.content" />
      </div>
    </Transition>
  </div>
</template>

<script setup lang="ts">
import { computed, ref, onMounted, onBeforeUnmount, watch, nextTick, useSlots } from 'vue'
import { RenderNodes, parseSlotMarkers } from '../utils/slotMarkers'

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

const slots = useSlots()
const isOpen = ref(false)
const dropdownRef = ref<HTMLElement>()
const contentRef = ref<HTMLElement>()
const autoPlacement = ref<OutsidePlacement>({ side: 'outside-bottom', align: 'start' })
const openDocumentHeight = ref(0)
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
const sideClass = computed(() => `dropdown-content--${effectiveSide.value}`)
const parsedChildren = computed(() => parseSlotMarkers(slots.default?.() ?? [], {
  trigger: 'DropdownTrigger',
  content: 'DropdownContent'
}))
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

function getVerticalOverflowScore(y: number, height: number, documentHeight: number) {
  const overflowTop = Math.max(0, -y)
  const overflowBottom = Math.max(0, y + height - documentHeight)
  return overflowTop + overflowBottom
}

function getPlacementCandidates(preferred: OutsideSide): OutsidePlacement[] {
  const sideOrder = [preferred, OPPOSITE_SIDE[preferred], ...CROSS_AXIS_SIDES[preferred]]
  return sideOrder.flatMap(side => [
    { side, align: 'start' as const },
    { side, align: 'end' as const }
  ])
}

function chooseBestPlacementFromCandidates(
  candidates: OutsidePlacement[],
  anchorLeft: number,
  anchorTop: number,
  anchorRight: number,
  anchorBottom: number,
  contentWidth: number,
  contentHeight: number,
  viewportWidth: number,
  documentHeight: number
) {
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
  if (preferred === 'outside-top' || preferred === 'outside-bottom') {
    const preferredPlacement = chooseBestPlacementFromCandidates(
      [
        { side: preferred, align: 'start' },
        { side: preferred, align: 'end' }
      ],
      anchorLeft,
      anchorTop,
      anchorRight,
      anchorBottom,
      contentWidth,
      contentHeight,
      viewportWidth,
      documentHeight
    )
    const preferredPosition = getOutsidePosition(preferredPlacement, anchorLeft, anchorTop, anchorRight, anchorBottom, contentWidth, contentHeight)
    const preferredVerticalOverflow = getVerticalOverflowScore(preferredPosition.y, contentHeight, documentHeight)

    if (preferredVerticalOverflow === 0) {
      return preferredPlacement
    }

    const opposite = OPPOSITE_SIDE[preferred]
    const oppositePlacement = chooseBestPlacementFromCandidates(
      [
        { side: opposite, align: 'start' },
        { side: opposite, align: 'end' }
      ],
      anchorLeft,
      anchorTop,
      anchorRight,
      anchorBottom,
      contentWidth,
      contentHeight,
      viewportWidth,
      documentHeight
    )
    const oppositePosition = getOutsidePosition(oppositePlacement, anchorLeft, anchorTop, anchorRight, anchorBottom, contentWidth, contentHeight)
    const oppositeVerticalOverflow = getVerticalOverflowScore(oppositePosition.y, contentHeight, documentHeight)

    if (oppositeVerticalOverflow === 0) {
      return oppositePlacement
    }
  }

  return chooseBestPlacementFromCandidates(
    getPlacementCandidates(preferred),
    anchorLeft,
    anchorTop,
    anchorRight,
    anchorBottom,
    contentWidth,
    contentHeight,
    viewportWidth,
    documentHeight
  )
}

function getDocumentHeight() {
  const doc = document.documentElement
  const body = document.body
  return Math.max(
    doc.scrollHeight,
    doc.clientHeight,
    body?.scrollHeight ?? 0,
    body?.clientHeight ?? 0
  )
}

function updateAutoSide() {
  if (isInsideSide.value) return
  if (!isOpen.value) return

  const anchor = dropdownRef.value
  const content = contentRef.value
  if (!anchor || !content) return

  const anchorRect = anchor.getBoundingClientRect()
  const scrollY = window.scrollY
  const anchorLeft = anchorRect.left
  const anchorTop = anchorRect.top + scrollY
  const anchorRight = anchorRect.right
  const anchorBottom = anchorRect.bottom + scrollY
  const contentWidth = content.offsetWidth
  const contentHeight = content.offsetHeight
  const viewportWidth = window.innerWidth
  const documentHeight = openDocumentHeight.value || getDocumentHeight()

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
    openDocumentHeight.value = 0
    removeViewportListeners()
    return
  }

  if (!openDocumentHeight.value) {
    openDocumentHeight.value = getDocumentHeight()
  }

  addViewportListeners()
  await nextTick()
  updateAutoSide()
})

watch(() => props.side, async () => {
  const open = isOpen.value
  if (!open) return
  await nextTick()
  updateAutoSide()
}, { flush: 'post' })

defineExpose({ close: () => { isOpen.value = false } })
</script>

<style scoped>
.dropdown {
  position: relative;
}

.dropdown-trigger {
  cursor: pointer;
}

.dropdown-content {
  position: absolute;
  z-index: 1000;
  --dropdown-enter-x: 0;
  --dropdown-enter-y: 0;
  min-width: 192px;
  max-width: calc(100vw - 80px);
  max-height: 100vh;
  width: auto;
  border-radius: 12px;
  background: var(--overlay-bgColor, #ffffff);
  box-shadow: var(--shadow-floating-small, 0px 0px 0px 1px #d1d9e080, 0px 6px 12px -3px #25292e0a, 0px 6px 18px 0px #25292e1f);
}

.dropdown-content--outside-bottom {
  top: calc(100% + 4px);
  --dropdown-enter-y: -8px;
}

.dropdown-content--outside-top {
  bottom: calc(100% + 4px);
  --dropdown-enter-y: 8px;
}

.dropdown-content--outside-left {
  right: calc(100% + 4px);
  --dropdown-enter-x: 8px;
}

.dropdown-content--outside-right {
  left: calc(100% + 4px);
  --dropdown-enter-x: -8px;
}

.dropdown-content--inside-top {
  top: 4px;
  left: 4px;
  --dropdown-enter-y: 8px;
}

.dropdown-content--inside-bottom {
  bottom: 4px;
  left: 4px;
  --dropdown-enter-y: -8px;
}

.dropdown-content--inside-left {
  top: 4px;
  left: 4px;
  --dropdown-enter-x: 8px;
}

.dropdown-content--inside-right {
  top: 4px;
  right: 4px;
  --dropdown-enter-x: -8px;
}

.dropdown-content--inside-center {
  top: 4px;
  left: 50%;
  --dropdown-enter-x: -50%;
  --dropdown-enter-y: -8px;
  transform: translate(-50%, 0);
}

.dropdown-fade-enter-active {
  transition: opacity 0.15s, transform 0.15s;
}

.dropdown-content--inside-center.dropdown-fade-enter-active {
  transition: none;
}

.dropdown-fade-enter-from {
  opacity: 0;
  transform: translate(var(--dropdown-enter-x), var(--dropdown-enter-y));
}

.dropdown-content--inside-center.dropdown-fade-enter-from {
  opacity: 1;
  transform: translate(-50%, 0);
}

/* 弹出时自动给 trigger 内的 Button 添加激活样式 */
.dropdown-trigger[data-open] :deep(.button[data-variant='default']) {
  background-color: var(--button-default-bgColor-active, var(--control-bgColor-active, #ebecf0));
  border-color: var(--button-default-borderColor-active, var(--button-default-borderColor-rest, #d0d7de));
}

.dropdown-trigger[data-open] :deep(.button[data-variant='primary']) {
  background-color: var(--button-primary-bgColor-active, #197935);
  border-color: var(--button-primary-borderColor-active, var(--button-primary-borderColor-rest, #1f232826));
  box-shadow: var(--button-primary-shadow-selected, var(--shadow-resting-small, 0 1px 0 0 #1f23280a));
}

.dropdown-trigger[data-open] :deep(.button[data-variant='danger']) {
  background-color: var(--button-danger-bgColor-active, var(--bgColor-danger-emphasis, #cf222e));
  border-color: var(--button-danger-borderColor-active, var(--button-danger-borderColor-hover, #1f232826));
  box-shadow: var(--button-danger-shadow-selected, var(--shadow-resting-small, 0 1px 0 0 #1f23280a));
  color: var(--button-danger-fgColor-active, #ffffff);
}

.dropdown-trigger[data-open] :deep(.button[data-variant='invisible']) {
  background-color: var(--button-invisible-bgColor-active, var(--control-transparent-bgColor-active, rgba(129, 139, 152, 0.16)));
}

.dropdown-trigger[data-open] :deep(.button[data-variant='link']) {
  text-decoration: underline;
}
</style>
