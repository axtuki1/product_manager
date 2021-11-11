<template>
  <div class="product-details">
    <div class="loading center-dialog " v-show="loading">
      <i class="fas fa-circle-notch anim"></i>
      <loading-text style="font-weight: bold"></loading-text>
    </div>
    <div class="error center-dialog " v-show="error">
      <i class="fas fa-exclamation-triangle"></i>
      <div style="font-weight: bold">
        指定された商品は見つかりませんでした。
      </div>
    </div>
    <div class="item-details">
      <h1>{{ itemData.name }}</h1>
      <div class="infomation">
        <infomation-item
          v-for="info in infoData"
          v-bind:key="info.id"
          v-bind:info="info"
          v-bind:item-data="itemData"
        ></infomation-item>
      </div>
    </div>
  </div>
</template>

<script>
module.exports = {
  props: ["id"],
  data: function () {
    return {
      loading: true,
      error: false,
      itemData: {},
      infoData: [
        {
          id: "genre",
          key: "ジャンル",
        },
      ]
    };
  },
  components: {
    "infomation-item": httpVueLoader("/vue/component/product/infomation.vue"),
    "loading-text": httpVueLoader("/vue/component/loading-text.vue"),
  },
  methods: {
  },
  mounted() {
    this.$emit("update-title", "商品詳細");
    fetch("/api/v1/item/" + this.id, {
      method: "GET",
      headers: new Headers({
        "content-type": "application/json",
      }),
    })
      .then((d) => d.json())
      .then((j) => {
        this.itemData = j.data.itemData;
        this.loading = false;
        fetch("/api/v1/genre/" + this.itemData.genre, {
          method: "GET",
          headers: new Headers({
            "content-type": "application/json",
          }),
        })
          .then((d) => d.json())
          .then((j) => {
            this.itemData.genreId = this.itemData.genre;
            this.itemData.genre = j.data.genre;
          })
          .catch((e) => {
            this.loading = false;
            this.error = true;
          });
      })
      .catch((e) => {
        this.loading = false;
        this.error = true;
      });
  },
};
</script>

<style scoped>
h1 {
  margin: 0;
}

i {
  font-size: 1.5em;
  margin-bottom: 10px;
}

i.anim {
  animation-duration: 1s;
  animation-name: fa-spin;
  animation-timing-function: ease;
  animation-iteration-count: infinite;
}

.item-details {
  padding: 10px 15px;
}
</style>