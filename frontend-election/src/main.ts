import './assets/main.css'

import { createApp } from 'vue'
import router from './router/router'
import App from './App.vue'

import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap-vue/dist/bootstrap-vue.css';
window.global = window;

createApp(App)
  .use(router)
  .mount('#app')