<script setup lang="ts">
import { ref } from 'vue'
import { zButton } from '@/components/z-ui/button'
import { ArrowUpRight, Bell, Check, Search } from '@/components/z-ui/icon/Octicons-vue'
import { zInput } from '@/components/z-ui/input'

const variants = [
  {
    value: 'default',
    label: 'Default',
    description: '基础按钮，适合常规操作。'
  },
  {
    value: 'primary',
    label: 'Primary',
    description: '强调主操作。'
  },
  {
    value: 'invisible',
    label: 'Invisible',
    description: '弱化容器感，保留交互反馈。'
  },
  {
    value: 'danger',
    label: 'Danger',
    description: '危险操作，例如删除或重置。'
  },
  {
    value: 'link',
    label: 'Link',
    description: '链接式按钮，弱视觉、强语义。'
  }
] as const

const sizes = [
  { value: 'small', label: 'Small' },
  { value: 'medium', label: 'Medium' },
  { value: 'large', label: 'Large' }
] as const

const defaultFieldValue = 'activate-demo'
const fieldValue = ref(defaultFieldValue)
const stateLog = ref('点击 Interactive 按钮验证事件透传。')
const formLog = ref('尚未触发表单行为。')

const defaultDom = `<button type="button" data-loading="false" data-size="medium" data-variant="default">
  <span>
    <span>
      <span><!-- leadingVisual --></span>
      <span>Activate</span>
      <span><!-- trailingVisual --></span>
    </span>
  </span>
</button>`

function setStateLog(message: string) {
  stateLog.value = message
}

function setFormLog(message: string) {
  formLog.value = message
}

function handleSubmit() {
  formLog.value = `submit fired, current value: "${fieldValue.value}"`
}

function handleReset() {
  fieldValue.value = defaultFieldValue
  formLog.value = `reset fired, value restored to "${defaultFieldValue}"`
}
</script>

<template>
  <div class="playground-page">
    <header class="hero">
      <p class="eyebrow">
        z-ui / Button
      </p>
      <h1>zButton Playground</h1>
      <p class="hero-copy">
        这里集中展示 `zButton` 当前全部公开特性：`variant`、`size`、`loading`、`disabled`、
        `type`，以及原生按钮事件透传。
      </p>
      <div class="hero-chips">
        <span class="chip">default slot</span>
        <span class="chip">leading / trailing visuals</span>
        <span class="chip">5 variants</span>
        <span class="chip">3 sizes</span>
        <span class="chip">loading / disabled</span>
        <span class="chip">button / submit / reset</span>
      </div>
    </header>

    <section class="panel">
      <div class="panel-header">
        <h2>Variants</h2>
        <p>每个变体都展示默认、禁用和加载状态。</p>
      </div>

      <div class="variant-list">
        <div
          v-for="variant in variants"
          :key="variant.value"
          class="demo-block"
        >
          <div class="demo-meta">
            <h3>{{ variant.label }}</h3>
            <p>{{ variant.description }}</p>
          </div>

          <div class="demo-row">
            <zButton :variant="variant.value">
              {{ variant.label }}
            </zButton>
            <zButton
              :variant="variant.value"
              disabled
            >
              {{ variant.label }} Disabled
            </zButton>
            <zButton
              :variant="variant.value"
              loading
            >
              {{ variant.label }} Loading
            </zButton>
          </div>
        </div>
      </div>
    </section>

    <section class="panel panel-grid">
      <div class="demo-block">
        <div class="panel-header">
          <h2>Visual Slots</h2>
          <p>通过 `#leadingVisual` 和 `#trailingVisual` 在文本前后插入轻量视觉元素。</p>
        </div>

        <div class="variant-list">
          <div class="demo-row">
            <zButton>
              <template #leadingVisual>
                <Search size="16" />
              </template>
              Search
            </zButton>

            <zButton variant="primary">
              Continue
              <template #trailingVisual>
                <ArrowUpRight size="16" />
              </template>
            </zButton>

            <zButton variant="danger">
              <template #leadingVisual>
                <Bell size="16" />
              </template>
              Alerts
              <template #trailingVisual>
                <Check size="16" />
              </template>
            </zButton>
          </div>

          <div class="demo-row">
            <zButton loading>
              <template #leadingVisual>
                <Search size="16" />
              </template>
              Loading Search
              <template #trailingVisual>
                <ArrowUpRight size="16" />
              </template>
            </zButton>
          </div>
        </div>
      </div>

      <div class="demo-block">
        <div class="panel-header">
          <h2>Sizes</h2>
          <p>尺寸只影响按钮高度、内边距和字号。</p>
        </div>

        <div class="size-list">
          <div
            v-for="size in sizes"
            :key="size.value"
            class="size-row"
          >
            <span class="size-label">{{ size.label }}</span>
            <div class="demo-row">
              <zButton :size="size.value">
                {{ size.label }}
              </zButton>
              <zButton
                :size="size.value"
                variant="primary"
              >
                {{ size.label }} Primary
              </zButton>
            </div>
          </div>
        </div>
      </div>

      <div class="demo-block">
        <div class="panel-header">
          <h2>States And Events</h2>
          <p>当前实现会在 `loading` 时使用原生 `disabled` 阻止交互。</p>
        </div>

        <div class="demo-row">
          <zButton @click="setStateLog('Interactive click fired.')">
            Interactive
          </zButton>
          <zButton
            loading
            @click="setStateLog('Loading click fired.')"
          >
            Loading
          </zButton>
          <zButton
            disabled
            @click="setStateLog('Disabled click fired.')"
          >
            Disabled
          </zButton>
        </div>

        <p class="status-text">
          {{ stateLog }}
        </p>
      </div>
    </section>

    <section class="panel">
      <div class="panel-header">
        <h2>Button Types</h2>
        <p>在表单中展示 `type="button"`、`type="submit"`、`type="reset"` 的真实行为。</p>
      </div>

      <form
        class="type-demo"
        @submit.prevent="handleSubmit"
        @reset.prevent="handleReset"
      >
        <label
          for="playground-field"
          class="field-label"
        >
          Demo field
        </label>
        <zInput
          id="playground-field"
          v-model="fieldValue"
        />

        <div class="demo-row">
          <zButton
            type="button"
            variant="invisible"
            @click="setFormLog('type=button click fired without submit.')"
          >
            type="button"
          </zButton>
          <zButton
            type="submit"
            variant="primary"
          >
            type="submit"
          </zButton>
          <zButton
            type="reset"
            variant="danger"
          >
            type="reset"
          </zButton>
        </div>

        <p class="status-text">
          {{ formLog }}
        </p>
      </form>
    </section>

    <section class="panel">
      <div class="panel-header">
        <h2>Default DOM Contract</h2>
        <p>默认渲染时的最小 DOM 结构与关键 data 属性。</p>
      </div>

      <pre class="dom-preview">{{ defaultDom }}</pre>
    </section>
  </div>
