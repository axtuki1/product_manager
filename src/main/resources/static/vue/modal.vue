<template>
  <div
    class="modal-wrapper"
    v-bind:class="{ show: this.show, close: this.close }"
    v-on:click.stop.prevent.self="clickBackgound"
  >
    <div class="modal">
      <slot class="modal-content">{{ modal.content }}<button class="btn primary" v-on:click="closeModal" v-if="modal.ok_btn">OK</button>
      </slot>
    </div>
  </div>
</template>

<script>
module.exports = {
  props: ["modal"],
  emits: [""],
  data() {
    return {
      show: false,
      close: false,
    };
  },
  methods: {
    clickBackgound() {
      this.closeModal();
    },
    closeModal() {
      this.close = true;
    },
  },
  mounted() {
    this.close = false;
    setTimeout(() => {
        this.show = true;
    }, 20);
  },
};
</script>

<style scoped>
.modal-wrapper {
  position: absolute;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: rgba(0, 0, 0, 0);
  backdrop-filter: blur(0);
  transition: all ease 200ms;
  pointer-events: none;
}

.modal-wrapper.show {
  background-color: rgba(0, 0, 0, 0.2);
  backdrop-filter: blur(1.25px);
  pointer-events: all;
}

.modal-wrapper.show.close {
  background-color: rgba(0, 0, 0, 0);
  backdrop-filter: blur(0);
  pointer-events: none;
}

.modal {
  display: inline-flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  padding: 15px 20px;
  min-width: 10%;
  min-height: 10%;
  border: 1px solid rgb(68, 68, 68);
  background: #fff;
  transition: all ease 250ms;
  transform: scale(1.2);
  opacity: 0;
  font-size: 1.1em;
  border-radius: 10px;
  user-select: none;
  white-space: pre-line;
  text-align: center;
}

.modal-wrapper.show .modal {
  transform: scale(1);
  opacity: 1;
}

.modal-wrapper.show.close .modal {
  transform: scale(0.8);
  opacity: 0;
}

.modal > * {
  width: 100%;
  text-align: center;
}

.title {
  font-weight: bold;
}

.title,
.content {
  user-select: all;
}

.modal .close-btn {
  width: 5em;
}
</style>