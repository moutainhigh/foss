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
 *  PROJECT NAME  : tfr-stockchecking-api
 *
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/stockchecking/api/server/dao/IStTaskDao.java
 *
 *  FILE NAME          :IStTaskDao.java
 *
 *  AUTHOR  : FOSS中转系统开发组
 *
 *  TIME              :
 *
 *  HOME PAGE    :  http://www.deppon.com
 *
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.unload.server.service.impl;

/**
 * 引入包
 */

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAdministrativeRegionsService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILoadAndUnloadSquadService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOutfieldService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IPlatformService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IPorterService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IReturnGoodsRequestEntityService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IVehicleAgencyDeptService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LoadAndUnloadSquadEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OutfieldEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PlatformEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PorterEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ReturnGoodsRequestEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;

import com.deppon.foss.module.pickup.pricing.dubbo.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.dubbo.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.waybill.api.server.service.IActualFreightService;
import com.deppon.foss.module.pickup.waybill.api.server.service.ICompensateSpreadService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IPdaScanService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcService;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.PdaScanQueryDto;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IAirHandOverBillService;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirHandOverBillDto;
import com.deppon.foss.module.transfer.arrival.api.server.dao.IArrivalDao;
import com.deppon.foss.module.transfer.arrival.api.shared.dto.WaybillPlanArriveTimeDto;
import com.deppon.foss.module.transfer.common.api.server.service.IBillNumService;
import com.deppon.foss.module.transfer.common.api.server.service.IFOSSToWkService;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrJobTodoService;
import com.deppon.foss.module.transfer.common.api.shared.define.BusinessGoalContants;
import com.deppon.foss.module.transfer.common.api.shared.define.BusinessSceneConstants;
import com.deppon.foss.module.transfer.common.api.shared.domain.FossToWKResponseEntity;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.common.server.utils.JSONUtils;
import com.deppon.foss.module.transfer.departure.api.server.dao.ISharedDao;
import com.deppon.foss.module.transfer.departure.api.shared.dto.AutoTaskDTO;
import com.deppon.foss.module.transfer.load.api.server.dao.IPDAExpressPackageDao;
import com.deppon.foss.module.transfer.load.api.server.dao.IPDALoadDao;
import com.deppon.foss.module.transfer.load.api.server.service.IHandOverBillService;
import com.deppon.foss.module.transfer.load.api.server.service.IPDACommonService;
import com.deppon.foss.module.transfer.load.api.server.service.IVehicleAssembleBillService;
import com.deppon.foss.module.transfer.load.api.shared.define.LoadConstants;
import com.deppon.foss.module.transfer.load.api.shared.define.SealConstant;
import com.deppon.foss.module.transfer.load.api.shared.define.TaskTruckConstant;
import com.deppon.foss.module.transfer.load.api.shared.domain.ExpressPackageDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.ExpressPackageEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoaderParticipationEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.PDATaskEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.SealEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.UpdateHandOverBillStateDto;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDAUnloadTaskService;
import com.deppon.foss.module.transfer.pda.api.shared.define.TransferPDADictConstants;
import com.deppon.foss.module.transfer.pda.api.shared.domain.PDAAssignUnloadBillEntity;
import com.deppon.foss.module.transfer.pda.api.shared.domain.PDAAssignUnloadTaskEntity;
import com.deppon.foss.module.transfer.pda.api.shared.domain.PDApreplatformEntity;
import com.deppon.foss.module.transfer.pda.api.shared.dto.LoaderDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.PDAGoodsSerialNoDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.UnloadGoodsDetailDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.UnloadScanDetailDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.UnloadTaskDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.UnloadTaskResultDto;
import com.deppon.foss.module.transfer.pda.api.shared.exception.TransferPDAExceptionCode;
import com.deppon.foss.module.transfer.scheduling.api.define.PlatformDispatchConstants;
import com.deppon.foss.module.transfer.scheduling.api.define.TransportPathConstants;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ICalculateTransportPathService;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.FeedbackDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;
import com.deppon.foss.module.transfer.stock.api.define.StockConstants;
import com.deppon.foss.module.transfer.stock.api.server.service.IStockService;
import com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.StockEntity;
import com.deppon.foss.module.transfer.unload.api.server.dao.IAssignUnloadTaskDao;
import com.deppon.foss.module.transfer.unload.api.server.dao.IPDAUnloadTaskDao;
import com.deppon.foss.module.transfer.unload.api.server.dao.IUnloadTaskDao;
import com.deppon.foss.module.transfer.unload.api.server.service.IPDAUnloadService;
import com.deppon.foss.module.transfer.unload.api.server.service.IUnloadTaskService;
import com.deppon.foss.module.transfer.unload.api.shared.define.ChangeLabelGoodsConstants;
import com.deppon.foss.module.transfer.unload.api.shared.define.UnloadConstants;
import com.deppon.foss.module.transfer.unload.api.shared.domain.AssignUnloadTaskEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.ChangeLabelGoodsEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadBillDetailEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadSerialNoDetailEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadTaskEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadWaybillDetailEntity;
import com.deppon.foss.module.transfer.unload.api.shared.dto.ArriveBillDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.ExpressCancelUnloadTaskDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.ExpressLoaderParticipateDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.ExpressUnloadTaskAddnewDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.ExpressUnloadTaskDetailDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.PDAUnloadAsyncBillMsgDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.PDApreplatformDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.SyncPDAUnloadTaskDataToWkDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.UnloadBillDetailDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.UnloadGoodsSerialDetailDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.UnloadSerialNoDetailDto;
import com.deppon.foss.module.transfer.unload.api.tools.UnloadCommonUtils;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * pda卸车service.
 *
 *
 *
 * @author dp-duyi
 * @date 2012-11-28 上午10:12:57
 */

public class PDAUnloadTaskService implements IPDAUnloadTaskService, IPDAUnloadService {

    private final static Logger LOGGER = LoggerFactory.getLogger(PDAUnloadTaskService.class);

    /**
     * 待办job service接口
     */
    private ITfrJobTodoService tfrJobTodoService;

    public void setTfrJobTodoService(ITfrJobTodoService tfrJobTodoService) {
        this.tfrJobTodoService = tfrJobTodoService;
    }

    private IBillNumService billNumService;

    private IFOSSToWkService fossToWkService;

    private IConfigurationParamsService configurationParamsService;

    public void setConfigurationParamsService(IConfigurationParamsService configurationParamsService) {
        this.configurationParamsService = configurationParamsService;
    }

    public void setFossToWkService(IFOSSToWkService fossToWkService) {
        this.fossToWkService = fossToWkService;
    }

    public void setBillNumService(IBillNumService billNumService) {
        this.billNumService = billNumService;
    }

    /**
     * 分配卸车任务dao
     */
    private IAssignUnloadTaskDao assignUnloadTaskDao;

    /** 正单交接单服务. */
    private IAirHandOverBillService airHandOverBillService;

    static final Logger logger = LoggerFactory.getLogger(PDAUnloadTaskService.class);
    private IPDAUnloadService pdaUnloadService;
    private IVehicleAgencyDeptService vehicleAgencyDeptService;
    /**快递集中卸货 -调用接送货接口 zwd 200968*/
    private IPdaScanService pdaScanService;

    /**快递集中卸货 -调用接送货接口 zwd 200968*/
    public void setPdaScanService(IPdaScanService pdaScanService) {
        this.pdaScanService = pdaScanService;
    }

    public void setAssignUnloadTaskDao(IAssignUnloadTaskDao assignUnloadTaskDao) {
        this.assignUnloadTaskDao = assignUnloadTaskDao;
    }

    /**行政区域 service */
    private IAdministrativeRegionsService administrativeRegionsService;

    public void setAdministrativeRegionsService(
            IAdministrativeRegionsService administrativeRegionsService) {
        this.administrativeRegionsService = administrativeRegionsService;
    }

    public void setVehicleAgencyDeptService(
            IVehicleAgencyDeptService vehicleAgencyDeptService) {
        this.vehicleAgencyDeptService = vehicleAgencyDeptService;
    }

    public void setPdaUnloadService(IPDAUnloadService pdaUnloadService) {
        this.pdaUnloadService = pdaUnloadService;
    }

    public void setAirHandOverBillService(
            IAirHandOverBillService airHandOverBillService) {
        this.airHandOverBillService = airHandOverBillService;
    }

    private IVehicleAssembleBillService vehicleAssembleBillService;

    /**
     * The Constant logger.
     *
     *
     */

    public void setVehicleAssembleBillService(
            IVehicleAssembleBillService vehicleAssembleBillService) {
        this.vehicleAssembleBillService = vehicleAssembleBillService;
    }

    /**
     * The waybill rfc service.
     *
     *
     */
    private IOutfieldService outfieldService;

    public void setOutfieldService(IOutfieldService outfieldService) {
        this.outfieldService = outfieldService;
    }

    private IWaybillRfcService waybillRfcService;

    /**
     * Sets the waybill rfc service.
     *
     *
     * @param waybillRfcService the new waybill rfc service
     */
    public void setWaybillRfcService(IWaybillRfcService waybillRfcService) {
        this.waybillRfcService = waybillRfcService;
    }

    /**
     * 晚到补差价接口
     */

    private ICompensateSpreadService compensateSpreadService;

    public void setCompensateSpreadService(
            ICompensateSpreadService compensateSpreadService) {
        this.compensateSpreadService = compensateSpreadService;
    }

    /**
     * The employee service.
     *
     *
     */
    private IEmployeeService employeeService;

    /**
     * Sets the employee service.
     *
     * @param employeeService the new employee service
     */
    public void setEmployeeService(IEmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * The waybill manager service.
     *
     *
     */
    private IWaybillManagerService waybillManagerService;

    /**
     * Sets the waybill manager service.
     *
     *
     * @param waybillManagerService the new waybill manager service
     */
    public void setWaybillManagerService(
            IWaybillManagerService waybillManagerService) {
        this.waybillManagerService = waybillManagerService;
    }

    /**
     *
     * The hand over bill service.
     *
     */
    private IHandOverBillService handOverBillService;

    /**
     * Sets the hand over bill service.
     *
     * @param handOverBillService the new hand over bill service
     */
    public void setHandOverBillService(IHandOverBillService handOverBillService) {
        this.handOverBillService = handOverBillService;
    }

    /**
     * The pda unload task dao.
     *
     *
     */
    private IPDAUnloadTaskDao pdaUnloadTaskDao;

    /**
     * Sets the pda unload task dao.
     *
     * @param pdaUnloadTaskDao the new pda unload task dao
     */
    public void setPdaUnloadTaskDao(IPDAUnloadTaskDao pdaUnloadTaskDao) {
        this.pdaUnloadTaskDao = pdaUnloadTaskDao;
    }

    /**
     * The unload task dao.
     *
     */
    IUnloadTaskDao unloadTaskDao;

    /**
     * Sets the unload task dao.
     *
     *
     * @param unloadTaskDao the new unload task dao
     */
    public void setUnloadTaskDao(IUnloadTaskDao unloadTaskDao) {
        this.unloadTaskDao = unloadTaskDao;
    }

    /**
     * The pda load dao.
     *
     *
     */
    private IPDALoadDao pdaLoadDao;

    /**
     * Sets the pda load dao.
     *
     * @param pdaLoadDao the new pda load dao
     */
    public void setPdaLoadDao(IPDALoadDao pdaLoadDao) {
        this.pdaLoadDao = pdaLoadDao;
    }

    /**
     * The platform service.
     *
     *
     */
    private IPlatformService platformService;

    /**
     * Sets the platform service.
     *
     * @param platformService the new platform service
     */
    public void setPlatformService(IPlatformService platformService) {
        this.platformService = platformService;
    }

    /**
     * The org administrative info service.
     *
     *
     */
    private IOrgAdministrativeInfoService orgAdministrativeInfoService;

    /**
     * Sets the org administrative info service.
     *
     * @param orgAdministrativeInfoService the new org administrative info service
     */
    public void setOrgAdministrativeInfoService(IOrgAdministrativeInfoService orgAdministrativeInfoService) {
        this.orgAdministrativeInfoService = orgAdministrativeInfoService;
    }

    /**
     *
     * The sale department service.
     *
     */
    private ISaleDepartmentService saleDepartmentService;

    /**
     * Sets the sale department service.
     *
     * @param saleDepartmentService the new sale department service
     */
    public void setSaleDepartmentService(
            ISaleDepartmentService saleDepartmentService) {
        this.saleDepartmentService = saleDepartmentService;
    }

    /**
     * 走货路径 service.
     *
     */
    private ICalculateTransportPathService calculateTransportPathService;

    /**
     * Sets the calculate transport path service.
     *
     * @param calculateTransportPathService the new calculate transport path service
     */
    public void setCalculateTransportPathService(
            ICalculateTransportPathService calculateTransportPathService) {
        this.calculateTransportPathService = calculateTransportPathService;
    }

    /**
     * The stock service.
     *
     */
    private IStockService stockService;

    /**
     * Sets the stock service.
     *
     * @param stockService the new stock service
     */
    public void setStockService(IStockService stockService) {
        this.stockService = stockService;
    }

    /**
     *  The arrival dao.
     *
     */
    private IArrivalDao arrivalDao;

    /**
     * Sets the arrival dao.
     *
     * @param arrivalDao the new arrival dao
     */
    public void setArrivalDao(IArrivalDao arrivalDao) {
        this.arrivalDao = arrivalDao;
    }

    /**
     * The unload task service.
     *
     *
     */
    private IUnloadTaskService unloadTaskService;

    /**
     * Sets the unload task service.
     *
     * @param unloadTaskService the new unload task service
     */
    public void setUnloadTaskService(IUnloadTaskService unloadTaskService) {
        this.unloadTaskService = unloadTaskService;
    }

    /**
     * The pda common service.
     *
     *
     */
    public IPDACommonService pdaCommonService;

    /**
     * Sets the pda common service.
     *
     *
     * @param pdaCommonService the new pda common service
     */
    public void setPdaCommonService(IPDACommonService pdaCommonService) {
        this.pdaCommonService = pdaCommonService;
    }

    /**
     * The porter service.
     *
     *
     */
    private IPorterService porterService;

    /**
     * Sets the porter service.
     *
     *
     *
     * @param porterService the new porter service
     */
    public void setPorterService(IPorterService porterService) {
        this.porterService = porterService;
    }

    /**
     * The load and unload squad service.
     *
     */
    private ILoadAndUnloadSquadService loadAndUnloadSquadService;

    /**
     * Sets the load and unload squad service.
     *
     * @param loadAndUnloadSquadService the new load and unload squad service
     */
    public void setLoadAndUnloadSquadService(ILoadAndUnloadSquadService loadAndUnloadSquadService) {
        this.loadAndUnloadSquadService = loadAndUnloadSquadService;
    }

    /**
     * The product service.
     *
     */
    @Resource
    public IProductService productService4Dubbo;

    /**
     * 注掉set方法  使用注解注入dubbo服务
     * Sets the product service.
     *
     * @param productService the new product service
     */
//    public void setProductService(IProductService productService) {
//        this.productService = productService;
//    }

    /**
     * The business lock service.
     *
     */
    public IBusinessLockService businessLockService;

    /**
     * Sets the business lock service.
     *
     * @param businessLockService the new business lock service
     */
    public void setBusinessLockService(IBusinessLockService businessLockService) {
        this.businessLockService = businessLockService;
    }

    private ISharedDao sharedDao;

    /**

     * Sets the departure sharedDao.
     *
     * @param sharedDao the new shared Dao
     */
    public final void setSharedDao(ISharedDao sharedDao) {
        this.sharedDao = sharedDao;
    }

    /**
     * pda包服务，用于获取包内流水号
     */
    private IPDAExpressPackageDao pdaExpressPackageDao;

    public final void setPdaExpressPackageDao(
            IPDAExpressPackageDao pdaExpressPackageDao) {
        this.pdaExpressPackageDao = pdaExpressPackageDao;
    }


    /**
     * 运单状态数据持久层
     */
    private IActualFreightService actualFreightService;

    public void setActualFreightService(IActualFreightService actualFreightService) {
        this.actualFreightService = actualFreightService;
    }

    /**
     * 判断是否为7天返货类型
     */
    private IReturnGoodsRequestEntityService returnGoodsRequestEntityService;

    public void setReturnGoodsRequestEntityService(
            IReturnGoodsRequestEntityService returnGoodsRequestEntityService) {
        this.returnGoodsRequestEntityService = returnGoodsRequestEntityService;
    }

    /**
     * 查询已分配卸车任务：未开始、进行中.
     *
     * @param loaderCode the loader code
     *
     * @param loadOrgCode the load org code
     *
     * @return the list
     *
     * @author dp-duyi
     *
     * @date 2012-11-28 上午10:16:57
     *
     * @see com.deppon.foss.module.transfer.pda.api.server.service.IPDAUnloadTaskService#pdaQueryAssignedUnloadTask(java.lang.String, java.lang.String)
     */
    @Override
    @Transactional
    public List<PDAAssignUnloadTaskEntity> pdaQueryAssignedUnloadTask(
            String loaderCode, String loadOrgCode) {
        Map<String, String> condition = new HashMap<String, String>();
        condition.put("loaderCode", loaderCode);
        condition.put("loadOrgCode", loadOrgCode);
        List<PDAAssignUnloadBillEntity> pdaAssignUnloadBills = pdaUnloadTaskDao.pdaQueryAssignedUnloadTask(condition);
        List<PDAAssignUnloadTaskEntity> pdaAssignUnloadTasks = new ArrayList<PDAAssignUnloadTaskEntity>();
        PDAAssignUnloadTaskEntity task;
        List<PDAAssignUnloadBillEntity> bills;
        boolean beExist = false;
        if (CollectionUtils.isNotEmpty(pdaAssignUnloadBills)) {
            for (PDAAssignUnloadBillEntity billTemp : pdaAssignUnloadBills) {
                beExist = false;
                for (PDAAssignUnloadTaskEntity taskTemp : pdaAssignUnloadTasks) {
                    if (billTemp.getVehicleNo().equals(taskTemp.getVehicleNo())) {
                        taskTemp.getBills().add(billTemp);
                        beExist = true;
                    }
                }
                if (!beExist) {
                    bills = new ArrayList<PDAAssignUnloadBillEntity>();
                    bills.add(billTemp);
                    task = new PDAAssignUnloadTaskEntity();
                    task.setVehicleNo(billTemp.getVehicleNo());
                    task.setBills(bills);
                    pdaAssignUnloadTasks.add(task);
                }
            }
        }
        return pdaAssignUnloadTasks;
    }

    /**
     * 建立卸车任务.
     *
     * @param unloadTask the unload task
     *
     * @return the list
     *
     * @author dp-duyi
     *
     * @date 2012-12-17 下午3:36:58
     *
     * @see com.deppon.foss.module.transfer.pda.api.server.service.IPDAUnloadTaskService#createLoadTask(com.deppon.foss.module.transfer.pda.api.shared.dto.UnloadTaskDto)
     */
    @Override
    @Transactional
    public UnloadTaskResultDto createLoadTask(UnloadTaskDto unloadTask) {

        /**快递新建卸车任务dto*/
        ExpressUnloadTaskAddnewDto expressUnloadTaskAddnewDto = new ExpressUnloadTaskAddnewDto();

        /**设置 车牌号*/
        expressUnloadTaskAddnewDto.setVehicleNo(unloadTask.getVehicleNo());

        /**设置 月台号*/
        expressUnloadTaskAddnewDto.setPlatformNo(unloadTask.getPlatformNo());

        /**设置 建立任务部门编号*/
        expressUnloadTaskAddnewDto.setUnloadOrgCode(unloadTask.getCreateOrgCode());

        /**设置 创建人部门编号*/
        expressUnloadTaskAddnewDto.setCreateOrgCode(unloadTask.getCreateOrgCode());

        /**设置 创建人工号*/
        expressUnloadTaskAddnewDto.setCreateNo(unloadTask.getOperatorCode());

        /**设置 创建时间*/
        expressUnloadTaskAddnewDto.setCreateTime(new Date());

        /**设置 操作设备*/
        expressUnloadTaskAddnewDto.setOperationDevice("PC");

        /**设置 操作设备编码*/
        expressUnloadTaskAddnewDto.setOperationDeviceCode(unloadTask.getDeviceNo());

        PDATaskEntity pdaEntity = new PDATaskEntity();
        // 卸车任务单据明细DTO
        List<UnloadBillDetailDto> unloadBills;
        UnloadTaskEntity unloadTaskEntity;
        String unloadTaskNo;
        LoaderParticipationEntity creator;
        List<LoaderDto> loaderCodes = new ArrayList<LoaderDto>();
        String unloadType = null;
        //如果任务编号不为空，则下拉卸车任务，更新PDA任务表
        if (unloadTask != null && StringUtils.isNotBlank(unloadTask.getTaskNo())) {
            //卸车任务编号
            unloadTaskNo = unloadTask.getTaskNo();
            /**设置 卸车任务编号*/
            expressUnloadTaskAddnewDto.setUnloadTaskNo(unloadTaskNo);

            // 根据卸车任务编号获取卸车任务 from tfr.t_opt_unload_task
            unloadTaskEntity = pdaUnloadTaskDao.queryUnloadTaskByTaskNo(unloadTaskNo);
            //若卸车任务状态为卸车中，则可以下拉卸车任务
            //卸车中   卸车任务  t.task_state 卸车任务状态 三种类型：UNLOADING FINISHED  CANCELED
            if (unloadTaskEntity != null && UnloadConstants.UNLOAD_TASK_STATE_UNLOADING.equals(unloadTaskEntity.getTaskState())) {

                /**设置 车辆类型*/
                expressUnloadTaskAddnewDto.setUnloadType(unloadTaskEntity.getUnloadType());

                /**设置 卸车方式*/
                expressUnloadTaskAddnewDto.setUnloadWay(unloadTaskEntity.getUnloadWay());

                /**快递 理货员list*/
                List<ExpressLoaderParticipateDto> loaderParticipateList = new ArrayList<ExpressLoaderParticipateDto>();

                // 装卸车人员参与情况
                LoaderParticipationEntity loaderQC = new LoaderParticipationEntity();
                loaderQC.setTaskId(unloadTaskEntity.getId());
                // 根据任务ID查询理货员 --装卸车人员参与情况 from tfr.t_opt_loader_participation p
                List<LoaderParticipationEntity> loaders = pdaUnloadTaskDao.queryLoaderByTaskId(loaderQC);
                creator = new LoaderParticipationEntity();
                if (CollectionUtils.isNotEmpty(loaders)) {
                    /**快递 理货员dto*/
                    ExpressLoaderParticipateDto expressLoaderParticipateDto = null;
                    LoaderDto loaderCode;
                    //  装卸车人员参与情况-->>理货员
                    for (LoaderParticipationEntity loader : loaders) {

                        expressLoaderParticipateDto = new ExpressLoaderParticipateDto();
                        loaderCode = new LoaderDto();
                        // 理货员工号
                        loaderCode.setLoaderCode(loader.getLoaderCode());
                        /**设置  卸车任务编号*/
                        expressLoaderParticipateDto.setTaskNo(unloadTaskNo);
                        /**设置  理货员工号*/
                        expressLoaderParticipateDto.setLoaderCode(loader.getLoaderCode());
                        /**设置  理货员姓名*/
                        expressLoaderParticipateDto.setLoaderName(loader.getLoaderName());
                        /**设置  加入时间*/
                        expressLoaderParticipateDto.setJoinTime(loader.getJoinTime());

                        // 标识
                        loaderCode.setFlag(loader.getFlag());
                        loaderCodes.add(loaderCode);
                        // 是否建立任务理货员
                        if (FossConstants.YES.equals(loader.getBeCreator())) {
                            creator = loader;
                            /**设置  是否为建立任务理货员*/
                            expressLoaderParticipateDto.setIscreator(FossConstants.YES);
                        } else {
                            expressLoaderParticipateDto.setIscreator(FossConstants.NO);
                        }
                        loaderParticipateList.add(expressLoaderParticipateDto);
                    }
                    /**设置创建 卸车人员参与list*/
                    expressUnloadTaskAddnewDto.setCreateLoaderParticipateList(loaderParticipateList);
                }

                // 200968 zwd 卸车任务单据明细DTO  根据卸车任务ID来查询卸车任务单据明细 from tfr.t_opt_unload_bill_detail
                unloadBills = pdaUnloadTaskDao.queryUnloadBillsByTaskId(unloadTaskEntity.getId());

                List<ExpressUnloadTaskDetailDto> expressUnloadTaskDetailList = new ArrayList<ExpressUnloadTaskDetailDto>();
                /**快递卸车任务明细Dto*/
                ExpressUnloadTaskDetailDto expressUnloadTaskDetailDto = null;
                for (UnloadBillDetailDto unloadBillDetailDto : unloadBills) {
                    if (UnloadCommonUtils.isExpressHandOver(unloadBillDetailDto.getBillNo())) {
                        expressUnloadTaskDetailDto = new ExpressUnloadTaskDetailDto();
                        /**设置  卸车任务编号*/
                        expressUnloadTaskDetailDto.setUnloadTaskNo(unloadTaskNo);
                        /**设置 单据编号*/
                        expressUnloadTaskDetailDto.setHandoverBillNo(unloadBillDetailDto.getBillNo());
                        /**设置 件数*/
                        expressUnloadTaskDetailDto.setGoodsQty(unloadBillDetailDto.getPieces().longValue());
                        /**设置 体积*/
                        expressUnloadTaskDetailDto.setVolume(unloadBillDetailDto.getVolume());
                        /**设置 重量*/
                        expressUnloadTaskDetailDto.setWeight(unloadBillDetailDto.getWeight());

                        expressUnloadTaskDetailList.add(expressUnloadTaskDetailDto);
                    }

                }
                /**设置  卸车任务明细list*/
                expressUnloadTaskAddnewDto.setCreateUnloadTaskDetailList(expressUnloadTaskDetailList);
                // pda参与装卸车情况
                pdaEntity.setBeCreator(FossConstants.NO);
                unloadType = unloadTaskEntity.getUnloadType();
            } else {
                throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_INVALID_TASK_MESSAGECODE);
            }
            //如果卸车任务编号为空
        } else {
            if (CollectionUtils.isNotEmpty(unloadTask.getBillNos())) {
                StringBuilder sb = new StringBuilder();
                sb.append("建立卸车任务开始，车牌号:");
                sb.append(unloadTask.getVehicleNo());
                sb.append(" 卸车单据编号:");
                for (String d : unloadTask.getBillNos()) {
                    sb.append(d + "/");
                }
                logger.error(sb.toString());
            }

            //封签是否检查;
            /*SealEntity sealQC = new SealEntity();
			sealQC.setVehicleNo(unloadTask.getVehicleNo());
			sealQC.setSealState(SealConstant.SEAL_STATE_UNCHECK);
			//不为已删除的
			sealQC.setSealType(SealConstant.SEAL_TYPE_INVALID);
			List<SealEntity> unCheckedSeal = pdaUnloadTaskDao.querySeal(sealQC);*/

            //查询卸车单据 zwd 200968 快递集中卸货
            unloadBills = pdaUnloadTaskDao.queryUnloadBillsByBillNo(unloadTask.getBillNos());
            /**
             * 卸车单据中只处理 商务专递或非商务专递 类型的卸车任务，多种类型混合的卸车任务中断中断操作，不进行处理
             * 2015-9-19 16:48:26
             * 263072
             */
            int expressHandOverBillCount = 0;
            if (CollectionUtils.isNotEmpty(unloadBills) && unloadBills.size() > 0) {
                for (UnloadBillDetailDto unloadBill : unloadBills) {
                    if (StringUtil.equals(UnloadConstants.UNLOAD_TASK_TYPE_BUSINESS, unloadBill.getBusinessType())) {
                        expressHandOverBillCount++;
                    }
                }
                if (expressHandOverBillCount > 0 && expressHandOverBillCount != unloadBills.size()) {
                    throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_INVALID_UNLOADBILL);
                }
            }

            //当为非商务专递的卸车单据时才进行封签校验处理
            if (expressHandOverBillCount == 0) {
                //获取封签
                List<SealEntity> unCheckedSeal = pdaUnloadTaskDao.querySealByBillNo(unloadTask.getBillNos(),
                        unloadTask.getVehicleNo());
                if (CollectionUtils.isNotEmpty(unCheckedSeal)) {
                    if (unCheckedSeal.get(0).getSealState().equals(SealConstant.SEAL_STATE_UNCHECK)) {
                        //如果该车辆存在尚未检查封签，则不允许建立卸车任务
                        throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_SEAL_UNCHEK_MESSAGECODE);
                    }
                }
            }

            //是否建立任务PDA为是
            pdaEntity.setBeCreator(FossConstants.YES);


