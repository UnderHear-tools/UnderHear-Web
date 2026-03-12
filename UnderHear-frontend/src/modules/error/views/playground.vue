<template>
  <div class="hero">

    <zDropdown>
      <template #trigger>
        <zButton variant="link">
          <template #leadingVisual>
            <Repo />
          </template>
          公开的
          <template #trailingVisual>
            <TriangleDown />
          </template>
        </zButton>
      </template>
      <template #content>
        <ActionList>
          <ActionList.Item>新建文件</ActionList.Item>
          <ActionList.Item>打开文件</ActionList.Item>
          <ActionList.Item>保存文件</ActionList.Item>
        </ActionList>
      </template>
    </zDropdown>



  </div>
</template>
<script setup lang="ts">
import { zButton } from '@/components/z-ui/button';
import { zDropdown } from '@/components/z-ui/dropdown';
import { ActionList } from '@/components/z-ui/action-list';
import Repo from '@/components/z-ui/icon/Octicons-vue/icons/repo.vue';
import TriangleDown from '@/components/z-ui/icon/Octicons-vue/icons/triangle-down.vue';


</script>
<style scoped>
.hero{
  height: 80vh;
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>