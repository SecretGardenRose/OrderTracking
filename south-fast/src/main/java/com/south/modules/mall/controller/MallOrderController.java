package com.south.modules.mall.controller;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

import com.south.common.exception.BizCodeEnume;
import com.south.common.exception.SOException;
import com.south.common.utils.Constant;
import com.south.modules.mall.OrderStatusVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.south.modules.mall.entity.MallOrderEntity;
import com.south.modules.mall.service.MallOrderService;
import com.south.common.utils.PageUtils;
import com.south.common.utils.R;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;


/**
 * 第三方订单
 *
 * @author Mr.Tang
 * @email 15902072436@163.com
 * @date 2021-08-04 20:51:10
 */
@RestController
@RequestMapping("mall/mallorder")
@Api(value = "第三方订单",tags = {"第三方订单"})
public class MallOrderController {
    @Autowired
    private MallOrderService mallOrderService;

    /**
     * 列表
     */
    @ApiOperation("列表")
    @GetMapping("/list")
//    @RequiresPermissions("mall:mallorder:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = mallOrderService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 订单筛选列表
     */
    @ApiOperation("订单筛选列表")
    @GetMapping("/listForSelect")
    public R listForSelect(@RequestParam Map<String, Object> params){
        PageUtils page = mallOrderService.listForSelect(params);

        return R.ok().put("page", page);
    }

    /**
     * 订单状态列表
     */
    @ApiOperation("订单状态列表")
    @GetMapping("/statusList")
    public R statusList(){
        List<OrderStatusVo> statusList = mallOrderService.getStatusList();

        return R.ok().put("data", statusList);
    }

    /**
     * 改变订单状态
     */
    @ApiOperation("改变订单状态")
    @PostMapping("/updateOrderStatus")
    public R updateOrderStatus(@RequestBody MallOrderEntity mallOrder){
        mallOrderService.updateOrderStatus(mallOrder);

        return R.ok();
    }
    /**
     * 改变订单图片
     */
    @ApiOperation("改变订单图片")
    @PostMapping("/updateOrderImages")
    public R updateOrderImages(HttpServletRequest request, @RequestParam("file") MultipartFile file,@RequestParam("id") String id){
        String uri=mallOrderService.updateOrderImages(request,file,id);

        return R.ok(uri);
    }

    /**
     * 获取最新数据
     */
    @ApiOperation("获取最新数据")
    @GetMapping("/getNewsData")
    public R getNewsData(@RequestParam Map<String, Object> params){
        mallOrderService.importOrder();
        PageUtils page = mallOrderService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**导入第三方订单数据
     *
     * @param email
     * @param orderNo
     * @return
     */
    @ApiOperation("导入第三方订单数据")
    @PostMapping("/importOrder")
    public R importOrder(@RequestParam("email") String email,@RequestParam("orderNo") String orderNo){
        // 1.通过第三方数据导入
        mallOrderService.importOrder();

        // 2.获取用户需要查询的数据(通过邮箱与订单号查询)
        MallOrderEntity order=mallOrderService.findOrder( email, orderNo);
        return R.ok().put("data",order);
    }

    /**
     * 图片上传
     * @param request
     * @param file
     * @return
     */
    @ApiOperation("商品图片上传")
    @PostMapping("upload/uploadOrderImage")
    public R uploadOrderImage(HttpServletRequest request, @RequestParam("file") MultipartFile file){
        String uri=mallOrderService.uploadimg(request,file);
        return R.ok(uri);
    }

    /**
     * 信息
     */
    @ApiOperation("信息")
    @GetMapping("/info/{id}")
//    @RequiresPermissions("mall:mallorder:info")
    public R info(@PathVariable("id") Integer id){
		MallOrderEntity mallOrder = mallOrderService.getById(id);

        return R.ok().put("mallOrder", mallOrder);
    }

    /**
     * 保存
     */
    @ApiOperation("保存")
    @PostMapping("/save")
//    @RequiresPermissions("mall:mallorder:save")
    public R save(@RequestBody MallOrderEntity mallOrder){
        mallOrder.setDeleteFlag(Constant.DELETE_FALG.DATA_OK.getValue());
        mallOrder.setCreateDate(new Date());
        mallOrder.setUpdateDate(new Date());

        if (!mallOrderService.save(mallOrder)) {
            throw new  SOException(BizCodeEnume.INSERT_DATA_ERROR);
        }

        return R.ok();
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @PostMapping("/update")
//    @RequiresPermissions("mall:mallorder:update")
    public R update(@RequestBody MallOrderEntity mallOrder){
        mallOrder.setUpdateDate(new Date());
		mallOrderService.updateById(mallOrder);

        return R.ok();
    }

    /**
     * 删除
     */
    @ApiOperation("删除")
    @PostMapping("/delete")
//    @RequiresPermissions("mall:mallorder:delete")
    public R delete(@RequestBody Integer[] ids){
		mallOrderService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
