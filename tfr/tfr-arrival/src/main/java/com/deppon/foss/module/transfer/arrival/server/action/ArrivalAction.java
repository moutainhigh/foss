/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
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
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-arrival
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/arrival/server/action/ArrivalAction.java
 *  
 *  FILE NAME          :ArrivalAction.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.arrival.server.action;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressBranchSalesDeptService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IMotorcadeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISecurityTfrMotorcadeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressBranchSalesDeptEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.MotorcadeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SecurityTfrMotorcadeEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.arrival.api.server.service.IArrivalService;
import com.deppon.foss.module.transfer.arrival.api.shared.define.ArrivalConstant;
import com.deppon.foss.module.transfer.arrival.api.shared.domain.ArrivalEntity;
import com.deppon.foss.module.transfer.arrival.api.shared.domain.QueryArrivalEntity;
import com.deppon.foss.module.transfer.arrival.api.shared.dto.LeasedTruckDTO;
import com.deppon.foss.module.transfer.arrival.api.shared.dto.OperationDTO;
import com.deppon.foss.module.transfer.arrival.api.shared.dto.PlatformDTO;
import com.deppon.foss.module.transfer.arrival.api.shared.dto.QueryPlatformDTO;
import com.deppon.foss.module.transfer.arrival.api.shared.dto.TruckActionDetailDto;
import com.deppon.foss.module.transfer.arrival.api.shared.dto.VehicleInfoDTO;
import com.deppon.foss.module.transfer.arrival.api.shared.vo.ArrivalVO;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.departure.api.shared.define.DepartureConstant;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;
import com.deppon.foss.util.define.FossConstants;


