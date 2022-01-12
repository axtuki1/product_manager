<template>
  <div class="settings">
    
    <button class="btn danger" v-on:click="$router.push('./danger')">
      <i class="fas fa-sign-out-alt" style="margin-right: 5px;"></i> 危険な操作
    </button>
    <button class="btn danger" v-on:click="$APPDATA.util_methods.logout">
      <i class="fas fa-sign-out-alt" style="margin-right: 5px;"></i> ログアウト
    </button>
    
  </div>
</template>

<script>
module.exports = {
  data: function () {
    return {
      loading: true,
    };
  },
  components: {
    "loading-text": httpVueLoader("/vue/component/loading-text.vue"),
  },
  methods: {},
  mounted() {
    this.$emit("update-title", "");
  },
};
</script>

<style scoped>
.settings {
    padding: 10px;
}

h1 {
  margin: 0;
}


</style>