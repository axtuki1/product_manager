<template>
  <div class="product-add">
    <div class="inputField productName">
      <label for="productName">商品名</label>
      <input type="text" v-model="productName" />
    </div>
    <div class="inputField productPrice">
      <label for="productPrice">価格</label>
      <input type="number" v-model="productPrice" />
    </div>
    <div class="inputField productAmount">
      <label for="productAmount">保有在庫数</label>
      <input type="number" v-model="productAmount" />
    </div>
    <div class="inputField productGenre">
      <label for="productGenre">ジャンル</label>
      <div class="genre-select-btn btn" v-on:click="openGenreSelector">
        <div class="selected-name">{{ this.genreList[this.productGenre].name }}</div>
      </div>
    </div>
  </div>
</template>

<script>
module.exports = {
  data: function () {
    return {
      loading: true,
      productName: "",
      productPrice: 0,
      productAmount: 0,
      productGenre: 0,
      genreList: [
        {
          id: 0,
          name: "未指定",
          parentId: null
        }
      ]
    };
  },
  components: {
    "loading-text": httpVueLoader("/vue/component/loading-text.vue"),
  },
  methods: {
    openGenreSelector() {
      this.$APPDATA.util_methods.callModal(
        httpVueLoader("/vue/component/product/genre-selector.vue")
      );
    },
  },
  mounted() {
    this.$emit("update-title", "新規追加");
    
  },
};
</script>

<style scoped>
h1 {
  margin: 0;
}

.product-add {
  padding: 10px 15px;
}
</style>