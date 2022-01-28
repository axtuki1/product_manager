<template>
  <div class="product-details">
    <div class="loading center-dialog" v-show="loading">
      <i class="fas fa-circle-notch anim"></i>
      <loading-text style="font-weight: bold"></loading-text>
    </div>
    <div class="error center-dialog" v-if="error">
      <i class="fas fa-exclamation-triangle"></i>
      <div style="font-weight: bold">
        指定された商品は見つかりませんでした。
      </div>
    </div>
    <div class="genre-list" v-if="!error && !loading">
      <genre-item
      v-for="item in genreList"
        v-bind:key="item.name"
        v-bind:item="item"
        ></genre-item>
    </div>
    <div
      class="deleting-window"
      v-on:click.stop.prevent="/* nope */"
      v-bind:class="{ show: deleting }"
    >
      <span class="progress-icon"
        ><i class="fas fa-circle-notch anim"></i
      ></span>
      削除中...
    </div>
  </div>
</template>

<script>
module.exports = {
  props: ["id"],
  data: function () {
    return {
      loading: true,
      deleting: false,
      error: false,
      genreList: [],
    };
  },
  components: {
    "genre-item": httpVueLoader("/vue/component/product/genre.vue"),
    "loading-text": httpVueLoader("/vue/component/loading-text.vue"),
  },
  methods: {
    reload() {
      this.loading = true;
      fetch("/api/v1/genre", {
        method: "GET",
        headers: new Headers({
          "content-type": "application/json",
        }),
      })
        .then((d) => d.json())
        .then((j) => {
          this.genreList = j.data.genre;
          this.loading = false;
        }).catch(()=>{
          this.error = true;
          this.loading = false;
        });
    },
  },
  mounted() {
    this.$emit("update-title", "ジャンル");
    this.reload();
  },
};
</script>

<style scoped>
h1 {
  margin: 0;
}

.genre-list {
  padding: 10px 15px;
}

.btn i {
  margin-right: 6px;
}

.moderatorBtn {
  position: absolute;
  right: 15px;
  top: 15px;
}

.deleting-window {
  position: absolute;
  top: 0;
  left: 0;
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.2);
  color: #000;
  opacity: 0;
  pointer-events: none;
  transition: all ease 200ms;
  font-size: 1.2em;
  user-select: none;
}

.deleting-window.show {
  opacity: 1;
  pointer-events: all;
}
</style>