            //单据不为空，且查询出的单据数量为查询条件中的单据数量
            if (CollectionUtils.isNotEmpty(unloadBills) && unloadBills.size() == unloadTask.getBillNos().size()) {
                List<ExpressUnloadTaskDetailDto> expressUnloadTaskDetailList = new ArrayList<ExpressUnloadTaskDetailDto>();
                /**快递卸车任务明细Dto*/
                ExpressUnloadTaskDetailDto expressUnloadTaskDetailDto = null;
                UnloadBillDetailEntity unloadBillEntity;
                List<UnloadBillDetailEntity> unloadBillEntitys = new ArrayList<UnloadBillDetailEntity>();
                String taskId = UUIDUtils.getUUID();
                double weightTotal = 0.0;
                double volumeTotal = 0.0;
                //List<AssignUnloadTaskEntity> assignedUnloadTasks = new ArrayList<AssignUnloadTaskEntity>();
                AssignUnloadTaskEntity assignedTask;
                for (UnloadBillDetailDto unloadBill : unloadBills) {
                    //到达单据状态为已分配
                    if (TaskTruckConstant.BILL_ASSIGN_STATE_ASSIGNED.equals(unloadBill.getBillState())) {
                        /**快递卸车明细dto*/
                        expressUnloadTaskDetailDto = new ExpressUnloadTaskDetailDto();
                        unloadBillEntity = new UnloadBillDetailEntity();
                        unloadBillEntity.setBillNo(unloadBill.getBillNo());
                        unloadBillEntity.setBillType(unloadBill.getBillType());
                        unloadBillEntity.setId(UUIDUtils.getUUID());
                        unloadBillEntity.setPieces(unloadBill.getPieces());
                        unloadBillEntity.setUnloadTaskId(taskId);
                        unloadBillEntity.setWaybillTotal(unloadBill.getWaybillTotal());
                        unloadBillEntity.setWeight(unloadBill.getWeight());
                        unloadBillEntity.setVolume(unloadBill.getVolume());
                        unloadBillEntity.setUnloadTaskId(taskId);
                        //配合BI，新增创建时间
                        unloadBillEntity.setCreateDate(new Date());
                        //配合BI，新增修改时间时间
                        unloadBillEntity.setModifyDate(new Date());
                        /** 判断是否为商务专递类型 ，若为商务专递，单据类型修改为 BUSINESS_AIR ***/
                        if (UnloadConstants.UNLOAD_TASK_TYPE_BUSINESS.equals(unloadBill.getBusinessType())) {
                            unloadBillEntity.setBillType(unloadBill.getBusinessType());
                        }

                        unloadBillEntitys.add(unloadBillEntity);

                        if (unloadBill.getWeight() == null) {
                            weightTotal = 0;
                        } else {
                            weightTotal = weightTotal + unloadBill.getWeight().doubleValue();
                        }
                        if (unloadBill.getVolume() == null) {
                            volumeTotal = 0;
                        } else {
                            volumeTotal = volumeTotal + unloadBill.getVolume().doubleValue();
                        }

                        unloadType = unloadBill.getBusinessType();
                        assignedTask = new AssignUnloadTaskEntity();
                        assignedTask.setBillNo(unloadBill.getBillNo());
                        assignedTask.setBeCanceled(FossConstants.NO);
                        assignedTask.setState(UnloadConstants.ASSIGN_UNLOAD_TASK_STATE_PROCESSING);
                        assignedTask.setUnloadStartTime(new Date());

                        int updateQty = pdaUnloadTaskDao.updateAssignUnloadTaskStateByState(assignedTask, UnloadConstants.ASSIGN_UNLOAD_TASK_STATE_UNSTART);

                        if (updateQty != 1) {
                            throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_INVALID_UNLAOD_BILL_MESSAGECODE);
                        }

                        if (UnloadCommonUtils.isExpressHandOver(unloadBill.getBillNo())) {
                            /**设置 单据编号*/
                            expressUnloadTaskDetailDto.setHandoverBillNo(unloadBill.getBillNo());
                            if (null == unloadBill.getPieces()) {
                                expressUnloadTaskDetailDto.setGoodsQty(new Long(0));
                            } else {
                                /**设置 件数*/
                                expressUnloadTaskDetailDto.setGoodsQty(unloadBill.getPieces().longValue());
                            }
                            /**设置 体积*/
                            expressUnloadTaskDetailDto.setVolume(unloadBill.getVolume());
                            /**设置 重量*/
                            expressUnloadTaskDetailDto.setWeight(unloadBill.getWeight());

                            expressUnloadTaskDetailList.add(expressUnloadTaskDetailDto);
                        }

                        //assignedUnloadTasks.add(assignedTask);
                    } else {//若到达单据状态为未分配、卸车中或卸车完成，则不能建立卸车任务
                        throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_INVALID_UNLAOD_BILL_MESSAGECODE);
                    }
                }

                /**设置  卸车任务明细list*/
                expressUnloadTaskAddnewDto.setCreateUnloadTaskDetailList(expressUnloadTaskDetailList);

                //tfr.T_OPT_UNLOAD_BILL_DETAIL
                pdaUnloadTaskDao.insertUnloadBillDetails(unloadBillEntitys);
                unloadTaskEntity = new UnloadTaskEntity();
                /**判断货物是否扫描入库,为Y时为扫描入库，为N时为提交时job异步入库，ISSUE-3317要求全部扫描入库,所以建立任务时全部保存为扫描入库*/
                unloadTaskEntity.setBeScanInstock(FossConstants.YES);
                //调用综合接口查询出发部门是否为外场
                OrgAdministrativeInfoEntity unloadOrg = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(unloadTask.getCreateOrgCode());
                if (unloadOrg != null) {
                    if (FossConstants.YES.equals(unloadOrg.getSalesDepartment())) {
                        SaleDepartmentEntity saleDetp = saleDepartmentService.querySaleDepartmentByCode(unloadOrg.getCode());
                        if (saleDetp != null && FossConstants.YES.equals(saleDetp.getStation())) {
                            unloadOrg = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(saleDetp.getTransferCenter());
                            unloadTaskEntity.setBeScanInstock(FossConstants.YES);
                        }
                    } else {
                        unloadOrg = pdaCommonService.getCurrentOutfieldCode(unloadTask.getCreateOrgCode());
                        unloadTaskEntity.setBeScanInstock(FossConstants.YES);
                    }
                }
                if (unloadOrg == null) {
                    throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_INVALID_ORG_MESSAGECODE);
                }

                unloadTaskEntity.setUnloadOrgCode(unloadOrg.getCode());
                unloadTaskEntity.setUnloadOrgName(unloadOrg.getName());
                //unloadTaskNo = tfrCommonService.generateSerialNumberRequireNew(TfrSerialNumberRuleEnum.XC,unloadTaskEntity.getUnloadOrgCode());
                unloadTaskNo = billNumService.generateUnLoadTaskNo(unloadTaskEntity.getUnloadOrgCode());

                /**设置 卸车任务编号*/
                expressUnloadTaskAddnewDto.setUnloadTaskNo(unloadTaskNo);

                /**设置 车辆类型*/
                expressUnloadTaskAddnewDto.setUnloadType(unloadType);

                /**设置 卸车方式*/
                expressUnloadTaskAddnewDto.setUnloadWay(UnloadConstants.UNLOAD_TASK_WAY_PDA);


                logger.error("建立卸车任务任务号:" + unloadTaskNo);
                unloadTaskEntity.setId(taskId);
                unloadTaskEntity.setUnloadTaskNo(unloadTaskNo);
                unloadTaskEntity.setUnloadStartTime(new Date());
                unloadTaskEntity.setTaskState(UnloadConstants.UNLOAD_TASK_STATE_UNLOADING);
                unloadTaskEntity.setUnloadWay(UnloadConstants.UNLOAD_TASK_WAY_PDA);
                unloadTaskEntity.setVehicleNo(unloadTask.getVehicleNo());
                unloadTaskEntity.setUnloadType(unloadType);
                unloadTaskEntity.setUnloadStartTime(new Date());
                Date planFinishTime = unloadTaskService.calculatePlanFinishTime(unloadTask.getCreateOrgCode(), weightTotal, volumeTotal, new Date());
                if (planFinishTime == null && !unloadTask.getCreateOrgCode().equals(unloadTaskEntity.getUnloadOrgCode())) {
                    planFinishTime = unloadTaskService.calculatePlanFinishTime(unloadTaskEntity.getUnloadOrgCode(), weightTotal, volumeTotal, new Date());
                }
                unloadTaskEntity.setPlanCompleteTime(planFinishTime);
                //调用综合接口查询月台虚拟编码
                if (StringUtils.isNotBlank(unloadTask.getPlatformNo())) {
                    if (unloadOrg != null) {
                        if (!FossConstants.YES.equals(unloadOrg.getSalesDepartment())) {
                            if (StringUtils.isNotBlank(unloadOrg.getCode())) {
                                PlatformEntity plateform = platformService.queryPlatformByCode(unloadOrg.getCode(), unloadTask.getPlatformNo());
                                if (plateform != null) {
                                    // 调用月台接口修改月台状态
                                    if (planFinishTime == null) {
                                        planFinishTime = new Date();
                                    }
									/*
									 * 占用月台
									 */
                                    //调用月台服务，占用月台
                                    //platformDispatchService.updatePlatformStatusForUsing(distributeEntity);
                                    //045923-shiwei 2014-09-24修改月台占用类型为实际使用
                                    try {
                                        pdaCommonService.updatePlatformStateByCreateTask(plateform.getVirtualCode(), unloadTask.getVehicleNo(), unloadTaskNo,
                                                unloadTaskEntity.getUnloadOrgCode(), PlatformDispatchConstants.PLATFORMDISPATCH_TYPE_ACTUALUSE,
                                                PlatformDispatchConstants.PLATFORMDISPATCH_SCHEDULESOURCE_UNLOAD, new Date(), planFinishTime);
                                    } catch (Exception e) {
                                        //sonar-352203
                                        logger.info("PDAUnloadTaskService.createLoadTask 报错:" + StringUtils.substring(e.toString(), 0, ConstantsNumberSonar.SONAR_NUMBER_100));
                                    }
                                    unloadTaskEntity.setPlatformId(plateform.getVirtualCode());
                                } else {
                                    throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_INVALID_PLATEFORM);
                                }
                            }
                        }
                    }
                    unloadTaskEntity.setPlatformNo(unloadTask.getPlatformNo());
                }
				/*if(this.isTransferCenterOrStation(unloadOrg)){
					unloadTaskEntity.setBeScanInstock(FossConstants.YES);
				}else{
					unloadTaskEntity.setBeScanInstock(FossConstants.NO);
				}*/
                unloadTaskEntity.setBeException(FossConstants.NO);
                //插入卸车任务
                unloadTaskDao.addUnloadTaskBasicInfo(unloadTaskEntity);

                /**快递 理货员list*/
                List<ExpressLoaderParticipateDto> loaderParticipateList = new ArrayList<ExpressLoaderParticipateDto>();
                /**快递 理货员dto*/
                ExpressLoaderParticipateDto expressLoaderParticipateDto = null;

                //理货员
                LoaderParticipationEntity loader;
                PorterEntity porter;
                List<LoaderParticipationEntity> loaders = new ArrayList<LoaderParticipationEntity>();
                boolean findCreate = false;
                for (LoaderDto loaderCode : unloadTask.getLoaderCodes()) {
                    loader = new LoaderParticipationEntity();
                    expressLoaderParticipateDto = new ExpressLoaderParticipateDto();
                    /**设置  卸车任务编号*/
                    expressLoaderParticipateDto.setTaskNo(unloadTaskNo);
                    /**设置  理货员工号*/
                    expressLoaderParticipateDto.setLoaderCode(loaderCode.getLoaderCode());
                    /**设置  加入时间*/
                    expressLoaderParticipateDto.setJoinTime(new Date());
                    loader.setJoinTime(new Date());
                    loader.setId(UUIDUtils.getUUID());
                    loader.setTaskId(taskId);
                    loader.setFlag(loaderCode.getFlag());
                    if (UnloadConstants.UNLOAD_TASK_TYPE_DELIVER.equals(unloadType)) {
                        loader.setTaskType(LoadConstants.LOADER_PARTICIPATION_DELIVER_UNLOAD);
                    } else {
                        loader.setTaskType(LoadConstants.LOADER_PARTICIPATION_TRANSFER_UNLOAD);
                    }
                    loader.setLoaderCode(loaderCode.getLoaderCode());
                    loader.setBeCreator(FossConstants.NO);
                    /**设置  是否为建立任务理货员*/
                    expressLoaderParticipateDto.setIscreator(FossConstants.NO);
                    if (loaderCode.getLoaderCode().equals(unloadTask.getOperatorCode())) {
                        if (!findCreate) {
                            loader.setBeCreator(FossConstants.YES);
                            /**设置  是否为建立任务理货员*/
                            expressLoaderParticipateDto.setIscreator(FossConstants.YES);
                            findCreate = true;
                        }
                    }
                    porter = porterService.queryPorterByEmpCode(loaderCode.getLoaderCode());
                    //调用综合接口查询理货员名称、理货员所属装卸车队
                    if (porter != null) {
                        loader.setLoaderName(porter.getEmpName());
                        /**设置  理货员姓名*/
                        expressLoaderParticipateDto.setLoaderName(porter.getEmpName());

                        if (StringUtils.isNotBlank(porter.getParentOrgCode())) {
                            loader.setLoadOrgCode(porter.getParentOrgCode());
                            LoadAndUnloadSquadEntity team = loadAndUnloadSquadService.queryLoadAndUnloadSquadByCode(porter.getParentOrgCode());
                            if (team != null) {
                                loader.setLoadOrgName(team.getName());
                            } else {
                                //非法理货员
                                //throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_INVALID_LOADER_MESSAGECODE);
                            }
                        }
                    } else {
                        EmployeeEntity emp = employeeService.queryEmployeeByEmpCode(loaderCode.getLoaderCode());
                        if (emp != null) {
                            loader.setLoaderName(emp.getEmpName());
                        } else {
                            throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_INVALID_LOADER_MESSAGECODE);
                        }
                        //非法理货员
                        //throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_INVALID_LOADER_MESSAGECODE);
                    }
                    loaders.add(loader);
                    LoaderDto loaderItem = new LoaderDto();
                    loaderItem.setLoaderCode(loader.getLoaderCode());
                    loaderItem.setFlag(loader.getFlag());
                    loaderCodes.add(loaderItem);
                    /**理货员列表*/
                    loaderParticipateList.add(expressLoaderParticipateDto);
                }
                /**设置创建 卸车人员参与list*/
                expressUnloadTaskAddnewDto.setCreateLoaderParticipateList(loaderParticipateList);
                creator = new LoaderParticipationEntity();
                creator.setLoaderCode(unloadTask.getOperatorCode());

                EmployeeEntity emp = employeeService.queryEmployeeByEmpCode(unloadTask.getOperatorCode());
                //调用综合接口查询理货员名称、理货员所属装卸车队
                if (emp != null) {
                    //职员姓名
                    creator.setLoaderName(emp.getEmpName());
                }
                if (CollectionUtils.isNotEmpty(loaders)) {
                    //插入理货员tfr.t_opt_loader_participation
                    pdaLoadDao.insertTransferLoaderParticipation(loaders);
                }
                //pdaUnloadTaskDao.updateAssignUnloadTaskState(assignedUnloadTasks);
                //修改到达单据状态为卸车中
                unloadTaskService.updateArriveBillState(unloadBills, TaskTruckConstant.BILL_ASSIGN_STATE_UNLOADING);
                //PDA建立卸车任务时，往接送货的临时表中插值，确定发短信通知
                AutoTaskDTO dto = new AutoTaskDTO();
                dto.setId(UUIDUtils.getUUID());
                dto.setTaskDetailId(taskId);
                dto.setTaskDetailType(UnloadConstants.UNLOAD_FOR_PKP);
                dto.setStockOrgCode("N/A");
                sharedDao.insertTempForPKP(dto);
            } else {
                //单据为空，或查询出的单据数量不等于查询条件中的单据数量
                throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_INVALID_UNLAOD_BILL_MESSAGECODE);
            }

            boolean tfrSwitch4Ecs = configurationParamsService.queryTfrSwitch4Ecs();
            logger.error("同步建立卸车任务到悟空系统开关" + tfrSwitch4Ecs);

            //调用综合接口查询当前部门是否是营业部

            if (tfrSwitch4Ecs) {
                OrgAdministrativeInfoEntity org = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(unloadTaskEntity.getUnloadOrgCode());
                boolean isSalesDepartment = FossConstants.YES.equals(org.getSalesDepartment()) ? true : false;
                if (!isSalesDepartment) {
                    syncPDACreateUnloadTaskToWk(expressUnloadTaskAddnewDto);
                }
            }
        }
        //插入卸车PDA
        pdaEntity.setTaskNo(unloadTaskNo);
        pdaEntity.setDeviceNo(unloadTask.getDeviceNo());
        pdaEntity.setId(UUIDUtils.getUUID());
        pdaEntity.setJoinTime(new Date());
        // 200968 zwd 卸车任务类型：接送货卸车
        if (UnloadConstants.UNLOAD_TASK_TYPE_DELIVER.equals(unloadTaskEntity.getUnloadType())) {
            pdaEntity.setTaskType(LoadConstants.LOADER_PARTICIPATION_DELIVER_UNLOAD);
        } else {
            //  200968 zwd 卸车任务类型：中转卸车
            pdaEntity.setTaskType(LoadConstants.LOADER_PARTICIPATION_TRANSFER_UNLOAD);
        }
        // 200968 zwd 记录PDA装卸车任务tfr.t_opt_pda_task
        pdaLoadDao.insertPDATask(pdaEntity);
        UnloadTaskResultDto unloadTaskResultDto = new UnloadTaskResultDto();
        if (creator != null) {
            unloadTaskResultDto.setCreatorCode(creator.getLoaderCode());
            unloadTaskResultDto.setCreatorName(creator.getLoaderName());
        }
        unloadTaskResultDto.setLoaders(loaderCodes);
        unloadTaskResultDto.setTaskNo(unloadTaskNo);
        unloadTaskResultDto.setUnloadType(unloadType);
        /**
         * 创建 卸车发短信 代办任务
         */
