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
				<Upload class="z-upload-icon" />
				<p class="z-upload-text">拖拽文件到此处，或点击选择</p>
			</slot>
			<p v-if="hint" class="z-upload-hint">{{ hint }}</p>
			<input ref="inputRef" type="file" :accept="accept" hidden @change="onFileChange" />
		</div>

		<div v-if="modelValue" class="z-upload-file">
			<span class="z-upload-file-name">{{ modelValue.name }}</span>
			<span class="z-upload-file-size">{{ (modelValue.size / 1024).toFixed(1) }} KB</span>
			<button class="z-upload-file-remove" @click="emit('update:modelValue', null)">&times;</button>
		</div>
	</div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { Upload } from '@/components/z-ui/icon/Octicons-vue'

interface Props {
	modelValue?: File | null
	accept?: string
	hint?: string
}

withDefaults(defineProps<Props>(), {
	modelValue: null,
	accept: '',
	hint: ''
})

const emit = defineEmits<{
	'update:modelValue': [value: File | null]
}>()

const inputRef = ref<HTMLInputElement>()
const dragging = ref(false)

const onDrop = (e: DragEvent) => {
	dragging.value = false
	emit('update:modelValue', e.dataTransfer!.files[0])
}

const onFileChange = (e: Event) => {
	emit('update:modelValue', (e.target as HTMLInputElement).files![0])
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
	font-size: 0.95rem;
	color: var(--fgColor-default);
}

.z-upload-file-size {
	font-size: 0.85rem;
	color: var(--fgColor-muted);
}

.z-upload-file-remove {
	margin-left: auto;
	appearance: none;
	border: none;
	background: none;
	font-size: 1.2rem;
	cursor: pointer;
	color: var(--fgColor-muted);
}

.z-upload-file-remove:hover {
	color: var(--fgColor-danger);
}
</style>
