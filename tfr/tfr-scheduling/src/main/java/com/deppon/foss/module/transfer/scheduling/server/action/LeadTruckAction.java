/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  
 *  PROJECT NAME  : tfr-scheduling
 *  
 *  PACKAGE NAME  : 
 * 
 *  DESCRIPTION   : 调度、发车计划、排班、月台、车辆管理等
 *  
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/server/action/LeadTruckAction.java
 * 
 *  FILE NAME     :LeadTruckAction.java
 *  
 *  AUTHOR        : FOSS中转开发组
 *  
 *  TIME          : 
 *  
 *  HOME PAGE     :  http://www.deppon.com
 *  
 *  COPYRIGHT     : Copyright (c) 2013  Deppon All Rights Reserved.
 * 
 *  VERSION       :0.1
 * 
 *  LAST MODIFY TIME:
 ******************************************************************************/
/*
 * PROJECT NAME: tfr-scheduling
 * PACKAGE NAME: com.deppon.foss.module.transfer.scheduling.server.action
 * FILE    NAME: TruckDepartPlanAction.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.transfer.scheduling.server.action;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.base.baseinfo.api.server.service.IMotorcadeService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.MotorcadeEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ILeadTruckService;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.LeadTruckEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.QueryLeadTruckEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.vo.LeadTruckVO;
import com.deppon.foss.util.define.FossConstants;
/**
 * 外请车询价
 * 
 * @author 096598-foss-liubinbin
 * @date 2012-11-22 下午3:01:36
 */
public class LeadTruckAction extends AbstractAction {
	/**
	 * 
	 * 查询外请车询价
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-26 下午2:35:51
	 */
	public String queryLeadTruckGrid() {
		try {
			QueryLeadTruckEntity queryEntity = leadTruckVO.getQueryEntity();
			
			//check start
			
			if (StringUtils.isBlank(queryEntity.getOrigOrgCode())
					&& StringUtils.isBlank(queryEntity.getDestOrgCode())) {// 外请车必须填一个
				LOGGER.error("必须输入出发外场或到达外场信息");
				throw new TfrBusinessException("必须输入出发外场或到达外场信息", "");
			}
			// if
			// (!FossUserContext.getCurrentDeptCode().equals(
			// queryEntity.getOrigOrgCode())
			// &&
			// !FossUserContext.getCurrentDeptCode().equals(
			// queryEntity.getDestOrgCode()))
			// {
			// LOGGER.error("出发外场、到达外场必须有一个是请车员当前所在外场");
			// throw new
			// TfrBusinessException("出发外场、到达外场必须有一个是请车员当前所在外场",
			// "");
			// }
			if (queryEntity.getOrigOrgCode().equals(
					queryEntity.getDestOrgCode())) {
				LOGGER.error("出发外场和到达外场不能相同");
				throw new TfrBusinessException("出发外场和到达外场不能相同", "");
			}
			//check end
			
			List<LeadTruckEntity> leadTruckList = leadTruckService
					.queryLeadTruckGrid(queryEntity, this.limit, this.start);
			leadTruckVO.setLeadTruckList(leadTruckList);
			
			this.setTotalCount(leadTruckService.getCount(queryEntity));
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.error(e.getMessage(), e);
			return this.returnError(e);
		}
	}
	/**
	 * 
	 * 通过车队编码取得车队所属外场的编码和名称
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-26 下午2:35:51
	 */
	public String queryOrgByTruckOrgCode() {
		try {
			MotorcadeEntity motorcade = new MotorcadeEntity();
			FossUserContext.getCurrentDept();
			// 如果是调度组
			if (FossConstants.YES.equals(FossUserContext.getCurrentDept()
					.getDispatchTeam())) {
				motorcade.setTransferCenter(FossUserContext.getCurrentDept()
						.getDeliverOutfield());
				motorcade.setTransferCenterName(FossUserContext
						.getCurrentDept().getDeliverOutfieldName());
			} else {// 车队
				motorcade = motorcadeService
						.queryMotorcadeByCode(FossUserContext
								.getCurrentDeptCode());
			}
			leadTruckVO.setMotorcadeEntity(motorcade);
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.error(e.getMessage(), e);
			return this.returnError(e);
		}
	}
	/**
	 * 
	 * 新增、保存外请车询价
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-26 下午2:35:51
	 */
	public String saveOrAddLeadTruck() {
		try {
			if (leadTruckVO.getManualEntity().getOrigOrgCode()
					.equals(leadTruckVO.getManualEntity().getDestOrgCode())) {
				LOGGER.error("出发外场、到达外场不能相同");
				throw new TfrBusinessException("出发外场、到达外场不能相同", "");
			}
			// 出发外场和到达外场至少有个一个请车员所在外场，且出发外场和到达外场不能相同
			if (leadTruckVO.getManualEntity().getOrigOrgCode() == null
					&& "".equals(leadTruckVO.getManualEntity().getOrigOrgCode())
					&& leadTruckVO.getManualEntity().getDestOrgCode() == null
					&& "".equals(leadTruckVO.getManualEntity().getDestOrgCode())) {// 外请车必须填一个
				LOGGER.error("出发外场、到达外场至少填一个");
				throw new TfrBusinessException("出发外场、到达外场至少填一个", "");
			}
//			if (!FossUserContext.getCurrentDeptCode().equals(
//					leadTruckVO.getManualEntity().getOrigOrgCode())
//					&& !FossUserContext.getCurrentDeptCode().equals(
//							leadTruckVO.getManualEntity().getDestOrgCode())) {
//				LOGGER.error("出发外场、到达外场必须有一个是请车员当前所在外场");
//				throw new TfrBusinessException("出发外场、到达外场必须有一个是请车员当前所在外场", "");
//			}
			// 询价日期转一下
			leadTruckVO.getManualEntity().setInquiryTimeDate(
					getDate(leadTruckVO.getManualEntity().getInquiryTime(),
							DATE_FORMAT_1));
			leadTruckService.addLeadTruck(leadTruckVO.getManualEntity());
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.error(e.getMessage(), e);
			return this.returnError(e);
		}
	}
	/***** 时间格式 *****/
	public static final String DATE_FORMAT_1 = "yyyy-MM-dd HH:mm:ss";
	/**
	 * 
	 * 转换日期为指定的模版类型
	 * 
	 * @author IBM-liubinbin
	 * @date 2012-11-8 下午3:02:56
	 */
	public static Date getDate(String strDate, String format) {
		SimpleDateFormat objSimpleDateFormat = new SimpleDateFormat(format);
		Date objDate = null;
		try {
			objDate = (Date) objSimpleDateFormat.parse(strDate);
		} catch (java.text.ParseException e) {
			// 日期比较不正确
			throw new TfrBusinessException("日期比较不正确");
		}
		return objDate;
	}
	
