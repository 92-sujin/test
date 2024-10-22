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

    <button class="login-btn" @click="goToSignIn">회원가입</button>

    <div class="oauth-container">
      <h2>SNS 로그인</h2>
      <a :href="googleLoginUrl" class="oauth-btn google">Login with Google</a>
      <a :href="naverLoginUrl" class="oauth-btn naver">Login with Naver</a>
      <a :href="kakaoLoginUrl" class="oauth-btn kakao">Login with Kakao</a>
    </div>
  </div>
</template>

<script>
import {mapActions, mapGetters} from "vuex";
import { login } from "@/api/login";

export default {
  data() {
    return {
      userId: '',
      password: '',
      googleLoginUrl: 'http://localhost:8080/oauth2/authorization/google',
      naverLoginUrl: 'http://localhost:8080/oauth2/authorization/naver',
      kakaoLoginUrl: 'http://localhost:8080/oauth2/authorization/kakao',
    };
  },

  mounted() {
    const token = this.$route.query.token;
    console.log("SNS 로그인 토큰: ", token);

    if (token) {
      // 토큰을 저장하고 사용자 이메일을 이용한 추가 정보 요청
      this.saveToken(token).then(() => {
        const userEmail = this.$store.getters['auth/userEmail'];
        console.log("userEmail:", userEmail);

        if (userEmail) {
          this.processSnsLogin(userEmail);
        } else {
          console.error("사용자 이메일이 유효하지 않습니다.");
        }
      });
    }
  },

  computed :{
    ...mapGetters('auth', ['userEmail', 'userName', 'loginId'])  // loginId 매핑
  },

  methods: {
    ...mapActions('auth', ['saveToken', 'logout']),

    /**
     * 로그인
     */
    login() {
      console.log("로그인");

      const param = {
        loginId: this.userId,
        userPassword: this.password
      };
      // login.js의 login 함수 호출
      login.login(param)
          .then(res => {
            if (res.code === "200") {
              // 로그인 성공 시 JWT 토큰을 저장하고 홈 화면으로 이동
              const token = res.jwtToken;
              this.saveToken(token).then(() => {
                this.$router.push('/home');
              });
            } else {
              console.error("로그인 실패: ", res.message);
            }
          })
          .catch(error => {
            console.error("로그인 에러: ", error);
            alert("로그인 요청에 실패했습니다.");
        });
    },
    /**
     * 회원가입 페이지로 이동
     */
    goToSignIn() {
      this.$router.push('/SignIn');
    },
    /**
     * SNS 로그인
     *
     * @param userEmail SNS로 로그인 한 EMAIL 주소
     */
    processSnsLogin(userEmail) {
      login.loginSns({ userEmail })
          .then(res => {
            // 핸드폰 번호 store에 저장
            this.$store.dispatch('auth/saveUserId', res.userInfo.loginId);  // 로그인 ID 저장
            if (res.code === "200") {
              if (!res.isPhoneNumber) {
                console.log("핸드폰 번호가 없습니다. 회원가입 페이지로 리디렉션");
                this.$router.push({ path: '/SignIn', query: { source: 'sns' } });
                this.$store.dispatch('auth/setRedirected', true);
              } else {
                console.log("핸드폰 번호가 존재합니다. 홈으로 리디렉션");
                this.$router.push('/home');
              }
            }
          })
          .catch(error => {
            console.error("SNS 로그인 에러: ", error);
            localStorage.removeItem('jwt');
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
  margin: 10px 0;
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

.naver {
  background-color: #03c75a;
}

.kakao {
  background-color: #fee500;
  color: #3d1d1d;
}
</style>
