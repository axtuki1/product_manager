const app = new Vue({
    el: "#app",
    components: {
        "page-layout": httpVueLoader("/vue/pageLayout.vue")
    },
    beforeCreate() {
        Vue.prototype.$APPDATA = {
            isLogin: false,
            loginBeforeViewPage: "/",
            disconnectedDetected: false
        }
    },
});