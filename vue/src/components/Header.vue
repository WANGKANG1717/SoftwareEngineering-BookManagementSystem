<template>
  <div style="height: 50px; line-height: 50px; border-bottom: 1px solid #ccc; display: flex">
    <div style="width: 200px; padding-left: 30px; font-weight: bold; color: dodgerblue">书城后台管理系统</div>
    <div style="flex: 1"></div>
    <div style="width: 100px">
      <el-dropdown>
        <span class="el-dropdown-link">
          <el-avatar :size="30" :src="admin.headpic" style="position: relative; top: 10px" align="center"></el-avatar>
          {{ admin.name }}
          <i class="el-icon-arrow-down el-icon--right"></i>
        </span>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item @click="$router.push('/person')">个人信息</el-dropdown-item>
            <el-dropdown-item @click="this.$router.push('/login')">退出系统</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "Header",
  data() {
    return {
      admin: {},
    }
  },
  created() {
    this.load();
  },
  methods: {
    load() {
      let adminJson = sessionStorage.getItem("admin");
      if (!adminJson) {
        this.$router.push("/login");
        return ;
      }
      this.admin=JSON.parse(adminJson);
    },
  },
}
</script>

<style scoped>

</style>
