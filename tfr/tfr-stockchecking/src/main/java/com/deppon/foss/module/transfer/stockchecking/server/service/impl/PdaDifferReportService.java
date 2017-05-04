/**
 * 
 */
package com.deppon.foss.module.transfer.stockchecking.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IUserOrgRoleService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserOrgRoleEntity;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.transfer.common.api.shared.define.TransferConstants;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.pda.api.server.service.IPdaDifferReportService;
import com.deppon.foss.module.transfer.pda.api.shared.domain.PdaDifferDetailEntity;
import com.deppon.foss.module.transfer.pda.api.shared.domain.PdaDifferEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;
import com.deppon.foss.module.transfer.stockchecking.api.server.dao.IPdaDifferReportDao;
import com.deppon.foss.module.transfer.stockchecking.api.server.service.IStReportService;
import com.deppon.foss.module.transfer.stockchecking.api.shared.domain.PdaDifferDetailLogEntity;
import com.deppon.foss.module.transfer.stockchecking.api.shared.domain.PdaDifferLogEntity;
import com.deppon.foss.module.transfer.stockchecking.api.shared.domain.PdaDifferOperatorEntity;
import com.deppon.foss.module.transfer.stockchecking.api.shared.domain.StDifferDetailEntity;
import com.deppon.foss.module.transfer.stockchecking.api.shared.domain.StDifferReportEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * @author niuly
 * @date 2014-05-21
 * @function PDA端处理清仓差异报告Service
 */
public class PdaDifferReportService implements IPdaDifferReportService {
	private static final Logger LOGGER = LoggerFactory.getLogger(PdaDifferReportService.class);
	
	private IConfigurationParamsService configurationParamsService;
	private IPdaDifferReportDao pdaDifferReportDao;
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	private ISaleDepartmentService saleDepartmentService;
	private IStReportService stReportService;
	private IEmployeeService employeeService;
	private IUserOrgRoleService userOrgRoleService;
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	/**
	 * @author niuly
	 * @date 2014-5-21上午9:15:00
	 * @function 查询清仓差异报告
	 * @param deptCode
	 * @param operatorCode
	 * @param pdaNo
	 * @return List
	 */
	@Override
	public List<PdaDifferEntity> queryDifferReportList(String deptCode, String operatorCode, String pdaNo) {
		List<PdaDifferEntity> reportList = new ArrayList<PdaDifferEntity>();
		List<String> deptCodes = this.getHandlerDeptCodes(deptCode, operatorCode, pdaNo);
		//默认48小时
		int hours = ConstantsNumberSonar.SONAR_NUMBER_48;
		try {
			//获取配置参数，确定查询多长范围内的清仓差异报告
			ConfigurationParamsEntity defaultBizHourSlice = configurationParamsService.queryConfigurationParamsByOrgCode(DictionaryConstants.SYSTEM_CONFIG_PARM__TFR , ConfigurationParamsConstants.TFR_PARM_PDA_ST_DIFFER_TIME, deptCode);
			hours = Integer.valueOf(defaultBizHourSlice.getConfValue()).intValue();
		}catch(Exception e) {
			throw new TfrBusinessException("获取配置参数失败");
		}
		try{
			reportList = pdaDifferReportDao.queryDifferReportList(deptCodes,hours);
		}catch(Exception e) {
			throw new TfrBusinessException("获取差异报告失败！");
		}
		return reportList;
	}


