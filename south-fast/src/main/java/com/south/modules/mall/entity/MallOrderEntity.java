package com.south.modules.mall.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 第三方订单
 * 
 * @author Mr.Tang
 * @email 15902072436@163.com
 * @date 2021-08-04 20:51:10
 */
@Data
@TableName("mall_order")
public class MallOrderEntity implements Serializable {
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
	 * 商品图片
	 */
	private String flowerPicture;
	/**
	 * 客户邮箱
	 */
	private String userEmail;
	/**
	 * 客户电话
	 */
	private String userPhone;
	/**
	 * 订单状态
	 */
	private String orderStatus;
	/**
	 * 客户名（姓）
	 */
	private String firstName;
	/**
	 * 客户名（名）
	 */
	private String lastName;
	/**
	 * 备注信息
	 */
	private String note;
	/**
	 * 用户地址
	 */
	private String address;
	/**
	 * 用户邮编
	 */
	private String userZip;
	/**
	 * 所在城市
	 */
	private String city;
	/**
	 * 订单总额
	 */
	private BigDecimal totalPrice;
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
	/**
	 * 标签，逗号分隔
	 */
	private String tags;
	/**
	 * sgr_cal
	 */
	private String sgrCal;
	/**
	 * sgr_cal_value
	 */
	private String sgrCalValue;
	/**
	 * sgr_inst
	 */
	private String sgrInst;
	/**
	 * sgr_inst_value
	 */
	private String sgrInstValue;
	/**
	 * product_name
	 */
	private String productName;
	/**
	 * fulfillment_status
	 */
	private String fulfillmentStatus;

}
