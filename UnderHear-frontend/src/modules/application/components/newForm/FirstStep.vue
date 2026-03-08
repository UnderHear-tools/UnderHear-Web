<template>
  <div>
    <div class="options-grid">
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
  </div>
</template>

<script setup lang="ts">
import { zTag } from '@/components/z-ui/tag'

type FrameworkType = 'html' | 'vue' | 'react'

interface Props {
  selectedFramework: FrameworkType | null
}

defineProps<Props>()

const emit = defineEmits<{
  'update:selectedFramework': [value: FrameworkType]
}>()

function selectFramework(framework: FrameworkType) {
  emit('update:selectedFramework', framework)
}
</script>

<style scoped>
.options-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 1.25rem;
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
