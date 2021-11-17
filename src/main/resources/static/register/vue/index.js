const app = new Vue({
    el: "#app",
    components: {
        "page-layout": httpVueLoader("/register/vue/pageLayout.vue")
    },
    beforeCreate() {
        Vue.prototype.$APPDATA = {
            isLogin: false
        }
    },
});