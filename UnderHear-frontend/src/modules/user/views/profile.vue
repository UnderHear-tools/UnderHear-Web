<template>
  <zContainer>
    <section class="profile-layout">
      <aside class="profile-sidebar">
        <zAvatar
          :placeholder="avatarPlaceholder"
          :size="128"
        />
        <div class="identity">
          <h1 class="display-name">
            {{ nickname }}
          </h1>
          <p class="handle">
            @{{ nickname }}
          </p>
        </div>
        <div class="profile-meta">
          <div class="meta-item">
            <IconPark
              type="user"
              theme="outline"
              size="18"
              fill="currentColor"
            />
            <span>个人资料</span>
          </div>
          <div class="meta-item">
            <IconPark
              type="link"
              theme="outline"
              size="18"
              fill="currentColor"
            />
            <span>/@{{ nickname }}</span>
          </div>
        </div>
      </aside>

      <main class="profile-main">
        <div class="profile-header">
          <div>
            <p class="eyebrow">
              User profile
            </p>
            <h2>{{ nickname }} 的主页</h2>
          </div>
          <zTag size="large">
            Preview
          </zTag>
        </div>

        <div class="empty-state">
          <h3>资料页已就绪</h3>
          <p>
            当前页面从 <code>/@:nickname</code> 路由读取昵称参数。后续接入后端后，可以在这里展示头像、简介、应用列表和社交链接。
          </p>
        </div>
      </main>
    </section>
  </zContainer>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { useRoute } from 'vue-router'
import { IconPark } from '@icon-park/vue-next/es/all'

import { zAvatar } from '@/components/z-ui/avatar'
import { zContainer } from '@/components/z-ui/container'
import { zTag } from '@/components/z-ui/tag'

const route = useRoute()

const nickname = computed(() => {
    const value = route.params.nickname
    return Array.isArray(value) ? value[0] : value
})

const avatarPlaceholder = computed(() => nickname.value.slice(0, 2).toUpperCase())
</script>

<style scoped>
.profile-layout {
    display: grid;
    grid-template-columns: 280px minmax(0, 1fr);
    gap: 28px;
    align-items: start;
}

.profile-sidebar,
.profile-main {
    border: 1px solid var(--borderColor-default);
    border-radius: 6px;
    background: var(--bgColor-default);
}

.profile-sidebar {
    padding: 24px;
}

.identity {
    margin-top: 18px;
}

.display-name {
    margin: 0;
    color: var(--fgColor-default);
    font-size: 26px;
    font-weight: 600;
    line-height: 1.25;
}

.handle {
    margin: 4px 0 0;
    color: var(--fgColor-muted);
    font-size: 18px;
    line-height: 1.4;
}

.profile-meta {
    display: grid;
    gap: 10px;
    margin-top: 24px;
    color: var(--fgColor-muted);
    font-size: 14px;
}

.meta-item {
    display: flex;
    align-items: center;
    gap: 8px;
    min-width: 0;
}

.meta-item span {
    overflow-wrap: anywhere;
}

.profile-main {
    min-width: 0;
    padding: 24px;
}

.profile-header {
    display: flex;
    align-items: flex-start;
    justify-content: space-between;
    gap: 16px;
    padding-bottom: 20px;
    border-bottom: 1px solid var(--borderColor-default);
}

.profile-header h2 {
    margin: 2px 0 0;
    color: var(--fgColor-default);
    font-size: 24px;
    font-weight: 600;
    line-height: 1.25;
}

.eyebrow {
    margin: 0;
    color: var(--fgColor-muted);
    font-size: 13px;
    line-height: 1.4;
}

.empty-state {
    margin-top: 24px;
    padding: 22px;
    border: 1px dashed var(--borderColor-default);
    border-radius: 6px;
    background: var(--bgColor-muted);
}

.empty-state h3 {
    margin: 0 0 8px;
    color: var(--fgColor-default);
    font-size: 18px;
    font-weight: 600;
}

.empty-state p {
    margin: 0;
    color: var(--fgColor-muted);
    font-size: 14px;
    line-height: 1.6;
}

.empty-state code {
    color: var(--fgColor-default);
    font-family: ui-monospace, SFMono-Regular, SFMono-Regular, Consolas, "Liberation Mono", Menlo, monospace;
    font-size: .92em;
}

@media (max-width: 768px) {
    .profile-layout {
        grid-template-columns: 1fr;
        gap: 16px;
    }

    .profile-sidebar {
        display: grid;
        grid-template-columns: auto minmax(0, 1fr);
        gap: 16px;
        align-items: center;
    }

    .identity {
        margin-top: 0;
    }

    .profile-meta {
        grid-column: 1 / -1;
        margin-top: 0;
    }

    .profile-header {
        flex-direction: column;
    }
}
</style>
