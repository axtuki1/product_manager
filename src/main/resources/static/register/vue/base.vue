
<template>
  <div class="base">
    <div class="left">
      <div class="current-shopping-details">
        <div class="registed-history">
          <item-wrapper v-for="item in items" :key="item.id" :item="item">
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
            <div class="value">{{ $t("totalCount.value", [numberFormat(totalCount)]) }}</div>
          </div>
          <div class="billing-amount">
            <div class="label">{{ $t("smallTotal.label") }}</div>
            <div class="value">{{ $t("smallTotal.value", [numberFormat(smallTotal)]) }}</div>
          </div>
          <div class="discount-amount">
            <div class="label">{{ $t("discount.label") }}</div>
            <div class="value">{{ $t("discount.value", [numberFormat(discount)]) }}</div>
          </div>
          <div class="billing-total-amount">
            <div class="label">{{ $t("total.label") }}</div>
            <div class="value">{{ $t("total.value", [numberFormat(total)]) }}</div>
          </div>
        </div>
      </div>
    </div>
    <div class="right">
      <div class="catalog">catalog</div>
      <div class="controls">
        <div>
          
        </div>
        <div class="keypad">
          <div class="top">
            <button>7</button>
            <button>8</button>
            <button>9</button>
          </div>
          <div class="middle">
            <button>4</button>
            <button>5</button>
            <button>6</button>
          </div>
          <div class="bottom">
            <button>1</button>
            <button>2</button>
            <button>3</button>
          </div>
          <div class="most-bottom">
            <button>00</button>
            <button>0</button>
            <button>.</button>
          </div>
        </div>
        <div class="actions">
          <button>{{ $t("button.changeInput.price") }}</button>
          <button>{{ $t("button.changeInput.register") }}</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
module.exports = {
  data() {
    return {
      items: [],
      inputMode: 0,
      currentInputData: "",
      totalCount: 0,
      total: 0,
      smallTotal: 0,
      discount: 0,
    };
  },
  methods: {
    numberFormat(num) {
      return (num || 0)
        .toString()
        .replace(/^-?\d+/g, (m) => m.replace(/(?=(?!\b)(\d{3})+$)/g, ","));
    },
    recalc(){
      for(let i = 0; i < this.items.length;i++){
        const item = this.items[i];
        this.totalCount += item.amount;
        this.smallTotal += item.price * item.amount;
      }
      this.total = this.smallTotal - this.discount;
    }
  },
  i18n: {
    messages: {
      ja: {
        totalCount: {
          label: "個数",
          value: "{0}個"
        },
        total: {
          label: "合計",
          value: "{0}円"
        },
        smallTotal: {
          label: "小計",
          value: "{0}円"
        },
        discount: {
          label: "割引",
          value: "{0}円"
        },
        button: {
          changeInput: {
            price: "金額\n入力",
            register: "登録",
          },
        },
      },
    },
  },
  components: {
    "loading-text": httpVueLoader("/vue/component/loading-text.vue"),
    "item-wrapper": httpVueLoader("/register/vue/component/item-wrapper.vue"),
  },
  mounted() {
    window.v = this;
    this.items.push({
      id: 1,
      name: "名前",
      type: "register",
      amount: 10,
      price: 10,
    });
    this.items.push({
      id: 2,
      name: "名前",
      type: "register",
      amount: 150,
      price: 105,
    });
    this.items.push({
      id: 3,
      name: "名前",
      type: "register",
      amount: 1042,
      price: 120,
    });
    this.items.push({
      id: 4,
      name: "名前",
      type: "register",
      amount: 1,
      price: 10136,
    });
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

.left > *,
.right > * {
  width: 100%;
}

.current-shopping-details {
  position: relative;
  display: flex;
  flex-direction: column;
  height: 100%;
  border-radius: 10px;
  background: rgb(241, 241, 241);
}

.registed-history {
  flex-grow: 1;
  overflow: auto;
}

.current-shopping-details .bottom {
  height: 210px;
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
  flex-grow: 1.5;
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

.controls {
  display: flex;
  flex-direction: row;
  height: 50%;
  white-space: pre-line
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

.controls button {
  border: 2px solid rgb(255, 180, 68);
  border-radius: 10px;
  background: rgb(255, 250, 183);
  text-align: center;
  font-size: 1.5em;
  width: 100%;
  height: 100%;
  flex-grow: 1;
}

.controls button:hover {
  border: 2px solid rgb(255, 197, 110);
  background: rgb(255, 252, 206);
}

.controls button:active {
  border: 2px solid rgb(228, 161, 62);
  background: rgb(238, 233, 140);
}
</style>