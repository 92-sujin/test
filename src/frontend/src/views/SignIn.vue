<template>
  <div class="signup-container">
    <h1>Sign Up</h1>
    <form @submit.prevent="save">
      <div class="form-group" v-if="!isSnsLogin">
        <label for="loginId">Login ID</label>
        <input type="text" v-model="formData.loginId" :required="!isSnsLogin" />
        <span v-if="errors.loginId" class="error">{{ errors.loginId }}</span>
      </div>

      <div class="form-group">
        <label for="userName">Name</label>
        <input type="text" v-model="formData.userName" required />
        <span v-if="errors.userName" class="error">{{ errors.userName }}</span>
      </div>

      <div class="form-group">
        <label for="userEmail">Email</label>
        <input type="email" v-model="formData.userEmail" required />
        <span v-if="errors.userEmail" class="error">{{ errors.userEmail }}</span>
      </div>

      <div class="form-group" v-if="!isSnsLogin">
        <label for="userPassword">Password</label>
        <input type="password" v-model="formData.userPassword" :required="!isSnsLogin" />
        <span v-if="errors.userPassword" class="error">{{ errors.userPassword }}</span>
      </div>

      <div class="form-group">
        <label for="phoneNumber">Phone Number</label>
        <input type="tel" v-model="formData.phoneNumber" />
      </div>

      <button type="submit" class="submit-btn">Sign Up</button>
    </form>
    <p v-if="errorMessage" class="error">{{ errorMessage }}</p>
  </div>
</template>


<script>
import { login } from "@/api/login";
import { user } from "@/api/user";
import {mapGetters} from "vuex";

export default {
  data() {
    return {
      formData: {
        loginId: '',
        userName: '',
        userEmail: '',
        userPassword: '',
        phoneNumber: ''
      },
      isSnsLogin: false, // SNS 로그인 여부 확인
      errors: {},
      errorMessage: ''
    };
  },
  mounted() {
    // URL에서 source 파라미터 확인하여 SNS 로그인 여부 설정
    const source = this.$route.query.source;
    if (source === 'sns') {
      this.isSnsLogin = true; // SNS 로그인일 때 로그인 ID와 비밀번호 필드를 숨김
      this.formData.loginId = this.loginId; // SNS 로그인시 loginId 설정
      this.formData.userEmail = this.userEmail;
    }

    // 페이지를 떠나기 전 처리
    window.addEventListener('beforeunload', this.handleBeforeUnload);
  },
  beforeUnmount() {
    // 컴포넌트가 파괴될 때 이벤트 리스너 제거
    window.removeEventListener('beforeunload', this.handleBeforeUnload);
  },
  computed: {
    ...mapGetters('auth', ['loginId','userEmail']) // 'userId'를 computed 속성으로 매핑
  },
  methods: {
    // 페이지를 떠날 때 호출되는 메서드
    handleBeforeUnload(event) {
      this.cancelSnsLogin(this.formData.loginId)

      // 사용자에게 경고 메시지 표시
      event.preventDefault(); // Chrome에서는 이 라인이 필요함
      event.returnValue = ''; // 기본 경고 메시지 표시
    },
    /**
     * 로그인 취소
     *
     * @param loginId 로그인 아이디
     */
    cancelSnsLogin(loginId) {
      localStorage.removeItem('jwt');

      login.loginSnsCancel({ loginId })
      .then(response => {
        localStorage.removeItem('jwt');
        console.log("SNS login canceled:", response);
      })
      .catch(err => {
        console.error("Error canceling SNS login:", err);
      });
    },
    /**
     * form 유효성 체크
     */
    validateForm() {
      this.errors = {};

      if (!this.isSnsLogin && !this.formData.loginId) {
        this.errors.loginId = "Login ID is required.";
      }
      if (!this.formData.userName) {
        this.errors.userName = "Name is required.";
      }
      if (!this.formData.userEmail) {
        this.errors.userEmail = "Email is required.";
      }
      if (!this.isSnsLogin && !this.formData.userPassword) { // SNS 로그인 아닐 때만 비밀번호 체크
        this.errors.userPassword = "Password is required.";
      }

      return Object.keys(this.errors).length === 0;
    },

    /** 회원가입 **/
    save() {
      if (!this.validateForm()) {
        return;
      }

      let userinfo = [this.formData];

      console.log("userInfo",JSON.stringify(userinfo));
      user.createUser(userinfo)
      .then(res => {
        if (res.code === "200") {
          this.$router.push('/login');
        } else {
          this.errorMessage = res.message || "An error occurred during sign-up.";
        }
      })
      .catch(err => {
        this.errorMessage = err.message;
      });
    }
  }
};
</script>
<style scoped>
.signup-container {
  width: 100%;
  max-width: 400px;
  margin: 0 auto;
  background-color: #f9f9f9;
  padding: 30px;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

h1 {
  text-align: center;
  font-family: 'Arial', sans-serif;
  color: #333;
  margin-bottom: 20px;
}

.form-group {
  margin-bottom: 15px;
}

label {
  display: block;
  margin-bottom: 5px;
  font-weight: bold;
  font-family: 'Arial', sans-serif;
  color: #555;
}

input {
  width: 100%;
  padding: 10px;
  font-size: 16px;
  border-radius: 5px;
  border: 1px solid #ccc;
  transition: border-color 0.3s ease;
}

input:focus {
  border-color: #007BFF;
}

.submit-btn {
  width: 100%;
  padding: 10px;
  background-color: #007BFF;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 16px;
  margin-top: 20px;
}

.submit-btn:hover {
  background-color: #0056b3;
}

.error {
  color: red;
  font-size: 14px;
}

p.error {
  text-align: center;
  font-family: 'Arial', sans-serif;
  color: #e74c3c;
  margin-top: 15px;
}
</style>

