<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="订单编号" prop="orderNumber">
      <el-input v-model="dataForm.orderNumber" placeholder="订单编号"></el-input>
    </el-form-item>
    <el-form-item label="客户邮箱" prop="userEmail">
      <el-input v-model="dataForm.userEmail" placeholder="客户邮箱"></el-input>
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
          userEmail: '',
          createDate: '',
          updateDate: '',
          deleteFlag: ''
        },
        dataRule: {
          orderNumber: [
            { required: true, message: '订单编号不能为空', trigger: 'blur' }
          ],
          userEmail: [
            { required: true, message: '客户邮箱不能为空', trigger: 'blur' }
          ],
          createDate: [
            { required: true, message: '创建时间不能为空', trigger: 'blur' }
          ],
          updateDate: [
            { required: true, message: '修改时间不能为空', trigger: 'blur' }
          ],
          deleteFlag: [
            { required: true, message: '逻辑删除标识不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/mall/accessrecord/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.orderNumber = data.accessRecord.orderNumber
                this.dataForm.userEmail = data.accessRecord.userEmail
                this.dataForm.createDate = data.accessRecord.createDate
                this.dataForm.updateDate = data.accessRecord.updateDate
                this.dataForm.deleteFlag = data.accessRecord.deleteFlag
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
              url: this.$http.adornUrl(`/mall/accessrecord/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'orderNumber': this.dataForm.orderNumber,
                'userEmail': this.dataForm.userEmail,
                'createDate': this.dataForm.createDate,
                'updateDate': this.dataForm.updateDate,
                'deleteFlag': this.dataForm.deleteFlag
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
