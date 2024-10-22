import { createRouter, createWebHistory } from 'vue-router';
import Home from '../views/Home.vue';
import Login from '../views/LoginPage.vue';
import SignIn from '../views/SignIn.vue';
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
        path: '/SignIn',
        name: 'SignIn',
        component: SignIn
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

    // JWT 토큰이 있는지 확인
    const isAuthenticated = store.getters['auth/isAuthenticated'];
    const isRedirected = store.getters['auth/isRedirected'];

    if (isRedirected) {
        next();
    }

    // 로그인되지 않은 경우 (isAuthenticated가 false)
    if (!isAuthenticated) {
        // 로그인 페이지나 회원가입 페이지는 예외로 처리
        if (to.name === 'Login' || to.name === 'SignIn') {
            next(); // 로그인 또는 회원가입 페이지로 이동 허용
        } else {
            console.log("로그인 페이지로 리다이렉션");
            next('/login'); // 그 외의 페이지는 로그인 페이지로 리다이렉션
        }
    } else {
        if (!isRedirected) {
            next();
        } else {
            // 정상적으로 폰번호가 존재하면 홈으로 이동
            next('/home');
        }
    }
});


export default router;
