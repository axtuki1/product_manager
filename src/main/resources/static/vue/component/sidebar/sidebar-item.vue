<template>
  <div class="sidebar-item" v-on:click="clickFunction">
    <div class="icon" v-html="item.icon"></div>
    <div class="name">{{ item.name }}</div>
  </div>
</template>

<script>
module.exports = {
    props: ["item"],
    methods: {
      clickFunction(){
        if(this.item.func != null && typeof this.item.func == 'function') this.item.func();
      }
    }
}
</script>
