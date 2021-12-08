<template>
  <div style="padding:0px 0">
    <!--    功能区域-->
    <div style="margin: 10px">
      <el-button type="primary" @click="add" v-if="admin.role===1">新增</el-button>
    </div>
    <!--    行区-->
    <div style="margin: 10px">
      <el-table v-loading="loading" :data="tableData" border stripe style="width: 99%" row-key="id" default-expand-all>
        <el-table-column prop="name" label="名称"></el-table-column>
        <el-table-column fixed="right" width="145px" label="操作" v-if="admin.role===1">
          <template #default="scope">
            <el-button size="mini" @click="handleEdit(scope.row)">编辑</el-button>
            <el-popconfirm title="这是一段内容确定删除吗？" @confirm="handleDelete(scope.row.id)">
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
      <el-dialog title="提示" v-model="dialogVisible" width="30%">
        <el-form :model="form" label-width="120px">
          <el-form-item label="类别">
            <el-input v-model="form.name" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item label="pid">
            <el-input v-model="form.pid" style="width: 80%"></el-input>
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
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "Category",
  components: {},
  data() {
    return {
      admin: {},
      dialogVisible: false,
      form: {},
      tableData: [],
      loading: true,
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
    this.load();
  },
  methods: {
    load() {
      this.loading=true;
      request.get("/category").then(res=>{
        this.loading=false;
        this.tableData=res.data;
      })
    },
    //添加按钮
    add() {
      this.dialogVisible = true
      this.form = {}
    },
    //保存&更新
    save() {
      if (this.form.id) {
        request.put("/category", this.form).then(
            res => {
              console.log("update" + res);
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
        request.post("/category", this.form).then(
            res => {
              console.log("add" + res);
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
      request.delete("/category/" + id).then(
          res => {
            console.log("delete" + res);
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
    },
  }
}
</script>