	/**
	 * @author niuly
	 * @date 2014-5-24下午4:46:49
	 * @function 根据差异报告编号查询对应的差异明细
	 * @param reportCode
	 * @param operatorCode
	 * @param pdaNo
	 * @param handInputFlg 用于标记是否通过输入差异报告编号查询
	 * @return
	 */
	@Override
	public List<PdaDifferDetailEntity> queryDifferDetailByReportNo(String reportCode,String deptCode,String operatorCode,String pdaNo,String handInputFlg) {
		if(StringUtils.equals("Y", handInputFlg)) {
			//判断此人是否有处理差异的权限
			List<String> deptCodes = this.getHandlerDeptCodes(deptCode, operatorCode, pdaNo);
			//确定是否存在此差错
			StDifferReportEntity reportEntity = pdaDifferReportDao.queryDifferByCode(reportCode);
			if(null == reportEntity) {
				throw new TfrBusinessException("该差异报告不存在！");
			}
			if(!deptCodes.contains(reportEntity.getDeptcode())) {
				throw new TfrBusinessException("无权处理其他部门差异报告！");
			}	
		}
		
		List<PdaDifferDetailEntity> detailList = new ArrayList<PdaDifferDetailEntity>();
		try{
			//查询差异报告中所有的快递货差异明细
			detailList = pdaDifferReportDao.queryDifferDetailByReportNo(reportCode);
		} catch(Exception e) {
			throw new TfrBusinessException("查询差异报告明细失败！");
		}
		try{
			//插入PDA分支日志
			this.addBranchInfo(reportCode, operatorCode, pdaNo, TransferConstants.STOCK_CHECKING_REPORT_DOING);
			//插入PDA处理差异参与人日志
			this.addOperatorInfo(reportCode,operatorCode);
		}catch(Exception e) {
			LOGGER.error("根据差异报告编号查询对应的差异明细保存日志时失败",e);
		}
		return detailList;
	}
	
	/**
	 * @author niuly
	 * @date 2014-5-24下午4:51:05
	 * @function 异步处理清仓差异明细。注：保留最早的处理时间；PDA端提交任务后，依然接收处理消息
	 * @param reportCode
	 * @param waybillNo
	 * @param serialNo
	 * @param ScanTime
	 * @param operatorCode
	 * @param pdaNo
	 * @return
	 */
	@Override
	public boolean handleDifferDetail(String reportCode,String waybillNo,String serialNo,Date scanTime,String operatorCode,String pdaNo) {
		
		boolean result = false;
		//差异明细
		StDifferDetailEntity stDifferDetail = pdaDifferReportDao.queryStDifferDetail(reportCode,waybillNo,serialNo);
		if(null == stDifferDetail) {
			LOGGER.error("未找到相应的差异明细，差异报告编号：" + reportCode + ",运单号：" + waybillNo + ",流水号：" + serialNo);
			throw new TfrBusinessException("未找到相应的差异明细！差异报告编号：" + reportCode + ",运单号：" + waybillNo + ",流水号：" + serialNo);
		}
		
		try{
			//状态为未处理时才作处理
			if(StringUtils.equals(TransferConstants.STOCK_CHECKING_REPORT_DOING, stDifferDetail.getHandleStatus())) {
				EmployeeEntity entity = employeeService.queryEmployeeByEmpCode(operatorCode);
				String userName = "";
				if(entity != null) {
					userName = entity.getEmpName();
				}
				stDifferDetail.setIsInStock("Y");
				stDifferDetail.setRemark("PDA端扫描处理！");
				//防止并发
				synchronized(this){
					stReportService.updateReportDetail(stDifferDetail, userName, operatorCode);
				}
			}
			
		}catch(Exception e) {
			LOGGER.error("PDA处理清仓差异明细失败！差异报告编号：" + reportCode + ",运单号：" + waybillNo + ",流水号：" + serialNo);
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			throw new TfrBusinessException("PDA处理清仓差异明细失败！差异报告编号：" + reportCode + ",运单号：" + waybillNo + ",流水号：" + serialNo);
		}
		
		//记录PDA处理日志
		try{
			PdaDifferDetailLogEntity detailEntity = new PdaDifferDetailLogEntity();
			detailEntity.setId(UUIDUtils.getUUID());
			detailEntity.setReportCode(reportCode);
			detailEntity.setWaybillNo(waybillNo);
			detailEntity.setSerialNo(serialNo);
			detailEntity.setScanTime(scanTime);
			detailEntity.setCreateTime(new Date());
			detailEntity.setOperatorCode(operatorCode);
			detailEntity.setPdaNo(pdaNo);
			pdaDifferReportDao.addScanInfo(detailEntity);
		} catch(Exception e) {
			LOGGER.error("PDA处理清仓差异明细时，日志记录失败！差异报告编号：" + reportCode + ",运单号：" + waybillNo + ",流水号：" + serialNo);
		}
		result = true;
		return result;
	}
	
