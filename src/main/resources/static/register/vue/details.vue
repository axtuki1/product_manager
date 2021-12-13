
<template>
  <div class="details">
    <div></div>
  </div>
</template>

<script>

module.exports = {
  props: ["items"],
  data() {
    return {
    };
  },
  methods: {

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
    
  },
  mounted() {
    console.log(this.items);
    if( this.items == null || this.items.length == 0 ){
      this.$router.push("/register");
    }
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