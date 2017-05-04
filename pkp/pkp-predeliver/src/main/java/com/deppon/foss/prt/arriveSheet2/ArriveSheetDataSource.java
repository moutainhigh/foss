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
 * PROJECT NAME	: pkp-predeliver
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/prt/arriveSheet2/ArriveSheetDataSource.java
 * 
 * FILE NAME        	: ArriveSheetDataSource.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.prt.arriveSheet2;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.context.ApplicationContext;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IBillAdvertisingSloganService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IPrintMarketingContentService;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IArrivesheetDao;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.INotifyCustomerDao;
import com.deppon.foss.module.pickup.predeliver.api.server.process.IHandleQueryOutfieldService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IArriveSheetManngerService;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.ArriveSheetConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ArriveSheetEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.NotificationEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ArriveSheetWaybillAddPropertyDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.vo.ArriveSheetVo;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IActualFreightDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillDao;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.print.jasper.JasperContext;
import com.deppon.foss.print.jasper.JasperDataSource;
import com.deppon.foss.print.jasper.MultiJasperDataSource;
import com.deppon.foss.print.util.BarCode128Util;
import com.deppon.foss.print.util.ClassPathResourceUtil;
import com.deppon.foss.prt.ArriveSheetPrintService;
import com.deppon.foss.util.define.FossConstants;

