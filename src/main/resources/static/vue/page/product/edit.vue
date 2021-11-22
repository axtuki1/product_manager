<template>
  <div class="product-edit">
    <div class="loading center-dialog " v-show="loading">
      <i class="fas fa-circle-notch anim"></i>
      <loading-text style="font-weight: bold"></loading-text>
    </div>
    <div class="error center-dialog " v-if="error">
      <i class="fas fa-exclamation-triangle"></i>
      <div style="font-weight: bold">
        指定された商品は見つかりませんでした。
      </div>
    </div>
    <div class="item-edit-form" v-if="!error && !loading">
      <div class="inputField productName">
      <label for="productName">商品名</label>
      <input type="text" v-model="productName" 
        v-on:input="check(0, $event, 255)"/>
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
    <div class="inputField barcode">
      <label for="barcode">バーコード (省略可)</label>
      <input type="text"
        v-model="barcode" 
        v-on:input="check(3, $event, 18, false)"/>
      <span class="error_text" v-if="inputErrors[3] != ''">{{
        inputErrors[3]
      }}</span>
    </div>
    <button v-on:click="registerItem" class="registerItem btn primary"  v-bind:class="{processing: isProcessing}">
      <div v-if="isProcessing"><i class="fas fa-spinner"></i></div>
      <div v-else>登録</div>
    </button>
  </div>
  
</template>

<script>
module.exports = {
  props: ["id"],
  data: function () {
    return {
      loading: true,
      error: false,
      productId: 0,
      productName: "",
      productPrice: 0,
      productAmount: 0,
      productGenre: 0,
      barcode: "",
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
    "infomation-item": httpVueLoader("/vue/component/product/infomation.vue"),
    "loading-text": httpVueLoader("/vue/component/loading-text.vue"),
  },
  methods: {
    check(n, e, max = 99999, required = true) {
      let value = e.srcElement.value;
      this.inputErrors[n] = "";
      if (value == "" && required) {
        this.inputErrors[n] = "このフィールドは必須です。";
      } else if (typeof value == "number" && value < 0) {
        this.inputErrors[n] = "0以上の値を入力してください。";
      } else if (typeof value == "string" && value.length > max) {
        this.inputErrors[n] = "最大文字数は" + max + "です。";
      }
    },
    allCheck() {
      const output = {
        isOK: true,
        error: [],
      };
      if (
        !this.$APPDATA.util_methods.emptyCheck([
          this.productName,
          this.productPrice,
          this.productAmount,
          this.productGenre,
        ])
      ) {
        // 空データチェック trueでOK
        output.isOK = false;
        output.error.push("必須のフィールドが入力されていません。");
      }
      if (
        !this.$APPDATA.util_methods.numberCheck([
          {
            v: this.productAmount,
            min: 0,
            max: Infinity,
          },
          {
            v: this.productPrice,
            min: 0,
            max: Infinity,
          },
          {
            v: this.productGenre,
            min: 0,
            max: Infinity,
          },
        ])
      ) {
        output.isOK = false;
        output.error.push("入力値が不正です。");
      }
      return output;
    },
    registerItem() {
      this.isProcessing = true;
      const check = this.allCheck();
      if (!check.isOK) {
        this.isProcessing = false;
        for (let i = 0; i < check.error.length; i++) {
          this.$APPDATA.util_methods.callNotice(check.error[i], 3);
        }
        return;
      }
      fetch("/api/v1/item/" + this.productId + "/edit", {
        method: "POST",
        headers: new Headers({
          "content-type": "application/json",
        }),
        body: JSON.stringify({
          name: this.productName,
          price: this.productPrice,
          amount: this.productAmount,
          genre: this.productGenre,
          code: this.barcode,
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
              "商品:" + this.productName + "を編集しました。",
              3
            );
            this.$router.push("/product");
          }
        });
    },
  },
  mounted() {
    this.$emit("update-title", "商品情報編集");
    fetch("/api/v1/item/" + this.id, {
      method: "GET",
      headers: new Headers({
        "content-type": "application/json",
      }),
    })
      .then((d) => d.json())
      .then((j) => {
        this.productId = j.data.itemData.id;
        this.productName = j.data.itemData.name;
        this.productPrice = j.data.itemData.price;
        this.productAmount = j.data.itemData.amount;
        this.productGenre = j.data.itemData.genre;
        this.barcode = j.data.itemData.code;
        fetch("/api/v1/genre", {
          method: "GET",
          headers: new Headers({
            "content-type": "application/json",
          }),
        })
          .then((d) => d.json())
          .then((j) => {
            this.genreList = j.data.genre;
            this.loading = false;
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

.product-edit {
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