<template>
    <div class="index">
        <div class="container">
            <div class="left-column">
                <div class="profile">
                    <div class="profile-header">
                        <img src="./assets/index/avatar.png" alt="Neko" class="avatar">
                        <div class="profile-info">
                            <div class="name">UnderHear</div>
                            <div class="views">
                                <IconPark type="preview-open" theme="outline" size="20" fill="#808080"/>
                                <div>1</div>
                            </div>
                        </div>
                    </div>
                    <p class="bio-en">Be interested in cutting-edge, advanced, and popular technologies and keep
                        exploring them.</p>
                    <p class="bio-cn">对前沿、先进和热门的技术感兴趣并保持探索。</p>
                    <div class="links">
                        <ul>
                            <li><icon-park type="local" theme="outline" size="20" fill="#5a5a5a"/><span>Hangzhou, China</span></li>
                            <li><icon-park type="time" theme="outline" size="20" fill="#5a5a5a"/><span>17:30 <span class="time-suffix">-
                                        same time</span></span></li>
                            <li><icon-park type="mail" theme="outline" size="20" fill="#5a5a5a"/><span><a
                                        href="mailto:2533643340@qq.com">2533643340@qq.com</a></span></li>
                            <li><icon-park type="github" theme="filled" size="20" fill="#5a5a5a"/><span><a
                                        href="https://github.com/UnderHear"
                                        target="_blank">@UnderHear</a></span></li>
                            <li><icon-park type="facebook-one" theme="outline" size="20" fill="#5a5a5a"/><span><a
                                        href="https://www.facebook.com/profile.php?id=100091494675623" target="_blank"
                                        rel="noopener noreferrer">@张成炜</a></span></li>
                            <li><icon-park type="twitter" theme="outline" size="20" fill="#5a5a5a"/><span><a href="https://x.com/zhangch94743257"
                                        target="_blank" rel="noopener noreferrer">@zhangch94743257</a></span></li>
                            <li><icon-park type="phone-telephone" theme="outline" size="20" fill="#5a5a5a"/><span><a
                                        href="tel:+8619858170547"><span class="country-code">+86
                                        </span>19858170547</a></span></li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="right-column">
                <div class="personal-introduction">通过以下这些来了解我 Learn about me through the following</div>
                <div class="greeting">你好 Hello 🤗</div>
                <div class="tip-section">
                    <div class="tip-header">
                        <span class="tip-icon"><icon-park type="tips" theme="filled" size="22" fill="#1a7f37"/></span>
                        <span class="tip-title">Tip</span>
                    </div>
                    <div class="tip-content">
                        <div class="description-en">
                            Certainly! I am currently taking on some part-time projects, including but not limited to
                            website development, mini-program development, management systems, and enterprise platforms.
                            I am currently delving into the fields of artificial intelligence and machine learning. If
                            you have related needs or would like to exchange ideas technically, feel free to reach out
                            to me.
                        </div>
                        <div class="description-cn">
                            目前我会接一些兼职项目，项目包括但不限于网站开发、小程序开发、管理系统、企业平台。目前我正在钻研的方向是人工智能与机器学习。如果你对此有需要或者想和我进行技术交流请联系我。
                        </div>
                    </div>
                </div>

                <div class="languages">
                    <h4>我使用的语言、框架和工具 Languages & Frameworks & Tools I use</h4>

                    <div class="language-icons">
                        <img src="./assets/index/skillsIcon/typescript-original.svg" class="language-icon" />
                        <img src="./assets/index/skillsIcon/uniapp.svg" class="language-icon" />
                        <img src="./assets/index/skillsIcon/vuejs-original.svg" class="language-icon" />
                        <img src="./assets/index/skillsIcon/cplusplus-original.svg" class="language-icon" />
                        <img src="./assets/index/skillsIcon/python-original.svg" class="language-icon" />
                        <img src="./assets/index/skillsIcon/java-original.svg" class="language-icon" />
                        <img src="./assets/index/skillsIcon/spring-original.svg" class="language-icon" />
                        <img src="./assets/index/skillsIcon/mysql-original.svg" class="language-icon" />
                        <img src="./assets/index/skillsIcon/kotlin-original.svg" class="language-icon" />
                        <img src="./assets/index/skillsIcon/tailwindcss-original.svg" class="language-icon" />
                        <img src="./assets/index/skillsIcon/vitejs-original.svg" class="language-icon" />
                        <img src="./assets/index/skillsIcon/anaconda-original.svg" class="language-icon" />
                        <img src="./assets/index/skillsIcon/matlab-original.svg" class="language-icon" />
                        <img src="./assets/index/skillsIcon/neo4j-original.svg" class="language-icon" />
                        <img src="./assets/index/skillsIcon/git-original.svg" class="language-icon" />
                        <img src="./assets/index/skillsIcon/docker-plain.svg" class="language-icon" />
                    </div>

                </div>
                <div class="sessions">
                    <h4>项目经历和成果 Project Experience & Achievements</h4>
                    <div class="tip-section1">
                        <div class="tip-content">
                            <div class="description-cn">
                                Maybe there's something you're interested in, maybe.
                            </div>
                        </div>
                    </div>
                    <div class="project-experience">
                        <div v-for="project in projects" :key="project.id" class="project-card">
                            <div class="project-header">
                                <h3>{{ project.title }}</h3>
                                <span class="project-date">{{ project.date }}</span>
                            </div>
                            <div class="project-description">
                                <p>{{ project.description.zh }}</p>
                                <p>{{ project.description.en }}</p>
                            </div>
                            <div class="project-tech">
                                <zTechTag v-for="tech in project.technologies" :key="tech">{{ tech }}</zTechTag>
                            </div>
                        </div>
                        <a href="/namecard/UnderHear/project" class="view-more-simple">Read more...</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'

