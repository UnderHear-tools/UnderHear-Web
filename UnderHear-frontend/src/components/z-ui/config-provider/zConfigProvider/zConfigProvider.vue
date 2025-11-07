<template>
  <div 
    :class="configProviderClass"
    :style="configProviderStyle"
  >
    <slot />
  </div>
</template>

<script setup lang="ts">
import { computed, provide, inject, type InjectionKey } from 'vue'

// 类型定义
export interface ThemeConfig {
  primaryColor?: string
  backgroundColor?: string
  textColor?: string
  borderColor?: string
  [key: string]: any
}

export interface LocaleConfig {
  locale: string
  messages?: Record<string, string>
  [key: string]: any
}

export interface ZIndexConfig {
  modal?: number
  drawer?: number
  message?: number
  notification?: number
  tooltip?: number
  [key: string]: any
}

export interface ConfigProviderConfig {
  theme?: 'light' | 'dark' | ThemeConfig
  size?: 'small' | 'medium' | 'large'
  locale?: 'zh-CN' | 'en-US' | LocaleConfig
  namespace?: string
  zIndex?: ZIndexConfig
  disabled?: boolean
  [key: string]: any
}

// Props 定义
interface Props {
  config?: ConfigProviderConfig
}

const props = withDefaults(defineProps<Props>(), {
  config: () => ({})
})

// 注入键
export const CONFIG_PROVIDER_KEY: InjectionKey<ConfigProviderConfig> = Symbol('configProvider')

// 获取父级配置
const parentConfig = inject(CONFIG_PROVIDER_KEY, {})

// 合并配置
const mergedConfig = computed(() => {
  const defaultConfig: ConfigProviderConfig = {
    theme: 'light',
    size: 'medium',
    locale: 'zh-CN',
    namespace: 'z',
    zIndex: {
      modal: 1000,
      drawer: 900,
      message: 800,
      notification: 700,
      tooltip: 600
    },
    disabled: false
  }

  return {
    ...defaultConfig,
    ...parentConfig,
    ...props.config
  }
})

// 提供配置给子组件
provide(CONFIG_PROVIDER_KEY, mergedConfig.value)

// 计算样式类名
const configProviderClass = computed(() => {
  const { theme, size, namespace, disabled } = mergedConfig.value
  
  return [
    `${namespace}-config-provider`,
    `${namespace}-theme-${theme}`,
    `${namespace}-size-${size}`,
    {
      [`${namespace}-disabled`]: disabled
    }
  ]
})

// 计算内联样式
const configProviderStyle = computed(() => {
  const { theme, zIndex } = mergedConfig.value
  const style: Record<string, any> = {}

  // 主题相关的 CSS 变量
  if (typeof theme === 'object') {
    Object.entries(theme).forEach(([key, value]) => {
      if (typeof value === 'string' || typeof value === 'number') {
        style[`--z-${key}`] = value
      }
    })
  }

  // z-index 相关的 CSS 变量
  if (zIndex) {
    Object.entries(zIndex).forEach(([key, value]) => {
      if (typeof value === 'number') {
        style[`--z-z-index-${key}`] = value
      }
    })
  }

  return style
})

// 导出配置供其他组件使用
defineExpose({
  config: mergedConfig
})

// 提供 hook 函数供其他组件使用
export function useConfigProvider() {
  return inject(CONFIG_PROVIDER_KEY, {
    theme: 'light',
    size: 'medium',
    locale: 'zh-CN',
    namespace: 'z',
    zIndex: {
      modal: 1000,
      drawer: 900,
      message: 800,
      notification: 700,
      tooltip: 600
    },
    disabled: false
  })
}
</script>

<style scoped>
.z-config-provider {
  /* 基础样式 */
  --z-font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, 'Helvetica Neue', Arial, sans-serif;
  --z-line-height: 1.5;
  --z-border-radius: 6px;
  --z-transition-duration: 0.2s;
  --z-transition-timing: cubic-bezier(0.4, 0, 0.2, 1);
  
  font-family: var(--z-font-family);
  line-height: var(--z-line-height);
}

