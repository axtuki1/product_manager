<i18n>
{
    "ja":{
        "smallTotal": {
            "label": "小　計",
            "value": "${0} 円"
        },
        "price": "@${0}円",
        "amount": "${0}個",
        "type": {
            "register": "登録",
            "delete": "取消"
        }
    }
}
</i18n>

<template>
  <div class="item-wrapper">
    <div class="name">{{ item.name }}</div>
    <div class="type">{{ $t("type."+item.type, item.type) }}</div>
    <div class="payment">
      <div class="amount">{{ $t("amount", item.amount) }}</div>
      <div class="price">{{ $t("price", item.price) }}</div>
      <div class="small-total">
        <div class="value">{{ $t("smallTotal.value", item.smallTotal) }}</div>
        <div class="label">{{ $t("smallTotal.label") }}</div>
      </div>
    </div>
  </div>
</template>

<script>
module.exports = {
    props: ["item"]
}
</script>
