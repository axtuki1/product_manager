<template>
  <div class="infomation-item" v-bind:class="info.id">
    <div class="key">{{ this.info.key }}</div>
    <div class="value">{{ (
      this.itemData[this.info.id] != null && this.itemData[this.info.id].toString() != "" ? (
        (this.itemData[this.info.id].name != null ? this.itemData[this.info.id].name : this.itemData[this.info.id])
      ) : "-") }}</div>
  </div>
</template>

<script>
module.exports = {
    props:["itemData", "info"]
};
</script>

<style scoped>

.infomation-item{
    display: flex;
    justify-content: flex-start;
    align-items: center;
}

.key:after {
    content: ":";
    margin-right: 5px;
}
</style>