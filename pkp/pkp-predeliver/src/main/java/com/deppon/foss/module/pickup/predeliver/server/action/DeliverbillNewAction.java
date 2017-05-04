package com.deppon.foss.module.pickup.predeliver.server.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAdministrativeRegionsService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISalesMotorcadeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IVehicleService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IDeliverbillNewService;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.DeliverbillConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.DeliverbillEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.exception.DeliverbillException;
import com.deppon.foss.module.pickup.predeliver.api.shared.vo.DeliverbillNewVo;

/**
 * 派送排单Action.
 *
 * @author 
 * @since
 * @version
 */
public class DeliverbillNewAction extends AbstractAction {
	
	/**
	 * 日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(DeliverbillNewAction.class);

	/** 
	 * 序列化
	 */
	private static final long serialVersionUID = 7512460180364008438L;

	/**
	 * 符号
	 */
	private static final String STRING_DELIMETER = ",";

	/**
	 * 派送单vo(新)
	 */
	private DeliverbillNewVo deliverbillVo = new DeliverbillNewVo();
	
	/** 
	 * 部门查询起始. 
	 */
	private static final int BEGIN_NUM = 0;
	
	/** 
	 * 派送部查询页面大小. 
	 */
	private static final int PAGE_SIZE = 100;

	/**
	 * 派送单service（新）
	 */
	private IDeliverbillNewService deliverbillNewService;

	/**
	 * 车辆service
	 */
	private IVehicleService vehicleService;

	/**
	 * 配置参数
	 */
	private IConfigurationParamsService configurationParamsService;
	/**
	 * 车队编码
	 */
	private String fleetCode;
	/**
	 * sequence
	 */
	private String sequence;
	/**
	 * 省名称
	 */
	private String provName;
	/**
	 * 市名称
	 */
	private String cityName;
	/**
	 * 部门 复杂查询 service
	 */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	/**
	 * 营业部车队对应
	 */
	private ISalesMotorcadeService salesMotorcadeService;
	/**
	 * 组织信息 Service接口
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	/**
	 * 行政区域 Service接口
	 */
	private IAdministrativeRegionsService administrativeRegionsService;
	
	/** 
	 * 业务互斥锁服务
	 */
	private IBusinessLockService businessLockService;
	
	
	
	
	
	/**
	 * 根据查询条件，查询派送单，分页显示.
	 *
	 * @return the string
	 * @author 
	 * @since
	 * @version
	 */
	public String queryDeliverbillNewList() {
		try {
			//查询总条数
			this.totalCount = this.deliverbillNewService.queryDeliverbillCountByCondition(this.deliverbillVo.getDeliverbillNewDto());
			//若总条数大于0
			if (this.totalCount != null && this.totalCount > 0) {
				//查询派送单
				this.deliverbillVo.setDeliverbillNewDtoList(this.deliverbillNewService.queryDeliverbillList(this.deliverbillVo.getDeliverbillNewDto(),this.getStart(), this.getLimit()));
			} else {
				//设置为null
				this.deliverbillVo.setDeliverbillNewDtoList(null);
			}
			//返回成功
			return super.returnSuccess();
		} catch (BusinessException e) {
			//记录日志
			LOGGER.error(e.getMessage(), e);
			//返回异常
			return super.returnError(e);
		}
	}
	
	
	
