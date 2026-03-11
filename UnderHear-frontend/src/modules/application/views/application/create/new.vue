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
            :invalid="showStepOneError"
            :validation="stepOneError"
            @update:selected-framework="updateSelectedFramework"
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
            :file="file"
            :html-source="htmlSource"
            :selected-framework="selectedFramework"
            :invalid="showStepTwoError"
            :validation="stepTwoError"
            @update:file="updateFile"
            @update:html-source="updateHtmlSource"
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
            :form-data="formData"
            :errors="displayedFormErrors"
            @update:form-data="updateFormData"
          />
        </Timeline.Body>
      </Timeline.Item>
    </Timeline>
    <div class="mt-6 flex justify-end">
      <button
        type="button"
        class="inline-flex items-center justify-center rounded-md bg-blue-600 px-4 py-2 text-sm font-medium text-white shadow-sm transition-colors hover:bg-blue-700 focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-blue-600"
        @click="submit"
      >
        创建应用
      </button>
    </div>
  </zContainer>
</template>

<script setup lang="ts">
import { computed, reactive, ref } from 'vue'

import { zContainer } from '@/components/z-ui/container'
import { Timeline } from '@/components/z-ui/timeline'
import FirstStep from '@/modules/application/components/newForm/FirstStep.vue'
import SecondStep from '@/modules/application/components/newForm/SecondStep.vue'
import ThirdStep from '@/modules/application/components/newForm/ThirdStep.vue'

type FrameworkType = 'html' | 'vue' | 'react'

interface AppFormData {
  appName: string
  englishName: string
  appDescription: string
}

interface AppFormErrors {
  appName: string
  englishName: string
  appDescription: string
}

const selectedFramework = ref<FrameworkType | null>(null)

const file = ref<File | null>(null)
const htmlSource = ref(`<div>hello, world!</div>`)

const formData = ref<AppFormData>({
  appName: '',
  englishName: '',
  appDescription: ''
})

const submitAttempted = ref(false)
const touched = reactive({
  file: false,
  htmlSource: false,
  appName: false,
  englishName: false,
  appDescription: false
})

function createEmptyFormErrors(): AppFormErrors {
  return {
    appName: '',
    englishName: '',
    appDescription: ''
  }
}

const stepOneError = computed(() => {
  return selectedFramework.value === null ? '请选择一个前端框架。' : ''
})

const stepTwoError = computed(() => {
  if (selectedFramework.value === null) {
    return ''
  }

  if (selectedFramework.value === 'html') {
    return htmlSource.value.trim() ? '' : '请输入 HTML 代码。'
  }

  return file.value ? '' : '请上传文件。'
})

const formErrors = computed<AppFormErrors>(() => {
  const errors = createEmptyFormErrors()

  if (!formData.value.appName.trim()) {
    errors.appName = '请输入应用名称。'
  } else if (formData.value.appName.length > 100) {
    errors.appName = '应用名称不能超过 100 个字符。'
  }

  const trimmedEnglishName = formData.value.englishName.trim()

  if (!trimmedEnglishName) {
    errors.englishName = '请输入英文名称。'
  } else if (!/^[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$/.test(formData.value.englishName)) {
    errors.englishName = '仅支持小写字母、数字和连字符（-），且不能以连字符开头或结尾。'
  } else if (trimmedEnglishName.length < 4) {
    errors.englishName = '英文名称至少 4 个字符。'
  } else if (formData.value.englishName.length > 63) {
    errors.englishName = '英文名称不能超过 63 个字符。'
  }

  if (!formData.value.appDescription.trim()) {
    errors.appDescription = '请输入应用描述。'
  } else if (formData.value.appDescription.length > 1000) {
    errors.appDescription = '应用描述不能超过 1000 个字符。'
  }

  return errors
})

const displayedFormErrors = computed<AppFormErrors>(() => {
  const errors = createEmptyFormErrors()

  if (submitAttempted.value || touched.appName) {
    errors.appName = formErrors.value.appName
  }

  if (submitAttempted.value || touched.englishName) {
    errors.englishName = formErrors.value.englishName
  }

  if (submitAttempted.value || touched.appDescription) {
    errors.appDescription = formErrors.value.appDescription
  }

  return errors
})

const showStepOneError = computed(() => {
  return submitAttempted.value && Boolean(stepOneError.value)
})

const showStepTwoError = computed(() => {
  if (!stepTwoError.value) {
    return false
  }

  if (submitAttempted.value) {
    return true
  }

  if (selectedFramework.value === 'html') {
    return touched.htmlSource
  }

  if (selectedFramework.value === 'vue' || selectedFramework.value === 'react') {
    return touched.file
  }

  return false
})

const isFormValid = computed(() => {
  return !stepOneError.value
    && !stepTwoError.value
    && !formErrors.value.appName
    && !formErrors.value.englishName
    && !formErrors.value.appDescription
})

function updateSelectedFramework(value: FrameworkType) {
  selectedFramework.value = value
}

function updateFile(value: File | null) {
  touched.file = true
  file.value = value
}

function updateHtmlSource(value: string) {
  touched.htmlSource = true
  htmlSource.value = value
}

function updateFormData(value: AppFormData) {
  if (value.appName !== formData.value.appName) {
    touched.appName = true
  }

  if (value.englishName !== formData.value.englishName) {
    touched.englishName = true
  }

  if (value.appDescription !== formData.value.appDescription) {
    touched.appDescription = true
  }

  formData.value = value
}

function submit() {
  submitAttempted.value = true

  if (!isFormValid.value) {
    return
  }

  console.log('Selected Framework:', selectedFramework.value)
  console.log('Uploaded File:', file.value)
  console.log('HTML Source:', new File([htmlSource.value], 'index.html', { type: 'text/html;charset=utf-8' }))
  console.log('Form Data:', formData.value)
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