// 引入图标
import {zTechTag} from '@/components/z-ui/tag/zTechTag/'
import {IconPark} from '@icon-park/vue-next/es/all';


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
}

const projects = ref<Project[]>([])

onMounted(() => {
  // 只获取前三条项目数据
  projects.value = projectData.slice(0, 3)
})

//
</script>

<style>
* {
    box-sizing: border-box;
}

.index {
    font-family: Arial, sans-serif;
    color: #24292e;
    background-color: #fff;
}

.container {
    display: flex;
    max-width: 1300px;
    margin: 0 auto;
    padding: 40px;
    gap: 30px;

}

.left-column {
    width: 22%;
}

.right-column {
    padding: 24px;
    border: 1px solid #d1d9e1;
    border-radius: 6px;
    width: 78%;
}

.profile-info {
    text-align: left;
    margin: 20px 0;
}

.profile .avatar {
    width: 100%;
    border-radius: 50%;
}

.views {
    font-size: 18px;
    font-weight: 300;
    line-height: 24px;
    color: #808080;
    margin-top: 4px;
    display: flex;
    align-items: center;
    gap: 4px;
}

.profile .name {
    font-size: 26px;
    font-weight: 600;
    line-height: 1.25;
}

.bio-en {
    margin-bottom: 16px;
}

.bio-cn {
    font-size: 15px;
    font-weight: 400;
    line-height: 20px;
    color: #808080;
    font-style: italic;
    margin-bottom: 16px;
}



.links ul {
    list-style: none;
    padding: 0;
    margin: 0;
}

.links li {
    display: flex;
    align-items: center;
    margin-bottom: 12px;
    color: #1f2328;
    gap: 10px;
    font-size: 14px;
}

.links li svg{
    margin-top: 2px;
}

.links li a {
    color: #1f2328;
    text-decoration: none;
}

.links li a:hover {
    color: rgb(66, 118, 215);
    text-decoration: underline;
}

.time-suffix {
    color: #7d7d7d;
}

.country-code {
    user-select: none;
}

.personal-introduction {
    font-size: 13px;
    margin-bottom: 20px;
}

.greeting {
    font-size: 1em;
    font-weight: bold;
    color: #24292e;
    margin-bottom: 10px;
}

