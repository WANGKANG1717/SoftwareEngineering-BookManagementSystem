<template>
  <el-row class="tac">
    <el-col :span="12">
      <!--      <h5>默认颜色</h5>-->
      <el-menu
          style="width:170px; min-height: calc(100vh - 70px)"
          :default-active="this.$route.path"
          router
          class="el-menu-vertical-demo">
        <el-menu-item index="/index">
          <i class="el-icon-house"></i>主页
        </el-menu-item>
        <el-menu-item index="/book">
          <i class="el-icon-notebook-1"></i>书籍管理
        </el-menu-item>
        <el-menu-item index="/category">
          <i class="el-icon-menu"></i>书籍分类
        </el-menu-item>
        <el-menu-item index="/order">
          <i class="el-icon-s-order"></i>订单管理
        </el-menu-item>
        <el-menu-item index="/user">
          <i class="el-icon-user"></i>用户管理
        </el-menu-item>
        <el-menu-item index="/message">
          <i class="el-icon-message"></i>用户留言
        </el-menu-item>
        <el-menu-item index="/admin" v-if="admin.role===1">
          <i class="el-icon-user-solid"></i>管理员管理
        </el-menu-item>
        <el-menu-item index="/about">
          <i class="el-icon-coffee"></i>关于
        </el-menu-item>
      </el-menu>
    </el-col>
  </el-row>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "Aside",
  data() {
    return {
      admin: {},
    }
  },
  created() {
    let adminJson = sessionStorage.getItem("admin");
    if (!adminJson) {
      this.$router.push("/login");
      return;
    }
    this.admin = JSON.parse(adminJson);
  }
}
</script>

<style scoped>

</style>