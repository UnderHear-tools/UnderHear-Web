<template>
  <div class="hero">


    <zButton>
      <template #leadingVisual>
        <Repo />
      </template>
      公开的
      <template #trailingVisual>
        <TriangleDown />
      </template>
    </zButton>


  </div>
</template>
<script setup lang="ts">
import { zButton } from '@/components/z-ui/button';
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