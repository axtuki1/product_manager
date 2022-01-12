<template>
  <button class="reverse" @click="push">
    <i class="fas fa-arrow-left"></i>
  </button>
</template>

<script>
module.exports = {
  methods:{
    push(){
      this.$router.go(-1);
    }
  }
}
</script>

<style scoped>
.reverse {
  border: none;
  background: transparent;
  margin-right: 10px;
  margin-left: -12.5px;
  font-size: 1.3rem;
  width: 40px;
  height: 40px;
  border-radius: 50%;
  background-color: rgba(0, 0, 0, 0);
  transition: background-color 50ms ease;
}

.reverse:hover {
  background-color: rgba(0, 0, 0, 0.2);
}

.reverse:active {
  background-color: rgba(0, 0, 0, 0.4);
}
</style>