<template>
  <div style="padding:0px 0">
    <!--    功能区域-->
    <div style="margin: 10px">
      <el-button type="primary" @click="add">新增</el-button>
      <el-upload
          :action="filesUploadUrl"
          :on-success="handleUploadSuccess"
          :show-file-list=false
          :limit="10"
          accept='.xlsx'
          style="display: inline-block; margin: 0 10px"
      ><el-button type="primary">导入</el-button>
      </el-upload>
      <el-button type="primary" @click="exportAdmin">导出</el-button>

      <span style="float: right; width: 40%">
        <el-input v-model="search" placeholder="请输入关键字" style="width: 80%" clearable></el-input>
        <el-button type="primary" style="margin-left: 5px;" @click="load">查询</el-button>
      </span>
    </div>
    <!--    行区-->
    <div style="margin: 10px">
      <el-table :data="tableData" border stripe style="width: 99%">
        <el-table-column prop="id" label="ID" sortable></el-table-column>
        <el-table-column prop="name" label="用户名" sortable></el-table-column>
        <el-table-column prop="nickname" label="昵称" sortable></el-table-column>
        <el-table-column prop="passwd" label="密码"></el-table-column>
        <el-table-column label="角色">
          <template #default="scope">
            <span v-if="scope.row.role===1">超级管理员</span>
            <span v-if="scope.row.role===2">普通管理员</span>
          </template>
        </el-table-column>
        <el-table-column label="头像" width="122px">
          <template #default="scope">
            <el-image
                style="width: 100px; height: 100px"
                :src="scope.row.headpic"
                :preview-src-list="[scope.row.headpic]">
            </el-image>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="145px">
          <template #default="scope">
            <el-button size="mini" @click="handleEdit(scope.row)">编辑</el-button>
            <el-popconfirm title="确定删除吗？" @confirm="handleDelete(scope.row.id)">
              <template #reference>
                <el-button size="mini" type="danger">删除</el-button>
              </template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <!--    分页区-->
    <div style="margin: 10px">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="currentPage"
          :page-sizes="[5, 10, 20, 40]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>
    <!--      新增表单-->
    <el-dialog title="提示" v-model="dialogVisible" width="30%">
      <el-form :model="form" label-width="120px">
        <el-form-item label="用户名">
          <el-input v-model="form.name" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item label="昵称">
          <el-input v-model="form.nickname" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="form.passwd" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item label="头像">
          <el-upload ref="upload" :action="headpicUploadUrl" :on-success="headpicUploadSuccess">
            <el-button type="primary">点击上传</el-button>
          </el-upload>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="save">确 定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "Admin",
  components: {},
  data() {
    return {
      admin: {},
      search: '',
      currentPage: 1,
      pageSize: 10,
      total: 10,
      dialogVisible: false,
      form: {},
      tableData: [],
      filesUploadUrl: "http://"+window.server.Url+":9090/admin/import",
      headpicUploadUrl: "http://"+window.server.Url+":9090/files/upload",
    }
  },
  //网页一加载就调用这个方法
  created() {
    this.load();
  },
  methods: {
    handleUploadSuccess(res) {
      if (res.code === "0") {
        this.$message.success("导入成功");
        this.load();
      }
      else {
        this.$message.error("导入失败");
      }
    },
    exportAdmin(){
      location.href = "http://"+window.server.Url+":9090/admin/export";
    },
    headpicUploadSuccess(res) {
      console.log("headpicUploadSuccess" + res);
      this.form.headpic = res.data;
    },
    load() {
      request.get("/admin", {
        params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          search: this.search,
        }
      }).then(
          res => {
            // console.log(res);
            this.tableData = res.data.records;
            this.total = res.data.total;
          }
      )
    },
    //添加按钮
    add() {
      this.dialogVisible = true,
      this.form = {},
      this.$nextTick(() => {
        if (this.$refs["upload"]) {
          this.$refs["upload"].clearFiles();
        }
      })
    },
    //保存&更新
    save() {
      console.log(this.form.id);
      if (this.form.id) {
        request.put("/admin", this.form).then(
            res => {
              console.log(res);
              if (res.code === '0') {
                this.$message({
                  type: "success",
                  message: "更新成功",
                })
              } else {
                this.$message({
                  type: "error",
                  message: res.msg
                })
              }
              //刷新当前登录的管理的数据
              if(res.data.id==(JSON.parse(sessionStorage.getItem("admin"))).id) {
                sessionStorage.setItem("admin", JSON.stringify(res.data));
              }
              //刷新数据，并关闭弹窗
              this.load();
              this.dialogVisible = false;
            }
        )
      } else {
        request.post("/admin", this.form).then(
            res => {
              console.log(res);
              if (res.code === '0') {
                this.$message({
                  type: "success",
                  message: "新增成功"
                })
              } else {
                this.$message({
                  type: "error",
                  message: res.msg
                })
              }
              this.load();
              this.dialogVisible = false;
            }
        )
      }
    },
    handleDelete(id) {
      console.log(id);
      request.delete("/admin/" + id).then(
          res => {
            console.log(res);
            if (res.code === '0') {
              this.$message({
                type: "success",
                message: "删除成功"
              })
            } else {
              this.$message({
                type: "error",
                message: res.msg
              })
            }
            this.load();
          }
      )
    },
    //编辑
    handleEdit(row) {
      //深拷贝，避免浅拷贝问题
      this.form = JSON.parse(JSON.stringify(row));
      this.dialogVisible = true;
      //这是一个处理未来元素的方法
      //处理可能因为点击按钮和弹窗是异步加载导致读取不到元素的情况
      this.$nextTick(() => {
        if (this.$refs["upload"]) {
          this.$refs["upload"].clearFiles();
        }
      })
    },
    handleSizeChange(pageSize) {
      this.pageSize = pageSize;
      this.load();
    },
    handleCurrentChange(pageNum) {
      this.currentPage = pageNum;
      this.load();
    },
  }
}
</script>
