package com.deppon.foss.module.transfer.unload.server.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.framework.exception.BusinessException;
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
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.load.api.server.service.IConnectionBillService;
import com.deppon.foss.module.transfer.load.api.shared.domain.ConnectionBillEntity;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDAUnloadDiffReportService;
import com.deppon.foss.module.transfer.pda.api.shared.dto.PDAUnloadDiffReportDetailDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.PDAUnloadDiffReportDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;
import com.deppon.foss.module.transfer.stock.api.define.StockConstants;
import com.deppon.foss.module.transfer.stock.api.server.service.IStockService;
import com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity;
import com.deppon.foss.module.transfer.stock.api.shared.exception.StockException;
import com.deppon.foss.module.transfer.unload.api.server.dao.IPDAUnloadDiffReportDao;
import com.deppon.foss.module.transfer.unload.api.server.service.IUnloadDiffReportService;
import com.deppon.foss.module.transfer.unload.api.server.service.IUnloadTaskQueryService;
import com.deppon.foss.module.transfer.unload.api.server.service.IUnloadTaskService;
import com.deppon.foss.module.transfer.unload.api.shared.define.UnloadConstants;
import com.deppon.foss.module.transfer.unload.api.shared.domain.PDAUnloadDiffReportDetailLogEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.PDAUnloaddiffReportLogEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadBillDetailEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadDiffReportDetailEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadDiffReportEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadTaskEntity;
import com.deppon.foss.module.transfer.unload.api.shared.dto.UnloadTaskDto;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

