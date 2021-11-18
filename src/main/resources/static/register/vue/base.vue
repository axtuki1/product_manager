<template>
  <div class="base">
    <div class="left">
      <div class="current-shopping-details">
        <div class="registed-history">履歴</div>
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
    <div class="right">
      <div class="catalog">catalog</div>
      <div class="controls">
        <div class="keypad">
          <div class="top">
            <button>7</button>
            <button>8</button>
            <button>9</button>
          </div>
          <div class="top">
            <button>4</button>
            <button>5</button>
            <button>6</button>
          </div>
          <div class="top">
            <button>1</button>
            <button>2</button>
            <button>3</button>
          </div>
          <div class="top">
            <button>0</button>
            <button>00</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
module.exports = {
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
  flex: 1;
  margin: 20px;
}

.current-shopping-details {
  height: 100%;
  border-radius: 10px;
  background: rgb(241, 241, 241);
  padding: 20px;
}
</style>