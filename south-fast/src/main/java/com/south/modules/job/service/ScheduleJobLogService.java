/**
 * Copyright (c) 2016-2021 南行开源 All rights reserved.
 *
 * http://www.southiu.cn
 *
 * 版权所有，侵权必究！
 */

package com.south.modules.job.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.south.common.utils.PageUtils;
import com.south.modules.job.entity.ScheduleJobLogEntity;

import java.util.Map;

/**
 * 定时任务日志
 *
 * @author Mr.Tang 15902072436@163.com
 */
public interface ScheduleJobLogService extends IService<ScheduleJobLogEntity> {

	PageUtils queryPage(Map<String, Object> params);
	
}
