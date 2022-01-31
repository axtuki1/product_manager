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
          v-on:keyup="search($event)"
        />
        <i class="fas fa-search search-icon" v-on:click="search"></i>
      </div>
      <!-- <input type="checkbox" v-model="enableRealTimeSearch" name="" id="" /> -->
      <button v-on:click="reload" class="btn refresh">
        <span v-if="!loading"><i class="fas fa-sync-alt"></i> 再読込</span>
        <span v-else><i class="fas fa-sync-alt fa-spin"></i> 読込中</span>
      </button>
    </div>
    <div class="item-list" v-show="!loading && itemList.length != 0">
      <product-item
        v-for="item in itemList"
        v-bind:key="item.id"
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
      <div style="font-weight: bold">
        入力された文字を含む商品は見つかりませんでした。
      </div>
    </div>
  </div>
</template>

<script>
module.exports = {
  data: function () {
    return {
      loading: true,
      enableRealTimeSearch: false,
      nextloading: false,
      searchQuery: "",
      beforeQuery: "",
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
      if (this.searchQuery != "") {
        this.beforeQuery = "";
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
    search(e) {
      // 関係ないキーでの検索を防止
      if(this.loading) return;
      if (this.enableRealTimeSearch) {
        // 重複する検索はやめよう！
        if (this.beforeQuery == this.searchQuery) {
          return;
        }
        if (
          e == null ||
          e.isComposing ||
          (e.code != null &&
            !(
              e.code.substring(0, 3) == "Key" ||
              e.code == "Enter" ||
              e.code == "NumpadEnter" ||
              e.key == "Backspace" ||
              e.key == "Delete"
            ))
        ) {
          return;
        }
      } else if (e != null && e.code != "Enter") {
        return;
      }
      this.beforeQuery = this.searchQuery;
      // 表示の切替
      this.loading = true;

      // パラメーターの設定
      const params = {
        q: this.searchQuery,
      };

      // 商品一覧の取得～条件を添えて～
      fetch("/api/v1/search?" + new URLSearchParams(params), {
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
.search-query-input-wrapper {
  position: relative;
  display: inline-block;
  width: 70%;
}

input[type="text"].search-query-input {
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