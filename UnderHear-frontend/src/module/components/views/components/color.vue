<template>
  <div class="color-page">
    <div class="page-header">
      <h1 class="page-title">Color 色彩</h1>
      <p class="page-description">为了避免视觉传达差异，使用一套特定的调色板来规定颜色，为你所搭建的产品提供一致的外观视觉感受。</p>
    </div>

    <div class="demo-section">
      <h2 class="section-title">主色</h2>
      <p class="section-description">主色是品牌色彩最直观的视觉元素，在你的产品中应该是极其有限的。</p>
      <div class="demo-container">
        <div class="color-palette">
          <div class="color-item">
            <div class="color-block primary-color"></div>
            <div class="color-info">
              <h4>Brand Blue</h4>
              <p>#3b82f6</p>
              <p>RGB(59, 130, 246)</p>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="demo-section">
      <h2 class="section-title">辅助色</h2>
      <p class="section-description">辅助色是具有代表性的颜色，常用于信息提示，比如成功、警告和失败。</p>
      <div class="demo-container">
        <div class="color-palette">
          <div class="color-item">
            <div class="color-block success-color"></div>
            <div class="color-info">
              <h4>Success</h4>
              <p>#10b981</p>
              <p>RGB(16, 185, 129)</p>
            </div>
          </div>
          <div class="color-item">
            <div class="color-block warning-color"></div>
            <div class="color-info">
              <h4>Warning</h4>
              <p>#f59e0b</p>
              <p>RGB(245, 158, 11)</p>
            </div>
          </div>
          <div class="color-item">
            <div class="color-block danger-color"></div>
            <div class="color-info">
              <h4>Danger</h4>
              <p>#ef4444</p>
              <p>RGB(239, 68, 68)</p>
            </div>
          </div>
          <div class="color-item">
            <div class="color-block info-color"></div>
            <div class="color-info">
              <h4>Info</h4>
              <p>#6b7280</p>
              <p>RGB(107, 114, 128)</p>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="demo-section">
      <h2 class="section-title">中性色</h2>
      <p class="section-description">中性色用于文本、背景和边框颜色。通过运用不同的中性色，来表现层次结构。</p>
      <div class="demo-container">
        <div class="color-palette">
          <div class="color-item">
            <div class="color-block neutral-900"></div>
            <div class="color-info">
              <h4>主要文字</h4>
              <p>#111827</p>
              <p>RGB(17, 24, 39)</p>
            </div>
          </div>
          <div class="color-item">
            <div class="color-block neutral-700"></div>
            <div class="color-info">
              <h4>常规文字</h4>
              <p>#374151</p>
              <p>RGB(55, 65, 81)</p>
            </div>
          </div>
          <div class="color-item">
            <div class="color-block neutral-500"></div>
            <div class="color-info">
              <h4>次要文字</h4>
              <p>#6b7280</p>
              <p>RGB(107, 114, 128)</p>
            </div>
          </div>
          <div class="color-item">
            <div class="color-block neutral-400"></div>
            <div class="color-info">
              <h4>占位文字</h4>
              <p>#9ca3af</p>
              <p>RGB(156, 163, 175)</p>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="demo-section">
      <h2 class="section-title">背景色</h2>
      <p class="section-description">背景色用于页面、卡片、弹窗等容器的背景。</p>
      <div class="demo-container">
        <div class="color-palette">
          <div class="color-item">
            <div class="color-block bg-white"></div>
            <div class="color-info">
              <h4>基础白色</h4>
              <p>#ffffff</p>
              <p>RGB(255, 255, 255)</p>
            </div>
          </div>
          <div class="color-item">
            <div class="color-block bg-gray-50"></div>
            <div class="color-info">
              <h4>浅灰背景</h4>
              <p>#f9fafb</p>
              <p>RGB(249, 250, 251)</p>
            </div>
          </div>
          <div class="color-item">
            <div class="color-block bg-gray-100"></div>
            <div class="color-info">
              <h4>页面背景</h4>
              <p>#f3f4f6</p>
              <p>RGB(243, 244, 246)</p>
            </div>
          </div>
          <div class="color-item">
            <div class="color-block bg-gray-200"></div>
            <div class="color-info">
              <h4>分割背景</h4>
              <p>#e5e7eb</p>
              <p>RGB(229, 231, 235)</p>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="api-section">
      <h2 class="section-title">使用建议</h2>
      <div class="usage-tips">
        <div class="tip-item">
          <h4>主色使用</h4>
          <p>主色在界面中的占比应该控制在10%以下，主要用于关键行动点、操作状态、重要信息突出等场景。</p>
        </div>
        <div class="tip-item">
          <h4>辅助色使用</h4>
          <p>辅助色主要用于信息提示、状态标识等，应该谨慎使用，避免过度使用造成视觉疲劳。</p>
        </div>
        <div class="tip-item">
          <h4>中性色使用</h4>
          <p>中性色是界面中占比最大的颜色，用于文本、边框、背景等，应该保持层次分明。</p>
        </div>
        <div class="tip-item">
          <h4>对比度</h4>
          <p>确保文字与背景之间有足够的对比度，以保证可读性和可访问性。</p>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.color-page {
  padding: 2rem;
  max-width: 1200px;
  margin: 0 auto;
}

