/**
 * Copyright (c) 2016-2021 南行开源 All rights reserved.
 *
 * http://www.southiu.cn
 *
 * 版权所有，侵权必究！
 */

package com.south.common.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 常量
 *
 * @author Mr.Tang 15902072436@163.com
 */
@Component
public class Constant {

    public static String FILE_UPLOAD_DIR;
    @Value("${south.file.upload.dir}")
    public void setFileUploadDir(String fileUploadDir){
        FILE_UPLOAD_DIR=fileUploadDir;
    }

    /**
     * 第三方订单数据主机地址
     */
    public static final String  ORDER_HOST_URL="https://1fe22e83bbe716b1e82b22d465b4c363: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@love-florist.myshopify.com";
    /**
     * 第三方订单数据接口地址
     */
    public static final String  ORDER_PATH_URL="/admin/api/2021-04/orders.json";

    public  static final String  DEFAULT_IMAGE="https://www.sgrcredit.com/south-fast/imgs/default.png";


    /** 超级管理员ID */
	public static final int SUPER_ADMIN = 1;
    /**
     * 当前页码
     */
    public static final String PAGE = "page";
    /**
     * 每页显示记录数
     */
    public static final String LIMIT = "limit";
    /**
     * 排序字段
     */
    public static final String ORDER_FIELD = "sidx";
    /**
     * 排序方式
     */
    public static final String ORDER = "order";
    /**
     *  升序
     */
    public static final String ASC = "asc";
	/**
	 * 菜单类型
	 * 
	 * @author chenshun
	 * @email 15902072436@163.com
	 * @date 2021年11月15日 下午1:24:29
	 */
    public enum MenuType {
        /**
         * 目录
         */
    	CATALOG(0),
        /**
         * 菜单
         */
        MENU(1),
        /**
         * 按钮
         */
        BUTTON(2);

        private int value;

        MenuType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 逻辑标识状态
     *
     * @author chenshun
     */
    public enum DELETE_FALG {
        /**
         * 正常
         */
        DATA_OK(0),
        /**
         * 数据异常
         */
        DATA_ERROR(1);

        private int value;

        DELETE_FALG(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
    
    /**
     * 定时任务状态
     * 
     * @author chenshun
     */
    public enum ScheduleStatus {
        /**
         * 正常
         */
    	NORMAL(0),
        /**
         * 暂停
         */
    	PAUSE(1);

        private int value;

        ScheduleStatus(int value) {
            this.value = value;
        }
        
        public int getValue() {
            return value;
        }
    }

    /**
     * 云服务商
     */
    public enum CloudService {
        /**
         * 七牛云
         */
        QINIU(1),
        /**
         * 阿里云
         */
        ALIYUN(2),
        /**
         * 腾讯云
         */
        QCLOUD(3);

        private int value;

        CloudService(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 订单流程状态
     */
    public enum ORDER_STATUS {
        /**
         * Order Received
         */
        ORDER_RECEIVED(1,"Order Received"),
        /**
         * We are working on your order
         */
        ORDER_WORKING(2,"We are working on your order"),
        /**
         * Bouquet Ready
         */
        ORDER_BOUQUET(3,"Bouquet Ready"),
        /**
         * Out for Delivery
         */
        ORDER_OUT(4,"Out for Delivery"),
        /**
         * Delivered
         */
        ORDER_DELIVERED(5,"Delivered");


        private int value;
        private String name;

        ORDER_STATUS(int value,String name) {
            this.value = value;
            this.name=name;
        }

        public int getValue() {
            return value;
        }
        public String getName() {
            return name;
        }
    }

    /**
     * 获取的订单状态
     *
     * @author chenshun
     * @email 15902072436@163.com
     * @date 2021年11月15日 下午1:24:29
     */
    public enum OrderGetStatus {
        /**
         * 目录
         */
        FUL("2"),
        /**
         * 菜单
         */
        UNFUL("1");

        private String value;

        OrderGetStatus(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * 按天
     *
     * @author chenshun
     * @email 15902072436@163.com
     * @date 2021年11月15日 下午1:24:29
     */
    public enum SELECTDAYS {
        /**
         * 今天
         */
        TODAY(0,"今天"),
        /**
         * 昨天 yesterday
         */
        YESTERDAY(1,"昨天"),
        /**
         * 明天 Tomorrow
         */
        TOMORROW(2,"明天");

        private Integer value;
        private String name;

        SELECTDAYS(Integer value,String name) {
            this.value = value;
            this.name = name;
        }

        public Integer getValue() {
            return value;
        }
        public String getName() {
            return name;
        }
    }

}
