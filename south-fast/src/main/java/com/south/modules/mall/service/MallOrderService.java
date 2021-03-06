package com.south.modules.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.south.common.utils.PageUtils;
import com.south.modules.mall.OrderStatusVo;
import com.south.modules.mall.controller.MallOrderController;
import com.south.modules.mall.entity.MallOrderEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.List;
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
     *导入第三方订单数据
     * @return
     */
    void importOrder();

    /**
     * 订单状态列表
     */
    List<OrderStatusVo> getStatusList();

    /**
     * 获取用户需要查询的数据(通过邮箱与订单号查询)
     * @param email
     * @param orderNo
     * @return
     */
    MallOrderEntity findOrder(String email, String orderNo);

    /**
     * 改变订单状态
     * @param mallOrder
     */
    void updateOrderStatus(MallOrderEntity mallOrder);

    /**
     * 图片上传
     * @param request
     * @param file
     * @return
     */
    String uploadimg(HttpServletRequest request, MultipartFile file);

    /**
     * 改变订单图片
     * @param request
     * @param file
     * @param id
     * @return
     */
    String updateOrderImages(HttpServletRequest request, MultipartFile file, String id);

    /**
     * 订单筛选列表
     * @param params
     * @return
     */
    PageUtils listForSelect(Map<String, Object> params);
}

