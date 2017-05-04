package com.deppon.foss.module.transfer.load.server.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.alibaba.fastjson.JSON;
import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.DriverBaseDto;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.common.api.server.service.IFOSSToWkService;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.load.api.server.dao.ILoadManagerExceptionDao;
import com.deppon.foss.module.transfer.load.api.server.dao.ILoadTaskQueryDao;
import com.deppon.foss.module.transfer.load.api.server.dao.IPDALoadDao;
import com.deppon.foss.module.transfer.load.api.server.service.IHandOverBillService;
import com.deppon.foss.module.transfer.load.api.server.service.ILoadManagerExceptionService;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoadManagerExceptionEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoadTaskEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.LoadTaskEntityDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.LoadTaskExceptionDto;
import com.deppon.foss.module.transfer.load.api.shared.vo.LoadManagerExceptionVo;
import com.deppon.foss.module.transfer.load.dubbo.api.define.CheckVehicleNoResultDto;
import com.deppon.foss.module.transfer.load.dubbo.api.define.SubmitLoadTaskDto;
import com.deppon.foss.module.transfer.load.dubbo.api.exception.TfrLoadException;
import com.deppon.foss.module.transfer.load.dubbo.api.service.IPDALoadCarManagerServicedubbo;
import com.deppon.foss.module.transfer.pda.api.shared.dto.LoadTaskCreateDto;
import com.deppon.foss.util.define.FossConstants;

public class LoadManagerExceptionService implements ILoadManagerExceptionService{
	
	/**
	 * 日志
	 */
	//private static final Logger LOGGER=LoggerFactory.getLogger(LoadManagerExceptionService.class);
	
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	
	private  ISaleDepartmentService saleDepartmentService;
	
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;

	private ILoadManagerExceptionDao loadManagerExceptionDao;
	
	private IPDALoadCarManagerServicedubbo pDALoadCarManagerServicedubbo;
	
	private ILoadTaskQueryDao loadTaskQueryDao;
	
    private IPDALoadDao pdaLoadDao;
	
	private IFOSSToWkService fossToWkService;
	
	private IHandOverBillService handOverBillService;
	
	/**
	 * @param loadManagerExceptionDao the loadManagerExceptionDao to set
	 */
	public void setLoadManagerExceptionDao(ILoadManagerExceptionDao loadManagerExceptionDao) {
		this.loadManagerExceptionDao = loadManagerExceptionDao;
	}

	
	public void setOrgAdministrativeInfoService(IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}


	public void setSaleDepartmentService(ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}

	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	public void setpDALoadCarManagerServicedubbo(IPDALoadCarManagerServicedubbo pDALoadCarManagerServicedubbo) {
		this.pDALoadCarManagerServicedubbo = pDALoadCarManagerServicedubbo;
	}

	public void setLoadTaskQueryDao(ILoadTaskQueryDao loadTaskQueryDao) {
		this.loadTaskQueryDao = loadTaskQueryDao;
	}


	public void setPdaLoadDao(IPDALoadDao pdaLoadDao) {
		this.pdaLoadDao = pdaLoadDao;
	}

	public void setFossToWkService(IFOSSToWkService fossToWkService) {
		this.fossToWkService = fossToWkService;
	}
	
	public void setHandOverBillService(IHandOverBillService handOverBillService) {
		this.handOverBillService = handOverBillService;
	}


	/**
	 * 根据界面输入的条件查询异常装车任务数量
	 * @author 332209-FOSS-ruilibao
	 * @date 2017年3月29日
	 * @param loadManagerExceptionVo
	 * @return
	 */
	@Override
	public Long queryLoadManagerExceptionCount(LoadManagerExceptionVo loadManagerExceptionVo) {
		//封装查询实体
		LoadManagerExceptionEntity entity = new LoadManagerExceptionEntity();
		//装车任务号
		entity.setLoadTaskNo(loadManagerExceptionVo.getLoadTaskNo());
		//当前部门Code
		String currentDeptCode = FossUserContext.getCurrentDeptCode();
		//当前登录部门
		entity.setOrgCode(queryOrgCode(currentDeptCode).getCode());
		//理货员编码
		entity.setLoaderCode(loadManagerExceptionVo.getLoaderCode());
		//装车开始时间
		entity.setTaskCreateTimeStart(loadManagerExceptionVo.getTaskCreateTimeStart());
		//装车开始时间
		entity.setTaskCreateTimeEnd(loadManagerExceptionVo.getTaskCreateTimeEnd());
		//任务状态
		entity.setTaskStatus(loadManagerExceptionVo.getTaskStatus());
		//车牌号
		entity.setVehicleNo(loadManagerExceptionVo.getVehicleNo());
		
		return loadManagerExceptionDao.queryLoadManagerExceptionCount(entity);
	}
	