/**
 * 获取到达联 激光版 数据源
 * 
 * @author 097972-foss-dengtingting
 * @date 2012-12-26 下午9:01:37
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class ArriveSheetDataSource implements MultiJasperDataSource {	
	@Override
	public JasperDataSource[] createJasperDataSources(JasperContext jasperContext) {

		String arriveSheetNoString = (String) jasperContext.get("arriveSheetNos");
		String currentUserEmpName = (String) jasperContext.get("currentUserEmpName");
		String currentUserDepCode = (String) jasperContext.get("currentUserDepCode");
		String printValue = (String) jasperContext.get("printValue");
		String arriveSheetNos[] = arriveSheetNoString.split(",");
		List<JasperDataSource> lst = new ArrayList<JasperDataSource>();
		ApplicationContext _ApplicationContext = jasperContext.getApplicationContext();
		//到达联上广告信息接口
		IBillAdvertisingSloganService billAdvertisingSloganService = (IBillAdvertisingSloganService) _ApplicationContext.getBean("billAdvertisingSloganService");
		//打印广告
		String arriveAd = billAdvertisingSloganService.queryBillSloganContent(DictionaryValueConstants.PKP_ARRIVE_LOWER_RIGHT);
		if (ArriveSheetConstants.oldArriveSheetTemplate.containsKey(jasperContext.getPrtkey())) {
			// 原到达联模版
			for (String arriveSheetNo : arriveSheetNos) {
				lst.add(new MyJasperDataSource(arriveSheetNo, currentUserEmpName, currentUserDepCode, printValue, arriveAd));
			}
		} else {
			// 新到达联模版（所谓连打）
			List<String> twoArriveSheetNoList = ArriveSheetPrintService.getTwoArriveSheetNoList(arriveSheetNos);
			for (String twoArriveSheetNo : twoArriveSheetNoList) {
				lst.add(new MyJasperDataSource(twoArriveSheetNo, currentUserEmpName, currentUserDepCode, printValue, arriveAd));
			}
		}
		return lst.toArray(new JasperDataSource[0]);
	}

	/**
	 * 
	 * 打印配置类
	 * 
	 * @author ibm-wangfei
	 * @date Apr 26, 2013 5:15:08 PM
	 */
	class MyJasperDataSource implements JasperDataSource {
		String datePattern = "yyyy-MM-dd HH:mm:ss";
		String twoArriveSheetNo = null;
		String currentUserEmpName = null;
		String currentUserDepCode = null;
		String printValue = null;
		String arriveAd = null;

		public MyJasperDataSource(String pTwoArriveSheetNo, String pCurrentUserEmpName, String pCurrentUserDepCode, String pPrintValue, String pArriveAd) {
			twoArriveSheetNo = pTwoArriveSheetNo;
			currentUserEmpName = pCurrentUserEmpName;
			currentUserDepCode = pCurrentUserDepCode;
			printValue = pPrintValue;
			arriveAd = pArriveAd;
		}

		@Override
		public List createDetailDataSource(JasperContext jasperContext) {
			List lst = new ArrayList();
			lst.add(new HashMap());
			return lst;
		}

		/**
		 * 创建数据源
		 * 
		 * @author 097972-foss-dengtingting
		 * @date 2012-12-26 下午9:02:55
		 * @see com.deppon.foss.print.jasper.JasperDataSource#createParameterDataSource(com.deppon.foss.print.jasper.JasperContext)
		 */
		@Override
		public Map createParameterDataSource(JasperContext jasperContext) {
			Map<String, Object> parameter = new HashMap<String, Object>();
			ApplicationContext _ApplicationContext = jasperContext.getApplicationContext();
			IArriveSheetManngerService arriveSheetManngerService = (IArriveSheetManngerService) _ApplicationContext.getBean("arriveSheetManngerService");
			IArrivesheetDao arrivesheetDao = (IArrivesheetDao) _ApplicationContext.getBean("arrivesheetDao");
			IHandleQueryOutfieldService handleQueryOutfieldService = (IHandleQueryOutfieldService) _ApplicationContext.getBean("handleQueryOutfieldService");
			IWaybillDao waybillDao = (IWaybillDao) _ApplicationContext.getBean("waybillDao");
			INotifyCustomerDao notifyCustomerDao = (INotifyCustomerDao) _ApplicationContext.getBean("notifyCustomerDao");
			IActualFreightDao actualFreightDao = (IActualFreightDao)_ApplicationContext.getBean("actualFreightDao");
			//打印营销内容基础资料Service层实现
			IPrintMarketingContentService printMarketingContentService = (IPrintMarketingContentService) _ApplicationContext.getBean("printMarketingContentService");
			String arriveSheetNos[] = twoArriveSheetNo.split(",");
			String suffix = ArriveSheetConstants.ARRIVE_SHEET_PRINT_SUFFIX_ONE;
			for (int i = 0; i < arriveSheetNos.length; i ++ ) {
				String arriveSheetNo = arriveSheetNos[i];
				if (i == 1) {
					suffix = ArriveSheetConstants.ARRIVE_SHEET_PRINT_SUFFIX_TWO;
				}
				if (ArriveSheetConstants.oldArriveSheetTemplate.containsKey(jasperContext.getPrtkey())) {
					suffix = "";
				}
				// 对双打到达联做循环
				ArriveSheetEntity arriveSheet = new ArriveSheetEntity();
				arriveSheet.setArrivesheetNo(arriveSheetNo);
				arriveSheet.setActive(FossConstants.YES);
				//根据到达联编号，查询运单编号
				List<ArriveSheetEntity> arriveEntityList = arrivesheetDao.queryArriveSheetListByEntity(arriveSheet);
				ArriveSheetEntity arriveEntity = null;
				if (CollectionUtils.isNotEmpty(arriveEntityList)) {
					arriveEntity = arriveEntityList.get(0);
				} else {
					return parameter;
				}
				//查询运单信息;
				ArriveSheetVo vo = arriveSheetManngerService.queryWaybillDetailByWaybillNo(arriveEntity.getWaybillNo());
				ArriveSheetWaybillAddPropertyDto waybillEntity = vo.getArriveSheetWaybillAddPropertyDto();
				/****************************** 上边信息开始 *********************************/
				if(StringUtil.isNotBlank(waybillEntity.getCustomerPickupCityCode())){
					//查询到达城市的营销内容
					String content = printMarketingContentService.queryEntityByCodeAndPattern(waybillEntity.getCustomerPickupCityCode(),  DictionaryValueConstants.ARRIVAL_CITY);
					//若营销内容不为空替换广告语
					if(StringUtil.isNotBlank(content)){
						parameter.put(ArriveSheetConstants.ARRIVE_SHEET_PRINT_arriveAd + suffix, content);
					}else {
						parameter.put(ArriveSheetConstants.ARRIVE_SHEET_PRINT_arriveAd + suffix, arriveAd);
					}
				}else{
					parameter.put(ArriveSheetConstants.ARRIVE_SHEET_PRINT_arriveAd + suffix, arriveAd);
				}
				
				/*********** 备注开始 ************/
				//打印通知客户内容
				StringBuffer content = new StringBuffer();
				if (FossConstants.YES.equals(arriveEntity.getIsSentRequired())) {
					content.append("必送货");
				}
				if (FossConstants.YES.equals(arriveEntity.getIsNeedInvoice())) {
					if (StringUtil.isNotEmpty(content.toString())) {
						content.append("\n");
					}
					content.append("需要发票");
				}
				//送货要求
				if (StringUtil.isNotEmpty(arriveEntity.getDeliverRequire())) {
					if (StringUtil.isNotEmpty(content.toString())) {
						content.append("\n");
					}
					content.append(arriveEntity.getDeliverRequire());
				}
				//开单送货地址(省市区编码字符串)
				String orderDeliveryAdd = null;
				//实际送货地址(省市区编码字符串)
				String actualDeliveryAdd = null;
				//开单送货地址与实际送货地址不相等时，打印实际送货地址到到达联备注信息框
				String printAddress = null;
				//获取实际送货地址
				NotificationEntity ne = new NotificationEntity();
				ne.setWaybillNo(arriveEntity.getWaybillNo());
				//取最新的数据
				ne.setOrder("DESC");
				List<NotificationEntity> nes = notifyCustomerDao.queryNotificationsByParam(ne);
				if(nes!=null&&nes.size()>0) {
					ne = nes.get(0);
					actualDeliveryAdd = ne.getActualProvCode()+"-"+ne.getActualCityCode()+"-"
							+ne.getActualDistrictCode()+"-"+ne.getActualAddressDetail()+"-"+ne.getActualStreetn();
					//获取开单送货地址(省、市、区、详细地址)
					com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity we = waybillDao.queryWaybillByNo(arriveEntity.getWaybillNo());
					//开单界面收货地址备注插入在实际承运表(用意不明)
					ActualFreightEntity ae = actualFreightDao.queryByWaybillNo(ne.getWaybillNo());
					if(we!=null&&ae!=null) {
						orderDeliveryAdd = we.getReceiveCustomerProvCode()+"-"+we.getReceiveCustomerCityCode()+"-"
										+we.getReceiveCustomerDistCode()+"-"+we.getReceiveCustomerAddress()+"-"+ae.getReceiveCustomerAddressNote();
					}
					if(!orderDeliveryAdd.equals(actualDeliveryAdd)) {
						printAddress = handleQueryOutfieldService.getCompleteAddressAttachAddrNote(ne.getActualProvCode(), 
								   ne.getActualCityCode(), ne.getActualDistrictCode(),ne.getActualAddressDetail(),ne.getActualStreetn());
						if (StringUtil.isNotEmpty(content.toString())) {
							content.append("\n");
						}
						content.append("【实际收货地址："+printAddress+"】");
					}
				}
				//提前通知内容
				if (StringUtil.isNotEmpty(arriveEntity.getPreNoticeContent())) {
					if (StringUtil.isNotEmpty(content.toString())) {
						content.append("\n");
					}
					content.append(arriveEntity.getPreNoticeContent());
				}
				//备注信息
				 parameter.put(ArriveSheetConstants.ARRIVE_SHEET_PRINT_content + suffix, content.toString());
				 /*********** 备注结束 ************/
				 // 到达联基本信息
				ArriveSheetPrintService.setArriveSheetBaseInfo(waybillEntity, parameter, _ApplicationContext, arriveEntity, currentUserDepCode, currentUserEmpName, suffix);
				// 到达联打印基本费用
				ArriveSheetPrintService.setGatArriveSheetInfo(waybillEntity, parameter, _ApplicationContext, suffix);
				//获取订单来源
				String orderChannel=waybillEntity.getOrderChannel();
				if(orderChannel !=null && !"".equals(orderChannel) && ArriveSheetConstants.ORDERCHANNEL.equals(orderChannel)){
				}else{
					// 设置激光版到达联其他费用
					ArriveSheetPrintService.setOtherFee(waybillEntity, StringUtil.equals(FossConstants.ACTIVE, waybillEntity.getSecretPrepaid()), parameter, printValue, suffix);
				}
				/*********** 到达部门客户联结束 ************/
				if (jasperContext.getCtxPath() != null) {
					String ctxpath = jasperContext.getCtxPath();
					if (StringUtil.equals(ArriveSheetConstants.ARRIVE_SHEET_PRINT_TYPE_LASER_OLD, printValue)) {
						// 激光原版
						parameter.put(ArriveSheetConstants.ARRIVE_SHEET_PRINT_imgbkg, ctxpath + "/images/predeliver/prt/" + jasperContext.getPrtkey() + "/arriveSheet2.jpg");
					} else {
						parameter.put(ArriveSheetConstants.ARRIVE_SHEET_PRINT_imgbkg, ctxpath + "/images/predeliver/prt/" + jasperContext.getPrtkey() + "/arriveSheet3.jpg");
					}
				} else {
					ClassPathResourceUtil util = new ClassPathResourceUtil();
					if (StringUtil.equals(ArriveSheetConstants.ARRIVE_SHEET_PRINT_TYPE_LASER_OLD, printValue)) {
						InputStream imgin = util.getInputStream("com/deppon/foss/prt/" + jasperContext.getPrtkey() + "/images/arriveSheet2.jpg");
						parameter.put(ArriveSheetConstants.ARRIVE_SHEET_PRINT_imgbkg, imgin);
					} else {
						InputStream imgin = util.getInputStream("com/deppon/foss/prt/" + jasperContext.getPrtkey() + "/images/arriveSheet3.jpg");
						parameter.put(ArriveSheetConstants.ARRIVE_SHEET_PRINT_imgbkg, imgin);
					}
				}

				try {
					parameter.put(ArriveSheetConstants.ARRIVE_SHEET_PRINT_bcodeimg1 + suffix, BarCode128Util.newBarCode128(arriveSheetNo, false));
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (StringUtil.equals(ArriveSheetConstants.ARRIVE_SHEET_PRINT_TYPE_LASER_NEW, printValue)) {
					try {
						parameter.put(ArriveSheetConstants.ARRIVE_SHEET_PRINT_bcodeimg2 + suffix, BarCode128Util.newBarCode128(arriveSheetNo, false));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if(orderChannel !=null && !"".equals(orderChannel) && !ArriveSheetConstants.ORDERCHANNEL.equals(orderChannel)){
					//如果是汽运偏线，隐藏
					if(!StringUtil.equals(waybillEntity.getProductCode().trim(), ArriveSheetPrintService.PLF_NAME)){
						//(货物基本信息-费率)
						parameter.put(ArriveSheetConstants.ARRIVE_SHEET_PRINT_unitPrice + suffix, "费率 " + waybillEntity.getUnitPrice() + ArriveSheetConstants.SPAN1);
					}
				}
			}
			return parameter;
		}
	}
}