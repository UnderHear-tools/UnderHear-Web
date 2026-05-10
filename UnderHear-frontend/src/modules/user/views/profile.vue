<template>
  <Container
    v-if="profile !== null && profile !== undefined"
    class="Container"
  >
    <div class="left-column">
      <div class="profile">
        <div class="profile-header">
          <Avatar
            class="avatar"
            :src="profile?.avatarUrl"
            :alt="profile?.nickname"
            :size="128"
          />
          <div class="profile-info">
            <div class="name">
              {{ profile?.nickname }}
            </div>
            <div class="username">
              @{{ profile?.nickname }}
            </div>
          </div>
        </div>

        <p class="bio">
          {{ profile?.bio || '这个用户还没有填写简介。' }}
        </p>

        <div class="links">
          <ul>
            <li v-if="profile?.pronoun">
              <Person
                size="16"
                color="#5a5a5a"
              />
              <span>{{ profile?.pronoun }}</span>
            </li>
            <li v-if="profile?.location">
              <Location
                size="16"
                color="#5a5a5a"
              />
              <span>{{ profile?.location }}</span>
            </li>
            <li v-if="profile?.email">
              <Mail
                size="16"
                color="#5a5a5a"
              />
              <span>{{ profile?.email }}</span>
            </li>
            <li v-if="profile?.socialAccount0">
              <Link
                size="16"
                color="#5a5a5a"
              />
              <span>{{ profile?.socialAccount0 }}</span>
            </li>
            <li v-if="profile?.socialAccount1">
              <Link
                size="16"
                color="#5a5a5a"
              />
              <span>{{ profile?.socialAccount1 }}</span>
            </li>
            <li v-if="profile?.socialAccount2">
              <Link
                size="16"
                color="#5a5a5a"
              />
              <span>{{ profile?.socialAccount2 }}</span>
            </li>
          </ul>
        </div>
      </div>
    </div>

    <div class="right-column">
      <div class="profile-markdown">
        <div class="markdown-header">
          {{ profile?.nickname }} / README.md
        </div>

        <div class="markdown-content">
          <!-- eslint-disable vue/no-v-html -->
          <div
            v-if="profile?.markdown !== null"
            class="markdown-body profile-readme"
            v-html="renderedProfileMarkdown"
          />
          <!-- eslint-enable vue/no-v-html -->
          <Blankslate
            v-else
            narrow
          >
            <Blankslate.Visual>
              <RepoTemplate />
            </Blankslate.Visual>
            <Blankslate.Heading>
              {{ isOwnProfile ? '编写你的 README' : '' }}
            </Blankslate.Heading>
            <Blankslate.Description>
              {{ isOwnProfile ? '用 Markdown 介绍你自己、项目和正在做的事情。让所有人认识你。' : '空空如也' }}
            </Blankslate.Description>
            <Blankslate.PrimaryAction
              v-if="isOwnProfile"
              @click="openMarkdownDialog"
            >
              现在开始
            </Blankslate.PrimaryAction>
          </Blankslate>
        </div>
      </div>
    </div>
  </Container>
  <Dialog
    v-model:open="markdownDialogOpen"
    title="编辑 README"
    subtitle="使用 Markdown 编写你的个人资料内容。"
    size="xlarge"
  >
    <Dialog.Body>
      <div class="markdown-editor">
        <div class="markdown-editor__toolbar">
          <Button
            :variant="markdownMode === 'edit' ? 'primary' : 'default'"
            size="small"
            @click="markdownMode = 'edit'"
          >
            编辑
          </Button>
          <Button
            :variant="markdownMode === 'preview' ? 'primary' : 'default'"
            size="small"
            @click="markdownMode = 'preview'"
          >
            预览
          </Button>
        </div>
        <Textarea
          v-if="markdownMode === 'edit'"
          v-model="markdownDraft"
          class="markdown-editor__textarea"
          rows="16"
          placeholder="用 Markdown 介绍你自己、项目和正在做的事情。"
          autofocus
        />
        <div
          v-else
          class="markdown-editor__preview"
        >
          <!-- eslint-disable vue/no-v-html -->
          <div
            v-if="markdownDraft"
            class="markdown-body markdown-editor__preview-body"
            v-html="renderedDraftMarkdown"
          />
          <!-- eslint-enable vue/no-v-html -->
          <div
            v-else
            class="markdown-editor__empty"
          >
            暂无可预览内容。
          </div>
        </div>
      </div>
    </Dialog.Body>
    <Dialog.Footer>
      <Button
        @click="markdownDialogOpen = false"
      >
        取消
      </Button>
      <Button
        :variant="'primary'"
      >
        保存
      </Button>
    </Dialog.Footer>
  </Dialog>
  <UserNotFound v-if="profile === null" />
</template>

<script setup lang="ts">
import DOMPurify from 'dompurify'
import { marked } from 'marked'
import { computed, onMounted, ref } from 'vue'
import { useRoute } from 'vue-router'
import 'github-markdown-css/github-markdown-light.css'
import { Avatar } from '@/components/z-ui/avatar'
import { Blankslate } from '@/components/z-ui/blankslate'
import { Button } from '@/components/z-ui/button'
import { Container } from '@/components/z-ui/container'
import { Dialog } from '@/components/z-ui/dialog'
import { Textarea } from '@/components/z-ui/textarea'
import {
  Location,
  Mail,
  Person,
  Link,
  RepoTemplate
} from '@/components/z-ui/icon/Octicons-vue'
import { useUserStore } from '@/stores/user'
import { getPublicUserProfile, type PublicUserProfile } from '../api/profile'