/* 浅色主题 */
.z-theme-light {
  --z-primary-color: #3b82f6;
  --z-primary-hover: #2563eb;
  --z-primary-active: #1d4ed8;
  --z-success-color: #10b981;
  --z-warning-color: #f59e0b;
  --z-error-color: #ef4444;
  --z-info-color: #6b7280;
  
  --z-text-primary: #1f2937;
  --z-text-secondary: #6b7280;
  --z-text-disabled: #9ca3af;
  
  --z-bg-primary: #ffffff;
  --z-bg-secondary: #f9fafb;
  --z-bg-tertiary: #f3f4f6;
  --z-bg-disabled: #e5e7eb;
  
  --z-border-primary: #e5e7eb;
  --z-border-secondary: #d1d5db;
  --z-border-focus: #3b82f6;
  
  --z-shadow-sm: 0 1px 2px 0 rgba(0, 0, 0, 0.05);
  --z-shadow-md: 0 4px 6px -1px rgba(0, 0, 0, 0.1), 0 2px 4px -1px rgba(0, 0, 0, 0.06);
  --z-shadow-lg: 0 10px 15px -3px rgba(0, 0, 0, 0.1), 0 4px 6px -2px rgba(0, 0, 0, 0.05);
}

/* 深色主题 */
.z-theme-dark {
  --z-primary-color: #60a5fa;
  --z-primary-hover: #3b82f6;
  --z-primary-active: #2563eb;
  --z-success-color: #34d399;
  --z-warning-color: #fbbf24;
  --z-error-color: #f87171;
  --z-info-color: #9ca3af;
  
  --z-text-primary: #f9fafb;
  --z-text-secondary: #d1d5db;
  --z-text-disabled: #6b7280;
  
  --z-bg-primary: #111827;
  --z-bg-secondary: #1f2937;
  --z-bg-tertiary: #374151;
  --z-bg-disabled: #4b5563;
  
  --z-border-primary: #374151;
  --z-border-secondary: #4b5563;
  --z-border-focus: #60a5fa;
  
  --z-shadow-sm: 0 1px 2px 0 rgba(0, 0, 0, 0.3);
  --z-shadow-md: 0 4px 6px -1px rgba(0, 0, 0, 0.4), 0 2px 4px -1px rgba(0, 0, 0, 0.3);
  --z-shadow-lg: 0 10px 15px -3px rgba(0, 0, 0, 0.4), 0 4px 6px -2px rgba(0, 0, 0, 0.3);
}

/* 小尺寸 */
.z-size-small {
  --z-font-size-xs: 10px;
  --z-font-size-sm: 12px;
  --z-font-size-base: 13px;
  --z-font-size-lg: 14px;
  --z-font-size-xl: 16px;
  
  --z-spacing-xs: 2px;
  --z-spacing-sm: 4px;
  --z-spacing-md: 6px;
  --z-spacing-lg: 8px;
  --z-spacing-xl: 12px;
  
  --z-height-sm: 24px;
  --z-height-md: 28px;
  --z-height-lg: 32px;
  
  --z-border-radius: 4px;
}

/* 中等尺寸 */
.z-size-medium {
  --z-font-size-xs: 12px;
  --z-font-size-sm: 14px;
  --z-font-size-base: 16px;
  --z-font-size-lg: 18px;
  --z-font-size-xl: 20px;
  
  --z-spacing-xs: 4px;
  --z-spacing-sm: 8px;
  --z-spacing-md: 12px;
  --z-spacing-lg: 16px;
  --z-spacing-xl: 24px;
  
  --z-height-sm: 32px;
  --z-height-md: 40px;
  --z-height-lg: 48px;
  
  --z-border-radius: 6px;
}

/* 大尺寸 */
.z-size-large {
  --z-font-size-xs: 14px;
  --z-font-size-sm: 16px;
  --z-font-size-base: 18px;
  --z-font-size-lg: 20px;
  --z-font-size-xl: 24px;
  
  --z-spacing-xs: 6px;
  --z-spacing-sm: 12px;
  --z-spacing-md: 18px;
  --z-spacing-lg: 24px;
  --z-spacing-xl: 32px;
  
  --z-height-sm: 40px;
  --z-height-md: 48px;
  --z-height-lg: 56px;
  
  --z-border-radius: 8px;
}

/* 禁用状态 */
.z-disabled {
  pointer-events: none;
  opacity: 0.6;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .z-config-provider {
    --z-spacing-xs: 2px;
    --z-spacing-sm: 4px;
    --z-spacing-md: 8px;
    --z-spacing-lg: 12px;
    --z-spacing-xl: 16px;
  }
}

/* 高对比度模式支持 */
@media (prefers-contrast: high) {
  .z-theme-light {
    --z-border-primary: #000000;
    --z-border-secondary: #000000;
  }
  
  .z-theme-dark {
    --z-border-primary: #ffffff;
    --z-border-secondary: #ffffff;
  }
}

/* 减少动画模式支持 */
@media (prefers-reduced-motion: reduce) {
  .z-config-provider {
    --z-transition-duration: 0s;
  }
}
</style>