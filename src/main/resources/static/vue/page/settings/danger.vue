<template>
  <div class="settings">
    <message class="danger">
      <strong>本ページ内の操作は破壊的な操作を含む場合があります！</strong
      ><br />
      利用する際は十分ご注意ください。
    </message>
    <h2>一括削除</h2>
    <p>
      各種累積データの一括削除が行えます。<br />
      <strong
        >失われたデータは再度入力しない限り元に戻ることはありません！</strong
      ><br />
      このボタンを使用する場合は
      <strong>Shiftを押しながら"右クリック"</strong> してください。
      <message class="danger">
        <strong>ボタンを押すと処理が開始されます</strong>
      </message>
    </p>
    <p>
      操作パスワード<br />
      <input type="text" class="text" v-model="password" />
    </p>
    <h3>売上履歴の削除</h3>
    <button class="btn danger" v-on:click.shift.right.prevent="salesDataReset">
      <i class="fas fa-bomb" style="margin-right: 5px"></i> 実行する
    </button>
    <h3>商品の削除</h3>
    <button class="btn danger" v-on:click.shift.right.prevent="productDeleter">
      <i class="fas fa-bomb" style="margin-right: 5px"></i> 実行する
    </button>
    <!-- <hr>
    <h2>データベースリセット</h2>
    <p>
      接続している <strong>データベースの全情報を削除</strong> し、セットアップ直後の状態に戻します。<br>
      データベース上には何も残りません。<br>
      このボタンを使用する場合は <strong>Shiftを押しながら"右クリック"</strong> してください。
    </p>
    <button class="btn danger" v-on:click.shift.right.prevent="">
      <i class="fas fa-bomb" style="margin-right: 5px;"></i> 実行する
    </button> -->
    <div class="loading" v-show="loading">
      <i class="fas fa-circle-notch anim"></i>
      <loading-text style="font-weight: bold">Deleteing</loading-text>
    </div>
  </div>
</template>

<script>
module.exports = {
  data: function () {
    return {
      loading: false,
      password: "",
    };
  },
  components: {
    "loading-text": httpVueLoader("/vue/component/loading-text.vue"),
    message: httpVueLoader("/vue/component/message.vue"),
  },
  methods: {
    productDeleter() {
      this.loading = true;
      fetch("/api/v1/DANGER/ProductDataAllClear", {
        method: "POST",
        headers: new Headers({
          "content-type": "application/json",
        }),
        body: JSON.stringify({
          password: this.password,
        }),
      })
        .then((d) => d.json())
        .then((j) => {
          if (j.statusCode != 200) {
            this.$APPDATA.util_methods.callNotice("失敗しました。");
          } else {
            this.$APPDATA.util_methods.callNotice("商品をすべて削除しました。");
          }
          this.loading = false;
        })
        .catch(() => {
          this.$APPDATA.util_methods.callNotice("失敗しました。");
          this.loading = false;
        });
    },
    salesDataReset() {
      this.loading = true;
      fetch("/api/v1/DANGER/SalesDataAllClear", {
        method: "POST",
        headers: new Headers({
          "content-type": "application/json",
        }),
        body: JSON.stringify({
          password: this.password,
        }),
      })
        .then((d) => d.json())
        .then((j) => {
          if (j.statusCode != 200) {
            this.$APPDATA.util_methods.callNotice("失敗しました。");
          } else {
            this.$APPDATA.util_methods.callNotice("販売履歴をすべて削除しました。");
          }
          this.loading = false;
        })
        .catch(() => {
          this.$APPDATA.util_methods.callNotice("失敗しました。");
          this.loading = false;
        });
    },
  },
  mounted() {
    this.$emit("update-title", "危険な操作");
  },
};
</script>

<style scoped>
.settings {
  padding: 10px;
}

h1 {
  margin: 0;
}

.loading {
  position: absolute;
  left: 0;
  top: calc(100% - 100vh);
  height: 100vh;
  background-color: rgba(0, 0, 0, 0.493);
  color: #fff;
}
</style>