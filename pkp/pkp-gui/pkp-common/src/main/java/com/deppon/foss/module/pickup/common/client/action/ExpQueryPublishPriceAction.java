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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/action/QueryPublishPriceAction.java
 * 
 * FILE NAME        	: QueryPublishPriceAction.java
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
package com.deppon.foss.module.pickup.common.client.action;

import java.awt.event.ActionEvent;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdesktop.swingx.JXTable;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.component.dataaccess.GuiceContextFactroy;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.pickup.common.client.service.IBaseDataService;
import com.deppon.foss.module.pickup.common.client.service.IWaybillPriceExpressService;
import com.deppon.foss.module.pickup.common.client.service.impl.BaseDataService;
import com.deppon.foss.module.pickup.common.client.ui.ExpPublishPriceLinkTableMode;
import com.deppon.foss.module.pickup.common.client.ui.ExpQueryPublishPriceUI;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PublishPriceExpressEntity;



/**
 * 
 * 查询公布价 action
 * <p style="display:none">
 * version:V1.0,author:Administrator,date:2012-10-17 上午11:16:43,
 * </p>
 * @author 105089-FOSS-shixiaowei
 * @date 2012-10-17 上午11:16:43
 * @since
 * @version
 */
public class ExpQueryPublishPriceAction extends
	AbstractButtonActionListener<ExpQueryPublishPriceUI> {
	
	/**
	 * 日志
	 */
	private static final Log LOG = LogFactory.getLog(ExpQueryPublishPriceAction.class);
	
	/**
	 * ui
	 */
	private ExpQueryPublishPriceUI ui;
	
	/**
	 * query result list
	 */
	private List<PublishPriceExpressEntity> list;
	
	
	/**
	 * 查询快递公布价信息
	 */
	private IWaybillPriceExpressService waybillPriceExpressService;

	/**
	 * 基础数据查询服务类 用于查询基础数据等信息
	 */
	private IBaseDataService baseDataService = GuiceContextFactroy.getInjector().getInstance(BaseDataService.class);

	
	/**
	 * 设置ui
	 */
	public void setIInjectUI(ExpQueryPublishPriceUI ui) {
		this.ui = ui;
	}

	/**
	 * 查询按钮功能
	 * 
	 * @author 105089-FOSS-yangtong
	 * @date 2012-10-16 下午02:46:47
	 * @param e
	 * 
	 */
	@SuppressWarnings({ "static-access" })
	public void actionPerformed(ActionEvent e) {
		//
		JXTable table = ui.getTable();
		waybillPriceExpressService=GuiceContextFactroy.getInjector().getInstance(IWaybillPriceExpressService.class);
				
		try {
			list = queryPublishPricing(ui);
		} catch (BusinessException e1) {
			e1.printStackTrace();
			String message = "";
			String code1 = StringUtil.defaultIfNull(e1.getErrorCode());
			String code2 = StringUtil.defaultIfNull(e1.getMessage());
			if(code1.trim().equals(code2.trim())){
				message = code1;
			}else{
				message = code1 +"\n" + code2;
			}
			
			if(StringUtils.isNotEmpty(message)){
				MsgBox.showError(message);
				return;
			}
		}
		
		if(CollectionUtils.isEmpty(list)){
			return;
		}else{
			for (PublishPriceExpressEntity entity : list) {
				if(StringUtils.isEmpty(entity.getDestinationCity())){
					//若返回的对象到达城市为空，则在此片进行设置
					entity.setDestinationCity(ui.getTxtTargetOrgName().getText().trim());
				}
				
				String str = entity.getDeliveryTime();
				//将时间进行转换
				if(StringUtils.isNotEmpty(str)){
					int len = str.trim().length();
					if(len >= NumberConstants.NUMBER_3 && len <= NumberConstants.NUMBER_4){
						String str1 = str.substring(0,len-2);
						String str2 = str.substring(len-2,len);
						entity.setDeliveryTime(str1+":"+str2);
					}
				}
				
			}
		}
		
		// 刷新表格
		ExpPublishPriceLinkTableMode tableModel = new ExpPublishPriceLinkTableMode(ui.getArray(list));

		table.setModel(tableModel);
		
		LOG.debug(tableModel);
		
		ui.refreshTable(table);
		
		//默认选中查询结果的第一行
		if(ui.getTable()!=null && ui.getTable().getRowCount()>0){
			ui.getTable().requestFocus();
			ui.getTable().setRowSelectionAllowed(true);
			ui.getTable().setRowSelectionInterval(0,0);
		}
	}

	/**
	 * 查询公布价格
	 * @param ui2
	 * @return
	 */
	private List<PublishPriceExpressEntity> queryPublishPricing(ExpQueryPublishPriceUI ui) {
		//出发城市编码
		String cityName = "";
		 //始发部门编码
		String createOrgCode = ui.getTxtCreateOrgCode().getText();
		//到达城市编码
		String targetOrgCode =  ui.getTxtTargetOrgCode().getText();
		OrgAdministrativeInfoEntity orgInfo = baseDataService.queryOrgAdministrativeInfoEntityByCode(StringUtil.defaultIfNull(createOrgCode));
		if (null != orgInfo) {
			cityName = StringUtil.defaultIfNull(orgInfo.getCityCode());
		}
		
		//返回公布价信息
		return waybillPriceExpressService.queryPublishPriceDetailOnline(cityName, targetOrgCode);
	}

	/**
	 * @return the list
	 */
	public List<PublishPriceExpressEntity> getList() {
		return list;
	}
	
	
}