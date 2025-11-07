<template>
  <div
    ref="scrollbarRef"
    :class="scrollbarClasses"
    :style="scrollbarStyles"
  >
    <div
      ref="wrapRef"
      :class="wrapClasses"
      :style="wrapStyles"
      @scroll="handleScroll"
    >
      <component
        :is="tag"
        ref="viewRef"
        :class="viewClasses"
        :style="viewStyles"
      >
        <slot />
      </component>
    </div>
    
    <!-- 垂直滚动条 -->
    <div
      v-if="!native && showVerticalBar"
      ref="verticalBarRef"
      class="z-scrollbar__bar z-scrollbar__bar--vertical"
      @mousedown="handleBarMouseDown('vertical', $event)"
    >
      <div
        class="z-scrollbar__thumb"
        :style="verticalThumbStyle"
      />
    </div>
    
    <!-- 水平滚动条 -->
    <div
      v-if="!native && showHorizontalBar"
      ref="horizontalBarRef"
      class="z-scrollbar__bar z-scrollbar__bar--horizontal"
      @mousedown="handleBarMouseDown('horizontal', $event)"
    >
      <div
        class="z-scrollbar__thumb"
        :style="horizontalThumbStyle"
      />
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted, onBeforeUnmount, nextTick, watch } from 'vue'
import { addUnit } from '@/lib/utils'

interface Props {
  height?: string | number
  maxHeight?: string | number
  native?: boolean
  wrapStyle?: string | Record<string, any>
  wrapClass?: string
  viewStyle?: string | Record<string, any>
  viewClass?: string
  noresize?: boolean
  tag?: string
}

interface Emits {
  scroll: [{ scrollLeft: number; scrollTop: number }]
}

const props = withDefaults(defineProps<Props>(), {
  native: false,
  noresize: false,
  tag: 'div'
})

const emit = defineEmits<Emits>()

const scrollbarRef = ref<HTMLElement>()
const wrapRef = ref<HTMLElement>()
const viewRef = ref<HTMLElement>()
const verticalBarRef = ref<HTMLElement>()
const horizontalBarRef = ref<HTMLElement>()

const scrollTop = ref(0)
const scrollLeft = ref(0)
const clientHeight = ref(0)
const clientWidth = ref(0)
const scrollHeight = ref(0)
const scrollWidth = ref(0)

const showVerticalBar = computed(() => {
  return scrollHeight.value > clientHeight.value
})

const showHorizontalBar = computed(() => {
  return scrollWidth.value > clientWidth.value
})

const verticalThumbStyle = computed(() => {
  if (!showVerticalBar.value) return {}
  
  const thumbHeight = (clientHeight.value / scrollHeight.value) * 100
  const thumbTop = (scrollTop.value / (scrollHeight.value - clientHeight.value)) * (100 - thumbHeight)
  
  return {
    height: `${thumbHeight}%`,
    transform: `translateY(${thumbTop}%)`
  }
})

const horizontalThumbStyle = computed(() => {
  if (!showHorizontalBar.value) return {}
  
  const thumbWidth = (clientWidth.value / scrollWidth.value) * 100
  const thumbLeft = (scrollLeft.value / (scrollWidth.value - clientWidth.value)) * (100 - thumbWidth)
  
  return {
    width: `${thumbWidth}%`,
    transform: `translateX(${thumbLeft}%)`
  }
})

const scrollbarClasses = computed(() => {
  return [
    'z-scrollbar',
    {
      'z-scrollbar--native': props.native
    }
  ]
})

const scrollbarStyles = computed(() => {
  const styles: Record<string, any> = {}
  
  if (props.height) {
    styles.height = addUnit(props.height)
  }
  
  if (props.maxHeight) {
    styles.maxHeight = addUnit(props.maxHeight)
  }
  
  return styles
})

const wrapClasses = computed(() => {
  return [
    'z-scrollbar__wrap',
    {
      'z-scrollbar__wrap--native': props.native
    },
    props.wrapClass
  ]
})

const wrapStyles = computed(() => {
  let styles: Record<string, any> = {}
  
  if (typeof props.wrapStyle === 'string') {
    styles = { cssText: props.wrapStyle }
  } else if (props.wrapStyle) {
    styles = { ...props.wrapStyle }
  }
  
  return styles
})

const viewClasses = computed(() => {
  return [
    'z-scrollbar__view',
    props.viewClass
  ]
})

const viewStyles = computed(() => {
  let styles: Record<string, any> = {}
  
  if (typeof props.viewStyle === 'string') {
    styles = { cssText: props.viewStyle }
  } else if (props.viewStyle) {
    styles = { ...props.viewStyle }
  }
  
  return styles
})

