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
    </FormControl>

    <FormControl required>
      <FormControl.Label>
        英文名称
      </FormControl.Label>
      <zInput
        class="w-full"
        :model-value="formData.englishName"
        placeholder="请输入英文名称"
        minlength="4"
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

    <section class="visibility-panel">
      <div class="visibility-text">
        <h4 class="visibility-title">
          选择可见性 *
        </h4>
        <p class="visibility-caption">
          选择你的应用公开的还是私有的。
        </p>
      </div>

      <div class="visibility-select-wrap">
        <zDropdown>
          <template #trigger>
            <zButton>
              <template #leadingVisual>
                <component
                  :is="selectedVisibilityOption.icon"
                  color="var(--fgColor-muted)"
                />
              </template>
              {{ label }}
              <template #trailingVisual>
                <TriangleDown color="var(--fgColor-muted)" />
              </template>
            </zButton>
          </template>
          <template #content>
            <ActionList
              v-model="selectedAction"
              selection-mode="single"
            >
              <ActionList.Item
                v-for="item in visibilityOptions"
                :key="item.value"
                class="visibility-action-item"
                :value="item.value"
              >
                <component
                  :is="item.icon"
                  class="visibility-item-icon"
                  color="var(--fgColor-muted)"
                />
                <span class="visibility-item-content">
                  <span class="visibility-item-title">{{ item.value }}</span>
                  <span class="visibility-item-desc">{{ item.description }}</span>
                </span>
              </ActionList.Item>
            </ActionList>
          </template>
        </zDropdown>
      </div>
    </section>

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
import { computed } from 'vue'
import { FormControl } from '@/components/z-ui/form-control'
import { zInput } from '@/components/z-ui/input'
import { zTextarea } from '@/components/z-ui/textarea'
import { zButton } from '@/components/z-ui/button'
import { zDropdown } from '@/components/z-ui/dropdown'
import { ActionList } from '@/components/z-ui/action-list'
import Repo from '@/components/z-ui/icon/Octicons-vue/icons/repo.vue'
import TriangleDown from '@/components/z-ui/icon/Octicons-vue/icons/triangle-down.vue'
import Lock from '@/components/z-ui/icon/Octicons-vue/icons/lock.vue'

interface AppFormData {
  appName: string
  englishName: string
  visibility: string
  appDescription: string
}

interface VisibilityOption {
  value: string
  description: string
  icon: typeof Repo
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

const visibilityOptions: VisibilityOption[] = [
  {
    value: '公开的',
    description: '你的应用被公开，所有人都能够在应用广场上看见它。',
    icon: Repo
  },
  {
    value: '私有的',
    description: '你可以在个人应用中查看和管理它。当然别人也可以通过URL进行访问。',
    icon: Lock
  }
]

const selectedVisibilityOption = computed(() => {
  return visibilityOptions.find(item => item.value === props.formData.visibility) ?? visibilityOptions[0]
})

const selectedAction = computed({
  get: () => props.formData.visibility,
  set: value => {
    emit('update:formData', {
      ...props.formData,
      visibility: value
    })
  }
})

const label = computed(() => selectedVisibilityOption.value.value)
</script>

<style scoped>
.app-info-form {
  display: grid;
  gap: 12px;
}

.visibility-panel {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 8px;
  padding: 12px;
  border: 1px solid var(--borderColor-muted);
  border-radius: 6px;
  background: var(--bgColor-default);
}

.visibility-text {
  min-width: 0;
}

.visibility-title {
  margin: 0;
  color: var(--fgColor-default);
  font-size: 14px;
  font-weight: 600;
}

.visibility-caption {
  margin: 2px 0 0;
  color: var(--fgColor-muted);
  font-size: 12px;
  line-height: 1.5;
}

.visibility-action-item {
  align-items: flex-start;
  gap: 10px;
  padding-top: 8px;
  padding-bottom: 8px;
}

.visibility-item-icon {
  margin-top: 2px;
  flex-shrink: 0;
}

.visibility-item-content {
  display: flex;
  flex-direction: column;
  gap: 2px;
  width: 240px;
}

.visibility-item-title {
  color: var(--fgColor-default);
  font-size: 14px;
  font-weight: 600;
  line-height: 1.4;
}

.visibility-item-desc {
  color: var(--fgColor-muted);
  font-size: 12px;
  line-height: 1.45;
}


@media (max-width: 768px) {
  .visibility-panel {
    flex-direction: column;
    align-items: stretch;
  }
}
</style>
