<template>
  <div class="analytics">
    <div v-show="!loading">
      <div class="this-month-total-seles">
        <div class="label">
          今月の売上
          <div class="upper-per"><span>伸び率</span>: <span>{{ 
              Math.floor((thisMonthSalesScore - lastMonthSalesScore) / lastMonthSalesScore * 10) / 10
            }}%</span></div>
        </div>
        <div class="value">{{ $APPDATA.util_methods.numberFormat(thisMonthSalesScore) }}円</div>
      </div>
      <div class="before-month-total-seles">
        <div class="label">
          先月の売上
        </div>
        <div class="value">{{ $APPDATA.util_methods.numberFormat(lastMonthSalesScore) }}円</div>
      </div>
    </div>
    <div class="item-list" v-show="!loading && salesList.length != 0">
      <sales-history-item
        v-for="item in salesList"
        v-bind:key="item.name"
        v-bind:item="item"
      ></sales-history-item>
    </div>
    <div class="loading" v-show="loading">
      <i class="fas fa-circle-notch anim"></i>
      <loading-text style="font-weight: bold"></loading-text>
    </div>
    <div class="nope" v-show="!loading && salesList.length == 0">
      <i class="fas fa-question-circle"></i>
      <div style="font-weight: bold">
        データが見つかりませんでした。
      </div>
    </div>
  </div>
</template>

<script>
module.exports = {
  data: function () {
    return {
      loading: true,
      salesList:[],
      thisMonthSalesScore: 0,
      lastMonthSalesScore: 0
    };
  },
  components: {
    "loading-text": httpVueLoader("/vue/component/loading-text.vue"),
    "sales-history-item": httpVueLoader("/vue/component/analytics/sales-history-item.vue"),
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
    search(){

    }

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

.this-month-total-seles,
.before-month-total-seles {
  margin: 10px;
  font-size: 2.5rem;
  font-weight: bold;
  background: rgb(235, 235, 235);
  border-radius: 10px;
  padding: 10px 15px;
}

.this-month-total-seles .label,
.before-month-total-seles .label {
  font-size: .75em;
}

.before-month-total-seles {
  font-size: 2rem;
}

.value {
  width: 100%;
  text-align: right;
}

.this-month-total-seles .upper-per {
  font-size: 1rem;
  width: 100%;
  text-align: right;
}

.nope {
  position: relative;
  display: flex;
  top: 0;
  left: 0;
  z-index: 2;
  width: 100%;
  height: calc(100% - 280px);
  min-height: 200px;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 15px 20px;
}

.nope i {
  font-size: 2em;
  margin-bottom: 10px;
}
</style>