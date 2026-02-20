import { createApp } from 'vue'
import { createPinia } from 'pinia'

//引入全局样式
import './css/style.css'
import './css/themes/light.css'
import './css/themes/dark.css'

import App from './App.vue'
import router from './router'
import { useUserStore } from '@/stores/user'

import '@/modules/application/lib/monaco/setupMonaco'

const app = createApp(App)
const pinia = createPinia()
app.use(pinia)
app.use(router)
const userStore = useUserStore(pinia)

Promise.all([userStore.hydrateUser(), router.isReady()]).then(() => {
  app.mount('#app')
})
