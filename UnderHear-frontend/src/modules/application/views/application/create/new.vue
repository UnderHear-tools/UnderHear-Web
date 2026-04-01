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
          <FirstStep
            :selected-framework="selectedFramework"
            :invalid="showFrameworkError"
            :validation="frameworkError"
            @update:selected-framework="setFramework"
          />
        </Timeline.Body>
      </Timeline.Item>
      <Timeline.Item>
        <Timeline.Badge>2</Timeline.Badge>
        <Timeline.Body>
          <div class="creatForm-heading">
            上传应用 & 代码
          </div>
          <SecondStep
            :files="files"
            :html-source="htmlSource"
            :selected-framework="selectedFramework"
            :invalid="showUploadError"
            :validation="uploadError"
            @update:files="setFiles"
            @update:html-source="setHtmlSource"
          />
        </Timeline.Body>
      </Timeline.Item>
      <Timeline.Item>
        <Timeline.Badge>3</Timeline.Badge>
        <Timeline.Body>
          <div class="creatForm-heading">
            填写基本信息
          </div>
          <ThirdStep
            :app-name="appName"
            :app-english-name="appEnglishName"
            :visibility="visibility"
            :app-description="appDescription"
            :app-name-error="displayedAppNameError"
            :app-english-name-error="displayedAppEnglishNameError"
            :app-description-error="displayedAppDescriptionError"
            @update:app-name="setAppName"
            @update:app-english-name="setAppEnglishName"
            @update:visibility="setVisibility"
            @update:app-description="setAppDescription"
          />
        </Timeline.Body>
      </Timeline.Item>
    </Timeline>
    <div class="mt-6 flex justify-end">
      <zButton
        variant="primary"
        @click="submit"
      >
        <template #leadingVisual>
          <Rocket />
        </template>
        创建应用
      </zButton>
    </div>
  </zContainer>
</template>

<script setup lang="ts">
import { zContainer } from '@/components/z-ui/container'
import { zButton } from '@/components/z-ui/button'
import { Timeline } from '@/components/z-ui/timeline'
import { applicationCreateNew } from '@/modules/application/api/create-new'
import FirstStep from '@/modules/application/components/newForm/FirstStep.vue'
import SecondStep from '@/modules/application/components/newForm/SecondStep.vue'
import ThirdStep from '@/modules/application/components/newForm/ThirdStep.vue'
import { useCreateApplicationForm } from '@/modules/application/components/newForm/useCreateApplicationForm'
import Rocket from '@/components/z-ui/icon/Octicons-vue/icons/rocket.vue'

const {
  selectedFramework,
  files,
  htmlSource,
  appName,
  appEnglishName,
  visibility,
  appDescription,
  frameworkError,
  uploadError,
  displayedAppNameError,
  displayedAppEnglishNameError,
  displayedAppDescriptionError,
  showFrameworkError,
  showUploadError,
  setFramework,
  setFiles,
  setHtmlSource,
  setAppName,
  setAppEnglishName,
  setVisibility,
  setAppDescription,
  prepareSubmit,
  buildRequest
} = useCreateApplicationForm()

async function submit() {
  if (!prepareSubmit()) {
    return
  }

  await applicationCreateNew(buildRequest())
}
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
}
</style>