.page-header {
  margin-bottom: 2rem;
  border-bottom: 1px solid #e5e7eb;
  padding-bottom: 1rem;
}

.page-title {
  font-size: 2rem;
  font-weight: 600;
  color: #1f2937;
  margin: 0 0 0.5rem 0;
}

.page-description {
  font-size: 1rem;
  color: #6b7280;
  margin: 0;
}

.demo-section {
  margin-bottom: 3rem;
}

.section-title {
  font-size: 1.25rem;
  font-weight: 600;
  color: #1f2937;
  margin: 0 0 0.5rem 0;
}

.section-description {
  font-size: 0.875rem;
  color: #6b7280;
  margin: 0 0 1rem 0;
}

.demo-container {
  padding: 1.5rem;
  border: 1px solid #e5e7eb;
  border-radius: 8px;
  background: #fff;
  margin-bottom: 1rem;
}

.color-palette {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 1.5rem;
}

.color-item {
  display: flex;
  align-items: center;
  gap: 1rem;
}

.color-block {
  width: 60px;
  height: 60px;
  border-radius: 8px;
  border: 1px solid #e5e7eb;
  flex-shrink: 0;
}

.color-info h4 {
  margin: 0 0 0.25rem 0;
  font-size: 0.875rem;
  font-weight: 600;
  color: #1f2937;
}

.color-info p {
  margin: 0;
  font-size: 0.75rem;
  color: #6b7280;
  font-family: 'Monaco', 'Menlo', 'Ubuntu Mono', monospace;
}

/* 颜色定义 */
.primary-color {
  background-color: #3b82f6;
}

.success-color {
  background-color: #10b981;
}

.warning-color {
  background-color: #f59e0b;
}

.danger-color {
  background-color: #ef4444;
}

.info-color {
  background-color: #6b7280;
}

.neutral-900 {
  background-color: #111827;
}

.neutral-700 {
  background-color: #374151;
}

.neutral-500 {
  background-color: #6b7280;
}

.neutral-400 {
  background-color: #9ca3af;
}

.bg-white {
  background-color: #ffffff;
}

.bg-gray-50 {
  background-color: #f9fafb;
}

.bg-gray-100 {
  background-color: #f3f4f6;
}

.bg-gray-200 {
  background-color: #e5e7eb;
}

.api-section {
  margin-top: 3rem;
}

.usage-tips {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 1.5rem;
}

.tip-item {
  padding: 1.5rem;
  border: 1px solid #e5e7eb;
  border-radius: 8px;
  background: #f9fafb;
}

.tip-item h4 {
  margin: 0 0 0.5rem 0;
  font-size: 1rem;
  font-weight: 600;
  color: #1f2937;
}

.tip-item p {
  margin: 0;
  font-size: 0.875rem;
  color: #6b7280;
  line-height: 1.5;
}
</style>