	/**
	 * 
	 * @Title: exportLoadWayBillByTaskNo 
	 * @Description: 根据任务号导出卸车明细 
	 * @return    
	 * @return String    返回类型 
	 * exportLoadWayBillByTaskNo
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-4-24下午4:11:59
	 */
	public String exportUnloadWayBillByTaskNo() {
		try {
			fileName = leadTruckService.encodeFileName("外请车询价");
			excelStream = leadTruckService.exportTruckDepartOrArriveByTaskNo(leadTruckVO.getIds());
		} catch (TfrBusinessException e) {
			return returnError(e);
		}
		return returnSuccess();
	}
	/*******************************************************************/
	private static final long serialVersionUID = -302159438416579731L;
	/**
	 * 日志管理器
	 */
	private static final Logger LOGGER = LogManager
			.getLogger(LeadTruckAction.class);
	/** 导出Excel 文件流*/
	private InputStream excelStream;
	/** 导出Excel 文件名*/
	private String fileName;
	public InputStream getExcelStream() {
		return excelStream;
	}
	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	/**
	 * 外请车询价Service
	 */
	private ILeadTruckService leadTruckService;
	/**
	 * 页面VO
	 */
	private LeadTruckVO leadTruckVO = new LeadTruckVO();
	private IMotorcadeService motorcadeService;
	/**
	 * 获取 页面VO.
	 * 
	 * @return the 页面VO
	 */
	public LeadTruckVO getLeadTruckVO() {
		return leadTruckVO;
	}
	/**
	 * 设置 页面VO.
	 * 
	 * @param leadTruckVO
	 *            the new 页面VO
	 */
	public void setLeadTruckVO(LeadTruckVO leadTruckVO) {
		this.leadTruckVO = leadTruckVO;
	}
	/**
	 * 设置 外请车询价Service.
	 * 
	 * @param leadTruckService
	 *            the new 外请车询价Service
	 */
	public void setLeadTruckService(ILeadTruckService leadTruckService) {
		this.leadTruckService = leadTruckService;
	}
	public void setMotorcadeService(IMotorcadeService motorcadeService) {
		this.motorcadeService = motorcadeService;
	}
}