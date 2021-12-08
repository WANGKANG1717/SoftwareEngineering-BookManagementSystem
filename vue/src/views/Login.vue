<template>
  <div class="homepage-hero-module">
    <div class="video-container">
      <div :style="fixStyle" class="filter">
        <div style="width: 400px; margin: 100px auto">
          <div style="font-size: 30px; text-align: center; padding: 30px 0; color: #333">欢迎登录</div>
          <el-form ref="form" :model="form" size="normal" :rules="rules">
            <el-form-item prop="username">
              <el-input prefix-icon="el-icon-user-solid" v-model="form.name" placeholder="请输入账号"></el-input>
            </el-form-item>
            <el-form-item prop="password">
              <el-input prefix-icon="el-icon-lock" v-model="form.passwd" show-password placeholder="请输入密码"></el-input>
            </el-form-item>
            <el-form-item>
              <div style="display: flex">
                <el-input prefix-icon="el-icon-key" v-model="form.validCode" style="width: 50%;" placeholder="请输入验证码"></el-input>
                <ValidCode @input="createValidCode" />
              </div>
            </el-form-item>
            <el-form-item>
              <el-button style="width: 100%" type="primary" @click="login">登 录</el-button>
            </el-form-item>
            <el-form-item><el-button type="text" @click="$router.push('/register')">前往注册 >> </el-button></el-form-item>
          </el-form>
        </div>
      </div>
      <video :style="fixStyle" autoplay loop muted class="fillWidth" v-on:canplay="canplay">
        <source src="../assets/sea.mp4" type="video/mp4"/>
        浏览器不支持 video 标签，建议升级浏览器。
      </video>
    </div>
  </div>

</template>

<script>
import request from "@/utils/request";
import ValidCode from "@/components/ValidCode";

export default {
  name: "Login",
  components: {
    ValidCode,
  },
  data() {
    return {
      vedioCanPlay: false,
      fixStyle: '',
      form: {},
      rules: {
        name: [
          {required: true, message: '请输入用户名', trigger: 'blur'},
        ],
        passwd: [
          {required: true, message: '请输入密码', trigger: 'blur'},
        ],
      },
      validCode: '',
    }
  },
  mounted() {
    sessionStorage.removeItem("admin")

    window.onresize = () => {
      const windowWidth = document.body.clientWidth;
      const windowHeight = document.body.clientHeight;
      const windowAspectRatio = windowHeight / windowWidth;
      let videoWidth;
      let videoHeight;
      if (windowAspectRatio < 0.5625) {
        videoWidth = windowWidth;
        videoHeight = videoWidth * 0.5625;
        this.fixStyle = {
          height: windowWidth * 0.5625 + 'px',
          width: windowWidth + 'px',
          'margin-bottom': (windowHeight - videoHeight) / 2 + 'px',
          'margin-left': 'initial'
        }
      } else {
        videoHeight = windowHeight;
        videoWidth = videoHeight / 0.5625;
        this.fixStyle = {
          height: windowHeight + 'px',
          width: windowHeight / 0.5625 + 'px',
          'margin-left': (windowWidth - videoWidth) / 2 + 'px',
          'margin-bottom': 'initial'
        }
      }
    }
  },
  methods: {
    canplay() {
      this.vedioCanPlay = true;
    },
    // 接收验证码组件提交的 4位验证码
    createValidCode(data) {
      this.validCode = data;
    },
    login() {
      this.$refs['form'].validate((valid) => {
        // console.log(this.form);
        if (valid) {
          if(!this.form.validCode) {
            this.$message.error("请填写验证码");
            return;
          }
          if(this.form.validCode.toLowerCase() !== this.validCode.toLowerCase()) {
            this.$message.error("验证码错误");
            return;
          }
          request.post("/admin/login", this.form).then(
              res => {
                if (res.code === '0') {
                  this.$message({
                    type: "success",
                    message: "登录成功"
                  })
                  sessionStorage.setItem("admin", JSON.stringify(res.data));  // 缓存用户信息
                  //缓存用户信息
                  this.$router.push("/index")  //登录成功之后进行页面跳转
                } else {
                  this.$message({
                    type: "error",
                    message: res.msg
                  })
                }
              }
          )
        } else {
          console.log('error submit!!');
          return ;
        }
      });
    },
  }
}
</script>

<style scoped>
.homepage-hero-module,
.video-container {
  position: relative;
  height: 100vh;
  overflow: hidden;
}

.video-container .poster img{
  z-index: 0;
  position: absolute;
}

