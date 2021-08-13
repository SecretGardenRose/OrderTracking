package com.south.modules.mall.controller;

import java.util.Arrays;
import java.util.Map;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.south.modules.mall.entity.AccessRecordEntity;
import com.south.modules.mall.service.AccessRecordService;
import com.south.common.utils.PageUtils;
import com.south.common.utils.R;



/**
 * 用户询问记录
 *
 * @author Mr.Tang
 * @email 15902072436@163.com
 * @date 2021-08-11 20:22:19
 */
@RestController
@RequestMapping("mall/accessrecord")
@Api(value = "用户询问记录",tags = {"用户询问记录"})
public class AccessRecordController {
    @Autowired
    private AccessRecordService accessRecordService;

    /**
     * 列表
     */
    @ApiOperation("列表")
    @GetMapping("/list")
//    @RequiresPermissions("mall:accessrecord:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = accessRecordService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @ApiOperation("信息")
    @GetMapping("/info/{id}")
//    @RequiresPermissions("mall:accessrecord:info")
    public R info(@PathVariable("id") Integer id){
		AccessRecordEntity accessRecord = accessRecordService.getById(id);

        return R.ok().put("accessRecord", accessRecord);
    }

    /**
     * 保存
     */
    @ApiOperation("保存")
    @PostMapping("/save")
//    @RequiresPermissions("mall:accessrecord:save")
    public R save(@RequestBody AccessRecordEntity accessRecord){
		accessRecordService.saveinfo(accessRecord);

        return R.ok();
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @PostMapping("/update")
//    @RequiresPermissions("mall:accessrecord:update")
    public R update(@RequestBody AccessRecordEntity accessRecord){
		accessRecordService.updateById(accessRecord);

        return R.ok();
    }

    /**
     * 删除
     */
    @ApiOperation("删除")
    @GetMapping("/delete")
//    @RequiresPermissions("mall:accessrecord:delete")
    public R delete(@RequestBody Integer[] ids){
		accessRecordService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
