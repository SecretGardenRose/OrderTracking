package com.south.modules.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.south.common.utils.PageUtils;
import com.south.modules.mall.entity.AccessRecordEntity;

import java.util.Map;

/**
 * 用户询问记录
 *
 * @author Mr.Tang
 * @email 15902072436@163.com
 * @date 2021-08-11 20:22:19
 */
public interface AccessRecordService extends IService<AccessRecordEntity> {

    PageUtils queryPage(Map<String, Object> params);


    void saveinfo(AccessRecordEntity accessRecord);
}

