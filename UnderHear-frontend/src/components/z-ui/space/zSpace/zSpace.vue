<template>
  <div
    :class="spaceClasses"
    :style="spaceStyles"
  >
    <template v-for="(item, index) in items" :key="index">
      <div class="z-space-item">
        <component :is="item" />
      </div>
      <div
        v-if="separator && index < items.length - 1"
        class="z-space-separator"
      >
        {{ separator }}
      </div>
    </template>
  </div>
</template>

<script setup lang="ts">
import { computed, useSlots } from 'vue'
import type { VNode } from 'vue'
import { addUnit } from '@/lib/utils'

interface Props {
  direction?: 'horizontal' | 'vertical'
  size?: 'small' | 'medium' | 'large' | number | [number, number]
  align?: 'start' | 'end' | 'center' | 'baseline'
  wrap?: boolean
  separator?: string
}

const props = withDefaults(defineProps<Props>(), {
  direction: 'horizontal',
  size: 'small',
  wrap: false
})

const slots = useSlots()

const items = computed(() => {
  const defaultSlot = slots.default?.()
  if (!defaultSlot) return []
  
  return defaultSlot.filter((item: VNode) => {
    return item.type !== Comment && item.type !== Text || 
           (item.type === Text && item.children && String(item.children).trim())
  })
})

const spaceClasses = computed(() => {
  return [
    'z-space',
    `z-space--${props.direction}`,
    {
      'z-space--wrap': props.wrap,
      [`z-space--align-${props.align}`]: props.align
    }
  ]
})

const spaceStyles = computed(() => {
  const styles: Record<string, any> = {}
  
  let gapValue: string
  
  if (typeof props.size === 'number') {
    gapValue = addUnit(props.size) || '0px'
  } else if (Array.isArray(props.size)) {
    const [horizontal, vertical] = props.size
    gapValue = `${addUnit(vertical)} ${addUnit(horizontal)}`
  } else {
    const sizeMap = {
      small: '8px',
      medium: '16px',
      large: '24px'
    }
    gapValue = sizeMap[props.size]
  }
  
  styles.gap = gapValue
  
  return styles
})
</script>

<style scoped>
.z-space {
  display: flex;
  align-items: flex-start;
}

.z-space--horizontal {
  flex-direction: row;
}

.z-space--vertical {
  flex-direction: column;
}

.z-space--wrap {
  flex-wrap: wrap;
}

.z-space--align-start {
  align-items: flex-start;
}

.z-space--align-end {
  align-items: flex-end;
}

.z-space--align-center {
  align-items: center;
}

.z-space--align-baseline {
  align-items: baseline;
}

.z-space-item {
  display: flex;
  align-items: center;
}

.z-space-separator {
  color: #d1d5db;
  font-size: 14px;
  user-select: none;
  display: flex;
  align-items: center;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .z-space {
    gap: 8px !important;
  }
  
  .z-space--horizontal {
    flex-wrap: wrap;
  }
}
</style>