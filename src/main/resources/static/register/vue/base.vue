<i18n>
{
    "ja":{
        "totalCount": "${0} 個",
        "total": "${0}円",
        "smallTotal": "${0}円",
        "discount": "${0}円"
    }
}
</i18n>

<template>
  <div class="base">
    <div class="left">
      <div class="current-shopping-details">
        <div class="registed-history">
          <item-wrapper
            v-for="item in items"
            :key="item.id"
            :item="item"
          >
          </item-wrapper>

          <div class="item-wrapper deleted">
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
          </div>
        </div>
        <div class="bottom">
          <div class="total-count">
            <div class="label">個数</div>
            <div class="value"></div>
          </div>
          <div class="billing-amount">
            <div class="label">小計</div>
            <div class="value"></div>
          </div>
          <div class="discount-amount">
            <div class="label">割引</div>
            <div class="value"></div>
          </div>
          <div class="billing-total-amount">
            <div class="label">合計</div>
            <div class="value"></div>
          </div>
        </div>
      </div>
    </div>
    <div class="right">
      <div class="catalog">catalog</div>
      <div class="controls">
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
          <button>金額<br />入力</button>
          <button>登録</button>
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
  components: {
    "loading-text": httpVueLoader("/vue/component/loading-text.vue"),
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
}

.item-wrapper {
  position: relative;
  border-bottom: 1px solid #000;
  padding: 20px;
  font-size: 1.2em;
}

.item-wrapper.deleted {
  color: #f00;
}

.item-wrapper .name {
  font-weight: bold;
  font-size: 1.3em;
}

.item-wrapper .type {
  position: absolute;
  bottom: 20px;
}

.item-wrapper .payment {
  width: 100%;
  display: flex;
  text-align: right;
  justify-content: flex-end;
  align-items: baseline;
}

.item-wrapper .amount {
}

.item-wrapper .price {
  margin: 0 10px;
}

.item-wrapper.deleted .price,
.item-wrapper.deleted .amount,
.item-wrapper.deleted .name {
  text-decoration: line-through;
}

.item-wrapper.deleted .value:before {
  content: "-";
}

.item-wrapper .payment .value {
  font-weight: bold;
  font-size: 1.4em;
}

.item-wrapper .payment .small-total {
  display: flex;
  flex-direction: column-reverse;
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
  bottom:10px;
  right:10px;
}

.current-shopping-details .bottom .billing-total-amount {
  flex-grow: 1.5;
}

.current-shopping-details .bottom .billing-total-amount .value {
  font-size: 1.3em;
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