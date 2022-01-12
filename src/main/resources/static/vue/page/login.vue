<template>
  <div class="login-page">
    <div class="login-window">
      <div class="error-window" v-bind:class="{ show: isError }">
        <div class="icon"><i class="fas fa-exclamation-triangle"></i></div>
        <div class="reason">{{ error_reason }}</div>
      </div>
      <div class="inputField user_name">
        <label for="user_name">ユーザ名</label>
        <input
          type="text"
          v-model="user_name"
          v-on:input="check('u', $event)"
        />
        <span class="error_text" v-if="inputErrors[0] != ''">{{
          inputErrors[0]
        }}</span>
      </div>
      <div class="inputField password">
        <label for="password">パスワード</label>
        <input
          type="password"
          id="password"
          v-model="password"
          v-on:input="check('p', $event)" v-on:keyPress="passEnter($event)"
        />
        <span class="error_text" v-if="inputErrors[1] != ''">{{
          inputErrors[1]
        }}</span>
      </div>
      <button
        v-on:click="doLogin"
        class="loginProcess btn primary"
        v-bind:class="{ processing: isProcessing, disable: (inputErrors.join('') != '') }"
      >
        <div v-if="isProcessing"><i class="fas fa-spinner"></i></div>
        <div v-else>ログイン</div>
      </button>
    </div>
  </div>
</template>

<script>
module.exports = {
  props:[],
  data() {
    return {
      user_name: "",
      password: "",
      isProcessing: false,
      isError: false,
      error_reason: "",
      inputErrors: [],
    };
  },
  methods: {
    loggedIn(){
      this.$router.push(this.$APPDATA.loginBeforeViewPage.toLowerCase() == "/login" ? "/" : this.$APPDATA.loginBeforeViewPage);
    },
    passEnter(e) {
      if (e.key == "Enter") {
        e.preventDefault();
        this.doLogin();
      }
    },
    validName(name) {
      const pt = /[!?_/'":;\^\[\]\\]+/;
      return !pt.test(name);
    },
    check(t, e) {
      let value = e.srcElement.value;
      if (t == "u") {
        this.inputErrors[0] = "";
        if (value == "") {
          this.inputErrors[0] = "このフィールドは必須です。";
        } else if (!this.validName(value)) {
          this.inputErrors[0] = "使用できない文字が含まれています。";
        }
      } else if (t == "p") {
        this.inputErrors[1] = "";
        if (value == "") {
          this.inputErrors[1] = "このフィールドは必須です。";
        }
      }
    },
    doLogin() {
      if (
        this.isProcessing ||
        !this.inputErrors.every((c) => {
          return c == "";
        })
      )
        return;
      this.isProcessing = true;
      this.$set(this, "isError", false);
      // 文字チェック
      if (!this.validName(this.user_name)) {
        this.error_handle("使用できない文字が含まれています。");
        this.isProcessing = false;
        return;
      }
      fetch("/api/v1/auth/login", {
        method: "GET",
        headers: new Headers({
          "content-type": "application/json",
          Authorization: btoa(unescape(encodeURIComponent(this.user_name + ":" + this.password))),
        }),
      })
        .then((res) => res.json())
        .then((loginResult) => {
          if (loginResult.status == "ok") {
            this.$APPDATA.isLogin = true;
            this.$APPDATA.disconnectedDetected = false;
            this.loggedIn();
          } else {
            this.error_handle(`ログインに失敗しました。
                               ユーザ名とパスワードをお確かめください。`);
          }
          this.isProcessing = false;
        })
        .catch((error) => {
          console.error(error);
          this.error_handle("エラーが発生しました。");
          this.isProcessing = false;
          this.$set(this, "isError", true);
        });
    },
    error_handle(reason) {
      this.$set(this, "isError", true);
      this.error_reason = reason;
    },
  },
};
</script>

<style scoped>
.login-page {
  background: rgb(184, 184, 184);
  display: flex;
  justify-content: center;
  align-items: center;
}

.login-window {
  position: relative;
  width: 30rem;
  background: #fff;
  box-shadow: #000 1px 1px 1px;
  border-radius: 10px;
  padding: 20px 20px;
}

.error-window {
  display: none;
  padding: 10px;
  background: rgb(255, 227, 227);
  color: rgb(255, 100, 100);
  white-space: pre-line;
  margin-bottom: 10px;
}

.error-window.show {
  display: flex;
}

.error-window > * {
  margin-right: 10px;
}

.loginProcess {
  font-size: 1.2em;
}

.loginProcess div {
  transform: rotate(0deg);
}

.loginProcess.processing div {
  animation-name: rotate;
  animation-duration: 1s;
  animation-timing-function: steps(7);
  animation-iteration-count: infinite;
}

@keyframes rotate {
  0% {
    transform: rotate(0deg);
  }
  50% {
    transform: rotate(180deg);
  }
  100% {
    transform: rotate(360deg);
  }
}

.error_text {
  color: #f00;
}
</style>