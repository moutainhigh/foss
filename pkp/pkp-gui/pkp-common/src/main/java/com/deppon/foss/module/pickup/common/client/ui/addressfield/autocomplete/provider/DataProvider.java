/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-common
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/ui/addressfield/autocomplete/provider/DataProvider.java
 * 
 * FILE NAME        	: DataProvider.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */
package com.deppon.foss.module.pickup.common.client.ui.addressfield.autocomplete.provider;

import com.deppon.foss.module.pickup.waybill.shared.dto.AddressFieldDto;

/**
 * 
 * 数据提供接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:102246-foss-shaohongliang,date:2012-10-24 下午6:38:03, </p>
 * @author 102246-foss-shaohongliang
 * @date 2012-10-24 下午6:38:03
 * @since
 * @version
 */
public interface DataProvider {

	/**
	 * 
	 * 获取数据
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午8:14:43
	 */
    AddressFieldDto getData(int index);

    /**
     * 
     * 追加数据
     * @author 102246-foss-shaohongliang
     * @date 2012-12-25 上午8:14:50
     */
    void appendData(AddressFieldDto value);

    /**
     * 
     * 插入数据
     * @author 102246-foss-shaohongliang
     * @date 2012-12-25 上午8:15:03
     */
     void insertData(int index, AddressFieldDto value);

    /**
     * 
     * 替换相应位置的旧数据
     * @author 102246-foss-shaohongliang
     * @date 2012-12-25 上午8:15:10
     */
     void replaceData(int index, AddressFieldDto value);

    /**
     * 
     * 将旧值替换为新值
     * @author 102246-foss-shaohongliang
     * @date 2012-12-25 上午8:15:20
     */
     void replaceData(AddressFieldDto oldValue, AddressFieldDto newValue);

    /**
     * 
     * 删除某个位置的数据
     * @author 102246-foss-shaohongliang
     * @date 2012-12-25 上午8:16:07
     */
     void removeDataAt(int index);

    /**
     * 
     * 删除某个数据对象
     * @author 102246-foss-shaohongliang
     * @date 2012-12-25 上午8:16:20
     */
     void removeData(AddressFieldDto value);

    /**
     * 
     * 清空集合
     * @author 102246-foss-shaohongliang
     * @date 2012-12-25 上午8:16:38
     */
     void clear();

    /**
     * 
     * 获取集合大小
     * @author 102246-foss-shaohongliang
     * @date 2012-12-25 上午8:16:47
     */
     int getSize();

    /**
     * 
     * 将集合数据转换为数组返回
     * @author 102246-foss-shaohongliang
     * @date 2012-12-25 上午8:16:56
     */
     AddressFieldDto[] toArray();

    /**
     * 
     * 设置数据改变时触发监听
     * @author 102246-foss-shaohongliang
     * @date 2012-12-25 上午8:17:15
     */
     void setDataChangeListener(DataChangeListener listener);

    /**
     * 数据改变监听接口
     */
    public interface DataChangeListener {
    	/**
    	 * 追加
    	 */
        public static final int APPEND = 1;
        
    	/**
    	 * 插入
    	 */
        public static final int INSERT = 2;
        
        /**
         * 替换
         */
        public static final int REPLACE = 3;
        
        /**
         * 删除
         */
        public static final int REMOVE = 4;
        
        /**
         * 清除
         */
        public static final int CLEAR = 5;

        /**
         * 
         * 数据变化回调方法
         * @author 102246-foss-shaohongliang
         * @date 2012-12-25 上午8:18:22
         */
         void dataChanged(int action, AddressFieldDto value);
    }
}