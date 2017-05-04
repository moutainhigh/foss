package com.deppon.foss.module.transfer.unload.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.module.pickup.pricing.dubbo.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.dubbo.api.shared.domain.ProductEntity;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILoadAndUnloadSquadService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IPorterService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LoadAndUnloadSquadEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PorterEntity;

import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.transfer.common.api.server.service.IBillNumService;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.load.api.server.dao.IPDALoadDao;
import com.deppon.foss.module.transfer.load.api.server.service.IPDACommonService;
import com.deppon.foss.module.transfer.load.api.shared.define.LoadConstants;
import com.deppon.foss.module.transfer.load.api.shared.define.TaskTruckConstant;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoaderParticipationEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.PDATaskEntity;
import com.deppon.foss.module.transfer.pda.api.shared.define.TransferPDADictConstants;
import com.deppon.foss.module.transfer.pda.api.shared.domain.SCPDAAssignUnloadTaskEntity;
import com.deppon.foss.module.transfer.pda.api.shared.dto.LoaderDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.PDAGoodsSerialNoDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.UnloadGoodsDetailDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.UnloadScanDetailDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.UnloadTaskDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.UnloadTaskResultDto;
import com.deppon.foss.module.transfer.pda.api.shared.exception.TransferPDAExceptionCode;
import com.deppon.foss.module.transfer.scheduling.api.define.TransportPathConstants;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ICalculateTransportPathService;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.FeedbackDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;
import com.deppon.foss.module.transfer.stock.api.define.StockConstants;
import com.deppon.foss.module.transfer.stock.api.server.service.IStockService;
import com.deppon.foss.module.transfer.stock.api.shared.domain.StockEntity;
import com.deppon.foss.module.transfer.unload.api.server.dao.IPDAUnloadTaskDao;
import com.deppon.foss.module.transfer.unload.api.server.dao.IUnloadTaskDao;
import com.deppon.foss.module.transfer.unload.api.server.service.ISCPDAUnloadTaskService;
import com.deppon.foss.module.transfer.unload.api.server.service.IUnloadTaskService;
import com.deppon.foss.module.transfer.unload.api.shared.define.ChangeLabelGoodsConstants;
import com.deppon.foss.module.transfer.unload.api.shared.define.UnloadConstants;
import com.deppon.foss.module.transfer.unload.api.shared.domain.ChangeLabelGoodsEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadBillDetailEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadSerialNoDetailEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadTaskEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadWaybillDetailEntity;
import com.deppon.foss.module.transfer.unload.api.shared.dto.PDAUnloadAsyncBillMsgDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.UnloadBillDetailDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.UnloadGoodsSerialDetailDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.UnloadSerialNoDetailDto;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

import javax.annotation.Resource;


public class SCPDAUnloadTaskService implements ISCPDAUnloadTaskService {

    static final Logger logger = LoggerFactory.getLogger(SCPDAUnloadTaskService.class);

    private IBillNumService billNumService;

    public void setBillNumService(IBillNumService billNumService) {
        this.billNumService = billNumService;
    }

    /**
     * The pda load dao.
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
     * The unload task dao.
     */
    IUnloadTaskDao unloadTaskDao;

    /**
     * Sets the unload task dao.
     *
     * @param unloadTaskDao the new unload task dao
     */
    public void setUnloadTaskDao(IUnloadTaskDao unloadTaskDao) {
        this.unloadTaskDao = unloadTaskDao;
    }

    /**
     * The pda unload task dao.
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
     * The org administrative info service.
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
     * The pda common service.
     */
    public IPDACommonService pdaCommonService;

    /**
     * Sets the pda common service.
     *
     * @param pdaCommonService the new pda common service
     */
    public void setPdaCommonService(IPDACommonService pdaCommonService) {
        this.pdaCommonService = pdaCommonService;
    }

    /**
     * The unload task service.
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
     * The porter service.
     */
    private IPorterService porterService;

    /**
     * Sets the porter service.
     *
     * @param porterService the new porter service
     */
    public void setPorterService(IPorterService porterService) {
        this.porterService = porterService;
    }

    /**
     * The load and unload squad service.
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
     * The employee service.
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
     */
    private IWaybillManagerService waybillManagerService;

