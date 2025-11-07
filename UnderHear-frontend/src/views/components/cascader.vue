<template>
  <div class="cascader-demo">
    <div class="demo-header">
      <h1>Cascader 级联选择器</h1>
      <p>当一个数据集合有清晰的层级结构时，可通过级联选择器逐级查看并选择。</p>
    </div>

    <div class="demo-section">
      <h2>基础用法</h2>
      <p>有两种触发子菜单的方式</p>
      <div class="demo-block">
        <ZCascader
          v-model="value1"
          :options="options"
          placeholder="请选择"
        />
        <ZCascader
          v-model="value2"
          :options="options"
          placeholder="悬停触发"
          expand-trigger="hover"
        />
      </div>
    </div>

    <div class="demo-section">
      <h2>禁用选项</h2>
      <p>通过在数据源中设置 disabled 字段来声明该选项是禁用的</p>
      <div class="demo-block">
        <ZCascader
          v-model="value3"
          :options="disabledOptions"
          placeholder="请选择"
        />
      </div>
    </div>

    <div class="demo-section">
      <h2>可清空</h2>
      <p>通过 clearable 设置输入框可清空</p>
      <div class="demo-block">
        <ZCascader
          v-model="value4"
          :options="options"
          placeholder="请选择"
          clearable
        />
      </div>
    </div>

    <div class="demo-section">
      <h2>仅显示最后一级</h2>
      <p>可以仅在输入框中显示选中项最后一级的标签，而不是选中项所在的完整路径</p>
      <div class="demo-block">
        <ZCascader
          v-model="value5"
          :options="options"
          placeholder="请选择"
          :show-all-levels="false"
        />
      </div>
    </div>

    <div class="demo-section">
      <h2>多选</h2>
      <p>可通过 props.multiple = true 来开启多选模式</p>
      <div class="demo-block">
        <ZCascader
          v-model="value6"
          :options="options"
          placeholder="请选择"
          :props="{ multiple: true }"
          clearable
        />
      </div>
    </div>

    <div class="demo-section">
      <h2>选择任意一级选项</h2>
      <p>在单选模式下，你只能选择叶子节点；而在多选模式下，勾选父节点真正选中的都是叶子节点</p>
      <div class="demo-block">
        <ZCascader
          v-model="value7"
          :options="options"
          placeholder="请选择"
          :props="{ checkStrictly: true }"
          clearable
        />
      </div>
    </div>

    <div class="demo-section">
      <h2>动态加载</h2>
      <p>当选中某一级时，动态加载该级下的选项</p>
      <div class="demo-block">
        <ZCascader
          v-model="value8"
          :options="dynamicOptions"
          placeholder="请选择"
          :props="{ lazy: true, lazyLoad }"
        />
      </div>
    </div>

    <div class="demo-section">
      <h2>可搜索</h2>
      <p>可以快捷地搜索选项并选择</p>
      <div class="demo-block">
        <ZCascader
          v-model="value9"
          :options="options"
          placeholder="请选择"
          filterable
        />
      </div>
    </div>

    <div class="demo-section">
      <h2>自定义节点内容</h2>
      <p>可以自定义备选项的节点内容</p>
      <div class="demo-block">
        <ZCascader
          v-model="value10"
          :options="options"
          placeholder="请选择"
        >   
          <template #default="{ node, data }">
            <span>{{ data.label }}</span>
            <span v-if="!node.isLeaf && data.children"> ({{ data.children.length }}) </span>
          </template>
        </ZCascader>
      </div>
    </div>

    <div class="demo-section">
      <h2>级联面板</h2>
      <p>级联面板是级联选择器的核心组件，与级联选择器一样，有单选、多选、动态加载等多种功能</p>
      <div class="demo-block">
        <ZCascaderPanel
          v-model="value11"
          :options="options"
        />
      </div>
    </div>

    <div class="api-section">
      <h2>API</h2>
      
      <h3>Cascader Attributes</h3>
      <table class="api-table">
        <thead>
          <tr>
            <th>参数</th>
            <th>说明</th>
            <th>类型</th>
            <th>可选值</th>
            <th>默认值</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td>model-value / v-model</td>
            <td>选中项绑定值</td>
            <td>string / number / array</td>
            <td>—</td>
            <td>—</td>
          </tr>
          <tr>
            <td>options</td>
            <td>可选项数据源，键名可通过 Props 属性配置</td>
            <td>array</td>
            <td>—</td>
            <td>—</td>
          </tr>
          <tr>
            <td>props</td>
            <td>配置选项</td>
            <td>object</td>
            <td>—</td>
            <td>—</td>
          </tr>
          <tr>
            <td>size</td>
            <td>尺寸</td>
            <td>string</td>
            <td>large / default / small</td>
            <td>default</td>
          </tr>
          <tr>
            <td>placeholder</td>
            <td>输入框占位文本</td>
            <td>string</td>
            <td>—</td>
            <td>请选择</td>
          </tr>
          <tr>
            <td>disabled</td>
            <td>是否禁用</td>
            <td>boolean</td>
            <td>—</td>
            <td>false</td>
          </tr>
          <tr>
            <td>clearable</td>
            <td>是否支持清空选项</td>
            <td>boolean</td>
            <td>—</td>
            <td>false</td>
          </tr>
          <tr>
            <td>show-all-levels</td>
            <td>输入框中是否显示选中值的完整路径</td>
            <td>boolean</td>
            <td>—</td>
            <td>true</td>
          </tr>
          <tr>
            <td>collapse-tags</td>
            <td>多选模式下是否折叠Tag</td>
            <td>boolean</td>
            <td>—</td>
            <td>false</td>
          </tr>
          <tr>
            <td>separator</td>
            <td>选项分隔符</td>
            <td>string</td>
            <td>—</td>
            <td>' / '</td>
          </tr>
          <tr>
            <td>filterable</td>
            <td>是否可搜索选项</td>
            <td>boolean</td>
            <td>—</td>
            <td>—</td>
          </tr>
          <tr>
            <td>filter-method</td>
            <td>自定义搜索逻辑</td>
            <td>function(node, keyword)</td>
            <td>—</td>
            <td>—</td>
          </tr>
          <tr>
            <td>debounce</td>
            <td>搜索关键词输入的去抖延迟，毫秒</td>
            <td>number</td>
            <td>—</td>
            <td>300</td>
          </tr>
          <tr>
            <td>before-filter</td>
            <td>筛选之前的钩子，参数为输入的值，若返回 false 或者返回 Promise 且被 reject，则停止筛选</td>
            <td>function(value)</td>
            <td>—</td>
            <td>—</td>
          </tr>
          <tr>
            <td>popper-class</td>
            <td>自定义浮层类名</td>
            <td>string</td>
            <td>—</td>
            <td>—</td>
          </tr>
        </tbody>
      </table>

      <h3>Cascader Events</h3>
      <table class="api-table">
        <thead>
          <tr>
            <th>事件名称</th>
            <th>说明</th>
            <th>回调参数</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td>change</td>
            <td>当选中节点变化时触发</td>
            <td>选中节点的值</td>
          </tr>
          <tr>
            <td>expand-change</td>
            <td>当展开节点发生变化时触发</td>
            <td>各父级选项值组成的数组</td>
          </tr>
          <tr>
            <td>blur</td>
            <td>当失去焦点时触发</td>
            <td>(event: Event)</td>
          </tr>
          <tr>
            <td>focus</td>
            <td>当获得焦点时触发</td>
            <td>(event: Event)</td>
          </tr>
          <tr>
            <td>visible-change</td>
            <td>下拉框出现/隐藏时触发</td>
            <td>出现则为 true，隐藏则为 false</td>
          </tr>
          <tr>
            <td>remove-tag</td>
            <td>在多选模式下，移除Tag时触发</td>
            <td>移除的Tag对应的节点的值</td>
          </tr>
        </tbody>
      </table>

      <h3>Cascader Methods</h3>
      <table class="api-table">
        <thead>
          <tr>
            <th>方法名</th>
            <th>说明</th>
            <th>参数</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td>getCheckedNodes</td>
            <td>获取选中的节点</td>
            <td>(leafOnly) 是否只是叶子节点，默认值为 false</td>
          </tr>
        </tbody>
      </table>

      <h3>Cascader Slots</h3>
      <table class="api-table">
        <thead>
          <tr>
            <th>插槽名</th>
            <th>说明</th>
            <th>参数</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td>default</td>
            <td>自定义备选项的节点内容，参数为 { node, data }，分别为当前节点的 Node 对象和数据</td>
            <td>{ node, data }</td>
          </tr>
          <tr>
            <td>empty</td>
            <td>无匹配选项时的内容</td>
            <td>—</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import ZCascader from '@/components/z-ui/cascader/zCascader/zCascader.vue'
