<template>
  <ComponentDocsPage>
    <ComponentDocsHeader title="Color 颜色设计">
      <template #description>
        为了避免视觉传达差异，使用一套特定的调色板来规定颜色，为组件们提供一致的外观视觉感受。
        感谢
        <zLink
          href="https://primer.style/brand/primitives/color/"
          link-text="primer style primitives"
          variant="primary"
        />
        设计师们提供的宝贵资源。
      </template>
    </ComponentDocsHeader>

    <ComponentDocsSection title="主题">
      <div
        class="theme-card-list"
        role="radiogroup"
        aria-label="主题预览切换"
      >
        <label
          v-for="option in themeOptions"
          :key="option.mode"
          class="theme-card"
          :class="{ 'theme-card--selected': option.mode === currentTheme }"
        >
          <div
            class="theme-card-preview"
            :class="`theme-card-preview--${option.mode}`"
          >
            <span
              v-for="family in previewDotFamilies"
              :key="`${option.mode}-${family}`"
              class="theme-card-dot"
              :style="{ backgroundColor: getPreviewDotColor(family, option.mode) }"
            />
          </div>

          <div class="theme-card-footer">
            <input
              v-model="currentTheme"
              class="theme-card-input"
              type="radio"
              name="docs-theme-mode"
              :value="option.mode"
            >
            <span class="theme-card-label">{{ option.label }}</span>
          </div>
        </label>
      </div>
    </ComponentDocsSection>

    <ComponentDocsSection title="配色">
      <div class="scales-board">
        <div
          v-for="families in paletteRows"
          :key="families.join('-')"
          class="scale-family-row"
        >
          <div
            v-for="family in families"
            :key="family"
            class="scale-family-column"
          >
            <div
              v-for="entry in colorScales[family]"
              :key="`${family}-${entry.step}`"
              class="color-swatch"
              :style="getSwatchStyle(entry, currentTheme)"
            >
              <span class="color-swatch-token">{{ family }}.{{ entry.step }}</span>
              <span class="color-swatch-value">{{ getScaleHex(entry, currentTheme) }}</span>
            </div>
          </div>
        </div>

        <div class="scale-family-row">
          <div class="scale-family-column">
            <div
              v-for="entry in colorScales.indigo"
              :key="`indigo-${entry.step}`"
              class="color-swatch"
              :style="getSwatchStyle(entry, currentTheme)"
            >
              <span class="color-swatch-token">indigo.{{ entry.step }}</span>
              <span class="color-swatch-value">{{ getScaleHex(entry, currentTheme) }}</span>
            </div>
          </div>
        </div>
      </div>

      <div class="single-scale-board">
        <div class="single-scale-grid">
          <div
            v-for="family in singleScales"
            :key="family"
            class="scale-family-column"
          >
            <div
              v-for="entry in colorScales[family]"
              :key="`${family}-${entry.step}`"
              class="color-swatch color-swatch--single"
              :class="{ 'color-swatch--white': family === 'white' }"
              :style="getSwatchStyle(entry, currentTheme)"
            >
              <span class="color-swatch-token">{{ family }}.{{ entry.step }}</span>
              <span class="color-swatch-value">{{ getScaleHex(entry, currentTheme) }}</span>
            </div>
          </div>
        </div>
      </div>
    </ComponentDocsSection>
  </ComponentDocsPage>
</template>

<script lang="ts" setup>
import { ref } from 'vue'
import { zLink } from '@/components/z-ui/link'
import ComponentDocsHeader from '@/modules/components/components/ComponentDocsPage/ComponentDocsHeader.vue'
import ComponentDocsPage from '@/modules/components/components/ComponentDocsPage/ComponentDocsPage.vue'
import ComponentDocsSection from '@/modules/components/components/ComponentDocsPage/ComponentDocsSection.vue'

type ThemeMode = 'light' | 'dark'

type ScaleEntry = {
  step: number
  light: string
  dark: string
}

type ColorFamily =
  | 'gray'
  | 'blue'
  | 'green'
  | 'yellow'
  | 'orange'
  | 'red'
  | 'purple'
  | 'pink'
  | 'coral'
  | 'lemon'
  | 'lime'
  | 'teal'
  | 'indigo'
  | 'black'
  | 'white'