/**
 * 车辆到达WEB层.
 * 
 * 系统默认显示交接单/配载单中到达部门为本部门的所有车辆。
 * 
 * 车辆业务类型：有装车任务的车辆记录为任务车辆；
 * 公司任务车辆类型分为长途、短途、接送货；
 * 长途，则有配载车次号，短途则有交接单号，且长途、短途都有交接目的站；
 * 接送货类型的车辆有交接目的站的则显示，无目的站的则标记类型为接送货。
 * 
 * 
 * 查询车辆到达情况数据来源：
 * 系统交接产生的车辆数据：
 * 1.	交接目的站为本部门的所有车辆，来源新增交接单、新增配载单；此类车辆有到达时效要求。
 * 不是系统交接产生的车辆数据：
 * 2.	车辆数据不是交接产生的，此车辆无计划到达时效要求（如：GPS系统没有集中接送货车辆的待跟踪清单）；如集中接送货返回运作外场的车辆。
 * 3.	此类车辆只有“PDA获取车辆到达时间或交接获取车辆到达时间”后，才显示在车辆到达时间列表中。
 * 
 * 
 * 到达情况：
 * 未到：为在途车辆，指发车计划中车辆已出发但未到达目的站的车辆，数据来源“新增配载单、新增交接单”。
 * 已到：发车计划中已到达本部门的车辆（到达时间字段不为空）；
 * 晚到：系统记录，已到车辆的到达时间字段与发车计划中规定的到达时间相比，晚于规定时间的，记录为晚到。
 * 车辆规定到达时间：为车辆出发时间+运行时长，来源发车时效基础资料。
 * 
 * 已经建立装车任务的长途、短途、接送货的在途的且未到达的车辆，
 * 记录的到达时间才能作为车辆到港时间，车辆到港时间参与时效考核。
 * 任务车辆以外的车辆到达，不在此查询列表。
 * 
 * 
 * 点击到达确认时：
 * 系统需检索此车牌号（交接单号/配载单号）是否有GPS或PDA获取车辆到达时间，假如检索结果为有时，
 * 系统提示“此车辆已有车辆到达时间，不允许再次到达确认”；假如检索结果为无时，则执行到达确认操作。
 * 在手动卸货确认结束录入时，当选择车辆为本运作的整车到达业务时，允许整车货物到达入库；即校验为整车到达车辆时，允许无卸货开始时间，
 * 入库的库区不强制整车业务时，必须填写）；
 * 点击发车确认时：
 * 系统需检索此车牌号（交接单号/配载单号）是否有GPS或PDA获取车辆出发时间，假如检索结果为有时，
 * 系统提示“此车辆已有车辆出发时间，不允许再次发车确认”；假如检索结果为无时，则执行发车确认操作。
 * 
 * 
 * 只有交接单中的到达部门和本部门一致的的车辆，才能做到达确认的操作。
 * 
 * 
 * 取消按钮业务操作规则：
 * 只能对人工做到达确认的车辆（即到时时间获取类型为交接获取的）做取消到达时间操作；
 * GPS及PDA获取的车辆到达时间不能执行取消操作，取消前的车辆状态为“已到达”，取消之后车辆状态为“已出发”，
 * 假如GPS或PDA已做到达的，车辆状态不变。
 * 只能对人工做发车确认的车辆（即出发时间获取类型为交接获取的）做取消出发时间操作；
 * GPS与PDA获取的车辆出发时间不能执行取消操作，取消前的车辆状态为“已出发”，取消后车辆状态变为“待放行”，假如GPS或PDA已做出发的，车辆状态不变。
 * 开始卸车的车辆：则不能取消车辆到达确认操作。
 * 车辆已经出发的：则不能取消发车确认操作。
 * 
 * 
 * 
 * 
 * 车辆到达时间获取规则：
 * 车辆到达分属营业部及外场。
 * 车辆到达时间获取方式：分为GPS获取车辆到达时间、PDA获取到达时间、交接获取车辆到达时间（到达确认）；
 * 营业部：为GPS获取、交接获取车辆到达时间两种；外场：为GPS获取、PDA获取、交接获取（手工）三种；
 * 系统保留任一获取方式的车辆到达时间记录，但在到达界面中只显示唯一一个，显示默认优先级为GPS获取、PDA获取、交接获取。
 * 如果：GPS获取时间为最后获取，则系统自动更新列表信息且显示在查询界面中。
 * 
 * 
 * 
 * 颜色区分显示原则：
 * 在车辆规定出发时间30分钟后，仍未有车辆出发时间的，此条车辆信息有颜色区分，警示车队调度，由调度确定是否进行发车确认。
 * 在车辆规定到达时间30分钟后，仍未有车辆到达时间的，此条车辆信息有颜色区分，警示车队调度，由调度确定是否进行到达确认。
 * 车辆规定出发时间、车辆规定到达时间，参见：线路发车时效标准基础资料。
 * 车辆规定到达时间=为车辆出发时间+运行时长。
 * 
 * 
 * 
 * 一个车辆对应多个交接单，当交接单中的到达部门为本部门时 ，此交接单才会记录为车辆到达。
 * 车辆中对应的其他交接单状态仍为“未到达”。
 * 
 * 
 * 车辆出发时间获取规则：
 * 车辆出发分属营业部及外场。
 * 车辆出发时间获取方式：分为出发时间（GPS获取）、出发时间（PDA获取）、出发时间（PDA获取）；
 * 营业部：为GPS获取、交接获取（发车确认）车辆出发时间两种；
 * 外场：为GPS获取、PDA获取、交接获取（手工）三种；
 * 系统保留任一获取方式的车辆出发时间记录，但在到达界面中只显示唯一一个，显示默认优先级为GPS获取、PDA获取、交接获取。
 * 
 * 
 * 点击确认分配按钮，
 * 系统保留预分配月台记录。
 * 对应月台的可用时间段减去已经“已预分配的”。
 * 月台分配记录为已分配。
 * 未做月台预分配的记录为未分配。
 * 
 * 
 * 车辆出发部门为本部门，才能做发车确认。
 * 车辆到达部门为本部门，才能做到达确认。
 * 车辆出发或到达部门为本部门，才能执行证件包上交管理。
 * 车辆到达部门为本部门，才能进行外请车尾款支付确认。
 * 
 * 
 * 系统默认显示发车计划中到达部门为本部门的所有车辆。
 * 
 * 
 * 车辆中配载单到达部门为本部门的才会在界面显示尾款未结清金额。
 * 只有长途外请车才可以结算。
 * 
 * 
 *当前登录部门为外场运作部门时，
 *“查询车辆到达情况”界面，点击发车确认的车辆，产生一条待放行信息，车辆状态记录为待放行，出发放行类型记录为“任务车辆”。
 * 
 * 
 * 
 * 当车辆到达部门为营业部时，在车辆出发时，GPS将车辆预计到达时间推送给到达部门。
 * 
 * 
 * 
 * 显示列表中的预计到达时间高亮显示：
 * 当预计到达时间晚于计划到达时间两个小时（时间差2个小时可以配置）时，此条信息高亮显示。
 * 计划到达时间=车辆时间出发时间+运行时长（来源发车计划时效表）。
 * 预计到达时间来源GPS或手动跟踪记录（无GPS车辆）。
 * 
 * 
 * @author foss-liubinbin(for IBM)
 * @date 2012-12-24 下午6:49:47
 */
