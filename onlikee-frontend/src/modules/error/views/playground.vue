<template>
  <Tag
    size="small"
    color="#fff"
    background="red"
    removable
    @remove="Remove"
  >
    <template #visual>
      <StarIcon />
    </template>
    66666
  </Tag>
</template>
<script setup lang="ts">
import { Tag } from "@/components/z-ui/Tag";
import { StarIcon } from "@/components/octicons-vue3";

const Remove = () => {
  console.log('tag removed')
}
</script>
<style scoped>

</style>