type PreviewDotFamily =
  | 'gray'
  | 'blue'
  | 'green'
  | 'orange'
  | 'red'
  | 'purple'
  | 'pink'

const createScale = (values: ReadonlyArray<readonly [string, string]>): ScaleEntry[] =>
  values.map(([light, dark], step) => ({
    step,
    light,
    dark
  }))

const themeOptions = [
  { mode: 'light', label: 'Light' },
  { mode: 'dark', label: 'Dark' }
] as const

const previewDotFamilies = ['gray', 'blue', 'green', 'orange', 'red', 'purple', 'pink'] as const

const paletteRows = [
  ['gray', 'blue', 'green'],
  ['yellow', 'orange', 'red'],
  ['purple', 'pink', 'coral'],
  ['lemon', 'lime', 'teal']
] as const satisfies readonly (readonly ColorFamily[])[]

const singleScales = ['black', 'white'] as const

const currentTheme = ref<ThemeMode>('light')

const colorScales: Record<ColorFamily, ScaleEntry[]> = {
  gray: createScale([
    ['#F2F5F3', '#D2D9D4'],
    ['#E4EBE6', '#C4CCC6'],
    ['#D2D9D4', '#A4AEA6'],
    ['#C4CCC6', '#7C8980'],
    ['#B6BFB8', '#58635B'],
    ['#96A199', '#353D37'],
    ['#77827A', '#262C28'],
    ['#58635B', '#191F1B'],
    ['#353D37', '#0F1511'],
    ['#191F1B', '#060907']
  ]),
  blue: createScale([
    ['#DDF4FF', '#C2EDFF'],
    ['#BCECFF', '#A2DAFF'],
    ['#8DD6FF', '#78BAFE'],
    ['#5FB9FF', '#3094FF'],
    ['#3094FF', '#0377FF'],
    ['#0377FF', '#0A50DB'],
    ['#0055D5', '#1530B7'],
    ['#0040A7', '#082A8F'],
    ['#002F7A', '#052063'],
    ['#001C4D', '#000839']
  ]),
  green: createScale([
    ['#EBF9F4', '#CDFCD9'],
    ['#BFFFD1', '#8CF2A6'],
    ['#8CF2A6', '#5FED83'],
    ['#5FED83', '#23EA57'],
    ['#23EA57', '#0FBF3E'],
    ['#0FBF3E', '#08872B'],
    ['#08872B', '#0D6731'],
    ['#0D6731', '#0E422C'],
    ['#0E4A2E', '#0D3024'],
    ['#0D3024', '#0A241B']
  ]),
  yellow: createScale([
    ['#FFF8C5', '#F8E3A1'],
    ['#FFE777', '#F7D162'],
    ['#FFD743', '#FABF21'],
    ['#FABF21', '#DB9D00'],
    ['#DB9D00', '#BE7D00'],
    ['#BE7D00', '#A06100'],
    ['#A06100', '#834800'],
    ['#824800', '#653200'],
    ['#653200', '#471F00'],
    ['#471F00', '#2A1000']
  ]),
  orange: createScale([
    ['#FFF1E5', '#FFE2CC'],
    ['#FCCEAB', '#FAB580'],
    ['#F4A876', '#F08A3A'],
    ['#F08A3A', '#EA7110'],
    ['#DA7210', '#D56101'],
    ['#B85B06', '#B35101'],
    ['#954502', '#924100'],
    ['#703100', '#703100'],
    ['#5C2300', '#572400'],
    ['#471700', '#3D1800']
  ]),
  red: createScale([
    ['#FFEBE9', '#FFD9D6'],
    ['#FFCECB', '#FEB2AE'],
    ['#FFABA8', '#FD8986'],
    ['#FF8182', '#FC5C5D'],
    ['#FA4549', '#FA383D'],
    ['#CF2230', '#D31231'],
    ['#AE0B29', '#AE0B29'],
    ['#860620', '#860620'],
    ['#730019', '#5E0217'],
    ['#420011', '#33000D']
  ]),
  purple: createScale([
    ['#F0E5FF', '#EADBFF'],
    ['#DBBFFD', '#D3B3FE'],
    ['#C898FD', '#C08BFC'],
    ['#B870FF', '#A665F9'],
    ['#9F51FA', '#8B40F5'],
    ['#8534F3', '#6619E1'],
    ['#6619E1', '#43179E'],
    ['#43179E', '#26115F'],
    ['#26115F', '#160048'],
    ['#160048', '#0E022C']
  ]),
  pink: createScale([
    ['#FFF0FC', '#FFDBF7'],
    ['#FFC9F2', '#FCABE7'],
    ['#F67ED2', '#F67ED2'],
    ['#FF80D2', '#ED55BA'],
    ['#FF4AC0', '#E22D9F'],
    ['#EF2AA4', '#CA2186'],
    ['#CA2186', '#961C66'],
    ['#952866', '#741550'],
    ['#651643', '#520E39'],
    ['#3D0A28', '#30081F']
  ]),
  coral: createScale([
    ['#FFF0EB', '#FFD5C7'],
    ['#FFCAB8', '#FDB7A1'],
    ['#FFA387', '#FA9072'],
    ['#FF7B56', '#F66945'],
    ['#FE4C25', '#EF4319'],
    ['#E13F1B', '#C53211'],
    ['#C53211', '#A22710'],
    ['#A22710', '#801E0F'],
    ['#801E0F', '#500A00'],
    ['#500A00', '#3C0000']
  ]),
  lemon: createScale([
    ['#FDF5B3', '#FCF2A5'],
    ['#F5E36B', '#F9E76A'],
    ['#F2DA3B', '#F4DA38'],
    ['#E1C50F', '#E4C411'],
    ['#C7A60B', '#C7A60B'],
    ['#A98906', '#A98906'],
    ['#806803', '#876A04'],
    ['#614D01', '#654D02'],
    ['#413200', '#423101'],
    ['#322400', '#241900']
  ]),
  lime: createScale([
    ['#F3FEC8', '#EDFFC9'],
    ['#E8FC97', '#DCFF96'],
    ['#DCFA67', '#CDF041'],
    ['#D1F441', '#B1E119'],
    ['#B2DE28', '#88B80F'],
    ['#92C219', '#608A10'],
    ['#698E17', '#3E5F0F'],
    ['#425E13', '#22360B'],
    ['#2C440B', '#142A08'],
    ['#182C01', '#091D05']
  ]),
  teal: createScale([
    ['#DAF9F5', '#CFF7F2'],
    ['#A4EFE8', '#99F1E8'],
    ['#6EE5DC', '#61EEE3'],
    ['#39DAD2', '#26EDE2'],
    ['#23B1AE', '#10DCD4'],
    ['#197B7B', '#0BBAB6'],
    ['#136061', '#079695'],
    ['#024B4D', '#047172'],
    ['#083D3D', '#024B4D'],
    ['#052B2C', '#052D2E']
  ]),
  indigo: createScale([
    ['#EFF2FF', '#DBE3FF'],
    ['#D4DBFF', '#B3C1FD'],
    ['#B3C1FD', '#8D9FF8'],
    ['#8E9DF7', '#6A7DF0'],
    ['#6B7BEF', '#4A5CE5'],
    ['#4956E5', '#2D3DD7'],
    ['#2D3DD7', '#232FB3'],
    ['#262DAE', '#212183'],
    ['#212183', '#161962'],
    ['#12144F', '#0D103F']
  ]),
  black: createScale([['#000000', '#000000']]),
  white: createScale([['#ffffff', '#ffffff']])
}