	/**
	 * 编辑/新增预派送单入口.
	 *
	 * @return the string
	 * @author 
	 * @date 
	 */
	@JSON
	public String goEditDeliverbillIndex() {
		try {
			//若派送单id为空
			if (this.deliverbillVo.getDeliverbill().getId() == null|| "".equals(this.deliverbillVo.getDeliverbill().getId())) {
				this.deliverbillVo.getDeliverbill().setId("");

				// SUC-447 –创建预派送单 SR6 预派送单号生成规则为：p+序列号（数据库生成，保证唯一）。如p00000001。
				// 生成P+9位编号
				this.deliverbillVo.getDeliverbill().setDeliverbillNo("P" + deliverbillNewService.querySequence());
			}
			//返回成功
			return super.returnSuccess();
		} catch (BusinessException e) {
			//记录日志
			LOGGER.error(e.getMessage(), e);
			//返回异常
			return super.returnError(e);
		}
	}
	
	
	/**
	 * 取消派送单.
	 *
	 * @return the string
	 * @author 
	 * @date 
	 */
	@JSON
	public String cancelDeliverbill() {
		try {
			MutexElement mutexElement = new MutexElement(deliverbillVo.getDeliverbillNewDto().getDeliverbillNo(), "派送单编号", MutexElementType.DELIVERBILL_NO);
			//互斥锁定
			boolean isLocked = businessLockService.lock(mutexElement, BEGIN_NUM);
			//若未上锁
			if(!isLocked){
				//抛出派送单异常
				throw new DeliverbillException(DeliverbillException.DELIVERBILL_LOCKED);
			}
			//若派送单状态为已保存
			if(DeliverbillConstants.STATUS_SAVED.equals(this.deliverbillVo.getDeliverbillNewDto().getStatus())||
					DeliverbillConstants.STATUS_SUBMITED.equals(this.deliverbillVo.getDeliverbillNewDto().getStatus()))
			{
				//取消已保存的派送单
				int result = this.deliverbillNewService.cancelDeliverbillForSaved(this.deliverbillVo.getDeliverbillNewDto().getId(),this.deliverbillVo.getDeliverbillNewDto().getDeliverbillNo());
				//解锁
				businessLockService.unlock(mutexElement);
				if (result > 0) {
					//返回成功
					return super.returnSuccess("取消派送单成功");
				} else {
					//返回异常
					return super.returnError("取消派送单失败");
				}
			}else
			{	
				//取消已保存的派送单
				int result = this.deliverbillNewService.cancelDeliverbill(this.deliverbillVo.getDeliverbillNewDto().getId(),this.deliverbillVo.getDeliverbillNewDto().getDeliverbillNo());

				if (result > 0) {
					//返回成功
					return super.returnSuccess("取消派送单成功");
				} else {
					//返回异常
					return super.returnError("取消派送单失败 - 中转入库失败");
				}
			}
		} catch (BusinessException e) {
			//记录日志
			LOGGER.error(e.getMessage(), e);
			//返回异常
			return super.returnError(e);
		}
	}
	
	
	
