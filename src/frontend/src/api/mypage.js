import { api } from './api'; // 공통 API import

const mypageUrl = {
    //  user 조회
    getUser: 'api/mypage/getUser',
};

export const mypage = {
    getUser(params) {
        return api.serviceGet(mypageUrl.getUser, params);
    }
};
