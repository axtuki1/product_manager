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
    <div class="item-details" v-if="!error && !loading">
      <div class="moderatorBtn">
        <button class="btn edit" v-on:click.stop.prevent="editPush">
          <i class="fas fa-pencil-alt"></i> 編集
        </button>
        <button class="btn remove danger" v-on:click.stop.prevent="removePush">
          <i class="fas fa-trash-alt"></i> 削除
        </button>
      </div>
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
    <div class="deleting-window" v-on:click.stop.prevent="/* nope */" v-bind:class="{ show: deleting }">
      <span class="progress-icon"><i class="fas fa-circle-notch anim"></i></span> 削除中...
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
      itemData: {},
      infoData: [
        {
          id: "genre",
          key: "ジャンル",
        },
        {
          id: "price",
          key: "単価",
        },
        {
          id: "amount",
          key: "個数",
        },
        {
          id: "code",
          key: "バーコード",
        },
      ],
    };
  },
  components: {
    "infomation-item": httpVueLoader("/vue/component/product/infomation.vue"),
    "loading-text": httpVueLoader("/vue/component/loading-text.vue"),
  },
  methods: {
    editPush() {
      this.$router.push("/product/" + this.itemData.id + "/edit");
    },
    removePush() {
      this.deleting = true;
      const deleteMethod = () => {
        fetch("/api/v1/item/" + this.itemData.id, {
          method: "DELETE",
          headers: new Headers({
            "content-type": "application/json",
          }),
        })
          .then((res) => res.json())
          .then((j) => {
            this.deleting = false;
            this.$APPDATA.util_methods.callNotice("商品を削除しました。", {
              sec: 3,
            });
            this.$router.push("/product");
          })
          .catch((error) => {
            this.deleting = false;
            v.$APPDATA.util_methods.callNotice(
              "削除処理中にエラーが発生しました。",
              { sec: 3 }
            );
          });
      };
      deleteMethod();
    },
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
            this.loading = false;
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

.item-details {
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