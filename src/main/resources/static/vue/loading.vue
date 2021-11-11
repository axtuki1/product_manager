<template>
  <div class="loading-screen-wrapper">
    <div class="loading-screen">
      <loading-text></loading-text>
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
.loading-screen-wrapper {
  z-index: 100;
  position: fixed;
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 100%;
  transition: opacity ease 250ms;
  opacity: 0;
  color: #000;
  /* background: linear-gradient(
    -45deg,
    rgba(0, 0, 0, .2) 25%,
    rgba(0, 0, 0, .4) 25%,
    rgba(0, 0, 0, .4) 50%,
    rgba(0, 0, 0, .2) 50%,
    rgba(0, 0, 0, .2) 75%,
    rgba(0, 0, 0, .4) 75%,
    rgba(0, 0, 0, .4)
  ); */
  background: rgb(36, 68, 110);
  background-size: 10px 10px;

  pointer-events: none;
}

.loading-screen-wrapper.show {
  pointer-events: all;
  opacity: 1;
}

.loading-screen {
  /* padding: 10px 20px; */
  /* background: #fff; */
  /* border-radius: 10px; */
  font-size: 1.6em;
  color: #FFF;
}

</style>