	/**
	 * 查询派送单信息.
	 *
	 * @return the string
	 * @author 
	 * @date 
	 * @since
	 * @version
	 */
	@JSON
	public String queryDeliverbill() {
		try {
			//根据派送单ID查找派送单信息
			this.deliverbillVo.setDeliverbill(this.deliverbillNewService.queryDeliverbill(this.deliverbillVo.getDeliverbillNewDto().getId()));
			//设置部门code
			String currentOrgCode = "";
			//获取组织信息
			OrgAdministrativeInfoEntity currentOrg = FossUserContext.getCurrentDept();
			//若组织信息不为空
			if (currentOrg != null) {
				//设置组织code
				currentOrgCode = currentOrg.getCode();
			}
			//车辆重量装载率阈值(下限)
			ConfigurationParamsEntity weightLowerThresholdEntity = null;
			//车辆体积装载率阈值(下限)
			ConfigurationParamsEntity volumeLowerThresholdEntity = null;
			try {
				//从配置参数中读取车辆重量装载率阈值(下限)
				weightLowerThresholdEntity = this.configurationParamsService.queryConfigurationParamsByOrgCode(DictionaryConstants.SYSTEM_CONFIG_PARM__PKP,
								ConfigurationParamsConstants.PKP_PARM__DELIVER_WEIGHT_LOWER_THRESHOLD,currentOrgCode);
				//从配置参数中读取车辆体积装载率阈值(下限)
				volumeLowerThresholdEntity = this.configurationParamsService.queryConfigurationParamsByOrgCode(DictionaryConstants.SYSTEM_CONFIG_PARM__PKP,
						        ConfigurationParamsConstants.PKP_PARM__DELIVER_VOLUME_LOWER_THRESHOLD,currentOrgCode);
			} catch (BusinessException e) {
				//记录日志
				LOGGER.error(e.getMessage(), e);
			}
			//若重量装载率阈值不为空
			if (weightLowerThresholdEntity != null) {
				//设置重量装载率阈值
				this.deliverbillVo.setWeightLowerThreshold(Double.parseDouble(weightLowerThresholdEntity.getConfValue()));
			} else {
				//设置重量装载率阈值0.6
				this.deliverbillVo.setWeightLowerThreshold(DeliverbillConstants.DELIVER_WEIGHT_LOWER_THRESHOLD);
			}
			//若体积装载率阈值不为空
			if (volumeLowerThresholdEntity != null) {
				//设置体积装载率阈值
				this.deliverbillVo.setVolumeLowerThreshold(Double.parseDouble(volumeLowerThresholdEntity.getConfValue()));
			} else {
				//设置体积装载率阈值0.6
				this.deliverbillVo.setVolumeLowerThreshold(DeliverbillConstants.DELIVER_VOLUME_LOWER_THRESHOLD);
			}
			//返回成功
			return super.returnSuccess();
		} catch (BusinessException e) {
			//记录日志
			LOGGER.error(e.getMessage(), e);
			//返回异常
			return super.returnError(e);
		}
	}
	
	
	/**
	 * 查询派送单信息.
	 *
	 * @return the string
	 * @author 
	 * @date 
	 * @since
	 * @version
	 */
	@JSON
	public String queryDeliverbillForSale() {
		try {
			//根据派送单ID查找派送单信息
			this.deliverbillVo.setDeliverbill(this.deliverbillNewService.queryDeliverbill(this.deliverbillVo.getDeliverbillDto().getId()));
			//设置部门code
			String currentOrgCode = "";
			//获取组织信息
			OrgAdministrativeInfoEntity currentOrg = FossUserContext.getCurrentDept();
			//若组织信息不为空
			if (currentOrg != null) {
				//设置组织code
				currentOrgCode = currentOrg.getCode();
			}
			//车辆重量装载率阈值(下限)
			ConfigurationParamsEntity weightLowerThresholdEntity = null;
			//车辆体积装载率阈值(下限)
			ConfigurationParamsEntity volumeLowerThresholdEntity = null;
			try {
				//从配置参数中读取车辆重量装载率阈值(下限)
				weightLowerThresholdEntity = this.configurationParamsService.queryConfigurationParamsByOrgCode(DictionaryConstants.SYSTEM_CONFIG_PARM__PKP,
								ConfigurationParamsConstants.PKP_PARM__DELIVER_WEIGHT_LOWER_THRESHOLD,currentOrgCode);
				//从配置参数中读取车辆体积装载率阈值(下限)
				volumeLowerThresholdEntity = this.configurationParamsService.queryConfigurationParamsByOrgCode(DictionaryConstants.SYSTEM_CONFIG_PARM__PKP,
						        ConfigurationParamsConstants.PKP_PARM__DELIVER_VOLUME_LOWER_THRESHOLD,currentOrgCode);
			} catch (BusinessException e) {
				//记录日志
				LOGGER.error(e.getMessage(), e);
			}
			//若重量装载率阈值不为空
			if (weightLowerThresholdEntity != null) {
				//设置重量装载率阈值
				this.deliverbillVo.setWeightLowerThreshold(Double.parseDouble(weightLowerThresholdEntity.getConfValue()));
			} else {
				//设置重量装载率阈值0.6
				this.deliverbillVo.setWeightLowerThreshold(DeliverbillConstants.DELIVER_WEIGHT_LOWER_THRESHOLD);
			}
			//若体积装载率阈值不为空
			if (volumeLowerThresholdEntity != null) {
				//设置体积装载率阈值
				this.deliverbillVo.setVolumeLowerThreshold(Double.parseDouble(volumeLowerThresholdEntity.getConfValue()));
			} else {
				//设置体积装载率阈值0.6
				this.deliverbillVo.setVolumeLowerThreshold(DeliverbillConstants.DELIVER_VOLUME_LOWER_THRESHOLD);
			}
			//返回成功
			return super.returnSuccess();
		} catch (BusinessException e) {
			//记录日志
			LOGGER.error(e.getMessage(), e);
			//返回异常
			return super.returnError(e);
		}
	}
	
	
	/**
	 * 查询派送单信息.
	 *
	 * @return the string
	 * @author 
	 * @date 
	 * @since
	 * @version
	 */
	@JSON
	public String queryDeliverbillForArriveSheet() {
		try {
			//根据派送单ID查找派送单信息
			this.deliverbillVo.setDeliverbill(this.deliverbillNewService.queryDeliverbill(this.deliverbillVo.getDeliverbillNewDto().getId()));
			//设置部门code
			String currentOrgCode = "";
			//获取组织信息
			OrgAdministrativeInfoEntity currentOrg = FossUserContext.getCurrentDept();
			//若组织信息不为空
			if (currentOrg != null) {
				//设置组织code
				currentOrgCode = currentOrg.getCode();
			}
			//车辆重量装载率阈值(下限)
			ConfigurationParamsEntity weightLowerThresholdEntity = null;
			//车辆体积装载率阈值(下限)
			ConfigurationParamsEntity volumeLowerThresholdEntity = null;
			try {
				//从配置参数中读取车辆重量装载率阈值(下限)
				weightLowerThresholdEntity = this.configurationParamsService.queryConfigurationParamsByOrgCode(DictionaryConstants.SYSTEM_CONFIG_PARM__PKP,
								ConfigurationParamsConstants.PKP_PARM__DELIVER_WEIGHT_LOWER_THRESHOLD,currentOrgCode);
				//从配置参数中读取车辆体积装载率阈值(下限)
				volumeLowerThresholdEntity = this.configurationParamsService.queryConfigurationParamsByOrgCode(DictionaryConstants.SYSTEM_CONFIG_PARM__PKP,
						        ConfigurationParamsConstants.PKP_PARM__DELIVER_VOLUME_LOWER_THRESHOLD,currentOrgCode);
			} catch (BusinessException e) {
				//记录日志
				LOGGER.error(e.getMessage(), e);
			}
			//若重量装载率阈值不为空
			if (weightLowerThresholdEntity != null) {
				//设置重量装载率阈值
				this.deliverbillVo.setWeightLowerThreshold(Double.parseDouble(weightLowerThresholdEntity.getConfValue()));
			} else {
				//设置重量装载率阈值0.6
				this.deliverbillVo.setWeightLowerThreshold(DeliverbillConstants.DELIVER_WEIGHT_LOWER_THRESHOLD);
			}
			//若体积装载率阈值不为空
			if (volumeLowerThresholdEntity != null) {
				//设置体积装载率阈值
				this.deliverbillVo.setVolumeLowerThreshold(Double.parseDouble(volumeLowerThresholdEntity.getConfValue()));
			} else {
				//设置体积装载率阈值0.6
				this.deliverbillVo.setVolumeLowerThreshold(DeliverbillConstants.DELIVER_VOLUME_LOWER_THRESHOLD);
			}
			//返回成功
			return super.returnSuccess();
		} catch (BusinessException e) {
			//记录日志
			LOGGER.error(e.getMessage(), e);
			//返回异常
			return super.returnError(e);
		}
	}
	
	
	/**
	 * 查询派送单下的明细信息，分页显示.
	 *
	 * @return the string
	 * @author 
	 * @date 
	 */
	@JSON
	public String queryDeliverbillDetailList() {
		try {
			//根据派送单ID查找派送单明细条数
			this.totalCount = this.deliverbillNewService.queryDeliverbillDetailCountByDeliverbillId(this.deliverbillVo.getDeliverbillNewDto().getId());
			//若大于0
			if (this.totalCount != null && this.totalCount > 0) {
				//根据派送单ID查找派送单明细列表大小
				this.deliverbillVo.setDeliverbillDetailList(this.deliverbillNewService.queryDeliverbillDetailList(this.deliverbillVo.getDeliverbillNewDto().getId(), this.getStart(), this.getLimit()));
			} else {
				//设置为null
				this.deliverbillVo.setDeliverbillDetailList(null);
			}
			//返回成功
			return super.returnSuccess();
		} catch (BusinessException e) {
			//记录日志
			LOGGER.error(e.getMessage(), e);
			//返回异常
			return super.returnError(e);
		}
	}
	
	
	/**
	 * 查询派送单下已生成到达联的派送明细列表.
	 *
	 * @return the string
	 * @author
	 * @date 
	 */
	@JSON
	public String queryDeliverbillArrivesheetList() {
		try {
			//根据派送单ID查找已生成到达联的派送明细列表
			this.deliverbillVo.setDeliverbillDetailList(this.deliverbillNewService.queryDeliverbillArrivesheetList(this.deliverbillVo.getDeliverbillNewDto().getId(),this.deliverbillVo.getDeliverbillNewDto().getStatus()));
			//返回成功
			return super.returnSuccess();
		} catch (BusinessException e) {
			//记录日志
			LOGGER.error(e.getMessage(), e);
			//返回异常
			return super.returnError(e);
		}
	}
	
