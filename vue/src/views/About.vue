<template>
  <div>
    <el-button type="text" @click="dialogVisible = true" style="flex: 1">源码下载</el-button>
    <v-md-preview :text="text"></v-md-preview>
  </div>
  <el-dialog v-model="dialogVisible" title="Warning" width="30%">
    <span>GitHub给我点星了吗？</span>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false" style="background: dodgerblue">
          <a :href="'https://github.com/WANGKANG1717/SoftwareEngineering-BookManagementSystem'"
             target="_blank"
             style="text-decoration: none; color:white"
        >没有(这就去)</a></el-button>
        <el-button @click="dialogVisible = false" style="background: dodgerblue">
          <a :href="'https://github.com/WANGKANG1717/SoftwareEngineering-BookManagementSystem'"
             target="_blank"
             style="text-decoration: none; color:white"
          >点了(好孩子)</a></el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script>
export default {
  name: "About",
  data() {
    return {
      dialogVisible:false,
      text: '# **书城后台管理系统**\n' +
          '\n' +
          '\n' +
          '\n' +
          '## 小组成员及分工\n' +
          '\n' +
          '**[王庆康](https://kang17.xyz)**：项目整体结构设计，前端代码实现，前后端代码整合，以及项目部署\n' +
          '\n' +
          '**何雁杰**：后端代码实现\n' +
          '\n' +
          '**张金星**：项目汇报、ppt制作\n' +
          '\n' +
          '**马云敏**：文档及数据录入\n' +
          '\n' +
          '**张靓**：文档及数据录入\n' +
          '\n' +
          '\n' +
          '\n' +
          '## 项目介绍\n' +
          '\n' +
          '众所周知，这是一个烂大街的项目。。。\n' +
          '\n' +
          '在尽力贴合实际的情况下，我尽力让这个项目成为一个切实可以使用的项目。虽然如此，陷于技术能力，目前只能做到这样了。\n' +
          '\n' +
          '此项目基于spring-boot+vue，前端使用的技术包括Vue3、vue router、element-plus、axios、v-md-editor，后端使用的技术包括SpringBoot、Mybatis-plus、hutool、jwt、lombok、poi、jdbc。\n' +
          '\n' +
          '包含六大模块：书籍管理、书籍分类、订单管理、用户管理、用户留言、管理员管理，并且添加了主页的echarts图书分类图标，以及about模块。\n' +
          '\n' +
          '此项目进行了较为严格的权限验证，超级管理员和普通管理员登陆界面有所不同，并且，为了防止通过更改存储在浏览器sessionStorage的内容，来绕过系统的权限验证，此项目使用了token验证，在每次加载页面前都会通过token进行权限验证，确保了权限的有效控制。并且，当token被人破坏，或过期时，系统会强制下线，确保系统的安全性。\n' +
          '\n' +
          '对数据的主要操作不仅包含了常见的增删改查，文件的上传下载，还包括数据的导入、导出，以及数据的可视化统计。\n' +
          '\n' +
          '\n' +
          '\n' +
          '## 项目存在的问题\n' +
          '\n' +
          '- 主页的图表种类不够丰富\n' +
          '- 书籍分类功能单一\n' +
          '- 用户留言模块目前只能瓜管理员留言和回复，暂时还没有实现用户和用户，用户和管理员之间的交互\n' +
          '- 另外，此项目存在的一个安全隐患是，用户和管理员的密码直接暴露在外，存在泄露风险，因此，这是一个需要重点改进的点\n' +
          '- 订单管理模块中存在数据的冗余，应该是只需要书籍id和用户id就够了，然后还应该添加查看订单中图书信息，和用户信息的功能，这个有时间会做的\n',
    };
  },
  methods: {
  }
}
</script>

<style scoped>

</style>
