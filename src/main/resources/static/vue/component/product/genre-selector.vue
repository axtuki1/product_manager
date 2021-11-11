<template>
  <div class="genre-selector">
    <div class="loading" v-show="loading">
      <i class="fas fa-circle-notch anim"></i>
      <loading-text style="font-weight: bold"></loading-text>
    </div>
    <div class="genre-list" v-if="!loading">
      あいうえお
    </div>
  </div>
</template>

<script>
module.exports = {
  props: ["itemData", "info"],
  data(){
    return {
      loading: true,
    }
  },
  components: {
    "loading-text": httpVueLoader("/vue/component/loading-text.vue"),
  },
  mounted(){
    fetch("/api/v1/genre", {
      method: "GET",
      headers: new Headers({
        "content-type": "application/json",
      }),
    })
      .then((d) => d.json())
      .then((j) => {
        this.genreList = j.data.genre;
      });
  }
};
</script>

<style scoped>
.infomation-item {
  display: flex;
  justify-content: flex-start;
  align-items: center;
}

.key:after {
  content: ":";
  margin-right: 5px;
}
</style>