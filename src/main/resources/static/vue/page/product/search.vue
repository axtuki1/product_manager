<template>
  <div class="search-view" style="overflow: hidden">
    <div class="search-container">
      <button
        v-on:click="$router.push('product/new')"
        class="btn primary addItem"
      >
        新規追加
      </button>
      <div class="search-query-input-wrapper">
        <input
          type="text"
          v-model="searchQuery"
          class="search-query-input"
          placeholder="検索..."
          v-on:keyup.enter="search"
        />
        <i class="fas fa-search search-icon" v-on:click="search"></i>
      </div>
      <button
        v-on:click="reload"
        class="btn refresh"
      >
        <i class="fas fa-sync-alt"></i> 再読込
      </button>
    </div>
    <div class="item-list" v-show="!loading && itemList.length != 0">
      <product-item
        v-for="item in itemList"
        v-bind:key="item.name"
        v-bind:item="item"
        :genre-list="genreList"
        @reload="reload"
      ></product-item>
    </div>
    <div class="loading" v-show="loading || nextloading">
      <i class="fas fa-circle-notch anim"></i>
      <loading-text style="font-weight: bold"></loading-text>
    </div>
    <div class="nope" v-show="!loading && itemList.length == 0">
      <i class="fas fa-question-circle"></i>
      <div style="font-weight: bold;">入力された文字を含む商品は見つかりませんでした。</div>
    </div>
  </div>
</template>

<script>
module.exports = {
  data: function () {
    return {
      loading: true,
      nextloading: false,
      searchQuery: "",
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
      if(this.searchQuery != ""){
        this.search();
        return;
      } 
      this.loading = true;
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
    search() {
      this.loading = true;
      const params = {
          q: this.searchQuery
      };
      fetch("/api/v1/search?"+new URLSearchParams(params), {
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

.search-query-input-wrapper{
  position: relative;
  display: inline-block;
  width: 70%;
}

.search-query-input {
  border-radius: 15rem;
  padding: 10px 12.5px;
}

.search-query-input-wrapper .search-icon {
  position: absolute;
  right: 12.5px;
  top: 10px;
  cursor: pointer;
  transition: all ease 100ms;
  color: rgb(71, 71, 71);
}

.refresh {
  position: absolute;
  top: 10px;
  right: 15px;
  height: 35px;
}

.refresh i {
  margin-right: 5px;
}

.search-query-input-wrapper .search-icon:hover {
  transform: scale(1.2);
}

.search-query-input-wrapper .search-icon:active {
  transform: scale(0.8);
}

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

.loading i {
  margin-bottom: 10px;
}

.item-list {
  overflow: auto;
  flex-grow: 1;
}

.nope {
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

.nope i {
  font-size: 2em;
  margin-bottom: 10px;
}
</style>