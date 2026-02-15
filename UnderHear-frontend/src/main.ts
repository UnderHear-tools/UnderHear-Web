import { createApp } from 'vue'
import { createPinia } from 'pinia'
import './style.css'
import App from './App.vue'
import router from './router'
import { useUserStore } from '@/stores/user'

import '@/module/application/lib/monaco/setupMonaco'

const app = createApp(App)
const pinia = createPinia()
app.use(pinia)
app.use(router)
const userStore = useUserStore(pinia)

Promise.all([router.isReady(), userStore.hydrateUser()]).then(() => {
  app.mount('#app')
})
