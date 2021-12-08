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
      ><el-button type="primary">导入</el-button>
      </el-upload>
      <el-button type="primary" @click="exportRrder" v-if="admin.role===1">导出</el-button>

      <el-popconfirm title="确定删除吗？" @confirm="deleteBatch" v-if="admin.role===1">
        <template #reference>
          <el-button type="danger">批量删除</el-button>
        </template>
      </el-popconfirm>
      <span style="float: right; width: 40%">
        <el-input v-model="search" placeholder="请输入关键字" style="width: 80%" clearable></el-input>
        <el-button type="primary" style="margin-left: 5px;" @click="load">查询</el-button>
      </span>
    </div>
    <!--    行区-->
    <div style="margin: 10px">
      <el-table :data="tableData" border stripe style="width: 99%" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="35"></el-table-column>
        <el-table-column prop="id" label="ID" sortable></el-table-column>
        <el-table-column prop="number" label="订单号" sortable></el-table-column>
        <el-table-column prop="bookName" label="书籍名" ></el-table-column>
        <el-table-column prop="userName" label="客户名" ></el-table-column>
        <el-table-column prop="totalPrice" label="总价"></el-table-column>
        <el-table-column prop="payPrice" label="实付款"></el-table-column>
        <el-table-column prop="discount" label="优惠"></el-table-column>
        <el-table-column prop="transportPrice" label="运费"></el-table-column>
        <el-table-column prop="createTime" label="创建时间" ></el-table-column>
        <el-table-column prop="payTime" label="付款时间" ></el-table-column>
        <el-table-column label="支付状态">
          <template #default="scope">
            <span v-if="scope.row.state===0">未支付</span>
            <span v-if="scope.row.state===1">已支付</span>
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

      <el-dialog title="提示" v-model="dialogVisible" width="30%">
        <el-form :model="form" label-width="120px">
          <el-form-item label="书籍ID">
            <el-input v-model="form.bookId" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item label="书籍名">
            <el-input v-model="form.bookName" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item label="客户ID">
            <el-input v-model="form.userId" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item label="客户名">
            <el-input v-model="form.userName" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item label="总价">
            <el-input v-model="form.totalPrice" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item label="实付款">
            <el-input v-model="form.payPrice" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item label="优惠">
            <el-input v-model="form.discount" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item label="运费">
            <el-input v-model="form.transportPrice" style="width: 80%"></el-input>
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
  name: "Order",
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
      importUrl: "http://"+window.server.Url+":9090/order/import",
      ids: [],  //需要删除的id的数组
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
    deleteBatch() {
      //判断是否选择了数据
      if(!this.ids.length) {
        this.$message.warning("请选择数据！");
        return;
      }
      //一次性将ids数组中的id发送到后台
      request.post("/order/deleteBatch", this.ids).then(res=>{
        if(res.code==='0') {
          this.$message.success("批量删除成功！");
          this.load();
        }
        else {
          this.$message.error(res.msg);
        }
      })
    },
    handleSelectionChange(val) {
      this.ids = val.map(v => v.id);      //只将id存到ids数组中
    },
    handleUploadSuccess(res) {
      if (res.code === "0") {
        this.$message.success("导入成功");
        this.load();
      }
      else {
        this.$message.error("导入失败");
      }
    },
    exportRrder(){
      location.href = "http://"+window.server.Url+":9090/order/export";
    },
    load() {
      request.get("/order", {
        params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          search: this.search,
        }
      }).then(
          res => {
            console.log("load" + res);
            this.tableData = res.data.records;
            this.total = res.data.total;
          }
      )
    },
    //添加按钮
    add() {
      this.dialogVisible = true,
      this.form = {}
    },
    //保存&更新
    save() {
      if (this.form.id) {
        request.put("/order", this.form).then(
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
        request.post("/order", this.form).then(
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
      request.delete("/order/" + id).then(
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
