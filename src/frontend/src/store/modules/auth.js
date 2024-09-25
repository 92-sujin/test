// store/modules/auth.js
import { jwtDecode } from 'jwt-decode'; // 이름 있는 내보내기로 import

/*
 state : 공통으로 사용할 변수, mutaitons을 통해서만 변경이 가능
 mutation: state를 변경시킴, 동기처리, 함수형태로 작성, commit('함수명','전달인자')
 actions: mutation을 실행, 비동기(콜백함수), 함수형태로 작성, dispatch('함수명','전달인자')
 getters : state의 데이터를 가져오거나 가공
 */
const state = {
    token: localStorage.getItem('jwt') || null,
    user: null
};

if (state.token) {
    state.user = jwtDecode(state.token);
}

const mutations = {
    setToken(state, token) {
        state.token = token;
        state.user = jwtDecode(token);
    },
    clearToken(state) {
        state.token = null;
        state.user = null;
    }
};

const actions = {
    saveToken({ commit }, token) {
        commit('setToken', token);
        localStorage.setItem('jwt', token);
    },
    logout({ commit }) {
        commit('clearToken');
        localStorage.removeItem('jwt');
    }
};

const getters = {
    isAuthenticated: state => !!state.token,
    userEmail: state => state.user?.email,
    userName: state => state.user?.name,
    loginId: state => state.user?.loginId
};

export default {
    namespaced: true,
    state,
    mutations,
    actions,
    getters
};
