<template>
  <div
    class="product-item"
    v-on:click="push"
    v-bind:class="{ deleting: deleting }"
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
      <div class="barcode">
        <span class="key">バーコード:</span>
        <span class="value">{{
          item.code == "" ? "未指定" : item.code
        }}</span>
      </div>
    </div>
    <div class="moderatorBtn">
      <button class="btn edit" v-on:click.stop.prevent="editPush">
        <i class="fas fa-pencil-alt"></i> 編集
      </button>
      <button class="btn remove danger" v-on:click.stop.prevent="removePush">
        <i class="fas fa-trash-alt"></i> 削除
      </button>
    </div>
    <div class="deleting-window" v-on:click.stop.prevent="/* nope */" v-bind:class="{ show: deleting }">
      <span class="progress-icon"><i class="fas fa-circle-notch anim"></i></span> 削除中...
    </div>
  </div>
</template>

<script>
module.exports = {
  props: ["item", "genre-list"],
  data() {
    return {
      deleting: false,
    };
  },
  mounted() {},
  methods: {
    push() {
      this.$router.push("/product/" + this.item.id);
    },
    editPush() {
      this.$router.push("/product/" + this.item.id + "/edit");
    },
    removePush() {
      this.deleting = true;
      const deleteMethod = () => {
        fetch("/api/v1/item/" + this.item.id, {
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
            this.$emit("reload");
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

.moderatorBtn {
  position: absolute;
  right: 15px;
  top: 15px;
}

.name {
  font-size: 1.4em;
}

.price {
}

.progress-icon {
  margin-right: 10px;;
}

i.anim {
  margin: 0;
}

.btn i {
  margin-right: 6px;
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
