<template>
	<span class="z-tag" :class="`z-tag--${size}`" :style="customStyle">
		<slot name="visual"></slot>
		<slot></slot>
		<slot name="action"></slot>
	</span>
</template>

<script setup lang="ts">
import { computed } from 'vue'

const props = withDefaults(defineProps<{
  size?: 'small' | 'medium' | 'large' | 'xlarge'
  color?: string
  bgColor?: string
}>(), {
  size: 'medium'
})

const customStyle = computed(() => ({
  color: props.color,
  backgroundColor: props.bgColor
}))
</script>

<style scoped>
.z-tag {
    display: inline-flex;
    align-items: center;
    background-color: var(--bgColor-accent-muted, #ddf4ff);
    color: var(--fgColor-link, #0969da);
    border-radius: 100px;
    font-weight: 500;
    gap: 6px;
}

.z-tag--small {
    padding: 0 8px;
    line-height: 20px;
    font-size: 12px;
}

.z-tag--medium {
    padding: 0 10px;
    line-height: 22px;
    font-size: 12px;
}

.z-tag--large {
    padding: 0 12px;
    line-height: 26px;
    font-size: 14px;
}

.z-tag--xlarge {
    padding: 0 14px;
    line-height: 30px;
    font-size: 14px;
}
</style>