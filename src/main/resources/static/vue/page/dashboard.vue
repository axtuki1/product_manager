<template>
<div>
  <button class="btn primary" v-on:click="logout_btn">ログアウト</button>
  <button class="btn primary" v-on:click="doFATAL_btn">err</button>
  <button class="btn primary" v-on:click="doModal_btn">Modal</button>
  <h1>{{ title }}</h1>
</div>
  
</template>

<script>
module.exports = {
  data: function() {
    return {
      title: 'page2.vue'
    }
  },
  methods:{
    logout_btn(){
      this.$APPDATA.util_methods.logout();
    },
    doFATAL_btn(){
      this.$APPDATA.util_methods.fatal_error_handle("エラーが発生しました。","ま、テストなんですけどね。");
    },
    doModal_btn(){
      this.$APPDATA.util_methods.callNotice("通知テスト",{
        sec:3,
      });
    }
  }
}
</script>

<style scoped>
h1 {
    color: blue;
}
</style>