.tip-section {
    border-left: 3.5px solid #1a7f37;
    padding: 0.5rem 1rem;
    margin-bottom: 20px;
}

.tip-section1 {
    border-left: 3.5px solid #d6d6d6;
    padding: 0rem 1rem;
    margin: 16px 0px;
}

.tip-header {
    display: flex;
    align-items: center;
    margin-bottom: 14px;
}

.tip-icon {
    margin-right: 7px;
    display: flex;
}

.tip-title {
    color: #1a7f37;
    font-weight: 600;
    line-height: 1;
}

.tip-content {
    font-size: 15px;
}

.tip-content a {
    color: #0366d6;
    text-decoration: none;
}

.tip-content a:hover {
    text-decoration: underline;
}

.description-en {
    margin-bottom: 15px;
    line-height: 1.6;
    color: #24292e;
}

.description-cn {
    line-height: 1.6;
    color: #64707b;
    font-style: italic;
}

.language-icons {
    display: flex;
    flex-wrap: wrap;
    gap: 8px;
    margin-top: 10px;
}

.language-icon {
    width: 46px;
    height: 46px;
    padding: 4px;
    border-radius: 4px;
    border: 1px solid #dddddddd;
}

.sponsors,
.sponsoring,
.achievements,
.highlights,
.organizations {
    margin-top: 24px;
}



.tip,
.languages,
.sessions,
.projects,
.pinned {
    margin-bottom: 24px;
}

.session-list {
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    gap: 16px;
}

.pinned-repos {
    display: grid;
    grid-template-columns: repeat(2, 1fr);
    gap: 16px;
}

.repo {
    border: 1px solid #d1d9e1;
    border-radius: 6px;
    padding: 16px;
}

.project-experience {
    margin-top: 20px;
    display: flex;
    flex-direction: column;
    border: 1px solid #d1d9e1;
}

.project-card {
    border-bottom: 1px solid #d1d9e1;
    padding: 20px;
    background-color: #fff;
    transition: box-shadow 0.2s ease;
}

.project-header {
    display: flex;
    justify-content: space-between;
    align-items: flex-start;
    margin-bottom: 12px;
}

.project-header h3 {
    margin: 0;
    font-size: 16px;
    font-weight: 600;
    color: #0969da;
    line-height: 1.25;
    text-decoration: underline;
}

.project-date {
    font-size: 12px;
    color: #656d76;
    background-color: #f3f5f7;
    padding: 4px 8px;
    border-radius: 12px;
    font-weight: 500;
    min-width: 115px;
}

.project-description {
    margin-bottom: 16px;
}

.project-description p {
    margin: 0 0 8px 0;
    font-size: 14px;
    line-height: 1.5;
    color: #656d76;
}

.project-description p:first-child {
    color: #24292f;
    font-weight: 500;
}

.project-tech {
    display: flex;
    flex-wrap: wrap;
    gap: 8px;
}



.view-more-simple {
    text-align: center;
    padding: 10px;
    color: #0969da;
    font-size: 14px;
}



@media (max-width: 768px) {
    .container {
        flex-direction: column;
        padding: 16px;
        gap: 10px;
    }

    .left-column,
    .right-column {
        width: 100%;
    }



    .session-list {
        grid-template-columns: 1fr;
    }

    .pinned-repos {
        grid-template-columns: 1fr;
    }

    .profile-header {
        margin-top: 20px;
        display: flex;
        align-items: center;
    }

    .profile .avatar {
        width: 66.4px;
        height: 66.4px;
        margin-right: 16px;
        box-shadow: 0 0 0 1px #1f232826;
        border: 1px solid #d1d9e0;
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

    .language-icon {
        width: 34px;
        height: 34px;
        border-radius: 4px;
        padding: 2px;
        border: 1px solid #dddddddd;
    }

    .sponsors,
    .sponsoring,
    .achievements,
    .highlights,
    .organizations {
        display: none;
    }

    .project-header {
        flex-direction: column;
        align-items: flex-start;
        gap: 10px;
    }

}
</style>