const handleScroll = (e: Event) => {
  const target = e.target as HTMLElement
  scrollTop.value = target.scrollTop
  scrollLeft.value = target.scrollLeft
  
  emit('scroll', {
    scrollLeft: scrollLeft.value,
    scrollTop: scrollTop.value
  })
}

const handleBarMouseDown = (direction: 'vertical' | 'horizontal', e: MouseEvent) => {
  e.preventDefault()
  
  const startPos = direction === 'vertical' ? e.clientY : e.clientX
  const startScroll = direction === 'vertical' ? scrollTop.value : scrollLeft.value
  
  const handleMouseMove = (moveEvent: MouseEvent) => {
    const currentPos = direction === 'vertical' ? moveEvent.clientY : moveEvent.clientX
    const delta = currentPos - startPos
    
    if (direction === 'vertical') {
      const maxScroll = scrollHeight.value - clientHeight.value
      const ratio = delta / clientHeight.value
      const newScrollTop = startScroll + ratio * maxScroll
      setScrollTop(Math.max(0, Math.min(maxScroll, newScrollTop)))
    } else {
      const maxScroll = scrollWidth.value - clientWidth.value
      const ratio = delta / clientWidth.value
      const newScrollLeft = startScroll + ratio * maxScroll
      setScrollLeft(Math.max(0, Math.min(maxScroll, newScrollLeft)))
    }
  }
  
  const handleMouseUp = () => {
    document.removeEventListener('mousemove', handleMouseMove)
    document.removeEventListener('mouseup', handleMouseUp)
  }
  
  document.addEventListener('mousemove', handleMouseMove)
  document.addEventListener('mouseup', handleMouseUp)
}

const update = () => {
  if (!wrapRef.value) return
  
  const wrap = wrapRef.value
  clientHeight.value = wrap.clientHeight
  clientWidth.value = wrap.clientWidth
  scrollHeight.value = wrap.scrollHeight
  scrollWidth.value = wrap.scrollWidth
  scrollTop.value = wrap.scrollTop
  scrollLeft.value = wrap.scrollLeft
}

const setScrollTop = (value: number) => {
  if (!wrapRef.value) return
  wrapRef.value.scrollTop = value
}

const setScrollLeft = (value: number) => {
  if (!wrapRef.value) return
  wrapRef.value.scrollLeft = value
}

let resizeObserver: ResizeObserver | null = null

onMounted(() => {
  nextTick(() => {
    update()
    
    if (!props.noresize && wrapRef.value) {
      resizeObserver = new ResizeObserver(() => {
        update()
      })
      resizeObserver.observe(wrapRef.value)
    }
  })
})

onBeforeUnmount(() => {
  if (resizeObserver) {
    resizeObserver.disconnect()
  }
})

watch(() => [props.height, props.maxHeight], () => {
  nextTick(() => {
    update()
  })
})

defineExpose({
  setScrollTop,
  setScrollLeft,
  update
})
</script>

<style scoped>
.z-scrollbar {
  position: relative;
  overflow: hidden;
}

.z-scrollbar__wrap {
  height: 100%;
  overflow: auto;
  scrollbar-width: none;
  -ms-overflow-style: none;
}

.z-scrollbar__wrap::-webkit-scrollbar {
  display: none;
}

.z-scrollbar__wrap--native {
  scrollbar-width: auto;
  -ms-overflow-style: auto;
}

.z-scrollbar__wrap--native::-webkit-scrollbar {
  display: block;
}

.z-scrollbar__view {
  display: block;
}

.z-scrollbar__bar {
  position: absolute;
  right: 2px;
  bottom: 2px;
  z-index: 1;
  border-radius: 4px;
  opacity: 0;
  transition: opacity 0.3s ease;
}

.z-scrollbar:hover .z-scrollbar__bar {
  opacity: 1;
}

.z-scrollbar__bar--vertical {
  width: 6px;
  top: 2px;
}

.z-scrollbar__bar--horizontal {
  height: 6px;
  left: 2px;
}

.z-scrollbar__thumb {
  position: relative;
  display: block;
  width: 100%;
  height: 100%;
  border-radius: inherit;
  background-color: rgba(144, 147, 153, 0.3);
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.z-scrollbar__thumb:hover {
  background-color: rgba(144, 147, 153, 0.5);
}

.z-scrollbar__bar:active .z-scrollbar__thumb {
  background-color: rgba(144, 147, 153, 0.7);
}

/* 响应式设计 */
@media (max-width: 768px) {
  .z-scrollbar__bar {
    width: 4px;
  }
  
  .z-scrollbar__bar--horizontal {
    height: 4px;
  }
}
</style>