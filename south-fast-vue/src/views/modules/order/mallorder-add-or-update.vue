<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="120px">
    <el-form-item label="订单编号" prop="orderNumber">
      <el-input v-model="dataForm.orderNumber" placeholder="订单编号"></el-input>
    </el-form-item>
    
    <el-form-item label="客户邮箱" prop="userEmail">
      <el-input v-model="dataForm.userEmail" placeholder="客户邮箱"></el-input>
    </el-form-item>
    <el-form-item label="客户电话" prop="userPhone">
      <el-input v-model="dataForm.userPhone" placeholder="客户电话"></el-input>
    </el-form-item>
    <el-form-item label="订单状态" prop="orderStatus">
      <el-input v-model="dataForm.orderStatus" placeholder="订单状态"></el-input>
    </el-form-item>
    <el-form-item label="客户名（姓）" prop="firstName">
      <el-input v-model="dataForm.firstName" placeholder="客户名（姓）"></el-input>
    </el-form-item>
    <el-form-item label="客户名（名）" prop="lastName">
      <el-input v-model="dataForm.lastName" placeholder="客户名（名）"></el-input>
    </el-form-item>
    <el-form-item label="订单总额" prop="totalPrice">
      <el-input v-model="dataForm.totalPrice" placeholder="订单总额"></el-input>
    </el-form-item>
    <el-form-item label="备注信息" >
      <el-input type="textarea"  :autosize="{ minRows: 2, maxRows: 8}" v-model="dataForm.note" placeholder="备注信息"></el-input>
    </el-form-item>
    
    <el-form-item label="商品图片" prop="flowerPicture">
      <!-- <el-input v-model="dataForm.flowerPicture" placeholder="商品图片"></el-input> -->
      <el-upload
          drag
          :action="url"
          :before-upload="beforeUploadHandle"
          :on-success="successHandle"
          multiple
          :file-list="fileList"
          style="text-align: center;">
          <i class="el-icon-upload"></i>
          <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
          <div class="el-upload__tip" slot="tip">只支持jpg、png、gif格式的图片！</div>
        </el-upload>
    </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        url: '',
        num: 0,
        successNum: 0,
        fileList: [],

        visible: false,
        dataForm: {
          id: 0,
          orderNumber: '',
          flowerPicture: '',
          userEmail: '',
          userPhone: '',
          orderStatus: '',
          firstName: '',
          lastName: '',
          note: '',
          totalPrice: ''
        },
        dataRule: {
          orderNumber: [
            { required: true, message: '订单编号不能为空', trigger: 'blur' }
          ],
          flowerPicture: [
            { required: true, message: '商品图片不能为空', trigger: 'blur' }
          ],
          userEmail: [
            { required: true, message: '客户邮箱不能为空', trigger: 'blur' }
          ],
          userPhone: [
            { required: true, message: '客户电话不能为空', trigger: 'blur' }
          ],
          orderStatus: [
            { required: true, message: '订单状态不能为空', trigger: 'blur' }
          ],
          firstName: [
            { required: true, message: '客户名（姓）不能为空', trigger: 'blur' }
          ],
          lastName: [
            { required: true, message: '客户名（名）不能为空', trigger: 'blur' }
          ],
          note: [
            { required: true, message: '备注信息不能为空', trigger: 'blur' }
          ],
          totalPrice: [
            { required: true, message: '订单总额不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        // this.url = this.$http.adornUrl(`/sys/oss/upload?token=${this.$cookie.get('token')}`)

        this.dataForm.id = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/mall/mallorder/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.orderNumber = data.mallOrder.orderNumber
                this.dataForm.flowerPicture = data.mallOrder.flowerPicture
                this.dataForm.userEmail = data.mallOrder.userEmail
                this.dataForm.userPhone = data.mallOrder.userPhone
                this.dataForm.orderStatus = data.mallOrder.orderStatus
                this.dataForm.firstName = data.mallOrder.firstName
                this.dataForm.lastName = data.mallOrder.lastName
                this.dataForm.note = data.mallOrder.note
                this.dataForm.totalPrice = data.mallOrder.totalPrice
              }
            })
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/mall/mallorder/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'orderNumber': this.dataForm.orderNumber,
                'flowerPicture': this.dataForm.flowerPicture,
                'userEmail': this.dataForm.userEmail,
                'userPhone': this.dataForm.userPhone,
                'orderStatus': this.dataForm.orderStatus,
                'firstName': this.dataForm.firstName,
                'lastName': this.dataForm.lastName,
                'note': this.dataForm.note,
                'totalPrice': this.dataForm.totalPrice
              })
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.$message({
                  message: '操作成功',
                  type: 'success',
                  duration: 1500,
                  onClose: () => {
                    this.visible = false
                    this.$emit('refreshDataList')
                  }
                })
              } else {
                this.$message.error(data.msg)
              }
            })
          }
        })
      },
      // 上传之前
      beforeUploadHandle (file) {
        if (file.type !== 'image/jpg' && file.type !== 'image/jpeg' && file.type !== 'image/png' && file.type !== 'image/gif') {
          this.$message.error('只支持jpg、png、gif格式的图片！')
          return false
        }
        this.num++
      },
      // 上传成功
      successHandle (response, file, fileList) {
        this.fileList = fileList
        this.successNum++
        if (response && response.code === 0) {
          if (this.num === this.successNum) {
            this.$confirm('操作成功, 是否继续操作?', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).catch(() => {
              // this.upload_visible = false
            })
          }
        } else {
          this.$message.error(response.msg)
        }
      }
    }
  }
</script>
