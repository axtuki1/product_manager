<template>
  <div class="dashboard">
    <div v-show="!loading" class="dashboard-content">
      <div class="this-month-total-seles">
        <div class="title">
          今月の売上
        </div>
        <div class="upper-per"><span>伸び率</span>: <span>{{ 
              Math.floor((thisMonthSalesScore - lastMonthSalesScore) / lastMonthSalesScore * 10) / 10
            }}%</span></div>
        <div class="value">{{ $APPDATA.util_methods.numberFormat(thisMonthSalesScore) }}円</div>
      </div>
      <div class="ranking">
        <div class="title">今月売上トップ</div>
        <div class="first" v-if="best[0] != null">
          <div class="label">1位</div>
          <div class="name">{{ best[0].item_name }}</div>
          <div class="score">{{ $APPDATA.util_methods.numberFormat(best[0].SUMS) }}</div>
        </div>
        <div class="second" v-if="best[1] != null">
          <div class="label">2位</div>
          <div class="name">{{ best[1].item_name }}</div>
          <div class="score">{{ $APPDATA.util_methods.numberFormat(best[1].SUMS) }}</div>
          </div>
        <div class="third" v-if="best[2] != null">
          <div class="label">3位</div>
          <div class="name">{{ best[2].item_name }}</div>
          <div class="score">{{ $APPDATA.util_methods.numberFormat(best[2].SUMS) }}</div>
          </div>
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
      lastMonthSalesScore:0,
      best: []
    };
  },
  components: {
    "loading-text": httpVueLoader("/vue/component/loading-text.vue"),
  },
  methods: {
    reload(){
      fetch("/api/v1/dashboard", {
        method: "GET",
        headers: new Headers({
          "content-type": "application/json",
        }),
      })
        .then((d) => d.json())
        .then((j) => {
          this.thisMonthSalesScore = j.data.this_month_sales_score;
          this.lastMonthSalesScore = j.data.last_month_sales_score;
          this.best = j.data.best;
          console.log(this.best);
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

.ranking > * {
  display: flex;
  align-items: baseline;
}

.ranking > * > * {
  margin-right: 10px;
  font-weight: bold;
}

.ranking .label {
  font-size: 1.3em;
}

.ranking .first .label{
  color: rgb(247, 71, 40);
}

.ranking .second .label{
  color: rgb(247, 40, 247);
}

.ranking .third .label{
  color: rgb(113, 40, 247);
}

.ranking .name {
  font-size: 1.3em;
}

.ranking .score {
  font-size: 1.2em;
}

.score:after {
  content: "円";
}


</style>