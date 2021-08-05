/**
 * Copyright (c) 2016-2021 南行开源 All rights reserved.
 *
 * http://www.southiu.cn
 *
 * 版权所有，侵权必究！
 */

package com.south.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.south.common.utils.R;
import com.south.modules.sys.entity.SysUserTokenEntity;

/**
 * 用户Token
 *
 * @author Mr.Tang 15902072436@163.com
 */
public interface SysUserTokenService extends IService<SysUserTokenEntity> {

	/**
	 * 生成token
	 * @param userId  用户ID
	 */
	R createToken(long userId);

	/**
	 * 退出，修改token值
	 * @param userId  用户ID
	 */
	void logout(long userId);

}
