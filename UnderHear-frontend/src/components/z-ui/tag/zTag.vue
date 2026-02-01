<template>
	<span class="z-tag" :class="[tagSize.get(props.size)]">
        <span v-if="$slots.visual || props.visual" class="z-tag-visual-icon">
            <slot name="visual">
                <component :is="props.visual" size="12" />
            </slot>
        </span>
        <span class="z-tag-content">
            <slot></slot>
        </span>
        <button v-if="$slots.action || props.action" type="button" class="z-tag-action-button" @click.stop="handleActionClick">
            <slot name="action">
                <component :is="props.action" size="12" />
            </slot>
        </button>
	</span>
</template>

<script setup lang="ts">
import type { Component } from 'vue'

// TechTag 组件 - 用于显示技术标签
interface Props {
    /** 标签尺寸 */
    size?: 'small' | 'medium' | 'large' | 'x-large'
    /** 操作icon */
    action?: Component;
    /** 视觉icon */
    visual?: Component;
}

const props = withDefaults(defineProps<Props>(), {
    size: 'medium'
})

const emit = defineEmits<{
    'actionClick': [event: MouseEvent]
}>()

// 标签尺寸类名
const tagSize = new Map<Props['size'], string>([
    ['small', 'z-tag-small'],
    ['medium', 'z-tag-medium'],
    ['large', 'z-tag-large'],
    ['x-large', 'z-tag-x-large']
])

function handleActionClick(event: MouseEvent) {
    emit('actionClick', event)
}
</script>

<style scoped>
.z-tag {
    background-color: #ddf4ff;
    color: #0969da;
    padding: 0px 10px 0px 10px;
    border-radius: 100px;
    display: flex;
    align-items: center;
}

.z-tag-content {
    font-size: 12px;
    line-height: 12px;
    height: 12px;
    font-weight: 500;
}

.z-tag-action-button {
    cursor: pointer;
    width: 12px;
    height: 12px;
    border-radius: 50%;
    display: flex;
    align-items: center;
    justify-content: center;
}

.z-tag-visual-icon {
    width: 12px;
    height: 12px;
    display: flex;
    align-items: center;
    justify-content: center;
}

.z-tag-small .z-tag-action-button {
    margin-left: 2px;
}

.z-tag-medium .z-tag-action-button {
    margin-left: 4px;
}

.z-tag-large .z-tag-action-button {
    margin-left: 6px;
}

.z-tag-x-large .z-tag-action-button {
    margin-left: 8px;
}

.z-tag-small .z-tag-visual-icon {
    margin-right: 2px;
}

.z-tag-medium .z-tag-visual-icon {
    margin-right: 4px;
}

.z-tag-large .z-tag-visual-icon {
    margin-right: 6px;
}

.z-tag-x-large .z-tag-visual-icon {
    margin-right: 8px;
}

.z-tag-action-button:hover {
    background-color: #0969da;
    color: #fff;
}

.z-tag-small {
    padding: 0px 8px 0px 8px;
    height: 20px;
}

.z-tag-medium {
    padding: 0px 10px 0px 10px;
    height: 24px;
}

.z-tag-large {
    padding: 0px 12px 0px 12px;
    height: 28px;
}

.z-tag-x-large {
    padding: 0px 14px 0px 14px;
    height: 32px;
}
</style>
