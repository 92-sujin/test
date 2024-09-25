import { createRouter, createWebHistory } from 'vue-router';
import Home from '../views/Home.vue';
import Login from '../views/LoginPage.vue';
import store from "@/store";

const routes = [
    {
        path: '/home',
        name: 'Home',
        component: Home
    },
    {
        path: '/login',
        name: 'Login',
        component: Login
    },
    {
        path: '/',
        redirect: '/login'
    }
];

// Vue 3에서는 createRouter를 사용해야 합니다.
const router = createRouter({
    history: createWebHistory(),  // 'history' 모드를 이렇게 설정합니다.
    routes
});

// 네비게이션 가드 추가
router.beforeEach((to, from, next) => {

    // 홈페이지 진입시 JWT 토큰이 있을 경우 홈페이지로 이동
    const isAuthenticated = store.getters['auth/isAuthenticated'];

    if (!isAuthenticated && to.name !== 'Login') {
        console.log("로그인페이지")
        next('/login');
    } else if (isAuthenticated && to.name === 'Login') {
        next('/home');
        console.log("홈페이지")
    } else {
        next();
        console.log(3)
    }
});

export default router;
