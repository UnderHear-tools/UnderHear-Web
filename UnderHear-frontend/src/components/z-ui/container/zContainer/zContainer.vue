<template>
  <div
    class="z-container"
    :class="{
      'z-container--horizontal': direction === 'horizontal',
      'z-container--vertical': direction === 'vertical',
      'z-container--page': variant === 'page'
    }"
  >
    <slot></slot>
  </div>
</template>


<script setup lang="ts">
interface Props {
  /** 子元素排列方向 */
  direction?: 'horizontal' | 'vertical'
  /** 容器变体：布局容器 / 页面容器 */
  variant?: 'layout' | 'page'
}

withDefaults(defineProps<Props>(), {
  direction: 'vertical',
  variant: 'layout'
})
</script>

<style scoped>
.z-container {
  display: flex;
  flex: 1;
  min-width: 0;
  box-sizing: border-box;
}

.z-container--vertical {
  flex-direction: column;
}

.z-container--horizontal {
  flex-direction: row;
}

.z-container--page {
  max-width: 1150px;
  margin: 0 auto;
  padding: 2rem;
  background: #fff;
}

@media (max-width: 768px) {
  .z-container--page {
    padding: 1rem;
  }
}
</style>
