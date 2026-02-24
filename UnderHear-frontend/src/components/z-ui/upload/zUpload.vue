<template>
	<div class="z-upload">
		<div
			class="z-upload-drop"
			:class="{ 'z-upload-drop--over': dragging }"
			@dragover.prevent="dragging = true"
			@dragleave="dragging = false"
			@drop.prevent="onDrop"
			@click="inputRef!.click()"
		>
			<slot>
				<svg class="z-upload-icon" aria-hidden="true" viewBox="0 0 16 16" fill="currentColor">
					<path d="M2.75 14A1.75 1.75 0 0 1 1 12.25v-2.5a.75.75 0 0 1 1.5 0v2.5c0 .138.112.25.25.25h10.5a.25.25 0 0 0 .25-.25v-2.5a.75.75 0 0 1 1.5 0v2.5A1.75 1.75 0 0 1 13.25 14Z" />
					<path d="M11.78 4.72a.749.749 0 1 1-1.06 1.06L8.75 3.811V9.5a.75.75 0 0 1-1.5 0V3.811L5.28 5.78a.749.749 0 1 1-1.06-1.06l3.25-3.25a.749.749 0 0 1 1.06 0l3.25 3.25Z" />
				</svg>
				<p class="z-upload-text">拖拽文件到此处，或点击选择</p>
			</slot>
			<p v-if="hint" class="z-upload-hint">{{ hint }}</p>
			<input ref="inputRef" type="file" :accept="accept" hidden @change="onFileChange" />
		</div>

		<div v-if="modelValue" class="z-upload-file">
			<span class="z-upload-file-name">{{ modelValue.name }}</span>
			<span class="z-upload-file-size">{{ (modelValue.size / 1024).toFixed(1) }} KB</span>
			<button class="z-upload-file-remove" @click="emit('update:modelValue', null)">
				<svg aria-hidden="true" width="16" height="16" viewBox="0 0 16 16" fill="currentColor"><path d="M3.72 3.72a.75.75 0 0 1 1.06 0L8 6.94l3.22-3.22a.749.749 0 0 1 1.275.326.749.749 0 0 1-.215.734L9.06 8l3.22 3.22a.749.749 0 0 1-.326 1.275.749.749 0 0 1-.734-.215L8 9.06l-3.22 3.22a.751.751 0 0 1-1.042-.018.751.751 0 0 1-.018-1.042L6.94 8 3.72 4.78a.75.75 0 0 1 0-1.06Z" /></svg>
			</button>
		</div>
	</div>
</template>

<script setup lang="ts">
import { ref } from 'vue'

interface Props {
	modelValue?: File | null
	accept?: string
	hint?: string
}

const props = withDefaults(defineProps<Props>(), {
	modelValue: null,
	accept: '',
	hint: ''
})

const emit = defineEmits<{
	'update:modelValue': [value: File | null]
}>()

const inputRef = ref<HTMLInputElement>()
const dragging = ref(false)

const isAccepted = (file: File) => {
	if (!props.accept) return true
	const types = props.accept.split(',').map(t => t.trim())
	return types.some(t =>
		t.startsWith('.') ? file.name.endsWith(t) : file.type === t
	)
}

const selectFile = (file: File) => {
	if (isAccepted(file)) emit('update:modelValue', file)
}

const onDrop = (e: DragEvent) => {
	dragging.value = false
	selectFile(e.dataTransfer!.files[0])
}

const onFileChange = (e: Event) => {
	selectFile((e.target as HTMLInputElement).files![0])
}
</script>

<style scoped>
.z-upload-drop {
	display: flex;
	flex-direction: column;
	align-items: center;
	justify-content: center;
	gap: 8px;
	padding: 3rem 2rem;
	border: 2px dashed var(--borderColor-default);
	border-radius: 6px;
	background: var(--bgColor-muted);
	cursor: pointer;
	transition: border-color 0.2s, background 0.2s;
}

.z-upload-drop:hover,
.z-upload-drop--over {
	border-color: var(--borderColor-accent-emphasis);
	background: color-mix(in srgb, var(--bgColor-accent-emphasis) 6%, var(--bgColor-muted));
}

.z-upload-icon {
	width: 32px;
	height: 32px;
	color: var(--fgColor-muted);
}

.z-upload-text {
	margin: 0;
	font-size: 1rem;
	color: var(--fgColor-default);
}

.z-upload-hint {
	margin: 0;
	font-size: 0.85rem;
	color: var(--fgColor-muted);
}

.z-upload-file {
	display: flex;
	align-items: center;
	gap: 12px;
	margin-top: 12px;
	padding: 10px 14px;
	border: 1px solid var(--borderColor-default);
	border-radius: 6px;
	background: var(--bgColor-muted);
}

.z-upload-file-name {
	font-size: 0.8rem;
	color: var(--fgColor-default);
}

.z-upload-file-size {
	font-size: 0.8rem;
	color: var(--fgColor-muted);
}

.z-upload-file-remove {
	margin-left: auto;
	display: grid;
	place-items: center;
	width: 32px;
	height: 32px;
	appearance: none;
	border: none;
	border-radius: 6px;
	background: none;
	cursor: pointer;
	color: var(--fgColor-muted);
}

.z-upload-file-remove:hover {
	background: var(--control-transparent-bgColor-hover, #818b981a);
	color: var(--fgColor-danger);
}

.z-upload-file-remove:active {
	background: var(--control-transparent-bgColor-active, #818b9826);
}

@media (max-width: 768px) {
	.z-upload-drop {
		padding: 2rem 1.25rem;
	}

	.z-upload-file-name {
		flex: 1 1 0%;
		min-width: 0;
		overflow: hidden;
		text-overflow: ellipsis;
		white-space: nowrap;
	}
}
</style>
