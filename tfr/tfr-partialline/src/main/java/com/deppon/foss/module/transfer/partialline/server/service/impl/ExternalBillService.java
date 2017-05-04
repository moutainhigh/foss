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
 *  PROJECT NAME  : tfr-partialline
 *  
 *  PACKAGE NAME  : 
 * 
 *  DESCRIPTION   : 偏线外发管理
 *  
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/partialline/server/service/impl/ExternalBillService.java
 * 
 *  FILE NAME     :ExternalBillService.java
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
package com.deppon.foss.module.transfer.partialline.server.service.impl;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.export.excel.ExcelExport;
import com.deppon.foss.framework.server.components.export.excel.SheetData;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IVehicleAgencyDeptService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.AgencyBranchOrCompanyDto;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.base.dict.api.util.DictUtil;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.pricing.dubbo.api.server.service.IAgentDeliveryFeeSchemeService;
import com.deppon.foss.module.pickup.pricing.dubbo.api.server.service.IExternalPriceSchemeService;
import com.deppon.foss.module.pickup.pricing.dubbo.api.shared.domain.AgentDeliveryFeeEntity;
import com.deppon.foss.module.pickup.pricing.dubbo.api.shared.domain.AgentDeliveryFeeSchemeEntity;
import com.deppon.foss.module.pickup.pricing.dubbo.api.shared.domain.ExternalPriceSchemeEntity;
import com.deppon.foss.module.pickup.pricing.dubbo.api.shared.exception.AgentDeliveryFeeSchemeException;
import com.deppon.foss.module.pickup.pricing.dubbo.api.shared.exception.ExternalPriceSchemeException;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillDto;
import com.deppon.foss.module.settlement.agency.api.server.service.IVehicleAgencyExternalService;
import com.deppon.foss.module.settlement.agency.api.shared.dto.SettlementExternalBillDto;
import com.deppon.foss.module.trackings.api.server.service.IPushTrackForCaiNiaoService;
import com.deppon.foss.module.trackings.api.shared.domain.SynTrackingEntity;
import com.deppon.foss.module.transfer.common.api.cubcgray.model.VestBatchResult;
import com.deppon.foss.module.transfer.common.api.cubcgray.model.VestResponse;
import com.deppon.foss.module.transfer.common.api.server.service.IFossToCubc;
import com.deppon.foss.module.transfer.common.api.shared.define.CUBCGrayContants;
import com.deppon.foss.module.transfer.common.api.shared.define.PricingConstants;
import com.deppon.foss.module.transfer.common.api.shared.dto.CubcExternalBillDto;
import com.deppon.foss.module.transfer.common.api.shared.dto.CubcExternalBillRequest;
import com.deppon.foss.module.transfer.common.api.shared.dto.CubcExternalBillResponse;
import com.deppon.foss.module.transfer.common.api.shared.dto.GrayParameterDto;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.common.server.utils.CUBCGrayUtil;
import com.deppon.foss.module.transfer.load.api.shared.define.LoadConstants;
import com.deppon.foss.module.transfer.partialline.api.server.dao.IExternalBillDao;
import com.deppon.foss.module.transfer.partialline.api.server.dao.IUninputPartiallineDao;
import com.deppon.foss.module.transfer.partialline.api.server.service.IASYCUBCExternal;
import com.deppon.foss.module.transfer.partialline.api.server.service.IExternalBillService;
import com.deppon.foss.module.transfer.partialline.api.shared.define.PartiallineConstants;
import com.deppon.foss.module.transfer.partialline.api.shared.domain.ExternalBillEntity;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.ExternalBillDto;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.ExternalBillWayBillInfoDto;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.HandoverBillDetailDto;
import com.deppon.foss.module.transfer.partialline.api.shared.exception.ExternalBillException;
import com.deppon.foss.module.transfer.partialline.api.shared.vo.ExternalBillVo;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 偏线Service
 * 
 * @author 096598-foss-zhongyubing
 * @date 2012-10-12 上午9:16:29
 */
public class ExternalBillService implements IExternalBillService {

	/**
	 * 日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ExternalBillService.class);
	/**
	 * 偏线DAO接口
	 */
	private IExternalBillDao externalBillDao;
	/**
	 * 查询未录入DAO接口
	 */
	private IUninputPartiallineDao uninputPartiallineDao;
	/**
	 * 结算接口Service
	 */
	private IVehicleAgencyExternalService vehicleAgencyExternalService;
	/**
	 * 查询偏线代理
	 */
	private IVehicleAgencyDeptService vehicleAgencyDeptService;
	/**
	 * 运单Service
	 */
	private IWaybillManagerService waybillManagerService;
	/**
	 * 人员信息Service
	 */
	private IEmployeeService employeeService;
	/**
	 * 开单金额配置Service
	 */
	private IConfigurationParamsService configurationParamsService;
	/**
	 * 部门Service
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	/**
	 * 部门Service
	 */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;

	/**
	 * 接送货外发价格方案Service
	 */
	@Resource
	private IExternalPriceSchemeService externalPriceSchemeService4Dubbo;

	/**
	 * 接送货外发送货方案Service
	 */
	@Resource
	private IAgentDeliveryFeeSchemeService agentDeliveryFeeSchemeService4Dubbo;

	/**
	 * 轨迹查询
	 * */
	private IPushTrackForCaiNiaoService pushTrackForCaiNiaoService;
	/**
	 *  偏线外发新增异步service
	 */
	private IASYCUBCExternal aSYCUBCExternal;  
	/**
	 * 给cubc接口Service
	 */
	private IFossToCubc fossToCubc;
	
	/**
	 * 灰度工具类
	 * @param fossToCubc
	 */
	private CUBCGrayUtil cubcUtil;

	public void setCubcUtil(CUBCGrayUtil cubcUtil) {
		this.cubcUtil = cubcUtil;
	}

	public void setFossToCubc(IFossToCubc fossToCubc) {
		this.fossToCubc = fossToCubc;
	}

	public void setaSYCUBCExternal(IASYCUBCExternal aSYCUBCExternal) {
		this.aSYCUBCExternal = aSYCUBCExternal;
	}

	public void setPushTrackForCaiNiaoService(
			IPushTrackForCaiNiaoService pushTrackForCaiNiaoService) {
		this.pushTrackForCaiNiaoService = pushTrackForCaiNiaoService;
	}

	/**
	 * 偏线DAO接口
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-20 上午10:45:01
	 */
	public void setExternalBillDao(IExternalBillDao externalBillDao) {
		this.externalBillDao = externalBillDao;
	}

	/**
	 * 查询未录入DAO
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-22 下午6:56:15
	 */
	public void setUninputPartiallineDao(IUninputPartiallineDao uninputPartiallineDao) {
		this.uninputPartiallineDao = uninputPartiallineDao;
	}

	/**
	 * 结算接口Service
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-24 上午11:45:17
	 */
	public void setVehicleAgencyExternalService(IVehicleAgencyExternalService vehicleAgencyExternalService) {
		this.vehicleAgencyExternalService = vehicleAgencyExternalService;
	}

	/**
	 * 人员信息Service
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-12-25 上午8:36:07
	 */
	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	/**
	 * 查询偏线代理Service
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-25 下午7:47:12
	 */
	public void setVehicleAgencyDeptService(IVehicleAgencyDeptService vehicleAgencyDeptService) {
		this.vehicleAgencyDeptService = vehicleAgencyDeptService;
	}

	/**
	 * 运单查询Service
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-12-25 上午8:36:39
	 */
	public void setWaybillManagerService(IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}

	/**
	 * 开单金额配置Service
	 */
	public void setConfigurationParamsService(IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}

	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	/*public void setExternalPriceSchemeService(
			IExternalPriceSchemeService externalPriceSchemeService) {
		this.externalPriceSchemeService = externalPriceSchemeService;
	}*/

	/*public void setAgentDeliveryFeeSchemeService(
			IAgentDeliveryFeeSchemeService agentDeliveryFeeSchemeService) {
		this.agentDeliveryFeeSchemeService = agentDeliveryFeeSchemeService;
	}*/

