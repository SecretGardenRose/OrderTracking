package com.south.modules.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.south.common.utils.PageUtils;
import com.south.modules.mall.entity.MallOrderEntity;

import java.util.Map;

/**
 * 第三方订单
 *
 * @author Mr.Tang
 * @email 15902072436@163.com
 * @date 2021-08-04 20:51:10
 */
public interface MallOrderService extends IService<MallOrderEntity> {

    /**
     * 数据分页查询
     * @param params
     * @return
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 第三方订单数据导入
     */
    void importOrder();
}

