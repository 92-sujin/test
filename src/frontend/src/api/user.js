import { api } from './api'; // 공통 API import

const userUrl = {
    // 유저 생성
    createUser: 'api/user/saveUser',
};

export const user = {
    createUser(data) {
        return api.servicePost(userUrl.createUser, data);
    }
};
