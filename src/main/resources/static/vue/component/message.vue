<template>
  <div class="message">
    <!-- <div class="icon">
      <i class="fas fa-info-circle"></i>
    </div> -->
    <div class="content">
      <slot></slot>
    </div>
  </div>
</template>

<script>
module.exports = {
  props:["type"],
  data(){
    return {
      
    }
  }
}
</script>

<style scoped>

.message {
  width: 100%;
  background-color: rgb(227, 229, 255);
  border-left: 5px solid rgb(49, 97, 255);
}

.message .icon {
  background-color: rgb(135, 116, 255);
  color: #FFF;
  padding: 10px;
  display: flex;
  justify-content: flex-start;
}

.message .content {
  padding: 15px;
}

.message.danger{
  background-color: rgb(255, 227, 227);
  border-left: 5px solid rgb(255, 55, 55);
}

.message.warn{
  background-color: rgb(255, 248, 227);
  border-left: 5px solid rgb(255, 218, 55);
}

.danger i:before {
  content: "\f1e2";
}

.warn i:before {
  content: "\f071";
}

</style>