public class ArrivalAction extends AbstractAction {
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ArrivalAction.class);

	/** 导出Excel 文件流*/
	private InputStream excelStream;
	/** 导出Excel 文件名*/
	private String fileName;
	
	/**
	 * orgAdministrativeInfoService
	 */
	private IOrgAdministrativeInfoService OrgAdministrativeInfoService;

	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		OrgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	/**
	 * 查询到达情况.
	 * 
	 * @return the string
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-26 下午2:35:51
	 * @modifier sunjianbo
	 * @mtime  2014-5-19 上午9:25:44
	 */
	@JSON
	public String queryDepartureGrid() {
		try {
			QueryArrivalEntity queryEntity = arrivalVO
					.getQueryDepartureEntity();
			// 只能查询出发部门或者到达部门为当前用户所在外场或者营业部的数据
			
			String transDepartment = null;
			String currentDeptCode = FossUserContext.getCurrentDeptCode();
			//根据当前部门编码查询保安组信息
			SecurityTfrMotorcadeEntity securityTfrMotorcadeEntity = new SecurityTfrMotorcadeEntity();
			securityTfrMotorcadeEntity.setSecurityCode(currentDeptCode);
			securityTfrMotorcadeEntity.setActive("Y");
			List<SecurityTfrMotorcadeEntity> securityTfrMotorcadeEntityList = securityTfrMotorcadeService.querySecurityTfrMotorcadeListBySecurityCode(securityTfrMotorcadeEntity, ConstantsNumberSonar.SONAR_NUMBER_10, 0);
			//如果是保安组，以保安组服务外场替换当前部门
			if(null != securityTfrMotorcadeEntityList && securityTfrMotorcadeEntityList.size() > 0 ){
				//获取保安组服务外场
				String transCenterCode = securityTfrMotorcadeEntityList.get(0).getTranscenterCode();
				//获取保安组服务车队
				transDepartment = securityTfrMotorcadeEntityList.get(0).getMotorcadeCode();
				if(StringUtils.isBlank(transCenterCode)){
					LOGGER.error("当前保安组服务外场未找到。");
					throw new TfrBusinessException("当前保安组服务外场未找到。", "");
				}else{
					currentDeptCode = transCenterCode;
				}
			}
			List<String> strOrgList = queryTransCenterChildrenCodes(currentDeptCode);
			if (strOrgList == null) {
				strOrgList = new ArrayList<String>();
				strOrgList.add(currentDeptCode);
			}
			//若保安组服务车队存在，把车队对应外场加入list
			if(StringUtil.isNotBlank(transDepartment)){
				MotorcadeEntity securitymotorcade = motorcadeService.queryMotorcadeByCode(transDepartment);
				if(securitymotorcade!=null&&securitymotorcade.getTransferCenter()!=null){
					strOrgList.add(securitymotorcade.getTransferCenter());
				}
			}
			//取得车队
			String motorcadeCode = OrgAdministrativeInfoService.queryDepartureOrArriveFleet(FossUserContext.getCurrentUser().getEmployee().getEmpCode());
			if(motorcadeCode!=null){//判断该部门是否是车队
				MotorcadeEntity motorcade = motorcadeService.queryMotorcadeByCode(motorcadeCode);
				if(motorcade!=null&&motorcade.getTransferCenter()!=null){
					strOrgList.add(motorcade.getTransferCenter());
				}
			}
			//判定营业部是否有对应快递分部，若有查询出该分部的到达车辆信息
			ExpressBranchSalesDeptEntity  branchSalesDeptEntity = new ExpressBranchSalesDeptEntity();
			branchSalesDeptEntity.setSalesDeptCode(FossUserContext.getCurrentDeptCode());
			ExpressBranchSalesDeptEntity branchSalesDept= expressBranchSalesDeptService.
					queryByExpressBranchSalesDeptCode(branchSalesDeptEntity);
			if(branchSalesDept !=null){
				strOrgList.add(branchSalesDept.getExpressBranchCode());
				
			}
			queryEntity.setOrigOrgCodes(strOrgList);
			List<ArrivalEntity> arrivalList = arrivalService.queryArrivalGrid(
					queryEntity, this.limit, this.start);
			arrivalVO.setDepartureList(arrivalList);
			this.setTotalCount(arrivalService.getCount(queryEntity));
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.error(e.getMessage(), e);
			return this.returnError(e);
		}
	}
	
	/**
	 * 
	 * 根据车辆任务ID查询出当前任务的取消出发记录
	 * @author 163580
	 * @date 2014-1-20 上午10:14:31
	 * @return
	 * @see
	 */
	@JSON
	public String queryCancelDepartureGrid() {
		try{
			//车辆任务明细ID
			String truckTaskDetailId = arrivalVO.getOperationDTO().getId();
			List<TruckActionDetailDto> truckActionDetailDtos = arrivalService.queryCancelDepartureGrid(truckTaskDetailId);
			arrivalVO.setTruckActionDetailDtos(truckActionDetailDtos);
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.error(e.getMessage(), e);
			return this.returnError(e);
		}
	}
	
	/**
	 * 
	 * 根据车辆任务ID查询出当前任务的取消到达记录
	 * @author 163580
	 * @date 2014-1-20 上午10:14:31
	 * @return
	 * @see
	 */
	@JSON
	public String queryCancelArrivalGrid() {
		try{
			//车辆任务明细ID
			String truckTaskDetailId = arrivalVO.getOperationDTO().getId();
			List<TruckActionDetailDto> truckActionDetailDtos = arrivalService.queryCancelArrivalGrid(truckTaskDetailId);
			arrivalVO.setTruckActionDetailDtos(truckActionDetailDtos);
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.error(e.getMessage(), e);
			return this.returnError(e);
		}
	}

	/**
	 * 根据用户当前部门获取相应大部门(车队)
	 * @param currentOrgCode 当前部门
	 * @author 097457-foss-wangqiang
	 * @date 2012-12-3 下午2:43:00
	 */
	private OrgAdministrativeInfoEntity getBigOrg(String currentOrgCode){
		//设置查询参数
		List<String> bizTypesList = new ArrayList<String>();
		//外场类型
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
	 * 
	 * 查询本部门到达
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2013-4-2 下午5:21:29
	 * @modifier sunjianbo
	 * @mtime  2014-5-19 上午9:26:00
	 */
	@JSON
	public String queryArrivalGrid() {
		try {
			QueryArrivalEntity queryEntity = arrivalVO.getQueryArriveEntity();
			// 只能查询出发部门或者到达部门为当前用户所在外场或者营业部的数据
			
			String transDepartment = null;
			String currentDeptCode = FossUserContext.getCurrentDeptCode();
			//根据当前部门编码查询保安组信息
			SecurityTfrMotorcadeEntity securityTfrMotorcadeEntity = new SecurityTfrMotorcadeEntity();
			securityTfrMotorcadeEntity.setSecurityCode(currentDeptCode);
			securityTfrMotorcadeEntity.setActive("Y");
			List<SecurityTfrMotorcadeEntity> securityTfrMotorcadeEntityList = securityTfrMotorcadeService.querySecurityTfrMotorcadeListBySecurityCode(securityTfrMotorcadeEntity, ConstantsNumberSonar.SONAR_NUMBER_10, 0);
			//如果是保安组，以保安组服务外场替换当前部门
			if(null != securityTfrMotorcadeEntityList && securityTfrMotorcadeEntityList.size() > 0 ){
				//获取保安组服务外场
				String transCenterCode = securityTfrMotorcadeEntityList.get(0).getTranscenterCode();
				//获取保安组服务车队
				transDepartment = securityTfrMotorcadeEntityList.get(0).getMotorcadeCode();
				if(StringUtils.isBlank(transCenterCode)){
					LOGGER.error("当前保安组服务外场未找到。");
					throw new TfrBusinessException("当前保安组服务外场未找到。", "");
				}else{
					currentDeptCode = transCenterCode;
				}
			}
			List<String> strOrgList = queryTransCenterChildrenCodes(currentDeptCode);
			if (strOrgList == null) {
				strOrgList = new ArrayList<String>();
				strOrgList.add(currentDeptCode);
			}
			//若保安组服务车队存在，把车队对应外场加入list
			if(StringUtil.isNotBlank(transDepartment)){
				MotorcadeEntity securitymotorcade = motorcadeService.queryMotorcadeByCode(transDepartment);
				if(securitymotorcade!=null&&securitymotorcade.getTransferCenter()!=null){
					strOrgList.add(securitymotorcade.getTransferCenter());
				}
			}
			//取得车队
			String motorcadeCode = OrgAdministrativeInfoService.queryDepartureOrArriveFleet(FossUserContext.getCurrentUser().getEmployee().getEmpCode());
			if(motorcadeCode!=null){//判断该部门是否是车队
				MotorcadeEntity motorcade = motorcadeService.queryMotorcadeByCode(motorcadeCode);
				if(motorcade!=null&&motorcade.getTransferCenter()!=null){
					strOrgList.add(motorcade.getTransferCenter());
				}
			}
			//判定营业部是否有对应快递分部，若有查询出该分部的到达车辆信息
			ExpressBranchSalesDeptEntity  branchSalesDeptEntity = new ExpressBranchSalesDeptEntity();
			branchSalesDeptEntity.setSalesDeptCode(FossUserContext.getCurrentDeptCode());
			ExpressBranchSalesDeptEntity branchSalesDept= expressBranchSalesDeptService.
					queryByExpressBranchSalesDeptCode(branchSalesDeptEntity);
			if(branchSalesDept !=null){
				strOrgList.add(branchSalesDept.getExpressBranchCode());
				
			}
			queryEntity.setDestOrgCodes(strOrgList);
			List<ArrivalEntity> arrivalList = arrivalService.queryArrivalGrid(
					queryEntity, this.limit, this.start);
			arrivalVO.setArrivalList(arrivalList);
			this.setTotalCount(arrivalService.getCount(queryEntity));
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.error(e.getMessage(), e);
			return this.returnError(e);
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
				.queryOrgAdministrativeInfoIncludeSelfByCode(orgCode,
						bizTypesList);
		if (orgAdministrativeInfoEntity != null) {
			// 返回部门
			return orgAdministrativeInfoEntity;
		} else {
			// 获取上级部门失败
			LOGGER.error("################查询组织（code：" + orgCode
					+ "）所属的上级部门失败(包括外场)！##########");
			return null;
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
			arrivalVO.setArrivalEntity(arrivalEntity);
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.error(e.getMessage(), e);
			return this.returnError(e);
		}
	}

	/**
	 * 查询外请车情况（当前部门出发）.
	 * 
	 * @return the string
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-26 下午2:35:51
	 * @modifier sunjianbo
	 * @mtime  2014-5-19 上午9:26:08
	 */
	@JSON
	public String queryLeasedDepartureTruck() {
		try {
			QueryArrivalEntity queryEntity = arrivalVO
					.getQueryDepartureEntity();
			// 只能查询出发部门或者到达部门为当前用户所在外场或者营业部的数据			
			
			String transDepartment = null;
			String currentDeptCode = FossUserContext.getCurrentDeptCode();
			//根据当前部门编码查询保安组信息
			SecurityTfrMotorcadeEntity securityTfrMotorcadeEntity = new SecurityTfrMotorcadeEntity();
			securityTfrMotorcadeEntity.setSecurityCode(currentDeptCode);
			securityTfrMotorcadeEntity.setActive("Y");
			List<SecurityTfrMotorcadeEntity> securityTfrMotorcadeEntityList = securityTfrMotorcadeService.querySecurityTfrMotorcadeListBySecurityCode(securityTfrMotorcadeEntity, ConstantsNumberSonar.SONAR_NUMBER_10, 0);
			//如果是保安组，以保安组服务外场替换当前部门
			if(null != securityTfrMotorcadeEntityList && securityTfrMotorcadeEntityList.size() > 0 ){
				//获取保安组服务外场
				String transCenterCode = securityTfrMotorcadeEntityList.get(0).getTranscenterCode();
				//获取保安组服务车队
				transDepartment = securityTfrMotorcadeEntityList.get(0).getMotorcadeCode();
				if(StringUtils.isBlank(transCenterCode)){
					LOGGER.error("当前保安组服务外场未找到。");
					throw new TfrBusinessException("当前保安组服务外场未找到。", "");
				}else{
					currentDeptCode = transCenterCode;
				}
			}
			List<String> strOrgList = queryTransCenterChildrenCodes(currentDeptCode);
			if (strOrgList == null) {
				strOrgList = new ArrayList<String>();
				strOrgList.add(currentDeptCode);
			}
			//若保安组服务车队存在，把车队对应外场加入list
			if(StringUtil.isNotBlank(transDepartment)){
				MotorcadeEntity securitymotorcade = motorcadeService.queryMotorcadeByCode(transDepartment);
				if(securitymotorcade!=null&&securitymotorcade.getTransferCenter()!=null){
					strOrgList.add(securitymotorcade.getTransferCenter());
				}
			}
			queryEntity.setOrigOrgCodes(strOrgList);
			// 只能查询出发部门或者到达部门为当前用户所在外场或者营业部的数据
			queryEntity.setOrigOrgCode(currentDeptCode);
			LeasedTruckDTO dto = arrivalService.queryLeasedTruck(queryEntity);
			dto.setEndTime(queryEntity.getEndTime());
			dto.setStartTime(queryEntity.getStartTime());
			arrivalVO.setLeasedDepartureTruckDTO(dto);
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.error(e.getMessage(), e);
			return this.returnError(e);
		}
	}

	/**
	 * 查询外请车情况（当前部门到达）.
	 * 
	 * @return the string
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-26 下午2:35:51
	 * @modifier sunjianbo
	 * @mtime  2014-5-19 上午9:26:17
	 */
	@JSON
	public String queryLeasedArriveTruck() {
		try {
			QueryArrivalEntity queryEntity = arrivalVO.getQueryArriveEntity();
			// 只能查询出发部门或者到达部门为当前用户所在外场或者营业部的数据
			
			String transDepartment = null;
			String currentDeptCode = FossUserContext.getCurrentDeptCode();
			//根据当前部门编码查询保安组信息
			SecurityTfrMotorcadeEntity securityTfrMotorcadeEntity = new SecurityTfrMotorcadeEntity();
			securityTfrMotorcadeEntity.setSecurityCode(currentDeptCode);
			securityTfrMotorcadeEntity.setActive("Y");
			List<SecurityTfrMotorcadeEntity> securityTfrMotorcadeEntityList = securityTfrMotorcadeService.querySecurityTfrMotorcadeListBySecurityCode(securityTfrMotorcadeEntity, ConstantsNumberSonar.SONAR_NUMBER_10, 0);
			//如果是保安组，以保安组服务外场替换当前部门
			if(null != securityTfrMotorcadeEntityList && securityTfrMotorcadeEntityList.size() > 0 ){
				//获取保安组服务外场
				String transCenterCode = securityTfrMotorcadeEntityList.get(0).getTranscenterCode();
				//获取保安组服务车队
				transDepartment = securityTfrMotorcadeEntityList.get(0).getMotorcadeCode();
				if(StringUtils.isBlank(transCenterCode)){
					LOGGER.error("当前保安组服务外场未找到。");
					throw new TfrBusinessException("当前保安组服务外场未找到。", "");
				}else{
					currentDeptCode = transCenterCode;
				}
			}
			queryEntity.setDestOrgCode(currentDeptCode);
			List<String> strOrgList = queryTransCenterChildrenCodes(currentDeptCode);
			if (strOrgList == null) {
				strOrgList = new ArrayList<String>();
				strOrgList.add(currentDeptCode);
			}
			//若保安组服务车队存在，把车队对应外场加入list
			if(StringUtil.isNotBlank(transDepartment)){
				MotorcadeEntity securitymotorcade = motorcadeService.queryMotorcadeByCode(transDepartment);
				if(securitymotorcade!=null&&securitymotorcade.getTransferCenter()!=null){
					strOrgList.add(securitymotorcade.getTransferCenter());
				}
			}
			queryEntity.setDestOrgCodes(strOrgList);
			LeasedTruckDTO dto = arrivalService.queryLeasedTruck(queryEntity);
			dto.setEndTime(queryEntity.getEndTime());
			dto.setStartTime(queryEntity.getStartTime());
			arrivalVO.setLeasedArriveTruckDTO(dto);
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.error(e.getMessage(), e);
			return this.returnError(e);
		}
	}

	/**
	 * 根据条件查询最优月台的信息.
	 * 
	 * @return the string
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-26 下午2:35:51
	 */
	@JSON
	public String queryPlatformGrid() {
		try {
			QueryPlatformDTO dto = arrivalVO.getQueryPlatformDTO();
			arrivalVO.setPlatformList(arrivalService.queryPlatformGrid(dto));
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.error(e.getMessage(), e);
			return this.returnError(e);
		}
	}

	/**
	 * 确认选择一条月台.
	 * 
	 * @return the string
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-11-20 上午11:48:12
	 */
	@JSON
	public String confirmPlatform() {
		try {
			arrivalService.confirmPlatform(arrivalVO.getVehicleInfoDTO());
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.error(e.getMessage(), e);
			return this.returnError(e);
		}
	}

	/**
	 * 发车确认.
	 * 
	 * @return the string
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-11-20 上午11:48:12
	 */
	@JSON
	public String departConfirm() {
		try {
			// // 判断当前的部门是否营业部，只有营业部才能发车确认
			// if (!FossConstants.YES.equals(FossUserContext.getCurrentDept()
			// .getSalesDepartment())) {
			// LOGGER.error("只有营业部才能发车确认，请到《放行申请界面》做车辆出发");
			// throw new TfrBusinessException("只有营业部才能发车确认，请到《放行申请界面》做车辆出发",
			// "");
			// }
			// 确认前先进行验证
//			if (ArrivalConstant.DEPART_ARRIVALED.equals(arrivalVO
//					.getOperationDTO().getArrivalStatus())
//					|| ArrivalConstant.DEPART_LATE_ARRIVALED.equals(arrivalVO
//							.getOperationDTO().getArrivalStatus())) {
//				// 已到跟晚到的记录，不允许发车确认
//				LOGGER.error("已到跟晚到的记录，不允许发车确认");
//				throw new TfrBusinessException("已到跟晚到的记录，不允许发车确认", "");
//			}
			if (!(ArrivalConstant.ARRIVAL_VEHICLE_UNDEPART.equals(arrivalVO
					.getOperationDTO().getVehicleStatus())||ArrivalConstant.ARRIVAL_VEHICLE_ONTHEWAY.equals(arrivalVO
							.getOperationDTO().getVehicleStatus()))) {
				// 只能对待出发的车辆进行发车确认
				LOGGER.error("只能对待出发和在途的车辆进行发车确认");
				throw new TfrBusinessException("只能对待出发和在途的车辆进行发车确认", "");
			}
			// 发车确认
			arrivalService.departConfirm(arrivalVO.getOperationDTO());
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.error(e.getMessage(), e);
			return this.returnError(e);
		}
	}

	/**
	 * 到达确认.
	 * 
	 * @return the string
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-11-20 上午11:48:12
	 */
	@JSON
	public String arrivalConfirm() {
		try {
//			// 确认前先进行验证
//			if (ArrivalConstant.DEPART_ARRIVALED.equals(arrivalVO
//					.getOperationDTO().getArrivalStatus())
//					|| ArrivalConstant.DEPART_LATE_ARRIVALED.equals(arrivalVO
//							.getOperationDTO().getArrivalStatus())) {
//				// 已到达的记录，不允许再次到达确认
//				LOGGER.error("已到达的记录，不允许再次到达确认");
//				throw new TfrBusinessException("已到达的记录，不允许再次到达确认", "");
//			}
//			if (!(ArrivalConstant.ARRIVAL_VEHICLE_ONTHEWAY.equals(arrivalVO
//					.getOperationDTO().getVehicleStatus())||ArrivalConstant.ARRIVAL_VEHICLE_UNDEPART.equals(arrivalVO
//							.getOperationDTO().getVehicleStatus()))) {
//				// 只能在途的车辆进行到达确认
//				LOGGER.error("只能对未到达的车辆进行到达确认");
//				throw new TfrBusinessException("只能对未到达的车辆进行到达确认", "");
//			}
			// 确认车辆到达
			List<String> list = arrivalService.arrivalConfirm(arrivalVO.getOperationDTO());
			if(list==null){
				arrivalVO.setHasAgency(FossConstants.NO);
			}else{
				arrivalVO.setHasAgency(FossConstants.YES);
				arrivalVO.setDetailIds(list);
			}
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.error(e.getMessage(), e);
			return this.returnError(e);
		}
	}

	/**
	 * 取消确认.
	 * 
	 * @return the string
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-11-20 上午11:48:12
	 */
	@JSON
	public String cancleConfirm() {
		try {
			if(DepartureConstant.ACTION_TYPE_CANCEL_DEPART.equals(arrivalVO.getOperationDTO().getActionType())) {
				if (ArrivalConstant.ARRIVAL_VEHICLE_ONTHEWAY.equals(arrivalVO
						.getOperationDTO().getVehicleStatus())) {
					// 取消放行
					// 只能对人工确认的放行做取消操作
					if (!(DepartureConstant.ACTUAL_DEPART_TYPE_MANUAL.equals(arrivalVO.getOperationDTO().getActualDepartType()) || 
							DepartureConstant.ACTUAL_DEPART_TYPE_MANUAL_CONFIRM.equals(arrivalVO.getOperationDTO().getActualDepartType())||
							StringUtil.isBlank(arrivalVO.getOperationDTO().getActualDepartType()))) {
						// 不能对PDA放行的记录做取消操作
						LOGGER.error("不能对"
								+ arrivalVO.getOperationDTO().getActualDepartType()
								+ "放行的记录做取消操作");
						throw new TfrBusinessException("不能对"
								+ arrivalVO.getOperationDTO().getActualDepartType()
								+ "放行的记录做取消操作", "");
					}
					// if (!arrivalVO.getOperationDTO().getOrigOrgCode()
					// .equals(FossUserContext.getCurrentDeptCode())) {
					// // 出发部门为当前部门才能取消放行
					// LOGGER.error("出发部门为当前部门才能取消放行");
					// throw new TfrBusinessException("出发部门为当前部门才能取消放行", "");
					// }
					arrivalService.cancleDepart(arrivalVO.getOperationDTO());
				} else {
					return returnError("无法对当前任务做取消操作!", "");
				}
			} else {
				if (ArrivalConstant.ARRIVAL_VEHICLE_ARRIVED.equals(arrivalVO.getOperationDTO().getVehicleStatus())) {
					// 取消到达
					// 只能对人工确认的到达做取消操作
					if (!DepartureConstant.ACTUAL_DEPART_TYPE_MANUAL.equals(arrivalVO.getOperationDTO().getActualArriveType()) &&
							!DepartureConstant.ACTUAL_DEPART_TYPE_FOSS.equals(arrivalVO.getOperationDTO().getActualArriveType())) {
						// 不能对PDA放行的记录做取消操作
						LOGGER.error("不能对PDA放行的记录做取消操作");
						throw new TfrBusinessException("不能对PDA放行的记录做取消操作", "");
					}
					// if (!arrivalVO.getOperationDTO().getDestOrgCode()
					// .equals(FossUserContext.getCurrentDeptCode())) {
					// // 到达部门为当前部门才能取消到达
					// LOGGER.error("到达部门为当前部门才能取消到达");
					// throw new TfrBusinessException("到达部门为当前部门才能取消到达", "");
					// }
					arrivalService.cancleArrive(arrivalVO.getOperationDTO());
				} else {
					if(ArrivalConstant.ARRIVAL_VEHICLE_UNLOADED.equals(arrivalVO.getOperationDTO().getVehicleStatus())) {
						return returnError("当前任务已卸车, 不能取消!", "");
					}
					return returnError("当前任务未做过到达, 请刷新后再试!", "");
				}
			}
			// 确认车辆到达
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.error(e.getMessage(), e);
			return this.returnError(e);
		}
	}

	/**
	 * 通过车牌号查询车辆的信息，需要调用接口.
	 * 
	 * @return the string
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-11-8 下午2:06:58
	 */
	@JSON
	public String queryVehicleForPlatform() {
		try {
			VehicleInfoDTO dto = arrivalService
					.queryVehicleForPlatform(arrivalVO.getQueryArriveEntity());
			arrivalVO.setVehicleInfoDTO(dto);
			arrivalVO.setPlatformList(new ArrayList<PlatformDTO>());
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.error(e.getMessage(), e);
			return this.returnError(e);
		}
	}

	/**
	 * 通过车牌号查询车辆的信息，需要调用接口.
	 * 
	 * @return the string
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-11-8 下午2:06:58
	 */
	@JSON
	public String validSail() {
		try {
			/*Long l = */arrivalService.validSail(arrivalVO.getQueryArriveEntity()
					.getId());
			arrivalVO.setPlatformList(new ArrayList<PlatformDTO>());
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.error(e.getMessage(), e);
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
			arrivalVO.getQueryDepartureEntity();
			fileName = arrivalService.encodeFileName("车辆出发到达情况");
			excelStream = arrivalService.exportTruckDepartOrArriveByTaskNo(arrivalVO.getIds());
		} catch (TfrBusinessException e) {
			return returnError(e);
		}
		return returnSuccess();
	}
	/**
	 * 
	 * @Description: 处理标签
	 * @return    
	 * @return String    返回类型 
	 * @author: ibm-liubinbin
	 * @throws 
	 * Date:2013-4-24下午4:11:59
	 */
	public String handleLabeledGoods() {
		try {
			arrivalService.handleLabeledGoods(arrivalVO.getDetailIds());
		} catch (TfrBusinessException e) {
			return returnError(e);
		}
		return returnSuccess();
	}

	/**
	 * 处理待办
	 * @author 163580
	 * @date 2014-6-27 下午7:14:11
	 * @return
	 * @see
	 */
	public String handleTodo() {
		try {
			if(StringUtils.isEmpty(arrivalVO.getOperationDTO().getId())) {
				return returnError("请选择一行待处理的数据!");
			}
			String handOverBillNo = arrivalService.handleTodo(arrivalVO.getOperationDTO().getId());
			arrivalVO.setHandOverBillNo(handOverBillNo);
		} catch (BusinessException e) {
			return returnError(e);
		}
		return returnSuccess();
	}

	
	/**
	 * @function (二程接驳出发到达)查询本部门出发的发车任务.
	 * @return the string
	 * @author gongjp
	 * @date 2015-08-25 下午20:08:51
	 */
	@JSON
	public String querySecondCarDepartureGrid(){
		try {
			QueryArrivalEntity queryEntity = arrivalVO.getQueryDepartureEntity();
			List<ArrivalEntity> departureList = arrivalService.querySecondCarDepartureGrid(queryEntity, this.limit, this.start);
			arrivalVO.setDepartureList(departureList);
			this.setTotalCount(arrivalService.getSecondCarDepartureCount(queryEntity));
			return returnSuccess();
		} catch (BusinessException e) {
		LOGGER.error(e.getMessage(), e);
		return this.returnError(e);
	  }
	}
	/**
	 * 二程接驳出发发车确认.
	 * @return the string
	 * @author gongjp
	 * @date 2015-08-27 上午15:11:34
	 */
	@JSON
	public String secondCarDepartConfirm(){
		try{
			OperationDTO dto = arrivalVO.getOperationDTO();
			String empCode = FossUserContext.getCurrentInfo().getEmpCode();
			dto.setDepartOperator(empCode);
			//二程接驳发车确认
			arrivalService.secondCarDepartConfirm(dto);
		  return returnSuccess();
	    } catch (BusinessException e) {
		    LOGGER.error(e.getMessage(), e);
		   return this.returnError(e);
	    }
	}
	/**
	 * 二程接驳本部门到达到达确认.
	 * @return the string
	 * @author gongjp
	 * @date 2015-08-28 上午15:01:24
	 */
	@JSON
	public String secondCarArrivalConfirm(){
		try{
			OperationDTO dto = arrivalVO.getOperationDTO();
			String empCode = FossUserContext.getCurrentInfo().getEmpCode();
			dto.setArrivalOperator(empCode);
			//二程接驳到达确认
			arrivalService.secondCarArrivalConfirm(dto);
		  return returnSuccess();
	    } catch (BusinessException e) {
		    LOGGER.error(e.getMessage(), e);
		   return this.returnError(e);
	    }
	}
	
	
	/**
	 * @function (二程接驳到达本部门查询)查询本部门到达的车辆信息.
	 * @return the string
	 * @author gongjp
	 * @date 2015-08-28 下午09:41:51
	 */
	@JSON
	public String querySecondCarArrivalGrid(){
		try {
			QueryArrivalEntity queryEntity = arrivalVO.getQueryArriveEntity();
			List<ArrivalEntity> arrivalList = arrivalService.querySecondCarArrivalGrid(queryEntity, this.limit, this.start);
			arrivalVO.setArrivalList(arrivalList);
			this.setTotalCount(arrivalService.getSecondCarArrivalCount(queryEntity));
			return returnSuccess();
		} catch (BusinessException e) {
		LOGGER.error(e.getMessage(), e);
		return this.returnError(e);
	  }
		
	}
	
	/**
	 * @function (二程接驳发车确认)校验是否重复发车
	 * @return the string
	 * @author gongjp
	 * @date 2015-09-01 下午18:15:51
	 */
	@JSON
	public String checkSecondCarDepartConfirm(){
		try{
			OperationDTO dto = arrivalVO.getOperationDTO();
			//二程接驳发车确认
			List<ArrivalEntity> list = arrivalService.checkSecondCarDepartConfirm(dto);
			arrivalVO.setDepartureConfirmList(list);
			return returnSuccess();
		}catch (BusinessException e) {
			LOGGER.error(e.getMessage(), e);
			return this.returnError(e);
		}
	}
	
	
	
	/**
	 * @function (二程接驳到达确认)校验是否重复确认
	 * @return the string
	 * @author gongjp
	 * @date 2015-09-01 下午18:16:34
	 */
	@JSON
	public String checkSecondCarArrivalConfirm(){
		try {
			OperationDTO dto = arrivalVO.getOperationDTO();
			//二程接驳到达确认
			List<ArrivalEntity> list = arrivalService.checkSecondCarArrivalConfirm(dto);
			arrivalVO.setArrivalConfirmList(list);
			return returnSuccess();
		} catch (BusinessException e) {
		LOGGER.error(e.getMessage(), e);
		return this.returnError(e);
	   }
	}
	
	
	
	/**
	 * ******************** 默认配置 **********************.
	 */
	private static final long serialVersionUID = -2956858602690171822L;
	/** ******到达服务********. */
	private IArrivalService arrivalService;

	IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	private IMotorcadeService motorcadeService;
	
	private IExpressBranchSalesDeptService expressBranchSalesDeptService;
	/** 
	 * @Fields securityTfrMotorcadeService : 保安组service 
	 */ 
	private ISecurityTfrMotorcadeService securityTfrMotorcadeService;
	
	/**
	 * @Title: setSecurityTfrMotorcadeService 
	 * @Description: setSecurityTfrMotorcadeService
	 * @author sunjianbo
	 * @date 2014-5-17 上午10:18:07 
	 * @param securityTfrMotorcadeService 
	 * @return void
	 * @throws 
	 */ 
	public void setSecurityTfrMotorcadeService(
			ISecurityTfrMotorcadeService securityTfrMotorcadeService) {
		this.securityTfrMotorcadeService = securityTfrMotorcadeService;
	}

	/** *******数据结构********. */
	private ArrivalVO arrivalVO = new ArrivalVO();

	/**
	 * Gets the arrival vo.
	 * 
	 * @return the arrival vo
	 */
	public ArrivalVO getArrivalVO() {
		return arrivalVO;
	}

	/**
	 * Sets the arrival vo.
	 * 
	 * @param arrivalVO
	 *            the new arrival vo
	 */
	public void setArrivalVO(ArrivalVO arrivalVO) {
		this.arrivalVO = arrivalVO;
	}

	/**
	 * Sets the arrival service.
	 * 
	 * @param arrivalService
	 *            the new arrival service
	 */
	public void setArrivalService(IArrivalService arrivalService) {
		this.arrivalService = arrivalService;
	}

	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	public void setMotorcadeService(IMotorcadeService motorcadeService) {
		this.motorcadeService = motorcadeService;
	}

	public final void setExpressBranchSalesDeptService(
			IExpressBranchSalesDeptService expressBranchSalesDeptService) {
		this.expressBranchSalesDeptService = expressBranchSalesDeptService;
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