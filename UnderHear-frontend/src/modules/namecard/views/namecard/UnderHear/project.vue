<template>
  <Container>
    <div class="project-header">
      <h1 class="project-title">
        我的项目
      </h1>
      <p class="project-subtitle">
        以下是我独立开发的一些项目，涵盖了全栈开发、机器人技术、小程序开发、AI等多个领域。其中一些还在持续维护。
      </p>
    </div>

    <div class="project-grid">
      <div
        v-for="project in projects"
        :key="project.id"
        class="project-card"
      >
        <div class="card-header">
          <a :href="project.projectLink">{{ project.title }}</a>
          <span class="project-date">{{ project.date }}</span>
        </div>
        <a
          v-if="project.weblink"
          :href="project.weblink"
          class="project-link"
        >{{ project.weblink }}</a>
        <div class="project-description">
          <p>{{ project.description.zh }}</p>
          <p>{{ project.description.en }}</p>
        </div>
        <div class="project-tech">
          <Tag
            v-for="tech in project.technologies"
            :key="tech"
          >
            {{ tech }}
          </Tag>
        </div>
        <div class="project-items">
          <div class="project-badges">
            <span class="project-type-badge">{{ project.projectType }}</span>
            <span
              class="project-status-badge"
              :class="getStatusClass(project.status)"
            >◉ {{ project.status }}</span>
          </div>
          <a
            :href="project.projectLink"
            class="project-detail"
          >MORE</a>
        </div>
      </div>
    </div>
  </Container>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'

import { Container } from '@/components/z-ui/container'
// 引入图标
import {Tag} from '@/components/z-ui/tag'

// 引入数据
import projectData from '../UnderHear/data/Projectcard.json'

//
interface ProjectDescription {
	zh: string
	en: string
}

interface Project {
	id: number
	title: string
	date: string
	description: ProjectDescription
	technologies: string[]
	projectLink: string
	weblink: string
	projectType: string
	status: string
}

const projects = ref<Project[]>([])

onMounted(() => {
	projects.value = projectData
})

// 根据状态返回对应的CSS类
const getStatusClass = (status: string) => {
	switch (status) {
		case '维护中':
			return 'status-active'
		case '更新中':
			return 'status-updating'
		case '已停止':
			return 'status-stopped'
		default:
			return ''
	}
}
//
</script>

<style scoped>
.project-header {
	text-align: center;
	margin-bottom: 3rem;
}

.project-title {
	font-size: 2.5rem;
	font-weight: 700;
	color: var(--fgColor-default);
	margin-bottom: 1rem;
	line-height: 1.2;
}

.project-subtitle {
	font-size: 1.1rem;
	color: var(--fgColor-muted);
	line-height: 1.6;
	max-width: 600px;
	margin: 0 auto;
}

.project-grid {
	display: grid;
	gap: 2rem;
}

.project-card {
	background: var(--bgColor-default);
	border: 1px solid var(--borderColor-default);
	border-radius: 4px;
	padding: 1.5rem;
}

.card-header {
	display: flex;
	justify-content: space-between;
	align-items: flex-start;
	flex-wrap: wrap;
	gap: 1rem;
}

.card-header a {
	margin: 0;
	font-size: 1.25rem;
	font-weight: 600;
	color: var(--fgColor-link);
	line-height: 1.3;
	flex: 1;
}

.project-date {
	font-size: 0.875rem;
	color: var(--fgColor-muted);
	background-color: var(--bgColor-muted);
	padding: 0.3rem 0.8rem;
	border-radius: 100px;
	font-weight: 500;
	white-space: nowrap;
}

.project-description {
	margin-bottom: 1.5rem;
}

.project-description p {
	margin: 0 0 1rem 0;
	font-size: 0.95rem;
	line-height: 1.6;
	color: var(--fgColor-muted);
}

.project-description p:first-child {
	color: var(--fgColor-default);
	font-weight: 500;
	font-size: 1rem;
}

.project-tech {
	display: flex;
	flex-wrap: wrap;
	gap: 0.5rem;
	margin-bottom: 0.4rem;
}



.project-items {
	display: flex;
	gap: 1rem;
	flex-wrap: wrap;
	justify-content: space-between;
	align-items: end;
}

.project-badges {
	display: flex;
	gap: 0.5rem;
	flex-wrap: wrap;
}

.project-type-badge {
	font-size: 0.75rem;
	color: var(--fgColor-muted);
	font-weight: 500;
	white-space: nowrap;
}

.project-status-badge {
	font-size: 0.75rem;
	font-weight: 500;
	white-space: nowrap;
}

.status-active {
	color: var(--fgColor-accent);
}

.status-updating {
	color: var(--fgColor-success);
}

.status-stopped {
	color: var(--fgColor-danger);
}

.project-link,.project-detail {
	display: inline-flex;
	align-items: center;
	color: var(--fgColor-link);
	text-decoration: none;
	font-weight: 600;
	font-size: 1rem;
	cursor: pointer;
}

.project-link{
	margin-bottom: 1rem;
	width: 100%;
}

.project-link:hover,.project-detail:hover {
	text-decoration: underline;
}


@media (max-width: 768px) {
	.project-title {
		font-size: 2rem;
	}

	.project-subtitle {
		font-size: 1rem;
	}

	.project-grid {
		grid-template-columns: 1fr;
		gap: 1.5rem;
	}

	.project-card {
		padding: 20px;
	}

	.card-header {
		flex-direction: column;
		align-items: flex-start;
		gap: 0.5rem;
		margin-bottom: 0.5rem;
	}

	.project-items {
		margin-top: 1rem;
		gap: 0.75rem;
		align-items: end;
	}

	.project-badges {
		order: 1;
	}

	.project-detail {
		color: var(--fgColor-link);
		justify-content: center;
		text-align: center;
		background-color: var(--bgColor-muted);
		padding: 0.2rem 0.5rem;
		border-radius: 0.2rem;
		order: 2;
		align-self: flex-end;
	}
}
</style>