const getScaleHex = (entry: ScaleEntry, theme: ThemeMode) => entry[theme]

const getPreviewDotColor = (family: PreviewDotFamily, theme: ThemeMode) => colorScales[family][5][theme]

const getReadableTextColor = (hex: string) => {
  const normalizedHex = hex.replace('#', '')

  if (normalizedHex.length !== 6) {
    return '#191F1B'
  }

  const [red, green, blue] = [0, 2, 4]
    .map((offset) => parseInt(normalizedHex.slice(offset, offset + 2), 16) / 255)
    .map((channel) =>
      channel <= 0.03928
        ? channel / 12.92
        : ((channel + 0.055) / 1.055) ** 2.4
    )

  const luminance = 0.2126 * red + 0.7152 * green + 0.0722 * blue

  return luminance > 0.42 ? '#191F1B' : '#FFFFFF'
}

const getSwatchStyle = (entry: ScaleEntry, theme: ThemeMode) => {
  const backgroundColor = getScaleHex(entry, theme)

  return {
    backgroundColor,
    color: getReadableTextColor(backgroundColor)
  }
}
</script>

<style scoped>
.theme-card-list {
  display: flex;
  flex-wrap: wrap;
  gap: 1rem;
}

.theme-card {
  width: min(200px, 100%);
  border: 1px solid var(--borderColor-default);
  border-radius: 10px;
  overflow: hidden;
  background: var(--bgColor-default);
  cursor: pointer;
  transition: border-color 0.2s ease, box-shadow 0.2s ease, transform 0.2s ease;
  user-select: none;
}

