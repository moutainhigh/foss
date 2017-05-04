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

import org.jdesktop.swingx.JXTextField;

import com.deppon.foss.framework.client.component.remote.DefaultRemoteServiceFactory;
import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.module.pickup.common.client.service.AddressServicesFactory;
import com.deppon.foss.module.pickup.common.client.service.IAddressService;
import com.deppon.foss.module.pickup.common.client.ui.addressfield.autocomplete.AutoCompleteExtender;
import com.deppon.foss.module.pickup.common.client.ui.addressfield.popup.AddressSelecterExtender;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.dto.AddressFieldDto;
import com.deppon.foss.module.pickup.waybill.shared.hessian.IAddressServiceHessianRemoting;

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
public class JAddressField extends JXTextField{
	/**
	 * 11
	 */
	private static final int ELEVEN = 11;


	/**
     * 序列化版本
     */
    private static final long serialVersionUID = 2156802410672107534L;
    
	
	/**
	 * 匹配拼音的省市区数据
	 */
	private static List<AddressFieldDto> pinyinDistrictList;
	
	/**
	 * 热门城市数据
	 */
	private static List<AddressFieldDto> hotCityList;
	
	/**
	 * 所有省份数据
	 */
	private static List<AddressFieldDto> provinceList;
	
	/**
	 * 所有国家数据
	 */
	private static List<AddressFieldDto> nationList;
    
    /**
     * 自动完成扩展
     */
	private AutoCompleteExtender autoCompleteExtender;
	
	/**
	 * 焦点选中弹出框扩展
	 */
	private AddressSelecterExtender addressSelecterExtender; 
	
	/**
	 * 选中的地址栏对象 
	 */
	private AddressFieldDto addressFieldDto = new AddressFieldDto();

	

	/**
	 * 静态初始化基础数据
	 * update 354805-taodongguo
	 * 更新内容：添加查询所有国家数据、修改查询所有省份为查询所有国内省份
	 * 2016-10-11 15:40:34
	 */
	static{
		if (WaybillConstants.ONLINE_LOGIN.equals(SessionContext.get(WaybillConstants.LOGIN_TYPE).toString())) {
			//在线查询
			IAddressServiceHessianRemoting districtService = DefaultRemoteServiceFactory.getService(IAddressServiceHessianRemoting.class);
			//含拼音的数据
			pinyinDistrictList = districtService.queryPinyinDistrictList();
			//热门城市数据
			hotCityList = districtService.queryHotCityList();
			//所有国内省份数据
			provinceList = districtService.queryChinaProvinceList();
			//所有国家数据
			nationList = districtService.queryNationList();
		} else {
			//离线查询
			//含拼音的区域、城市数据
			IAddressService districtService = AddressServicesFactory.getAddressService();
			//含拼音的数据
			pinyinDistrictList = districtService.queryPinyinDistrictList();
			//热门城市数据
			hotCityList = districtService.queryHotCityList();
			//所有国内省份数据
			provinceList = districtService.queryChinaProvinceList();
			//所有国家数据
			nationList = districtService.queryNationList();
		}
	    
	}
	
	/**
	 * 构造方法
	 */
	public JAddressField() {
		super();
		setAutoComplete();
		setPopupSelecter();
	}
	
	/**
	 * 有参构造-在输入框中显示默认提示信息
	 * @author 354805-taodongguo
	 * @crateDate 2016-10-11 15:43:39
	 * @param promptText 默认提示信息
	 */
	public JAddressField(String promptText) {
		super(promptText);
		setAutoComplete();
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
		addressSelecterExtender = new AddressSelecterExtender(this,hotCityList, provinceList, nationList);
		addressSelecterExtender.setCommitListener(new CommitListener() {

			// 值最终被选中时会触发该函数
			public void commit(AddressFieldDto value) {
				addressFieldDto = value;
			}
		});

	}

	/**
	 * 
	 * 设置自动下拉扩展服务
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午8:30:05
	 */
	private void setAutoComplete() {
		autoCompleteExtender = new AutoCompleteExtender(this,pinyinDistrictList);
		autoCompleteExtender.setMatchDataAsync(true);
		autoCompleteExtender.setSizeFitComponent();
		autoCompleteExtender.setMaxVisibleRows(ELEVEN);
		autoCompleteExtender.setCommitListener(new CommitListener() {

			// 值最终被选中时会触发该函数
			public void commit(AddressFieldDto value) {
				addressFieldDto = value;
				if(value.getDegree() == 1){ //检索省份
					addressSelecterExtender.showCityByProvince(value.getProvinceId());
				}else if(value.getDegree() == 2){ //检索城市
					addressSelecterExtender.showCoutysByCity(value.getCityId());
				}
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