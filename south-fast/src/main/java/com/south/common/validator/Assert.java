/**
 * Copyright (c) 2016-2021 南行开源 All rights reserved.
 *
 * http://www.southiu.cn
 *
 * 版权所有，侵权必究！
 */

package com.south.common.validator;

import com.south.common.exception.SOException;
import org.apache.commons.lang.StringUtils;

/**
 * 数据校验
 *
 * @author Mr.Tang 15902072436@163.com
 */
public abstract class Assert {

    public static void isBlank(String str, String message) {
        if (StringUtils.isBlank(str)) {
            throw new SOException(message);
        }
    }

    public static void isNull(Object object, String message) {
        if (object == null) {
            throw new SOException(message);
        }
    }
}
