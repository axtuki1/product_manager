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
      <h1>{{ dateStr }}</h1>
      <div class="detail-info">
        <div class="">
          売上金額: {{ 
            $APPDATA.util_methods.numberFormat(
              salesData.billingAmount
             ) }} 円
        </div>
      </div>
      <div class="items">
        <product-item
        v-for="item in itemData"
        v-bind:key="item.name"
        v-bind:item="item"
      ></product-item>
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
      dateStr: "",
      itemData: {},
      genreList: {},
      salesData: {}
    };
  },
  components: {
    "product-item": httpVueLoader("/vue/component/analytics/product-item.vue"),
    "loading-text": httpVueLoader("/vue/component/loading-text.vue"),
  },
  methods: {
    editPush() {
      this.$router.push("/analytics/history/" + this.id + "/edit");
    },
    removePush() {
      this.deleting = true;
      const deleteMethod = () => {
        fetch("/api/v1/sales/history/" + this.id, {
          method: "DELETE",
          headers: new Headers({
            "content-type": "application/json",
          }),
        })
          .then((res) => res.json())
          .then((j) => {
            this.deleting = false;
            this.$APPDATA.util_methods.callNotice("販売情報を削除しました。", {
              sec: 3,
            });
            this.$router.push("/analytics");
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
    this.$emit("update-title", "販売情報詳細");
    fetch("/api/v1/sales/history/" + this.id, {
      method: "GET",
      headers: new Headers({
        "content-type": "application/json",
      }),
    })
      .then((d) => d.json())
      .then((j) => {
        this.itemData = j.data.move;
        this.salesData = j.data.salesData;
        const date = new Date(Date.parse(this.salesData.paymentTimestamp));
        this.dateStr = date.getFullYear() + "年"+(date.getMonth()+1)+"月"+date.getDate()+"日 "+date.getHours()+":"+date.getMinutes()+":"+date.getSeconds();
        this.loading = false;
      })
      .catch((e) => {
        console.log(e);
        this.loading = false;
        this.error = true;
      });
  },
};
</script>

<style scoped>
h1, .detail-info {
  margin: 10px 15px;
}

.detail-info {
  font-size: 1.2em;
}

.items{
  border-top: 1px solid #000;
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