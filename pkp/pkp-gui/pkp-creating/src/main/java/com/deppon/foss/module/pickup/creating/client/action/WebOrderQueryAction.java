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
 * PROJECT NAME	: pkp-creating
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/action/WebOrderQueryAction.java
 * 
 * FILE NAME        	: WebOrderQueryAction.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.creating.client.action;

import java.awt.event.ActionEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener;
import com.deppon.foss.framework.client.component.dataaccess.GuiceContextFactroy;
import com.deppon.foss.framework.client.core.binding.IBinder;
import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.server.service.impl.OrgAdministrativeInfoService;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.pickup.common.client.service.IDataDictionaryValueService;
import com.deppon.foss.module.pickup.common.client.service.impl.DataDictionaryValueService;
import com.deppon.foss.module.pickup.common.client.utils.DateUtils;
import com.deppon.foss.module.pickup.common.client.vo.WaybillPanelVo;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.creating.client.ui.order.WebOrderDialog;
import com.deppon.foss.module.pickup.creating.client.vo.WebOrderVo;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmOrderInfo;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmOrderInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmOrderQueryDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmTransportTypeEnum;
import com.deppon.foss.module.pickup.waybill.shared.exception.CrmOrderImportException;

/**
 * 
 * 查询网上订单
 * 
 * @author 102246-foss-shaohongliang
 * @date 2012-11-13 下午4:33:55
 */
