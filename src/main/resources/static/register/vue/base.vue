
<template>
  <div class="base">
    <div class="left">
      <div class="current-shopping-details">
        <div class="registed-history">
          <item-wrapper
            v-for="item in items"
            :key="item.id"
            :item="item"
            v-on:cancel="itemDelete"
          >
            {{ item }}
          </item-wrapper>
          <!-- <div class="item-wrapper deleted">
            <div class="name">商品名商品名商品名</div>
            <div class="type">登録</div>
            <div class="payment">
              <div class="amount">3個</div>
              <div class="price">@500円</div>
              <div class="small-total">
                <div class="value">500円</div>
                <div class="label">小　計</div>
              </div>
            </div>
          </div> -->
        </div>
        <div class="bottom">
          <div class="total-count">
            <div class="label">{{ $t("totalCount.label") }}</div>
            <div class="value">
              {{ $t("totalCount.value", [numberFormat(totalCount)]) }}
            </div>
          </div>
          <div class="billing-amount">
            <div class="label">{{ $t("smallTotal.label") }}</div>
            <div class="value">
              {{ $t("smallTotal.value", [numberFormat(smallTotal)]) }}
            </div>
          </div>
          <!-- <div class="discount-amount">
            <div class="label">{{ $t("discount.label") }}</div>
            <div class="value">
              {{ $t("discount.value", [numberFormat(discount)]) }}
            </div>
          </div> -->
          <div class="billing-total-amount">
            <div class="label">{{ $t("total.label") }}</div>
            <div class="value">
              {{ $t("total.value", [numberFormat(total)]) }}
            </div>
          </div>
        </div>
      </div>
      <div class="inputingData">
        <div class="label">{{ $t("inputingData.label") }}</div>
        <div class="value">
          <current-keypad-item :item="keypadItem"></current-keypad-item>
        </div>
      </div>
    </div>
    <div class="right">
      <div class="catalog">catalog</div>
      <div class="">
        <button class="" v-on:click="allClear">← ALLCLEAR</button>
        <button class="" v-on:click="currentInputClear">Clear</button>
        <button class="" v-on:click="sendingDetails">確　定</button>
      </div>
      <div class="controls">
        <div class="keypad">
          <div class="top">
            <button v-on:click="KeyPadInput(7)">7</button>
            <button v-on:click="KeyPadInput(8)">8</button>
            <button v-on:click="KeyPadInput(9)">9</button>
          </div>
          <div class="middle">
            <button v-on:click="KeyPadInput(4)">4</button>
            <button v-on:click="KeyPadInput(5)">5</button>
            <button v-on:click="KeyPadInput(6)">6</button>
          </div>
          <div class="bottom">
            <button v-on:click="KeyPadInput(1)">1</button>
            <button v-on:click="KeyPadInput(2)">2</button>
            <button v-on:click="KeyPadInput(3)">3</button>
          </div>
          <div class="most-bottom">
            <button v-on:click="KeyPadInput('00')">00</button>
            <button v-on:click="KeyPadInput('0')">0</button>
          </div>
        </div>
        <div class="actions">
          <button v-bind:class="{primary: this.currentKeyPadMode == 0}" v-on:click="()=>{this.currentKeyPadMode = 0}" class="price">{{ $t("button.changeInput.price") }}</button>
          <button v-bind:class="{primary: this.currentKeyPadMode == 1}" v-on:click="()=>{this.currentKeyPadMode = 1}" class="amount">{{ $t("button.changeInput.amount") }}</button>
          <!-- <button v-bind:class="{primary: this.currentKeyPadMode == 2}" v-on:click="()=>{this.currentKeyPadMode = 2}" class="discount">{{ $t("button.changeInput.discount") }}</button> -->
          <button v-on:click="inputDataRegister" class="register">{{ $t("button.changeInput.register") }}</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
const KEYPAD_MODE = {
  PRICE: 0,
  AMOUNT: 1,
  DISCOUNT: 2,
};

