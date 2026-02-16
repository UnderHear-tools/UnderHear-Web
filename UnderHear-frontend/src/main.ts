import { createApp } from 'vue'
import { createPinia } from 'pinia'
import './style.css'
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
