package com.south.modules.mall.dao;

import com.south.modules.mall.entity.AccessRecordEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户询问记录
 * 
 * @author Mr.Tang
 * @email 15902072436@163.com
 * @date 2021-08-11 20:22:19
 */
@Mapper
public interface AccessRecordDao extends BaseMapper<AccessRecordEntity> {

    AccessRecordEntity getCurrentCountByOrder(@Param("orderNumber") String orderNumber, @Param("createDate") String createDate);
}
