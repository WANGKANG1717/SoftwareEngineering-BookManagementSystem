<template>
  <div style="padding:0px 0">
    <!--    功能区域-->
    <div style="margin: 10px">
      <el-button type="primary" @click="add">新增</el-button>
      <el-upload
          :action="importUrl"
          :on-success="handleUploadSuccess"
          :show-file-list=false
          :limit="10"
          accept='.xlsx'
          style="display: inline-block; margin: 0 10px"
          v-if="admin.role===1"
      >
        <el-button type="primary">导入</el-button>
      </el-upload>
      <el-button type="primary" @click="exportUser" v-if="admin.role===1">导出</el-button>

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
        <el-table-column prop="age" label="年龄" sortable></el-table-column>
        <el-table-column prop="sex" label="性别" sortable></el-table-column>
        <el-table-column prop="address" label="地址" sortable></el-table-column>
        <el-table-column label="角色">
          <template #default="scope">
            <span v-if="scope.row.role===0">普通用户</span>
            <span v-if="scope.row.role===1">会员</span>
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
        <el-form-item label="年龄"
                      :rules="[{ required: true, message: '年龄不能为空'},{ type: 'number', message: '年龄必须为数字值'}]">
          <el-input v-model.number="form.age" autocomplete="off" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <div>
            <el-radio v-model="form.sex" label="男">男</el-radio>
            <el-radio v-model="form.sex" label="女">女</el-radio>
            <el-radio v-model="form.sex" label="未知">未知</el-radio>
          </div>
        </el-form-item>
        <el-form-item label="地址">
          <el-input v-model="form.address" style="width: 80%"></el-input>
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
  name: "User",
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
      importUrl: "http://"+window.server.Url+":9090/user/import",
      headpicUploadUrl: "http://"+window.server.Url+":9090/files/upload",
    }
  },
  //网页一加载就调用这个方法
  created() {
    let adminStr = sessionStorage.getItem("admin");
    this.admin = JSON.parse(adminStr);
    //请求服务器，确认当前用户的合法信息
    request.get("/admin/" + this.admin.id).then(res => {
      if (res.code === '0') {
        this.admin = res.data;
      }
    })
    // console.log(window.server.filesUploadUrl);
    this.load();
  },
  methods: {
    handleUploadSuccess(res) {
      if (res.code === "0") {
        this.$message.success("导入成功");
        this.load();
      } else {
        this.$message.error("导入失败");
      }
    },
    exportUser() {
      location.href = "http://"+window.server.Url+":9090/user/export";
    },
    headpicUploadSuccess(res) {
      console.log("headpicUploadSuccess" + res);
      this.form.headpic = res.data;
    },
    load() {
      request.get("/user", {
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
      if (this.form.id) {
        request.put("/user", this.form).then(
            res => {
              console.log(res);
              if (res.code === '0') {
                this.$message({
                  type: "success",
                  message: "更新成功"
                })
              } else {
                this.$message({
                  type: "error",
                  message: res.msg
                })
              }
              //刷新数据，并关闭弹窗
              this.load();
              this.dialogVisible = false;
            }
        )
      } else {
        request.post("/user", this.form).then(
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
      request.delete("/user/" + id).then(
          res => {
            // console.log(res);
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
