<template>
  <Container
    max-width="1280px"
  >
    <div
      v-if="profile !== null && profile !== undefined"
      class="layout"
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
            <div
              v-if="!profileEditing"
              class="profile-info"
            >
              <div class="name">
                {{ profile?.nickname }}
              </div>
              <div class="username">
                @{{ profile?.nickname }}
              </div>
            </div>
          </div>
          <form
            v-if="profileEditing"
            class="profile-edit-form"
            @submit.prevent="saveProfileEdit"
          >
            <FormControl class="profile-edit-field">
              <FormControl.Label>昵称</FormControl.Label>
              <Input
                v-model="profileEditDraft.nickname"
                class="profile-edit-input"
                placeholder="昵称"
                disabled
              />
            </FormControl>
            <FormControl class="profile-edit-field">
              <FormControl.Label>简介</FormControl.Label>
              <Textarea
                v-model="profileEditDraft.bio"
                class="profile-edit-textarea"
                rows="3"
                placeholder="添加简介"
              />
            </FormControl>
            <FormControl class="profile-edit-field">
              <FormControl.Label>Pronoun</FormControl.Label>
              <Input
                v-model="profileEditDraft.pronoun"
                class="profile-edit-input"
                placeholder="Pronoun"
              />
            </FormControl>
            <FormControl class="profile-edit-field">
              <FormControl.Label>位置</FormControl.Label>
              <Input
                v-model="profileEditDraft.location"
                class="profile-edit-input"
                placeholder="Location"
              />
            </FormControl>
            <FormControl class="profile-edit-field">
              <FormControl.Label>Email</FormControl.Label>
              <Input
                v-model="profileEditDraft.email"
                class="profile-edit-input"
                disabled
              />
            </FormControl>
            <FormControl class="profile-edit-field">
              <FormControl.Label>社交账号 1</FormControl.Label>
              <Input
                v-model="profileEditDraft.socialAccount0"
                class="profile-edit-input"
                placeholder="Link to social profile 1"
              />
            </FormControl>
            <FormControl class="profile-edit-field">
              <FormControl.Label>社交账号 2</FormControl.Label>
              <Input
                v-model="profileEditDraft.socialAccount1"
                class="profile-edit-input"
                placeholder="Link to social profile 2"
              />
            </FormControl>
            <FormControl class="profile-edit-field">
              <FormControl.Label>社交账号 3</FormControl.Label>
              <Input
                v-model="profileEditDraft.socialAccount2"
                class="profile-edit-input"
                placeholder="Link to social profile 3"
              />
            </FormControl>
            <div class="profile-edit-actions">
              <Button
                type="submit"
                variant="primary"
                size="small"
                :loading="profileSaving"
              >
                保存
              </Button>
              <Button
                type="button"
                size="small"
                @click="cancelProfileEdit"
              >
                取消
              </Button>
            </div>
          </form>
          <template v-else>
            <p class="bio">
              {{ profile?.bio || '这个用户还没有填写简介。' }}
            </p>
            <Button
              v-if="isOwnProfile"
              class="edit-profile-button"
              @click="openProfileEdit"
            >
              编辑资料
            </Button>
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
          </template>
        </div>
      </div>
      <div class="right-column">
        <div class="profile-markdown">
          <div class="markdown-header">
            <div>{{ profile?.nickname }} / README.md</div>
            <Button
              v-if="isOwnProfile && profile?.markdown !== null"
              variant="link"
              @click="openMarkdownDialog"
            >
              编辑
            </Button>
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
        :loading="markdownSaving"
        @click="saveMarkdown"
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
import { computed, onMounted, reactive, ref } from 'vue'
import { useRoute } from 'vue-router'
import 'github-markdown-css/github-markdown-light.css'
import { Avatar } from '@/components/z-ui/avatar'
import { Blankslate } from '@/components/z-ui/blankslate'
import { Button } from '@/components/z-ui/button'
import { Banner } from '@/components/z-ui/banner'
import { Container } from '@/components/z-ui/container'
import { Dialog } from '@/components/z-ui/dialog'
import { FormControl } from '@/components/z-ui/form-control'
import { Input } from '@/components/z-ui/input'
import { Textarea } from '@/components/z-ui/textarea'
import {
  Location,
  Mail,
  Person,
  Link,
  RepoTemplate
} from '@/components/z-ui/icon/Octicons-vue'
import { useUserStore } from '@/stores/user'
import {
  getPublicUserProfile,
  saveCurrentUserMarkdown,
  saveCurrentUserProfile,
  type PublicUserProfile
} from '../api/profile'

import UserNotFound from '@/modules/error/views/404-user.vue'