public class PDAUnloadDiffReportService implements IPDAUnloadDiffReportService {
	static final Logger LOGGER = LoggerFactory.getLogger(PDAUnloadDiffReportService.class);

	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	private ISaleDepartmentService saleDepartmentService;
	private IEmployeeService employeeService;
	private IUserOrgRoleService userOrgRoleService;
	private IConfigurationParamsService configurationParamsService;
	private IPDAUnloadDiffReportDao pdaUnloadDiffReportDao;
	private IUnloadDiffReportService unloadDiffReportService;
	private IStockService stockService;
	private IUnloadTaskQueryService unloadTaskQueryService;
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	private IUnloadTaskService unloadTaskService;
	private IConnectionBillService connectionBillService;
	
	
	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	public void setSaleDepartmentService(
			ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}


	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}


	public void setUserOrgRoleService(IUserOrgRoleService userOrgRoleService) {
		this.userOrgRoleService = userOrgRoleService;
	}


	public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}


	public void setPdaUnloadDiffReportDao(
			IPDAUnloadDiffReportDao pdaUnloadDiffReportDao) {
		this.pdaUnloadDiffReportDao = pdaUnloadDiffReportDao;
	}

	public void setUnloadDiffReportService(
			IUnloadDiffReportService unloadDiffReportService) {
		this.unloadDiffReportService = unloadDiffReportService;
	}

	public void setStockService(IStockService stockService) {
		this.stockService = stockService;
	}

	public void setUnloadTaskQueryService(
			IUnloadTaskQueryService unloadTaskQueryService) {
		this.unloadTaskQueryService = unloadTaskQueryService;
	}

	public void setUnloadTaskService(IUnloadTaskService unloadTaskService) {
		this.unloadTaskService = unloadTaskService;
	}

	public void setConnectionBillService(
			IConnectionBillService connectionBillService) {
		this.connectionBillService = connectionBillService;
	}

	/**
	 * 
	 * <p>PDA根据部门查询卸车差异</p> 
	 * 1、可查询8小时内卸车差异报告；
	   2、返回满足以下条件的差异报告：
		1）	差异报告状态为“处理中”；
		2）	且差异明细类型为“少货”,或者“手输”；
		3）	且差异明细运输性质为快递；
		4）	且差异明细处理状态为“处理中”；
		5）	PDA端任务未提交；
	 * @author alfred
	 * @date 2014-6-11 下午4:26:43
	 * @param deptCode
	 * @param operatorCode
	 * @param pdaNo
	 * @return 
	 * @see com.deppon.foss.module.transfer.pda.api.server.service.IPDAUnloadDiffReportService#queryPDAUnloadDiffReportList(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<PDAUnloadDiffReportDto> queryPDAUnloadDiffReportList(
			String deptCode, String operatorCode, String pdaNo) {
		List<PDAUnloadDiffReportDto> unloadDiffReportList = new ArrayList<PDAUnloadDiffReportDto>(); 
		List<String> deptCodes = this.getHandlerDeptCodes(deptCode, operatorCode,pdaNo);
		
		//默认8小时
		int hours = ConstantsNumberSonar.SONAR_NUMBER_8;
		try {
			//获取配置参数，确定查询多长范围内的卸车差异报告
			ConfigurationParamsEntity defaultBizHourSlice = configurationParamsService.queryConfigurationParamsByOrgCode(DictionaryConstants.SYSTEM_CONFIG_PARM__TFR , ConfigurationParamsConstants.TFR_PARM_PDA_UNLOAD_DIFFER_TIME, deptCode);
			hours = Integer.valueOf(defaultBizHourSlice.getConfValue()).intValue();
		}catch(Exception e) {
			throw new TfrBusinessException("获取配置参数失败", ExceptionUtils.getFullStackTrace(e));
		}
		
		unloadDiffReportList =pdaUnloadDiffReportDao.queryPDAUnloadDiffReportList(deptCodes, hours);
		return unloadDiffReportList;
	}

	/**
	 * 
	 * <p>判断操作人是否可以处理差异报告，并获取可以处理的差异部门</p> 
	 * @author alfred
	 * @date 2014-6-12 下午4:26:52
	 * @param deptCode
	 * @param operatorCode
	 * @param pdaNo
	 * @return
	 * @see
	 */
	private List<String> getHandlerDeptCodes(String deptCode, String operatorCode, String pdaNo) {
		List<String> deptCodes = new ArrayList<String>();
		
		//判断部门属性
		OrgAdministrativeInfoEntity operaterOrgEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(deptCode);
		if(null == operaterOrgEntity) {
			LOGGER.error("PDA查询卸车差异报告时发生异常，获取部门信息失败!pdaNo："+ pdaNo + ",operatorCode:" + operatorCode + ",deptCode:" + deptCode);
			throw new TfrBusinessException("获取部门信息失败！");
		}
		
		OrgAdministrativeInfoEntity orgEntity = this.getBigOrg(operaterOrgEntity);
		
		if(null == orgEntity) {
			LOGGER.error("PDA查询卸车差异报告时发生异常，获取上级部门失败!pdaNo："+ pdaNo + ",operatorCode:" + operatorCode + ",deptCode:" + deptCode);
			throw new TfrBusinessException("获取上级组织失败！");
		}
		
		deptCodes.add(orgEntity.getCode());
		
		//如果为外场，先判断登录人是否为外场负责人，若是，则查询外场的差异报告
		if(StringUtils.equals(FossConstants.YES, orgEntity.getTransferCenter())) {
			//判断操作人是否为外场经理
			EmployeeEntity employeeEntity = employeeService.queryEmployeeByEmpCode(operatorCode);
			if(null == employeeEntity) {
				LOGGER.error("PDA查询卸车差异报告时发生异常，获取人员信息失败!pdaNo："+ pdaNo + ",operatorCode:" + operatorCode + ",deptCode:" + deptCode);
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
				LOGGER.error("PDA查询卸车差异报告时发生异常，登录人角色不是外场经理!pdaNo："+ pdaNo + ",operatorCode:" + operatorCode + ",deptCode:" + deptCode);
				throw new TfrBusinessException("该人员角色不是外场经理，无权处理卸车差异报告！");
			}
			
		} else if(StringUtils.equals(FossConstants.YES, orgEntity.getSalesDepartment())) {
			//若为营业部，则判断是否为驻地营业部
			SaleDepartmentEntity saleDepartmentEntity = saleDepartmentService.querySimpleSaleDepartmentByCode(orgEntity.getCode());
			
			if(null == saleDepartmentEntity) {
				LOGGER.error("PDA查询卸车差异报告时发生异常，获取营业部信息失败!pdaNo："+ pdaNo + ",operatorCode:" + operatorCode + ",deptCode:" + deptCode);
				throw new TfrBusinessException("获取营业部信息失败！");
			}
			//营业部为驻地营业部时，仅对应外场的负责人可处理
			if(StringUtils.equals(FossConstants.YES, saleDepartmentEntity.getStation())) {
				LOGGER.error("PDA查询卸车差异报告时发生异常，驻地营业部无权处理卸车差异报告!pdaNo："+ pdaNo + ",operatorCode:" + operatorCode + ",deptCode:" + deptCode);
				throw new TfrBusinessException("该部门为驻地部门，无权处理卸车差异报告！请联系外场经理！");
			} else {
				//非驻地部门时，员工均可处理
				
			}
		} else if(StringUtils.equals(FossConstants.YES, orgEntity.getAirDispatch())) {
			//空运总调差异报告对应外场经理处理
			LOGGER.error("PDA查询卸车差异报告时发生异常，空运总调无权处理卸车差异报告!pdaNo："+ pdaNo + ",operatorCode:" + operatorCode + ",deptCode:" + deptCode);
			throw new TfrBusinessException("该部门为空运总调，无权处理卸车差异报告！请联系外场经理！");
		} else {
			LOGGER.error("PDA查询卸车差异报告时发生异常，无效部门!pdaNo："+ pdaNo + ",operatorCode:" + operatorCode + ",deptCode:" + deptCode);
			throw new TfrBusinessException("所属部门不可处理卸车差异报告！");
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
	 * 
	 * <p>判断是否为外场经理。外场经理角色对应3个：
	 * 外场经理(TFR_TRANSFER_MANAGER)
	 * 外场经理（枢纽）(FOSS_WCJLSN)
	 * 快递外场经理(TKD_FR_TRANSFER_MANAGER)
	 </p> 
	 * @author alfred
	 * @date 2014-6-11 上午10:18:06
	 * @param unifiedCode
	 * @param empCode
	 * @return
	 * @see
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
	 * 
	 * <p>根据外场编码，获取该外场对应的所有驻地部门</p> 
	 * @author alfred
	 * @date 2014-6-11 上午10:24:25
	 * @param deptCode
	 * @return
	 * @see
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
	 * 
	 * <p>PDA根据卸车差异编号查询卸车差异明细</p> 
	 * @author alfred
	 * @date 2014-6-11 上午11:39:10
	 * @param reportCode
	 * @param operatorCode
	 * @param pdaNo
	 * @return 
	 * @see com.deppon.foss.module.transfer.unload.api.server.service.IPDAUnloadDiffReportService#queryPDAUnloadDiffReportDetailList(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<PDAUnloadDiffReportDetailDto> queryPDAUnloadDiffReportDetailList(
			String reportCode, String operatorCode, String pdaNo,String handInputFlg,String deptCode) {
		List<PDAUnloadDiffReportDetailDto>  pdaReportDetailList = new ArrayList<PDAUnloadDiffReportDetailDto>();
		//获取卸车差异报告基本信息
		UnloadDiffReportEntity baseEntity = unloadDiffReportService.queryUnloadDiffReportBasicInfo(reportCode);
		if(null == baseEntity) {
			throw new TfrBusinessException("该差异报告不存在！");
		}
		if(StringUtils.equals("Y", handInputFlg)) {
			//判断此人是否有处理差异的权限
			List<String> deptCodes = this.getHandlerDeptCodes(deptCode, operatorCode, pdaNo);
			
			if(!deptCodes.contains(baseEntity.getOrgCode())) {
				throw new TfrBusinessException("无权处理其他部门差异报告！");
			}	
		}
		//获取差异报告详情列表
		pdaReportDetailList = pdaUnloadDiffReportDao.queryPDAUnloadDiffDetailList(reportCode);
		return pdaReportDetailList;
	}

	/**
	 * 
	 * <p>PDA异步处理卸车差异明细</p> 
	 * @author alfred
	 * @date 2014-6-12 上午11:27:21
	 * @param reportCode
	 * @param waybillNo
	 * @param serialNo
	 * @param ScanTime
	 * @param operatorCode
	 * @param PdaNo
	 * @return 
	 * @see com.deppon.foss.module.transfer.pda.api.server.service.IPDAUnloadDiffReportService#handleUnloadDiffReport(java.lang.String, java.lang.String, java.lang.String, java.util.Date, java.lang.String, java.lang.String)
	 */
	@Override
	public int handleUnloadDiffReport(String deptCode,String reportCode, String waybillNo,
			String serialNo, Date scanTime, String operatorCode, String pdaNo) {
		
		if(StringUtils.isEmpty(reportCode)||StringUtils.isEmpty(waybillNo)||StringUtils.isEmpty(serialNo)){
			throw new TfrBusinessException("传入差异编号、运单号、流水号的参数有为空");
		}
		
		/**入库该件货物**/
		EmployeeEntity employeeEntity = employeeService.queryEmployeeByEmpCode(operatorCode);
		//获取卸车差异报告基本信息
		UnloadDiffReportEntity baseEntity = unloadDiffReportService.queryUnloadDiffReportBasicInfo(reportCode);
		//操作人code
		String optManCode = employeeEntity.getEmpCode();
		//操作人name
		String optManName = employeeEntity.getEmpName();
		//入库部门编码
		String optDeptCode = baseEntity.getOrgCode();
		
		/**查询少货差异明细**/
		UnloadDiffReportDetailEntity detailEntity = new UnloadDiffReportDetailEntity();
		//部门
		detailEntity.setUnloadOrgCode(optDeptCode);
		//运单号
		detailEntity.setWaybillNo(waybillNo);
		//流水号
		detailEntity.setSerialNo(serialNo);
		
		//查询少货差异明细
		List<UnloadDiffReportDetailEntity> diffReportDetailList = unloadDiffReportService.queryUnresolvedLackGoodsException(detailEntity);
		if(!CollectionUtils.isNotEmpty(diffReportDetailList)){
			//差异明细里没有该货件、抛出异常
			throw new StockException(StockException.NO_UNLODA_DIFF, "");
		}
		//处理部门为对应的外场或营业部
		String handleOrgCode = "";
		String handleOrgName = "";
		//获取操作部门基本信息
		OrgAdministrativeInfoEntity operatorOrgEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(deptCode);
		if(null != operatorOrgEntity) {
			//获取当前部门所对应的外场和营业部
			OrgAdministrativeInfoEntity orgEntity = this.getBigOrg(operatorOrgEntity);
			if(null != orgEntity) {
				handleOrgCode = orgEntity.getCode();
				handleOrgName = orgEntity.getName();
			} else {
				handleOrgCode = operatorOrgEntity.getCode();
				handleOrgName = operatorOrgEntity.getName();
			}
		}
		/*
		 * 入库
		 */
		InOutStockEntity inEntity = new InOutStockEntity();
		//运单号
		inEntity.setWaybillNO(waybillNo);
		//流水号
		inEntity.setSerialNO(serialNo);
		//操作人code
		inEntity.setOperatorCode(optManCode);
		//操作人name
		inEntity.setOperatorName(optManName);
		//入库时间
		inEntity.setInOutStockTime(new Date());
		//部门code
		inEntity.setOrgCode(optDeptCode);
		//备注
		inEntity.setNotes("PDA扫描");
		//入库类型：卸车少货找到入库
		inEntity.setInOutStockType(StockConstants.UNLOAD_LOSE_GOODS_FOUND_IN_STOCK_TYPE);
		UnloadTaskDto unloadTaskDto = unloadTaskQueryService.getUnloadTaskByUnloadTaskNo(baseEntity.getUnloadTaskNo());
		//车牌号, 综合查询模块需要用到
		inEntity.setInOutStockBillNO(unloadTaskDto.getVehicleNo());
		
		int result = 0;
		//查询入库记录
		List<InOutStockEntity> inOutStocks = stockService.queryInStockInfo(waybillNo, serialNo, null, unloadTaskDto.getUnloadStartTime());
		if(CollectionUtils.isNotEmpty(inOutStocks)) {
			//如果差异报告创建时间之后本部门有入库记录则不用继续入库, 直接更新卸车差异状态
			result = -2;
		} else {
			//如果成功, 该接口里会调用处理卸车差异的方法
			//如果正常签收则返回-2
			result = stockService.inStockPC(inEntity);
		}
		
		//返回-2说明该票货已经正常签收, 直接更新卸车差异状态
		//返回其他则 stockService中自己调用处理差错的接口
		if(result == -2) {
			//处理卸车少货差异
			UnloadDiffReportDetailEntity diffReportDetail = diffReportDetailList.get(0);
			//处理时间
			diffReportDetail.setExceptionHandleTime(new Date());
			//部门编号
			diffReportDetail.setHandleOrgCode(handleOrgCode);
			//部门名称
			diffReportDetail.setHandleOrgName(handleOrgName);
			//处理人工号
			diffReportDetail.setHandlerCode(optManCode);
			//处理人名称
			diffReportDetail.setHandlerName(optManName);
			//备注
			diffReportDetail.setNote("PDA扫描");
			synchronized (this) {
				//处理差错
				unloadDiffReportService.handleUnloadLackDiffReport(diffReportDetail, optManCode, optManName, optDeptCode);
			}
		}
		
		//记录PDA处理日志
		try{
			PDAUnloadDiffReportDetailLogEntity  detailLogEntity = new PDAUnloadDiffReportDetailLogEntity();
			detailLogEntity.setId(UUIDUtils.getUUID());
			detailLogEntity.setCreateDate(new Date());
			detailLogEntity.setCreateUser(operatorCode);
			detailLogEntity.setPdaNo(pdaNo);
			detailLogEntity.setReportCode(reportCode);
			detailLogEntity.setScanTime(scanTime);
			detailLogEntity.setSerialNo(serialNo);
			detailLogEntity.setWaybillNo(waybillNo);
			this.addPDAScanDetailLog(detailLogEntity);
		} catch(Exception e) {
			LOGGER.error("PDA处理卸车差异明细时，日志记录失败！差异报告编号：" + reportCode + ",运单号：" + waybillNo + ",流水号：" + serialNo);
		}
		//返回处理成功
		return FossConstants.SUCCESS;
	}

	/**
	 * 
	 * <p>提交卸车差异报告-同步</p> 
	 * @author alfred
	 * @date 2014-6-13 上午10:07:37
	 * @param reportCode
	 * @param operatorCode
	 * @param pdaNo
	 * @return 
	 * @see com.deppon.foss.module.transfer.pda.api.server.service.IPDAUnloadDiffReportService#commitUnloadDiffReport(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public int commitUnloadDiffReport(String reportCode, String operatorCode,
			String pdaNo) {
		//获取卸车差异报告基本信息
		UnloadDiffReportEntity baseEntity = unloadDiffReportService.queryUnloadDiffReportBasicInfo(reportCode);
		if(null != baseEntity&&StringUtils.isNotBlank(baseEntity.getPdaHandleStatus())){
			if(!baseEntity.getPdaHandleStatus().equals("Y")){
				try{
					pdaUnloadDiffReportDao.updateUnloadDiffReportHandleState(reportCode);
				} catch(Exception e) {
					LOGGER.error("提交失败！差异报告编号：" + reportCode+"操作人工号" + operatorCode + "PDA编号"+pdaNo,e);
					throw new TfrBusinessException("提交失败，请重试！");
				}
				//插入日志
				try{
					PDAUnloaddiffReportLogEntity  reportLogEntity = new PDAUnloaddiffReportLogEntity();
					reportLogEntity.setId(UUIDUtils.getUUID());
					reportLogEntity.setCreateUser(operatorCode);
					reportLogEntity.setCreateDate(new Date());
					reportLogEntity.setPdaNo(pdaNo);
					reportLogEntity.setReportCode(reportCode);
					reportLogEntity.setStatus("Y");
					this.addPDAReportLog(reportLogEntity);
					
				}catch(Exception e) {
					LOGGER.error("PDA提交卸车差异报告处理任务，保存处理日志失败！差异报告编号：" + reportCode+"操作人工号" + operatorCode + "PDA编号"+pdaNo);
				}
			}
		}
		return FossConstants.SUCCESS;
	}
	
	/**
	 * 
	 * <p>插入PDA处理卸车差异报告明细日志</p> 
	 * @author alfred
	 * @date 2014-6-13 下午3:41:51
	 * @param DetailLogEntity
	 * @see
	 */
	public void addPDAScanDetailLog(PDAUnloadDiffReportDetailLogEntity  detailLogEntity){
		pdaUnloadDiffReportDao.addPDAScanDetailLog(detailLogEntity);
	}
	
	/**
	 * 
	 * <p>插入PDA处理卸车报告日志</p> 
	 * @author alfred
	 * @date 2014-6-13 下午3:44:03
	 * @param reportLogEntity
	 * @see
	 */
	public void addPDAReportLog(PDAUnloaddiffReportLogEntity reportLogEntity){
		pdaUnloadDiffReportDao.addPDAReportLog(reportLogEntity);
	}
	
	

	/**
	 *查询司机当天的二程接驳卸车差异报告
	 * @author 205109-foss-zenghaibin
	 * @date 2015-05-24 13:26:30
	 * @param deptCode部门编码，operatorCode操作人
	 ***/
	public List<PDAUnloadDiffReportDto>  querySCPDAUnloadDiffReportList(String deptCode,String operatorCode){
		
		if(StringUtils.isBlank(deptCode)){
			throw new TfrBusinessException("部门编码为空");
		}
		if(StringUtils.isBlank(operatorCode)){
			throw new TfrBusinessException("操作员编码为空");
		}
			
		List<PDAUnloadDiffReportDto> unloadDiffReportList = new ArrayList<PDAUnloadDiffReportDto>(); 
		
		unloadDiffReportList =pdaUnloadDiffReportDao.querySCPDAUnloadDiffReportList(deptCode, operatorCode);
		return unloadDiffReportList;
	
	}
	/**
	 *查询二程接驳卸车差异报告
	 * @author 205109-foss-zenghaibin
	 * @date 2015-05-24 13:26:30
	 * @param deptCode部门编码，operatorCode操作人
	 ***/
	public List<PDAUnloadDiffReportDto>  querySCPDAUnloadDiffReportList(String deptCode,String operatorCode,
			String reportCode,Date queryDate){
		
		if(StringUtils.isBlank(deptCode)){
			throw new TfrBusinessException("部门编码为空");
		}
		if(StringUtils.isBlank(operatorCode)){
			throw new TfrBusinessException("操作员编码为空");
		}
		List<PDAUnloadDiffReportDto> unloadDiffReportList = new ArrayList<PDAUnloadDiffReportDto>(); 
		unloadDiffReportList =pdaUnloadDiffReportDao.querySCPDAUnloadDiffReportList(deptCode, operatorCode,reportCode,queryDate);
		return unloadDiffReportList;	
		
	}

	/**
	 *根据差异报告编号查询二程接驳卸车差异报告明细
	 * @author 205109-foss-zenghaibin
	 * @date 2015-05-24 13:26:30
	 * @param reportCode
	 ***/
	public List<PDAUnloadDiffReportDetailDto> querySCPDAUnloadDiffReportDetailList(String reportCode,String operatorCode){
		
		if(StringUtils.isBlank(reportCode)){
			throw new TfrBusinessException("差异报告编码为空");
		}
		if(StringUtils.isBlank(operatorCode)){
			throw new TfrBusinessException("操作员编码为空");
		}
		List<PDAUnloadDiffReportDetailDto> unloadDiffReporDetailtList = new ArrayList<PDAUnloadDiffReportDetailDto>(); 
		unloadDiffReporDetailtList =pdaUnloadDiffReportDao.querySCPDAUnloadDiffReportDetailList(reportCode,operatorCode);
		return unloadDiffReporDetailtList;
	}
	
	
	/**
	 *二程接驳卸车差异明细扫描处理
	 *@date 2015-05-25 11:05:20	 
	 *@author 205109 foss zenghaibin
	 *@param deptCode 部门编码 
	 *@param reportCode 差异报告编码
	 *@param waybillno 运单好
	 *@param serialNo 流水号
	 *@param Scantim 扫描时间
	 *@param operatorCode 操作员工号
	 *@param pdaNo 设备编号
	 ***/
	public int handleSCUnloadDiffReport(String deptCode,String reportCode,String waybillNo,String serialNo,
			Date scanTime,String operatorCode,String pdaNo){
		
		if(StringUtils.isEmpty(reportCode)||StringUtils.isEmpty(waybillNo)||StringUtils.isEmpty(serialNo)){
			throw new TfrBusinessException("传入差异编号、运单号、流水号的参数有为空");
		}
		
		/**入库该件货物**/
		EmployeeEntity employeeEntity = employeeService.queryEmployeeByEmpCode(operatorCode);
		//获取卸车差异报告基本信息
		UnloadDiffReportEntity baseEntity = unloadDiffReportService.queryUnloadDiffReportBasicInfo(reportCode);
		
		//操作人code
		String optManCode = employeeEntity.getEmpCode();
		//操作人name
		String optManName = employeeEntity.getEmpName();
		//卸车部门编码（一般车队）
		String optDeptCode = baseEntity.getOrgCode();
		//获取上一环节部门，即装车部门
		OrgAdministrativeInfoEntity loadOrg  = null;
		try{
			loadOrg = this.queryPreviousOrgForUnloadDiff(baseEntity);
		}catch(BusinessException e){
			LOGGER.error("手工处理卸车差异报告时发生异常，差异报告编号：" + reportCode + "异常信息：" + e.getMessage());
			return FossConstants.FAILURE;
		}
		//sonar为空判断 218427
		if(loadOrg==null){
			throw new TfrBusinessException("loadOrg为空");
		}
		//入库部门编码
		String instockCode="";
		if(loadOrg!=null){
			instockCode = loadOrg.getCode();	
			if(StringUtils.isBlank(loadOrg.getCode())){
				LOGGER.error("手工处理卸车差异报告时发生异常，差异报告编号：" + reportCode + "异常信息：" +"装车部门编码为空");
				return  FossConstants.FAILURE;
			}
		}
		
		/*
		 * 读取配置参数中卸车少货上报OA的时限
		 */
		ConfigurationParamsEntity entityDuration = configurationParamsService.queryConfigurationParamsByOrgCode(
																					DictionaryConstants.SYSTEM_CONFIG_PARM__TFR,
																					ConfigurationParamsConstants.TFR_PARM__UNLOAD_EXC_TIME_LIMIT_REPORT_OA_ERROR,
																					loadOrg.getCode());
		Long timeLimit =0L;//初始为0
		if (entityDuration != null && StringUtils.isNotEmpty(entityDuration.getConfValue())) {
			timeLimit = Long.parseLong(entityDuration.getConfValue());
		} 
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//定义时间格式

		long from =0L;
		long to=0L;
		try {
			from = df.parse(df.format(baseEntity.getReportCreateTime())).getTime();
			to = df.parse(df.format(new Date())).getTime();
			} catch (ParseException e) {
				LOGGER.error("手工处理卸车差异报告时发生异常，差异报告编号：" + reportCode + "异常信息：" + e.getMessage());
				throw new TfrBusinessException("时间转换错误");
			}
		long validate = (to - from) / (ConstantsNumberSonar.SONAR_NUMBER_1000 * ConstantsNumberSonar.SONAR_NUMBER_60 * ConstantsNumberSonar.SONAR_NUMBER_60 );
		if(validate>timeLimit){
		
			throw new TfrBusinessException("该差异流水明细已超过配置oa时间，不可手工处理");
		}	
		
		
		/**查询少货差异明细**/
		UnloadDiffReportDetailEntity detailEntity = new UnloadDiffReportDetailEntity();
		//部门
		detailEntity.setUnloadOrgCode(optDeptCode);
		//运单号
		detailEntity.setWaybillNo(waybillNo);
		//流水号
		detailEntity.setSerialNo(serialNo);
		
		//查询少货差异明细
		List<UnloadDiffReportDetailEntity> diffReportDetailList = unloadDiffReportService.queryUnresolvedLackGoodsException(detailEntity);
		if(!CollectionUtils.isNotEmpty(diffReportDetailList)){
			//差异明细里没有该货件、抛出异常
			throw new StockException(StockException.NO_UNLODA_DIFF, "");
		}
		//处理部门为对应的外场或营业部
		String handleOrgCode = "";
		String handleOrgName = "";
		//获取操作部门基本信息
		OrgAdministrativeInfoEntity operatorOrgEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(deptCode);
		if(null != operatorOrgEntity) {
			
			handleOrgCode=operatorOrgEntity.getCode();
			handleOrgName=operatorOrgEntity.getName();
		}
		/*
		 * 入库
		 */
		InOutStockEntity inEntity = new InOutStockEntity();
		//运单号
		inEntity.setWaybillNO(waybillNo); 
		//流水号
		inEntity.setSerialNO(serialNo);
		//操作人code
		inEntity.setOperatorCode(optManCode);
		//操作人name
		inEntity.setOperatorName(optManName);
		//入库时间
		inEntity.setInOutStockTime(new Date());
		//部门code
		inEntity.setOrgCode(instockCode);
		//卸车任务建立部门
		inEntity.setUnloadSCOrgCode(optDeptCode);
		//二程接驳卸车类型
		inEntity.setUnloadSCType(UnloadConstants.UNLOAD_TASK_TYPE_SCEXPRESS);
		//备注
		inEntity.setNotes("PDA扫描");
		//入库类型：卸车少货找到入库
		inEntity.setInOutStockType(StockConstants.UNLOAD_LOSE_GOODS_FOUND_IN_STOCK_TYPE);
		UnloadTaskDto unloadTaskDto = unloadTaskQueryService.getUnloadTaskByUnloadTaskNo(baseEntity.getUnloadTaskNo());
		//车牌号, 综合查询模块需要用到
		inEntity.setInOutStockBillNO(unloadTaskDto.getVehicleNo());
		
		int result = 0;
		//查询入库记录
		List<InOutStockEntity> inOutStocks = stockService.queryInStockInfo(waybillNo, serialNo, null, unloadTaskDto.getUnloadStartTime());
		if(CollectionUtils.isNotEmpty(inOutStocks)) {
			//如果差异报告创建时间之后本部门有入库记录则不用继续入库, 直接更新卸车差异状态
			result = -2;
		} else {
			//如果成功, 该接口里会调用处理卸车差异的方法
			//如果正常签收则返回-2
			result = stockService.inStockPC(inEntity);
		}
		
		//返回-2说明该票货已经正常签收, 直接更新卸车差异状态
		//返回其他则 stockService中自己调用处理差错的接口
		if(result == -2) {
			//处理卸车少货差异
			UnloadDiffReportDetailEntity diffReportDetail = diffReportDetailList.get(0);
			//处理时间
			diffReportDetail.setExceptionHandleTime(new Date());
			//部门编号
			diffReportDetail.setHandleOrgCode(handleOrgCode);
			//部门名称
			diffReportDetail.setHandleOrgName(handleOrgName);
			//处理人工号
			diffReportDetail.setHandlerCode(optManCode);
			//处理人名称
			diffReportDetail.setHandlerName(optManName);
			//备注
			diffReportDetail.setNote("PDA扫描");
			synchronized (this) {
				//处理差错
				unloadDiffReportService.handleSCUnloadLackDiffReport(diffReportDetail, optManCode, optManName, optDeptCode,instockCode);
			}
		}
		
		//记录PDA处理日志
		try{
			PDAUnloadDiffReportDetailLogEntity  detailLogEntity = new PDAUnloadDiffReportDetailLogEntity();
			detailLogEntity.setId(UUIDUtils.getUUID());
			detailLogEntity.setCreateDate(new Date());
			detailLogEntity.setCreateUser(operatorCode);
			detailLogEntity.setPdaNo(pdaNo);
			detailLogEntity.setReportCode(reportCode);
			detailLogEntity.setScanTime(scanTime);
			detailLogEntity.setSerialNo(serialNo);
			detailLogEntity.setWaybillNo(waybillNo);
			this.addPDAScanDetailLog(detailLogEntity);
		} catch(Exception e) {
			LOGGER.error("PDA处理卸车差异明细时，日志记录失败！差异报告编号：" + reportCode + ",运单号：" + waybillNo + ",流水号：" + serialNo);
		}
		//返回处理成功
		return FossConstants.SUCCESS;
	}
	
	
	/**
	 * 上报多货、少货差错时调用该方法获取上一环节部门
	 * @author 205109-foss-zenghaibin
	 * @date 2015-05-25 下午6:44:46
	 */
	private OrgAdministrativeInfoEntity queryPreviousOrgForUnloadDiff(UnloadDiffReportEntity tempEntity){
		//卸车任务ID
		String unloadTaskId = tempEntity.getUnloadTaskId();
		//获取卸车任务实体
		UnloadTaskEntity unloadTask = this.unloadTaskService.queryUnloadTaskBaseInfoById(unloadTaskId);
		if(unloadTask != null){
			//卸车类型
			String unloadType = unloadTask.getUnloadType();
			//获取卸车任务下的单据
			List<UnloadBillDetailEntity> billList = unloadTaskService.queryUnloadTaskBillDetailListById(unloadTaskId);
			//如果为二程接驳卸车
			 if(StringUtils.equals(unloadType, UnloadConstants.UNLOAD_TASK_TYPE_SCEXPRESS)){
				if(CollectionUtils.isNotEmpty(billList)){
					//获取卸车任务单据明细
					UnloadBillDetailEntity billEntity = billList.get(0);
					//获取交接单号
					String connectionBillNo = billEntity.getBillNo();
					//调用交接单服务，获取出发部门code
					 ConnectionBillEntity  connectionBill = this.connectionBillService.queryConnectionBillByNo(connectionBillNo);
					//出发部门code
					String departOrgCode = connectionBill.getDepartDeptCode();
					//获取出发部门实体
					OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(departOrgCode);
					return orgEntity;
				}else{
					LOGGER.error("根据卸车任务（编号：" + unloadTask.getUnloadTaskNo() + "）获取任务单据列表为空！");
				}
			}else{
				LOGGER.error("卸车任务（编号：" + unloadTask.getUnloadTaskNo() + unloadType + "）类型无法识别！");
			}
		}
		return null;
	}
	
}
