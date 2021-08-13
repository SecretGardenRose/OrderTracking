package com.south.modules.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.south.common.utils.Constant;
import com.south.common.utils.DateUtils;
import com.south.modules.mall.entity.MallOrderEntity;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.south.common.utils.PageUtils;
import com.south.common.utils.Query;

import com.south.modules.mall.dao.AccessRecordDao;
import com.south.modules.mall.entity.AccessRecordEntity;
import com.south.modules.mall.service.AccessRecordService;


@Service("accessRecordService")
public class AccessRecordServiceImpl extends ServiceImpl<AccessRecordDao, AccessRecordEntity> implements AccessRecordService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        LambdaQueryWrapper<AccessRecordEntity> wrapper = new LambdaQueryWrapper<>();
        if(StringUtils.isNotEmpty(params.get("orderNumber").toString())){
            wrapper.eq(AccessRecordEntity::getOrderNumber,params.get("orderNumber").toString());
        }
        if(StringUtils.isNotEmpty(params.get("userEmail").toString())){
            wrapper.like(AccessRecordEntity::getUserEmail,params.get("userEmail").toString());
        }
        wrapper.orderByDesc(AccessRecordEntity::getCreateDate);

        IPage<AccessRecordEntity> page = this.page(
                new Query<AccessRecordEntity>().getPage(params),
                wrapper
        );
        //统计用户当天访问次数
        List<AccessRecordEntity> records = page.getRecords();
        if(CollectionUtils.isNotEmpty(records)){
            for (AccessRecordEntity record : records) {
                AccessRecordEntity one =this.baseMapper.getCurrentCountByOrder(record.getOrderNumber(), DateUtils.format(record.getCreateDate()));
                if (one != null) {
                    record.setViewTotal(one.getViewTotal());
                }
            }
            page.setRecords(records);
        }


        return new PageUtils(page);
    }

    @Override
    public void saveinfo(AccessRecordEntity accessRecord) {
        accessRecord.setCreateDate(new Date());
        accessRecord.setUpdateDate(new Date());
        accessRecord.setDeleteFlag(Constant.DELETE_FALG.DATA_OK.getValue());
        this.save(accessRecord);
    }

}