	/**
	 * 短信通知司机
	 *
	 * @return the string
	 * @author	deliverbillNewDto
	 * 		id 派送单id
	 * 		driverCode 司机Code
	 * @date 
	 */
	@JSON
	public String isSendSMSToDriver() {
		try {
			deliverbillNewService.isSendSMSToDriver(this.deliverbillVo.getDeliverbillNewDto().getId());
			return super.returnSuccess();
		} catch (BusinessException e) {
			//记录日志
			LOGGER.error(e.getMessage(), e);
			//返回异常
			return super.returnError(e);
		}
		
	}
	
	/**
	 * 短信通知客户
	 * @author 306548
	 */
	@JSON
	public String isSendSMSToConsumer() {
		try {
			deliverbillNewService.isSendSMSToConsumers(this.deliverbillVo.getDeliverbillDto().getId(),this.deliverbillVo.getLoadTaskDto().getTaskNo());
			return super.returnSuccess();
		} catch (BusinessException e) {
			//记录日志
			LOGGER.error(e.getMessage(), e);
			//返回异常
			return super.returnError(e);
		}
		
	}
	
	/**
	 * 更新派送单打印到达联状态
	 * @return
	 */
	@JSON
	public String updateIsArriveSheet() {
		try {
			DeliverbillEntity deliverbill=new DeliverbillEntity();
			deliverbill.setId(deliverbillVo.getDeliverbill().getId());
			deliverbill.setIsArriveSheet(DeliverbillConstants.IS_ARRIVE_SHEET_YES);
			this.deliverbillNewService.updateDeliverBill(deliverbill);
			return super.returnSuccess();
		} catch (BusinessException e) {
			//记录日志
			LOGGER.error(e.getMessage(), e);
			//返回异常
			return super.returnError(e);
		}
	}
	
	
	
	
	/**
	 * 根据车牌号找寻司机信息.
	 *
	 * @return the string
	 * @author 
	 * @date 
	 */
	@JSON
	public String queryVehiclePdaSigned() {
		try {
			/*
			 * 刷新司机信息 SR9 1. 若排班（和处理订单中排班是同一个排班）中存在该车牌号，则用户在选择司机时自动带出对应的司机 2.
			 * 若PDA绑定中存在该车牌号，则系统自动带出对应的司机且不可修改 3. 当排班和PDA绑定同时存在时，以PDA绑定为准
			 */
			this.deliverbillVo.setDriverDto(this.deliverbillNewService.queryDriverByVehicleNo(this.deliverbillVo.getDeliverbillNewDto().getVehicleNo()));
			//返回成功
			return super.returnSuccess();
		} catch (BusinessException e) {
			//记录日志
			LOGGER.error(e.getMessage(), e);
			//返回异常
			return super.returnError(e);
		}
	}
	
	
	/**
	 * 查询公司司机.
	 *
	 * @return the string
	 * @author 
	 * @date 
	 */
	@JSON
	public String queryDriver() {
		try {
			//根据查询条件(工号/姓名/电话号码)查询公司司机
			this.deliverbillVo.setDriverList(this.deliverbillNewService.queryDriverListByDriverDto(this.deliverbillVo.getDriverDto()));
			//返回成功
			return super.returnSuccess();
		} catch (BusinessException e) {
			//记录日志
			LOGGER.error(e.getMessage(), e);
			//返回异常
			return super.returnError(e);
		}
	}
	
	
	/**
	 * 分配派送任务.
	 *
	 * @return the string
	 * @author 
	 * @date 
	 */
	@JSON
	public String assignDriver() {
		try {
			//分配派送任务
			this.deliverbillNewService.assignDriver(this.deliverbillVo.getDeliverbill().getId(), this.deliverbillVo.getDriverDto());
			//返回成功
			return super.returnSuccess("分配派送任务成功");
		} catch (BusinessException e) {
			//记录日志
			LOGGER.error(e.getMessage(), e);
			//返回异常
			return super.returnError(e);
		}
	}
	
	
	
	
	
	
	

	public DeliverbillNewVo getDeliverbillVo() {
		return deliverbillVo;
	}

	public void setDeliverbillVo(DeliverbillNewVo deliverbillVo) {
		this.deliverbillVo = deliverbillVo;
	}

	public String getFleetCode() {
		return fleetCode;
	}

	public void setFleetCode(String fleetCode) {
		this.fleetCode = fleetCode;
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public String getProvName() {
		return provName;
	}

	public void setProvName(String provName) {
		this.provName = provName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public void setDeliverbillNewService(
			IDeliverbillNewService deliverbillNewService) {
		this.deliverbillNewService = deliverbillNewService;
	}

	public void setVehicleService(IVehicleService vehicleService) {
		this.vehicleService = vehicleService;
	}

	public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}

	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	public void setSalesMotorcadeService(
			ISalesMotorcadeService salesMotorcadeService) {
		this.salesMotorcadeService = salesMotorcadeService;
	}

	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	public void setAdministrativeRegionsService(
			IAdministrativeRegionsService administrativeRegionsService) {
		this.administrativeRegionsService = administrativeRegionsService;
	}

	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}
	
	

	
}