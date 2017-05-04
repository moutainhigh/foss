package com.deppon.foss.module.transfer.unload.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.deppon.foss.module.pickup.pricing.dubbo.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.dubbo.api.shared.domain.ProductEntity;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAdministrativeRegionsService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressSortStationMappingService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IGoodsAreaService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.GoodsAreaEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;


import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.load.api.server.service.IPDACommonService;
import com.deppon.foss.module.transfer.load.api.shared.domain.TrayScanDetaiEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.TrayScanDto;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDAStockService;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDATrayScanService;
import com.deppon.foss.module.transfer.pda.api.shared.define.TransferPDADictConstants;
import com.deppon.foss.module.transfer.pda.api.shared.domain.PDATrayOfflineScanWaybillEntiy;
import com.deppon.foss.module.transfer.pda.api.shared.domain.PDATrayScanDetailEntity;
import com.deppon.foss.module.transfer.pda.api.shared.domain.PDATrayScanTaskEntity;
import com.deppon.foss.module.transfer.pda.api.shared.domain.QueryTrayScanTaskEntity;
import com.deppon.foss.module.transfer.pda.api.shared.dto.ExpressTransferScanDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.PDATrayOfflineScanDto;
import com.deppon.foss.module.transfer.pda.api.shared.exception.TransferPDAExceptionCode;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;
import com.deppon.foss.module.transfer.stock.api.server.service.IStockService;
import com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.StockEntity;
import com.deppon.foss.module.transfer.unload.api.server.dao.IPDATrayScanDao;
import com.deppon.foss.module.transfer.unload.api.shared.define.UnloadConstants;
import com.deppon.foss.module.transfer.unload.api.shared.domain.TrayOfflineScanEntity;
import com.deppon.foss.module.transfer.unload.api.shared.dto.PcakageWayBillDto;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

import javax.annotation.Resource;

/**
 * 引入包
 */

public class PDATrayScanService implements IPDATrayScanService {
    static final Logger logger = LoggerFactory
            .getLogger(PDATrayScanService.class);
    /**
     * The padTrayScanDao
     */
    private IPDATrayScanDao pdaTrayScanDao;

    /**
     * The waybillDao
     */
    private IWaybillManagerService waybillManagerService;
    /**
     * The employee service.
     */
    private IEmployeeService employeeService;

    /**
     * 数据字典
     */
    private IConfigurationParamsService configurationParamsService;

    private IPDAStockService pdaStockService;

    public void setPdaStockService(IPDAStockService pdaStockService) {
        this.pdaStockService = pdaStockService;
    }

    private IAdministrativeRegionsService administrativeRegionsService;


    public void setAdministrativeRegionsService(
            IAdministrativeRegionsService administrativeRegionsService) {
        this.administrativeRegionsService = administrativeRegionsService;
    }

    public void setConfigurationParamsService(
            IConfigurationParamsService configurationParamsService) {
        this.configurationParamsService = configurationParamsService;
    }

