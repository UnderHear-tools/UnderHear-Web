<template>
  <div
    class="z-step"
    :class="{
      'is-horizontal': direction === 'horizontal',
      'is-vertical': direction === 'vertical',
      'is-flex': !space && !alignCenter,
      'is-center': alignCenter && direction === 'horizontal'
    }"
    :style="stepStyle">
    <div
      class="z-step__head"
      :class="{
        'is-process': active + 1 === resolvedStep,
        'is-wait': active + 1 < resolvedStep,
        'is-finish': active >= resolvedStep
      }">
      <div class="z-step__icon is-text">
        <div class="z-step__icon-inner">{{ resolvedStep }}</div>
      </div>
      <div class="z-step__line">
        <div
          class="z-step__line-inner"
          :style="{
            width: (active > resolvedStep && direction === 'horizontal') ? '100%' : 0,
            height: (active > resolvedStep && direction === 'vertical') ? '100%' : 0,
            borderWidth: active > resolvedStep ? '1px' : 0
          }">
        </div>
      </div>
    </div>
    <div class="z-step__content">
      <div
        class="z-step__title"
        :class="{
          'is-process': active + 1 === resolvedStep,
          'is-wait': active + 1 < resolvedStep,
          'is-finish': active >= resolvedStep
        }">
        {{ title }}
      </div>
      <div 
        class="z-step__description"
        :class="{
          'is-process': active + 1 === resolvedStep,
          'is-wait': active + 1 < resolvedStep,
          'is-finish': active >= resolvedStep
        }">
          {{ description }}
        </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed, getCurrentInstance, inject, onBeforeMount, onBeforeUnmount, ref, type ComputedRef, type Ref } from 'vue'

const active = inject<Ref<number>>('zStepsActive', ref<number>(0))
const direction = inject<Ref<'horizontal' | 'vertical'>>('zStepsDirection', ref<'horizontal' | 'vertical'>('horizontal'))
const space = inject<Ref<number | string>>('zStepsSpace', ref<number | string>(''))
const alignCenter = inject<Ref<boolean | undefined>>('zStepsAlignCenter', ref<boolean | undefined>(undefined))

const instance = getCurrentInstance()
const uid = instance?.uid ?? -1

const registerStep = inject<(uid: number) => void>('zStepsRegisterStep', (_uid: number) => {})
const unregisterStep = inject<(uid: number) => void>('zStepsUnregisterStep', (_uid: number) => {})
const getStepNumber = inject<(uid: number) => number>('zStepsGetStepNumber', (_uid: number) => 0)
const stepCount = inject<ComputedRef<number>>('zStepsCount', computed(() => 0))

interface Props {
  /** 步骤标题 */
  title?: string;
  /** 步骤描述 */
  description?: string;
  // TODO 废弃step属性
  /** 步骤索引，如果未传入，则根据组件个数计算 */
  // step?: number;
}

const props = withDefaults(defineProps<Props>(), {
  title: '',
  description: ''
})

onBeforeMount(() => {
  // 注册step组件
  registerStep(uid)
})

onBeforeUnmount(() => {
  // 移除注册的step组件
  unregisterStep(uid)
})

// 计算步骤索引
const resolvedStep = computed(() => {
  // if (typeof props.step === 'number') return props.step
  return getStepNumber(uid)
})

// 计算step组件样式
const stepStyle = computed(() => {
  // TODO 需要根据z-step组件个数计算flex-basis
  // TODO 最后一个z-step组件需要根据组件个数计算max-width
  const style: Record<string, string> = {}
  const count = Math.max(stepCount.value, 1)
  const isLast = resolvedStep.value === count
  if (direction.value === 'horizontal' && count > 1) {
    style.flexBasis = `${100 / (count - 1)}%`
    if (isLast) {
      style.maxWidth = `${100 / count}%`
    }
  } else if (direction.value === 'vertical' && count > 1) {
    style.flexBasis = `${100 / (count - 1)}%`
  }
  if (typeof space.value === 'number') {
    style.flexBasis = `${space.value}px`
  } else if (typeof space.value === 'string' && space.value !== '') {
    style.flexBasis = space.value
  }
  return style
})
</script>

<style scoped>
.z-step {
  position: relative;
  flex-shrink: 1;
}

.z-step.is-horizontal {
  display: inline-block;
}

.z-step.is-vertical {
  display: flex;
}

.z-step:last-of-type.is-flex {
  flex-basis: auto !important;
  flex-grow: 0;
  flex-shrink: 0;
}

.z-step__head {
  position: relative;
  width: 100%;
}

.z-step.is-vertical .z-step__head {
  flex-grow: 0;
  width: 24px;
}

.z-step__head.is-process {
  color: #303133;
  border-color: #a8abb2;
}

.z-step__head.is-wait {
  color: #a8abb2;
  border-color: #a8abb2;
}

.z-step__head.is-finish {
  color: #0969da;
  border-color: #0969da;
}

.z-step__icon {
  position: relative;
  z-index: 1;
  display: inline-flex;
  justify-content: center;
  align-items: center;
  width: 24px;
  height: 24px;
  font-size: 14px;
  background: #fff;
}

.z-step__icon.is-text {
  border: 2px solid currentColor;
  border-radius: 50%;
}

.z-step__icon-inner {
  display: inline-block;
  font-weight: 700;
  line-height: 1;
  text-align: center;
  user-select: none;
}

.z-step__line {
  position: absolute;
  border-color: currentColor;
  background-color: #a8abb2;
}

.z-step__content {
  white-space: normal;
  text-align: left;
}

.z-step.is-vertical .z-step__content {
  padding-left: 10px;
  flex-grow: 1;
}

.z-step.is-center .z-step__head,
.z-step.is-center .z-step__content {
  text-align: center;
}

.z-step__title {
  font-size: 16px;
  line-height: 38px;
}

.z-step.is-vertical .z-step__title {
  line-height: 24px;
  padding-bottom: 8px;
}

.z-step__title.is-process {
  color: #303133;
  font-weight: 700;
}

.z-step__title.is-wait {
  color: #a8abb2;
}

.z-step__title.is-finish {
  color: #0969da;
}

.z-step__description {
  padding-right: 10%;
  margin-top: -5px;
  font-size: 12px;
  line-height: 20px;
  font-weight: 400;
}

.z-step.is-vertical .z-step__description {
  padding-right: 0;
}

.z-step:last-of-type .z-step__description {
  padding-right: 0;
}

.z-step.is-center .z-step__description {
  padding-left: 20%;
  padding-right: 20%;
}

.z-step__description.is-process {
  color: #303133;
}

.z-step__description.is-wait {
  color: #a8abb2;
}

.z-step__description.is-finish {
  color: #0969da;
}

.z-step.is-horizontal .z-step__line {
  height: 2px;
  top: 11px;
  right: 0;
  left: 0;
}

.z-step.is-vertical .z-step__line {
  width: 2px;
  top: 0;
  bottom: 0;
  left: 11px;
}

.z-step.is-center .z-step__line {
  left: 50%;
  right: -50%;
}

.z-step:last-of-type .z-step__line {
  display: none;
}

.z-step__line-inner {
  display: block;
  border: 1px solid currentColor;
  box-sizing: border-box;
  width: 0;
  height: 0;
}
</style>
