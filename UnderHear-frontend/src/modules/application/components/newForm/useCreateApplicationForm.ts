import { computed, ref } from 'vue'

import type {
  CreateApplicationRequest
} from '@/modules/application/api/create-new'

export type FrameworkValue = 'html' | 'vue' | 'react'

export function useCreateApplicationForm() {
  const selectedFramework = ref<FrameworkValue | null>(null)
  const file = ref<File | null>(null)
  const htmlSource = ref('<div>hello, world!</div>')

  const appName = ref('')
  const appEnglishName = ref('')
  const visibility = ref('公开的')
  const appDescription = ref('')

  const submitAttempted = ref(false)
  const touchedFile = ref(false)
  const touchedHtmlSource = ref(false)
  const touchedAppName = ref(false)
  const touchedAppEnglishName = ref(false)
  const touchedAppDescription = ref(false)

  const frameworkError = computed(() => {
    return selectedFramework.value === null ? '请选择一个前端框架。' : ''
  })

  const uploadError = computed(() => {
    if (selectedFramework.value === null) {
      return ''
    }

    if (selectedFramework.value === 'html') {
      return htmlSource.value.trim() ? '' : '请输入 HTML 代码。'
    }

    return file.value ? '' : '请上传文件。'
  })

  const appNameError = computed(() => {
    if (!appName.value.trim()) {
      return '请输入应用名称。'
    }

    if (appName.value.length > 100) {
      return '应用名称不能超过 100 个字符。'
    }

    return ''
  })

  const appEnglishNameError = computed(() => {
    const trimmedAppEnglishName = appEnglishName.value.trim()

    if (!trimmedAppEnglishName) {
      return '请输入英文名称。'
    }

    if (!/^[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$/.test(appEnglishName.value)) {
      return '仅支持小写字母、数字和连字符（-），且不能以连字符开头或结尾。'
    }

    if (trimmedAppEnglishName.length < 4) {
      return '英文名称至少 4 个字符。'
    }

    if (appEnglishName.value.length > 63) {
      return '英文名称不能超过 63 个字符。'
    }

    return ''
  })

  const appDescriptionError = computed(() => {
    if (!appDescription.value.trim()) {
      return '请输入应用描述。'
    }

    if (appDescription.value.length > 1000) {
      return '应用描述不能超过 1000 个字符。'
    }

    return ''
  })

  const displayedAppNameError = computed(() => {
    return submitAttempted.value || touchedAppName.value ? appNameError.value : ''
  })

  const displayedAppEnglishNameError = computed(() => {
    return submitAttempted.value || touchedAppEnglishName.value ? appEnglishNameError.value : ''
  })

  const displayedAppDescriptionError = computed(() => {
    return submitAttempted.value || touchedAppDescription.value ? appDescriptionError.value : ''
  })

  const showFrameworkError = computed(() => {
    return submitAttempted.value && Boolean(frameworkError.value)
  })

  const showUploadError = computed(() => {
    if (!uploadError.value) {
      return false
    }

    if (submitAttempted.value) {
      return true
    }

    return selectedFramework.value === 'html' ? touchedHtmlSource.value : touchedFile.value
  })

  const isFormValid = computed(() => {
    return !frameworkError.value
      && !uploadError.value
      && !appNameError.value
      && !appEnglishNameError.value
      && !appDescriptionError.value
  })

  function setFramework(value: FrameworkValue) {
    selectedFramework.value = value

    if (value === 'html') {
      file.value = null
      touchedFile.value = false
      return
    }

    htmlSource.value = ''
    touchedHtmlSource.value = false
  }

  function setFile(value: File | null) {
    touchedFile.value = true
    file.value = value
  }

  function setHtmlSource(value: string) {
    touchedHtmlSource.value = true
    htmlSource.value = value
  }

  function setAppName(value: string) {
    touchedAppName.value = true
    appName.value = value
  }

  function setAppEnglishName(value: string) {
    touchedAppEnglishName.value = true
    appEnglishName.value = value
  }

  function setVisibility(value: string) {
    visibility.value = value
  }

  function setAppDescription(value: string) {
    touchedAppDescription.value = true
    appDescription.value = value
  }

  function buildRequest(): CreateApplicationRequest {
    const framework = selectedFramework.value as FrameworkValue
    const uploadFile = framework === 'html'
      ? new File([htmlSource.value], 'index.html', { type: 'text/html;charset=utf-8' })
      : (file.value as File)

    return {
      framework,
      uploadFile,
      appName: appName.value,
      appEnglishName: appEnglishName.value,
      visibility: visibility.value,
      appDescription: appDescription.value
    }
  }

  function prepareSubmit() {
    submitAttempted.value = true
    return isFormValid.value
  }

  return {
    selectedFramework,
    file,
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
    setFile,
    setHtmlSource,
    setAppName,
    setAppEnglishName,
    setVisibility,
    setAppDescription,
    prepareSubmit,
    buildRequest
  }
}
