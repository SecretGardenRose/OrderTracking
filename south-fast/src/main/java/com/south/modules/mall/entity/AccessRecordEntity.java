package com.south.modules.mall.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 用户询问记录
 * 
 * @author Mr.Tang
 * @email 15902072436@163.com
 * @date 2021-08-11 20:22:19
 */
@Data
@TableName("access_record")
public class AccessRecordEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	@TableId
	private Integer id;
	/**
	 * 订单编号
	 */
	private String orderNumber;
	/**
	 * 客户邮箱
	 */
	private String userEmail;
	/**
	 * 创建时间
	 */
	private Date createDate;
	/**
	 * 修改时间
	 */
	private Date updateDate;
	/**
	 * 逻辑删除标识
	 */
	private Integer deleteFlag;

	@TableField(exist = false)
	private Integer viewTotal;

}
