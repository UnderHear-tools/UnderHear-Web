<template>
  <div
    class="z-form-control"
    :data-validation-variant="validationVariant"
  >
    <slot />
  </div>
</template>

<script setup lang="ts">
import { toRef } from 'vue'
import { createFormControlContext, provideFormControlContext } from './context'

defineOptions({
  name: 'FormControl'
})

const props = withDefaults(defineProps<{
  required?: boolean
}>(), {
  required: false
})

const context = createFormControlContext(toRef(props, 'required'))

provideFormControlContext(context)

const validationVariant = context.validationVariant
</script>

<style scoped>
.z-form-control {
  display: grid;
  gap: 4px;
}

.z-form-control[data-validation-variant='error'] :deep(.z-input),
.z-form-control[data-validation-variant='error'] :deep(.z-textarea) {
  border-color: var(--borderColor-danger-emphasis, #cf222e);
}

.z-form-control[data-validation-variant='error'] :deep(.z-input:focus),
.z-form-control[data-validation-variant='error'] :deep(.z-textarea:focus) {
  outline: 2px solid var(--borderColor-danger-emphasis, #cf222e);
  outline-offset: -1px;
}

.z-form-control[data-validation-variant='success'] :deep(.z-input),
.z-form-control[data-validation-variant='success'] :deep(.z-textarea) {
  border-color: var(--borderColor-success-emphasis, #1a7f37);
}
</style>