	/**
	 * 根据界面输入的条件查询异常装车任务
	 * @author 332209-FOSS-ruilibao
	 * @date 2017年3月29日
	 * @param loadManagerExceptionVo
	 * @return
	 */
	public List<LoadTaskExceptionDto> queryLoadManagerException(LoadManagerExceptionVo loadManagerExceptionVo,int limit,int start){
		//封装查询实体
		LoadManagerExceptionEntity entity = new LoadManagerExceptionEntity();
		//装车任务号
		entity.setLoadTaskNo(loadManagerExceptionVo.getLoadTaskNo());
		//当前部门Code
		String currentDeptCode = FossUserContext.getCurrentDeptCode();
		//当前登录部门
		entity.setOrgCode(queryOrgCode(currentDeptCode).getCode());
		//理货员编码
		entity.setLoaderCode(loadManagerExceptionVo.getLoaderCode());
		//装车开始时间
		entity.setTaskCreateTimeStart(loadManagerExceptionVo.getTaskCreateTimeStart());
		//装车开始时间
		entity.setTaskCreateTimeEnd(loadManagerExceptionVo.getTaskCreateTimeEnd());
		//任务状态
		entity.setTaskStatus(loadManagerExceptionVo.getTaskStatus());
		//车牌号
		entity.setVehicleNo(loadManagerExceptionVo.getVehicleNo());
		
		return loadManagerExceptionDao.queryLoadManagerException(entity,limit,start);
	}

	/**
	 * 根据界面输入的条件查询异常装车任务
	 * @author 328768-FOSS-jianfugao
	 * @date 2017年3月29日
	 * @param String orgCode
	 * @return
	 */
	public OrgAdministrativeInfoEntity queryOrgCode(String orgCode){
		// 调用综合接口查询出发部门是否为外场
		OrgAdministrativeInfoEntity origOrg = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(orgCode);
		if (origOrg != null) {
			// 如果营业部
			if (FossConstants.YES.equals(origOrg.getSalesDepartment())) {
				SaleDepartmentEntity saleDetp = saleDepartmentService.querySaleDepartmentByCode(origOrg.getCode());
				if (saleDetp != null && FossConstants.YES.equals(saleDetp.getStation())) {
					origOrg = orgAdministrativeInfoService
							.queryOrgAdministrativeInfoByCode(saleDetp.getTransferCenter());
				}
			}
			// 装车部门为外场
			else {
				// 查询对应装车部门的上级部门
				List<String> bizTypes = new ArrayList<String>();
				bizTypes.add(BizTypeConstants.ORG_TRANSFER_CENTER);
				try {
					OrgAdministrativeInfoEntity org = orgAdministrativeInfoComplexService
							.queryOrgAdministrativeInfoByCode(orgCode, bizTypes);
					if (org != null) {
						if (StringUtils.isNotBlank(org.getCode())) {
							return org;
						} else {
							return null;
						}
					} else {
						return null;// 部门信息为空
					}
				} catch (BusinessException e) {
					return null;
				}
			}
		}
		return origOrg;
	}
	
