<template>
  <div class="sidebar-item" @click.middle="clickFunction($event)" @click.left="clickFunction($event)">
    <div class="icon" v-html="item.icon"></div>
    <div class="name">{{ item.name }}</div>
    <div class="external" v-if="item.external"><i class="fas fa-external-link-alt"></i></div>
  </div>
</template>

<script>
module.exports = {
    props: ["item"],
    methods: {
      clickFunction(event){
        if(this.item.func != null && typeof this.item.func == 'function') this.item.func(event);
      }
    }
}
</script>
