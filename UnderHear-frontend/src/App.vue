<script setup lang="ts">
import MainHeader from '@/layout/Header.vue';
import { useRoute } from 'vue-router';
import { computed } from 'vue';

const route = useRoute();

// 判断是否需要隐藏 Header（ComponentLayout 页面和登录页面）
const shouldHideHeader = computed(() => {
  return route.path === '/auth/login' || route.path === '/auth/logout';
});
</script>

<template>
  <div id="app" data-color-mode="light" data-light-theme="light">
    <MainHeader v-if="!shouldHideHeader" />
    <router-view />
  </div>
</template>

<style scoped></style>