	/**
	 * 查询偏线列表
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-12 上午9:01:30
	 * @see com.deppon.foss.module.transfer.partialline.api.server.service.IExternalBillService#selectByParams(com.deppon.foss.module.transfer.partialline.api.shared.dto.ExternalBillDto)
	 */
	@Override
	public List<ExternalBillDto> selectByParams(ExternalBillDto dto, int limit,
			int start, CurrentInfo currentInfo) {
		LOGGER.info("查询偏线列表=>");
		if (dto != null && currentInfo != null) {
			LOGGER.info("查询筛选的当前部门名称:" + currentInfo.getCurrentDeptName()
					+ ",部门编码:" + currentInfo.getCurrentDeptCode());
			// 录入员对应部门查询外场
			OrgAdministrativeInfoEntity transCenter = querySuperiorOrgByOrgCode(currentInfo
					.getCurrentDeptCode());
			if (transCenter != null
					&& StringUtils.isNotBlank(transCenter.getCode())) {
				dto.setTransCenterCode(transCenter.getCode());
			}

		}

		// 如果状态选择了全部
		if (null != dto
				&& StringUtils.isNotBlank(dto.getAuditStatus())
				&& PartiallineConstants.PARTIALLINE_AUDITSTATUS_ALL.equals(dto
						.getAuditStatus())) {
			// 清空状态
			dto.setAuditStatus(null);
			// 状态列表
			List<String> availableStatus = new ArrayList<String>();
			// 待审核
			availableStatus
					.add(PartiallineConstants.PARTIALLINE_AUDITSTATUS_WAITINGAUDIT);
			// 已审核
			availableStatus
					.add(PartiallineConstants.PARTIALLINE_AUDITSTATUS_HASAUDITED);
			// 反审核
			availableStatus
					.add(PartiallineConstants.PARTIALLINE_AUDITSTATUS_BACKAUDIT);
			// 已经作废
			availableStatus
					.add(PartiallineConstants.PARTIALLINE_AUDITSTATUS_INVALID);
			// 设置
			dto.setList(availableStatus);
		}
		// 查询偏线列表
		List<ExternalBillDto> tempList = externalBillDao.selectByParams(dto,
				limit, start);
		if (CollectionUtils.isNotEmpty(tempList)) {
			// 员工信息
			EmployeeEntity emp = null;
			for (ExternalBillDto tmpDto : tempList) {
				// 通过录入员编码查询姓名
				emp = employeeService.queryEmployeeByEmpCode(tmpDto
						.getRegisterUserCode());
				// 录入员姓名
				if (emp != null && StringUtils.isNotBlank(emp.getEmpName())) {
					tmpDto.setRegisterUser(emp.getEmpName());
				}
			}
		}
		return tempList;
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
	 * 获取偏线外发单总条数
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-12 下午2:33:09
	 * @see com.deppon.foss.module.transfer.partialline.api.server.service.IExternalBillService#getCount(com.deppon.foss.module.transfer.partialline.api.shared.dto.ExternalBillDto)
	 */
	@Override
	public Long queryCount(ExternalBillDto dto, CurrentInfo currentInfo) {
		if (dto != null && currentInfo != null) {
			LOGGER.info("查询筛选的当前部门名称:" + currentInfo.getCurrentDeptName()
					+ ",部门编码:" + currentInfo.getCurrentDeptCode());
			// 录入员对应部门查询外场
			OrgAdministrativeInfoEntity transCenter = querySuperiorOrgByOrgCode(currentInfo
					.getCurrentDeptCode());
			if (transCenter != null
					&& StringUtils.isNotBlank(transCenter.getCode())) {
				dto.setTransCenterCode(transCenter.getCode());
			}
		}
		// 如果状态选择了全部
		if (null != dto
				&& StringUtils.isNotBlank(dto.getAuditStatus())
				&& PartiallineConstants.PARTIALLINE_AUDITSTATUS_ALL.equals(dto
						.getAuditStatus())) {
			// 清空状态
			dto.setAuditStatus(null);
			// 状态列表
			List<String> availableStatus = new ArrayList<String>();
			// 待审核
			availableStatus
					.add(PartiallineConstants.PARTIALLINE_AUDITSTATUS_WAITINGAUDIT);
			// 已审核
			availableStatus
					.add(PartiallineConstants.PARTIALLINE_AUDITSTATUS_HASAUDITED);
			// 反审核
			availableStatus
					.add(PartiallineConstants.PARTIALLINE_AUDITSTATUS_BACKAUDIT);
			// 已经作废
			availableStatus
					.add(PartiallineConstants.PARTIALLINE_AUDITSTATUS_INVALID);
			// 设置
			dto.setList(availableStatus);
		}
		// 查询总条数
		return externalBillDao.queryCount(dto);
	}

	/**
	 * 保存偏线外发单，并同步结算数据
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-12 上午11:47:04
	 * @see com.deppon.foss.module.transfer.partialline.api.server.service.IExternalBillService#addExternalBill(com.deppon.foss.module.transfer.partialline.api.shared.dto.ExternalBillDto)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public int addExternalBill(ExternalBillDto tempDto, CurrentInfo currentInfo) {

		if (tempDto != null) {

			//外发单录入校验中文时要用字节码做验证
			if(StringUtils.isNotBlank(tempDto.getExternalBillNo())){
				if(tempDto.getExternalBillNo().getBytes().length > ConstantsNumberSonar.SONAR_NUMBER_50){
					throw new ExternalBillException("外发单长度超长!");
				}
			}
			
				// 录入员对应部门
			// sonar-352203
				if (currentInfo == null) {
					throw new ExternalBillException("获取当前用户失败 ", "");
				}
			if (currentInfo != null) {
				OrgAdministrativeInfoEntity transCenter = querySuperiorOrgByOrgCode(currentInfo.getCurrentDeptCode());
				if (transCenter != null && StringUtils.isNotBlank(transCenter.getCode())) {
					tempDto.setTransCenterCode(transCenter.getCode());
				} else {
					LOGGER.info("录入员操作部门对应的外场为空:当前操作部门编码:" + currentInfo.getCurrentDeptCode());
					throw new ExternalBillException("录入员操作部门对应的外场为空", "");
				}
			}
			
			LOGGER.info("保存偏线外发单，并同步结算数据");
			// 根据运单号查询运单信息
			WaybillDto waybillDto = null;
			try {
				LOGGER.info("根据运单号查询运单信息:" + tempDto.getWaybillNo());
				waybillDto = waybillManagerService.queryWaybillByNo(tempDto
						.getWaybillNo());
			} catch (Exception e) {
				LOGGER.error(PartiallineConstants.WAY_BILL_EXCEPTION, e);
				throw new ExternalBillException(
						PartiallineConstants.WAY_BILL_EXCEPTION, e);
			}
			if (waybillDto != null) {
				LOGGER.info("通过网点查询代理编码查询代理公司,并补充代理信息");
				// 外发单补充代理信息
				makeExternalBillAgentCompanyInfo(waybillDto, tempDto);
				// UUID生成
				tempDto.setId(UUIDUtils.getUUID());
				// 待审核状态
				tempDto.setAuditStatus(PartiallineConstants.PARTIALLINE_AUDITSTATUS_WAITINGAUDIT);
				// 偏线类型
				tempDto.setHandoverType(PartiallineConstants.HANDOVER_TYPE_PARTIALLINE_HANDOVER);
				// 根据运单号取最新的交接单明细信息，获取并设置交接单号
				queryLastHandoverBillDetial(tempDto, currentInfo);
				// 执行业务校验
				validateAddExternalBill(tempDto);
				// 验证通过，执行录入操作
				// 插入偏线外发单
				LOGGER.info("保存插入偏线外发单" + tempDto.toString());
				externalBillDao.insert(tempDto);

				// 310248 wqs待查询轨迹表
				SynTrackingEntity synTrackEntity = new SynTrackingEntity();
				synTrackEntity.setId(UUIDUtils.getUUID());
				synTrackEntity.setWayBillNo(tempDto.getWaybillNo());
				synTrackEntity.setNextMailNos(tempDto.getExternalBillNo());
				synTrackEntity.setCreateDate(new Date());
				synTrackEntity.setOperateTime(new Date());
				synTrackEntity.setOperatorCode(currentInfo.getEmpCode());
				synTrackEntity.setOperatorName(currentInfo.getEmpName());
				synTrackEntity.setOrgCode(currentInfo.getCurrentDeptCode());
				synTrackEntity.setOrgName(currentInfo.getCurrentDeptName());
				synTrackEntity.setEventType("LDP_PARTIAL_LINE");
				synTrackEntity.setTrackInfo(currentInfo.getCurrentDeptName()+ " " + currentInfo.getEmpName() + "外发单录入！");
				synTrackEntity.setOperateCity(currentInfo.getDept().getProvName()+ " "+ currentInfo.getDept().getCityName()+ " "+ currentInfo.getDept().getCountyName());
				//2016年8月19日09:00:17 311396 增加代理公司名称,DN201611150016 增加编码
				synTrackEntity.setNextTpCode(tempDto.getAgentCompanyCode());//代理公司编码
				synTrackEntity.setAgentCompanyName(tempDto.getAgentCompanyName());//代理公司名称
				pushTrackForCaiNiaoService.addSynTrackToWQS(synTrackEntity);

				// 结算系统数据插入
				SettlementExternalBillDto stlDto = new SettlementExternalBillDto();
				// cubc
				CubcExternalBillDto cubcDto = new CubcExternalBillDto();

				// 由于新增的数据一定不是迁移数据所以这边直接设置N
				tempDto.setIsInit("N");
				// 调结算接口时将其他金额算在外发费用中
				tempDto.setExternalAgencyFee(tempDto.getExternalAgencyFee()
						.add(new BigDecimal(tempDto.getOtherFee())));

				// 拷贝数据
				copyProperties(stlDto, tempDto, waybillDto);

				// cubc拷贝数据
				copyPropertiesCUBC(cubcDto, tempDto, waybillDto);

				// 验证费用
				validateFeesAndWriteOff(tempDto, waybillDto);
				
				
				//// cubcgray 335284-310248
				GrayParameterDto param = new GrayParameterDto();
				param.setSourceBillNos(new String[] { cubcDto.getWaybillNo() });
				param.setSourceBillType(CUBCGrayUtil.SBType.W.getName());
				VestResponse response = cubcUtil.getUcbcGrayData(param, new Throwable());
				boolean flag = true;//stl, not cubc
				for (VestBatchResult r : response.getVestBatchResult()) {
					if (CUBCGrayContants.SYSTEM_CODE_CUBC.equals(r.getVestSystemCode())) {
						flag = false;
						break;
					}
				}
				LOGGER.info("新增外发单CUBC分流标识：" + flag);
				// 310248添加 由于韩国件是线下结算，所以外发总费用可以为0，不调用结算接口
				if (flag && tempDto.getCostAmount() != null
						&& tempDto.getCostAmount().compareTo(BigDecimal.ZERO) > 0) {
					// 结算新增addExternalBill(stlDto)
					vehicleAgencyExternalService.addExternalBill(stlDto, currentInfo);
				}
				if (!flag && cubcDto.getCostAmount() != null
						&& cubcDto.getCostAmount().compareTo(BigDecimal.ZERO) > 0) {
					// cubc结算新增addExternalBill(stlDto)
					CubcExternalBillRequest requestExternalBillDto = new CubcExternalBillRequest();
					requestExternalBillDto.setCubcExternalBillDto(cubcDto);
					// 员工工号
					requestExternalBillDto.setEmpCode(FossUserContext
							.getCurrentInfo().getEmpCode());
					// 员工姓名
					requestExternalBillDto.setEmpName(FossUserContext
							.getCurrentInfo().getEmpName());
					// 当前登录部门编码
					requestExternalBillDto.setCurrentDeptCode(FossUserContext
							.getCurrentInfo().getCurrentDeptCode());
					// 当前登录部门名称
					requestExternalBillDto.setCurrentDeptName(FossUserContext
							.getCurrentInfo().getCurrentDeptName());

					LOGGER.error("偏线外发录入异步接口给CUBC数据  ,外发单号:" + cubcDto.getExternalBillNo());
					aSYCUBCExternal.pushAddExternalBill(requestExternalBillDto);
					LOGGER.error("偏线外发录入异步接口给CUBC数据  ,外发单号:" + cubcDto.getExternalBillNo() + "结束!");
				}
				////cubcgray 335284-310248
			} else {
				// 未查询到对应的运单
				throw new ExternalBillException("未查询到对应的运单", "");
			}
		}
		return PartiallineConstants.SERVICE_RESULT_INT_VALUE;
	}

	/**
	 * 外发单补充代理信息
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-12-5 下午1:56:09
	 */
	private void makeExternalBillAgentCompanyInfo(WaybillDto waybillDto,
			ExternalBillDto tempDto) {
		if (waybillDto != null) {
			// 根据运单号查询偏线代理信息
			// 提货网点CODE
			WaybillEntity waybill = waybillDto.getWaybillEntity();
			// 代理编码
			String agencyBranchCode = null;
			if (waybill != null) {
				// 代理信息CODE
				agencyBranchCode = waybill.getCustomerPickupOrgCode();
				LOGGER.info("代理网点CODE:" + agencyBranchCode);
			}
			// 代理公司
			AgencyBranchOrCompanyDto companyDto = null;
			// 代理编码查询代理公司
			if (StringUtils.isNotBlank(agencyBranchCode)) {
				LOGGER.info("通过网点查询代理编码查询代理公司:");
				// 代理信息Dto
				companyDto = vehicleAgencyDeptService
						.queryAgencyBranchCompanyInfo(agencyBranchCode);
			}
			if (companyDto != null) {
				// 偏线代理编号( 外发代理编号)
				if (StringUtils.isNotBlank(companyDto.getAgentCompanyCode())) {
					tempDto.setAgentCompanyCode(companyDto
							.getAgentCompanyCode());
					LOGGER.info("偏线代理编号( 外发代理编号):"
							+ companyDto.getAgentCompanyCode());
				}
				// 偏线代理名称（外发代理）
				if (StringUtils.isNotBlank(companyDto.getAgentCompanyName())) {
					tempDto.setAgentCompanyName(companyDto
							.getAgentCompanyName());
					LOGGER.info("偏线代理名称（外发代理）:"
							+ companyDto.getAgentCompanyName());
				}
			} else {
				LOGGER.info("偏线代理为空,请核实");
				// 抛出业务异常“偏线代理为空,请核实”
				throw new ExternalBillException("偏线代理为空,请核实", "");
			}
		}
	}

	/**
	 * 更新偏线外发单，并同步结算数据
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-15 上午8:39:06
	 * @see com.deppon.foss.module.transfer.partialline.api.server.service.IExternalBillService#updateExternalBill(com.deppon.foss.module.transfer.partialline.api.shared.dto.ExternalBillDto)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public int updateExternalBill(ExternalBillDto tempDto,
			CurrentInfo currentInfo) throws ExternalBillException {
		//sonar-352203
		if (tempDto == null) {
			return PartiallineConstants.SERVICE_RESULT_INT_VALUE;
		}
			
			//外发单录入校验中文时要用字节码做验证
			if(StringUtils.isNotBlank(tempDto.getExternalBillNo())){
				if(tempDto.getExternalBillNo().getBytes().length > ConstantsNumberSonar.SONAR_NUMBER_50){
					throw new ExternalBillException("外发单长度超长!");
				}
			}

				// 查询原有外发单信息
				ExternalBillEntity oldExternalBill = externalBillDao.queryByPrimaryKey(tempDto.getId());
				// 查看是否已经被其他人员修改
				//sonar-352203
				if (oldExternalBill == null || !oldExternalBill.getModifyDate().equals(tempDto.getModifyDate())) {
					throw new ExternalBillException("数据时效已被修改过，请重新打开再修改", "");
				}
					// 根据运单号查询运单信息
					WaybillDto waybillDto = null;
					try {
						waybillDto = waybillManagerService.queryWaybillByNo(tempDto.getWaybillNo());
					} catch (Exception e) {
						LOGGER.error(PartiallineConstants.WAY_BILL_EXCEPTION, e);
						throw new ExternalBillException(PartiallineConstants.WAY_BILL_EXCEPTION, e);
					}
					//sonar-352203
					if (waybillDto == null) {
						// 未查询到对应的运单
						throw new ExternalBillException("未查询到对应的运单", "");
					}

					// 如果做过改动，则验证是否与其他的重复
					validateUpdateExternalBill(tempDto, oldExternalBill);

					// 设置录入时间
					tempDto.setRegisterTime(oldExternalBill.getRegisterTime());
					// 设置修改时间
					tempDto.setModifyDate(Calendar.getInstance().getTime());
					// 保持状态不变
					tempDto.setAuditStatus(oldExternalBill.getAuditStatus());
					// 外发单补充代理信息
					makeExternalBillAgentCompanyInfo(waybillDto, tempDto);
					// 设置是否是迁移数据
					tempDto.setIsInit(oldExternalBill.getIsInit());

					// 把外发代理设置成存在数据库里面的CODE
					tempDto.setAgentCompanyCode(oldExternalBill
							.getAgentCompanyCode());
					tempDto.setAgentCompanyName(oldExternalBill
							.getAgentCompanyName());

					// 执行更改
					externalBillDao.updateByPrimaryKey(tempDto);
					// 调结算接口时将其他金额算在外发费用中
					tempDto.setExternalAgencyFee(tempDto.getExternalAgencyFee()
							.add(new BigDecimal(tempDto.getOtherFee())));

					// 结算系统数据更新
					SettlementExternalBillDto stlDto = new SettlementExternalBillDto();

					// CUBC系统数据更新
					CubcExternalBillDto cubcDto = new CubcExternalBillDto();

					// 拷贝数据
					copyProperties(stlDto, tempDto, waybillDto);

					// cubc拷贝数据
					copyPropertiesCUBC(cubcDto, tempDto, waybillDto);

					// 如果是改了外发单号，需要先调用结算作废操作，然后调用结算新增
					if (StringUtils.isNotBlank(oldExternalBill
							.getExternalBillNo())
							&& !oldExternalBill.getExternalBillNo().equals(
									tempDto.getExternalBillNo())) {
						// DOTO新增
						List<SettlementExternalBillDto> stlDtos = new ArrayList<SettlementExternalBillDto>();
						// CUBC
						List<CubcExternalBillDto> cubcDtos = new ArrayList<CubcExternalBillDto>();
						SettlementExternalBillDto oldStlDto = new SettlementExternalBillDto();
						CubcExternalBillDto oldCUBCDto = new CubcExternalBillDto();

						// 根据现有的外发单构建原来的外发单
						ExternalBillDto oldTempBillDto = tempDto;
						// 获取原来外发单号
						oldTempBillDto.setExternalBillNo(oldExternalBill
								.getExternalBillNo());
						// 拷贝数据
						copyProperties(oldStlDto, oldTempBillDto, waybillDto);
						// cubc拷贝数据
						copyPropertiesCUBC(oldCUBCDto, oldTempBillDto,waybillDto);
						stlDtos.add(oldStlDto);
						cubcDtos.add(oldCUBCDto);
						// 如果外发单号改过
						// 结算作废
						//灰度DTO封装
						List<String> wayBillList = new ArrayList<String>();
						for (SettlementExternalBillDto dtoTemp : stlDtos) {
							wayBillList.add(dtoTemp.getWaybillNo());
						}
						GrayParameterDto parDto = new GrayParameterDto();
						parDto.setSourceBillType(CUBCGrayUtil.SBType.W.getName());
						parDto.setSourceBillNos((String []) wayBillList.toArray(new String [wayBillList.size()]));
						//调用灰度工具类方法
						VestResponse vestResponse = cubcUtil.getUcbcGrayData(parDto, new Throwable());
						//返回FOSS， 走STL模块代码
						boolean flag = CUBCGrayContants.SYSTEM_CODE_FOSS.equals(vestResponse.getVestBatchResult().get(0).getVestSystemCode());
						if (flag) {
				vehicleAgencyExternalService.disableExternalBill(
						stlDtos, currentInfo);
						} else {
							CubcExternalBillRequest requestExternalBillDto = new CubcExternalBillRequest();
							requestExternalBillDto.setCubcExternalList(cubcDtos);
							// 员工工号
							requestExternalBillDto.setEmpCode(FossUserContext
									        .getCurrentInfo().getEmpCode());
							// 员工姓名
							requestExternalBillDto.setEmpName(FossUserContext
									        .getCurrentInfo().getEmpName());
							// 当前登录部门编码
							requestExternalBillDto.setCurrentDeptCode(FossUserContext
											.getCurrentInfo().getCurrentDeptCode());
							// 当前登录部门名称
							requestExternalBillDto.setCurrentDeptName(FossUserContext
											.getCurrentInfo().getCurrentDeptName());
							// 状态；1审核2反审核,0作废
							requestExternalBillDto.setStatus("0");
							CubcExternalBillResponse response = null;
					LOGGER.error("FOSS推送作废偏线外发单号"+ requestExternalBillDto.getCubcExternalList().get(0).getExternalBillNo() + "给CUBC开始...");
							try {
						  
								 response = fossToCubc
										.pushExternalBillStatus(requestExternalBillDto);
								 if(null == response){
									 throw new ExternalBillException("由于改了外发单号需先作废，后调用新增;FOSS调用CUBC作废操作时ESB发生异常!");	
								 }
								 if(StringUtils.equals("0",response.getResult())){
									 throw new ExternalBillException("由于改了外发单号需先作废，后调用新增;FOSS调用CUBC作废操作失败，失败原因:"+response.getReason());	
								 }
							} catch (Exception e) {
								throw new ExternalBillException("更新偏线外发单时，由于改了外发单号需先作废，后调用新增;调用CUBC作废操作失败，失败原因:"+e);	
							}
					LOGGER.error("FOSS推送作废偏线外发单号"+ requestExternalBillDto.getCubcExternalList().get(0).getExternalBillNo()+ "给CUBC结束;返回值是 :" + response);
						}

						CubcExternalBillRequest requestExternalBillDto = new CubcExternalBillRequest();
						requestExternalBillDto.setCubcExternalList(cubcDtos);
						// 员工工号
						requestExternalBillDto.setEmpCode(FossUserContext
								        .getCurrentInfo().getEmpCode());
						// 员工姓名
						requestExternalBillDto.setEmpName(FossUserContext
								        .getCurrentInfo().getEmpName());
						// 当前登录部门编码
						requestExternalBillDto.setCurrentDeptCode(FossUserContext
										.getCurrentInfo().getCurrentDeptCode());
						// 当前登录部门名称
						requestExternalBillDto.setCurrentDeptName(FossUserContext
										.getCurrentInfo().getCurrentDeptName());
						// 状态；1审核2反审核,0作废
						requestExternalBillDto.setStatus("0");
						CubcExternalBillResponse response = null;
				LOGGER.error("FOSS推送作废偏线外发单号"+ requestExternalBillDto.getCubcExternalList().get(0).getExternalBillNo() + "给CUBC开始...");
						try {
					  
							 response = fossToCubc
									.pushExternalBillStatus(requestExternalBillDto);
							 if(null == response){
								 throw new BusinessException("由于改了外发单号需先作废，后调用新增;FOSS调用CUBC作废操作时ESB发生异常!");	
							 }
							 if(StringUtils.equals("0",response.getResult())){
								 throw new BusinessException(response.getReason());	
							 }
						} catch (Exception e) {
							throw new ExternalBillException("更新偏线外发单时，由于改了外发单号需先作废，后调用新增;调用CUBC作废操作失败，失败原因:"+e.getMessage());	
						}
				LOGGER.error("FOSS推送作废偏线外发单号"+ requestExternalBillDto.getCubcExternalList().get(0).getExternalBillNo()+ "给CUBC结束;返回值是 :" + response);
						// 验证费用
						validateFeesAndWriteOff(tempDto, waybillDto);
						
						////cubcgray 335284-310248
						GrayParameterDto param = new GrayParameterDto();
						param.setSourceBillNos(new String[] { cubcDto.getWaybillNo() });
						param.setSourceBillType(CUBCGrayUtil.SBType.W.getName());
						VestResponse vresponse = cubcUtil.getUcbcGrayDataBySourceBillNo(param, new Throwable());
//						boolean flag = true;//stl, not cubc
						for (VestBatchResult r : vresponse.getVestBatchResult()) {
							if (CUBCGrayContants.SYSTEM_CODE_CUBC.equals(r.getVestSystemCode())) {
								flag = false;
								break;
							}
							flag = true;
							break;
						}
						LOGGER.info("修改外发单（单号变更）CUBC分流标识：" + flag);
						if (flag) {
							// 结算新增
							vehicleAgencyExternalService.addExternalBill(stlDto, currentInfo);
						} else {
							// CUBC新增
							try {
								CubcExternalBillRequest addrequestExternalBill = new CubcExternalBillRequest();
								addrequestExternalBill.setCubcExternalBillDto(cubcDto);
								aSYCUBCExternal.pushAddExternalBill(addrequestExternalBill);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					} else {

						// 判断是否有修改过 外发成本总额
						if (null == tempDto.getCostAmount()) {
							tempDto.setCostAmount(BigDecimal.ZERO);
						}
						if (null == oldExternalBill.getCostAmount()) {
							oldExternalBill.setCostAmount(BigDecimal.ZERO);
						}
						if (StringUtils.equals(tempDto.getCostAmount()
								.toString(), oldExternalBill.getCostAmount()
								.toString())) {
							stlDto.setIsModifyCostAmount(FossConstants.NO);
							cubcDto.setIsModifyCostAmount(FossConstants.NO);
						} else {
							stlDto.setIsModifyCostAmount(FossConstants.YES);
							cubcDto.setIsModifyCostAmount(FossConstants.YES);
						}

						// 判断是否修改了,中转外发这个属性
						if (null == tempDto.getTransferExternal()
								|| null == oldExternalBill
										.getTransferExternal()) {
							throw new ExternalBillException("外发单必须选择是否外发", "");
						}
						if (StringUtils.equals(tempDto.getTransferExternal(),
								oldExternalBill.getTransferExternal())) {
							stlDto.setIsModifyTransferExternal(FossConstants.NO);
							cubcDto.setIsModifyTransferExternal(FossConstants.NO);
						} else {
							stlDto.setIsModifyTransferExternal(FossConstants.YES);
							cubcDto.setIsModifyTransferExternal(FossConstants.YES);
						}

						// 验证费用
						validateFeesAndWriteOff(tempDto, waybillDto);
						//灰度DTO封装
						GrayParameterDto parDto = new GrayParameterDto();
						parDto.setSourceBillType(CUBCGrayUtil.SBType.W.getName());
						parDto.setSourceBillNos(new String[] {stlDto.getWaybillNo()});
						//调用灰度工具类方法
						VestResponse vestResponse = cubcUtil.getUcbcGrayData(parDto, new Throwable());
						//返回FOSS， 走STL模块代码
						if (CUBCGrayContants.SYSTEM_CODE_FOSS.equals(vestResponse.getVestBatchResult().get(0).getVestSystemCode())) {
							// 结算更新
							vehicleAgencyExternalService.modifyExternalBill(stlDto,
									currentInfo);
						} else {

							// CUBC更新接口
							CubcExternalBillRequest requestExternalBillDto = new CubcExternalBillRequest();
							// 外发实体
							requestExternalBillDto.setCubcExternalBillDto(cubcDto);
							// 员工工号
							requestExternalBillDto.setEmpCode(FossUserContext.getCurrentInfo().getEmpCode());
							// 员工姓名
							requestExternalBillDto.setEmpName(FossUserContext.getCurrentInfo().getEmpName());
							// 当前登录部门编码
							requestExternalBillDto
									.setCurrentDeptCode(FossUserContext.getCurrentInfo().getCurrentDeptCode());
							// 当前登录部门名称
							requestExternalBillDto
									.setCurrentDeptName(FossUserContext.getCurrentInfo().getCurrentDeptName());
							LOGGER.error("FOSS推送更新偏线外发单号" + cubcDto.getExternalBillNo() + "给CUBC开始...");

							CubcExternalBillResponse response = null;
							try {
								response = fossToCubc.pushUpdataExternalBill(requestExternalBillDto);
								if (null == response) {
									throw new ExternalBillException("FOSS推送更新偏线外发单操作时ESB发生异常!");
								}
							 if(StringUtils.equals("0",response.getResult())){
								 throw new BusinessException(response.getReason());	
								}

							} catch (Exception e) {
								throw new ExternalBillException(
										"更新偏线外发单时，由于改了外发单号需先作废，后调用新增;调用CUBC作废操作失败，失败原因:" + e.getMessage());

							}
						}
//					} else {
//						// 未查询到对应的运单
//						throw new ExternalBillException("未查询到对应的运单", "");
//					}
//				} else {
//					throw new ExternalBillException("数据时效已被修改过，请重新打开再修改", "");
//				}
		}

		return PartiallineConstants.SERVICE_RESULT_INT_VALUE;
	}

	/**
	 * 根据SR-15验证费用信息
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-12-31 下午3:58:01
	 */
	private void validateFeesAndWriteOff(ExternalBillDto tempDto,
			WaybillDto waybillDto) {

		// 验证到付、应付、外发成本总额
		if (tempDto != null && waybillDto != null
				&& waybillDto.getWaybillEntity() != null) {
			// 验证外发成本总额比开单金额大时出现提示信息，当外发成本总额比开单金额大1倍时不能录入，默认为1倍，可配置
			validateCostAmountAndTotalFee(tempDto, waybillDto);
			// 外发成本总额
			BigDecimal costAmount = defaultValueForBigDecimal(tempDto
					.getCostAmount());
			// 到付运费(到付金额)
			BigDecimal toPayAmount = defaultValueForBigDecimal(waybillDto
					.getWaybillEntity().getToPayAmount());
			// 实付代理金额(实付代理费)
			BigDecimal payAgencyFee = defaultValueForBigDecimal(tempDto
					.getPayAgencyFee());
			// 实收代理金额(实收代理费)
			BigDecimal receiveAgencyFee = defaultValueForBigDecimal(tempDto
					.getReceiveAgencyFee());
			// 自动核销
			String isWriteOff = tempDto.getIsWriteOff();
			// 1.当运单到付金额＞0时，运单到付金额-外发成本总额＞0时，是否自动核销为否时，
			// 0≤实收代理金额≤运单到付金额，0≤实付代理金额≤外发成本总额；
			// 是否自动核销为是时，0≤实收代理金额≤（运单到付金额-外发成本总额），
			// 实付代理金额为灰色，不可编辑，只能为0
			if (toPayAmount.compareTo(BigDecimal.ZERO) == PartiallineConstants.GREATER) {
				// 运单到付金额-外发成本总额
				BigDecimal x = toPayAmount.subtract(costAmount);
				// 运单到付金额-外发成本总额＞0时
				if (x.compareTo(BigDecimal.ZERO) == PartiallineConstants.GREATER) {
					// 非自动核销
					if (StringUtils.isNotBlank(isWriteOff)
							&& PartiallineConstants.IS_TRANSFER_EXTERNAL_NO
									.equals(isWriteOff)) {
						// 验证实付代理费
						if ((payAgencyFee.compareTo(BigDecimal.ZERO) == PartiallineConstants.EQUAL || payAgencyFee
								.compareTo(BigDecimal.ZERO) == PartiallineConstants.GREATER)
								&& ((payAgencyFee.compareTo(costAmount) == PartiallineConstants.LESS || payAgencyFee
										.compareTo(toPayAmount) == PartiallineConstants.EQUAL))) {
							// 验证通过
						} else {
							LOGGER.info("到付金额>外发成本总额,且非自动核销时，需要[0≤实付代理费≤外发成本总额]"
									+ tempDto.toString());
							throw new ExternalBillException(
									"到付金额>外发成本总额,且非自动核销时，需要[0≤实付代理费≤外发成本总额]",
									"");
						}
						// 验证实收代理费
						if ((receiveAgencyFee.compareTo(BigDecimal.ZERO) == PartiallineConstants.EQUAL || receiveAgencyFee
								.compareTo(BigDecimal.ZERO) == PartiallineConstants.GREATER)
								&& ((receiveAgencyFee.compareTo(toPayAmount) == PartiallineConstants.LESS || receiveAgencyFee
										.compareTo(toPayAmount) == PartiallineConstants.EQUAL))) {
							// 验证通过
						} else {
							LOGGER.info("到付金额>外发成本总额,且非自动核销时，需要[0≤实收代理费≤到付金额]"
									+ tempDto.toString());
							throw new ExternalBillException(
									"到付金额>外发成本总额,且非自动核销时，需要[0≤实收代理费≤到付金额]", "");

						}

					}
					// 自动核销
					else {
						// 实付代理金额为灰色，不可编辑，只能为0
						// 是否自动核销为是时，0≤实收代理金额≤（运单到付金额-外发成本总额）
						if ((receiveAgencyFee.compareTo(BigDecimal.ZERO) == PartiallineConstants.EQUAL || receiveAgencyFee
								.compareTo(BigDecimal.ZERO) == PartiallineConstants.GREATER)
								&& ((receiveAgencyFee.compareTo(x) == PartiallineConstants.LESS || receiveAgencyFee
										.compareTo(x) == PartiallineConstants.EQUAL))) {
							// 验证通过
						} else {
							LOGGER.info("到付金额>外发成本总额,且自动核销时，需要[0≤实收代理费≤（到付金额-外发成本总额）]"
									+ tempDto.toString());
							throw new ExternalBillException(
									"到付金额>外发成本总额,且自动核销时，需要[0≤实收代理费≤（到付金额-外发成本总额）]",
									"");

						}
					}
				}
				// 2.当运单到付金额＞0时，运单到付金额-外发成本总额≤0时（此时亏本走货，外发成本总额在容忍亏本的范围内，通过参数配置），
				// 是否自动核销为否时，0≤实收代理金额≤运单到付金额，0≤实付代理金额≤外发成本总额；是否自动核销为是时，实收代理金额为灰色，
				// 不可编辑，只能为0，0≤实付代理金额≤（外发成本总额-运单到付金额）
				// 运单到付金额-外发成本总额≤0时（此时亏本走货，外发成本总额在容忍亏本的范围内，通过参数配置）
				else {
					// 非自动核销
					if (StringUtils.isNotBlank(isWriteOff)
							&& PartiallineConstants.IS_TRANSFER_EXTERNAL_NO
									.equals(isWriteOff)) {
						// 验证实付代理费,此时亏本走货，外发成本总额在容忍亏本的范围内，通过参数配置
						if ((payAgencyFee.compareTo(BigDecimal.ZERO) == PartiallineConstants.EQUAL || payAgencyFee
								.compareTo(BigDecimal.ZERO) == PartiallineConstants.GREATER)
								&& ((payAgencyFee.compareTo(costAmount) == PartiallineConstants.LESS || payAgencyFee
										.compareTo(costAmount) == PartiallineConstants.EQUAL))) {
							// 验证通过
						} else {
							LOGGER.info("到付金额<=外发成本总额,且非自动核销时，需要[0≤实付代理费≤外发成本总额]"
									+ tempDto.toString());
							throw new ExternalBillException(
									"到付金额<=外发成本总额,且非自动核销时，需要[0≤实付代理费≤外发成本总额]",
									"");
						}
						// 验证实收代理费
						if ((receiveAgencyFee.compareTo(BigDecimal.ZERO) == PartiallineConstants.EQUAL || receiveAgencyFee
								.compareTo(BigDecimal.ZERO) == PartiallineConstants.GREATER)
								&& ((receiveAgencyFee.compareTo(toPayAmount) == PartiallineConstants.LESS || receiveAgencyFee
										.compareTo(toPayAmount) == PartiallineConstants.EQUAL))) {
							// 验证通过
						} else {
							LOGGER.info("到付金额<=外发成本总额,且非自动核销时，需要[0≤实收代理费≤到付金额]"
									+ tempDto.toString());
							throw new ExternalBillException(
									"到付金额<=外发成本总额,且非自动核销时，需要[0≤实收代理费≤到付金额]", "");
						}

					}
					// 自动核销
					else {
						// 是否自动核销为是时，实收代理金额为灰色，不可编辑，只能为0

						// 外发成本总额-运单到付金额
						BigDecimal y = costAmount.subtract(toPayAmount);
						// 是否自动核销为是时，0≤实付代理金额≤（外发成本总额-运单到付金额）
						if ((payAgencyFee.compareTo(BigDecimal.ZERO) == PartiallineConstants.EQUAL || payAgencyFee
								.compareTo(BigDecimal.ZERO) == PartiallineConstants.GREATER)
								&& ((payAgencyFee.compareTo(y) == PartiallineConstants.LESS || payAgencyFee
										.compareTo(y) == PartiallineConstants.EQUAL))) {
							// 验证通过
						} else {
							LOGGER.info("到付金额<=外发成本总额,且自动核销时，需要[0≤实付代理金额≤（外发成本费-运单到付金额）]"
									+ tempDto.toString());
							throw new ExternalBillException(
									"到付金额<=外发成本总额,且自动核销时，需要[0≤实付代理金额≤（外发成本费-运单到付金额）]",
									"");
						}
					}
				}
			}
			// 当运单到付金额=0时（此时，发货人会和我司进行运费结算，外发部门无需委托代理收款，我司只需给代理支付成本），
			// 实收代理金额为灰色，不可编辑，只能为0，0≤实付代理金额≤外发成本总额。
			else if (toPayAmount.compareTo(BigDecimal.ZERO) == PartiallineConstants.EQUAL) {
				// 实收代理金额为灰色，不可编辑，只能为0
				// 0≤实付代理金额≤外发成本总额
				if ((payAgencyFee.compareTo(BigDecimal.ZERO) == PartiallineConstants.EQUAL || payAgencyFee
						.compareTo(BigDecimal.ZERO) == PartiallineConstants.GREATER)
						&& ((payAgencyFee.compareTo(costAmount) == PartiallineConstants.LESS || payAgencyFee
								.compareTo(costAmount) == PartiallineConstants.EQUAL))) {
					// 验证通过
				} else {
					LOGGER.info("当运单到付金额=0时，需要[0≤实付代理费≤外发成本总额]"
							+ tempDto.toString());
					throw new ExternalBillException(
							"当运单到付金额=0时，需要[0≤实付代理费≤外发成本总额]", "");
				}
			}

		}
	}

	/**
	 * 验证外发成本总额比开单金额大时出现提示信息，当外发成本总额比开单金额大1倍时不能录入，默认为1倍，可配置
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-1-4 下午3:24:05
	 */
	private void validateCostAmountAndTotalFee(ExternalBillDto tempDto,
			WaybillDto waybillDto) {
		// 验证外发成本总额比开单金额大时出现提示信息，当外发成本总额比开单金额大1倍时不能录入，默认为1倍，可配置
		if (tempDto != null && waybillDto != null
				&& waybillDto.getWaybillEntity() != null) {
			// 查询配置的倍数
			ConfigurationParamsEntity configEntity = configurationParamsService
					.queryConfigurationParamsByVirtualCode(ConfigurationParamsConstants.STL_PL_FEE_RATIO);
			// 获取默认倍数
			Long multipler = PartiallineConstants.MULTIPLER;
			// 判断倍数不为空，且为数值
			if (configEntity != null
					&& StringUtils.isNotBlank(configEntity.getConfValue())
					&& StringUtils.isNumeric(configEntity.getConfValue())) {
				// 配置倍数
				multipler = Long.parseLong(configEntity.getConfValue());
			}
			// 验证倍数
			// 开单金额
			BigDecimal totalFee = BigDecimal.ZERO;
			if (waybillDto.getWaybillEntity().getTotalFee() != null) {
				totalFee = waybillDto.getWaybillEntity().getTotalFee();
			}
			// 外发成本总额
			BigDecimal costAmount = defaultValueForBigDecimal(tempDto
					.getCostAmount());
			// 比较外发成本总额与开单金额
			if (costAmount != null) {
				totalFee = totalFee.multiply(BigDecimal.valueOf(multipler));
				// 外发成本>开单金额*倍数
				if (costAmount.compareTo(totalFee) == PartiallineConstants.GREATER) {
					LOGGER.info("外发成本总额超过开单金额的" + multipler.intValue() + "倍"
							+ tempDto.toString());
					throw new ExternalBillException("外发成本总额超过开单金额的"
							+ multipler.intValue() + "倍", "");
				}
			}
		}

	}

	/**
	 * 数值型默认值
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-12-31 下午4:05:52
	 */
	private BigDecimal defaultValueForBigDecimal(BigDecimal val) {
		if (val == null) {
			return BigDecimal.ZERO;
		} else {
			return val;
		}
	}

	/**
	 * 拷贝数据
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-30 上午10:02:55
	 */
	private void copyProperties(SettlementExternalBillDto stlDto,
			ExternalBillDto tempDto, WaybillDto waybillDto) {
		if (stlDto != null && tempDto != null) {
			LOGGER.info("拷贝数据:" + tempDto.toString());
			// 运单号
			stlDto.setWaybillNo(tempDto.getWaybillNo());
			// 根据运单号查询运单信息
			if (waybillDto != null) {
				WaybillEntity waybill = waybillDto.getWaybillEntity();
				// 付款方式
				if (waybill != null) {
					stlDto.setPaidMethod(waybill.getPaidMethod());
					LOGGER.info("付款方式" + waybill.getPaidMethod());
				} else {
					throw new ExternalBillException("没有查询到对应的运单信息", "");
				}
				// --补充运单信息
				// 代收货款费用
				stlDto.setCodAmount(waybill.getCodAmount());
				// 总费用
				stlDto.setTotalFee(waybill.getTotalFee());
				// 送货费
				stlDto.setDeliveryGoodsFee(waybill.getDeliveryGoodsFee());
				// 到达部门编码
				stlDto.setLastLoadOrgCode(waybill.getLastLoadOrgCode());
				// 出发部门编码
				stlDto.setReceiveOrgCode(waybill.getReceiveOrgCode());
				// waybillId
				stlDto.setWaybillId(waybill.getId());
			} else {
				throw new ExternalBillException("没有查询到对应的运单信息", "");
			}

			// BUG-7245 外发部门修改为外发员所属的外场，如果找不到外场，则报错
			OrgAdministrativeInfoEntity transCenter = querySuperiorOrgByOrgCode(tempDto
					.getExternalOrgCode());
			// BUG-7245 外发部门修改为外发员所属的外场，如果找不到外场，则报错
			if (transCenter != null
					&& StringUtils.isNotBlank(transCenter.getCode())
					&& FossConstants.ACTIVE.equals(transCenter
							.getTransferCenter())) {
				LOGGER.info("外发员查找到对应的外场,外场编码:" + transCenter.getCode()
						+ ",名称:" + transCenter.getName());
				// 外发部门//外发员所在的部门//BUG-7245 外发部门修改为外发员所属的外场，如果找不到外场，则报错
				stlDto.setWaifabumen(transCenter.getCode());
				// 外发部门名称//外发员所在的部门
				stlDto.setWaifabumenName(transCenter.getName());
			} else {
				LOGGER.info("外发员没有查找到对应的外场,外发部门编码:"
						+ tempDto.getExternalOrgCode());
				throw new ExternalBillException("外发员没有查找到对应的外场,请选择正确的外发员", "");
			}
			// 外发单号
			stlDto.setExternalBillNo(tempDto.getExternalBillNo());
			// 外发代理费
			stlDto.setExternalAgencyFee(tempDto.getExternalAgencyFee());
			// 代理送货费
			stlDto.setDeliveryFee(tempDto.getDeliveryFee());
			// 外发成本总额
			stlDto.setCostAmount(tempDto.getCostAmount());
			// 实收代理费
			stlDto.setReceiveAgencyFee(tempDto.getReceiveAgencyFee());
			// 实付代理费
			stlDto.setPayAgencyFee(tempDto.getPayAgencyFee());
			// 自动核销申请
			stlDto.setIsWriteOff(tempDto.getIsWriteOff());
			// 审核状态（数值型）
			stlDto.setAuditStatus(tempDto.getAuditStatus());
			// 偏线代理编号
			stlDto.setAgentCompanyCode(tempDto.getAgentCompanyCode());
			// 偏线代理名称
			stlDto.setAgentCompanyName(tempDto.getAgentCompanyName());
			// 是否中转外发
			stlDto.setTransferExternal(tempDto.getTransferExternal());
			// 币种
			stlDto.setCurrencyCode(tempDto.getCurrencyCode());
			// 修改日期
			stlDto.setBusinessDate(tempDto.getModifyDate());
			// 录入日期
			stlDto.setCreateTime(tempDto.getRegisterTime());
			// 是否初始化
			stlDto.setIsInit(tempDto.getIsInit());
		}

	}

	/**
	 * 拷贝数据
	 * 
	 * @author 310248
	 * 
	 */
	private void copyPropertiesCUBC(CubcExternalBillDto stlDto,
			ExternalBillDto tempDto, WaybillDto waybillDto) {
		if (stlDto != null && tempDto != null) {
			LOGGER.info("拷贝数据:" + tempDto.toString());
			// 运单号
			stlDto.setWaybillNo(tempDto.getWaybillNo());
			// 根据运单号查询运单信息
			if (waybillDto != null) {
				WaybillEntity waybill = waybillDto.getWaybillEntity();
				// 付款方式
				if (waybill != null) {
					stlDto.setPaidMethod(waybill.getPaidMethod());
					LOGGER.info("付款方式" + waybill.getPaidMethod());
				} else {
					throw new ExternalBillException("没有查询到对应的运单信息", "");
				}
				// --补充运单信息
				// 代收货款费用
				stlDto.setCodAmount(waybill.getCodAmount());
				// 总费用
				stlDto.setTotalFee(waybill.getTotalFee());
				// 送货费
				stlDto.setDeliveryGoodsFee(waybill.getDeliveryGoodsFee());
				// 到达部门编码
				stlDto.setLastLoadOrgCode(waybill.getLastLoadOrgCode());
				// 出发部门编码
				stlDto.setReceiveOrgCode(waybill.getReceiveOrgCode());
				// waybillId
				stlDto.setWaybillId(waybill.getId());
			} else {
				throw new ExternalBillException("没有查询到对应的运单信息", "");
			}

			// BUG-7245 外发部门修改为外发员所属的外场，如果找不到外场，则报错
			OrgAdministrativeInfoEntity transCenter = querySuperiorOrgByOrgCode(tempDto
					.getExternalOrgCode());
			// BUG-7245 外发部门修改为外发员所属的外场，如果找不到外场，则报错
			if (transCenter != null
					&& StringUtils.isNotBlank(transCenter.getCode())
					&& FossConstants.ACTIVE.equals(transCenter
							.getTransferCenter())) {
				LOGGER.info("外发员查找到对应的外场,外场编码:" + transCenter.getCode()
						+ ",名称:" + transCenter.getName());
				// 外发部门//外发员所在的部门//BUG-7245 外发部门修改为外发员所属的外场，如果找不到外场，则报错
				stlDto.setWaifabumen(transCenter.getCode());
				// 外发部门名称//外发员所在的部门
				stlDto.setWaifabumenName(transCenter.getName());
			} else {
				LOGGER.info("外发员没有查找到对应的外场,外发部门编码:"
						+ tempDto.getExternalOrgCode());
				throw new ExternalBillException("外发员没有查找到对应的外场,请选择正确的外发员", "");
			}
			// 外发单号
			stlDto.setExternalBillNo(tempDto.getExternalBillNo());
			// 外发代理费
			stlDto.setExternalAgencyFee(tempDto.getExternalAgencyFee());
			// 代理送货费
			stlDto.setDeliveryFee(tempDto.getDeliveryFee());
			// 外发成本总额
			stlDto.setCostAmount(tempDto.getCostAmount());
			// 实收代理费
			stlDto.setReceiveAgencyFee(tempDto.getReceiveAgencyFee());
			// 实付代理费
			stlDto.setPayAgencyFee(tempDto.getPayAgencyFee());
			// 自动核销申请
			stlDto.setIsWriteOff(tempDto.getIsWriteOff());
			// 审核状态（数值型）
			stlDto.setAuditStatus(tempDto.getAuditStatus());
			// 偏线代理编号
			stlDto.setAgentCompanyCode(tempDto.getAgentCompanyCode());
			// 偏线代理名称
			stlDto.setAgentCompanyName(tempDto.getAgentCompanyName());
			// 是否中转外发
			stlDto.setTransferExternal(tempDto.getTransferExternal());
			// 币种
			stlDto.setCurrencyCode(tempDto.getCurrencyCode());
			// 修改日期
			stlDto.setBusinessDate(tempDto.getModifyDate());
			// 录入日期
			stlDto.setCreateTime(tempDto.getRegisterTime());
			// 是否初始化
			stlDto.setIsInit(tempDto.getIsInit());
		}

	}

	/**
	 * 
	 * 根据运单号查询运单信息
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-20 上午10:21:14
	 * @see com.deppon.foss.module.transfer.partialline.api.server.service.IExternalBillService#queryWaybillInfo(com.deppon.foss.module.transfer.partialline.api.shared.dto.ExternalBillDto)
	 */
	@Override
	public ExternalBillWayBillInfoDto queryWaybillInfo(ExternalBillDto tempDto,
			String validateWaybillNo, CurrentInfo currentInfo) {
		if (tempDto != null && currentInfo != null) {
			LOGGER.info("查询筛选的当前部门名称:" + currentInfo.getCurrentDeptName()
					+ ",部门编码:" + currentInfo.getCurrentDeptCode());
			tempDto.setFilterOrgCode(currentInfo.getCurrentDeptCode());
		}
		ExternalBillWayBillInfoDto billInfo = null;
		//352203-sonar
		if (tempDto == null) {
			return billInfo;
		}
			// 偏线类型
			tempDto.setHandoverType(PartiallineConstants.HANDOVER_TYPE_PARTIALLINE_HANDOVER);
			// 验证运单号(录入偏线外发情况)，需要查询最新的交接单号
			if (PartiallineConstants.VALIDATE_YES.equals(validateWaybillNo)) {
				// 根据运单号取最新的交接单明细信息，获取并设置交接单号
				queryLastHandoverBillDetial(tempDto, currentInfo);
				// 执行验证运单号(录入偏线外发情况)，需要查询最新的交接单号
				validateQueryWaybillInfo(tempDto);
			}
			/***
			 * 1、根据运单号，找结算获取付款方式、 到付金额、 目的站、 收货日期、自提等运单信息
			 * 2、根据运单号，到运单获取提货网点code，再跟这个code找综合接口获取外发代理、
			 * 到达网点、到达网点电话、到达网点地址、代理电话
			 ***/
			WaybillDto waybillDto = null;
			try {
				waybillDto = waybillManagerService.queryWaybillByNo(tempDto
						.getWaybillNo());
			} catch (Exception e) {
				LOGGER.error(PartiallineConstants.WAY_BILL_EXCEPTION, e);
				throw new ExternalBillException(
						PartiallineConstants.WAY_BILL_EXCEPTION, "");
			}
			// 查询到运单信息
			//sonar-352203
			if (waybillDto == null) {
				LOGGER.info("没有查询到对应的运单信息,运单号:" + tempDto.getWaybillNo());
				throw new ExternalBillException("没有查询到对应的运单信息", "");
			}
				WaybillEntity waybill = waybillDto.getWaybillEntity();
				//sonar-352203
				if (waybill == null) {
					LOGGER.info("没有查询到对应的运单信息,运单号:" + tempDto.getWaybillNo());
					throw new ExternalBillException("没有查询到对应的运单信息", "");
				}
					// 用于存储运单信息
					billInfo = new ExternalBillWayBillInfoDto();
					if (StringUtils.isNotBlank(waybill.getPaidMethod())) {
						billInfo.setPaidMethodCode(waybill.getPaidMethod());
					}
					// 根据外发代理编号查询,外发代理,到达网点,到达网点电话,到达网点地址,代理电话
					// 提货网点CODE
					String agencyBranchCode = waybill
							.getCustomerPickupOrgCode();
					AgencyBranchOrCompanyDto companyDto = null;
					if (StringUtils.isNotBlank(agencyBranchCode)) {
						// 代理信息Dto
						companyDto = vehicleAgencyDeptService
								.queryAgencyBranchCompanyInfo(agencyBranchCode);
					}
					//sonar-352203
					if (companyDto == null) {
						LOGGER.info("没有查询到对应的运单的代理信息,提货网点CODE:" + agencyBranchCode);
						throw new ExternalBillException("没有查询到对应的运单的代理信息", "");
					}
						// 外发代理(代理公司名称)
						if (StringUtils.isNotBlank(companyDto
								.getAgentCompanyName())) {
							billInfo.setAgentCompanyName(companyDto
									.getAgentCompanyName());
						}
						// 到达网点
						if (StringUtils.isNotBlank(companyDto
								.getAgentDeptName())) {
							billInfo.setAgentDeptName(companyDto
									.getAgentDeptName());
						}
						// 到达网点电话
						if (StringUtils.isNotBlank(companyDto
								.getBranchContactPhone())) {
							billInfo.setContactPhone(companyDto
									.getBranchContactPhone());
						}
						// 到达网点地址
						if (StringUtils.isNotBlank(companyDto
								.getBranchAddress())) {
							billInfo.setAddress(companyDto.getBranchAddress());
						}
						// 代理电话
						if (StringUtils.isNotBlank(companyDto
								.getPartnerContactPhone())) {
							billInfo.setPartnerContactPhone(companyDto
									.getPartnerContactPhone());
						}
						// 币种
						billInfo.setCurrencyCode(waybill.getCurrencyCode());
						// 付款方式
						if (StringUtils.isNotBlank(waybill.getPaidMethod())) {
							billInfo.setPaidMethodCode(waybill.getPaidMethod());
							// 付款方式中文
							billInfo.setPaidMethod(DictUtil.rendererSubmitToDisplay(
									waybill.getPaidMethod(),
									PartiallineConstants.SETTLEMENT__PAYMENT_TYPE));
						}
						// 提货方式（是否自提）
						billInfo.setBeAutoDelivery(waybill.getReceiveMethod());
						// 到付金额
						billInfo.setToPayAmount(waybill.getToPayAmount());
						// 目的站
						billInfo.setTargetOrgCode(waybill.getTargetOrgCode());
						// 收货日期
						billInfo.setHandGoodsTime(DateUtils.convert(
								waybill.getCreateTime(),
								DateUtils.DATE_TIME_FORMAT));
						// 收货部门
						if (StringUtils.isNotBlank(waybill.getCreateOrgCode())) {
							OrgAdministrativeInfoEntity org = orgAdministrativeInfoService
									.queryOrgAdministrativeInfoByCode(waybill
											.getCreateOrgCode());
							if (org != null) {
								billInfo.setCreateOrgCode(org.getName());
							}
						}
						// 重量
						billInfo.setGoodsWeightTotal(waybill
								.getGoodsWeightTotal());
						// 体积
						billInfo.setGoodsVolumeTotal(waybill
								.getGoodsVolumeTotal());
						// 件数
						if (waybill.getGoodsQtyTotal() != null) {
							billInfo.setGoodsQtyTotal(BigDecimal
									.valueOf(waybill.getGoodsQtyTotal()));
						}
						// 运费
						billInfo.setTransportFee(waybill.getTransportFee());
						// 货物名称
						billInfo.setGoodsName(waybill.getGoodsName());
						// 保险费
						billInfo.setInsuranceFee(waybill.getInsuranceFee());
						// 包装
						billInfo.setGoodsPackage(waybill.getGoodsPackage());
						// 托运人姓名
						billInfo.setDeliveryCustomerName(waybill
								.getDeliveryCustomerName());
						// 保险价值
						billInfo.setInsuranceAmount(waybill
								.getInsuranceAmount());
						// 运输事项
						String yunshushixiang = "";
						if (StringUtils.isNotBlank(waybill.getInnerNotes())) {
							yunshushixiang = yunshushixiang.concat(
									waybill.getInnerNotes()).concat(",");
						}
						if (StringUtils.isNotBlank(waybill.getOuterNotes())) {
							yunshushixiang = yunshushixiang.concat(waybill
									.getOuterNotes());
						}
						billInfo.setYunshushixiang(yunshushixiang);
						// 开单金额
						billInfo.setTotalFee(waybill.getTotalFee());
						// 代收货款
						if (waybill.getCodAmount() != null) {
							billInfo.setCodAmount(waybill.getCodAmount()
									.toString());
						}
						// 收货客户
						billInfo.setReceiveCustomerName(waybill
								.getReceiveCustomerName());
						// 收货客户联系人
						billInfo.setReceiveCustomerContact(waybill
								.getReceiveCustomerContact());
						// 付款方式CODE
						billInfo.setPaidMethodCode(waybill.getPaidMethod());

						// 初始化运单号
						billInfo.setWaybillNo(waybill.getWaybillNo());
//					} else {
//						LOGGER.info("没有查询到对应的运单的代理信息,提货网点CODE:" + agencyBranchCode);
//						throw new ExternalBillException("没有查询到对应的运单的代理信息", "");
//					}
//				} else {
//					LOGGER.info("没有查询到对应的运单信息,运单号:" + tempDto.getWaybillNo());
//					throw new ExternalBillException("没有查询到对应的运单信息", "");
//				}
//			} else {
//				LOGGER.info("没有查询到对应的运单信息,运单号:" + tempDto.getWaybillNo());
//				throw new ExternalBillException("没有查询到对应的运单信息", "");
//			}

			// 计算外发代理费用和外发送货费
			if (PartiallineConstants.VALIDATE_YES.equals(validateWaybillNo)) {
				calculateDlyFeeAndEtdAgFee(billInfo);
			}

//		}
		return billInfo;
	}

	/**
	 * 验证运单号已经做过交接，且未录入偏线外发单；以及外发单未被使用
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-20 上午8:56:39
	 */
	private void validateAddExternalBill(ExternalBillDto tempDto)
			throws ExternalBillException {
		LOGGER.info("校验运单号是否做过交接的偏线运单，如果本运单号未做过交接，则提示错误消息");
		// 校验运单号是否做过交接的偏线运单，如果本运单号未做过交接，则提示错误消息
		hasWaybillNoHanded(tempDto);
		// 校验本运单号是否已经被录入了偏线外发单，如果已经录入，则提示错误消息
		LOGGER.info("校验本运单号是否已经被录入了偏线外发单，如果已经录入，则提示错误消息");
		hasWaybillNoInputed(tempDto);
		// 验证外发单号未录入过，如果外发单已经录入过，则返回错误提示信息
		LOGGER.info(" 验证外发单号未录入过，如果外发单已经录入过，则返回错误提示信息");
		hasExternalBillNoInputed(tempDto);
	}

	/**
	 * 验证更新的外发单号是否已经被录入过
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-17 上午9:34:52
	 */
	private void validateUpdateExternalBill(ExternalBillDto tempDto,
			ExternalBillEntity oldExternalBill) throws ExternalBillException {
		// 原有外发单
		ExternalBillEntity tempEntity = oldExternalBill;
		if (tempEntity != null) {
			// 首先查询本外发单是否改过
			if (StringUtils.isNotBlank(tempEntity.getExternalBillNo())
					&& tempEntity.getExternalBillNo().equals(
							tempDto.getExternalBillNo())) {
				// 没有改过
			} else {
				// 改动过
				// 验证外发单号未录入过，如果外发单号已经录入过，则返回错误提示信息
				hasExternalBillNoInputed(tempDto);
			}
			// 查看原来是否为中转外发，且查看本次是否修改了本字段，如果修改了本字段则需要验证是否可以改动
			if (PartiallineConstants.IS_TRANSFER_EXTERNAL_YES.equals(tempEntity
					.getTransferExternal())) {
				if (tempEntity.getTransferExternal().equals(
						tempDto.getTransferExternal())) {
					// 没有改动中转外发字段，验证通过
				} else {// 则需要查看本单后面是否有存在本运单的外发单，如果存在，则不允许修改“中转外发字段”
					tempDto.setRegisterTime(tempEntity.getRegisterTime());
					// 查询后续是否存在本运单的中转外发单录入
					// 如果查询发现，存在运单号相同，且后面录入的情况，则不允许更新
					if (externalBillDao
							.queryByWaybillNoAndRegisterTime(tempDto) > 0) {
						// 抛出业务异常“中转外发单对应的运单号已经被重新录入，不能更改此状态”
						throw new ExternalBillException(
								PartiallineConstants.EXCEPTION_WAYBILL_TRANSFER_EXTERNAL_INPUTED_AGAIN);
					}
				}
			}
		} else {
			// 抛出业务异常“中转外发单对应的运单号已经被重新录入，不能更改此状态”
			throw new ExternalBillException("未查询到相应的外发单", "");
		}
	}

	/**
	 * 验证查询的运单号
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-17 下午4:43:52
	 */
	private void validateQueryWaybillInfo(ExternalBillDto tempDto)
			throws ExternalBillException {

		// 通过运单号,查询是否存在(1.偏线 2.已经做过交接)的外发单交接单明细，如果本运单号未做过交接，则提示错误消息
		hasWaybillNoHanded(tempDto);
		// 验证运单号未录入过，如果运单号已经录入过，则返回错误提示信息
		hasWaybillNoInputed(tempDto);
	}

	/**
	 * 验证外发单号是否已经录入，如果查外发单号已经录入过，提示错误外发单已经被使用过
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-18 下午4:46:28
	 */
	private void hasExternalBillNoInputed(ExternalBillDto tempDto)
			throws ExternalBillException {
		List<ExternalBillDto> inputedList = externalBillDao
				.queryByExternalBillNo(tempDto);
		// 如果查询本次的外发单号已经录入过，提示错误外发单已经被使用过
		// 如果查询结果不大于0，则表示已经录入过，需要进一步验证
		if (CollectionUtils.isNotEmpty(inputedList)) {
			throw new ExternalBillException(
					PartiallineConstants.EXCEPTION_EXTERNALBILLNO_HAS_USED);
		}
	}

	/**
	 * 通过运单号,查询是否存在(1.偏线 2.已经做过交接)的外发单交接单明细
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-18 下午4:43:26
	 */
	private void hasWaybillNoHanded(ExternalBillDto tempDto)
			throws ExternalBillException {
		if (tempDto != null) {
			// 非已预配、非作废状态
			List<Integer> billStatuslist = new ArrayList<Integer>();
			// 20：已交接，
			billStatuslist
					.add(LoadConstants.HANDOVERBILL_STATE_FORMAL_HANDOVER);
			// (集配交接单专属状态，21：已配载)
			billStatuslist
					.add(LoadConstants.HANDOVERBILL_STATE_ALREADY_ASSEMBLE);
			// 30：已出发，
			billStatuslist.add(LoadConstants.HANDOVERBILL_STATE_ALREADY_DEPART);
			// 40：已到达，
			billStatuslist.add(LoadConstants.HANDOVERBILL_STATE_ALREADY_ARRIVE);
			// 50：已入库
			billStatuslist
					.add(LoadConstants.HANDOVERBILL_STATE_ALREADY_INSTOCK);

			tempDto.setBillStatuslist(billStatuslist);
			// 查询
			Long handCnt = externalBillDao.queryCount(tempDto,
					"queryWaybillNoHasHanded");
			// 如果运单号未交接
			if (handCnt < PartiallineConstants.LIST_SIZE_ONE) {
				throw new ExternalBillException(
						PartiallineConstants.EXCEPTION_WAYBILL_HAS_NOT_HANDED);
			}
		}

	}

	/**
	 * 验证运单号是否录入过，如果运单已经录入过，则返回错误提示信息
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-18 下午4:42:38
	 */
	private void hasWaybillNoInputed(ExternalBillDto tempDto)
			throws ExternalBillException {
		// 根据运单号查询已录入列表
		List<ExternalBillDto> inputedList = externalBillDao
				.queryByWaybillNo(tempDto);
		// 如果运单号已经被录入过
		if (CollectionUtils.isNotEmpty(inputedList)) {
			// 如果本运单已经录入过，则需要遍历是否全部为外发退回情况；如果对应的外发单全部为外发退回，则可以通过校验
			boolean flag = false;
			for (ExternalBillDto dto : inputedList) {
				// 如果存在非作废且非中转外发，则不允许录入, 抛出异常
				if (PartiallineConstants.IS_TRANSFER_EXTERNAL_NO.equals(dto
						.getTransferExternal())
						&& !PartiallineConstants.PARTIALLINE_AUDITSTATUS_INVALID
								.equals(dto.getAuditStatus())) {
					flag = true;
					break;
				}
			}
			if (flag) {
				throw new ExternalBillException(
						PartiallineConstants.EXCEPTION_WAYBILL_HAS_INPUTED);
			}
		}
	}

	/**
	 * 审核偏线
	 * 
	 * @author 096598-foss-zhongyubing
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @date 2012-10-20 上午11:48:48
	 * @see com.deppon.foss.module.transfer.partialline.api.server.service.IExternalBillService#auditPartialline(java.util.List)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public int auditPartialline(List<String> auditIds, CurrentInfo currentInfo)
			throws IllegalAccessException, InvocationTargetException {
		// 查询审核的外发单列表，提供给结算系统
		List<ExternalBillDto> auditList = externalBillDao
				.selectExternalBillByPrimaryKeys(auditIds);
		if (CollectionUtils.isNotEmpty(auditList)) {
			List<SettlementExternalBillDto> stlDtos = new ArrayList<SettlementExternalBillDto>();
			List<CubcExternalBillDto> cubcDtos = new ArrayList<CubcExternalBillDto>();
			// 循环拷贝
			SettlementExternalBillDto stlDto = null;

			CubcExternalBillDto cubcDto = null;
			// 运单对象
			WaybillDto waybillDto = null;
			for (ExternalBillDto tempDto : auditList) {
				if (PartiallineConstants.PARTIALLINE_AUDITSTATUS_HASAUDITED
						.equals(tempDto.getAuditStatus())) {
					throw new ExternalBillException("运单号:"
							+ tempDto.getWaybillNo() + ",外发单已审核,请核实再操作", "");
				}
				stlDto = new SettlementExternalBillDto();
				cubcDto = new CubcExternalBillDto();
				// 根据运单号查询运单信息
				try {
					waybillDto = waybillManagerService.queryWaybillByNo(tempDto
							.getWaybillNo());
				} catch (Exception e) {
					LOGGER.error(PartiallineConstants.WAY_BILL_EXCEPTION, e);
					throw new ExternalBillException(
							PartiallineConstants.WAY_BILL_EXCEPTION, "");
				}
				// stlDto.
				if (waybillDto != null) {
					// 拷贝数据
					copyProperties(stlDto, tempDto, waybillDto);

					copyPropertiesCUBC(cubcDto, tempDto, waybillDto);
					// 310248添加，由于韩国件外发费用总额为0未生成应付单，所以不将数据传给结算
					if (stlDto.getCostAmount() != null
							&& stlDto.getCostAmount()
									.compareTo(BigDecimal.ZERO) > 0) {
						stlDtos.add(stlDto);
					}
					if (cubcDto.getCostAmount() != null
							&& cubcDto.getCostAmount().compareTo(
									BigDecimal.ZERO) > 0) {
						cubcDtos.add(cubcDto);
					}
				}

			}
			// 灰度实体对象封装
			//封装灰度实体，类型是运单
			List<String> wayBillList = new ArrayList<String>();
			for (SettlementExternalBillDto dtoTemp : stlDtos) {
				wayBillList.add(dtoTemp.getWaybillNo());
			}
			GrayParameterDto parDto = new GrayParameterDto();
			parDto.setSourceBillType(CUBCGrayUtil.SBType.W.getName());
			parDto.setSourceBillNos((String []) wayBillList.toArray(new String [wayBillList.size()]));
			//调用灰度
			VestResponse vestResponseDto = cubcUtil.getUcbcGrayData(parDto, new Throwable());
			//STL CUBC调用标识
			String fossCubc = "";
			List<String> fossWayBillList = new ArrayList<String>();
			List<String> ucbcWayBillList = new ArrayList<String>();
			List<SettlementExternalBillDto> fossEntityList = new ArrayList<SettlementExternalBillDto>();
			List<CubcExternalBillDto> ucbcEntityList = new ArrayList<CubcExternalBillDto>();
			//分析灰度返回结果集，如果是2条的情况
			if (vestResponseDto.getVestBatchResult().size() > 1) {
				fossCubc = CUBCGrayContants.SYSTEM_CODE_FOSS_CUBC;
				//循环得到分流的运单号
				for (VestBatchResult vestResult : vestResponseDto.getVestBatchResult()) {
					 if (vestResult.getVestSystemCode().equals(CUBCGrayContants.SYSTEM_CODE_FOSS)) {
						 fossWayBillList = vestResult.getVestObject();
					 } else {
						 ucbcWayBillList = vestResult.getVestObject();
					 }
				}
				//得到运单号对应的实体，提供给FOSS STL用
				for (SettlementExternalBillDto dto : stlDtos) {
					for (String wayBill : fossWayBillList) {
						if (dto.getWaybillNo().equals(wayBill)) {
							fossEntityList.add(dto);
						}
					}
				}
				//得到运单号对应的实体，提供给UCBC用
				for (CubcExternalBillDto dto : cubcDtos) {
					for (String wayBill : ucbcWayBillList) {
						if (dto.getWaybillNo().equals(wayBill)) {
							ucbcEntityList.add(dto);
						}
					}
				}
				
				//赋值
				stlDtos = fossEntityList;
				cubcDtos = ucbcEntityList;
			}
			
			if (vestResponseDto.getVestBatchResult().get(0).getVestSystemCode().equals(CUBCGrayContants.SYSTEM_CODE_FOSS) || fossCubc.equals(CUBCGrayContants.SYSTEM_CODE_FOSS_CUBC)) {
				if (null != stlDtos && stlDtos.size() > 0) {
					// 结算审核
					vehicleAgencyExternalService.auditExternalBill(stlDtos,
							currentInfo);
				}
			} 

			if (vestResponseDto.getVestBatchResult().get(0).getVestSystemCode().equals(CUBCGrayContants.SYSTEM_CODE_CUBC) || fossCubc.equals(CUBCGrayContants.SYSTEM_CODE_FOSS_CUBC)) {
				if (null != cubcDtos && cubcDtos.size() > 0) {
					// cubc审核
					CubcExternalBillRequest requestExternalBillDto = new CubcExternalBillRequest();
					requestExternalBillDto.setCubcExternalList(cubcDtos);
					// 状态；1审核2反审核,0作废
					requestExternalBillDto.setStatus("1");
					// 员工工号
					requestExternalBillDto.setEmpCode(FossUserContext
							.getCurrentInfo().getEmpCode());
					// 员工姓名
					requestExternalBillDto.setEmpName(FossUserContext
							.getCurrentInfo().getEmpName());
					// 当前登录部门编码
					requestExternalBillDto.setCurrentDeptCode(FossUserContext
							.getCurrentInfo().getCurrentDeptCode());
					// 当前登录部门名称
					requestExternalBillDto.setCurrentDeptName(FossUserContext
							.getCurrentInfo().getCurrentDeptName());
	                CubcExternalBillResponse response = null;
					try {
						LOGGER.error("FOSS推送审核偏线外发单号" + requestExternalBillDto + "给CUBC开始...");
						LOGGER.error("FOSS推送审核偏线外发单号参数：" + JSONObject.toJSONString(requestExternalBillDto));
						response = fossToCubc.pushExternalBillStatus(requestExternalBillDto);
						if (null == response) {
							throw new BusinessException("FOSS推送CUBC审核偏线外发单操作时ESB发生异常!");
						}
						if (StringUtils.equals("0", response.getResult())) {
							throw new BusinessException(response.getReason());
						}
					} catch (Exception e) {
						throw new ExternalBillException("CUBC - " + e.getMessage());
					}
					LOGGER.error("FOSS推送审核偏线外发单号" + requestExternalBillDto + "给CUBC结束;返回值是 :" + response);
				}
			}
		}
		// 再次查询查询，防止并发情况,做校验
		auditList = externalBillDao.selectExternalBillByPrimaryKeys(auditIds);
		// 可否审核标记
		boolean flag = true;
		// 循环校验
		for (ExternalBillDto tempDto : auditList) {
			// 如果存在不满足的情况，则改变标记,非待审核或单审核，则不允许审核操作
			if (PartiallineConstants.PARTIALLINE_AUDITSTATUS_HASAUDITED
					.equals(tempDto.getAuditStatus())
					|| PartiallineConstants.PARTIALLINE_AUDITSTATUS_INVALID
							.equals(tempDto.getAuditStatus())
					|| PartiallineConstants.PARTIALLINE_AUDITSTATUS_UNKNOWN
							.equals(tempDto.getAuditStatus())) {
				flag = false;
				throw new ExternalBillException("存在不是待审核状态的外发单,请核实再操作", "");
			}
		}
		// 如果可以修改
		if (flag) {
			// 更新为“已审核”状态
			String auditStatus = PartiallineConstants.PARTIALLINE_AUDITSTATUS_HASAUDITED;
			// 执行更新
				externalBillDao
					.updateAuditStatusByPrimaryKey(auditIds, auditStatus);

		}

		return PartiallineConstants.SERVICE_RESULT_INT_VALUE;
	}

	/**
	 * 反审核偏线
	 * 
	 * @author 096598-foss-zhongyubing
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @date 2012-10-20 下午1:44:54
	 * @see com.deppon.foss.module.transfer.partialline.api.server.service.IExternalBillService#deAuditPartialline(java.util.List)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public int deAuditPartialline(List<String> auditIds, CurrentInfo currentInfo)
			throws IllegalAccessException, InvocationTargetException {

		// 查询反审核的外发单列表，提供给结算系统
		List<ExternalBillDto> auditList = externalBillDao
				.selectExternalBillByPrimaryKeys(auditIds);
		if (CollectionUtils.isNotEmpty(auditList)) {
			List<SettlementExternalBillDto> stlDtos = new ArrayList<SettlementExternalBillDto>();
			List<CubcExternalBillDto> cubcDtos = new ArrayList<CubcExternalBillDto>();
			// 循环拷贝
			SettlementExternalBillDto stlDto = null;
			CubcExternalBillDto cubcDto = null;
			// 运单对象
			WaybillDto waybillDto = null;
			for (ExternalBillDto tempDto : auditList) {
				// 待审核状态，不能反审核
				if (PartiallineConstants.PARTIALLINE_AUDITSTATUS_WAITINGAUDIT.equals(tempDto.getAuditStatus())) {
					throw new ExternalBillException("运单号:" + tempDto.getWaybillNo() + ",外发单为待审核,不能翻审核,请核实再操作", "");
				}
				// 已经为反审核状态，不能再做反审核
				if (PartiallineConstants.PARTIALLINE_AUDITSTATUS_BACKAUDIT.equals(tempDto.getAuditStatus())) {
					throw new ExternalBillException("运单号:" + tempDto.getWaybillNo() + ",外发单已为反审核状态,请核实再操作", "");
				}
				stlDto = new SettlementExternalBillDto();
				cubcDto = new CubcExternalBillDto();
				// 根据运单号查询运单信息
				try {
					waybillDto = waybillManagerService.queryWaybillByNo(tempDto.getWaybillNo());
				} catch (Exception e) {
					LOGGER.error(PartiallineConstants.WAY_BILL_EXCEPTION, e);
					throw new ExternalBillException(PartiallineConstants.WAY_BILL_EXCEPTION, "");
				}
				if (waybillDto != null) {
					// 拷贝数据
					copyProperties(stlDto, tempDto, waybillDto);
					copyPropertiesCUBC(cubcDto, tempDto, waybillDto);
					// 310248添加，由于韩国件外发费用总额为0未生成应付单，所以不将数据传给结算
					if (stlDto.getCostAmount() != null && stlDto.getCostAmount().compareTo(BigDecimal.ZERO) > 0) {
						stlDtos.add(stlDto);
					}
					if (cubcDto.getCostAmount() != null && cubcDto.getCostAmount().compareTo(BigDecimal.ZERO) > 0) {
						cubcDtos.add(cubcDto);
					}

				}

			}
			// 灰度实体对象封装
			// 封装灰度实体，类型是运单
			List<String> wayBillList = new ArrayList<String>();
			for (SettlementExternalBillDto dtoTemp : stlDtos) {
				wayBillList.add(dtoTemp.getWaybillNo());
			}
			LOGGER.error("灰度开始...");
			GrayParameterDto parDto = new GrayParameterDto();
			parDto.setSourceBillType(CUBCGrayUtil.SBType.W.getName());
			parDto.setSourceBillNos((String[]) wayBillList.toArray(new String[wayBillList.size()]));
			// 调用灰度
			VestResponse vestResponseDto = cubcUtil.getUcbcGrayData(parDto, new Throwable());
			LOGGER.error("灰度结束...");
			// STL CUBC调用标识
			String fossCubc = "";
			List<String> fossWayBillList = new ArrayList<String>();
			List<String> ucbcWayBillList = new ArrayList<String>();
			List<SettlementExternalBillDto> fossEntityList = new ArrayList<SettlementExternalBillDto>();
			List<CubcExternalBillDto> ucbcEntityList = new ArrayList<CubcExternalBillDto>();
			// 分析灰度返回结果集，如果是2条的情况
			if (vestResponseDto.getVestBatchResult().size() > 1) {
				fossCubc = CUBCGrayContants.SYSTEM_CODE_FOSS_CUBC;
				// 循环得到分流的运单号
				for (VestBatchResult vestResult : vestResponseDto.getVestBatchResult()) {
					if (vestResult.getVestSystemCode().equals(CUBCGrayContants.SYSTEM_CODE_FOSS)) {
						fossWayBillList = vestResult.getVestObject();
					} else {
						ucbcWayBillList = vestResult.getVestObject();
					}
				}

				// 得到运单号对应的实体，提供给FOSS STL用
				for (SettlementExternalBillDto dto : stlDtos) {
					for (String wayBill : fossWayBillList) {
						if (dto.getWaybillNo().equals(wayBill)) {
							fossEntityList.add(dto);
						}
					}
				}
				// 得到运单号对应的实体，提供给UCBC用
				for (CubcExternalBillDto dto : cubcDtos) {
					for (String wayBill : ucbcWayBillList) {
						if (dto.getWaybillNo().equals(wayBill)) {
							ucbcEntityList.add(dto);
						}
					}
				}

				// 赋值
				stlDtos = fossEntityList;
				cubcDtos = ucbcEntityList;
			}
			if (vestResponseDto.getVestBatchResult().get(0).getVestSystemCode().equals(
					CUBCGrayContants.SYSTEM_CODE_FOSS) || fossCubc.equals(CUBCGrayContants.SYSTEM_CODE_FOSS_CUBC)) {
				LOGGER.error("调用FOSS结算反审核" + vestResponseDto.getVestBatchResult().get(0).getVestSystemCode());
				if (null != stlDtos && stlDtos.size() > 0) {
					// 结算反审核
					vehicleAgencyExternalService.reverseAuditExternalBill(stlDtos, currentInfo);
				}
			}
			if (vestResponseDto.getVestBatchResult().get(0).getVestSystemCode().equals(
					CUBCGrayContants.SYSTEM_CODE_CUBC) || fossCubc.equals(CUBCGrayContants.SYSTEM_CODE_FOSS_CUBC)) {
				LOGGER.error("调用CUBC客户结算中心反审核" + vestResponseDto.getVestBatchResult().get(0).getVestSystemCode());
				if (null != cubcDtos && cubcDtos.size() > 0) {
					// cubc反审核
					CubcExternalBillRequest requestExternalBillDto = new CubcExternalBillRequest();
					requestExternalBillDto.setCubcExternalList(cubcDtos);
					requestExternalBillDto.setStatus("2");
					// 员工工号
					requestExternalBillDto.setEmpCode(FossUserContext.getCurrentInfo().getEmpCode());
					// 员工姓名
					requestExternalBillDto.setEmpName(FossUserContext.getCurrentInfo().getEmpName());
					// 当前登录部门编码
					requestExternalBillDto.setCurrentDeptCode(FossUserContext.getCurrentInfo().getCurrentDeptCode());
					// 当前登录部门名称
					requestExternalBillDto.setCurrentDeptName(FossUserContext.getCurrentInfo().getCurrentDeptName());
					LOGGER.error("FOSS推送反审核偏线外发单号" + requestExternalBillDto + "给CUBC开始...");

					CubcExternalBillResponse response = null;
					try {
						response = fossToCubc.pushExternalBillStatus(requestExternalBillDto);
				 if(null == response){
					 throw new BusinessException("FOSS推送CUBC反审核偏线外发单操作时ESB发生异常!");	
						}
				 if(StringUtils.equals("0",response.getResult())){
					 throw new BusinessException(response.getReason());	
						}
					} catch (Exception e) {
						throw new ExternalBillException("FOSS推送给CUBC反审核偏线外发单状态,调用CUBC接口操作失败，失败原因:" + e.getMessage());
					}
				}
			}
		}
		// 再次查询查询，防止并发情况,做校验
		auditList = externalBillDao.selectExternalBillByPrimaryKeys(auditIds);
		// 可否审核标记
		boolean flag = true;
		// 循环校验
		for (ExternalBillDto tempDto : auditList) {
			// 如果存在不满足的情况，则改变标记
			if (!PartiallineConstants.PARTIALLINE_AUDITSTATUS_HASAUDITED
					.equals(tempDto.getAuditStatus())) {
				flag = false;
				throw new ExternalBillException("存在不是已审核状态的外发单,请核实再操作", "");
			}
		}
		// 如果可以修改
		if (flag) {
			// 更新为“反审核”状态
			String auditStatus = PartiallineConstants.PARTIALLINE_AUDITSTATUS_BACKAUDIT;
			// 执行更新
			externalBillDao
					.updateAuditStatusByPrimaryKey(auditIds, auditStatus);
		}
		return PartiallineConstants.SERVICE_RESULT_INT_VALUE;
	}

	/**
	 * 作废偏线
	 * 
	 * @author 096598-foss-zhongyubing
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @date 2012-10-20 下午1:44:54
	 * @see com.deppon.foss.module.transfer.partialline.api.server.service.IExternalBillService#invalidePartialline(java.util.List)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public int invalidePartialline(List<String> auditIds,
			CurrentInfo currentInfo) throws IllegalAccessException,
			InvocationTargetException {

		// 查询作废的外发单列表，提供给结算系统
		List<ExternalBillDto> auditList = externalBillDao
				.selectExternalBillByPrimaryKeys(auditIds);
		if (CollectionUtils.isNotEmpty(auditList)) {
			List<SettlementExternalBillDto> stlDtos = new ArrayList<SettlementExternalBillDto>();
			List<CubcExternalBillDto> cubcDtos = new ArrayList<CubcExternalBillDto>();
			// 循环拷贝
			SettlementExternalBillDto stlDto = null;
			CubcExternalBillDto cubcDto = null;
			// 运单对象
			WaybillDto waybillDto = null;
			for (ExternalBillDto tempDto : auditList) {
				// 已作废，则不能再作废
				if (PartiallineConstants.PARTIALLINE_AUDITSTATUS_INVALID
						.equals(tempDto.getAuditStatus())) {
					throw new ExternalBillException("运单号:"
							+ tempDto.getWaybillNo() + ",外发单已作废,请核实再操作", "");
				}
				stlDto = new SettlementExternalBillDto();
				cubcDto = new CubcExternalBillDto();
				// 根据运单号查询运单信息
				try {
					waybillDto = waybillManagerService.queryWaybillByNo(tempDto
							.getWaybillNo());
				} catch (Exception e) {
					LOGGER.error(PartiallineConstants.WAY_BILL_EXCEPTION, e);
					throw new ExternalBillException(
							PartiallineConstants.WAY_BILL_EXCEPTION, "");
				}
				if (waybillDto != null) {
					// 拷贝数据
					copyProperties(stlDto, tempDto, waybillDto);
					copyPropertiesCUBC(cubcDto, tempDto, waybillDto);
					// 310248添加，由于韩国件外发费用总额为0未生成应付单，所以不将数据传给结算
					if (stlDto.getCostAmount() != null
							&& stlDto.getCostAmount()
									.compareTo(BigDecimal.ZERO) > 0) {
						stlDtos.add(stlDto);
					}
					if (cubcDto.getCostAmount() != null
							&& cubcDto.getCostAmount().compareTo(
									BigDecimal.ZERO) > 0) {
						cubcDtos.add(cubcDto);
					}
				}

			}
			
			// 灰度实体对象封装
			//封装灰度实体，类型是运单
			List<String> wayBillList = new ArrayList<String>();
			for (SettlementExternalBillDto dtoTemp : stlDtos) {
				wayBillList.add(dtoTemp.getWaybillNo());
			}
			GrayParameterDto parDto = new GrayParameterDto();
			parDto.setSourceBillType(CUBCGrayUtil.SBType.W.getName());
			parDto.setSourceBillNos((String []) wayBillList.toArray(new String [wayBillList.size()]));
			//调用灰度
			VestResponse vestResponseDto = cubcUtil.getUcbcGrayData(parDto, new Throwable());
			//STL CUBC调用标识
			String fossCubc = "";
			List<String> fossWayBillList = new ArrayList<String>();
			List<String> ucbcWayBillList = new ArrayList<String>();
			List<SettlementExternalBillDto> fossEntityList = new ArrayList<SettlementExternalBillDto>();
			List<CubcExternalBillDto> ucbcEntityList = new ArrayList<CubcExternalBillDto>();
			//分析灰度返回结果集，如果是2条的情况
			if (vestResponseDto.getVestBatchResult().size() > 1) {
				fossCubc = CUBCGrayContants.SYSTEM_CODE_FOSS_CUBC;
				//循环得到分流的运单号
				for (VestBatchResult vestResult : vestResponseDto.getVestBatchResult()) {
					 if (vestResult.getVestSystemCode().equals(CUBCGrayContants.SYSTEM_CODE_FOSS)) {
						 fossWayBillList = vestResult.getVestObject();
					 } else {
						 ucbcWayBillList = vestResult.getVestObject();
					 }
				}
				
				//得到运单号对应的实体，提供给FOSS STL用
				for (SettlementExternalBillDto dto : stlDtos) {
					for (String wayBill : fossWayBillList) {
						if (dto.getWaybillNo().equals(wayBill)) {
							fossEntityList.add(dto);
						}
					}
				}
				//得到运单号对应的实体，提供给UCBC用
				for (CubcExternalBillDto dto : cubcDtos) {
					for (String wayBill : ucbcWayBillList) {
						if (dto.getWaybillNo().equals(wayBill)) {
							ucbcEntityList.add(dto);
						}
					}
				}
				
				//赋值
				stlDtos = fossEntityList;
				cubcDtos = ucbcEntityList;
			}
			if (vestResponseDto.getVestBatchResult().get(0).getVestSystemCode().equals(CUBCGrayContants.SYSTEM_CODE_FOSS) || fossCubc.equals(CUBCGrayContants.SYSTEM_CODE_FOSS_CUBC)) {
				if (null != stlDtos && stlDtos.size() > 0) {
					// 结算作废
					vehicleAgencyExternalService.disableExternalBill(stlDtos,
							currentInfo);
				}
			}
			
			if (vestResponseDto.getVestBatchResult().get(0).getVestSystemCode().equals(CUBCGrayContants.SYSTEM_CODE_CUBC) || fossCubc.equals(CUBCGrayContants.SYSTEM_CODE_FOSS_CUBC)) {
				if (null != cubcDtos && cubcDtos.size() > 0) {
					// cubc反审核
					CubcExternalBillRequest requestExternalBillDto = new CubcExternalBillRequest();
					requestExternalBillDto.setCubcExternalList(cubcDtos);
					requestExternalBillDto.setStatus("0");
					// 员工工号
					requestExternalBillDto.setEmpCode(FossUserContext
							.getCurrentInfo().getEmpCode());
					// 员工姓名
					requestExternalBillDto.setEmpName(FossUserContext
							.getCurrentInfo().getEmpName());
					// 当前登录部门编码
					requestExternalBillDto.setCurrentDeptCode(FossUserContext
							.getCurrentInfo().getCurrentDeptCode());
					// 当前登录部门名称
					requestExternalBillDto.setCurrentDeptName(FossUserContext
							.getCurrentInfo().getCurrentDeptName());
			LOGGER.error("FOSS推送作废偏线外发单号" + requestExternalBillDto+ "给CUBC开始...");
			       CubcExternalBillResponse response = null;
			try {
						response = fossToCubc.pushExternalBillStatus(requestExternalBillDto);
						 if(null == response){
						 throw new BusinessException("FOSS推送CUBC作废偏线外发单操作时ESB发生异常!");	
						 }
						 if(StringUtils.equals("0",response.getResult())){
						 throw new BusinessException(response.getReason());	
						 }
					} catch (Exception e) {
					throw new ExternalBillException("FOSS推送给CUBC作废偏线外发单状态,调用CUBC接口操作失败"+e);	
					}
		LOGGER.error("FOSS推送作废偏线外发单号" + requestExternalBillDto+ "给CUBC结束;返回值是 :" + response);
				}
			}
		}
		// 再次查询查询，防止并发情况,做校验
		auditList = externalBillDao.selectExternalBillByPrimaryKeys(auditIds);
		// 可否审核标记
		boolean flag = true;
		// 循环校验
		for (ExternalBillDto tempDto : auditList) {
			// 如果状态为作废或者已审核状态，则不能进行作废，则改变标记
			if (PartiallineConstants.PARTIALLINE_AUDITSTATUS_INVALID
					.equals(tempDto.getAuditStatus())
					|| PartiallineConstants.PARTIALLINE_AUDITSTATUS_HASAUDITED
							.equals(tempDto.getAuditStatus())) {
				flag = false;
				throw new ExternalBillException("存在不能作废的外发单,请刷新后,再操作", "");
			}
		}
		// 如果可以修改
		if (flag) {
			// 更新为“已作废”状态
			String auditStatus = PartiallineConstants.PARTIALLINE_AUDITSTATUS_INVALID;
			// 执行更新
			externalBillDao
					.updateAuditStatusByPrimaryKey(auditIds, auditStatus);
		}
		return PartiallineConstants.SERVICE_RESULT_INT_VALUE;
	}

	/**
	 * 根据运单号取最新的交接单明细信息
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-22 下午2:56:03
	 */
	private void queryLastHandoverBillDetial(ExternalBillDto tempDto,
			CurrentInfo currentInfo) {

		if (tempDto != null) {
			// 交接明细
			HandoverBillDetailDto detailDto = new HandoverBillDetailDto();
			// 运单号
			detailDto.setWaybillNo(tempDto.getWaybillNo());
			// 设置查询部门
			if (currentInfo != null) {
				LOGGER.info("查询筛选的当前部门名称:" + currentInfo.getCurrentDeptName()
						+ ",部门编码:" + currentInfo.getCurrentDeptCode());
				detailDto.setOrgCodes(queryTransCenterChildrenCodes(currentInfo
						.getCurrentDeptCode()));
			}
			LOGGER.info("根据运单号取最新的交接单明细信息,运单号:" + tempDto.getWaybillNo());
			// 查询未录入的交接明细
			HandoverBillDetailDto hdetail = uninputPartiallineDao
					.queryLastHandoverBillDetial(detailDto);
			// 获取交接单号
			if (hdetail != null
					&& StringUtils.isNotBlank(hdetail.getHandoverNo())) {
				tempDto.setHandoverNo(hdetail.getHandoverNo());
				LOGGER.info("根据运单号取最新的交接单明细信息,交接单号:" + hdetail.getHandoverNo());
			} else {
				LOGGER.error("运单号:" + tempDto.getWaybillNo()
						+ ",没有找到相关的交接单号,未作交接或已经录入,请核实再操作");
				throw new ExternalBillException("运单号:" + tempDto.getWaybillNo()
						+ ",没有找到相关的交接单号,未作交接或已经录入,请核实再操作", "");
			}
		}
	}

	/**
	 * 根据运单号查询外发单信息
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-12-12 下午3:39:54
	 * @see com.deppon.foss.module.transfer.partialline.api.server.service.IExternalBillService#selectExternalBillByWaybillNo(com.deppon.foss.module.transfer.partialline.api.shared.dto.ExternalBillDto)
	 */
	@Override
	public ExternalBillDto queryExternalBillByWaybillNo(ExternalBillDto tempDto) {
		// 验证运单号码
		if (tempDto == null) {
			throw new ExternalBillException("查询参数为空", "");
		} else {
			if (StringUtils.isBlank(tempDto.getWaybillNo())) {
				throw new ExternalBillException("运单号为空", "");
			}
		}
		return externalBillDao.queryExternalBillByWaybillNo(tempDto);
	}

	/**
	 * 运单号查询是否已存在有效的非中转外发的外发单(用于更改单的查询)
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-12-14 下午3:57:54
	 * @see com.deppon.foss.module.transfer.partialline.api.server.service.IExternalBillService#queryExternalBillByWaybillNoForChange(com.deppon.foss.module.transfer.partialline.api.shared.dto.ExternalBillDto)
	 */
	@Override
	public ExternalBillDto queryExternalBillByWaybillNoForChange(
			ExternalBillDto tempDto) {
		// 验证运单号码
		if (tempDto == null) {
			throw new ExternalBillException("查询参数为空", "");
		} else {
			if (StringUtils.isBlank(tempDto.getWaybillNo())) {
				throw new ExternalBillException("运单号为空", "");
			}
		}
		return externalBillDao.queryExternalBillByWaybillNoForChange(tempDto);
	}

	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	/**
	 * 导出偏线外发单列表
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2013-3-22 上午9:25:38
	 * 
	 * @see com.deppon.foss.module.transfer.partialline.api.server.service.IExternalBillService#exportExternalBill(java.util.List)
	 */
	@Override
	public InputStream exportExternalBill(
			List<ExternalBillDto> externalBillList, ExternalBillVo vo) {
		// 导出文件流
		InputStream excelStream = null;
		// 定义表头
		String[] rowHeads = PartiallineConstants.ROW_HEADS;

		SheetData sheetData = new SheetData();
		// 头数据
		sheetData.setRowHeads(rowHeads);
		// 查询出的所有数据
		List<List<String>> rowList = new ArrayList<List<String>>();
		// 循环转换
		List<String> columnList = null;
		// 判空
		if (CollectionUtils.isNotEmpty(externalBillList)) {
			// 循环转换
			for (ExternalBillDto dto : externalBillList) {
				columnList = new ArrayList<String>();
				// 运单号
				columnList.add(dto.getWaybillNo());
				// 外发单号
				columnList.add(dto.getExternalBillNo());
				// 中转外发
				if (PartiallineConstants.IS_TRANSFER_EXTERNAL_NO.equals(dto
						.getTransferExternal())) {
					columnList
							.add(PartiallineConstants.IS_TRANSFER_EXTERNAL_NO_DESC);
				} else {
					columnList
							.add(PartiallineConstants.IS_TRANSFER_EXTERNAL_YES_DESC);
				}
				// 外发代理
				columnList.add(dto.getAgentCompanyName());
				// 到付运费
				columnList.add(dto.getToPayAmount());

				// 外发代理费
				BigDecimal externalAgencyFee = dto.getExternalAgencyFee();
				if (externalAgencyFee == null) {
					externalAgencyFee = BigDecimal.ZERO;
				}
				columnList.add(externalAgencyFee.toString());

				// 代理送货费
				BigDecimal deliveryFee = dto.getDeliveryFee();
				if (deliveryFee == null) {
					deliveryFee = BigDecimal.ZERO;
				}
				columnList.add(deliveryFee.toString());
				// 外发成本总额
				columnList.add(dto.getCostAmount().toString());
				// 状态
				// 待审核
				if (PartiallineConstants.PARTIALLINE_AUDITSTATUS_WAITINGAUDIT
						.equals(dto.getAuditStatus())) {
					// 待审核
					columnList
							.add(PartiallineConstants.PARTIALLINE_AUDITSTATUS_WAITINGAUDIT_DESC);
				}
				// 已审核
				else if (PartiallineConstants.PARTIALLINE_AUDITSTATUS_HASAUDITED
						.equals(dto.getAuditStatus())) {
					// 已审核
					columnList
							.add(PartiallineConstants.PARTIALLINE_AUDITSTATUS_HASAUDITED_DESC);
				}
				// 反审核
				else if (PartiallineConstants.PARTIALLINE_AUDITSTATUS_BACKAUDIT
						.equals(dto.getAuditStatus())) {
					// 反审核
					columnList
							.add(PartiallineConstants.PARTIALLINE_AUDITSTATUS_BACKAUDIT_DESC);
				}
				// 已作废
				else if (PartiallineConstants.PARTIALLINE_AUDITSTATUS_INVALID
						.equals(dto.getAuditStatus())) {
					// 已作废
					columnList
							.add(PartiallineConstants.PARTIALLINE_AUDITSTATUS_INVALID_DESC);
				} else {
					columnList.add(StringUtils.EMPTY);
				}
				// 外发员
				columnList.add(dto.getExternalUserName());
				// 录入时间
				columnList.add(DateUtils.convert(dto.getRegisterTime(),
						DateUtils.DATE_TIME_FORMAT));
				// 币种
				columnList.add(dto.getCurrencyCode());
				// 其他费用 chigo
				columnList.add(dto.getOtherFee());
				// 备注 chigo
				columnList.add(dto.getNotes());
				// 加入列表
				rowList.add(columnList);
			}
		}
		sheetData.setRowList(rowList);
		// 导出工具
		ExcelExport excelExportUtil = new ExcelExport();
		// 导出成文件
		excelStream = excelExportUtil.inputToClient(excelExportUtil
				.exportExcel(sheetData,
						PartiallineConstants.EXCEPTION_WAYBILL_SHEET_NAME,
						PartiallineConstants.EXCEL_DEFAULT_SHEET_SIZE));
		// 返回
		return excelStream;
	}

	/**
	 * 提供给结算的接口，校验是否存在外发单
	 * 
	 * @author foss-liuxue(for IBM)
	 * @date 2013-7-9 上午11:27:24
	 */
	@Override
	public boolean validateIsExistExternalBill(String waybillNo) {
		boolean result = false;
		// 校验运单号是否为空
		if (StringUtils.isBlank(waybillNo)) {
			LOGGER.error("运单号为空");
			throw new TfrBusinessException("运单号不能为空！", "");
		}
		// 根据运单号查询状态不为作废的全部条数
		List<ExternalBillDto> externalBillDtos = externalBillDao
				.validateIsExistExternalBill(waybillNo);
		// 如果count为0，则说明当前运单号不存在外发单，result设为true
		if (externalBillDtos.size() == 0) {
			result = true;
		} else {
			for (ExternalBillDto dto : externalBillDtos) {
				// 如果存在非作废且非中转外发，则不允许录入, 抛出异常
				if (PartiallineConstants.IS_TRANSFER_EXTERNAL_NO.equals(dto
						.getTransferExternal())) {
					result = false;
					break;
				} else {
					result = true;
				}
			}
		}
		if (!result) {
			throw new TfrBusinessException("存在非作废状态且不为中转外发的外发单，不允许发更改！", "");
		}
		// 返回result
		return result;
	}

	/**
	 * @Desc 根据运单号查询有效的(Not "INVALID")外发单号列表
	 * @Author 370210
	 * @Created 2016-12-14 下午1:51:59
	 */
	public List<String> getExternalBillNumListByWaybillNo(String waybillNo) {
		List<String> result = new ArrayList<String>();
		try {
			if (StringUtils.isNotEmpty(waybillNo)) {
				// 根据运单号获取所有的外发单
				List<ExternalBillDto> externalBillDtos = externalBillDao
						.validateIsExistExternalBill(waybillNo);
				if (CollectionUtils.isNotEmpty(externalBillDtos)) {
					// 抽取外发单号
					for (ExternalBillDto externalBillDto : externalBillDtos) {
						result.add(externalBillDto.getExternalBillNo());
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("根据运单号查询有效的外发单号列表错误:"+e.getMessage());
			throw new RuntimeException(e);
		}
		return result;
	}
	

	/**
	 * @author niuly
	 * @date 2014-3-3下午3:59:14
	 * @function 根据运单号list查询有效的外发单信息
	 * @param waybillNoList
	 * @return List<ExternalBillDto>
	 */
	public List<ExternalBillDto> queryExternalBillByWaybillNos(
			List<String> waybillNoList) {
		List<ExternalBillDto> list = new ArrayList<ExternalBillDto>();
		if (waybillNoList != null && waybillNoList.size() > 0) {
			list = externalBillDao.queryExternalBillByWaybillNos(waybillNoList);
		}
		return list;
	}

	/**
	 * @author foss-中转-wqh
	 * @date 2014-07-29
	 * @function 计算外发代理费和送货费
	 * @param ExternalBillWayBillInfoDto
	 * @return void
	 */

	private void calculateDlyFeeAndEtdAgFee(
			ExternalBillWayBillInfoDto externalBillWayBillInfoDto) {
		LOGGER.error("开始计算外发代理费和送货费");
		// 参数检验
		if (externalBillWayBillInfoDto == null) {
			LOGGER.error("参数为空");
			throw new ExternalBillException("开始计算外发代理费和送货费");
		}
		// 获取当前信息
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();

		// 外发外场
		String outOrgCode;
		OrgAdministrativeInfoEntity transCenter = querySuperiorOrgByOrgCode(currentInfo
				.getCurrentDeptCode());
		if (transCenter != null
				&& StringUtils.isNotBlank(transCenter.getCode())) {
			outOrgCode = transCenter.getCode();
		} else {
			LOGGER.error("当前外场不存在");
			throw new ExternalBillException("当前外场不存在");
		}

		// 查询运单基本信息，拿到运输类型
		WaybillEntity waybillEntity = waybillManagerService
				.queryWaybillBasicByNo(externalBillWayBillInfoDto
						.getWaybillNo());
		if (waybillEntity == null) {
			LOGGER.error("运单：" + externalBillWayBillInfoDto.getWaybillNo()
					+ " 不存在！");
			throw new ExternalBillException("运单："
					+ externalBillWayBillInfoDto.getWaybillNo() + " 不存在！");
		}
		if (waybillEntity.getTransportType() == null
				|| "".equals(waybillEntity.getTransportType())) {
			LOGGER.error("运单：" + waybillEntity.getWaybillNo() + " 运输类型不存在！");
			throw new ExternalBillException("运单："
					+ waybillEntity.getWaybillNo() + " 运输类型不存在！");
		}

		externalBillWayBillInfoDto.setGoodsWeightTotal(waybillEntity
				.getGoodsWeightTotal());
		externalBillWayBillInfoDto.setGoodsVolumeTotal(waybillEntity
				.getGoodsVolumeTotal());

		// 目的站去运单中的客户提货网点编码
		String targetOrgCode = waybillEntity.getCustomerPickupOrgCode();

		String transportType = waybillEntity.getTransportType();
		// 当前时间，备注 ：由于外发单还没有生成，这里取当前时间作为参数
		Date externalBillCreateTime = new Date();

		ExternalPriceSchemeEntity externalPriceSchemeEntity = null;
		// 调接送接口，获取偏线外发代理方案
		try {

			externalPriceSchemeEntity = externalPriceSchemeService4Dubbo
					.queryAgentOutPriceInfo(targetOrgCode, outOrgCode,
							transportType, externalBillCreateTime);
		} catch (ExternalPriceSchemeException e) {
			LOGGER.error("调接送货接口获取偏线外发价格方案异常：{" + e.getErrorCode() + "}");
			throw new ExternalBillException("调接送货接口获取偏线外发价格方案异常：{"
					+ e.getErrorCode() + "}");

		}
		if (externalPriceSchemeEntity == null) {
			LOGGER.error(transCenter.getName() + " ————> "
					+ waybillEntity.getCustomerPickupOrgName()
					+ "  不存在该偏线外发代理方案");
			throw new ExternalBillException(transCenter.getName() + " ————>  "
					+ waybillEntity.getCustomerPickupOrgName()
					+ "  不存在该偏线外发代理方案");
		}

		// 外发单重量
		BigDecimal goodsWeightTotal = externalBillWayBillInfoDto
				.getGoodsWeightTotal();
		// 外发单体积
		BigDecimal goodsVolumeTotal = externalBillWayBillInfoDto
				.getGoodsVolumeTotal();

		/**
		 * 计算外发代理费 1
		 * 读取偏线外发价格方案中对应目的站的重货费率与货物重量的乘积或者轻货费率与货物总体积的乘积，如果任意不为空，二者取大，为空，取不为空的值，
		 * 然后四舍五入保留小数点后2位; 2 取大后若费用小于最低一票，则外发代理费取最低一票的值;
		 * 
		 * **/
		BigDecimal etdAgFee = BigDecimal.ZERO;

		if (externalPriceSchemeEntity.getHeavyPrice() == null
				&& externalPriceSchemeEntity.getLightPrice() == null) {
			LOGGER.error(transCenter.getName() + "  ————>   "
					+ waybillEntity.getCustomerPickupOrgName()
					+ "   不存在轻货费率或者重货费率");
			throw new ExternalBillException(transCenter.getName()
					+ "  ————>   " + waybillEntity.getCustomerPickupOrgName()
					+ "   不存在轻货费率或者重货费率");
		}

		if (externalPriceSchemeEntity.getHeavyPrice() == null
				&& externalPriceSchemeEntity.getLightPrice() != null) {
			/*
			 * LOGGER.error(externalPriceSchemeEntity.getAgentDeptName()+
			 * "：对应的重货费率不存在"); throw new
			 * TfrBusinessException(externalPriceSchemeEntity
			 * .getAgentDeptName()+"：对应的重货费率不存在");
			 */
			etdAgFee = externalPriceSchemeEntity.getLightPrice().multiply(
					goodsVolumeTotal);
		}
		if (externalPriceSchemeEntity.getLightPrice() == null
				&& externalPriceSchemeEntity.getHeavyPrice() != null) {
			/*
			 * LOGGER.error(externalPriceSchemeEntity.getAgentDeptName()+
			 * "：对应的轻货费率不存在"); throw new
			 * TfrBusinessException(externalPriceSchemeEntity
			 * .getAgentDeptName()+"：对应的轻货费率不存在");
			 */
			etdAgFee = externalPriceSchemeEntity.getHeavyPrice().multiply(
					goodsWeightTotal);
		}

		if (externalPriceSchemeEntity.getHeavyPrice() != null
				&& externalPriceSchemeEntity.getLightPrice() != null) {
			// 重货价格
			BigDecimal heavyPrice = externalPriceSchemeEntity.getHeavyPrice();

			// 轻货价格
			BigDecimal lightPrice = externalPriceSchemeEntity.getLightPrice();

			BigDecimal result1 = heavyPrice.multiply(goodsWeightTotal);
			BigDecimal result2 = lightPrice.multiply(goodsVolumeTotal);
			// 比较两者大小，取大者
			etdAgFee = result2;
			if (result1.compareTo(result2) > 0) {
				etdAgFee = result1;
			}

		}
		// DEFECT-4144 外发代理费未取最低一票。
		if (externalPriceSchemeEntity.getLowestPrice() == null) {
			LOGGER.error("最低价格不存在");
			throw new ExternalBillException("最低价格不存在");
		}
		// 最低费用
		BigDecimal lowestPrice = externalPriceSchemeEntity.getLowestPrice();
		// 再和最低费用比较
		etdAgFee = etdAgFee.compareTo(lowestPrice) > 0 ? etdAgFee : lowestPrice;
		// 外发代理费保留两位小数
		etdAgFee = etdAgFee.setScale(2, BigDecimal.ROUND_HALF_UP);
		externalBillWayBillInfoDto.setExternalAgencyFee(etdAgFee);

		BigDecimal agtDelFee = BigDecimal.ZERO;

		// 提货方式
		List<String> begindList = new ArrayList<String>();
		// 汽运自提
		begindList.add(WaybillConstants.SELF_PICKUP);
		// 汽运内部自提
		begindList.add(WaybillConstants.INNER_PICKUP);
		// 获取当前运单提货方式
		String receiveMethod = waybillEntity.getReceiveMethod();
		// 若该运单提货方式为自提类（自提或者内部带货自提），则代理送货费文本框显示为0
		if (!begindList.contains(receiveMethod)) {
			/**
			 * 
			 * 若该运单提货方式非自提类，则代理送货费读取偏线代理送货费方案中对应目的站的送货费信息，
			 * 根据运单的货物重量与总体积读取相应重量或体积范围内的收费标准，
			 * 收费标准与重量（或体积）的乘积即为送货费，如果任意不为空，二者取大，为空，取不为空的值，然后四舍五入保留小数点后2位；
			 * 取大后若费用小于最低一票，则送货费读取最低一票
			 * 
			 * **/
			// 调接送接口，获取偏线代理送货费方案
			AgentDeliveryFeeSchemeEntity agentDeliveryFeeSchemeEntity = null;

			try {

				agentDeliveryFeeSchemeEntity = agentDeliveryFeeSchemeService4Dubbo
						.queryAgentDeliveryCharge(transportType, targetOrgCode,
								goodsWeightTotal, goodsVolumeTotal);
			} catch (AgentDeliveryFeeSchemeException e) {
				LOGGER.error("调接送货接口获取偏线外发代理送货费方案异常：{" + e.getErrorCode() + "}");
				throw new ExternalBillException("调接送货接口获取偏线外发代理送货费方案异常：{"
						+ e.getErrorCode() + "}");
			}

			if (agentDeliveryFeeSchemeEntity == null
					|| agentDeliveryFeeSchemeEntity.getFeeEntityList() == null
					|| agentDeliveryFeeSchemeEntity.getFeeEntityList().size() == 0) {
				LOGGER.error(waybillEntity.getCustomerPickupOrgName()
						+ "  不存在外发送货费方案");
				throw new ExternalBillException(
						waybillEntity.getCustomerPickupOrgName()
								+ "  不存在外发送货费方案");
			}

			List<AgentDeliveryFeeEntity> feeEntityList = agentDeliveryFeeSchemeEntity
					.getFeeEntityList();
			/**
			 * 如果方案明细大于两条记录，则说明该外发送货费方案有误， 1 通常情况是 返回两条记录，一条 重量明细，一条是体积明细 2 特殊情况
			 * 只有重量或者体积，如果出现其他情况则该方案有误
			 * 
			 * **/
			if (feeEntityList.size() > 2) {
				LOGGER.error("调接送货接口获得外发送货费异常： "
						+ waybillEntity.getCustomerPickupOrgName()
						+ "  存在大于2条的外发送货费方案明细");
				throw new ExternalBillException("调接送货接口获得外发送货费异常： "
						+ waybillEntity.getCustomerPickupOrgName()
						+ "  存在大于2条的外发送货费方案明细");
			}

			// 获取送货费中的重量明细
			AgentDeliveryFeeEntity weightDetail = null;

			// 获取送货费中的体积明细
			AgentDeliveryFeeEntity volumnDetail = null;

			for (int i = 0; i < feeEntityList.size(); i++) {
				AgentDeliveryFeeEntity deliveryFeeEntity = feeEntityList.get(i);
				// 计费方式
				String pricinManner = deliveryFeeEntity.getPricingManner();
				// 重量
				if (PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT
						.equals(pricinManner)) {
					weightDetail = deliveryFeeEntity;
					continue;
				}

				// 体积
				if (PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_VOLUME
						.equals(pricinManner)) {
					volumnDetail = deliveryFeeEntity;
					continue;
				}

			}
			// 重量明细不为空，体积明细为空，送货费用取重量明细结果
			if (weightDetail != null && volumnDetail == null) {
				// 重量收费标准
				BigDecimal weightChargeStandard = weightDetail
						.getChargeStandard();
				// 重量最低一票费用
				BigDecimal weightLowestPrice = new BigDecimal(
						weightDetail.getLowestPrice());

				// 如果 重量x重量收费标准>重量最低一票费用，取如果重量x重量收费标准，否则取重量最低一票费用
				if (goodsWeightTotal.multiply(weightChargeStandard).compareTo(
						weightLowestPrice) > 0) {
					agtDelFee = goodsWeightTotal.multiply(weightChargeStandard);
				} else {
					agtDelFee = weightLowestPrice;
				}

			}
			// 重量明细为空，体积明细不为空，送货费用取体积明细结果
			else if (weightDetail == null && volumnDetail != null) {

				// 体积收费标准
				BigDecimal volChargeStandard = volumnDetail.getChargeStandard();
				// 体积最低一票费用
				BigDecimal volLowestPrice = new BigDecimal(
						volumnDetail.getLowestPrice());
				// 如果 体积x收费标准>最低一票费用，取 体积x收费标准 否则取 体积最低一票费用
				if (goodsVolumeTotal.multiply(volChargeStandard).compareTo(
						volLowestPrice) > 0) {
					agtDelFee = goodsVolumeTotal.multiply(volChargeStandard);
				} else {
					agtDelFee = volLowestPrice;
				}

			}
			//如果两则都不为空  二者取大
			//sonar-352203-存在两个都为空，
			else if(weightDetail!=null&& volumnDetail!=null){
				
				//重量收费标准
				BigDecimal weightChargeStandard=weightDetail.getChargeStandard();
				//重量最低一票费用
				BigDecimal weightLowestPrice=new BigDecimal(weightDetail.getLowestPrice());
				
				BigDecimal weiResult=goodsWeightTotal.multiply(weightChargeStandard).compareTo(weightLowestPrice)>0
						             ?goodsWeightTotal.multiply(weightChargeStandard):weightLowestPrice;
				//体积收费标准
				BigDecimal volChargeStandard=volumnDetail.getChargeStandard();
				//体积最低一票费用
				BigDecimal volLowestPrice=new BigDecimal(volumnDetail.getLowestPrice());
				
				
				//第一次pk，取大者
				BigDecimal volResult=goodsVolumeTotal.multiply(volChargeStandard).compareTo(volLowestPrice)>0
						             ?goodsVolumeTotal.multiply(volChargeStandard):volLowestPrice;
				//第二次PK，取大者
				 agtDelFee=weiResult.compareTo(volResult)>0?weiResult:volResult;
						             
						             
			}

		}
		// 然后四舍五入保留小数点后2位
		agtDelFee = agtDelFee.setScale(2, BigDecimal.ROUND_HALF_UP);

		externalBillWayBillInfoDto.setDeliveryFee(agtDelFee);
	}

	/**
	 * @author wqh
	 * @date 2014-07-31下午4:55:15
	 * @function 根据运单号计算外发费用和外发送货费用
	 * @param waybillNo
	 * @return
	 */
	@Override
	public ExternalBillWayBillInfoDto calculateDeliverFeeAndEtdFee(
			String waybillNo) {

		if (waybillNo == null || StringUtil.isBlank(waybillNo)) {
			throw new ExternalBillException("请输入运单号");
		}
		ExternalBillWayBillInfoDto externalBillWayBillInfoDto = new ExternalBillWayBillInfoDto();
		externalBillWayBillInfoDto.setWaybillNo(waybillNo);
		// 计算外发费用和外发送货费用
		this.calculateDlyFeeAndEtdAgFee(externalBillWayBillInfoDto);

		return externalBillWayBillInfoDto;
	}

	/**
	 * @author zwd 2009687
	 * @date 2015-04-24
	 * @function 根据运单号查询偏线外发信息
	 * @param dto
	 * @return
	 */
	@Override
	public List<ExternalBillDto> selectExternalByWayBillNo(ExternalBillDto dto) {

		return externalBillDao.selectExternalByWayBillNo(dto);
	}

}