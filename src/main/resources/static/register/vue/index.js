const i18n = new VueI18n({
  locale: 'ja',
  fallbackLocale: 'ja',
  messages: {
    ja: {}
  }
})

const app = new Vue({
    el: "#app",
    components: {
        "page-layout": httpVueLoader("/register/vue/pageLayout.vue")
    },
    i18n: i18n,
    beforeCreate() {
        Vue.prototype.$APPDATA = {
            isLogin: false
        }
    },
});