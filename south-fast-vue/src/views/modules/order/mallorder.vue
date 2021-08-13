<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="dataForm.orderNumber"  @change="changekeyword" placeholder="订单号" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-input v-model="dataForm.userEmail" @change="changekeyword" placeholder="用户邮箱" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-select v-model="dataForm.fulfillment"  @change="changeFulStatus" placeholder="请选择">
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <!-- <el-button @click="getDataList()">查询</el-button> -->
        <el-button type="primary" @click="getNewsData()">获取最新数据</el-button>
        <!-- <el-button v-if="isAuth('mall:mallorder:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button> -->
        <el-button v-if="isAuth('mall:mallorder:delete')" type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量删除</el-button>
      </el-form-item>
    </el-form>
    <el-table
      :data="dataList"
      border
      v-loading="dataListLoading"
      @row-click="rowclick"
      @selection-change="selectionChangeHandle"
      style="width: 100%;">
      <el-table-column
        type="selection"
        header-align="center"
        align="center"
        width="50">
      </el-table-column>
      <el-table-column
        prop="orderNumber"
        header-align="center"
        align="center"
        label="订单编号">
      </el-table-column>
      <el-table-column
        prop="flowerPicture"
        header-align="center"
        align="center"
        label="商品图片">
        <template slot-scope="scope">
          <div slot="reference" class="name-wrapper">
            <el-upload
              :action="url"
              :before-upload="beforeUploadHandle"
              :on-success="successHandle"
              :show-file-list="false"
              multiple
              class="flower_img"
              style="text-align: center;width: 120px; height: 100px;margin-left: 50%;transform: translateX(-50%);">
              <img :src="scope.row.flowerPicture"  class="flower_img" style="width: 120px; height: 100px" >
            </el-upload>
              <!-- <el-tag v-if="scope.row.flowerPicture==null || scope.row.flowerPicture==''" type="success">暂无图片</el-tag>
              <el-image
                v-else
                style="width: 120px; height: 100px"
                class="flower_img"
                :src="scope.row.flowerPicture"
                fit="scale-down"></el-image> -->
            </div>
        </template>
      </el-table-column>
      <el-table-column
        prop="orderStatus"
        header-align="center"
        align="center"
        width="200"
        label="订单状态">
        <template slot-scope="scope">
          <div slot="orderStatus" class="name-wrapper">
            <el-select v-model="scope.row.orderStatus" @change="changeOrderStatus(scope.row)" placeholder="请选择订单状态" >
              <el-option
                v-for="item in statusList"
                :key="item.id"
                :label="item.name"
                :value="item.name">
              </el-option>
            </el-select>  
          </div>
        </template>
      </el-table-column>
     
      <el-table-column
        prop="productName"
        header-align="center"
        align="center"
        label="商品名称">
      </el-table-column>
      <!-- <el-table-column
        prop="tags"
        header-align="center"
        align="center"
        label="标签，逗号分隔">
      </el-table-column>
      <el-table-column
        prop="firstName"
        header-align="center"
        align="center"
        label="客户名（姓）">
      </el-table-column>
      <el-table-column
        prop="lastName"
        header-align="center"
        align="center"
        label="客户名（名）">
      </el-table-column>
       <el-table-column
        prop="userEmail"
        header-align="center"
        align="center"
        label="客户邮箱">
      </el-table-column>
      <el-table-column
        prop="city"
        header-align="center"
        align="center"
        label="所在城市">
      </el-table-column>
      </el-table-column> -->
      <el-table-column
        prop="createDate"
        header-align="center"
        align="center"
        label="创建时间">
      </el-table-column>
      <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        width="150"
        label="操作">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="addOrUpdateHandle(scope.row.id)">修改</el-button>
          <el-button type="text" size="small" @click="deleteHandle(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="sizeChangeHandle"
      @current-change="currentChangeHandle"
      :current-page="pageIndex"
      :page-sizes="[10, 20, 50, 100]"
      :page-size="pageSize"
      :total="totalPage"
      layout="total, sizes, prev, pager, next, jumper">
    </el-pagination>
    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>
  </div>
</template>

