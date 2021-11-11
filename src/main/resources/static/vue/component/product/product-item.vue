<template>
  <div
    class="product-item"
    v-on:click="push"
    v-bind:class="[item.id]"
  >
    <div class="name">{{ item.name }}</div>
    <div class="infomation">
      <div class="price">
        <span class="key">価格:</span>
        <span class="value">{{ item.price }}円</span>
      </div>
      <div class="amount">
        <span class="key">保有在庫数:</span>
        <span class="value">{{ item.amount }}個</span>
      </div>
      <div class="genre">
        <span class="key">ジャンル:</span>
        <span class="value">{{
          genreList[item.genre] == null ? "未指定" : genreList[item.genre].name
        }}</span>
      </div>
    </div>
    <button class="btn edit" v-on:click.stop.prevent="editPush"><i class="fas fa-pencil-alt"> 編集</i></button>
  </div>
</template>

<script>
module.exports = {
  props: ["item", "genre-list"],
  mounted() {
    
  },
  methods: {
    push() {
      this.$router.push("/product/"+this.item.id);
    },
    editPush() {
      this.$router.push("/product/"+this.item.id+"/edit");
    }
  },
};
</script>

<style scoped>
.product-item {
  padding: 15px 15px;
  border-bottom: 1px solid #000;
  position: relative;
  box-shadow: 0 0 0 #000;
  transition: box-shadow ease 250ms;
}

.product-item:hover {
  box-shadow: 0px 0px 7.5px rgba(0, 0, 0, 0.4);
}

.edit {
  position: absolute;
  right: 15px;
  top: 15px;
}

.name {
  font-size: 1.4em;
}

.price {
}
</style>
