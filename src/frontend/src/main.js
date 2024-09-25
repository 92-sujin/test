import { createApp } from 'vue';
import App from './App.vue';
import router from './router';
import store from './store';

createApp(App)
    .use(router) // 라우터를 앱에 사용
    .use(store)  // Vuex store를 앱에 사용
    .mount('#app'); // 앱을 #app 요소에 마운트
