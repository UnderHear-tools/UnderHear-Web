<template>
  <ComponentDocsPage>
    <ComponentDocsHeader
      title="Dropdown 下拉菜单"
      description="向下弹出的菜单容器。"
    />

    <ComponentDocsSection title="基础用法">
      <template #description>
        通过 <code>trigger</code> 插槽定义触发元素，content 插槽放置下拉内容。
      </template>

      <ComponentDocsDemoBlock :code="demo1Code">
        <div class="dropdown-demo">
          <zDropdown>
            <template #trigger>
              <button class="demo-trigger">
                点击展开
              </button>
            </template>
            <template #content>
              <div class="demo-content">
                <div class="demo-item">
                  选项一
                </div>
                <div class="demo-item">
                  选项二
                </div>
                <div class="demo-item">
                  选项三
                </div>
              </div>
            </template>
          </zDropdown>
        </div>
      </ComponentDocsDemoBlock>
    </ComponentDocsSection>

    <ComponentDocsSection title="弹出方向">
      <template #description>
        通过 <code>placement</code> 设置下拉菜单弹出方向。
      </template>

      <ComponentDocsDemoBlock :code="demoPlacementCode">
        <div class="dropdown-placement-demo">
          <zDropdown placement="left-top">
            <template #trigger>
              <button class="demo-trigger">
                左上
              </button>
            </template>
            <template #content>
              <div class="demo-content">
                左上内容
              </div>
            </template>
          </zDropdown>
          <zDropdown placement="right-top">
            <template #trigger>
              <button class="demo-trigger">
                右上
              </button>
            </template>
            <template #content>
              <div class="demo-content">
                右上内容
              </div>
            </template>
          </zDropdown>
          <zDropdown placement="left-bottom">
            <template #trigger>
              <button class="demo-trigger">
                左下
              </button>
            </template>
            <template #content>
              <div class="demo-content">
                左下内容
              </div>
            </template>
          </zDropdown>
          <zDropdown placement="right-bottom">
            <template #trigger>
              <button class="demo-trigger">
                右下
              </button>
            </template>
            <template #content>
              <div class="demo-content">
                右下内容
              </div>
            </template>
          </zDropdown>
        </div>
      </ComponentDocsDemoBlock>
    </ComponentDocsSection>

    <ComponentDocsSection title="点击内容自动关闭">
      <template #description>
        点击 <code>content</code> 插槽内的任意元素，下拉菜单会自动收起。配合 <code>ActionList</code> 使用时无需手动绑定关闭逻辑。
      </template>

      <ComponentDocsDemoBlock :code="demoAutoCloseCode">
        <div class="dropdown-demo">
          <zDropdown>
            <template #trigger>
              <button class="demo-trigger">
                点击展开
              </button>
            </template>
            <template #content>
              <div class="demo-content">
                <div class="demo-item">
                  选项一（点击自动关闭）
                </div>
                <div class="demo-item">
                  选项二（点击自动关闭）
                </div>
              </div>
            </template>
          </zDropdown>
        </div>
      </ComponentDocsDemoBlock>
    </ComponentDocsSection>

    <ComponentDocsSection title="编程式关闭">
      <template #description>
        通过 template ref 获取组件实例，调用暴露的 <code>close()</code> 方法可在任意时机关闭下拉菜单。
      </template>

      <ComponentDocsDemoBlock :code="demoExposeCode">
        <div class="dropdown-demo">
          <zDropdown ref="exposeDropdownRef">
            <template #trigger>
              <button class="demo-trigger">
                点击展开
              </button>
            </template>
            <template #content>
              <div class="demo-content">
                <div
                  class="demo-item"
                  @click="exposeDropdownRef?.close()"
                >
                  点击关闭
                </div>
              </div>
            </template>
          </zDropdown>
        </div>
      </ComponentDocsDemoBlock>
    </ComponentDocsSection>

    <ComponentDocsSection
      title="API"
      variant="api"
    >
      <h4>Props 属性</h4>
      <zTable
        :columns="propsTableColumns"
        :data="propsTableRows"
        row-key="name"
        compact
        :hoverable="false"
      />
      <h4 style="margin-top: 24px;">
        Slots 插槽
      </h4>
      <zTable
        :columns="slotsTableColumns"
        :data="slotsTableRows"
        row-key="name"
        compact
        :hoverable="false"
      />
      <h4 style="margin-top: 24px;">
        Expose 方法
      </h4>
      <zTable
        :columns="exposeTableColumns"
        :data="exposeTableRows"
        row-key="name"
        compact
        :hoverable="false"
      />
    </ComponentDocsSection>
  </ComponentDocsPage>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { zDropdown } from '@/components/z-ui/dropdown'
import { zTable, type ZTableColumn } from '@/components/z-ui/table'
import ComponentDocsDemoBlock from '@/modules/components/components/ComponentDocsPage/ComponentDocsDemoBlock.vue'
import ComponentDocsHeader from '@/modules/components/components/ComponentDocsPage/ComponentDocsHeader.vue'
import ComponentDocsPage from '@/modules/components/components/ComponentDocsPage/ComponentDocsPage.vue'
import ComponentDocsSection from '@/modules/components/components/ComponentDocsPage/ComponentDocsSection.vue'

const exposeDropdownRef = ref<InstanceType<typeof zDropdown>>()