import UserNotFound from '@/modules/error/views/404-user.vue'

const route = useRoute()
const userStore = useUserStore()
const nickname = String(route.params.nickname ?? '')
const profile = ref<PublicUserProfile | null | undefined>(undefined)
const markdownDialogOpen = ref(false)
const markdownDraft = ref('')
const markdownMode = ref<'edit' | 'preview'>('edit')

const renderMarkdown = (content: string) => {
  const html = marked.parse(content, {
    gfm: true,
    breaks: false,
    async: false
  })
  return DOMPurify.sanitize(html)
}

const isOwnProfile = computed(() => {
  return Boolean(profile.value?.uuid && userStore.userInfo?.uuid && profile.value.uuid === userStore.userInfo.uuid)
})

const renderedProfileMarkdown = computed(() => {
  return renderMarkdown(profile.value?.markdown ?? '')
})

const renderedDraftMarkdown = computed(() => {
  return renderMarkdown(markdownDraft.value)
})

const openMarkdownDialog = () => {
  markdownDraft.value = profile.value?.markdown ?? ''
  markdownMode.value = 'edit'
  markdownDialogOpen.value = true
}

const fetchProfile = async () => {
  try {
    profile.value = await getPublicUserProfile(nickname)
  } catch {
    profile.value = null
  }
}

onMounted(async () => {
  await fetchProfile()
})

</script>

<style scoped>
.Container {
  display: flex;
  gap: 30px;
  min-height: calc(100vh - var(--header-height));
}

.left-column {
  width: 22%;
  min-width: 200px;
}

.right-column {
  width: 78%;
  min-width: 0;
}

.profile .avatar {
  width: 100% !important;
  height: auto !important;
  aspect-ratio: 1;
  border-radius: 50%;
}

.profile-info {
  min-width: 0;
  margin: 20px 0;
  text-align: left;
}

.name {
  color: var(--fgColor-default);
  font-size: 26px;
  font-weight: 600;
  line-height: 1.25;
  overflow-wrap: anywhere;
}

.username {
  margin-top: 4px;
  color: var(--fgColor-muted);
  font-size: 18px;
  line-height: 1.4;
  overflow-wrap: anywhere;
}

.bio {
  margin: 16px 0;
  color: var(--fgColor-muted);
  font-size: 15px;
  font-style: italic;
  font-weight: 400;
  line-height: 20px;
  overflow-wrap: anywhere;
}

.links ul {
  list-style: none;
  padding: 0;
  margin: 0;
}

.links li {
  display: flex;
  align-items: flex-start;
  min-width: 0;
  margin-bottom: 12px;
  color: var(--fgColor-default);
  gap: 8px;
  font-size: 14px;
  line-height: 1.45;
}

.links li svg {
  flex-shrink: 0;
  transform: translateY(2px);
}

.links li span {
  min-width: 0;
  overflow-wrap: anywhere;
}

.profile-markdown {
  display: flex;
  flex-direction: column;
  padding: 24px;
  border: 1px solid var(--borderColor-default);
  border-radius: 6px;
  background: var(--bgColor-default);
  min-height: 480px;
}

.markdown-header {
  display: flex;
  margin-bottom: 16px;
  color: var(--fgColor-default);
  font-size: 12px;
}

.markdown-content {
  display: flex;
  align-items: center;
  flex: 1;
}

.profile-readme {
  align-self: stretch;
  min-width: 0;
  width: 100%;
}

.markdown-editor {
  display: flex;
  flex-direction: column;
  gap: 12px;
  min-height: 0;
}

.markdown-editor__toolbar {
  display: flex;
  justify-content: flex-end;
  gap: 8px;
}

.markdown-editor__textarea {
  width: 100%;
  min-height: 360px;
  resize: vertical;
}

.markdown-editor__preview {
  box-sizing: border-box;
  height: 360px;
  min-height: 0;
  overflow-y: auto;
  overscroll-behavior: contain;
  padding: 12px;
  border: 1px solid var(--borderColor-default);
  border-radius: 6px;
  background: var(--bgColor-default);
  scrollbar-color: var(--fgColor-muted, #59636e) transparent;
}

.markdown-editor__preview-body {
  min-width: 0;
}

.markdown-editor__empty {
  display: flex;
  align-items: center;
  justify-content: center;
  min-height: 320px;
  color: var(--fgColor-muted);
  font-size: 14px;
}

@media (max-width: 768px) {
  .Container {
      flex-direction: column;
      gap: 10px;
  }

  .left-column,
  .right-column {
    width: 100%;
  }

  .profile-header {
    margin-top: 20px;
    display: flex;
    align-items: center;
  }

  .profile .avatar {
    width: 66.4px !important;
    height: 66.4px !important;
    margin-right: 16px;
    box-shadow: 0 0 0 1px var(--borderColor-translucent);
    border: 1px solid var(--borderColor-default);
  }

  .profile-info {
    text-align: left;
    margin: 10px 0;
  }

  .profile .name {
    font-size: 24px;
  }

  .profile .username {
    font-size: 16px;
  }
}
</style>