    /**
     * Sets the waybill manager service.
     *
     * @param waybillManagerService the new waybill manager service
     */
    public void setWaybillManagerService(
            IWaybillManagerService waybillManagerService) {
        this.waybillManagerService = waybillManagerService;
    }


    private IWaybillRfcService waybillRfcService;

    /**
     * Sets the waybill rfc service.
     *
     * @param waybillRfcService the new waybill rfc service
     */
    public void setWaybillRfcService(IWaybillRfcService waybillRfcService) {
        this.waybillRfcService = waybillRfcService;
    }

    /**
     * The stock service.
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

    @Resource
    public IProductService productService4Dubbo;
    /**
     * Sets the product service.
     * @param productService the new product service
     */
//	public void setProductService(IProductService productService) {
//		this.productService = productService;
//	}


    /**
     * 查询已分配卸车任务：未开始、进行中.
     *
     * @param vehicleNo the vehicleNo
     * @return
     * @author foss-hongwy  218427
     * @date 2015-05-08 下午14:26:27
     * @see com.deppon.foss.module.transfer.pda.api.server.service.ISCPDAUnloadTaskService#pdaQueryAssignedSCUnloadTask(java.lang.String)
     */
    @Override
    @Transactional
    public List<SCPDAAssignUnloadTaskEntity> pdaQueryAssignedSCUnloadTask(
            String vehicleNo) {
        Map<String, String> condition = new HashMap<String, String>();
        condition.put("vehicleNo", vehicleNo);
        List<SCPDAAssignUnloadTaskEntity> pdaAssignUnloadTasks = pdaUnloadTaskDao.pdaQueryAssignedSCUnloadTask(condition);
        return pdaAssignUnloadTasks;
    }

