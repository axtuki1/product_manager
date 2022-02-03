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
          v-bind:type="isPasswordShow ? 'text' : 'password'"
          id="password"
          v-model="password"
          v-on:input="check('p', $event)"
          v-on:keyPress="passEnter($event)"
        />
        <span class="error_text" v-if="inputErrors[1] != ''">{{
          inputErrors[1]
        }}</span>
      </div>
      <div class="inputField showPassword">
        <input type="checkbox" id="showPassword" v-model="isPasswordShow" />
        <label for="showPassword">
          <button
            class="checkboxView"
            v-on:click="isPasswordShow = !isPasswordShow"
          ></button>
          パスワードを表示
        </label>
      </div>
      <div id="recaptcha" class="recaptcha"></div>
      <button
        v-on:click="doLogin"
        class="loginProcess btn primary"
        v-bind:class="{
          processing: isProcessing,
          disable: inputErrors.join('') != '' || !recaptchaAccept,
        }"
      >
        <div v-if="isProcessing"><i class="fas fa-spinner"></i></div>
        <div v-else>ログイン</div>
      </button>
    </div>
  </div>
</template>

<script>
module.exports = {
  props: [],
  data() {
    return {
      user_name: "",
      password: "",
      isProcessing: false,
      isError: false,
      error_reason: "",
      inputErrors: [],
      isPasswordShow: false,
      recaptchaId: null,
      recaptchaAccept: false,
      recaptchaToken: "",
      recaptchaSiteKey: "",
    };
  },
  mounted() {
    fetch("/api/v1/recaptchaKey")
      .then((d) => d.json())
      .then((json) => {
        this.recaptchaSiteKey = json.key;
        if (this.recaptchaSiteKey != "") {
          setTimeout(() => {
            this.recaptchaId = grecaptcha.render("recaptcha", {
              sitekey: this.recaptchaSiteKey,
              theme: "light",
              callback: (res) => {
                this.recaptchaAccept = true;
                this.isError = false;
                this.recaptchaToken = res;
              },
              "expired-callback": () => {
                this.error_handle(`recaptcha認証が期限切れです。
                               もう一度実行してください。`);
              },
              "error-callback": () => {
                this.error_handle(`recaptcha認証に失敗しました。
                               時間を空けて再試行してください。`);
              },
            });
          }, 0.5 * 1000);
        } else {
          this.recaptchaAccept = true;
        }
      });
  },
  methods: {
    recaptchaReset() {
      if(this.recaptchaSiteKey == "") return;
      grecaptcha.reset(this.recaptchaId);
      this.recaptchaAccept = false;
    },
    loggedIn() {
      this.$router.push(
        this.$APPDATA.loginBeforeViewPage.toLowerCase() == "/login"
          ? "/"
          : this.$APPDATA.loginBeforeViewPage
      );
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
        !this.recaptchaAccept ||
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
        method: "POST",
        headers: new Headers({
          "content-type": "application/json",
          Authorization: btoa(
            unescape(encodeURIComponent(this.user_name + ":" + this.password))
          ),
        }),
        body: JSON.stringify({
          token: this.recaptchaToken,
        }),
      })
        .then((res) => res.json())
        .then((loginResult) => {
          if (loginResult.status == "ok") {
            this.$APPDATA.isLogin = true;
            this.$APPDATA.disconnectedDetected = false;
            this.loggedIn();
          } else {
            if (loginResult.errorCode == "recaptcha-failed") {
              this.error_handle(
                `recaptcha認証に失敗しました。
                               ` + loginResult.reason.join(", ")
              );
            } else {
              this.error_handle(`ログインに失敗しました。
                               ユーザ名とパスワードをお確かめください。`);
            }
            this.recaptchaReset();
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

.recaptcha {
  margin-bottom: 20px;
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

.showPassword input {
  display: none;
}

.showPassword label {
  display: flex;
  align-items: center;
  user-select: none;
  cursor: pointer;
  height: calc(1.2em + 2px);
}

.showPassword label .checkboxView {
  position: relative;
  display: inline-block;
  width: calc(1.2em + 2px);
  height: calc(1.2em + 2px);
  border: 1px solid #000;
  border-radius: 5px;
  margin-right: 10px;
  margin-bottom: 2px;
  cursor: pointer;
  background: rgb(255, 255, 255);
}

.showPassword .checkboxView:after {
  position: absolute;
  content: "";
  width: 10px;
  height: 20px;
  top: -10px;
  left: 5px;
  border-top: 5px solid rgb(67, 109, 247);
  border-left: 5px solid rgb(67, 109, 247);
  transform: rotate(225deg) scale(0);
  transition: transform ease-in-out 100ms;
}

.showPassword input:checked + label .checkboxView:after {
  transform: rotate(225deg) scale(1);
}
</style>