//		派送发短信回滚
//		unloadTaskService.addJobTodo(unloadTaskEntity.getId(), creator.getLoaderName(),unloadTask.getCreateOrgCode());
        logger.error("建立卸车任务结束:" + unloadTaskNo);

        return unloadTaskResultDto;
    }

    /**
     * @description 同步PDA创建卸车任务到悟空系统
     * @param expressUnloadTaskAddnewDto
     * @return
     * @version 1.0
     * @author 328768-foss-gaojianfu
     * @update 2016年5月19日 上午9:56:31
     */
    private void syncPDACreateUnloadTaskToWk(ExpressUnloadTaskAddnewDto expressUnloadTaskAddnewDto) {
        // 获取objectMapper
        ObjectMapper objectMapper = JSONUtils.obtainObjectMapper();
        // 设置时间转换格式
        SimpleDateFormat dateFormat = new SimpleDateFormat(DateUtils.DATE_TIME_FORMAT);
        // 设置到objectMapper
        objectMapper.setDateFormat(dateFormat);

        String requestJsonStr = null;
        try {
            requestJsonStr = objectMapper.writeValueAsString(expressUnloadTaskAddnewDto);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        //调用同步数据接口
        FossToWKResponseEntity fossToWKResponseEntity = null;
        try {
            logger.error("Foss同步PDA创建卸车任务到悟空参数：" + requestJsonStr);
            fossToWKResponseEntity = fossToWkService.syncPDACreateUnloadTaskToWk(requestJsonStr);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new TfrBusinessException("Foss同步PDA创建卸车任务到悟空系统失败，错误信息：" + e.getMessage());
        }
        if (fossToWKResponseEntity == null) {
            logger.error("Foss同步PDA创建卸车任务到悟空系统失败!");
            throw new TfrBusinessException("Foss同步PDA创建卸车任务到悟空系统失败!");
        }
        if ("0".equals(fossToWKResponseEntity.getStatus())) {
            logger.error("Foss同步创建PDA卸车任务到悟空系统失败，错误信息：" + fossToWKResponseEntity.getExMsg());
            throw new TfrBusinessException("ECS-" + fossToWKResponseEntity.getExMsg());
        }

    }
    // 计算预计完成时间
	/*private Date calculatePlanFinishTime(String orgCode,double weightTotal,double volumeTotal,Date unloadStartTime){
		LoadAndUnloadEfficiencyTonEntity efficiencyEntity = loadAndUnloadEfficiencyTonService.queryLoadAndUnloadEfficiencyTonByOrgCode(orgCode);
		if(efficiencyEntity != null){
			if(efficiencyEntity!=null && StringUtils.isNotBlank(efficiencyEntity.getId())){
				long unloadVolumeMin = (long) (volumeTotal/efficiencyEntity.getUnloadVolumeStd().doubleValue());
				long unloadWeightMin = (long) ((weightTotal/1000)/efficiencyEntity.getUnloadWeightStd().doubleValue());
				long planUnloadTime = (unloadVolumeMin > unloadWeightMin ? unloadVolumeMin : unloadWeightMin);
				long planEndTime = unloadStartTime.getTime()+planUnloadTime*1000*60;
				return new Date(planEndTime);
			}
		}
		return null;
	}*/

    /**
     * 刷新卸车任务.
     * @param taskNo the task no
     * @return the list
     * @author dp-duyi
     * @date 2012-12-17 下午6:47:44
     * @see com.deppon.foss.module.transfer.pda.api.server.service.IPDAUnloadTaskService#refrushUnloadTaskDetail(java.lang.String)
     */
    @Override
    public List<UnloadGoodsDetailDto> refrushUnloadTaskDetail(String taskNo) {
        logger.error("刷新卸车任务开始:" + taskNo);
        List<UnloadBillDetailDto> unloadBills;
        List<UnloadBillDetailDto> unloadNoPackageBills = new ArrayList<UnloadBillDetailDto>();
        UnloadTaskEntity unloadTaskEntity;
        unloadTaskEntity = pdaUnloadTaskDao.queryUnloadTaskByTaskNo(taskNo);

        /**查询卸车部门是不是驻地营业部,并返回他的驻地外场*/
        // 建立任务部门编码
        OrgAdministrativeInfoEntity unloadOrg = orgAdministrativeInfoService
                .queryOrgAdministrativeInfoByCode(unloadTaskEntity.getUnloadOrgCode());
        if (unloadOrg != null) {
            //是否是营业部派送部
            if (FossConstants.YES.equals(unloadOrg.getSalesDepartment())) {
                SaleDepartmentEntity saleDetp = saleDepartmentService
                        .querySaleDepartmentByCode(unloadOrg.getCode());
                //是否驻地部门
                if (saleDetp != null
                        && FossConstants.YES.equals(saleDetp.getStation())) {
                    //驻地营业部所属外场
                    unloadOrg = orgAdministrativeInfoService
                            .queryOrgAdministrativeInfoByCode(saleDetp
                                    .getTransferCenter());
                }
            } else {
                //获取部门所属外场编码.
                unloadOrg = pdaCommonService.getCurrentOutfieldCode(unloadTaskEntity.getUnloadOrgCode());
            }
        } else {
            throw new TfrBusinessException(
                    TransferPDAExceptionCode.EXCEPTION_INVALID_ORG_MESSAGECODE);
        }
        //当前建立卸车任务部门的顶级外场
        String outfieldCode = unloadOrg.getCode();

        //若卸车任务状态为卸车中，则可以下拉卸车任务   卸车中UNLOADING
        if (unloadTaskEntity != null && UnloadConstants.UNLOAD_TASK_STATE_UNLOADING.equals(unloadTaskEntity.getTaskState())) {
            //卸车任务单据明细DTO
            unloadBills = pdaUnloadTaskDao.queryUnloadBillsByTaskId(unloadTaskEntity.getId());
            if (null != unloadBills) {
                //剔除包交接单
                for (UnloadBillDetailDto unloadBill : unloadBills) {
                    //汽运配载单
                    if (UnloadConstants.BILL_TYPE_VEHICLEASSEMBLE.equals(unloadBill.getBillType())) {
                        unloadNoPackageBills.add(unloadBill);
                    } else {
                        //配载单和交接单是以B开头的，前面的配载单数据已经添加
                        if (!unloadBill.getBillNo().startsWith("B") && !unloadBill.getBillNo().startsWith("KYB")) {
                            unloadNoPackageBills.add(unloadBill);
                        } else {
                            if (StringUtils.equals(unloadBill.getBillType(), UnloadConstants.BILL_TYPE_EWAYBILL) || StringUtils.equals(unloadBill.getBillType(), UnloadConstants.BILL_TYPE_ELECTWAYBILL)) {
                                unloadNoPackageBills.add(unloadBill);
                            }
                            //sonar-内容相同-352203,提到一个if中
							/*else if(StringUtils.equals(unloadBill.getBillType(), UnloadConstants.BILL_TYPE_ELECTWAYBILL)){
								unloadNoPackageBills.add(unloadBill);
							}*/
                        }
                    }

                }
            }
            LoaderParticipationEntity loaderQC = new LoaderParticipationEntity();
            loaderQC.setTaskId(unloadTaskEntity.getId());
            loaderQC.setBeCreator(FossConstants.YES);
            //根据任务ID查询理货员
            List<LoaderParticipationEntity> loaders = pdaUnloadTaskDao.queryLoaderByTaskId(loaderQC);
            LoaderParticipationEntity creator = new LoaderParticipationEntity();
            if (CollectionUtils.isNotEmpty(loaders)) {
                creator = loaders.get(0);
            }
            // zwd 200968 新加快递集中卸货
            List<UnloadGoodsDetailDto> result = this.queryUnloadTaskDetail(unloadNoPackageBills, unloadTaskEntity.getUnloadTaskNo(), creator, null, null, null, unloadTaskEntity.getId());
            StringBuilder s = new StringBuilder();
            s.append("刷新卸车任务");
            if (CollectionUtils.isNotEmpty(result)) {
                for (UnloadGoodsDetailDto d : result) {
                    s.append("任务号：" + d.getTaskNo());
                    s.append("运单号:" + d.getWayBillNo());
                    //s.append("更改状态:"+d.getModifyState());
                    s.append("贵重物品:" + d.getIsValue());
					/*//转寄退回件
					ReturnGoodsRequestEntity returnGoodsRequestEntity =
							returnGoodsRequestEntityService.queryReturnGoodsEntityByWayBillNo(d.getWayBillNo());
					//未上报工单:isHandle为null,上报工单未处理:isHandle为N,上报工单已处理:isHandle为Y,如果为N,不允许提交
					String isHandle = null;
					if(returnGoodsRequestEntity!=null){
						isHandle = returnGoodsRequestEntity.getIsHandle();
					}

					if("N".equalsIgnoreCase(isHandle)){
						isHandle = "Y";
					}else{
						isHandle = "N";
					}
					s.append("是否上报工单且未处理:"+isHandle);
					d.setIsHandle(isHandle);*/

                    if (CollectionUtils.isNotEmpty(d.getSerialNos())) {
                        for (PDAGoodsSerialNoDto ss : d.getSerialNos()) {
                            s.append("流水号:");
                            s.append(ss.getSerialNo());
                            s.append("代办事项:" + ss.getIsToDoList());
                            s.append("打木架：" + ss.getIsUnPacking());
                        }
                    }
                    s.append("/&n");

                    /**
                     * 返回结果增加行政区域名称,result是以运单为单位的
                     */
                    //查询运单实体
                    WaybillEntity waybillInfo = waybillManagerService.queryWaybillBasicByNo(d.getWayBillNo());
                    //调用综合接口 ,传入参数 提货网点waybillInfo.getCustomerPickupOrgCode()
                    String pickupoutfild = null;
                    //查询提货网点的外场     提货网点CustomerPickupOrgCode
                    if (waybillInfo != null && waybillInfo.getCustomerPickupOrgCode() != null) {
                        //提供营业部Code 查询时汽运到达的驻地外场
                        pickupoutfild = saleDepartmentService.queryTransferCenterCodeByStationCode(waybillInfo.getCustomerPickupOrgCode());
                    }
                    /**
                     * 理货员登录PDA下拉卸车任务明细，当前理货员所属顶级外场为①；
                     * 货物开单时提货网点部门行政组织业务属性为驻地部门且可汽运到达，该驻地营业部所属外场为②；
                     * 当①=②时，则给出货物行政区域，若①≠②时，则无需给出货物行政区域。
                     * 在上述规则下，本外场到达派送库区的货物：其中自提的货物行政区域传输为“自提区”PDA在前端显示，送货的货物行政区显示为开单收货人地址的行政区域。
                     * 快递的货物行政区域显示为空。
                     */
                    //设置行政区域  是否快递
                    if (StringUtils.equals(pickupoutfild, outfieldCode) && waybillInfo != null && !StringUtils.equals(waybillInfo.getIsExpress(), "Y")) {
                        //设置运单提货区域
                        if (waybillInfo != null) {
                            // 判断是否自提      提货方式ReceiveMethod
                            if (StringUtils.equals(waybillInfo.getReceiveMethod(), "SELF_PICKUP")
                                    || StringUtils.equals(waybillInfo.getReceiveMethod(), "INNER_PICKUP")
                                    || StringUtils.equals(waybillInfo.getReceiveMethod(), "SELF_PICKUP_FREE_AIR")
                                    || StringUtils.equals(waybillInfo.getReceiveMethod(), "SELF_PICKUP_AIR")
                                    || StringUtils.equals(waybillInfo.getReceiveMethod(), "AIRPORT_PICKUP")) {
                                //行政区域名称
                                d.setAdminiRegions("自提区");
                            } else {
                                //收货区 ReceiveCustomerDistCode
                                if (StringUtils.isNotEmpty(waybillInfo.getReceiveCustomerDistCode())) {
                                    d.setAdminiRegions(administrativeRegionsService.queryAdministrativeRegionsNameByCode(waybillInfo.getReceiveCustomerDistCode()));
                                }
                            }
                        }
                    }


                }

                logger.error(s.toString());
                logger.error("刷新卸车任务结束:" + taskNo);
                return result;
            } else {
                logger.error("刷新卸车任务结束:" + taskNo);
                throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_NO_TASK_DETAIL_MESSAGECODE);
            }
        } else {
            logger.error("刷新卸车任务结束:" + taskNo);
            throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_INVALID_TASK_MESSAGECODE);
        }
    }
	/*//更新到达单据状态
	@Transactional
	private void updateArriveBillState(List<UnloadBillDetailDto> bills,String state){
		List<ArriveBillDto> arriveTransferBills = new ArrayList<ArriveBillDto>();
		List<ArriveBillDto> arrivePickUpBills = new ArrayList<ArriveBillDto>();
		ArriveBillDto arriveBill;
		String billNo;
		for(UnloadBillDetailDto unloadBill : bills){
			//到达单据
			arriveBill = new ArriveBillDto();
			arriveBill.setBillNo(unloadBill.getBillNo());
			arriveBill.setAssignState(state);
			if(UnloadConstants.BILL_TYPE_PICKUP.equals(unloadBill.getBillType())){
				arrivePickUpBills.add(arriveBill);
			}else{
				arriveTransferBills.add(arriveBill);
			}
		}
		//修改到达单据状态
		if(CollectionUtils.isNotEmpty(arriveTransferBills)){
			assignUnloadTaskDao.updateArriveTransferBillState(arriveTransferBills);
		}
		if(CollectionUtils.isNotEmpty(arrivePickUpBills)){
			assignUnloadTaskDao.updateArrivePickUpBillState(arrivePickUpBills);
		}
		if(TaskTruckConstant.BILL_ASSIGN_STATE_UNLOADED.equals(state)){
			// 修改任务车辆状态
			billNo = bills.get(0).getBillNo();
			TruckTaskHandOverDto truckTask = truckTaskDao.queryTruckTaskIdByHandOverBill(billNo);
			int unfinishUnloadBillQty = pdaUnloadTaskDao.queryUnfinishUnloadedValideBillQty(truckTask.getTruckTaskDettailId());
			if(unfinishUnloadBillQty == 0){
				TruckTaskDetailEntity truckTaskDetail = new TruckTaskDetailEntity();
				truckTaskDetail.setId(truckTask.getTruckTaskDettailId());
				truckTaskDetail.setState(TaskTruckConstant.TASK_TRUCK_STATE_UNLOADED);
				pdaUnloadTaskDao.updateTruckTaskDetailState(truckTaskDetail);
				int unfinishUnloadTruckTaskDetail = pdaUnloadTaskDao.queryUnfinishUnloadedTruckTaskDetailQty(truckTask.getTruckTaskId());
				if(unfinishUnloadTruckTaskDetail == 0){
					TruckTaskEntity truckTaskEntity = new TruckTaskEntity();
					truckTaskEntity.setId(truckTask.getTruckTaskId());
					truckTaskEntity.setState(TaskTruckConstant.TASK_TRUCK_STATE_UNLOADED);
					pdaUnloadTaskDao.updateTruckTaskState(truckTaskEntity);
				}
			}
		}
	}*/

    /**
     * 根据卸车单据 明细查询卸车明细
     *
     *
     * 1、原来是按照流水号返回给PDA
     *
     *
     * 2、后来变为按运单号返回给PDA
     *
     *
     * 3、未修改DAO在此封装了下，未来要是查询不出或有问题，可在此优化.
     *
     *
     *
     * @param bills the bills
     * @param taskNo the task no
     * @param loader the loader
     * @param loaders the loaders
     * @param valueGoodsAreaCode the value goods area code
     * @param packGoodsAreaCode the pack goods area code
     * @param taskId the task id
     * @return the list
     */
    private List<UnloadGoodsDetailDto> queryUnloadTaskDetail(List<UnloadBillDetailDto> bills, String taskNo, LoaderParticipationEntity loader, List<LoaderDto> loaders, String valueGoodsAreaCode, String packGoodsAreaCode, String taskId) {
        List<UnloadGoodsSerialDetailDto> unloadGoodsList = new ArrayList<UnloadGoodsSerialDetailDto>();
        List<UnloadBillDetailDto> handOvers = new ArrayList<UnloadBillDetailDto>();
        List<UnloadBillDetailDto> assembleBills = new ArrayList<UnloadBillDetailDto>();
        List<UnloadBillDetailDto> airBills = new ArrayList<UnloadBillDetailDto>();
        List<UnloadBillDetailDto> pickups = new ArrayList<UnloadBillDetailDto>();
        List<UnloadBillDetailDto> electWayBills = new ArrayList<UnloadBillDetailDto>();//322610
        /**zwd 200968 快递集中卸货*/
        List<UnloadBillDetailDto> ewaybills = new ArrayList<UnloadBillDetailDto>();
        // 卸车单据明细 单据类型 6种类型 : AIRBILL  VEHICLEASSEMBLE  HANDOVER  PICKUP  EWAYBILL ELOOKBILL
        for (UnloadBillDetailDto bill : bills) {
            if (UnloadConstants.BILL_TYPE_HANDOVER.equals(bill.getBillType())) {
                handOvers.add(bill);
            } else if (UnloadConstants.BILL_TYPE_VEHICLEASSEMBLE.equals(bill.getBillType())) {
                assembleBills.add(bill);
            } else if (UnloadConstants.UNLOAD_TASK_TYPE_BUSINESS.equals(bill.getBillType())) {
                airBills.add(bill);
            } else if (UnloadConstants.BILL_TYPE_PICKUP.equals(bill.getBillType())) {
                pickups.add(bill);
            } else if (UnloadConstants.BILL_TYPE_ELECTWAYBILL.equals(bill.getBillType())) {
                electWayBills.add(bill);
            } else {
                ewaybills.add(bill);
            }
        }
        List<UnloadGoodsSerialDetailDto> handOverGoodsDetail;
        List<UnloadGoodsSerialDetailDto> assembleGoodsDetail;
        List<UnloadGoodsSerialDetailDto> pickupGoodsDetail;
        List<UnloadGoodsSerialDetailDto> eLookGoodsDetail;
        List<UnloadGoodsSerialDetailDto> airGoodsDetail;
        /**zwd 200968 快递集中卸货*/
        List<UnloadGoodsSerialDetailDto> ewaybillGoodsDetail;
        if (CollectionUtils.isNotEmpty(handOvers)) {
            //查询卸车交接单
            handOverGoodsDetail = pdaUnloadTaskDao.queryHandOverUnloadDetail(handOvers, taskId);
            if (CollectionUtils.isNotEmpty(handOverGoodsDetail)) {
                unloadGoodsList.addAll(handOverGoodsDetail);
            }
        }
        if (CollectionUtils.isNotEmpty(assembleBills)) {
            //查询卸车配载单
            assembleGoodsDetail = pdaUnloadTaskDao.queryAssembleUnloadDetail(assembleBills, taskId);
            if (CollectionUtils.isNotEmpty(assembleGoodsDetail)) {
                unloadGoodsList.addAll(assembleGoodsDetail);
            }
        }
        if (CollectionUtils.isNotEmpty(pickups)) {
            //查询卸车接送货单据
            pickupGoodsDetail = pdaUnloadTaskDao.queryPickUpUnloadDetail(pickups, taskId);
            if (CollectionUtils.isNotEmpty(pickupGoodsDetail)) {
                unloadGoodsList.addAll(pickupGoodsDetail);
            }
        }
        if (CollectionUtils.isNotEmpty(electWayBills)) {
            //322610 查询电子面单单据
            eLookGoodsDetail = pdaUnloadTaskDao.queryELookGoodsDetail(electWayBills, taskId);
            if (CollectionUtils.isNotEmpty(eLookGoodsDetail)) {
                unloadGoodsList.addAll(eLookGoodsDetail);
            }
        }

		/*if(CollectionUtils.isNotEmpty(airBills)){
			//查询卸车航空正单单据
			airGoodsDetail = pdaUnloadTaskDao.queryAirUnloadDetail(airBills,taskId);
			if(CollectionUtils.isNotEmpty(airGoodsDetail)){
				unloadGoodsList.addAll(airGoodsDetail);
			}
		}*/
        /**
         * 弃用航空正单单据，改为查询航空交接单（商务专递项目）
         * linyuzhu 263072
         * 2015-9-2 14:54:26
         */
        if (CollectionUtils.isNotEmpty(airBills)) {
            //查询航空交接单
            airGoodsDetail = pdaUnloadTaskDao.queryAirHandoverUnloadDetail(airBills, taskId);
            if (CollectionUtils.isNotEmpty(airGoodsDetail)) {
                unloadGoodsList.addAll(airGoodsDetail);
            }
        }

        //zwd 200968  查询快递集中卸车单据
        if (CollectionUtils.isNotEmpty(ewaybills)) {
            ewaybillGoodsDetail = pdaUnloadTaskDao.queryEwaybillUnloadDetail(ewaybills, taskId);
            if (CollectionUtils.isNotEmpty(ewaybillGoodsDetail)) {
                unloadGoodsList.addAll(ewaybillGoodsDetail);
            }
        }

        //查询卸车多货货物2013-04-22添加
        List<UnloadGoodsSerialDetailDto> unloadMoreGoods = pdaUnloadTaskDao.queryUnloadMoreGoods(taskNo);
        if (CollectionUtils.isNotEmpty(unloadMoreGoods)) {
            unloadGoodsList.addAll(unloadMoreGoods);
        }
        //返回给PDA卸车任务列表Dto
        List<UnloadGoodsDetailDto> details = new ArrayList<UnloadGoodsDetailDto>();
        UnloadGoodsDetailDto detailTemp;
        List<PDAGoodsSerialNoDto> serials;
        PDAGoodsSerialNoDto serial;
        boolean beExist = false;
        for (UnloadGoodsSerialDetailDto goods : unloadGoodsList) {
            beExist = false;
            goods.setTaskNo(taskNo);
            //理货员code
            if (StringUtils.isNotBlank(loader.getLoaderCode())) {
                goods.setCreatorCode(loader.getLoaderCode());
            }
            //理货员name
            if (StringUtils.isNotBlank(loader.getLoaderName())) {
                goods.setCreatorName(loader.getLoaderName());
            }
            for (UnloadGoodsDetailDto detail : details) {
                if (detail.getWayBillNo().equals(goods.getWayBillNo()) && detail.getBillNo().equals(goods.getBillNo())) {
                    beExist = true;
                    if (StringUtils.isNotBlank(goods.getSerialNo())) {
                        boolean beExistSerial = false;
                        if (CollectionUtils.isNotEmpty(detail.getSerialNos())) {
                            for (PDAGoodsSerialNoDto s : detail.getSerialNos()) {
                                if (s.getSerialNo().equals(goods.getSerialNo())) {
                                    beExistSerial = true;
                                }
                            }
                        }
                        if (!beExistSerial) {
                            serial = new PDAGoodsSerialNoDto();
                            serial.setIsUnPacking(goods.getIsUnPacking());
                            serial.setIsToDoList(goods.getIsToDoList());
                            serial.setSerialNo(goods.getSerialNo());
                            detail.getSerialNos().add(serial);
                            if (FossConstants.YES.equals(goods.getIsUnPacking())) {
                                if (StringUtils.isNotBlank(detail.getNotes())) {
                                    if (!detail.getNotes().contains("代打包装")) {
                                        detail.setNotes(detail.getNotes() + " 代打包装");
                                    }
                                } else {
                                    detail.setNotes("代打包装");
                                }

                            }
                            if (FossConstants.YES.equals(goods.getIsToDoList())) {
                                if (StringUtils.isNotBlank(detail.getNotes())) {
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
            }
            if (!beExist) {
                detailTemp = new UnloadGoodsDetailDto();
                serials = new ArrayList<PDAGoodsSerialNoDto>();
                if (StringUtils.isNotBlank(goods.getSerialNo())) {
                    serial = new PDAGoodsSerialNoDto();
                    //是否未打包装
                    serial.setIsUnPacking(goods.getIsUnPacking());
                    serial.setSerialNo(goods.getSerialNo());
                    serial.setIsToDoList(goods.getIsToDoList());
                    serials.add(serial);
                }
                detailTemp.setSerialNos(serials);
                detailTemp.setBeContraband(goods.getBeContraband());
                detailTemp.setBePriorityGoods(goods.getBePriorityGoods());
                detailTemp.setBillNo(goods.getBillNo());
                //detailTemp.setCreatorCode(goods.getCreatorCode());
                //detailTemp.setCreatorName(goods.getCreatorName());
                detailTemp.setDestOrgCode(goods.getDestOrgCode());
                detailTemp.setGoodsName(goods.getGoodsName());
                //detailTemp.setModifyState(goods.getModifyState());
                detailTemp.setIsValue(goods.getIsValue());
                //if(CollectionUtils.isNotEmpty(loaders)){
                //	detailTemp.setLoaders(loaders);
                //}
                //detailTemp.setValueGoodsAreaCode(valueGoodsAreaCode);
                //detailTemp.setPackGoodsAreaCode(packGoodsAreaCode);
                //detailTemp.setModifyContent(goods.getModifyContent());
                StringBuilder str = new StringBuilder();
                if (FossConstants.YES.equals(goods.getIsToDoList())) {
                    str.append("有更改 ");
                } else if (FossConstants.YES.equals(goods.getIsUnPacking())) {
                    str.append(" 代打包装 ");
                } else if (FossConstants.YES.equals(goods.getIsValue())) {
                    str.append(" 贵重物品");
                }
                //Sets the 备注
                detailTemp.setNotes(str.toString().trim());
                //出发部门
                detailTemp.setOrigOrgCode(goods.getOrigOrgCode());
                //Sets the 包装.
                detailTemp.setPacking(goods.getPacking());
                //Sets the 到达部门编码
                detailTemp.setReachOrgCode(goods.getReachOrgCode());
                //Sets the 到达部门名称.
                detailTemp.setReachOrgName(goods.getReachOrgName());
                //Sets the 收货部门名称.
                detailTemp.setReceiveOrgName(goods.getReceiveOrgName());
                //Sets the 收货部门编码.
                detailTemp.setReceiveOrgCode(goods.getReceiveOrgCode());
                //Sets the 入库时间.
                detailTemp.setStockTime(goods.getStockTime());
                //Sets the 任务编号.
                detailTemp.setTaskNo(taskNo);
                //Sets the 运输性质.
                detailTemp.setTransportType(goods.getTransportType());
                //Sets the 体积
                detailTemp.setVolume(goods.getVolume());
                //Sets the 运单号.
                detailTemp.setWayBillNo(goods.getWayBillNo());
                //7天返货类型判断 author-218427 foss-hongwy
                ReturnGoodsRequestEntity returnGoodsRequestEntity = returnGoodsRequestEntityService.queryReturnGoodsRequestEntityByWayBillNo(goods.getWayBillNo());
                if (returnGoodsRequestEntity != null && StringUtils.equals("SEVEN_DAYS_RETURN", returnGoodsRequestEntity.getReturnType())) {
                    detailTemp.setIsSevenDaysReturn("Y");
                } else {
                    detailTemp.setIsSevenDaysReturn("N");
                }
                //转寄退回件 218381
                ReturnGoodsRequestEntity returnGoodsRequestEntityForReturn =
                        returnGoodsRequestEntityService.queryReturnGoodsEntityByWayBillNo(goods.getWayBillNo());
                //未上报工单:isHandle为null,上报工单未处理:isHandle为N,上报工单已处理:isHandle为Y,如果为N,不允许提交
                String isHandle = null;
                if (returnGoodsRequestEntityForReturn != null) {
                    isHandle = "Y";
                } else {
                    isHandle = "N";
                }
                detailTemp.setIsHandle(isHandle);

                //Sets the 开单件数.
                detailTemp.setWayBillQty(goods.getWayBillQty());
                //Sets the 重量
                detailTemp.setWeight(goods.getWeight());
                //Sets the 操作件数.
                detailTemp.setOperateQty(goods.getOperateQty());
                //sets the 接货网点编码
                detailTemp.setStationNumber(goods.getStationNumber());
                detailTemp.setPackageRemark(goods.getPackageRemark());
                /** zwd 200968  运单生效状态 - YES NO*/
                detailTemp.setWayBillStatus(goods.getWayBillStatus());
                //sets the 是否电子面单
                ActualFreightEntity actualFreightEntity = new ActualFreightEntity();
                actualFreightEntity = this.actualFreightService.queryByWaybillNo(goods.getWayBillNo());
                if (actualFreightEntity != null) {
                    if (StringUtils.equals(actualFreightEntity.getWaybillType(), "EWAYBILL")) {
                        detailTemp.setBeEWaybill(FossConstants.YES);
                    } else {
                        detailTemp.setBeEWaybill(FossConstants.NO);
                    }
                } else {
                    detailTemp.setBeEWaybill(FossConstants.NO);
                }
                details.add(detailTemp);
            }
        }
        return details;
    }
    //根据卸车单据 明细查询卸车单据流水号明细

    /**
     * Query unload serial detail.
     *
     *
     *
     *
     *
     * @param bills the bills
     * @return the list
     */
    private List<UnloadGoodsSerialDetailDto> queryUnloadSerialDetail(List<UnloadBillDetailDto> bills) {
        List<UnloadGoodsSerialDetailDto> unloadGoodsList = new ArrayList<UnloadGoodsSerialDetailDto>();
        List<UnloadBillDetailDto> handOvers = new ArrayList<UnloadBillDetailDto>();
        List<UnloadBillDetailDto> assembleBills = new ArrayList<UnloadBillDetailDto>();
        List<UnloadBillDetailDto> pickups = new ArrayList<UnloadBillDetailDto>();
        List<UnloadBillDetailDto> airBills = new ArrayList<UnloadBillDetailDto>();
		/*zwd 200968 快递集中卸货*/
        List<UnloadBillDetailDto> eWayBills = new ArrayList<UnloadBillDetailDto>();
        for (UnloadBillDetailDto bill : bills) {
            if (UnloadConstants.BILL_TYPE_HANDOVER.equals(bill.getBillType())) {
                handOvers.add(bill);
            } else if (UnloadConstants.BILL_TYPE_VEHICLEASSEMBLE.equals(bill.getBillType())) {
                assembleBills.add(bill);
            } else if (StringUtils.equals(UnloadConstants.UNLOAD_TASK_TYPE_BUSINESS, bill.getBillType())) {
                airBills.add(bill);
            } else if (StringUtils.equals(UnloadConstants.BILL_TYPE_EWAYBILL, bill.getBillType())) {
                eWayBills.add(bill);
            } else {
                pickups.add(bill);
            }
        }


        List<UnloadGoodsSerialDetailDto> airGoodsDetail;
        List<UnloadGoodsSerialDetailDto> handOverGoodsDetail;
        List<UnloadGoodsSerialDetailDto> assembleGoodsDetail;
        List<UnloadGoodsSerialDetailDto> pickupGoodsDetail;
		/*zwd 200968 快递集中卸货*/
        List<UnloadGoodsSerialDetailDto> eWayGoodsDetail;
        if (CollectionUtils.isNotEmpty(eWayBills)) {
            eWayGoodsDetail = pdaUnloadTaskDao.queryEWayUnloadSerialDetail(eWayBills);
            if (CollectionUtils.isNotEmpty(eWayGoodsDetail)) {
                unloadGoodsList.addAll(eWayGoodsDetail);
            }
        }
        if (CollectionUtils.isNotEmpty(handOvers)) {
            handOverGoodsDetail = pdaUnloadTaskDao.queryHandOverUnloadSerialDetail(handOvers);
            if (CollectionUtils.isNotEmpty(handOverGoodsDetail)) {
                unloadGoodsList.addAll(handOverGoodsDetail);
            }
        }
        if (CollectionUtils.isNotEmpty(assembleBills)) {
            assembleGoodsDetail = pdaUnloadTaskDao.queryAssembleUnloadSerialDetail(assembleBills);
            if (CollectionUtils.isNotEmpty(assembleGoodsDetail)) {
                unloadGoodsList.addAll(assembleGoodsDetail);
            }
        }
        if (CollectionUtils.isNotEmpty(pickups)) {
            pickupGoodsDetail = pdaUnloadTaskDao.queryPickUpUnloadSerialDetail(pickups);
            if (CollectionUtils.isNotEmpty(pickupGoodsDetail)) {
                unloadGoodsList.addAll(pickupGoodsDetail);
            }
        }
        if (CollectionUtils.isNotEmpty(airBills)) {
            airGoodsDetail = pdaUnloadTaskDao.queryAireHandOverUnloadSerialDetail(airBills);
            if (CollectionUtils.isNotEmpty(airGoodsDetail)) {
                unloadGoodsList.addAll(airGoodsDetail);
            }
        }
        return unloadGoodsList;
    }

    /**
     * 中途添加/删除理货员.
     *
     *
     *
     *
     * @param taskNo the task no
     * @param loaderCodes the loader codes
     * @param operateTime the operate time
     * @param loaderState the loader state
     * @return the string
     * @author dp-duyi
     * @date 2012-12-19 上午10:59:04
     * @see com.deppon.foss.module.transfer.pda.api.server.service.IPDAUnloadTaskService#modifyLoader(java.lang.String, java.util.List, java.util.Date, java.lang.String)
     */
    @Override
    @Transactional
    public String modifyLoader(String taskNo, List<LoaderDto> loaderCodes,
                               Date operateTime, String loaderState) {
        //查询卸车任务
        UnloadTaskEntity unloadTask = pdaUnloadTaskDao.queryUnloadTaskByTaskNo(taskNo);
        //只有卸车中的任务可以修改理货员
        if (unloadTask != null && UnloadConstants.UNLOAD_TASK_STATE_UNLOADING.equals(unloadTask.getTaskState())) {
            if (UnloadConstants.UNLOAD_TASK_TYPE_DELIVER.equals(unloadTask.getUnloadType())) {
                pdaCommonService.modifyLoader(unloadTask.getId(), LoadConstants.LOADER_PARTICIPATION_DELIVER_UNLOAD, loaderCodes, operateTime, loaderState);
            } else {
                pdaCommonService.modifyLoader(unloadTask.getId(), LoadConstants.LOADER_PARTICIPATION_TRANSFER_UNLOAD, loaderCodes, operateTime, loaderState);
            }

            boolean tfrSwitch4Ecs = configurationParamsService.queryTfrSwitch4Ecs();
            logger.error("同步修改理货员信息到悟空系统开关" + tfrSwitch4Ecs);

            if (tfrSwitch4Ecs) {
                OrgAdministrativeInfoEntity org = orgAdministrativeInfoService
                        .queryOrgAdministrativeInfoByCode(unloadTask.getUnloadOrgCode());
                boolean isSalesDepartment = FossConstants.YES.equals(org.getSalesDepartment()) ? true : false;
                if (!isSalesDepartment) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    // 装卸车人员参与情况
                    LoaderParticipationEntity loaderQC = new LoaderParticipationEntity();
                    loaderQC.setTaskId(unloadTask.getId());
                    loaderQC.setBeCreator("Y");
                    // 根据任务ID查询理货员 --装卸车人员参与情况 from
                    // tfr.t_opt_loader_participation p
                    List<LoaderParticipationEntity> loaders = pdaUnloadTaskDao.queryLoaderByTaskId(loaderQC);
                    String operatorNo = null;
                    List<String> loaderList = new ArrayList<String>();
                    if (CollectionUtils.isNotEmpty(loaders)) {
                        operatorNo = loaders.get(0).getLoaderCode();
                    }
                    for (LoaderDto loaderCode : loaderCodes) {
                        loaderList.add(loaderCode.getLoaderCode());
                    }
                    map.put("taskNo", taskNo);
                    map.put("loaderCodes", loaderList);
                    map.put("operatorNo", operatorNo);
                    map.put("operationOrgCode", unloadTask.getUnloadOrgCode());
                    map.put("operationTime", operateTime);
                    if (TransferPDADictConstants.ADD_LOADR.equals(loaderState)) {
                        map.put("loaderState", loaderState);
                    } else {
                        map.put("loaderState", TransferPDADictConstants.DETELE_LOADR);
                    }

                    syncPDAmodifyLoaderToWk(map);

                }
            }
            return TransferPDADictConstants.SUCCESS;

        } else {//卸车任务为空，或状态不为卸车中，不能修改理货员
            throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_INVALID_TASK_MESSAGECODE);
        }
    }


    /**
     * @description 同步PDA修改理货员信息到悟空系统
     * @param syncPDAmodifyLoaderToWk
     * @return
     * @version 1.0
     * @author 328768-foss-gaojianfu
     * @update 2016年5月19日 上午9:56:31
     */
    private void syncPDAmodifyLoaderToWk(Map<String, Object> map) {
        // 获取objectMapper
        ObjectMapper objectMapper = JSONUtils.obtainObjectMapper();
        // 设置时间转换格式
        SimpleDateFormat dateFormat = new SimpleDateFormat(DateUtils.DATE_TIME_FORMAT);
        // 设置到objectMapper
        objectMapper.setDateFormat(dateFormat);

        String requestJsonStr = null;
        try {
            requestJsonStr = objectMapper.writeValueAsString(map);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        //调用同步数据接口
        FossToWKResponseEntity fossToWKResponseEntity = null;
        try {
            logger.error("同步修改理货员信息到悟空系统参数：" + requestJsonStr);
            fossToWKResponseEntity = fossToWkService.syncPDAmodifyLoaderToWk(requestJsonStr);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new TfrBusinessException("同步修改理货员信息到悟空系统失败，错误信息：" + e.getMessage());
        }
        if (fossToWKResponseEntity == null) {
            logger.error("同步修改理货员信息到悟空系统失败!");
            throw new TfrBusinessException("同步修改理货员信息到悟空系统失败!");
        }
        if ("0".equals(fossToWKResponseEntity.getStatus())) {
            logger.error("同步修改理货员信息到悟空系统失败，错误信息：" + fossToWKResponseEntity.getExMsg());
            throw new TfrBusinessException("ECS-" + fossToWKResponseEntity.getExMsg());
        }

    }

    /**
     * 撤销卸车任务.
     *
     *
     *
     *
     * @param taskNo the task no
     * @param deviceNo the device no
     * @param operatorCode the operator code
     * @param cancelTime the cancel time
     * @return the string
     * @author dp-duyi
     * @date 2012-12-19 上午11:18:58
     * @see com.deppon.foss.module.transfer.pda.api.server.service.IPDAUnloadTaskService#cancelUnloadTask(java.lang.String, java.lang.String, java.lang.String, java.util.Date)
     */
    @Override
    @Transactional
    public String cancelUnloadTask(String taskNo, String deviceNo,
                                   String operatorCode, Date cancelTime) {
        logger.error("撤销卸车任务开始" + taskNo);
        //查询卸车任务
        UnloadTaskEntity unloadTask = pdaUnloadTaskDao.queryUnloadTaskByTaskNo(taskNo);
        //只有卸车中的任务可以修改理货员
        if (unloadTask != null && UnloadConstants.UNLOAD_TASK_STATE_UNLOADING.equals(unloadTask.getTaskState())) {
            int scanQty = pdaUnloadTaskDao.queryScanSerialNoQTYByTaskId(unloadTask.getId());
            //扫描件数等于0才能取消
            if (scanQty > 0) {
                throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_EXTIST_SCAN_RECORD_MESSAGECODE);
            } else {
                unloadTask.setTaskState(UnloadConstants.UNLOAD_TASK_STATE_CANCELED);
                unloadTask.setUnloadEndTime(cancelTime);
                pdaUnloadTaskDao.updateUnloadTask(unloadTask);
                //PDA建立卸车任务时，往接送货的临时表中插值，确定发短信通知
                AutoTaskDTO dto = new AutoTaskDTO();
                dto.setId(UUIDUtils.getUUID());
                dto.setTaskDetailId(unloadTask.getId());
                dto.setTaskDetailType(UnloadConstants.UNLOAD_FOR_PKP);
                dto.setStockOrgCode("N/A");
                sharedDao.insertTempForPKP(dto);
                //修改到达单据状态为卸车中
                List<UnloadBillDetailDto> unloadBills = pdaUnloadTaskDao.queryUnloadBillsByTaskId(unloadTask.getId());
                if (CollectionUtils.isNotEmpty(unloadBills)) {
                    unloadTaskService.updateArriveBillState(unloadBills, TaskTruckConstant.BILL_ASSIGN_STATE_ASSIGNED);

                    //修改分配任务状态
                    List<AssignUnloadTaskEntity> assignedUnloadTasks = new ArrayList<AssignUnloadTaskEntity>();
                    AssignUnloadTaskEntity assignedTask;
                    /*** 修改正单交接单状态  商务专递  263072 linyuzhu 2015-9-23 19:18:46********/
                    List<ArriveBillDto> arriveAirBills = new ArrayList<ArriveBillDto>();
                    ArriveBillDto arriveBill;
                    for (UnloadBillDetailDto unloadBill : unloadBills) {
                        assignedTask = new AssignUnloadTaskEntity();
                        assignedTask.setBillNo(unloadBill.getBillNo());
                        assignedTask.setBeCanceled(FossConstants.NO);
                        assignedTask.setState(UnloadConstants.ASSIGN_UNLOAD_TASK_STATE_UNSTART);
                        assignedTask.setUnloadStartTime(null);
                        assignedUnloadTasks.add(assignedTask);
                        //修改正单交接点状态为取消
                        if (UnloadConstants.BILL_TYPE_AIRBILL.equals(unloadBill.getBillType())) {
                            arriveBill = new ArriveBillDto();
                            arriveBill.setBillNo(unloadBill.getBillNo());
                            arriveBill.setAssignState(UnloadConstants.UNLOAD_TASK_STATE_CANCELED);
                            arriveAirBills.add(arriveBill);
                        }

                    }
                    pdaUnloadTaskDao.updateAssignUnloadTaskState(assignedUnloadTasks);

                    /**
                     * 修改正单交接单卸车状态为 卸车中 （商务专递）263072 linyuzhu  2015-9-19 10:13:59
                     */
                    if (CollectionUtils.isNotEmpty(arriveAirBills)) {
                        assignUnloadTaskDao.updateAirHandOverBillState(arriveAirBills);
                    }
                }
                logger.error("撤销卸车任务结束" + taskNo);

                if (StringUtils.isNotBlank(unloadTask.getPlatformId())) {
                    logger.error("撤销卸车任务释放月台开始" + taskNo);
                    pdaCommonService.updatePlatformStateByFinishTask(taskNo, new Date());
                    logger.error("撤销卸车任务释放月台结束" + taskNo);
                }
//				派送发短信
//				unloadTaskService.deleteTfrJob(unloadTask.getId());
                boolean tfrSwitch4Ecs = configurationParamsService.queryTfrSwitch4Ecs();
                logger.error("同步建立卸车任务到悟空系统开关" + tfrSwitch4Ecs);

                if (tfrSwitch4Ecs) {
                    //调用综合接口查询当前部门是否是营业部
                    OrgAdministrativeInfoEntity org = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(unloadTask.getUnloadOrgCode());
                    boolean isSalesDepartment = FossConstants.YES.equals(org.getSalesDepartment()) ? true : false;
                    if (!isSalesDepartment) {
                        syncCancelUnloadTaskToWk(taskNo, unloadTask.getUnloadOrgCode(), operatorCode);
                    }
                }

                return TransferPDADictConstants.SUCCESS;
            }
        } else if (unloadTask != null && UnloadConstants.UNLOAD_TASK_STATE_CANCELED.equals(unloadTask.getTaskState())) {
            logger.error("撤销卸车任务结束" + taskNo);
            return TransferPDADictConstants.SUCCESS;
        } else {
            throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_INVALID_TASK_MESSAGECODE);
        }
    }

    /**
     * 非建立任务PDA完成任务.
     *
     *
     *
     *
     * @param taskNo the task no
     * @param loadEndTime the load end time
     * @param deviceNo the device no
     * @return the string
     * @author dp-duyi
     * @date 2012-12-19 下午1:44:47
     * @see com.deppon.foss.module.transfer.pda.api.server.service.IPDAUnloadTaskService#submitUnloadTask(java.lang.String, java.util.Date, java.lang.String)
     */
    @Override
    @Transactional
    public String finishUnloadTask(String taskNo, Date loadEndTime,
                                   String deviceNo) {
        UnloadTaskEntity unloadTask = pdaUnloadTaskDao.queryUnloadTaskByTaskNo(taskNo);
        //任务为进行中才能提交
        if (unloadTask != null && UnloadConstants.UNLOAD_TASK_STATE_UNLOADING.equals(unloadTask.getTaskState())) {
            //更新pdaTask:leaveTime
            Map<String, Object> parameter = new HashMap<String, Object>();
            parameter.put("taskNo", taskNo);
            parameter.put("deviceNo", deviceNo);
            parameter.put("leaveTime", loadEndTime);
            pdaLoadDao.updatePDATaskEntity(parameter);
            return TransferPDADictConstants.SUCCESS;
        } else {
            throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_INVALID_TASK_MESSAGECODE);
        }
    }

    /**
     * 建立任务PDA提交任务.
     *
     *
     *
     *
     * @param taskNo the task no
     * @param loadEndTime the load end time
     * @param deviceNo the device no
     * @param loaderCode the loader code
     * @param beException the be exception
     * @param notes the notes
     * @return the string
     * @author dp-duyi
     * @date 2012-12-19 下午1:44:476
     * @see com.deppon.foss.module.transfer.pda.api.server.service.IPDAUnloadTaskService#finishUnloadTask(java.lang.String, java.util.Date, java.lang.String, java.lang.String)
     */
    @Override
    @Transactional
    public String submitUnloadTask(String taskNo, Date loadEndTime,
                                   String deviceNo, String loaderCode, String beException, String notes) {
        logger.error("正常提交卸车任务开始" + taskNo);
        if (pdaLoadDao.queryUnSubmitPDAQty(taskNo) > 1) {
            //如果有PDA未提交任务，则失败
            throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_EXTIST_UNFINISH_PDA_MESSAGECODE);
        } else {
            pdaUnloadService.finishUnloadTaskAndSoOn(taskNo, loadEndTime, deviceNo, loaderCode, beException, notes);
            logger.error("正常提交卸车任务结束" + taskNo);
            return TransferPDADictConstants.SUCCESS;
        }
    }

    /**
     * 强制提交任务.
     *
     *
     *
     *
     * @param taskNo the task no
     * @param loadEndTime the load end time
     * @param deviceNo the device no
     * @param loaderCode the loader code
     * @param beException the be exception
     * @param notes the notes
     * @return the string
     * @author dp-duyi
     * @date 2012-12-19 下午1:44:47
     * @see com.deppon.foss.module.transfer.pda.api.server.service.IPDAUnloadTaskService#forceFinishUnloadTask(java.lang.String, java.util.Date, java.lang.String, java.lang.String)
     */
    @Override
    @Transactional
    public String forceSubmitUnloadTask(String taskNo, Date loadEndTime,
                                        String deviceNo, String loaderCode, String beException, String notes) {
        logger.error("强制提交卸车任务开始" + taskNo);
        pdaUnloadService.finishUnloadTaskAndSoOn(taskNo, new Date(), deviceNo, loaderCode, beException, notes);
        logger.error("强制提交卸车任务结束" + taskNo);
        return TransferPDADictConstants.SUCCESS;
    }

    /**
     * Finish unload task and so on.
     *
     *
     *
     *
     * @param taskNo the task no
     * @param loadEndTime the load end time
     * @param deviceNo the device no
     * @param loaderCode the loader code
     * @param beException the be exception
     * @param notes the notes
     * @return the string
     */
    @Transactional
    public String finishUnloadTaskAndSoOn(String taskNo, Date loadEndTime,
                                          String deviceNo, String loaderCode, String beException, String notes) {
        logger.error("提交卸车任务开始" + taskNo);
        UnloadTaskEntity unloadTask = pdaUnloadTaskDao.queryUnloadTaskByTaskNo(taskNo);
        //任务为进行中才能提交
        if (unloadTask != null && UnloadConstants.UNLOAD_TASK_STATE_UNLOADING.equals(unloadTask.getTaskState())) {

            List<UnloadBillDetailDto> unloadBills = pdaUnloadTaskDao.queryUnloadBillsByTaskId(unloadTask.getId());
            List<AssignUnloadTaskEntity> assignedUnloadTasks = new ArrayList<AssignUnloadTaskEntity>();
            AssignUnloadTaskEntity assignedTask;
            List<UpdateHandOverBillStateDto> handOverStates = new ArrayList<UpdateHandOverBillStateDto>();
            //修改空运正单交接单状态   263072 商务专递
            List<AirHandOverBillDto> airHandOverStates = new ArrayList<AirHandOverBillDto>();
            UpdateHandOverBillStateDto handOverState;
            for (UnloadBillDetailDto unloadBill : unloadBills) {
                assignedTask = new AssignUnloadTaskEntity();
                assignedTask.setBillNo(unloadBill.getBillNo());
                assignedTask.setBeCanceled(FossConstants.NO);
                assignedTask.setState(UnloadConstants.ASSIGN_UNLOAD_TASK_STATE_FINISHED);
                assignedTask.setUnloadEndTime(new Date());
                assignedUnloadTasks.add(assignedTask);
                if (UnloadConstants.BILL_TYPE_HANDOVER.equals(unloadBill.getBillType())) {
                    handOverState = new UpdateHandOverBillStateDto();
                    handOverState.setHandOverBillNo(unloadBill.getBillNo());
                    handOverState.setTargetState(LoadConstants.HANDOVERBILL_STATE_ALREADY_INSTOCK);
                    handOverStates.add(handOverState);
                } else if (UnloadConstants.BILL_TYPE_VEHICLEASSEMBLE.equals(unloadBill.getBillType())) {
                    List<String> handOverBillNoList = vehicleAssembleBillService.queryHandOverBillNosByVehicleAssembleNo(unloadBill.getBillNo());
                    if (CollectionUtils.isNotEmpty(handOverBillNoList)) {
                        for (String handOverBillNo : handOverBillNoList) {
                            handOverState = new UpdateHandOverBillStateDto();
                            handOverState.setHandOverBillNo(handOverBillNo);
                            handOverState.setTargetState(LoadConstants.HANDOVERBILL_STATE_ALREADY_INSTOCK);
                            handOverStates.add(handOverState);
                        }
                    }
                } else if (UnloadConstants.UNLOAD_TASK_TYPE_BUSINESS.equals(unloadBill.getBillType()) ||
                        UnloadConstants.UNLOAD_TASK_TYPE_PACKAGE.equals(unloadBill.getBillType())) {
                    /****** 商务专递类型 263072 *********/
                    AirHandOverBillDto airHandOverBillDto = new AirHandOverBillDto();
                    airHandOverBillDto.setExpressUnloadStatus(UnloadConstants.ASSIGN_UNLOAD_TASK_STATE_FINISHED);
                    airHandOverBillDto.setAirWaybillStockState("Y");
                    airHandOverBillDto.setAirHandoverNo(unloadBill.getBillNo());
                    airHandOverStates.add(airHandOverBillDto);
                }

            }

            //卸车流水号清单
            List<UnloadGoodsSerialDetailDto> unloadList = this.queryUnloadSerialDetail(unloadBills);
            //卸车扫描清单（包括已撤销记录）
            List<UnloadSerialNoDetailDto> scanList = pdaUnloadTaskDao.queryUnloadScanSerialDetailByTaskId(unloadTask.getId());
            LoaderParticipationEntity loaderParticipationEntity = pdaLoadDao.queryLoaderByTaskID(unloadTask.getId());
            //zwd 200968
            for (UnloadSerialNoDetailDto unloadSerialNoDetailDto : scanList) {
                if (UnloadConstants.WAYBILL_STATUS_NO.equals(unloadSerialNoDetailDto.getWayBillStatus())) {
                    PdaScanQueryDto pdaScanInfo = new PdaScanQueryDto();
                    //运单号
                    pdaScanInfo.setWaybillNo(unloadSerialNoDetailDto.getWayBillNo());
                    //流水号
                    pdaScanInfo.setSerialNo(unloadSerialNoDetailDto.getSerialNo());
                    //司机（快递员）工号
                    pdaScanInfo.setDriverCode(loaderParticipationEntity.getLoaderCode());
                    //Pda扫描时间
                    pdaScanInfo.setScanTime(unloadSerialNoDetailDto.getOptTime());
                    //卸车人     2015.3.26
                    pdaScanInfo.setClerkCode(loaderParticipationEntity.getLoaderCode());
                    //卸车部门 2015.3.26
                    pdaScanInfo.setOperateOrgCode(unloadSerialNoDetailDto.getCreateOrgCode());
                    //Pda扫描类型 任务类型
                    if (unloadSerialNoDetailDto.getOptGoodsQty() > 0) {
                        pdaScanInfo.setScanType("SCAN");
                        pdaScanInfo.setTaskType("UNLOAD");
                    } else {
                        pdaScanInfo.setScanType("CANCEL");
                        pdaScanInfo.setTaskType("CANCELED");
                    }

                    //Pda扫描任务ID
                    pdaScanInfo.setTaskId(unloadTask.getId());
                    //设置重量
                    pdaScanInfo.setGoodsWeight(BigDecimal.valueOf(unloadSerialNoDetailDto.getWeight()));
                    //设置体积
                    pdaScanInfo.setGoodsVolume(BigDecimal.valueOf(unloadSerialNoDetailDto.getVolume()));
                    //设置订单号
                    pdaScanInfo.setOrderNo(null);
                    pdaScanService.savePdaScanInfo(pdaScanInfo);
                }
            }

            //生成少货
            if (CollectionUtils.isNotEmpty(unloadList)) {
                this.generateLackGoods(unloadTask, unloadList, scanList);
            }
            //入库卸车货物
			/*boolean beInstock;
			if(!FossConstants.YES.equals(unloadTask.getBeScanInstock())){
				beInstock = true;
			}else{
				beInstock = false;
			}*/
			/*InOutStockEntity inOutStockEntity;
			for(UnloadSerialNoDetailDto scanGoods : scanList){
				if( !UnloadConstants.UNLOAD_GOODS_STATE_CANCELED.equals(scanGoods.getGoodsStatus())){
					//入库
					if(beInstock){
						inOutStockEntity = new InOutStockEntity();
						inOutStockEntity.setWaybillNO(scanGoods.getWayBillNo());
						inOutStockEntity.setSerialNO(scanGoods.getSerialNo());
						inOutStockEntity.setOrgCode(unloadTask.getUnloadOrgCode());
						inOutStockEntity.setInOutStockType(StockConstants.UNLOAD_GOODS_IN_STOCK_TYPE);
						inOutStockEntity.setOperatorCode("N/A");
						inOutStockEntity.setOperatorName("N/A");
						stockService.inStockPC(inOutStockEntity);
					}
				}
			}*/
            // 修改卸车理货员离开时间
            Map<String, Object> param = new HashMap<String, Object>();
            //任务id
            param.put("id", unloadTask.getId());
            //结束时间
            param.put("endTime", new Date());
            pdaLoadDao.updateLoaderParticipationByLoadTask(param);
            // 修改卸车PDA离开时间
            if (StringUtils.isNotBlank(deviceNo)) {
                Map<String, Object> parameter = new HashMap<String, Object>();
                //任务号
                parameter.put("taskNo", taskNo);
                //设备号
                parameter.put("deviceNo", deviceNo);
                //离开时间
                parameter.put("leaveTime", new Date());
                pdaLoadDao.updatePDATaskEntity(parameter);
            }
            // 修改卸车任务
            unloadTask.setUnloadEndTime(new Date());
            //已完成
            unloadTask.setTaskState(UnloadConstants.UNLOAD_TASK_STATE_FINISHED);
            //设置 是否卸车异常
            unloadTask.setBeException(beException);
            //设置 异常备注
            unloadTask.setExceptionNotes(notes);
            //更新卸车任务
            pdaUnloadTaskDao.updateUnloadTask(unloadTask);
            // 修改月台
            //platformDispatchService.updatePlatformStatusForEnd(taskNo, loadEndTime);

            //营业部卸车不更新月台---332219
            if (!StringUtils.equals(unloadTask.getUnloadType(), UnloadConstants.UNLOAD_TASK_TYPE_DEPARTMENT)) {
                try {
                    pdaCommonService.updatePlatformStateByFinishTask(taskNo, new Date());
                } catch (Exception e) {
                    //sonar-352203
                    logger.info("PDAUnloadTaskService.finishUnloadTaskAndSoOn 报错:" + StringUtils.substring(e.toString(), 0, ConstantsNumberSonar.SONAR_NUMBER_100));
                }
            }
            // 修改到达单据状态、修改任务车辆状态
            unloadTaskService.updateArriveBillState(unloadBills, TaskTruckConstant.BILL_ASSIGN_STATE_UNLOADED);

            List<UpdateHandOverBillStateDto> expressList = new ArrayList<UpdateHandOverBillStateDto>();

            for (UpdateHandOverBillStateDto UpdateHandOverBillStateDto : handOverStates) {
                if (UnloadCommonUtils.isExpressHandOver(UpdateHandOverBillStateDto.getHandOverBillNo())) {
                    expressList.add(UpdateHandOverBillStateDto);
                }
            }

            // 修改交接单状态
            if (CollectionUtils.isNotEmpty(handOverStates)) {
                //此处不更新快递交接单据状态
                handOverStates.removeAll(expressList);
                if (CollectionUtils.isNotEmpty(handOverStates)) {
                    handOverBillService.updateHandOverBillState(handOverStates);
                }
            }

            /** 更新空运正单交接单卸车状态 263072 商务专递  **/
            if (CollectionUtils.isNotEmpty(airHandOverStates)) {
                airHandOverBillService.updateAirWaybillUnloadStatus(airHandOverStates);
            }

            // 修改分配卸车任务状态
            pdaUnloadTaskDao.updateAssignUnloadTaskState(assignedUnloadTasks);

            UnloadTaskEntity unloadTaskEntity = pdaUnloadTaskDao.queryUnloadTaskByTaskNo(taskNo);
            String orgCode = unloadTaskEntity.getUnloadOrgCode();
            if (StringUtils.isEmpty(loaderCode)) {
                if (null != loaderParticipationEntity) {
                    loaderCode = loaderParticipationEntity.getLoaderCode();
                }
            }
            //营业部卸车不同步WK---332219
            if (!StringUtils.equals(unloadTask.getUnloadType(), UnloadConstants.UNLOAD_TASK_TYPE_DEPARTMENT)) {
                boolean tfrSwitch4Ecs = configurationParamsService.queryTfrSwitch4Ecs();
                logger.error("同步提交卸车任务到悟空系统开关" + tfrSwitch4Ecs);
                if (tfrSwitch4Ecs) {
                    //调用综合接口查询当前部门是否是营业部
                    OrgAdministrativeInfoEntity org = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(orgCode);
                    boolean isSalesDepartment = FossConstants.YES.equals(org.getSalesDepartment()) ? true : false;
                    /**foss确认卸车任务成功后同步确认卸车信息到WK系统*/
                    if (!isSalesDepartment) {
                        syncPDAConfirmUnloadTaskToWk(taskNo, orgCode, loadEndTime, deviceNo, loaderCode, beException, notes);
                    }
                }
            }
            //判断是否合伙人部门  标记
            boolean isPTPfalage = false;
            //查询卸车部门
            SaleDepartmentEntity saleDepartmentEntity = saleDepartmentService.querySaleDepartmentInfoByCode(unloadTask.getUnloadOrgCode());
            if (saleDepartmentEntity != null) {
                isPTPfalage = StringUtils.equals(FossConstants.YES, saleDepartmentEntity.getIsLeagueSaleDept());
            }
            //到达合伙人卸车 插入代办表 定时任务  推送PTP触发扣款
            if (isPTPfalage) {
                // 卸车任务id存入job 代办表
                tfrJobTodoService.addJobTodo(
                        unloadTask.getId(),
                        BusinessSceneConstants.BUSINESS_SCENE_TRUCK_UNLOAD,
                        new String[]{BusinessGoalContants.BUSINESS_GOAL_TO_PTP},
                        new Date(), loaderCode);
            }
            logger.error("提交卸车任务结束" + taskNo);
            return TransferPDADictConstants.SUCCESS;
        } else {
            logger.error(taskNo, TransferPDAExceptionCode.EXCEPTION_INVALID_TASK_MESSAGECODE);
            logger.error("提交卸车任务结束" + taskNo);
            return TransferPDADictConstants.SUCCESS;
        }
    }

    /**
     * @description FOSS同步取消pda卸车任务到wk系统
     * @param taskNo
     * @param loadEndTime
     * @param deviceNo
     * @param loaderCode
     * @param beException
     * @param notes
     * @version 1.0
     * @author 328768-foss-gaojianfu
     * @update 2016年5月18日 下午7:01:21
     */
    private FossToWKResponseEntity syncCancelUnloadTaskToWk(String taskNo,
                                                            String updateOrgCode, String updateNo) {

        ExpressCancelUnloadTaskDto expressCancelUnloadTaskDto = new ExpressCancelUnloadTaskDto();
        expressCancelUnloadTaskDto.setUnloadTaskNo(taskNo);
        expressCancelUnloadTaskDto.setUpdateTime(new Date());
        //设置  当前操作人编号
        expressCancelUnloadTaskDto.setUpdateNo(updateNo);
        //设置 当前部门编号
        expressCancelUnloadTaskDto.setUpdateOrgCode(updateOrgCode);
        // 获取objectMapper
        ObjectMapper objectMapper = JSONUtils.obtainObjectMapper();
        // 设置时间转换格式
        SimpleDateFormat dateFormat = new SimpleDateFormat(DateUtils.DATE_TIME_FORMAT);
        // 设置到objectMapper
        objectMapper.setDateFormat(dateFormat);

        String requestJsonStr = null;
        try {
            requestJsonStr = objectMapper.writeValueAsString(expressCancelUnloadTaskDto);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        //调用同步数据接口
        FossToWKResponseEntity fossToWKResponseEntity = null;
        try {
            logger.error("Foss同步取消卸车任务到悟空系统参数：" + requestJsonStr);
            fossToWKResponseEntity = fossToWkService.syncCancelUnloadTaskToWk(requestJsonStr);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new TfrBusinessException("Foss同步取消卸车任务到悟空系统失败，错误信息：" + e.getMessage());
        }
        if (fossToWKResponseEntity == null) {
            logger.error("Foss同步取消卸车任务到悟空系统失败!");
            throw new TfrBusinessException("Foss同步取消卸车任务到悟空系统失败!");
        }
        if ("0".equals(fossToWKResponseEntity.getStatus())) {
            logger.error("Foss同步取消卸车任务到悟空系统失败，错误信息：" + fossToWKResponseEntity.getExMsg());
            throw new TfrBusinessException("ECS-" + fossToWKResponseEntity.getExMsg());
        }
        return fossToWKResponseEntity;
    }


    /**
     * @description FOSS同步pda确认卸车任务到wk系统
     * @param taskNo
     * @param loadEndTime
     * @param deviceNo
     * @param loaderCode
     * @param beException
     * @param notes
     * @version 1.0
     * @author 328768-foss-gaojianfu
     * @update 2016年5月18日 下午7:01:21
     */
    private FossToWKResponseEntity syncPDAConfirmUnloadTaskToWk(String taskNo, String orgCode, Date loadEndTime,
                                                                String deviceNo, String loaderCode, String beException, String notes) {

        /**同步pda确认卸车任务到悟空dto*/
        SyncPDAUnloadTaskDataToWkDto syncPDAUnloadTaskDataToWkDto = new SyncPDAUnloadTaskDataToWkDto();
        /**设置   卸车任务编号*/
        syncPDAUnloadTaskDataToWkDto.setUnloadTaskNo(taskNo);
        /**设置  操作部门编号*/
        syncPDAUnloadTaskDataToWkDto.setOperationOrgCode(orgCode);
        /**设置  卸车结束时间*/
        syncPDAUnloadTaskDataToWkDto.setUnloadEndTime(loadEndTime);
        /**设置  操作设备*/
        syncPDAUnloadTaskDataToWkDto.setOperationDevice("PDA");
        /**设置  操作设备编码*/
        syncPDAUnloadTaskDataToWkDto.setOperationDeviceCode(deviceNo);
        /** 离开任务时间*/
        syncPDAUnloadTaskDataToWkDto.setLeaveTaskTime(loadEndTime);
        /**备注*/
        syncPDAUnloadTaskDataToWkDto.setNotes(notes);
        /**异常信息*/
        syncPDAUnloadTaskDataToWkDto.setBeException(beException);
        /**操作人工号*/
        syncPDAUnloadTaskDataToWkDto.setOperatorNo(loaderCode);

        // 获取objectMapper
        ObjectMapper objectMapper = JSONUtils.obtainObjectMapper();
        // 设置时间转换格式
        SimpleDateFormat dateFormat = new SimpleDateFormat(DateUtils.DATE_TIME_FORMAT);
        // 设置到objectMapper
        objectMapper.setDateFormat(dateFormat);

        String requestJsonStr = null;
        try {
            requestJsonStr = objectMapper.writeValueAsString(syncPDAUnloadTaskDataToWkDto);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        //调用同步数据接口
        FossToWKResponseEntity fossToWKResponseEntity = null;
        try {
            logger.error("Foss同步确认卸车任务到悟空系统参数：" + requestJsonStr);
            fossToWKResponseEntity = fossToWkService.syncPDAConfirmUnloadTaskToWk(requestJsonStr);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new TfrBusinessException("Foss同步确认卸车任务到悟空系统失败，错误信息：" + e.getMessage());
        }
        if (fossToWKResponseEntity == null) {
            logger.error("Foss同步确认卸车任务到悟空系统失败!");
            throw new TfrBusinessException("Foss同步确认卸车任务到悟空系统失败!");
        }
        if ("0".equals(fossToWKResponseEntity.getStatus())) {
            logger.error("Foss同步确认卸车任务到悟空系统失败，错误信息：" + fossToWKResponseEntity.getExMsg());
            throw new TfrBusinessException("ECS-" + fossToWKResponseEntity.getExMsg());
        }
        return fossToWKResponseEntity;
    }

    //判断卸车部门是否为外场或驻地派送部
    /**
     * Checks if is transfer center or station.
     *
     *
     *
     * @param unloadOrg the unload org
     * @return true, if is transfer center or station
     */
	/*private  boolean isTransferCenterOrStation(OrgAdministrativeInfoEntity unloadOrg){
		if(FossConstants.YES.equals(unloadOrg.getTransferCenter())){
			return true;
		}else{
			SaleDepartmentEntity saleDetp = saleDepartmentService.querySaleDepartmentByCode(unloadOrg.getCode());
			if(saleDetp != null && FossConstants.YES.equals(saleDetp.getStation())){
				return true;
			}
			return false;
		}
	}*/

    /**
     * 卸车多货检验
     * @author dp-duyi
     * @date 2013-4-24 上午9:49:13
     * @see com.deppon.foss.module.transfer.pda.api.server.service.IPDAUnloadTaskService#moreGoodsUnloadVerify(java.lang.String, java.lang.String, java.lang.String)
     */
    //@Override
    public UnloadGoodsDetailDto moreGoodsUnloadVerify(String taskNo,
                                                      String wayBillNo, String serialNo) {
        logger.error("卸车多货校验开始" + taskNo + " " + wayBillNo + " " + serialNo);
        UnloadTaskEntity unloadTask = pdaUnloadTaskDao.queryUnloadTaskByTaskNo(taskNo);
        //校验任务是否有效
        if (unloadTask != null && UnloadConstants.UNLOAD_TASK_STATE_UNLOADING.equals(unloadTask.getTaskState())) {
            //加强卸校验开关
            boolean verifyMoreGoods = false;
            ConfigurationParamsEntity defaultBizHourSlice = configurationParamsService.queryConfigurationParamsByOrgCode(
                    DictionaryConstants.SYSTEM_CONFIG_PARM__TFR,
                    ConfigurationParamsConstants.TFR_PARM_UNLOAD_IGNORE_SEPARATE_UNLOAD,
                    FossConstants.ROOT_ORG_CODE);
            if (defaultBizHourSlice != null
                    && StringUtils.isNotEmpty(defaultBizHourSlice.getConfValue())) {
                if (FossConstants.NO.equals(defaultBizHourSlice.getConfValue())) {
                    verifyMoreGoods = true;
                } else {
                    verifyMoreGoods = false;
                }
            }
            if (verifyMoreGoods) {
                //校验标签是否有效
                if (!this.beValidLabeledGoods(wayBillNo, serialNo)) {
                    logger.error("卸车多货校验结束" + taskNo + " " + wayBillNo + " " + serialNo);
                    throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_INVALID_LABELED_GOODS_MESSAGECODE);
                }
                List<UnloadBillDetailDto> unloadBills = pdaUnloadTaskDao.queryUnloadBillsByTaskId(unloadTask.getId());
                if (CollectionUtils.isNotEmpty(unloadBills)) {
                    List<String> unloadBillCodes = new ArrayList<String>();
                    for (UnloadBillDetailDto unloadBill : unloadBills) {
                        //去掉接送货单据
                        if (TransferPDADictConstants.UNLOAD_ORDER_TYPE_HANDOVER.equals(unloadBill.getBillType()) || TransferPDADictConstants.UNLOAD_ORDER_TYPE_VEHICLEASSEMBLE.equals(unloadBill.getBillType())) {
                            unloadBillCodes.add(unloadBill.getBillNo());
                        }
                    }
                    if (CollectionUtils.isNotEmpty(unloadBillCodes)) {
                        List<String> orgCodes = new ArrayList<String>();
                        //到达部门为建立卸车任务部门
                        orgCodes.add(unloadTask.getUnloadOrgCode());
                        //如果建立任务部门为外场，且存在空运总调，则到达部门加上空运总调
                        OutfieldEntity outfieldEntity = outfieldService.queryOutfieldByCode(unloadTask.getUnloadOrgCode());
                        if (outfieldEntity != null) {
                            if (StringUtils.isNotBlank(outfieldEntity.getAirDispatchCode())) {
                                orgCodes.add(outfieldEntity.getAirDispatchCode());
                            }
                        }
                        List<ArriveBillDto> unArriveBills = pdaUnloadTaskDao.queryUnArriveBillInVehicle(orgCodes, unloadBillCodes);
                        if (CollectionUtils.isNotEmpty(unArriveBills)) {
                            List<String> handOverCodes = new ArrayList<String>();
                            List<String> assembleCodes = new ArrayList<String>();
                            for (ArriveBillDto unArriveBill : unArriveBills) {
                                if (TransferPDADictConstants.UNLOAD_ORDER_TYPE_VEHICLEASSEMBLE.equals(unArriveBill.getBillType())) {
                                    assembleCodes.add(unArriveBill.getBillNo());
                                } else {
                                    handOverCodes.add(unArriveBill.getBillNo());
                                }
                            }
                            boolean goodsInUnArriveBill = false;
                            if (CollectionUtils.isNotEmpty(assembleCodes)) {
                                goodsInUnArriveBill = pdaUnloadTaskDao.queryGoodsBeInAssembleBill(assembleCodes, wayBillNo, serialNo);
                                if (goodsInUnArriveBill) {
                                    logger.error("卸车多货校验结束" + taskNo + " " + wayBillNo + " " + serialNo);
                                    throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_UNLOAD_EXTRA);
                                }
                            }
                            if (CollectionUtils.isNotEmpty(handOverCodes)) {
                                goodsInUnArriveBill = pdaUnloadTaskDao.queryGoodsBeInHandOverBill(handOverCodes, wayBillNo, serialNo);
                                if (goodsInUnArriveBill) {
                                    logger.error("卸车多货校验结束" + taskNo + " " + wayBillNo + " " + serialNo);
                                    throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_UNLOAD_EXTRA);
                                }
                            }
                        }
                    }
                } else {
                    logger.error("卸车多货校验结束" + taskNo + " " + wayBillNo + " " + serialNo);
                    throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_INVALID_UNLAOD_BILL_MESSAGECODE);
                }
            }
            UnloadGoodsDetailDto goodsDetail = new UnloadGoodsDetailDto();
            WaybillEntity wayBill = waybillManagerService.queryWaybillBasicByNo(wayBillNo);
            if (wayBill != null) {
                goodsDetail.setGoodsName(wayBill.getGoodsName());
                goodsDetail.setIsValue(wayBill.getPreciousGoods());
                goodsDetail.setPacking(wayBill.getGoodsPackage());
                goodsDetail.setReachOrgCode(wayBill.getCustomerPickupOrgCode());
                goodsDetail.setReachOrgName(wayBill.getCustomerPickupOrgName());
                goodsDetail.setReceiveOrgCode(wayBill.getReceiveOrgCode());
                goodsDetail.setReceiveOrgName(wayBill.getReceiveOrgName());
                ProductEntity product = productService4Dubbo.getProductByCache(wayBill.getProductCode(), null);
                LOGGER.info("PDAUnloadTaskService调用计价dubbo接口成功，product = {}" ,product);
                if (product != null) {
                    goodsDetail.setTransportType(product.getName());
                }
                if (wayBill.getGoodsVolumeTotal() != null) {
                    goodsDetail.setVolume(wayBill.getGoodsVolumeTotal().doubleValue());
                }
                if (wayBill.getGoodsWeightTotal() != null) {
                    goodsDetail.setWeight(wayBill.getGoodsWeightTotal().doubleValue());
                }
                goodsDetail.setWayBillQty(wayBill.getGoodsQtyTotal());
                if (StringUtils.isNotBlank(wayBill.getCustomerPickupOrgCode())) {
                    SaleDepartmentEntity saleDetp = saleDepartmentService.querySaleDepartmentByCode(wayBill.getCustomerPickupOrgCode());
                    if (saleDetp != null) {
                        goodsDetail.setStationNumber(saleDetp.getStationNumber());
                    } else {
                        OuterBranchEntity outBranch = vehicleAgencyDeptService.queryOuterBranchByBranchCode(wayBill.getCustomerPickupOrgCode(), null);
                        if (outBranch != null) {
                            goodsDetail.setStationNumber(outBranch.getStationNumber());
                        }
                    }
                }
            }
            goodsDetail.setBePriorityGoods(FossConstants.NO);
            goodsDetail.setBillNo(UnloadConstants.UNLOAD_MORE_BILL_NO);
            goodsDetail.setDestOrgCode(unloadTask.getUnloadOrgCode());
            goodsDetail.setOperateQty(1);
            goodsDetail.setOrigOrgCode(unloadTask.getUnloadOrgCode());
            List<PDAGoodsSerialNoDto> serialNos = new ArrayList<PDAGoodsSerialNoDto>();
            PDAGoodsSerialNoDto serial = new PDAGoodsSerialNoDto();
            //serial.setHasToDoList(hasToDoList);
            UnloadGoodsSerialDetailDto goodsMsg = pdaUnloadTaskDao.queryUnloadGoodsDetail(wayBillNo, serialNo, unloadTask.getUnloadOrgCode());
            if (goodsMsg != null) {
                serial.setIsUnPacking(goodsMsg.getIsUnPacking());
                serial.setIsToDoList(goodsMsg.getIsToDoList());
                goodsDetail.setBeContraband(goodsMsg.getBeContraband());
                //goodsDetail.setModifyState(goodsMsg.getModifyState());
                //goodsDetail.setModifyContent(goodsMsg.getModifyContent());
                StringBuilder str = new StringBuilder();
                if (FossConstants.YES.equals(goodsMsg.getIsToDoList())) {
                    str.append("有更改 ");
                } else if (FossConstants.YES.equals(goodsMsg.getIsUnPacking())) {
                    str.append(" 代打包装 ");
                } else if (FossConstants.YES.equals(goodsMsg.getIsValue())) {
                    str.append(" 贵重物品");
                }
                //Sets the 备注
                goodsDetail.setNotes(str.toString().trim());
            } else {
                serial.setIsUnPacking(FossConstants.NO);
                serial.setIsToDoList(FossConstants.NO);
                goodsDetail.setBeContraband(FossConstants.NO);
                //goodsDetail.setModifyState(TransferPDADictConstants.MODIFY_STATE_NO_CHANGE);
            }
            serial.setSerialNo(serialNo);
            serialNos.add(serial);
            goodsDetail.setSerialNos(serialNos);
            //goodsDetail.setStockAreaCode(stockAreaCode);
            //goodsDetail.setStockAreaName(stockAreaName);
            //goodsDetail.setStockTime(stockTime);
            goodsDetail.setTaskNo(taskNo);
            goodsDetail.setWayBillNo(wayBillNo);
            logger.error("卸车多货校验结束" + taskNo + " " + wayBillNo + " " + serialNo);
            return goodsDetail;
        } else {
            logger.error("卸车多货校验结束" + taskNo + " " + wayBillNo + " " + serialNo);
            throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_INVALID_TASK_MESSAGECODE);
        }
    }

    /**
     * 没有业务锁的卸车扫描
     * @author dp-duyi
     * @date 2013-3-20 上午9:17:37
     * @see com.deppon.foss.module.transfer.unload.api.server.service.IPDAUnloadService#unlockunLoadScan(com.deppon.foss.module.transfer.pda.api.shared.dto.LoadScanDetailDto)
     */
    @Override
    public String unlockunLoadScan(UnloadScanDetailDto scanRecord) {
        logger.error("卸车扫描开始" + scanRecord.getType() + " " + scanRecord.getTaskNo() + " " + scanRecord.getBillNo() + " " + scanRecord.getWayBillNo() + " " + scanRecord.getSerialNo() + " " + scanRecord.getVolume());
        UnloadTaskEntity unloadTask = pdaUnloadTaskDao.queryUnloadTaskByTaskNo(scanRecord.getTaskNo());
        boolean hasTodoList = false;
        UnloadSerialNoDetailEntity serialNoEntity = new UnloadSerialNoDetailEntity();
        //任务为进行中才能提交
        if (unloadTask != null && UnloadConstants.UNLOAD_TASK_STATE_UNLOADING.equals(unloadTask.getTaskState())) {
            List<UnloadBillDetailDto> unloadBills = null;
            unloadBills = pdaUnloadTaskDao.queryUnloadBillsByTaskId(unloadTask.getId());
            Map<String, String> condition = new HashMap<String, String>();
            condition.put("wayBillNo", scanRecord.getWayBillNo());
            condition.put("serialNo", scanRecord.getSerialNo());
            condition.put("active", FossConstants.ACTIVE);
            //查询货物是否已签收 查询运单签收状态
            int signedQty = pdaLoadDao.queryWayBillSignedState(condition);
            //货签已签收
            if (signedQty > 0) {
                throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_ALREADY_SIGN__MESSAGECODE);
            }
            if (FossConstants.YES.equals(scanRecord.getBePartial()) && !UnloadConstants.UNLOAD_GOODS_STATE_MORE.equals(scanRecord.getType())) {
                /**查询货物所在单据及交接件数，
                 * 如果不在，货物的状态为多货（扫描货物状态不为删除的情况），单据号为多货，交接件数为0
                 * */
                if (CollectionUtils.isNotEmpty(unloadBills)) {
                    List<String> unloadHandOverBillCodes = new ArrayList<String>();
                    List<String> unloadPickUpBillCodes = new ArrayList<String>();
                    List<String> unloadAssembleBillCodes = new ArrayList<String>();
                    List<String> unloadAirBillCodes = new ArrayList<String>();
                    // 200968 zwd 快递集中卸货 2015.3.24
                    List<String> unloadEwayBillCodes = new ArrayList<String>();

                    //TODO
                    //判断属于哪一种卸车类型
                    for (UnloadBillDetailDto unloadBill : unloadBills) {
                        if (TransferPDADictConstants.UNLOAD_ORDER_TYPE_HANDOVER.equals(unloadBill.getBillType())) {
                            unloadHandOverBillCodes.add(unloadBill.getBillNo());
                        } else if (TransferPDADictConstants.UNLOAD_ORDER_TYPE_VEHICLEASSEMBLE.equals(unloadBill.getBillType())) {
                            unloadAssembleBillCodes.add(unloadBill.getBillNo());
                        } else if (TransferPDADictConstants.UNLOAD_ORDER_TYPE_AIRBILL.equals(unloadBill.getBillType())) {
                            unloadAirBillCodes.add(unloadBill.getBillNo());
                        } else if (TransferPDADictConstants.UNLOAD_ORDER_TYPE_EWAYBILL.equals(unloadBill.getBillType())) {
                            unloadEwayBillCodes.add(unloadBill.getBillNo());
                        } else {
                            unloadPickUpBillCodes.add(unloadBill.getBillNo());
                        }
                    }
                    // 出发部门编号origOrgCode  到达部门编号destOrgCode 单据编号billNo  交接件数handOverQty
                    UnloadBillDetailDto unloadBill = null;
                    if (CollectionUtils.isNotEmpty(unloadAssembleBillCodes)) {
                        unloadBill = pdaUnloadTaskDao.queryAssembleBillByGoods(unloadAssembleBillCodes, scanRecord.getWayBillNo(), scanRecord.getSerialNo());
                    }
                    if (CollectionUtils.isNotEmpty(unloadHandOverBillCodes) && unloadBill == null) {
                        unloadBill = pdaUnloadTaskDao.queryHandOverBillByGoods(unloadHandOverBillCodes, scanRecord.getWayBillNo(), scanRecord.getSerialNo());
                    }
                    if (CollectionUtils.isNotEmpty(unloadPickUpBillCodes) && unloadBill == null) {
                        //h.handovertask_no as billNo,hd.goods_qty as pieces
                        unloadBill = pdaUnloadTaskDao.queryPickUpBillByGoods(unloadPickUpBillCodes, scanRecord.getWayBillNo(), scanRecord.getSerialNo());
                        if (unloadBill != null) {
                            unloadBill.setOrigOrgCode(unloadTask.getUnloadOrgCode());
                            unloadBill.setDestOrgCode(unloadTask.getUnloadOrgCode());
                        }
                    }
                    if (CollectionUtils.isNotEmpty(unloadAirBillCodes) && unloadBill == null) {
                        unloadBill = pdaUnloadTaskDao.queryAirBillByGoods(unloadPickUpBillCodes, scanRecord.getWayBillNo(), scanRecord.getSerialNo());
                        if (unloadBill != null) {
                            unloadBill.setOrigOrgCode(unloadTask.getUnloadOrgCode());
                            unloadBill.setDestOrgCode(unloadTask.getUnloadOrgCode());
                        }
                    }
                    //zwd 200968 快递集中卸车 2015.3.24
                    if (CollectionUtils.isNotEmpty(unloadEwayBillCodes) && unloadBill == null) {
                        //h.handovertask_no as billNo,hd.goods_qty as pieces
                        unloadBill = pdaUnloadTaskDao.queryEwayBillByGoods(unloadEwayBillCodes, scanRecord.getWayBillNo(), scanRecord.getSerialNo());
                        if (unloadBill != null) {
                            unloadBill.setOrigOrgCode(unloadTask.getUnloadOrgCode());
                            unloadBill.setDestOrgCode(unloadTask.getUnloadOrgCode());
                        }
                    }
                    // 出发部门编号origOrgCode  到达部门编号destOrgCode 单据编号billNo  交接件数handOverQty
                    if (unloadBill != null) {
                        scanRecord.setBillNo(unloadBill.getBillNo());
                        if (unloadBill.getPieces() != null) {
                            scanRecord.setHandOverQty(unloadBill.getPieces().intValue());
                        } else {
                            scanRecord.setHandOverQty(0);
                        }
                        scanRecord.setOrigOrgCode(unloadBill.getOrigOrgCode());
                        scanRecord.setDestOrgCode(unloadBill.getDestOrgCode());
                    } else {
                        if (!UnloadConstants.UNLOAD_GOODS_STATE_CANCELED.equals(scanRecord.getType())) {
                            scanRecord.setType(UnloadConstants.UNLOAD_GOODS_STATE_MORE);
                        }
                        //四种类型都不属于，则属于多货
                        scanRecord.setBillNo(UnloadConstants.UNLOAD_MORE_BILL_NO);
                        scanRecord.setHandOverQty(0);
                    }
                }
            }
            String handleType = UnloadConstants.LABEL_HANDLE_NOTHING;
            //扫描件数
            int scanGoodsQtyDif = 0;
            String goodsState = scanRecord.getType();

            //KDTE-3384，把代码由2095行提前至此
            //查询接送货单据中是否有此货物
            boolean beExistsInPickUpBill = false;
            UnloadBillDetailDto pickUpBill = null;
            if (CollectionUtils.isNotEmpty(unloadBills)) {
                List<String> unloadPickUpBillCodes = new ArrayList<String>();
                for (UnloadBillDetailDto unloadBill : unloadBills) {
                    //去掉中转单据
                    if (!TransferPDADictConstants.UNLOAD_ORDER_TYPE_HANDOVER.equals(unloadBill.getBillType()) && !TransferPDADictConstants.UNLOAD_ORDER_TYPE_VEHICLEASSEMBLE.equals(unloadBill.getBillType())
                            && !TransferPDADictConstants.UNLOAD_ORDER_TYPE_AIRBILL.equals(unloadBill.getBillType()) && !TransferPDADictConstants.UNLOAD_ORDER_TYPE_EWAYBILL.equals(unloadBill.getBillType())) {
                        unloadPickUpBillCodes.add(unloadBill.getBillNo());
                    }
                }
                if (CollectionUtils.isNotEmpty(unloadPickUpBillCodes)) {
                    pickUpBill = pdaUnloadTaskDao.queryPickUpBillByGoods(unloadPickUpBillCodes, scanRecord.getWayBillNo(), scanRecord.getSerialNo());
                    if (pickUpBill != null) {
                        pickUpBill.setOrigOrgCode(unloadTask.getUnloadOrgCode());
                        pickUpBill.setDestOrgCode(unloadTask.getUnloadOrgCode());
                    }
                }
                //如果接送货结果集不为空则赋值
                if (pickUpBill != null) {
                    beExistsInPickUpBill = true;
                    scanRecord.setBillNo(pickUpBill.getBillNo());
                    goodsState = UnloadConstants.UNLOAD_GOODS_STATE_NORMAL;
                    if (pickUpBill.getPieces() != null) {
                        scanRecord.setHandOverQty(pickUpBill.getPieces().intValue());
                    }
                    scanRecord.setDestOrgCode(pickUpBill.getDestOrgCode());
                    scanRecord.setOrigOrgCode(pickUpBill.getOrigOrgCode());
                }
            }
            //如果为取消扫!
            if (UnloadConstants.UNLOAD_GOODS_STATE_CANCELED.equals(scanRecord.getType())) {
                //查询卸车运单明细
                UnloadWaybillDetailEntity wayBillEntityQC = new UnloadWaybillDetailEntity();
                if (StringUtils.isBlank(scanRecord.getBillNo())) {
                    wayBillEntityQC.setBillNo(UnloadConstants.UNLOAD_MORE_BILL_NO);
                    scanRecord.setBillNo(UnloadConstants.UNLOAD_MORE_BILL_NO);
                } else {
                    wayBillEntityQC.setBillNo(scanRecord.getBillNo());
                }
                wayBillEntityQC.setUnloadTaskId(unloadTask.getId());
                wayBillEntityQC.setWaybillNo(scanRecord.getWayBillNo());
                UnloadWaybillDetailEntity wayBIllEntity = pdaUnloadTaskDao.queryUnloadWayBillDetail(wayBillEntityQC);
                //查询卸车流水号明细
                if (wayBIllEntity != null) {
                    UnloadSerialNoDetailEntity serialNoEntityQC = new UnloadSerialNoDetailEntity();
                    serialNoEntityQC.setSerialNo(scanRecord.getSerialNo());
                    serialNoEntityQC.setUnloadWaybillDetailId(wayBIllEntity.getId());
                    serialNoEntity = pdaUnloadTaskDao.queryUnloadSerialNoEntity(serialNoEntityQC);
                    //scanGoodsQty = wayBIllEntity.getScanGoodsQty();
                }
                //如果已存在扫描流水号
                if (serialNoEntity != null && StringUtils.isNotBlank(serialNoEntity.getId())) {
                    if (TransferPDADictConstants.SCAN_STATE_SCANED.equals(serialNoEntity.getScanStatus())) {
                        scanGoodsQtyDif = -1;
                    }
                    this.newUpdateUnloadWaybillDetailEntity(wayBIllEntity, scanRecord, scanGoodsQtyDif, false, goodsState);
                    serialNoEntity = this.newUpdateUnloadSerialNoDetail(serialNoEntity, serialNoEntity.getScanStatus(), UnloadConstants.UNLOAD_GOODS_STATE_CANCELED, scanRecord.getScanTime());
                    //货物状态不为已取消且操作时间在扫描时间之前，更新流水号
                    boolean beUpdate = false;
                    if (serialNoEntity.getOptTime() == null) {
                        beUpdate = true;
                    } else {
                        if (serialNoEntity.getOptTime().before(scanRecord.getScanTime())) {
                            beUpdate = true;
                        }
                    }
                    if (beUpdate) {
                        if (!UnloadConstants.UNLOAD_GOODS_STATE_CANCELED.equals(serialNoEntity.getGoodsStatus())) {
							/*if(TransferPDADictConstants.SCAN_STATE_SCANED.equals(serialNoEntity.getScanStatus())){
								//scanGoodsQty--;
								scanGoodsQtyDif = -1;
							}
							//wayBIllEntity.setHandOverPieces(handOverPieces);
							if(!wayBIllEntity.getOperationGoodsQty().equals(0)){
								wayBIllEntity = this.updateUnloadWaybillDetailEntity(wayBIllEntity,scanRecord, scanGoodsQtyDif, false);
							}*/
                            if (FossConstants.YES.equals(unloadTask.getBeScanInstock())) {
                                //TODO 还原库存
                                //TODO 还原走货路径
                            }
                            handleType = UnloadConstants.LABEL_HANDLE_DETELE;
                        } else {
                            handleType = UnloadConstants.LABEL_HANDLE_NOTHING;
                        }
                        //serialNoEntity = this.updateUnloadSerialNoDetail(serialNoEntity, serialNoEntity.getScanStatus(), UnloadConstants.UNLOAD_GOODS_STATE_CANCELED, scanRecord.getScanTime());
                    } else {//货物状态为已取消
                        handleType = UnloadConstants.LABEL_HANDLE_NOTHING;
                    }
                } else {//如果不存在扫描流水号记录
                    //如果存在卸车运单明细，扫描件数减去一件，否则插入卸车运单明细
                    if (wayBIllEntity != null) {
                        if (TransferPDADictConstants.SCAN_STATE_SCANED.equals(scanRecord.getScanState())) {
                            scanGoodsQtyDif = -1;
                        }
                        this.newUpdateUnloadWaybillDetailEntity(wayBIllEntity, scanRecord, scanGoodsQtyDif, false, goodsState);
                    } else {
                        wayBIllEntity = this.insertUnloadWayBillDetail(scanRecord, unloadTask, false);
                    }
                    //插入卸车流水号明细
                    serialNoEntity = this.insertUnloadSerialNoDetail(unloadTask, scanRecord, wayBIllEntity.getId(), UnloadConstants.UNLOAD_GOODS_STATE_CANCELED);
                    handleType = UnloadConstants.LABEL_HANDLE_DETELE;
                }
            } else {
                //alfred  2014-12-02  考虑到卸车更新卸车明细sql，容易引起阻塞，特把入库逻辑提到更新卸车明细之前
                if (FossConstants.YES.equals(unloadTask.getBeScanInstock())) {
                    //入库卸车货物
                    InOutStockEntity inOutStockEntity = new InOutStockEntity();
                    inOutStockEntity.setWaybillNO(scanRecord.getWayBillNo());
                    inOutStockEntity.setSerialNO(scanRecord.getSerialNo());
                    //若交接单/配载单的到达部门不为空，则为到达部门，否则为卸车部门
                    if (StringUtil.isNotBlank(scanRecord.getDestOrgCode())) {
                        //判定货物卸车部门是否等于提货网点--分部卸车入库需求      建立任务部门编码UnloadOrgCode
                        if (StringUtil.equals(scanRecord.getReachOrgCode(), unloadTask.getUnloadOrgCode())) {
                            inOutStockEntity.setOrgCode(unloadTask.getUnloadOrgCode());
                        } else {
                            //到达部门编号 DestOrgCode
                            inOutStockEntity.setOrgCode(scanRecord.getDestOrgCode());
                        }
                    } else {
                        inOutStockEntity.setOrgCode(unloadTask.getUnloadOrgCode());
                    }
                    LoaderParticipationEntity loaderParticipation = getLoaderParticipationByTaskId(unloadTask.getId());
                    inOutStockEntity.setInOutStockType(StockConstants.UNLOAD_GOODS_IN_STOCK_TYPE);
                    inOutStockEntity.setOperatorCode(loaderParticipation.getLoaderCode());
                    inOutStockEntity.setOperatorName(loaderParticipation.getLoaderName());
                    //PDA-2167 入库失败，抛出异常，pda端出现异常信息，现不捕获异常由pda捕获
                    //try{
                    this.unloadInStock(inOutStockEntity);
                    //}catch(Exception e){
                    //logger.error("卸车入库失败"+scanRecord.getType()+" "+scanRecord.getTaskNo()+" "+scanRecord.getBillNo()+" "+scanRecord.getWayBillNo()+" "+scanRecord.getSerialNo());
                    //}
                }

                if (UnloadConstants.UNLOAD_GOODS_STATE_MORE.equals(scanRecord.getType()) || UnloadConstants.UNLOAD_MORE_BILL_NO.equals(scanRecord.getBillNo())) {
                    if (!beExistsInPickUpBill) {
                        //查询上一部门是否存在未完成待办事项
                        StockEntity stock = stockService.queryUniqueStock(scanRecord.getWayBillNo(), scanRecord.getSerialNo());
                        if (stock != null && StringUtils.isNotBlank(stock.getOrgCode())) {
                            List<String> todoList = waybillRfcService.queryTodoWhenDumpTruck(scanRecord.getWayBillNo(), scanRecord.getSerialNo(), unloadTask.getUnloadOrgCode(), stock.getOrgCode());
                            if (CollectionUtils.isNotEmpty(todoList)) {
                                hasTodoList = true;
                            }
                        }
                        //默认为多货-异地夹带
                        goodsState = UnloadConstants.UNLOAD_GOODS_STATE_MORE_ALLOPATRY_ENTRAINED;
                        //查询货物走货路径
                        FeedbackDto feedbackDto = calculateTransportPathService.getNextOrgAndTime(scanRecord.getWayBillNo(),
                                scanRecord.getSerialNo(), unloadTask.getUnloadOrgCode());
                        if (feedbackDto != null && feedbackDto.getResult() != TransportPathConstants.STATUS_WRONG) {
                            //若装车部门在货物走货路径上，则为多货-夹带
                            goodsState = UnloadConstants.UNLOAD_GOODS_STATE_MORE_ENTRAINED;
                        }
                        scanRecord.setBillNo(UnloadConstants.UNLOAD_MORE_BILL_NO);
                    }
                }
                //查询卸车运单明细
                UnloadWaybillDetailEntity wayBillEntityQC = new UnloadWaybillDetailEntity();
                wayBillEntityQC.setBillNo(scanRecord.getBillNo());
                wayBillEntityQC.setUnloadTaskId(unloadTask.getId());
                wayBillEntityQC.setWaybillNo(scanRecord.getWayBillNo());
                UnloadWaybillDetailEntity wayBIllEntity = pdaUnloadTaskDao.queryUnloadWayBillDetail(wayBillEntityQC);

                //查询卸车流水号明细
                if (wayBIllEntity != null) {
                    wayBIllEntity.setHandOverPieces(scanRecord.getHandOverQty());
                    UnloadSerialNoDetailEntity serialNoEntityQC = new UnloadSerialNoDetailEntity();
                    serialNoEntityQC.setSerialNo(scanRecord.getSerialNo());
                    serialNoEntityQC.setUnloadWaybillDetailId(wayBIllEntity.getId());
                    serialNoEntity = pdaUnloadTaskDao.queryUnloadSerialNoEntity(serialNoEntityQC);
                    //scanGoodsQty = wayBIllEntity.getScanGoodsQty();
                }
                //如果已存在扫描记录
                if (serialNoEntity != null && StringUtils.isNotBlank(serialNoEntity.getId())) {
                    if (TransferPDADictConstants.SCAN_STATE_SCANED.equals(scanRecord.getScanState())) {
                        scanGoodsQtyDif = 1;
                    }
                    this.newUpdateUnloadWaybillDetailEntity(wayBIllEntity, scanRecord, scanGoodsQtyDif, true, goodsState);
                    serialNoEntity = this.newUpdateUnloadSerialNoDetail(serialNoEntity, scanRecord.getScanState(), goodsState, scanRecord.getScanTime());

                    //如果扫描时间在操作时间之后
                    boolean beUpdate = false;
                    if (serialNoEntity.getOptTime() == null) {
                        beUpdate = true;
                    } else {
                        if (serialNoEntity.getOptTime().before(scanRecord.getScanTime())) {
                            beUpdate = true;
                        }
                    }
                    if (beUpdate) {
                        //如果已存在状态为已取消
                        if (UnloadConstants.UNLOAD_GOODS_STATE_CANCELED.equals(serialNoEntity.getGoodsStatus())) {
                            handleType = UnloadConstants.LABEL_HANDLE_ADD;
							/*if(TransferPDADictConstants.SCAN_STATE_SCANED.equals(scanRecord.getScanState())){
								//scanGoodsQty++;
								scanGoodsQtyDif = 1;
							}
							wayBIllEntity = this.updateUnloadWaybillDetailEntity(wayBIllEntity,scanRecord, scanGoodsQtyDif, true);*/
                        } else {
                            handleType = UnloadConstants.LABEL_HANDLE_NOTHING;
                        }
                        //serialNoEntity = this.updateUnloadSerialNoDetail(serialNoEntity, scanRecord.getScanState(), goodsState, scanRecord.getScanTime());
                    } else {
                        handleType = UnloadConstants.LABEL_HANDLE_NOTHING;
                    }
                } else {//如果不存在扫描记录
                    //如果存在卸车运单明细，则更新
                    if (wayBIllEntity != null && StringUtils.isNotBlank(wayBIllEntity.getId())) {
                        if (TransferPDADictConstants.SCAN_STATE_SCANED.equals(scanRecord.getScanState())) {
                            scanGoodsQtyDif = 1;
                        }
                        this.newUpdateUnloadWaybillDetailEntity(wayBIllEntity, scanRecord, scanGoodsQtyDif, true, goodsState);
                    } else {
                        //如果不存在卸车运单明细，则新增
                        wayBIllEntity = this.insertUnloadWayBillDetail(scanRecord, unloadTask, true);
                    }
                    //插入卸车流水号明细
                    serialNoEntity = this.insertUnloadSerialNoDetail(unloadTask, scanRecord, wayBIllEntity.getId(), goodsState);
                    handleType = UnloadConstants.LABEL_HANDLE_ADD;
                }

            }
            // 处理重贴标签
            this.handleLabeledGoods(unloadTask, scanRecord, handleType);
        } else {
            logger.error("卸车扫描结束" + scanRecord.getType() + " " + scanRecord.getTaskNo() + " " + scanRecord.getBillNo() + " " + scanRecord.getWayBillNo() + " " + scanRecord.getSerialNo());
            throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_INVALID_TASK_MESSAGECODE);
        }
        if (UnloadConstants.UNLOAD_GOODS_STATE_MORE_ALLOPATRY_ENTRAINED.equals(serialNoEntity.getGoodsStatus())) {
            if (hasTodoList) {
                logger.error("卸车扫描结束" + scanRecord.getType() + " " + scanRecord.getTaskNo() + " " + scanRecord.getBillNo() + " " + scanRecord.getWayBillNo() + " " + scanRecord.getSerialNo());
                return TransferPDADictConstants.UNLOAD_SCAN_RETURN_SUCCESS_ALLOPATRY_ENTRAINED_HAS_TODOLIST;
            }
            logger.error("卸车扫描结束" + scanRecord.getType() + " " + scanRecord.getTaskNo() + " " + scanRecord.getBillNo() + " " + scanRecord.getWayBillNo() + " " + scanRecord.getSerialNo());
            return TransferPDADictConstants.UNLOAD_SCAN_RETURN_SUCCESS_ALLOPATRY_ENTRAINED_NO_TODOLIST;
        }
        if (UnloadConstants.UNLOAD_GOODS_STATE_MORE_ENTRAINED.equals(serialNoEntity.getGoodsStatus())) {
            if (hasTodoList) {
                logger.error("卸车扫描结束" + scanRecord.getType() + " " + scanRecord.getTaskNo() + " " + scanRecord.getBillNo() + " " + scanRecord.getWayBillNo() + " " + scanRecord.getSerialNo());
                return TransferPDADictConstants.UNLOAD_SCAN_RETURN_SUCCESS_ENTRAINED_HAS_TODOLIST;
            }
            logger.error("卸车扫描结束" + scanRecord.getType() + " " + scanRecord.getTaskNo() + " " + scanRecord.getBillNo() + " " + scanRecord.getWayBillNo() + " " + scanRecord.getSerialNo());
            return TransferPDADictConstants.UNLOAD_SCAN_RETURN_SUCCESS_ENTRAINED_NO_TODOLIST;
        }
        logger.error("卸车扫描结束" + scanRecord.getType() + " " + scanRecord.getTaskNo() + " " + scanRecord.getBillNo() + " " + scanRecord.getWayBillNo() + " " + scanRecord.getSerialNo());
        return TransferPDADictConstants.SUCCESS;
    }

    /**
     * @Title: getLoaderParticipationByTaskId
     * @Description: 根据卸车任务ID获取卸车任务创建人
     * @param taskId
     * @return
     * @return LoaderParticipationEntity    返回类型
     * getLoaderParticipationByTaskId
     * @author: ibm-zhangyixin
     * @throws
     * Date:2013-7-22下午5:10:13
     */
    private LoaderParticipationEntity getLoaderParticipationByTaskId(String taskId) {
        return pdaUnloadTaskDao.getLoaderParticipationByTaskId(taskId);
    }

    /**
     * 卸车扫描.
     *
     *
     *
     *
     * @param scanRecord the scan record
     * @return the string
     * @author dp-duyi
     * @date 2012-12-20 下午2:30:26
     * @see com.deppon.foss.module.transfer.pda.api.server.service.IPDAUnloadTaskService#unloadBillScan(com.deppon.foss.module.transfer.pda.api.shared.dto.UnloadScanDetailDto)
     */
    @Override
    public String unloadBillScan(UnloadScanDetailDto scanRecord) {
		/*MutexElement mutex = new MutexElement(scanRecord.getWayBillNo(),"LOAD_SCAN",MutexElementType.PDA_WAYBILL_NO);
		businessLockService.lock(mutex, LoadConstants.PDA_SCAN_OUTTIME);*/
        logger.error("unload scan begin:" + scanRecord.getTaskNo() + " " + scanRecord.getWayBillNo() + " " + scanRecord.getSerialNo());
        String result;
        try {
            if (StringUtils.equals(FossConstants.YES, scanRecord.getIsPackageScan())) {
                result = pdaUnloadService.unlockunLoadPackageScan(scanRecord);
            } else {
                if (UnloadConstants.WAYBILL_STATUS_NO.equals(scanRecord.getWayBillStatus())) {
                    /**zwd 200968  快递集中卸车*/
                    result = pdaUnloadService.unlockunLoadScanWaybillStatus(scanRecord);
                } else {
                    result = pdaUnloadService.unlockunLoadScan(scanRecord);
                }

            }
        } catch (TfrBusinessException e) {
			/*businessLockService.unlock(mutex);*/
            logger.error("unload scan exception:" + scanRecord.getTaskNo() + " " + scanRecord.getWayBillNo() + " " + scanRecord.getSerialNo(), e);
            throw (e);
        }
		/*businessLockService.unlock(mutex);*/
        logger.error("unload scan end:" + scanRecord.getTaskNo() + " " + scanRecord.getWayBillNo() + " " + scanRecord.getSerialNo());
        return result;
    }

    //插入卸车运单明细

    /**
     * Insert unload way bill detail.
     *
     * @param scanRecord the scan record
     * @param unloadTask the unload task
     * @param beUnloaded the be unloaded
     * @return the unload waybill detail entity
     */
    private UnloadWaybillDetailEntity insertUnloadWayBillDetail(UnloadScanDetailDto scanRecord, UnloadTaskEntity unloadTask, boolean beUnloaded) {
        UnloadWaybillDetailEntity wayBIllEntity = new UnloadWaybillDetailEntity();
        wayBIllEntity.setId(UUIDUtils.getUUID());
        wayBIllEntity.setBillNo(scanRecord.getBillNo());
        wayBIllEntity.setDestOrgCode(scanRecord.getDestOrgCode());
        wayBIllEntity.setOrigOrgCode(scanRecord.getOrigOrgCode());
        wayBIllEntity.setTaskBeginTime(unloadTask.getUnloadStartTime());
        wayBIllEntity.setUnloadOrgCode(unloadTask.getUnloadOrgCode());
        wayBIllEntity.setUnloadTaskId(unloadTask.getId());
        //200968 zwd 运单生效状态 - YES NO 2015.3.27
        //wayBIllEntity.setWayBillStatus(scanRecord.getWayBillStatus());

        if (UnloadConstants.UNLOAD_MORE_BILL_NO.equals(scanRecord.getBillNo()) || UnloadConstants.UNLOAD_GOODS_STATE_MORE.equals(scanRecord.getType())) {
            wayBIllEntity.setHandOverPieces(0);
        } else {
            wayBIllEntity.setHandOverPieces(scanRecord.getHandOverQty());
        }
        wayBIllEntity.setWaybillNo(scanRecord.getWayBillNo());

        BigDecimal volume = BigDecimal.ZERO;
        BigDecimal weight = BigDecimal.ZERO;

        //对于多货运单会没有很多信息，这里要查询出来
        if (!UnloadConstants.UNLOAD_GOODS_STATE_NORMAL.equals(scanRecord.getType()) && StringUtils.isBlank(scanRecord.getGoodsName())) {
            WaybillEntity wayBill = waybillManagerService.queryWaybillBasicByNo(scanRecord.getWayBillNo());
            if (wayBill != null) {
                wayBIllEntity.setGoodsName(wayBill.getGoodsName());
                wayBIllEntity.setPack(wayBill.getGoodsPackage());
                ProductEntity product = productService4Dubbo.getProductByCache(wayBill.getProductCode(), null);
                if (product != null) {
                    wayBIllEntity.setTransportType(product.getName());
                }
                try {
                    volume = wayBill.getGoodsVolumeTotal().divide(BigDecimal.valueOf(wayBill.getGoodsQtyTotal())).setScale(ConstantsNumberSonar.SONAR_NUMBER_3, BigDecimal.ROUND_HALF_UP);
                    weight = wayBill.getGoodsWeightTotal().divide(BigDecimal.valueOf(wayBill.getGoodsQtyTotal())).setScale(ConstantsNumberSonar.SONAR_NUMBER_3, BigDecimal.ROUND_HALF_UP);
                } catch (Exception e) {
                    volume = BigDecimal.ZERO;
                    weight = BigDecimal.ZERO;
                }
            }
        } else {
            wayBIllEntity.setGoodsName(scanRecord.getGoodsName());
            wayBIllEntity.setPack(scanRecord.getPack());
            wayBIllEntity.setTransportType(scanRecord.getTransportType());
            try {
                volume = BigDecimal.valueOf(scanRecord.getVolume());
                weight = BigDecimal.valueOf(scanRecord.getWeight());
            } catch (Exception e) {
                volume = BigDecimal.ZERO;
                weight = BigDecimal.ZERO;
            }
        }

        if (beUnloaded) {
            wayBIllEntity.setOperationGoodsQty(1);
            if (TransferPDADictConstants.SCAN_STATE_SCANED.equals(scanRecord.getScanState())) {
                wayBIllEntity.setScanGoodsQty(1);
            } else {
                wayBIllEntity.setScanGoodsQty(0);
            }
            wayBIllEntity.setUnloadVolumeTotal(volume);
            wayBIllEntity.setUnloadWeightTotal(weight);
        } else {
            wayBIllEntity.setOperationGoodsQty(-1);
            if (TransferPDADictConstants.SCAN_STATE_SCANED.equals(scanRecord.getScanState())) {
                wayBIllEntity.setScanGoodsQty(-1);
            } else {
                wayBIllEntity.setScanGoodsQty(0);
            }
            wayBIllEntity.setUnloadVolumeTotal(BigDecimal.ZERO.subtract(volume));
            wayBIllEntity.setUnloadWeightTotal(BigDecimal.ZERO.subtract(weight));
        }
        try {
            pdaUnloadTaskDao.insertUnloadWayBillEntity(wayBIllEntity);
        } catch (org.springframework.dao.DuplicateKeyException e) {
            UnloadWaybillDetailEntity wayBIllEntityId = pdaUnloadTaskDao.queryUnloadWayBillDetail(wayBIllEntity);
            if (wayBIllEntityId != null) {
                wayBIllEntity.setId(wayBIllEntityId.getId());
                pdaUnloadTaskDao.updateUnloadWayBillEntity(wayBIllEntity);
            } else {
                throw e;
            }
        }
        return wayBIllEntity;
    }
    //插入扫描流水号明细

    /**
     * Insert unload serial no detail.
     *
     *
     *
     *
     *
     * @param unloadTask the unload task
     * @param scanRecord the scan record
     * @param wayBillDetailId the way bill detail id
     * @param goodsState the goods state
     * @return the unload serial no detail entity
     */
    private UnloadSerialNoDetailEntity insertUnloadSerialNoDetail(UnloadTaskEntity unloadTask, UnloadScanDetailDto scanRecord, String wayBillDetailId, String goodsState) {
        //卸车任务流水号明细实体
        UnloadSerialNoDetailEntity seialNoDetail = new UnloadSerialNoDetailEntity();
        //设置id
        seialNoDetail.setId(UUIDUtils.getUUID());
        //
        seialNoDetail.setDeviceNo(scanRecord.getDeviceNo());
        //
        seialNoDetail.setGoodsStatus(goodsState);
        //
        seialNoDetail.setOptTime(scanRecord.getScanTime());
        //
        seialNoDetail.setScanStatus(scanRecord.getScanState());
        //
        seialNoDetail.setSerialNo(scanRecord.getSerialNo());
        //
        seialNoDetail.setUnloadWaybillDetailId(wayBillDetailId);
        //
        seialNoDetail.setCreateDate(new Date());
        //
        seialNoDetail.setTaskCreateTime(unloadTask.getUnloadStartTime());
        //
        seialNoDetail.setCreateOrgCode(unloadTask.getUnloadOrgCode());
        //插入扫描流水号
        pdaUnloadTaskDao.insertUnloadSerialNoEntity(seialNoDetail);
        return seialNoDetail;
    }

    /**
     * 重写更新卸车运单明细方法，后台校验数据是否进行更新
     *
     *
     *
     *
     * @param wayBIllEntity the way b ill entity
     * @param scanRecord the scan record
     * @param scanGoodsQty the scan goods qty
     * @param beUnloaded the be unloaded
     * @return the unload waybill detail entity
     */
    private void newUpdateUnloadWaybillDetailEntity(UnloadWaybillDetailEntity wayBIllEntity, UnloadScanDetailDto scanRecord, int scanGoodsQty, boolean beUnloaded, String goodsState) {
        //重量
        double weight = wayBIllEntity.getUnloadWeightTotal().doubleValue();
        //体积
        double volume = wayBIllEntity.getUnloadVolumeTotal().doubleValue();
        //数量
        int optGoodsQty = wayBIllEntity.getOperationGoodsQty();

        int optGoodsQtyDif = 0;
        if (beUnloaded) {
            if (optGoodsQty > 0) {
                weight = weight / optGoodsQty;
                volume = volume / optGoodsQty;
            } else {
                weight = scanRecord.getWeight();
                volume = scanRecord.getVolume();
            }
            optGoodsQtyDif = 1;
        } else {
            if (optGoodsQty > 0) {
                weight = -weight / optGoodsQty;
                volume = -volume / optGoodsQty;
            } else {
                weight = -scanRecord.getWeight();
                volume = -scanRecord.getVolume();
            }
            optGoodsQtyDif = -1;
        }
        try {
            wayBIllEntity.setUnloadVolumeTotal(BigDecimal.valueOf(volume));
            wayBIllEntity.setUnloadWeightTotal(BigDecimal.valueOf(weight));
        } catch (Exception e) {
            wayBIllEntity.setUnloadVolumeTotal(BigDecimal.ZERO);
            wayBIllEntity.setUnloadWeightTotal(BigDecimal.ZERO);
        }
        wayBIllEntity.setOperationGoodsQty(optGoodsQtyDif);
        wayBIllEntity.setScanGoodsQty(scanGoodsQty);
        if (UnloadConstants.UNLOAD_MORE_BILL_NO.equals(wayBIllEntity.getBillNo())) {
            wayBIllEntity.setHandOverPieces(0);
        } else {
            wayBIllEntity.setHandOverPieces(scanRecord.getHandOverQty());
        }
        wayBIllEntity.setHandOverPieces(scanRecord.getHandOverQty());
        pdaUnloadTaskDao.newUpdateUnloadWayBillEntity(wayBIllEntity, scanRecord.getSerialNo(), scanRecord.getScanTime(), goodsState);
    }

    //更新卸车流水号明细

    /**
     * Update unload serial no detail.
     *
     *
     *
     *
     *
     *
     * @param seialNoDetail the seial no detail
     * @param scanState the scan state
     * @param goodsState the goods state
     * @param scanTime the scan time
     * @return the unload serial no detail entity
     */
    private UnloadSerialNoDetailEntity updateUnloadSerialNoDetail(UnloadSerialNoDetailEntity seialNoDetail, String scanState, String goodsState, Date scanTime) {
        if (StringUtils.isNotBlank(scanState)) {
            seialNoDetail.setScanStatus(scanState);
        } else {
            seialNoDetail.setScanStatus(UnloadConstants.UNLOAD_TASK_SCAN_STATUS_NA);
        }
        seialNoDetail.setGoodsStatus(goodsState);
        seialNoDetail.setOptTime(scanTime);

        pdaUnloadTaskDao.updateUnloadSerialNoEntity(seialNoDetail);
        return seialNoDetail;
    }

    /**
     * 重写更新卸车流水号状态方法
     *
     *
     *
     *
     *
     *
     * @param seialNoDetail the seial no detail
     * @param scanState the scan state
     * @param goodsState the goods state
     * @param scanTime the scan time
     * @return the unload serial no detail entity
     */
    private UnloadSerialNoDetailEntity newUpdateUnloadSerialNoDetail(UnloadSerialNoDetailEntity seialNoDetail, String scanState, String goodsState, Date scanTime) {
        if (StringUtils.isNotBlank(scanState)) {
            seialNoDetail.setScanStatus(scanState);
        } else {
            seialNoDetail.setScanStatus(UnloadConstants.UNLOAD_TASK_SCAN_STATUS_NA);
        }
        seialNoDetail.setGoodsStatus(goodsState);
        seialNoDetail.setOptTime(scanTime);

        pdaUnloadTaskDao.newUpdateUnloadSerialNoEntity(seialNoDetail);
        return seialNoDetail;
    }
    //生成少货

    /**
     * Generate lack goods.
     *
     *
     *
     * @param unloadTask the unload task
     * @param unloadList the unload list
     * @param scanList the scan list
     */
    private boolean generateLackGoods(UnloadTaskEntity unloadTask, List<UnloadGoodsSerialDetailDto> unloadList, List<UnloadSerialNoDetailDto> scanList) {
        /******************************************生成少货Begin*************************************************/
        UnloadSerialNoDetailEntity seialNoDetail;
        List<UnloadSerialNoDetailEntity> lackGoodsList = new ArrayList<UnloadSerialNoDetailEntity>();
        UnloadWaybillDetailEntity wayBillEntityQC;
        UnloadWaybillDetailEntity wayBIllEntity;
        List<UnloadWaybillDetailEntity> lackWayBillList = new ArrayList<UnloadWaybillDetailEntity>();
        boolean beLack = true;
        for (UnloadGoodsSerialDetailDto unloadGoods : unloadList) {
            beLack = true;
            if (CollectionUtils.isNotEmpty(scanList)) {
                for (UnloadSerialNoDetailDto scanGoods : scanList) {
                    //如果存在扫描记录
                    if (unloadGoods.getWayBillNo().equals(scanGoods.getWayBillNo()) && unloadGoods.getBillNo().equals(scanGoods.getBillNo())) {
                        //接送货流水号可能为空，如果流水号为空，则直接插入卸车运单明细，不插入流水号明细
                        if (StringUtils.isBlank(unloadGoods.getSerialNo())) {
                            wayBIllEntity = new UnloadWaybillDetailEntity();
                            WaybillEntity wayBill = waybillManagerService.queryWaybillBasicByNo(unloadGoods.getWayBillNo());
                            wayBIllEntity.setId(UUIDUtils.getUUID());
                            wayBIllEntity.setBillNo(unloadGoods.getBillNo());
                            //查询交接单，查看是否存在包号
                            if (StringUtils.isNotBlank(unloadGoods.getHandoverNo())) {
                                if (unloadGoods.getHandoverNo().startsWith("B") || unloadGoods.getHandoverNo().startsWith("KYB")) {
                                    if (unloadGoods.getHandoverNo().indexOf("-") == -1) {
                                        wayBIllEntity.setPackageNo(unloadGoods.getHandoverNo());
                                    } else {
                                        wayBIllEntity.setPackageNo(unloadGoods.getHandoverNo().substring(0, unloadGoods.getHandoverNo().indexOf("-")));
                                    }
                                }
                            }
                            wayBIllEntity.setDestOrgCode(unloadGoods.getDestOrgCode());
                            wayBIllEntity.setOrigOrgCode(unloadGoods.getOrigOrgCode());
                            wayBIllEntity.setPack(wayBill.getGoodsPackage());
                            wayBIllEntity.setGoodsName(wayBill.getGoodsName());
                            ProductEntity product = productService4Dubbo.getProductByCache(wayBill.getProductCode(), null);
                            if (LOGGER.isInfoEnabled()){
                                LOGGER.info("PDAUnloadTaskService调用计价dubbo接口成功，product = {}" ,product);
                            }
                            if (product != null) {
                                wayBIllEntity.setTransportType(product.getName());
                            }
                            wayBIllEntity.setTaskBeginTime(unloadTask.getUnloadStartTime());
                            wayBIllEntity.setCreateDate(new Date());
                            wayBIllEntity.setUnloadOrgCode(unloadTask.getUnloadOrgCode());
                            wayBIllEntity.setUnloadTaskId(unloadTask.getId());
                            wayBIllEntity.setWaybillNo(unloadGoods.getWayBillNo());
                            wayBIllEntity.setOperationGoodsQty(0);
                            wayBIllEntity.setScanGoodsQty(0);
                            wayBIllEntity.setUnloadVolumeTotal(BigDecimal.ZERO);
                            wayBIllEntity.setUnloadWeightTotal(BigDecimal.ZERO);
                            wayBIllEntity.setHandOverPieces(unloadGoods.getHandOverQty());
                            lackWayBillList.add(wayBIllEntity);
                        } else {
                            if (unloadGoods.getSerialNo().equals(scanGoods.getSerialNo())) {
                                beLack = false;
                                //如果扫描记录的状态为已取消，则更新扫描状态为少货
                                if (UnloadConstants.UNLOAD_GOODS_STATE_CANCELED.equals(scanGoods.getGoodsStatus())) {
                                    seialNoDetail = scanGoods;
                                    this.updateUnloadSerialNoDetail(seialNoDetail, UnloadConstants.UNLOAD_TASK_SCAN_STATUS_NA, UnloadConstants.UNLOAD_GOODS_STATE_LACK, scanGoods.getOptTime());
                                }
                            }
                        }
                        //如果卸车的交接件数小于交接件数，则更新卸车运单明细
                        if (scanGoods.getHandOverQty() != unloadGoods.getHandOverQty()) {
                            wayBIllEntity = new UnloadWaybillDetailEntity();
                            wayBIllEntity.setId(scanGoods.getUnloadWaybillDetailId());
                            wayBIllEntity.setOperationGoodsQty(0);
                            wayBIllEntity.setScanGoodsQty(0);
                            wayBIllEntity.setHandOverPieces(unloadGoods.getHandOverQty());
                            wayBIllEntity.setUnloadVolumeTotal(BigDecimal.ZERO);
                            wayBIllEntity.setUnloadWeightTotal(BigDecimal.ZERO);
                            pdaUnloadTaskDao.updateUnloadWayBillEntity(wayBIllEntity);
                        }
                    }
                }
            }
            if (beLack) {
                boolean beExist = false;
                String wayBillDetailId = "";
                //查询少货运单是否已经在少货列表中
                for (UnloadWaybillDetailEntity temp : lackWayBillList) {
                    if (unloadGoods.getBillNo().equals(temp.getBillNo()) && unloadGoods.getWayBillNo().equals(temp.getWaybillNo())) {
                        beExist = true;
                        wayBIllEntity = temp;
                        wayBillDetailId = temp.getId();
                    }
                }
                if (!beExist) {
                    wayBillEntityQC = new UnloadWaybillDetailEntity();
                    wayBillEntityQC.setBillNo(unloadGoods.getBillNo());
                    wayBillEntityQC.setWaybillNo(unloadGoods.getWayBillNo());
                    wayBillEntityQC.setUnloadTaskId(unloadTask.getId());
                    wayBIllEntity = pdaUnloadTaskDao.queryUnloadWayBillDetail(wayBillEntityQC);
                    //如果已存在卸车运单明细，则什么都不做
                    if (wayBIllEntity != null && StringUtils.isNotBlank(wayBIllEntity.getId())) {
                        wayBillDetailId = wayBIllEntity.getId();
                    } else {//如果不存在卸车运单明细，则新增卸车运单明细
                        wayBIllEntity = new UnloadWaybillDetailEntity();
                        WaybillEntity wayBill = waybillManagerService.queryWaybillBasicByNo(unloadGoods.getWayBillNo());
                        wayBIllEntity.setId(UUIDUtils.getUUID());
                        wayBIllEntity.setBillNo(unloadGoods.getBillNo());
                        //查询交接单，查看是否存在包号
                        if (StringUtils.isNotBlank(unloadGoods.getHandoverNo())) {
                            if (unloadGoods.getHandoverNo().startsWith("B") || unloadGoods.getHandoverNo().startsWith("KYB")) {
                                if (unloadGoods.getHandoverNo().indexOf("-") == -1) {
                                    wayBIllEntity.setPackageNo(unloadGoods.getHandoverNo());
                                } else {
                                    wayBIllEntity.setPackageNo(unloadGoods.getHandoverNo().substring(0, unloadGoods.getHandoverNo().indexOf("-")));
                                }
                            }
                        }
                        wayBIllEntity.setDestOrgCode(unloadGoods.getDestOrgCode());
                        wayBIllEntity.setOrigOrgCode(unloadGoods.getOrigOrgCode());
                        if (wayBill != null) {
                            wayBIllEntity.setPack(wayBill.getGoodsPackage());
                            wayBIllEntity.setGoodsName(wayBill.getGoodsName());
                            ProductEntity product = productService4Dubbo.getProductByCache(wayBill.getProductCode(), null);
                            if (product != null) {
                                wayBIllEntity.setTransportType(product.getName());
                            }
                        }
                        wayBIllEntity.setTaskBeginTime(unloadTask.getUnloadStartTime());
                        wayBIllEntity.setCreateDate(new Date());
                        wayBIllEntity.setUnloadOrgCode(unloadTask.getUnloadOrgCode());
                        wayBIllEntity.setUnloadTaskId(unloadTask.getId());
                        wayBIllEntity.setWaybillNo(unloadGoods.getWayBillNo());
                        wayBIllEntity.setOperationGoodsQty(0);
                        wayBIllEntity.setScanGoodsQty(0);
                        wayBIllEntity.setUnloadVolumeTotal(BigDecimal.ZERO);
                        wayBIllEntity.setUnloadWeightTotal(BigDecimal.ZERO);
                        wayBIllEntity.setHandOverPieces(unloadGoods.getHandOverQty());
                        lackWayBillList.add(wayBIllEntity);
                        wayBillDetailId = wayBIllEntity.getId();
                    }
                }
                seialNoDetail = new UnloadSerialNoDetailEntity();
                seialNoDetail.setCreateOrgCode(unloadTask.getUnloadOrgCode());
                seialNoDetail.setTaskCreateTime(unloadTask.getUnloadStartTime());
                seialNoDetail.setGoodsStatus(UnloadConstants.UNLOAD_GOODS_STATE_LACK);
                seialNoDetail.setId(UUIDUtils.getUUID());
                seialNoDetail.setScanStatus(UnloadConstants.UNLOAD_TASK_SCAN_STATUS_NA);
                seialNoDetail.setCreateDate(new Date());
                seialNoDetail.setSerialNo(unloadGoods.getSerialNo());
                seialNoDetail.setUnloadWaybillDetailId(wayBillDetailId);
                lackGoodsList.add(seialNoDetail);
            }
        }
        //批量插入少货运单
        if (CollectionUtils.isNotEmpty(lackWayBillList)) {
            unloadTaskDao.addUnloadTaskWaybillDetailList(lackWayBillList);
        }
        //批量插入少货流水号
        if (CollectionUtils.isNotEmpty(lackGoodsList)) {
            for (UnloadSerialNoDetailEntity lackGoods : lackGoodsList) {
                UnloadWaybillDetailEntity lackWayBillEntity = new UnloadWaybillDetailEntity();
                try {
                    lackWayBillEntity.setId(lackGoods.getUnloadWaybillDetailId());
                    lackWayBillEntity = pdaUnloadTaskDao.queryUnloadWayBillDetail(lackWayBillEntity);
                    waybillRfcService.resetTodoWhenLost(lackWayBillEntity.getWaybillNo(),
                            lackGoods.getSerialNo(), unloadTask.getUnloadOrgCode());
                } catch (Exception e) {
                    logger.error("代办重置异常" + lackWayBillEntity.getWaybillNo());
                }

            }
            unloadTaskDao.addUnloadTaskSerialNoDetailList(lackGoodsList);
        }
        return beLack;
/******************************************生成少货End*************************************************/
    }

    /**
     * 查询标签是否有效.
     *
     * @param wayBillNo the way bill no
     * @param serialNo the serial no
     * @return true, if successful
     */
    private boolean beValidLabeledGoods(String wayBillNo, String serialNo) {
        //查询标签是否有效
        Map<String, String> condition = new HashMap<String, String>();
        condition.put("wayBillNo", wayBillNo);
        condition.put("serialNo", serialNo);
        condition.put("active", FossConstants.ACTIVE);
        //查询货签是否有效
        int valideLabeledCount = pdaLoadDao.queryValidLabeledCount(condition);
        //查询货物是否已签收
        int signedQty = pdaLoadDao.queryWayBillSignedState(condition);
        //货签无效或已签收，则货物状态为无效
        if (valideLabeledCount <= 0 || signedQty > 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 处理标签.
     *
     *
     *
     *
     * @param unloadTask the unload task
     * @param scanRecord the scan record
     * @param handleType the handle type
     */
    private void handleLabeledGoods(UnloadTaskEntity unloadTask, UnloadScanDetailDto scanRecord, String handleType) {
        ChangeLabelGoodsEntity changeLabelGoods;
        if (UnloadConstants.LABEL_HANDLE_ADD.equals(handleType)) {
            boolean beAdd = false;
            String changeReason = null;
            //如果手输，则新增更换标签
            if (TransferPDADictConstants.SCAN_STATE_BY_HAND.equals(scanRecord.getScanState())) {
                beAdd = true;
                changeReason = ChangeLabelGoodsConstants.CHANGE_REASON_BY_HAND;
            } else {
                //调用接送货接口判断是否有待办事项
                List<String> todoItems = waybillRfcService.queryTodoWhenLoadTruck(scanRecord.getWayBillNo(), scanRecord.getSerialNo(), unloadTask.getUnloadOrgCode());
                //如果存在代办事项，则新增更换标签
                if (CollectionUtils.isNotEmpty(todoItems)) {
                    //beAdd = true;
                    //changeReason = ChangeLabelGoodsConstants.CHANGE_REASON_BY_MODIFY;
                }
            }
            //新增更换标签
            if (beAdd) {
                changeLabelGoods = new ChangeLabelGoodsEntity();
                changeLabelGoods.setId(UUIDUtils.getUUID());
                //设置 单据编号
                changeLabelGoods.setBillNo(scanRecord.getBillNo());
                //设置 运单号
                changeLabelGoods.setWaybillNo(scanRecord.getWayBillNo());
                //设置 流水号
                changeLabelGoods.setSerialNo(scanRecord.getSerialNo());
                //设置 更换原因
                changeLabelGoods.setChangeReason(changeReason);
                //设置 发现环节
                changeLabelGoods.setDiscoverTache(ChangeLabelGoodsConstants.FIND_SCENE_UNLOAD);
                //设置 发现时间
                changeLabelGoods.setDiscoverTime(scanRecord.getScanTime());
                //设置 处理状态
                changeLabelGoods.setHandleStatus(ChangeLabelGoodsConstants.HANDLESTATUS_UNTREATED);
                //设置 发现部门编号
                changeLabelGoods.setOrgCode(unloadTask.getUnloadOrgCode());
                //设置 发现部门名称
                changeLabelGoods.setOrgName(unloadTask.getUnloadOrgName());
                //插入更换标签
                pdaUnloadTaskDao.insertChangeLabelGoodsEntity(changeLabelGoods);
            }
        } else if (UnloadConstants.LABEL_HANDLE_DETELE.equals(handleType)) {
            //删除更换标签
            changeLabelGoods = new ChangeLabelGoodsEntity();
            //设置 发现部门编号
            changeLabelGoods.setOrgCode(unloadTask.getUnloadOrgCode());
            //设置 发现环节
            changeLabelGoods.setDiscoverTache(ChangeLabelGoodsConstants.FIND_SCENE_UNLOAD);
            //设置 处理状态
            changeLabelGoods.setHandleStatus(ChangeLabelGoodsConstants.HANDLESTATUS_UNTREATED);
            //设置 单据编号
            changeLabelGoods.setBillNo(scanRecord.getBillNo());
            //设置 运单号
            changeLabelGoods.setWaybillNo(scanRecord.getWayBillNo());
            //设置 流水号
            changeLabelGoods.setSerialNo(scanRecord.getSerialNo());
            //删除更换标签
            pdaUnloadTaskDao.deleteChangeLabelGoodsEntity(changeLabelGoods);
        }
    }

    /**
     * 卸车入库
     * @author dp-duyio
     * @date 2013-6-3 上午12:27:57
     * @see com.deppon.foss.module.transfer.unload.api.server.service.IPDAUnloadService#unloadInStock(com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity)
     */
    private void unloadInStock(InOutStockEntity inOutStockEntity) {
        stockService.inStockUnload(inOutStockEntity);
    }

    /**
     *
     * <p>卸车包扫描</p>
     * @author alfred
     * @date 2014-10-29 上午8:34:53
     * @param scanRecord
     * @return
     * @see com.deppon.foss.module.transfer.unload.api.server.service.IPDAUnloadService#unlockunLoadPackageScan(com.deppon.foss.module.transfer.pda.api.shared.dto.UnloadScanDetailDto)
     */
    @Override
    public String unlockunLoadPackageScan(UnloadScanDetailDto scanRecord) {
        logger.error("卸车包扫描开始" + scanRecord.getType() + " " + scanRecord.getTaskNo() + " " + scanRecord.getBillNo() + " " + scanRecord.getWayBillNo() + " " + scanRecord.getSerialNo() + " " + scanRecord.getVolume());
        UnloadTaskEntity unloadTask = pdaUnloadTaskDao.queryUnloadTaskByTaskNo(scanRecord.getTaskNo());
        if (unloadTask != null && UnloadConstants.UNLOAD_TASK_STATE_UNLOADING.equals(unloadTask.getTaskState())) {
            String goodsState = scanRecord.getType();
            //查询扫描包中的运单明细
            List<ExpressPackageDetailEntity> packageDetailList = pdaExpressPackageDao.queryScanPackageDetails(scanRecord.getWayBillNo());
            if (null == packageDetailList || packageDetailList.size() <= 0) {
                throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_INVALID_NOPACKAGEDETAIL_MESSAGECODE);
            }
            if (UnloadConstants.UNLOAD_GOODS_STATE_MORE.equals(scanRecord.getType()) || UnloadConstants.UNLOAD_MORE_BILL_NO.equals(scanRecord.getBillNo())) {
                //默认为多货-异地夹带
                goodsState = UnloadConstants.UNLOAD_GOODS_STATE_MORE_ALLOPATRY_ENTRAINED;
                //默认为多货-异地夹带
                goodsState = UnloadConstants.UNLOAD_GOODS_STATE_MORE_ALLOPATRY_ENTRAINED;
                //查询货物走货路径
                FeedbackDto feedbackDto = calculateTransportPathService.getNextOrgAndTime(packageDetailList.get(0).getWayBillNo(),
                        packageDetailList.get(0).getSerialNo(), unloadTask.getUnloadOrgCode());
                if (feedbackDto != null && feedbackDto.getResult() != TransportPathConstants.STATUS_WRONG) {
                    //若装车部门在货物走货路径上，则为多货-夹带
                    goodsState = UnloadConstants.UNLOAD_GOODS_STATE_MORE_ENTRAINED;
                }
                scanRecord.setBillNo(UnloadConstants.UNLOAD_MORE_BILL_NO);
            }

            //查询该包是否已经扫描
            List<UnloadWaybillDetailEntity> waybillDetails = pdaUnloadTaskDao.queryIsPackageScan(unloadTask.getId(), scanRecord.getWayBillNo());

            if (CollectionUtils.isNotEmpty(waybillDetails)) {
                for (UnloadWaybillDetailEntity waybillDetail : waybillDetails) {
                    UnloadWaybillDetailEntity wayBillEntity = new UnloadWaybillDetailEntity();
                    ExpressPackageDetailEntity entity = pdaUnloadTaskDao.queryPackageDetailsByWaybill(waybillDetail.getWaybillNo(), scanRecord.getWayBillNo());
                    wayBillEntity.setWaybillNo(waybillDetail.getWaybillNo());
                    wayBillEntity.setPackageNo(scanRecord.getWayBillNo());
                    if (UnloadConstants.UNLOAD_GOODS_STATE_CANCELED.equals(scanRecord.getType())) {
                        wayBillEntity.setUnloadVolumeTotal(new BigDecimal(0));
                        wayBillEntity.setUnloadWeightTotal(new BigDecimal(0));
                        wayBillEntity.setScanGoodsQty(0);
                        wayBillEntity.setOperationGoodsQty(0);
                    } else {
                        wayBillEntity.setUnloadVolumeTotal(new BigDecimal(entity.getVolume()));
                        wayBillEntity.setUnloadWeightTotal(new BigDecimal(entity.getWeight()));
                        wayBillEntity.setScanGoodsQty(entity.getGoodsQty());
                        wayBillEntity.setOperationGoodsQty(entity.getGoodsQty());
                    }
                    wayBillEntity.setModifyDate(scanRecord.getScanTime());
                    wayBillEntity.setId(waybillDetail.getId());
                    pdaUnloadTaskDao.updatePackageWayBillEntity(wayBillEntity);


                    UnloadSerialNoDetailEntity serialNoEntity = new UnloadSerialNoDetailEntity();
                    serialNoEntity.setUnloadWaybillDetailId(waybillDetail.getId());
                    if (StringUtils.isNotBlank(scanRecord.getScanState())) {
                        serialNoEntity.setScanStatus(scanRecord.getScanState());
                    } else {
                        serialNoEntity.setScanStatus(UnloadConstants.UNLOAD_TASK_SCAN_STATUS_NA);
                    }
                    serialNoEntity.setGoodsStatus(goodsState);
                    serialNoEntity.setOptTime(scanRecord.getScanTime());

                    pdaUnloadTaskDao.updateUnloadPackageSerialNo(serialNoEntity);
                }
            } else {
                List<ExpressPackageDetailEntity> detailList = pdaUnloadTaskDao.queryScanPackageDetails(scanRecord.getWayBillNo());
                if (CollectionUtils.isNotEmpty(detailList)) {
                    for (ExpressPackageDetailEntity detail : detailList) {
                        //插入运单
                        String wayBillDetailId = insertDetailbypackage(scanRecord, unloadTask, detail);
                        //批量插入流水号
                        insertSerialsbyPackage(scanRecord, unloadTask, goodsState, detail, wayBillDetailId);
                    }
                }
            }
            //扫描入库
            if (!UnloadConstants.UNLOAD_GOODS_STATE_CANCELED.equals(scanRecord.getType())) {
                for (int i = 0; i < packageDetailList.size(); i++) {
                    ExpressPackageDetailEntity packageDetail = packageDetailList.get(i);
                    //查询运单基本属性
                    WaybillEntity wayBill = waybillManagerService.queryWaybillBasicByNo(packageDetail.getWayBillNo());
                    if (null != wayBill) {
                        if (FossConstants.YES.equals(unloadTask.getBeScanInstock())) {
                            //入库卸车货物
                            InOutStockEntity inOutStockEntity = new InOutStockEntity();
                            inOutStockEntity.setWaybillNO(packageDetail.getWayBillNo());
                            inOutStockEntity.setSerialNO(packageDetail.getSerialNo());
                            //若交接单/配载单的到达部门不为空，则为到达部门，否则为卸车部门
                            if (StringUtil.isNotBlank(scanRecord.getDestOrgCode())) {
                                //判定货物卸车部门是否等于提货网点--分部卸车入库需求
                                if (StringUtil.equals(wayBill.getCustomerPickupOrgCode(), unloadTask.getUnloadOrgCode())) {
                                    inOutStockEntity.setOrgCode(unloadTask.getUnloadOrgCode());
                                } else {
                                    inOutStockEntity.setOrgCode(scanRecord.getDestOrgCode());
                                }
                            } else {
                                inOutStockEntity.setOrgCode(unloadTask.getUnloadOrgCode());
                            }
                            LoaderParticipationEntity loaderParticipation = getLoaderParticipationByTaskId(unloadTask.getId());
                            inOutStockEntity.setInOutStockType(StockConstants.UNLOAD_GOODS_IN_STOCK_TYPE);
                            inOutStockEntity.setOperatorCode(loaderParticipation.getLoaderCode());
                            inOutStockEntity.setOperatorName(loaderParticipation.getLoaderName());
                            try {
                                this.unloadInStock(inOutStockEntity);
                            } catch (Exception e) {
                                logger.error("卸车入库失败" + scanRecord.getType() + " " + scanRecord.getTaskNo() + " " +
                                        scanRecord.getBillNo() + " " + packageDetail.getWayBillNo() + " " + packageDetail.getSerialNo());
                            }
                        }
                    }
                }
            }
            // 若扫描的是直达包或者是空运直达包，则更改包中的运单在入库表中是否建包状态
            ExpressPackageEntity packageEntity = pdaExpressPackageDao.queryPackageByNo(scanRecord.getWayBillNo());
            if (null != packageEntity) {
                if ((StringUtils.equals(packageEntity.getExpressPackageType(), "THROUGH_ARRIVE") ||
                        StringUtils.equals(packageEntity.getExpressPackageType(), "AIRTHROUGH_ARRIVE")) &&
                        !StringUtil.equals(packageEntity.getArriveOrgCode(), unloadTask.getUnloadOrgCode())) {
                    for (ExpressPackageDetailEntity packDetail : packageDetailList) {
                        logger.error("更新库存是否包扫描" + scanRecord.getWayBillNo() + packDetail.getWayBillNo() +
                                packDetail.getSerialNo());
                        pdaExpressPackageDao.updateStockIsPackage(packDetail.getWayBillNo(), packDetail.getSerialNo());
                    }
                }
            }
        }

        return TransferPDADictConstants.SUCCESS;
    }

    /**
     *
     * <p>包扫描新增插入流水号</p>
     * @author alfred
     * @date 2014-10-29 下午4:40:34
     * @param scanRecord
     * @param unloadTask
     * @param goodsState
     * @param detail
     * @param wayBillDetailId
     * @see
     */
    private void insertSerialsbyPackage(UnloadScanDetailDto scanRecord,
                                        UnloadTaskEntity unloadTask, String goodsState,
                                        ExpressPackageDetailEntity detail, String wayBillDetailId) {
        List<UnloadSerialNoDetailEntity> entitys = new ArrayList<UnloadSerialNoDetailEntity>();
        List<String> serials = pdaUnloadTaskDao.queryPackageSerialNo(scanRecord.getWayBillNo(), detail.getWayBillNo());
        if (CollectionUtils.isNotEmpty(serials)) {
            for (String serial : serials) {
                //卸车任务流水号明细实体
                UnloadSerialNoDetailEntity seialNoDetail = new UnloadSerialNoDetailEntity();
                //设置id
                seialNoDetail.setId(UUIDUtils.getUUID());
                //
                seialNoDetail.setDeviceNo(scanRecord.getDeviceNo());
                //
                seialNoDetail.setGoodsStatus(goodsState);
                //
                seialNoDetail.setOptTime(scanRecord.getScanTime());
                //
                seialNoDetail.setScanStatus(scanRecord.getScanState());
                //
                seialNoDetail.setSerialNo(serial);
                //
                seialNoDetail.setUnloadWaybillDetailId(wayBillDetailId);
                //
                seialNoDetail.setCreateDate(new Date());
                //
                seialNoDetail.setTaskCreateTime(unloadTask.getUnloadStartTime());
                //
                seialNoDetail.setCreateOrgCode(unloadTask.getUnloadOrgCode());
                entitys.add(seialNoDetail);
            }
        }
        pdaUnloadTaskDao.insertUnloadSerialNos(entitys);
    }

    /**
     *
     * <p>包扫描新增插入运单明细</p>
     * @author alfred
     * @date 2014-10-29 下午4:40:38
     * @param scanRecord
     * @param unloadTask
     * @param detail
     * @return
     * @see
     */
    private String insertDetailbypackage(UnloadScanDetailDto scanRecord,
                                         UnloadTaskEntity unloadTask, ExpressPackageDetailEntity detail) {
        UnloadWaybillDetailEntity wayBIllEntity = new UnloadWaybillDetailEntity();
        String wayBillDetailId = UUIDUtils.getUUID();
        wayBIllEntity.setId(wayBillDetailId);
        wayBIllEntity.setBillNo(scanRecord.getBillNo());
        wayBIllEntity.setDestOrgCode(scanRecord.getDestOrgCode());
        wayBIllEntity.setOrigOrgCode(scanRecord.getOrigOrgCode());
        wayBIllEntity.setTaskBeginTime(unloadTask.getUnloadStartTime());
        wayBIllEntity.setUnloadOrgCode(unloadTask.getUnloadOrgCode());
        wayBIllEntity.setUnloadTaskId(unloadTask.getId());
        if (UnloadConstants.UNLOAD_MORE_BILL_NO.equals(scanRecord.getBillNo()) || UnloadConstants.UNLOAD_GOODS_STATE_MORE.equals(scanRecord.getType())) {
            wayBIllEntity.setHandOverPieces(0);
        } else {
            wayBIllEntity.setHandOverPieces(detail.getGoodsQty());
        }
        wayBIllEntity.setWaybillNo(detail.getWayBillNo());
        wayBIllEntity.setGoodsName(detail.getGoodsName());
        wayBIllEntity.setPack(detail.getPack());
        wayBIllEntity.setTransportType(detail.getTransportTypeName());
        if (UnloadConstants.UNLOAD_GOODS_STATE_CANCELED.equals(scanRecord.getType())) {
            wayBIllEntity.setUnloadVolumeTotal(new BigDecimal(0));
            wayBIllEntity.setUnloadWeightTotal(new BigDecimal(0));
            wayBIllEntity.setScanGoodsQty(0);
            wayBIllEntity.setOperationGoodsQty(0);
        } else {
            wayBIllEntity.setUnloadVolumeTotal(new BigDecimal(detail.getVolume()));
            wayBIllEntity.setUnloadWeightTotal(new BigDecimal(detail.getWeight()));
            wayBIllEntity.setScanGoodsQty(detail.getGoodsQty());
            wayBIllEntity.setOperationGoodsQty(detail.getGoodsQty());
        }
        wayBIllEntity.setPackageNo(scanRecord.getWayBillNo());
        wayBIllEntity.setModifyDate(scanRecord.getScanTime());
        //注掉此行 避免数据库重复插入 218427-foss-hongwy
        //pdaUnloadTaskDao.insertUnloadWayBillEntity(wayBIllEntity);
        try {
            pdaUnloadTaskDao.insertUnloadWayBillEntity(wayBIllEntity);
        } catch (org.springframework.dao.DuplicateKeyException e) {
            UnloadWaybillDetailEntity wayBIllEntityId = pdaUnloadTaskDao.queryUnloadWayBillDetail(wayBIllEntity);
            if (wayBIllEntityId != null) {
                wayBIllEntity.setId(wayBIllEntityId.getId());
                pdaUnloadTaskDao.updatePackageWayBillEntity(wayBIllEntity);
            } else {
                throw e;
            }
        }

        return wayBillDetailId;
    }

    /**
     *
     * <p>刷新包信息</p>
     * @author alfred
     * @date 2014-11-4 上午8:48:50
     * @param taskNo
     * @return
     * @see com.deppon.foss.module.transfer.pda.api.server.service.IPDAUnloadTaskService#refrushUnloadTaskPackage(java.lang.String)
     */
    @Override
    public List<UnloadGoodsDetailDto> refrushUnloadTaskPackage(String taskNo) {
        logger.error("刷新包卸车任务开始:" + taskNo);
        List<UnloadBillDetailDto> unloadBills;
        List<UnloadBillDetailDto> handOvers = new ArrayList<UnloadBillDetailDto>();
        List<UnloadBillDetailDto> assembleBills = new ArrayList<UnloadBillDetailDto>();
        UnloadTaskEntity unloadTaskEntity;
        unloadTaskEntity = pdaUnloadTaskDao.queryUnloadTaskByTaskNo(taskNo);
        if (unloadTaskEntity != null &&
                UnloadConstants.UNLOAD_TASK_STATE_UNLOADING.equals(unloadTaskEntity.getTaskState())) {
            unloadBills = pdaUnloadTaskDao.queryUnloadBillsByTaskId(unloadTaskEntity.getId());

            if (null != unloadBills) {
                for (UnloadBillDetailDto unloadBill : unloadBills) {
                    if (UnloadConstants.BILL_TYPE_HANDOVER.equals(unloadBill.getBillType())) {
                        handOvers.add(unloadBill);
                    } else if (UnloadConstants.BILL_TYPE_VEHICLEASSEMBLE.equals(unloadBill.getBillType())) {
                        assembleBills.add(unloadBill);
                    }
                }
            }
            //卸车包清单
            List<UnloadGoodsSerialDetailDto> unloadGoodsDetail = new ArrayList<UnloadGoodsSerialDetailDto>();

            List<UnloadGoodsSerialDetailDto> handOverGoodsDetail;
            List<UnloadGoodsSerialDetailDto> assembleGoodsDetail;
            //交接单包
            if (CollectionUtils.isNotEmpty(handOvers)) {
                handOverGoodsDetail = pdaUnloadTaskDao.queryPackageHandUnloadDetail(handOvers);
                if (CollectionUtils.isNotEmpty(handOverGoodsDetail)) {
                    unloadGoodsDetail.addAll(handOverGoodsDetail);
                }
            }

            //配载单中包
            if (CollectionUtils.isNotEmpty(assembleBills)) {
                assembleGoodsDetail = pdaUnloadTaskDao.queryPackageAssembleDetail(assembleBills);
                if (CollectionUtils.isNotEmpty(assembleGoodsDetail)) {
                    unloadGoodsDetail.addAll(assembleGoodsDetail);
                }
            }

            //刷新强卸包信息
            List<UnloadGoodsSerialDetailDto> moreGoodsDetail = pdaUnloadTaskDao.queryUnloadMorePackageGoods(taskNo);

            if (CollectionUtils.isNotEmpty(moreGoodsDetail)) {
                unloadGoodsDetail.addAll(moreGoodsDetail);
            }

            List<UnloadGoodsDetailDto> details = new ArrayList<UnloadGoodsDetailDto>();
            UnloadGoodsDetailDto detailTemp = new UnloadGoodsDetailDto();

            LoaderParticipationEntity loaderQC = new LoaderParticipationEntity();
            loaderQC.setTaskId(unloadTaskEntity.getId());
            loaderQC.setBeCreator(FossConstants.YES);
            List<LoaderParticipationEntity> loaders = pdaUnloadTaskDao.queryLoaderByTaskId(loaderQC);
            LoaderParticipationEntity creator = new LoaderParticipationEntity();
            if (CollectionUtils.isNotEmpty(loaders)) {
                creator = loaders.get(0);
            }

            //刷新交接单包明细
            //List<UnloadGoodsSerialDetailDto> handOverGoodsDetail = pdaUnloadTaskDao.queryPackageUnloadDetail(unloadPackageBills) ;
            if (null != unloadGoodsDetail && unloadGoodsDetail.size() > 0) {
                for (UnloadGoodsSerialDetailDto goods : unloadGoodsDetail) {
                    detailTemp = new UnloadGoodsDetailDto();
                    goods.setTaskNo(taskNo);
                    if (StringUtils.isNotBlank(creator.getLoaderCode())) {
                        goods.setCreatorCode(creator.getLoaderCode());
                    }
                    if (StringUtils.isNotBlank(creator.getLoaderName())) {
                        goods.setCreatorName(creator.getLoaderName());
                    }
                    detailTemp.setBeContraband(goods.getBeContraband());
                    detailTemp.setBillNo(goods.getBillNo());
                    detailTemp.setDestOrgCode(goods.getDestOrgCode());
                    //
                    detailTemp.setOrigOrgCode(goods.getOrigOrgCode());
                    //Sets the 到达部门编码
                    detailTemp.setReachOrgCode(goods.getReachOrgCode());
                    //Sets the 到达部门名称.
                    detailTemp.setReachOrgName(goods.getReachOrgName());
                    //Sets the 收货部门名称.
                    detailTemp.setReceiveOrgName(goods.getReceiveOrgName());
                    //Sets the 收货部门编码.
                    detailTemp.setReceiveOrgCode(goods.getReceiveOrgCode());
                    //Sets the 任务编号.
                    detailTemp.setTaskNo(taskNo);
                    //Sets the 运输性质.
                    detailTemp.setTransportType(goods.getTransportType());
                    //Sets the 体积
                    detailTemp.setVolume(goods.getVolume());

                    /**** 若为商务专递包 ，包号不显示“KY”，运输性质 改为“空运”   263072  2015-10-17 08:49:11******/
                    if (goods.getWayBillNo().startsWith("KYB")) {
                        String billNo = goods.getWayBillNo().substring(2);
                        detailTemp.setTransportType("空运");
                        if (billNo.indexOf("-") == -1) {
                            //Sets the 包号.
                            detailTemp.setWayBillNo(billNo);
                        } else {
                            //Sets the 包号.
                            detailTemp.setWayBillNo(billNo.substring(0, billNo.indexOf("-")));
                        }
                    }
                    //过滤包交接单，交接单中含有类似-1的取-之前的数据，为包号
                    else if (goods.getWayBillNo().startsWith("B")) {
                        if (goods.getWayBillNo().indexOf("-") == -1) {
                            //Sets the 包号.
                            detailTemp.setWayBillNo(goods.getWayBillNo());
                        } else {
                            //Sets the 包号.
                            detailTemp.setWayBillNo(goods.getWayBillNo().substring(0, goods.getWayBillNo().indexOf("-")));
                        }
                    }
                    //Sets the 开单件数.
                    detailTemp.setWayBillQty(goods.getWayBillQty());
                    //Sets the 重量
                    detailTemp.setWeight(goods.getWeight());
                    details.add(detailTemp);
                }
                logger.error("刷新包卸车任务结束:" + taskNo);
                return details;
            } else {
                logger.error("刷新包卸车任务结束:" + taskNo + ",无卸车包明细");
                throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_NO_TASK_DETAIL_MESSAGECODE);
            }
        } else {
            logger.error("刷新包卸车任务结束:" + taskNo);
            throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_INVALID_TASK_MESSAGECODE);
        }
    }

    /**
     * 没有业务锁的卸车扫描-快递集中卸车
     * @author 200968-zwd
     * @date 2012-2-5 上午9:17:37
     */
    public String unlockunLoadScanWaybillStatus(UnloadScanDetailDto scanRecord) {
        logger.error("快递集中卸车扫描开始" + scanRecord.getType() + " " + scanRecord.getTaskNo() + " " + scanRecord.getBillNo() + " " + scanRecord.getWayBillNo() + " " + scanRecord.getSerialNo() + " " + scanRecord.getVolume());
        UnloadTaskEntity unloadTask = pdaUnloadTaskDao.queryUnloadTaskByTaskNo(scanRecord.getTaskNo());
        boolean hasTodoList = false;
        UnloadSerialNoDetailEntity serialNoEntity = new UnloadSerialNoDetailEntity();
        //任务为进行中才能提交
        if (unloadTask != null && UnloadConstants.UNLOAD_TASK_STATE_UNLOADING.equals(unloadTask.getTaskState())) {
            List<UnloadBillDetailDto> unloadBills = null;
            unloadBills = pdaUnloadTaskDao.queryUnloadBillsByTaskId(unloadTask.getId());
            if (FossConstants.YES.equals(scanRecord.getBePartial()) && !UnloadConstants.UNLOAD_GOODS_STATE_MORE.equals(scanRecord.getType())) {
                /**查询货物所在单据及交接件数，
                 * 如果不在，货物的状态为多货（扫描货物状态不为删除的情况），单据号为多货，交接件数为0
                 * */
                if (CollectionUtils.isNotEmpty(unloadBills)) {
                    //快递集中卸货
                    List<String> unloadEwayBillCodes = new ArrayList<String>();

                    for (UnloadBillDetailDto unloadBill : unloadBills) {
                        unloadEwayBillCodes.add(unloadBill.getBillNo());
                    }
                    // 出发部门编号origOrgCode  到达部门编号destOrgCode 单据编号billNo  交接件数handOverQty
                    UnloadBillDetailDto unloadBill = null;
                    if (CollectionUtils.isNotEmpty(unloadEwayBillCodes) && unloadBill == null) {
                        //h.handovertask_no as billNo,hd.goods_qty as pieces
                        unloadBill = pdaUnloadTaskDao.queryEwayBillByGoods(unloadEwayBillCodes, scanRecord.getWayBillNo(), scanRecord.getSerialNo());
                        if (unloadBill != null) {
                            unloadBill.setOrigOrgCode(unloadTask.getUnloadOrgCode());
                            unloadBill.setDestOrgCode(unloadTask.getUnloadOrgCode());
                        }
                    }

                    // 出发部门编号origOrgCode  到达部门编号destOrgCode 单据编号billNo  交接件数handOverQty
                    if (unloadBill != null) {
                        scanRecord.setBillNo(unloadBill.getBillNo());
                        if (unloadBill.getPieces() != null) {
                            scanRecord.setHandOverQty(unloadBill.getPieces().intValue());
                        } else {
                            scanRecord.setHandOverQty(0);
                        }
                        scanRecord.setOrigOrgCode(unloadBill.getOrigOrgCode());
                        scanRecord.setDestOrgCode(unloadBill.getDestOrgCode());
                    } else {
                        if (!UnloadConstants.UNLOAD_GOODS_STATE_CANCELED.equals(scanRecord.getType())) {
                            scanRecord.setType(UnloadConstants.UNLOAD_GOODS_STATE_MORE);
                        }
                        //四种类型都不属于，则属于多货
                        scanRecord.setBillNo(UnloadConstants.UNLOAD_MORE_BILL_NO);
                        scanRecord.setHandOverQty(0);
                    }
                }
            }
            //扫描件数
            int scanGoodsQtyDif = 0;
            String goodsState = scanRecord.getType();

            //如果为取消扫!
            if (UnloadConstants.UNLOAD_GOODS_STATE_CANCELED.equals(scanRecord.getType())) {
                //查询卸车运单明细
                UnloadWaybillDetailEntity wayBillEntityQC = new UnloadWaybillDetailEntity();
                if (StringUtils.isBlank(scanRecord.getBillNo())) {
                    wayBillEntityQC.setBillNo(UnloadConstants.UNLOAD_MORE_BILL_NO);
                    scanRecord.setBillNo(UnloadConstants.UNLOAD_MORE_BILL_NO);
                } else {
                    wayBillEntityQC.setBillNo(scanRecord.getBillNo());
                }
                wayBillEntityQC.setUnloadTaskId(unloadTask.getId());
                wayBillEntityQC.setWaybillNo(scanRecord.getWayBillNo());
                UnloadWaybillDetailEntity wayBIllEntity = pdaUnloadTaskDao.queryUnloadWayBillDetail(wayBillEntityQC);
                //查询卸车流水号明细
                if (wayBIllEntity != null) {
                    UnloadSerialNoDetailEntity serialNoEntityQC = new UnloadSerialNoDetailEntity();
                    serialNoEntityQC.setSerialNo(scanRecord.getSerialNo());
                    serialNoEntityQC.setUnloadWaybillDetailId(wayBIllEntity.getId());
                    serialNoEntity = pdaUnloadTaskDao.queryUnloadSerialNoEntity(serialNoEntityQC);
                }
                //如果已存在扫描流水号
                if (serialNoEntity != null && StringUtils.isNotBlank(serialNoEntity.getId())) {
                    if (TransferPDADictConstants.SCAN_STATE_SCANED.equals(serialNoEntity.getScanStatus())) {
                        scanGoodsQtyDif = -1;
                    }
                    this.newUpdateUnloadWaybillDetailEntity(wayBIllEntity, scanRecord, scanGoodsQtyDif, false, goodsState);
                    serialNoEntity = this.newUpdateUnloadSerialNoDetail(serialNoEntity, serialNoEntity.getScanStatus(), UnloadConstants.UNLOAD_GOODS_STATE_CANCELED, scanRecord.getScanTime());
                } else {
                    //如果存在卸车运单明细，扫描件数减去一件，否则插入卸车运单明细
                    if (wayBIllEntity != null) {
                        if (TransferPDADictConstants.SCAN_STATE_SCANED.equals(scanRecord.getScanState())) {
                            scanGoodsQtyDif = -1;
                        }
                        this.newUpdateUnloadWaybillDetailEntity(wayBIllEntity, scanRecord, scanGoodsQtyDif, false, goodsState);
                    } else {
                        wayBIllEntity = this.insertUnloadWayBillDetail(scanRecord, unloadTask, false);
                    }
                    //插入卸车流水号明细
                    serialNoEntity = this.insertUnloadSerialNoDetail(unloadTask, scanRecord, wayBIllEntity.getId(), UnloadConstants.UNLOAD_GOODS_STATE_CANCELED);
                }
            } else {

                if (UnloadConstants.UNLOAD_GOODS_STATE_MORE.equals(scanRecord.getType()) || UnloadConstants.UNLOAD_MORE_BILL_NO.equals(scanRecord.getBillNo())) {
                    scanRecord.setBillNo(UnloadConstants.UNLOAD_MORE_BILL_NO);
                }
                //查询卸车运单明细
                UnloadWaybillDetailEntity wayBillEntityQC = new UnloadWaybillDetailEntity();
                wayBillEntityQC.setBillNo(scanRecord.getBillNo());
                wayBillEntityQC.setUnloadTaskId(unloadTask.getId());
                wayBillEntityQC.setWaybillNo(scanRecord.getWayBillNo());
                UnloadWaybillDetailEntity wayBIllEntity = pdaUnloadTaskDao.queryUnloadWayBillDetail(wayBillEntityQC);

                //查询卸车流水号明细
                if (wayBIllEntity != null) {
                    wayBIllEntity.setHandOverPieces(scanRecord.getHandOverQty());
                    UnloadSerialNoDetailEntity serialNoEntityQC = new UnloadSerialNoDetailEntity();
                    serialNoEntityQC.setSerialNo(scanRecord.getSerialNo());
                    serialNoEntityQC.setUnloadWaybillDetailId(wayBIllEntity.getId());
                    serialNoEntity = pdaUnloadTaskDao.queryUnloadSerialNoEntity(serialNoEntityQC);
                }
                //如果已存在扫描记录
                if (serialNoEntity != null && StringUtils.isNotBlank(serialNoEntity.getId())) {
                    if (TransferPDADictConstants.SCAN_STATE_SCANED.equals(scanRecord.getScanState())) {
                        scanGoodsQtyDif = 1;
                    }
                    this.newUpdateUnloadWaybillDetailEntity(wayBIllEntity, scanRecord, scanGoodsQtyDif, true, goodsState);
                    serialNoEntity = this.newUpdateUnloadSerialNoDetail(serialNoEntity, scanRecord.getScanState(), goodsState, scanRecord.getScanTime());
                } else {
                    //如果存在卸车运单明细，则更新
                    if (wayBIllEntity != null && StringUtils.isNotBlank(wayBIllEntity.getId())) {
                        if (TransferPDADictConstants.SCAN_STATE_SCANED.equals(scanRecord.getScanState())) {
                            scanGoodsQtyDif = 1;
                        }
                        this.newUpdateUnloadWaybillDetailEntity(wayBIllEntity, scanRecord, scanGoodsQtyDif, true, goodsState);
                    } else {
                        //如果不存在卸车运单明细，则新增
                        wayBIllEntity = this.insertUnloadWayBillDetail(scanRecord, unloadTask, true);
                    }
                    //插入卸车流水号明细
                    serialNoEntity = this.insertUnloadSerialNoDetail(unloadTask, scanRecord, wayBIllEntity.getId(), goodsState);
                }

            }

        } else {
            logger.error("卸车扫描结束" + scanRecord.getType() + " " + scanRecord.getTaskNo() + " " + scanRecord.getBillNo() + " " + scanRecord.getWayBillNo() + " " + scanRecord.getSerialNo());
            throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_INVALID_TASK_MESSAGECODE);
        }
        if (UnloadConstants.UNLOAD_GOODS_STATE_MORE_ALLOPATRY_ENTRAINED.equals(serialNoEntity.getGoodsStatus())) {
            if (hasTodoList) {
                logger.error("卸车扫描结束" + scanRecord.getType() + " " + scanRecord.getTaskNo() + " " + scanRecord.getBillNo() + " " + scanRecord.getWayBillNo() + " " + scanRecord.getSerialNo());
                return TransferPDADictConstants.UNLOAD_SCAN_RETURN_SUCCESS_ALLOPATRY_ENTRAINED_HAS_TODOLIST;
            }
            logger.error("卸车扫描结束" + scanRecord.getType() + " " + scanRecord.getTaskNo() + " " + scanRecord.getBillNo() + " " + scanRecord.getWayBillNo() + " " + scanRecord.getSerialNo());
            return TransferPDADictConstants.UNLOAD_SCAN_RETURN_SUCCESS_ALLOPATRY_ENTRAINED_NO_TODOLIST;
        }
        if (UnloadConstants.UNLOAD_GOODS_STATE_MORE_ENTRAINED.equals(serialNoEntity.getGoodsStatus())) {
            if (hasTodoList) {
                logger.error("卸车扫描结束" + scanRecord.getType() + " " + scanRecord.getTaskNo() + " " + scanRecord.getBillNo() + " " + scanRecord.getWayBillNo() + " " + scanRecord.getSerialNo());
                return TransferPDADictConstants.UNLOAD_SCAN_RETURN_SUCCESS_ENTRAINED_HAS_TODOLIST;
            }
            logger.error("卸车扫描结束" + scanRecord.getType() + " " + scanRecord.getTaskNo() + " " + scanRecord.getBillNo() + " " + scanRecord.getWayBillNo() + " " + scanRecord.getSerialNo());
            return TransferPDADictConstants.UNLOAD_SCAN_RETURN_SUCCESS_ENTRAINED_NO_TODOLIST;
        }
        logger.error("卸车扫描结束" + scanRecord.getType() + " " + scanRecord.getTaskNo() + " " + scanRecord.getBillNo() + " " + scanRecord.getWayBillNo() + " " + scanRecord.getSerialNo());
        return TransferPDADictConstants.SUCCESS;
    }

    /**
     * 卸车单据扫描（运单，包等）新
     * 不加锁
     * @author 105869-heyongdong
     * @date 2015年3月3日 15:52:38
     * @param scanRecord
     * @return string
     * @throws Exception
     * @see com.deppon.foss.module.transfer.pda.api.server.service.IPDAUnloadTaskService#unloadScan(com.deppon.foss.module.transfer.pda.api.shared.dto.UnloadScanDetailDto)
     */
    @Override
    public String unloadScan(UnloadScanDetailDto scanRecord) {
        logger.error("卸车扫描开始:" + scanRecord.getTaskNo() + " " + scanRecord.getWayBillNo() + " " + scanRecord.getSerialNo());
        String result;
        try {
            if (StringUtils.equals(FossConstants.YES, scanRecord.getIsPackageScan())) {
                result = pdaUnloadService.unLoadPackageScan(scanRecord);
            } else {
                result = pdaUnloadService.unLoadWayBillScan(scanRecord);
            }
        } catch (TfrBusinessException e) {
			/*businessLockService.unlock(mutex);*/
            logger.error("卸车扫描异常:" + scanRecord.getTaskNo() + " " + scanRecord.getWayBillNo() + " " + scanRecord.getSerialNo(), e);
            throw (e);
        }
		/*businessLockService.unlock(mutex);*/
        logger.error("卸车扫描结束:" + scanRecord.getTaskNo() + " " + scanRecord.getWayBillNo() + " " + scanRecord.getSerialNo());
        return result;

    }

    /**
     * 卸车运单扫描（新）
     * 1、更新卸车运单明细表
     * 2、插入卸车流水号明细表
     * 3、插入运单相关信息到运单入库信息表中（job入库运单）
     * @author 105869-heyongdong
     * @date 2015年3月3日 16:04:20
     * @param scanRecord
     * @return String
     * @throws Exception
     * @see com.deppon.foss.module.transfer.unload.api.server.service.IPDAUnloadService#unLoadWayBillScan(com.deppon.foss.module.transfer.pda.api.shared.dto.UnloadScanDetailDto)
     */
    @Override
    public String unLoadWayBillScan(UnloadScanDetailDto scanRecord) {

        logger.error("卸车扫描开始" + scanRecord.getType() + " " + scanRecord.getTaskNo() + " " + scanRecord.getBillNo() + " " + scanRecord.getWayBillNo() + " " + scanRecord.getSerialNo() + " " + scanRecord.getVolume());
        //查询当前卸车任务
        UnloadTaskEntity unloadTask = pdaUnloadTaskDao.queryUnloadTaskByTaskNo(scanRecord.getTaskNo());
        boolean hasTodoList = false;
        UnloadSerialNoDetailEntity serialNoEntity = new UnloadSerialNoDetailEntity();
        //只有卸车中才可以插入明细做一下操作
        if (unloadTask != null && UnloadConstants.UNLOAD_TASK_STATE_UNLOADING.equals(unloadTask.getTaskState())) {
            //查询该卸车任务所有单据（交接单，配载单，空运单、接货单。。。。）
            List<UnloadBillDetailDto> unloadBills = null;
            unloadBills = pdaUnloadTaskDao.queryUnloadBillsByTaskId(unloadTask.getId());
            Map<String, String> condition = new HashMap<String, String>();
            condition.put("wayBillNo", scanRecord.getWayBillNo());
            condition.put("serialNo", scanRecord.getSerialNo());
            condition.put("active", FossConstants.ACTIVE);
            //查询货物是否已签收
            int signedQty = pdaLoadDao.queryWayBillSignedState(condition);
            //货签已签收
            if (signedQty > 0) {
                logger.error("PDAUnloadTaskService.unLoadWayBillScan(UnloadScanDetailDto) waybill has signed waybillNo="
                        + scanRecord.getWayBillNo() + ", serialNo=" + scanRecord.getSerialNo());
                return TransferPDADictConstants.SUCCESS;
                //throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_ALREADY_SIGN__MESSAGECODE);
            }

            UnloadBillDetailDto unloadBill = null;
            boolean bequery = false;
            if (FossConstants.YES.equals(scanRecord.getBePartial()) && !UnloadConstants.UNLOAD_GOODS_STATE_MORE.equals(scanRecord.getType())) {
                /**查询货物所在单据及交接件数，
                 * 如果不在，货物的状态为多货（扫描货物状态不为删除的情况），单据号为多货，交接件数为0
                 * */
                if (CollectionUtils.isNotEmpty(unloadBills)) {
                    List<String> unloadHandOverBillCodes = new ArrayList<String>();
                    List<String> unloadPickUpBillCodes = new ArrayList<String>();
                    List<String> unloadAssembleBillCodes = new ArrayList<String>();
                    List<String> unloadAirBillCodes = new ArrayList<String>();
                    //200968 zwd 快递集中卸货 2015.3.26
                    List<String> unloadEwayBillCodes = new ArrayList<String>();
                    for (UnloadBillDetailDto temp : unloadBills) {
                        if (TransferPDADictConstants.UNLOAD_ORDER_TYPE_HANDOVER.equals(temp.getBillType())) {
                            unloadHandOverBillCodes.add(temp.getBillNo());
                        } else if (TransferPDADictConstants.UNLOAD_ORDER_TYPE_VEHICLEASSEMBLE.equals(temp.getBillType())) {
                            unloadAssembleBillCodes.add(temp.getBillNo());
                        } else if (UnloadConstants.UNLOAD_TASK_TYPE_BUSINESS.equals(temp.getBillType())) {
                            unloadAirBillCodes.add(temp.getBillNo());
                        } else if (TransferPDADictConstants.UNLOAD_ORDER_TYPE_EWAYBILL.equals(temp.getBillType())) {
                            unloadEwayBillCodes.add(temp.getBillNo());
                        } else {
                            unloadPickUpBillCodes.add(temp.getBillNo());
                        }
                    }
                    //查询这四个单据 是获取交接件数 设置出发部门，达到部门
                    if (CollectionUtils.isNotEmpty(unloadAssembleBillCodes)) {
                        unloadBill = pdaUnloadTaskDao.queryAssembleBillByGoods(unloadAssembleBillCodes, scanRecord.getWayBillNo(), scanRecord.getSerialNo());
                    }
                    if (CollectionUtils.isNotEmpty(unloadHandOverBillCodes) && unloadBill == null) {
                        unloadBill = pdaUnloadTaskDao.queryHandOverBillByGoods(unloadHandOverBillCodes, scanRecord.getWayBillNo(), scanRecord.getSerialNo());
                    }
                    if (CollectionUtils.isNotEmpty(unloadPickUpBillCodes) && unloadBill == null) {
                        unloadBill = pdaUnloadTaskDao.queryPickUpBillByGoods(unloadPickUpBillCodes, scanRecord.getWayBillNo(), scanRecord.getSerialNo());
                        if (unloadBill != null) {
                            unloadBill.setOrigOrgCode(unloadTask.getUnloadOrgCode());
                            unloadBill.setDestOrgCode(unloadTask.getUnloadOrgCode());
                            bequery = true;
                        }
                    }
                    if (CollectionUtils.isNotEmpty(unloadAirBillCodes) && unloadBill == null) {
                        unloadBill = pdaUnloadTaskDao.queryAirBillByGoods(unloadPickUpBillCodes, scanRecord.getWayBillNo(), scanRecord.getSerialNo());
                        if (unloadBill != null) {
                            unloadBill.setOrigOrgCode(unloadTask.getUnloadOrgCode());
                            unloadBill.setDestOrgCode(unloadTask.getUnloadOrgCode());
                        }
                    }
                    //zwd 200968 快递集中卸车 2015.3.26
                    if (CollectionUtils.isNotEmpty(unloadEwayBillCodes) && unloadBill == null) {
                        //h.handovertask_no as billNo,hd.goods_qty as pieces
                        unloadBill = pdaUnloadTaskDao.queryEwayBillByGoods(unloadEwayBillCodes, scanRecord.getWayBillNo(), scanRecord.getSerialNo());
                        if (unloadBill != null) {
                            unloadBill.setOrigOrgCode(unloadTask.getUnloadOrgCode());
                            unloadBill.setDestOrgCode(unloadTask.getUnloadOrgCode());
                        }
                    }
                    //查询到卸车单据则 设置出发部门、到达部门 、交接件数 ，查询不到则该货为 卸车多货
                    if (unloadBill != null) {
                        scanRecord.setBillNo(unloadBill.getBillNo());
                        //设置交接件数
                        if (unloadBill.getPieces() != null) {
                            scanRecord.setHandOverQty(unloadBill.getPieces().intValue());
                        } else {
                            scanRecord.setHandOverQty(0);
                        }
                        //设置出发部门
                        scanRecord.setOrigOrgCode(unloadBill.getOrigOrgCode());
                        //设置到达部门
                        scanRecord.setDestOrgCode(unloadBill.getDestOrgCode());
                    } else {
                        if (!UnloadConstants.UNLOAD_GOODS_STATE_CANCELED.equals(scanRecord.getType())) {
                            scanRecord.setType(UnloadConstants.UNLOAD_GOODS_STATE_MORE);
                        }
                        scanRecord.setBillNo(UnloadConstants.UNLOAD_MORE_BILL_NO);
                        scanRecord.setHandOverQty(0);
                    }
                }
            }
            String handleType = UnloadConstants.LABEL_HANDLE_NOTHING;
            //扫描件数
            int scanGoodsQtyDif = 0;
            String goodsState = scanRecord.getType();

            //KDTE-3384，把代码由2095行提前至此
            //查询接送货单据中是否有此货物
            boolean beExistsInPickUpBill = false;
            UnloadBillDetailDto pickUpBill = null;
            //卸车单据不为空时
            if (CollectionUtils.isNotEmpty(unloadBills)) {
                if (bequery) {
                    pickUpBill = unloadBill;
                } else {
                    List<String> unloadPickUpBillCodes = new ArrayList<String>();
                    //如果是接送货单据则 添加接送货单据（除去交接单、配载单、空运单）
                    for (UnloadBillDetailDto temp : unloadBills) {
                        //去掉中转单据 200968 zwd 2015.3.26
                        if (!TransferPDADictConstants.UNLOAD_ORDER_TYPE_HANDOVER.equals(temp.getBillType()) && !TransferPDADictConstants.UNLOAD_ORDER_TYPE_VEHICLEASSEMBLE.equals(temp.getBillType())
                                && !UnloadConstants.UNLOAD_TASK_TYPE_BUSINESS.equals(temp.getBillType()) && !TransferPDADictConstants.UNLOAD_ORDER_TYPE_EWAYBILL.equals(temp.getBillType())) {
                            unloadPickUpBillCodes.add(temp.getBillNo());
                        }
                    }
                    //设置接送货单据的出发、达到部门
                    if (CollectionUtils.isNotEmpty(unloadPickUpBillCodes)) {
                        pickUpBill = pdaUnloadTaskDao.queryPickUpBillByGoods(unloadPickUpBillCodes, scanRecord.getWayBillNo(), scanRecord.getSerialNo());
                        if (pickUpBill != null) {
                            pickUpBill.setOrigOrgCode(unloadTask.getUnloadOrgCode());
                            pickUpBill.setDestOrgCode(unloadTask.getUnloadOrgCode());
                        }
                    }
                }

                //如果接送货单据存在，则设置扫描信息中的交接件数和出发部门到达部门
                if (pickUpBill != null) {
                    beExistsInPickUpBill = true;
                    scanRecord.setBillNo(pickUpBill.getBillNo());
                    goodsState = UnloadConstants.UNLOAD_GOODS_STATE_NORMAL;
                    if (pickUpBill.getPieces() != null) {
                        scanRecord.setHandOverQty(pickUpBill.getPieces().intValue());
                    }
                    scanRecord.setDestOrgCode(pickUpBill.getDestOrgCode());
                    scanRecord.setOrigOrgCode(pickUpBill.getOrigOrgCode());
                }
            }

            //如果为取消扫!
            if (UnloadConstants.UNLOAD_GOODS_STATE_CANCELED.equals(scanRecord.getType())) {
                //设置查询条件 ，查询卸车运单明细
                UnloadWaybillDetailEntity wayBillEntityQC = new UnloadWaybillDetailEntity();
                if (StringUtils.isBlank(scanRecord.getBillNo())) {
                    wayBillEntityQC.setBillNo(UnloadConstants.UNLOAD_MORE_BILL_NO);
                    scanRecord.setBillNo(UnloadConstants.UNLOAD_MORE_BILL_NO);
                } else {
                    wayBillEntityQC.setBillNo(scanRecord.getBillNo());
                }
                wayBillEntityQC.setUnloadTaskId(unloadTask.getId());
                wayBillEntityQC.setWaybillNo(scanRecord.getWayBillNo());
                UnloadWaybillDetailEntity wayBIllEntity = pdaUnloadTaskDao.queryUnloadWayBillDetail(wayBillEntityQC);
                //查询卸车流水号明细
                if (wayBIllEntity != null) {
                    UnloadSerialNoDetailEntity serialNoEntityQC = new UnloadSerialNoDetailEntity();
                    serialNoEntityQC.setSerialNo(scanRecord.getSerialNo());
                    serialNoEntityQC.setUnloadWaybillDetailId(wayBIllEntity.getId());
                    serialNoEntity = pdaUnloadTaskDao.queryUnloadSerialNoEntity(serialNoEntityQC);
                    //scanGoodsQty = wayBIllEntity.getScanGoodsQty();
                }
                //如果已存在扫描流水号
                if (serialNoEntity != null && StringUtils.isNotBlank(serialNoEntity.getId())) {
                    if (TransferPDADictConstants.SCAN_STATE_SCANED.equals(serialNoEntity.getScanStatus())) {
                        scanGoodsQtyDif = -1;
                    }
                    //更新流水号明细
                    serialNoEntity = this.newUpdateUnloadSerialNoDetail(serialNoEntity, serialNoEntity.getScanStatus(), UnloadConstants.UNLOAD_GOODS_STATE_CANCELED, scanRecord.getScanTime());
                    //更新运单明细，件数-1
                    this.newUpdateUnloadWaybillDetailEntity(wayBIllEntity, scanRecord, scanGoodsQtyDif, false, goodsState);

                    //货物状态不为已取消且操作时间在扫描时间之前，更新流水号
                    boolean beUpdate = false;
                    if (serialNoEntity.getOptTime() == null) {
                        beUpdate = true;
                    } else {
                        if (serialNoEntity.getOptTime().before(scanRecord.getScanTime())) {
                            beUpdate = true;
                        }
                    }
                    //用于设置 重贴标签是删除还是新增
                    if (beUpdate) {
                        if (!UnloadConstants.UNLOAD_GOODS_STATE_CANCELED.equals(serialNoEntity.getGoodsStatus())) {
                            handleType = UnloadConstants.LABEL_HANDLE_DETELE;
                        } else {
                            handleType = UnloadConstants.LABEL_HANDLE_NOTHING;
                        }
                    } else {//货物状态为已取消
                        handleType = UnloadConstants.LABEL_HANDLE_NOTHING;
                    }
                } else {//如果不存在扫描流水号记录
                    //如果存在卸车运单明细，扫描件数减去一件，否则插入卸车运单明细
                    if (wayBIllEntity != null) {
                        if (TransferPDADictConstants.SCAN_STATE_SCANED.equals(scanRecord.getScanState())) {
                            scanGoodsQtyDif = -1;
                        }
                        //插入卸车流水号明细
                        serialNoEntity = this.insertUnloadSerialNoDetail(unloadTask, scanRecord, wayBIllEntity.getId(), UnloadConstants.UNLOAD_GOODS_STATE_CANCELED);
                        this.newUpdateUnloadWaybillDetailEntity(wayBIllEntity, scanRecord, scanGoodsQtyDif, false, goodsState);
                    } else {
                        wayBIllEntity = this.insertUnloadWayBillDetail(scanRecord, unloadTask, false);
                        //插入卸车流水号明细
                        serialNoEntity = this.insertUnloadSerialNoDetail(unloadTask, scanRecord, wayBIllEntity.getId(), UnloadConstants.UNLOAD_GOODS_STATE_CANCELED);
                    }

                    handleType = UnloadConstants.LABEL_HANDLE_DETELE;
                }
            } else {

                //插入扫描信息到异步信息表中 然后运单入库按照该表数据入库（job入库）
                if (FossConstants.YES.equals(unloadTask.getBeScanInstock()) && !StringUtils.equals(UnloadConstants.WAYBILL_STATUS_NO, scanRecord.getWayBillStatus())) {
                    //卸车异步入库信息表
                    PDAUnloadAsyncBillMsgDto unloadAsyncMsgDto = new PDAUnloadAsyncBillMsgDto();
                    //id
                    unloadAsyncMsgDto.setId(UUIDUtils.getUUID());
                    //任务号
                    unloadAsyncMsgDto.setTaskNo(scanRecord.getTaskNo());
                    //单据编号
                    String billNo = scanRecord.getBillNo();
                    unloadAsyncMsgDto.setBillNo(billNo);
                    //运单号
                    String waybillNo = scanRecord.getWayBillNo();
                    unloadAsyncMsgDto.setWayBillNo(waybillNo);
                    //流水号
                    unloadAsyncMsgDto.setSerialNo(scanRecord.getSerialNo());
                    //若交接单/配载单的到达部门不为空，则为到达部门，否则为卸车部门
                    if (StringUtil.isNotBlank(scanRecord.getDestOrgCode())) {
                        //判定货物卸车部门是否等于提货网点--分部卸车入库需求
                        if (StringUtil.equals(scanRecord.getReachOrgCode(), unloadTask.getUnloadOrgCode())) {
                            unloadAsyncMsgDto.setOrgCode(unloadTask.getUnloadOrgCode());
                        } else {
                            unloadAsyncMsgDto.setOrgCode(scanRecord.getDestOrgCode());
                        }
                    } else {
                        unloadAsyncMsgDto.setOrgCode(unloadTask.getUnloadOrgCode());
                    }
                    LoaderParticipationEntity loaderParticipation = getLoaderParticipationByTaskId(unloadTask.getId());
                    //入库类型 卸车入库
                    unloadAsyncMsgDto.setInStockType(StockConstants.UNLOAD_GOODS_IN_STOCK_TYPE);
                    //操作人
                    unloadAsyncMsgDto.setOperatorCode(loaderParticipation.getLoaderCode());
                    unloadAsyncMsgDto.setOperatorName(loaderParticipation.getLoaderName());
                    //创建时间
                    unloadAsyncMsgDto.setCreateTime(new Date());
                    //job类型 "N/A"
                    unloadAsyncMsgDto.setJobId(UnloadConstants.UNLOAD_INSTOCK_MSG_JOBID);
                    //是否建包
                    unloadAsyncMsgDto.setBePackage("N");

                    List<PDAUnloadAsyncBillMsgDto> unloadAsyncMsgs = new ArrayList<PDAUnloadAsyncBillMsgDto>();
                    unloadAsyncMsgs.add(unloadAsyncMsgDto);
                    //插入异步信息表中
                    pdaUnloadTaskDao.insertUnloadAsyncMsg(unloadAsyncMsgs);

                }
                if (UnloadConstants.UNLOAD_GOODS_STATE_MORE.equals(scanRecord.getType()) || UnloadConstants.UNLOAD_MORE_BILL_NO.equals(scanRecord.getBillNo())) {
                    //不是接送货单据
                    if (!beExistsInPickUpBill) {
                        //查询上一部门是否存在未完成待办事项
                        StockEntity stock = stockService.queryUniqueStock(scanRecord.getWayBillNo(), scanRecord.getSerialNo());
                        if (stock != null && StringUtils.isNotBlank(stock.getOrgCode())) {
                            //卸车进行的代办飘移
                            List<String> todoList = waybillRfcService.queryTodoWhenDumpTruck(scanRecord.getWayBillNo(), scanRecord.getSerialNo(), unloadTask.getUnloadOrgCode(), stock.getOrgCode());
                            if (CollectionUtils.isNotEmpty(todoList)) {
                                hasTodoList = true;
                            }
                        }
                        //默认为多货-异地夹带
                        goodsState = UnloadConstants.UNLOAD_GOODS_STATE_MORE_ALLOPATRY_ENTRAINED;
                        //查询货物走货路径
                        FeedbackDto feedbackDto = calculateTransportPathService.getNextOrgAndTime(scanRecord.getWayBillNo(),
                                scanRecord.getSerialNo(), unloadTask.getUnloadOrgCode());
                        if (feedbackDto != null && feedbackDto.getResult() != TransportPathConstants.STATUS_WRONG) {
                            //若装车部门在货物走货路径上，则为多货-夹带
                            goodsState = UnloadConstants.UNLOAD_GOODS_STATE_MORE_ENTRAINED;
                        }
                        scanRecord.setBillNo(UnloadConstants.UNLOAD_MORE_BILL_NO);
                    }
                }
                //查询卸车运单明细
                UnloadWaybillDetailEntity wayBillEntityQC = new UnloadWaybillDetailEntity();
                wayBillEntityQC.setBillNo(scanRecord.getBillNo());
                wayBillEntityQC.setUnloadTaskId(unloadTask.getId());
                wayBillEntityQC.setWaybillNo(scanRecord.getWayBillNo());
                UnloadWaybillDetailEntity wayBIllEntity = pdaUnloadTaskDao.queryUnloadWayBillDetail(wayBillEntityQC);

                //查询卸车流水号明细
                if (wayBIllEntity != null) {
                    wayBIllEntity.setHandOverPieces(scanRecord.getHandOverQty());
                    UnloadSerialNoDetailEntity serialNoEntityQC = new UnloadSerialNoDetailEntity();
                    serialNoEntityQC.setSerialNo(scanRecord.getSerialNo());
                    serialNoEntityQC.setUnloadWaybillDetailId(wayBIllEntity.getId());
                    serialNoEntity = pdaUnloadTaskDao.queryUnloadSerialNoEntity(serialNoEntityQC);
                }
                //如果已存在扫描记录
                if (serialNoEntity != null && StringUtils.isNotBlank(serialNoEntity.getId())) {
                    if (TransferPDADictConstants.SCAN_STATE_SCANED.equals(scanRecord.getScanState())) {
                        scanGoodsQtyDif = 1;
                    }
                    serialNoEntity = this.newUpdateUnloadSerialNoDetail(serialNoEntity, scanRecord.getScanState(), goodsState, scanRecord.getScanTime());
                    this.newUpdateUnloadWaybillDetailEntity(wayBIllEntity, scanRecord, scanGoodsQtyDif, true, goodsState);

                    //如果扫描时间在操作时间之后
                    boolean beUpdate = false;
                    if (serialNoEntity.getOptTime() == null) {
                        beUpdate = true;
                    } else {
                        if (serialNoEntity.getOptTime().before(scanRecord.getScanTime())) {
                            beUpdate = true;
                        }
                    }
                    if (beUpdate) {
                        //如果已存在状态为已取消
                        if (UnloadConstants.UNLOAD_GOODS_STATE_CANCELED.equals(serialNoEntity.getGoodsStatus())) {
                            handleType = UnloadConstants.LABEL_HANDLE_ADD;
                        } else {
                            handleType = UnloadConstants.LABEL_HANDLE_NOTHING;
                        }
                    } else {
                        handleType = UnloadConstants.LABEL_HANDLE_NOTHING;
                    }
                } else {//如果不存在扫描记录

                    //如果存在卸车运单明细，则更新
                    if (wayBIllEntity != null && StringUtils.isNotBlank(wayBIllEntity.getId())) {
                        if (TransferPDADictConstants.SCAN_STATE_SCANED.equals(scanRecord.getScanState())) {
                            scanGoodsQtyDif = 1;
                        }
                        //插入卸车流水号明细
                        serialNoEntity = this.insertUnloadSerialNoDetail(unloadTask, scanRecord, wayBIllEntity.getId(), goodsState);
                        this.newUpdateUnloadWaybillDetailEntity(wayBIllEntity, scanRecord, scanGoodsQtyDif, true, goodsState);
                    } else {
                        //如果不存在卸车运单明细，则新增
                        wayBIllEntity = this.insertUnloadWayBillDetail(scanRecord, unloadTask, true);
                        //插入卸车流水号明细
                        serialNoEntity = this.insertUnloadSerialNoDetail(unloadTask, scanRecord, wayBIllEntity.getId(), goodsState);
                    }
                    handleType = UnloadConstants.LABEL_HANDLE_ADD;
                }

            }
            // 处理重贴标签
            this.handleLabeledGoods(unloadTask, scanRecord, handleType);
            //判断是否晚到
            //this.isArriveLate(scanRecord);
            //2016年12月29日15:51:36 zm  添加时间点限制   如果超出配置时间则不判断晚到
            try {
                ConfigurationParamsEntity configurationParams = configurationParamsService.queryConfigurationParamsByOrgCode(
                        DictionaryConstants.SYSTEM_CONFIG_PARM__TFR, DictionaryConstants.IS_ARRIVE_LATE_PARAMS,
                        FossConstants.ROOT_ORG_CODE);
                Date bizDate = null;
                if (configurationParams != null && StringUtils.isNotEmpty(configurationParams.getConfValue())) {
                    logger.error("判断是否晚到配置参数:" + configurationParams.getConfValue());
                    bizDate = DateUtils.strToDate(configurationParams.getConfValue());
                }
                //当前时间小于设定时间点则不调用晚到逻辑
                if (null != bizDate && scanRecord.getScanTime().getTime() < bizDate.getTime()) {
                    //判断是否晚到
                    this.isArriveLate(scanRecord);
                }
            } catch (Exception e) {
                logger.error("判断是否晚到出错:" + e.getMessage());
            }
        } else {
            logger.error("PDAUnloadTaskService.unLoadWayBillScan(UnloadScanDetailDto)无效任务，卸车扫描结束" + scanRecord.getType() + " " + scanRecord.getTaskNo() + " " + scanRecord.getBillNo() + " " + scanRecord.getWayBillNo() + " " + scanRecord.getSerialNo());
            //throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_INVALID_TASK_MESSAGECODE);
        }
        if (UnloadConstants.UNLOAD_GOODS_STATE_MORE_ALLOPATRY_ENTRAINED.equals(serialNoEntity.getGoodsStatus())) {//多货异地夹带
            if (hasTodoList) {
                logger.error("卸车扫描结束" + scanRecord.getType() + " " + scanRecord.getTaskNo() + " " + scanRecord.getBillNo() + " " + scanRecord.getWayBillNo() + " " + scanRecord.getSerialNo());
                return TransferPDADictConstants.UNLOAD_SCAN_RETURN_SUCCESS_ALLOPATRY_ENTRAINED_HAS_TODOLIST;
            }
            logger.error("卸车扫描结束" + scanRecord.getType() + " " + scanRecord.getTaskNo() + " " + scanRecord.getBillNo() + " " + scanRecord.getWayBillNo() + " " + scanRecord.getSerialNo());
            return TransferPDADictConstants.UNLOAD_SCAN_RETURN_SUCCESS_ALLOPATRY_ENTRAINED_NO_TODOLIST;
        }
        if (UnloadConstants.UNLOAD_GOODS_STATE_MORE_ENTRAINED.equals(serialNoEntity.getGoodsStatus())) {//多货夹带
            if (hasTodoList) {
                logger.error("卸车扫描结束" + scanRecord.getType() + " " + scanRecord.getTaskNo() + " " + scanRecord.getBillNo() + " " + scanRecord.getWayBillNo() + " " + scanRecord.getSerialNo());
                return TransferPDADictConstants.UNLOAD_SCAN_RETURN_SUCCESS_ENTRAINED_HAS_TODOLIST;
            }
            logger.error("卸车扫描结束" + scanRecord.getType() + " " + scanRecord.getTaskNo() + " " + scanRecord.getBillNo() + " " + scanRecord.getWayBillNo() + " " + scanRecord.getSerialNo());
            return TransferPDADictConstants.UNLOAD_SCAN_RETURN_SUCCESS_ENTRAINED_NO_TODOLIST;
        }
        logger.error("卸车扫描结束" + scanRecord.getType() + " " + scanRecord.getTaskNo() + " " + scanRecord.getBillNo() + " " + scanRecord.getWayBillNo() + " " + scanRecord.getSerialNo());


        return TransferPDADictConstants.SUCCESS;
    }

    /**
     * * 卸车包扫描（新）
     * 1、更新卸车运单明细表
     * 2、插入卸车流水号明细表
     * 3、插入运单相关信息到运单入库信息表中（job入库运单）
     * @author 105869-heyongdong
     * @date 2015年3月3日 16:07:47
     * @param scanRecord
     * @return String
     * @see com.deppon.foss.module.transfer.unload.api.server.service.IPDAUnloadService#unLoadPackageScan(com.deppon.foss.module.transfer.pda.api.shared.dto.UnloadScanDetailDto)
     */
    @Override
    public String unLoadPackageScan(UnloadScanDetailDto scanRecord) {
        logger.error("卸车包扫描开始" + scanRecord.getType() + " " + scanRecord.getTaskNo() + " " + scanRecord.getBillNo() + " " + scanRecord.getWayBillNo() + " " + scanRecord.getSerialNo() + " " + scanRecord.getVolume());
        UnloadTaskEntity unloadTask = pdaUnloadTaskDao.queryUnloadTaskByTaskNo(scanRecord.getTaskNo());
        if (unloadTask != null && UnloadConstants.UNLOAD_TASK_STATE_UNLOADING.equals(unloadTask.getTaskState())) {
            String goodsState = scanRecord.getType();
            //查询扫描包中的运单明细
            List<ExpressPackageDetailEntity> packageDetailList = pdaExpressPackageDao.queryScanPackageDetails(scanRecord.getWayBillNo());
            if (null == packageDetailList || packageDetailList.size() <= 0) {
                return TransferPDADictConstants.SUCCESS;
                //throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_INVALID_NOPACKAGEDETAIL_MESSAGECODE);
            }
            if (UnloadConstants.UNLOAD_GOODS_STATE_MORE.equals(scanRecord.getType()) || UnloadConstants.UNLOAD_MORE_BILL_NO.equals(scanRecord.getBillNo())) {
                //默认为多货-异地夹带
                goodsState = UnloadConstants.UNLOAD_GOODS_STATE_MORE_ALLOPATRY_ENTRAINED;
                //默认为多货-异地夹带
                goodsState = UnloadConstants.UNLOAD_GOODS_STATE_MORE_ALLOPATRY_ENTRAINED;
                //查询货物走货路径
                FeedbackDto feedbackDto = calculateTransportPathService.getNextOrgAndTime(packageDetailList.get(0).getWayBillNo(),
                        packageDetailList.get(0).getSerialNo(), unloadTask.getUnloadOrgCode());
                if (feedbackDto != null && feedbackDto.getResult() != TransportPathConstants.STATUS_WRONG) {
                    //若装车部门在货物走货路径上，则为多货-夹带
                    goodsState = UnloadConstants.UNLOAD_GOODS_STATE_MORE_ENTRAINED;
                }
                scanRecord.setBillNo(UnloadConstants.UNLOAD_MORE_BILL_NO);
            }

            //查询该包是否已经扫描
            List<UnloadWaybillDetailEntity> waybillDetails = pdaUnloadTaskDao.queryIsPackageScan(unloadTask.getId(), scanRecord.getWayBillNo());

            if (CollectionUtils.isNotEmpty(waybillDetails)) {
                for (UnloadWaybillDetailEntity waybillDetail : waybillDetails) {
                    UnloadWaybillDetailEntity wayBillEntity = new UnloadWaybillDetailEntity();
                    ExpressPackageDetailEntity entity = pdaUnloadTaskDao.queryPackageDetailsByWaybill(waybillDetail.getWaybillNo(), scanRecord.getWayBillNo());
                    wayBillEntity.setWaybillNo(waybillDetail.getWaybillNo());
                    wayBillEntity.setPackageNo(scanRecord.getWayBillNo());
                    if (UnloadConstants.UNLOAD_GOODS_STATE_CANCELED.equals(scanRecord.getType())) {
                        wayBillEntity.setUnloadVolumeTotal(new BigDecimal(0));
                        wayBillEntity.setUnloadWeightTotal(new BigDecimal(0));
                        wayBillEntity.setScanGoodsQty(0);
                        wayBillEntity.setOperationGoodsQty(0);
                    } else {
                        wayBillEntity.setUnloadVolumeTotal(new BigDecimal(entity.getVolume()));
                        wayBillEntity.setUnloadWeightTotal(new BigDecimal(entity.getWeight()));
                        wayBillEntity.setScanGoodsQty(entity.getGoodsQty());
                        wayBillEntity.setOperationGoodsQty(entity.getGoodsQty());
                    }
                    wayBillEntity.setModifyDate(scanRecord.getScanTime());
                    wayBillEntity.setId(waybillDetail.getId());
                    pdaUnloadTaskDao.updatePackageWayBillEntity(wayBillEntity);


                    UnloadSerialNoDetailEntity serialNoEntity = new UnloadSerialNoDetailEntity();
                    serialNoEntity.setUnloadWaybillDetailId(waybillDetail.getId());
                    if (StringUtils.isNotBlank(scanRecord.getScanState())) {
                        serialNoEntity.setScanStatus(scanRecord.getScanState());
                    } else {
                        serialNoEntity.setScanStatus(UnloadConstants.UNLOAD_TASK_SCAN_STATUS_NA);
                    }
                    serialNoEntity.setGoodsStatus(goodsState);
                    serialNoEntity.setOptTime(scanRecord.getScanTime());

                    pdaUnloadTaskDao.updateUnloadPackageSerialNo(serialNoEntity);
                }
            } else {
                List<ExpressPackageDetailEntity> detailList = pdaUnloadTaskDao.queryScanPackageDetails(scanRecord.getWayBillNo());
                if (CollectionUtils.isNotEmpty(detailList)) {
                    for (ExpressPackageDetailEntity detail : detailList) {
                        //插入运单
                        String wayBillDetailId = insertDetailbypackage(scanRecord, unloadTask, detail);
                        //批量插入流水号
                        insertSerialsbyPackage(scanRecord, unloadTask, goodsState, detail, wayBillDetailId);
                    }
                }
            }

            // 若扫描的是直达包，则更改包中的运单在入库表中是否建包状态
            String bePackage = "N";
            ExpressPackageEntity packageEntity = pdaExpressPackageDao.queryPackageByNo(scanRecord.getWayBillNo());
            if (null != packageEntity) {
                if ((StringUtils.equals(packageEntity.getExpressPackageType(), "THROUGH_ARRIVE") ||
                        StringUtils.equals(packageEntity.getExpressPackageType(), "AIRTHROUGH_ARRIVE")) &&
                        !StringUtil.equals(packageEntity.getArriveOrgCode(), unloadTask.getUnloadOrgCode())) {
                    bePackage = "Y";
                }
            }
            //扫描入库
            if (!UnloadConstants.UNLOAD_GOODS_STATE_CANCELED.equals(scanRecord.getType())) {
                for (int i = 0; i < packageDetailList.size(); i++) {
                    ExpressPackageDetailEntity packageDetail = packageDetailList.get(i);
                    //查询运单基本属性
                    WaybillEntity wayBill = waybillManagerService.queryWaybillBasicByNo(packageDetail.getWayBillNo());
                    if (null != wayBill) {
                        if (FossConstants.YES.equals(unloadTask.getBeScanInstock())) {

                            //卸车异步入库信息表
                            PDAUnloadAsyncBillMsgDto unloadAsyncMsgDto = new PDAUnloadAsyncBillMsgDto();
                            //id
                            unloadAsyncMsgDto.setId(UUIDUtils.getUUID());
                            //任务号
                            unloadAsyncMsgDto.setTaskNo(scanRecord.getTaskNo());
                            //单据编号
                            unloadAsyncMsgDto.setBillNo(scanRecord.getBillNo());
                            //运单号
                            String waybillNo = packageDetail.getWayBillNo();
                            unloadAsyncMsgDto.setWayBillNo(waybillNo);
                            //流水号
                            unloadAsyncMsgDto.setSerialNo(packageDetail.getSerialNo());
                            //若交接单/配载单的到达部门不为空，则为到达部门，否则为卸车部门
                            if (StringUtil.isNotBlank(scanRecord.getDestOrgCode())) {
                                //判定货物卸车部门是否等于提货网点--分部卸车入库需求
                                if (StringUtil.equals(wayBill.getCustomerPickupOrgCode(), unloadTask.getUnloadOrgCode())) {
                                    unloadAsyncMsgDto.setOrgCode(unloadTask.getUnloadOrgCode());
                                } else {
                                    unloadAsyncMsgDto.setOrgCode(scanRecord.getDestOrgCode());
                                }
                            } else {
                                unloadAsyncMsgDto.setOrgCode(unloadTask.getUnloadOrgCode());
                            }
                            LoaderParticipationEntity loaderParticipation = getLoaderParticipationByTaskId(unloadTask.getId());
                            //入库类型 卸车入库
                            unloadAsyncMsgDto.setInStockType(StockConstants.UNLOAD_GOODS_IN_STOCK_TYPE);
                            //操作人
                            unloadAsyncMsgDto.setOperatorCode(loaderParticipation.getLoaderCode());
                            unloadAsyncMsgDto.setOperatorName(loaderParticipation.getLoaderName());
                            //创建时间
                            unloadAsyncMsgDto.setCreateTime(new Date());
                            //job类型 "N/A"
                            unloadAsyncMsgDto.setJobId(UnloadConstants.UNLOAD_INSTOCK_MSG_JOBID);
                            //是否建包
                            unloadAsyncMsgDto.setBePackage(bePackage);

                            List<PDAUnloadAsyncBillMsgDto> unloadAsyncMsgs = new ArrayList<PDAUnloadAsyncBillMsgDto>();
                            unloadAsyncMsgs.add(unloadAsyncMsgDto);
                            //插入异步信息表中
                            pdaUnloadTaskDao.insertUnloadAsyncMsg(unloadAsyncMsgs);

                        }
                    }
                }
            }

        }

        return TransferPDADictConstants.SUCCESS;
    }


    /**
     * @desc 提供给PDA 查询待卸车辆预分配月台情况
     * @param currOrgCode 当前部门编码
     * @param vehicleNo 车牌号  (非必填)
     * @param platformNo 月台号(非必填)
     * @param businessType 业务类型(必填)  长途:LONG_DISTANCE ,短途:SHORT_DISTANCE,接送货：DIVISION
     * @param enterTfrCenterType 入场情况(必填) 已入场 IN,未入场 OUT
     * @author 105795
     * @date 2015-05-09
     * */
    public List<PDApreplatformEntity> queryPreplatformNo(String currOrgCode, String vehicleNo, String platformNo, String businessType, String enterTfrCenterType) {

        List<PDApreplatformEntity> prePlatformList = new ArrayList<PDApreplatformEntity>();
        //对pda传过来的参数进行校验
        if (StringUtil.isEmpty(businessType)) {
            throw new TfrBusinessException("PDA业务类型不能为空！");
        }
        if (StringUtil.isEmpty(enterTfrCenterType)) {
            throw new TfrBusinessException("车辆入场类型不能为空！");

        }
        if (StringUtil.isEmpty(currOrgCode)) {
            throw new TfrBusinessException("当前部门编码为空");
        }
        //根据pda传过来的部门code向上查询顶级部门
        OrgAdministrativeInfoEntity outfield = pdaCommonService.getCurrentOutfieldCode(currOrgCode);
        if (outfield == null) {
            throw new TfrBusinessException("根据当前部门向上找顶级外场失败!");
        }
        currOrgCode = outfield.getCode();
        //如果车牌号or月台号不为空 则这两条件优先
        if (StringUtil.isNotEmpty(vehicleNo) || StringUtil.isNotEmpty(platformNo)) {
            businessType = null;
            enterTfrCenterType = null;
        }


        //查询预分配月台信息
        long timeLong = System.currentTimeMillis();
        logger.info("开始查询预分配月台信息 -----------");
        List<PDApreplatformDto> resultListDto = unloadTaskDao.queryPreplatformNo(currOrgCode, vehicleNo, platformNo, businessType, enterTfrCenterType);
        logger.info("查询预分配月台信息结束 -----------耗时：" + (System.currentTimeMillis() - timeLong));

        if (com.deppon.foss.util.CollectionUtils.isEmpty(resultListDto) && resultListDto.size() < 1) {
            throw new TfrBusinessException("没有查询到预分配月台信息");
        }
        PDApreplatformEntity prePlatform = null;
        for (PDApreplatformDto dto : resultListDto) {
            prePlatform = new PDApreplatformEntity();
            prePlatform.setVehicleNo(dto.getVehicleNo() == null ? "" : dto.getVehicleNo());
            prePlatform.setArriveTime(dto.getArriveTime() == null ? "" : dto.getArriveTime());
            prePlatform.setDepartOrgCode(dto.getDepartOrgCode() == null ? "" : dto.getDepartOrgCode());
            prePlatform.setDepartOrgName(dto.getDepartOrgName() == null ? "" : dto.getDepartOrgName());
            prePlatform.setPlatformNo(dto.getPlatformNo() == null ? "" : dto.getPlatformNo());
            prePlatformList.add(prePlatform);
        }

        return prePlatformList;
    }

    /**
     * @author zyr
     * @date 2015-7-11上午10:12:26
     * @function 到达部门是驻地营业部，获取有效的运单信息，到达部门为运单的提货网点所属外场，判断是否晚到，晚到则调用接送货接口
     * @param scanRecord
     * @return
     */
    private void isArriveLate(UnloadScanDetailDto scanRecord) {
        //获取运单提货网点
        WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(scanRecord.getWayBillNo());
        //通过OrgCode部门编号获取部门实体，判定此部门性质是否为非驻地营业部
        OrgAdministrativeInfoEntity org = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(waybillEntity.getCustomerPickupOrgCode());
        if (null != org) {
            //营业部
            if (StringUtils.equals(FossConstants.YES, org.getSalesDepartment())) {
                SaleDepartmentEntity saleDepartmentEntity = saleDepartmentService.querySimpleSaleDepartmentByCode(waybillEntity.getCustomerPickupOrgCode());
                if (saleDepartmentEntity == null) {
                    return;
                }

                //驻地营业部
                if (StringUtils.equals(FossConstants.YES, saleDepartmentEntity.getStation())) {
                    if (StringUtil.equals(scanRecord.getDestOrgCode(), saleDepartmentEntity.getTransferCenter())) {
                        //查询运单的预计到达时间
                        WaybillPlanArriveTimeDto dto = arrivalDao.queryPlanArriveTime(scanRecord.getWayBillNo());
                        if (null != dto) {
                            if (null != dto.getPreArriveTime()) {
                                //Date realityArriverTime = Calendar.getInstance().getTime();
                                //2016年12月27日09:12:27 zm add  添加晚到预计到达时间推后24小时
                                long time = ConstantsNumberSonar.SONAR_NUMBER_24 * ConstantsNumberSonar.SONAR_NUMBER_60 * ConstantsNumberSonar.SONAR_NUMBER_60 * ConstantsNumberSonar.SONAR_NUMBER_1000;
                                if (scanRecord.getScanTime().getTime() > (dto.getPreArriveTime().getTime() + time)) {
                                    //调接送货接口
                                    compensateSpreadService.calculateSpread(scanRecord.getWayBillNo());
                                }
                            } else {
                                logger.error("运单号" + scanRecord.getWayBillNo() + "未查询到预计到达时间!");
                            }
                        }
                    }
                }
            }
        }
    }
}