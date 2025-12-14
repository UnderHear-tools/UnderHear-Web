import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
import router from './router'

import '@/monaco/setupMonaco'

const app = createApp(App)
app.use(router)
router.isReady().then(() => {
  app.mount('#app')
})
