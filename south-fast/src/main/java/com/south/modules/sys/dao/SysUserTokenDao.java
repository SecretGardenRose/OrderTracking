/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * http://www.southiu.cn
 *
 * 版权所有，侵权必究！
 */

package com.south.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.south.modules.sys.entity.SysUserTokenEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统用户Token
 *
 * @author Mark sunlightcs@gmail.com
 */
@Mapper
public interface SysUserTokenDao extends BaseMapper<SysUserTokenEntity> {

    SysUserTokenEntity queryByToken(String token);
	
}
