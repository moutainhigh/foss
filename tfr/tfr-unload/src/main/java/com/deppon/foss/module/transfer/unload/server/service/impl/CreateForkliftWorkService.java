package com.deppon.foss.module.transfer.unload.server.service.impl;

import com.deppon.foss.module.base.baseinfo.api.server.service.IGoodsAreaService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.GoodsAreaEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.pickup.pricing.dubbo.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.dubbo.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrCommonService;
import com.deppon.foss.module.transfer.common.api.shared.define.TfrJobBusinessTypeEnum;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobProcessLogEntity;
import com.deppon.foss.module.transfer.load.api.server.service.IPDACommonService;
import com.deppon.foss.module.transfer.load.api.shared.dto.TrayScanDto;
import com.deppon.foss.module.transfer.pda.api.shared.domain.PDATrayScanDetailEntity;
import com.deppon.foss.module.transfer.pda.api.shared.domain.PDATrayScanTaskEntity;
import com.deppon.foss.module.transfer.pda.api.shared.domain.QueryTrayScanTaskEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;
import com.deppon.foss.module.transfer.stock.api.shared.domain.StockEntity;
import com.deppon.foss.module.transfer.unload.api.server.dao.IPDATrayScanDao;
import com.deppon.foss.module.transfer.unload.api.server.service.ICreateForkliftWorkService;
import com.deppon.foss.module.transfer.unload.api.shared.define.UnloadConstants;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.define.FossConstants;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CreateForkliftWorkService implements ICreateForkliftWorkService {
    static final Logger logger = LoggerFactory
            .getLogger(CreateForkliftWorkService.class);
    /**
     * The padTrayScanDao
     */
    private IPDATrayScanDao pdaTrayScanDao;

    public void setPdaTrayScanDao(IPDATrayScanDao pdaTrayScanDao) {
        this.pdaTrayScanDao = pdaTrayScanDao;
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
     * The waybillDao
     */
    private IWaybillManagerService waybillManagerService;

    public void setWaybillManagerService(
            IWaybillManagerService waybillManagerService) {
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

    private ITfrCommonService tfrCommonService;

    public void setTfrCommonService(ITfrCommonService tfrCommonService) {
        this.tfrCommonService = tfrCommonService;
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
     * 数据字典
     */
    private IConfigurationParamsService configurationParamsService;

    public void setConfigurationParamsService(
            IConfigurationParamsService configurationParamsService) {
        this.configurationParamsService = configurationParamsService;
    }

    /**
     * 查询未生成叉车工作量任务，生成叉车工作量
     *
     * @author foss-heyongdong
     * @date 2013-8-20 11:17:29
     * @see com.deppon.foss.module.transfer.unload.api.server.service.ICreateForkliftWorkService#createForkliftWork(Date
     *, Date, int, int)
     */
    @Override
    public void createForkliftWork(Date bizJobStartTime, Date bizJobEndTime,
                                   int threadNo, int threadCount) {
        while (true) {
            // 获取未生成叉车工作量的托盘任务
            List<PDATrayScanTaskEntity> trayScanTasks = pdaTrayScanDao
                    .queryNotForkliftWork(bizJobStartTime, bizJobEndTime, threadNo,
                            threadCount);
            if (trayScanTasks.size() <= ConstantsNumberSonar.SONAR_NUMBER_300) {
                return;
            }
            if (CollectionUtils.isNotEmpty(trayScanTasks)
                    && trayScanTasks.size() > 0) {
                for (PDATrayScanTaskEntity trayScanTask : trayScanTasks) {
                    try {
                        // 统计叉车票数
                        calculateForkliftCounts(trayScanTask);
                    } catch (Exception e) {
                        logger.error(e.getMessage());

                        TfrJobProcessLogEntity jobProcessLogEntity = new TfrJobProcessLogEntity();
                        jobProcessLogEntity
                                .setBizName(TfrJobBusinessTypeEnum.LOADER_FORKLIFT_WORK
                                        .getBizName());
                        jobProcessLogEntity
                                .setBizCode(TfrJobBusinessTypeEnum.LOADER_FORKLIFT_WORK
                                        .getBizCode());
                        jobProcessLogEntity.setExecBizId(trayScanTask.getId());
                        jobProcessLogEntity
                                .setRemark("叉车票统计失败，叉车统计任务号：" + trayScanTask.getTaskNo());
                        jobProcessLogEntity.setExceptionInfo(e.getMessage());
                        jobProcessLogEntity.setCreateTime(Calendar.getInstance()
                                .getTime());

                        tfrCommonService.addJobProcessLog(jobProcessLogEntity);
                    }

                }
            }
        }
    }

    /**
     * 统计叉车票数
     *
     * @param trayScanTask
     * @author 105869-foss-heyongdong
     * @date 2013-8-12 15:10:27
     */
    @Override
    @Transactional
    public int calculateForkliftCounts(PDATrayScanTaskEntity trayScanTask) {

        logger.error("统计叉车票数开始：叉车扫描任务号：" + trayScanTask.getTaskNo() + "\t叉车扫描外场：" + trayScanTask.getOutfieldCode());
        // 任务号
        String taskNo = trayScanTask.getTaskNo();
        // 外出编码
        String outfieldcode = trayScanTask.getOutfieldCode();
        // 托盘创建时间
        Date createTime = trayScanTask.getTraytaskCreatTime();

        // 创建查询条件
        QueryTrayScanTaskEntity querydetail = new QueryTrayScanTaskEntity();
        querydetail.setTaskNo(taskNo);
        // 查询叉车任务明细
        List<PDATrayScanDetailEntity> trayTaskDetails = pdaTrayScanDao
                .queryTrayScanDetail(querydetail);
        // 定义叉车票数
        int forkliftcount = 0;
        // 派送的叉车票数
        int delivercount = 0;
        // 快递叉车票数
        int expressCount = 0;
        // 是否存在快递运单
        boolean isExpressWaybill = false;
        // 标价是否有未入库的运单
        boolean isInStock = false;
        // 统计目的站的set存放的是库区编号
        Set<String> destnames = new HashSet<String>();

        // 根据外场说有计算卡普 AB的库区
        List<GoodsAreaEntity> karpABAreaList = goodsAreaService
                .queryCountingModeGoodsAreaListByOrganizationCode(outfieldcode);
        // 用于每个库区统计票数的变量
        Map<String, boolean[]> karpABArea = new HashMap<String, boolean[]>();
        // 没有库区对应的实体关系变量
        Map<String, GoodsAreaEntity> areaEntityMap = new HashMap<String, GoodsAreaEntity>();
        for (int i = 0; i < karpABAreaList.size(); i++) {
            boolean[] karpAB = new boolean[UnloadConstants.SONAR_NUMBER_8];
            karpABArea.put(karpABAreaList.get(i).getGoodsAreaCode(), karpAB);
            areaEntityMap.put(karpABAreaList.get(i).getGoodsAreaCode(),
                    karpABAreaList.get(i));
        }

        // 根据部门查询货区
        List<GoodsAreaEntity> goodsAreaList = goodsAreaService
                .queryGoodsAreaListByType(outfieldcode,
                        DictionaryValueConstants.BSE_GOODSAREA_TYPE_STATION);

        if (CollectionUtils.isNotEmpty(goodsAreaList)
                && goodsAreaList.size() > 0
                && StringUtils.isNotBlank(goodsAreaList.get(0).getNotes())) {
            /**
             * 存在派送库区，则需要统计派送库区票数。 这一段代码主要是把库区查询出来，以1-3类似为单位存放到一个二维数组中 以便后面比较
             */
            // 获取库区的分区，分区存在于备注中，格式为
            // :[1-3,4-6,7,8<]上海派送库区或者[1-3,4-6,7,8-14]上海派送库区
            GoodsAreaEntity goodsArea = goodsAreaList.get(0);
            String strNotes = goodsArea.getNotes();

            // 格式变为：(s001,s003)[1,2,3-5,6-10,11<];(s002)[1,2-5,6,7-8];(ZT)[1<]
            // 存储行政区域编码
            Map<String, List<String>> map1 = new HashMap<String, List<String>>();
            // 存储
            Map<String, List<String>> map2 = new HashMap<String, List<String>>();
            String[] strNum = strNotes.split(";");
            // 有多少条记录
            int keyLen = strNum.length;
            // 取arrs中最长的长度，方便建立数组
            int maxLen = 0;
            try {
                for (int i = 0; i < keyLen; i++) {
                    String s = strNum[i];
                    // 货区编码
                    List<String> areas = new ArrayList<String>();
                    // 存放库位
                    List<String> arrs = new ArrayList<String>();
                    int begin = s.indexOf("(");
                    int end = s.indexOf(")");
                    String areasStr = s.substring(begin + 1, end);
                    begin = s.indexOf("[");
                    end = s.indexOf("]");
                    String arrsStr = s.substring(begin + 1, end);
                    String[] areasStrArray = areasStr.split(",");
                    for (String s2 : areasStrArray) {
                        areas.add(s2);// 添加行政区域
                    }
                    String[] arrsStrArray = arrsStr.split(",");
                    int lenM = arrsStrArray.length;
                    if (maxLen < lenM)
                        maxLen = lenM;// 找到最大长度并存到maxLen
                    for (String s3 : arrsStrArray) {
                        arrs.add(s3);// 添加库位信息
                    }
                    map1.put(i + "", areas);// 将获取信息放到map1中
                    map2.put(i + "", arrs);// 将库位信息放到map2中
                }
                // 记录叉车票数
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
                logger.error("统计叉车票数,遍历叉车扫描明细：叉车扫描运单号：" + rs.getWaybillNo() + "\t叉车扫描外场：" + outfieldcode + "\t扫描流水号：" + rs.getSerialNo());
                // 运单号
                String waybillNo = rs.getWaybillNo();
                // 流水号
                String serialNo = rs.getSerialNo();

                // 通过运单号获取运单信息
                WaybillEntity waybillInfo = waybillManagerService
                        .queryWaybillBasicByNo(waybillNo);
                // 若运单信息为空则不计算叉车票数
                if (waybillInfo != null) {

                    if (StringUtils.equals(waybillInfo.getProductCode(), "PACKAGE")
                            || StringUtils.equals(waybillInfo.getProductCode(), "RCP")
                            || StringUtils.equals(waybillInfo.getProductCode(), "EPEP")) {
                        if (!isExpressWaybill) {
                            isExpressWaybill = true;
                            ConfigurationParamsEntity paramEntity = configurationParamsService
                                    .queryConfigurationParamsByOrgCode(
                                            DictionaryConstants.SYSTEM_CONFIG_PARM__TFR,
                                            ConfigurationParamsConstants.TFR_PARM_EXPRESS_FORKLIFTTICKET_PARAMETERS,
                                            outfieldcode);
                            if (paramEntity == null) {
                                expressCount = 1;
                                logger.error("没有配置快递叉车票统计参数，请配置参数！");
                            } else {
                                String expressTickts = paramEntity
                                        .getConfValue();

                                try {
                                    expressCount = Integer
                                            .valueOf(expressTickts);
                                } catch (NumberFormatException e) {
                                    logger.error("配置快递叉车票统计参数有误，请重新配置参数！");
                                    expressCount = 1;
                                }
                            }
                            continue;
                        } else {
                            continue;
                        }
                    }
                    // 查询是否入派送库区
                    List<StockEntity> waybillStock = pdaTrayScanDao
                            .queryWaybillStock(waybillNo, serialNo,
                                    outfieldcode);
                    // 查询到库存
                    if (CollectionUtils.isNotEmpty(waybillStock)
                            && waybillStock.size() > 0) {
                        // 当前库区编码
                        String goodsAreaCode = waybillStock.get(0)
                                .getGoodsAreaCode();
                        // 如果当前运单是到达派送部
                        if (StringUtils.equals(goodsAreaCode,
                                goodsArea.getGoodsAreaCode())) {
                            // 查询运单开单件数
                            int serialCount = waybillInfo.getGoodsQtyTotal();

                            // 库区比较
                            comparisonGoodsArea(keyLen, serialCount, map1,
                                    map2, isInit, waybillInfo);

                        } else {
                            // 通过运单的目的站查找是否最终派送外场
                            String outCode = getCenterCode(waybillInfo
                                    .getCustomerPickupOrgCode());
                            // 如果运单目的站是最终派送外场
                            if (StringUtils.equals(outCode, outfieldcode)) {
                                // 查询运单开单件数
                                int serialCount = waybillInfo
                                        .getGoodsQtyTotal();
                                // 库区比较
                                comparisonGoodsArea(keyLen, serialCount, map1,
                                        map2, isInit, waybillInfo);

                            } else {
                                // 1.2.2如果当前运单不是到达派送部的
                                boolean[] temp = karpABArea.get(goodsAreaCode);
                                if (temp != null) {
                                    // 计算特殊库区的卡普票数
                                    calculationKarpAB(goodsAreaCode,
                                            karpABArea, areaEntityMap,
                                            waybillInfo);
                                } else if (StringUtils
                                        .isNotBlank(goodsAreaCode)) {
                                    // 运单目的站为空则不计算叉车票数
                                    if (!destnames.contains(goodsAreaCode)) {
                                        forkliftcount += 1;
                                    }
                                    destnames.add(goodsAreaCode);
                                }
                            }
                        }
                    } else {

                        // 从库存日志中查找（入库记录）
                        // 查询是否入库在库存表中查询
                        List<StockEntity> waybillinStock = pdaTrayScanDao
                                .queryWaybillInStock(waybillNo, serialNo,
                                        outfieldcode);
                        if (CollectionUtils.isNotEmpty(waybillinStock)
                                && waybillinStock.size() > 0) {
                            // 当前库区编码
                            String goodsAreaCode = waybillinStock.get(0)
                                    .getGoodsAreaCode();
                            // 如果当前运单是到达派送部
                            if (StringUtils.equals(goodsAreaCode,
                                    goodsArea.getGoodsAreaCode())) {
                                // 查询运单开单件数
                                int serialCount = waybillInfo
                                        .getGoodsQtyTotal();
                                // 库区比较
                                comparisonGoodsArea(keyLen, serialCount, map1,
                                        map2, isInit, waybillInfo);
                            } else {
                                // 通过运单的目的站查找是否最终派送外场
                                String outCode = getCenterCode(waybillInfo
                                        .getCustomerPickupOrgCode());
                                // 如果运单目的站是最终派送外场
                                if (StringUtils.equals(outCode, outfieldcode)) {
                                    // 查询运单开单件数
                                    int serialCount = waybillInfo
                                            .getGoodsQtyTotal();
                                    // 库区比较
                                    comparisonGoodsArea(keyLen, serialCount,
                                            map1, map2, isInit, waybillInfo);
                                } else {
                                    // 2.2.2如果当前运单不是到达派送部的
                                    boolean[] temp = karpABArea
                                            .get(goodsAreaCode);
                                    if (temp != null) {
                                        // 计算特殊库区的卡普票数
                                        calculationKarpAB(goodsAreaCode,
                                                karpABArea, areaEntityMap,
                                                waybillInfo);
                                    } else if (StringUtils
                                            .isNotBlank(goodsAreaCode)) {
                                        // 运单目的站为空则不计算叉车票数
                                        if (!destnames.contains(goodsAreaCode)) {
                                            forkliftcount += 1;
                                        }
                                        destnames.add(goodsAreaCode);
                                    }
                                }
                            }
                        } else {
                            logger.error("查询不到运单:" + waybillNo + "流水号："
                                    + serialNo + "在当前部门：" + outfieldcode
                                    + "库存！需要走job统计票数");
                            // 如果有未入库的运单则标记，不影响统计
                            isInStock = true;
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
                // 运单号
                String waybillNo = rs.getWaybillNo();
                // 流水号
                String serialNo = rs.getSerialNo();
                // 查询是否入派送库区
                List<StockEntity> waybillStock = pdaTrayScanDao
                        .queryWaybillStock(waybillNo, serialNo, outfieldcode);

                // 通过运单号获取运单信息
                WaybillEntity waybillInfo = waybillManagerService
                        .queryWaybillBasicByNo(waybillNo);
                // 剔除掉有异常的运单
                if (waybillInfo != null) {
                    if (StringUtils.equals(waybillInfo.getProductCode(), "PACKAGE")
                            || StringUtils.equals(waybillInfo.getProductCode(), "RCP")
                            || StringUtils.equals(waybillInfo.getProductCode(), "EPEP")) {
                        if (!isExpressWaybill) {
                            isExpressWaybill = true;
                            ConfigurationParamsEntity paramEntity = configurationParamsService
                                    .queryConfigurationParamsByOrgCode(
                                            DictionaryConstants.SYSTEM_CONFIG_PARM__TFR,
                                            ConfigurationParamsConstants.TFR_PARM_EXPRESS_FORKLIFTTICKET_PARAMETERS,
                                            outfieldcode);
                            if (paramEntity == null) {
                                expressCount = 1;
                                logger.error("没有配置快递叉车票统计参数，请配置参数！");
                            } else {
                                String expressTickts = paramEntity
                                        .getConfValue();

                                try {
                                    expressCount = Integer
                                            .valueOf(expressTickts);
                                } catch (NumberFormatException e) {
                                    logger.error("配置快递叉车票统计参数有误，请重新配置参数！");
                                    expressCount = 1;
                                }
                            }
                            continue;
                        } else {
                            continue;
                        }
                    }

                    if (CollectionUtils.isNotEmpty(waybillStock)
                            && waybillStock.size() > 0) {
                        String goodsAreaCode = waybillStock.get(0)
                                .getGoodsAreaCode();
                        boolean[] temp = karpABArea.get(goodsAreaCode);
                        if (temp != null) {
                            // 计算特殊库区的卡普票数
                            calculationKarpAB(goodsAreaCode, karpABArea,
                                    areaEntityMap, waybillInfo);
                        } else {

                            // 是否存在相同的目的站，不存在则叉车票数加一
                            if (!destnames.contains(goodsAreaCode)) {
                                forkliftcount += 1;
                            }
                            destnames.add(goodsAreaCode);

                        }
                    } else {
                        List<StockEntity> waybillinStock = pdaTrayScanDao
                                .queryWaybillInStock(waybillNo, serialNo,
                                        outfieldcode);
                        if (CollectionUtils.isNotEmpty(waybillinStock)
                                && waybillinStock.size() > 0) {
                            String goodsAreaCode = waybillinStock.get(0)
                                    .getGoodsAreaCode();
                            boolean[] temp = karpABArea.get(goodsAreaCode);
                            if (temp != null) {
                                // 计算特殊库区的卡普票数
                                calculationKarpAB(goodsAreaCode, karpABArea,
                                        areaEntityMap, waybillInfo);
                            } else {

                                // 是否存在相同的目的站，不存在则叉车票数加一
                                if (!destnames.contains(goodsAreaCode)) {
                                    forkliftcount += 1;
                                }
                                destnames.add(goodsAreaCode);

                            }
                        } else {
                            logger.error("查询不到运单:" + waybillNo + "流水号："
                                    + serialNo + "在当前部门：" + outfieldcode
                                    + "库存！需要走job统计票数");
                            // 如果有未入库的运单则标记，不影响统计
                            isInStock = true;
                        }

                    }

                } else {
                    logger.error("不存在该运单的信息：" + waybillNo);
                }

            }
        }
        // 统计卡普AB货票数
        int kpABcount = 0;
        for (int i = 0; i < karpABAreaList.size(); i++) {
            boolean[] bb = karpABArea.get(karpABAreaList.get(i)
                    .getGoodsAreaCode());
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
        // 新建托盘任务
        TrayScanDto trayScanDto = new TrayScanDto();
        if (forkliftcount == 0) {
            trayScanDto.setForkliftCount(1);
        } else {
            // 设置叉车票数
            trayScanDto.setForkliftCount(forkliftcount);
        }
        // 如果存在未入库的运单，则需要判断是否需要更新统计工作量的状态（即Y或者N）：判断标准是托盘绑定的时间大于当前时间减去一天则更新为Y
        Date nowDate = new Date();
        Date beDate = DateUtils.addDayToDate(nowDate, -1);
        boolean isUpdate = createTime.before(beDate);
        if (isInStock) {
            if (isUpdate) {
                // 设置生成工作量状态
                trayScanDto.setBeCreateWork(FossConstants.YES);
            }
        } else {
            // 设置生成工作量状态
            trayScanDto.setBeCreateWork(FossConstants.YES);
        }
        // 设置任务号
        trayScanDto.setTaskNo(taskNo);
        // 更新任务
        int updateCount = pdaTrayScanDao.updateTrayScanForkliftCount(trayScanDto);
        return updateCount;
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
    private void calculationKarpAB(String goodsAreaCode,
                                   Map<String, boolean[]> karpABArea,
                                   Map<String, GoodsAreaEntity> areaEntityMap,
                                   WaybillEntity waybillInfo) {
        /*
         * 1、通过库区号查询库区信息，是否按照卡普或者AB货计算 2、如果查询出库区信息，有四种情况：a、不计算卡普AB b、计算卡普 c、计算AB
		 * d、计算卡普AB 3、如果为bcd情况，则先通过运单查询运单是卡普以及AB性质 4、 如果计算方式是卡普，但是运单性质不是卡普怎么计算？
		 */

        String[] typeOfArea = {"A", "B", "卡", "普", "卡A", "卡B", "普A", "普B"};
        String typeAB = waybillInfo.getGoodsTypeCode();
        String typeKarp = "";
        String typeAll = "";
        ProductEntity productEntity = productService4Dubbo
                .getLevel3ProductInfo(waybillInfo.getProductCode());
        logger.info("CreateForkliftWorkService叉车票数统计 调用计价接口成功 productEntity = {}" , productEntity);
        if (StringUtils.equals("FAST", productEntity.getPriority())) {
            typeKarp = "卡";
        } else {
            typeKarp = "普";
        }

        typeAll = productEntity.getShortName() + typeAB;
        // 获取计票类型
        String calculationType = areaEntityMap.get(goodsAreaCode)
                .getCountingMode();
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
     * 叉车票统计方法
     *
     * @param keyLen      map中key长度
     * @param serialCount 开单件数
     * @param map1        行政区域编码
     * @param map2        库位数据
     * @param isInit      返回叉车票数
     * @param waybillInfo 运单信息
     * @Author: 200978 xiaobingcheng 2014-10-31
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    private void comparisonGoodsArea(int keyLen, int serialCount, Map map1,
                                     Map map2, int isInit[][], WaybillEntity waybillInfo) {
        // 行政区域
        String distCode = waybillInfo.getReceiveCustomerDistCode();
        if (distCode == null || distCode.equals("")) {
            return;
        }
        // 提货方式
        String receiveMethod = waybillInfo.getReceiveMethod();
        // 如果是自提
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
                        boolean temp = comparisonResult(serialCount,
                                arrs.get(j));
                        if (temp) {
                            isInit[i][j] = 1;
                        }
                    }
                    break;// 找到就跳出里层循环
                }
            }
        }
    }

    /**
     * 比较这个运单件数是否在这个库位 如果再则返回true
     *
     * @param serialCount 开单件数
     * @param str         库位件数长度 1/2-5/6<...
     * @return
     * @Author: 200978 xiaobingcheng 2014-10-31
     */
    private boolean comparisonResult(int serialCount, String str) {
        logger.error("托盘件数：" + serialCount + "----叉车库位数" + str);
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

}
