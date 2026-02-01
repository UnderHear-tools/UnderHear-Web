<template>
	<span class="z-tag" :class="`z-tag--${size}`">
		<slot></slot>
	</span>
</template>

<script setup lang="ts">
withDefaults(defineProps<{
  size?: 'small' | 'medium' | 'large' | 'xlarge'
}>(), {
  size: 'medium'
})
</script>

<style scoped>
.z-tag {
    background-color: #ddf4ff;
    color: #0969da;
    border-radius: 100px;
    font-weight: 500;
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