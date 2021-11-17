<template>
  <div class="product-add">
    <div class="inputField productName">
      <label for="productName">商品名</label>
      <input type="text" v-model="productName" />
      <span class="error_text" v-if="inputErrors[0] != ''">{{
        inputErrors[0]
      }}</span>
    </div>
    <div class="inputField productPrice">
      <label for="productPrice">価格</label>
      <input
        type="number"
        v-model="productPrice"
        v-on:input="check(1, $event)"
      />
      <span class="error_text" v-if="inputErrors[1] != ''">{{
        inputErrors[1]
      }}</span>
    </div>
    <div class="inputField productAmount">
      <label for="productAmount">保有在庫数</label>
      <input
        type="number"
        v-model="productAmount"
        v-on:input="check(2, $event)"
      />
      <span class="error_text" v-if="inputErrors[2] != ''">{{
        inputErrors[2]
      }}</span>
    </div>
    <div class="inputField productGenre">
      <label for="productGenre">ジャンル</label>
      <!-- <div class="genre-select-btn btn" v-on:click="openGenreSelector">
        <div class="selected-name">{{ this.genreList[this.productGenre].name }}</div>
      </div> -->
      <select v-model="productGenre">
        <option
          v-for="option in genreList"
          v-bind:key="option.id"
          v-bind:value="option.id"
        >
          {{ option.name }}
        </option>
      </select>
    </div>
    <button v-on:click="registerItem" class="registerItem btn primary" v-bind:class="{processing: isProcessing}">
      <div v-if="isProcessing"><i class="fas fa-spinner"></i></div>
      <div v-else>登録</div>
    </button>
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
      isProcessing: false,
      inputErrors: [],
      genreList: [
        {
          id: 0,
          name: "未指定",
          parentId: null,
        },
      ],
    };
  },
  components: {
    "loading-text": httpVueLoader("/vue/component/loading-text.vue"),
  },
  methods: {
    openGenreSelector() {},
    registerItem() {
      this.isProcessing = true;
      setTimeout(() => {
      fetch("/api/v1/item/new", {
        method: "POST",
        headers: new Headers({
          "content-type": "application/json",
        }),
        body: JSON.stringify({
          name: this.productName,
          price: this.productPrice,
          amount: this.productAmount,
          genre: this.productGenre,
        }),
      })
        .then((d) => d.json())
        .then((j) => {
          this.isProcessing = false;
          if (j.statusCode != 200) {
            this.$APPDATA.util_methods.callNotice(
              "エラーが発生しました: " + j.message,
              3
            );
          } else {
            this.$APPDATA.util_methods.callNotice(
              "商品:" + this.productName + "を追加しました。",
              3
            );
            
            this.productName = "";
            this.productPrice = 0;
            this.productAmount = 0;
            this.productGenre = 0;
          }
        });
      },10);
    },
    check(n, e) {
      let value = e.srcElement.value;
      this.inputErrors[n] = "";
      if (value == "") {
        this.inputErrors[n] = "このフィールドは必須です。";
      } else if (typeof value == "number" && value < 0) {
        this.inputErrors[n] = "0以上の値を入力してください。";
      }
    },
  },
  mounted() {
    this.$emit("update-title", "新規追加");
    fetch("/api/v1/genre", {
      method: "GET",
      headers: new Headers({
        "content-type": "application/json",
      }),
    })
      .then((d) => d.json())
      .then((j) => {
        this.genreList = j.data.genre;
      });
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

.registerItem div {
  transform: rotate(0deg);
}

.registerItem.processing div {
  animation-name: rotate;
  animation-duration: 1s;
  animation-timing-function: steps(7);
  animation-iteration-count: infinite;
}

@keyframes rotate {
  0% {
    transform: rotate(0deg);
  }
  50% {
    transform: rotate(180deg);
  }
  100% {
    transform: rotate(360deg);
  }
}

.error_text {
  color: #f00;
}
</style>