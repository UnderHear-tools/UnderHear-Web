<template>
  <ComponentDocsPage>
    <ComponentDocsHeader
      title="Dropdown 下拉菜单"
      description="向下弹出的菜单容器。"
    />

    <ComponentDocsSection title="基础用法">
      <template #description>
        通过 <code>Dropdown.trigger</code> 子组件定义触发元素，<code>Dropdown.content</code> 子组件放置下拉内容。
      </template>

      <ComponentDocsDemoBlock :code="demo1Code">
        <div class="dropdown-demo">
          <Dropdown>
            <Dropdown.trigger>
              <button class="demo-trigger">
                点击展开
              </button>
            </Dropdown.trigger>
            <Dropdown.content>
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
            </Dropdown.content>
          </Dropdown>
        </div>
      </ComponentDocsDemoBlock>
    </ComponentDocsSection>

    <ComponentDocsSection title="弹出方向">
      <template #description>
        通过 <code>side</code> 设置下拉菜单弹出方向。
      </template>

      <ComponentDocsDemoBlock :code="demoPlacementCode">
        <div class="dropdown-placement-demo">
          <Dropdown side="inside-top">
            <Dropdown.trigger>
              <button class="demo-trigger">
                内侧上方
              </button>
            </Dropdown.trigger>
            <Dropdown.content>
              <div class="demo-content">
                内侧上方内容
              </div>
            </Dropdown.content>
          </Dropdown>
          <Dropdown side="inside-bottom">
            <Dropdown.trigger>
              <button class="demo-trigger">
                内侧下方
              </button>
            </Dropdown.trigger>
            <Dropdown.content>
              <div class="demo-content">
                内侧下方内容
              </div>
            </Dropdown.content>
          </Dropdown>
          <Dropdown side="inside-left">
            <Dropdown.trigger>
              <button class="demo-trigger">
                内侧左边
              </button>
            </Dropdown.trigger>
            <Dropdown.content>
              <div class="demo-content">
                内侧左边内容
              </div>
            </Dropdown.content>
          </Dropdown>
          <Dropdown side="inside-right">
            <Dropdown.trigger>
              <button class="demo-trigger">
                内侧右边
              </button>
            </Dropdown.trigger>
            <Dropdown.content>
              <div class="demo-content">
                内侧右边内容
              </div>
            </Dropdown.content>
          </Dropdown>
          <Dropdown side="inside-center">
            <Dropdown.trigger>
              <button class="demo-trigger">
                内侧居中
              </button>
            </Dropdown.trigger>
            <Dropdown.content>
              <div class="demo-content">
                内侧居中内容
              </div>
            </Dropdown.content>
          </Dropdown>
          <Dropdown side="outside-top">
            <Dropdown.trigger>
              <button class="demo-trigger">
                外侧上方
              </button>
            </Dropdown.trigger>
            <Dropdown.content>
              <div class="demo-content">
                外侧上方内容
              </div>
            </Dropdown.content>
          </Dropdown>
          <Dropdown side="outside-bottom">
            <Dropdown.trigger>
              <button class="demo-trigger">
                外侧下方
              </button>
            </Dropdown.trigger>
            <Dropdown.content>
              <div class="demo-content">
                外侧下方内容
              </div>
            </Dropdown.content>
          </Dropdown>
          <Dropdown side="outside-left">
            <Dropdown.trigger>
              <button class="demo-trigger">
                外侧左边
              </button>
            </Dropdown.trigger>
            <Dropdown.content>
              <div class="demo-content">
                外侧左边内容
              </div>
            </Dropdown.content>
          </Dropdown>
          <Dropdown side="outside-right">
            <Dropdown.trigger>
              <button class="demo-trigger">
                外侧右边
              </button>
            </Dropdown.trigger>
            <Dropdown.content>
              <div class="demo-content">
                外侧右边内容
              </div>
            </Dropdown.content>
          </Dropdown>
        </div>
      </ComponentDocsDemoBlock>
    </ComponentDocsSection>

    <ComponentDocsSection title="点击内容保持展开">
      <template #description>
        点击 <code>Dropdown.content</code> 子组件内的任意元素时，下拉菜单会保持展开；点击组件外部区域才会关闭。
      </template>

      <ComponentDocsDemoBlock :code="demoKeepOpenCode">
        <div class="dropdown-demo">
          <Dropdown>
            <Dropdown.trigger>
              <button class="demo-trigger">
                点击展开
              </button>
            </Dropdown.trigger>
            <Dropdown.content>
              <div class="demo-content">
                <div class="demo-item">
                  选项一（点击后保持展开）
                </div>
                <div class="demo-item">
                  选项二（点击后保持展开）
                </div>
              </div>
            </Dropdown.content>
          </Dropdown>
        </div>
      </ComponentDocsDemoBlock>
    </ComponentDocsSection>

    <ComponentDocsSection title="编程式关闭">
      <template #description>
        通过 template ref 获取组件实例，调用暴露的 <code>close()</code> 方法可在任意时机关闭下拉菜单。
      </template>

      <ComponentDocsDemoBlock :code="demoExposeCode">
        <div class="dropdown-demo">
          <Dropdown ref="exposeDropdownRef">
            <Dropdown.trigger>
              <button class="demo-trigger">
                点击展开
              </button>
            </Dropdown.trigger>
            <Dropdown.content>
              <div class="demo-content">
                <div
                  class="demo-item"
                  @click="exposeDropdownRef?.close()"
                >
                  点击关闭
                </div>
              </div>
            </Dropdown.content>
          </Dropdown>
        </div>
      </ComponentDocsDemoBlock>
    </ComponentDocsSection>

    <ComponentDocsSection
      title="API"
      variant="api"
    >
      <h4>Props 属性</h4>
      <Table
        :columns="propsTableColumns"
        :data="propsTableRows"
        row-key="name"
        compact
        :hoverable="false"
      />
      <h4 style="margin-top: 24px;">
        内容与子组件
      </h4>
      <Table
        :columns="contentTableColumns"
        :data="contentTableRows"
        row-key="name"
        compact
        :hoverable="false"
      />
      <h4 style="margin-top: 24px;">
        Expose 方法
      </h4>
      <Table
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
import { Dropdown } from '@/components/z-ui/dropdown'
import { Table, type TableColumn } from '@/components/z-ui/table'
import ComponentDocsDemoBlock from '@/modules/components/components/ComponentDocsPage/ComponentDocsDemoBlock.vue'
import ComponentDocsHeader from '@/modules/components/components/ComponentDocsPage/ComponentDocsHeader.vue'
import ComponentDocsPage from '@/modules/components/components/ComponentDocsPage/ComponentDocsPage.vue'
import ComponentDocsSection from '@/modules/components/components/ComponentDocsPage/ComponentDocsSection.vue'

