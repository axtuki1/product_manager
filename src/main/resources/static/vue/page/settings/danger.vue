<template>
  <div class="settings">
    <message class="danger">
      <strong>本ページ内の操作は破壊的な操作を含む場合があります！</strong><br>
      利用する際は十分ご注意ください。
    </message>
    <h2>一括削除</h2>
    <p>
      各種累積データの一括削除が行えます。<br>
      <strong>失われたデータは再度入力しない限り元に戻ることはありません！</strong><br>
      このボタンを使用する場合は <strong>Shiftを押しながら"右クリック"</strong> してください。
    </p>
    <h3>売上履歴の削除</h3>
    <button class="btn danger" v-on:click.shift.right.prevent="">
      <i class="fas fa-bomb" style="margin-right: 5px;"></i> 実行する
    </button>
    <h3>商品の削除</h3>
    <button class="btn danger" v-on:click.shift.right.prevent="">
      <i class="fas fa-bomb" style="margin-right: 5px;"></i> 実行する
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
  </div>
</template>

<script>
module.exports = {
  data: function () {
    return {
      loading: true,
    };
  },
  components: {
    "loading-text": httpVueLoader("/vue/component/loading-text.vue"),
    "message": httpVueLoader("/vue/component/message.vue"),
  },
  methods: {
    productDeleter(){

    },
    salesDataReset(){

    }


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


</style>