module.exports = {
  data() {
    return {
      items: [],
      keypadItem: {
        price: 0,
        amount: 0,
      },
      currentItemId: 0,
      totalCount: 0,
      total: 0,
      smallTotal: 0,
      discount: 0,
      currentKeyPadMode: KEYPAD_MODE.PRICE,
      currentKeyPadText: "",
      currentKeyboardText: "",
    };
  },
  methods: {
    sendingDetails(){
      this.$emit("set-items", this.items);
    },
    currentInputClear() {
      this.currentKeyPadText = "";
      this.keypadItem = {
        price: 0,
        amount: 0
      }
    },
    KeyPadInput(text) {
      // this.currentKeyPadText += text;
      let value = 0;
      switch(this.currentKeyPadMode){
        case KEYPAD_MODE.PRICE:
          value = this.keypadItem.price + "" + text;
          this.keypadItem.price = Number.parseInt(value);
          break;
        case KEYPAD_MODE.AMOUNT:
          value = this.keypadItem.amount + "" + text;
          this.keypadItem.amount = Number.parseInt(value);
          break;
      }
    },
    inputDataRegister(){
      this.itemAdd({
        name: "商品",
        price: this.keypadItem.price,
        amount: this.keypadItem.amount == 0 ? 1 : this.keypadItem.amount,
        type:"register"
      });
      this.currentInputClear();
    },
    numberFormat(num) {
      return (num || 0)
        .toString()
        .replace(/^-?\d+/g, (m) => m.replace(/(?=(?!\b)(\d{3})+$)/g, ","));
    },
    recalc() {
      this.totalCount = 0;
      this.smallTotal = 0;
      this.discount = 0;
      this.total = 0;
      for (let i = 0; i < this.items.length; i++) {
        const item = this.items[i];
        if (item.type == "delete") {
          this.totalCount -= item.amount;
          this.smallTotal -= item.price * item.amount;
        } else {
          this.totalCount += item.amount;
          this.smallTotal += item.price * item.amount;
        }
      }
      this.total = this.smallTotal - this.discount;
    },
    allClear() {
      this.items = [];
      this.recalc();
    },
    historyLastScroll() {
      const el = this.$el.querySelector(".registed-history");
      el.scrollTo(0, el.scrollHeight);
    },
    itemAdd(data) {
      data.id = this.currentItemId;
      this.items.push(data);
      this.currentItemId++;
      setTimeout(this.historyLastScroll, 5);
      this.recalc();
    },
    itemDelete(id) {
      const data = { ...this.items[id] };
      data.id = this.currentItemId;
      data.type = "delete";
      this.items.push(data);
      this.currentItemId++;
      setTimeout(this.historyLastScroll, 5);
      this.recalc();
    },
    windowKeyInput(event) {
      if (event.key == "Enter") {
        this.barcodeSearch(this.currentKeyboardText);
        this.currentKeyboardText = "";
      } else if (!isNaN(event.key.slice(-1))) {
        this.currentKeyboardText += event.key.slice(-1);
      } else if (event.key == "Backspace") {
        this.currentKeyboardText = this.currentKeyboardText.slice(0, -1);
      }
    },
    barcodeSearch(barcode) {
      if (barcode == "") return;
      fetch("/api/v1/item/code/" + barcode, {
        method: "GET",
        headers: new Headers({
          "content-type": "application/json",
        }),
      })
        .then((d) => d.json())
        .then((j) => {
          if (j.data.itemData.length == 0) {
            this.$APPDATA.util_methods.callNotice(
              "コード[" + barcode + "]はデータベースにありません",
              3
            );
            return;
          }
          for (let i = 0; i < j.data.itemData.length; i++) {
            const data = j.data.itemData[i];
            this.itemAdd({
              name: data.name,
              type: "register",
              amount: 1,
              price: data.price,
            });
          }
          this.recalc();
        });
    },
  },
  i18n: {
    messages: {
      ja: {
        totalCount: {
          label: "個数",
          value: "{0}個",
        },
        total: {
          label: "合計",
          value: "{0}円",
        },
        smallTotal: {
          label: "小計",
          value: "{0}円",
        },
        discount: {
          label: "割引",
          value: "{0}円",
        },
        button: {
          changeInput: {
            price: "価格",
            amount: "個数",
            discount: "割引",

            other: "その他",
            register: "登録",
          },
        },
        inputingData:{
          label: "次の商品に付加 or 登録 >>"
        }
      },
    },
  },
  components: {
    "loading-text": httpVueLoader("/vue/component/loading-text.vue"),
    "item-wrapper": httpVueLoader("/register/vue/component/item-wrapper.vue"),
    "current-keypad-item": httpVueLoader("/register/vue/component/current-keypad-item.vue")
  },
  beforeDestroy() {
    document.removeEventListener("keydown", this.windowKeyInput);
  },
  mounted() {
    document.addEventListener("keydown", this.windowKeyInput);
    window.v = this;
    this.recalc();
  },
};
</script>

