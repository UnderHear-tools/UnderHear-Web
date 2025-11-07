<template>
	<span class="tech-tag">
		<slot></slot>
	</span>
</template>

<script setup lang="ts">
// TechTag 组件 - 用于显示技术标签
</script>

<style scoped>
.tech-tag {
    background-color: #ddf4ff;
    color: #0969da;
    padding: 0px 10px 0px 10px;
    line-height: 22px;
    border-radius: 100px;
    font-size: 12px;
    font-weight: 500;
}
</style>