.video-container .filter {
  z-index: 1;
  position: absolute;
  /*background: rgba(0, 0, 0, 0.4);*/
  width: 100%;
}

.fillWidth {
  width: 100%;
}
</style>

<!--<template>-->
<!--  <div style="width:100%; height: 100vh; background-color: darkslateblue; overflow:hidden">-->
<!--    <div style="width:400px; margin:100px auto">-->
<!--      <div style="color: #cccccc; font-size: 30px; text-align: center; padding: 30px 0">欢迎登录</div>-->
<!--      <el-form ref="form" :model="form" :rules="rules" size="normal">-->
<!--        <el-form-item prop="name">-->
<!--          <el-input v-model="form.name" prefix-icon="el-icon-user-solid"></el-input>-->
<!--        </el-form-item>-->
<!--        <el-form-item prop="password">-->
<!--          <el-input v-model="form.passwd" prefix-icon="el-icon-lock" show-password></el-input>-->
<!--        </el-form-item>-->
<!--        <el-form-item>-->
<!--          <div style="display: flex">-->
<!--            <el-input prefix-icon="el-icon-key" v-model="form.validCode" style="width: 50%;" placeholder="请输入验证码"></el-input>-->
<!--            <ValidCode @input="createValidCode"/>-->
<!--          </div>-->
<!--        </el-form-item>-->
<!--        <el-form-item prop="login">-->
<!--          <el-button style="width:100%" type="primary" @click="login">登录</el-button>-->
<!--        </el-form-item>-->
<!--        <el-form-item prop="register">-->
<!--          <el-button style="width:100%" type="primary" @click="register">什么？还没账号！赶紧注册吧</el-button>-->
<!--        </el-form-item>-->
<!--      </el-form>-->
<!--    </div>-->
<!--  </div>-->
<!--</template>-->

<!--<script>-->
<!--import request from "@/utils/request";-->
<!--import ValidCode from "@/components/ValidCode"-->

<!--export default {-->
<!--  name: "Login",-->
<!--  components: {-->
<!--    ValidCode-->
<!--  },-->
<!--  data() {-->
<!--    return {-->
<!--      //不知道啥用-->
<!--      form: {},-->
<!--      //表单校验-->
<!--      rules: {-->
<!--        name: [-->
<!--          {required: true, message: '请输入用户名称', trigger: 'blur'},-->
<!--        ],-->
<!--        passwd: [-->
<!--          {required: true, message: '请输入密码', trigger: 'blur'},-->
<!--        ],-->
<!--      },-->
<!--      validCode: '',-->
<!--    }-->
<!--  },-->
<!--  //网页打开进行的操作-->
<!--  created() {-->
<!--    sessionStorage.removeItem("admin");-->
<!--  },-->
<!--  methods: {-->
<!--    //接收验证码组件提交的四位验证码-->
<!--    createValidCode(data) {-->
<!--      this.validCode=data;-->
<!--    },-->
<!--    login() {-->
<!--      this.$refs['form'].validate((valid) => {-->
<!--        // console.log(this.form);-->
<!--        if (valid) {-->
<!--          if(!this.form.validCode) {-->
<!--            this.$message.error("请填写验证码");-->
<!--            return;-->
<!--          }-->
<!--          if(this.form.validCode.toLowerCase() !== this.validCode.toLowerCase()) {-->
<!--            this.$message.error("验证码错误");-->
<!--            return;-->
<!--          }-->
<!--          request.post("/admin/login", this.form).then(-->
<!--              res => {-->
<!--                if (res.code === '0') {-->
<!--                  this.$message({-->
<!--                    type: "success",-->
<!--                    message: "登录成功"-->
<!--                  })-->
<!--                  sessionStorage.setItem("admin", JSON.stringify(res.data));  // 缓存用户信息-->
<!--                  //缓存用户信息-->
<!--                  this.$router.push("/index")  //登录成功之后进行页面跳转-->
<!--                } else {-->
<!--                  this.$message({-->
<!--                    type: "error",-->
<!--                    message: res.msg-->
<!--                  })-->
<!--                }-->
<!--              }-->
<!--          )-->
<!--        } else {-->
<!--          console.log('error submit!!');-->
<!--          return false;-->
<!--        }-->
<!--      });-->
<!--    },-->
<!--    register() {-->
<!--      this.$router.push("/register")  //页面跳转-->
<!--    },-->
<!--  }-->
<!--}-->
<!--</script>-->

<!--<style scoped>-->

<!--</style>-->