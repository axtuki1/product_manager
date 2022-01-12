<template>
  <div class="dashboard">
    <div v-show="!loading" class="dashboard-content">
      <div class="this-month-total-seles">
        <div class="title">
          今月の売上
        </div>
        <div class="upper-per"><span>伸び率</span>: <span>0.1%</span></div>
        <div class="value">{{ $APPDATA.util_methods.numberFormat(thisMonthSalesScore) }}円</div>
      </div>
      <div class="ranking">
        <div class="title">売上トップ</div>
        <div class="first">1位 / 商品A</div>
        <div class="second">2位 / 商品B</div>
        <div class="third">3位 / 商品C</div>
      </div>
    </div>
    <div class="loading" v-show="loading">
      <i class="fas fa-circle-notch anim"></i>
      <loading-text style="font-weight: bold"></loading-text>
    </div>
  </div>
</template>

<script>
module.exports = {
  data: function () {
    return {
      loading: true,
      thisMonthSalesScore: 0,
    };
  },
  components: {
    "loading-text": httpVueLoader("/vue/component/loading-text.vue"),
  },
  methods: {
    reload(){
      fetch("/api/v1/sales", {
        method: "GET",
        headers: new Headers({
          "content-type": "application/json",
        }),
      })
        .then((d) => d.json())
        .then((j) => {
          this.thisMonthSalesScore = j.data.this_month_sales_score;
          this.lastMonthSalesScore = j.data.last_month_sales_score;
        });
      fetch("/api/v1/sales/history", {
        method: "GET",
        headers: new Headers({
          "content-type": "application/json",
        }),
      })
        .then((d) => d.json())
        .then((j) => {
          this.salesList = j.data.sales_history;
          this.loading = false;
        });
    },
  },
  mounted() {
    this.$emit("update-title", "");
    this.reload();
  },
};
</script>

<style scoped>
h1 {
  margin: 0;
}

.dashboard {
  padding: 10px 15px;
  border-bottom: 1px solid #000;
}

.dashboard-content > * {
  margin: 10px 0;
  background: rgb(235, 235, 235);
  border-radius: 10px;
  padding: 10px 15px;
}

.this-month-total-seles .label,
.before-month-total-seles .label {
  font-size: 0.75em;
}

.this-month-total-seles,
.before-month-total-seles {
  font-weight: bold;
  font-size: 2.5em;
}

.value {
  width: 100%;
  text-align: right;
}

.title {
  font-size: 2rem;
  font-weight: bold;
}

.this-month-total-seles .upper-per {
  font-size: 1rem;
  width: 100%;
  text-align: right;
}
</style>