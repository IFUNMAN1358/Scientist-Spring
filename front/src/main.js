import { createApp } from 'vue';
import App from './App.vue';
import router from '@/js/config/router';
import VueCookies from 'vue-cookies';
import store from "@/js/config/store";

const app = createApp(App);

store.actions.initializeStore();

app.use(router)
app.use(VueCookies)
app.mount('#app')