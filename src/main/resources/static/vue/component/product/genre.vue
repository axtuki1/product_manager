<template>
  <div
    class="genre-item"
    v-on:click="push"
    v-bind:class="{ deleting: deleting }"
  >
    <genre-item
      v-for="item in genreList"
        v-bind:key="item.name"
        v-bind:item="item"
    ></genre-item>
  </div>
</template>

<script>
module.exports = {
  props: ["item", "genreList"],
  data() {
    return {
      deleting: false,
      childGenreList: []
    };
  },
  components: {
    "genre-item": httpVueLoader("/vue/component/product/genre.vue"),
  },
  mounted() {
    this.childGenreList = this.genreList;
  },
  methods: {
    push() {
      this.$router.push("/product/" + this.item.id);
    },
    editPush() {
      this.$router.push("/product/" + this.item.id + "/edit");
    },
    removePush() {
      this.deleting = true;
      const deleteMethod = () => {
        fetch("/api/v1/item/" + this.item.id, {
          method: "DELETE",
          headers: new Headers({
            "content-type": "application/json",
          }),
        })
          .then((res) => res.json())
          .then((j) => {
            this.deleting = false;
            this.$APPDATA.util_methods.callNotice("商品を削除しました。", {
              sec: 3,
            });
            this.$emit("reload");
          })
          .catch((error) => {
            this.deleting = false;
            v.$APPDATA.util_methods.callNotice(
              "削除処理中にエラーが発生しました。",
              { sec: 3 }
            );
          });
      };
      
      deleteMethod();
    },
  },
};
</script>

<style scoped>
.product-item {
  padding: 15px 15px;
  border-bottom: 1px solid #000;
  position: relative;
  box-shadow: 0 0 0 #000;
  transition: box-shadow ease 250ms;
}

.product-item:hover {
  box-shadow: 0px 0px 7.5px rgba(0, 0, 0, 0.4);
}

.moderatorBtn {
  position: absolute;
  right: 15px;
  top: 15px;
}

.name {
  font-size: 1.4em;
}

.price {
}

.progress-icon {
  margin-right: 10px;;
}

i.anim {
  margin: 0;
}

.btn i {
  margin-right: 6px;
}

.deleting-window {
  position: absolute;
  top: 0;
  left: 0;
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.2);
  color: #000;
  opacity: 0;
  pointer-events: none;
  transition: all ease 200ms;
  font-size: 1.2em;
  user-select: none;
}

.deleting-window.show {
  opacity: 1;
  pointer-events: all;
}
</style>
