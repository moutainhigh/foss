package com.deppon.foss.module.transfer.load.server.service.impl;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.*;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.DeptTransferMappingEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.GoodsAreaEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.pickup.waybill.api.server.service.IActualFreightService;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.transfer.load.api.server.dao.IPDALoadDao;
import com.deppon.foss.module.transfer.load.api.server.service.IPDALoadService;
import com.deppon.foss.module.transfer.load.api.shared.define.LoadConstants;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoadTaskEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.LoadGoodsDetailSerialDto;
import com.deppon.foss.module.transfer.load.dubbo.api.define.LoadGoodsDetailDto;
import com.deppon.foss.module.transfer.load.dubbo.api.define.PDAGoodsSerialNoDto;
import com.deppon.foss.module.transfer.load.dubbo.api.exception.TfrLoadException;
import com.deppon.foss.module.transfer.load.dubbo.api.service.IPDALoadCarRefreshDetailService;
import com.deppon.foss.module.transfer.pda.api.shared.define.TransferPDADictConstants;
import com.deppon.foss.module.transfer.pda.api.shared.exception.TransferPDAExceptionCode;
import com.deppon.foss.module.transfer.scheduling.api.server.service.IStowagePlansService;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;
import com.deppon.foss.module.transfer.stock.api.define.StockConstants;
import com.deppon.foss.module.transfer.stock.api.server.service.IStockService;
import com.deppon.foss.module.transfer.stock.api.shared.dto.WaybillStockQueryDto;
import com.deppon.foss.util.define.FossConstants;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * 装车下拉明细
 *
 * @author 335284
 */
public class PDALoadCarRefreshDetailService implements IPDALoadCarRefreshDetailService {
    private final static Logger LOGGER = LoggerFactory.getLogger(PDALoadCarRefreshDetailService.class);

    private IConfigurationParamsService configurationParamsService;
    private IPDALoadDao pdaLoadDao;
    private IPDALoadService pdaLoadService;
    private IOrgAdministrativeInfoService orgAdministrativeInfoService;
    private IDeptTransferMappingService deptTransferMappingService;
    private ISaleDepartmentService saleDepartmentService;
    private IGoodsAreaService goodsAreaService;
    private IStockService stockService;
    private IActualFreightService actualFreightService;
    private IStowagePlansService stowagePlansService;
    private ILineService lineService;
    private IExpressLineService expresslineService;

    public static final String NO = "No";

    @Override
    public List<LoadGoodsDetailDto> refrushLoadTaskDetail(String taskNo) throws TfrLoadException {
        LOGGER.error("刷新装车任务开始:" + taskNo);
        LoadTaskEntity tempLoadTask = pdaLoadDao.queryLoadTaskByTaskNo(taskNo);
        //只有装车状态为装车中的任务可以下拉装车清单
        if (tempLoadTask != null && LoadConstants.LOAD_TASK_STATE_LOADING.equals(tempLoadTask.getState())) {
            if (queryLoadSwitch4PDA()) {//是否下拉到达未卸车数据
                return refreshSimpleDetail(taskNo, tempLoadTask);
            } else {
                return downLoadCompreDetail(taskNo, tempLoadTask);
            }
        } else {
            LOGGER.error("刷新装车任务结束:" + taskNo);
            throw new TfrLoadException(TransferPDAExceptionCode.EXCEPTION_INVALID_TASK_MESSAGECODE);
        }
    }

