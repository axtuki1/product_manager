<template>
  <div class="notice-wrapper">
    <div
      class="notice"
      v-bind:class="{ show: show, close: close, deleteing: deleteing }"
    >
      <slot></slot>
    </div>
  </div>
</template>

<script>
module.exports = {
  props: ["notice"],
  data() {
    return {
      show: false,
      close: false,
      deleteing: false,
    };
  },
  mounted() {
    setTimeout(() => {
      this.show = true;
      setTimeout(() => {
        this.close = true;
        setTimeout(() => {
          this.deleteing = true;
          setTimeout(() => {
            this.$emit("close-n", this.notice.id);
          }, 200);
        }, 500);
      }, this.notice.sec * 1000 + 250);
    }, 20);
  },
};
</script>


<style scoped>
.notice-wrapper {
  z-index: 200;
  display: flex;
  justify-content: flex-end;
}

.notice {
  margin: 7.5px 10px;
  display: inline-block;
  padding: 5px 10px;
  color: #FFF;
  background: rgb(0, 46, 99);
  transition: all ease 250ms;
  transform: translateX(120%);
  border: 1px solid rgb(0, 81, 202);
  border-radius: 10px;
}

.notice.show {
  transform: translateX(0);
}

.notice.show.close {
  transform: translateX(120%);
}

.notice.show.close.deleteing {
  font-size: 0;
  padding: 0px;
  margin: 0px;
  transform: translateX(120%);
}
</style>