const exposeDropdownRef = ref<InstanceType<typeof Dropdown>>()

const demo1Code = `<template>
  <div class="dropdown-demo">
    <Dropdown>
      <Dropdown.trigger>
        <button class="demo-trigger">点击展开</button>
      </Dropdown.trigger>
      <Dropdown.content>
        <div class="demo-content">
          <div class="demo-item">选项一</div>
          <div class="demo-item">选项二</div>
          <div class="demo-item">选项三</div>
        </div>
      </Dropdown.content>
    </Dropdown>
  </div>
</template>

<script setup lang="ts">
import { Dropdown } from '@/components/z-ui/dropdown'
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
    <Dropdown side="inside-top">
      <Dropdown.trigger>
        <button class="demo-trigger">内侧上方</button>
      </Dropdown.trigger>
      <Dropdown.content>
        <div class="demo-content">内侧上方内容</div>
      </Dropdown.content>
    </Dropdown>
    <Dropdown side="inside-bottom">
      <Dropdown.trigger>
        <button class="demo-trigger">内侧下方</button>
      </Dropdown.trigger>
      <Dropdown.content>
        <div class="demo-content">内侧下方内容</div>
      </Dropdown.content>
    </Dropdown>
    <Dropdown side="inside-left">
      <Dropdown.trigger>
        <button class="demo-trigger">内侧左边</button>
      </Dropdown.trigger>
      <Dropdown.content>
        <div class="demo-content">内侧左边内容</div>
      </Dropdown.content>
    </Dropdown>
    <Dropdown side="inside-right">
      <Dropdown.trigger>
        <button class="demo-trigger">内侧右边</button>
      </Dropdown.trigger>
      <Dropdown.content>
        <div class="demo-content">内侧右边内容</div>
      </Dropdown.content>
    </Dropdown>
    <Dropdown side="inside-center">
      <Dropdown.trigger>
        <button class="demo-trigger">内侧居中</button>
      </Dropdown.trigger>
      <Dropdown.content>
        <div class="demo-content">内侧居中内容</div>
      </Dropdown.content>
    </Dropdown>
    <Dropdown side="outside-top">
      <Dropdown.trigger>
        <button class="demo-trigger">外侧上方</button>
      </Dropdown.trigger>
      <Dropdown.content>
        <div class="demo-content">外侧上方内容</div>
      </Dropdown.content>
    </Dropdown>
    <Dropdown side="outside-bottom">
      <Dropdown.trigger>
        <button class="demo-trigger">外侧下方</button>
      </Dropdown.trigger>
      <Dropdown.content>
        <div class="demo-content">外侧下方内容</div>
      </Dropdown.content>
    </Dropdown>
    <Dropdown side="outside-left">
      <Dropdown.trigger>
        <button class="demo-trigger">外侧左边</button>
      </Dropdown.trigger>
      <Dropdown.content>
        <div class="demo-content">外侧左边内容</div>
      </Dropdown.content>
    </Dropdown>
    <Dropdown side="outside-right">
      <Dropdown.trigger>
        <button class="demo-trigger">外侧右边</button>
      </Dropdown.trigger>
      <Dropdown.content>
        <div class="demo-content">外侧右边内容</div>
      </Dropdown.content>
    </Dropdown>
  </div>
</template>

<script setup lang="ts">
import { Dropdown } from '@/components/z-ui/dropdown'
<\/script>

<style scoped>
.dropdown-placement-demo {
  display: grid;
  grid-template-columns: repeat(3, minmax(0, 1fr));
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

const propsTableColumns: TableColumn[] = [
  { key: 'name', label: '属性名', rowHeader: true, minWidth: '140px' },
  { key: 'default', label: '默认值', minWidth: '120px' },
  { key: 'type', label: '类型', minWidth: '220px', wrap: true },
  { key: 'description', label: '说明', minWidth: '220px', wrap: true }
]

const propsTableRows = [
  {
    name: 'side',
    description: '下拉菜单弹出方向',
    type: `'inside-top' | 'inside-bottom' | 'inside-left' | 'inside-right' | 'inside-center' | 'outside-top' | 'outside-bottom' | 'outside-left' | 'outside-right'`,
    default: 'outside-bottom'
  }
]

const contentTableColumns: TableColumn[] = [
  { key: 'name', label: '名称', rowHeader: true, minWidth: '180px' },
  { key: 'description', label: '说明', minWidth: '300px', wrap: true }
]

const contentTableRows = [
  {
    name: 'Dropdown.trigger',
    description: '触发下拉的元素子组件，点击该元素会展开/收起下拉内容'
  },
  {
    name: 'Dropdown.content',
    description: '下拉菜单的内容子组件，点击内容区域会保持展开，点击组件外部区域会关闭'
  }
]

const exposeTableColumns: TableColumn[] = [
  { key: 'name', label: '方法名', rowHeader: true, minWidth: '140px' },
  { key: 'description', label: '说明', minWidth: '300px', wrap: true }
]

const exposeTableRows = [
  {
    name: 'close()',
    description: '关闭下拉菜单，可通过 template ref 调用'
  }
]

const demoKeepOpenCode = `<template>
  <Dropdown>
    <Dropdown.trigger>
      <button>点击展开</button>
    </Dropdown.trigger>
    <Dropdown.content>
      <div class="demo-item">选项一（点击后保持展开）</div>
      <div class="demo-item">选项二（点击后保持展开）</div>
    </Dropdown.content>
  </Dropdown>
</template>

<script setup lang="ts">
import { Dropdown } from '@/components/z-ui/dropdown'
<\/script>`

const demoExposeCode = `<template>
  <Dropdown ref="dropdownRef">
    <Dropdown.trigger>
      <button>点击展开</button>
    </Dropdown.trigger>
    <Dropdown.content>
      <div @click="dropdownRef?.close()">点击关闭</div>
    </Dropdown.content>
  </Dropdown>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { Dropdown } from '@/components/z-ui/dropdown'

const dropdownRef = ref<InstanceType<typeof Dropdown>>()
<\/script>`
</script>

<style scoped>
.dropdown-demo {
  position: absolute;
}

.dropdown-placement-demo {
  display: grid;
  grid-template-columns: repeat(3, minmax(0, 1fr));
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
