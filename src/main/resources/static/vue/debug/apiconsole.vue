<template>
  <div class="api-console-wrapper"
        v-on:mousemove.stop.prevent="move($event)">
    <div
      class="api-console"
      v-bind:class="{ show: isOpen, grab: isGrabbing }"
      :style="{ transform: `translate(${pos.x}px, ${pos.y}px)` }"
    >
      <div
        class="header"
        v-on:mousedown.stop.prevent="grab($event)"
        v-on:mouseup.stop.prevent="release($event)"
        v-on:mouseleave.stop.prevent="release($event)"
      >
        <div class="close" v-on:click="this.isOpen = false">✖</div>
      </div>
      <div class="content">
        <button v-on:click="$APPDATA.util_methods.logout" class="btn primary">Logout</button>
        <button v-on:click="silent_logout" class="btn primary">Logout (silent)</button>
        <hr>
        <div class="inputField api_endpoint">
          <label for="api_endpoint">API Endpoint</label>
          <input type="text" id="api_endpoint" v-model="api_endpoint" />
        </div>
        <div class="inputField api_method">
          <label for="api_method">Request Method</label>
          <select id="api_method" v-model="api_method">
            <option value="GET">GET</option>
            <option value="POST">POST</option>
          </select>
        </div>
        <div class="inputField api_body">
          <label for="api_body">Request Body</label>
          <textarea id="api_body" v-model="api_body"></textarea>
        </div>
        <button v-on:click="doReq" class="btn primary">Req invite</button>
        <hr />
        StatusCode: <span>{{ statusCode }}</span>
        <div class="output" v-bind:class="{ error: isError }">
          {{ output }}
        </div>
      </div>
    </div>
  </div>
</template>

<script>
module.exports = {
  data() {
    return {
      api_endpoint: "/api/v1/auth",
      api_method: "GET",
      api_body: "",
      output: "",
      statusCode: 200,
      isError: false,
      isOpen: false,
      isGrabbing: false,
      pos: {
        x: 0,
        y: 0,
      },
      basePos: {
        x: 0,
        y: 0
      }
    };
  },
  mounted() {
    document.addEventListener("keydown", (e) => {
      if (e.ctrlKey && e.shiftKey && e.key == "!") {
        e.stopPropagation();
        e.preventDefault();
        this.isOpen = !this.isOpen;
      }
    });
  },
  methods: {
    doReq() {
      let header = {
        method: this.api_method,
        headers: new Headers({
          "content-type": "application/json",
        }),
      };
      if( this.api_method != "GET" ){
          header.body = this.api_body;
      }
      fetch(this.api_endpoint, header).then((d) => {
        this.isError = !d.ok;
        this.status = d.statusCode;
        return d.json();
      }).then((j)=>{
        this.output = j;
      });
    },
    grab(e) {
      this.isGrabbing = true;
      this.basePos.x = e.screenX;
      this.basePos.y = e.screenY;
    },
    move(e) {
      if (this.isGrabbing) {
        this.pos.x += e.screenX - this.basePos.x;
        this.pos.y += e.screenY - this.basePos.y;
        this.basePos.x = e.screenX;
        this.basePos.y = e.screenY;
      }
    },
    release(e) {
      this.isGrabbing = false;
    },
    silent_logout() {
      fetch("/api/v1/logout", {
        method: "GET",
        headers: new Headers({
          "content-type": "application/json",
        }),
      })
        .then((res) => res.json())
        .then((loginResult) => {
        })
        .catch((error) => {
          console.log(error);
          v.$APPDATA.util_methods.fatal_error_handle(
            "ログアウト処理中にエラーが発生しました",
            "通信環境を再度確認してください。"
          );
        });
    },
  },
};
</script>

<style scoped>
label {
  width: 100%;
  display: block;
}

input,
textarea {
  width: 100%;
  outline: none;
}

.inputField {
  margin-bottom: 20px;
}

.inputField input {
  font-size: 1.1em;
}

.output {
  background: rgb(216, 255, 189);
  height: 10em;
  overflow: auto;
}

.output.error {
  background: rgb(255, 213, 189);
}

.api-console-wrapper {
  z-index: 999999;
  position: absolute;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  pointer-events: none;
}

.api-console {
  display: inline-block;
  opacity: 0;
  pointer-events: none;
}

.api-console.show {
  opacity: 1;
  pointer-events: all;
}

.api-console .header {
  padding: 7.5px 12.5px;
  background: rgb(194, 194, 194);
  border: 1px solid #000;
  border-bottom: none;
  border-top-left-radius: 10px;
  border-top-right-radius: 10px;
  display: flex;
  justify-content: flex-end;
  cursor: grab;
  user-select: none;
}

.api-console.grab .header {
  cursor: grabbing;
}

.api-console .content {
  padding: 15px;
  background: rgb(255, 255, 255);
  border: 1px solid #000;
  border-top: none;
  border-bottom-left-radius: 10px;
  border-bottom-right-radius: 10px;
}
</style>