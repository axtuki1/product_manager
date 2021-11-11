<template>
  <div v-bind:class="{ close : isClose }" v-on:click="toggleSidebar">
    <div class="sidebar-column" v-on:click.stop="blank">
      <sidebar-item
        v-for="item in topList"
        v-bind:key="item.name"
        v-bind:item="item"
        v-bind:class="[item.id]"
        class="topList"
      ></sidebar-item>
      <span style="flex-grow: 1"></span>
      <sidebar-item
        v-for="item in bottomList"
        v-bind:key="item.name"
        v-bind:item="item"
        v-bind:class="[item.id]"
        class="bottomList"
      ></sidebar-item>
      <div class="sidebar-item bottomList close" v-on:click="toggleSidebar">
        <div class="icon">
          <i class="fas fa-arrow-circle-left fa-fw"></i>
        </div>
        <div class="name">折りたたむ</div>
      </div>
    </div>
  </div>
</template>

<script>
module.exports = {
  data: function () {
    const v = this;
    return {
      isClose: true,
      topList: [
        {
          id: "dashboard",
          name: "ダッシュボード",
          icon: '<i class="fas fa-tachometer-alt fa-fw"></i>',
          func: () => {
            v.push("/");
          },
        },
        {
          id: "analytics",
          name: "販売分析",
          icon: '<i class="fas fa-chart-line fa-fw"></i>',
          func() {
            v.push("/analytics");
          },
        },
        {
          id: "product-management",
          name: "商品管理",
          icon: '<i class="fas fa-box-open fa-fw"></i>',
          func() {
            v.push("/product");
          },
        },
      ],
      bottomList: [
        {
          id: "settings",
          name: "設定",
          icon: '<i class="fas fa-cog fa-fw"></i>',
          func() {
            v.push("/settings");
          },
        },
      ],
    };
  },
  components: {
    "sidebar-item": httpVueLoader("/vue/component/sidebar/sidebar-item.vue"),
  },
  methods: {
    toggleSidebar(){
      this.isClose = !this.isClose;
    },
    push(path){
      this.$router.push(path).catch((error) => {});
      this.isClose = true;
    },
    blank(){
      // do nothing.
    }
  }
};
</script>

<style scoped>
.sidebar-column {
  display: flex;
  height: 100%;
  flex-direction: column;
  overflow: auto;
  overflow-x: hidden;
}

.sidebar-item {
  display: flex;
  align-items: center;
  min-height: 60px;
  font-size: 1.1em;
  user-select: none;
  overflow: hidden;
}

.sidebar-item:hover {
  background-color: rgba(255, 255, 255, 0.2);
}

.sidebar-item:active {
  background-color: rgba(255, 255, 255, 0.4);
}

.sidebar-item .icon {
  margin: 0 16.5px;
  transition: transform 250ms ease;
}

.sidebar-item.close .icon i {
  transition: transform 250ms ease;
}

.close .sidebar-item.close .icon i {
  transform: rotate(180deg) translateY(0.3px);
}

</style>