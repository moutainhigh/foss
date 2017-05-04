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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/ui/addressfield/autocomplete/provider/DefaultDataProvider.java
 * 
 * FILE NAME        	: DefaultDataProvider.java
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

import java.util.ArrayList;
import java.util.List;

import com.deppon.foss.module.pickup.waybill.shared.dto.AddressFieldDto;

/**
 * 
 * 数据提供实现类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:102246-foss-shaohongliang,date:2012-10-24 下午6:38:17, </p>
 * @author 102246-foss-shaohongliang
 * @date 2012-10-24 下午6:38:17
 * @since
 * @version
 */
public class DefaultDataProvider implements DataProvider {
	
	/**
	 * 封装数据集合
	 */
    private List<AddressFieldDto> data;
    
    /**
     * 数据变化时回调监听
     */
    private DataChangeListener listener;

    /**
     * 
     * 构造Provider对象
     * @author 102246-foss-shaohongliang
     * @date 2012-12-25 上午8:19:46
     */
    public DefaultDataProvider() {
        data = new ArrayList<AddressFieldDto>();
    }

    /**
     * 
     * 获取数据
     * @author 102246-foss-shaohongliang
     * @date 2012-12-25 上午8:20:03
     * @see com.deppon.foss.module.pickup.common.client.ui.addressfield.autocomplete.provider.DataProvider#getData(int)
     */
    public synchronized AddressFieldDto getData(int index) {
        return data.get(index);
    }

    /**
     * 
     * 追加数据
     * @author 102246-foss-shaohongliang
     * @date 2012-12-25 上午8:20:23
     * @see com.deppon.foss.module.pickup.common.client.ui.addressfield.autocomplete.provider.DataProvider#appendData(com.deppon.foss.module.pickup.common.client.dto.AddressFieldDto)
     */
    public synchronized void appendData(AddressFieldDto value) {
        if (data.add(value)) {
            if (listener != null) {
                listener.dataChanged(DataChangeListener.APPEND, value);
            }
        }
    }

    /**
     * 
     * 插入数据
     * @author 102246-foss-shaohongliang
     * @date 2012-12-25 上午8:20:33
     * @see com.deppon.foss.module.pickup.common.client.ui.addressfield.autocomplete.provider.DataProvider#insertData(int, com.deppon.foss.module.pickup.common.client.dto.AddressFieldDto)
     */
    public synchronized void insertData(int index, AddressFieldDto value) {
        data.add(index, value);
        if (listener != null) {
            listener.dataChanged(DataChangeListener.INSERT, value);
        }
    }

    /**
     * 
     * 替换数据
     * @author 102246-foss-shaohongliang
     * @date 2012-12-25 上午8:20:41
     * @see com.deppon.foss.module.pickup.common.client.ui.addressfield.autocomplete.provider.DataProvider#replaceData(int, com.deppon.foss.module.pickup.common.client.dto.AddressFieldDto)
     */
    public synchronized void replaceData(int index, AddressFieldDto value) {
        if (data.set(index, value).equals(value)) {
            if (listener != null) {
                listener.dataChanged(DataChangeListener.REPLACE, value);
            }
        }
    }

    /**
     * 
     * 替换数据
     * @author 102246-foss-shaohongliang
     * @date 2012-12-25 上午8:20:41
     * @see com.deppon.foss.module.pickup.common.client.ui.addressfield.autocomplete.provider.DataProvider#replaceData(int, com.deppon.foss.module.pickup.common.client.dto.AddressFieldDto)
     */
    public synchronized void replaceData(AddressFieldDto oldValue, AddressFieldDto newValue) {
        int index = data.indexOf(oldValue);
        if (data.set(index, newValue).equals(newValue)) {
            if (listener != null) {
                listener.dataChanged(DataChangeListener.REPLACE, newValue);
            }
        }
    }

    /**
     * 
     * 删除数据
     * @author 102246-foss-shaohongliang
     * @date 2012-12-25 上午8:21:05
     * @see com.deppon.foss.module.pickup.common.client.ui.addressfield.autocomplete.provider.DataProvider#removeDataAt(int)
     */
    public synchronized void removeDataAt(int index) {
    	AddressFieldDto value = data.get(index);
        data.remove(index);
        if (listener != null) {
            listener.dataChanged(DataChangeListener.REMOVE, value);
        }
    }

    /**
     * 
     * 删除数据
     * @author 102246-foss-shaohongliang
     * @date 2012-12-25 上午8:21:05
     * @see com.deppon.foss.module.pickup.common.client.ui.addressfield.autocomplete.provider.DataProvider#removeDataAt(int)
     */
    public synchronized void removeData(AddressFieldDto value) {
        if (data.remove(value)) {
            if (listener != null) {
                listener.dataChanged(DataChangeListener.REMOVE, value);
            }
        }
    }

    /**
     * 
     * 清空数据
     * @author 102246-foss-shaohongliang
     * @date 2012-12-25 上午8:21:20
     * @see com.deppon.foss.module.pickup.common.client.ui.addressfield.autocomplete.provider.DataProvider#clear()
     */
    public synchronized void clear() {
        data.clear();
        if (listener != null) {
            listener.dataChanged(DataChangeListener.CLEAR, null);
        }
    }

    /**
     * 
     * 获取数据集合大小
     * @author 102246-foss-shaohongliang
     * @date 2012-12-25 上午8:21:27
     * @see com.deppon.foss.module.pickup.common.client.ui.addressfield.autocomplete.provider.DataProvider#getSize()
     */
    public synchronized int getSize() {
        return data.size();
    }

    /**
     * 
     * 返回数组元素
     * @author 102246-foss-shaohongliang
     * @date 2012-12-25 上午8:21:36
     * @see com.deppon.foss.module.pickup.common.client.ui.addressfield.autocomplete.provider.DataProvider#toArray()
     */
    public synchronized AddressFieldDto[] toArray() {
        return data.toArray(new AddressFieldDto[]{new AddressFieldDto()});
    }
    
    /**
     * 
     * 添加监听
     * @author 102246-foss-shaohongliang
     * @date 2012-12-25 上午8:21:55
     * @see com.deppon.foss.module.pickup.common.client.ui.addressfield.autocomplete.provider.DataProvider#setDataChangeListener(com.deppon.foss.module.pickup.common.client.ui.addressfield.autocomplete.provider.DataProvider.DataChangeListener)
     */
    public synchronized void setDataChangeListener(DataChangeListener listener) {
        this.listener = listener;
    }
}