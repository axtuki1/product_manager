<template>
  <div class="page">
    <error v-if="FATALERROR_BOOL" :fatalerror="fatalerror"></error>
    <loading v-bind:class="{ show: loading }"></loading>
    <div class="page-view">
      <sidebar v-if="$APPDATA.isLogin" class="sidebar-wrapper"></sidebar>
      <div
        class="outline-content-wrapper"
        v-bind:class="{ loggined: $APPDATA.isLogin }"
      >
        <router-view class="outline-content"></router-view>
      </div>
    </div>
    <apiconsole></apiconsole>
    <div class="overlay-wrapper">
      <modal
        v-for="modal in modals"
        :key="modal.id"
        :modal="modal"
        @close="closeModal"
      >
      </modal>
      <notice
        v-for="notice in notices"
        :key="notice.id"
        :notice="notice"
        @close-n="closeNotice"
      >
        {{ notice.content }}
      </notice>
    </div>
  </div>
</template>

<script>
const router = new VueRouter({
  mode: "history",
  routes: [
    {
      path: "/",
      component: httpVueLoader("/vue/page/dashboard/index.vue"),
      children: [
        {
          path: "",
          component: httpVueLoader("/vue/page/dashboard/dashboard.vue"),
        },
      ],
    },
    { path: "/login", component: httpVueLoader("/vue/page/login.vue") },
    {
      path: "/product",
      component: httpVueLoader("/vue/page/product/index.vue"),
      children: [
        { path: "", component: httpVueLoader("/vue/page/product/search.vue") },
        { path: "new", component: httpVueLoader("/vue/page/product/new.vue") },
        { path: "genre", component: httpVueLoader("/vue/page/product/genre.vue") },
        {
          path: ":id",
          component: httpVueLoader("/vue/page/product/details.vue"),
          props: true,
        },
        {
          path: ":id/edit",
          component: httpVueLoader("/vue/page/product/edit.vue"),
          props: true,
        },
      ],
    },
    {
      path: "/settings",
      component: httpVueLoader("/vue/page/settings/index.vue"),
      children: [
        {
          path: "",
          component: httpVueLoader("/vue/page/settings/general.vue"),
        },
        {
          path: "/danger",
          component: httpVueLoader("/vue/page/settings/danger.vue"),
        },
      ],
    },
    {
      path: "/analytics",
      component: httpVueLoader("/vue/page/analytics/index.vue"),
      children: [
        {
          path: "",
          component: httpVueLoader("/vue/page/analytics/general.vue"),
        },
        {
          path: "history",
          component: null,
        },
        {
          path: "history/:id",
          component: httpVueLoader("/vue/page/analytics/details.vue"),
          props: true,
        },
        {
          path: "history/:id/edit",
          component: httpVueLoader("/vue/page/analytics/edit.vue"),
          props: true,
        },
      ],
    },
    { path: "*", component: httpVueLoader("/vue/notfound.vue") },
  ],
});

