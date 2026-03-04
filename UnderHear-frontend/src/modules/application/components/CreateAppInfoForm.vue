<template>
  <form
    class="app-info-form"
    @submit.prevent
  >
    <zFormControl
      label="应用名称"
      html-for="create-app-name"
      required
      caption="用于区分不同应用，支持中英文、数字和下划线。"
    >
      <zInput
        id="create-app-name"
        :model-value="formData.appName"
        placeholder="请输入应用名称"
        @update:model-value="updateAppName"
      />
    </zFormControl>

    <zFormControl
      label="展示名称"
      html-for="create-display-name"
      required
      :invalid="hasInvalidDisplayNameChars"
      validation="展示名称不能包含特殊符号。"
      caption="该名称会展示在前台页面。"
    >
      <zInput
        id="create-display-name"
        :model-value="formData.displayName"
        placeholder="请输入展示名称"
        @update:model-value="updateDisplayName"
      />
    </zFormControl>
  </form>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { zFormControl } from '@/components/z-ui/formControl'
import { zInput } from '@/components/z-ui/input'

interface AppFormData {
  appName: string
  displayName: string
}

interface Props {
  formData: AppFormData
}

const props = defineProps<Props>()

const emit = defineEmits<{
  'update:formData': [value: AppFormData]
}>()

const hasInvalidDisplayNameChars = computed(() => {
  return props.formData.displayName.length > 0 && /[^\u4e00-\u9fa5a-zA-Z0-9\s_-]/.test(props.formData.displayName)
})

const updateAppName = (value: string) => {
  emit('update:formData', {
    ...props.formData,
    appName: value
  })
}

const updateDisplayName = (value: string) => {
  emit('update:formData', {
    ...props.formData,
    displayName: value
  })
}
</script>

<style scoped>
.app-info-form {
  display: grid;
  justify-content: center;
  gap: 12px;
}
</style>
