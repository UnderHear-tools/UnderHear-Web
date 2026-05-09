<template>
  <div class="hero">
    <Dropdown side="outside-right">
      <template #trigger>
        <Button>
          <template #leadingVisual>
            <Repo />
          </template>
          {{ label }}
          <template #trailingVisual>
            <TriangleDown />
          </template>
        </Button>
      </template>
      <template #content>
        <ActionList
          v-model="selectedAction"
          selection-mode="single"
        >
          <ActionList.Item
            value="1"
            @click="label = '新建文件'"
          >
            新建文件
          </ActionList.Item>
          <ActionList.Item
            value="2"
            @click="label = '打开文件'"
          >
            打开文件
          </ActionList.Item>
          <ActionList.Item
            value="3"
            @click="label = '保存文件'"
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
            <Repo />
          </template>
          打开菜单
          <template #trailingVisual>
            <TriangleDown />
          </template>
        </Button>
      </template>
      <template #content>
        <ActionList>
          <ActionList.Item>选项1</ActionList.Item>
          <ActionList.Item>选项2</ActionList.Item>
          <Dropdown side="outside-right">
            <template #trigger>
              <ActionList.Item class="select">
                选项3 <ChevronRight color="#59636e" />
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
import Repo from "@/components/z-ui/icon/Octicons-vue/icons/repo.vue";
import TriangleDown from "@/components/z-ui/icon/Octicons-vue/icons/triangle-down.vue";
import { ChevronRight } from '@/components/z-ui/icon/Octicons-vue';

const selectedAction = ref('1')
const label = ref('新建文件')
</script>
<style scoped>
.hero {
  position: absolute;
  right: 0;
  bottom: 0;
}

.select {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
}
</style>
