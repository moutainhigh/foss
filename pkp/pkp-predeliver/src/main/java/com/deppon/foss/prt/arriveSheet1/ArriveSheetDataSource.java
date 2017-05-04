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
 * FILE PATH        	: src/main/java/com/deppon/foss/prt/arriveSheet1/ArriveSheetDataSource.java
 * 
 * FILE NAME        	: ArriveSheetDataSource.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.prt.arriveSheet1;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.lf5.util.DateFormatManager;
import org.springframework.context.ApplicationContext;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IBillAdvertisingSloganService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IPrintMarketingContentService;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IArrivesheetDao;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IArriveSheetManngerService;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.ArriveSheetConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ArriveSheetEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ArriveSheetWaybillAddPropertyDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.vo.ArriveSheetVo;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillQueryService;
import com.deppon.foss.module.pickup.waybill.shared.dto.LeaveChangeByWaybillNoResultDto;
import com.deppon.foss.print.jasper.JasperContext;
import com.deppon.foss.print.jasper.JasperDataSource;
import com.deppon.foss.print.jasper.MultiJasperDataSource;
import com.deppon.foss.print.util.BarCode128Util;
import com.deppon.foss.print.util.ClassPathResourceUtil;
import com.deppon.foss.prt.ArriveSheetPrintService;
import com.deppon.foss.util.define.FossConstants;

/**
 * 获取普通版到达联 数据源
 * 
 * @author 097972-foss-dengtingting
 * @date 2012-12-26 下午9:00:33
 */
public class ArriveSheetDataSource implements MultiJasperDataSource {

	@Override
	public JasperDataSource[] createJasperDataSources(JasperContext jasperContext) {

		String arriveSheetNoString = (String) jasperContext.get("arriveSheetNos");
		String currentUserEmpName = (String) jasperContext.get("currentUserEmpName");
		String currentUserDepCode = (String) jasperContext.get("currentUserDepCode");
		String arriveSheetNos[] = arriveSheetNoString.split(",");
		List<JasperDataSource> lst = new ArrayList<JasperDataSource>();
		ApplicationContext _ApplicationContext = jasperContext.getApplicationContext();
		//到达联上广告信息接口
		IBillAdvertisingSloganService billAdvertisingSloganService = (IBillAdvertisingSloganService) _ApplicationContext.getBean("billAdvertisingSloganService");
		//打印广告
		String adTopLeft = billAdvertisingSloganService.queryBillSloganContent(DictionaryValueConstants.PKP_ARRIVE_TOP_LEFT);
		String adLoverLeft = billAdvertisingSloganService.queryBillSloganContent(DictionaryValueConstants.PKP_ARRIVE_LOWER_LEFT, currentUserDepCode);
		if (ArriveSheetConstants.oldArriveSheetTemplate.containsKey(jasperContext.getPrtkey())) {
			// 原到达联模版
			for (String arriveSheetNo : arriveSheetNos) {
				lst.add(new MyJasperDataSource(arriveSheetNo, currentUserEmpName, currentUserDepCode, adTopLeft, adLoverLeft));
			}
		} else {
			// 新到达联模版（所谓连打）
			List<String> twoArriveSheetNoList = ArriveSheetPrintService.getTwoArriveSheetNoList(arriveSheetNos);
			for (String twoArriveSheetNo : twoArriveSheetNoList) {
				lst.add(new MyJasperDataSource(twoArriveSheetNo, currentUserEmpName, currentUserDepCode, adTopLeft, adLoverLeft));
			}
		}
		return lst.toArray(new JasperDataSource[0]);
	}

	class MyJasperDataSource implements JasperDataSource {

		String datePattern = "yyyy-MM-dd HH:mm:ss";
		String twoArriveSheetNo = null;
		String currentUserEmpName = null;
		String currentUserDepCode = null;
		String adTopLeft = null;
		String adLoverLeft = null;