const demo1Code = `<template>
  <div class="dropdown-demo">
    <zDropdown>
      <template #trigger>
        <button class="demo-trigger">点击展开</button>
      </template>
      <template #content>
        <div class="demo-content">
          <div class="demo-item">选项一</div>
          <div class="demo-item">选项二</div>
          <div class="demo-item">选项三</div>
        </div>
      </template>
    </zDropdown>
  </div>
</template>

<script setup lang="ts">
import { zDropdown } from '@/components/z-ui/dropdown'
<\/script>

<style scoped>
.dropdown-demo {
  position: absolute;
}

.demo-trigger {
  padding: 8px 16px;
  border: 1px solid var(--borderColor-default);
  border-radius: 6px;
  background: var(--bgColor-default);
  font-size: 14px;
  cursor: pointer;
}

.demo-trigger:hover {
  background: var(--bgColor-muted);
}

.demo-content {
  padding: 8px;
  min-width: 120px;
}

.demo-item {
  padding: 6px 12px;
  border-radius: 4px;
  font-size: 14px;
  cursor: pointer;
}

.demo-item:hover {
  background: var(--bgColor-muted);
}
<\/style>`

const demoPlacementCode = `<template>
  <div class="dropdown-placement-demo">
    <zDropdown placement="left-top">
      <template #trigger>
        <button class="demo-trigger">左上</button>
      </template>
      <template #content>
        <div class="demo-content">左上内容</div>
      </template>
    </zDropdown>
    <zDropdown placement="right-top">
      <template #trigger>
        <button class="demo-trigger">右上</button>
      </template>
      <template #content>
        <div class="demo-content">右上内容</div>
      </template>
    </zDropdown>
    <zDropdown placement="left-bottom">
      <template #trigger>
        <button class="demo-trigger">左下</button>
      </template>
      <template #content>
        <div class="demo-content">左下内容</div>
      </template>
    </zDropdown>
    <zDropdown placement="right-bottom">
      <template #trigger>
        <button class="demo-trigger">右下</button>
      </template>
      <template #content>
        <div class="demo-content">右下内容</div>
      </template>
    </zDropdown>
  </div>
</template>

<script setup lang="ts">
import { zDropdown } from '@/components/z-ui/dropdown'
<\/script>

<style scoped>
.dropdown-placement-demo {
  display: flex;
  gap: 20px;
}

.demo-trigger {
  padding: 8px 16px;
  border: 1px solid var(--borderColor-default);
  border-radius: 6px;
  background: var(--bgColor-default);
  font-size: 14px;
  cursor: pointer;
}

.demo-trigger:hover {
  background: var(--bgColor-muted);
}

.demo-content {
  padding: 8px;
  min-width: 120px;
}
<\/style>`

const propsTableColumns: ZTableColumn[] = [
  { key: 'name', label: '属性名', rowHeader: true, minWidth: '140px' },
  { key: 'description', label: '说明', minWidth: '220px', wrap: true },
  { key: 'type', label: '类型', minWidth: '220px', wrap: true },
  { key: 'default', label: '默认值', minWidth: '120px' }
]

const propsTableRows = [
  {
    name: 'placement',
    description: '下拉菜单弹出方向',
    type: `'left-top' | 'right-top' | 'left-bottom' | 'right-bottom'`,
    default: 'right-bottom'
  }
]

const slotsTableColumns: ZTableColumn[] = [
  { key: 'name', label: '插槽名', rowHeader: true, minWidth: '140px' },
  { key: 'description', label: '说明', minWidth: '300px', wrap: true }
]

const slotsTableRows = [
  {
    name: 'trigger',
    description: '触发下拉的元素，点击该元素会展开/收起下拉内容'
  },
  {
    name: 'content',
    description: '下拉菜单的内容，点击内容区域会自动关闭下拉菜单'
  }
]

const exposeTableColumns: ZTableColumn[] = [
  { key: 'name', label: '方法名', rowHeader: true, minWidth: '140px' },
  { key: 'description', label: '说明', minWidth: '300px', wrap: true }
]

const exposeTableRows = [
  {
    name: 'close()',
    description: '关闭下拉菜单，可通过 template ref 调用'
  }
]

const demoAutoCloseCode = `<template>
  <zDropdown>
    <template #trigger>
      <button>点击展开</button>
    </template>
    <template #content>
      <div class="demo-item">选项一（点击自动关闭）</div>
      <div class="demo-item">选项二（点击自动关闭）</div>
    </template>
  </zDropdown>
</template>

<script setup lang="ts">
import { zDropdown } from '@/components/z-ui/dropdown'
<\/script>`

const demoExposeCode = `<template>
  <zDropdown ref="dropdownRef">
    <template #trigger>
      <button>点击展开</button>
    </template>
    <template #content>
      <div @click="dropdownRef?.close()">点击关闭</div>
    </template>
  </zDropdown>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { zDropdown } from '@/components/z-ui/dropdown'

const dropdownRef = ref<InstanceType<typeof zDropdown>>()
<\/script>`
</script>

<style scoped>
.dropdown-demo {
  position: absolute;
}

.dropdown-placement-demo {
  display: flex;
  gap: 20px;
}

.demo-trigger {
  padding: 8px 16px;
  border: 1px solid var(--borderColor-default);
  border-radius: 6px;
  background: var(--control-transparent-bgColor-rest, #ffffff00);
  font-size: 14px;
  cursor: pointer;
}

.demo-trigger:hover {
  background: var(--control-transparent-bgColor-hover, #818b981a);
}

.demo-content {
  padding: 8px;
  min-width: 120px;
}

.demo-item {
  padding: 6px 12px;
  border-radius: 4px;
  font-size: 14px;
  cursor: pointer;
}

.demo-item:hover {
  background: var(--control-transparent-bgColor-hover, #818b981a);
}

h4 {
  margin: 0 0 12px;
  font-size: 14px;
  font-weight: 600;
  color: var(--fgColor-default);
}
</style>
