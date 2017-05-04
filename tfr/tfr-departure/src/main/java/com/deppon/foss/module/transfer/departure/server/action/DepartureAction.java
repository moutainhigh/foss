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
 *  PROJECT NAME  : tfr-departure
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/departure/server/action/DepartureAction.java
 *  
 *  FILE NAME          :DepartureAction.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.departure.server.action;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IMotorcadeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISecurityTfrMotorcadeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.MotorcadeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SecurityTfrMotorcadeEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.departure.api.handle.DepartureHandle;
import com.deppon.foss.module.transfer.departure.api.server.service.IDepartureService;
import com.deppon.foss.module.transfer.departure.api.server.service.ISharedService;
import com.deppon.foss.module.transfer.departure.api.shared.define.DepartureConstant;
import com.deppon.foss.module.transfer.departure.api.shared.domain.QueryDepartEntity;
import com.deppon.foss.module.transfer.departure.api.shared.domain.TruckDepartEntity;
import com.deppon.foss.module.transfer.departure.api.shared.dto.VehiclePrintDTO;
import com.deppon.foss.module.transfer.departure.api.shared.vo.DepartureVO;
import com.deppon.foss.module.transfer.load.api.shared.define.LoadConstants;
import com.deppon.foss.util.define.FossConstants;
/**
 * 
 * 查询车辆放行 系统默认显示数据原则：
 * 公司车、短途班车班车外请车统一由调度来进行放行操作
 * ，长途外请车由外场来放行（
 * 长途外请车在外场报到，公司车及短途班车班车外请车在车队报到）。
 * 对应公司车辆及短途班车班车外请车辆的临时放行为车队调度处理；
 * 长途外请车辆的临时放行为外场操作员处理；
 * 车队调度及外场操作员管理的车辆列表数据源不同。
 * 司机信息公开规则：公司长途车辆的司机
 * 、司机电话只有车队调度可以看到；其他类型车辆的司机信息都可以公开
 * 。（通过部门来验证） 司机信息来源参见“通过车牌查找司机信息”。
 * 点击任务确认按钮，车辆状态记录为“待放行”，保安PDA校验放行需求。
 * 临时任务查询界面
 * ：只能对日期为当日的年审/季审、保养的车辆执行任务确认操作，否则系统报错。
 * 1.每个车辆放行，后台增加放行流水号列，在查询车辆放行情况页面，
 * 可以查看车辆放行信息明细。 2.点击关闭按钮时； 任务车辆页面：
 * 当车牌号不为空时，系统信息提示，
 * “还有任务车辆，是否继续放行？”，选择是，
 * 则不退出页面，选择否，则退出页面； 当车牌号为空时，直接退出页面。
 * 临时任务车辆页面： 当车牌号不为空时，系统信息提示，
 * “还有临时任务车辆，是否继续放行？
 * ”，选择是，则不退出页面，选择否，则退出页面；
 * 当车牌号为空时，直接退出页面。 车牌号与司机信息绑定关系：
 * 人工放行界面录入车牌号，后台依据车牌号，关联车辆基础资料，
 * 检索到此车牌号的车辆归属类型为外请车时
 * ，从外请车辆基础资料中关联处司机的姓名、联系电话；
 * 后台依据车牌号，判断车辆归属类型为公司车
 * ，检索司机字段是否为空，为空时系统提示车队调度手动录入；司机工号不为空时，
 * 后台依据司机工号到车辆基础资料中关联司机姓名及联系方式。
 * 关联司机信息，显示在查询界面。 放行方式：记录为纸质确认放行。
 * 放行状态：纸质放行的车辆，车辆状态记录为已出发。
 * 打印放行条模板，参见系统用例“查询车辆放行情况”。
 * 打印放行条按钮：此按钮，
 * 针对来源LMS系统的年审/季审、保养数据来开具纸质放行条。
 * 
 * 
 * 
 * @author foss-liubinbin(for IBM)
 * @date 2013-3-19 上午10:34:55
 */
public class DepartureAction extends AbstractAction {
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LogManager
			.getLogger(DepartureAction.class);
	