		public MyJasperDataSource(String pTwoArriveSheetNo, String pCurrentUserEmpName, String pCurrentUserDepCode, String pAdTopLeft, String pAdLoverLeft) {
			twoArriveSheetNo = pTwoArriveSheetNo;
			currentUserEmpName = pCurrentUserEmpName;
			currentUserDepCode = pCurrentUserDepCode;
			adTopLeft = pAdTopLeft;
			adLoverLeft = pAdLoverLeft;
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
		public List createDetailDataSource(JasperContext jasperContext) {
			List lst = new ArrayList();
			lst.add(new HashMap());
			return lst;
		}

		private String dateFormat(Date date, String pattern) {
			if (date == null) {
				return "";
			}
			DateFormatManager formatManager = new DateFormatManager();
			return formatManager.format(date, pattern);
		}

		/**
		 * 创建数据源
		 * 
		 * @author 097972-foss-dengtingting
		 * @date 2012-12-26 下午9:03:19
		 * @see com.deppon.foss.print.jasper.JasperDataSource#createParameterDataSource(com.deppon.foss.print.jasper.JasperContext)
		 */
		@SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
		public Map createParameterDataSource(JasperContext jasperContext) {
			Map<String, Object> parameter = new HashMap<String, Object>();
			ApplicationContext _ApplicationContext = jasperContext.getApplicationContext();
			//到达联管理服务
			IArriveSheetManngerService arriveSheetManngerService = (IArriveSheetManngerService) _ApplicationContext.getBean("arriveSheetManngerService");
			//到达联DAO
			IArrivesheetDao arrivesheetDao = (IArrivesheetDao) _ApplicationContext.getBean("arrivesheetDao");
			//打印营销内容基础资料Service层实现
			IPrintMarketingContentService printMarketingContentService = (IPrintMarketingContentService) _ApplicationContext.getBean("printMarketingContentService");
			String arriveSheetNos[] = twoArriveSheetNo.split(",");
			String suffix = ArriveSheetConstants.ARRIVE_SHEET_PRINT_SUFFIX_ONE;
			for (int i = 0; i < arriveSheetNos.length; i++) {
				String arriveSheetNo = arriveSheetNos[i];
				if (i == 1) {
					suffix = ArriveSheetConstants.ARRIVE_SHEET_PRINT_SUFFIX_TWO;
				}
				if (ArriveSheetConstants.oldArriveSheetTemplate.containsKey(jasperContext.getPrtkey())) {
					suffix = "";
				}
				//到达联实体
				ArriveSheetEntity arriveSheet = new ArriveSheetEntity();
				//到达联号
				arriveSheet.setArrivesheetNo(arriveSheetNo);
				//是否激活
				arriveSheet.setActive(FossConstants.YES);
				//根据到达联编号，查询到达联信息
				List<ArriveSheetEntity> arriveEntityList = arrivesheetDao.queryArriveSheetListByEntity(arriveSheet);
				ArriveSheetEntity arriveEntity = null;
				if (CollectionUtils.isNotEmpty(arriveEntityList)) {
					arriveEntity = arriveEntityList.get(0);
				} else {
					return parameter;
				}
				//到达联VO
				ArriveSheetVo vo = arriveSheetManngerService.queryWaybillDetailByWaybillNo(arriveEntity.getWaybillNo());
				//运单信息 与查询到达联详细信息Dto
				ArriveSheetWaybillAddPropertyDto waybillEntity = vo.getArriveSheetWaybillAddPropertyDto();
				/************************ 广告开始 ********************************************/
				parameter.put(ArriveSheetConstants.ARRIVE_SHEET_PRINT_arriveAd, adTopLeft);
				//变更内容--取消变更内容为部门广告语
				//			queryWaybillRfcRecord(arriveEntity.getWaybillNo(), waybillQueryService, parameter);
				if(StringUtil.isNotBlank(waybillEntity.getCustomerPickupCityCode())){
					//查询到达城市的营销内容
					String content = printMarketingContentService.queryEntityByCodeAndPattern(waybillEntity.getCustomerPickupCityCode(), DictionaryValueConstants.ARRIVAL_CITY );
					//若营销内容不为空替换广告语
					if(StringUtil.isNotBlank(content)){
						parameter.put(ArriveSheetConstants.ARRIVE_SHEET_PRINT_changeInfo, content);
					}else {
						parameter.put(ArriveSheetConstants.ARRIVE_SHEET_PRINT_changeInfo, adLoverLeft);
					}
				}else{
					parameter.put(ArriveSheetConstants.ARRIVE_SHEET_PRINT_changeInfo, adLoverLeft);
				}
				/************************* 广告结束 *******************************************/
				//预约送货时间
				parameter.put(ArriveSheetConstants.ARRIVE_SHEET_PRINT_deliverDate, dateFormat(arriveEntity.getDeliverDate(), "yyyy-MM-dd")+"  ");
				//送货要求
				parameter.put(ArriveSheetConstants.ARRIVE_SHEET_PRINT_deliverRequire, arriveEntity.getDeliverRequire());

				//(货物基本信息-发货网点制单人/时间)
				parameter.put(ArriveSheetConstants.ARRIVE_SHEET_PRINT_receiveOrgCode,
						waybillEntity.getReceiveOrgName() + ArriveSheetConstants.BACKSLASH + waybillEntity.getCreateUserCode() + ArriveSheetConstants.BACKSLASH + dateFormat(waybillEntity.getBillTime(), datePattern));
				//(货物基本信息-提货网点 制单人/时间)
				parameter.put(ArriveSheetConstants.ARRIVE_SHEET_PRINT_customerPickupOrgCode,
						StringUtil.defaultIfNull(waybillEntity.getCustomerPickupOrgCode()) + ArriveSheetConstants.BACKSLASH + currentUserEmpName + ArriveSheetConstants.BACKSLASH + dateFormat(new Date(), datePattern));
				//parameter.put("customerPickupOrgCode", waybillEntity.getCustomerPickupOrgCode()+FossUserContext.getCurrentUser().getUserName()+new Date());
				/*********** 货物基本信息结束 ************/
				/*********** 到达部门存根开始 ************/
				
				// 到达联基本信息
				ArriveSheetPrintService.setArriveSheetBaseInfo(waybillEntity, parameter, _ApplicationContext, arriveEntity, currentUserDepCode, currentUserEmpName, suffix);
				// 到达联打印基本费用
				ArriveSheetPrintService.setGatArriveSheetInfo(waybillEntity, parameter, _ApplicationContext, suffix);
				//获取订单来源
				String orderChannel=waybillEntity.getOrderChannel();
				if(orderChannel !=null && !"".equals(orderChannel) && ArriveSheetConstants.ORDERCHANNEL.equals(orderChannel)){
				}else{
					// 其他费用
					ArriveSheetPrintService.setOtherFee(waybillEntity, StringUtil.equals(FossConstants.ACTIVE, waybillEntity.getSecretPrepaid()), parameter, ArriveSheetConstants.ARRIVE_SHEET_PRINT_TYPE_PIN, suffix);
				}
				/*********** 到达部门存根结束 ************/

				/*********** 其它服务费结束 ************/
				if (jasperContext.getCtxPath() != null) {
					String ctxpath = jasperContext.getCtxPath();
					parameter.put(ArriveSheetConstants.ARRIVE_SHEET_PRINT_imgbkg, ctxpath + "/images/predeliver/prt/" + jasperContext.getPrtkey() + "/arriveSheet.jpg");
				} else {
					ClassPathResourceUtil util = new ClassPathResourceUtil();
					InputStream imgin = util.getInputStream("com/deppon/foss/prt/" + jasperContext.getPrtkey() + "/images/arriveSheet.jpg");
					parameter.put(ArriveSheetConstants.ARRIVE_SHEET_PRINT_imgbkg, imgin);
				}

				try {
					parameter.put(ArriveSheetConstants.ARRIVE_SHEET_PRINT_bcodeimg, BarCode128Util.newBarCode128(arriveSheetNo, false));
				} catch (Exception e) {
					e.printStackTrace();
				} finally {

				}
			}
			return parameter;
		}

		/**
		 * 
		 * <p>
		 * 获得运单变更信息集合<br />
		 * </p>
		 * 
		 * @author ibm-lizhiguo
		 * @version 0.1 2013-3-21
		 * @param waybillNo
		 * @param waybillQueryService void
		 */
		private void queryWaybillRfcRecord(String waybillNo, IWaybillQueryService waybillQueryService, Map<String, Object> parameter) {
			//显示变更内容
			StringBuffer sb = new StringBuffer();
			//获得变更内容LIST
			List<LeaveChangeByWaybillNoResultDto> dtoList = waybillQueryService.queryLeaveChangeByWaybillNo(waybillNo);
			//遍历变更内容
			for (LeaveChangeByWaybillNoResultDto leaveChangeByWaybillNoResultDto : dtoList) {
				sb.append("起草时间" + dateFormat(leaveChangeByWaybillNoResultDto.getDraftTime(), datePattern)).append("变更内容" + leaveChangeByWaybillNoResultDto.getChangeItems());
			}
			parameter.put("changeInfo", sb.toString());
		}
	}
}