<template>
  <modal>
    <div v-if="mode == 'warn'" class="warn">
      <i class="fas fa-exclamation-triangle fa-fw"></i>
    </div>
    <div v-else-if="mode == 'error'" class="error">
      <i class="fas fa-times"></i>
    </div>
    <div v-else-if="mode == 'help'" class="help">
      <i class="fas fa-question-circle"></i>
    </div>
    <div v-else class="info">
      <i class="fas fa-info-circle fa-fw"></i>
    </div>
    <slot></slot>
    <div class="buttons" v-if="type == 'ask'">
      <button class="btn" v-on:click.stop.prevent="cancel">いいえ</button>
      <button class="btn primary" v-on:click.stop.prevent="ok">はい</button>
    </div>
    <div class="buttons" v-else>
      <!-- info -->
      <button class="btn primary" v-on:click.stop.prevent="ok">OK</button>
    </div>
  </modal>
</template>

<script>
module.exports = {
  props: ["mode", "type"],
  data() {},
  components: {
      modal: httpVueLoader("/vue/modal.vue")
  },
  methods: {
    ok() {
      this.$emit("ok");
    },
    cancel() {
      this.$emit("cancel");
    },
  },
};
</script>
