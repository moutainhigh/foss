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
 * PROJECT NAME	: pkp-waybill
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/server/service/impl/HisDeliveryCusService.java
 * 
 * FILE NAME        	: HisDeliveryCusService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.server.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IHisDeliveryCusDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.IHisDeliveryCusService;
import com.deppon.foss.module.pickup.waybill.server.utils.Constants;
import com.deppon.foss.module.pickup.waybill.shared.domain.HisDeliveryCusEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.HisCustomerDto;

/**
 * 
 * 发货客户历史
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:foss-gengzhe,date:2012-12-12 下午9:12:21, </p>
 * @author foss-gengzhe
 * @date 2012-12-12 下午9:12:21
 * @since
 * @version
 */
public class HisDeliveryCusService implements IHisDeliveryCusService{
	/**
	 * 历史发货客户数据持久层接口
	 */
	IHisDeliveryCusDao hisDeliveryCusDao;
	/**
	 * 设置  历史发货客户数据持久层接口
	 * @param hisDeliveryCusDao
	 */
	public void setHisDeliveryCusDao(IHisDeliveryCusDao hisDeliveryCusDao) {
		this.hisDeliveryCusDao = hisDeliveryCusDao;
	}

	/**
	 * 
	 * <p>保存历史发货客户</p> 
	 * @author foss-gengzhe
	 * @date 2012-12-13 上午9:19:37 
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IHisDeliveryCusService#addHisDeliveryCustomer()
	 */
	public void addHisDeliveryCustomer(HisDeliveryCusEntity hisDeliveryCusEntity) {
		//判断对象是否为空
		if(hisDeliveryCusEntity != null){
			//手机
			String mobilePhone = StringUtil.defaultIfNull(hisDeliveryCusEntity.getDeliveryCustomerMobilephone());
			//电话
			String telePhone = StringUtil.defaultIfNull(hisDeliveryCusEntity.getDeliveryCustomerPhone());
			//地址
			String address = StringUtil.defaultIfNull(hisDeliveryCusEntity.getDeliveryCustomerAddress());
			//名称
			String name = StringUtil.defaultIfNull(hisDeliveryCusEntity.getDeliveryCustomerContact());
			
			//非空判断
			if("".equals(mobilePhone.trim()) && "".equals(telePhone.trim()) && "".equals(address.trim()) && "".equals(name.trim())){
				return;
			}
			
			//封装历史发货客户在查询条件
			HisCustomerDto hisCustomer = new HisCustomerDto();
			//地址
			hisCustomer.setAddress(address);
			//手机
			hisCustomer.setMobilePhone(mobilePhone);
			//电话
			hisCustomer.setPhone(telePhone);
			//名称
			hisCustomer.setContact(name);
			//时间
			hisCustomer.setCreateTime(Constants.genLastThreeMonthDate());
			
			//存储查询出的历史客户信息
			List<HisDeliveryCusEntity> deliveryCusList = hisDeliveryCusDao.queryByCondition(hisCustomer);
			//若已有该记录则不插入，否则插入新记录
			if(CollectionUtils.isEmpty(deliveryCusList)){
				//新增历史发货客户信息
				hisDeliveryCusDao.insert(hisDeliveryCusEntity);
			}else{
				//不做业务处理
			}
		}
	}
}