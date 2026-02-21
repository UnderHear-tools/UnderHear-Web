<template>
	<div class="z-steps" :class="`z-steps--${orientation}`">
		<div
			v-for="(step, i) in steps"
			:key="i"
			class="z-steps__item"
			:data-state="getState(i)"
		>
			<button
				class="z-steps__trigger"
				:disabled="linear && i > modelValue"
				@click="$emit('update:modelValue', i)"
			>
				<span class="z-steps__indicator">
					<slot :name="`icon-${i}`" :state="getState(i)" :index="i">
						{{ i + 1 }}
					</slot>
				</span>
			</button>
			<div v-if="i < steps.length - 1" class="z-steps__separator" />
			<div class="z-steps__content">
				<span class="z-steps__title">{{ step.title }}</span>
				<span v-if="step.description" class="z-steps__description">{{ step.description }}</span>
			</div>
		</div>
	</div>
</template>

<script setup lang="ts">
export interface StepItem {
	title: string
	description?: string
}

const props = withDefaults(defineProps<{
	steps: StepItem[]
	modelValue?: number
	linear?: boolean
	orientation?: 'horizontal' | 'vertical'
}>(), {
	modelValue: 0,
	linear: false,
	orientation: 'horizontal'
})

defineEmits<{ 'update:modelValue': [value: number] }>()

const getState = (i: number) =>
	i < props.modelValue ? 'completed' : i === props.modelValue ? 'active' : 'inactive'
</script>

<style scoped>
.z-steps {
	display: flex;
	align-items: flex-start;
	gap: 8px;
	width: 100%;
}

.z-steps__item {
	position: relative;
	display: flex;
	flex-direction: column;
	align-items: center;
	flex: 1;
	gap: 8px;
}

.z-steps__trigger {
	display: flex;
	align-items: center;
	justify-content: center;
	padding: 4px;
	border: none;
	background: none;
	cursor: pointer;
	border-radius: 6px;
}

.z-steps__trigger:disabled {
	pointer-events: none;
	opacity: 0.5;
}

.z-steps__indicator {
	display: inline-flex;
	align-items: center;
	justify-content: center;
	width: 32px;
	height: 32px;
	border-radius: 50%;
	font-size: 14px;
	font-weight: 600;
	background-color: var(--bgColor-neutral-muted, #d1d9e080);
	color: var(--fgColor-muted, #656d76);
	transition: background-color 0.2s, color 0.2s;
}

[data-state="active"] .z-steps__indicator {
	background-color: var(--bgColor-accent-emphasis, #0969da);
	color: var(--fgColor-onEmphasis, #fff);
}

[data-state="completed"] .z-steps__indicator {
	background-color: var(--bgColor-accent-emphasis, #0969da);
	color: var(--fgColor-onEmphasis, #fff);
}

.z-steps__separator {
	position: absolute;
	left: calc(50% + 24px);
	right: calc(-50% + 16px);
	top: 20px;
	height: 2px;
	border-radius: 2px;
	background-color: var(--borderColor-muted, #d1d9e0);
	transition: background-color 0.2s;
}

[data-state="completed"] .z-steps__separator {
	background-color: var(--bgColor-accent-emphasis, #0969da);
}

.z-steps__content {
	display: flex;
	flex-direction: column;
	align-items: center;
	text-align: center;
}

.z-steps__title {
	font-size: 14px;
	font-weight: 600;
	color: var(--fgColor-default, #1f2328);
	white-space: nowrap;
}

.z-steps__description {
	font-size: 12px;
	color: var(--fgColor-muted, #656d76);
}

/* vertical */
.z-steps--vertical {
	flex-direction: column;
	gap: 0;
}

.z-steps--vertical .z-steps__item {
	flex-direction: row;
	align-items: flex-start;
	gap: 12px;
}

.z-steps--vertical .z-steps__separator {
	position: absolute;
	left: 20px;
	top: 40px;
	bottom: -8px;
	right: auto;
	width: 2px;
	height: auto;
}

.z-steps--vertical .z-steps__content {
	align-items: flex-start;
	text-align: left;
	padding-bottom: 24px;
}

/* responsive < 768px */
@media (max-width: 767px) {
	.z-steps--horizontal {
		gap: 4px;
	}

	.z-steps--horizontal .z-steps__indicator {
		width: 24px;
		height: 24px;
		font-size: 12px;
	}

	.z-steps--horizontal .z-steps__separator {
		left: calc(50% + 18px);
		right: calc(-50% + 12px);
		top: 16px;
	}

	.z-steps--horizontal .z-steps__title {
		font-size: 12px;
	}
}
</style>