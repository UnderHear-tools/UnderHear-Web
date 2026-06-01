import { computed, ref } from 'vue'

import type {
  CreateApplicationRequest
} from '@/modules/application/api/create-new'

export type FrameworkValue = 'html' | 'vue' | 'react'

export function useCreateApplicationForm() {
  const selectedFramework = ref<FrameworkValue | null>(null)
  const files = ref<File[]>([])
  const htmlSource = ref('<div>hello, world!</div>')

  const appName = ref('')
  const appUrlPrefix = ref('')
  const visibility = ref('公开的')
  const appDescription = ref('')

  const submitAttempted = ref(false)
  const touchedFile = ref(false)
  const touchedHtmlSource = ref(false)
  const touchedAppName = ref(false)
  const touchedAppUrlPrefix = ref(false)
  const touchedAppDescription = ref(false)

  const frameworkError = computed(() => {
    return selectedFramework.value === null ? '请选择一个前端框架。' : ''
  })

  function isZipFile(file: File | undefined) {
    return Boolean(file?.name.toLowerCase().endsWith('.zip'))
  }

  const uploadError = computed(() => {
    if (selectedFramework.value === null) {
      return ''
    }

    if (selectedFramework.value !== 'html') {
      const selectedFile = files.value[0]
      if (!selectedFile) {
        return '请上传文件。'
      }

      if (!isZipFile(selectedFile)) {
        return 'Vue 和 React 仅支持上传 .zip 格式的 dist 构建包。'
      }
    }

    if (selectedFramework.value === 'html') {
      return htmlSource.value.trim() ? '' : '请输入 HTML 代码。'
    }

    return files.value.length > 0 ? '' : '请上传文件。'
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

  const appUrlPrefixError = computed(() => {
    const trimmedAppUrlPrefix = appUrlPrefix.value.trim()

    if (!trimmedAppUrlPrefix) {
      return '请输入应用地址前缀。'
    }

    if (!/^[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$/.test(appUrlPrefix.value)) {
      return '仅支持小写字母、数字和连字符（-），且不能以连字符开头或结尾。'
    }

    if (appUrlPrefix.value.length > 63) {
      return '应用地址前缀不能超过 63 个字符。'
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

  const displayedAppUrlPrefixError = computed(() => {
    return submitAttempted.value || touchedAppUrlPrefix.value ? appUrlPrefixError.value : ''
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
      && !appUrlPrefixError.value
      && !appDescriptionError.value
  })

  function setFramework(value: FrameworkValue) {
    selectedFramework.value = value

    if (value === 'html') {
      files.value = []
      touchedFile.value = false
      return
    }

    htmlSource.value = ''
    touchedHtmlSource.value = false
  }

  function setFiles(value: File[]) {
    touchedFile.value = true
    files.value = value
  }

  function setHtmlSource(value: string) {
    touchedHtmlSource.value = true
    htmlSource.value = value
  }

  function setAppName(value: string) {
    touchedAppName.value = true
    appName.value = value
  }

  function setAppUrlPrefix(value: string) {
    touchedAppUrlPrefix.value = true
    appUrlPrefix.value = value
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
    const appFile = framework === 'html'
      ? new File([htmlSource.value], 'index.html', { type: 'text/html;charset=utf-8' })
      : (files.value[0] as File)

    return {
      framework,
      appFile,
      appName: appName.value,
      appUrl: `https://${appUrlPrefix.value}.onlikee.cn/`,
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
    files,
    htmlSource,
    appName,
    appUrlPrefix,
    visibility,
    appDescription,
    frameworkError,
    uploadError,
    displayedAppNameError,
    displayedAppUrlPrefixError,
    displayedAppDescriptionError,
    showFrameworkError,
    showUploadError,
    setFramework,
    setFiles,
    setHtmlSource,
    setAppName,
    setAppUrlPrefix,
    setVisibility,
    setAppDescription,
    prepareSubmit,
    buildRequest
  }
}
