<template>
  <zContainer>
    <h1>Welcome to UnderHear Frontend</h1>
    <p>This is the main index view of the UnderHear frontend application.</p>
    <telescopeFill :size="24" />
  </zContainer>
</template>

<script setup lang="ts">
import { zContainer } from '@/components/z-ui/container/zContainer'
import { telescopeFill } from '@/components/z-ui/icon/Octicons-vue/index.ts'
</script>

<style scoped>
</style>
