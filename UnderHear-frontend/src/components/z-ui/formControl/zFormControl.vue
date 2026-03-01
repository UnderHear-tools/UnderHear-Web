<template>
  <div class="z-form-control" :class="{ 'is-invalid': invalid }">
    <label v-if="$slots.label || label" class="z-form-control__label" :for="htmlFor">
      <slot name="label">{{ label }}</slot>
      <span v-if="required" class="z-form-control__required" aria-hidden="true">{{ requiredIndicator }}</span>
    </label>

    <div class="z-form-control__input">
      <slot />
    </div>

    <p v-if="hasValidation" class="z-form-control__validation" role="alert">
      <svg
        v-if="showValidationIcon"
        class="z-form-control__validation-icon"
        aria-hidden="true"
        viewBox="0 0 16 16"
      >
        <path
          d="M8.865.513a.75.75 0 0 0-1.73 0l-6.5 14.25A.75.75 0 0 0 1.365 16h13.27a.75.75 0 0 0 .73-1.237ZM8 5a.75.75 0 0 1 .75.75v3.5a.75.75 0 0 1-1.5 0v-3.5A.75.75 0 0 1 8 5Zm0 8a1 1 0 1 1 0-2 1 1 0 0 1 0 2Z"
          fill="currentColor"
        />
      </svg>
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
  showValidationIcon?: boolean
}

const props = withDefaults(defineProps<Props>(), {
  label: '',
  htmlFor: '',
  required: false,
  requiredIndicator: '*',
  invalid: false,
  validation: '',
  caption: '',
  showValidationIcon: true
})

const slots = useSlots()

const hasValidation = computed(() => Boolean(slots.validation || props.validation))
const hasCaption = computed(() => Boolean(slots.caption || props.caption))
</script>

<style scoped>
.z-form-control {
  display: grid;
  gap: 8px;
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
  color: var(--fgColor-danger, #d1242f);
}

.z-form-control__input {
  width: 100%;
}

.z-form-control__validation {
  display: inline-flex;
  align-items: center;
  gap: 6px;
  margin: 0;
  font-size: 14px;
  line-height: 1.4;
  color: var(--fgColor-danger, #d1242f);
}

.z-form-control__validation-icon {
  width: 16px;
  height: 16px;
  flex: 0 0 16px;
}

.z-form-control__caption {
  margin: 0;
  font-size: 14px;
  line-height: 1.4;
  color: var(--fgColor-muted, #656d76);
}

.z-form-control.is-invalid :deep(input),
.z-form-control.is-invalid :deep(textarea),
.z-form-control.is-invalid :deep(select),
.z-form-control.is-invalid :deep(.z-input) {
  border-color: var(--borderColor-danger-emphasis, #cf222e);
}
</style>