.theme-card:hover {
  transform: translateY(-1px);
}

.theme-card--selected {
  border-color: var(--borderColor-accent-emphasis);
  box-shadow: 0 0 0 1px var(--borderColor-accent-emphasis);
}

.theme-card:focus-within {
  outline: 2px solid var(--focus-outlineColor);
  outline-offset: 2px;
}

.theme-card-preview {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  min-height: 56px;
  padding: 1rem;
  border-bottom: 1px solid var(--borderColor-default);
}

.theme-card-preview--light {
  background: #ffffff;
}

.theme-card-preview--dark {
  background: #000000;
  border-bottom-color: #1f2328;
}

.theme-card-dot {
  width: 18px;
  height: 18px;
  border-radius: 999px;
  flex-shrink: 0;
}

.theme-card-footer {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  padding: 0.875rem 1rem;
  background: var(--bgColor-default);
}

.theme-card-input {
  width: 16px;
  height: 16px;
  margin: 0;
  accent-color: var(--borderColor-accent-emphasis);
}

.theme-card-label {
  font-size: 1rem;
  color: var(--fgColor-default);
}

.scales-board,
.single-scale-board {
  border: 1px solid var(--borderColor-default);
  border-radius: 12px;
  background: var(--bgColor-default);
  padding: 0.875rem;
}

.single-scale-board {
  margin-top: 0.875rem;
}

.scale-family-row {
  display: grid;
  grid-template-columns: repeat(3, minmax(0, 1fr));
  gap: 0.875rem;
}

.scale-family-row + .scale-family-row {
  margin-top: 0.875rem;
}

.scale-family-column {
  overflow: hidden;
  border: 1px solid var(--borderColor-default);
  border-radius: 10px;
}

.single-scale-grid {
  display: grid;
  grid-template-columns: repeat(2, minmax(0, 1fr));
  gap: 0.875rem;
}

.color-swatch {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 0.75rem;
  min-height: 44px;
  padding: 0.75rem 0.875rem;
  font-family: 'Consolas', 'Monaco', 'Courier New', monospace;
  font-size: 0.875rem;
  line-height: 1.2;
}

.color-swatch + .color-swatch {
  border-top: 1px solid rgb(25 31 27 / 8%);
}

.color-swatch--single {
  min-height: 46px;
}

.color-swatch--white {
  box-shadow: inset 0 0 0 1px rgb(25 31 27 / 12%);
}

.color-swatch-token,
.color-swatch-value {
  font-weight: 600;
}

.color-swatch-value {
  text-align: right;
}

@media (max-width: 768px) {
  .theme-card-list {
    gap: 0.75rem;
  }

  .theme-card {
    width: 100%;
  }

  .theme-card-preview {
    min-height: 52px;
    padding: 0.875rem;
  }

  .theme-card-dot {
    width: 16px;
    height: 16px;
  }

  .scales-board,
  .single-scale-board {
    padding: 0.75rem;
  }

  .scale-family-row,
  .single-scale-grid {
    grid-template-columns: 1fr;
    gap: 0.75rem;
  }

  .scale-family-row + .scale-family-row {
    margin-top: 0.75rem;
  }

  .color-swatch {
    min-height: 42px;
    padding: 0.625rem 0.75rem;
    font-size: 0.8125rem;
  }
}
</style>