</template>

<style scoped>
.playground-page {
  display: grid;
  gap: 24px;
  margin: 0 auto;
  max-width: 1200px;
  padding: 32px 24px 56px;
}

.hero,
.panel {
  background: var(--bgColor-default, #fff);
  border: 1px solid var(--borderColor-default, #d0d7de);
  border-radius: 16px;
  box-shadow: var(--shadow-resting-small, 0 1px 3px rgba(31, 35, 40, 0.08));
}

.hero {
  display: grid;
  gap: 14px;
  padding: 32px;
}

.eyebrow {
  color: var(--fgColor-muted, #656d76);
  font-size: 0.75rem;
  font-weight: 600;
  letter-spacing: 0.08em;
  margin: 0;
  text-transform: uppercase;
}

.hero h1,
.panel h2,
.demo-meta h3 {
  color: var(--fgColor-default, #1f2328);
  margin: 0;
}

.hero h1 {
  font-size: clamp(2rem, 4vw, 3.75rem);
  letter-spacing: -0.04em;
  line-height: 0.95;
}

.hero-copy,
.panel-header p,
.demo-meta p,
.status-text,
.field-label {
  color: var(--fgColor-muted, #656d76);
  margin: 0;
}

.hero-copy {
  font-size: 1rem;
  line-height: 1.6;
  max-width: 760px;
}

.hero-chips {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
}

.chip {
  background: linear-gradient(180deg, var(--bgColor-muted, #f6f8fa), var(--button-default-bgColor-rest, #f6f8fa));
  border: 1px solid var(--borderColor-muted, #d8dee4);
  border-radius: 999px;
  color: var(--fgColor-default, #1f2328);
  font-size: 0.8125rem;
  font-weight: 600;
  padding: 8px 12px;
}

.panel {
  display: grid;
  gap: 20px;
  padding: 24px;
}

.panel-grid {
  grid-template-columns: repeat(2, minmax(0, 1fr));
}

.panel-header {
  display: grid;
  gap: 6px;
}

.variant-list,
.size-list {
  display: grid;
  gap: 18px;
}

.demo-block {
  display: grid;
  gap: 14px;
}

.demo-meta {
  display: grid;
  gap: 4px;
}

.demo-row {
  align-items: center;
  display: flex;
  flex-wrap: wrap;
  gap: 12px;
}

.size-row {
  align-items: center;
  display: grid;
  gap: 12px;
  grid-template-columns: 88px minmax(0, 1fr);
}

.size-label {
  color: var(--fgColor-muted, #656d76);
  font-size: 0.8125rem;
  font-weight: 600;
  text-transform: uppercase;
}

.status-text {
  background: var(--bgColor-muted, #f6f8fa);
  border: 1px solid var(--borderColor-muted, #d8dee4);
  border-radius: 12px;
  padding: 12px 14px;
}

.type-demo {
  display: grid;
  gap: 14px;
  max-width: 560px;
}

.field-label {
  font-size: 0.875rem;
  font-weight: 600;
}

.dom-preview {
  background: linear-gradient(180deg, var(--bgColor-muted, #f6f8fa), var(--bgColor-default, #fff));
  border: 1px solid var(--borderColor-muted, #d8dee4);
  border-radius: 12px;
  color: var(--fgColor-default, #1f2328);
  font-family: ui-monospace, SFMono-Regular, Menlo, Consolas, monospace;
  font-size: 0.8125rem;
  line-height: 1.6;
  margin: 0;
  overflow-x: auto;
  padding: 16px;
  white-space: pre-wrap;
}

@media (max-width: 900px) {
  .panel-grid {
    grid-template-columns: 1fr;
  }

  .size-row {
    grid-template-columns: 1fr;
  }
}

@media (max-width: 640px) {
  .playground-page {
    padding-inline: 16px;
  }

  .hero,
  .panel {
    padding: 20px;
  }
}
</style>