import ZCascaderPanel from '@/components/z-ui/cascader/zCascaderPanel/zCascaderPanel.vue'

const value1 = ref([])
const value2 = ref([])
const value3 = ref([])
const value4 = ref([])
const value5 = ref([])
const value6 = ref([])
const value7 = ref([])
const value8 = ref([])
const value9 = ref([])
const value10 = ref([])
const value11 = ref([])

const options = [
  {
    value: 'guide',
    label: '指南',
    children: [
      {
        value: 'disciplines',
        label: '设计原则',
        children: [
          {
            value: 'consistency',
            label: '一致'
          },
          {
            value: 'feedback',
            label: '反馈'
          },
          {
            value: 'efficiency',
            label: '效率'
          },
          {
            value: 'controllability',
            label: '可控'
          }
        ]
      },
      {
        value: 'navigation',
        label: '导航',
        children: [
          {
            value: 'side-nav',
            label: '侧向导航'
          },
          {
            value: 'top-nav',
            label: '顶部导航'
          }
        ]
      }
    ]
  },
  {
    value: 'component',
    label: '组件',
    children: [
      {
        value: 'basic',
        label: 'Basic',
        children: [
          {
            value: 'layout',
            label: 'Layout 布局'
          },
          {
            value: 'color',
            label: 'Color 色彩'
          },
          {
            value: 'typography',
            label: 'Typography 字体'
          },
          {
            value: 'icon',
            label: 'Icon 图标'
          },
          {
            value: 'button',
            label: 'Button 按钮'
          }
        ]
      },
      {
        value: 'form',
        label: 'Form',
        children: [
          {
            value: 'radio',
            label: 'Radio 单选框'
          },
          {
            value: 'checkbox',
            label: 'Checkbox 多选框'
          },
          {
            value: 'input',
            label: 'Input 输入框'
          },
          {
            value: 'input-number',
            label: 'InputNumber 计数器'
          },
          {
            value: 'select',
            label: 'Select 选择器'
          },
          {
            value: 'cascader',
            label: 'Cascader 级联选择器'
          },
          {
            value: 'switch',
            label: 'Switch 开关'
          },
          {
            value: 'slider',
            label: 'Slider 滑块'
          },
          {
            value: 'time-picker',
            label: 'TimePicker 时间选择器'
          },
          {
            value: 'date-picker',
            label: 'DatePicker 日期选择器'
          },
          {
            value: 'datetime-picker',
            label: 'DateTimePicker 日期时间选择器'
          },
          {
            value: 'upload',
            label: 'Upload 上传'
          },
          {
            value: 'rate',
            label: 'Rate 评分'
          },
          {
            value: 'form',
            label: 'Form 表单'
          }
        ]
      },
      {
        value: 'data',
        label: 'Data',
        children: [
          {
            value: 'table',
            label: 'Table 表格'
          },
          {
            value: 'tag',
            label: 'Tag 标签'
          },
          {
            value: 'progress',
            label: 'Progress 进度条'
          },
          {
            value: 'tree',
            label: 'Tree 树形控件'
          },
          {
            value: 'pagination',
            label: 'Pagination 分页'
          },
          {
            value: 'badge',
            label: 'Badge 标记'
          }
        ]
      },
      {
        value: 'notice',
        label: 'Notice',
        children: [
          {
            value: 'alert',
            label: 'Alert 警告'
          },
          {
            value: 'loading',
            label: 'Loading 加载'
          },
          {
            value: 'message',
            label: 'Message 消息提示'
          },
          {
            value: 'message-box',
            label: 'MessageBox 弹框'
          },
          {
            value: 'notification',
            label: 'Notification 通知'
          }
        ]
      },
      {
        value: 'navigation',
        label: 'Navigation',
        children: [
          {
            value: 'menu',
            label: 'Menu 导航菜单'
          },
          {
            value: 'tabs',
            label: 'Tabs 标签页'
          },
          {
            value: 'breadcrumb',
            label: 'Breadcrumb 面包屑'
          },
          {
            value: 'dropdown',
            label: 'Dropdown 下拉菜单'
          },
          {
            value: 'steps',
            label: 'Steps 步骤条'
          }
        ]
      },
      {
        value: 'others',
        label: 'Others',
        children: [
          {
            value: 'dialog',
            label: 'Dialog 对话框'
          },
          {
            value: 'tooltip',
            label: 'Tooltip 文字提示'
          },
          {
            value: 'popover',
            label: 'Popover 弹出框'
          },
          {
            value: 'card',
            label: 'Card 卡片'
          },
          {
            value: 'carousel',
            label: 'Carousel 走马灯'
          },
          {
            value: 'collapse',
            label: 'Collapse 折叠面板'
          }
        ]
      }
    ]
  },
  {
    value: 'resource',
    label: '资源',
    children: [
      {
        value: 'axure',
        label: 'Axure Components'
      },
      {
        value: 'sketch',
        label: 'Sketch Templates'
      },
      {
        value: 'docs',
        label: '组件文档'
      }
    ]
  }
]