<script>
import AddOrUpdate from "./mallorder-add-or-update";
export default {
  data() {
    return {
      dataForm: {
        key: "",
        orderNumber: "",
        userEmail: "",
        fulfillment: "1",
      },
      current_row:"",
      options: [
        { label: "未完成", value: "1" },
        { label: "已完成", value: "2" },
        { label: "全部", value: "0" },
      ],
      url: "",
      statusList: [],
      dataList: [],
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      dataListLoading: false,
      dataListSelections: [],
      addOrUpdateVisible: false,
    };
  },
  components: {
    AddOrUpdate,
  },
  activated() {
    this.initStatusList();
    this.getDataList();
  },
  methods: {
    // 获取数据列表
    getDataList() {
      this.dataListLoading = true;
      this.$http({
        url: this.$http.adornUrl("/mall/mallorder/list"),
        method: "get",
        params: this.$http.adornParams({
          page: this.pageIndex,
          limit: this.pageSize,
          orderNumber: this.dataForm.orderNumber,
          userEmail: this.dataForm.userEmail,
          fulfillment: this.dataForm.fulfillment,
        }),
      }).then(({ data }) => {
        if (data && data.code === 0) {
          this.dataList = data.page.list;
          this.totalPage = data.page.totalCount;
        } else {
          this.dataList = [];
          this.totalPage = 0;
        }
        this.dataListLoading = false;
      });
    },
    //初始化订单状态列表数据
    initStatusList() {
      this.$http({
        url: this.$http.adornUrl(`/mall/mallorder/statusList`),
        method: "get",
        params: this.$http.adornParams(),
      }).then(({ data }) => {
        if (data && data.code === 0) {
          // console.log(data.data);
          this.statusList = data.data;
        }
      });
    },
    //获取最新数据
    getNewsData() {
      this.dataListLoading = true;
      this.$http({
        url: this.$http.adornUrl("/mall/mallorder/getNewsData"),
        method: "get",
        params: this.$http.adornParams({
          page: this.pageIndex,
          limit: this.pageSize,
          orderNumber: "",
          userEmail: "",
          fulfillment: this.dataForm.fulfillment,
        }),
      }).then(({ data }) => {
        if (data && data.code === 0) {
          this.dataList = data.page.list;
          this.totalPage = data.page.totalCount;
        } else {
          this.dataList = [];
          this.totalPage = 0;
        }
        this.dataListLoading = false;
      });
    },
    changeFulStatus(){
      this.getDataList();
    },
    changekeyword(){
      this.getDataList();
    },
    changeOrderStatus(row) {
      // console.log(row);
      this.$http({
        url: this.$http.adornUrl("/mall/mallorder/updateOrderStatus"),
        method: "post",
        data: this.$http.adornData({
          id: row.id || undefined,
          orderStatus: row.orderStatus,
        }),
      }).then(({ data }) => {
        if (data && data.code === 0) {
          this.$message({
            message: "操作成功",
            type: "success",
            duration: 1500,
            onClose: () => {
              // this.visible = false
              // this.$emit('refreshDataList')
            },
          });
        } else {
          this.$message.error(data.msg);
        }
      });
    },
    rowclick(row, column, event){
      // console.log(row);
      this.current_row=row;
    },
    // 上传之前
    beforeUploadHandle(file) {
      if (
        file.type !== "image/jpg" &&
        file.type !== "image/jpeg" &&
        file.type !== "image/png" &&
        file.type !== "image/gif"
      ) {
        this.$message.error("只支持jpg、png、gif格式的图片！");
        return false;
      }
      // console.log("文件");
      this.uploadingHandle(file);
    },
    // 上传图片到服务器
    uploadingHandle(file) {
      var that = this;
      var params = new FormData();
      params.append("file", file);
      params.append("id", that.current_row.id);
      this.$http({
        url: this.$http.adornUrl(`/mall/mallorder/updateOrderImages`),
        method: "post",
        data: params,
      }).then(({ data }) => {
        if (data && data.code === 0) {
          that.getDataList();
          this.$message({
            message: "操作成功",
            type: "success",
            duration: 1500
          });
        }
      });
    },
    // 上传成功
    successHandle(response, file, fileList) {
      this.fileList = fileList;
      this.successNum++;
      if (response && response.code === 0) {
        if (this.num === this.successNum) {
          this.$confirm("操作成功, 是否继续操作?", "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning",
          }).catch(() => {
            // this.upload_visible = false
          });
        }
      } else {
        this.$message.error(response.msg);
      }
    },
    // 每页数
    sizeChangeHandle(val) {
      this.pageSize = val;
      this.pageIndex = 1;
      this.getDataList();
    },
    // 当前页
    currentChangeHandle(val) {
      this.pageIndex = val;
      this.getDataList();
    },
    // 多选
    selectionChangeHandle(val) {
      this.dataListSelections = val;
    },
    // 新增 / 修改
    addOrUpdateHandle(id) {
      this.addOrUpdateVisible = true;
      this.$nextTick(() => {
        this.$refs.addOrUpdate.init(id);
      });
    },
    // 删除
    deleteHandle(id) {
      var ids = id
        ? [id]
        : this.dataListSelections.map((item) => {
            return item.id;
          });
      this.$confirm(
        `确定对[id=${ids.join(",")}]进行[${id ? "删除" : "批量删除"}]操作?`,
        "提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      ).then(() => {
        this.$http({
          url: this.$http.adornUrl("/mall/mallorder/delete"),
          method: "post",
          data: this.$http.adornData(ids, false),
        }).then(({ data }) => {
          if (data && data.code === 0) {
            this.$message({
              message: "操作成功",
              type: "success",
              duration: 1500,
              onClose: () => {
                this.getDataList();
              },
            });
          } else {
            this.$message.error(data.msg);
          }
        });
      });
    },
  },
};
</script>
<style>
.flower_img img {
  width: 100%;
  height: 100%;
}
</style>