public class WebOrderQueryAction implements
		IButtonActionListener<WebOrderDialog> {

	// 日志
	private static Log log = LogFactory
				.getLog(WebOrderDialog.class);	
	/**
	 * 国际化
	 */
	private II18n i18n = I18nManager.getI18n(WebOrderQueryAction.class);	
	
	private WebOrderDialog ui;

	private IWaybillService waybillService = WaybillServiceFactory
			.getWaybillService();

	// 数据字典
	private IDataDictionaryValueService dataDictionaryValueService;
	
	//组织信息 Service接口
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	
	public WebOrderQueryAction(){
		dataDictionaryValueService = GuiceContextFactroy.getInjector()
		.getInstance(DataDictionaryValueService.class);
		orgAdministrativeInfoService = GuiceContextFactroy.getInjector()
				.getInstance(OrgAdministrativeInfoService.class);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		HashMap<String, IBinder<WaybillPanelVo>> map = ui.getWaybillEditUI().getBindersMap();
		IBinder<WaybillPanelVo> waybillBinder = map.get("waybillBinder");
		WaybillPanelVo bean = waybillBinder.getBean();
		WebOrderVo queryWebOrderVo = ui.getBinder().getBean();
		int pagesize = ui.getNavigator().getPageSize();
		if(pagesize<=0){
			pagesize = NumberConstants.NUMBER_50;
		}
		int currentPage = 1 ;
		// 查询条件
		if(ui.getDatePickerStart().getDate() == null || ui.getDatePickerEnd().getDate() == null){
			//请输入起始时间段！
			MsgBox.showInfo(i18n.get("foss.gui.creating.webOrderQueryAction.msgBox.nullDatePickerStart"));
			return;
		}
		
		if(ui.getDatePickerEnd().getDate().after(waybillService.gainServerTime())){
			//结束时间不能晚于今天！
			MsgBox.showInfo(i18n.get("foss.gui.creating.webOrderQueryAction.msgBox.afterDatePickerEnd"));
			return;
		}
		
		/**
		 * ISSUE-3155
		 * 1. 按照时间查询跨度3天，不限制3天内 
		 * 注释查询三天的限制
		 */
//		if(DateUtils.dateDiffInDays(ui.getDatePickerEnd().getDate(), ui.getDatePickerStart().getDate())>3){
//			//结束时间和开始时间之差不能大于3天！
//			MsgBox.showInfo(i18n.get("foss.gui.creating.webOrderQueryAction.msgBox.overTimeInterval"));
//			return;
//		}
		
		if(DateUtils.dateDiffInDays(ui.getDatePickerStart().getDate(), ui.getDatePickerEnd().getDate())>0){
			MsgBox.showInfo(i18n.get("foss.gui.creating.webOrderQueryAction.msgBox.timeParadox"));
			return;
		}
		
		CrmOrderQueryDto queryVo = new CrmOrderQueryDto();
		queryVo.setAcceptStatus(queryWebOrderVo.getOrderStatus().getValueCode());
		queryVo.setOrderType(queryWebOrderVo.getOrderType().getValueCode());
		queryVo.setShipperCust(queryWebOrderVo.getDeliveryCustomerName());
		queryVo.setShipperLinkman(queryWebOrderVo.getDeliveryCustomerContact());
		queryVo.setShipperMobile(queryWebOrderVo.getDeliveryCustomerMobilephone());
		queryVo.setShipperPhone(queryWebOrderVo.getDeliveryCustomerPhone());		
		queryVo.setPageNum(currentPage);
		queryVo.setPageSize(pagesize);
		
		/**
		 * ISSUE-3155
		 * 2、按照订单号码导入的，不受时间限制 
		 */
		String orderNumber = ui.getTxtOrderNo().getText().trim();
		//运单号
		String waybillNumber = ui.getTxtWaybillNumber().getText().trim();
		
		//订单号不存在
		if(StringUtils.isEmpty(orderNumber)){
			if(StringUtils.isEmpty(waybillNumber)){
				//开始时间
				queryVo.setBeginTime(ui.getDatePickerStart().getDate());
				Date endTime = null;
				if(ui.getDatePickerEnd().getDate()!=null){
		    		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		    		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    		try {
						endTime = sdf2.parse(sdf1.format(ui.getDatePickerEnd().getDate())+" 23:59:59");
					} catch (ParseException e1) {
						log.error("ParseException",e1);
					}
				}
				//结束时间
				queryVo.setEndTime(endTime);
			}else{
				queryVo.setWaybillNumber(waybillNumber);
			}
			
		}else{
			queryVo.setOrderNumber(orderNumber);
		}
		
		UserEntity user = (UserEntity) SessionContext.getCurrentUser();
		OrgAdministrativeInfoEntity dept = user.getEmployee().getDepartment();
		if(dept!=null){
			queryVo.setSalesDept(dept.getUnifiedCode());
		}
		
		//集中接货时取收货营业部的标杆编码
		if(bean.getPickupCentralized()){
			OrgAdministrativeInfoEntity org = waybillService.queryByCode(bean.getReceiveOrgCode());
			if(org!=null){
				queryVo.setSalesDept(org.getUnifiedCode());
			}else{
				MsgBox.showInfo(i18n.get("foss.gui.creating.webOrderQueryAction.msgBox.nullOrgAdministrativeInfo"));
				return;
			}
		}
		
		// 查询数据
		CrmOrderInfoDto infoVos = null;
		try{
			infoVos = waybillService.queryCrmOrder(queryVo);
    
    		if (infoVos.getTotalCount() == 0) {
    			MsgBox.showInfo(i18n.get("foss.gui.creating.webOrderQueryAction.msgBox.nulCrmOrderInfo"));
    			//zxy 20130925 BUG-55949 start 修改：如果查询不到任何数据，把表格数据清空并且状态条信息重置
//    			查询result
    			ui.getWebOrderSortedTableDataUpdater().setResultDto(infoVos);    			
    			ui.getWebOrderSortedTableDataUpdater().setNeedCount(false);//true表示count不允许为空，所以会再执行一遍查询
    			ui.setTableData(infoVos);
    			ui.getNavigator().refresh();
    			//zxy 20130925 BUG-55949 end 修改：如果查询不到任何数据，把表格数据清空并且状态条信息重置
    			return;
    		}
		} catch (CrmOrderImportException ex) {
			MsgBox.showInfo(i18n.get("foss.gui.creating.webOrderQueryAction.msgBox.failQueryCrmOrder") + ex.getMessage());
		}
		
		//将订单中的code转换为中文显示在界面上
		if(infoVos != null){
			convertName(infoVos);
		}
		
		
		//按钮查询 不需要再次查询了
		ui.getWebOrderSortedTableDataUpdater().setNeedCount(false);
		//按钮查询 不需要再次查询了
		ui.getWebOrderSortedTableDataUpdater().setNeedSearch(false);
		//查询result
		if(infoVos != null){
			ui.getWebOrderSortedTableDataUpdater().setResultDto(infoVos);		
			ui.setTableData(infoVos);
		}
		ui.getNavigator().setPageSize(pagesize);
		ui.getNavigator().setCurrentPage(currentPage);
		ui.getNavigator().refresh();
	}

	/**
	 * 将订单中的code转换为中文显示在界面上
	 * @param infoVos
	 */
	private void convertName(CrmOrderInfoDto infoVos) {
		List<CrmOrderInfo> crmOrderInfoList = infoVos.getCrmOrderInfoList();
		if(crmOrderInfoList==null || crmOrderInfoList.isEmpty()){
			return;
		}
		for(CrmOrderInfo crm : crmOrderInfoList){
			String orderStatus = crm.getOrderStatus();
			if(StringUtils.isNotEmpty(orderStatus)){
				DataDictionaryValueEntity e = dataDictionaryValueService
						.queryDataDictoryValueByCode(WaybillConstants.ORDER_STATUS, orderStatus);
				if(e!=null){
					crm.setOrderStatusName(e.getValueName());
				}
			}
			
			String orderType = crm.getResource();
			if(StringUtils.isNotEmpty(orderType)){
				DataDictionaryValueEntity e = dataDictionaryValueService
						.queryDataDictoryValueByCode(WaybillConstants.ORDER_TYPE, orderType);
				if(e!=null){
					crm.setResourceName(e.getValueName());
				}
			}
			
			String tranportMode = crm.getTransportMode();
			if(StringUtils.isNotEmpty(tranportMode)){
				for(CrmTransportTypeEnum tenum : CrmTransportTypeEnum.values()){
					if(tranportMode.equals(tenum.getCode())){
						crm.setTransportModeName(tenum.getName());
						crm.setTransportModeFossCode(tenum.getFossCode());
					}
				}
			}
			
			
			String orgUnifCode  = crm.getStartStation();
			if(StringUtils.isNotEmpty(orgUnifCode)){
				OrgAdministrativeInfoEntity e= 
					orgAdministrativeInfoService.queryOrgAdministrativeInfoByUnifiedCode(orgUnifCode);
				if(e!=null){
					crm.setStartStationName(e.getName());
				}
				
			}
		}
		
	}

	@Override
	public void setInjectUI(WebOrderDialog ui) {
		this.ui = ui;
	}

}