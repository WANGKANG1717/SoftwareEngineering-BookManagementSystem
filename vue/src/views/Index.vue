<template>
  <div style="padding: 10px" align="center">
    <el-card>
      <div id="myChart" :style="{height: '600px'}"></div>
    </el-card>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "Index",
  data() {
    return {}
  },
  mounted() {
    this.drawLine();
  },
  methods: {
    drawLine() {
      // 基于准备好的dom，初始化echarts实例
      let myChart = this.$root.echarts.init(document.getElementById('myChart'))
      let option = {
        title: {
          text: '图书类别比例统计图',
          // subtext: '',
          left: 'left'
        },
        tooltip: {
          trigger: 'item'
        },
        legend: {
          trigger: 'item',
          top: 'bottom'
        },
        toolbox: {
          show: true,
          feature: {
            mark: {show: true},
            dataView: {show: true, readOnly: false},
            restore: {show: true},
            saveAsImage: {show: true}
          }
        },
        series: [
          {
            name: '数量',
            type: 'pie',
            radius: [50, 250],
            center: ['50%', '50%'],
            roseType: 'area',
            itemStyle: {
              borderRadius: 8
            },
            data: []
          }
        ]
      }
      request.get("/book/count").then(res => {
        res.data.forEach(item => {
          option.series[0].data.push({name: item.category, value: item.count})
        })
        // 绘制图表
        myChart.setOption(option);
      })
    }
  }
}
</script>

<style scoped>

</style>
