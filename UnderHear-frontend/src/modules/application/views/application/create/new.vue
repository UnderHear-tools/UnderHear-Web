<template>
  <zContainer
    style="max-width: 768px;"
  >
    <div class="ad-placeholder">
      <img
        src="@/modules/application/assets/ad.png"
        class="ad-image"
        alt="广告位图片"
      >
    </div>
    <div class="page-header">
      <div class="page-title">
        创建一个新的应用
      </div>
      <span class="page-description">
        请确保代码能够正确运行！
      </span>
    </div>

    <Timeline clip-sidebar>
      <Timeline.Item>
        <Timeline.Badge>1</Timeline.Badge>
        <Timeline.Body>
          <div class="creatForm-heading">
            点击选择你使用的前端框架
          </div>
          <div class="options-grid">
            <button
              type="button"
              class="option-card"
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
        </Timeline.Body>
      </Timeline.Item>
      <Timeline.Item>
        <Timeline.Badge>2</Timeline.Badge>
        <Timeline.Body>
          <div class="creatForm-heading">
            上传应用
          </div>
          <zUpload
            v-model="file"
            accept=".zip,.html"
            hint="支持 .zip 格式的 dist 构建包或 .html 文件"
          />
        </Timeline.Body>
      </Timeline.Item>
      <Timeline.Item>
        <Timeline.Badge>3</Timeline.Badge>
        <Timeline.Body>
          <div class="creatForm-heading">
            填写基本信息
          </div>
          <CreateAppInfoForm
            v-model:form-data="formData"
          />
        </Timeline.Body>
      </Timeline.Item>
    </Timeline>
    <div class="mt-6 flex justify-end">
      <button
        type="button"
        class="inline-flex items-center justify-center rounded-md bg-blue-600 px-4 py-2 text-sm font-medium text-white shadow-sm transition-colors hover:bg-blue-700 focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-blue-600"
      >
        创建应用
      </button>
    </div>
  </zContainer>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { zContainer } from '@/components/z-ui/container'
import { Timeline } from '@/components/z-ui/timeline'
import { zTag } from '@/components/z-ui/tag'
import { zUpload } from '@/components/z-ui/upload'

const file = ref<File | null>(null)

import CreateAppInfoForm from '@/modules/application/components/CreateAppInfoForm.vue'

const formData = ref({
	appName: '',
	displayName: '',
	displayDescription: ''
})
</script>

<style scoped>
.ad-placeholder {
  position: relative;
  overflow: hidden;
  margin-bottom: 2rem;
  border-radius: 12px;
}

.ad-image {
  display: block;
  width: 100%;
  height: auto;
}

.ad-placeholder::after {
  position: absolute;
  right: 1rem;
  bottom: 2rem;
  padding: 0.4rem 0.5rem;
  border-radius: 999px;
  background: color-mix(in srgb, var(--fgColor-default) 50%, transparent);
  color: var(--bgColor-default);
  font-size: 12px;
  line-height: 1;
  font-weight: 600;
  box-shadow: 0 8px 24px -12px color-mix(in srgb, var(--fgColor-default) 48%, transparent);
  content: "展示广告位，联系2533643340@qq.com";
}

.page-header {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
  margin-left: 2rem;
  margin-bottom: 2rem;
}

.page-title {
  color: var(--fgColor-default);
  font-size: 20px;
  font-weight: 600;
  line-height: 1.25;
}

.page-description {
  color: var(--fgColor-muted);
  display: block;
  font-size: 14px;
  line-height: 1.6;
}

.creatForm-heading {
  line-height: 24px;
  font-size: 1rem;
  font-weight: 600;
  color: var(--fgColor-default);
  margin-bottom: 1rem;
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
  padding: 1rem;
  cursor: pointer;
}

.option-card:hover {
	outline: 2px solid var(--borderColor-accent-emphasis);
	outline-offset: -1px;
  box-shadow: 0 4px 14px -4px color-mix(in srgb, var(--fgColor-default) 8%, var(--bgColor-transparent));
}

.option-title {
  font-size: 1rem;
  font-weight: 700;
  color: var(--fgColor-default);
  display: flex;
  align-items: center;
  gap: 0.5rem;
}

.option-desc {
  margin: 0;
  font-size: 14px;
  line-height: 1.6;
  color: var(--fgColor-muted);
}

.create-image {
  width: 50%;
  height: auto;
  margin-top: 0.5rem;
}

@media (max-width: 768px) {
  .page-header {
    margin-top: 2rem;
  }

  .page-title {
    font-size: 1.5rem;
  }

  .page-description {
    font-size: 0.875rem;
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

  .option-title {
    margin-bottom: 0.6rem;
  }

  .create-image {
    width: 30%;
    margin-top: 0;
  }
}
</style>
