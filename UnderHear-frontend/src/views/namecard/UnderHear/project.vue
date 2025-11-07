<template>
	<div class="project-container">
		<div class="project-header">
			<h1 class="project-title">我的项目</h1>
			<p class="project-subtitle">以下是我独立开发的一些项目，涵盖了全栈开发、机器人技术、小程序开发、AI等多个领域。其中一些还在持续维护。</p>
		</div>

		<div class="project-grid">
			<div v-for="project in projects" :key="project.id" class="project-card">
				<div class="card-header">
					<a :href="project.projectLink">{{ project.title }}</a>
					<span class="project-date">{{ project.date }}</span>
				</div>
				<a :href="project.weblink" v-if="project.weblink" class="project-link">{{ project.weblink }}</a>
				<div class="project-description">
					<p>{{ project.description.zh }}</p>
					<p>{{ project.description.en }}</p>
				</div>
				<div class="project-tech">
					<zTechTag v-for="tech in project.technologies" :key="tech">{{ tech }}</zTechTag>
				</div>
				<div class="project-items">
					<div class="project-badges">
						<span class="project-type-badge">{{ project.projectType }}</span>
						<span class="project-status-badge" :class="getStatusClass(project.status)">◉ {{ project.status }}</span>
					</div>
					<a :href="project.projectLink" class="project-detail">MORE</a>
				</div>
			</div>
		</div>
	</div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'

// 引入图标
import {zTechTag} from '@/components/z-ui/tag/zTechTag/'

// 引入数据
import projectData from '@/views/namecard/UnderHear/data/Projectcard.json'

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
.project-container {
	max-width: 1150px;
	margin: 0 auto;
	padding: 2rem;
	background-color: #fff;
}

.project-header {
	text-align: center;
	margin-bottom: 3rem;
}

.project-title {
	font-size: 2.5rem;
	font-weight: 700;
	color: #24292f;
	margin-bottom: 1rem;
	line-height: 1.2;
}

.project-subtitle {
	font-size: 1.1rem;
	color: #656d76;
	line-height: 1.6;
	max-width: 600px;
	margin: 0 auto;
}

.project-grid {
	display: grid;
	gap: 2rem;
}

.project-card {
	background: white;
	border: 1px solid #d1d9e1;
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
	color: #0969da;
	line-height: 1.3;
	flex: 1;
}

.project-date {
	font-size: 0.875rem;
	color: #656d76;
	background-color: #f6f8fa;
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
	color: #656d76;
}

.project-description p:first-child {
	color: #24292f;
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
	color: #7c7c7c;
	font-weight: 500;
	white-space: nowrap;
}

.project-status-badge {
	font-size: 0.75rem;
	font-weight: 500;
	white-space: nowrap;
}

.status-active {
	color: #0969da;
}

.status-updating {
	color: #3bc24f;
}

.status-stopped {
	color: #e63838;
}

.project-link,.project-detail {
	display: inline-flex;
	align-items: center;
	color: #0969da;
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
	.project-container {
		padding: 1rem;
	}

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
		color: #3082e0;
		justify-content: center;
		text-align: center;
		background-color: #f3f3f3;
		padding: 0.2rem 0.5rem;
		border-radius: 0.2rem;
		order: 2;
		align-self: flex-end;
	}
}
</style>
