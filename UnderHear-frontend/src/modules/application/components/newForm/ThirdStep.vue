<template>
  <form
    class="app-info-form"
    @submit.prevent
  >
    <FormControl required>
      <FormControl.Label>
        应用名称
      </FormControl.Label>
      <zInput
        class="w-full"
        :model-value="formData.appName"
        placeholder="请输入应用名称"
        maxlength="100"
        @update:model-value="updateAppName"
      />
      <FormControl.Validation
        v-if="errors.appName"
        variant="error"
      >
        {{ errors.appName }}
      </FormControl.Validation>
      <FormControl.Caption>
        {{ `${formData.appName.length} / 100` }}
      </FormControl.Caption>
    </FormControl>

    <FormControl required>
      <FormControl.Label>
        英文名称
      </FormControl.Label>
      <zInput
        class="w-full"
        :model-value="formData.englishName"
        placeholder="请输入英文名称"
        maxlength="63"
        @update:model-value="updateEnglishName"
      />
      <FormControl.Validation
        v-if="errors.englishName"
        variant="error"
      >
        {{ errors.englishName }}
      </FormControl.Validation>
      <FormControl.Caption v-if="formData.englishName.trim()">
        应用地址：https://{{ formData.englishName }}.underhear.cn/
      </FormControl.Caption>
    </FormControl>

    <FormControl required>
      <FormControl.Label>
        应用描述
      </FormControl.Label>
      <zTextarea
        class="w-full"
        :model-value="formData.appDescription"
        placeholder="请输入应用描述"
        maxlength="1000"
        rows="7"
        @update:model-value="updateAppDescription"
      />
      <FormControl.Validation
        v-if="errors.appDescription"
        variant="error"
      >
        {{ errors.appDescription }}
      </FormControl.Validation>
      <FormControl.Caption>
        {{ `${formData.appDescription.length} / 1000` }}
      </FormControl.Caption>
    </FormControl>
  </form>
</template>

<script setup lang="ts">
import { FormControl } from '@/components/z-ui/form-control'
import { zInput } from '@/components/z-ui/input'
import { zTextarea } from '@/components/z-ui/textarea'

interface AppFormData {
  appName: string
  englishName: string
  appDescription: string
}

interface AppFormErrors {
  appName?: string
  englishName?: string
  appDescription?: string
}

interface Props {
  formData: AppFormData
  errors?: AppFormErrors
}

const props = withDefaults(defineProps<Props>(), {
  errors: () => ({})
})

const emit = defineEmits<{
  'update:formData': [value: AppFormData]
}>()

const updateAppName = (value: string) => {
  emit('update:formData', {
    ...props.formData,
    appName: value
  })
}

const updateEnglishName = (value: string) => {
  emit('update:formData', {
    ...props.formData,
    englishName: value
  })
}

const updateAppDescription = (value: string) => {
  emit('update:formData', {
    ...props.formData,
    appDescription: value
  })
}
</script>

<style scoped>
.app-info-form {
  display: grid;
  gap: 12px;
}
</style>