	/**
	 * @author niuly
	 * @date 2014-5-24下午4:53:08
	 * @function PDA提交差异报告处理任务
	 * @param deptCode
	 * @param operatorCode
	 * @param pdaNo
	 * @return
	 */
	@Override
	public boolean commitHandleTask(String reportCode,String operatorCode,String pdaNo) {
		//更新差异报告PDA标志位，若未更新才更新，已更新则不做操作
		boolean hasDone = false;
		hasDone = this.isDoneByPDA(reportCode);
		if(!hasDone) {
			try{
				pdaDifferReportDao.updatePdaHandleStatus(reportCode);
			} catch(Exception e) {
				LOGGER.error("提交失败！差异报告编号：" + reportCode+"操作人工号" + operatorCode + "PDA编号"+pdaNo,e);
				throw new TfrBusinessException("提交失败，请重试！");
			}
		}
		try{
			//插入处理日志
			this.addBranchInfo(reportCode, operatorCode, pdaNo, TransferConstants.STOCK_CHECKING_REPORT_DONE);
		}catch(Exception e) {
			LOGGER.error("PDA提交清仓差异报告处理任务，保存处理日志失败！差异报告编号：" + reportCode+"操作人工号" + operatorCode + "PDA编号"+pdaNo);
		}
		
		return true;
	}

	/**
	 * @author niuly
	 * @date 2014-5-29下午5:03:16
	 * @function 插入PDA分支处理日志
	 * @param reportCode
	 * @param operatorCode
	 * @param pdaNo
	 * @param status
	 */
	public void addBranchInfo(String reportCode,String operatorCode,String pdaNo,String status) {
		PdaDifferLogEntity differEntity = new PdaDifferLogEntity();
		differEntity.setId(UUIDUtils.getUUID());
		differEntity.setPdaNo(pdaNo);
		differEntity.setReportCode(reportCode);
		differEntity.setOperatorCode(operatorCode);
		differEntity.setStatus(status);
		differEntity.setCreateTime(new Date());
		pdaDifferReportDao.addBranchInfo(differEntity);
	}
	
	/**
	 * @author niuly
	 * @date 2014-5-29下午6:14:35
	 * @function 判断PDA处理状态
	 * @param reportCode
	 * @return
	 */
	public boolean isDoneByPDA(String reportCode) {
		boolean isDone = false;
		StDifferReportEntity reportEntity = pdaDifferReportDao.queryDifferByCode(reportCode);
		if(null != reportEntity && StringUtils.equals(TransferConstants.STOCK_CHECKING_REPORT_DONE, reportEntity.getPdaHandleStatus())) {
			isDone = true;
		}
		return isDone;
	}
	/**
	 * @author niuly
	 * @date 2014-6-1上午10:19:33
	 * @function PDA处理差异参与人
	 * @param reportCode
	 * @param operatorCode
	 */
	public void addOperatorInfo(String reportCode,String operatorCode) {
		//若已经存在，则不再保存
		PdaDifferOperatorEntity entity = pdaDifferReportDao.queryDifferOperator(reportCode,operatorCode);
		if(null != entity) {
			return;
		}
		PdaDifferOperatorEntity operatorEntity = new PdaDifferOperatorEntity();
		operatorEntity.setId(UUIDUtils.getUUID());
		operatorEntity.setReportCode(reportCode);
		operatorEntity.setOperatorCode(operatorCode);
		EmployeeEntity empEntity = employeeService.queryEmployeeByEmpCode(operatorCode);
		String userName = "";
		if(empEntity != null) {
			userName = empEntity.getEmpName();
		}
		operatorEntity.setOperatorName(userName);
		
		pdaDifferReportDao.addOperatorInfo(operatorEntity);
	}
	
