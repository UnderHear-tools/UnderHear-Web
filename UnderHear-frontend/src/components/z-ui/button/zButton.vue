<template>
  <button
    class="z-button"
    :type="type"
    :disabled="disabled || loading"
    :aria-busy="loading ? 'true' : undefined"
    :data-loading="loading"
    :data-size="size"
    :data-variant="variant"
    :data-icon-button="!$slots.default ? 'true' : 'false'"
  >
    <span
      class="z-button__content"
    >
      <span class="z-button__content-row">
        <span
          v-if="$slots.leadingVisual"
          class="z-button__visual"
        >
          <slot name="leadingVisual" />
        </span>
        <span
          v-if="$slots.default"
          class="z-button__label"
        >
          <slot />
        </span>
        <span
          v-if="$slots.trailingVisual"
          class="z-button__visual"
        >
          <slot name="trailingVisual" />
        </span>
      </span>
      <svg
        v-if="loading"
        aria-hidden="true"
        focusable="false"
        width="16"
        height="16"
        viewBox="0 0 16 16"
        fill="currentColor"
        class="z-button__spinner"
      >
        <circle
          cx="8"
          cy="8"
          r="7"
          fill="none"
          stroke="currentColor"
          stroke-opacity="0.25"
          stroke-width="2"
          vector-effect="non-scaling-stroke"
        />
        <path
          d="M15 8a7.002 7.002 0 0 0-7-7"
          fill="none"
          stroke="currentColor"
          stroke-linecap="round"
          stroke-width="2"
          vector-effect="non-scaling-stroke"
        />
      </svg>
    </span>
  </button>
</template>

<script setup lang="ts">
interface Props {
  type?: 'button' | 'submit' | 'reset'
  variant?: 'default' | 'primary' | 'invisible' | 'danger' | 'link'
  size?: 'small' | 'medium' | 'large'
  loading?: boolean
  disabled?: boolean
}

withDefaults(defineProps<Props>(), {
  type: 'button',
  variant: 'default',
  size: 'medium',
  loading: false,
  disabled: false
})
</script>

