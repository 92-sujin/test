import { api } from './api'; // 공통 API import

const loginUrl = {
    // 로그인
    login: 'api/login',
    // sns 로그인
    loginSns: 'api/login/sns',
    // 로그인 취소
    loginSnsCancel: 'api/login/sns/cancel'
};

export const login = {
    login(data) {
        return api.servicePost(loginUrl.login, data);
    },
    loginSns(data) {
        return api.servicePost(loginUrl.loginSns, data);
    },
    loginSnsCancel(data) {
        return api.servicePost(loginUrl.loginSnsCancel, data);
    }
};
