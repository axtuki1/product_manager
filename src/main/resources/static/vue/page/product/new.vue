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
      <div class="genre-select-btn" v-on:click="openGenreSelector">
        <div class="selected-name"></div>
        <input type="hidden" v-model="productGenre"/>
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
      productGenre: 0
    };
  },
  components: {
    "loading-text": httpVueLoader("/vue/component/loading-text.vue"),
    "product-item": httpVueLoader("/vue/component/product/product-item.vue"),
  },
  methods: {
    openGenreSelector(){

    }
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

.product-add{
  padding: 10px 15px;
}
</style>