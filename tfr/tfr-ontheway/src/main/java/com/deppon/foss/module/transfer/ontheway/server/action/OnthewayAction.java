/**
 *  initial comments.
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
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
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-ontheway
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/ontheway/server/action/OnthewayAction.java
 *  
 *  FILE NAME          :OnthewayAction.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.ontheway.server.action;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.IMotorcadeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.MotorcadeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.ontheway.api.server.service.IOnthewayService;
import com.deppon.foss.module.transfer.ontheway.api.shared.domain.ArrivalEntity;
import com.deppon.foss.module.transfer.ontheway.api.shared.util.ApiUtil;
import com.deppon.foss.module.transfer.ontheway.api.shared.util.Constants;
import com.deppon.foss.module.transfer.ontheway.api.shared.vo.OnthewayVO;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * 车辆在途的WEB实现
 * 
 * @author foss-liubinbin(for IBM)
 * @date 2012-12-24 下午7:08:01
 */
public class OnthewayAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private static final Logger LOGGER = LogManager
			.getLogger(OnthewayAction.class);

	/**
	 * 
	 * 通过车牌号查询车辆的信息，需要调用接口
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-11-8 下午2:06:58
	 */
	public String queryOnthewayGrid() {
		try {
			//出发部门或者到达部门为当前部门
			List<String> strOrgList =queryTransCenterChildrenCodes(FossUserContext
					.getCurrentDeptCode());
			if (strOrgList == null) {
				strOrgList = new ArrayList<String>();
				strOrgList.add(FossUserContext.getCurrentDeptCode());
			}
			//如果是调度组，取得车队所属的外场
			//取得车队
			OrgAdministrativeInfoEntity org = getBigOrg(FossUserContext
					.getCurrentDeptCode());
			if(org!=null)
			{//判断该部门是否是车队
				if(FossConstants.YES.equals(org.getTransDepartment()))
				{
					MotorcadeEntity motorcade = motorcadeService.queryMotorcadeByCode(org.getCode());
					if(motorcade!=null&&motorcade.getTransferCenter()!=null)
					{
						strOrgList.add(motorcade.getTransferCenter());
					}
				}
			}
			
			//如果是车队，取得车队所属的外场
			if(FossConstants.YES.equals(FossUserContext.getCurrentDept().getTransDepartment()))
			{
				MotorcadeEntity motorcade = motorcadeService.queryMotorcadeByCode(FossUserContext.getCurrentDeptCode());
				if(motorcade!=null&&motorcade.getTransferCenter()!=null)
				{
					strOrgList.add(motorcade.getTransferCenter());
				}
			}
			onthewayVO.getQueryEntity().setCurrentOrgCodes(strOrgList);
			if ("LONG_COUNT".equals(onthewayVO.getQueryEntity()
					.getFilterCondition())) {
				onthewayVO.setOnthewayList(onthewayService.getLongGrid(
						onthewayVO.getQueryEntity(), limit, start));
				this.setTotalCount(onthewayService.getLongCount(onthewayVO
						.getQueryEntity()));
			} else if ("SHORT_GPS".equals(onthewayVO.getQueryEntity()
					.getFilterCondition())) {
				onthewayVO.setOnthewayList(onthewayService.getShortGPSGrid(
						onthewayVO.getQueryEntity(), limit, start));
				this.setTotalCount(onthewayService.getShortGPSCount(onthewayVO
						.getQueryEntity()));
			} else if ("PKP_GPS".equals(onthewayVO.getQueryEntity()
					.getFilterCondition())) {
				onthewayVO.setOnthewayList(onthewayService.getPkpGPSGrid(
						onthewayVO.getQueryEntity(), limit, start));
				this.setTotalCount(onthewayService.getPkpGPSCount(onthewayVO
						.getQueryEntity()));
			} else {
				//此处filterCondition == '';从在途跟踪查询界面 查询按钮过来
				onthewayVO.setOnthewayList(onthewayService.queryOnthewayGrid(
						onthewayVO.getQueryEntity(), limit, start));
				this.setTotalCount(onthewayService.getCount(onthewayVO
						.getQueryEntity()));
			}
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.error(e);
			return this.returnError(e);
		}
	}
	/**
	 * 根据传入的部门code，获取该部门所属的外场
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-4-2 下午4:17:21
	 * @see com.deppon.foss.module.transfer.load.api.server.service.ILoadService#querySuperiorOrgByOrgCode(java.lang.String)
	 */
	@JSON
	public String querySuperiorOrgByOrgCode() {

		try {
			// 设置查询参数
			List<String> bizTypesList = new ArrayList<String>();
			// 车队类型
			bizTypesList.add(BizTypeConstants.ORG_TRANS_DEPARTMENT);
			// 外场类型
			bizTypesList.add(BizTypeConstants.ORG_TRANSFER_CENTER);
			// 空运总调类型
			bizTypesList.add(BizTypeConstants.ORG_AIR_DISPATCH);
			// 派送不营业部
			bizTypesList.add(BizTypeConstants.ORG_SALES_DEPARTMENT);
			// 查询上级部门
			OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoComplexService
					.queryOrgAdministrativeInfoIncludeSelfByCode(
							FossUserContext.getCurrentDeptCode(), bizTypesList);
			ArrivalEntity arrivalEntity = new ArrivalEntity();
			if (orgAdministrativeInfoEntity != null) {
				// 返回部门
				arrivalEntity.setOrigOrgCode(orgAdministrativeInfoEntity
						.getCode());
				arrivalEntity.setOrigOrgName(orgAdministrativeInfoEntity
						.getName());
			} else {
				arrivalEntity.setOrigOrgCode(FossUserContext
						.getCurrentDeptCode());
				arrivalEntity.setOrigOrgName(FossUserContext
						.getCurrentDeptName());
			}
			onthewayVO.setArrivalEntity(arrivalEntity);
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.error(e.getMessage(), e);
			return this.returnError(e);
		}
	}
	/**
	 * 根据用户当前部门获取相应大部门
	 * @param currentOrgCode 当前部门
	 * @author 097457-foss-wangqiang
	 * @date 2012-12-3 下午2:43:00
	 */
	private OrgAdministrativeInfoEntity getBigOrg(String currentOrgCode){
		//设置查询参数
		List<String> bizTypesList = new ArrayList<String>();
		//外场类型
		bizTypesList.add(BizTypeConstants.ORG_TRANS_DEPARTMENT);
		bizTypesList.add(BizTypeConstants.ORG_TRANS_DEPARTMENT);
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoComplexService.
				queryOrgAdministrativeInfoByCode(currentOrgCode, bizTypesList);
		if(orgAdministrativeInfoEntity != null){
			//返回部门
			return orgAdministrativeInfoEntity;
		}else{
			return null;
		}
	}

	/**
	 * 查询当前用户部门对应的外场下所有的子孙部门
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-4-11 上午10:02:45
	 */
	private List<String> queryTransCenterChildrenCodes(String currentDeptCode) {
		List<String> rs = null;
		// 当前部门所对应的外场编码
		String transCenterCode = "";
		OrgAdministrativeInfoEntity transCenter = querySuperiorOrgByOrgCode(currentDeptCode);
		if (transCenter != null) {
			transCenterCode = transCenter.getCode();
		}
		// 根据外场查询子孙部门编码
		if (StringUtils.isNotBlank(transCenterCode)) {
			List<OrgAdministrativeInfoEntity> orgs = orgAdministrativeInfoComplexService
					.queryOrgAdministrativeInfoEntityAllSubByCode(transCenterCode);
			if (CollectionUtils.isNotEmpty(orgs)) {
				rs = new ArrayList<String>();
				for (OrgAdministrativeInfoEntity org : orgs) {
					rs.add(org.getCode());
				}
			}
		}
		return rs;
	}
	
	/**
	 * 根据传入的部门code，获取该部门所属的外场
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-4-2 下午4:17:21
	 * @see com.deppon.foss.module.transfer.load.api.server.service.ILoadService#querySuperiorOrgByOrgCode(java.lang.String)
	 */
	private OrgAdministrativeInfoEntity querySuperiorOrgByOrgCode(String orgCode) {

		// 设置查询参数
		List<String> bizTypesList = new ArrayList<String>();
		// 外场类型
		bizTypesList.add(BizTypeConstants.ORG_TRANSFER_CENTER);
		bizTypesList.add(BizTypeConstants.ORG_TRANS_DEPARTMENT);
		// 查询上级部门
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoComplexService
				.queryOrgAdministrativeInfoIncludeSelfByCode(orgCode, bizTypesList);
		if (orgAdministrativeInfoEntity != null) {
			// 返回部门
			return orgAdministrativeInfoEntity;
		} else {
			// 获取上级部门失败
			LOGGER.error("################查询组织（code：" + orgCode + "）所属的上级部门失败(包括外场)！##########");
			return null;
		}
	}
	/**
	 * 
	 * 通过任务车辆明细ID查询所有的跟踪明细
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-11-8 下午2:06:58
	 */
	public String queryOnthewayGridById() {
		try {
			onthewayVO.setDetailList(onthewayService
					.queryOnthewayGridById(onthewayVO.getTruckTaskDetailId()));
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.error(e);
			return this.returnError(e);
		}
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
			fileName = onthewayService.encodeFileName("车辆在途跟踪情况");
			excelStream = onthewayService.exportTruckDepartOrArriveByTaskNo(onthewayVO.getIds());
		} catch (TfrBusinessException e) {
			return returnError(e);
		}
		return returnSuccess();
	}

	/**
	 * 
	 * 通过车牌号查询车辆的信息，需要调用接口
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-11-8 下午2:06:58
	 */
	public String queryVehicleStatistics() {
		try {
			// 待跟踪长途车辆
			onthewayVO.getVehicleStatistics().setLongVehicleQuantity(
					onthewayService.getLongCount(onthewayVO.getQueryEntity()));
			// 短途GPS离线车辆
			onthewayVO.getVehicleStatistics().setShortVehicleQuantity(
					onthewayService.getShortGPSCount(onthewayVO
							.getQueryEntity()));
			// 接送货GPS离线车辆
			onthewayVO.getVehicleStatistics()
					.setPkpVehicleQuantity(
							onthewayService.getPkpGPSCount(onthewayVO
									.getQueryEntity()));
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.error(e);
			return this.returnError(e);
		}
	}

	/**
	 * 
	 * 查询待跟踪长途车辆
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-11-8 下午2:06:58
	 */
	public String queryLongCount() {
		try {
			// 待跟踪长途车辆
			onthewayVO.getVehicleStatistics().setLongVehicleQuantity(
					onthewayService.getLongCount(onthewayVO.getQueryEntity()));
//			onthewayVO.setOnthewayList(onthewayService.queryOnthewayGrid(
//					onthewayVO.getQueryEntity(), limit, start));
			this.setTotalCount(onthewayService.getCount(onthewayVO
					.getQueryEntity()));
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.error(e);
			return this.returnError(e);
		}
	}

	/**
	 * 
	 * 短途GPS离线车辆
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-11-8 下午2:06:58
	 */
	public String queryShortGPSCount() {
		try {
			// 待跟踪长途车辆
			onthewayVO.getVehicleStatistics().setShortVehicleQuantity(
					onthewayService.getShortGPSCount(onthewayVO
							.getQueryEntity()));
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.error(e);
			return this.returnError(e);
		}
	}

	/**
	 * 
	 * 接送货GPS离线车辆
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-11-8 下午2:06:58
	 */
	public String queryPkpGPSCount() {
		try {
			// 待跟踪长途车辆
			onthewayVO.getVehicleStatistics()
					.setPkpVehicleQuantity(
							onthewayService.getPkpGPSCount(onthewayVO
									.getQueryEntity()));
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.error(e);
			return this.returnError(e);
		}
	}

	/**
	 * 
	 * 手动新增一条跟踪信息
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-11-8 下午2:06:58
	 */
	public String addManual() {
		try {
			onthewayService.addManual(onthewayVO.getManualEntity());
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.error(e);
			return this.returnError(e);
		}
	}

	/**
	 * 
	 * 手动新增一条跟踪信息
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-11-8 下午2:06:58
	 */
	public String queryGpsUrl() {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("carnum", onthewayVO.getVehicleNo());
			map.put("starttime",
					ApiUtil.getDateStr(onthewayVO.getActualDepartTime()));
			map.put("endtime",
					ApiUtil.getDateStr(onthewayVO.getPlanArriveTime()));
			String gpsUrl = ApiUtil
					.getUri(Constants.getKeyValue("method"), map);
			onthewayVO.setGpsUrl(gpsUrl);
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.error(e);
			return this.returnError(e);
		}
	}

	/************************************/
	/******* 车辆明细信息的接口 *******/
	private IOnthewayService onthewayService;
	private IMotorcadeService motorcadeService;
	
	/** 导出Excel 文件流*/
	private InputStream excelStream;
	/** 导出Excel 文件名*/
	private String fileName;
	/** ***** 底层数据结构 ******. */
	IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;

	/**
	 * 
	 */
	private OnthewayVO onthewayVO = new OnthewayVO();

	public void setMotorcadeService(IMotorcadeService motorcadeService) {
		this.motorcadeService = motorcadeService;
	}

	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	/**
	 * 设置 *********************************.
	 * 
	 * @param onthewayService
	 *            the new *********************************
	 */
	public void setOnthewayService(IOnthewayService onthewayService) {
		this.onthewayService = onthewayService;
	}

	/**
	 * 
	 * 
	 * @param onthewayVO
	 */
	public void setOnthewayVO(OnthewayVO onthewayVO) {
		this.onthewayVO = onthewayVO;
	}

	/**
	 * 
	 * 
	 * @return
	 */
	public OnthewayVO getOnthewayVO() {
		return onthewayVO;
	}
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

}