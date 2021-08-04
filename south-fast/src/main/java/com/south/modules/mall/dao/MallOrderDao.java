package com.south.modules.mall.dao;

import com.south.modules.mall.entity.MallOrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 第三方订单
 * 
 * @author Mr.Tang
 * @email 15902072436@163.com
 * @date 2021-08-04 20:51:10
 */
@Mapper
public interface MallOrderDao extends BaseMapper<MallOrderEntity> {
	
}