module.exports = {
  data() {
    return {
      loading: true,
      FATALERROR_BOOL: false,
      fatalerror: {
        title: "",
        reason: "",
      },
      modals: [],
      modalCount: 0,
      notices: [],
      noticeCount: 0,
    };
  },
  router: router,
  components: {
    loading: httpVueLoader("/vue/loading.vue"),
    sidebar: httpVueLoader("/vue/component/sidebar/sidebar.vue"),
    error: httpVueLoader("/vue/error.vue"),
    apiconsole: httpVueLoader("/vue/debug/apiconsole.vue"),
    modal: httpVueLoader("/vue/modal.vue"),
    notice: httpVueLoader("/vue/notice.vue"),
  },
  methods: {
    closeModal(id) {
      this.modals = this.modals.filter((item) => item.id != id);
    },
    closeNotice(id) {
      this.notices = this.notices.filter((item) => item.id != id);
    },
  },
  mounted() {
    const v = this;
    // 全体で用がありそうな変数とかメソッド
    Vue.prototype.$APPDATA.util_methods = {
      logout() {
        fetch("/api/v1/logout", {
          method: "GET",
          headers: new Headers({
            "content-type": "application/json",
          }),
        })
          .then((res) => res.json())
          .then((loginResult) => {
            if (loginResult.status == "ok") {
              v.$APPDATA.isLogin = false;
              v.$router.push("/login");
            }
          })
          .catch((error) => {
            console.log(error);
            v.$APPDATA.util_methods.fatal_error_handle(
              "ログアウト処理中にエラーが発生しました",
              "通信環境を再度確認してください。"
            );
          });
      },
      fatal_error_handle(title, reason) {
        v.FATALERROR_BOOL = true;
        v.fatalerror = {
          title: title,
          reason: reason,
        };
      },
      callModal(content, options) {
        const default_options = {
          title: "",
          force_ok: false,
          ok_btn: true
        };
        options = Object.assign(default_options, options);
        v.modals.push({
          id: v.modalCount,
          title: options.title,
          content: content,
          force_ok: options.force_ok,
          ok_btn: options.ok_btn,
          component: options.component,
        });
        v.modalCount++;
      },
      callNotice(content, options) {
        const default_options = {
          sec: 3,
        };
        options = Object.assign(default_options, options);
        v.notices.push({
          id: v.noticeCount,
          content: content,
          sec: options.sec,
        });
        v.noticeCount++;
      },
      emptyCheck(values) {
        for (let i = 0; i < values.length; i++) {
          if (values[i].toString() == "") return false;
        }
        return true;
      },
      numberCheck(values) {
        for (let i = 0; i < values.length; i++) {
          if (values[i].v.toString() == "") return false;
          if (Number(values[i].v) == NaN) return false;
          if (values[i].min > values[i].v) return false;
          if (values[i].v > values[i].max) return false;
        }

        return true;
      },
      numberFormat(num) {
        return (num || 0)
          .toString()
          .replace(/^-?\d+/g, (m) => m.replace(/(?=(?!\b)(\d{3})+$)/g, ","));
      },
    };

    let isInit = false;

    setTimeout(() => {
      this.$APPDATA.loginBeforeViewPage = this.$router.currentRoute.fullPath;
      let id = setInterval(() => {
        if (isInit) {
          this.$set(this, "loading", false);
          if (
            !this.$APPDATA.isLogin &&
            this.$router.currentRoute.fullPath != "/login"
          ) {
            // ログイン画面に飛ばす
            this.$router.replace("/login");
          } else if (
            this.$APPDATA.isLogin &&
            this.$router.currentRoute.fullPath == "/login"
          ) {
            this.$router.replace("/");
          }
          clearInterval(id);
        }
      }, 100);
    }, 900);

    // 資格情報の確認

    const checkLogin = () => {
      fetch("/api/v1/auth", {
        method: "GET",
        headers: new Headers({
          "content-type": "application/json",
        }),
      })
        .then((res) => res.json())
        .then((loginResult) => {
          if (loginResult.status == "ok") {
            this.$APPDATA.isLogin = true;
            this.$APPDATA.disconnectedDetected = false;
          } else {
            if (
              isInit &&
              this.$APPDATA.loginBeforeViewPage != "/login" &&
              !this.$APPDATA.disconnectedDetected
            ) {
              // 通常時にログインが死んでることに気が付いたら
              this.$APPDATA.loginBeforeViewPage =
                this.$router.currentRoute.fullPath;
              this.$APPDATA.isLogin = false;
              this.$APPDATA.disconnectedDetected = true;
              this.$APPDATA.util_methods.callModal(
                "現在の接続が何らかの操作で切断されていました。\nお手数ですが再度ログインをお願いします。"
              );
              this.$router.push("/login");
            }
          }
          isInit = true;
        })
        .catch((error) => {});
    };

    setInterval(() => {
      if (this.$APPDATA.isLogin) checkLogin();
    }, 10 * 1000);
    checkLogin();
  },
};
</script>

