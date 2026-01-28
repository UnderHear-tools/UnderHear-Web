<template>
  <span class="z-link-wrapper">
    <span v-if="prefixText" class="z-link-prefix">{{ prefixText }}</span>
    <a 
      :href="href" 
      :target="target" 
      :rel="(external || target === '_blank') ? 'noopener noreferrer' : undefined"
      class="z-link"
      :class="{
        'z-link-external': external,
        'z-link-primary': variant === 'primary',
        'z-link-secondary': variant === 'secondary',
        'z-link-danger': variant === 'danger'
      }"
    >
      {{ linkText }}
    </a>
    <span v-if="suffixText" class="z-link-suffix">{{ suffixText }}</span>
  </span>
</template>

<script setup lang="ts">
interface Props {
  /** 链接地址 */
  href: string
  /** 链接文本 */
  linkText: string
  /** 前置文本 */
  prefixText?: string
  /** 后置文本 */
  suffixText?: string
  /** 是否为外部链接 */
  external?: boolean
  /** 链接打开方式 */
  target?: '_blank' | '_self' | '_parent' | '_top'
  /** 链接样式变体 */
  variant?: 'primary' | 'secondary' | 'danger' | 'default'
}

withDefaults(defineProps<Props>(), {
  external: false,
  target: '_self',
  variant: 'primary'
})
</script>

<style scoped>
.z-link-wrapper {
  display: inline;
}

.z-link-prefix,
.z-link-suffix {
  color: inherit;
}

.z-link {
  color: #0969da;
  text-decoration: none;
  font-weight: 500;
  transition: all 0.2s ease;
  border-radius: 3px;
  padding: 0 2px;
}

.z-link:hover {
  color: #0550ae;
  text-decoration: underline;
}

.z-link:active {
  color: #0a3069;
}

/* 变体样式 */
.z-link-primary {
  color: #0969da;
}

.z-link-primary:hover {
  color: #0550ae;
}

.z-link-secondary {
  color: #656d76;
}

.z-link-secondary:hover {
  color: #24292f;
}

.z-link-danger {
  color: #d1242f;
}

.z-link-danger:hover {
  color: #a40e26;
}

/* 外部链接样式 */
.z-link-external::after {
  content: '↗';
  font-size: 0.8em;
  margin-left: 2px;
}

/* 焦点样式 */
.z-link:focus {
  outline: 2px solid #0969da;
  outline-offset: 2px;
}

.z-link:focus:not(:focus-visible) {
  outline: none;
}
</style>