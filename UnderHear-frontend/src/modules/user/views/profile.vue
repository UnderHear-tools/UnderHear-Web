<template>
  <zContainer>
    <section class="profile-layout">
      <aside class="profile-sidebar">
        <zAvatar
          :src="profile?.avatarUrl || undefined"
          :alt="`${displayName} avatar`"
          :placeholder="avatarPlaceholder"
          :size="128"
        />
        <div class="identity">
          <h1 class="display-name">
            {{ displayName }}
          </h1>
          <p class="handle">
            @{{ routeNickname }}
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
            <span>{{ profile?.pronoun || '未设置称谓' }}</span>
          </div>
          <div class="meta-item">
            <IconPark
              type="local"
              theme="outline"
              size="18"
              fill="currentColor"
            />
            <span>{{ profile?.location || '未设置位置' }}</span>
          </div>
          <div class="meta-item">
            <IconPark
              type="link"
              theme="outline"
              size="18"
              fill="currentColor"
            />
            <span>/@{{ routeNickname }}</span>
          </div>
        </div>
      </aside>

      <main class="profile-main">
        <div class="profile-header">
          <div>
            <p class="eyebrow">
              User profile
            </p>
            <h2>{{ displayName }} 的主页</h2>
          </div>
          <zTag
            size="large"
            :color="statusTag.color"
            :bg-color="statusTag.bgColor"
          >
            {{ statusTag.text }}
          </zTag>
        </div>

        <div
          v-if="isLoading"
          class="state-panel"
        >
          <h3>正在加载资料</h3>
          <p>正在读取 @{{ routeNickname }} 的公开资料。</p>
        </div>

        <div
          v-else-if="errorMessage"
          class="state-panel state-panel--error"
        >
          <h3>未找到用户</h3>
          <p>{{ errorMessage }}</p>
        </div>

        <div
          v-else-if="profile"
          class="profile-content"
        >
          <section class="bio-section">
            <h3>简介</h3>
            <p>{{ profile.bio || '这个用户还没有填写简介。' }}</p>
          </section>

          <section class="info-grid">
            <div class="info-item">
              <span class="info-label">邮箱</span>
              <span class="info-value">{{ profile.email || '未公开' }}</span>
            </div>
            <div class="info-item">
              <span class="info-label">UUID</span>
              <span class="info-value">{{ profile.uuid }}</span>
            </div>
          </section>

          <section class="social-section">
            <h3>社交账号</h3>
            <div
              v-if="socialAccounts.length > 0"
              class="social-list"
            >
              <a
                v-for="account in socialAccounts"
                :key="account"
                class="social-link"
                :href="account"
                target="_blank"
                rel="noreferrer"
              >
                {{ account }}
              </a>
            </div>
            <p
              v-else
              class="muted-text"
            >
              暂无公开社交账号。
            </p>
          </section>
        </div>
      </main>
    </section>
  </zContainer>
</template>

<script setup lang="ts">
import { computed, ref, watch } from 'vue'
import { useRoute } from 'vue-router'
import { IconPark } from '@icon-park/vue-next/es/all'

import { zAvatar } from '@/components/z-ui/avatar'
import { zContainer } from '@/components/z-ui/container'
import { zTag } from '@/components/z-ui/tag'
import type { UserInfo } from '@/stores/user'
import { getPublicUserProfile } from '../api/profile'

const route = useRoute()

const profile = ref<UserInfo | null>(null)
const isLoading = ref(false)
const errorMessage = ref('')
let requestId = 0

const routeNickname = computed(() => {
  const value = route.params.nickname
  return Array.isArray(value) ? value[0] : value || ''
})

const displayName = computed(() => profile.value?.nickname || routeNickname.value)

const avatarPlaceholder = computed(() => {
  const source = displayName.value || routeNickname.value
  return source.slice(0, 2).toUpperCase()
})

const socialAccounts = computed(() => {
  if (!profile.value) return []
  return [
    profile.value.socialAccount0,
    profile.value.socialAccount1,
    profile.value.socialAccount2,
  ].filter((account): account is string => Boolean(account))
})

const statusTag = computed(() => {
  if (isLoading.value) {
    return {
      text: 'Loading',
      color: 'var(--fgColor-muted)',
      bgColor: 'var(--bgColor-muted)',
    }
  }
  if (errorMessage.value) {
    return {
      text: 'Not found',
      color: 'var(--fgColor-danger, #cf222e)',
      bgColor: 'var(--bgColor-danger-muted, #ffebe9)',
    }
  }
  return {
    text: 'Public',
    color: 'var(--fgColor-success, #1a7f37)',
    bgColor: 'var(--bgColor-success-muted, #dafbe1)',
  }
})

watch(
  routeNickname,
  async (nickname) => {
    const currentRequestId = ++requestId
    profile.value = null
    errorMessage.value = ''

    if (!nickname) {
      errorMessage.value = '用户昵称无效'
      return
    }

    isLoading.value = true
    try {
      const userProfile = await getPublicUserProfile(nickname)
      if (currentRequestId === requestId) {
        profile.value = userProfile
      }
    } catch (error) {
      if (currentRequestId === requestId) {
        errorMessage.value = error instanceof Error ? error.message : '用户资料加载失败'
      }
    } finally {
      if (currentRequestId === requestId) {
        isLoading.value = false
      }
    }
  },
  { immediate: true }
)
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
    overflow-wrap: anywhere;
}

.handle {
    margin: 4px 0 0;
    color: var(--fgColor-muted);
    font-size: 18px;
    line-height: 1.4;
    overflow-wrap: anywhere;
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
    overflow-wrap: anywhere;
}

.eyebrow {
    margin: 0;
    color: var(--fgColor-muted);
    font-size: 13px;
    line-height: 1.4;
}

.state-panel,
.bio-section,
.social-section {
    margin-top: 24px;
    padding: 22px;
    border: 1px solid var(--borderColor-default);
    border-radius: 6px;
    background: var(--bgColor-muted);
}

.state-panel--error {
    border-color: var(--borderColor-danger-muted, #ff818266);
    background: var(--bgColor-danger-muted, #ffebe9);
}

.state-panel h3,
.bio-section h3,
.social-section h3 {
    margin: 0 0 8px;
    color: var(--fgColor-default);
    font-size: 18px;
    font-weight: 600;
}

.state-panel p,
.bio-section p,
.muted-text {
    margin: 0;
    color: var(--fgColor-muted);
    font-size: 14px;
    line-height: 1.6;
}

.profile-content {
    min-width: 0;
}

.info-grid {
    display: grid;
    grid-template-columns: repeat(2, minmax(0, 1fr));
    gap: 12px;
    margin-top: 16px;
}

.info-item {
    display: grid;
    gap: 6px;
    min-width: 0;
    padding: 16px;
    border: 1px solid var(--borderColor-default);
    border-radius: 6px;
}

.info-label {
    color: var(--fgColor-muted);
    font-size: 13px;
}

.info-value {
    color: var(--fgColor-default);
    font-size: 14px;
    overflow-wrap: anywhere;
}

.social-list {
    display: grid;
    gap: 10px;
}

.social-link {
    color: var(--fgColor-link);
    font-size: 14px;
    line-height: 1.5;
    overflow-wrap: anywhere;
    text-decoration: none;
}

.social-link:hover {
    text-decoration: underline;
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

    .info-grid {
        grid-template-columns: 1fr;
    }
}
</style>
