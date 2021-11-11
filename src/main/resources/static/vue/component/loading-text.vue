<template>
    <div class="loading-text">
        Loading<span>.</span><span style="animation-delay: 50ms">.</span><span style="animation-delay: 100ms">.</span>
    </div>
</template>

<style scoped>
span{
  animation-name: blink;
  animation-timing-function: ease;
  animation-iteration-count: infinite;
  animation-duration: 300ms;
}

@keyframes blink {
  0%{
    opacity: 0;
  }
  50%{
    opacity: 1;
  }
  100%{
    opacity: 0;
  }
}
</style>