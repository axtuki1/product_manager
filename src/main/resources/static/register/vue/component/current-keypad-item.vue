<template>
  <div class="item-wrapper">
    <div class="payment">
      <div class="amount">{{ $t("amount", [numberFormat(item.amount == 0 ? 1 : item.amount)]) }}</div>
      <div class="price">{{ $t("price", [numberFormat(item.price)]) }}</div>
      <div class="small-total">
        <div class="value">{{ $t("smallTotal.value", [numberFormat(item.amount * item.price)]) }}</div>
        <div class="label">{{ $t("smallTotal.label") }}</div>
      </div>
    </div>
  </div>
</template>

<script>
module.exports = {
  props: ["item"],
  data(){
    return {
      isDeleted: false
    }
  },
  i18n: {
    locale: i18n.locale,
    messages: {
      ja: {
        smallTotal: {
          label: "小　計",
          value: "{0} 円",
        },
        price: "@{0}円",
        amount: "{0}個",
        type: {
          register: "登録",
          delete: "取消",
        },
      },
    },
  },
  methods: {
    numberFormat(num) {
      return (num || 0)
        .toString()
        .replace(/^-?\d+/g, (m) => m.replace(/(?=(?!\b)(\d{3})+$)/g, ","));
    },
    cancelBtn(){
      this.isDeleted = true;
      this.$emit("cancel", this.item.id);
    }
  },
  mounted() {},
};
</script>

<style scoped>
.item-wrapper {
  position: relative;
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

.small-total .label {
  font-size: 0.95em;
  height: 1.1em;
}
</style>