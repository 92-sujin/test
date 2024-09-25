<template>
  <div class="home-container">
    <h1>User Name = {{ userName }}</h1>
    <h1>User Email = {{ userEmail }}</h1>

    -----------------------------------------
    <h1> 로그인한 ID는 {{this.loginId}} 이며 {{ this.provider}}로 로그인 하셨습니다.</h1>
    <button @click="logOut">로그아웃</button>
  </div>
</template>

<script>
import {mapActions, mapGetters} from 'vuex';
import {api,url} from "@/api/api";

export default {
  name: 'HomePage',  // 이름이 규칙을 따르는지 확인 (vue/multi-word-component-names)
  data() {
    return {
      loginId:'',
      provider:''
    };
  },
  computed: {
    // 이메일 가져오기
    // userEmail() {
    //   return this.$store.getter['auth/userEmail']
    // },
    ...mapGetters('auth', ['userEmail', 'userName']) // auth 모듈의 getter를 컴포넌트의 computed 속성으로 매핑
  },

  methods: {
    ...mapActions('auth',['logout']),
    getUrl() {
      let param = {
        email: this.userEmail
      }
      api.serviceGet(url.getUser, param).then(res => {
          this.loginId = res.response[0].loginId;
          this.provider = res.response[0].provider;
          console.log("this.loginId == " ,this.loginId);
      })
    },
    logOut() {
      // this.$store.dispatch('auth/logout')
      //     .then(() => {
      //       console.log("Logged out successfully");
      //     });
      // Vuex의 'logout' 액션을 호출
      this.logout().then(() => {
        console.log("Logged out successfully");
        // 로그인 페이지로이동
        this.$router.push('/login');
      }).catch(error => {
        console.error("Logout failed:", error);
      });
    }
  },
  created() {
    this.getUrl()
  }

};
</script>

<style scoped>
.home-container {
  text-align: center;
  margin: 20px;
}
</style>
