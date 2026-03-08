<template>
  <div class="first-step">
    <div
      class="options-grid"
      :class="{ 'options-grid--invalid': invalid }"
    >
      <button
        type="button"
        class="option-card"
        :class="{ 'option-card--active': selectedFramework === 'html' }"
        :aria-pressed="selectedFramework === 'html'"
        @click="selectFramework('html')"
      >
        <div class="option-header">
          <div class="option-title">
            HTML<zTag>推荐</zTag>
          </div>
          <p class="option-desc">
            从一段HTML代码开始！
          </p>
        </div>
        <img
          class="create-image"
          src="@/modules/application/assets/new-html.svg"
          alt="Create Collect"
        >
      </button>

      <button
        type="button"
        class="option-card"
        :class="{ 'option-card--active': selectedFramework === 'vue' }"
        :aria-pressed="selectedFramework === 'vue'"
        @click="selectFramework('vue')"
      >
        <div class="option-header">
          <div class="option-title">
            Vue
          </div>
          <p class="option-desc">
            从一个Vue项目开始！
          </p>
        </div>
        <img
          class="create-image"
          src="@/modules/application/assets/new-vue.svg"
          alt="Create New"
        >
      </button>

      <button
        type="button"
        class="option-card"
        :class="{ 'option-card--active': selectedFramework === 'react' }"
        :aria-pressed="selectedFramework === 'react'"
        @click="selectFramework('react')"
      >
        <div class="option-header">
          <div class="option-title">
            React
          </div>
          <p class="option-desc">
            从一个React项目开始！
          </p>
        </div>
        <img
          class="create-image"
          src="@/modules/application/assets/new-react.svg"
          alt="Create Website"
        >
      </button>
    </div>
    <p
      v-if="invalid && validation"
      class="step-validation"
      role="alert"
    >
      <svg
        aria-hidden="true"
        focusable="false"
        width="12"
        height="12"
        viewBox="0 0 16 16"
        fill="currentColor"
        class="icon-svg"
      ><path d="M6.457 1.047c.659-1.234 2.427-1.234 3.086 0l6.082 11.378A1.75 1.75 0 0 1 14.082 15H1.918a1.75 1.75 0 0 1-1.543-2.575ZM8 5a.75.75 0 0 0-.75.75v2.5a.75.75 0 0 0 1.5 0v-2.5A.75.75 0 0 0 8 5Zm1 6a1 1 0 1 0-2 0 1 1 0 0 0 2 0Z" /></svg>
      {{ validation }}
    </p>
  </div>
</template>

<script setup lang="ts">
import { zTag } from '@/components/z-ui/tag'

type FrameworkType = 'html' | 'vue' | 'react'

interface Props {
  selectedFramework: FrameworkType | null
  invalid?: boolean
  validation?: string
}

withDefaults(defineProps<Props>(), {
  invalid: false,
  validation: ''
})

const emit = defineEmits<{
  'update:selectedFramework': [value: FrameworkType]
}>()

function selectFramework(framework: FrameworkType) {
  emit('update:selectedFramework', framework)
}
</script>

<style scoped>
.first-step {
  display: grid;
  gap: 8px;
}

.options-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 1.25rem;
}

.options-grid--invalid .option-card {
  border-color: var(--borderColor-danger-emphasis, #cf222e);
}

.option-card {
  appearance: none;
  width: 100%;
  text-align: left;
  border: 1px solid var(--borderColor-default);
  border-radius: 6px;
  background: var(--bgColor-muted);
  padding: 1rem;
  cursor: pointer;
}

.option-card:hover,
.option-card--active {
  outline: 2px solid var(--borderColor-accent-emphasis);
  outline-offset: -1px;
  box-shadow: 0 4px 14px -4px color-mix(in srgb, var(--fgColor-default) 8%, var(--bgColor-transparent));
}

.option-title {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  color: var(--fgColor-default);
  font-size: 1rem;
  font-weight: 700;
}

.option-desc {
  margin: 0;
  color: var(--fgColor-muted);
  font-size: 14px;
  line-height: 1.6;
}

.create-image {
  width: 50%;
  height: auto;
  margin-top: 0.5rem;
}

.step-validation {
  display: inline-flex;
  align-items: center;
  gap: 3px;
  margin: 0;
  font-size: 12px;
  line-height: 1.4;
  font-weight: 600;
  color: var(--fgColor-danger, #d1242f);
}

@media (max-width: 768px) {
  .options-grid {
    grid-template-columns: 1fr;
  }

  .option-card {
    display: flex;
  }

  .option-header {
    width: 60%;
  }

  .option-title {
    margin-bottom: 0.6rem;
  }

  .create-image {
    width: 30%;
    margin-top: 0;
  }
}
</style>
