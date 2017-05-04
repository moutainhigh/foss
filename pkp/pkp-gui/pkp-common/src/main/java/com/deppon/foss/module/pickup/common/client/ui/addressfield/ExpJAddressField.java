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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/ui/addressfield/JAddressField.java
 * 
 * FILE NAME        	: JAddressField.java
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
package com.deppon.foss.module.pickup.common.client.ui.addressfield;

import java.util.List;

import javax.swing.JTextField;

import com.deppon.foss.framework.client.component.remote.DefaultRemoteServiceFactory;
import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.dto.AddressFieldDto;
import com.deppon.foss.module.pickup.waybill.shared.hessian.IAddressServiceHessianRemoting;
import com.deppon.foss.module.pickup.common.client.service.AddressServicesFactory;
import com.deppon.foss.module.pickup.common.client.service.IAddressService;
import com.deppon.foss.module.pickup.common.client.ui.addressfield.popup.ExpAddressSelecterExtender;

/**
 * 
 * 地址栏控件
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:102246-foss-shaohongliang,date:2012-10-24 下午6:41:13, </p>
 * @author 102246-foss-shaohongliang
 * @date 2012-10-24 下午6:41:13
 * @since
 * @version
 */
public class ExpJAddressField extends JTextField{

	/**
     * 序列化版本
     */
    private static final long serialVersionUID = 2156802410672107534L;
    
	/**
	 * 热门城市数据
	 */
	private static List<AddressFieldDto> hotCityList;
	
	/**
	 * 所有省份数据
	 */
	private static List<AddressFieldDto> provinceList;
	
	/**
	 * 焦点选中弹出框扩展
	 */
	private ExpAddressSelecterExtender addressSelecterExtender; 
	
	/**
	 * 选中的地址栏对象 
	 */
	private AddressFieldDto addressFieldDto = new AddressFieldDto();

	

	/**
	 * 静态初始化基础数据
	 */
	static{
		//zxy 20140428 DEFECT-2741 MANA-2018 start 修改:在线读取远程服务数据
		if (WaybillConstants.ONLINE_LOGIN.equals(SessionContext.get(WaybillConstants.LOGIN_TYPE).toString())) {
			//在线查询
			IAddressServiceHessianRemoting districtService = DefaultRemoteServiceFactory.getService(IAddressServiceHessianRemoting.class);
			//热门城市数据
			hotCityList = districtService.queryHotCityList();
			//所有省份数据
			provinceList = districtService.queryProvinceList();
		}else{
			//含拼音的区域、城市数据
			IAddressService districtService = AddressServicesFactory.getAddressService();
			//热门城市数据
			hotCityList = districtService.queryHotCityList();
			//所有省份数据
			provinceList = districtService.queryProvinceList();
		}
		//zxy 20140428 DEFECT-2741 MANA-2018 end 修改:在线读取远程服务数据
	    
	}
	
	/**
	 * 构造方法
	 */
	public ExpJAddressField() {
		setPopupSelecter();
	}

	/**
	 * 
	 * 设置弹出九宫格扩展服务
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午8:30:05
	 */
	private void setPopupSelecter() {
		// 弹出选择器
		addressSelecterExtender = new ExpAddressSelecterExtender(this,hotCityList, provinceList);
		addressSelecterExtender.setCommitListener(new CommitListener() {

			// 值最终被选中时会触发该函数
			public void commit(AddressFieldDto value) {
				addressFieldDto = value;
			}
		});

	}

	/**
	 * @return the addressFieldDto
	 */
	public AddressFieldDto getAddressFieldDto() {
		return addressFieldDto;
	}

	
	/**
	 * @param addressFieldDto the addressFieldDto to set
	 */
	public void setAddressFieldDto(AddressFieldDto addressFieldDto) {
		this.addressFieldDto = addressFieldDto;
	}
}