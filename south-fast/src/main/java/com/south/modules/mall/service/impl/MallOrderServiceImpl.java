package com.south.modules.mall.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.south.common.exception.BizCodeEnume;
import com.south.common.exception.SOException;
import com.south.common.utils.*;
import com.south.common.utils.Constant.ORDER_STATUS;
import com.south.modules.mall.OrderStatusVo;
import lombok.SneakyThrows;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.south.modules.mall.dao.MallOrderDao;
import com.south.modules.mall.entity.MallOrderEntity;
import com.south.modules.mall.service.MallOrderService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;


@Service("mallOrderService")
public class MallOrderServiceImpl extends ServiceImpl<MallOrderDao, MallOrderEntity> implements MallOrderService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        LambdaQueryWrapper<MallOrderEntity> wrapper = new LambdaQueryWrapper<>();

        if(StringUtils.isNotEmpty(params.get("orderNumber").toString())){
            wrapper.eq(MallOrderEntity::getOrderNumber,params.get("orderNumber").toString());
        }
        if(StringUtils.isNotEmpty(params.get("userEmail").toString())){
            wrapper.like(MallOrderEntity::getUserEmail,params.get("userEmail").toString());
        }
        if(StringUtils.isNotEmpty(params.get("fulfillment").toString())){
            String ful=params.get("fulfillment").toString();
            if(ful.equals(Constant.OrderGetStatus.FUL.getValue())){//已完成
                wrapper.eq(MallOrderEntity::getFulfillmentStatus,"fulfilled");
            }else if(ful.equals(Constant.OrderGetStatus.UNFUL.getValue())){//未完成
                wrapper.eq(MallOrderEntity::getFulfillmentStatus,"");
            }else{//全部

            }

        }
        wrapper.orderByDesc(MallOrderEntity::getOrderNumber);
        IPage<MallOrderEntity> page = this.page(
                new Query<MallOrderEntity>().getPage(params),
                wrapper
        );

        return new PageUtils(page);
    }

    /**
     * 第三方订单数据导入
     */
    @SneakyThrows
    @Override
    public void importOrder() {

        // 1.获取数据进行数据导入
        Map<String, String> query = new HashMap<>();
        //https://love-florist.myshopify.com/admin/api/2021-04/orders.json?limit=10&status=any
        HttpResponse response = HttpUtils.doGet(Constant.ORDER_HOST_URL, Constant.ORDER_PATH_URL+"?limit=100&status=any", "get", new HashMap<>(), query);

        if (response.getStatusLine().getStatusCode() == 200) {
            //查询成功（最新所有订单数据）
            String json = EntityUtils.toString(response.getEntity());
            JSONObject jsonObject = JSON.parseObject(json);

            //解析order json数据列表
            String  order_json=jsonObject.getString("orders");
            JSONArray orderArray = JSON.parseArray(order_json);
            if(orderArray!=null && orderArray.size()>0){
                //通过转成list遍历得到数据
                for (Object obj : orderArray) {
                    JSONObject order = (JSONObject)obj;
                    //order_number
                    String order_number= order.get("order_number")==null?"":order.get("order_number").toString();
                    //user_email
                    String user_email=  order.get("email")==null?"":order.get("email").toString();
                    //user_phone
                    String user_phone=  order.get("phone")==null?"":order.get("phone").toString();
                    //total_price
//                    String total_price=  order.get("total_price")==null?"":order.get("total_price").toString();
                    //note
                    String note=  order.get("note")==null?"":order.get("note").toString();
                    //tags
                    String tags=  order.get("tags")==null?"":order.get("tags").toString();


                    //order_status 自定义状态流程
                    //customer
                    String customer_json=  order.get("shipping_address")==null?"":order.get("shipping_address").toString();
                    String first_name="";
                    String last_name="";

                    String city="";
                    String address="";
                    String zip="";
                    if(!StringUtils.isEmpty(customer_json)){
                        JSONObject customer = JSON.parseObject(customer_json);
                        if(customer!=null){
                            //first_name
                            first_name=  customer.get("first_name")==null?"":customer.get("first_name").toString();
                            //last_name
                            last_name=  customer.get("last_name")==null?"":customer.get("last_name").toString();
                            //city
                            city=  customer.get("province")==null?"":customer.get("province").toString();
                            //address
                            address=  customer.get("address1")==null?"":customer.get("address1").toString();
                            //zip
                            zip=  customer.get("zip")==null?"":customer.get("zip").toString();
                        }
                    }

                    //name
                    String line_items=  order.get("line_items")==null?"":order.get("line_items").toString();
                    String productName="";
                    String fulfillmentStatus="";
                    if(!StringUtils.isEmpty(line_items)){
                        JSONArray lineItems = JSON.parseArray(line_items);
                        if(lineItems!=null &&  lineItems.size()>0){
                            for (Object item : lineItems) {
                                JSONObject line = (JSONObject)item;
                                if(StringUtils.isEmpty(productName)){
                                    String p_name=  line.get("name")==null?"":line.get("name").toString();
                                    if(!p_name.trim().toLowerCase().equals("tip")){
                                        productName=p_name;
                                    }
                                }
                                if(StringUtils.isEmpty(fulfillmentStatus)){
                                    fulfillmentStatus=  line.get("fulfillment_status")==null?"":line.get("fulfillment_status").toString();
                                }
                            }
                        }
                    }

                    //sgr-cal
                    String note_attributes=  order.get("note_attributes")==null?"":order.get("note_attributes").toString();
                    String sgr_cal="";
                    String sgr_cal_val="";
                    String sgr_inst="";
                    String sgr_inst_val="";
                    if(!StringUtils.isEmpty(note_attributes)){
                        JSONArray attributes = JSON.parseArray(note_attributes);
                        if(attributes!=null &&  attributes.size()>0){
                            for (Object item : attributes) {
                                JSONObject line = (JSONObject)item;
                                String a_name=  line.get("name")==null?"":line.get("name").toString();
                                String a_value=  line.get("value")==null?"":line.get("value").toString();
                                if("sgr-cal".equals(a_name)){
                                    sgr_cal=a_name;
                                    sgr_cal_val=a_value;
                                }
                                if("sgr-inst".equals(a_name)){
                                    sgr_inst=a_name;
                                    sgr_inst_val=a_value;
                                }
                            }
                        }
                    }


                    //校验不存在的订单数据
                    MallOrderEntity orderEntity = this.getOne(new QueryWrapper<MallOrderEntity>()
                            .eq("order_number", order_number)
                            .last("limit 1"));
                    if(orderEntity==null){
                        orderEntity=new MallOrderEntity();
                        //把用户信息插入到数据库中
                        orderEntity.setOrderNumber(order_number);
                        orderEntity.setUserEmail(user_email);
                        orderEntity.setUserPhone(user_phone);
                        //默认图片地址
                        orderEntity.setFlowerPicture(Constant.DEFAULT_IMAGE);
                        //设置地址信息
                        orderEntity.setAddress(address);
                        orderEntity.setUserZip(zip);
                        orderEntity.setCity(city);

                        //name  && sgr  && tags
                        orderEntity.setTags(tags);
                        orderEntity.setProductName(productName);
                        orderEntity.setSgrCal(sgr_cal);
                        orderEntity.setSgrCalValue(sgr_cal_val);
                        orderEntity.setSgrInst(sgr_inst);
                        orderEntity.setSgrInstValue(sgr_inst_val);
                        orderEntity.setFulfillmentStatus(fulfillmentStatus);

                        //初始化订单流程
                        orderEntity.setOrderStatus(Constant.ORDER_STATUS.ORDER_RECEIVED.getName());
                        orderEntity.setFirstName(first_name);
                        orderEntity.setLastName(last_name);
                        orderEntity.setNote(note);
//                        BigDecimal total=new BigDecimal(total_price);
//                        orderEntity.setTotalPrice(total);
                        orderEntity.setCreateDate(new Date());
                        orderEntity.setUpdateDate(new Date());
                        orderEntity.setDeleteFlag(Constant.DELETE_FALG.DATA_OK.getValue());
                        if (!this.save(orderEntity)) {
                            throw new SOException(BizCodeEnume.IMPORT_DATA_ERROR);
                        }

                    }
                }
            }//无数据无需处理

        }else{
            //数据请求失败处理

        }
    }

    /**
     * 订单状态列表
     */
    @Override
    public List<OrderStatusVo> getStatusList() {
        //初始化订单流程状态数据
        List<OrderStatusVo> list=new ArrayList<>();
        for (ORDER_STATUS value : ORDER_STATUS.values()) {
            OrderStatusVo statusVo = new OrderStatusVo();
            statusVo.setId(value.getValue());
            statusVo.setName(value.getName());
            list.add(statusVo);
        }
        return list;
    }

    /**
     * 获取用户需要查询的数据(通过邮箱与订单号查询)
     * @param email
     * @param orderNo
     * @return
     */
    @Override
    public MallOrderEntity findOrder(String email, String orderNo) {
        MallOrderEntity order = super.getOne(new LambdaQueryWrapper<MallOrderEntity>()
                .eq(MallOrderEntity::getOrderNumber, orderNo)
                .eq(MallOrderEntity::getUserEmail, email));

        return order;
    }

    /**
     * 改变订单状态
     * @param mallOrder
     */
    @Override
    public void updateOrderStatus(MallOrderEntity mallOrder) {
        MallOrderEntity byId = this.getById(mallOrder.getId());
        byId.setOrderStatus(mallOrder.getOrderStatus());
        byId.setUpdateDate(new Date());
        if (!this.updateById(byId)) {
            throw new SOException(BizCodeEnume.UPDATE_DATA_ERROR);
        }
    }

    @Override
    public String uploadimg(HttpServletRequest request, MultipartFile file) {
        String fileName=file.getOriginalFilename();
        String suffixName=fileName.substring(fileName.lastIndexOf("."));
        //生成文件名称UUID
        UUID uuid=UUID.randomUUID();
        String newFileName=uuid.toString()+suffixName;
        //创建文件
        File fileDirectory=new File(Constant.FILE_UPLOAD_DIR);
        File destFile=new File(Constant.FILE_UPLOAD_DIR+newFileName);
        if(!fileDirectory.exists()){
            if(!fileDirectory.mkdir()){
                throw new SOException(BizCodeEnume.MKDIR_FAILED);
            }
        }
        try {
            file.transferTo(destFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return getHost(new URI(request.getRequestURL()+""))+"/south-fast/imgs/"+newFileName;
        } catch (URISyntaxException e) {
            throw new SOException(BizCodeEnume.UPLOAD_FAILED);
        }
    }

    /**
     * 改变订单图片
     * @param request
     * @param file
     * @param id
     * @return
     */
    @Override
    public String updateOrderImages(HttpServletRequest request, MultipartFile file, String id) {
        String uri = uploadimg(request, file);
        MallOrderEntity byId = this.getById(id);
        byId.setUpdateDate(new Date());
        byId.setFlowerPicture(uri);
        if (!this.updateById(byId)) {
            throw new SOException(BizCodeEnume.UPDATE_DATA_ERROR);
        }
        return null;
    }

    /**
     * 订单筛选列表
     * @param params
     * @return
     */
    @Override
    public PageUtils listForSelect(Map<String, Object> params) {
        LambdaQueryWrapper<MallOrderEntity> wrapper = new LambdaQueryWrapper<>();

        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH)+1;
        int day = now.get(Calendar.DAY_OF_MONTH);
        String orderTime=day+"/"+month;
        if(1==1){
            orderTime=day+"/"+month;
        }

        if(StringUtils.isNotEmpty(params.get("orderNumber").toString())){
            wrapper.eq(MallOrderEntity::getOrderNumber,params.get("orderNumber").toString());
        }
        if(StringUtils.isNotEmpty(params.get("userEmail").toString())){
            wrapper.like(MallOrderEntity::getUserEmail,params.get("userEmail").toString());
        }
        //day
        if(StringUtils.isNotEmpty(params.get("day").toString())){
            wrapper.like(MallOrderEntity::getTags,orderTime);
        }

        if(StringUtils.isNotEmpty(params.get("fulfillment").toString())){
            String ful=params.get("fulfillment").toString();
            if(ful.equals(Constant.OrderGetStatus.FUL.getValue())){//已完成
                wrapper.eq(MallOrderEntity::getFulfillmentStatus,"fulfilled");
            }else if(ful.equals(Constant.OrderGetStatus.UNFUL.getValue())){//未完成
                wrapper.eq(MallOrderEntity::getFulfillmentStatus,"");
            }else{//全部

            }

        }
        wrapper.orderByDesc(MallOrderEntity::getOrderNumber);
        IPage<MallOrderEntity> page = this.page(
                new Query<MallOrderEntity>().getPage(params),
                wrapper
        );

        return new PageUtils(page);
    }

    /**
     * 通过请求获取当前服务器地址
     * @param uri
     * @return
     */
    private URI getHost(URI uri){
        URI effectiveURI;
        try {
            effectiveURI=new URI(uri.getScheme(),uri.getUserInfo(),uri.getHost(),uri.getPort(),null,null,null);
        } catch (URISyntaxException e) {
            effectiveURI=null;
        }
        return effectiveURI;
    }



}