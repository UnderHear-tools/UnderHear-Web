<template>
  <zContainer>
    <div class="create-app">
      <zSteps
        v-model="current"
        :steps="steps"
        class="steps"
      >
        <template #icon-0>
          <Stack />
        </template>
        <template #icon-1>
          <Package />
        </template>
        <template #icon-2>
          <IdBadge />
        </template>
        <template #icon-3>
          <Rocket />
        </template>
      </zSteps>

      <div class="step-body">
        <!-- 步骤1：选择框架 -->
        <div
          v-if="current === 0"
          class="step-content"
        >
          <div class="options-grid">
            <button
              type="button"
              class="option-card"
            >
              <div class="option-header">
                <h2 class="option-title">
                  HTML<zTag>推荐</zTag>
                </h2>
                <p class="option-desc">
                  从一段HTML代码开始！非常快速！
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
            >
              <div class="option-header">
                <h2 class="option-title">
                  Vue
                </h2>
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
            >
              <div class="option-header">
                <h2 class="option-title">
                  React
                </h2>
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

        <!-- 步骤2：上传应用 -->
        <div
          v-if="current === 1"
          class="step-content"
        >
          <zUpload
            v-model="file"
            accept=".zip,.html"
            hint="支持 .zip 格式的 dist 构建包或 .html 文件"
          />
        </div>

        <!-- 步骤3：基本资料 -->
        <div
          v-if="current === 2"
          class="step-content"
        />

        <!-- 步骤4：完成 -->
        <div
          v-if="current === 3"
          class="step-content"
        />
      </div>

      <div class="step-actions">
        <button
          v-if="current > 0"
          @click="current--"
        >
          上一步
        </button>
        <button
          v-if="current < steps.length - 1"
          @click="current++"
        >
          下一步
        </button>
        <button
          v-if="current === steps.length - 1"
          @click="submit"
        >
          提交
        </button>
      </div>
    </div>
  </zContainer>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { zSteps, type StepItem } from '@/components/z-ui/steps'
import { zContainer } from '@/components/z-ui/container'
import { zTag } from '@/components/z-ui/tag'
import { Stack, Package, IdBadge, Rocket } from '@/components/z-ui/icon/Octicons-vue'
import { zUpload } from '@/components/z-ui/upload'

const steps: StepItem[] = [
  { title: '选择框架' },
  { title: '上传应用' },
  { title: '应用信息' },
  { title: '完成!' }
] 

const current = ref(0)
const file = ref<File | null>(null)

const submit = () => {
	console.log('提交', file.value)
}
</script>

<style scoped>
.create-app {
	display: flex;
	flex-direction: column;
	gap: 64px;
}

.steps{
	margin-top: 32px;
}

.step-body {
	min-height: 200px;
}

.step-content {
	display: flex;
	flex-direction: column;
	gap: 12px;
}

.step-actions {
	display: flex;
	gap: 8px;
	justify-content: flex-end;
}

.step-actions button {
	padding: 8px 20px;
	border: none;
	border-radius: 6px;
	cursor: pointer;
	font-size: 14px;
	background: var(--bgColor-accent-emphasis, #0969da);
	color: var(--fgColor-onEmphasis, #fff);
}

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
	padding: 1.5rem;
	cursor: pointer;
}

.option-card:hover {
	border-color: var(--borderColor-accent-emphasis);
	box-shadow: 0 4px 14px -4px color-mix(in srgb, var(--fgColor-default) 8%, var(--bgColor-transparent));
}

.option-title {
	margin: 0 0 0.6rem 0;
	font-size: 1.15rem;
	font-weight: 700;
	color: var(--fgColor-default);
	display: flex;
	align-items: center;
	gap: 0.5rem;
}

.option-desc {
	margin: 0;
	font-size: 0.95rem;
	line-height: 1.6;
	color: var(--fgColor-muted);
}

.create-image {
	margin-top: 2rem;
	width: 70%;
	height: auto;
	margin: 0 auto;
}

@media (max-width: 768px) {
	.steps {
		margin-bottom: 12px;
	}
	.options-grid {
		grid-template-columns: 1fr;
	}
	.option-card {
		display: flex;
	}
	.option-header {
		width: 60%;
	}
	.create-image {
		width: 40%;
	}
}
</style>