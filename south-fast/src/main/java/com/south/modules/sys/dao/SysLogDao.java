/**
 * Copyright (c) 2016-2021 南行开源 All rights reserved.
 *
 * http://www.southiu.cn
 *
 * 版权所有，侵权必究！
 */

package com.south.modules.sys.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.south.modules.sys.entity.SysLogEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统日志
 *
 * @author Mr.Tang 15902072436@163.com
 */
@Mapper
public interface SysLogDao extends BaseMapper<SysLogEntity> {
	
}
