<template>
  <div class="search-view" style="overflow: hidden">
    <div class="search-container">
      [検索窓設置場所]
      <button
        v-on:click="$router.push('product/new')"
        class="btn primary addItem"
      >
        新規追加
      </button>
    </div>
    <div class="loading" v-show="loading">
      <i class="fas fa-circle-notch anim"></i>
      <loading-text style="font-weight: bold"></loading-text>
    </div>
    <div class="item-list">
      <product-item
        v-for="item in itemList"
        v-bind:key="item.name"
        v-bind:item="item"
        :genre-list="genreList"
        @reload="reload"
      ></product-item>
    </div>
  </div>
</template>

<script>
module.exports = {
  data: function () {
    return {
      loading: true,
      itemList: {},
      genreList: {},
    };
  },
  components: {
    "loading-text": httpVueLoader("/vue/component/loading-text.vue"),
    "product-item": httpVueLoader("/vue/component/product/product-item.vue"),
  },
  methods: {
    reload() {
      fetch("/api/v1/items", {
        method: "GET",
        headers: new Headers({
          "content-type": "application/json",
        }),
      })
        .then((d) => d.json())
        .then((j) => {
          this.itemList = j.data.items;
          this.loading = false;
        });
    },
  },
  mounted() {
    this.$emit("update-title", "");
    fetch("/api/v1/genre", {
      method: "GET",
      headers: new Headers({
        "content-type": "application/json",
      }),
    })
      .then((d) => d.json())
      .then((j) => {
        this.genreList = j.data.genre;
        this.reload();
      });
  },
};
</script>

<style scoped>
.search-view {
  display: flex;
  flex-direction: column;
}

h1 {
  margin: 0;
}

.search-container {
  padding: 10px 15px;
  border-bottom: 1px solid #000;
}

.item-list {
  overflow: auto;
  flex-grow: 1;
}
</style>