const route = useRoute()
const userStore = useUserStore()
const nickname = String(route.params.nickname ?? '')
const profile = ref<PublicUserProfile | null | undefined>(undefined)
const profileEditing = ref(false)
const profileSaving = ref(false)
const profileEditDraft = reactive({
  nickname: '',
  bio: '',
  pronoun: '',
  location: '',
  email: '',
  socialAccount0: '',
  socialAccount1: '',
  socialAccount2: ''
})
const markdownDialogOpen = ref(false)
const markdownDraft = ref('')
const markdownMode = ref<'edit' | 'preview'>('edit')
const markdownSaving = ref(false)

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

const fillProfileEditDraft = () => {
  if (!profile.value) {
    return
  }

  profileEditDraft.nickname = profile.value.nickname
  profileEditDraft.bio = profile.value.bio ?? ''
  profileEditDraft.pronoun = profile.value.pronoun ?? ''
  profileEditDraft.location = profile.value.location ?? ''
  profileEditDraft.email = profile.value.email
  profileEditDraft.socialAccount0 = profile.value.socialAccount0 ?? ''
  profileEditDraft.socialAccount1 = profile.value.socialAccount1 ?? ''
  profileEditDraft.socialAccount2 = profile.value.socialAccount2 ?? ''
}

const openProfileEdit = () => {
  fillProfileEditDraft()
  profileEditing.value = true
}

const cancelProfileEdit = () => {
  profileEditing.value = false
}

const saveProfileEdit = async () => {
  if (!profile.value || profileSaving.value) {
    return
  }

  profileSaving.value = true
  try {
    const updatedProfile = await saveCurrentUserProfile({
      bio: profileEditDraft.bio,
      pronoun: profileEditDraft.pronoun,
      location: profileEditDraft.location,
      socialAccount0: profileEditDraft.socialAccount0,
      socialAccount1: profileEditDraft.socialAccount1,
      socialAccount2: profileEditDraft.socialAccount2
    })
    profile.value = {
      ...updatedProfile,
      markdown: profile.value.markdown
    }
    userStore.setUserInfo(updatedProfile)
    profileEditing.value = false
    Banner.success('保存成功。')
  } catch (error) {
    Banner.error(error instanceof Error ? error.message : '保存失败，请稍后重试。')
  } finally {
    profileSaving.value = false
  }
}

const openMarkdownDialog = () => {
  markdownDraft.value = profile.value?.markdown ?? ''
  markdownMode.value = 'edit'
  markdownDialogOpen.value = true
}

const saveMarkdown = async () => {
  if (!profile.value || markdownSaving.value) {
    return
  }

  markdownSaving.value = true
  try {
    await saveCurrentUserMarkdown({ content: markdownDraft.value })
    profile.value = {
      ...profile.value,
      markdown: markdownDraft.value
    }
    markdownDialogOpen.value = false
    Banner.success('保存成功。')
  } catch (error) {
    Banner.error(error instanceof Error ? error.message : '保存失败，请稍后重试。')
  } finally {
    markdownSaving.value = false
  }
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
.layout {
  display: flex;
  gap: 30px;
  min-height: calc(100vh - var(--header-height));
}

.left-column {
  width: 25%;
  min-width: 200px;
}

.right-column {
  width: 75%;
  min-width: 0;
}

.profile .avatar {
  width: 100% !important;
  height: auto !important;
  aspect-ratio: 1;
  border-radius: 50%;
}

.profile-info {
  padding: 16px 0;
  min-width: 0;
  text-align: left;
}

.name {
  color: var(--fgColor-default);
  font-size: 24px;
  font-weight: 600;
  line-height: 1.25;
  overflow-wrap: anywhere;
}

.username {
  color: var(--fgColor-muted);
  font-size: 20px;
  overflow-wrap: anywhere;
  font-weight: 200;
}

.bio {
  margin-bottom: 16px;
  color: var(--fgColor-default);
  font-size: 15px;
  font-weight: 400;
  line-height: 20px;
  overflow-wrap: anywhere;
}

.edit-profile-button {
  margin-bottom: 16px;
  width: 100%;
}

.profile-edit-form {
  display: grid;
  gap: 12px;
  margin-top: 16px;
}

.profile-edit-field :deep(.input),
.profile-edit-field :deep(.textarea) {
  box-sizing: border-box;
  width: 100%;
}

.profile-edit-textarea {
  min-height: 88px;
}

.profile-edit-actions {
  display: flex;
  gap: 8px;
  margin-top: 4px;
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
  justify-content: space-between;
  align-items: center;
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
  .layout {
      flex-direction: column;
      gap: 10px;
  }

  .left-column,
  .right-column {
    width: 100%;
  }

  .profile-header {
    margin: 24px 0;
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
  }
}
</style>