	/** 导出Excel 文件流*/
	private InputStream excelStream;
	/** 导出Excel 文件名*/
	private String fileName;
	/**
	 * 查询运单库存.
	 * 
	 * @return the string
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-26 下午2:35:51
	 * @modifier sunjianbo
	 * @mtime  2014-5-19 上午9:49:37
	 */
	@JSON public String queryDepart() {
		try {
			String departType = departureVO.getQueryEntity().getDepartType();
			// 判断是纸质放行还是pda放行
			if (DepartureConstant.DEPART_TYPE_MANUAL_CODE.equals(departType)) {
				// 传入这个值表示sql执行的时候有这个条件
				departureVO.getQueryEntity().setManualDepartUserCode(
						DepartureConstant.MANUAL_OR_PDA_NOT_NULL);
			}
			if (DepartureConstant.DEPART_TYPE_PDA_CODE.equals(departType)) {
				// 传入这个值表示sql执行的时候有这个条件
				departureVO.getQueryEntity().setPdaDepartUserCode(
						DepartureConstant.MANUAL_OR_PDA_NOT_NULL);
			}
			departureVO.getQueryEntity().setBeginTime(
					DepartureHandle.getDate(departureVO.getQueryEntity()
							.getBeginDate(), DepartureHandle.DATE_FORMAT_1));
			departureVO.getQueryEntity().setEndTime(
					DepartureHandle.getDate(departureVO.getQueryEntity()
							.getEndDate(), DepartureHandle.DATE_FORMAT_1));
			
			String transDepartment = null;
			String currentDeptCode = FossUserContext.getCurrentDeptCode();
			//根据当前部门编码查询保安组信息
			SecurityTfrMotorcadeEntity securityTfrMotorcadeEntity = new SecurityTfrMotorcadeEntity();
			securityTfrMotorcadeEntity.setSecurityCode(currentDeptCode);
			securityTfrMotorcadeEntity.setActive("Y");
			List<SecurityTfrMotorcadeEntity> securityTfrMotorcadeEntityList = securityTfrMotorcadeService.querySecurityTfrMotorcadeListBySecurityCode(securityTfrMotorcadeEntity, LoadConstants.SONAR_NUMBER_10, 0);
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
			// 只能查询出发部门或者到达部门为当前用户所在外场或者营业部的数据
			departureVO.getQueryEntity().setCurrentOrgCode(
					currentDeptCode);
			

//			FossUserContext.getCurrentDept();
			List<String> strOrgList =queryTransCenterChildrenCodes(currentDeptCode);
			if (strOrgList == null) {
				strOrgList = new ArrayList<String>();
				strOrgList.add(currentDeptCode);
			}
			//若保安组服务车队存在，把车队对应外场加入list
			if(StringUtil.isNotBlank(transDepartment)){
				MotorcadeEntity securitymotorcade = motorcadeService.queryMotorcadeByCode(transDepartment);
				if(securitymotorcade!=null&&securitymotorcade.getTransferCenter()!=null)
				{
					strOrgList.add(securitymotorcade.getTransferCenter());
				}
			}

//			//如果是调度组，取得车队所属的外场
//			if(FossUserContext.getCurrentDept().checkDispatchTeam())
//			{
				//取得车队
				OrgAdministrativeInfoEntity org = getBigOrg(FossUserContext
						.getCurrentDeptCode());
				//sonar-352203-合并if判断
//				if(org!=null)
//				{//判断该部门大部门是否是车队
					if(org!=null && FossConstants.YES.equals(org.getTransDepartment()))
					{
						MotorcadeEntity motorcade = motorcadeService.queryMotorcadeByCode(org.getCode());
						if(motorcade!=null&&motorcade.getTransferCenter()!=null)
						{
							strOrgList.add(motorcade.getTransferCenter());
						}
					}
//				}
//			}
			
			//如果是车队，取得车队所属的外场
			if(FossConstants.YES.equals(FossUserContext.getCurrentDept().getTransDepartment()))
			{
				MotorcadeEntity motorcade = motorcadeService.queryMotorcadeByCode(FossUserContext.getCurrentDeptCode());
				if(motorcade!=null&&motorcade.getTransferCenter()!=null)
				{
					strOrgList.add(motorcade.getTransferCenter());
				}
			}
			departureVO.getQueryEntity().setCurrentOrgCodes(strOrgList);
			List<TruckDepartEntity> departList = departureService.queryDepart(
					departureVO.getQueryEntity(), this.getLimit(),
					this.getStart());
			if (departList != null) {
				for (TruckDepartEntity truckDepartEntity : departList) {
					// 实际出发方式
					if (truckDepartEntity.getGpsDepartTime() != null) {
						LOGGER.info("GPS放行");
						truckDepartEntity
								.setActualDepartType(DepartureConstant.DEPART_TYPE_GPS);
						truckDepartEntity.setActualDepartTime(truckDepartEntity
								.getGpsDepartTime());
					} else if (truckDepartEntity.getPdaDepartTime() != null) {
						LOGGER.info("PDA放行");
						truckDepartEntity
								.setActualDepartType(DepartureConstant.DEPART_TYPE_PDA);
						truckDepartEntity.setActualDepartTime(truckDepartEntity
								.getPdaDepartTime());
					} else if (truckDepartEntity.getManualDepartTime() != null) {
						truckDepartEntity
								.setActualDepartType(DepartureConstant.DEPART_TYPE_MANUAL);
						truckDepartEntity.setActualDepartTime(truckDepartEntity
								.getManualDepartTime());
					}
				}
			}
			departureVO.setDepartList(departList);
			this.setTotalCount(departureService.getCount(departureVO
					.getQueryEntity()));
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.error(e);
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
		List<String> rstemp = null;
		// 当前部门所对应的外场编码
		String transCenterCode = "";
		OrgAdministrativeInfoEntity transCenter = querySuperiorOrgByOrgCode(currentDeptCode);
		if (transCenter != null) {
			transCenterCode = transCenter.getCode();
		}else{
			transCenter = new OrgAdministrativeInfoEntity();
		}
		// 根据外场查询子孙部门编码
		if (StringUtils.isNotBlank(transCenterCode)) {
			List<OrgAdministrativeInfoEntity> orgs = orgAdministrativeInfoComplexService
					.queryOrgAdministrativeInfoEntityAllSubByCode(transCenterCode);
			//临时存放找到外场下外场的部门
			List<OrgAdministrativeInfoEntity> orgstemp = new ArrayList<OrgAdministrativeInfoEntity>();
			if (CollectionUtils.isNotEmpty(orgs)) {
				rs = new ArrayList<String>();
				rstemp = new ArrayList<String>();
				for (OrgAdministrativeInfoEntity org : orgs) {
					//如果是外场，剔除
					if(org.checkTransferCenter()&&!transCenter.getCode().equals(org.getCode()))
					{
						rstemp.add(org.getCode());
					}
						rs.add(org.getCode());
				}
			}
			for(String str:rstemp)
			{
				List<OrgAdministrativeInfoEntity> orgas = orgAdministrativeInfoComplexService
						.queryOrgAdministrativeInfoEntityAllSubByCode(str);
				if (CollectionUtils.isNotEmpty(orgas)) {
					orgstemp.addAll(orgas);
				}
			}
			//把外场下的外场全部剔除
			if (CollectionUtils.isNotEmpty(orgstemp) && CollectionUtils.isNotEmpty(rs)) {
				for (OrgAdministrativeInfoEntity org : orgstemp) {
					rs.remove(org.getCode());
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
		bizTypesList.add(BizTypeConstants.ORG_TRANSFER_CENTER);
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoComplexService.
				queryOrgAdministrativeInfoByCode(currentOrgCode, bizTypesList);
		if(orgAdministrativeInfoEntity != null){
			//返回部门
			return orgAdministrativeInfoEntity;
		}else{
			return null;
		}
	}
	/** The shared service. */
	ISharedService sharedService;
	/**
	 * Sets the shared service.
	 * 
	 * @param sharedService
	 *            the new shared service
	 */
	public void setSharedService(ISharedService sharedService) {
		this.sharedService = sharedService;
	}
	/**
	 * 取消申请或者取消放行.
	 * 
	 * @return the string
	 * @author IBM-刘彬彬
	 * @date 2012-10-24 下午4:41:55
	 */
	@JSON public String cancleDepart() {
		try {
			// 遍历取得ID的集合
			List<String> strlist = new ArrayList<String>();
			for (TruckDepartEntity truck : departureVO.getActiveList()) {
				strlist.add(truck.getId());
			}
			departureVO.setQueryEntity(new QueryDepartEntity());
			departureVO.getQueryEntity().setIdsForCancle(strlist);
			// 检查状态是待放行的申请
			departureVO.getQueryEntity().setStatus(
					DepartureConstant.DEPART_STATUS_WAIT);
			// 检查状态,因为有定时任务的存在，可能存在界面的数据跟实际的数据不一致，检查是不是完全一直，不是则结束操作
			Long size = departureService
					.validBeforeCancleOrActiveDepart(departureVO
							.getQueryEntity());
			// 检查状态是已放行的申请
			departureVO.getQueryEntity().setStatus(
					DepartureConstant.DEPART_STATUS_DEPARTURE);
			// 检查状态,因为有定时任务的存在，可能存在界面的数据跟实际的数据不一致，检查是不是完全一直，不是则结束操作
			Long size2 = departureService
					.validBeforeCancleOrActiveDepart(departureVO
							.getQueryEntity());
			if (departureVO.getQueryEntity().getIdsForCancle().size() != (size + size2)) {
				departureVO
						.setOperatStatus(DepartureConstant.DEPART_CANCLE_STATUS_ERROR);
			} else {// 取消申请或者取消放行
				departureService.cancleDeparts(departureVO.getActiveList());
			}
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.error(e);
			return this.returnError(e);
		}
	}
	/**
	 * 激活申请.
	 * 
	 * @return the string
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-24 下午4:55:45
	 */
	@JSON public String activeDepart() {
		try {
			// sharedService.refreshDataForActionTask();
			departureVO.getQueryEntity().setIdsForCancle(
					Arrays.asList(departureVO.getQueryEntity().getIds()
							.split(",")));
			departureVO.getQueryEntity().setStatus(
					DepartureConstant.DEPART_STATUS_Fail);
			// 检查状态,因为有定时任务的存在，可能存在界面的数据跟实际的数据不一致，检查是不是完全一直，不是则结束操作
			Long size = departureService
					.validBeforeCancleOrActiveDepart(departureVO
							.getQueryEntity());
			if (departureVO.getQueryEntity().getIdsForCancle().size() != size) {
				departureVO
						.setOperatStatus(DepartureConstant.DEPART_ACTIVE_STATUS_ERROR);
			} else {
				departureService.activeDepart(departureVO.getQueryEntity()
						.getIdsForCancle());
			}
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.error(e);
			return this.returnError(e);
		}
	}
	/**
	 * 打印纸质放行条.
	 * 
	 * @return the string
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-23 下午7:34:02
	 */
	@JSON public String saveOrAddManual() {// 通过有没有主键值来判断是新增还是直接对申请信息进行操作
		try {
			if (null == departureVO.getManualEntity().getId()
					|| "".equals(departureVO.getManualEntity().getId())) {
				LOGGER.info("新增一条手工放行");
				departureService.addManual(departureVO.getManualEntity());
			} else {
				//判断是否要全部放行
				if (!StringUtil.equals(departureVO.getManualEntity().getBeDepartureAll(), "Y") ) {
					LOGGER.info("放行");
					departureService.saveManual(departureVO.getManualEntity());
				}
				else {
					departureService.saveManualAll(departureVO.getManualEntity());
				}
			} 
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.error(e);
			return this.returnError(e);
		}
	}
	/**
	 * 打印纸质放行条前校验状态.
	 * 
	 * @return the string
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-23 下午7:34:02
	 */
	@JSON public String validStatus() {// 通过有没有主键值来判断是新增还是直接对申请信息进行操作
		try {
			String result = departureService.validStatus(departureVO
					.getManualEntity());
			departureVO.setOperatStatus(result);
			if(!StringUtil.equals(result, "1")) {
				String deliverbills = departureService.releaseReminder(departureVO
						.getManualEntity());
				departureVO.setDeliverbills(deliverbills);
			}
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.error(e);
			return this.returnError(e);
		}
	}
	/**
	 * 打印纸质放行条前校验状态.
	 * 
	 * @return the string
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-23 下午7:34:02
	 */
	@JSON public String queryVehicleInfoForPrint() {// 通过有没有主键值来判断是新增还是直接对申请信息进行操作
		try {
			VehiclePrintDTO result = departureService
					.queryVehicleInfoForPrint(departureVO.getVehiclePrintDTO()
							.getVehicleNo());
			departureVO.setVehiclePrintDTO(result);
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
	 * @modifier sunjianbo
	 * @mtime  2014-5-19 上午9:49:23
	 */
	public String exportUnloadWayBillByTaskNo() {
		try {
//			arrivalVO.getQueryDepartureEntity();
			fileName = departureService.encodeFileName("车辆出发到达情况");
			
			//2013-10-16 15:18 BUG-57170 begin
			String departType = departureVO.getQueryEntity().getDepartType();
			// 判断是纸质放行还是pda放行
			if (DepartureConstant.DEPART_TYPE_MANUAL_CODE.equals(departType)) {
				// 传入这个值表示sql执行的时候有这个条件
				departureVO.getQueryEntity().setManualDepartUserCode(
						DepartureConstant.MANUAL_OR_PDA_NOT_NULL);
			}
			if (DepartureConstant.DEPART_TYPE_PDA_CODE.equals(departType)) {
				// 传入这个值表示sql执行的时候有这个条件
				departureVO.getQueryEntity().setPdaDepartUserCode(
						DepartureConstant.MANUAL_OR_PDA_NOT_NULL);
			}
			departureVO.getQueryEntity().setBeginTime(
					DepartureHandle.getDate(departureVO.getQueryEntity()
							.getBeginDate(), DepartureHandle.DATE_FORMAT_1));
			departureVO.getQueryEntity().setEndTime(
					DepartureHandle.getDate(departureVO.getQueryEntity()
							.getEndDate(), DepartureHandle.DATE_FORMAT_1));
			String transDepartment = null;
			String currentDeptCode = FossUserContext.getCurrentDeptCode();
			//根据当前部门编码查询保安组信息
			SecurityTfrMotorcadeEntity securityTfrMotorcadeEntity = new SecurityTfrMotorcadeEntity();
			securityTfrMotorcadeEntity.setSecurityCode(currentDeptCode);
			securityTfrMotorcadeEntity.setActive("Y");
			List<SecurityTfrMotorcadeEntity> securityTfrMotorcadeEntityList = securityTfrMotorcadeService.querySecurityTfrMotorcadeListBySecurityCode(securityTfrMotorcadeEntity, LoadConstants.SONAR_NUMBER_10, 0);
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
			// 只能查询出发部门或者到达部门为当前用户所在外场或者营业部的数据
			departureVO.getQueryEntity().setCurrentOrgCode(currentDeptCode);
			

//			FossUserContext.getCurrentDept();
			List<String> strOrgList =queryTransCenterChildrenCodes(currentDeptCode);
			if (strOrgList == null) {
				strOrgList = new ArrayList<String>();
				strOrgList.add(currentDeptCode);
			}
			//若保安组服务车队存在，把车队对应外场加入list
			if(StringUtil.isNotBlank(transDepartment)){
				MotorcadeEntity securitymotorcade = motorcadeService.queryMotorcadeByCode(transDepartment);
				if(securitymotorcade!=null&&securitymotorcade.getTransferCenter()!=null)
				{
					strOrgList.add(securitymotorcade.getTransferCenter());
				}
			}
//			//如果是调度组，取得车队所属的外场
//			if(FossUserContext.getCurrentDept().checkDispatchTeam())
//			{
				//取得车队
				OrgAdministrativeInfoEntity org = getBigOrg(FossUserContext
						.getCurrentDeptCode());
				//sonar-352203-合并if判断
//				if(org!=null)
//				{//判断该部门是否是车队
					if(org!=null && FossConstants.YES.equals(org.getTransDepartment()))
					{
						MotorcadeEntity motorcade = motorcadeService.queryMotorcadeByCode(org.getCode());
						if(motorcade!=null&&motorcade.getTransferCenter()!=null)
						{
							strOrgList.add(motorcade.getTransferCenter());
						}
					}
//				}
//			}
			
			//如果是车队，取得车队所属的外场
			if(FossConstants.YES.equals(FossUserContext.getCurrentDept().getTransDepartment()))
			{
				MotorcadeEntity motorcade = motorcadeService.queryMotorcadeByCode(FossUserContext.getCurrentDeptCode());
				if(motorcade!=null&&motorcade.getTransferCenter()!=null)
				{
					strOrgList.add(motorcade.getTransferCenter());
				}
			}
			departureVO.getQueryEntity().setCurrentOrgCodes(strOrgList);
			List<TruckDepartEntity> departList = departureService.queryDepart(
					departureVO.getQueryEntity());
			if (departList != null) {
				for (TruckDepartEntity truckDepartEntity : departList) {
					// 实际出发方式
					if (truckDepartEntity.getGpsDepartTime() != null) {
						LOGGER.info("GPS放行");
						truckDepartEntity
								.setActualDepartType(DepartureConstant.DEPART_TYPE_GPS);
						truckDepartEntity.setActualDepartTime(truckDepartEntity
								.getGpsDepartTime());
					} else if (truckDepartEntity.getPdaDepartTime() != null) {
						LOGGER.info("PDA放行");
						truckDepartEntity
								.setActualDepartType(DepartureConstant.DEPART_TYPE_PDA);
						truckDepartEntity.setActualDepartTime(truckDepartEntity
								.getPdaDepartTime());
					} else if (truckDepartEntity.getManualDepartTime() != null) {
						truckDepartEntity
								.setActualDepartType(DepartureConstant.DEPART_TYPE_MANUAL);
						truckDepartEntity.setActualDepartTime(truckDepartEntity
								.getManualDepartTime());
					}
				}
			}
			if(CollectionUtils.isEmpty(departList)) {
				//无数据
				return returnSuccess();
			}
			
			StringBuffer ids = new StringBuffer();
			for(TruckDepartEntity truckDepart : departList) {
				ids.append(truckDepart.getId() + ",");
			}
			//end
			excelStream = departureService.exportTruckDepartOrArriveByTaskNo(ids.toString());
		} catch (TfrBusinessException e) {
			return returnError(e);
		}
		return returnSuccess();
	}
	/**
	 * ******************** 默认配置
	 * **********************.
	 */
	private static final long serialVersionUID = -2956858602690171822L;
	/** ***** 放行接口 ******. */
	private IDepartureService departureService;
	private IMotorcadeService motorcadeService;
	private ISecurityTfrMotorcadeService securityTfrMotorcadeService;

	/** ***** 底层数据结构 ******. */
	private DepartureVO departureVO = new DepartureVO();
	IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	/**
	 * Sets the departure service.
	 * 
	 * @param departureService
	 *            the new departure
	 *            service
	 */
	public void setDepartureService(IDepartureService departureService) {
		this.departureService = departureService;
	}
	/**
	 * Gets the departure vo.
	 * 
	 * @return the departure vo
	 */
	public DepartureVO getDepartureVO() {
		return departureVO;
	}
	/**
	 * Sets the departure vo.
	 * 
	 * @param departureVO
	 *            the new departure vo
	 */
	public void setDepartureVO(DepartureVO departureVO) {
		this.departureVO = departureVO;
	}
	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	public void setMotorcadeService(IMotorcadeService motorcadeService) {
		this.motorcadeService = motorcadeService;
	}

	/**
	 * @Title: setSecurityTfrMotorcadeService 
	 * @Description: setSecurityTfrMotorcadeService
	 * @author sunjianbo
	 * @date 2014-5-19 上午9:28:15 
	 * @param securityTfrMotorcadeService void
	 * @throws 
	 */ 
	public void setSecurityTfrMotorcadeService(
			ISecurityTfrMotorcadeService securityTfrMotorcadeService) {
		this.securityTfrMotorcadeService = securityTfrMotorcadeService;
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