<style scoped>
.base {
  display: flex;
  width: 100%;
  height: 100%;
}

.left,
.right {
  position: relative;
  flex: 1;
  margin: 20px;
  display: flex;
  flex-direction: column;
}

.right {
  margin-left: 0;
}

.left > *,
.right > * {
  width: 100%;
}

.current-shopping-details {
  position: relative;
  display: flex;
  flex-direction: column;
  height: calc(100% - 8.5em);
  border-radius: 10px;
  background: rgb(241, 241, 241);
}

.registed-history {
  flex-grow: 1;
  overflow: auto;
}

.current-shopping-details .bottom {
  min-height: 210px;
  display: flex;
  flex-direction: column;
}

.current-shopping-details .bottom > * {
  position: relative;
  padding: 10px;
  border-top: 1px solid #000;
}

.current-shopping-details .bottom .value {
  position: absolute;
  bottom: 10px;
  right: 10px;
}

.current-shopping-details .bottom .billing-total-amount {
  height: 100%;
}

.current-shopping-details .bottom .billing-total-amount .value {
  font-size: 2em;
  font-weight: bold;
}

.catalog {
  height: 47.5%;
  border-radius: 10px;
  background: rgb(241, 241, 241);
  padding: 20px;
  margin-bottom: 5%;
}

.register {
  height: 40%;
}

.inputingData {
  height: 7em;
  border-radius: 10px;
  background: rgb(241, 241, 241);
  padding: 20px;
  margin-top: 5%;
}

.inputingData .value {
  bottom: 0;
}

.controls {
  display: flex;
  flex-direction: row;
  height: 50%;
  white-space: pre-line;
}

.keypad {
  width: 75%;
  flex-flow: 1;
}

.keypad > * {
  display: flex;
  height: calc(100% / 4);
  font-size: 1.5em;
}

.actions {
  flex-grow: 1;
  display: flex;
  flex-direction: column;
}

.actions > * {
  width: 100%;
}

button {
  border: 2px solid rgb(255, 180, 68);
  border-radius: 10px;
  background: rgb(255, 250, 183);
  text-align: center;
  font-size: 1.5em;
  flex-grow: 1;
  width: 100%;
  padding: 10px 10px;
}

button:hover {
  border: 2px solid rgb(255, 197, 110);
  background: rgb(255, 252, 206);
}

button:active {
  border: 2px solid rgb(228, 161, 62);
  background: rgb(238, 233, 140);
}


button.primary {
  border: 2px solid rgb(255, 68, 215);
  border-radius: 10px;
  background: rgb(249, 183, 255);
  text-align: center;
  font-size: 1.5em;
  flex-grow: 1;
  width: 100%;
  padding: 10px 10px;
}

button.primary:hover {
  border: 2px solid rgb(243, 110, 255);
  background: rgb(255, 206, 253);
}

button.primary:active {
  border: 2px solid rgb(228, 62, 200);
  background: rgb(238, 140, 238);
}
</style>