const disabledOptions = [
  {
    value: 'guide',
    label: '指南',
    disabled: true,
    children: [
      {
        value: 'disciplines',
        label: '设计原则',
        children: [
          {
            value: 'consistency',
            label: '一致'
          },
          {
            value: 'feedback',
            label: '反馈'
          },
          {
            value: 'efficiency',
            label: '效率'
          },
          {
            value: 'controllability',
            label: '可控'
          }
        ]
      }
    ]
  },
  {
    value: 'component',
    label: '组件',
    children: [
      {
        value: 'basic',
        label: 'Basic',
        disabled: true,
        children: [
          {
            value: 'layout',
            label: 'Layout 布局'
          }
        ]
      }
    ]
  }
]

const dynamicOptions = [
  {
    value: 'beijing',
    label: '北京',
    isLeaf: false
  },
  {
    value: 'shanghai',
    label: '上海',
    isLeaf: false
  },
  {
    value: 'guangzhou',
    label: '广州',
    isLeaf: false
  }
]

const lazyLoad = (node: any, resolve: any) => {
  const { level } = node
  setTimeout(() => {
    const nodes = Array.from({ length: level + 1 })
      .map((_, index) => ({
        value: `选项${index + 1}`,
        label: `选项${index + 1}`,
        isLeaf: level >= 2
      }))
    resolve(nodes)
  }, 1000)
}
</script>

