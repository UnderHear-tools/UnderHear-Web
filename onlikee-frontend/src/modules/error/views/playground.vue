<template>
  <div class="hero">
    <Dropdown side="outside-right">
      <template #trigger>
        <Button>
          <template #leadingVisual>
            <RepoIcon />
          </template>
          {{ label }}
          <template #trailingVisual>
            <TriangleDownIcon />
          </template>
        </Button>
      </template>
      <template #content>
        <ActionList
          selection-variant="single"
          role="listbox"
        >
          <ActionList.Item
            role="option"
            :selected="selectedAction === '1'"
            @select="selectAction('1', '新建文件')"
          >
            新建文件
          </ActionList.Item>
          <ActionList.Item
            role="option"
            :selected="selectedAction === '2'"
            @select="selectAction('2', '打开文件')"
          >
            打开文件
          </ActionList.Item>
          <ActionList.Item
            role="option"
            :selected="selectedAction === '3'"
            @select="selectAction('3', '保存文件')"
          >
            保存文件
          </ActionList.Item>
        </ActionList>
      </template>
    </Dropdown>


    <Dropdown>
      <template #trigger>
        <Button>
          <template #leadingVisual>
            <RepoIcon />
          </template>
          打开菜单
          <template #trailingVisual>
            <TriangleDownIcon />
          </template>
        </Button>
      </template>
      <template #content>
        <ActionList>
          <ActionList.Item>选项1</ActionList.Item>
          <ActionList.Item>选项2</ActionList.Item>
          <Dropdown side="outside-right">
            <template #trigger>
              <ActionList.Item>
                选项3
                <template #trailingVisual>
                  <ChevronRightIcon />
                </template>
              </ActionList.Item>
            </template>
            <template #content>
              <ActionList>
                <ActionList.Item>选项1</ActionList.Item>
                <ActionList.Item>选项2</ActionList.Item>
                <ActionList.Item>选项3</ActionList.Item>
              </ActionList>
            </template>
          </Dropdown>
        </ActionList>
      </template>
    </Dropdown>
  </div>
</template>
<script setup lang="ts">
import { ref } from 'vue'
import { Button } from "@/components/z-ui/button";
import { Dropdown } from "@/components/z-ui/dropdown";
import { ActionList } from "@/components/z-ui/action-list";
import RepoIcon from "@/components/octicons-vue3/icons/repo.vue";
import TriangleDownIcon from "@/components/octicons-vue3/icons/triangle-down.vue";
import { ChevronRightIcon } from '@/components/octicons-vue3'

const selectedAction = ref('1')
const label = ref('新建文件')

function selectAction(value: string, nextLabel: string) {
  selectedAction.value = value
  label.value = nextLabel
}
</script>
<style scoped>
.hero {
  position: absolute;
  right: 0;
  bottom: 0;
}

</style>
