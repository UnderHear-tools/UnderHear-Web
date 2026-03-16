import { computed, reactive, ref } from 'vue'

import type {
  CreateApplicationRequest
} from '@/modules/application/api/create-new'

export type FrameworkValue = 'html' | 'vue' | 'react'

export interface AppFormErrors {
  appName: string
  englishName: string
  appDescription: string
}

function createEmptyFormErrors(): AppFormErrors {
  return {
    appName: '',
    englishName: '',
    appDescription: ''
  }
}

export function useCreateApplicationForm() {
  const selectedFramework = ref<FrameworkValue | null>(null)
  const file = ref<File | null>(null)
  const htmlSource = ref('<div>hello, world!</div>')

  const appName = ref('')
  const englishName = ref('')
  const visibility = ref('公开的')
  const appDescription = ref('')

  const submitAttempted = ref(false)
  const touched = reactive({
    file: false,
    htmlSource: false,
    appName: false,
    englishName: false,
    appDescription: false
  })

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

  const formErrors = computed<AppFormErrors>(() => {
    const errors = createEmptyFormErrors()
    const trimmedEnglishName = englishName.value.trim()

    if (!appName.value.trim()) {
      errors.appName = '请输入应用名称。'
    } else if (appName.value.length > 100) {
      errors.appName = '应用名称不能超过 100 个字符。'
    }

    if (!trimmedEnglishName) {
      errors.englishName = '请输入英文名称。'
    } else if (!/^[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$/.test(englishName.value)) {
      errors.englishName = '仅支持小写字母、数字和连字符（-），且不能以连字符开头或结尾。'
    } else if (trimmedEnglishName.length < 4) {
      errors.englishName = '英文名称至少 4 个字符。'
    } else if (englishName.value.length > 63) {
      errors.englishName = '英文名称不能超过 63 个字符。'
    }

    if (!appDescription.value.trim()) {
      errors.appDescription = '请输入应用描述。'
    } else if (appDescription.value.length > 1000) {
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

    return selectedFramework.value === 'html' ? touched.htmlSource : touched.file
  })

  const isFormValid = computed(() => {
    return !frameworkError.value
      && !uploadError.value
      && !formErrors.value.appName
      && !formErrors.value.englishName
      && !formErrors.value.appDescription
  })

  function setFramework(value: FrameworkValue) {
    selectedFramework.value = value

    if (value === 'html') {
      file.value = null
      touched.file = false
      return
    }

    htmlSource.value = ''
    touched.htmlSource = false
  }

  function setFile(value: File | null) {
    touched.file = true
    file.value = value
  }

  function setHtmlSource(value: string) {
    touched.htmlSource = true
    htmlSource.value = value
  }

  function setAppName(value: string) {
    touched.appName = true
    appName.value = value
  }

  function setEnglishName(value: string) {
    touched.englishName = true
    englishName.value = value
  }

  function setVisibility(value: string) {
    visibility.value = value
  }

  function setAppDescription(value: string) {
    touched.appDescription = true
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
      englishName: englishName.value,
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
    englishName,
    visibility,
    appDescription,
    frameworkError,
    uploadError,
    displayedFormErrors,
    showFrameworkError,
    showUploadError,
    setFramework,
    setFile,
    setHtmlSource,
    setAppName,
    setEnglishName,
    setVisibility,
    setAppDescription,
    prepareSubmit,
    buildRequest
  }
}
