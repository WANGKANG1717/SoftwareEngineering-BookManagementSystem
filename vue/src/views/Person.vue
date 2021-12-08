<template>
  <el-card class="box-card">
    <template #header>
      <div class="card-header">
        <span style="color: dodgerblue">个人信息</span>
      </div>
    </template>
    <div style="margin: 0px; width: 500px">
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="头像">
          <el-image
              style="width: 100px; height: 100px"
              :src="form.headpic"
              :preview-src-list="[form.headpic]">
          </el-image>
          <el-upload ref="upload" :action="headpicUploadUrl" :on-success="headpicUploadSuccess">
            <el-button type="primary">点击上传</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item label="用户名">
          <el-input :disabled="true" v-model="form.name" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item label="昵称">
          <el-input v-model="form.nickname" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="form.passwd" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item label="角色">
            <span v-if="form.role===1">超级管理员</span>
            <span v-if="form.role===2">普通管理员</span>
        </el-form-item>
        <div style="text-align: center;">
          <el-button type="primary" @click="update" style="margin-right: 120px">保存</el-button>
          <el-button type="primary" @click="this.$router.push('/index')">返回</el-button>
        </div>
      </el-form>
    </div>
  </el-card>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "Person",
  data() {
    return {
      form: {},
      headpicUploadUrl: "http://"+window.server.Url+":9090/files/upload",
    }
  },
  created() {
    //深拷贝，避免浅拷贝问题
    this.form = JSON.parse(sessionStorage.getItem("admin"));
  },
  methods: {
    headpicUploadSuccess(res) {
      console.log("headpicUploadSuccess" + res);
      this.form.headpic = res.data;
    },
    update() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          request.put("/admin", this.form).then(
              res => {
                console.log(res);
                if (res.code === '0') {
                  this.$message({
                    type: "success",
                    message: "更新成功",
                  })
                  //刷新数据，并关闭弹窗
                  sessionStorage.setItem("admin", JSON.stringify(res.data))  // 缓存用户信息
                  this.form = JSON.parse(sessionStorage.getItem("admin"));
                  this.$router.push("/");
                } else {
                  this.$message({
                    type: "error",
                    message: "更新失败"
                  })
                  this.$router.push("/");
                }
              }
          )
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    }
  }
}
</script>