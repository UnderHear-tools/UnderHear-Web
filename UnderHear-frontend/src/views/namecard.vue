<template>
	<div class="namecard-wrapper">
		<div class="namecard-header">
			<h1 class="title">创作者名片</h1>
			<p class="subtitle">这里展示本站创作者 / 贡献者的个人名片。点击进入详情页面了解更多。</p>
			<p class="subtitle">你也想加入？前往
				<zLink href="https://github.com/UnderHear/toolsWeb" link-text="本项目的GitHub" target="_blank" :external="true" />页！
			</p>
		</div>

		<div class="creator-grid">
			<RouterLink to="/namecard/UnderHear" class="creator-card">
				<div class="creator-main">
					<img src="@/views/namecard/UnderHear/assets/index/avatar.png" alt="UnderHear" class="avatar" />
					<div class="info">
						<div class="name-row">
							<h3 class="name">UnderHear</h3>
							<span class="role">原始创作者</span>
						</div>
						<div class="bio">
							<p class="bio-en">Be interested in cutting-edge, advanced, and popular technologies and keep
								exploring them.</p>
							<p class="bio-zh">对前沿、先进和热门的技术感兴趣并保持探索。</p>
						</div>
					</div>
				</div>
				<div class="card-footer">
					<span class="enter">enter</span>
				</div>
			</RouterLink>
		</div>
	</div>
</template>

<script setup lang="ts">
import { zLink } from '@/components/z-ui/link/zlink'
</script>

<style scoped>
.namecard-wrapper {
	max-width: 1150px;
	margin: 0 auto;
	padding: 2rem;
	background: #fff;
}

.namecard-header {
	text-align: center;
	margin-bottom: 3rem;
}

.title {
	font-size: 2.5rem;
	font-weight: 700;
	color: #24292f;
	margin-bottom: 1rem;
	line-height: 1.2;
}

.subtitle {
	font-size: 1.05rem;
	color: #656d76;
	line-height: 1.6;
	max-width: 620px;
	margin: 0 auto;
}

.creator-grid {
	display: grid;
	grid-template-columns: 1fr;
	gap: 1.6rem;
}

.creator-card {
	display: flex;
	flex-direction: column;
	border: 1px solid #d1d9e1;
	border-radius: 6px;
	padding: 1.5rem 1.6rem 1.1rem;
	text-decoration: none;
	background: #fff;
	transition: border-color .18s ease, box-shadow .18s ease;
	position: relative;
}

.creator-card:hover {
	border-color: #0969da;
	box-shadow: 0 4px 14px -4px rgba(0, 0, 0, 0.08);
}

.creator-main {
	display: flex;
	gap: 1.25rem;
}

.avatar {
	width: 92px;
	height: 92px;
	border-radius: 50%;
	object-fit: cover;
	box-shadow: 0 0 0 1px #1f232826;
	border: 1px solid #d1d9e0;
}

.info {
	flex: 1;
	min-width: 0;
}

.name-row {
	display: flex;
	align-items: center;
	gap: .6rem;
	margin-bottom: .4rem;
}

.name {
	margin: 0;
	font-size: 1.25rem;
	font-weight: 600;
	color: #0969da;
}

.role {
	font-size: .65rem;
	text-transform: uppercase;
	letter-spacing: .5px;
	background: #f6f8fa;
	padding: .25rem .55rem;
	border-radius: 12px;
	color: #656d76;
	font-weight: 600;
	line-height: 1;
}

.bio-zh,
.bio-en {
	margin: 0 0 .5rem;
	font-size: .85rem;
	line-height: 1.5;
	color: #24292f;
}

.bio-zh {
	color: #656d76;
	font-style: italic;
}


.card-footer {
	display: flex;
	justify-content: flex-end;
}

.enter {
	font-size: 1rem;
	font-weight: 600;
	color: #0969da;
	padding: .35rem .7rem;
	border-radius: 6px;
	transition: background .18s ease;
}

@media (max-width: 768px) {
	.namecard-wrapper {
		padding: 1rem;
	}

	.name-row {
		flex-direction: column;
		align-items: flex-start;
	}

	.title {
		font-size: 2rem;
	}

	.creator-main {
		flex-direction: row;
	}

	.creator-main {
		gap: 12px;
	}

	.avatar {
		width: 64px;
		height: 64px;
	}

	.bio{
		position: relative;
		left: -76px;
		top: 10px;
		width: calc(100% + 76px);
	}

	.bio-zh,
	.bio-en {
		font-size: .8rem;
	}
}
</style>