<template>
  <div class="error-screen-wrapper">
    <div class="error-screen">
      <div class="title">{{ fatalerror.title }}</div>
      <div class="reason">{{ fatalerror.reason }}</div>
    </div>
  </div>
</template>

<script>
module.exports = {
  props:["fatalerror"]
};
</script>

<style scoped>
.error-screen-wrapper {
  position: absolute;
  top: 0;
  left: 0;
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 100%;
  z-index: 110;
  background: rgba(0, 0, 0, 0.473);
  animation-name: showAnim;
  animation-duration: .5s;
  animation-timing-function: ease;
  animation-iteration-count: 1;
}

.error-screen {
  position: relative;
  width: 100%;
  background: #FFF;
  padding: 20px;
}

@keyframes showAnim {
  0%{
    opacity: 0;
  }
  100%{
    opacity: 1;
  }
}

.error-screen::after,
.error-screen::before {
  content: "";
  position: absolute;
  width: 100%;
  height: 10px;
  background: linear-gradient(
    -45deg,
    rgb(255, 196, 0) 25%,
    rgb(0, 0, 0) 25%,
    rgb(0, 0, 0) 50%,
    rgb(255, 196, 0) 50%,
    rgb(255, 196, 0) 75%,
    rgb(0, 0, 0) 75%,
    rgb(0, 0, 0)
  );
  background-size: 20px 20px;
  animation-duration: 0.5s;
  animation-name: err-border-move;
  animation-iteration-count: infinite;
  animation-timing-function: linear;
}

@keyframes err-border-move {
    0%{
        background-position-x: 0;
    }
    100%{
        background-position-x: 40px;
    }
}

.error-screen::after {
  top: -10px;
  left: 0;
}
.error-screen::before {
  bottom: -10px;
  left: 0;
  animation-direction: reverse;
}

.error-screen .title {
    font-size: 1.3em;
    font-weight: bold;
    border-bottom: 1.5px solid #000;
}

.error-screen .reason {
    margin-top: 10px;
}
</style>