<style scoped>
.page,
.page-view,
.outline-content-wrapper,
.outline-content {
  position: relative;
  width: 100%;
  height: 100%;
  overflow: hidden;
}

.overlay-wrapper {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  overflow: hidden;
  pointer-events: none;
  z-index: 200;
}

.page-view {
  width: 100%;
  height: 100%;
}

.outline-content-wrapper {
  width: 100%;
}

.outline-content-wrapper.loggined {
  width: calc(100% - 55px);
  margin-left: 55px;
}

.outline-content {
  overflow: auto;
}

.sidebar-wrapper {
  z-index: 10;
  position: absolute;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  pointer-events: none;
  transition: background-color ease 250ms;
  background-color: rgba(41, 41, 41, 0.3);
  pointer-events: all;
}

.sidebar-wrapper.close {
  background-color: transparent;
  pointer-events: none;
}

.sidebar-column {
  width: 250px;
  height: 100%;
  transition: width ease 250ms;
  background: rgb(41, 41, 41);
  color: rgb(196, 196, 196);
  pointer-events: all;
  box-shadow: #000 0px 0px 2px;
}

.sidebar-wrapper .name {
  opacity: 1;
  transition: opacity ease 250ms;
  word-break: keep-all;
}

.sidebar-wrapper.close .sidebar-column {
  width: 55px;
  box-shadow: none;
}

.sidebar-wrapper.close .name {
  opacity: 0;
}

.loading {
  position: relative;
  display: flex;
  top: 0;
  left: 0;
  z-index: 2;
  width: 100%;
  height: 100%;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 15px 20px;
}

i.anim {
  font-size: 1.5em;
  animation-duration: 1s;
  animation-name: fa-spin;
  animation-timing-function: ease;
  animation-iteration-count: infinite;
}

label {
  width: 100%;
}

input {
  width: 100%;
  outline: none;
  border: 1px solid rgb(94, 94, 94);
}

.inputField {
  margin-bottom: 20px;
}

.inputField input {
  font-size: 1.1em;
}

.btn {
  display: inline-flex;
  justify-content: center;
  align-items: center;
  padding: 7.5px 12.5px;
  background: rgb(235, 235, 235);
  color: rgb(0, 0, 0);
  border-radius: 10px;
  border: 0px;
  user-select: none;
  transition: background-color ease 50ms;
  cursor: pointer;
}

.btn:hover {
  background: rgb(240, 240, 240);
  color: rgb(0, 0, 0);
}

.btn:active {
  background: rgb(182, 182, 182);
  color: rgb(0, 0, 0);
}

.btn.primary {
  background: rgb(31, 53, 255);
  color: rgb(255, 255, 255);
}

.btn.primary:hover {
  background: rgb(92, 108, 255);
  color: rgb(255, 255, 255);
}

.btn.primary:active {
  background: rgb(52, 68, 212);
  color: rgb(199, 199, 199);
}

.btn.primary.disable,
.btn.primary.disable:hover,
.btn.primary.disable:active {
  background: rgb(84, 98, 223);
  color: rgb(182, 182, 182);
  cursor: not-allowed;
}

.btn.danger {
  background: rgb(255, 76, 31);
  color: rgb(255, 255, 255);
}

.btn.danger:hover {
  background: rgb(255, 130, 92);
  color: rgb(255, 255, 255);
}

.btn.danger:active {
  background: rgb(212, 97, 52);
  color: rgb(199, 199, 199);
}

.btn.danger.disable,
.btn.danger.disable:hover,
.btn.danger.disable:active {
  background: rgb(223, 93, 84);
  color: rgb(182, 182, 182);
  cursor: not-allowed;
}

input[type="text"], input[type="password"], input[type="number"] {
  border-radius: 5px;
  padding: 5px 10px;
}
</style>