	/**
	 * @author niuly
	 * @date 2014-6-5上午10:36:25
	 * @function 判断是否为外场经理。外场经理角色对应3个：外场经理(TFR_TRANSFER_MANAGER)/外场经理（枢纽）(FOSS_WCJLSN)/快递外场经理(TKD_FR_TRANSFER_MANAGER)
	 * @return
	 */
	private boolean isTransferManager(String unifiedCode, String empCode) {
		UserOrgRoleEntity userOrgRole = new UserOrgRoleEntity();
		userOrgRole.setOrgUnifiedCode(unifiedCode);
		userOrgRole.setEmpCode(empCode);
		userOrgRole.setRoleCode("TFR_TRANSFER_MANAGER");
		userOrgRole.setActive("Y");
		List<UserOrgRoleEntity> roleList = userOrgRoleService.queryUserOrgRoleListBySelective(userOrgRole);
		//外场经理
		if(null != roleList && roleList.size() > 0) {
			return true;
		}
		
		userOrgRole.setRoleCode("FOSS_WCJLSN");
		List<UserOrgRoleEntity> roleList1 = userOrgRoleService.queryUserOrgRoleListBySelective(userOrgRole);
		//外场经理(枢纽)
		if(null != roleList1 && roleList1.size() > 0) {
			return true;
		}
		
		userOrgRole.setRoleCode("TKD_FR_TRANSFER_MANAGER");
		List<UserOrgRoleEntity> roleList2 = userOrgRoleService.queryUserOrgRoleListBySelective(userOrgRole);
		//快递外场经理
		if(null != roleList2 && roleList2.size() > 0) {
			return true;
		}
		
		return false;
	}
	
	
	/**
	 * @author niuly
	 * @date 2014-6-5下午1:47:28
	 * @function 根据外场编码，获取该外场对应的所有驻地部门
	 * @param deptCode
	 * @return
	 */
	private List<String> getStationDeptCodes(String deptCode) {
		List<String> stationDeptCodes = new ArrayList<String>();
		List<SaleDepartmentEntity> stationList = saleDepartmentService.queryStationListByTransferCenterCode(deptCode);
		if(CollectionUtils.isNotEmpty(stationList)) {
			for(SaleDepartmentEntity entity:stationList) {
				if(StringUtils.isNotEmpty(entity.getCode())) {
					stationDeptCodes.add(entity.getCode());
				}
			}
		}
		return stationDeptCodes;
	}

