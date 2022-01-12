<template>
  <div class="sales-analytics">
    <header>
      <h1>
        <div v-if="titleText == ''">
          ダッシュボード
        </div>
        <div v-else>
          <reverse-button></reverse-button>{{this.titleText}} <small> / ダッシュボード</small>
        </div>
      </h1>
    </header>
    <router-view class="content" @update-title="updateTitle"></router-view>
  </div>
</template>

<script>


module.exports = {
  data: function () {
    return {
      titleText: ""
    };
  },
  components: {
    "reverse-button": httpVueLoader("/vue/component/reverse-button.vue")
  },
  methods: {
    updateTitle(text){
      this.titleText = text;
    }
  },
  mounted() {
    
  },
};
</script>

<style scoped>
h1 {
  margin: 0;
}

header {
  display: flex;
  justify-content: flex-start;
  align-items: center;
  border-bottom: 3px solid #000;
  padding: 0 20px;
  height: 50px;
  font-size: .75em;
}

.content {
  position: relative;
  width: 100%;
  height: calc(100% - 50px);
}

.center-dialog {
  position: absolute;
  display: flex;
  top: 0;
  left: 0;
  z-index: 2;
  width: 100%;
  height: 100%;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

i.anim {
  font-size: 1.5em;
  margin-bottom: 10px;
  animation-duration: 1s;
  animation-name: fa-spin;
  animation-timing-function: ease;
  animation-iteration-count: infinite;
}

.search-container {
  padding: 10px 15px;
  border-bottom: 1px solid #000;
}


</style>