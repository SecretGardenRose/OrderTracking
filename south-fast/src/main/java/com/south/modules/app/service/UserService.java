/**
 * Copyright (c) 2016-2021 南行开源 All rights reserved.
 *
 * http://www.southiu.cn
 *
 * 版权所有，侵权必究！
 */

package com.south.modules.app.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.south.modules.app.entity.UserEntity;
import com.south.modules.app.form.LoginForm;

/**
 * 用户
 *
 * @author Mr.Tang 15902072436@163.com
 */
public interface UserService extends IService<UserEntity> {

	UserEntity queryByMobile(String mobile);

	/**
	 * 用户登录
	 * @param form    登录表单
	 * @return        返回用户ID
	 */
	long login(LoginForm form);
}