<style scoped>
.z-button {
  align-items: center;
  appearance: none;
  background-color: transparent;
  border: var(--borderWidth-thin, 0.0625rem) solid transparent;
  border-radius: var(--borderRadius-medium, 0.375rem);
  color: var(--button-default-fgColor-rest, var(--control-fgColor-rest, #1f2328));
  cursor: pointer;
  display: inline-flex;
  font-family: inherit;
  font-size: var(--text-body-size-medium, 0.875rem);
  font-weight: var(--base-text-weight-medium, 500);
  height: var(--control-medium-size, 2rem);
  min-width: max-content;
  padding: 0 var(--control-medium-paddingInline-normal, 0.75rem);
  text-align: center;
  text-decoration: none;
  transition: 80ms cubic-bezier(0.65, 0, 0.35, 1);
  transition-property: color, background-color, border-color, box-shadow;
  user-select: none;
  vertical-align: middle;
}

.z-button:focus-visible {
  box-shadow: none;
  outline: 2px solid var(--focus-outlineColor, #0969da);
  outline-offset: -2px;
}

.z-button:disabled {
  box-shadow: none;
  cursor: not-allowed;
}

.z-button__content {
  display: grid;
  flex: 1 0 auto;
  grid-template-areas: 'stack';
  place-items: center;
  width: 100%;
  justify-content: center;
}

.z-button__content-row,
.z-button__spinner {
  grid-area: stack;
}

.z-button__content-row {
  align-items: center;
  display: inline-flex;
  gap: 0.5rem;
  max-width: 100%;
}

.z-button__label {
  line-height: var(--text-body-lineHeight-medium, 1.5);
  white-space: nowrap;
}

.z-button__visual {
  align-items: center;
  display: inline-flex;
  flex-shrink: 0;
}

.z-button__spinner {
  animation: z-button-spin 1s linear infinite;
  display: block;
}

.z-button[data-loading='true'] .z-button__content-row {
  visibility: hidden;
}

.z-button[data-icon-button='true'] {
  justify-content: center;
  min-width: unset;
  padding: unset;
  width: var(--control-medium-size, 2rem);
}

.z-button[data-icon-button='true'][data-size='small'] {
  width: var(--control-small-size, 1.75rem);
}

.z-button[data-icon-button='true'][data-size='large'] {
  width: var(--control-large-size, 2.5rem);
}

.z-button[data-size='small'] {
  font-size: var(--text-body-size-small, 0.75rem);
  height: var(--control-small-size, 1.75rem);
  padding: 0 var(--control-small-paddingInline-condensed, 0.5rem);
}

.z-button[data-size='small'] .z-button__label {
  line-height: var(--text-body-lineHeight-small, 1.625);
}

.z-button[data-size='large'] {
  height: var(--control-large-size, 2.5rem);
  padding: 0 var(--control-large-paddingInline-spacious, 1rem);
}

.z-button[data-variant='default'] {
  background-color: var(--button-default-bgColor-rest, var(--control-bgColor-rest, #f6f8fa));
  border-color: var(--button-default-borderColor-rest, var(--control-borderColor-rest, #d0d7de));
  box-shadow: var(--button-default-shadow-resting, 0 1px 0 0 #1f23280a);
  color: var(--button-default-fgColor-rest, var(--control-fgColor-rest, #1f2328));
}

.z-button[data-variant='default']:hover:not(:disabled) {
  background-color: var(--button-default-bgColor-hover, var(--control-bgColor-hover, #f3f4f6));
  border-color: var(--button-default-borderColor-hover, var(--button-default-borderColor-rest, #d0d7de));
}

.z-button[data-variant='default']:active:not(:disabled) {
  background-color: var(--button-default-bgColor-active, var(--control-bgColor-active, #ebecf0));
  border-color: var(--button-default-borderColor-active, var(--button-default-borderColor-rest, #d0d7de));
}

.z-button[data-variant='default']:disabled {
  background-color: var(--button-default-bgColor-disabled, var(--control-bgColor-disabled, #f6f8fa));
  border-color: var(--button-default-borderColor-disabled, var(--control-borderColor-disabled, #d0d7de));
  color: var(--control-fgColor-disabled, #8c959f);
}

.z-button[data-variant='primary'] {
  background-color: var(--button-primary-bgColor-rest, var(--bgColor-success-emphasis, #1f883d));
  border-color: var(--button-primary-borderColor-rest, var(--borderColor-translucent, #1f232826));
  box-shadow: var(--shadow-resting-small, var(--button-default-shadow-resting, 0 1px 0 0 #1f23280a));
  color: var(--button-primary-fgColor-rest, var(--fgColor-white, #ffffff));
}

.z-button[data-variant='primary']:hover:not(:disabled) {
  background-color: var(--button-primary-bgColor-hover, #1c8139);
  border-color: var(--button-primary-borderColor-hover, var(--button-primary-borderColor-rest, #1f232826));
}

.z-button[data-variant='primary']:active:not(:disabled) {
  background-color: var(--button-primary-bgColor-active, #197935);
  border-color: var(--button-primary-borderColor-active, var(--button-primary-borderColor-rest, #1f232826));
  box-shadow: var(--button-primary-shadow-selected, var(--shadow-resting-small, 0 1px 0 0 #1f23280a));
}

.z-button[data-variant='primary']:focus-visible {
  box-shadow: inset 0 0 0 3px var(--fgColor-onEmphasis, #ffffff);
}

.z-button[data-variant='primary']:disabled {
  background-color: var(--button-primary-bgColor-disabled, #95d8a6);
  border-color: var(--button-primary-borderColor-disabled, var(--button-primary-bgColor-disabled, #95d8a6));
  color: var(--button-primary-fgColor-disabled, rgba(255, 255, 255, 0.8));
}

.z-button[data-variant='danger'] {
  background-color: var(--button-danger-bgColor-rest, var(--control-bgColor-rest, #f6f8fa));
  border-color: var(--button-danger-borderColor-rest, var(--control-borderColor-rest, #d0d7de));
  box-shadow: var(--button-default-shadow-resting, 0 1px 0 0 #1f23280a);
  color: var(--button-danger-fgColor-rest, var(--fgColor-danger, #cf222e));
}

.z-button[data-variant='danger']:hover:not(:disabled) {
  background-color: var(--button-danger-bgColor-hover, var(--bgColor-danger-emphasis, #cf222e));
  border-color: var(--button-danger-borderColor-hover, var(--button-primary-borderColor-rest, #1f232826));
  color: var(--button-danger-fgColor-hover, #ffffff);
}

.z-button[data-variant='danger']:active:not(:disabled) {
  background-color: var(--button-danger-bgColor-active, var(--bgColor-danger-emphasis, #cf222e));
  border-color: var(--button-danger-borderColor-active, var(--button-danger-borderColor-hover, #1f232826));
  box-shadow: var(--button-danger-shadow-selected, var(--shadow-resting-small, 0 1px 0 0 #1f23280a));
  color: var(--button-danger-fgColor-active, #ffffff);
}

.z-button[data-variant='danger']:disabled {
  background-color: var(--button-danger-bgColor-disabled, var(--control-bgColor-disabled, #f6f8fa));
  border-color: var(--button-default-borderColor-disabled, var(--control-borderColor-disabled, #d0d7de));
  color: var(--button-danger-fgColor-disabled, rgba(207, 34, 46, 0.5));
}

.z-button[data-variant='invisible'] {
  background-color: var(--button-invisible-bgColor-rest, transparent);
  border-color: var(--button-invisible-borderColor-rest, transparent);
  box-shadow: none;
  color: var(--button-invisible-fgColor-rest, var(--control-fgColor-rest, #1f2328));
}

.z-button[data-variant='invisible']:hover:not(:disabled) {
  background-color: var(--button-invisible-bgColor-hover, var(--control-transparent-bgColor-hover, rgba(129, 139, 152, 0.1)));
  border-color: var(--button-invisible-borderColor-hover, transparent);
  color: var(--button-invisible-fgColor-hover, var(--control-fgColor-rest, #1f2328));
}

.z-button[data-variant='invisible']:active:not(:disabled) {
  background-color: var(--button-invisible-bgColor-active, var(--control-transparent-bgColor-active, rgba(129, 139, 152, 0.16)));
}

.z-button[data-variant='invisible']:disabled {
  background-color: var(--button-invisible-bgColor-disabled, transparent);
  border-color: var(--button-invisible-borderColor-disabled, transparent);
  color: var(--button-invisible-fgColor-disabled, var(--control-fgColor-disabled, #8c959f));
}

.z-button[data-variant='link'] {
  background-color: transparent;
  border-color: transparent;
  border-radius: 0;
  box-shadow: none;
  color: var(--fgColor-link, #0969da);
  font-size: inherit;
  height: auto;
  min-width: fit-content;
  padding: 0;
  text-align: left;
}

.z-button[data-variant='link']:hover:not(:disabled) {
  text-decoration: underline;
}

.z-button[data-variant='link']:active:not(:disabled) {
  text-decoration: underline;
}

.z-button[data-variant='link']:focus-visible {
  outline-offset: 2px;
}

.z-button[data-variant='link']:disabled {
  background-color: transparent;
  border-color: transparent;
  color: var(--control-fgColor-disabled, #8c959f);
}

@keyframes z-button-spin {
  to {
    transform: rotate(360deg);
  }
}
</style>