	/**
	 * @author niuly
	 * @date 2014-6-12下午3:07:40
	 * @function 判断操作人是否可以处理差异报告，并获取可以处理的差异部门
	 * @param deptCode
	 * @param operatorCode
	 * @return
	 */
	private List<String> getHandlerDeptCodes(String deptCode,String operatorCode,String pdaNo){
		//部门
		List<String> deptCodes = new ArrayList<String>();
		
		//登录人部门
		OrgAdministrativeInfoEntity operaterOrgEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(deptCode);
		if(null == operaterOrgEntity) {
			LOGGER.error("PDA查询清仓差异报告时发生异常，获取部门信息失败!pdaNo："+ pdaNo + ",operatorCode:" + operatorCode + ",deptCode:" + deptCode);
			throw new TfrBusinessException("获取部门信息失败！");
		}
		//获取登录人部门对应的外场、营业部、空运总调
		OrgAdministrativeInfoEntity orgEntity = this.getBigOrg(operaterOrgEntity);
		if(null == orgEntity) {
			LOGGER.error("PDA查询清仓差异报告时发生异常，获取上级部门失败!pdaNo："+ pdaNo + ",operatorCode:" + operatorCode + ",deptCode:" + deptCode);
			throw new TfrBusinessException("获取上级组织失败！");
		}
		
		deptCodes.add(orgEntity.getCode());
		
		//如果为外场，先判断登录人是否为外场负责人，若是，则查询外场的差异报告
		if(StringUtils.equals(FossConstants.YES, orgEntity.getTransferCenter())) {
			//判断操作人是否为外场经理
			EmployeeEntity employeeEntity = employeeService.queryEmployeeByEmpCode(operatorCode);
			if(null == employeeEntity) {
				LOGGER.error("PDA查询清仓差异报告时发生异常，获取人员信息失败!pdaNo："+ pdaNo + ",operatorCode:" + operatorCode + ",deptCode:" + deptCode);
				throw new TfrBusinessException("获取人员信息失败！");
			}
			
			boolean isManager = this.isTransferManager(operaterOrgEntity.getUnifiedCode(),operatorCode);
			//外场经理
			if(isManager) {
				//查询外场下对应的所有驻地部门
				List<String> tempDeptCodes = this.getStationDeptCodes(orgEntity.getCode());
				if(CollectionUtils.isNotEmpty(tempDeptCodes)) {
					for(String code : tempDeptCodes) {
						deptCodes.add(code);
					}
				}
				
			} else {
				LOGGER.error("PDA查询清仓差异报告时发生异常，登录人角色不是外场经理!pdaNo："+ pdaNo + ",operatorCode:" + operatorCode + ",deptCode:" + deptCode);
				throw new TfrBusinessException("该人员角色不是外场经理，无权处理清仓差异报告！");
			}
			
		} else if(StringUtils.equals(FossConstants.YES, orgEntity.getSalesDepartment())) {
			//若为营业部，则判断是否为驻地营业部
			SaleDepartmentEntity saleDepartmentEntity = saleDepartmentService.querySimpleSaleDepartmentByCode(orgEntity.getCode());
			
			if(null == saleDepartmentEntity) {
				LOGGER.error("PDA查询清仓差异报告时发生异常，获取营业部信息失败!pdaNo："+ pdaNo + ",operatorCode:" + operatorCode + ",deptCode:" + deptCode);
				throw new TfrBusinessException("获取营业部信息失败！");
			}
			//营业部为驻地营业部时，仅对应外场的负责人可处理
			if(StringUtils.equals(FossConstants.YES, saleDepartmentEntity.getStation())) {
				LOGGER.error("PDA查询清仓差异报告时发生异常，驻地营业部无权处理清仓差异报告!pdaNo："+ pdaNo +",operatorCode:" + operatorCode + ",deptCode:" + deptCode);
				throw new TfrBusinessException("该部门为驻地部门，无权处理清仓差异报告！请联系外场经理！");
			} else {
				//非驻地部门时，员工均可处理
				
			}
		} else if(StringUtils.equals(FossConstants.YES, orgEntity.getAirDispatch())) {
			//空运总调差异报告对应外场经理处理
			LOGGER.error("PDA查询清仓差异报告时发生异常，空运总调无权处理清仓差异报告!pdaNo："+ pdaNo + ",operatorCode:" + operatorCode + ",deptCode:" + deptCode);
			throw new TfrBusinessException("该部门为空运总调，无权处理清仓差异报告！请联系外场经理！");
		} else {
			LOGGER.error("PDA查询清仓差异报告时发生异常，无效部门!"+ ",operatorCode:" + operatorCode + ",deptCode:" + deptCode);
			throw new TfrBusinessException("所属部门不可处理清仓差异报告！");
		}	
		
		return deptCodes;
	}
	/**
	 * 获取部门的上级外场、空运总调大部门
	 * @author niuly
	 * @date 2014-07-17 下午12:50:19
	 */
	private OrgAdministrativeInfoEntity getBigOrg(OrgAdministrativeInfoEntity currentOrg){
		if(StringUtils.equals(FossConstants.YES, currentOrg.getSalesDepartment()) || 
				StringUtils.equals(FossConstants.YES, currentOrg.getAirDispatch()) ||
				StringUtils.equals(FossConstants.YES, currentOrg.getTransferCenter())){
			return currentOrg;
		}else{
			//设置查询参数
			List<String> bizTypesList = new ArrayList<String>();
			//外场类型
			bizTypesList.add(BizTypeConstants.ORG_TRANSFER_CENTER);
			//空运总调类型
			bizTypesList.add(BizTypeConstants.ORG_AIR_DISPATCH);
			
			OrgAdministrativeInfoEntity bigOrg = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoIncludeSelfByCode(currentOrg.getCode(), bizTypesList);
			if(bigOrg == null){
				LOGGER.error("查询部门：" + currentOrg.getCode() + " 所属外场或空运总调大部门失败");
			}
			return bigOrg;
		}
	}
	/**
	 * @param configurationParamsService the configurationParamsService to set
	 */
	public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}


	/**
	 * @param pdaDifferReportDao the pdaDifferReportDao to set
	 */
	public void setPdaDifferReportDao(IPdaDifferReportDao pdaDifferReportDao) {
		this.pdaDifferReportDao = pdaDifferReportDao;
	}


	/**
	 * @param orgAdministrativeInfoService the orgAdministrativeInfoService to set
	 */
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}


	/**
	 * @param saleDepartmentService the saleDepartmentService to set
	 */
	public void setSaleDepartmentService(
			ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}

	/**
	 * @param stReportService the stReportService to set
	 */
	public void setStReportService(IStReportService stReportService) {
		this.stReportService = stReportService;
	}

	/**
	 * @param employeeService the employeeService to set
	 */
	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	/**
	 * @param userOrgRoleService the userOrgRoleService to set
	 */
	public void setUserOrgRoleService(IUserOrgRoleService userOrgRoleService) {
		this.userOrgRoleService = userOrgRoleService;
	}

	/**
	 * @param orgAdministrativeInfoComplexService the orgAdministrativeInfoComplexService to set
	 */
	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

}
