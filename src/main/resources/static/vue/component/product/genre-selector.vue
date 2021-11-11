<template>
  <div class="genre-selector">じゃんるじぇね</div>
</template>

<script>
module.exports = {
  props: ["itemData", "info"],
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