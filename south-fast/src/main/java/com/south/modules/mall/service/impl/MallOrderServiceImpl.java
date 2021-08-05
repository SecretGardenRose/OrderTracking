package com.south.modules.mall.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.south.common.exception.BizCodeEnume;
import com.south.common.exception.SOException;
import com.south.common.utils.Constant;
import com.south.common.utils.Constant.ORDER_STATUS;
import com.south.common.utils.HttpUtils;
import com.south.modules.mall.OrderStatusVo;
import com.south.modules.mall.controller.MallOrderController;
import io.swagger.models.auth.In;
import lombok.SneakyThrows;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.south.common.utils.PageUtils;
import com.south.common.utils.Query;

import com.south.modules.mall.dao.MallOrderDao;
import com.south.modules.mall.entity.MallOrderEntity;
import com.south.modules.mall.service.MallOrderService;


@Service("mallOrderService")
public class MallOrderServiceImpl extends ServiceImpl<MallOrderDao, MallOrderEntity> implements MallOrderService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MallOrderEntity> page = this.page(
                new Query<MallOrderEntity>().getPage(params),
                new QueryWrapper<MallOrderEntity>()
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
        HttpResponse response = HttpUtils.doGet(Constant.ORDER_HOST_URL, Constant.ORDER_PATH_URL, "get", new HashMap<>(), query);

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
                    String total_price=  order.get("total_price")==null?"":order.get("total_price").toString();
                    //note
                    String note=  order.get("note")==null?"":order.get("note").toString();


                    //order_status 自定义状态流程
                    //customer
                    String customer_json=  order.get("customer")==null?"":order.get("customer").toString();
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

                            //address   json
                            String address_json=  customer.get("default_address")==null?"":customer.get("default_address").toString();
                            if(!StringUtils.isEmpty(address_json)){
                                JSONObject addressObj = JSON.parseObject(address_json);
                                if(addressObj!=null){
                                    //city
                                    city=  addressObj.get("city")==null?"":addressObj.get("city").toString();
                                    //address
                                    address=  addressObj.get("address1")==null?"":addressObj.get("address1").toString();
                                    //zip
                                    zip=  addressObj.get("zip")==null?"":addressObj.get("zip").toString();
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
                        //设置地址信息
                        orderEntity.setAddress(address);
                        orderEntity.setUserZip(zip);
                        orderEntity.setCity(city);

                        //初始化订单流程
                        orderEntity.setOrderStatus(Constant.ORDER_STATUS.ORDER_RECEIVED.getName());
                        orderEntity.setFirstName(first_name);
                        orderEntity.setLastName(last_name);
                        orderEntity.setNote(note);
                        BigDecimal total=new BigDecimal(total_price);
                        orderEntity.setTotalPrice(total);
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

}