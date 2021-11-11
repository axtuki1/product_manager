<template>
  <div class="settings">
    設定。
  </div>
</template>

<script>
module.exports = {
  data: function () {
    return {
      loading: true,
    };
  },
  components: {
    "loading-text": httpVueLoader("/vue/component/loading-text.vue"),
  },
  methods: {},
  mounted() {
    this.$emit("update-title", "");
  },
};
</script>

<style scoped>
.search-view {
    display: flex;
    flex-direction: column;
}

h1 {
  margin: 0;
}

.search-container {
  padding: 10px 15px;
  border-bottom: 1px solid #000;
}

.item-list {
    overflow: auto;
    flex-grow: 1;
}

</style>