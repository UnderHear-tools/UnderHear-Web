<template>
  <div class="hero">

    <zDropdown>
      <template #trigger>
        <zButton>
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
        <zMenu>
          <zMenuItem>新建文件</zMenuItem>
          <zMenuItem>打开文件</zMenuItem>
          <zMenuItem>保存文件</zMenuItem>
        </zMenu>
      </template>
    </zDropdown>



  </div>
</template>
<script setup lang="ts">
import { zButton } from '@/components/z-ui/button';
import { zDropdown } from '@/components/z-ui/dropdown';
import { zMenu, zMenuItem } from '@/components/z-ui/menu';
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