    /**
     * 建立卸车任务.
     *
     * @param unloadTask the unload task
     * @return the list
     * @author hongwy-218427
     * @date 2015-04-18下午3:36:50
     */
    //创建卸车任务
    public UnloadTaskResultDto createLoadTask(UnloadTaskDto unloadTask) {

        PDATaskEntity pdaEntity = new PDATaskEntity();
        // 卸车任务单据明细DTO
        List<UnloadBillDetailDto> unloadBills;
        List<String> billNos = new ArrayList<String>();
        UnloadTaskEntity unloadTaskEntity;
        String unloadTaskNo;
        LoaderParticipationEntity creator;
        List<LoaderDto> loaderCodes = new ArrayList<LoaderDto>();
        //如果任务编号不为空，则下拉卸车任务，更新PDA任务表
        if (unloadTask != null && StringUtils.isNotBlank(unloadTask.getTaskNo())) {
            //卸车任务编号
            unloadTaskNo = unloadTask.getTaskNo();
            // 根据卸车任务编号获取卸车任务 from tfr.t_opt_unload_task
            unloadTaskEntity = pdaUnloadTaskDao.querySCUnloadTaskByTaskNo(unloadTaskNo);
            //若卸车任务状态为卸车中，则可以下拉卸车任务
            //卸车中   卸车任务  t.task_state 卸车任务状态 三种类型：UNLOADING FINISHED  CANCELED
            if (unloadTaskEntity != null && UnloadConstants.UNLOAD_TASK_STATE_UNLOADING.equals(unloadTaskEntity.getTaskState())) {
                // 装卸车人员参与情况
                LoaderParticipationEntity loaderQC = new LoaderParticipationEntity();
                loaderQC.setTaskId(unloadTaskEntity.getId());
                // 根据任务ID查询理货员 --装卸车人员参与情况 from tfr.t_opt_loader_participation p
                List<LoaderParticipationEntity> loaders = pdaUnloadTaskDao.queryLoaderByTaskId(loaderQC);
                creator = new LoaderParticipationEntity();
                if (CollectionUtils.isNotEmpty(loaders)) {
                    LoaderDto loaderCode;
                    //  装卸车人员参与情况-->>理货员
                    for (LoaderParticipationEntity loader : loaders) {
                        loaderCode = new LoaderDto();
                        // 理货员工号
                        loaderCode.setLoaderCode(loader.getLoaderCode());
                        // 标识
                        loaderCode.setFlag(loader.getFlag());
                        loaderCodes.add(loaderCode);
                        // 是否建立任务理货员
                        if (FossConstants.YES.equals(loader.getBeCreator())) {
                            creator = loader;
                        }
                    }
                }
                // 218427 hwy卸车任务单据明细DTO  根据卸车任务ID来查询卸车任务单据明细 from tfr.t_opt_unload_bill_detail
                unloadBills = pdaUnloadTaskDao.queryUnloadBillsByTaskId(unloadTaskEntity.getId());
                // pda参与装卸车情况
                pdaEntity.setBeCreator(FossConstants.NO);
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
            } else {

                throw new TfrBusinessException("单据编号为空");
            }

            //是否建立任务PDA为是
            pdaEntity.setBeCreator(FossConstants.YES);

            //查询卸车单据 218427 hongwy 二程接驳卸货
            unloadBills = pdaUnloadTaskDao.querySCUnloadBillsByBillNo(unloadTask.getBillNos());
            //插入二程接驳卸车任务 到达时间 218427 hongwy 二程接驳
//			pdaUnloadTaskDao.updateSCArrivalTime(unloadTask.getBillNos());

            //单据不为空，且查询出的单据数量为查询条件中的单据数量
            if (CollectionUtils.isNotEmpty(unloadBills) && unloadBills.size() == unloadTask.getBillNos().size()) {
                UnloadBillDetailEntity unloadBillEntity;
                List<UnloadBillDetailEntity> unloadBillEntitys = new ArrayList<UnloadBillDetailEntity>();
                String taskId = UUIDUtils.getUUID();
                String unloadType = null;
                double weightTotal = 0.0;
                double volumeTotal = 0.0;
                for (UnloadBillDetailDto unloadBill : unloadBills) {
                    billNos.add(unloadBill.getBillNo());
                    //到达单据状态为已分配
                    if (TaskTruckConstant.BILL_ASSIGN_STATE_UNASSIGN.equals(unloadBill.getBillState())) {
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
                        unloadBillEntitys.add(unloadBillEntity);
                        weightTotal = weightTotal + unloadBill.getWeight().doubleValue();
                        volumeTotal = volumeTotal + unloadBill.getVolume().doubleValue();
                        unloadType = unloadBill.getBusinessType();

                    } else {//若到达单据状态为未分配、卸车中或卸车完成，则不能建立卸车任务
                        throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_INVALID_UNLAOD_BILL_MESSAGECODE);
                    }
                }
                //tfr.T_OPT_UNLOAD_BILL_DETAIL
                pdaUnloadTaskDao.insertUnloadBillDetails(unloadBillEntitys);
                unloadTaskEntity = new UnloadTaskEntity();
                /**判断货物是否扫描入库,为Y时为扫描入库，为N时为提交时job异步入库，ISSUE-3317要求全部扫描入库,所以建立任务时全部保存为扫描入库*/
                unloadTaskEntity.setBeScanInstock(FossConstants.YES);
                //调用综合接口查询出发部门是否为外场
                OrgAdministrativeInfoEntity unloadOrg = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(unloadTask.getCreateOrgCode());

                if (unloadOrg == null) {
                    throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_INVALID_ORG_MESSAGECODE);
                }

                unloadTaskEntity.setUnloadOrgCode(unloadOrg.getCode());
                unloadTaskEntity.setUnloadOrgName(unloadOrg.getName());
//				unloadTaskNo = tfrCommonService.generateSerialNumberRequireNew(TfrSerialNumberRuleEnum.XC,unloadTaskEntity.getUnloadOrgCode());
                unloadTaskNo = billNumService.generateUnLoadTaskNo(unloadTaskEntity.getUnloadOrgCode());

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

                unloadTaskEntity.setBeException(FossConstants.NO);
                //插入卸车任务
                unloadTaskDao.addUnloadTaskBasicInfo(unloadTaskEntity);
                //理货员
                LoaderParticipationEntity loader;
                PorterEntity porter;
                List<LoaderParticipationEntity> loaders = new ArrayList<LoaderParticipationEntity>();
                boolean findCreate = false;
                for (LoaderDto loaderCode : unloadTask.getLoaderCodes()) {
                    loader = new LoaderParticipationEntity();
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
                    if (loaderCode.getLoaderCode().equals(unloadTask.getOperatorCode())) {
                        if (!findCreate) {
                            loader.setBeCreator(FossConstants.YES);
                            findCreate = true;
                        }
                    }
                    porter = porterService.queryPorterByEmpCode(loaderCode.getLoaderCode());
                    //调用综合接口查询理货员名称、理货员所属装卸车队
                    if (porter != null) {
                        loader.setLoaderName(porter.getEmpName());
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
                }
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
            } else {
                //单据为空，或查询出的单据数量不等于查询条件中的单据数量
                throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_INVALID_UNLAOD_BILL_MESSAGECODE);
            }
        }
        //插入卸车PDA
        pdaEntity.setTaskNo(unloadTaskNo);
        pdaEntity.setDeviceNo(unloadTask.getDeviceNo());
        pdaEntity.setId(UUIDUtils.getUUID());
        pdaEntity.setJoinTime(new Date());
        // 218427 hwy卸车任务类型：二程接驳卸车
        pdaEntity.setTaskType(LoadConstants.LOADER_PARTICIPATION_SC_UNLOAD);

        // 218427 hwy记录PDA装卸车任务tfr.t_opt_pda_task
        pdaLoadDao.insertPDATask(pdaEntity);
        UnloadTaskResultDto unloadTaskResultDto = new UnloadTaskResultDto();
        if (creator != null) {
            unloadTaskResultDto.setCreatorCode(creator.getLoaderCode());
            unloadTaskResultDto.setCreatorName(creator.getLoaderName());
        }
        unloadTaskResultDto.setLoaders(loaderCodes);
        unloadTaskResultDto.setTaskNo(unloadTaskNo);

        pdaUnloadTaskDao.updateAssignSCUnloadTaskStateByState(billNos, LoadConstants.HANDOVERBILL_STATE_ALREADY_ARRIVE
                , TaskTruckConstant.BILL_ASSIGN_STATE_ASSIGNED);
        logger.error("建立卸车任务结束:" + unloadTaskNo);
        return unloadTaskResultDto;

    }

    /**
     * 刷新卸车任务.
     *
     * @param taskNo the task no
     * @return the list
     * @author 218427-hongwy
     * @date 2015-04-20 下午16:27:29
     */
    public List<UnloadGoodsDetailDto> refrushUnloadTaskDetail(String taskNo) {

        logger.error("刷新卸车任务开始:" + taskNo);
        List<UnloadBillDetailDto> unloadBills;
        UnloadTaskEntity unloadTaskEntity;
        //查询卸车任务
        unloadTaskEntity = pdaUnloadTaskDao.queryUnloadTaskByTaskNo(taskNo);

        //若卸车任务状态为卸车中，则可以下拉卸车任务   卸车中UNLOADING
        if (unloadTaskEntity != null && UnloadConstants.UNLOAD_TASK_STATE_UNLOADING.equals(unloadTaskEntity.getTaskState())) {
            //卸车任务单据明细DTO
            unloadBills = pdaUnloadTaskDao.queryUnloadBillsByTaskId(unloadTaskEntity.getId());
            LoaderParticipationEntity loaderQC = new LoaderParticipationEntity();
            loaderQC.setTaskId(unloadTaskEntity.getId());
            loaderQC.setBeCreator(FossConstants.YES);
            //根据任务ID查询理货员
            List<LoaderParticipationEntity> loaders = pdaUnloadTaskDao.queryLoaderByTaskId(loaderQC);
            LoaderParticipationEntity creator = new LoaderParticipationEntity();
            if (CollectionUtils.isNotEmpty(loaders)) {
                creator = loaders.get(0);
            }
            //  二程接驳卸货
            List<UnloadGoodsDetailDto> result = this.querySCUnloadTaskDetail(unloadBills, unloadTaskEntity.getUnloadTaskNo(), creator, null, null, null, unloadTaskEntity.getId());
            StringBuilder s = new StringBuilder();
            s.append("刷新卸车任务");
            if (CollectionUtils.isNotEmpty(result)) {
                for (UnloadGoodsDetailDto d : result) {
                    s.append("任务号：" + d.getTaskNo());
                    s.append("运单号:" + d.getWayBillNo());
                    //s.append("更改状态:"+d.getModifyState());
                    s.append("贵重物品:" + d.getIsValue());
                    if (CollectionUtils.isNotEmpty(d.getSerialNos())) {
                        for (PDAGoodsSerialNoDto ss : d.getSerialNos()) {
                            s.append("流水号:");
                            s.append(ss.getSerialNo());
                            s.append("代办事项:" + ss.getIsToDoList());
                            s.append("打木架：" + ss.getIsUnPacking());
                        }
                    }
                    s.append("/&n");

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

    /**
     * 根据二程接驳卸车单据 明细查询卸车明细
     *
     * @param bills              the bills
     * @param taskNo             the task no
     * @param loader             the loader
     * @param loaders            the loaders
     * @param valueGoodsAreaCode the value goods area code
     * @param packGoodsAreaCode  the pack goods area code
     * @param taskId             the task id
     * @return the list
     */
    private List<UnloadGoodsDetailDto> querySCUnloadTaskDetail(List<UnloadBillDetailDto> bills,
                                                               String taskNo, LoaderParticipationEntity loader, List<LoaderDto> loaders, String valueGoodsAreaCode, String packGoodsAreaCode, String taskId) {
        List<UnloadGoodsSerialDetailDto> unloadGoodsList = new ArrayList<UnloadGoodsSerialDetailDto>();
        //二程接驳卸车 hwy-foss 218427
        List<UnloadBillDetailDto> scbills = new ArrayList<UnloadBillDetailDto>();
        //二程接驳卸车单据类型 : SCBILL
        for (UnloadBillDetailDto bill : bills) {
            if (UnloadConstants.BILL_TYPE_SCBILL.equals(bill.getBillType())) {
                scbills.add(bill);
            }
        }
        // 218427-hwy foss  二程接驳卸货
        List<UnloadGoodsSerialDetailDto> scbillGoodsDetail;
        if (CollectionUtils.isNotEmpty(scbills)) {
            scbillGoodsDetail = pdaUnloadTaskDao.querySCUnloadDetail(scbills, taskId);
            if (CollectionUtils.isNotEmpty(scbillGoodsDetail)) {
                unloadGoodsList.addAll(scbillGoodsDetail);
            }
        }
        //查询卸车多货货物
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
                detailTemp.setDestOrgCode(goods.getDestOrgCode());
                detailTemp.setGoodsName(goods.getGoodsName());
                detailTemp.setIsValue(goods.getIsValue());
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
                details.add(detailTemp);
            }
        }
        return details;
    }


    /**
     * 卸车运单扫描（新）
     * 1、更新卸车运单明细表
     * 2、插入卸车流水号明细表
     * 3、插入运单相关信息到运单入库信息表中（job入库运单）
     *
     * @param scanRecord
     * @return String
     * @author 105869-heyongdong
     * @date 2015年3月3日 16:04:20
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
            Map<String, String> condition = new HashMap<String, String>();
            condition.put("wayBillNo", scanRecord.getWayBillNo());
            condition.put("serialNo", scanRecord.getSerialNo());
            condition.put("active", FossConstants.ACTIVE);
            //查询货物是否已签收
            int signedQty = pdaLoadDao.queryWayBillSignedState(condition);
            //货签已签收
            if (signedQty > 0) {
                logger.error("SCPDAUnloadTaskService.unLoadWayBillScan(UnloadScanDetailDto) waybill has signed waybillNo="
                        + scanRecord.getWayBillNo() + ", serialNo=" + scanRecord.getSerialNo());
                return TransferPDADictConstants.SUCCESS;
                //throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_ALREADY_SIGN__MESSAGECODE);
            }

            //扫描件数
            int scanGoodsQtyDif = 0;
            String handleType = UnloadConstants.LABEL_HANDLE_NOTHING;

            String goodsState = scanRecord.getType();
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
                if (FossConstants.YES.equals(unloadTask.getBeScanInstock())
                        && !StringUtils.equals(UnloadConstants.WAYBILL_STATUS_NO, scanRecord.getWayBillStatus())
                        && !UnloadConstants.UNLOAD_GOODS_STATE_MORE.equals(scanRecord.getType())) {
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

                    //入库部门为营业部门 二程接驳 hwy 218427
                    String customerOrgCode = pdaUnloadTaskDao.querySCUnloadOrgCode(scanRecord.getWayBillNo());

                    if (StringUtil.isNotBlank(customerOrgCode)) {
                        //判定货物卸车部门 二程接驳卸车入库 至对应的到达网点
                        unloadAsyncMsgDto.setOrgCode(customerOrgCode);
                    }
                    LoaderParticipationEntity loaderParticipation = getLoaderParticipationByTaskId(unloadTask.getId());
                    //入库类型 卸车入库
                    unloadAsyncMsgDto.setInStockType(StockConstants.UNLOAD_GOODS_IN_STOCK_TYPE);
                    if (loaderParticipation != null) {
                        //操作人
                        unloadAsyncMsgDto.setOperatorCode(loaderParticipation.getLoaderCode());
                        unloadAsyncMsgDto.setOperatorName(loaderParticipation.getLoaderName());
                    }
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
                //多货
                if (UnloadConstants.UNLOAD_GOODS_STATE_MORE.equals(scanRecord.getType()) || UnloadConstants.UNLOAD_MORE_BILL_NO.equals(scanRecord.getBillNo())) {
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
        } else {
            logger.error("卸车扫描结束" + scanRecord.getType() + " " + scanRecord.getTaskNo() + " " + scanRecord.getBillNo() + " " + scanRecord.getWayBillNo() + " " + scanRecord.getSerialNo());
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

        pdaUnloadTaskDao.updateUnloadScanState(scanRecord.getBillNo());

        return TransferPDADictConstants.SUCCESS;
    }

    /**
     * 重写更新卸车流水号状态方法
     *
     * @param seialNoDetail the seial no detail
     * @param scanState     the scan state
     * @param goodsState    the goods state
     * @param scanTime      the scan time
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

    /**
     * 重写更新卸车运单明细方法，后台校验数据是否进行更新
     *
     * @param wayBIllEntity the way bill entity
     * @param scanRecord    the scan record
     * @param scanGoodsQty  the scan goods qty
     * @param beUnloaded    the be unloaded
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

    //插入扫描流水号明细

    /**
     * Insert unload serial no detail.
     *
     * @param unloadTask      the unload task
     * @param scanRecord      the scan record
     * @param wayBillDetailId the way bill detail id
     * @param goodsState      the goods state
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
        // 运单生效状态 - YES NO 2015.3.27
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

    /**
     * @param taskId
     * @return LoaderParticipationEntity    返回类型
     * getLoaderParticipationByTaskId
     * @Title: getLoaderParticipationByTaskId
     * @Description: 根据卸车任务ID获取卸车任务创建人
     */
    private LoaderParticipationEntity getLoaderParticipationByTaskId(String taskId) {
        return pdaUnloadTaskDao.getLoaderParticipationByTaskId(taskId);
    }

    /**
     * 处理标签.
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
     * Finish unload task
     *
     * @param taskNo      the task no
     * @param loadEndTime the load end time
     * @param deviceNo    the device no
     * @param loaderCode  the loader code
     * @param beException the be exception
     * @param notes       the notes
     * @return the string
     * @author 218427 hwy
     */
    @Transactional
    public String finishSCUnloadTask(String taskNo, Date loadEndTime,
                                     String deviceNo, String loaderCode, String beException, String notes) {
        logger.error("提交卸车任务开始" + taskNo);
        //存放单据编号
        List<String> billNos = new ArrayList<String>();
        UnloadTaskEntity unloadTask = pdaUnloadTaskDao.queryUnloadTaskByTaskNo(taskNo);
        //任务为进行中才能提交
        if (unloadTask != null && UnloadConstants.UNLOAD_TASK_STATE_UNLOADING.equals(unloadTask.getTaskState())) {
            List<UnloadBillDetailDto> unloadBills = pdaUnloadTaskDao.queryUnloadBillsByTaskId(unloadTask.getId());
            for (UnloadBillDetailDto unloadBill : unloadBills) {
                billNos.add(unloadBill.getBillNo());
            }

            //卸车流水号清单
            List<UnloadGoodsSerialDetailDto> unloadList = this.queryUnloadSerialDetail(unloadBills);
            //卸车扫描清单（包括已撤销记录）
            List<UnloadSerialNoDetailDto> scanList = pdaUnloadTaskDao.queryUnloadScanSerialDetailByTaskId(unloadTask.getId());
            //生成少货
            if (CollectionUtils.isNotEmpty(unloadList)) {
                this.generateLackGoods(unloadTask, unloadList, scanList);
            }

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

            //二程接驳卸车任务提交后，状态改变
            pdaUnloadTaskDao.updateAssignSCUnloadTaskStateByState(billNos, LoadConstants.HANDOVERBILL_STATE_ALREADY_INSTOCK
                    , TaskTruckConstant.BILL_ASSIGN_STATE_UNLOADED);

//			try{
//				pdaCommonService.updatePlatformStateByFinishTask(taskNo, new Date());
//			}catch(Exception e){
//				
//			}
            logger.error("提交卸车任务结束" + taskNo);
            return TransferPDADictConstants.SUCCESS;
        } else {
            logger.error(taskNo, TransferPDAExceptionCode.EXCEPTION_INVALID_TASK_MESSAGECODE);

            logger.error("提交卸车任务结束" + taskNo);
            return TransferPDADictConstants.SUCCESS;
        }
    }

    //根据卸车单据 明细查询卸车单据流水号明细

    /**
     * Query unload serial detail.
     *
     * @param bills the bills
     * @return the list
     */
    private List<UnloadGoodsSerialDetailDto> queryUnloadSerialDetail(List<UnloadBillDetailDto> bills) {
        List<UnloadGoodsSerialDetailDto> unloadGoodsList = new ArrayList<UnloadGoodsSerialDetailDto>();

        // hwy 218427 二程接驳卸货
        List<UnloadBillDetailDto> scbills = new ArrayList<UnloadBillDetailDto>();
        for (UnloadBillDetailDto bill : bills) {
            if (StringUtils.equals(UnloadConstants.BILL_TYPE_SCBILL, bill.getBillType())) {
                scbills.add(bill);
            }
        }
        /* 二程接驳卸货*/
        List<UnloadGoodsSerialDetailDto> scGoodsDetail;

        if (CollectionUtils.isNotEmpty(scbills)) {
            scGoodsDetail = pdaUnloadTaskDao.querySCUnloadSerialDetail(scbills);
            if (CollectionUtils.isNotEmpty(scGoodsDetail)) {
                unloadGoodsList.addAll(scGoodsDetail);
            }
        }
        return unloadGoodsList;
    }

    //生成少货

    /**
     * Generate lack goods.
     *
     * @param unloadTask the unload task
     * @param unloadList the unload list
     * @param scanList   the scan list
     */
    private void generateLackGoods(UnloadTaskEntity unloadTask, List<UnloadGoodsSerialDetailDto> unloadList, List<UnloadSerialNoDetailDto> scanList) {
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
                                if (unloadGoods.getHandoverNo().startsWith("B")) {
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
                            if (logger.isInfoEnabled()) {
                                logger.info("SCPDAUnloadTaskService调用计价dubbo接口成功，productService4Dubbo" +
                                        " = {} , product = {} ", productService4Dubbo, product);
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
                            if (unloadGoods.getHandoverNo().startsWith("B")) {
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

    }

    //更新卸车流水号明细

    /**
     * Update unload serial no detail.
     *
     * @param seialNoDetail the seial no detail
     * @param scanState     the scan state
     * @param goodsState    the goods state
     * @param scanTime      the scan time
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


    //扫描后 状态改变   foss-hwy 218427
    @Override
    public int scUnloadState(String taskNo, String billNo) {
        //查询当前卸车任务
        UnloadTaskEntity unloadTask = pdaUnloadTaskDao.queryUnloadTaskByTaskNo(taskNo);
        if (null != unloadTask) {
            LoaderParticipationEntity loaderParticipationEntity = pdaUnloadTaskDao.
                    getLoaderParticipationByTaskId(unloadTask.getId());
            Map<String, Object> condition = new HashMap<String, Object>();
            condition.put("taskNo", taskNo);
            condition.put("billNo", billNo);
            condition.put("loaderCode", loaderParticipationEntity.getLoaderCode());
            //condition.put("time", new Date());
            return pdaUnloadTaskDao.updateState(condition);
        } else {
            logger.error("SCPDAUnloadTaskService.scUnloadState(String, String) 任务为空 taskNo=" + taskNo);
            return 0;
            //throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_INVALID_TASK_MESSAGECODE);
        }

    }


}