    /**
     * Sets the employee service.
     *
     * @param employeeService the new employee service
     */
    public void setEmployeeService(IEmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public void setPdaTrayScanDao(IPDATrayScanDao pdaTrayScanDao) {
        this.pdaTrayScanDao = pdaTrayScanDao;
    }


    public void setWaybillManagerService(IWaybillManagerService waybillManagerService) {
        this.waybillManagerService = waybillManagerService;
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
    public void setOrgAdministrativeInfoService(
            IOrgAdministrativeInfoService orgAdministrativeInfoService) {
        this.orgAdministrativeInfoService = orgAdministrativeInfoService;
    }

    /**
     * The sale department service.
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
     * 综合管理 货区service
     */
    private IGoodsAreaService goodsAreaService;

    /**
     * 设置 综合管理 货区service.
     *
     * @param goodsAreaService the new 综合管理 货区service
     */
    public void setGoodsAreaService(IGoodsAreaService goodsAreaService) {
        this.goodsAreaService = goodsAreaService;
    }

    /**
     * 产品接口
     */
    @Resource
    private IProductService productService4Dubbo;

//	public void setProductService(IProductService productService) {
//		this.productService = productService;
//	}

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

    /**
     * 快递分拣目的站映射接口
     */
    private IExpressSortStationMappingService expressSortStationMappingService;

    /**
     * 快递分拣目的站映射service.
     *
     * @param expressSortStationMappingService the new 快递分拣目的站映射service
     */
    public void setExpressSortStationMappingService(
            IExpressSortStationMappingService expressSortStationMappingService) {
        this.expressSortStationMappingService = expressSortStationMappingService;
    }

    /**
     * 建立托盘扫描任务.
     *
     * @param trayScanTask
     * @return the String
     * @author 105869-foss-heyongdong
     * @date 2013-8-6 11:44:43
     * @see com.deppon.foss.module.transfer.pda.api.server.service.IPDATrayScanService#createTrayScanTask(com.deppon.foss.module.transfer.pda.api.shared.domain.PDATrayScanTaskEntity)
     */
    @Override
    @Transactional
    public String createTrayScanTask(PDATrayScanTaskEntity trayScanTask) {
        logger.error("创建托盘任务：" + trayScanTask.getTaskNo());

        /** 创建查询任务号的查询条件 */
        QueryTrayScanTaskEntity queryCondtion = new QueryTrayScanTaskEntity();

        /** 新建托盘任务 **/
        TrayScanDto trayScanDto = new TrayScanDto();

        // 调用综合接口查询出发部门是否为外场
        String unloadOrgCode = getCenterCode(trayScanTask.getBindingDept());
        // 设置查询条件外场
        queryCondtion.setOrgCode(unloadOrgCode);
        // 设置绑定人部门所属外场
        trayScanDto.setOutfieldCode(unloadOrgCode);

        /******************* 设置托盘任务 **********************/
        // 设置任务id
        trayScanDto.setId(UUIDUtils.getUUID());
        // 设置绑定人工号
        trayScanDto.setBindingCode(trayScanTask.getBindingCode());

        // 设置任务号
        trayScanDto.setTaskNo(trayScanTask.getTaskNo());
        /**
         * 设置绑定人姓名
         */

        EmployeeEntity emp = employeeService
                .queryEmployeeByEmpCode(trayScanTask.getBindingCode());
        if (emp != null) {
            // 设置绑定人姓名
            trayScanDto.setBindingName(emp.getEmpName());
        }
        // 设置绑定人部门
        trayScanDto.setBindingDeptCode(trayScanTask.getBindingDept());

        // 设置托盘创建时间
        trayScanDto.setTraytaskCreatTime(trayScanTask.getTraytaskCreatTime());
        // 设置任务状态
        /**
         * @author 105795
         * @date 2015-04-13
         * @ 增加托盘操作类型，HANDSCAN 表示手动拉车，UNSCAN 表示叉车分货
         * */
        if (StringUtil.isNotEmpty(trayScanTask.getOperationType())) {
            trayScanDto.setStatu(trayScanTask.getOperationType());
        } else {
            trayScanDto.setStatu(UnloadConstants.UNLOAD_TRAYSCAN_TASK_STATE_UNSCAN);
        }
        /**
         * @author wqh
         * @date 2013-12-24
         * */
        //设置卸车任务
        if (StringUtils.isNotEmpty(trayScanTask.getUnloadTaskNo())) {
            trayScanDto.setUnloadTaskNo(trayScanTask.getUnloadTaskNo());
        } else {
            trayScanDto.setUnloadTaskNo("");
        }

        /********************* 设置托盘任务明细 ************/
        // 创建新增时的list
        List<TrayScanDetaiEntity> addnewTaskDetails = new ArrayList<TrayScanDetaiEntity>();
        // 获取明细
        List<PDATrayScanDetailEntity> trayTaskDetails = trayScanTask
                .getTrayScanDetails();
        List<PDATrayScanDetailEntity> trayTaskDetailsAll = new ArrayList<PDATrayScanDetailEntity>();
        /**
         *update by 205109——zenghaibin 2014-11-06 16:53
         ***/
        for (PDATrayScanDetailEntity allTrayTaskDetail : trayTaskDetails) {
            String packageNo = allTrayTaskDetail.getWaybillNo();
            if (!StringUtils.isBlank(packageNo) && StringUtils.equals(packageNo.substring(0, 1), "B")) {//是包
                //如果是包,查询包内运单明细（运单号和流水号）
                List<PcakageWayBillDto> pcakageWayBillList = pdaTrayScanDao.queryPackageDetail(packageNo);
                if (pcakageWayBillList != null && pcakageWayBillList.size() > 0) {
                    for (PcakageWayBillDto dto : pcakageWayBillList) {
                        PDATrayScanDetailEntity pdaEntity = new PDATrayScanDetailEntity();
                        pdaEntity.setWaybillNo(dto.getWayBillNo());
                        pdaEntity.setSerialNo(dto.getSerialNo());
                        pdaEntity.setPackageNo(packageNo);
                        trayTaskDetailsAll.add(pdaEntity);//添加运单明细
                    }
                }
            } else {
                trayTaskDetailsAll.add(allTrayTaskDetail);
            }

        }
        /**直接赋值给trayTaskDetails**/

        trayTaskDetails = trayTaskDetailsAll;
        //解绑任务号集合
        List<String> oldTaskNos = new ArrayList<String>();
        for (PDATrayScanDetailEntity newTaskDetail : trayTaskDetails) {
            TrayScanDetaiEntity trayScanDetail = new TrayScanDetaiEntity();
            // 运单号
            String waybillNo = newTaskDetail.getWaybillNo();
            // 流水号
            String serialNo = newTaskDetail.getSerialNo();
            String packageNo = newTaskDetail.getPackageNo();
            trayScanDetail.setId(UUIDUtils.getUUID());
            // 设置绑定托盘任务的id
            trayScanDetail.setTrayScanTaskNo(trayScanTask.getTaskNo());
            trayScanDetail.setPackageNo(packageNo);
            // 设置绑定托盘的运单
            trayScanDetail.setWaybillNo(waybillNo);
            // 设置绑定托盘的流水号
            trayScanDetail.setSerialNo(serialNo);
            //设置创建时间    ---配合BI
            trayScanDetail.setCreateDate(trayScanTask.getTraytaskCreatTime());
            // 设置绑定托盘的运单号对应的目的站
            WaybillEntity waybillInfo = waybillManagerService.queryWaybillBasicByNo(waybillNo);
            if (waybillInfo != null) {
                //通过运单里的提货网点code在组织表中查询其简称
                String destdept = pdaTrayScanDao.queryOrgSimaleName(waybillInfo.getCustomerPickupOrgCode());
                // 设置目的站名称
                if (StringUtils.isNotBlank(destdept)) {
                    trayScanDetail.setDestDeptName(destdept);
                } else {
                    //查询不到目的站可能偏线的，直接从运单里面取
                    trayScanDetail.setDestDeptName(waybillInfo.getTargetOrgCode());
                }

            }
            /********************************** 设置托盘任务查询条件 *****************************/
            // 设置查询条件运单号
            queryCondtion.setWaybillNo(waybillNo);

            // 设置查询条件流水号
            queryCondtion.setSerialNo(serialNo);

            // 设置查询条件绑定人工号
            queryCondtion.setBindingCode(trayScanTask.getBindingCode());
            // 设置查询时间
            //Date date = trayScanTask.getTraytaskCreatTime();
            //Date queryDate = new Date(date.getTime() - 1000l * 24 * 60 * 60 * 1/ 2);
            //queryCondtion.setQueryTime(queryDate);
            /** 查询任务 **/
            List<PDATrayScanTaskEntity> padTrayScanTasks = pdaTrayScanDao.queryTrayScanNO(queryCondtion);

            /**
             * 1.查询托盘任务通过运单号、流水号、外场、绑定人工号、任务生成时间12小时以内
             * 2.如果查询到，判断任务号大小，如果查询到的任务号小于当前任务号，则解除查询到的任务否则返回不新增当前任务
             * 3.查询不到则新增当前任务明细
             * **/
            if (padTrayScanTasks != null && padTrayScanTasks.size() > 0) {
                // 获取任务号
                PDATrayScanTaskEntity cancelTask = padTrayScanTasks.get(0);
                // 数据库中的任务号
                String oldtaskcode = cancelTask.getTaskNo();
                // 传递过来的新的任务号
                String newtaskcode = trayScanTask.getTaskNo();
                if (StringUtils.isNotBlank(oldtaskcode)
                        && StringUtils.isNotBlank(newtaskcode)) {

                    long code1 = Long.parseLong(oldtaskcode.substring(
                            oldtaskcode.length() - ConstantsNumberSonar.SONAR_NUMBER_17, oldtaskcode.length()));

                    long code2 = Long.parseLong(newtaskcode.substring(
                            newtaskcode.length() - ConstantsNumberSonar.SONAR_NUMBER_17, newtaskcode.length()));

                    if (StringUtils.equals(oldtaskcode, newtaskcode)) {
                        // 数据异常
                        logger.error("运单号：" + waybillNo + "流水号：" + serialNo
                                + "任务号：" + newtaskcode + "已存在！");
                        continue;
                    } else if (code2 > code1) {
                        logger.error("创建任务:" + newtaskcode + "时解绑任务："
                                + oldtaskcode);

                        // 解除任务的绑定关系,sql 放在最后面执行了，避免行锁
                        oldTaskNos.add(oldtaskcode);


                    } else {
                        /**
                         * 如果当前任务小于查询到的任务 1、查询当前任务在数据库中是否存在，存在则解绑当前任务 2、如果不存在
                         * **/
                        logger.error("当前任务号：" + code2 + "先于任务号：" + code1
                                + "生成，" + "运单：" + waybillNo + "流水号：" + serialNo
                                + "未插入成功!");
                        // 不需要新增
                        return TransferPDADictConstants.SUCCESS;
                    }
                }
            }
            // 插入卸车任务明细
            addnewTaskDetails.add(trayScanDetail);
        }
        //获取叉车票数
        int forkliftCount = getForkliftCounts(trayTaskDetails, unloadOrgCode);
        //叉车票数等于0说明有未入库的运单
        if (forkliftCount != 0) {
            //设置叉车票数
            trayScanDto.setForkliftCount(forkliftCount);
            //设置生成叉车工作量的状态
            trayScanDto.setBeCreateWork(FossConstants.YES);
        } else {
            //设置叉车票数
            trayScanDto.setForkliftCount(1);
            //设置没有生成叉车工作量的状态
            trayScanDto.setBeCreateWork(FossConstants.NO);
        }

        for (String oldtaskcode : oldTaskNos) {
            pdaTrayScanDao.cancelTrayScanTask(oldtaskcode);
        }
        //没有明细是则不保存任务
        if (addnewTaskDetails != null && addnewTaskDetails.size() > 0) {
            // 创建任务
            pdaTrayScanDao.createTrayScanTask(trayScanDto);
            // 创建托盘任务明细
            pdaTrayScanDao.createTrayScanDetail(addnewTaskDetails);
        } else {
            logger.error("任务号：" + trayScanTask.getTaskNo() + "重复!");
        }

        logger.error("创建托盘任务结束：" + trayScanTask.getTaskNo());

        return TransferPDADictConstants.SUCCESS;
    }

    /**
     * 查询托盘扫描任务.
     *
     * @param querytask
     * @return the PDATrayScanTaskEntity
     * @author 105869-foss-heyongdong
     * @date 2013-8-6 11:44:43
     * @see com.deppon.foss.module.transfer.pda.api.server.service.IPDATrayScanService#queryTrayScanDetail(java.lang.String)
     */
    @Override
    public PDATrayScanTaskEntity queryTrayScanDetail(
            QueryTrayScanTaskEntity querytask) {
        // 创建查询条件
        QueryTrayScanTaskEntity queryCondtion = new QueryTrayScanTaskEntity();
        // 调用综合接口查询出发部门是否为外场
        OrgAdministrativeInfoEntity unloadOrg = orgAdministrativeInfoService
                .queryOrgAdministrativeInfoByCode(querytask.getOrgCode());
        if (unloadOrg != null) {
            if (FossConstants.YES.equals(unloadOrg.getSalesDepartment())) {
                SaleDepartmentEntity saleDetp = saleDepartmentService
                        .querySaleDepartmentByCode(unloadOrg.getCode());
                if (saleDetp != null
                        && FossConstants.YES.equals(saleDetp.getStation())) {
                    unloadOrg = orgAdministrativeInfoService
                            .queryOrgAdministrativeInfoByCode(saleDetp
                                    .getTransferCenter());
                }
            } else {
                unloadOrg = pdaCommonService.getCurrentOutfieldCode(querytask
                        .getOrgCode());
            }
        }
        if (unloadOrg == null) {
            throw new TfrBusinessException(
                    TransferPDAExceptionCode.EXCEPTION_INVALID_ORG_MESSAGECODE);
        }
        String outfieldCode = unloadOrg.getCode();
        // 设置外场
        queryCondtion.setOrgCode(outfieldCode);
        if (!StringUtils.isBlank(querytask.getWaybillNo()) && StringUtils.equals(querytask.getWaybillNo().substring(0, 1), "B")) {
            //如果是包
            queryCondtion.setPackageNo(querytask.getWaybillNo());
        } else {
            // 设置运单号
            queryCondtion.setWaybillNo(querytask.getWaybillNo());
            // 设置流水号
            queryCondtion.setSerialNo(querytask.getSerialNo());
        }
        // 获取托盘任务
        List<PDATrayScanTaskEntity> padTrayScanTasks = pdaTrayScanDao
                .queryTrayScanNO(queryCondtion);
        // 返回给PDA的托盘任务
        PDATrayScanTaskEntity pdaTask = null;

        //当前外场派送部所在区域
        String deliverDeptArea = "自提区";
        //自提类型集合
        Set<String> pickUpSet = new HashSet<String>();
        pickUpSet.add("SELF_PICKUP_FREE_AIR");
        pickUpSet.add("AIRPORT_PICKUP");
        pickUpSet.add("SELF_PICKUP_AIR");
        pickUpSet.add("SELF_PICKUP");
        pickUpSet.add("INNER_PICKUP");
        //派送类型集合
        Set<String> deliverSet = new HashSet<String>();
        deliverSet.add("DELIVER_INGA");
        deliverSet.add("DELIVER_NOUP_AIR");
        deliverSet.add("DELIVER_AIR");
        deliverSet.add("DELIVER_NOUP");
        deliverSet.add("DELIVER");
        deliverSet.add("DELIVER_UP_AIR");
        deliverSet.add("DELIVER_INGA_AIR");
        deliverSet.add("DELIVER_UP");


        if (padTrayScanTasks != null && padTrayScanTasks.size() > 0) {
            // 设置返回给PDA的托盘任务
            pdaTask = padTrayScanTasks.get(0);
            // 任务号
            String taskNo = pdaTask.getTaskNo();

            // 创建查询条件
            QueryTrayScanTaskEntity querydetail = new QueryTrayScanTaskEntity();
            querydetail.setTaskNo(taskNo);
            // 查询明细
            List<PDATrayScanDetailEntity> trayScandDetails = pdaTrayScanDao.queryTrayScanPackageDetail(querydetail);

            if (trayScandDetails != null && trayScandDetails.size() > 0) {
                for (int i = 0; i < trayScandDetails.size(); i++) {
                    //如果是包
                    if (trayScandDetails.get(i).getPackageNo() != null &&
                            !StringUtils.isBlank(trayScandDetails.get(i).getPackageNo())) {

                        trayScandDetails.get(i).setAdminiRegions("");
                        trayScandDetails.get(i).setDestDeptName("");
                        trayScandDetails.get(i).setDestDept("");
                        trayScandDetails.get(i).setGoodAreaCode("");
                        trayScandDetails.get(i).setGoodAreaName("");
                        trayScandDetails.get(i).setWaybillInfo("");
//						trayScandDetails.get(i).setSerialCount(0);
                        trayScandDetails.get(i).setWaybillNo(trayScandDetails.get(i).getPackageNo());
                        trayScandDetails.get(i).setTranProperty("快递");

                    } else {
                        // 设置运单号
                        String waybillNo = trayScandDetails.get(i).getWaybillNo();
                        // 流水号
                        String serialNo = trayScandDetails.get(i).getSerialNo();
                        // 设置备注
                        String notes = null;
                        boolean isWood = false;
                        boolean isPrecious = false;

                        /**
                         * 查询是否代打木架
                         * **/
                        QueryTrayScanTaskEntity queryIsWooded = new QueryTrayScanTaskEntity();
                        // 设置条件运单号
                        queryIsWooded.setWaybillNo(waybillNo);
                        // 设置查询条件流水号
                        queryIsWooded.setSerialNo(serialNo);
                        // 设置查询条件外场
                        queryIsWooded.setOrgCode(outfieldCode);
                        long isWooded = pdaTrayScanDao.queryIsWooded(queryIsWooded);
                        if (isWooded > 0) {
                            isWood = true;
                        }

                        /**
                         * 查询是否贵重物品
                         * */
                        //String isPreciousGoods = pdaTrayScanDao.queryIsPreciousGoods(waybillNo);

                        WaybillEntity waybillInfo = waybillManagerService.queryWaybillBasicByNo(waybillNo);

                        // 判断的原因是可能有强卸的货，不一定能查到运单信息
                        if (waybillInfo != null) {
                            if (StringUtils.equals(waybillInfo.getPreciousGoods(),
                                    FossConstants.YES)) {
                                isPrecious = true;
                            }
                            //运输性质code
                            String property = waybillInfo.getProductCode();
                            if (StringUtils.isNotBlank(property)) {
                                String propertyName = pdaTrayScanDao.queryWaybillProperty(property);
                                if (StringUtils.isNotBlank(propertyName)) {
                                    //设置运输性质
                                    trayScandDetails.get(i).setTranProperty(propertyName);
                                }

                            }
                            //重新设置目的站，以防目的站更改
                            String destdept = pdaTrayScanDao.queryOrgSimaleName(waybillInfo.getCustomerPickupOrgCode());

                            if (StringUtils.isNotBlank(destdept)) {
                                trayScandDetails.get(i).setDestDeptName(destdept);
                            } else {
                                //查询不到目的站可能偏线的，直接从运单里面取。坑爹的目的站啊
                                trayScandDetails.get(i).setDestDeptName(waybillInfo.getTargetOrgCode());
                            }
                            //设置开单件数
                            trayScandDetails.get(i).setSerialCount(waybillInfo.getGoodsQtyTotal());
                        }
                        if (isWood == true && isPrecious == false) {
                            //标志打木架
                            notes = "2";
                        } else if (isWood == false && isPrecious == true) {
                            //标志贵重物品
                            notes = "1";
                        } else if (isWood && isPrecious) {
                            //标志 打木架和贵重物品
                            notes = "3";
                        } else {
                            //没有备注了
                            notes = "0";
                        }
                        // 设置备注信息
                        trayScandDetails.get(i).setWaybillInfo(notes);

						/*================设置 库区名称===============*/
                        // 查询是否入库区
                        List<StockEntity> waybillStock = pdaTrayScanDao.queryWaybillStock(waybillNo, serialNo, outfieldCode);
                        if (CollectionUtils.isNotEmpty(waybillStock) && waybillStock.size() > 0) {
                            String goodAreaCode = waybillStock.get(0).getGoodsAreaCode();
                            String goodAreaName = goodsAreaService.queryNameByCode(outfieldCode, goodAreaCode);
                            trayScandDetails.get(i).setGoodAreaName(goodAreaName);
                            trayScandDetails.get(i).setGoodAreaCode(goodAreaCode);


                        } else {
                            List<StockEntity> waybillinStock = pdaTrayScanDao.queryWaybillInStock(waybillNo, serialNo, outfieldCode);
                            if (CollectionUtils.isNotEmpty(waybillStock) && waybillStock.size() > 0) {
                                String goodAreaCode = waybillinStock.get(0).getGoodsAreaCode();
                                String goodAreaName = goodsAreaService.queryNameByCode(outfieldCode, goodAreaCode);
                                trayScandDetails.get(i).setGoodAreaName(goodAreaName);
                                trayScandDetails.get(i).setGoodAreaCode(goodAreaCode);
                            }
                        }
                        //调用综合接口 ,传入参数 提货网点waybillInfo.getCustomerPickupOrgCode()
                        String pickupoutfild = null;
                        if (waybillInfo != null && waybillInfo.getCustomerPickupOrgCode() != null) {
                            pickupoutfild = saleDepartmentService.queryTransferCenterCodeByStationCode(waybillInfo.getCustomerPickupOrgCode());
                        }
                        //如派送库区则设置行政区域
                        if (StringUtils.equals(pickupoutfild, outfieldCode)) {
                            //如果自提则设置派送营业部所属区域
                            if (waybillInfo != null && StringUtils.isNotEmpty(waybillInfo.getReceiveMethod())
                                    && pickUpSet.contains(waybillInfo.getReceiveMethod())) {
                                trayScandDetails.get(i).setAdminiRegions(deliverDeptArea);
                            }
                            //如果派送则设置运单提货区域
                            if (waybillInfo != null && StringUtils.isNotEmpty(waybillInfo.getReceiveMethod())
                                    && StringUtils.isNotEmpty(waybillInfo.getReceiveCustomerDistCode())
                                    && deliverSet.contains(waybillInfo.getReceiveMethod())) {
                                trayScandDetails.get(i).setAdminiRegions(administrativeRegionsService.queryAdministrativeRegionsNameByCode(waybillInfo.getReceiveCustomerDistCode()));
                            }
                        }

                    }
                }

            } else {
                logger.error("查询不到托盘任务：" + taskNo + "的明细！");
                throw new TfrBusinessException(
                        TransferPDAExceptionCode.EXCEPTION_QUERY_TRAYSCANTASK_FAILURE_MESSAGECODE);
            }
            // 设置托盘任务明细
            pdaTask.setTrayScanDetails(trayScandDetails);

        } else {
            logger.error("查询不到运单号：" + querytask.getWaybillNo() + "流水号："
                    + querytask.getSerialNo() + "对应的托盘任务！");
            // 查询托盘任务失败
            // throw new
            // TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_QUERY_TRAYSCANTASK_FAILURE_MESSAGECODE);

        }
        return pdaTask;
    }

    /**
     * 更新叉车扫描任务的信息.
     *
     * @param trayScanTask
     * @return the PDATrayScanTaskEntity
     * @author 105869-foss-heyongdong
     * @date 2013-8-7 10:10:44
     * @see com.deppon.foss.module.transfer.pda.api.server.service.IPDATrayScanService#updateTrayScanTask(com.deppon.foss.module.transfer.pda.api.shared.domain.PDATrayScanTaskEntity)
     */
    @Override
    public String updateTrayScanTask(PDATrayScanTaskEntity trayScanTask) {
        // 新建托盘任务
        TrayScanDto trayScanDto = new TrayScanDto();
        /**
         * 设置叉车司机姓名、部门
         */
        if (trayScanTask == null) {
            logger.error("传递的参数为空！");
            return TransferPDADictConstants.FAIL;
        }
        String forkliftDriverName = "";
        if (StringUtils.isNotBlank(trayScanTask.getForkliftDriverCode())) {
            // 设置叉车司机工号
            trayScanDto.setForkliftDriverCode(trayScanTask.getForkliftDriverCode());
            // 获取人员信息接口
            EmployeeEntity empd = employeeService.queryEmployeeByEmpCode(trayScanTask.getForkliftDriverCode());
            if (empd != null) {
                // 设置叉车司机姓名
                forkliftDriverName = empd.getEmpName();
                trayScanDto.setForkliftDriverName(forkliftDriverName);
            }
        } else {
            logger.error("叉车司机工号为空！");
        }
        if (StringUtils.isNotBlank(trayScanTask.getForkliftDept())) {
            // 设置叉车司机部门
            trayScanDto.setForkliftDepartmentCode(trayScanTask.getForkliftDept());
        } else {
            logger.error("叉车司机部门为空！");
        }

        // 设置叉车扫描时间
        if (trayScanTask.getTrayscanTime() != null) {
            trayScanDto.setTrayscanTime(trayScanTask.getTrayscanTime());
        }
        /**入库位 在更新托盘任务状态前更新库存信息*/
        logger.error("在线扫描入库位开始！");
        inStockPosition(trayScanTask.getTaskNo(), trayScanTask.getForkliftDriverCode(), forkliftDriverName, trayScanTask.getTrayscanTime());
        logger.error("在线扫描入库位结束！");
        // 设置任务状态
        trayScanDto.setStatu(UnloadConstants.UNLOAD_TRAYSCAN_TASK_STATE_SCANNED);
        // 设置任务号
        trayScanDto.setTaskNo(trayScanTask.getTaskNo());
        // 更新任务
        pdaTrayScanDao.updateTrayScanTask(trayScanDto);
        return TransferPDADictConstants.SUCCESS;
    }

    /**
     * 统计叉车票数
     *
     * @param trayTaskDetails
     * @author 105869-foss-heyongdong
     * @date 2013-8-12 15:10:27
     */
    private int getForkliftCounts(
            List<PDATrayScanDetailEntity> trayTaskDetails, String outfieldcode) {
        logger.error("叉车票统计外场：" + outfieldcode);
        // 定义叉车票数
        int forkliftcount = 0;
        // 派送的叉车票数
        int delivercount = 0;
        //快递叉车票数
        int expressCount = 0;
        //是否存在快递运单
        boolean isExpressWaybill = false;
        // 统计目的站的set存放的是库区编号
        Set<String> destnames = new HashSet<String>();
        // 根据外场说有计算卡普 AB的库区
        List<GoodsAreaEntity> karpABAreaList = goodsAreaService.queryCountingModeGoodsAreaListByOrganizationCode(outfieldcode);
        //用于每个库区统计票数的变量
        Map<String, boolean[]> karpABArea = new HashMap<String, boolean[]>();
        //没有库区对应的实体关系变量
        Map<String, GoodsAreaEntity> areaEntityMap = new HashMap<String, GoodsAreaEntity>();
        for (int i = 0; i < karpABAreaList.size(); i++) {
            boolean[] karpAB = new boolean[UnloadConstants.SONAR_NUMBER_8];
            karpABArea.put(karpABAreaList.get(i).getGoodsAreaCode(), karpAB);
            areaEntityMap.put(karpABAreaList.get(i).getGoodsAreaCode(), karpABAreaList.get(i));
        }

        // 根据部门查询货区
        List<GoodsAreaEntity> goodsAreaList = goodsAreaService
                .queryGoodsAreaListByType(outfieldcode,
                        DictionaryValueConstants.BSE_GOODSAREA_TYPE_STATION);

        if (CollectionUtils.isNotEmpty(goodsAreaList) && goodsAreaList.size() > 0
                && StringUtils.isNotBlank(goodsAreaList.get(0).getNotes())) {
            /**
             * 存在派送库区，则需要统计派送库区票数。 这一段代码主要是把库区查询出来，以1-3类似为单位存放到一个二维数组中 以便后面比较
             */
            // 获取库区的分区，分区存在于备注中，格式为
            // :[1-3,4-6,7,8<]上海派送库区或者[1-3,4-6,7,8-14]上海派送库区
            GoodsAreaEntity goodsArea = goodsAreaList.get(0);
            String strNotes = goodsArea.getNotes();

            //格式变为：(s001,s003)[1,2,3-5,6-10,11<];(s002)[1,2-5,6,7-8];(ZT)[1<]
            //存储行政区域编码
            Map<String, List<String>> map1 = new HashMap<String, List<String>>();
            //存储
            Map<String, List<String>> map2 = new HashMap<String, List<String>>();
            String[] strNum = strNotes.split(";");
            //有多少条记录
            int keyLen = strNum.length;
            //取arrs中最长的长度，方便建立数组
            int maxLen = 0;
            try {
                for (int i = 0; i < keyLen; i++) {
                    String s = strNum[i];
                    //货区编码
                    List<String> areas = new ArrayList<String>();
                    //存放库位
                    List<String> arrs = new ArrayList<String>();
                    int begin = s.indexOf("(");
                    int end = s.indexOf(")");
                    String areasStr = s.substring(begin + 1, end);
                    begin = s.indexOf("[");
                    end = s.indexOf("]");
                    String arrsStr = s.substring(begin + 1, end);
                    String[] areasStrArray = areasStr.split(",");
                    for (String s2 : areasStrArray) {
                        areas.add(s2);//添加行政区域
                    }
                    String[] arrsStrArray = arrsStr.split(",");
                    int lenM = arrsStrArray.length;
                    if (maxLen < lenM)
                        maxLen = lenM;//找到最大长度并存到maxLen
                    for (String s3 : arrsStrArray) {
                        arrs.add(s3);//添加库位信息
                    }
                    map1.put(i + "", areas);//将获取信息放到map1中
                    map2.put(i + "", arrs);//将库位信息放到map2中
                }
                //记录叉车票数
            } catch (Exception e) {
                logger.error("库区转换失败：" + e);
            }
            int isInit[][] = new int[keyLen][maxLen];

            /**
             * 下面的代码是用运单号、库区编码查询是否有入派送库区 如果存在则，按照派送区域件数统计派送区的叉车票数，
             * 如果不存在则用运单号的目的站查询查询当前外场是否是目的站的驻地外场 如果是则，按照派送区域件数统计派送区的叉车票数。
             * 不是则，按运单当前库区编码统计叉车票数
             * ***/
            for (PDATrayScanDetailEntity rs : trayTaskDetails) {
                logger.error("叉车票统计运单号：" + rs.getWaybillNo() + "\t叉车票统计流水号：" + rs.getSerialNo() + "\t叉车票统计外场：" + outfieldcode);
                // 运单号
                String waybillNo = rs.getWaybillNo();
                //流水号
                String serialNo = rs.getSerialNo();
                //通过运单号获取运单信息
                WaybillEntity waybillInfo = waybillManagerService.queryWaybillBasicByNo(waybillNo);
                // 若运单信息为空则不计算叉车票数
                if (waybillInfo != null) {
                    if (StringUtils.equals(waybillInfo.getProductCode(), "PACKAGE")
                            || StringUtils.equals(waybillInfo.getProductCode(), "RCP")
                            || StringUtils.equals(waybillInfo.getProductCode(), "EPEP")) {
                        if (!isExpressWaybill) {
                            isExpressWaybill = true;
                            ConfigurationParamsEntity paramEntity = configurationParamsService.queryConfigurationParamsByOrgCode(
                                    DictionaryConstants.SYSTEM_CONFIG_PARM__TFR,
                                    ConfigurationParamsConstants.TFR_PARM_EXPRESS_FORKLIFTTICKET_PARAMETERS,
                                    outfieldcode);
                            if (paramEntity == null) {
                                expressCount = 0;
                                logger.error("没有配置快递叉车票统计参数，请配置参数！");
                            } else {
                                String expressTickts = paramEntity.getConfValue();

                                try {
                                    expressCount = Integer.valueOf(expressTickts);
                                } catch (NumberFormatException e) {
                                    logger.error("配置快递叉车票统计参数有误，请重新配置参数！");
                                    expressCount = 0;
                                }
                            }
                            continue;
                        } else {
                            continue;
                        }
                    }

                    // 1.查询是否入库在库存表中查询
                    List<StockEntity> waybillStock = pdaTrayScanDao.queryWaybillStock(waybillNo, serialNo, outfieldcode);
                    //查询到库存
                    if (CollectionUtils.isNotEmpty(waybillStock) && waybillStock.size() > 0) {
                        //当前库区编码
                        String goodsAreaCode = waybillStock.get(0).getGoodsAreaCode();
                        // 1.1如果当前运单是到达派送部
                        if (StringUtils.equals(goodsAreaCode, goodsArea.getGoodsAreaCode())) {
                            // 查询运单开单件数
                            int serialCount = waybillInfo.getGoodsQtyTotal();
                            //调用库区比较方法
                            comparisonGoodsArea(keyLen, serialCount, map1, map2, isInit, waybillInfo);
                        } else {
                            // 1.2通过运单的目的站查找是否最终派送外场
                            String outCode = getCenterCode(waybillInfo
                                    .getCustomerPickupOrgCode());
                            // 1.2.1如果运单目的站是最终派送外场
                            if (StringUtils.equals(outCode, outfieldcode)) {
                                // 查询运单开单件数
                                int serialCount = waybillInfo.getGoodsQtyTotal();
                                //调用库区比较方法
                                comparisonGoodsArea(keyLen, serialCount, map1, map2, isInit, waybillInfo);
                            } else {
                                // 1.2.2如果当前运单不是到达派送部的
                                boolean[] temp = karpABArea.get(goodsAreaCode);
                                if (temp != null) {
                                    //计算特殊库区的卡普票数
                                    calculationKarpAB(goodsAreaCode, karpABArea, areaEntityMap, waybillInfo);
                                } else if (StringUtils.isNotBlank(goodsAreaCode)) {
                                    // 运单目的站为空则不计算叉车票数
                                    if (!destnames.contains(goodsAreaCode)) {
                                        forkliftcount += 1;
                                    }
                                    destnames.add(goodsAreaCode);
                                }
                            }
                        }
                    } else {
                        //2.从库存日志中查找（入库记录）
                        // 查询是否入库在库存表中查询
                        List<StockEntity> waybillinStock = pdaTrayScanDao.queryWaybillInStock(waybillNo, serialNo, outfieldcode);
                        if (CollectionUtils.isNotEmpty(waybillinStock) && waybillinStock.size() > 0) {
                            //当前库区编码
                            String goodsAreaCode = waybillinStock.get(0).getGoodsAreaCode();
                            // 如果当前运单是到达派送部
                            if (StringUtils.equals(goodsAreaCode, goodsArea.getGoodsAreaCode())) {
                                // 查询运单开单件数
                                int serialCount = waybillInfo.getGoodsQtyTotal();
                                //调用库区比较方法
                                comparisonGoodsArea(keyLen, serialCount, map1, map2, isInit, waybillInfo);
                            } else {
                                // 通过运单的目的站查找是否最终派送外场
                                String outCode = getCenterCode(waybillInfo
                                        .getCustomerPickupOrgCode());
                                // 如果运单目的站是最终派送外场
                                if (StringUtils.equals(outCode, outfieldcode)) {
                                    // 查询运单开单件数
                                    int serialCount = waybillInfo.getGoodsQtyTotal();
                                    //调用库区比较方法
                                    comparisonGoodsArea(keyLen, serialCount, map1, map2, isInit, waybillInfo);

                                } else {
                                    // 2.2.2如果当前运单不是到达派送部的
                                    boolean[] temp = karpABArea.get(goodsAreaCode);
                                    if (temp != null) {
                                        //计算特殊库区的卡普票数
                                        calculationKarpAB(goodsAreaCode, karpABArea, areaEntityMap, waybillInfo);
                                    } else if (StringUtils.isNotBlank(goodsAreaCode)) {
                                        // 运单目的站为空则不计算叉车票数
                                        if (!destnames.contains(goodsAreaCode)) {
                                            forkliftcount += 1;
                                        }
                                        destnames.add(goodsAreaCode);
                                    }

                                }
                            }
                        } else {
                            logger.error("查询不到运单:" + waybillNo + "流水号：" + serialNo + "在当前部门：" + outfieldcode + "库存！需要走job统计票数");
                            //如果有未入库的运单则返回0在job中统计
                            return 0;
                        }
                    }


                } else {
                    logger.error("不存在该运单的信息：" + waybillNo);
                }

            }
            // 计算派送区叉车票数
            for (int i = 0; i < keyLen; i++) {
                for (int j = 0; j < maxLen; j++) {
                    delivercount += isInit[i][j];
                }
            }

        } else {
            logger.error("外场：" + outfieldcode + " 对应的派送库区不存在或者派送库区没有分件区！");

            // 不存在派送库区，则不需要统计派送库区票数
            for (PDATrayScanDetailEntity rs : trayTaskDetails) {
                //运单号
                String waybillNo = rs.getWaybillNo();
                //流水号
                String serialNo = rs.getSerialNo();
                // 查询是否入库区
                List<StockEntity> waybillStock = pdaTrayScanDao.queryWaybillStock(waybillNo, serialNo, outfieldcode);

                //通过运单号获取运单信息
                WaybillEntity waybillInfo = waybillManagerService.queryWaybillBasicByNo(waybillNo);
                //剔除掉有异常的运单
                if (waybillInfo != null) {
                    if (StringUtils.equals(waybillInfo.getProductCode(), "PACKAGE")
                            || StringUtils.equals(waybillInfo.getProductCode(), "RCP")
                            || StringUtils.equals(waybillInfo.getProductCode(), "EPEP")) {
                        if (!isExpressWaybill) {
                            isExpressWaybill = true;
                            ConfigurationParamsEntity paramEntity = configurationParamsService.queryConfigurationParamsByOrgCode(
                                    DictionaryConstants.SYSTEM_CONFIG_PARM__TFR,
                                    ConfigurationParamsConstants.TFR_PARM_EXPRESS_FORKLIFTTICKET_PARAMETERS,
                                    outfieldcode);
                            if (paramEntity == null) {
                                expressCount = 0;
                                logger.error("没有配置快递叉车票统计参数，请配置参数！");
                            } else {
                                String expressTickts = paramEntity.getConfValue();

                                try {
                                    expressCount = Integer.valueOf(expressTickts);
                                } catch (NumberFormatException e) {
                                    logger.error("配置快递叉车票统计参数有误，请重新配置参数！");
                                    expressCount = 0;
                                }
                            }
                            continue;
                        } else {
                            continue;
                        }
                    }

                    if (CollectionUtils.isNotEmpty(waybillStock) && waybillStock.size() > 0) {
                        String goodsAreaCode = waybillStock.get(0).getGoodsAreaCode();
                        boolean[] temp = karpABArea.get(goodsAreaCode);
                        if (temp != null) {
                            //计算特殊库区的卡普票数
                            calculationKarpAB(goodsAreaCode, karpABArea, areaEntityMap, waybillInfo);
                        } else {

                            // 是否存在相同的目的站，不存在则叉车票数加一
                            if (!destnames.contains(goodsAreaCode)) {
                                forkliftcount += 1;
                            }
                            destnames.add(goodsAreaCode);

                        }
                    } else {
                        List<StockEntity> waybillinStock = pdaTrayScanDao.queryWaybillInStock(waybillNo, serialNo, outfieldcode);
                        if (CollectionUtils.isNotEmpty(waybillinStock) && waybillinStock.size() > 0) {
                            String goodsAreaCode = waybillinStock.get(0).getGoodsAreaCode();
                            boolean[] temp = karpABArea.get(goodsAreaCode);
                            if (temp != null) {
                                //计算特殊库区的卡普票数
                                calculationKarpAB(goodsAreaCode, karpABArea, areaEntityMap, waybillInfo);
                            } else {

                                // 是否存在相同的目的站，不存在则叉车票数加一
                                if (!destnames.contains(goodsAreaCode)) {
                                    forkliftcount += 1;
                                }
                                destnames.add(goodsAreaCode);

                            }
                        } else {
                            logger.error("查询不到运单:" + waybillNo + "流水号：" + serialNo + "在当前部门：" + outfieldcode + "库存！需要走job统计票数");
                            //如果有未入库的运单则返回0在job中统计
                            return 0;
                        }
                    }


                } else {
                    logger.error("不存在该运单的信息：" + waybillNo);
                }
            }
        }
        //统计卡普AB货票数
        int kpABcount = 0;
        for (int i = 0; i < karpABAreaList.size(); i++) {
            boolean[] bb = karpABArea.get(karpABAreaList.get(i).getGoodsAreaCode());
            int temp = 0;
            if (bb != null) {
                for (int j = 0; j < bb.length; j++) {
                    if (bb[j]) {
                        temp += 1;
                    }
                }
            }
            kpABcount = kpABcount + temp;
        }
        // 叉车总票数
        forkliftcount = forkliftcount + delivercount + kpABcount + expressCount;

        return forkliftcount;
    }

    /**
     * 叉车票统计方法
     *
     * @param keyLen      map中key长度
     * @param serialCount 开单件数
     * @param map1        行政区域编码
     * @param map2        库位数据
     * @param isInit      返回叉车票数
     * @param waybillInfo 运单信息
     * @Author: 200978  xiaobingcheng
     * 2014-10-31
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    private void comparisonGoodsArea(int keyLen, int serialCount, Map map1, Map map2, int isInit[][], WaybillEntity waybillInfo) {
        //行政区域
        String distCode = waybillInfo.getReceiveCustomerDistCode();
        if (distCode == null || distCode.equals("")) {
            return;
        }
        //提货方式
        String receiveMethod = waybillInfo.getReceiveMethod();
        //如果是自提
        if (receiveMethod.contains("PICKUP")) {
            distCode = "ZT";
        }
        for (int i = 0; i < keyLen; i++) {
            List<String> areas = (List<String>) map1.get(i + "");
            if (CollectionUtils.isEmpty(areas)) {
                continue;
            }
            for (String s : areas) {
                if (StringUtils.equals(s, distCode)) {
                    List<String> arrs = (List<String>) map2.get(i + "");
                    for (int j = 0; j < arrs.size(); j++) {
                        boolean temp = comparisonResult(serialCount, arrs.get(j));
                        if (temp) {
                            isInit[i][j] = 1;
                        }
                    }
                    break;//找到就跳出里层循环
                }
            }
        }
    }

    /**
     * 比较这个运单件数是否在这个库位
     * 如果再则返回true
     *
     * @param serialCount 开单件数
     * @param str         库位件数长度   1/2-5/6<...
     * @return
     * @Author: 200978  xiaobingcheng
     * 2014-10-31
     */
    private boolean comparisonResult(int serialCount, String str) {
        try {
            if (str == null || str.equals("")) {
                return false;
            }
            if (str.contains("<")) {
                int n = Integer.parseInt((str.split("<")[0]));
                if (serialCount >= n) {
                    return true;
                } else {
                    return false;
                }
            }
            String[] numStr = str.split("-");
            if (numStr.length > 1) {
                int min = Integer.parseInt(numStr[0]);
                int max = Integer.parseInt(numStr[1]);
                if (serialCount >= min && serialCount <= max) {
                    return true;
                } else {
                    return false;
                }
            } else {
                if (Integer.parseInt(numStr[0]) == serialCount) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (Exception e) {
            logger.error("库区件数比较失败：" + e.getStackTrace());
            return false;
        }
    }

    /**
     * 统计特殊库区的按卡普AB方式计算叉车票数
     *
     * @param goodsAreaCode
     * @param karpABArea
     * @param areaEntityMap
     * @param waybillInfo
     * @author foss-heyongdong
     * @date 2014年2月13日 10:30:16
     */
    private void calculationKarpAB(String goodsAreaCode, Map<String, boolean[]> karpABArea, Map<String, GoodsAreaEntity> areaEntityMap, WaybillEntity waybillInfo) {
        /*
		 * 1、通过库区号查询库区信息，是否按照卡普或者AB货计算
		 * 2、如果查询出库区信息，有四种情况：a、不计算卡普AB b、计算卡普   c、计算AB d、计算卡普AB
		 * 3、如果为bcd情况，则先通过运单查询运单是卡普以及AB性质
		 * 4、
		 * 如果计算方式是卡普，但是运单性质不是卡普怎么计算？
		 */

        String[] typeOfArea = {"A", "B", "卡", "普", "卡A", "卡B", "普A", "普B"};
        String typeAB = waybillInfo.getGoodsTypeCode();
        String typeKarp = "";
        String typeAll = "";
        ProductEntity productEntity = productService4Dubbo.getLevel3ProductInfo(waybillInfo.getProductCode());
        if (logger.isInfoEnabled()) {
            logger.info("PADTrayScanService调用计价Dubbo接口成功，productService4Dubbo = {} ，" +
                    "productEntity = {} ", productService4Dubbo, productEntity);
        }
        if (StringUtils.equals("FAST", productEntity.getPriority())) {
            typeKarp = "卡";
        } else {
            typeKarp = "普";
        }

        typeAll = productEntity.getShortName() + typeAB;
        //获取计票类型
        String calculationType = areaEntityMap.get(goodsAreaCode).getCountingMode();
        boolean[] bb = karpABArea.get(goodsAreaCode);
        if (StringUtils.equals(calculationType, "AB")) {
            for (int i = 0; i < typeOfArea.length; i++) {
                if (StringUtils.equals(typeAB, typeOfArea[i])) {
                    bb[i] = true;
                }
            }
        } else if (StringUtils.equals(calculationType, "KP")) {
            for (int i = 0; i < typeOfArea.length; i++) {
                if (StringUtils.equals(typeKarp, typeOfArea[i])) {
                    bb[i] = true;
                }
            }
        } else if (StringUtils.equals(calculationType, "KPAB")) {
            for (int i = 0; i < typeOfArea.length; i++) {
                if (StringUtils.equals(typeAll, typeOfArea[i])) {
                    bb[i] = true;
                }
            }
        }
        karpABArea.put(goodsAreaCode, bb);


    }

    /**
     * 调用综合接口通过当前部门编码查询对应外场编码，如果查询不到则返当前部门编码
     *
     * @param orgCode
     * @author 105869-foss-heyongdong
     * @date 2013-8-13 10:33:01
     */
    private String getCenterCode(String orgCode) {
        OrgAdministrativeInfoEntity unloadOrg = orgAdministrativeInfoService
                .queryOrgAdministrativeInfoByCode(orgCode);
        String transferCenterCode = orgCode;
        if (unloadOrg != null) {
            if (FossConstants.YES.equals(unloadOrg.getSalesDepartment())) {
                SaleDepartmentEntity saleDetp = saleDepartmentService
                        .querySaleDepartmentByCode(unloadOrg.getCode());
                if (saleDetp != null
                        && FossConstants.YES.equals(saleDetp.getStation())) {
                    unloadOrg = orgAdministrativeInfoService
                            .queryOrgAdministrativeInfoByCode(saleDetp
                                    .getTransferCenter());
                }
            } else {
                unloadOrg = pdaCommonService.getCurrentOutfieldCode(orgCode);
            }
        }
        if (unloadOrg == null) {
            logger.error("查询不到组织：" + orgCode + "对应的外场!");
        } else {
            transferCenterCode = unloadOrg.getCode();
        }

        return transferCenterCode;
    }

    /**
     * 插入叉车离线扫描信息
     *
     * @author foss-heyongdong
     * @see com.deppon.foss.module.transfer.pda.api.server.service.IPDATrayScanService#createTrayOfflineScanTask(java.lang.String, java.lang.String, java.util.Date, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public String createTrayOfflineScanTask(PDATrayOfflineScanDto trayOfflineScanDto) {
        if (trayOfflineScanDto == null) {
            logger.error("叉车离线扫描接口传递对象为null");
            return TransferPDADictConstants.SUCCESS;
        }
        String offlineTaskNo = trayOfflineScanDto.getOfflineTaskNo();
        String forkLiftDept = trayOfflineScanDto.getForkLiftDept();
        //查询叉车司机部门对应的外场
        String outFieldCode = getCenterCode(forkLiftDept);
        String operatType = trayOfflineScanDto.getOperatType();
        Date trayOfflineScanTime = trayOfflineScanDto.getTrayOfflineScanTime();
        String forkLiftDriverCode = trayOfflineScanDto.getForkLiftDriverCode();
        String forkLiftDriverName = "";
        // 获取人员信息接口
        EmployeeEntity empd = employeeService.queryEmployeeByEmpCode(forkLiftDriverCode);
        if (empd != null) {
            //叉车司机姓名
            forkLiftDriverName = empd.getEmpName();
        }

        /**
         *update by 205109——zenghaibin 2014-11-12 09:20
         *如果是包，则先删除运单，然后添加包号所对应的运单，
         ***/
        List<PDATrayOfflineScanWaybillEntiy> wayBillEntityList = new ArrayList<PDATrayOfflineScanWaybillEntiy>();

        for (PDATrayOfflineScanWaybillEntiy wayBillEntity : trayOfflineScanDto.getWaybillEntity()) {
            String packageNo = wayBillEntity.getWaybillNo();
            if (!StringUtils.isBlank(wayBillEntity.getWaybillNo()) && StringUtils.equals(packageNo.substring(0, 1), "B")) {
                List<PcakageWayBillDto> pcakageWayBillList = pdaTrayScanDao.queryPackageDetail(packageNo);
                for (PcakageWayBillDto pcakageWayBill : pcakageWayBillList) {
                    PDATrayOfflineScanWaybillEntiy offLineEntity = new PDATrayOfflineScanWaybillEntiy();
                    offLineEntity.setSerialNo(pcakageWayBill.getSerialNo());
                    offLineEntity.setWaybillNo(pcakageWayBill.getWayBillNo());
                    offLineEntity.setPackageNo(packageNo);
                    wayBillEntityList.add(offLineEntity);
                }
            } else {

                wayBillEntityList.add(wayBillEntity);
            }
        }
        //设置运单明细
        trayOfflineScanDto.setWaybillEntity(wayBillEntityList);
        //传递list实体
        List<TrayOfflineScanEntity> trayOfflineScanList = new ArrayList<TrayOfflineScanEntity>();
        for (PDATrayOfflineScanWaybillEntiy dto : trayOfflineScanDto.getWaybillEntity()) {
            //创建离线扫描对象
            TrayOfflineScanEntity trayOfflineScanEntity = new TrayOfflineScanEntity();
            //设置Id
            trayOfflineScanEntity.setId(UUIDUtils.getUUID());
            //运单号
            trayOfflineScanEntity.setWaybillNo(dto.getWaybillNo());
            //流水号
            trayOfflineScanEntity.setSerialNo(dto.getSerialNo());
            //离线扫描时间
            trayOfflineScanEntity.setTrayOfflineScanTime(trayOfflineScanTime);
            //叉车司机部门
            trayOfflineScanEntity.setForkLiftDept(forkLiftDept);
            //叉车司机工号
            trayOfflineScanEntity.setForkLiftDriverCode(forkLiftDriverCode);

            //叉车司机姓名
            trayOfflineScanEntity.setForkLiftDriverName(forkLiftDriverName);

            //离线扫描任务号
            trayOfflineScanEntity.setOfflineTaskNo(offlineTaskNo);

            trayOfflineScanEntity.setOutFieldCode(outFieldCode);
            //操作类型
            trayOfflineScanEntity.setOperatType(operatType);
            //包号
            trayOfflineScanEntity.setPackageNo(dto.getPackageNo());

            trayOfflineScanList.add(trayOfflineScanEntity);
        }

        pdaTrayScanDao.createTrayOfflineScanTask(trayOfflineScanList);
        //入库位
        logger.error("在线扫描入库位开始！");
        this.inStockPosition(trayOfflineScanDto, outFieldCode, forkLiftDriverCode, forkLiftDriverName, trayOfflineScanTime);
        logger.error("在线扫描入库位结束！");
        return TransferPDADictConstants.SUCCESS;
    }


    /**
     * 根据操作人工号、操作人部门及当前时间，返回该时间往前12个小时内的叉车票数统计值给PDA
     *
     * @param ceaterCode     创建人code
     * @param createrOrgCode 创建人部门code
     * @param currentTime    当前查询时间
     * @return PDATrayScanTaskEntity
     * @author 105795-foss-wqh
     * @date 2014-01-11
     */
    @Override
    public List<PDATrayScanTaskEntity> queryTraybindforkLiftTicks(String ceaterCode, String createrOrgCode, Date currentTime, int hours) {
        //参数检验
        if (StringUtil.isEmpty(ceaterCode) || StringUtil.isBlank(ceaterCode)) {
            logger.error("当前操作人工号为空");
            throw new TfrBusinessException("当前操作人工号为空");
        }
        if (StringUtil.isEmpty(createrOrgCode) || StringUtil.isBlank(createrOrgCode)) {
            logger.error("当前操作人部门为空");
            throw new TfrBusinessException("当前操作人部门为空");
        }
        //查询对应外场编码
        String outfieldCode = getCenterCode(createrOrgCode);
        //如果PDA传过来的当前时间为空，则重新赋值
        if (currentTime == null) {
            currentTime = new Date();
        }
        Date beginTime = new Date(currentTime.getTime() - 1000l * ConstantsNumberSonar.SONAR_NUMBER_60 * ConstantsNumberSonar.SONAR_NUMBER_60 * hours);
        //叉车绑定实体list
        List<PDATrayScanTaskEntity> trayScanTaskList;
        trayScanTaskList = pdaTrayScanDao.queryTraybindforkLiftTicks(ceaterCode, outfieldCode, currentTime, beginTime);
        return trayScanTaskList;

    }

    /**
     * 根据操作人工号、操作人部门及当前时间，返回该时间往前 hours 个小时内的运单票数统计值给PDA
     *
     * @param createrCode    创建人code
     * @param createrOrgCode 创建人部门code
     * @param currentTime    当前查询时间
     * @param hours          查询递归小时
     * @return PDATrayOfflineScanDto
     * @author 105869-foss-heyondong
     * @date 2014年5月5日 14:26:44
     * @see com.deppon.foss.module.transfer.pda.api.server.service.IPDATrayScanService#queryTrayOfflineScanWaybillQty(java.lang.String, java.lang.String, java.util.Date, int)
     */
    @Override
    public List<PDATrayOfflineScanDto> queryTrayOfflineScanWaybillQty(String ceaterCode, String createrOrgCode, Date currentTime, int hours) {
        //参数检验
        if (StringUtil.isEmpty(ceaterCode) || StringUtil.isBlank(ceaterCode)) {
            logger.error("当前操作人工号为空");
            throw new TfrBusinessException("当前操作人工号为空");
        }
        if (StringUtil.isEmpty(createrOrgCode) || StringUtil.isBlank(createrOrgCode)) {
            logger.error("当前操作人部门为空");
            throw new TfrBusinessException("当前操作人部门为空");
        }
        //查询对应外场编码
        String outfieldCode = getCenterCode(createrOrgCode);
        //如果PDA传过来的当前时间为空，则重新赋值
        if (currentTime == null) {
            currentTime = new Date();
        }
        Date beginTime = new Date(currentTime.getTime() - 1000l * ConstantsNumberSonar.SONAR_NUMBER_60 * ConstantsNumberSonar.SONAR_NUMBER_60 * hours);
        List<PDATrayOfflineScanDto> trayOfflineScanlist = pdaTrayScanDao.queryTrayOfflineScanWaybillQty(ceaterCode, outfieldCode, currentTime, beginTime);
        return trayOfflineScanlist;
    }

    /**
     * 托盘绑定后如果是派送库区的则入库位
     *
     * @param PDATrayScanTaskEntity trayScanTask
     * @author heyongdong
     * @date 2014年8月5日 16:43:50
     */
    private String inStockPosition(String taskNo, String operatorCode, String operatorName, Date scanTime) {
        // 创建查询条件
        QueryTrayScanTaskEntity queryCondtion = new QueryTrayScanTaskEntity();
        queryCondtion.setTaskNo(taskNo);
        // 获取托盘任务
        List<PDATrayScanTaskEntity> padTrayScanTasks = pdaTrayScanDao.queryTrayScanNO(queryCondtion);
        if (CollectionUtils.isEmpty(padTrayScanTasks) && padTrayScanTasks.size() == 0) {
            logger.error("找不到托盘任务，入库位失败！");
            return "Failure";
        }
        //外场编码
        String outFieldCode = padTrayScanTasks.get(0).getOutfieldCode();
        // 根据部门驻地派送
        List<GoodsAreaEntity> goodsAreaList = goodsAreaService.queryGoodsAreaListByType(outFieldCode, DictionaryValueConstants.BSE_GOODSAREA_TYPE_STATION);
        if (CollectionUtils.isEmpty(goodsAreaList) && goodsAreaList.size() < 0) {
            logger.error("不存在派送库区！");
            return "Failure";
        }
        //派送库区编号
        String goodsAreaCode = goodsAreaList.get(0).getGoodsAreaCode();

        //获取PDA设备号
        String pdaDeviceNO = taskNo.substring(0, taskNo.length() - ConstantsNumberSonar.SONAR_NUMBER_17);

        // 获取明细
        // 创建查询条件
        QueryTrayScanTaskEntity querydetail = new QueryTrayScanTaskEntity();
        querydetail.setTaskNo(taskNo);
        // 查询明细
        List<PDATrayScanDetailEntity> trayScandDetails = pdaTrayScanDao.queryTrayScanPackageDetail(querydetail);
        for (PDATrayScanDetailEntity temp : trayScandDetails) {
            //运单号
            String waybillNo = temp.getWaybillNo();
            //流水号
            String seriaNo = temp.getSerialNo();
            WaybillEntity waybillInfo = null;
            if (waybillNo != null && !waybillNo.equals("")) {
                waybillInfo = waybillManagerService.queryWaybillBasicByNo(waybillNo);
            }
            //如果运单不存在则入库位下一个运单
            if (waybillInfo == null) {
                logger.error("运单不存在！");
                continue;
            }
            // 查询是否入库区
            List<StockEntity> waybillStock = pdaTrayScanDao.queryWaybillStock(waybillNo, seriaNo, outFieldCode);
            //没有入库则不能入库位
            if (CollectionUtils.isEmpty(waybillStock) && waybillStock.size() <= 0) {
                continue;
            }
            //如果是派送库区则入库位，否则不入库位
            if (StringUtils.equals(goodsAreaCode, waybillStock.get(0).getGoodsAreaCode())) {
                //构造传入参数
                InOutStockEntity stockEntity = new InOutStockEntity();
                stockEntity.setWaybillNO(waybillNo);//运单号
                stockEntity.setSerialNO(seriaNo);//流水号
                stockEntity.setPosition("001");//库位号
                stockEntity.setOrgCode(outFieldCode);//部门
                stockEntity.setOperatorCode(operatorCode);//操作人工号
                stockEntity.setOperatorName(operatorName);//操作人名字
                stockEntity.setPdaDeviceNO(pdaDeviceNO);//pda设备号
                stockEntity.setScanTime(scanTime);//扫描时间
                //调用入库位接口
                pdaStockService.updateStockStockPosition(stockEntity);
            }

        }
        return "Success";
    }

    private String inStockPosition(PDATrayOfflineScanDto trayOfflineScanDto, String outFieldCode, String forkLiftDriverCode,
                                   String forkLiftDriverName, Date trayOfflineScanTime) {
        //外场为空入库位失败
        if (outFieldCode == null) {
            logger.error("没有查询到对应的外场！");
            return "Failure";
        }
        // 根据部门驻地派送
        List<GoodsAreaEntity> goodsAreaList = goodsAreaService.queryGoodsAreaListByType(outFieldCode, DictionaryValueConstants.BSE_GOODSAREA_TYPE_STATION);
        if (CollectionUtils.isEmpty(goodsAreaList)) {
            logger.error("不存在派送库区！");
            return "Failure";
        }
        //派送库区编号
        String goodsAreaCode = goodsAreaList.get(0).getGoodsAreaCode();

        //获取PDA设备号
        String taskNo = trayOfflineScanDto.getOfflineTaskNo();
        if (taskNo == null) {
            logger.error("离线扫描任务号为空！");
            return "Failure";
        }
        String pdaDeviceNO = taskNo.substring(0, taskNo.length() - ConstantsNumberSonar.SONAR_NUMBER_17);
        for (PDATrayOfflineScanWaybillEntiy temp : trayOfflineScanDto.getWaybillEntity()) {
            //运单号
            String waybillNo = temp.getWaybillNo();
            //流水号
            String seriaNo = temp.getSerialNo();
            WaybillEntity waybillInfo = waybillManagerService.queryWaybillBasicByNo(waybillNo);
            //如果运单不存在则入库位下一个运单
            if (waybillInfo == null) {
                logger.error("运单不存在！");
                continue;
            }
            // 查询是否入库区
            List<StockEntity> waybillStock = pdaTrayScanDao.queryWaybillStock(waybillNo, seriaNo, outFieldCode);
            //没有入库则不能入库位
            if (CollectionUtils.isEmpty(waybillStock) && waybillStock.size() <= 0) {
                continue;
            }
            //如果是派送库区则入库位，否则不入库位
            if (StringUtils.equals(goodsAreaCode, waybillStock.get(0).getGoodsAreaCode())) {
                //构造传入参数
                InOutStockEntity stockEntity = new InOutStockEntity();
                stockEntity.setWaybillNO(waybillNo);//运单号
                stockEntity.setSerialNO(seriaNo);//流水号
                stockEntity.setPosition("001");//库位号
                stockEntity.setOrgCode(outFieldCode);//部门
                stockEntity.setOperatorCode(forkLiftDriverCode);//操作人工号
                stockEntity.setOperatorName(forkLiftDriverName);//操作人名字
                stockEntity.setPdaDeviceNO(pdaDeviceNO);//pda设备号
                stockEntity.setScanTime(trayOfflineScanTime);//扫描时间
                //调用入库位接口
                pdaStockService.updateStockStockPosition(stockEntity);
            }
        }
        return "Success";

    }

    /**
     * 下拉包的运单明细
     *
     * @param Strng packageNo
     * @return List<PcakageWayBillDto> (运单和流水号)
     * @author 205109 zenghaibin
     * @date 2014-11-06 09：17
     **/
    private List<PcakageWayBillDto> queryPackageDetail(String packageNo) {
        return pdaTrayScanDao.queryPackageDetail(packageNo);
    }

    /**
     * 快递转货扫描
     *
     * @param ExpressTransferScanDto
     * @return String(零担库区名称)
     * @author 218442 zhuyunrong
     * @date 2014-12-23
     */
    @Override
    public String queryGoodsAreaForPdaTrayScan(ExpressTransferScanDto expressTransferScanDto) {
        //非空校验
        if (expressTransferScanDto == null) {
            logger.error("PDA传的参数为空！");
            throw new TfrBusinessException("PDA传的参数为空！");
        }
        String wayBillNo = expressTransferScanDto.getWayBillNo();   //运单号
        String serialNo = expressTransferScanDto.getSerialNo();     //流水号
        String packageNo = expressTransferScanDto.getPackageNo();   //包号
        String orgCode = expressTransferScanDto.getOrgCode();       //当前外场编码
        //取包中任一运单号、流水号
        if (StringUtils.isNotEmpty(packageNo)) {
            List<PcakageWayBillDto> pcakageWayBillDtos = this.queryPackageDetail(packageNo);
            if (pcakageWayBillDtos.size() == 0) {
                logger.error("包内运单明细为空");
                throw new TfrBusinessException("包内运单明细为空");
            }
            wayBillNo = pcakageWayBillDtos.get(0).getWayBillNo();
            serialNo = pcakageWayBillDtos.get(0).getSerialNo();
        }
        //查询目的站编码（下一部门编码）
        StockEntity stockEntity = stockService.queryUniqueStock(wayBillNo, serialNo);
        if (stockEntity != null) {
            if (StringUtils.equals(orgCode, stockEntity.getOrgCode())) {
                String nextOrgCode = stockEntity.getNextOrgCode();
                //调用综合接口查询零担库区名称
                try {
                    return expressSortStationMappingService.queryGoodSAreaByCondition(nextOrgCode, orgCode);
                } catch (Exception e) {
                    logger.error("" + e);
                    throw new TfrBusinessException("查询参数为空");
                }
            }
        }
        return "不在当前库存";
    }

}
