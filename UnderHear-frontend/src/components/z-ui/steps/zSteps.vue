<template>
  <div
    class="z-steps"
    :class="{
      'z-steps--horizontal': props.direction === 'horizontal',
      'z-steps--vertical': props.direction === 'vertical'
    }">
    <slot></slot>
  </div>
</template>

<script setup lang="ts">
import { computed, provide, ref, toRef } from 'vue'

interface Props {
  /** 当前激活的步骤 */
  active?: number;
  /** 方向 */
  direction?: 'horizontal' | 'vertical';
  /** 步骤间距 */
  space?: number | string;
  /** 是否居中 */
  alignCenter?: boolean;
}

const props = withDefaults(defineProps<Props>(), {
  active: 0,
  direction: 'horizontal',
  space: ''
})

const registeredStepUids = ref<number[]>([])

const registerStep = (uid: number) => {
  if (!registeredStepUids.value.includes(uid)) {
    registeredStepUids.value.push(uid)
  }
}

const unregisterStep = (uid: number) => {
  registeredStepUids.value = registeredStepUids.value.filter(item => item !== uid)
}

const getStepNumber = (uid: number) => {
  const index = registeredStepUids.value.indexOf(uid)
  return index === -1 ? 0 : index + 1
}

const active = toRef(props, 'active')
provide('zStepsActive', active)

const direction = toRef(props, 'direction')
provide('zStepsDirection', direction)

const space = toRef(props, 'space')
provide('zStepsSpace', space)

const alignCenter = toRef(props, 'alignCenter')
provide('zStepsAlignCenter', alignCenter)

provide('zStepsRegisterStep', registerStep)
provide('zStepsUnregisterStep', unregisterStep)
provide('zStepsGetStepNumber', getStepNumber)

// 提供步骤个数
const stepCount = computed(() => registeredStepUids.value.length)
provide('zStepsCount', stepCount)
</script>

<style scoped>
.z-steps {
  display: flex;
  line-height: normal;
}

.z-steps--horizontal {
  white-space: nowrap;
}

.z-steps--vertical {
  flex-direction: column;
  flex-wrap: nowrap;
  height: 100%;
}
</style>
