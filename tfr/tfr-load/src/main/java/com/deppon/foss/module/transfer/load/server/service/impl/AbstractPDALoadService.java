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
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-load
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/server/action/DeliverLoadGapReportAction.java
 *  
 *  FILE NAME          :DeliverLoadGapReportAction.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.load.server.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.deppon.foss.base.util.define.MessageConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IFreightRouteService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IGoodsAreaService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IHeavyBubbleRatioService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILdpAgencyCompanyService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILdpAgencyDeptService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILoadAndUnloadSquadService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IPlatformService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IPorterService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IVehicleAgencyDeptService;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonTransferCenterService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IVehicleService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.GoodsAreaEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchExpressEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.FreightRouteLineDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.VehicleAssociationDto;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.server.service.IMessageService;
import com.deppon.foss.module.base.common.api.server.service.ISMSTempleteService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.domain.InstationJobMsgEntity;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.base.common.api.shared.dto.SmsParamDto;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.server.service.IDataDictionaryValueService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IDeliverBillVaryStatusService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IDeliverbillService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IVisibleOrderService;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.DeliverbillConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.DeliverBillVaryStatusEntity;
import com.deppon.foss.module.pickup.pricing.dubbo.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.dubbo.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.waybill.api.server.service.IActualFreightService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.transfer.common.api.server.service.IFOSSToOAService;
import com.deppon.foss.module.transfer.common.api.server.service.IFOSSToWkService;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrCommonService;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrJobTodoService;
import com.deppon.foss.module.transfer.common.api.shared.define.BusinessGoalContants;
import com.deppon.foss.module.transfer.common.api.shared.define.BusinessSceneConstants;
import com.deppon.foss.module.transfer.common.api.shared.domain.FossToWKResponseEntity;
import com.deppon.foss.module.transfer.common.api.shared.dto.OaReportClearMore;
import com.deppon.foss.module.transfer.common.api.shared.dto.WKLoadTempDto;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.common.server.utils.JSONUtils;
import com.deppon.foss.module.transfer.load.api.server.dao.IAssignLoadTaskDao;
import com.deppon.foss.module.transfer.load.api.server.dao.IAutoGenerateHandOverBillDao;
import com.deppon.foss.module.transfer.load.api.server.dao.IExpressPackageDao;
import com.deppon.foss.module.transfer.load.api.server.dao.IPDAExpressDeliverLoadDao;
import com.deppon.foss.module.transfer.load.api.server.dao.IPDALoadDao;
import com.deppon.foss.module.transfer.load.api.server.dao.IWKLoadTempDao;
import com.deppon.foss.module.transfer.load.api.server.service.ILoadService;
import com.deppon.foss.module.transfer.load.api.server.service.IPDACommonService;
import com.deppon.foss.module.transfer.load.api.server.service.IPDALoadService;
import com.deppon.foss.module.transfer.load.api.server.service.ITrayScanService;
import com.deppon.foss.module.transfer.load.api.server.service.ITruckTaskService;
import com.deppon.foss.module.transfer.load.api.server.service.IVehicleSealService;
import com.deppon.foss.module.transfer.load.api.server.service.IWKTfrBillService;
import com.deppon.foss.module.transfer.load.api.shared.define.LoadConstants;
import com.deppon.foss.module.transfer.load.api.shared.define.LoadSourceEnum;
import com.deppon.foss.module.transfer.load.api.shared.define.TaskTruckConstant;
import com.deppon.foss.module.transfer.load.api.shared.domain.DeliverBillEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.DeliverLoadGapReportWayBillEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.ExpressPackageDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.ExpressPackageEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoadSerialNoEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoadTaskEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoadWaybillDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoaderParticipationEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.WKBillEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.GoodsStateDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.LoadGoodsDetailSerialDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.LoadTaskSerialNoDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.VehicleSealInfoDto;
import com.deppon.foss.module.transfer.load.server.dao.impl.PDALoadDao;
import com.deppon.foss.module.transfer.load.server.dao.impl.WKLoadTempDao;
import com.deppon.foss.module.transfer.pda.api.shared.define.TransferPDADictConstants;
import com.deppon.foss.module.transfer.pda.api.shared.dto.FinshWKLoadTaskDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.LoadGoodsDetailDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.LoadSaleGoodsDetailDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.LoadSaleTaskDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.LoadSaleTaskResultDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.LoadScanDetailDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.LoadTaskDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.LoadTaskResultDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.LoaderDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.PDAGoodsSerialNoDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.PackagePathLoaderDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.SmtWKLoadTaskDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.WKLoadTaskDto;
import com.deppon.foss.module.transfer.pda.api.shared.exception.TransferPDAExceptionCode;
import com.deppon.foss.module.transfer.scheduling.api.define.TransportPathConstants;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ICalculateTransportPathService;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.FeedbackDto;
import com.deppon.foss.module.transfer.stock.api.define.StockConstants;
import com.deppon.foss.module.transfer.stock.api.server.service.IStockService;
import com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.StockEntity;
import com.deppon.foss.module.transfer.stock.api.shared.dto.WaybillStockQueryDto;
import com.deppon.foss.module.transfer.unload.api.server.dao.IPDAUnloadTaskDao;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

import javax.annotation.Resource;

/**
 * PDA装车公用父类
 * 
 * 
 * @author dp-duyi
 * 
 * 
 * 
 * 
 *
 * @date 2012-12-15 上午10:36:57
 */
public class AbstractPDALoadService implements IPDALoadService {
	/**
	 * 日志
	 * 
	 * 
	 */ 
	static final Logger LOGGER = LoggerFactory.getLogger(AbstractPDALoadService.class);
	public IEmployeeService employeeService;
	public IPDALoadService pdaLoadService;
	public IGoodsAreaService goodsAreaService;
	public IPDALoadDao pdaLoadDao;
	public IAssignLoadTaskDao assignLoadTaskDao;
	public IPDAExpressDeliverLoadDao pdaExpressDeliverLoadDao;
	public IFreightRouteService freightRouteService;
	public IOrgAdministrativeInfoService orgAdministrativeInfoService;
	public IStockService stockService;
	public ICalculateTransportPathService calculateTransportPathService;
	public IWaybillManagerService waybillManagerService;
	public IFOSSToOAService fossToOAService;
	public IDataDictionaryValueService dataDictionaryValueService;

	@Resource
	public IProductService productService4Dubbo;

	public IWaybillRfcService waybillRfcService;
	public IPDACommonService pdaCommonService;
	public IMessageService messageService;
	public IDeliverbillService deliverbillService;
	public IConfigurationParamsService configurationParamsService;
	public IBusinessLockService businessLockService;
	public IVehicleSealService vehicleSealService;
	public ILoadService loadService;
	public IVehicleAgencyDeptService vehicleAgencyDeptService;
	public ISMSTempleteService sMSTempleteService;
	// 派送单状态更新记录表Service
	private IDeliverBillVaryStatusService deliverBillVaryStatusService;
	/** 子类公用service */
	public IPlatformService platformService;
	public ITfrCommonService tfrCommonService;
	public IPorterService porterService;
	public ILoadAndUnloadSquadService loadAndUnloadSquadService;
	public IPDAUnloadTaskDao pdaUnloadTaskDao;
	public ISaleDepartmentService saleDepartmentService;
	public IVehicleService vehicleService;
	public IAutoGenerateHandOverBillDao autoGenerateHandOverBillDao;
	public ITruckTaskService truckTaskService;
	public ICommonTransferCenterService commonTransferCenterService;
	public ITrayScanService trayScanService;
	/** 快递：落地配装车 */
	public ILdpAgencyDeptService ldpAgencyDeptService;
	public ILdpAgencyCompanyService ldpAgencyCompanyService;
	/** 包 zwd 200968 2015.4.7 */
	/** PDA运单退回 */
	public IVisibleOrderService visibleOrderService;

	public void setVisibleOrderService(IVisibleOrderService visibleOrderService) {
		this.visibleOrderService = visibleOrderService;
	}
	
	private ITfrJobTodoService tfrJobTodoService;
	public void setTfrJobTodoService(ITfrJobTodoService tfrJobTodoService) {
		this.tfrJobTodoService = tfrJobTodoService;
	}
	
	private IWKLoadTempDao wkLoadTempDao;
	public void setWkLoadTempDao(WKLoadTempDao wkLoadTempDao) {
		this.wkLoadTempDao = wkLoadTempDao;
	}
	
	//第二套改同步
	private IFOSSToWkService fossToWkService;
	public void setFossToWkService(IFOSSToWkService fossToWkService) {
		this.fossToWkService = fossToWkService;
	}
	private IWKTfrBillService wKTfrBillService;
	public void setwKTfrBillService(IWKTfrBillService wKTfrBillService) {
		this.wKTfrBillService = wKTfrBillService;
	}

	/**
	 * 重泡比 zwd
	 */
	private IHeavyBubbleRatioService heavyBubbleRatioService;

	/**
	 * 运单状态数据持久层
	 */
	private IActualFreightService actualFreightService;

	/**
	 * 重泡比 zwd
	 */
	public void setHeavyBubbleRatioService(IHeavyBubbleRatioService heavyBubbleRatioService) {
		this.heavyBubbleRatioService = heavyBubbleRatioService;
	}

	/**
	 * expressPackageDao
	 */
	private IExpressPackageDao expressPackageDao;

	public void setCommonTransferCenterService(ICommonTransferCenterService commonTransferCenterService) {
		this.commonTransferCenterService = commonTransferCenterService;
	}

	public void setLdpAgencyDeptService(ILdpAgencyDeptService ldpAgencyDeptService) {
		this.ldpAgencyDeptService = ldpAgencyDeptService;
	}

	public void setLdpAgencyCompanyService(ILdpAgencyCompanyService ldpAgencyCompanyService) {
		this.ldpAgencyCompanyService = ldpAgencyCompanyService;
	}

	public void setTrayScanService(ITrayScanService trayScanService) {
		this.trayScanService = trayScanService;
	}

	public void setPlatformService(IPlatformService platformService) {
		this.platformService = platformService;
	}

	public void setTfrCommonService(ITfrCommonService tfrCommonService) {
		this.tfrCommonService = tfrCommonService;
	}

	public void setPorterService(IPorterService porterService) {
		this.porterService = porterService;
	}

	public void setPdaUnloadTaskDao(IPDAUnloadTaskDao pdaUnloadTaskDao) {
		this.pdaUnloadTaskDao = pdaUnloadTaskDao;
	}

	public void setSaleDepartmentService(ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}

	public void setVehicleService(IVehicleService vehicleService) {
		this.vehicleService = vehicleService;
	}

	/**
	 * Sets the employee service.
	 *
	 * @param employeeService
	 *            the new employee service
	 */
	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	public void setPdaLoadService(IPDALoadService pdaLoadService) {
		this.pdaLoadService = pdaLoadService;
	}

	public void setGoodsAreaService(IGoodsAreaService goodsAreaService) {
		this.goodsAreaService = goodsAreaService;
	}

	public void setTruckTaskService(ITruckTaskService truckTaskService) {
		this.truckTaskService = truckTaskService;
	}

	/**
	 * 装车dao接口
	 * 
	 * 
	 */
	/**
	 * PDA装车dao
	 * 
	 *
	 * @param pdaLoadDao
	 */
	public void setPdaLoadDao(PDALoadDao pdaLoadDao) {
		this.pdaLoadDao = pdaLoadDao;
	}

	/**
	 * 分配装车任务dao接口
	 * 
	 * 
	 */
	/**
	 * 分配派送装车任务dao
	 * 
	 *
	 * @param assignLoadTaskDao
	 */
	public void setAssignLoadTaskDao(IAssignLoadTaskDao assignLoadTaskDao) {
		this.assignLoadTaskDao = assignLoadTaskDao;
	}

	public void setPdaExpressDeliverLoadDao(IPDAExpressDeliverLoadDao pdaExpressDeliverLoadDao) {
		this.pdaExpressDeliverLoadDao = pdaExpressDeliverLoadDao;
	}

	/**
	 * 组织信息 Service接口
	 * 
	 * 
	 */
	/**
	 * 设置组织信息
	 *
	 * @param orgAdministrativeInfoService
	 */
	public void setOrgAdministrativeInfoService(IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	/**
	 * 库存接口
	 * 
	 */
	/**
	 * 设置库存服务
	 *
	 * @param stockService
	 */
	public void setStockService(IStockService stockService) {
		this.stockService = stockService;
	}

	/**
	 * 走货路径 service
	 */
	/**
	 * Sets the 走货路径 service.
	 *
	 * @param calculateTransportPathService
	 *            the new 走货路径 service
	 */
	public void setCalculateTransportPathService(ICalculateTransportPathService calculateTransportPathService) {
		this.calculateTransportPathService = calculateTransportPathService;
	}

	/**
	 * 运单管理接口
	 * 
	 */
	/**
	 * 
	 *
	 * @param waybillManagerService
	 */
	public void setWaybillManagerService(IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}

	/**
	 * foss上报差错信息给oa接口
	 * 
	 */
	/**
	 * 
	 *
	 * @param fossToOAService
	 */
	public void setFossToOAService(IFOSSToOAService fossToOAService) {
		this.fossToOAService = fossToOAService;
	}

	/**
	 * 数据字典接口
	 */

	/**
	 * 
	 *
	 * @param dataDictionaryValueService
	 */
	public void setDataDictionaryValueService(IDataDictionaryValueService dataDictionaryValueService) {
		this.dataDictionaryValueService = dataDictionaryValueService;
	}

	/**
	 * 产品接口
	 */

	/**
	 * 
	 *
	 * @param productService
	 */
//	public void setProductService(IProductService productService) {
//		this.productService = productService;
//	}

	/**
	 * 更改单服务接口
	 * 
	 */
	/**
	 * 
	 *
	 * @param waybillRfcService
	 */
	public void setWaybillRfcService(IWaybillRfcService waybillRfcService) {
		this.waybillRfcService = waybillRfcService;
	}

	/**
	 * 装卸车PDA共通service接口
	 * 
	 */
	/**
	 * 
	 *
	 * @param pdaCommonService
	 */
	public void setPdaCommonService(IPDACommonService pdaCommonService) {
		this.pdaCommonService = pdaCommonService;
	}

	/**
	 * 站内消息
	 * 
	 * 
	 */
	/**
	 * Sets the 站内消息.
	 *
	 * @param messageService
	 *            the new 站内消息
	 */
	public void setMessageService(IMessageService messageService) {
		this.messageService = messageService;
	}

	/**
	 * 
	 */
	/**
	 * 
	 *
	 * @param deliverbillservice
	 */
	public void setDeliverbillService(IDeliverbillService deliverbillService) {
		this.deliverbillService = deliverbillService;
	}

	/**
	 * 业务锁服务
	 * 
	 * 
	 */
	/**
	 * 业务锁
	 * 
	 *
	 * @param businessLockService
	 */
	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}

	public void setVehicleSealService(IVehicleSealService vehicleSealService) {
		this.vehicleSealService = vehicleSealService;
	}

	public void setsMSTempleteService(ISMSTempleteService sMSTempleteService) {
		this.sMSTempleteService = sMSTempleteService;
	}

	/**
	 * 配置参数service
	 */
	public void setConfigurationParamsService(IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}

	public void setVehicleAgencyDeptService(IVehicleAgencyDeptService vehicleAgencyDeptService) {
		this.vehicleAgencyDeptService = vehicleAgencyDeptService;
	}

	public void setLoadService(ILoadService loadService) {
		this.loadService = loadService;
	}

	/**
	 * 综合生成走货路径service
	 */

	public void setFreightRouteService(IFreightRouteService freightRouteService) {
		this.freightRouteService = freightRouteService;
	}

	public void setLoadAndUnloadSquadService(ILoadAndUnloadSquadService loadAndUnloadSquadService) {
		this.loadAndUnloadSquadService = loadAndUnloadSquadService;
	}

	public void setAutoGenerateHandOverBillDao(IAutoGenerateHandOverBillDao autoGenerateHandOverBillDao) {
		this.autoGenerateHandOverBillDao = autoGenerateHandOverBillDao;
	}

	public final void setExpressPackageDao(IExpressPackageDao expressPackageDao) {
		this.expressPackageDao = expressPackageDao;
	}

	public void setActualFreightService(IActualFreightService actualFreightService) {
		this.actualFreightService = actualFreightService;
	}

