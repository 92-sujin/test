import { jwtDecode } from 'jwt-decode';

const state = {
    token: localStorage.getItem('jwt') || null,
    user: {
        username: '',
        userid: '',
        userEmail: ''
    },
    isRedirected: false, // 리다이렉션 여부 추가
};

// JWT 토큰이 있는 경우 디코드된 데이터를 유저 객체에 저장
if (state.token) {
    const decoded = jwtDecode(state.token);
    state.user.username = decoded.name || '';  // decoded 내에 있는 값을 매핑
    state.user.userid = decoded.loginId || '';
    state.user.userEmail = decoded.email || '';
}

const mutations = {
    setToken(state, token) {
        state.token = token;
        const decoded = jwtDecode(token);  // token 디코딩

        // JWT에서 받은 데이터를 user 객체에 각각 매핑
        state.user.username = decoded.name || '';   // 이름
        state.user.userid = decoded.loginId || '';  // 로그인 ID
        state.user.userEmail = decoded.email || ''; // 이메일
        console.log("Decoded user = ", state.user);
    },
    clearToken(state) {
        state.token = null;
        state.user = {  // 유저 정보를 초기화
            username: '',
            userid: '',
            userEmail: ''
        };
    },
    // 리다이렉션 상태를 설정하는 mutation
    setRedirected(state, value) {
        state.isRedirected = value; // 상태 업데이트
    },
    setUserId(state, userId) {
        state.user.userid = userId;
    }

};

const actions = {
    // 로컬스토리지에 토큰 저장
    saveToken({ commit }, token) {
        commit('setToken', token);  // 토큰 저장
        localStorage.setItem('jwt', token);  
    },
    // 로컬스토리지에서 토큰 삭제
    logout({ commit }) {
        commit('clearToken');  // 토큰 삭제
        localStorage.removeItem('jwt');  
    },
    // 리다이렉션 상태 업데이트
    setRedirected({ commit }, value) {
        commit('setRedirected', value); // mutation 호출
    },
    // userId 저장 
    saveUserId({ commit }, userid) {
        commit('setUserId', userid);  
    }
};

const getters = {
    isAuthenticated: state => !!state.token,  // 토큰이 있으면 인증
    userEmail: state => state.user.userEmail,  // 이메일 가져오기
    userName: state => state.user.username,    // 유저 이름 가져오기
    loginId: state => state.user.userid,       // 로그인 ID 가져오기
    isRedirected: state => state.isRedirected, // 리다이렉션 상태 가져오기
};

export default {
    namespaced: true,
    state,
    mutations,
    actions,
    getters
};
