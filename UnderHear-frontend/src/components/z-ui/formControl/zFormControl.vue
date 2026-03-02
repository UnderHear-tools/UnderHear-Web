<template>
  <div class="z-form-control" :class="{ 'is-invalid': invalid }">
    <label v-if="$slots.label || label" class="z-form-control__label" :for="htmlFor">
      <slot name="label">{{ label }}</slot>
      <span v-if="required" class="z-form-control__required" aria-hidden="true">{{ requiredIndicator }}</span>
    </label>

    <div class="z-form-control__input">
      <slot />
    </div>

    <p v-if="invalid && hasValidation" class="z-form-control__validation" role="alert">
      <svg aria-hidden="true" focusable="false" width="12" height="12" viewBox="0 0 16 16" fill="currentColor" class="icon-svg"><path d="M6.457 1.047c.659-1.234 2.427-1.234 3.086 0l6.082 11.378A1.75 1.75 0 0 1 14.082 15H1.918a1.75 1.75 0 0 1-1.543-2.575ZM8 5a.75.75 0 0 0-.75.75v2.5a.75.75 0 0 0 1.5 0v-2.5A.75.75 0 0 0 8 5Zm1 6a1 1 0 1 0-2 0 1 1 0 0 0 2 0Z"></path></svg>
      <slot name="validation">{{ validation }}</slot>
    </p>

    <p v-if="hasCaption" class="z-form-control__caption">
      <slot name="caption">{{ caption }}</slot>
    </p>
  </div>
</template>

<script setup lang="ts">
import { computed, useSlots } from 'vue'

interface Props {
  label?: string
  htmlFor?: string
  required?: boolean
  requiredIndicator?: string
  invalid?: boolean
  validation?: string
  caption?: string
}

const props = withDefaults(defineProps<Props>(), {
  label: '',
  htmlFor: '',
  required: false,
  requiredIndicator: '*',
  invalid: false,
  validation: '',
  caption: ''
})

const slots = useSlots()

const hasValidation = computed(() => Boolean(slots.validation || props.validation))
const hasCaption = computed(() => Boolean(slots.caption || props.caption))
</script>

<style scoped>
.z-form-control {
  display: grid;
  gap: 4px;
}

.z-form-control__label {
  display: inline-flex;
  align-items: center;
  gap: 4px;
  font-size: 14px;
  line-height: 1.4;
  font-weight: 600;
  color: var(--fgColor-default, #1f2328);
}

.z-form-control__required {
  color: var(--fgColor-default, #1f2328);
}

.z-form-control__input {
  width: 100%;
}

.z-form-control__validation {
  display: inline-flex;
  align-items: center;
  gap: 3px;
  margin: 0;
  font-size: 12px;
  line-height: 1.4;
  font-weight: 600;
  color: var(--fgColor-danger, #d1242f);
}

.z-form-control__caption {
  margin: 0;
  font-size: 12px;
  line-height: 1.4;
  color: var(--fgColor-muted, #656d76);
}

.z-form-control.is-invalid :deep(.z-input) {
  border-color: var(--borderColor-danger-emphasis, #cf222e);
}

.z-form-control.is-invalid :deep(.z-input:focus) {
  outline: 2px solid var(--borderColor-danger-emphasis, #cf222e);
}
</style>