	/**
	 * 非建立任务PDA提交任务
	 * 
	 * 
	 * @author dp-duyi
	 * @date 2012-11-20 上午8:41:50
	 * @see com.deppon.foss.module.transfer.pda.api.server.service.IPDATransferLoadService#submitLoadTask(java.lang.String,
	 *      java.util.Date)
	 */
	@Override
	@Transactional
	public String finishLoadTask(String taskNo, Date loadEndTime, String deviceNo, String loaderCode) {
		LoadTaskEntity loadTask = pdaLoadDao.queryLoadTaskByTaskNo(taskNo);
		// 任务为进行中才能提交
		if (loadTask != null && LoadConstants.LOAD_TASK_STATE_LOADING.equals(loadTask.getState())) {
			// 更新pdaTask:leaveTime
			Map<String, Object> parameter = new HashMap<String, Object>();
			// 任务号
			parameter.put("taskNo", taskNo);
			// 设备号
			parameter.put("deviceNo", deviceNo);
			// 离开时间
			parameter.put("leaveTime", loadEndTime);
			// 更新PDA任务
			pdaLoadDao.updatePDATaskEntity(parameter);
			// 返回成功
			return TransferPDADictConstants.SUCCESS;
		} else {
			// 无效任务
			throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_INVALID_TASK_MESSAGECODE);
		}
	}
	/**
	 * 强制完成任务
	 * 
	 * @param taskNo
	 * @param loadEndTime
	 * @param deviceNo
	 * @param loaderCode
	 * @author dp-duyi
	 * @date 2012-12-6 下午7:35:37
	 * @see com.deppon.foss.module.transfer.pda.api.server.service.IPDATransferLoadService#forceFinishLoadTask(java.lang.String,
	 *      java.util.Date, java.lang.String)
	 */
	@Override
	@Transactional
	public String forceSubmitLoadTask(String taskNo, Date loadEndTime, String deviceNo, String loaderCode) { LoadTaskEntity loadTask = null;
		MutexElement mutexElement = null;
		try {
			// 为了查询派送单
			loadTask = pdaLoadDao.queryLoadTaskByTaskNo(taskNo);
		} catch (CannotAcquireLockException e) {
			throw new TfrBusinessException("任务提交中请稍后再试");
		}
		// 完成装车任务
		if (loadTask != null && LoadConstants.LOAD_TASK_TYPE_DELIVER.equals(loadTask.getTaskType())) {
			mutexElement = new MutexElement(loadTask.getDeliverBillNo(), "派送单编号", MutexElementType.DELIVERBILL_NO);
			// 互斥锁定
			boolean isLocked = businessLockService.lock(mutexElement, 0);
			// 若未上锁
			if (!isLocked) {
				// 抛出派送单异常
				throw new TfrBusinessException("派送单修改中请稍后再试");
			}

		}
		try {
			pdaLoadService.finishLoadAndSoOn(loadTask, loadEndTime, "", loaderCode);
			if (mutexElement != null) {
				businessLockService.unlock(mutexElement);
			}
		} catch (Exception e) {
			if (mutexElement != null) {
				businessLockService.unlock(mutexElement);
			}
			throw new TfrBusinessException(e.getMessage());
		}
		return TransferPDADictConstants.SUCCESS;
	}
	
	/**
	 * 强制完成任务
	 * 
	 * @param taskNo
	 * @param loadEndTime
	 * @param deviceNo
	 * @param loaderCode
	 * @author foss-332209 ruilibao
	 * @date 2016-09-21
	 */
	@Override
	@Transactional
	public String forceSubmitLoadTask(SmtWKLoadTaskDto dto){
		LoadTaskEntity loadTask = null;
		MutexElement mutexElement = null;
		try {
			// 为了查询派送单
			loadTask = pdaLoadDao.queryLoadTaskByTaskNo(dto.getLoadTaskNo());
		} catch (CannotAcquireLockException e) {
			throw new TfrBusinessException("任务提交中请稍后再试");
		}
		// 完成装车任务
		if (loadTask != null && LoadConstants.LOAD_TASK_TYPE_DELIVER.equals(loadTask.getTaskType())) {
			mutexElement = new MutexElement(loadTask.getDeliverBillNo(), "派送单编号", MutexElementType.DELIVERBILL_NO);
			// 互斥锁定
			boolean isLocked = businessLockService.lock(mutexElement, 0);
			// 若未上锁
			if (!isLocked) {
				// 抛出派送单异常
				throw new TfrBusinessException("派送单修改中请稍后再试");
			}

		}
		try {
			//生成FOSS交接单
			pdaLoadService.finishLoadAndSoOn(loadTask, dto.getLoadEndTime(), "", dto.getLoaderCode());
			
			//生成悟空交接单
			sendToWKAndGetHandover(dto, loadTask);
			
			if (mutexElement != null) {
				businessLockService.unlock(mutexElement);
			}
		} catch (Exception e) {
			if (mutexElement != null) {
				businessLockService.unlock(mutexElement);
			}
			throw new TfrBusinessException(e.getMessage());
		}
		return TransferPDADictConstants.SUCCESS;
		
	}

	/**
	 * 建立任务PDA完成任务
	 * 
	 * 1、修改理货员离开时间
	 * 
	 * 2、修改PDA离开时间
	 * 
	 * 3、修改任务状态、完成时间
	 * 
	 * 4、如果不为派送装车，则更新月台状态
	 * 
	 * @param taskNo
	 * @param loadEndTime
	 * @param deviceNo
	 * @param loaderCode
	 * @author dp-duyi
	 * @date 2012-12-5 下午5:52:59
	 * @see com.deppon.foss.module.transfer.pda.api.server.service.IPDATransferLoadService#finishLoadTask(java.lang.String,
	 *      java.util.Date, java.lang.String)
	 */
	@Override
	@Transactional
	public String submitLoadTask(String taskNo, Date loadEndTime, String deviceNo, String loaderCode) {
		if (pdaLoadDao.queryUnSubmitPDAQty(taskNo) > 1) {
			// 如果有PDA未提交任务，则失败
			throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_EXTIST_UNFINISH_PDA_MESSAGECODE);
		} else {
			LoadTaskEntity loadTask = null;
			MutexElement mutexElement = null;
			try {
				// 查询派送单号
				loadTask = pdaLoadDao.queryLoadTaskByTaskNo(taskNo);
			} catch (CannotAcquireLockException e) {
				throw new TfrBusinessException("任务提交中请稍后再试");
			}
			if (loadTask != null && LoadConstants.LOAD_TASK_TYPE_DELIVER.equals(loadTask.getTaskType())) {
				mutexElement = new MutexElement(loadTask.getDeliverBillNo(), "派送单编号", MutexElementType.DELIVERBILL_NO);
				// 互斥锁定
				boolean isLocked = businessLockService.lock(mutexElement, 0);
				// 若未上锁
				if (!isLocked) {
					// 抛出派送单异常
					throw new TfrBusinessException("派送单修改中请稍后再试");
				}

			}
			// 如果装车对象为空，直接返回正确的值
			if (loadTask == null) {
				// 成功
				return TransferPDADictConstants.SUCCESS;
			}
			try {
				// 完成装车任务
				pdaLoadService.finishLoadAndSoOn(loadTask, loadEndTime, deviceNo, loaderCode);
				if (mutexElement != null) {
					businessLockService.unlock(mutexElement);
				}
			} catch (Exception e) {
				if (mutexElement != null) {
					businessLockService.unlock(mutexElement);
				}
				throw new TfrBusinessException(e.getMessage());
			}
			// 成功
			return TransferPDADictConstants.SUCCESS;
		}
	}

	/**
	 * 完成装车任务
	 * 
	 *
	 *
	 * @param taskNo
	 * 
	 * 
	 * @param loadEndTime
	 * @param deviceNo
	 * 
	 * @param loaderCode
	 * 
	 * @return
	 */

	@Transactional
	public String finishLoadAndSoOn(LoadTaskEntity loadTask, Date loadEndTime, String deviceNo, String loaderCode) {
		LOGGER.error("提交装车任务开始" + loadTask.getTaskNo());
		// 查询装车任务
		// 只有装车任务状态为装车中的任务可以提交
		if (loadTask != null && LoadConstants.LOAD_TASK_STATE_LOADING.equals(loadTask.getState())) {
			// 锁住装车记录，只是很单纯的锁
			loadTask = pdaLoadDao.queryLoadTaskByTaskNoForUpdate(loadTask.getTaskNo());
			//营业部装车和派送装车不校验
			if (!LoadConstants.LOAD_TASK_TYPE_DELIVER.equals(loadTask.getTaskType()) &&! TransferPDADictConstants.LOAD_TASK_TYPE_SALES_DISTANCE.equals(loadTask.getTaskType())) {
				VehicleSealInfoDto seal = vehicleSealService.queryVehicleSealInfo(loadTask.getVehicleNo());
				if (seal != null) {
					throw new TfrBusinessException(
							TransferPDAExceptionCode.EXCEPTION_VEHICLE_SEALED + "封签部门: " + seal.getSealdOrgName());
				}
				// 如果车辆已使用未出发（非本部门），则不能建立装车任务，该方法会自己拋异常的，所以不用在这里抛
				loadService.queryUndepartRecByVehicleNo(loadTask.getOrigOrgCode(), loadTask.getVehicleNo(),
						TaskTruckConstant.TASK_TRUCK_STATE_UNDEPART);

			}
			// 如果车辆已封签，则不能提交装车任务
			EmployeeEntity emp = employeeService.queryEmployeeByEmpCode(loaderCode);
			String loaderName = "装车多货";
			if (emp != null) {
				loaderName = emp.getEmpName();
			}
			// 格式化日期
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			// Sets the 装车结束时间
			loadTask.setLoadEndTime(df.format(new Date()));
			// 状态FINISHED
			loadTask.setState(LoadConstants.LOAD_TASK_STATE_FINISHEN);
			Date loadStartTime = new Date();
			try {
				// 转日期格式
				loadStartTime = df.parse(loadTask.getLoadStartTime());
			} catch (ParseException e1) {
				// 异常 记录日志
				LOGGER.error("装车开始时间转化失败", e1);
			}
			// 修改装车任务
			pdaLoadDao.updateLoadTask(loadTask);
			// 修改装车任务中理货员
			Map<String, Object> param = new HashMap<String, Object>();
			// 任务id
			param.put("id", loadTask.getId());
			// 结束时间,结束的时间用pda传过来的时间，2013-7-26--liubinbin
			param.put("endTime", loadEndTime == null ? new Date() : loadEndTime);
			// 提交装车任务时更新理货员
			pdaLoadDao.updateLoaderParticipationByLoadTask(param);
			// 不为营业部装车，则更新月台状态
			if (!LoadConstants.LOAD_TASK_TYPE_DELIVER.equals(loadTask.getTaskType()) || TransferPDADictConstants.LOAD_TASK_TYPE_SALES_DISTANCE.equals(loadTask.getTaskType())) {
			} else {
				//8月9号版本这段代码被覆盖，现重新提交begin
				//326027 派送装车，添加派送单到JOB_TODO
				LOGGER.error("派件装车扫描"+loadTask.getTaskNo()+"推送货物轨迹，插入未执行job开始： ");
				try{
					tfrJobTodoService.addJobTodo(loadTask.getTaskNo(),
						BusinessSceneConstants.BUSINESS_SCENE_SENT_SCAN,
						new String[]{BusinessGoalContants.BUSINESS_GOAL_TO_TAOBAOJZ,
									 BusinessGoalContants.BUSINESS_GOAL_TO_TAOBAO,
									 BusinessGoalContants.BUSINESS_GOAL_TO_JIAWAYUN
				                    }, 
				        new Date(), 
				        loaderCode);  
				}catch(TfrBusinessException e){
					throw  e;
				}catch(Exception e){
					LOGGER.error(e.getMessage());
					throw new TfrBusinessException(e.toString());
				}
				LOGGER.error("派件装车扫描"+loadTask.getTaskNo()+"推送货物轨迹，插入未执行job结束 ");
				//8月9号版本这段代码被覆盖，现重新提交end

				// 更新派送单状态为已装车
				DeliverBillEntity bill = new DeliverBillEntity();
				bill.setBillNo(loadTask.getDeliverBillNo());
				bill.setState(DeliverbillConstants.STATUS_LOADED);
				assignLoadTaskDao.updateDeliverBillState(bill, null);
				// 如果派送单号和派送单状态不为空，添加-派送单状态更新记录
				if (StringUtils.isNotBlank(bill.getBillNo()) && StringUtils.isNotBlank(bill.getState())) {
					DeliverBillVaryStatusEntity deliverBillVary = new DeliverBillVaryStatusEntity();
					deliverBillVary.setDeliverBillNo(bill.getBillNo());// 派送单号
					deliverBillVary.setDeliverBillStatus(bill.getState());// 派送单状态
					deliverBillVaryStatusService.insertDBVaryStatus(deliverBillVary);
				}
			}
			// 修改PDA:leaveTime
			Map<String, Object> parameter = new HashMap<String, Object>();
			// 任务号
			parameter.put("taskNo", loadTask.getTaskNo());
			// 设备号
			parameter.put("deviceNo", deviceNo);
			// 离开时间
			parameter.put("leaveTime", new Date());
			pdaLoadDao.updatePDATaskEntity(parameter);

			// 处理多货2013-05-30修改，所有的装车都要处理多货
			pdaLoadService.moreGoodsHandler(loadTask.getOrigOrgCode(), loadTask.getId(), loaderCode, loaderName);

			// 处理派送装车扫描记录
			if (LoadConstants.LOAD_TASK_TYPE_DELIVER.equals(loadTask.getTaskType())) {
				// 入库已删除货物
				pdaLoadService.inStockCanceledDeliverScan(loadTask.getOrigOrgCode(), loadTask.getId(), loaderCode,
						loaderName);
				// 生成派送装车少货记录
				this.deliverLoadCreateLackGoods(loadTask.getTaskNo(), loadTask.getOrigOrgCode(), loadTask.getId(),
						loadStartTime);
			} /* else{ */
			// 处理多货2013-05-30修改，所有的装车都要处理多货
			// pdaLoadService.moreGoodsHandler(loadTask.getOrigOrgCode(),
			// loadTask.getId(),loaderCode,loaderName);
			/* } */
			// 出库装车扫描流水号
			LOGGER.error("装车出库开始" + loadTask.getTaskNo());
			this.outStockLoadGoods(loadTask.getId(), loaderCode);
			LOGGER.error("装车出库结束" + loadTask.getTaskNo());
			if (LoadConstants.LOAD_TASK_TYPE_DELIVER.equals(loadTask.getTaskType())) {
				// 通知排单员
				try {
					pdaLoadService.noticeDeliverOrg(loadTask, loaderCode, loaderName);
				} catch (Exception e) {

				}
			} else {
				// OA上报多货差错，派送装车不会有多货的情况
				try {
					pdaLoadService.oaReportMoreGoods(loadTask.getId(), loaderCode, loadTask.getOrigOrgCode());
				} catch (Exception e) {
				}
				if (StringUtils.isNotBlank(loadTask.getPlatformId())) {
					// 调用月台接口修改月台状态
					try {
						pdaCommonService.updatePlatformStateByFinishTask(loadTask.getTaskNo(), new Date());
					} catch (Exception e) {

					}
				}
				// 生成交接单
				// 276198-duhao-20151212-增加交接单号参数
				autoGenerateHandOverBillDao.createHandOverBillByTaskNo(loadTask.getTaskNo());
				List<String> handOverBillNos = pdaLoadDao.queryHandOverBillNoByTaskNo(loadTask.getTaskNo());
				// 生成任务车辆
				if (CollectionUtils.isNotEmpty(handOverBillNos)) {
					for (String handOverBillNo : handOverBillNos) {
						truckTaskService.createTruckTaskByHandOverBill(handOverBillNo);
					}
				}

				List<String> packageNoList = pdaLoadDao.queryLoadPackageNoById(loadTask.getId());
				if (CollectionUtils.isNotEmpty(packageNoList)) {
					for (String packageNo : packageNoList) {
						ExpressPackageEntity expressPackageEntity = expressPackageDao
								.queryExpressPackageByPackageNo(packageNo);
						if (expressPackageEntity.getExpressPackageType().equals("THROUGH_ARRIVE")) {
							@SuppressWarnings("rawtypes")
							Map paramsMap = pdaLoadDao.autoCreatePackHandoverbill(loadTask.getTaskNo(), packageNo,
									packageNo.length(), "Y");
							String handoverNo = (String) paramsMap.get("handoverNo");
							if (StringUtils.isNotBlank(handoverNo)) {
								// 生成任务车辆
								truckTaskService.createTruckTaskByHandOverBill(handoverNo);
							}
						} else if (expressPackageEntity.getExpressPackageType().equals("AIRTHROUGH_ARRIVE")) {
							@SuppressWarnings("rawtypes")
							Map paramsMap = pdaLoadDao.autoCreatePackHandoverbill(loadTask.getTaskNo(), packageNo,
									packageNo.length(), "KY");
							String handoverNo3 = (String) paramsMap.get("handoverNo");
							if (StringUtils.isNotBlank(handoverNo3)) {
								// 生成任务车辆
								truckTaskService.createTruckTaskByHandOverBill(handoverNo3);
							}
						} else {
							@SuppressWarnings("rawtypes")
							Map paramsMap = pdaLoadDao.autoCreatePackHandoverbill(loadTask.getTaskNo(), packageNo,
									packageNo.length(), "N");
							String handoverNo2 = (String) paramsMap.get("handoverNo");
							if (StringUtils.isNotBlank(handoverNo2)) {
								// 生成任务车辆
								truckTaskService.createTruckTaskByHandOverBill(handoverNo2);
							}
						}
					}
				}

			}
			// 返回成功
			LOGGER.error("提交装车任务结束" + loadTask.getTaskNo());
			return TransferPDADictConstants.SUCCESS;
		} else {
			// 抛出异常
			LOGGER.error(loadTask.getTaskNo(), TransferPDAExceptionCode.EXCEPTION_INVALID_TASK_MESSAGECODE);
			LOGGER.error("提交装车任务结束" + loadTask.getTaskNo());
			return TransferPDADictConstants.SUCCESS;
		}
	}

	// 入库多货-夹带、多货-异地夹带
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void moreGoodsHandler(String origOrgCode, String taskId, String loaderCode, String loaderName) {
		List<String> goodsStates = new ArrayList<String>();
		// 查询多货
		goodsStates.add(TransferPDADictConstants.LOAD_GOODS_STATE_MORE_ENTRAINED);
		goodsStates.add(TransferPDADictConstants.LOAD_GOODS_STATE_MORE_ALLOPATRY_ENTRAINED);
		// 强装配置参数
		boolean beVerifyExtraLoad = false;
		ConfigurationParamsEntity defaultBizHourSlice = configurationParamsService.queryConfigurationParamsByOrgCode(
				DictionaryConstants.SYSTEM_CONFIG_PARM__TFR,
				ConfigurationParamsConstants.TFR_PARM_HANDOVER_IGNORE_TRANSPORT_PATH_PDA, FossConstants.ROOT_ORG_CODE);
		if (defaultBizHourSlice != null && StringUtils.isNotEmpty(defaultBizHourSlice.getConfValue())) {
			if (FossConstants.NO.equals(defaultBizHourSlice.getConfValue())) {
				beVerifyExtraLoad = true;
			} else {
				beVerifyExtraLoad = false;
			}
		}
		if (!beVerifyExtraLoad) {
			goodsStates.add(TransferPDADictConstants.LOAD_GOODS_STATE_EXTRA_ENTRAINED);
			goodsStates.add(TransferPDADictConstants.LOAD_GOODS_STATE_EXTRA_ALLOPATRY_ENTRAINED);
		}
		Map<String, Object> condition = this.makeQueryLoadGoodsByGooodsStateQC(taskId, goodsStates);
		List<LoadTaskSerialNoDto> moreGoodsList = pdaLoadDao.queryGoodsByGoodsStates(condition);

		if (CollectionUtils.isNotEmpty(moreGoodsList)) {
			List<InOutStockEntity> inStock = new ArrayList<InOutStockEntity>();
			for (LoadTaskSerialNoDto moreGoods : moreGoodsList) {
				// 入库多货货物
				InOutStockEntity inOutStockEntity = new InOutStockEntity();
				inOutStockEntity.setWaybillNO(moreGoods.getWayBillNo());
				inOutStockEntity.setSerialNO(moreGoods.getSerialNo());
				inOutStockEntity.setOrgCode(origOrgCode);
				inOutStockEntity.setInOutStockType(StockConstants.LOAD_MORE_GOODS_IN_STOCK_TYPE);
				inOutStockEntity.setOperatorCode(loaderCode);
				inOutStockEntity.setOperatorName(loaderName);
				inStock.add(inOutStockEntity);
			}
			pdaLoadService.loadInStock(inStock, LoadConstants.LOAD_INSTOCK_TIMES);
		}
	}

	/**
	 * 中途添加、删除理货员
	 * 
	 * 
	 * @author dp-duyi
	 * @date 2012-11-20 上午11:57:49
	 * @see com.deppon.foss.module.transfer.pda.api.server.service.IPDATransferLoadService#updateLoader(java.lang.String,
	 *      java.lang.String, java.util.Date, java.lang.String)
	 */
	@Override
	@Transactional
	public String modifyLoader(String taskNo, List<LoaderDto> loaderCodes, Date operateTime, String loaderState) {
		// 查询装车任务
		LoadTaskEntity loadTask = pdaLoadDao.queryLoadTaskByTaskNo(taskNo);
		// 只有装车中的任务可以修改理货员
		if (loadTask != null && LoadConstants.LOAD_TASK_STATE_LOADING.equals(loadTask.getState())) {
			int addCount = 0;
			// 装车任务类型
			if (LoadConstants.LOAD_TASK_TYPE_DELIVER.equals(loadTask.getTaskType())) {
				addCount = pdaCommonService.modifyLoader(loadTask.getId(),
						LoadConstants.LOADER_PARTICIPATION_DELIVER_LOAD, loaderCodes, operateTime, loaderState);
			} else {
				addCount = pdaCommonService.modifyLoader(loadTask.getId(),
						LoadConstants.LOADER_PARTICIPATION_TRANSFER_LOAD, loaderCodes, operateTime, loaderState);
			}
			// 增加理货员
			if (TransferPDADictConstants.ADD_LOADR.equals(loaderState)) {
				loadTask.setLoaderQty(loadTask.getLoaderQty() + addCount);
				pdaLoadDao.updateLoadTask(loadTask);
			}
			
			boolean tfrSwitch4Ecs = configurationParamsService.queryTfrSwitch4Ecs();
			LOGGER.error("同步修改装车理货员信息到悟空系统开关" + tfrSwitch4Ecs);

			if (tfrSwitch4Ecs) {
				OrgAdministrativeInfoEntity org = orgAdministrativeInfoService
						.queryOrgAdministrativeInfoByCode(loadTask.getOrigOrgCode());
				boolean isSalesDepartment = FossConstants.YES.equals(org.getSalesDepartment()) ? true : false;
				if (!isSalesDepartment) {
					Map<String, Object> map = new HashMap<String, Object>();
					// 查询理货员
					LoaderParticipationEntity loaderParticipation = pdaLoadDao.queryLoaderByTaskID(loadTask.getId());
					String operatorNo = null;
					List<String> loaderList = new ArrayList<String>();
					if (null!=loaderParticipation) {
						operatorNo = loaderParticipation.getLoaderCode();
					}
					for (LoaderDto loaderCode : loaderCodes) {
						loaderList.add(loaderCode.getLoaderCode());
					}
					map.put("taskNo", taskNo);
					map.put("loaderCodes", loaderList);
					map.put("operatorNo", operatorNo);
					map.put("operationOrgCode", loadTask.getOrigOrgCode());
					map.put("operationTime", operateTime);
					if (TransferPDADictConstants.ADD_LOADR.equals(loaderState)) {
						map.put("operationType", loaderState);
					} else {
						map.put("operationType", "REMOVE");
					}

					syncPDALoadModifyLoaderToWk(map);
				}
			}
			// 返回成功
			return TransferPDADictConstants.SUCCESS;
		} else {// 装车任务为空，或状态不为装车中，不能修改理货员
			throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_INVALID_TASK_MESSAGECODE);
		}
	}
	
	
	/**
	 * @description 同步PDA装车修改理货员信息到悟空系统
	 * @param syncPDAmodifyLoaderToWk
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年5月19日 上午9:56:31
	 */
	private void syncPDALoadModifyLoaderToWk(Map<String,Object> map){
		// 获取objectMapper
		ObjectMapper objectMapper = JSONUtils.obtainObjectMapper();
		// 设置时间转换格式
		SimpleDateFormat dateFormat = new SimpleDateFormat(DateUtils.DATE_TIME_FORMAT);
		// 设置到objectMapper
		objectMapper.setDateFormat(dateFormat);
		
		String requestJsonStr=null;
		try {
			requestJsonStr = objectMapper.writeValueAsString(map);
		}catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		
		//调用同步数据接口
		FossToWKResponseEntity fossToWKResponseEntity=null;
		try {
			LOGGER.error("同步装车修改理货员信息到悟空系统参数："+requestJsonStr);
			fossToWKResponseEntity = fossToWkService.syncPDALoadModifyLoaderToWk(requestJsonStr);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new TfrBusinessException("同步装车修改理货员信息到悟空系统失败，错误信息："+e.getMessage());
		}
		if(fossToWKResponseEntity==null){
			LOGGER.error("同步装车修改理货员信息到悟空系统失败!");
			throw new TfrBusinessException("同步装车修改理货员信息到悟空系统失败!");
		}
		if("0".equals(fossToWKResponseEntity.getStatus())){
			LOGGER.error("同步装车修改理货员信息到悟空系统失败，错误信息："+fossToWKResponseEntity.getExMsg());
			throw new TfrBusinessException("ECS-"+fossToWKResponseEntity.getExMsg());
		}
		
	}

	/**
	 * 撤销装车任务
	 * 
	 * 
	 * @param taskNo
	 * @param deviceNo
	 * @param operatorCode
	 * @param cancelTime
	 * @author dp-duyi
	 * @date 2012-12-6 下午2:54:36
	 * @see com.deppon.foss.module.transfer.pda.api.server.service.IPDATransferLoadService#cancelLoadTask(java.lang.String,
	 *      java.lang.String, java.util.Date)
	 */
	@Override

	public String cancelLoadTask(String taskNo, String deviceNo, String operatorCode, Date cancelTime, String orgCode) {
		LOGGER.error("撤销装车任务开始" + taskNo);
		// 查询装车任务
		LoadTaskEntity loadTask = pdaLoadDao.queryLoadTaskByTaskNo(taskNo);
		// 状态为进行中
		if (loadTask != null && LoadConstants.LOAD_TASK_STATE_LOADING.equals(loadTask.getState())) {
			int scanQty = pdaLoadDao.queryScanSerialNoQTYByTaskId(loadTask.getId());
			// 扫描件数等于0才能取消
			if (scanQty > 0) {
				throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_EXTIST_SCAN_RECORD_MESSAGECODE);
			} else {
				loadTask.setState(LoadConstants.LOAD_TASK_STATE_CANCELED);
				pdaLoadDao.updateLoadTask(loadTask);
				
				if (LoadConstants.LOAD_TASK_TYPE_DELIVER.equals(loadTask.getTaskType())) {
					// 更新派送单状态为已分配
					DeliverBillEntity bill = new DeliverBillEntity();
					// Gets the 派送单单号
					bill.setBillNo(loadTask.getDeliverBillNo());
					// 已分配
					bill.setState(DeliverbillConstants.STATUS_ASSIGNED);
					// 更新派送单状态
					assignLoadTaskDao.updateDeliverBillState(bill, null);
					// 如果派送单号和派送单状态不为空，添加-派送单状态更新记录
					if (StringUtils.isNotBlank(bill.getBillNo()) && StringUtils.isNotBlank(bill.getState())) {
						DeliverBillVaryStatusEntity deliverBillVary = new DeliverBillVaryStatusEntity();
						deliverBillVary.setDeliverBillNo(bill.getBillNo());// 派送单号
						deliverBillVary.setDeliverBillStatus(bill.getState());// 派送单状态
						deliverBillVaryStatusService.insertDBVaryStatus(deliverBillVary);
					}
					// 更新分配装车任务状态为未开始
					Map<String, String> condition = new HashMap<String, String>();
					// 是否取消
					condition.put("beCancelled", FossConstants.NO);
					// 已分配派送装车任务状态
					condition.put("changeToTaskState", LoadConstants.ASSIGN_DELIVER_LOAD_TASK_STATE_UNSTART);
					condition.put("conditionTaskState", LoadConstants.ASSIGN_DELIVER_LOAD_TASK_STATE_PROCESSING);
					// 派送单单号.
					condition.put("deliverBillNo", loadTask.getDeliverBillNo());
					// 更新分配记录状态：进行中、已完成
					assignLoadTaskDao.updateAssignedLoadTaskState(condition);
				}
				// 成功
				LOGGER.error("撤销装车任务结束" + taskNo);
				if (StringUtils.isNotBlank(loadTask.getPlatformId())) {
					LOGGER.error("撤销装车任务释放月台开始" + taskNo);
					pdaCommonService.updatePlatformStateByFinishTask(taskNo, new Date());
					LOGGER.error("撤销装车任务释放月台结束" + taskNo);
				}
				
				boolean tfrSwitch4Ecs = configurationParamsService.queryTfrSwitch4Ecs();
				LOGGER.info("对接悟空开关（是否插入smtTask悟空同步表 cancled）：" + tfrSwitch4Ecs);
				if (tfrSwitch4Ecs && !TransferPDADictConstants.LOAD_TASK_TYPE_DIVISION.equals(loadTask.getTaskType())
						&& !TransferPDADictConstants.LOAD_TASK_TYPE_DELIVER.equals(loadTask.getTaskType())) {
					SmtWKLoadTaskDto dto = new SmtWKLoadTaskDto();
					dto.setLoadTaskNo(taskNo);
					dto.setOperatorNo(operatorCode);
					dto.setOperationTime(DateUtils.convert(cancelTime));
					dto.setOperationOrgCode(orgCode);
					dto.setCancel(true);
//					addDataToWKLOADTemp(taskNo, dto, LoadStatueEnum.CREATE.getCode());
					
					/////////////////// 第二套改同步
					Map<String, Object> result = fossToWkService.sysnSubmitLoadToWK(JSON.toJSONString(dto));
					
					String status = null;
					if (result == null || !result.containsKey("status") || result.get("status") == null) {
						throw new TfrBusinessException("ECS - 提交装车任务异常");
					}
					status = result.get("status").toString();
					if (!status.equals("1")) {
						throw new TfrBusinessException("ECS - " + result.get("exMsg"));
					}
					LOGGER.error("同步撤销装车任务结束 - 成功" + taskNo);
					/////// END///////////
				}
				
				return TransferPDADictConstants.SUCCESS;
			}
		} else if (loadTask != null && LoadConstants.LOAD_TASK_STATE_CANCELED.equals(loadTask.getState())) {
			LOGGER.error("撤销装车任务结束" + taskNo);
			return TransferPDADictConstants.SUCCESS;
		} else {
			// 异常
			LOGGER.error("撤销装车任务结束" + taskNo);
			throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_INVALID_TASK_MESSAGECODE);
		}
	}
	
	
	@Override
	public String cancelLoadTask(String taskNo, String deviceNo, String operatorCode, Date cancelTime) {
		LOGGER.error("撤销装车任务开始" + taskNo);
		// 查询装车任务
		LoadTaskEntity loadTask = pdaLoadDao.queryLoadTaskByTaskNo(taskNo);
		// 状态为进行中
		if (loadTask != null && LoadConstants.LOAD_TASK_STATE_LOADING.equals(loadTask.getState())) {
			int scanQty = pdaLoadDao.queryScanSerialNoQTYByTaskId(loadTask.getId());
			// 扫描件数等于0才能取消
			if (scanQty > 0) {
				throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_EXTIST_SCAN_RECORD_MESSAGECODE);
			} else {
				loadTask.setState(LoadConstants.LOAD_TASK_STATE_CANCELED);
				pdaLoadDao.updateLoadTask(loadTask);
				
				if (LoadConstants.LOAD_TASK_TYPE_DELIVER.equals(loadTask.getTaskType())) {
					// 更新派送单状态为已分配
					DeliverBillEntity bill = new DeliverBillEntity();
					// Gets the 派送单单号
					bill.setBillNo(loadTask.getDeliverBillNo());
					// 已分配
					bill.setState(DeliverbillConstants.STATUS_ASSIGNED);
					// 更新派送单状态
					assignLoadTaskDao.updateDeliverBillState(bill, null);
					// 如果派送单号和派送单状态不为空，添加-派送单状态更新记录
					if (StringUtils.isNotBlank(bill.getBillNo()) && StringUtils.isNotBlank(bill.getState())) {
						DeliverBillVaryStatusEntity deliverBillVary = new DeliverBillVaryStatusEntity();
						deliverBillVary.setDeliverBillNo(bill.getBillNo());// 派送单号
						deliverBillVary.setDeliverBillStatus(bill.getState());// 派送单状态
						deliverBillVaryStatusService.insertDBVaryStatus(deliverBillVary);
					}
					// 更新分配装车任务状态为未开始
					Map<String, String> condition = new HashMap<String, String>();
					// 是否取消
					condition.put("beCancelled", FossConstants.NO);
					// 已分配派送装车任务状态
					condition.put("changeToTaskState", LoadConstants.ASSIGN_DELIVER_LOAD_TASK_STATE_UNSTART);
					condition.put("conditionTaskState", LoadConstants.ASSIGN_DELIVER_LOAD_TASK_STATE_PROCESSING);
					// 派送单单号.
					condition.put("deliverBillNo", loadTask.getDeliverBillNo());
					// 更新分配记录状态：进行中、已完成
					assignLoadTaskDao.updateAssignedLoadTaskState(condition);
				}
				// 成功
				LOGGER.error("撤销装车任务结束" + taskNo);
				if (StringUtils.isNotBlank(loadTask.getPlatformId())) {
					LOGGER.error("撤销装车任务释放月台开始" + taskNo);
					pdaCommonService.updatePlatformStateByFinishTask(taskNo, new Date());
					LOGGER.error("撤销装车任务释放月台结束" + taskNo);
				}
				
				//如果是营业部装车，不需要同步悟空---332219
				if(!StringUtils.equals(loadTask.getTaskType(),TransferPDADictConstants.LOAD_TASK_TYPE_SALES_DISTANCE)){
					boolean tfrSwitch4Ecs = configurationParamsService.queryTfrSwitch4Ecs();
					LOGGER.info("对接悟空开关（是否插入smtTask悟空同步表 cancled）：" + tfrSwitch4Ecs);
					if (tfrSwitch4Ecs && !TransferPDADictConstants.LOAD_TASK_TYPE_DIVISION.equals(loadTask.getTaskType())
							&& !TransferPDADictConstants.LOAD_TASK_TYPE_DELIVER.equals(loadTask.getTaskType())) {
						SmtWKLoadTaskDto dto = new SmtWKLoadTaskDto();
						dto.setLoadTaskNo(taskNo);
						dto.setCancel(true);
						//addDataToWKLOADTemp(taskNo, dto, LoadStatueEnum.CREATE.getCode());
						fossToWkService.sysnSubmitLoadToWK(JSON.toJSONString(dto));
					}
				}
				return TransferPDADictConstants.SUCCESS;
			}
		} else if (loadTask != null && LoadConstants.LOAD_TASK_STATE_CANCELED.equals(loadTask.getState())) {
			LOGGER.error("撤销装车任务结束" + taskNo);
			return TransferPDADictConstants.SUCCESS;
		} else {
			// 异常
			LOGGER.error("撤销装车任务结束" + taskNo);
			throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_INVALID_TASK_MESSAGECODE);
		}
	}

	/**
	 * 查询装车流水号及装车运单明细
	 *
	 * @param taskNo
	 * @param taskState
	 * @param wayBillNo
	 * @param serialNo
	 * @param goodsState
	 * @return
	 */
	private LoadTaskSerialNoDto queryLoadTaskSerialNoDtoByCondition(String taskNo, String taskState, String wayBillNo,
			String serialNo, String goodsState) {
		Map<String, String> condition = new HashMap<String, String>();
		// 任务号
		condition.put("taskNo", taskNo);
		// 状态
		condition.put("taskState", taskState);
		// 运单号
		condition.put("wayBillNo", wayBillNo);
		// 流水号
		condition.put("serialNo", serialNo);
		// 货物状态
		condition.put("goodsState", goodsState);
		// 查询装车流水号及装车运单明细
		return pdaLoadDao.queryLoadTaskSerialNoDtoByCondition(condition);
	}

	/**
	 * 删除扫描记录
	 * 
	 * 
	 * @author dp-duyi
	 * 
	 * @date 2012-11-20 下午3:32:56
	 * 
	 * @see com.deppon.foss.module.transfer.pda.api.server.service.IPDATransferLoadService#deleteScanRecord(java.lang.String,
	 *      java.lang.String)
	 */
	@Transactional
	private LoadGoodsDetailDto deleteScanRecord(LoadScanDetailDto scanRecord, String taskId, String origOrgCode,
			Date loadBeginTime, LoaderParticipationEntity loaderParticipation) {
		try {
			int updateDetailCount = 0;

			LoadTaskSerialNoDto loadTaskSerialNoDto = this.queryLoadTaskSerialNoDtoByCondition(scanRecord.getTaskNo(),
					LoadConstants.LOAD_TASK_STATE_LOADING, scanRecord.getWayBillNo(), scanRecord.getSerialNo(), null);
			// 扫描运单明细不为空、流水号明细不为空
			if (loadTaskSerialNoDto != null && StringUtils.isNotBlank(loadTaskSerialNoDto.getId())) {
				LoadWaybillDetailEntity loadWaybillDetail = new LoadWaybillDetailEntity();
				loadWaybillDetail.setId(loadTaskSerialNoDto.getLoadWaybillDetailId());
				if (!LoadConstants.LOAD_GOODS_STATE_NOT_LOADING.equals(loadTaskSerialNoDto.getGoodsState())) {
					loadWaybillDetail.setScanQty(-1);
				} else {
					loadWaybillDetail.setScanQty(0);
				}
				loadWaybillDetail.setStockQty(scanRecord.getStockQty());
				// 装车
				loadWaybillDetail.setLoadQty(-1);
				loadWaybillDetail.setLoadVolumeTotal(-scanRecord.getVolume());
				loadWaybillDetail.setLoadWeightTotal(-scanRecord.getWeight());
				// 更新装车运单明细中装车件数已操作件数
				// pdaLoadDao.updateLoadTaskWayBillDetail(loadWaybillDetail);
				updateDetailCount = pdaExpressDeliverLoadDao.updateLoadWayBillQTYByScanTime(loadWaybillDetail,
						scanRecord.getSerialNo(), scanRecord.getScanTime());

				// 删除装车扫描流水号
				LoadSerialNoEntity loadSerialNoEntity = new LoadSerialNoEntity();
				loadSerialNoEntity.setId(loadTaskSerialNoDto.getId());
				// 取消
				loadSerialNoEntity.setGoodsState(TransferPDADictConstants.LOAD_GOODS_STATE_CANCELED);
				loadSerialNoEntity.setLoadTime(scanRecord.getScanTime());
				loadSerialNoEntity.setBeLoaded(FossConstants.NO);
				// 扫描状态
				loadSerialNoEntity.setScanState(scanRecord.getScanState());
				// 更新装车流水号信息
				// pdaLoadDao.updateLoadTaskSerialNo(loadSerialNoEntity);
				pdaExpressDeliverLoadDao.updateLoadSerialNoByLoadTime(loadSerialNoEntity);
			} else {// 如果不存在扫描记录，则插入删除记录
				LoadWaybillDetailEntity loadTaskWayBillTemp = new LoadWaybillDetailEntity();
				loadTaskWayBillTemp.setLoadTaskId(taskId);
				loadTaskWayBillTemp.setWaybillNo(scanRecord.getWayBillNo());
				LoadWaybillDetailEntity loadWayBillDetailEntity = pdaLoadDao
						.queryLoadWaybillDetailEntityByWayBillNo(loadTaskWayBillTemp);
				String loadWayBillDetailId = null;
				if (loadWayBillDetailEntity != null) {
					loadWayBillDetailId = loadWayBillDetailEntity.getId();
				}
				if (loadWayBillDetailEntity == null) {
					// 如果不存在装车运单记录，则插入装车运单记录
					LoadWaybillDetailEntity loadWayBillEntity = new LoadWaybillDetailEntity();
					// 生成id
					loadWayBillDetailId = UUIDUtils.getUUID();
					// 设置id
					loadWayBillEntity.setId(loadWayBillDetailId);
					// 设置 loadTask_ID
					loadWayBillEntity.setLoadTaskId(taskId);
					// 设置 已装车件数
					loadWayBillEntity.setLoadQty(0);
					// 设置 已扫描件数
					loadWayBillEntity.setScanQty(0);
					// 设置 库存件数
					loadWayBillEntity.setStockQty(scanRecord.getStockQty());
					// 设置 装车体积
					loadWayBillEntity.setLoadVolumeTotal(0);
					// 设置 装车重量*.
					loadWayBillEntity.setLoadWeightTotal(0);
					// 设置 出发部门编号
					loadWayBillEntity.setOrigOrgCode(origOrgCode);
					// 设置 运单号
					loadWayBillEntity.setWaybillNo(scanRecord.getWayBillNo());
					// 设置 建立任务时间
					loadWayBillEntity.setTaskBeginTime(loadBeginTime);
					// 设置 运输性质
					loadWayBillEntity.setTransportType(scanRecord.getTransportType());
					// 设置 收货部门
					loadWayBillEntity.setReceiveOrgName(scanRecord.getReceiveOrgName());
					// 设置 到达部门
					loadWayBillEntity.setReachOrgName(scanRecord.getReachOrgName());
					// 设置 包装
					loadWayBillEntity.setPack(scanRecord.getPack());
					// 设置 货名
					loadWayBillEntity.setGoodsName(scanRecord.getGoodsName());
					// 设置 是否合车
					loadWayBillEntity.setBeJoinCar(scanRecord.getBeJoinCar());
					// 插入装车流水号明细记录
					updateDetailCount = pdaLoadDao.insertLoadWayBillDetailEntity(loadWayBillEntity);
				}
				// 装车流水号实体
				LoadSerialNoEntity loadSerialNoEntity = new LoadSerialNoEntity();
				// CANCELED
				loadSerialNoEntity.setGoodsState(TransferPDADictConstants.LOAD_GOODS_STATE_CANCELED);
				// 否装车
				loadSerialNoEntity.setBeLoaded(FossConstants.NO);
				// 设备编号
				loadSerialNoEntity.setDeviceNo(scanRecord.getDeviceNo());
				// 扫描状态
				loadSerialNoEntity.setScanState(scanRecord.getScanState());
				// 创建时间.
				loadSerialNoEntity.setCreateTime(new Date());
				// 操作时间
				loadSerialNoEntity.setLoadTime(scanRecord.getScanTime());
				// 流水号
				loadSerialNoEntity.setSerialNo(scanRecord.getSerialNo());
				// 建立任务时间
				loadSerialNoEntity.setTaskBeginTime(loadBeginTime);
				// 出发部门编号
				loadSerialNoEntity.setOrigOrgCode(origOrgCode);
				loadSerialNoEntity.setLoadWaybillDetailId(loadWayBillDetailId);
				loadSerialNoEntity.setId(UUIDUtils.getUUID());
				// 插入装车运单明细记录
				pdaLoadDao.insertLoadSerialNoEntity(loadSerialNoEntity);
			}

			/*
			 * if(updateDetailCount>0){ //撤销入库货物 InOutStockEntity
			 * inOutStockEntity = new InOutStockEntity();
			 * inOutStockEntity.setWaybillNO(scanRecord.getWayBillNo());
			 * inOutStockEntity.setSerialNO(scanRecord.getSerialNo());
			 * inOutStockEntity.setOrgCode(origOrgCode);
			 * inOutStockEntity.setInOutStockType(StockConstants.
			 * PDA_CANCEL_IN_STOCK_TYPE);
			 * inOutStockEntity.setOperatorCode(loaderParticipation.
			 * getLoaderCode());
			 * inOutStockEntity.setOperatorName(loaderParticipation.
			 * getLoaderName()); this.instock(inOutStockEntity); }
			 */

			// 成功
			return null;
		} catch (Exception e) {
			// 记录日志
			LOGGER.error("删除扫描记录", e);
			throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_PROCEDURE_ERROR_MESSAGECODE);
		}
	}

	/**
	 * 派送/中转装车扫描
	 * 
	 * 
	 * @author dp-duyi
	 * 
	 * @date 2012-12-7 上午10:12:55
	 * 
	 * @see com.deppon.foss.module.transfer.pda.api.server.service.IPDATransferLoadService#loadScan(com.deppon.foss.module.transfer.pda.api.shared.dto.LoadScanDetailDto)
	 */
	@Override
	public String loadScan(LoadScanDetailDto scanRecord) {

		// 业务锁
		MutexElement mutex = new MutexElement(scanRecord.getWayBillNo(), "LOAD_SCAN", MutexElementType.PDA_WAYBILL_NO);
		LOGGER.error("load scan begin:" + scanRecord.getTaskNo() + " " + scanRecord.getWayBillNo() + " "
				+ scanRecord.getSerialNo());
		businessLockService.lock(mutex, LoadConstants.PDA_SCAN_OUTTIME);
		try {
			if (FossConstants.YES.equals(scanRecord.getIsPackageScan())) {
				/** 装车包扫描新方法 zwd 200968 */
				pdaLoadService.unlockPackageLoadScanNew(scanRecord);
				// pdaLoadService.unlockPackageLoadScan(scanRecord);
			} else {
				pdaLoadService.unlockLoadScan(scanRecord);
			}
		} catch (TfrBusinessException e) {
			// 释放锁
			businessLockService.unlock(mutex);
			// 记录日志
			LOGGER.error("load scan exception:" + scanRecord.getTaskNo() + " " + scanRecord.getWayBillNo() + " "
					+ scanRecord.getSerialNo(), e);
			throw (e);
		}

		// 释放锁
		businessLockService.unlock(mutex);
		// 记录日志
		LOGGER.error("load scan end:" + scanRecord.getTaskNo() + " " + scanRecord.getWayBillNo() + " "
				+ scanRecord.getSerialNo());
		// 成功
		return TransferPDADictConstants.SUCCESS;
	}

	/**
	 * 多货扫描
	 * 
	 * @author dp-duyi
	 * @date 2013-4-10 上午8:50:11
	 * @see com.deppon.foss.module.transfer.pda.api.server.service.IPDATransferLoadService#moreGoodsLoadScan(com.deppon.foss.module.transfer.pda.api.shared.dto.LoadScanDetailDto)
	 */
	@Override
	public LoadGoodsDetailDto moreGoodsLoadScan(LoadScanDetailDto scanRecord) {
		// 业务锁
		MutexElement mutex = new MutexElement(scanRecord.getWayBillNo(), "LOAD_SCAN", MutexElementType.PDA_WAYBILL_NO);
		LOGGER.error("load scan begin:" + scanRecord.getTaskNo() + " " + scanRecord.getWayBillNo() + " "
				+ scanRecord.getSerialNo());
		businessLockService.lock(mutex, LoadConstants.PDA_SCAN_OUTTIME);
		LoadGoodsDetailDto loadGoodsDetailDto;
		try {
			loadGoodsDetailDto = pdaLoadService.unlockLoadScan(scanRecord);
		} catch (TfrBusinessException e) {
			// 释放锁
			businessLockService.unlock(mutex);
			// 记录日志
			LOGGER.error("load scan exception:" + scanRecord.getTaskNo() + " " + scanRecord.getWayBillNo() + " "
					+ scanRecord.getSerialNo(), e);
			throw (e);
		}

		// 释放锁
		businessLockService.unlock(mutex);
		// 记录日志
		LOGGER.error("load scan end:" + scanRecord.getTaskNo() + " " + scanRecord.getWayBillNo() + " "
				+ scanRecord.getSerialNo());
		return loadGoodsDetailDto;
	}

	/**
	 * 没有锁的派送/中转装车扫描
	 * 
	 * @author dp-duyi
	 * @date 2012-12-7 上午10:12:55
	 * 
	 * @see com.deppon.foss.module.transfer.pda.api.server.service.IPDATransferLoadService#loadScan(com.deppon.foss.module.transfer.pda.api.shared.dto.LoadScanDetailDto)
	 */
	@Override
	// @Transactional(propagation = Propagation.REQUIRES_NEW) PDA-2144防止撤销失败
	public LoadGoodsDetailDto unlockLoadScan(LoadScanDetailDto scanRecord) {
		LOGGER.error("装车扫描开始" + " " + scanRecord.getType() + " " + scanRecord.getTaskNo() + " "
				+ scanRecord.getWayBillNo() + " " + scanRecord.getSerialNo());
		LOGGER.info(scanRecord.toString());
		// 如果删除扫描记录
		// 扫描记录必有重量、体积
		LoadGoodsDetailDto loadGoodsDetailDto = null;
		WaybillEntity wayBill = null;
		ProductEntity product = null;
		GoodsStateDto goodsStateDto = null;
		// 强装配置参数
		boolean beVerifyExtraLoad = false;
		ConfigurationParamsEntity defaultBizHourSlice = configurationParamsService.queryConfigurationParamsByOrgCode(
				DictionaryConstants.SYSTEM_CONFIG_PARM__TFR,
				ConfigurationParamsConstants.TFR_PARM_HANDOVER_IGNORE_TRANSPORT_PATH_PDA, FossConstants.ROOT_ORG_CODE);
		if (defaultBizHourSlice != null && StringUtils.isNotEmpty(defaultBizHourSlice.getConfValue())) {
			if (FossConstants.NO.equals(defaultBizHourSlice.getConfValue())) {
				beVerifyExtraLoad = true;
			} else {
				beVerifyExtraLoad = false;
			}
		}
		String goodsState = scanRecord.getType();
		LOGGER.info("beVerifyExtraLoad = " + beVerifyExtraLoad + " ; goodsState  = " + goodsState);
		// 查询装车任务
		LoadTaskEntity loadTask = pdaLoadDao.queryLoadTaskByTaskNo(scanRecord.getTaskNo());

		// 如果多货了
		if (LoadConstants.LOAD_GOODS_STATE_MORE.equals(scanRecord.getType())) {
			wayBill = waybillManagerService.queryWaybillBasicByNo(scanRecord.getWayBillNo());
			if (wayBill != null && StringUtils.isNotBlank(wayBill.getProductCode())) {
				if (wayBill.getProductCode().equals("WVH")) {
					throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_LOAD_WHOLE_VEHICLE__MESSAGECODE);
				}
			}
			if (wayBill != null && wayBill.getGoodsVolumeTotal() != null && wayBill.getGoodsWeightTotal() != null
					&& wayBill.getGoodsQtyTotal() > 0) {
				// 快递转换体积参数
				// zwd 调用综合接口，根据部门编码去查询重泡比，如果存在则使用查询到的重泡比，如果不存在则使用公用的重量体积转换参数。
				String orgCode = loadTask.getOrigOrgCode();
				BigDecimal converParameter = conversionParameters(orgCode);

				double waybillVolume = wayBill.getGoodsVolumeTotal().doubleValue() / wayBill.getGoodsQtyTotal();
				double waybillWeight = wayBill.getGoodsWeightTotal().doubleValue() / wayBill.getGoodsQtyTotal();
				// 快递货转换体积
				if (converParameter.compareTo(BigDecimal.ZERO) == 0) {
					scanRecord.setVolume(waybillVolume);
				} else {
					/** 查询所有快递编码集合 **/
					List<String> expressCodeList = productService4Dubbo.getAllLevels3ExpressProductCode();
					boolean expressFlag = false;// 快递标识
					for (String expressCode : expressCodeList) {
						if (StringUtils.equals(wayBill.getProductCode(), expressCode)) {
							expressFlag = true;
							break;
						}
					}
					/**
					 * 加入快递空运(商务专递)产品类型的判断 2015-8-21 09:00:22 263072-linyuzhu
					 **/
					if (expressFlag) {
						// if(wayBill.getProductCode().equals("PACKAGE")
						// ||wayBill.getProductCode().equals("RCP")
						// ||wayBill.getProductCode().equals("EPEP")){
						BigDecimal volume = new BigDecimal(waybillWeight).multiply(converParameter)
								.divide(new BigDecimal(LoadConstants.VOLUME_SIZE)).setScale(4, BigDecimal.ROUND_HALF_UP);
						scanRecord.setVolume(volume.doubleValue());
					} else {
						scanRecord.setVolume(waybillVolume);
					}
				}
				scanRecord.setWeight(wayBill.getGoodsWeightTotal().doubleValue() / wayBill.getGoodsQtyTotal());
				product = productService4Dubbo.getProductByCache(wayBill.getProductCode(), null);
			}
			LOGGER.info("多货处理完成：wayBill = " + wayBill + " ; scanRecord = " + scanRecord + " ; product = "
					+ product);
		}
		if ((scanRecord.getVolume() <= 0 || scanRecord.getWeight() <= 0) && beVerifyExtraLoad) {
			throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_INVALID_WEIGHT_OR_VOLUME_MESSAGECODE);
		}
		
		// 状态为进行中
		if (loadTask != null && LoadConstants.LOAD_TASK_STATE_LOADING.equals(loadTask.getState())) {
			// 查询理货员
			LoaderParticipationEntity loaderParticipation = pdaLoadDao.queryLoaderByTaskID(loadTask.getId());
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date loadStartTime = new Date();
			try {
				loadStartTime = df.parse(loadTask.getLoadStartTime());
			} catch (ParseException e) {
				LOGGER.error("装车扫描", e);
			}
			if (TransferPDADictConstants.LOAD_GOODS_STATE_CANCELED.equals(scanRecord.getType())) {
				return this.deleteScanRecord(scanRecord, loadTask.getId(), loadTask.getOrigOrgCode(), loadStartTime,
						loaderParticipation);
			} else {
				// 统计任务明细更新条数
				int updateDetailCount = 0;
				// 货物状态
				if (!LoadConstants.LOAD_GOODS_STATE_MORE.equals(scanRecord.getType())) {
					WaybillEntity wayBillTemp = new WaybillEntity();
					wayBillTemp.setWaybillNo(scanRecord.getWayBillNo());
					goodsStateDto = this.getGoodsState(wayBillTemp, scanRecord.getSerialNo(), scanRecord.getType(),
							loadTask.getOrigOrgCode(), loadTask);
				} else {
					goodsStateDto = this.getGoodsState(wayBill, scanRecord.getSerialNo(), scanRecord.getType(),
							loadTask.getOrigOrgCode(), loadTask);
				}
				LOGGER.info("goodsState = " + goodsState);
				goodsState = goodsStateDto.getGoodsState();
				LoadSerialNoEntity loadSerialNoEntity = new LoadSerialNoEntity();

				// 根据货物状态判断是否装车
				String beLoaded = this.beLoaded(goodsState, beVerifyExtraLoad);// 是否装车
				// 如果不能装车则提交PDA不能装车原因，则直接失败
				if (FossConstants.NO.equals(beLoaded) && beVerifyExtraLoad) {
					throw new TfrBusinessException(goodsState);
				}
				// 货物状态
				loadSerialNoEntity.setGoodsState(goodsState);
				// 是否装车
				loadSerialNoEntity.setBeLoaded(beLoaded);
				// 设备编号.
				loadSerialNoEntity.setDeviceNo(scanRecord.getDeviceNo());
				// 扫描状态
				loadSerialNoEntity.setScanState(scanRecord.getScanState());
				// 创建时间.
				loadSerialNoEntity.setCreateTime(new Date());
				// 操作时间
				loadSerialNoEntity.setLoadTime(scanRecord.getScanTime());
				// 流水号
				loadSerialNoEntity.setSerialNo(scanRecord.getSerialNo());
				// 建立任务时间.
				loadSerialNoEntity.setTaskBeginTime(loadStartTime);
				// 出发部门编号
				loadSerialNoEntity.setOrigOrgCode(loadTask.getOrigOrgCode());
				LOGGER.info("loadSerialNoEntity = " + loadSerialNoEntity);
				// 查询运单明细
				LoadWaybillDetailEntity loadTaskWayBillTemp = new LoadWaybillDetailEntity();
				loadTaskWayBillTemp.setLoadTaskId(loadTask.getId());
				loadTaskWayBillTemp.setWaybillNo(scanRecord.getWayBillNo());
				LoadWaybillDetailEntity loadWayBillEntity = pdaLoadDao
						.queryLoadWaybillDetailEntityByWayBillNo(loadTaskWayBillTemp);
				LOGGER.warn("(更新 或者 插入)loadWayBillEntity = " +loadWayBillEntity);
				// 如果已经存在装车运单明细，则更新
				if (loadWayBillEntity != null) {
					// 查询是否存在装车流水号
					LoadSerialNoEntity loadSerialNoEntityTemp = new LoadSerialNoEntity();
					loadSerialNoEntityTemp.setLoadWaybillDetailId(loadWayBillEntity.getId());
					loadSerialNoEntityTemp.setSerialNo(scanRecord.getSerialNo());
					LoadSerialNoEntity updateLoadSerialNo = pdaLoadDao
							.queryLoadSerialNoEntityBySerialNo(loadSerialNoEntityTemp);

					loadWayBillEntity.setScanQty(1);
					loadWayBillEntity.setStockQty(scanRecord.getStockQty());
					if (goodsStateDto != null && goodsStateDto.getStockQty() > 0) {
						loadWayBillEntity.setStockQty(goodsStateDto.getStockQty());
					}
					if (FossConstants.YES.equals(beLoaded)) {
						// 设置 已装车件数
						loadWayBillEntity.setLoadQty(1);
						// 设置 装车体积
						loadWayBillEntity.setLoadVolumeTotal(scanRecord.getVolume());
						// 设置 装车重量
						loadWayBillEntity.setLoadWeightTotal(scanRecord.getWeight());
					} else {
						// 设置 已装车件数
						loadWayBillEntity.setLoadQty(0);
						// 设置 装车体积
						loadWayBillEntity.setLoadVolumeTotal(0);
						// 设置 装车重量
						loadWayBillEntity.setLoadWeightTotal(0);
					}
					// pdaLoadDao.updateLoadTaskWayBillDetail(loadWayBillEntity);
					updateDetailCount = pdaExpressDeliverLoadDao.updateLoadWayBillQTYByScanTime(loadWayBillEntity,
							scanRecord.getSerialNo(), scanRecord.getScanTime());
					// 若记录的装车时间在扫描记录的装车时间之前，则更新货物状态
					if (updateLoadSerialNo != null) {
						if (TransferPDADictConstants.LOAD_GOODS_STATE_CANCELED
								.equals(updateLoadSerialNo.getGoodsState())
								|| LoadConstants.LOAD_GOODS_STATE_NOT_LOADING
										.equals(updateLoadSerialNo.getGoodsState())) {
							//上面goodsState已经算过了，这里不再重新计算
							/*if (wayBill == null) {
								wayBill = waybillManagerService.queryWaybillBasicByNo(scanRecord.getWayBillNo());
							}
							goodsStateDto = this.getGoodsState(wayBill, scanRecord.getSerialNo(),
									LoadConstants.LOAD_GOODS_STATE_MORE, loadTask.getOrigOrgCode(), loadTask);
							goodsState = goodsStateDto.getGoodsState();*/
							updateLoadSerialNo.setGoodsState(goodsState);
						}
						beLoaded = this.beLoaded(updateLoadSerialNo.getGoodsState(), beVerifyExtraLoad);
						updateLoadSerialNo.setBeLoaded(beLoaded);
						updateLoadSerialNo.setDeviceNo(scanRecord.getDeviceNo());
						updateLoadSerialNo.setScanState(scanRecord.getScanState());
						// updateLoadSerialNo.setCreateTime(new Date());
						// 扫描状态
						updateLoadSerialNo.setScanState(scanRecord.getScanState());
						updateLoadSerialNo.setLoadTime(scanRecord.getScanTime());
						// pdaLoadDao.updateLoadTaskSerialNo(updateLoadSerialNo);
						LOGGER.info("updateLoadSerialNo = " + updateLoadSerialNo.toString());
						pdaExpressDeliverLoadDao.updateLoadSerialNoByLoadTime(updateLoadSerialNo);
					} else {// 如果不存在装车流水号，则插入
						loadSerialNoEntity.setId(UUIDUtils.getUUID());
						loadSerialNoEntity.setLoadWaybillDetailId(loadWayBillEntity.getId());
						try {
							pdaLoadDao.insertLoadSerialNoEntity(loadSerialNoEntity);
						} catch (DuplicateKeyException e) {
							updateLoadSerialNo = pdaLoadDao.queryLoadSerialNoEntityBySerialNo(loadSerialNoEntityTemp);
							if(updateLoadSerialNo != null && !StringUtils.equals(goodsState, updateLoadSerialNo.getGoodsState())){
								if (TransferPDADictConstants.LOAD_GOODS_STATE_CANCELED
										.equals(updateLoadSerialNo.getGoodsState())
										|| LoadConstants.LOAD_GOODS_STATE_NOT_LOADING
										.equals(updateLoadSerialNo.getGoodsState())) {
									updateLoadSerialNo.setGoodsState(goodsState);
								}
								beLoaded = this.beLoaded(updateLoadSerialNo.getGoodsState(), beVerifyExtraLoad);
								updateLoadSerialNo.setBeLoaded(beLoaded);
								updateLoadSerialNo.setDeviceNo(scanRecord.getDeviceNo());
								updateLoadSerialNo.setScanState(scanRecord.getScanState());
								updateLoadSerialNo.setScanState(scanRecord.getScanState());
								updateLoadSerialNo.setLoadTime(scanRecord.getScanTime());
								pdaExpressDeliverLoadDao.updateLoadSerialNoByLoadTime(updateLoadSerialNo);
							}
						}
					}
				} else {// 如果不存在装车运单明细，则插入记录
					loadWayBillEntity = new LoadWaybillDetailEntity();
					String loadWaybillDetailId = UUIDUtils.getUUID();
					loadWayBillEntity.setId(loadWaybillDetailId);
					loadWayBillEntity.setStockQty(scanRecord.getStockQty());
					if (goodsStateDto != null && goodsStateDto.getStockQty() > 0) {
						loadWayBillEntity.setStockQty(goodsStateDto.getStockQty());
					}
					if (wayBill != null) {
						loadWayBillEntity.setBeJoinCar(FossConstants.NO);
						loadWayBillEntity.setGoodsName(wayBill.getGoodsName());
						loadWayBillEntity.setPack(wayBill.getGoodsPackage());
						loadWayBillEntity.setReachOrgName(orgAdministrativeInfoService
								.queryOrgAdministrativeInfoNameByCode(wayBill.getCustomerPickupOrgCode()));
						loadWayBillEntity.setReceiveOrgName(orgAdministrativeInfoService
								.queryOrgAdministrativeInfoNameByCode(wayBill.getReceiveOrgCode()));
						if (product != null) {
							loadWayBillEntity.setTransportType(product.getName());
						}
						List<PDAGoodsSerialNoDto> serialNos = new ArrayList<PDAGoodsSerialNoDto>();
						PDAGoodsSerialNoDto serialNo = new PDAGoodsSerialNoDto();
						serialNo.setIsUnPacking(FossConstants.NO);
						serialNo.setSerialNo(scanRecord.getSerialNo());
						serialNos.add(serialNo);
					} else {
						if (StringUtils.isNotBlank(scanRecord.getBeJoinCar())) {
							loadWayBillEntity.setBeJoinCar(scanRecord.getBeJoinCar());
						} else {
							loadWayBillEntity.setBeJoinCar(FossConstants.NO);
						}
						loadWayBillEntity.setGoodsName(scanRecord.getGoodsName());
						loadWayBillEntity.setPack(scanRecord.getPack());
						loadWayBillEntity.setReachOrgName(scanRecord.getReachOrgName());
						loadWayBillEntity.setReceiveOrgName(scanRecord.getReceiveOrgName());
						loadWayBillEntity.setTransportType(scanRecord.getTransportType());
					}
					if (FossConstants.YES.equals(beLoaded)) {
						loadWayBillEntity.setLoadQty(1);
						loadWayBillEntity.setLoadVolumeTotal(scanRecord.getVolume());
						loadWayBillEntity.setLoadWeightTotal(scanRecord.getWeight());
					} else {
						loadWayBillEntity.setLoadQty(0);
						loadWayBillEntity.setLoadVolumeTotal(0);
						loadWayBillEntity.setLoadWeightTotal(0);
					}
					loadWayBillEntity.setScanQty(1);
					loadWayBillEntity.setWaybillNo(scanRecord.getWayBillNo());
					loadWayBillEntity.setLoadTaskId(loadTask.getId());
					loadWayBillEntity.setOrigOrgCode(loadTask.getOrigOrgCode());
					loadWayBillEntity.setTaskBeginTime(loadStartTime);
					try {
						updateDetailCount = pdaLoadDao.insertLoadWayBillDetailEntity(loadWayBillEntity);
					} catch (org.springframework.dao.DuplicateKeyException e) {
						LoadWaybillDetailEntity loadWayBillEntityID = pdaLoadDao
								.queryLoadWaybillDetailEntityByWayBillNo(loadWayBillEntity);
						if (loadWayBillEntityID != null) {
							loadWayBillEntity.setId(loadWayBillEntityID.getId());
							// KDTE-4411
							loadWaybillDetailId = loadWayBillEntityID.getId();
							// pdaLoadDao.updateLoadTaskWayBillDetail(loadWayBillEntity);
							updateDetailCount = pdaExpressDeliverLoadDao.updateLoadWayBillQTYByScanTime(
									loadWayBillEntity, scanRecord.getSerialNo(), scanRecord.getScanTime());
						} else {
							throw e;
						}
					}
					// 插入装车流水号
					loadSerialNoEntity.setLoadWaybillDetailId(loadWaybillDetailId);
					loadSerialNoEntity.setId(UUIDUtils.getUUID());
					pdaLoadDao.insertLoadSerialNoEntity(loadSerialNoEntity);
				}
				/*
				 * if(updateDetailCount>0){ //出库货物 InOutStockEntity
				 * inOutStockEntity = new InOutStockEntity();
				 * inOutStockEntity.setWaybillNO(scanRecord.getWayBillNo());
				 * inOutStockEntity.setSerialNO(scanRecord.getSerialNo());
				 * inOutStockEntity.setOrgCode(loadTask.getOrigOrgCode());
				 * inOutStockEntity.setInOutStockType(StockConstants.
				 * LOAD_GOODS_OUT_STOCK_TYPE);
				 * inOutStockEntity.setOperatorCode(loaderParticipation.
				 * getLoaderCode());
				 * inOutStockEntity.setOperatorName(loaderParticipation.
				 * getLoaderName()); this.outstock(inOutStockEntity); }
				 */
			}
			if (wayBill != null) {
				loadGoodsDetailDto = new LoadGoodsDetailDto();
				loadGoodsDetailDto.setBeJoinCar(FossConstants.NO);
				loadGoodsDetailDto.setWayBillNo(wayBill.getWaybillNo());
				loadGoodsDetailDto.setTaskNo(loadTask.getTaskNo());
				List<PDAGoodsSerialNoDto> serialNos = new ArrayList<PDAGoodsSerialNoDto>();
				PDAGoodsSerialNoDto serialNo = new PDAGoodsSerialNoDto();
				serialNo.setIsUnPacking(FossConstants.NO);
				serialNo.setIsToDoList(FossConstants.NO);
				if (goodsStateDto != null && goodsStateDto.getInStockTime() != null) {
					serialNo.setStockAreaCode(goodsStateDto.getGoodsAreaCode());
					loadGoodsDetailDto.setStockTime(goodsStateDto.getInStockTime());
					loadGoodsDetailDto.setStockQty(goodsStateDto.getStockQty());
				} else {
					if (TransferPDADictConstants.LOAD_GOODS_STATE_NORMAL.equals(goodsState)) {
						loadGoodsDetailDto.setBeJoinCar(FossConstants.YES);
					}
				}
				if (TransferPDADictConstants.LOAD_GOODS_STATE_EXTRA_UNPACKAGE.equals(goodsState)) {
					serialNo.setIsUnPacking(FossConstants.YES);
				} else if (TransferPDADictConstants.LOAD_GOODS_STATE_EXTRA_MODIFY.equals(goodsState)) {
					serialNo.setIsToDoList(FossConstants.YES);
				}
				serialNo.setSerialNo(scanRecord.getSerialNo());
				serialNos.add(serialNo);
				loadGoodsDetailDto.setSerialNos(serialNos);
				loadGoodsDetailDto.setGoodsName(wayBill.getGoodsName());
				loadGoodsDetailDto.setPacking(wayBill.getGoodsPackage());
				loadGoodsDetailDto.setReachOrgCode(wayBill.getCustomerPickupOrgCode());
				loadGoodsDetailDto.setReceiveOrgCode(wayBill.getReceiveOrgCode());
				loadGoodsDetailDto.setReachOrgName(orgAdministrativeInfoService
						.queryOrgAdministrativeInfoNameByCode(wayBill.getCustomerPickupOrgCode()));
				loadGoodsDetailDto.setReceiveOrgName(
						orgAdministrativeInfoService.queryOrgAdministrativeInfoNameByCode(wayBill.getReceiveOrgCode()));
				loadGoodsDetailDto.setStockQty(0);
				loadGoodsDetailDto.setWayBillQty(wayBill.getGoodsQtyTotal());
				loadGoodsDetailDto.setWeight(wayBill.getGoodsWeightTotal().doubleValue());
				loadGoodsDetailDto.setVolume(wayBill.getGoodsVolumeTotal().doubleValue());
				loadGoodsDetailDto.setBePriorityGoods(FossConstants.NO);
				loadGoodsDetailDto.setIsValue(wayBill.getPreciousGoods());
				if (StringUtils.isNotBlank(wayBill.getCustomerPickupOrgCode())) {
					SaleDepartmentEntity saleDetp = saleDepartmentService
							.querySaleDepartmentByCode(wayBill.getCustomerPickupOrgCode());
					if (saleDetp != null) {
						loadGoodsDetailDto.setStationNumber(saleDetp.getStationNumber());
					} else {
						OuterBranchEntity outBranch = vehicleAgencyDeptService
								.queryOuterBranchByBranchCode(wayBill.getCustomerPickupOrgCode(), null);
						if (outBranch != null) {
							loadGoodsDetailDto.setStationNumber(outBranch.getStationNumber());
						}
					}
				}
				if (product != null) {
					loadGoodsDetailDto.setTransportType(product.getName());
				}
			}
		} else {
			LOGGER.error("装车扫描结束(无效任务)" + " " + scanRecord.getType() + " " + scanRecord.getTaskNo() + " "
					+ scanRecord.getWayBillNo() + " " + scanRecord.getSerialNo());
			//throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_INVALID_TASK_MESSAGECODE);
		}
		// 成功
		LOGGER.info("装车扫描结束" + " " + scanRecord.getType() + " " + scanRecord.getTaskNo() + " "
				+ scanRecord.getWayBillNo() + " " + scanRecord.getSerialNo());
		return loadGoodsDetailDto;
	}

	/**
	 * 根据货物类型判断是否装车
	 * 
	 * @param goodsType
	 * @return
	 */
	private String beLoaded(String goodsType, boolean beVerifyExtraLoad) {
		// 装车货物类型 正常
		if (TransferPDADictConstants.LOAD_GOODS_STATE_NORMAL.equals(goodsType)) {
			return FossConstants.YES;
			// 强装-有更改
		} else if (TransferPDADictConstants.LOAD_GOODS_STATE_MORE_ALLOPATRY_ENTRAINED.equals(goodsType)) {
			return FossConstants.YES;
			//// 多货-夹带
		} else if (TransferPDADictConstants.LOAD_GOODS_STATE_MORE_ENTRAINED.equals(goodsType)) {
			return FossConstants.YES;
		} else if (TransferPDADictConstants.LOAD_GOODS_STATE_EXTRA_UNPACKAGE.equals(goodsType) && !beVerifyExtraLoad) {
			return FossConstants.YES;
		} else if (TransferPDADictConstants.LOAD_GOODS_STATE_EXTRA_MODIFY.equals(goodsType) && !beVerifyExtraLoad) {
			return FossConstants.YES;
		} else if (TransferPDADictConstants.LOAD_GOODS_STATE_EXTRA_PACKAGE_UNOUT_STORAGE.equals(goodsType)
				&& !beVerifyExtraLoad) {
			return FossConstants.YES;
		} else if (TransferPDADictConstants.LOAD_GOODS_STATE_EXTRA_VALUABLES_UNOUT_STORAGE.equals(goodsType)
				&& !beVerifyExtraLoad) {
			return FossConstants.YES;
		} else if (TransferPDADictConstants.LOAD_GOODS_STATE_EXTRA.equals(goodsType) && !beVerifyExtraLoad) {
			return FossConstants.YES;
		} else if (TransferPDADictConstants.LOAD_GOODS_STATE_EXTRA_ENTRAINED.equals(goodsType) && !beVerifyExtraLoad) {
			return FossConstants.YES;
		} else if (TransferPDADictConstants.LOAD_GOODS_STATE_EXTRA_ALLOPATRY_ENTRAINED.equals(goodsType)
				&& !beVerifyExtraLoad) {
			return FossConstants.YES;
		} else if (TransferPDADictConstants.LOAD_GOODS_STATE_EXTRA_UNPREWIRED.equals(goodsType) && !beVerifyExtraLoad) {
			return FossConstants.YES;
		} else {
			// 否
			return FossConstants.NO;
		}
	}

	/**
	 * 若PDA返回的货物类型为正常，则判断货物类型是正常还是强装有更改
	 * 
	 * 如果货物为多货，则判断货物是否已签收或已作废
	 * 
	 * @param wayBillNo
	 * @param serialNo
	 * @param goodsType
	 * @param origOrgCode
	 * @return
	 */
	private GoodsStateDto getGoodsState(WaybillEntity wayBill, String serialNo, String goodsType, String origOrgCode,
			LoadTaskEntity loadTask) {
		// 货物状态为正常，需判断是否有代办事项，如果存在代办事项，则为强装-有更改
		String goodsState = null;
		GoodsStateDto result = new GoodsStateDto();
		if (LoadConstants.LOAD_GOODS_STATE_MORE.equals(goodsType)) {// 如果货物为多货，则判断货物是否已签收或已作废
			if (wayBill == null) {
				// 无效
				goodsState = TransferPDADictConstants.LOAD_GOODS_STATE_INVALID;
				result.setGoodsState(goodsState);
				return result;
			}
			// 查询标签是否有效
			Map<String, String> condition = new HashMap<String, String>();
			// 运单号
			condition.put("wayBillNo", wayBill.getWaybillNo());
			// 流水号
			condition.put("serialNo", serialNo);
			// 有效状态
			condition.put("active", FossConstants.ACTIVE);
			// 查询货签是否有效
			int valideLabeledCount = pdaLoadDao.queryValidLabeledCount(condition);
			// 查询货物是否已签收
			int signedQty = pdaLoadDao.queryWayBillSignedState(condition);
			// 货签无效或已签收，则货物状态为无效
			if (valideLabeledCount <= 0 || signedQty > 0) {
				// 无效
				goodsState = TransferPDADictConstants.LOAD_GOODS_STATE_INVALID;
				result.setGoodsState(goodsState);
				return result;
			}

			// 多货状态查询
			// 未装车件数
			int needloadQty = LoadConstants.MAX_SERIAL_NUM;
			// 查询库存
			int stockQty = pdaLoadDao.queryWayBillStockQty(wayBill.getWaybillNo(), loadTask.getOrigOrgCode());
			StockEntity stock = null;
			if (stockQty > 0) {
				result.setStockQty(stockQty);
				stock = stockService.queryUniqueStock(wayBill.getWaybillNo(), serialNo);
				if (stock != null && loadTask.getOrigOrgCode().equals(stock.getOrgCode())) {
					result.setInStockTime(stock.getInStockTime());
					result.setGoodsAreaCode(stock.getGoodsAreaCode());
				}
			}
			// 处理派送装车扫描多货记录
			if (LoadConstants.LOAD_TASK_TYPE_DELIVER.equals(loadTask.getTaskType())) {
				result.setStockQty(
						pdaLoadDao.queryDeliverArrangeQty(loadTask.getDeliverBillNo(), wayBill.getWaybillNo()));
				// 未装车件数
				needloadQty = pdaLoadDao.stockQtyIsBiggerThanLoadQty(loadTask.getId(), loadTask.getDeliverBillNo(),
						wayBill.getWaybillNo());
				// 若货物在本部门库存,则货物状态为强装-未预配
				if (stock != null && loadTask.getOrigOrgCode().equals(stock.getOrgCode())) {
					if (needloadQty > 0) {
						goodsState = TransferPDADictConstants.LOAD_GOODS_STATE_NORMAL;
					} else {
						goodsState = TransferPDADictConstants.LOAD_GOODS_STATE_EXTRA_UNPREWIRED;
					}
				} else {// 若货物不在本部门
						// 查询走货路径
					try {
						FeedbackDto feedbackDto = calculateTransportPathService
								.getNextOrgAndTime(wayBill.getWaybillNo(), serialNo, loadTask.getOrigOrgCode());
						// 货物状态默认为强装-异地夹带
						if (feedbackDto != null && feedbackDto.getResult() != TransportPathConstants.STATUS_WRONG) {// 若装车部门在货物走货路径上，则为强装-夹带
							if (needloadQty > 0) {
								goodsState = TransferPDADictConstants.LOAD_GOODS_STATE_MORE_ENTRAINED;
							} else {
								goodsState = TransferPDADictConstants.LOAD_GOODS_STATE_EXTRA_ENTRAINED;
							}
						} else {
							if (needloadQty > 0) {
								goodsState = TransferPDADictConstants.LOAD_GOODS_STATE_MORE_ALLOPATRY_ENTRAINED;
							} else {
								goodsState = TransferPDADictConstants.LOAD_GOODS_STATE_EXTRA_ALLOPATRY_ENTRAINED;
							}
						}
					} catch (Exception e) {
						throw new TfrBusinessException(
								TransferPDAExceptionCode.EXCEPTION_QUERY_TRANSPORT_PATH_FAILURE_MESSAGECODE);
					}
				}

			} else {// 处理非派送装车扫描记录
					// 处理多货
				List<String> destOrgCodes = null;
				if (TransferPDADictConstants.LOAD_TASK_TYPE_PARTIALLINE.equals(loadTask.getTaskType())) {
					destOrgCodes = this.queryOuterBranchCodesByTaskId(loadTask.getId());
				} else if (TransferPDADictConstants.LOAD_TASK_TYPE_LDP.equals(loadTask.getTaskType())) {// 快递:落地配装车
					destOrgCodes = this.queryLDPDeptCodesByTaskId(loadTask.getId());
				} else {
					destOrgCodes = pdaLoadDao.queryLoadDestOrgCodesById(loadTask.getId());
				}
				// 空运
				destOrgCodes.add(StockConstants.AIR_FREIGHT_NEXT_ORG_CODE);
				// 若货物在本部门库存
				if (stock != null && loadTask.getOrigOrgCode().equals(stock.getOrgCode())) {
					boolean beNormal = false;
					// stockTime = stock.getCreateDate();
					// 判断货物的下一到达部门是否为装车到达部门
					for (String destOrgCode : destOrgCodes) {
						// 如果库存下一部门为装车到达部门,则货物状态为正常
						if (destOrgCode.equals(stock.getNextOrgCode())) {
							beNormal = true;
						}
					}
					if (!beNormal) {
						// 如果货物库存下一部门不为装车到达部门
						// 查询合车表
						Map<String, String> queryTogetherDestQC = new HashMap<String, String>();
						queryTogetherDestQC.put("wayBillNo", wayBill.getWaybillNo());
						queryTogetherDestQC.put("serialNo", serialNo);
						queryTogetherDestQC.put("orgCode", loadTask.getOrigOrgCode());
						List<String> togetherOrgCodes = pdaLoadDao.queryTogetherDestOrgCodes(queryTogetherDestQC);
						if (CollectionUtils.isNotEmpty(togetherOrgCodes)) {// 如果存在合车记录
							for (String orgCode : destOrgCodes) {
								for (String togetherOrgCode : togetherOrgCodes) {
									if (orgCode.equals(togetherOrgCode)) {// 如果合车到达部门为装车到达部门
										beNormal = true;
										// beJoinCar = true;
									}
								}
							}
						}
					}
					if (beNormal) {// 如果合车到达部门为装车到达部门，货物类型为正常
						goodsState = TransferPDADictConstants.LOAD_GOODS_STATE_NORMAL;
						try {
							// 是否有更改单未受理
							List<String> todoItems = waybillRfcService.queryTodoWhenLoadTruck(wayBill.getWaybillNo(),
									serialNo, loadTask.getOrigOrgCode());
							if (CollectionUtils.isNotEmpty(todoItems)) {
								goodsState = TransferPDADictConstants.LOAD_GOODS_STATE_EXTRA_MODIFY;
							} else {
								boolean keepOn = true;
								// 是否未打包装
								if (pdaLoadDao.queryBeUnpack(wayBill.getWaybillNo(), serialNo,
										loadTask.getOrigOrgCode())) {
									goodsState = TransferPDADictConstants.LOAD_GOODS_STATE_EXTRA_UNPACKAGE;
								} else {
									if (StringUtils.isNotBlank(stock.getGoodsAreaCode())) {
										// 查询异常库区
										List<GoodsAreaEntity> exceptionGoodsAreaList = goodsAreaService
												.queryGoodsAreaListByType(loadTask.getOrigOrgCode(),
														DictionaryValueConstants.BSE_GOODSAREA_TYPE_EXCEPTION);
										if (CollectionUtils.isNotEmpty(exceptionGoodsAreaList)) {
											for (GoodsAreaEntity area : exceptionGoodsAreaList) {
												if (stock.getGoodsAreaCode().equals(area.getGoodsAreaCode())) {
													throw new TfrBusinessException(
															TransferPDAExceptionCode.EXCEPTION_INVALID_LABELED_GOODS_MESSAGECODE);
												}
											}
										}
										// 查询贵重物品库区
										List<GoodsAreaEntity> valueGoodsAreaList = goodsAreaService
												.queryGoodsAreaListByType(loadTask.getOrigOrgCode(),
														DictionaryValueConstants.BSE_GOODSAREA_TYPE_VALUABLE);
										if (CollectionUtils.isNotEmpty(valueGoodsAreaList)) {
											for (GoodsAreaEntity area : valueGoodsAreaList) {
												if (stock.getGoodsAreaCode().equals(area.getGoodsAreaCode())) {
													goodsState = TransferPDADictConstants.LOAD_GOODS_STATE_EXTRA_VALUABLES_UNOUT_STORAGE;
													keepOn = false;
												}
											}
										}
										if (keepOn) {
											// 查询包装库区
											List<GoodsAreaEntity> packGoodsAreaList = goodsAreaService
													.queryGoodsAreaListByType(loadTask.getOrigOrgCode(),
															DictionaryValueConstants.BSE_GOODSAREA_TYPE_PACKING);
											if (CollectionUtils.isNotEmpty(packGoodsAreaList)) {
												for (GoodsAreaEntity area : packGoodsAreaList) {
													if (stock.getGoodsAreaCode().equals(area.getGoodsAreaCode())) {
														goodsState = TransferPDADictConstants.LOAD_GOODS_STATE_EXTRA_PACKAGE_UNOUT_STORAGE;
														keepOn = false;
													}
												}
											}
										}
									}
								}
							}
						} catch (Exception e) {
							LOGGER.error("判断货物状态失败", e);
							// 查询库存货物状态失败
							throw new TfrBusinessException(
									TransferPDAExceptionCode.EXCEPTION_QUERY_STOCK_GOODS_STATE_FAILURE_MESSAGECODE);
						}
					} else {// 如果合车到达部门不为装车到达部门，货物类型为强装
						goodsState = TransferPDADictConstants.LOAD_GOODS_STATE_EXTRA;
					}

				} else {// 若货物不在本部门库存
					FeedbackDto feedbackDto = null;
					// 是否夹带
					boolean beAllopatryEnained = true;
					// 是否强装
					boolean beExtra = true;
					try {
						// 查询走货路径
						feedbackDto = calculateTransportPathService.getNextOrgAndTime(wayBill.getWaybillNo(), serialNo,
								loadTask.getOrigOrgCode());
					} catch (Exception e) {
						LOGGER.error("查询中转走货路径失败" + wayBill.getWaybillNo() + serialNo + loadTask.getOrigOrgCode(), e);
					}
					if (feedbackDto != null && feedbackDto.getResult() == TransportPathConstants.STATUS_RIGHT) {// 如果装车部门不在走货路径上，则为异地夹带
						beAllopatryEnained = false;
						// 空运不校验走货路径
						if (WaybillConstants.AIR_FLIGHT.equals(wayBill.getProductCode())) {
							beExtra = false;
						} else {
							for (String destOrgCode : destOrgCodes) {
								// 若走货路径下一部门在装车部门中，则不为强装
								if (destOrgCode.equals(feedbackDto.getPathDetailEntity().getObjectiveOrgCode())) {
									beExtra = false;
								}
							}
						}
					} else {// 如果装车部门不在走货路径上，则为夹带
							// 重新计算走货路径判断是否强装
						if (WaybillConstants.AIR_FLIGHT.equals(wayBill.getProductCode())) {
							beExtra = false;
						} else {
							try {
								// WaybillEntity wayBill =
								// waybillManagerService.queryWaybillBasicByNo(wayBillNo);
								List<FreightRouteLineDto> freightRouteLineDtoList = freightRouteService
										.queryFreightRouteBySourceTarget(loadTask.getOrigOrgCode(),
												wayBill.getCustomerPickupOrgCode(), wayBill.getProductCode(),
												new Date());
								if (CollectionUtils.isEmpty(freightRouteLineDtoList)) {
									beExtra = true;
								} else {
									for (FreightRouteLineDto path : freightRouteLineDtoList) {
										for (String destOrgCode : destOrgCodes) {
											// 若走货路径下一部门在装车部门中，则不为强装
											if (destOrgCode.equals(path.getTargetCode())) {
												beExtra = false;
											}
										}
									}
								}
							} catch (Exception e) {
								LOGGER.error(
										"查询综合走货路径失败" + wayBill.getWaybillNo() + serialNo + loadTask.getOrigOrgCode(),
										e);
							}
						}
					}
					if (beExtra) {
						if (beAllopatryEnained) {
							goodsState = TransferPDADictConstants.LOAD_GOODS_STATE_EXTRA_ALLOPATRY_ENTRAINED;
						} else {
							goodsState = TransferPDADictConstants.LOAD_GOODS_STATE_EXTRA_ENTRAINED;
						}
					} else {
						if (beAllopatryEnained) {
							goodsState = TransferPDADictConstants.LOAD_GOODS_STATE_MORE_ALLOPATRY_ENTRAINED;
						} else {
							goodsState = TransferPDADictConstants.LOAD_GOODS_STATE_MORE_ENTRAINED;
						}
					}
				}
			}
		} else {
			goodsState = goodsType;
		}
		// 返回结果
		result.setGoodsState(goodsState);
		return result;
	}

	/**
	 * 派送装车生成少货记录
	 * 
	 * @param taskNo
	 * @param origOrgCode
	 * @param taskId
	 * @param loadBeginTime
	 * @return
	 */
	public void deliverLoadCreateLackGoods(String taskNo, String origOrgCode, String taskId, Date loadBeginTime) {
		Map<String, String> lackWayBillQC = new HashMap<String, String>();
		// 任务号
		lackWayBillQC.put("taskNo", taskNo);
		// 查询少货运单
		List<DeliverLoadGapReportWayBillEntity> lackWayBills = pdaLoadDao.queryDeliverLoadLackWayBill(lackWayBillQC);
		for (DeliverLoadGapReportWayBillEntity lackWayBill : lackWayBills) {
			String loadWayBillDetailId;
			LoadWaybillDetailEntity loadWayBillEntity;
			// 少货运单id不为空
			if (StringUtils.isNotBlank(lackWayBill.getLoadWayBillDetailId())) {
				loadWayBillDetailId = lackWayBill.getLoadWayBillDetailId();
				if (lackWayBill.getStockQty() != lackWayBill.getPlanLoadQty()) {
					loadWayBillEntity = new LoadWaybillDetailEntity();
					loadWayBillEntity.setId(loadWayBillDetailId);
					// 设置 库存件数
					loadWayBillEntity.setStockQty(lackWayBill.getPlanLoadQty());
					loadWayBillEntity.setLoadQty(0);
					loadWayBillEntity.setScanQty(0);
					loadWayBillEntity.setLoadVolumeTotal(0);
					loadWayBillEntity.setLoadWeightTotal(0);
					pdaLoadDao.updateLoadTaskWayBillDetail(loadWayBillEntity);
				}
			} else {
				// 如果不存在装车运运单明细，则插入
				// 不会修改装车运单 明细，因为少货不会修改已存在的装车运单明细
				loadWayBillEntity = new LoadWaybillDetailEntity();
				// id
				loadWayBillDetailId = UUIDUtils.getUUID();
				// 设置id
				loadWayBillEntity.setId(loadWayBillDetailId);
				// 设置 loadTask_ID
				loadWayBillEntity.setLoadTaskId(taskId);
				// 设置 已装车件数
				loadWayBillEntity.setLoadQty(0);
				// 设置 已扫描件数
				loadWayBillEntity.setScanQty(0);
				// 设置 库存件数
				loadWayBillEntity.setStockQty(lackWayBill.getPlanLoadQty());
				// 设置 装车体积
				loadWayBillEntity.setLoadVolumeTotal(0);
				// 设置 装车重量
				loadWayBillEntity.setLoadWeightTotal(0);
				// 设置 出发部门编号
				loadWayBillEntity.setOrigOrgCode(origOrgCode);
				// 设置 运单号
				loadWayBillEntity.setWaybillNo(lackWayBill.getWayBillNo());
				// 设置 建立任务时间
				loadWayBillEntity.setTaskBeginTime(loadBeginTime);
				// 设置 运输性质
				loadWayBillEntity.setTransportType(lackWayBill.getTransportType());
				// 设置 收货部门
				loadWayBillEntity.setReceiveOrgName(lackWayBill.getReceiveOrgName());
				// 设置 到达部门
				loadWayBillEntity.setReachOrgName(lackWayBill.getReachOrgName());
				// 设置 包装
				loadWayBillEntity.setPack(lackWayBill.getPack());
				// 设置 货名
				loadWayBillEntity.setGoodsName(lackWayBill.getGoodsName());
				pdaLoadDao.insertLoadWayBillDetailEntity(loadWayBillEntity);
			}
			int stockUnloadQty = lackWayBill.getLackGoodsQty();
			if (StringUtils.isNotBlank(lackWayBill.getLoadWayBillDetailId())) {
				// 查询已扫描未装车流水号
				/*
				 * List<String> scanUnloadState = new ArrayList<String>();
				 * scanUnloadState.add(TransferPDADictConstants.
				 * LOAD_GOODS_STATE_CANCELED);
				 * scanUnloadState.add(LoadConstants.
				 * LOAD_GOODS_STATE_NOT_LOADING);
				 */
				List<LoadSerialNoEntity> scanedUnloadSerialNos = pdaLoadDao
						.queryScanedUnloadSerialNos(loadWayBillDetailId);
				// 遍历扫描未装车流水号
				if (CollectionUtils.isNotEmpty(scanedUnloadSerialNos)) {
					stockUnloadQty = stockUnloadQty - scanedUnloadSerialNos.size();
					for (LoadSerialNoEntity loadSerialNo : scanedUnloadSerialNos) {
						int i = 0;
						i++;
						if (i <= lackWayBill.getLackGoodsQty()) {
							loadSerialNo.setBeLoaded(FossConstants.NO);
							//// 未装车
							loadSerialNo.setGoodsState(LoadConstants.LOAD_GOODS_STATE_NOT_LOADING);
							pdaLoadDao.updateLoadTaskSerialNo(loadSerialNo);
						} else {
							if (LoadConstants.LOAD_GOODS_STATE_NOT_LOADING.equals(loadSerialNo.getGoodsState())) {
								loadSerialNo.setBeLoaded(FossConstants.NO);
								// 未扫描
								loadSerialNo.setGoodsState(TransferPDADictConstants.LOAD_GOODS_STATE_CANCELED);
								pdaLoadDao.updateLoadTaskSerialNo(loadSerialNo);
							}
						}
					}
				}
			}

			// 查询库存少货流水号
			if (stockUnloadQty > 0) {
				List<LoadSerialNoEntity> stockUnloadSerialNos = pdaLoadDao.queryStockUnloadSerialNos(
						lackWayBill.getWayBillNo(), origOrgCode, loadWayBillDetailId, stockUnloadQty);
				if (CollectionUtils.isNotEmpty(stockUnloadSerialNos)) {
					for (LoadSerialNoEntity loadSerialNo : stockUnloadSerialNos) {
						loadSerialNo.setBeLoaded(FossConstants.NO);
						//// 未装车
						loadSerialNo.setGoodsState(LoadConstants.LOAD_GOODS_STATE_NOT_LOADING);
						// 主键id
						loadSerialNo.setId(UUIDUtils.getUUID());
						// 明细id
						loadSerialNo.setLoadWaybillDetailId(loadWayBillDetailId);
						loadSerialNo.setCreateTime(new Date());
						loadSerialNo.setOrigOrgCode(origOrgCode);
						loadSerialNo.setScanState("N/A");
						loadSerialNo.setTaskBeginTime(loadBeginTime);
						// 插入装车运单明细记录
						pdaLoadDao.insertLoadSerialNoEntity(loadSerialNo);
					}
				}
			}
			/*
			 * Map<String,Object> lackSerialQC = new HashMap<String,Object>();
			 * //运单明细id lackSerialQC.put("loadWayBillDetailId",
			 * lackWayBill.getLoadWayBillDetailId()); //运单号
			 * lackSerialQC.put("wayBillNo", lackWayBill.getWayBillNo()); //少货件数
			 * lackSerialQC.put("rowNum", lackWayBill.getLackGoodsQty()); //取消
			 * lackSerialQC.put("goodsType",
			 * TransferPDADictConstants.LOAD_GOODS_STATE_CANCELED);
			 * lackSerialQC.put("goodsType1",
			 * LoadConstants.LOAD_GOODS_STATE_NOT_LOADING); //原始部门
			 * lackSerialQC.put("origOrgCode", origOrgCode); //查询少货流水号
			 * List<LoadSerialNoEntity> loadSerialNos =
			 * pdaLoadDao.queryDeliverLoadLackSerial(lackSerialQC); //遍历少货流水号
			 * for(LoadSerialNoEntity loadSerialNo : loadSerialNos){
			 * loadSerialNo.setBeLoaded(FossConstants.NO); ////未装车
			 * loadSerialNo.setGoodsState(LoadConstants.
			 * LOAD_GOODS_STATE_NOT_LOADING); //如果已经存在少货（被删除的扫描记录），则更新状态
			 * if(StringUtils.isNotBlank(loadSerialNo.getId())){
			 * pdaLoadDao.updateLoadTaskSerialNo(loadSerialNo);
			 * //如果不存在扫描记录，则新增少货记录 }else{ //主键id
			 * loadSerialNo.setId(UUIDUtils.getUUID()); //明细id
			 * loadSerialNo.setLoadWaybillDetailId(loadWayBillDetailId); //
			 * 插入装车运单明细记录 pdaLoadDao.insertLoadSerialNoEntity(loadSerialNo); } }
			 */
		}
	}

	/**
	 * make根据货物状态查询装车货物查询条件
	 * 
	 * @param taskId
	 * @param goodsStates
	 * @return
	 */
	private Map<String, Object> makeQueryLoadGoodsByGooodsStateQC(String taskId, List<String> goodsStates) {
		Map<String, Object> condition = new HashMap<String, Object>();
		// 任务id
		condition.put("taskId", taskId);
		// 货物状态
		condition.put("goodsStates", goodsStates);
		// 返回条件
		return condition;
	}
	/**
	 * 派送多货处理
	 * 
	 * @param taskNo
	 * @param origOrgCode
	 * @param taskId
	 */
	/*
	 * public void deliverMoreGoodsHandler(String taskNo,String
	 * origOrgCode,String taskId){ List<String> goodsStates = new
	 * ArrayList<String>(); //查询多货
	 * goodsStates.add(LoadConstants.LOAD_GOODS_STATE_MORE); //查询条件
	 * Map<String,Object> condition =
	 * this.makeQueryLoadGoodsByGooodsStateQC(taskId, goodsStates);
	 * //根据条件查询查询货物状态 List<LoadTaskSerialNoDto> moreGoodsList =
	 * pdaLoadDao.queryGoodsByGoodsStates(condition); //如果多货列表不为空
	 * if(CollectionUtils.isNotEmpty(moreGoodsList)){ String goodsState;
	 * //遍历多货列表 for(LoadTaskSerialNoDto moreGoods : moreGoodsList){ boolean
	 * beLoaded = false; if(moreGoods.getLoadQty() < moreGoods.getStockQty()){
	 * beLoaded = true; //遍历多货列表 for(LoadTaskSerialNoDto goods : moreGoodsList){
	 * if(moreGoods.getWayBillNo().equals(goods.getWayBillNo())){ //装车件数
	 * goods.setLoadQty(goods.getLoadQty()+1); } } } //查询库存 StockEntity stock =
	 * stockService.queryUniqueStock(moreGoods.getWayBillNo(),
	 * moreGoods.getSerialNo()); //若货物在本部门库存,则货物状态为强装-未预配 if(stock != null &&
	 * origOrgCode.equals(stock.getOrgCode())){ if(beLoaded){ //装车货物类型-正常
	 * goodsState = TransferPDADictConstants.LOAD_GOODS_STATE_NORMAL; }else{
	 * //强装-未预配 goodsState =
	 * TransferPDADictConstants.LOAD_GOODS_STATE_EXTRA_UNPREWIRED; } //若货物不在本部门
	 * }else{ //查询走货路径 if(beLoaded){ ////多货-异地夹带 goodsState =
	 * TransferPDADictConstants.LOAD_GOODS_STATE_MORE_ALLOPATRY_ENTRAINED;
	 * }else{ //强装-异地夹带 goodsState =
	 * TransferPDADictConstants.LOAD_GOODS_STATE_EXTRA_ALLOPATRY_ENTRAINED; }
	 * try{ FeedbackDto feedbackDto =
	 * calculateTransportPathService.getNextOrgAndTime(moreGoods.getWayBillNo(),
	 * moreGoods.getSerialNo(), origOrgCode); //货物状态默认为强装-异地夹带 if(feedbackDto !=
	 * null && feedbackDto.getResult()!=TransportPathConstants.STATUS_WRONG){//
	 * 若装车部门在货物走货路径上，则为强装-夹带 if(beLoaded){ //多货-夹带 goodsState =
	 * TransferPDADictConstants.LOAD_GOODS_STATE_MORE_ENTRAINED; }else{ //多货-夹带
	 * goodsState = TransferPDADictConstants.LOAD_GOODS_STATE_EXTRA_ENTRAINED; }
	 * } }catch(Exception e){ //日志 LOGGER.error("修改走货路径", e); } //入库多货货物
	 * InOutStockEntity inOutStockEntity = new InOutStockEntity(); //运单号
	 * inOutStockEntity.setWaybillNO(moreGoods.getWayBillNo()); //流水号
	 * inOutStockEntity.setSerialNO(moreGoods.getSerialNo()); //部门号
	 * inOutStockEntity.setOrgCode(origOrgCode); //装车多货
	 * inOutStockEntity.setInOutStockType(StockConstants.
	 * LOAD_MORE_GOODS_IN_STOCK_TYPE); //空
	 * inOutStockEntity.setOperatorCode("N/A"); //空
	 * inOutStockEntity.setOperatorName("N/A"); //调用库存服务
	 * stockService.inStockPC(inOutStockEntity); } //处理后的装车流水号明细，更新货物状态
	 * if(beLoaded){ //更新货物状态为正常、多货-夹带、多货-异地夹带
	 * this.updateGoodsStateToBeLoaded(moreGoods, goodsState); }else{
	 * //更新货物状态为强装、强装夹带、强装异地夹带 this.updateGoodsStateToCanntLoad(moreGoods,
	 * goodsState); } } } }
	 */
	/**
	 * 更新货物状态为正常、多货-夹带、多货-异地夹带
	 * 
	 * @param moreGoods
	 * @param goodsType
	 */
	/*
	 * private void updateGoodsStateToBeLoaded(LoadTaskSerialNoDto
	 * moreGoods,String goodsType){ //处理后的装车流水号明细 LoadSerialNoEntity
	 * newLoadSerialNo; //处理后的装车运单明细 LoadWaybillDetailEntity
	 * newLoadWayBillDetail; //更新装车流水号 newLoadSerialNo = new
	 * LoadSerialNoEntity(); //主键id newLoadSerialNo.setId(moreGoods.getId());
	 * //货物类型 newLoadSerialNo.setGoodsState(goodsType); //装车
	 * newLoadSerialNo.setBeLoaded(FossConstants.YES); //更新装车流水号信息
	 * pdaLoadDao.updateLoadTaskSerialNo(newLoadSerialNo); //更新装车运单明细
	 * newLoadWayBillDetail = new LoadWaybillDetailEntity(); //装车体积 double
	 * loadVolumeTotal = moreGoods.getLoadVolumeTotal(); //装车重量 double
	 * loadWeightTotal = moreGoods.getLoadWeightTotal(); //装车重量 int loadQty =
	 * moreGoods.getLoadQty(); if(loadQty <= 0 || loadVolumeTotal <= 0 ||
	 * loadWeightTotal <= 0){ //查询运单开单体积、开单重量 WaybillEntity wayBill =
	 * waybillManagerService.queryWaybillBasicByNo(moreGoods.getWayBillNo());
	 * if(wayBill.getGoodsQtyTotal()!= 0){ loadVolumeTotal =
	 * wayBill.getGoodsVolumeTotal().doubleValue()/wayBill.getGoodsQtyTotal();
	 * loadWeightTotal =
	 * wayBill.getGoodsWeightTotal().doubleValue()/wayBill.getGoodsQtyTotal(); }
	 * }else{ loadVolumeTotal = loadVolumeTotal/loadQty; loadWeightTotal =
	 * loadWeightTotal/loadQty; } //id
	 * newLoadWayBillDetail.setId(moreGoods.getLoadWaybillDetailId());
	 * //newLoadWayBillDetail.setLoadQty(loadQty); //设置 装车体积
	 * newLoadWayBillDetail.setLoadVolumeTotal(loadVolumeTotal); //设置 装车重量
	 * newLoadWayBillDetail.setLoadWeightTotal(loadWeightTotal); //设置 已扫描件数
	 * newLoadWayBillDetail.setScanQty(moreGoods.getScanQty()); //更新装车任务运单明细+增加
	 * pdaLoadDao.updateLoadTaskWayBillDetailAdd(newLoadWayBillDetail); }
	 */
	/**
	 * 更新货物状态为强装、强装夹带、强装异地夹带
	 * 
	 * @param moreGoods
	 * @param goodsType
	 */

	/*
	 * private void updateGoodsStateToCanntLoad(LoadTaskSerialNoDto
	 * moreGoods,String goodsType){ //处理后的装车流水号明细 LoadSerialNoEntity
	 * newLoadSerialNo; //更新装车流水号 newLoadSerialNo = new LoadSerialNoEntity();
	 * //id newLoadSerialNo.setId(moreGoods.getId()); //Sets the 货物状态
	 * newLoadSerialNo.setGoodsState(goodsType); //是否装车
	 * newLoadSerialNo.setBeLoaded(FossConstants.NO); //更新装车流水号信息
	 * pdaLoadDao.updateLoadTaskSerialNo(newLoadSerialNo); }
	 */
	/**
	 * 出库装车货物
	 *
	 *
	 * @param taskId
	 * @param loaderCode
	 */
	public void outStockLoadGoods(String taskId, String loaderCode) {
		// 查询出库货物
		Map<String, String> condition = new HashMap<String, String>();
		condition.put("taskId", taskId);
		condition.put("beLoaded", FossConstants.YES);

		// 查询库存的出库货物
		List<LoadTaskSerialNoDto> outStockGoodsList = pdaLoadDao
				.queryOutStockGoods(condition);
		List<InOutStockEntity> inOutStockList = new ArrayList<InOutStockEntity>();
		// 出库货物
		if (CollectionUtils.isNotEmpty(outStockGoodsList)) {
			for (LoadTaskSerialNoDto outStockGoods : outStockGoodsList) {
				// 记录出入库动作信息
				InOutStockEntity inOutStockEntity = new InOutStockEntity();
				// 设置 运单号
				inOutStockEntity.setWaybillNO(outStockGoods.getWayBillNo());
				// 设置 当前部门
				inOutStockEntity.setOrgCode(outStockGoods.getOrgCode());
				// 设置 流水号
				inOutStockEntity.setSerialNO(outStockGoods.getSerialNo());
				// 装车出库
				inOutStockEntity
						.setInOutStockType(StockConstants.LOAD_GOODS_OUT_STOCK_TYPE);
				// 设置 操作人编号
				inOutStockEntity.setOperatorCode(loaderCode);
				// 设置 操作人姓名
				inOutStockEntity.setOperatorName(loaderCode);
				// 加入出入库动作信息
				inOutStockList.add(inOutStockEntity);
				// stockService.outStockPC(inOutStockEntity);
				LOGGER.error("装车出库:" + outStockGoods.getWayBillNo()
						+ outStockGoods.getSerialNo() + "/");
			}
			// PC端操作批量按照类型出库
			stockService.outStockBatchPCByType(inOutStockList,
					StockConstants.OUT_STOCK_TYPE_LOADING, taskId);
		}

		// 查出虚拟出库货物
		List<LoadTaskSerialNoDto> ptpWaybills = pdaLoadDao
				.querySaleOutStockGoods(condition);
		List<InOutStockEntity> outPtpSaleStock = new ArrayList<InOutStockEntity>();
		if (CollectionUtils.isNotEmpty(ptpWaybills)) {
			for (LoadTaskSerialNoDto outStockGoods : ptpWaybills) {
				// 记录出入库动作信息
				InOutStockEntity inOutStockEntity = new InOutStockEntity();
				// 设置 运单号
				inOutStockEntity.setWaybillNO(outStockGoods.getWayBillNo());
				// 设置 当前部门
				inOutStockEntity.setOrgCode(outStockGoods.getOrgCode());
				// 设置 流水号
				inOutStockEntity.setSerialNO(outStockGoods.getSerialNo());
				// 装车出库
				inOutStockEntity
						.setInOutStockType(StockConstants.LOAD_GOODS_OUT_STOCK_TYPE);
				// 设置 操作人编号
				inOutStockEntity.setOperatorCode(loaderCode);
				// 设置 操作人姓名
				inOutStockEntity.setOperatorName(loaderCode);
				// 加入出入库动作信息
				outPtpSaleStock.add(inOutStockEntity);
				
			}
			
			stockService.outStockSaleStockInfoSale(outPtpSaleStock);
		}
	}

	/**
	 * OA上报多货
	 * 
	 * @param taskId
	 * @param loaderCode
	 * @param origOrgCode
	 * 
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void oaReportMoreGoods(String taskId, String loaderCode, String origOrgCode) {
		ConfigurationParamsEntity defaultBizHourSlice = configurationParamsService.queryConfigurationParamsByOrgCode(
				DictionaryConstants.SYSTEM_CONFIG_PARM__TFR, ConfigurationParamsConstants.TFR_PARM_LOAD_EXC_LATEST_TIME,
				FossConstants.ROOT_ORG_CODE);
		Date bizDate = null;
		if (defaultBizHourSlice != null && StringUtils.isNotEmpty(defaultBizHourSlice.getConfValue())) {
			bizDate = DateUtils.strToDate(defaultBizHourSlice.getConfValue());
		}
		List<OaReportClearMore> oaReportClearMores = pdaLoadDao.queryLoadOaReportMoreGoods(taskId, bizDate);
		if (CollectionUtils.isNotEmpty(oaReportClearMores)) {
			for (OaReportClearMore oaReportClearMore : oaReportClearMores) {
				WaybillEntity waybillEntity = waybillManagerService
						.queryWaybillBasicByNo(oaReportClearMore.getWayBillId());
				oaReportClearMore.setReportTime(Calendar.getInstance().getTime());
				if ("0".equals(oaReportClearMore.getHaveGoodsNoReplay())) {
					oaReportClearMore.setHaveGoodsNoReplay("经手部门发现");
				} else {
					oaReportClearMore.setHaveGoodsNoReplay("非经手部门发现");
				}
				try {
					if (StringUtils.isNotBlank(waybillEntity.getTransportType())) {
						oaReportClearMore
								.setTransportType(dataDictionaryValueService
										.queryDataDictionaryValueByTermsCodeValueCode(
												DictionaryConstants.BSE_TRANS_TYPE, waybillEntity.getTransportType())
										.getValueName()); // 运输类型
					} else {
						ProductEntity entity = productService4Dubbo.getProductLele(waybillEntity.getProductCode(), null, 1);
						if (entity != null) {
							oaReportClearMore.setTransportType(entity.getName()); // 运输类型
						}

					}
					oaReportClearMore.setReturnBillType(waybillEntity.getReturnBillType());
					oaReportClearMore.setShipper(waybillEntity.getDeliveryCustomerName());
					ProductEntity product = productService4Dubbo.getProductByCache(waybillEntity.getProductCode(), null);
					if (product != null) {
						// 产品类型
						oaReportClearMore.setTransportProduct(product.getName());
					}
					DataDictionaryValueEntity d = dataDictionaryValueService
							.queryDataDictionaryValueByTermsCodeValueCode(DictionaryConstants.ASSEMBLE_TYPE,
									waybillEntity.getLoadMethod());
					if (d != null) {
						// 配载类型
						oaReportClearMore.setStowageType(d.getValueName());
					}
					oaReportClearMore.setReceiverTel(waybillEntity.getReceiveCustomerPhone());
					d = dataDictionaryValueService.queryDataDictionaryValueByTermsCodeValueCode(
							DictionaryConstants.PICKUP_GOODS, waybillEntity.getReceiveMethod());
					if (d != null) {
						// 提货方式
						oaReportClearMore.setGroupSendFlag(d.getValueName());
					}
					// 设置 储运事项
					oaReportClearMore.setRemark(waybillEntity.getTransportationRemark());
					// 设置 毛重
					oaReportClearMore.setWeight(waybillEntity.getGoodsWeightTotal().doubleValue());
					// 设置 体积
					oaReportClearMore.setVloume(waybillEntity.getGoodsVolumeTotal().doubleValue());
					// 设置 货物名称
					oaReportClearMore.setGoods(waybillEntity.getGoodsName());
					// 设置 发货时间
					oaReportClearMore.setSendTime(com.deppon.foss.util.DateUtils
							.convert(waybillEntity.getPreDepartureTime(), "yyyy-MM-dd HH:mm:ss"));
					// 设置 目的站
					oaReportClearMore.setDestination(orgAdministrativeInfoService
							.queryOrgAdministrativeInfoNameByCode(waybillEntity.getTargetOrgCode()));
					// 设置 收货人
					oaReportClearMore.setReceiver(waybillEntity.getReceiveCustomerName());
					// 收货部门
					oaReportClearMore.setReceivingDept(orgAdministrativeInfoService
							.queryOrgAdministrativeInfoNameByCode(waybillEntity.getReceiveOrgCode()));
					// 设置 收货部门ORGID.
					oaReportClearMore.setReceivingDeptID(waybillEntity.getReceiveOrgCode());
					// 根据 TERMS_CODE,VALUE_CODE 查询值对象
					d = dataDictionaryValueService.queryDataDictionaryValueByTermsCodeValueCode(
							DictionaryConstants.PAYMENT_MODE, waybillEntity.getPaidMethod());
					if (d != null) {
						// 付款方式
						oaReportClearMore.setPayType(d.getValueName());
					}
					// 设置 保险金额
					oaReportClearMore.setInsuranceMoney(waybillEntity.getInsuranceFee().doubleValue());
					// 包装
					oaReportClearMore.setGoodsPacking(waybillEntity.getGoodsPackage());
					// 设置 运费总额.
					oaReportClearMore.setTotal(waybillEntity.getTotalFee().doubleValue());
					// 设置 事件经过
					oaReportClearMore.setEventReport("装车上报");
					// 设置 多货类型
					oaReportClearMore.setMoreGoodsType("装车多货");
					// 所属大区或者责任事业部标杆编码
					oaReportClearMore.setResponsibleDept(
							orgAdministrativeInfoService.queryOrgAdministrativeInfoNameByCode(origOrgCode));
					// 责任部门
					oaReportClearMore.setResponsibleDeptId(origOrgCode);
					// 设置 开单部门编号
					oaReportClearMore.setSheetDept(orgAdministrativeInfoService
							.queryOrgAdministrativeInfoNameByCode(waybillEntity.getCreateOrgCode())); // 开单部门
					// 设置 开单部门编号
					oaReportClearMore.setSheetDeptId(waybillEntity.getCreateOrgCode());
					// 上报多货差错（清仓多货，装车多货，卸车多货）
					fossToOAService.reportMoreGoods(oaReportClearMore);
				} catch (Exception e) {
					LOGGER.error("上报OA多货失败" + waybillEntity.getWaybillNo(), e);
				}
			}
		}
	}

	/**
	 * 入库派送装车取消扫描货物
	 * 
	 * @param loadGoodsDetails
	 * 
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void inStockCanceledDeliverScan(String taskId, String orgCode, String loaderCode, String loaderName) {
		List<String> goodsStates = new ArrayList<String>();
		// 查询多货
		goodsStates.add(TransferPDADictConstants.LOAD_GOODS_STATE_CANCELED);
		Map<String, Object> condition = this.makeQueryLoadGoodsByGooodsStateQC(taskId, goodsStates);
		List<LoadTaskSerialNoDto> moreGoodsList = pdaLoadDao.queryGoodsByGoodsStates(condition);

		if (CollectionUtils.isNotEmpty(moreGoodsList)) {
			List<InOutStockEntity> inStock = new ArrayList<InOutStockEntity>();
			for (LoadTaskSerialNoDto moreGoods : moreGoodsList) {
				// 入库多货货物
				InOutStockEntity inOutStockEntity = new InOutStockEntity();
				inOutStockEntity.setWaybillNO(moreGoods.getWayBillNo());
				inOutStockEntity.setSerialNO(moreGoods.getSerialNo());
				inOutStockEntity.setOrgCode(orgCode);
				inOutStockEntity.setInOutStockType(StockConstants.CANCEL_DELIVERY_LOAD_IN_STOCK_TYPE);
				inOutStockEntity.setOperatorCode(loaderCode);
				inOutStockEntity.setOperatorName(loaderName);
				inStock.add(inOutStockEntity);
			}
			pdaLoadService.loadInStock(inStock, LoadConstants.LOAD_INSTOCK_TIMES);
		}
	}

	/**
	 * 下拉运单明细流水号
	 * 
	 * @param loadGoodsDetails
	 * 
	 * @return
	 */
	public List<LoadGoodsDetailDto> loadSerialDetailToWayBillDetail(List<LoadGoodsDetailSerialDto> loadGoodsDetails,
			String orgCode) {
		// 如果货物列表不为空
		if (CollectionUtils.isNotEmpty(loadGoodsDetails)) {
			// 查询所有快递编码集合
			List<String> expressCodeList = productService4Dubbo.getAllLevels3ExpressProductCode();
			List<GoodsAreaEntity> valueGoodsAreaList = goodsAreaService.queryGoodsAreaListByType(orgCode,
					DictionaryValueConstants.BSE_GOODSAREA_TYPE_VALUABLE);
			List<GoodsAreaEntity> packGoodsAreaList = goodsAreaService.queryGoodsAreaListByType(orgCode,
					DictionaryValueConstants.BSE_GOODSAREA_TYPE_PACKING);

			String valueGoodsAreaCode = null;
			String packGoodsAreaCode = null;
			if (CollectionUtils.isNotEmpty(valueGoodsAreaList)) {
				valueGoodsAreaCode = valueGoodsAreaList.get(0).getGoodsAreaCode();
			}
			if (CollectionUtils.isNotEmpty(packGoodsAreaList)) {
				packGoodsAreaCode = packGoodsAreaList.get(0).getGoodsAreaCode();
			}
			// 创建明细列表
			List<LoadGoodsDetailDto> details = new ArrayList<LoadGoodsDetailDto>();
			// 返回给PDA装车任务列表单条Dto
			LoadGoodsDetailDto detailTemp;
			// 流水号列表
			List<PDAGoodsSerialNoDto> serials;
			PDAGoodsSerialNoDto serial;
			// 标志位
			boolean beExist = false;
			BigDecimal converParameter = conversionParameters(orgCode);
			// 遍历货物列表
			for (LoadGoodsDetailSerialDto goods : loadGoodsDetails) {
				beExist = false;
				/**** 判断产品类型是否为快递 2015-8-20 17:02:33 linyuzhu ******/
				boolean expressFlag = false;// 快递标识
				for (String expressCode : expressCodeList) {
					if (StringUtils.equals(goods.getTransportTypeCode(), expressCode)) {
						expressFlag = true;
						break;
					}
				}
				// 遍历明细列表
				for (LoadGoodsDetailDto detail : details) {
					if (detail.getWayBillNo().equals(goods.getWayBillNo())) {
						beExist = true;
						// 去除重复流水号
						boolean beExtistSerial = false;
						if (CollectionUtils.isNotEmpty(detail.getSerialNos())) {
							for (PDAGoodsSerialNoDto serialTemp : detail.getSerialNos()) {
								if (StringUtil.isNotBlank(serialTemp.getSerialNo())
										&& serialTemp.getSerialNo().equals(goods.getSerialNo())) {
									beExtistSerial = true;
								}
							}
						}
						if (!beExtistSerial) {
							serial = new PDAGoodsSerialNoDto();
							// Sets the 是否未打包装
							serial.setIsUnPacking(goods.getIsUnPacking());
							serial.setIsToDoList(goods.getIsToDoList());
							// Sets the 流水号
							serial.setSerialNo(goods.getSerialNo());
							// set流水号
							serial.setGoodsPosition(goods.getGoodsPosition());
							serial.setStockAreaCode(goods.getStockAreaCode());
							/*
							 * List<String> todoItems =
							 * waybillRfcService.queryTodoWhenLoadTruck(goods.
							 * getWayBillNo(),goods.getSerialNo(),orgCode);
							 * if(CollectionUtils.isNotEmpty(todoItems)){
							 * serial.setHasToDoList(FossConstants.YES); }else{
							 * serial.setHasToDoList(FossConstants.NO); }
							 */
							// serial.setHasToDoList(goods.getHasToDoList());
							//
							// 加入流水号
							detail.getSerialNos().add(serial);
							// 如果是快递 则累加其重量体积

							/********
							 * 加入快递空运(商务专递)产品类型的判断 2015-8-20 16:56:14
							 * 263072-linyuzhu
							 *******/
							// if(
							// StringUtils.equals(goods.getTransportTypeCode(),
							// "PACKAGE")
							// ||
							// StringUtils.equals(goods.getTransportTypeCode(),"RCP")
							// ||StringUtils.equals(goods.getTransportTypeCode(),"EPEP")){
							if (expressFlag) {
								if (StringUtils.equals(FossConstants.NO, goods.getBeSortScan())) {
									// 没有上计泡机则 按照重泡比计算
									BigDecimal volume = new BigDecimal(goods.getWeight()
											/ (goods.getWayBillQty() == 0 ? 1 : goods.getWayBillQty()))
													.multiply(converParameter)
													.divide(new BigDecimal(LoadConstants.VOLUME_SIZE), 4, BigDecimal.ROUND_HALF_UP);
									detail.setVolume(detail.getVolume() + volume.doubleValue());
									detail.setWeight(detail.getWeight() + new BigDecimal(goods.getWeight())
											.divide(new BigDecimal(goods.getWayBillQty()), 4, BigDecimal.ROUND_HALF_UP)
											.doubleValue());
								} else {
									// 上计泡机则按照称重的重量体积计算
									detail.setVolume(detail.getVolume() + goods.getVolume());
									detail.setWeight(detail.getWeight() + goods.getWeight());
								}
							}
							if (FossConstants.YES.equals(goods.getIsUnPacking())) {
								if (StringUtils.isNotBlank(detail.getNotes())) {
									// 备注中如果有包含：代打包装字样
									if (!detail.getNotes().contains("代打包装")) {
										detail.setNotes(detail.getNotes() + " 代打包装");
									}
								} else {
									detail.setNotes("代打包装");
								}
							}
							if (FossConstants.YES.equals(goods.getIsToDoList())) {
								if (StringUtils.isNotBlank(detail.getNotes())) {
									// 备注中如果有包含：代打包装字样
									if (!detail.getNotes().contains("有更改")) {
										detail.setNotes(detail.getNotes() + " 有更改");
									}
								} else {
									detail.setNotes("有更改");
								}
							}
						}
					}
				}
				if (!beExist) {
					// 流水号明细
					serial = new PDAGoodsSerialNoDto();
					// Sets the 是否未打包装
					serial.setIsUnPacking(goods.getIsUnPacking());
					serial.setIsToDoList(goods.getIsToDoList());
					// Sets the 流水号.
					serial.setSerialNo(goods.getSerialNo());
					serial.setStockAreaCode(goods.getStockAreaCode());
					// set货物位置
					serial.setGoodsPosition(goods.getGoodsPosition());
					serials = new ArrayList<PDAGoodsSerialNoDto>();
					serials.add(serial);
					detailTemp = new LoadGoodsDetailDto();
					// 包装货区
					detailTemp.setPackGoodsAreaCode(packGoodsAreaCode);
					// 贵重物品货区
					detailTemp.setValueGoodsAreaCode(valueGoodsAreaCode);
					// Sets the 流水号
					detailTemp.setSerialNos(serials);
					// //Sets the 是否必走货
					// detailTemp.setBePriorityGoods(goods.getBePriorityGoods());
					// Gets the 货名
					detailTemp.setGoodsName(goods.getGoodsName());
					// Sets the 是否有更改
					// detailTemp.setModifyContent(goods.getModifyState());
					// Sets the 是否贵重物品
					detailTemp.setIsValue(goods.getIsValue());
					// detailTemp.setModifyState(goods.getModifyState());
					// Sets the 更改内容
					// detailTemp.setModifyContent(goods.getModifyContent());
					StringBuilder str = new StringBuilder();
					// 判断提示
					if (FossConstants.YES.equals(goods.getIsToDoList())) {
						// 更改
						str.append("有更改 ");
					} else if (FossConstants.YES.equals(goods.getIsUnPacking())) {
						// 包装
						str.append(" 代打包装 ");
					} else if (FossConstants.YES.equals(goods.getIsValue())) {
						// 贵重物品
						str.append(" 贵重物品");
					}
					// Sets the 备注
					detailTemp.setNotes(str.toString().trim());
					// s.append(b);
					// Sets the 包装
					detailTemp.setPacking(goods.getPacking());
					// Sets the 到达部门编码
					detailTemp.setReachOrgCode(goods.getReachOrgCode());
					// Sets the 收货部门编码
					detailTemp.setReceiveOrgCode(goods.getReceiveOrgCode());
					// Sets the 到达部门名称
					detailTemp.setReachOrgName(goods.getReachOrgName());
					// Sets the 收货部门名称
					detailTemp.setReceiveOrgName(goods.getReceiveOrgName());
					// Sets the 入库时间
					detailTemp.setStockTime(goods.getStockTime());
					// Gets the 任务编号
					detailTemp.setTaskNo(goods.getTaskNo());
					// Gets the 运输性质
					detailTemp.setTransportType(goods.getTransportType());
					// Gets the 体积.
					// 快递转换体积参数
					// zwd
					// 调用综合接口，根据部门编码去查询重泡比，如果存在则使用查询到的重泡比，如果不存在则使用公用的重量体积转换参数。
					// 由于计泡机对快递件进行 称重量方 （安件）所有，统计运单的重量体积就必须按件累加求和

					if (converParameter.compareTo(BigDecimal.ZERO) == 0) {
						detailTemp.setVolume(goods.getVolume());
					} else {
						/********
						 * 加入快递空运(商务专递)产品类型的判断 2015-8-20 17:03:33
						 * 263072-linyuzhu
						 *******/
						if (expressFlag) {
							// if(
							// StringUtils.equals(goods.getTransportTypeCode(),
							// "PACKAGE")
							// ||
							// StringUtils.equals(goods.getTransportTypeCode(),"RCP")
							// ||StringUtils.equals(goods.getTransportTypeCode(),"EPEP")){
							if (StringUtils.equals(FossConstants.NO, goods.getBeSortScan())) {
								BigDecimal volume = new BigDecimal(
										goods.getWeight() / (goods.getWayBillQty() == 0 ? 1 : goods.getWayBillQty()))
												.multiply(converParameter)
												.divide(new BigDecimal(LoadConstants.VOLUME_SIZE), 4, BigDecimal.ROUND_HALF_UP);
								detailTemp.setVolume(volume.doubleValue());
							} else {
								// 按件的重量设置，后面有其他流水号时累加重量体积
								// BigDecimal volume = new
								// BigDecimal(goods.getVolume()).divide(new
								// BigDecimal(goods.getWayBillQty()),4,BigDecimal.ROUND_HALF_UP);
								detailTemp.setVolume(goods.getVolume());
							}

						} else {
							detailTemp.setVolume(goods.getVolume());
						}
					}
					// Sets the 运单号
					detailTemp.setWayBillNo(goods.getWayBillNo());

					// Sets the 开单件数
					detailTemp.setWayBillQty(goods.getWayBillQty());
					// 按件的重量设置，后面有其他流水号时累加重量体积
					/********
					 * 加入快递空运(商务专递)产品类型的判断 2015-8-20 17:03:33 263072-linyuzhu
					 *******/
					if (expressFlag) {
						// if(StringUtils.equals(goods.getTransportTypeCode(),
						// "PACKAGE")
						// ||
						// StringUtils.equals(goods.getTransportTypeCode(),"RCP")
						// ||StringUtils.equals(goods.getTransportTypeCode(),"EPEP")){
						if (StringUtils.equals(FossConstants.NO, goods.getBeSortScan())) {
							// 按件的重量设置，后面有其他流水号时累加重量体积
							detailTemp.setWeight(new BigDecimal(goods.getWeight())
									.divide(new BigDecimal(goods.getWayBillQty()), 4, BigDecimal.ROUND_HALF_UP)
									.doubleValue());
						} else {

							detailTemp.setWeight(goods.getWeight());
						}
					} else {
						// Gets the 重量.
						detailTemp.setWeight(goods.getWeight());
					}

					// Gets the 库存件数
					detailTemp.setStockQty(goods.getStockQty());
					// 货区
					detailTemp.setStockAreaCode(goods.getStockAreaCode());
					// 操作数量
					detailTemp.setOperateQty(goods.getOperateQty());
					// Sets the 是否合车
					detailTemp.setBeJoinCar(goods.getBeJoinCar());
					detailTemp.setStationNumber(goods.getStationNumber());
					// 运单对应行政属性
					detailTemp.setReceiveCustDistName(goods.getReceiveCustDistName());
					// 查询必走货
					WaybillStockQueryDto waybillStockQueryDto = new WaybillStockQueryDto();
					waybillStockQueryDto.setOrgCode(goods.getReachOrgCode());
					waybillStockQueryDto.setGoodsAreaCode(goods.getStockAreaCode());
					List<WaybillStockQueryDto> list = stockService.queryPriorityGoods(waybillStockQueryDto, 10, 0);
					// ISSUE-3445 liubinbin 2013-07-30
					if (list != null && list.size() > 0) {
						detailTemp.setBePriorityGoods(FossConstants.YES);
					} else {
						detailTemp.setBePriorityGoods(FossConstants.NO);
					}
					// 快递：如果运输性质为包裹，则为优先货

					/********
					 * 加入快递空运(商务专递)产品类型的判断 2015-8-20 17:03:33 263072-linyuzhu
					 *******/
					if (expressFlag) {
						// if(LoadConstants.PRODUCT_CODE_PACKAGE.equals(detailTemp.getTransportTypeCode())||
						// LoadConstants.PRODUCT_CODE_RCP.equals(detailTemp.getTransportTypeCode())
						// ||LoadConstants.PRODUCT_CODE_EPEP.equals(detailTemp.getTransportTypeCode())){
						detailTemp.setBePriorityGoods(FossConstants.YES);
					} else {
						detailTemp.setBePriorityGoods(FossConstants.NO);
					}
					ActualFreightEntity actualFreightEntity = new ActualFreightEntity();
					actualFreightEntity = actualFreightService.queryByWaybillNo(goods.getWayBillNo());
					if (actualFreightEntity != null) {
						if (StringUtils.equals(actualFreightEntity.getWaybillType(), "EWAYBILL")) {
							detailTemp.setBeEWaybill(FossConstants.YES);
						} else {
							detailTemp.setBeEWaybill(FossConstants.NO);
						}
					} else {
						detailTemp.setBeEWaybill(FossConstants.NO);
					}
					// 加入明细
					details.add(detailTemp);
				}
			}
			StringBuilder s = new StringBuilder();
			s.append("刷新装车任务:");
			for (LoadGoodsDetailDto d : details) {
				s.append("任务号：" + d.getTaskNo());
				s.append("运单号:" + d.getWayBillNo());
				// s.append("更改状态:"+d.getModifyState());
				s.append("贵重物品:" + d.getIsValue());
				if (CollectionUtils.isNotEmpty(d.getSerialNos())) {
					for (PDAGoodsSerialNoDto ss : d.getSerialNos()) {
						s.append("流水号:");
						s.append(ss.getSerialNo());
						s.append("打木架：" + ss.getIsUnPacking());
						s.append("代办事项" + ss.getIsToDoList());
					}
				}
				s.append("/&n");
			}
			LOGGER.error(s.toString());
			return details;
		} else {
			return null;
		}
	}

	/**
	 * 
	 * <p>
	 * 查询快递转化参数
	 * </p>
	 * 
	 * @author alfred
	 * @date 2014-9-17 上午9:57:41
	 * @return
	 * @see
	 */
	private BigDecimal conversionParameters(String orgCode) {
		// zwd 调用综合接口，根据部门编码去查询重泡比，如果存在则使用查询到的重泡比，如果不存在则使用公用的重量体积转换参数。
		BigDecimal converParameter = BigDecimal.ZERO;
		String stringValue = "";
		try {
			if (StringUtils.isNotEmpty(orgCode)) {
				// zwd 调用综合接口，根据部门编码去查询重泡比，如果存在则使用查询到的重泡比，如果不存在则使用公用的重量体积转换参数。
				stringValue = heavyBubbleRatioService.queryHeavyBubbleParamByOutfield(orgCode);
			}

		} catch (Exception e) {
			throw new TfrBusinessException("调综合接口根据外场编码来查询重泡比参数异常" + e.toString());
		}
		if (stringValue != null && StringUtils.isNotEmpty(stringValue)) {
			double doubleValue = Double.valueOf(stringValue.toString());
			converParameter = new BigDecimal(doubleValue);
			BigDecimal a = new BigDecimal("1.000");
			// 重泡比为重量体积转换参数分之一
			converParameter = a.divide(converParameter, LoadConstants.WEIGHT_VOLUME_SCALE_THREE);
		} else {
			ConfigurationParamsEntity paramEntity = configurationParamsService.queryConfigurationParamsByOrgCode(
					DictionaryConstants.SYSTEM_CONFIG_PARM__TFR,
					ConfigurationParamsConstants.TFR_PARM_EXPRESS_CONVERTVOLUME_PARAMETERS, "DIP");
			if (paramEntity != null && StringUtils.isNotEmpty(paramEntity.getConfValue())) {
				converParameter = new BigDecimal(paramEntity.getConfValue());
			}
		}
		return converParameter;
	}
	

	public LoadTaskResultDto createLoadTaskResult(String taskNo, String orgCode, LoaderParticipationEntity creator,
			List<LoaderDto> loaders, VehicleAssociationDto vehicleDto) {
		LoadTaskResultDto loadTaskResultDto = new LoadTaskResultDto();
		if (creator != null) {
			loadTaskResultDto.setCreatorCode(creator.getLoaderCode());
			loadTaskResultDto.setCreatorName(creator.getLoaderName());
		}
		loadTaskResultDto.setLoaders(loaders);

		// 车辆额定载重、额定净空
		if (vehicleDto != null) {
			double vehicleDeadWeight = 0;
			if (vehicleDto.getVehicleDeadLoad() != null) {
				vehicleDeadWeight = vehicleDto.getVehicleDeadLoad().doubleValue();
			}
			double vehicleDeadVolume = 0;
			if (vehicleDto.getVehicleSelfVolume() != null) {
				vehicleDeadVolume = vehicleDto.getVehicleSelfVolume().doubleValue();
			}
			loadTaskResultDto.setVehicleDeadVolume(vehicleDeadVolume);
			loadTaskResultDto.setVehicleDeadWeight(vehicleDeadWeight);
			loadTaskResultDto.setVehicleLengthName(vehicleDto.getVehicleLengthName());
			if (vehicleDto.getVehicleUsedTypeName() != null) {
				if (vehicleDto.getVehicleUsedTypeName().equals("长途车")) {
					if (StringUtils.isNotBlank(vehicleDto.getVehicleOrganizationCode())) {
						loadTaskResultDto.setVehicleLengthName("长途公司车");
					}
				}
			}
		}
		loadTaskResultDto.setTaskNo(taskNo);
		// zwd 调用综合接口，根据部门编码去查询重泡比，如果存在则使用查询到的重泡比，如果不存在则使用公用的重量体积转换参数。
		BigDecimal converParameter = conversionParameters(orgCode);
		loadTaskResultDto.setExpressConvertParameter(converParameter);
		return loadTaskResultDto;
	}

	/**
	 * （方法详细描述说明、方法参数的具体涵义）
	 * 
	 * @author dp-duyi
	 * @date 2013-3-21 上午7:52:31
	 * @see com.deppon.foss.module.transfer.pda.api.server.service.IPDATransferLoadService#createLoadTask(com.deppon.foss.module.transfer.pda.api.shared.dto.LoadTaskDto)
	 */
	@Override
	public LoadTaskResultDto createLoadTask(LoadTaskDto loadTask) {
		// Auto-generated method stub
		return null;
	}

	/**
	 * （方法详细描述说明、方法参数的具体涵义）
	 * 
	 * @author dp-duyi
	 * @date 2013-3-21 上午7:52:31
	 * @see com.deppon.foss.module.transfer.pda.api.server.service.
	 *      IPDATransferLoadService# (java.lang.String)
	 */
	@Override
	public List<LoadGoodsDetailDto> refrushLoadTaskDetail(String taskNo) {
		// Auto-generated method stub
		return null;
	}

	/**
	 * 
	 * @author dp-duyi
	 * @date 2013-4-12 下午2:10:12
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IPDALoadService#noticeDeliverOrg()
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void noticeDeliverOrg(LoadTaskEntity loadTask, String loaderCode, String loaderName) {
		try {
			if (LoadConstants.LOAD_TASK_TYPE_DELIVER.equals(loadTask.getTaskType())) {
				// 查询派送单

				List<String> deliverBillCreateOrgCodes = pdaLoadDao
						.queryDeliverBillOrgCodeByDeliverBillNo(loadTask.getDeliverBillNo());
				if (CollectionUtils.isNotEmpty(deliverBillCreateOrgCodes)
						&& StringUtils.isNotBlank(deliverBillCreateOrgCodes.get(0))) {
					SmsParamDto smsParamDto = new SmsParamDto();
					Map<String, String> smsParam = new HashMap<String, String>();
					smsParam.put("deliverBillNo", loadTask.getDeliverBillNo());
					smsParam.put("taskNo", loadTask.getTaskNo());
					smsParam.put("loader", loaderCode + " " + loaderName);
					smsParamDto.setMap(smsParam);
					smsParamDto.setOrgCode(null);
					smsParamDto.setSmsCode("NOTICE_DELIVER_LOAD");
					String msg = sMSTempleteService.querySmsByParam(smsParamDto);
					if (StringUtils.isNotBlank(msg)) {
						// 通知审核派送任务 :设置消息参数
						InstationJobMsgEntity msgEntity = new InstationJobMsgEntity();
						// 生成id
						msgEntity.setId(UUIDUtils.getUUID());
						// 工号
						msgEntity.setSenderCode(loaderCode);
						msgEntity.setSenderName(loaderName);
						msgEntity.setSenderOrgCode(loadTask.getOrigOrgCode());
						msgEntity.setSenderOrgName(loadTask.getOrigOrgName());
						// 接收部门编号
						msgEntity.setReceiveOrgCode(deliverBillCreateOrgCodes.get(0));
						// Gets the 派送单单号
						msgEntity.setContext(msg);
						// 接收方类型
						msgEntity.setReceiveType(MessageConstants.MSG__RECEIVE_TYPE__ORG);
						// 综合管理-站内消息-消息类型 "值代码"
						msgEntity.setMsgType(DictionaryValueConstants.MSG_TYPE__NORMAL);
						// 发送日期
						msgEntity.setPostDate(new Date());
						// 人员对组织的站内消息发送
						messageService.createBatchInstationMsg(msgEntity);
					}

				}
			}
		} catch (Exception e) {
			LOGGER.error("通知排单员失败" + loadTask.getDeliverBillNo(), e);
		}
	}

	/**
	 * 根据任务id查询外发代理网点
	 * 
	 * @author dp-duyi
	 * @date 2013-5-14 上午9:43:23
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IPDALoadService#queryOuterBranchCodesByPartnerCodes(java.util.List)
	 */
	@Override
	public List<String> queryOuterBranchCodesByTaskId(String taskId) {
		List<String> destOrgCodes = pdaLoadDao.queryLoadDestOrgCodesById(taskId);
		if (CollectionUtils.isNotEmpty(destOrgCodes)) {
			List<OuterBranchEntity> totalOuterBranchs = new ArrayList<OuterBranchEntity>();
			List<OuterBranchEntity> outerBranchs = null;

			for (String destOrgCode : destOrgCodes) {
				outerBranchs = vehicleAgencyDeptService.queryOuterBrangchsByCode(destOrgCode);
				if (CollectionUtils.isNotEmpty(outerBranchs)) {
					totalOuterBranchs.addAll(outerBranchs);
				}
			}

			if (CollectionUtils.isNotEmpty(totalOuterBranchs)) {
				List<String> outerBranchCodes = new ArrayList<String>();
				for (OuterBranchEntity outerBranch : totalOuterBranchs) {
					if (StringUtils.isNotBlank(outerBranch.getAgentDeptCode())) {
						outerBranchCodes.add(outerBranch.getAgentDeptCode());
					}
				}
				if (CollectionUtils.isNotEmpty(outerBranchCodes)) {
					return outerBranchCodes;
				}
			}
		}
		return null;
	}

	/**
	 * 快递:根据任务id查询落地配网点
	 * 
	 * @author dp-duyi
	 * @date 2013-5-14 上午9:43:23
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IPDALoadService#queryOuterBranchCodesByPartnerCodes(java.util.List)
	 */
	@Override
	public List<String> queryLDPDeptCodesByTaskId(String taskId) {
		List<String> destOrgCodes = pdaLoadDao.queryLoadDestOrgCodesById(taskId);
		if (CollectionUtils.isNotEmpty(destOrgCodes)) {
			List<String> outerBranchCodes = new ArrayList<String>();
			List<OuterBranchExpressEntity> outerBranchs = null;
			for (String destOrgCode : destOrgCodes) {
				outerBranchs = ldpAgencyDeptService.queryLdpAgencyDeptsByagencyCompanyCode(destOrgCode,
						FossConstants.YES);
				if (CollectionUtils.isNotEmpty(outerBranchs)) {
					for (OuterBranchExpressEntity outerBranch : outerBranchs) {
						outerBranchCodes.add(outerBranch.getAgentDeptCode());
					}
				}
			}
			return outerBranchCodes;
		} else {
			return null;
		}
	}

	/**
	 * 装车入库
	 * 
	 * @author dp-duyi
	 * @date 2013-5-21 上午11:18:51
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IPDALoadService#loadInStock(java.util.List)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void loadInStock(List<InOutStockEntity> goodsList, int inStockTimes) {
		if (CollectionUtils.isNotEmpty(goodsList) && inStockTimes >= 0) {
			List<InOutStockEntity> exceptionStock = new ArrayList<InOutStockEntity>();
			for (InOutStockEntity inOutStockEntity : goodsList) {
				try {
					pdaLoadService.instock(inOutStockEntity);
				} catch (Exception e) {
					if (e.getClass() != BusinessException.class) {
						exceptionStock.add(inOutStockEntity);
						LOGGER.error("装车入库失败" + inOutStockEntity.getWaybillNO() + inOutStockEntity.getSerialNO()
								+ inOutStockEntity.getOrgCode(), e);
					}
				}
			}
			if (CollectionUtils.isNotEmpty(exceptionStock)) {
				try {
					Thread.sleep(LoadConstants.LOAD_INSTOCK_WAIT_TIME);
				} catch (InterruptedException e) {
				}
				pdaLoadService.loadInStock(exceptionStock, --inStockTimes);
			}
		}
	}

	/**
	 * @author dp-duyi
	 * @date 2013-5-21 上午11:30:23
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IPDALoadService#instock(com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void instock(InOutStockEntity goods) {
		stockService.inStockPC(goods);
	}

	/**
	 * 
	 * PDA扫描出库
	 * 
	 * @author alfred
	 * @date 2014-2-27 上午11:36:04
	 * @param goods
	 * @see
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void outstock(InOutStockEntity goods) {
		stockService.outStockPC(goods);
	}

	/**
	 * 
	 * <p>
	 * 提供包扫描
	 * </p>
	 * 
	 * @author alfred
	 * @date 2014-10-26 上午11:02:07
	 * @param scanRecord
	 * @return
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IPDALoadService#unlockPackageLoadScan(com.deppon.foss.module.transfer.pda.api.shared.dto.LoadScanDetailDto)
	 */
	@Override
	public LoadGoodsDetailDto unlockPackageLoadScan(LoadScanDetailDto scanRecord) {
		LOGGER.error("装车包扫描开始" + " " + scanRecord.getType() + " " + scanRecord.getTaskNo() + " "
				+ scanRecord.getWayBillNo());
		ExpressPackageEntity expressPackageEntity = expressPackageDao
				.queryExpressPackageByPackageNo(scanRecord.getWayBillNo());
		if (null == expressPackageEntity || StringUtils.equals(expressPackageEntity.getStatus(), "ALREADY_CANCELED")
				|| StringUtils.equals(expressPackageEntity.getStatus(), "CANCELED")) {
			throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_INVALID_PACKAGE_MESSAGECODE);
		}
		if (LoadConstants.LOAD_GOODS_STATE_MORE.equals(scanRecord.getType())) {
			if (expressPackageEntity != null && null != expressPackageEntity.getWeight()
					&& null != expressPackageEntity.getVolume()) {
				scanRecord.setVolume(expressPackageEntity.getVolume().doubleValue());
				scanRecord.setWeight(expressPackageEntity.getWeight().doubleValue());
			}
		}
		if (scanRecord.getWeight() <= 0) {
			throw new TfrBusinessException("无效重量");
		}
		// 查询装车任务
		LoadTaskEntity loadTask = pdaLoadDao.queryLoadTaskByTaskNo(scanRecord.getTaskNo());
		if (loadTask != null && LoadConstants.LOAD_TASK_STATE_LOADING.equals(loadTask.getState())) {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date loadStartTime = new Date();
			try {
				loadStartTime = df.parse(loadTask.getLoadStartTime());
			} catch (ParseException e) {
				LOGGER.error("装车包扫描", e);
			}
			// 查询运单明细
			LoadWaybillDetailEntity loadTaskWayBillTemp = new LoadWaybillDetailEntity();
			loadTaskWayBillTemp.setLoadTaskId(loadTask.getId());
			loadTaskWayBillTemp.setWaybillNo(scanRecord.getWayBillNo());
			LoadWaybillDetailEntity loadWayBillEntity = pdaLoadDao
					.queryLoadWaybillDetailEntityByWayBillNo(loadTaskWayBillTemp);
			if (loadWayBillEntity != null) {
				if (TransferPDADictConstants.LOAD_GOODS_STATE_CANCELED.equals(scanRecord.getType())) {
					loadWayBillEntity.setScanQty(0);
					loadWayBillEntity.setLoadQty(0);
				} else {
					loadWayBillEntity.setScanQty(loadWayBillEntity.getStockQty());
					loadWayBillEntity.setLoadQty(loadWayBillEntity.getStockQty());
				}
				pdaLoadDao.updateLoadWayBillQTYByScanTime(loadWayBillEntity, scanRecord.getScanTime());
			} else {
				// 如果不存在装车运单明细，则插入记录
				loadWayBillEntity = new LoadWaybillDetailEntity();
				String loadWaybillDetailId = UUIDUtils.getUUID();
				loadWayBillEntity.setId(loadWaybillDetailId);
				loadWayBillEntity.setStockQty(scanRecord.getStockQty());
				loadWayBillEntity.setBeJoinCar(FossConstants.NO);
				loadWayBillEntity.setGoodsName("包");
				loadWayBillEntity.setReachOrgName(expressPackageEntity.getArriveOrgName());
				loadWayBillEntity.setReceiveOrgName(expressPackageEntity.getDepartOrgName());
				loadWayBillEntity.setTransportType("快递");
				loadWayBillEntity.setLoadVolumeTotal(
						expressPackageEntity.getVolume() == null ? 0 : expressPackageEntity.getVolume().doubleValue());
				loadWayBillEntity.setLoadWeightTotal(
						expressPackageEntity.getWeight() == null ? 0 : expressPackageEntity.getWeight().doubleValue());
				if (TransferPDADictConstants.LOAD_GOODS_STATE_CANCELED.equals(scanRecord.getType())) {
					loadWayBillEntity.setScanQty(0);
					loadWayBillEntity.setLoadQty(0);
				} else {
					loadWayBillEntity.setLoadQty(expressPackageEntity.getGoodsQty() == null ? 0
							: expressPackageEntity.getGoodsQty().intValue());
					loadWayBillEntity.setScanQty(expressPackageEntity.getGoodsQty() == null ? 0
							: expressPackageEntity.getGoodsQty().intValue());
				}
				loadWayBillEntity.setWaybillNo(scanRecord.getWayBillNo());
				loadWayBillEntity.setLoadTaskId(loadTask.getId());
				loadWayBillEntity.setOrigOrgCode(loadTask.getOrigOrgCode());
				loadWayBillEntity.setTaskBeginTime(loadStartTime);
				loadWayBillEntity.setModifyDate(scanRecord.getScanTime());
				loadWayBillEntity.setPackageNo(scanRecord.getWayBillNo());
				try {
					pdaLoadDao.insertLoadWayBillDetailEntity(loadWayBillEntity);
				} catch (org.springframework.dao.DuplicateKeyException e) {
					LoadWaybillDetailEntity loadWayBillEntityID = pdaLoadDao
							.queryLoadWaybillDetailEntityByWayBillNo(loadWayBillEntity);
					if (loadWayBillEntityID != null) {
						loadWayBillEntity.setId(loadWayBillEntityID.getId());
						pdaLoadDao.updateLoadWayBillQTYByScanTime(loadWayBillEntity, scanRecord.getScanTime());
					} else {
						throw e;
					}
				}
			}
		} else {
			LOGGER.error("装车包扫描结束" + " " + scanRecord.getType() + " " + scanRecord.getTaskNo() + " "
					+ scanRecord.getWayBillNo() + " " + scanRecord.getSerialNo());
			throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_INVALID_TASK_MESSAGECODE);
		}
		// 成功
		LOGGER.error("装车包扫描结束" + " " + scanRecord.getType() + " " + scanRecord.getTaskNo() + " "
				+ scanRecord.getWayBillNo() + " " + scanRecord.getSerialNo());
		return null;
	}

	/**
	 * 
	 * <p>
	 * 提供包扫描
	 * </p>
	 * 
	 * @author zwd 200968
	 * @date 2015-4-7 下午14:02:07
	 * @param scanRecord
	 * @return
	 */
	@Override
	public LoadGoodsDetailDto unlockPackageLoadScanNew(LoadScanDetailDto scanRecord) {
		LOGGER.error("装车包扫描开始" + " " + scanRecord.getType() + " " + scanRecord.getTaskNo() + " "
				+ scanRecord.getWayBillNo());

		String goodsState = scanRecord.getType();

		// 运单号里面保存的是 ： 包号
		// 包有效
		ExpressPackageEntity expressPackageEntity = expressPackageDao
				.queryExpressPackageByPackageNo(scanRecord.getWayBillNo());
		if (null == expressPackageEntity || StringUtils.equals(expressPackageEntity.getStatus(), "ALREADY_CANCELED")
				|| StringUtils.equals(expressPackageEntity.getStatus(), "CANCELED")) {
			LOGGER.error("AbstractPDALoadService.unlockPackageLoadScanNew(LoadScanDetailDto) package invaild packageNo="
					+ scanRecord.getWayBillNo());
			return null;
			// throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_INVALID_PACKAGE_MESSAGECODE);
		}
		// 多货
		if (LoadConstants.LOAD_GOODS_STATE_MORE.equals(scanRecord.getType())) {
			goodsState = LoadConstants.LOAD_GOODS_STATE_MORE;
			if (expressPackageEntity != null && null != expressPackageEntity.getWeight()
					&& null != expressPackageEntity.getVolume()) {
				scanRecord.setVolume(expressPackageEntity.getVolume().doubleValue());
				scanRecord.setWeight(expressPackageEntity.getWeight().doubleValue());
			}
		}
		if (scanRecord.getWeight() <= 0 && !LoadConstants.LOAD_TASK_STATE_CANCELED.equals(scanRecord.getType())) {
			scanRecord.setWeight(0);
			//throw new TfrBusinessException("无效重量");
		}
		// 查询装车任务
		LoadTaskEntity loadTask = pdaLoadDao.queryLoadTaskByTaskNo(scanRecord.getTaskNo());
		if (loadTask != null && LoadConstants.LOAD_TASK_STATE_LOADING.equals(loadTask.getState())) {
			goodsState = LoadConstants.LOAD_GOODS_STATE_NORMAL;
			
			// 查询运单明细
			LoadWaybillDetailEntity loadTaskWayBillTemp = new LoadWaybillDetailEntity();
			// 装车任务ID
			loadTaskWayBillTemp.setLoadTaskId(loadTask.getId());
			// 包号
			loadTaskWayBillTemp.setWaybillNo(scanRecord.getWayBillNo());
			// 装车任务id，包号来查询装车运单明细中是否对这包号进行扫描tfr.t_opt_load_waybill_detail
			List<LoadWaybillDetailEntity> loadWayBillEntityList = new ArrayList<LoadWaybillDetailEntity>();
			loadWayBillEntityList = pdaLoadDao.queryLoadWaybillDetailEntityByPackageNo(loadTaskWayBillTemp);

			if (CollectionUtils.isNotEmpty(loadWayBillEntityList)) {
				for (LoadWaybillDetailEntity waybillDetail : loadWayBillEntityList) {

					LoadWaybillDetailEntity wayBillEntity = new LoadWaybillDetailEntity();
					LoadSerialNoEntity serialNoEntity = new LoadSerialNoEntity();
					ExpressPackageDetailEntity entity = pdaUnloadTaskDao
							.queryPackageDetailsByWaybill(waybillDetail.getWaybillNo(), scanRecord.getWayBillNo());
					wayBillEntity.setWaybillNo(waybillDetail.getWaybillNo());
					wayBillEntity.setPackageNo(scanRecord.getWayBillNo());

					if (LoadConstants.LOAD_TASK_STATE_CANCELED.equals(scanRecord.getType())) {
						wayBillEntity.setLoadVolumeTotal(0);
						wayBillEntity.setLoadWeightTotal(0);
						wayBillEntity.setScanQty(0);
						wayBillEntity.setLoadQty(0);
						// 流水号反扫
						serialNoEntity.setGoodsState(TransferPDADictConstants.LOAD_GOODS_STATE_CANCELED);
						serialNoEntity.setBeLoaded(FossConstants.NO);
					} else {
						wayBillEntity.setLoadVolumeTotal(entity.getVolume());
						wayBillEntity.setLoadWeightTotal(entity.getWeight());
						wayBillEntity.setScanQty(entity.getGoodsQty());
						wayBillEntity.setLoadQty(entity.getGoodsQty());
						// 流水号正扫
						serialNoEntity.setGoodsState(goodsState);
						serialNoEntity.setBeLoaded(FossConstants.YES);
					}
					wayBillEntity.setModifyDate(scanRecord.getScanTime());
					wayBillEntity.setId(waybillDetail.getId());
					pdaLoadDao.updatePackageWayBillEntity(wayBillEntity);
					// 更新流水号信息
					serialNoEntity.setLoadWaybillDetailId(waybillDetail.getId());
					serialNoEntity.setScanState(scanRecord.getScanState());
					serialNoEntity.setLoadTime(scanRecord.getScanTime());
					pdaLoadDao.updateLoadTaskSerialNoNew(serialNoEntity);
				}
			} else {
				List<ExpressPackageDetailEntity> detailList = pdaUnloadTaskDao
						.queryScanPackageDetails(scanRecord.getWayBillNo());
				if (CollectionUtils.isNotEmpty(detailList)) {
					for (ExpressPackageDetailEntity detail : detailList) {
						// 插入运单
						String wayBillDetailId = insertLoadWaybillDetailbypackage(scanRecord, loadTask, detail);
						// 批量插入流水号
						insertLoadSerialsDetailbyPackage(scanRecord, loadTask, goodsState, detail, wayBillDetailId);
					}
				}
			}
		} else {
			LOGGER.error("装车包扫描结束" + " " + scanRecord.getType() + " " + scanRecord.getTaskNo() + " "
					+ scanRecord.getWayBillNo() + " " + scanRecord.getSerialNo());
			//throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_INVALID_TASK_MESSAGECODE);
		}
		// 成功
		LOGGER.error("装车包扫描结束" + " " + scanRecord.getType() + " " + scanRecord.getTaskNo() + " "
				+ scanRecord.getWayBillNo() + " " + scanRecord.getSerialNo());
		return null;
	}

	/**
	 * 
	 * <p>
	 * 包扫描新增插入运单明细
	 * </p>
	 * 
	 * @author zwd
	 * @date 2015-4-17 下午4:40:38
	 * @param scanRecord
	 * @param loadTask
	 * @param detail
	 * @return
	 * @see
	 */
	private String insertLoadWaybillDetailbypackage(LoadScanDetailDto scanRecord, LoadTaskEntity loadTask,
			ExpressPackageDetailEntity detail) {
		// 装车运单明细
		LoadWaybillDetailEntity wayBillEntity = new LoadWaybillDetailEntity();
		String wayBillDetailId = UUIDUtils.getUUID();
		wayBillEntity.setId(wayBillDetailId);
		wayBillEntity.setLoadTaskId(loadTask.getId());
		if (LoadConstants.LOAD_TASK_STATE_CANCELED.equals(scanRecord.getType())) {
			wayBillEntity.setLoadVolumeTotal(0);
			wayBillEntity.setLoadWeightTotal(0);
			wayBillEntity.setScanQty(0);
			wayBillEntity.setLoadQty(0);
		} else {
			wayBillEntity.setLoadVolumeTotal(detail.getVolume());
			wayBillEntity.setLoadWeightTotal(detail.getWeight());
			wayBillEntity.setScanQty(detail.getGoodsQty());
			wayBillEntity.setLoadQty(detail.getGoodsQty());
		}
		wayBillEntity.setStockQty(detail.getGoodsQty());
		wayBillEntity.setPack(detail.getPack());
		wayBillEntity.setWaybillNo(detail.getWayBillNo());
		wayBillEntity.setTransportType(detail.getTransportTypeName());
		wayBillEntity.setGoodsName(detail.getGoodsName());
		wayBillEntity.setReceiveOrgName(detail.getRecieveOrgName());
		wayBillEntity.setReachOrgName(detail.getReachOrgName());
		wayBillEntity.setTaskBeginTime(DateUtils.strToDate(loadTask.getLoadStartTime()));
		wayBillEntity.setBeJoinCar(FossConstants.NO);
		wayBillEntity.setOrigOrgCode(loadTask.getOrigOrgCode());
		wayBillEntity.setModifyDate(scanRecord.getScanTime());
		wayBillEntity.setPackageNo(scanRecord.getWayBillNo());
		try {
			pdaLoadDao.insertLoadWayBillDetailEntity(wayBillEntity);
		} catch (org.springframework.dao.DuplicateKeyException e) {
			LoadWaybillDetailEntity loadWayBillEntityID = pdaLoadDao
					.queryLoadWaybillDetailEntityByWayBillNo(wayBillEntity);
			if (loadWayBillEntityID != null) {
				wayBillEntity.setId(loadWayBillEntityID.getId());
				pdaLoadDao.updateLoadWayBillQTYByScanTime(wayBillEntity, scanRecord.getScanTime());
			} else {
				throw e;
			}
		}
		return wayBillDetailId;
	}

	/**
	 * 
	 * <p>
	 * 包扫描新增插入流水号
	 * </p>
	 * 
	 * @author zwd 200968
	 * @date 2015-4-17 下午4:40:34
	 * @param scanRecord
	 * @param loadTask
	 * @param goodsState
	 * @param detail
	 * @param wayBillDetailId
	 * @see
	 */
	private void insertLoadSerialsDetailbyPackage(LoadScanDetailDto scanRecord, LoadTaskEntity loadTask,
			String goodsState, ExpressPackageDetailEntity detail, String wayBillDetailId) {
		List<String> serials = pdaUnloadTaskDao.queryPackageSerialNo(scanRecord.getWayBillNo(), detail.getWayBillNo());
		if (CollectionUtils.isNotEmpty(serials)) {
			for (String serial : serials) {
				// 装车任务流水号明细实体
				LoadSerialNoEntity seialNoDetail = new LoadSerialNoEntity();
				// 设置id
				seialNoDetail.setId(UUIDUtils.getUUID());
				// 设备编号
				seialNoDetail.setDeviceNo(scanRecord.getDeviceNo());
				// TODO
				// 货物状态
				seialNoDetail.setGoodsState(goodsState);
				// 操作时间&扫描时间
				seialNoDetail.setLoadTime(scanRecord.getScanTime());
				// 扫描状态
				seialNoDetail.setScanState(scanRecord.getScanState());
				// 流水号
				seialNoDetail.setSerialNo(serial);
				// 是否装车
				if (scanRecord.getScanState().equals("SCANED")) {
					seialNoDetail.setBeLoaded("Y");
				} else {
					seialNoDetail.setBeLoaded("N");
				}

				seialNoDetail.setLoadWaybillDetailId(wayBillDetailId);
				// 创建时间
				seialNoDetail.setCreateTime(new Date());
				// 出发部门编号
				seialNoDetail.setOrigOrgCode(loadTask.getOrigOrgCode());
				// 建立任务时间
				seialNoDetail.setTaskBeginTime(DateUtils.strToDate(loadTask.getLoadStartTime()));

				// 插入到装车流水明细表中
				pdaLoadDao.insertLoadSerialNoEntity(seialNoDetail);
			}
		}
	}

	/**
	 * 
	 * <p>
	 * (方法详细描述说明、方法参数的具体涵义)
	 * </p>
	 * 
	 * @author alfred
	 * @date 2014-10-30 下午4:28:16
	 * @param taskNo
	 * @return
	 * @see com.deppon.foss.module.transfer.pda.api.server.service.IPDATransferLoadService#refrushLoadTaskPackageDetail(java.lang.String)
	 */
	@Override
	public List<LoadGoodsDetailDto> refrushLoadTaskPackageDetail(String taskNo) {
		// Auto-generated method stub
		return null;
	}
	
	
	/***
	 * 
	 * <p>
	 * TODO(方法详细描述说明、方法参数的具体涵义)
	 * </p>
	 * 
	 * @author alfred
	 * @date 2014-12-5 下午2:44:25
	 * @param taskNo
	 * @return
	 * @see com.deppon.foss.module.transfer.pda.api.server.service.IPDATransferLoadService#refrushLoadTaskExpressDetail(java.lang.String)
	 */
	@Override
	public List<LoadGoodsDetailDto> refrushLoadTaskExpressDetail(String taskNo) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setDeliverBillVaryStatusService(IDeliverBillVaryStatusService deliverBillVaryStatusService) {
		this.deliverBillVaryStatusService = deliverBillVaryStatusService;
	}

	/**
	 * FOSS根据PDA传过来的运单号和当前所在转运场，查找出该运单走货路径中的下一转运场，并把结果返回给PDA。
	 * 
	 * @author zwd 200968 2015-07-21
	 * @param waybillNo
	 * @param orgCode
	 * @return
	 */
	public PackagePathLoaderDto unlockPackagePathDetail(String waybillNo, String orgCode) {
		LOGGER.error("PDA传递的参数>>运单号:" + waybillNo + ",当前外场:" + orgCode);

		if (waybillNo == null || orgCode == null) {
			LOGGER.error("PDA传递的参数>>运单号:" + waybillNo + ",当前外场:" + orgCode + "有为空");
			// throw new
			// Exception("PDA传递的参数>>运单号:"+waybillNo+",当前外场:"+orgCode+"有为空");
		}
		// 异常处理
		PackagePathLoaderDto packagePathLoaderDto = new PackagePathLoaderDto();
		packagePathLoaderDto = pdaLoadDao.unlockPackagePathDetail(waybillNo, orgCode);

		if (packagePathLoaderDto != null) {
			return packagePathLoaderDto;
		} else {
			// throw new Exception("运单"+waybillNo+"的下一部门有多个");
			return null;
		}
	}

	@Override
	public LoadTaskResultDto createLoadTask(WKLoadTaskDto wkLoadTask) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String finishLoadTask(FinshWKLoadTaskDto finshWKLoadTaskDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public String submitLoadTask(SmtWKLoadTaskDto dto) {
		String trace = buildInvokeTrace(new Throwable());
		LOGGER.info("PDA提交装车任务：" + dto.toString() + " ===== call trace: " + trace);
		String taskNo = dto.getLoadTaskNo();
		if (pdaLoadDao.queryUnSubmitPDAQty(taskNo) > 1) {
			// 如果有PDA未提交任务，则失败
			throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_EXTIST_UNFINISH_PDA_MESSAGECODE);
		} else {
			LoadTaskEntity loadTask = null;
			MutexElement mutexElement = null;
			try {
				// 查询派送单号
				loadTask = pdaLoadDao.queryLoadTaskByTaskNo(taskNo);
			} catch (CannotAcquireLockException e) {
				throw new TfrBusinessException("任务提交中请稍后再试");
			}
			if (loadTask != null && LoadConstants.LOAD_TASK_TYPE_DELIVER.equals(loadTask.getTaskType())) {
				mutexElement = new MutexElement(loadTask.getDeliverBillNo(), "派送单编号", MutexElementType.DELIVERBILL_NO);
				// 互斥锁定
				boolean isLocked = businessLockService.lock(mutexElement, 0);
				// 若未上锁
				if (!isLocked) {
					// 抛出派送单异常
					throw new TfrBusinessException("派送单修改中请稍后再试");
				}

			}
			// 如果装车对象为空，直接返回正确的值
			if (loadTask == null) {
				// 成功
				return TransferPDADictConstants.SUCCESS;
			}
			try {
				// 完成装车任务
				pdaLoadService.finishLoadAndSoOn(loadTask, dto.getLoadEndTime(), dto.getDeviceNo(), dto.getLoaderCode());
				
				//生成悟空交接单
				//如果是营业部装车，不需要生成悟空交接单---332219
				if(!StringUtils.equals(loadTask.getTaskType(),TransferPDADictConstants.LOAD_TASK_TYPE_SALES_DISTANCE)){
					sendToWKAndGetHandover(dto, loadTask);
				}
			} finally {
				if (mutexElement != null) {
					businessLockService.unlock(mutexElement);
				}
			}

			// 成功
			return TransferPDADictConstants.SUCCESS;
		}
	}

	/**
	 * 生成悟空交接单，并获取交接单信息
	 * @param dto
	 * @param loadTask
	 */
	private void sendToWKAndGetHandover(SmtWKLoadTaskDto dto, LoadTaskEntity loadTask) {
		Log.error("生成悟空交接单开始..." + dto);
		//是否是零担，不是零担悟空才生成交接单
		if (!LoadSourceEnum.LINGDAN.getCode().equals(dto.getSendType())) {
			boolean tfrSwitch4Ecs = configurationParamsService.queryTfrSwitch4Ecs();
			LOGGER.info("对接悟空开关（是否smtTask悟空同步）：" + tfrSwitch4Ecs);
			if (tfrSwitch4Ecs
					&& !TransferPDADictConstants.LOAD_TASK_TYPE_DIVISION.equals(loadTask.getTaskType())
					&& !TransferPDADictConstants.LOAD_TASK_TYPE_DELIVER.equals(loadTask.getTaskType())) {
				LOGGER.info("非纯零担任务： " + dto.getSendType());
				dto.setTaskStatus(null);
				dto.setCancel(false);
				dto.setDeviceNo(null);
				dto.setLoaderCode(null);
				// addDataToWKLOADTemp(dto.getLoadTaskNo(), dto, loadStatueEnum.FINISH.getCode());

				/////////////////// 第二套改同步
				Map<String, Object> result = fossToWkService.sysnSubmitLoadToWK(JSON.toJSONString(dto));

				String status = null;
				if (result == null || !result.containsKey("status") || result.get("status") == null) {
					throw new TfrBusinessException("ECS - 提交装车任务异常");
				}

				status = result.get("status").toString();
				if (!status.equals("1")) {
					throw new TfrBusinessException("ECS - " + result.get("exMsg"));
				}

				WKBillEntity wkBillEntity = null;
				if (result.get("data") == null) {
					LOGGER.error("提交装车任务：悟空返回信息为空");
				} else {
					if (result.get("data") instanceof WKBillEntity) {
						wkBillEntity = (WKBillEntity) result.get("data");
					} else {
						String handoverString = result.get("data").toString();
						LOGGER.info("第二套同步data:" + handoverString);
						wkBillEntity = JSON.parseObject(handoverString, WKBillEntity.class);
					}
					if (wkBillEntity != null) {
						Map<String, String> insertWKBill = wKTfrBillService.insertWKBill(wkBillEntity);
						if ("1".equals(insertWKBill.get("result"))) {
							LOGGER.info("悟空交接单插入成功");
						} else {
							LOGGER.error("没有悟空交接单信息，未插入任何信息");
						}
					}
				}
			}
		}
		Log.error("生成悟空交接单结束..." + dto);
	}

	/**
	* @description 储存同步到悟空的数据到临时表
	* @param taskNo
	* @param data
	* @param type
	* @version 1.0
	* @author 328864-foss-xieyang
	* @update 2016年5月10日 上午9:45:51
	*/
	private void addDataToWKLOADTemp(String taskNo, Object data, int type) {
		WKLoadTempDto dto = new WKLoadTempDto();
//		dto.setCreateTime(new Date());//2016年8月22日 11:39:32新增字段335284 第一套加字
		String str = JSON.toJSONString(data);
		dto.setJsonData(str);
		dto.setTaskNo(taskNo);
		dto.setTaskType(type);
		wkLoadTempDao.inertData(dto);
		
	}
	
	private String buildInvokeTrace(Throwable throwable) {
		try {
			StackTraceElement[] elements = throwable.getStackTrace();
			StringBuilder sb = new StringBuilder();
			for (StackTraceElement e : elements) {
				sb.append(e.getClassName()).append(":").append(e.getMethodName()).append(":").append(e.getLineNumber()).append("\r\n");
			}
			return sb.toString();
		} catch (Exception e) {
			return "";
		}
	}
	
	public LoadSaleTaskResultDto createSaleLoadTaskResult(String taskNo, String orgCode, LoaderParticipationEntity creator,
			List<LoaderDto> loaders, VehicleAssociationDto vehicleDto) {
		LoadSaleTaskResultDto loadSaleTaskResultDto = new LoadSaleTaskResultDto();
		if (creator != null) {
			loadSaleTaskResultDto.setCreatorCode(creator.getLoaderCode());
			loadSaleTaskResultDto.setCreatorName(creator.getLoaderName());
		}
		loadSaleTaskResultDto.setLoaders(loaders);

		// 车辆额定载重、额定净空
		if (vehicleDto != null) {
			double vehicleDeadWeight = 0;
			if (vehicleDto.getVehicleDeadLoad() != null) {
				vehicleDeadWeight = vehicleDto.getVehicleDeadLoad().doubleValue();
			}
			double vehicleDeadVolume = 0;
			if (vehicleDto.getVehicleSelfVolume() != null) {
				vehicleDeadVolume = vehicleDto.getVehicleSelfVolume().doubleValue();
			}
			loadSaleTaskResultDto.setVehicleDeadVolume(vehicleDeadVolume);
			loadSaleTaskResultDto.setVehicleDeadWeight(vehicleDeadWeight);
			loadSaleTaskResultDto.setVehicleLengthName(vehicleDto.getVehicleLengthName());
			if (vehicleDto.getVehicleUsedTypeName() != null) {
				if (vehicleDto.getVehicleUsedTypeName().equals("长途车")) {
					if (StringUtils.isNotBlank(vehicleDto.getVehicleOrganizationCode())) {
						loadSaleTaskResultDto.setVehicleLengthName("长途公司车");
					}
				}
			}
		}
		loadSaleTaskResultDto.setTaskNo(taskNo);
		// zwd 调用综合接口，根据部门编码去查询重泡比，如果存在则使用查询到的重泡比，如果不存在则使用公用的重量体积转换参数。
		BigDecimal converParameter = conversionParameters(orgCode);
		loadSaleTaskResultDto.setExpressConvertParameter(converParameter);
		return loadSaleTaskResultDto;
	}
	
	/**
	 * 下拉运单明细流水号
	 * 
	 * @param loadGoodsDetails
	 * 
	 * @return
	 */
	public List<LoadSaleGoodsDetailDto> loadSaleSerialDetailToWayBillDetail(List<LoadGoodsDetailSerialDto> loadGoodsDetails,
			String orgCode) {
		// 如果货物列表不为空
		if (CollectionUtils.isNotEmpty(loadGoodsDetails)) {
			// 查询所有快递编码集合
			List<String> expressCodeList = productService4Dubbo.getAllLevels3ExpressProductCode();
			List<GoodsAreaEntity> valueGoodsAreaList = goodsAreaService.queryGoodsAreaListByType(orgCode,
					DictionaryValueConstants.BSE_GOODSAREA_TYPE_VALUABLE);
			List<GoodsAreaEntity> packGoodsAreaList = goodsAreaService.queryGoodsAreaListByType(orgCode,
					DictionaryValueConstants.BSE_GOODSAREA_TYPE_PACKING);

			String valueGoodsAreaCode = null;
			String packGoodsAreaCode = null;
			if (CollectionUtils.isNotEmpty(valueGoodsAreaList)) {
				valueGoodsAreaCode = valueGoodsAreaList.get(0).getGoodsAreaCode();
			}
			if (CollectionUtils.isNotEmpty(packGoodsAreaList)) {
				packGoodsAreaCode = packGoodsAreaList.get(0).getGoodsAreaCode();
			}
			// 创建明细列表
			List<LoadSaleGoodsDetailDto> details = new ArrayList<LoadSaleGoodsDetailDto>();
			// 返回给PDA装车任务列表单条Dto
			LoadSaleGoodsDetailDto detailTemp;
			// 流水号列表
			List<PDAGoodsSerialNoDto> serials;
			PDAGoodsSerialNoDto serial;
			// 标志位
			boolean beExist = false;
			BigDecimal converParameter = conversionParameters(orgCode);
			// 遍历货物列表
			for (LoadGoodsDetailSerialDto goods : loadGoodsDetails) {
				beExist = false;
				/**** 判断产品类型是否为快递 2015-8-20 17:02:33 linyuzhu ******/
				boolean expressFlag = false;// 快递标识
				for (String expressCode : expressCodeList) {
					if (StringUtils.equals(goods.getTransportTypeCode(), expressCode)) {
						expressFlag = true;
						break;
					}
				}
				// 遍历明细列表
				for (LoadSaleGoodsDetailDto detail : details) {
					if (detail.getWayBillNo().equals(goods.getWayBillNo())) {
						beExist = true;
						// 去除重复流水号
						boolean beExtistSerial = false;
						if (CollectionUtils.isNotEmpty(detail.getSerialNos())) {
							for (PDAGoodsSerialNoDto serialTemp : detail.getSerialNos()) {
								if (StringUtil.isNotBlank(serialTemp.getSerialNo())
										&& serialTemp.getSerialNo().equals(goods.getSerialNo())) {
									beExtistSerial = true;
								}
							}
						}
						if (!beExtistSerial) {
							serial = new PDAGoodsSerialNoDto();
							// Sets the 是否未打包装
							serial.setIsUnPacking(goods.getIsUnPacking());
							serial.setIsToDoList(goods.getIsToDoList());
							// Sets the 流水号
							serial.setSerialNo(goods.getSerialNo());
							// set流水号
							serial.setGoodsPosition(goods.getGoodsPosition());
							serial.setStockAreaCode(goods.getStockAreaCode());
							/*
							 * List<String> todoItems =
							 * waybillRfcService.queryTodoWhenLoadTruck(goods.
							 * getWayBillNo(),goods.getSerialNo(),orgCode);
							 * if(CollectionUtils.isNotEmpty(todoItems)){
							 * serial.setHasToDoList(FossConstants.YES); }else{
							 * serial.setHasToDoList(FossConstants.NO); }
							 */
							// serial.setHasToDoList(goods.getHasToDoList());
							//
							// 加入流水号
							detail.getSerialNos().add(serial);
							// 如果是快递 则累加其重量体积

							/********
							 * 加入快递空运(商务专递)产品类型的判断 2015-8-20 16:56:14
							 * 263072-linyuzhu
							 *******/
							// if(
							// StringUtils.equals(goods.getTransportTypeCode(),
							// "PACKAGE")
							// ||
							// StringUtils.equals(goods.getTransportTypeCode(),"RCP")
							// ||StringUtils.equals(goods.getTransportTypeCode(),"EPEP")){
							if (expressFlag) {
								if (StringUtils.equals(FossConstants.NO, goods.getBeSortScan())) {
									// 没有上计泡机则 按照重泡比计算
									BigDecimal volume = new BigDecimal(goods.getWeight()
											/ (goods.getWayBillQty() == 0 ? 1 : goods.getWayBillQty()))
													.multiply(converParameter)
													.divide(new BigDecimal(1000), 4, BigDecimal.ROUND_HALF_UP);
									detail.setVolume(detail.getVolume() + volume.doubleValue());
									detail.setWeight(detail.getWeight() + new BigDecimal(goods.getWeight())
											.divide(new BigDecimal(goods.getWayBillQty()), 4, BigDecimal.ROUND_HALF_UP)
											.doubleValue());
								} else {
									// 上计泡机则按照称重的重量体积计算
									detail.setVolume(detail.getVolume() + goods.getVolume());
									detail.setWeight(detail.getWeight() + goods.getWeight());
								}
							}
							if (FossConstants.YES.equals(goods.getIsUnPacking())) {
								if (StringUtils.isNotBlank(detail.getNotes())) {
									// 备注中如果有包含：代打包装字样
									if (!detail.getNotes().contains("代打包装")) {
										detail.setNotes(detail.getNotes() + " 代打包装");
									}
								} else {
									detail.setNotes("代打包装");
								}
							}
							if (FossConstants.YES.equals(goods.getIsToDoList())) {
								if (StringUtils.isNotBlank(detail.getNotes())) {
									// 备注中如果有包含：代打包装字样
									if (!detail.getNotes().contains("有更改")) {
										detail.setNotes(detail.getNotes() + " 有更改");
									}
								} else {
									detail.setNotes("有更改");
								}
							}
						}
					}
				}
				if (!beExist) {
					// 流水号明细
					serial = new PDAGoodsSerialNoDto();
					// Sets the 是否未打包装
					serial.setIsUnPacking(goods.getIsUnPacking());
					serial.setIsToDoList(goods.getIsToDoList());
					// Sets the 流水号.
					serial.setSerialNo(goods.getSerialNo());
					serial.setStockAreaCode(goods.getStockAreaCode());
					// set货物位置
					serial.setGoodsPosition(goods.getGoodsPosition());
					serials = new ArrayList<PDAGoodsSerialNoDto>();
					serials.add(serial);
					detailTemp = new LoadSaleGoodsDetailDto();
					// 包装货区
					detailTemp.setPackGoodsAreaCode(packGoodsAreaCode);
					// 贵重物品货区
					detailTemp.setValueGoodsAreaCode(valueGoodsAreaCode);
					// Sets the 流水号
					detailTemp.setSerialNos(serials);
					// //Sets the 是否必走货
					// detailTemp.setBePriorityGoods(goods.getBePriorityGoods());
					// Gets the 货名
					detailTemp.setGoodsName(goods.getGoodsName());
					// Sets the 是否有更改
					// detailTemp.setModifyContent(goods.getModifyState());
					// Sets the 是否贵重物品
					detailTemp.setIsValue(goods.getIsValue());
					// detailTemp.setModifyState(goods.getModifyState());
					// Sets the 更改内容
					// detailTemp.setModifyContent(goods.getModifyContent());
					StringBuilder str = new StringBuilder();
					// 判断提示
					if (FossConstants.YES.equals(goods.getIsToDoList())) {
						// 更改
						str.append("有更改 ");
					} else if (FossConstants.YES.equals(goods.getIsUnPacking())) {
						// 包装
						str.append(" 代打包装 ");
					} else if (FossConstants.YES.equals(goods.getIsValue())) {
						// 贵重物品
						str.append(" 贵重物品");
					}
					// Sets the 备注
					detailTemp.setNotes(str.toString().trim());
					// s.append(b);
					// Sets the 包装
					detailTemp.setPacking(goods.getPacking());
					// Sets the 到达部门编码
					detailTemp.setReachOrgCode(goods.getReachOrgCode());
					// Sets the 收货部门编码
					detailTemp.setReceiveOrgCode(goods.getReceiveOrgCode());
					// Sets the 到达部门名称
					detailTemp.setReachOrgName(goods.getReachOrgName());
					// Sets the 收货部门名称
					detailTemp.setReceiveOrgName(goods.getReceiveOrgName());
					// Sets the 入库时间
					detailTemp.setStockTime(goods.getStockTime());
					// Gets the 任务编号
					detailTemp.setTaskNo(goods.getTaskNo());
					// Gets the 运输性质
					detailTemp.setTransportType(goods.getTransportType());
					// Gets the 体积.
					// 快递转换体积参数
					// zwd
					// 调用综合接口，根据部门编码去查询重泡比，如果存在则使用查询到的重泡比，如果不存在则使用公用的重量体积转换参数。
					// 由于计泡机对快递件进行 称重量方 （安件）所有，统计运单的重量体积就必须按件累加求和

					if (converParameter.compareTo(BigDecimal.ZERO) == 0) {
						detailTemp.setVolume(goods.getVolume());
					} else {
						/********
						 * 加入快递空运(商务专递)产品类型的判断 2015-8-20 17:03:33
						 * 263072-linyuzhu
						 *******/
						if (expressFlag) {
							if (StringUtils.equals(FossConstants.NO, goods.getBeSortScan())) {
								BigDecimal volume = new BigDecimal(
										goods.getWeight() / (goods.getWayBillQty() == 0 ? 1 : goods.getWayBillQty()))
												.multiply(converParameter)
												.divide(new BigDecimal(1000), 4, BigDecimal.ROUND_HALF_UP);
								detailTemp.setVolume(volume.doubleValue());
							} else {
								// 按件的重量设置，后面有其他流水号时累加重量体积
								// BigDecimal volume = new
								// BigDecimal(goods.getVolume()).divide(new
								// BigDecimal(goods.getWayBillQty()),4,BigDecimal.ROUND_HALF_UP);
								detailTemp.setVolume(goods.getVolume());
							}

						} else {
							detailTemp.setVolume(goods.getVolume());
						}
					}
					// Sets the 运单号
					detailTemp.setWayBillNo(goods.getWayBillNo());

					// Sets the 开单件数
					detailTemp.setWayBillQty(goods.getWayBillQty());
					// 按件的重量设置，后面有其他流水号时累加重量体积
					/********
					 * 加入快递空运(商务专递)产品类型的判断 2015-8-20 17:03:33 263072-linyuzhu
					 *******/
					if (expressFlag) {
						// if(StringUtils.equals(goods.getTransportTypeCode(),
						// "PACKAGE")
						// ||
						// StringUtils.equals(goods.getTransportTypeCode(),"RCP")
						// ||StringUtils.equals(goods.getTransportTypeCode(),"EPEP")){
						if (StringUtils.equals(FossConstants.NO, goods.getBeSortScan())) {
							// 按件的重量设置，后面有其他流水号时累加重量体积
							detailTemp.setWeight(new BigDecimal(goods.getWeight())
									.divide(new BigDecimal(goods.getWayBillQty()), 4, BigDecimal.ROUND_HALF_UP)
									.doubleValue());
						} else {

							detailTemp.setWeight(goods.getWeight());
						}
					} else {
						// Gets the 重量.
						detailTemp.setWeight(goods.getWeight());
					}

					// Gets the 库存件数
					detailTemp.setStockQty(goods.getStockQty());
					// 货区
					detailTemp.setStockAreaCode(goods.getStockAreaCode());
					// 操作数量
					detailTemp.setOperateQty(goods.getOperateQty());
					// Sets the 是否合车
					detailTemp.setBeJoinCar(goods.getBeJoinCar());
					detailTemp.setStationNumber(goods.getStationNumber());
					// 运单对应行政属性
					detailTemp.setReceiveCustDistName(goods.getReceiveCustDistName());
					// 查询必走货
					WaybillStockQueryDto waybillStockQueryDto = new WaybillStockQueryDto();
					waybillStockQueryDto.setOrgCode(goods.getReachOrgCode());
					waybillStockQueryDto.setGoodsAreaCode(goods.getStockAreaCode());
					List<WaybillStockQueryDto> list = stockService.queryPriorityGoods(waybillStockQueryDto, 10, 0);
					// ISSUE-3445 liubinbin 2013-07-30
					if (list != null && list.size() > 0) {
						detailTemp.setBePriorityGoods(FossConstants.YES);
					} else {
						detailTemp.setBePriorityGoods(FossConstants.NO);
					}
					// 快递：如果运输性质为包裹，则为优先货

					/********
					 * 加入快递空运(商务专递)产品类型的判断 2015-8-20 17:03:33 263072-linyuzhu
					 *******/
					if (expressFlag) {
						// if(LoadConstants.PRODUCT_CODE_PACKAGE.equals(detailTemp.getTransportTypeCode())||
						// LoadConstants.PRODUCT_CODE_RCP.equals(detailTemp.getTransportTypeCode())
						// ||LoadConstants.PRODUCT_CODE_EPEP.equals(detailTemp.getTransportTypeCode())){
						detailTemp.setBePriorityGoods(FossConstants.YES);
					} else {
						detailTemp.setBePriorityGoods(FossConstants.NO);
					}
					ActualFreightEntity actualFreightEntity = new ActualFreightEntity();
					actualFreightEntity = actualFreightService.queryByWaybillNo(goods.getWayBillNo());
					if (actualFreightEntity != null) {
						if (StringUtils.equals(actualFreightEntity.getWaybillType(), "EWAYBILL")) {
							detailTemp.setBeEWaybill(FossConstants.YES);
						} else {
							detailTemp.setBeEWaybill(FossConstants.NO);
						}
					} else {
						detailTemp.setBeEWaybill(FossConstants.NO);
					}
					// 加入明细
					details.add(detailTemp);
				}
			}
			StringBuilder s = new StringBuilder();
			s.append("刷新装车任务:");
			for (LoadSaleGoodsDetailDto d : details) {
				s.append("任务号：" + d.getTaskNo());
				s.append("运单号:" + d.getWayBillNo());
				//s.append("更改状态:"+d.getModifyState());
				s.append("贵重物品:" + d.getIsValue());
				if (CollectionUtils.isNotEmpty(d.getSerialNos())) {
					for (PDAGoodsSerialNoDto ss : d.getSerialNos()) {
						s.append("流水号:");
						s.append(ss.getSerialNo());
						s.append("打木架：" + ss.getIsUnPacking());
						s.append("代办事项" + ss.getIsToDoList());
					}
				}
				s.append("/&n");
			}
			LOGGER.error(s.toString());
			return details;
		} else {
			return null;
		}
	}
	
	@Override
	public List<LoadSaleGoodsDetailDto> refrushSaleLoadTaskExpressDetail(
			String taskNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LoadSaleTaskResultDto createSaleLoadTask(LoadSaleTaskDto loadTask) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LoadSaleGoodsDetailDto> refrushSaleLoadTaskDetail(String taskNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LoadSaleGoodsDetailDto> refrushSaleLoadTaskPackageDetail(
			String taskNo) {
		// TODO Auto-generated method stub
		return null;
	}
}