	@Transactional
	public void editLoadTaskVehicleNo(LoadManagerExceptionVo loadManagerExceptionVo) {
		// 获取车牌号
		String vehicleNo = loadManagerExceptionVo.getVehicleNo();
		String loadTaskNo = loadManagerExceptionVo.getLoadTaskNo();
		String submitTotalCount=loadManagerExceptionVo.getSubmitTotalCount();

		// 根据装车任务号查询装车实体
		LoadTaskEntityDto loadTaskDto = loadManagerExceptionDao.queryLoadManagerEntity(loadTaskNo);

		// 修改界面上修改过的车牌号进行校验
		com.deppon.foss.module.transfer.load.dubbo.api.define.LoadTaskDto editloadTask = new com.deppon.foss.module.transfer.load.dubbo.api.define.LoadTaskDto();
		editloadTask.setVehicleNo(vehicleNo);
		// 任务状态
		if ("LOADING".equals(loadManagerExceptionVo.getTaskStatus())) {
			editloadTask.setSubmitStatus("LOADING");
		} else {
			editloadTask.setSubmitStatus("FINISHED");
		}
		editloadTask.setTaskNo(loadTaskNo);
		editloadTask.setLoadTaskType(loadTaskDto.getLoadTaskType());
		editloadTask.setCreateOrgCode(loadTaskDto.getOrigOrgCode());
		List<String> destOrgCodesList = new ArrayList<String>();
		destOrgCodesList.add(loadTaskDto.getDestOrgCode());
		editloadTask.setDestOrgCodes(destOrgCodesList);
		editloadTask.setSendType(loadTaskDto.getSendType());
		editloadTask.setOperatorCode(loadTaskDto.getLoaderCode());
		// 校验车牌号
		try {
			CheckVehicleNoResultDto resultDto = pDALoadCarManagerServicedubbo.checkPdaVehicleNo(editloadTask);
			if (resultDto.getExceptionMsg() != null) {
				throw new TfrBusinessException(resultDto.getExceptionMsg());
			} else {
				LoadTaskEntity entity = new LoadTaskEntity();
				// 校验通过，更新车牌号，更新异常信息为空
				entity.setVehicleNo(resultDto.getVehicleNo());
				entity.setErrorMsg(null);
				entity.setTaskNo(loadTaskNo);
				entity.setLine(resultDto.getLineName());
				pdaLoadDao.updateLoadTask(entity);
				if (loadTaskDto.getSendType() != 0) {
					// 通知悟空修改车牌号
					LoadTaskCreateDto wkLoadTask = new LoadTaskCreateDto();
					wkLoadTask.setLoadTaskNo(loadTaskDto.getLoadTaskNo());
					wkLoadTask.setVehicleNo(resultDto.getVehicleNo());
					wkLoadTask.setOrigOrgCode(loadTaskDto.getOrigOrgCode());
					wkLoadTask.setOperationOrgCode(loadTaskDto.getOrigOrgCode());
					wkLoadTask.setOperatorNo(loadTaskDto.getLoaderCode());
					wkLoadTask.setDriverNo(loadManagerExceptionVo.getDriverBaseDto().getDriverCode());
					wkLoadTask.setDriverName(loadManagerExceptionVo.getDriverBaseDto().getDriverName());
					wkLoadTask.setDriverPhone(loadManagerExceptionVo.getDriverBaseDto().getDriverPhone());
					String reqMsg = JSON.toJSONString(wkLoadTask);
					
					@SuppressWarnings("unchecked")
					Map<String, String> resMap =  (Map<String, String>)fossToWkService.sysnEditLoadToWkByTaskNo(reqMsg);
					if(!resMap.containsKey("result") ) {
						throw new TfrBusinessException("ECS - 修改装车车牌号失败");
					} else {
						if( "0".equals(resMap.get("result")) ) {
							if(resMap.containsKey("errMsg")) {
								throw new TfrBusinessException("ECS - "+resMap.get("errMsg"));
							} else {
								throw new TfrBusinessException("ECS - 修改装车车牌号失败,发生未知错误");
							}
						}
					}
				}
			}
		} catch (TfrLoadException e) {
			throw new TfrBusinessException(e.getMessage());
		}
		// 如果是提交
		if ("FINISHED".equals(loadManagerExceptionVo.getTaskStatus())) {
			// 执行FOSS提交装车任务
			SubmitLoadTaskDto dto = new SubmitLoadTaskDto();
			dto.setLoadTaskNo(loadTaskNo);
			dto.setSubmitTotalTicketCount(submitTotalCount);
			dto.setLoadEndTime(loadTaskDto.getLoadEndTime());
			dto.setDeviceNo(loadTaskDto.getDeviceNo());
			dto.setLoaderCode(loadTaskDto.getLoaderCode());
			dto.setSendType(loadTaskDto.getSendType());
			dto.setOperationOrgCode(loadTaskDto.getOrigOrgCode());
			dto.setOperatorNo(loadTaskDto.getLoaderCode());
			dto.setExceptionMsg(null);
			dto.setIsPageFlag("Y");
			try {
				pDALoadCarManagerServicedubbo.submitLoadTask(dto);
			} catch (TfrLoadException e) {
				throw new TfrBusinessException(e.getMessage());
			}
		}
	}
	
	public DriverBaseDto queryDriverInfoByVehicleNo(String vehicleNo){
		
		DriverBaseDto driverInfo = handOverBillService.queryDriverInfoByVehicleNo(vehicleNo);
		//返回司机信息
		return driverInfo;
	}
}
