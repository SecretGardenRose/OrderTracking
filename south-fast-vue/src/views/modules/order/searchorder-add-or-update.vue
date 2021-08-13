<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="订单编号" prop="orderNumber">
      <el-input v-model="dataForm.orderNumber" placeholder="订单编号"></el-input>
    </el-form-item>
    <el-form-item label="商品图片" prop="flowerPicture">
      <el-input v-model="dataForm.flowerPicture" placeholder="商品图片"></el-input>
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
    <el-form-item label="用户邮编" prop="userZip">
      <el-input v-model="dataForm.userZip" placeholder="用户邮编"></el-input>
    </el-form-item>
    <el-form-item label="备注信息" prop="note">
      <el-input v-model="dataForm.note" placeholder="备注信息"></el-input>
    </el-form-item>
    <el-form-item label="所在城市" prop="city">
      <el-input v-model="dataForm.city" placeholder="所在城市"></el-input>
    </el-form-item>
    <el-form-item label="用户地址" prop="address">
      <el-input v-model="dataForm.address" placeholder="用户地址"></el-input>
    </el-form-item>
    <el-form-item label="订单总额" prop="totalPrice">
      <el-input v-model="dataForm.totalPrice" placeholder="订单总额"></el-input>
    </el-form-item>
    <el-form-item label="创建时间" prop="createDate">
      <el-input v-model="dataForm.createDate" placeholder="创建时间"></el-input>
    </el-form-item>
    <el-form-item label="修改时间" prop="updateDate">
      <el-input v-model="dataForm.updateDate" placeholder="修改时间"></el-input>
    </el-form-item>
    <el-form-item label="逻辑删除标识" prop="deleteFlag">
      <el-input v-model="dataForm.deleteFlag" placeholder="逻辑删除标识"></el-input>
    </el-form-item>
    <el-form-item label="标签，逗号分隔" prop="tags">
      <el-input v-model="dataForm.tags" placeholder="标签，逗号分隔"></el-input>
    </el-form-item>
    <el-form-item label="sgr_cal" prop="sgrCal">
      <el-input v-model="dataForm.sgrCal" placeholder="sgr_cal"></el-input>
    </el-form-item>
    <el-form-item label="" prop="sgrCalValue">
      <el-input v-model="dataForm.sgrCalValue" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="sgr_inst" prop="sgrInst">
      <el-input v-model="dataForm.sgrInst" placeholder="sgr_inst"></el-input>
    </el-form-item>
    <el-form-item label="" prop="sgrInstValue">
      <el-input v-model="dataForm.sgrInstValue" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="商品名称" prop="productName">
      <el-input v-model="dataForm.productName" placeholder="商品名称"></el-input>
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
          userZip: '',
          note: '',
          city: '',
          address: '',
          totalPrice: '',
          createDate: '',
          updateDate: '',
          deleteFlag: '',
          tags: '',
          sgrCal: '',
          sgrCalValue: '',
          sgrInst: '',
          sgrInstValue: '',
          productName: ''
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
          userZip: [
            { required: true, message: '用户邮编不能为空', trigger: 'blur' }
          ],
          note: [
            { required: true, message: '备注信息不能为空', trigger: 'blur' }
          ],
          city: [
            { required: true, message: '所在城市不能为空', trigger: 'blur' }
          ],
          address: [
            { required: true, message: '用户地址不能为空', trigger: 'blur' }
          ],
          totalPrice: [
            { required: true, message: '订单总额不能为空', trigger: 'blur' }
          ],
          createDate: [
            { required: true, message: '创建时间不能为空', trigger: 'blur' }
          ],
          updateDate: [
            { required: true, message: '修改时间不能为空', trigger: 'blur' }
          ],
          deleteFlag: [
            { required: true, message: '逻辑删除标识不能为空', trigger: 'blur' }
          ],
          tags: [
            { required: true, message: '标签，逗号分隔不能为空', trigger: 'blur' }
          ],
          sgrCal: [
            { required: true, message: 'sgr_cal不能为空', trigger: 'blur' }
          ],
          sgrCalValue: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          sgrInst: [
            { required: true, message: 'sgr_inst不能为空', trigger: 'blur' }
          ],
          sgrInstValue: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          productName: [
            { required: true, message: '商品名称不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
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
                this.dataForm.userZip = data.mallOrder.userZip
                this.dataForm.note = data.mallOrder.note
                this.dataForm.city = data.mallOrder.city
                this.dataForm.address = data.mallOrder.address
                this.dataForm.totalPrice = data.mallOrder.totalPrice
                this.dataForm.createDate = data.mallOrder.createDate
                this.dataForm.updateDate = data.mallOrder.updateDate
                this.dataForm.deleteFlag = data.mallOrder.deleteFlag
                this.dataForm.tags = data.mallOrder.tags
                this.dataForm.sgrCal = data.mallOrder.sgrCal
                this.dataForm.sgrCalValue = data.mallOrder.sgrCalValue
                this.dataForm.sgrInst = data.mallOrder.sgrInst
                this.dataForm.sgrInstValue = data.mallOrder.sgrInstValue
                this.dataForm.productName = data.mallOrder.productName
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
                'userZip': this.dataForm.userZip,
                'note': this.dataForm.note,
                'city': this.dataForm.city,
                'address': this.dataForm.address,
                'totalPrice': this.dataForm.totalPrice,
                'createDate': this.dataForm.createDate,
                'updateDate': this.dataForm.updateDate,
                'deleteFlag': this.dataForm.deleteFlag,
                'tags': this.dataForm.tags,
                'sgrCal': this.dataForm.sgrCal,
                'sgrCalValue': this.dataForm.sgrCalValue,
                'sgrInst': this.dataForm.sgrInst,
                'sgrInstValue': this.dataForm.sgrInstValue,
                'productName': this.dataForm.productName
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
      }
    }
  }
</script>