    private List<LoadGoodsDetailDto> downLoadCompreDetail(String taskNo, LoadTaskEntity tempLoadTask) throws TfrLoadException {
        int loadlimit = ConstantsNumberSonar.SONAR_NUMBER_1000;
        List<LoadGoodsDetailSerialDto> loadGoodsDetails = new ArrayList<LoadGoodsDetailSerialDto>();
        //正常库区装车清单
        List<LoadGoodsDetailSerialDto> normalLoadGoodsDetails = null;
        //虚拟库存装车清单
        List<LoadGoodsDetailSerialDto> saleLoadGoodsDetails = null;
        //合车库区装车清单
        List<LoadGoodsDetailSerialDto> togetherLoadGoodsDetails = null;
        if (StringUtils.isBlank(tempLoadTask.getGoodsType())) {
            //
        } else if ("ALL".equals(tempLoadTask.getGoodsType().toUpperCase())) {
            tempLoadTask.setGoodsType(null);
        } else if (("B_TYPE".equals(tempLoadTask.getGoodsType().toUpperCase()))) {
            tempLoadTask.setGoodsType("B");
        } else if (("A_TYPE".equals(tempLoadTask.getGoodsType().toUpperCase()))) {
            tempLoadTask.setGoodsType("A");
        }
        //偏线装车
        if (TransferPDADictConstants.LOAD_TASK_TYPE_PARTIALLINE.equals(tempLoadTask.getTaskType())) {
            List<String> outerBranchCodes = pdaLoadService.queryOuterBranchCodesByTaskId(tempLoadTask.getId());
            if (CollectionUtils.isNotEmpty(outerBranchCodes)) {
                normalLoadGoodsDetails = pdaLoadDao.refrushNormalTransferLoadTaskDetail(taskNo, outerBranchCodes, tempLoadTask.getGoodsType(), NO);
                togetherLoadGoodsDetails = pdaLoadDao.refrushTogetherTransferLoadTaskDetail(taskNo, outerBranchCodes, tempLoadTask.getGoodsType(), NO);
            }
        } else {//长短途装车
            List<String> destOrgCodes = pdaLoadDao.queryLoadDestOrgCodesById(tempLoadTask.getId());
            //查询出发部门属性
            OrgAdministrativeInfoEntity origOrg = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(tempLoadTask.getOrigOrgCode());
            //零担货量下拉合伙人
            /*****下拉合伙人一级及二级营业部-alfred 2016-10-1****/
            if (TransferPDADictConstants.LOAD_TASK_TYPE_SHORT_DISTANCE.equals(tempLoadTask.getTaskType())
                    && StringUtils.equals(origOrg.getTransferCenter(), FossConstants.YES)) {
                List<DeptTransferMappingEntity> deptTrans = deptTransferMappingService.queryDeptTransferMappingListByCode(destOrgCodes.get(0));
                if (null != deptTrans) {
                    //查询到达部门集合
                    List<String> mapList = this.queryShortArriveDeptList(tempLoadTask.getOrigOrgCode(), destOrgCodes.get(0));
                    destOrgCodes.addAll(mapList);
                }
            }
            destOrgCodes.add(StockConstants.AIR_FREIGHT_NEXT_ORG_CODE);
            //下拉正常库区货物
            normalLoadGoodsDetails = pdaLoadDao.refrushNormalTransferLoadTaskDetail(taskNo, destOrgCodes, tempLoadTask.getGoodsType(), NO);
            //如果出发部门为营业部才下拉虚拟库区货物
            if (StringUtils.equals(origOrg.getTransferCenter(), "N")) {
                saleLoadGoodsDetails = pdaLoadDao.refrushSaleNormalTransferLoadTaskDetail(taskNo, destOrgCodes, tempLoadTask.getGoodsType(), NO);
            }
            togetherLoadGoodsDetails = pdaLoadDao.refrushTogetherTransferLoadTaskDetail(taskNo, null, tempLoadTask.getGoodsType(), NO);
        }
        //多货装车清单
        List<LoadGoodsDetailSerialDto> moreGoodsLoadGoodsDetails = pdaLoadDao.refrushMoreGoodsLoadTaskDetail(taskNo, NO);
        if (CollectionUtils.isNotEmpty(normalLoadGoodsDetails)) {
            loadGoodsDetails.addAll(normalLoadGoodsDetails);
        }
        if (CollectionUtils.isNotEmpty(togetherLoadGoodsDetails)) {
            loadGoodsDetails.addAll(togetherLoadGoodsDetails);
        }
        if (CollectionUtils.isNotEmpty(moreGoodsLoadGoodsDetails)) {
            loadGoodsDetails.addAll(moreGoodsLoadGoodsDetails);
        }
        if (CollectionUtils.isNotEmpty(saleLoadGoodsDetails)) {
            loadGoodsDetails.addAll(saleLoadGoodsDetails);
        }

        //限制货物在两千票以内所以这边判定货物在大于两千票则不继续查询则直接返回
        if (CollectionUtils.isEmpty(loadGoodsDetails)) {
            loadGoodsDetails = new ArrayList<LoadGoodsDetailSerialDto>();
        }
        List<LoadGoodsDetailDto> result = loadSerialDetailToWayBillDetail(loadGoodsDetails, tempLoadTask.getOrigOrgCode());
        //控制票数
        if (result.size() < loadlimit) { //运单号不足limit
            // 获取当前部门对象
            OrgAdministrativeInfoEntity orgEntityTemp = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(tempLoadTask.getOrigOrgCode());
            // 如果当前不是外场,则直接跳过不执行下面的业务逻辑
            if (null != orgEntityTemp && StringUtils.equals(FossConstants.YES, orgEntityTemp.getTransferCenter())) {
                List<String> destOrgCodes = pdaLoadDao.queryLoadDestOrgCodesById(tempLoadTask.getId());
                //如果没有到达部门则直接跳过不做查询
                if (CollectionUtils.isNotEmpty(destOrgCodes)) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    //origOrgCode,handoverbillStateList,arriveDeptList,start,limit
                    map.put("origOrgCode", tempLoadTask.getOrigOrgCode());
                    List<String> handoverbillStateList = new ArrayList<String>(2);
                    ////40：已到达
                    handoverbillStateList.add(String.valueOf(LoadConstants.HANDOVERBILL_STATE_ALREADY_ARRIVE));
                    //设置查询条件
                    map.put("handoverbillStateList", handoverbillStateList);
                    //用Set过滤一下重复的值
                    Set<String> arriveDeptSetTemp = new HashSet<String>();
                    for (String deptStr : destOrgCodes) {
                        List<String> stowagePlansList = stowagePlansService.queryStowageWithid(tempLoadTask.getOrigOrgCode(), deptStr);
                        if (CollectionUtils.isNotEmpty(stowagePlansList)) {
                            arriveDeptSetTemp.addAll(stowagePlansList);
                        }
                        arriveDeptSetTemp.add(deptStr);
                    }
                    List<String> stowagePlansList = new ArrayList<String>(arriveDeptSetTemp);

                    // 接收到达部门、配载部门所辐射的营业部的list
                    List<String> arriveDeptList = new ArrayList<String>();
                    for (String arriveDept : stowagePlansList) {
                        // 获取该部门对象
                        OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(arriveDept);
                        arriveDeptList.add(arriveDept);
                        // 如果为外场，则获取其辐射的营业部code
                        if (null != orgEntity && StringUtils.equals(FossConstants.YES, orgEntity.getTransferCenter())) {
                            List<String> list = lineService.queryArriveCodeListByTransferCode(arriveDept);
                            List<String> explist = expresslineService.queryArriveCodeListByTransferCode(arriveDept);
                            arriveDeptList.addAll(explist);
                            arriveDeptList.addAll(list);
                        }
                    }
                    map.put("arriveDeptList", arriveDeptList);
                    //装车任务ID
                    map.put("taskNo", taskNo);
                    map.put("goodsType", tempLoadTask.getGoodsType());

                    //设置分页其实页数
                    int start = 0;
                    int limit = ConstantsNumberSonar.SONAR_NUMBER_20000;
                    while (result.size() < loadlimit) {
                        map.put("start", start);
                        map.put("limit", limit);
                        map.put("isExpress", NO);
                        //把库存和到达未卸车的数据汇总
                        List<LoadGoodsDetailSerialDto> unloadingNotReachList = pdaLoadDao.refrushNormalTransferLoadTaskDetailUnloadingNotReach(map);
                        if (CollectionUtils.isEmpty(unloadingNotReachList)) {
                            break;
                        }
                        List<LoadGoodsDetailDto> cList = loadSerialDetailToWayBillDetail(unloadingNotReachList, tempLoadTask.getOrigOrgCode());
                        result.addAll(cList);
//                        if (result.size() > loadlimit) break;
                        if (unloadingNotReachList.size() < limit) {//数量不足 说明已全部取出
                            break;
                        }
                        start += limit;
                    }
                }
            }
        }
        //如果放回值大于1000条数据则消除大于limit的部分
        if (CollectionUtils.isNotEmpty(result) && result.size() > loadlimit) {
            result = result.subList(0, loadlimit);
        }
        if (CollectionUtils.isNotEmpty(result)) {
            LOGGER.error("刷新装车任务结束:" + taskNo);
            return result;
        } else {
            LOGGER.error("empty刷新装车任务结束:" + taskNo);
            throw new TfrLoadException(TransferPDAExceptionCode.EXCEPTION_NO_TASK_DETAIL_MESSAGECODE);
        }
    }

    private List<LoadGoodsDetailDto> refreshSimpleDetail(String taskNo, LoadTaskEntity tempLoadTask) throws TfrLoadException {
        int loadlimit = ConstantsNumberSonar.SONAR_NUMBER_1000;

        List<LoadGoodsDetailSerialDto> loadGoodsDetails = new ArrayList<LoadGoodsDetailSerialDto>();
        //正常库区装车清单
        List<LoadGoodsDetailSerialDto> normalLoadGoodsDetails = null;
        //虚拟库存装车清单
        List<LoadGoodsDetailSerialDto> saleLoadGoodsDetails = null;
        //合车库区装车清单
        List<LoadGoodsDetailSerialDto> togetherLoadGoodsDetails = null;
        if (StringUtils.isBlank(tempLoadTask.getGoodsType())) {
        } else if ("ALL".equals(tempLoadTask.getGoodsType().toUpperCase())) {
            tempLoadTask.setGoodsType(null);
        } else if (("B_TYPE".equals(tempLoadTask.getGoodsType().toUpperCase()))) {
            tempLoadTask.setGoodsType("B");
        } else if (("A_TYPE".equals(tempLoadTask.getGoodsType().toUpperCase()))) {
            tempLoadTask.setGoodsType("A");
        }
        //偏线装车
        if (TransferPDADictConstants.LOAD_TASK_TYPE_PARTIALLINE.equals(tempLoadTask.getTaskType())) {
            List<String> outerBranchCodes = pdaLoadService.queryOuterBranchCodesByTaskId(tempLoadTask.getId());
            if (CollectionUtils.isNotEmpty(outerBranchCodes)) {
                //零担货
                normalLoadGoodsDetails = pdaLoadDao.refreshSimpleLDDetail(taskNo, outerBranchCodes, tempLoadTask.getGoodsType());
                togetherLoadGoodsDetails = pdaLoadDao.refreshTogetherSimpleDetail(taskNo, outerBranchCodes, tempLoadTask.getGoodsType());
            }
        } else {//长短途装车
            List<String> destOrgCodes = pdaLoadDao.queryLoadDestOrgCodesById(tempLoadTask.getId());
            //查询出发部门属性
            OrgAdministrativeInfoEntity origOrg = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(tempLoadTask.getOrigOrgCode());
            /*****下拉合伙人一级及二级营业部-alfred 2016-10-1****/
            if (TransferPDADictConstants.LOAD_TASK_TYPE_SHORT_DISTANCE.equals(tempLoadTask.getTaskType())
                    && StringUtils.equals(origOrg.getTransferCenter(), FossConstants.YES)) {
                List<DeptTransferMappingEntity> deptTrans = deptTransferMappingService.
                        queryDeptTransferMappingListByCode(destOrgCodes.get(0));
                if (null != deptTrans) {
                    //查询到达部门集合
                    List<String> mapList = queryShortArriveDeptList(tempLoadTask.getOrigOrgCode(), destOrgCodes.get(0));
                    destOrgCodes.addAll(mapList);
                }
            }
            destOrgCodes.add(StockConstants.AIR_FREIGHT_NEXT_ORG_CODE);
            //下拉正常库区货物
            normalLoadGoodsDetails = pdaLoadDao.refreshSimpleLDDetail(taskNo, destOrgCodes, tempLoadTask.getGoodsType());
            //如果出发部门为营业部才下拉虚拟库区货物
            if (StringUtils.equals(origOrg.getTransferCenter(), "N")) {
                saleLoadGoodsDetails = pdaLoadDao.refrushSaleNormalTransferLoadTaskDetail(taskNo, destOrgCodes, tempLoadTask.getGoodsType(), NO);
            }
            //长途装车下拉合车运单
            if (TransferPDADictConstants.LOAD_TASK_TYPE_LONG_DISTANCE.equals(tempLoadTask.getTaskType())) {
                togetherLoadGoodsDetails = pdaLoadDao.
                        refreshTogetherSimpleDetail(taskNo, null, tempLoadTask.getGoodsType());
            }
        }
        //多货装车清单
        List<LoadGoodsDetailSerialDto> moreGoodsLoadGoodsDetails = pdaLoadDao.refrushMoreGoodsLoadTaskDetail(taskNo, NO);
        if (CollectionUtils.isNotEmpty(normalLoadGoodsDetails)) {
            loadGoodsDetails.addAll(normalLoadGoodsDetails);
        }
        if (CollectionUtils.isNotEmpty(togetherLoadGoodsDetails)) {
            loadGoodsDetails.addAll(togetherLoadGoodsDetails);
        }
        if (CollectionUtils.isNotEmpty(moreGoodsLoadGoodsDetails)) {
            loadGoodsDetails.addAll(moreGoodsLoadGoodsDetails);
        }
        if (CollectionUtils.isNotEmpty(saleLoadGoodsDetails)) {
            loadGoodsDetails.addAll(saleLoadGoodsDetails);
        }

        //限制货物在两千票以内所以这边判定货物在大于两千票则不继续查询则直接返回
        if (CollectionUtils.isEmpty(loadGoodsDetails)) {
            loadGoodsDetails = new ArrayList<LoadGoodsDetailSerialDto>();
        }

        List<LoadGoodsDetailDto> result = loadSerialDetailToWayBillDetail(loadGoodsDetails, tempLoadTask.getOrigOrgCode());
        //如果放回值大于1000条数据则消除大于一千的部分
        if (CollectionUtils.isNotEmpty(result)) {
            if (result.size() > loadlimit) {
                result = result.subList(0, loadlimit);
            }
        }
        if (CollectionUtils.isNotEmpty(result)) {
            LOGGER.error("刷新简易装车任务结束:" + taskNo);
            return result;
        } else {
            LOGGER.error("empty 刷新简易装车任务结束:" + taskNo);
            throw new TfrLoadException(TransferPDAExceptionCode.EXCEPTION_NO_TASK_DETAIL_MESSAGECODE);
        }
    }

    private List<LoadGoodsDetailDto> loadSerialDetailToWayBillDetail(List<LoadGoodsDetailSerialDto> loadGoodsDetails, String orgCode) {
        if (CollectionUtils.isEmpty(loadGoodsDetails)) {
            return null;
        }
        // 如果货物列表不为空
        List<GoodsAreaEntity> valueGoodsAreaList = goodsAreaService.queryGoodsAreaListByType(orgCode, DictionaryValueConstants.BSE_GOODSAREA_TYPE_VALUABLE);
        List<GoodsAreaEntity> packGoodsAreaList = goodsAreaService.queryGoodsAreaListByType(orgCode, DictionaryValueConstants.BSE_GOODSAREA_TYPE_PACKING);

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
        // 遍历货物列表
        for (LoadGoodsDetailSerialDto goods : loadGoodsDetails) {
            boolean beExist = false;
            // 遍历明细列表
            PDAGoodsSerialNoDto serial;

            beExist = aftefExistSameWaybill(details, goods);//335284 抽取为方法

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
                // Gets the 货名
                detailTemp.setGoodsName(goods.getGoodsName());
                // Sets the 是否贵重物品
                detailTemp.setIsValue(goods.getIsValue());
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
                detailTemp.setVolume(goods.getVolume());
                // Sets the 运单号
                detailTemp.setWayBillNo(goods.getWayBillNo());
                // Sets the 开单件数
                detailTemp.setWayBillQty(goods.getWayBillQty());
                detailTemp.setWeight(goods.getWeight());
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
                detailTemp.setBePriorityGoods(FossConstants.NO);
                List<WaybillStockQueryDto> list = stockService.queryPriorityGoods(waybillStockQueryDto, 10, 0);
                if (list != null && list.size() > 0) {
                    detailTemp.setBePriorityGoods(FossConstants.YES);
                }
                detailTemp.setBePriorityGoods(FossConstants.NO);
                detailTemp.setBeEWaybill(FossConstants.NO);
                ActualFreightEntity actualFreightEntity = actualFreightService.queryByWaybillNo(goods.getWayBillNo());
                if (actualFreightEntity != null) {
                    if (StringUtils.equals(actualFreightEntity.getWaybillType(), "EWAYBILL")) {
                        detailTemp.setBeEWaybill(FossConstants.YES);
                    } else {
                        detailTemp.setBeEWaybill(FossConstants.NO);
                    }
                }
                // 加入明细
                details.add(detailTemp);
            }
        }
        logDetail(details);
        return details;
    }

    private void logDetail(List<LoadGoodsDetailDto> details) {
        StringBuilder s = new StringBuilder();
        s.append("刷新装车任务:");
        for (LoadGoodsDetailDto d : details) {
            s.append("任务号：" + d.getTaskNo());
            s.append("运单号:" + d.getWayBillNo());
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
        LOGGER.warn(s.toString());
    }

    /**
     * @param details 给定集合
     * @param goods   判断goods的运单和流水是否存在
     * @return 是否存在
     * @author 335284
     */
    private boolean aftefExistSameWaybill(List<LoadGoodsDetailDto> details, LoadGoodsDetailSerialDto goods) {
        boolean beExist = false;
        PDAGoodsSerialNoDto serial;
        for (LoadGoodsDetailDto detail : details) {
            if (detail.getWayBillNo().equals(goods.getWayBillNo())) {//335284 单号不一样不管 一样的在这里处理流水，下面不再加入
                beExist = true;
                boolean beExtistSerial = false;
                if (CollectionUtils.isNotEmpty(detail.getSerialNos())) {
                    for (PDAGoodsSerialNoDto serialTemp : detail.getSerialNos()) {
                        if (StringUtil.isNotBlank(serialTemp.getSerialNo()) && serialTemp.getSerialNo().equals(goods.getSerialNo())) {
                            beExtistSerial = true;
                            break;//335284 单号和流水都一样 跳出
                        }
                    }
                }
                if (!beExtistSerial) {
                    serial = new PDAGoodsSerialNoDto();
                    serial.setIsUnPacking(goods.getIsUnPacking());
                    serial.setIsToDoList(goods.getIsToDoList());
                    serial.setSerialNo(goods.getSerialNo());
                    serial.setGoodsPosition(goods.getGoodsPosition());
                    serial.setStockAreaCode(goods.getStockAreaCode());
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
        return beExist;
    }

    private List<String> queryShortArriveDeptList(String superOrgCode, String arriveDeptCode) {
        //返回的集合
        List<String> deptMapList = new ArrayList<String>();
        //查询出发部门获取是否是营业部
        SaleDepartmentEntity departDept = saleDepartmentService.querySaleDepartmentByCode(superOrgCode);
        //出发部门默认是外场
        String departWork = "YC";
        //判断不为空
        if (departDept != null) {
            //判断是否是一级网点
            String departModel = departDept.getIsLeagueSaleDept();
            //判断是否是二级网点
            String departNetwork = departDept.getIsTwoLevelNetwork();
            //出发部门是营业部
            if ("N".equals(departModel) && "N".equals(departNetwork)) {
                departWork = "YYB";
            }
            //出发部门一级网点
            if ("Y".equals(departModel) && "N".equals(departNetwork)) {
                departWork = "YJB";
            }
        }
        //查询到达部门是否是营业部
        SaleDepartmentEntity arriveDept = saleDepartmentService.querySaleDepartmentByCode(arriveDeptCode);
        //到达部门默认是 外场           外场:"YC" ,  营业部："YYB"  , 一级网点 "YJB"
        String arriveWork = "YC";
        //判断不为空
        if (arriveDept != null) {
            //判断是否是一级网点
            String arriveModel = arriveDept.getIsLeagueSaleDept();
            //判断是否是二级网点
            String arriveNetwork = arriveDept.getIsTwoLevelNetwork();
            //到达部门是营业部
            if ("N".equals(arriveModel) && "N".equals(arriveNetwork)) {
                arriveWork = "YYB";
            }
            //	到达部门一级网点
            if ("Y".equals(arriveModel) && "N".equals(arriveNetwork)) {
                arriveWork = "YJB";
            }
        }
        //外场到营业部
        if ("YC".equals(departWork) && "YYB".equals(arriveWork)) {
            //根据营业部查询到映射表的数据
            List<DeptTransferMappingEntity> deptTransferMappinglist = deptTransferMappingService.queryDeptTransferMappingListByCode(arriveDeptCode);
            //结果集不为空才添加到映射的到达部门
            if (deptTransferMappinglist != null && deptTransferMappinglist.size() > 0) {
                //映射的到达部门结果集
                List<String> arrivelist = new ArrayList<String>();
                for (DeptTransferMappingEntity entity : deptTransferMappinglist) {
                    //获取一级网点的code
                    String fthNetworkCode = entity.getFthNetworkCode();
                    if (fthNetworkCode != null) {
                        arrivelist.add(fthNetworkCode);
                    }
                    //获取二级网点的code
                    String secNetworkCode = entity.getSecNetworkCode();
                    if (secNetworkCode != null) {
                        arrivelist.add(secNetworkCode);
                    }
                }
                //去掉重复的数据再加入集合中
                deptMapList.addAll(new HashSet(arrivelist));
            }
        }
        //外场到一级网点
        if ("YC".equals(departWork) && "YJB".equals(arriveWork)) {
            //根据一级网点查询到映射表的数据
            List<DeptTransferMappingEntity> deptTransferMappinglist = deptTransferMappingService.queryDeptTransferMappingListByCode(arriveDeptCode);
            //判断结果集不为空才添加到映射的到达部门
            if (deptTransferMappinglist != null && deptTransferMappinglist.size() > 0) {
                for (DeptTransferMappingEntity entity : deptTransferMappinglist) {
                    //获取二级网点的code
                    String secNetworkCode = entity.getSecNetworkCode();
                    if (secNetworkCode != null) {
                        //添加关联
                        deptMapList.add(secNetworkCode);
                    }
                }
            }
        }
        //一级网点到外场
        if ("YJB".equals(departWork) && "YC".equals(arriveWork)) {
            //根据一级网点查询到映射表的数据
            List<DeptTransferMappingEntity> deptTransferMappinglist = deptTransferMappingService.queryDeptTransferMappingListByCode(superOrgCode);
            //判断结果集不为空才添加到映射的到达部门
            if (deptTransferMappinglist != null && deptTransferMappinglist.size() > 0) {
                //获取直营网点的code
                String deptCode = deptTransferMappinglist.get(0).getDeptCode();
                //替换下一部门code
                if (deptCode != null) {
                    deptMapList.add(deptCode);
                }
            }

        }
        return deptMapList;
    }

    public boolean queryLoadSwitch4PDA() {
        //final String code = "TFR_FOSS_LOAD_DOWNLOAD";
        String value = configurationParamsService.querySysConfig(DictionaryConstants.SYSTEM_CONFIG_PARM__TFR,
                ConfigurationParamsConstants.TFR_FOSS_LOAD_DOWNLOAD,
                FossConstants.ROOT_ORG_CODE);
        return FossConstants.YES.equals(value) ? true : false;
    }

    //@Autowired
    public void setConfigurationParamsService(IConfigurationParamsService configurationParamsService) {
        this.configurationParamsService = configurationParamsService;
    }

    //@Autowired
    public void setPdaLoadDao(IPDALoadDao pdaLoadDao) {
        this.pdaLoadDao = pdaLoadDao;
    }
    //@Autowired
    public void setPdaLoadService(IPDALoadService pdaLoadService) {
        this.pdaLoadService = pdaLoadService;
    }
    //@Autowired
    public void setOrgAdministrativeInfoService(IOrgAdministrativeInfoService orgAdministrativeInfoService) {
        this.orgAdministrativeInfoService = orgAdministrativeInfoService;
    }
    //@Autowired
    public void setDeptTransferMappingService(IDeptTransferMappingService deptTransferMappingService) {
        this.deptTransferMappingService = deptTransferMappingService;
    }
    //@Autowired
    public void setSaleDepartmentService(ISaleDepartmentService saleDepartmentService) {
        this.saleDepartmentService = saleDepartmentService;
    }
    //@Autowired
    public void setGoodsAreaService(IGoodsAreaService goodsAreaService) {
        this.goodsAreaService = goodsAreaService;
    }
    //@Autowired
    public void setStockService(IStockService stockService) {
        this.stockService = stockService;
    }
    //@Autowired
    public void setActualFreightService(IActualFreightService actualFreightService) {
        this.actualFreightService = actualFreightService;
    }
    //@Autowired
    public void setStowagePlansService(IStowagePlansService stowagePlansService) {
        this.stowagePlansService = stowagePlansService;
    }
    //@Autowired
    public void setLineService(ILineService lineService) {
        this.lineService = lineService;
    }
    //@Autowired
    public void setExpresslineService(IExpressLineService expresslineService) {
        this.expresslineService = expresslineService;
    }
}