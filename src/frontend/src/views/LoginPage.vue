<template>
  <div class="login-container">
    <h1>Login</h1>
    <form @submit.prevent="login" class="login-form">
      <input v-model="userId" placeholder="Username" class="input-field" />
      <br>
      <input v-model="password" type="password" placeholder="Password" class="input-field" />
      <br>
      <button type="submit" class="login-btn">Login</button>
    </form>

    <div class="oauth-container">
      <h2>SNS 로그인</h2>
      <a :href="googleLoginUrl" class="oauth-btn google">
        Login with Google
      </a>
      <a :href="azureLoginUrl" class="oauth-btn azure">
       Login with Azure
      </a>
      <a :href="naverLoginUrl" class="oauth-btn naver">
        Login with Naver
      </a>
      <a :href="kakaoLoginUrl" class="oauth-btn kakao">
      Login with Kakao
      </a>
    </div>
  </div>
</template>

<script>
import {mapActions} from "vuex";
import {api,url} from "@/api/api";

export default {
  data() {
    return {
      userId: '',
      password: '',
      googleLoginUrl: 'http://localhost:8080/oauth2/authorization/google',
      azureLoginUrl: 'http://localhost:8080/oauth2/authorization/azure',
      naverLoginUrl: 'http://localhost:8080/oauth2/authorization/naver',
      kakaoLoginUrl: 'http://localhost:8080/oauth2/authorization/kakao',
    };
  },

  mounted() {
    const token = this.$route.query.token;
    console.log("sns로그인 토큰 ",token)
    if (token) {
      this.saveToken(token);  // Vuex에 토큰 저장
      this.$router.push('/home'); // 홈 화면으로 이동
    }
  },
  methods: {
    ...mapActions('auth', ['saveToken']),

    login() {
      console.log("로그인")

      let param = {
        loginId: this.userId,
        userPassword: this.password
      }

      api.servicePost(url.login, param) // POST 요청으로 변경
          .then(res => {
            console.log("res",res);
            if (res.code === "200") {
              // 로그인 성공 시
              const token = res.jwtToken;
              this.saveToken(token);
              this.$router.push('/home'); // 홈 화면으로 이동
            } else {
              // 로그인 실패 시 에러 메시지 표시
              this.errorMessage = res.data.message;
              console.error(res.data.message);
            }
          })
          .catch(error => {
            console.error("로그인 실패", error);
            this.errorMessage = "로그인 요청에 실패했습니다.";
          });
    }
  }
};
</script>
<style scoped>
.login-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100vh;
  background-color: #f0f0f0;
}

.login-form {
  margin-bottom: 20px;
}

.input-field {
  width: 300px;
  padding: 10px;
  margin: 10px 0;
  border-radius: 5px;
  border: 1px solid #ccc;
}

.login-btn {
  width: 300px;
  padding: 10px;
  border-radius: 5px;
  border: none;
  background-color: #4caf50;
  color: white;
  font-size: 16px;
  cursor: pointer;
}

.oauth-container {
  text-align: center;
}

.oauth-btn {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 300px;
  padding: 10px;
  margin: 10px 0;
  border-radius: 5px;
  text-align: center;
  color: white;
  font-size: 16px;
  text-decoration: none;
  cursor: pointer;
  font-weight: bold;
}

.oauth-btn img {
  margin-right: 10px;
  width: 24px;
  height: 24px;
}

.google {
  background-color: #db4437;
}

.azure {
  background-color: #0078d4;
}

.naver {
  background-color: #03c75a;
}

.kakao {
  background-color: #fee500;
  color: #3d1d1d;
}
</style>