<style scoped>
.cascader-demo {
  padding: 20px;
  max-width: 1200px;
  margin: 0 auto;
}

.demo-header {
  margin-bottom: 40px;
}

.demo-header h1 {
  font-size: 28px;
  color: #303133;
  margin: 0 0 10px 0;
}

.demo-header p {
  font-size: 14px;
  color: #606266;
  margin: 0;
}

.demo-section {
  margin-bottom: 40px;
}

.demo-section h2 {
  font-size: 20px;
  color: #303133;
  margin: 0 0 10px 0;
}

.demo-section p {
  font-size: 14px;
  color: #606266;
  margin: 0 0 20px 0;
}

.demo-block {
  border: 1px solid #ebebeb;
  border-radius: 3px;
  transition: 0.2s;
  padding: 20px;
  background-color: #fff;
}

.demo-block:hover {
  box-shadow: 0 0 8px 0 rgba(232, 237, 250, 0.6), 0 2px 4px 0 rgba(232, 237, 250, 0.5);
}

.demo-block .z-cascader {
  margin-right: 20px;
  margin-bottom: 10px;
}

.api-section {
  margin-top: 60px;
}

.api-section h2 {
  font-size: 24px;
  color: #303133;
  margin: 0 0 20px 0;
  border-bottom: 1px solid #ebeef5;
  padding-bottom: 10px;
}

.api-section h3 {
  font-size: 18px;
  color: #303133;
  margin: 30px 0 15px 0;
}

.api-table {
  width: 100%;
  border-collapse: collapse;
  margin-bottom: 30px;
  font-size: 14px;
}

.api-table th,
.api-table td {
  border: 1px solid #ebeef5;
  padding: 12px 8px;
  text-align: left;
}

.api-table th {
  background-color: #fafafa;
  font-weight: 600;
  color: #303133;
}

.api-table td {
  color: #606266;
}

.api-table code {
  background-color: #f5f7fa;
  padding: 2px 4px;
  border-radius: 3px;
  font-size: 12px;
  color: #e6a23c;
}

@media (max-width: 768px) {
  .cascader-demo {
    padding: 10px;
  }
  
  .demo-block {
    padding: 15px;
  }
  
  .demo-block .z-cascader {
    margin-right: 0;
    margin-bottom: 15px;
    width: 100%;
  }
  
  .api-table {
    font-size: 12px;
  }
  
  .api-table th,
  .api-table td {
    padding: 8px 4px;
  }
}
</style>