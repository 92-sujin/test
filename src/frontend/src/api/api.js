import axios from 'axios';

// api url 정보
export const url = {
    // 로그인
    login: 'api/login',
    // 마이페이지
    getUser: 'api/mypage/getUser', // 유저 정보 가져오기
};

// Axios 인스턴스 생성
const axiosInstance = axios.create({
    baseURL: 'http://localhost:8080/', // 기본 URL 설정
    headers: {
        'Content-Type': 'application/json'
    }
});

axiosInstance.interceptors.request.use(request => {
    console.log('Starting Request', request);
    return request;
});

// 공통 GET 요청 함수
const serviceGet = (url, params = {}) => {
    return axiosInstance.get(url, { params })  // params를 객체로 전달
        .then(response => response.data)
        .catch(error => {
            console.error('GET 요청 에러:', error);
            console.error('url:', url);
            console.error('params:', params);
            throw error;
        });
};

// 공통 POST 요청 함수
const servicePost = (url, data) => {
    return axiosInstance.post(url, data)
        .then(response => response.data)
        .catch(error => {
            console.error('POST 요청 에러:', error);
            throw error;
        });
};

// 공통 PUT 요청 함수
const servicePut = (url, data) => {
    return axiosInstance.put(url, data)
        .then(response => response.data)
        .catch(error => {
            console.error('PUT 요청 에러:', error);
            throw error;
        });
};

// 공통 DELETE 요청 함수 (필요할 경우)
const serviceDelete = (url) => {
    return axiosInstance.delete(url)
        .then(response => response.data)
        .catch(error => {
            console.error('DELETE 요청 에러:', error);
            throw error;
        });
};

export const api= {
    serviceGet,
    servicePost,
    servicePut,
    serviceDelete
}
