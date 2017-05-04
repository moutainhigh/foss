package com.deppon.foss.module.settlement.common.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.classes.BeanUtils;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.settlement.common.api.server.dao.IWSCManageDao;
import com.deppon.foss.module.settlement.common.api.server.service.IWSCManageService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.PosCardDetailEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.PosCardEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.WSCEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.util.CollectionUtils;

/**
 * Created by yaq on 2016/5/31.
 */
public class WSCManageService implements IWSCManageService {

    /**
     * 获取日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(WSCManageService.class);
    private static final int ZERO = 0;
    private IWSCManageDao wscManageDao;
    private IBusinessLockService businessLockService;
    private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;

    /**
     * 快递补录时
     * 根据最新传入的金额生成一条未支付的待刷卡单据
     * 再根据交易流水号，获取数据库中最新的未使用金额，来对新增的单据进行支付。
     *
     * @param params
     */
    @Transactional
    @Override
    public void payWscEntityForESC(WSCEntity params) {
        LOGGER.info("快递补录开始----");
        this.validateParams(params, null);
        String wayBillNo = params.getWayBillNo();
        String serialNo = params.getSerialNo();
        this.validateParams(wayBillNo, "运单号---");
        //新增未支付的待刷卡单据
        WSCEntity wscEntity = new WSCEntity();
        BeanUtils.copyProperties(params, wscEntity);
        wscEntity.setSerialNo(null);
        //插入最新的待刷卡单据
        this.insertWSCWayBill(wscEntity);
        Map<String, Object> map = this.queryWscWayBillAll(wayBillNo);
        wscEntity = (WSCEntity) map.get("N");
        //获取明细
		List<PosCardDetailEntity> list = (List<PosCardDetailEntity>) map.get("posCardDetailEntity");
		list = list == null || list.size() == 0 ? new ArrayList<PosCardDetailEntity>() : list;
        for (PosCardDetailEntity entity : list) {
            serialNo = entity.getTradeSerialNo();
            params.setSerialNo(serialNo);
            break;
        }
        serialNo = serialNo == null ? "没有明细" : serialNo;
        //获取T+0数据    存在数据不同步的问题
        PosCardEntity posCardEntity;
        try {
            posCardEntity = this.getPosCardEntity(serialNo);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.info("快递补录结束----新增待刷卡单据");
            return;
        }
        //T+0中可用金额
        Double payAmount = new Double(posCardEntity.getUnUsedAmount().toString());
        //设置真实支付金额
        payAmount = payAmount > params.getWaitSwipeAmount() ? params.getWaitSwipeAmount() : payAmount;
        params.setWaitSwipeAmount(payAmount);
        params.setAlreadySwipeAmount(payAmount);
        this.payWscEntity(wscEntity, params);
        LOGGER.info("快递补录结束----");
    }

    /**
     * 快递PDA刷卡存入PosCard和PosCardDetail
     *
     * @param params PosCardEntity
     */
    @Transactional
    @Override
    public void addPosCardAndDetail(PosCardEntity params) {
        if (null == params || StringUtil.isNullOrEmpty(params.getTradeSerialNo())) {
            throw new SettlementException("传入参数有误");
        }
        try {
            //通过交易流水号获取PosCard信息
            this.getPosCardEntity(params.getTradeSerialNo());
        } catch (BusinessException e) {//不存在，插入新的PosCard信息
            //设置交易流水的一些默认值
            this.addDefaultValues(params);
            this.insertPosCard(params);
            List<PosCardDetailEntity> posCardDetailEntitys = params.getPosCardDetailEntitys();
			if (CollectionUtils.isEmpty(posCardDetailEntitys)) {// 数据不全不存
				return;
			}
            for (PosCardDetailEntity entity : posCardDetailEntitys) {
                if (StringUtil.isNullOrEmpty(entity.getInvoiceNo()) ||
                        StringUtil.isNullOrEmpty(entity.getTradeSerialNo()) ||
                        StringUtil.isNullOrEmpty(entity.getInvoiceType())) {//数据不全不存
                    continue;
                }
                try {
                    this.getPosCardDetailEntity(entity.getInvoiceNo(), entity.getTradeSerialNo(), entity.getInvoiceType());
                } catch (BusinessException e1) {
                    //插入对应明细
                    this.insertPosCardDetail(entity);
                }
            }
        }
    }

    /**
     * 设置T+0默认参数
     *
     * @param posCardEntity
     */
    private void addDefaultValues(PosCardEntity posCardEntity) {
        List<String> bizTypes = new ArrayList<String>();
        // 设置类型集合
        bizTypes.add("SALES_DEPARTMENT");// 营业部
        OrgAdministrativeInfoEntity entityInfo = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoByCode(posCardEntity.getCardDeptCode(), bizTypes);
        entityInfo = entityInfo == null ? new OrgAdministrativeInfoEntity() : entityInfo;
        String businessAreaCode = entityInfo.getParentOrgCode();
        /**
         * 设置营业区的bizTypes
         */
        bizTypes.clear();// 清空所有的值
        // 重新设置类型
        bizTypes.add("SMALL_REGION");
        // 根据营业区去获取上级部门
        OrgAdministrativeInfoEntity entityInfo2 = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoByCode(businessAreaCode, bizTypes);
        entityInfo2 = entityInfo2 == null ? new OrgAdministrativeInfoEntity() : entityInfo2;
        //所属大区  所属大区编码  所属大区标杆编码
        posCardEntity.setBelongRegionName(entityInfo2.getParentOrgName());
        posCardEntity.setBelongRegionCode(entityInfo2.getParentOrgCode());
        posCardEntity.setBelongRegionBMCode(entityInfo2.getParentOrgUnifiedCode());

        //所属营业区名称  所属营业区编码  所属营业区标杆编码
        posCardEntity.setBusinessAreaName(entityInfo2.getName());
        posCardEntity.setBusinessAreaCode(entityInfo2.getCode());
        posCardEntity.setBusinessAreaBMCode(entityInfo2.getUnifiedCode());
        //设置默认值
        posCardEntity.setBelongModule("快递");
        this.validateParams(posCardEntity.getUsedAmount(), "流水号金额");
        posCardEntity.setCardTime(posCardEntity.getCardTime() == null ? new Date() : posCardEntity.getCardTime());
        posCardEntity.setUnUsedAmount(posCardEntity.getUnUsedAmount() == null ? BigDecimal.ZERO : posCardEntity.getUnUsedAmount());
        posCardEntity.setCardDeptName(StringUtil.isNullOrEmpty(posCardEntity.getCardDeptName()) ? " " : posCardEntity.getCardDeptName());
        posCardEntity.setCardDeptCode(StringUtil.isNullOrEmpty(posCardEntity.getCardDeptCode()) ? " " : posCardEntity.getCardDeptCode());
        posCardEntity.setCardDeptBMCode(StringUtil.isNullOrEmpty(posCardEntity.getCardDeptBMCode()) ? " " : posCardEntity.getCardDeptBMCode());
        posCardEntity.setBusinessAreaName(StringUtil.isNullOrEmpty(posCardEntity.getBusinessAreaName()) ? " " : posCardEntity.getBusinessAreaName());
        posCardEntity.setBusinessAreaCode(StringUtil.isNullOrEmpty(posCardEntity.getBusinessAreaCode()) ? " " : posCardEntity.getBusinessAreaCode());
        posCardEntity.setBusinessAreaBMCode(StringUtil.isNullOrEmpty(posCardEntity.getBusinessAreaBMCode()) ? " " : posCardEntity.getBusinessAreaBMCode());
        posCardEntity.setBelongRegionName(StringUtil.isNullOrEmpty(posCardEntity.getBelongRegionName()) ? " " : posCardEntity.getBelongRegionName());
        posCardEntity.setBelongRegionCode(StringUtil.isNullOrEmpty(posCardEntity.getBelongRegionCode()) ? " " : posCardEntity.getBelongRegionCode());
        posCardEntity.setBelongRegionBMCode(StringUtil.isNullOrEmpty(posCardEntity.getBelongRegionBMCode()) ? " " : posCardEntity.getBelongRegionBMCode());
        posCardEntity.setBusinessDeptName(StringUtil.isNullOrEmpty(posCardEntity.getBusinessDeptName()) ? " " : posCardEntity.getBusinessDeptName());
        posCardEntity.setBusinessDeptCode(StringUtil.isNullOrEmpty(posCardEntity.getBusinessDeptCode()) ? " " : posCardEntity.getBusinessDeptCode());
        posCardEntity.setBusinessDeptBMCode(StringUtil.isNullOrEmpty(posCardEntity.getBusinessDeptBMCode()) ? " " : posCardEntity.getBusinessDeptBMCode());
        posCardEntity.setCardTime(new Date());
    }

    /**
     * 更改运单号
     * 运单号发生更改，更新T+0明细，和待刷卡数据
     */
    @Transactional
    @Override
    public void changeWayBillNo(WSCEntity params) {
        LOGGER.info("运单更改开始----");
        this.validateParams(params, null);
        String oldWayBillNo = params.getOldWayBillNo();
        String wayBillNo = params.getWayBillNo();
        this.validateParams(oldWayBillNo, "运单号---");
        this.validateParams(wayBillNo, "运单号---");
        this.updateAllParams(params, oldWayBillNo, wayBillNo, oldWayBillNo);
    }

    /**
     * 运单新增 待刷卡
     * 运单更改 待刷卡
     * 运单刷卡 待刷卡
     * 操作  -----  add by 309603 yangqiang
     */
    @Transactional
    @Override
    public void changeWayBill(WSCEntity params) {
        LOGGER.info("运单更改开始----");
        this.validateParams(params, null);
        String oldWayBillNo = params.getOldWayBillNo();
        String wayBillNo = params.getWayBillNo();
        this.validateParams(wayBillNo, "运单号---");
        //获取数据库存在的运单号
        String queryNo = oldWayBillNo == null ? wayBillNo : oldWayBillNo;
        MutexElement mutexElement = new MutexElement(queryNo, "待刷卡", MutexElementType.VTS_FOSS_WSC_WAYBILL);
        //互斥锁定
        boolean isLocked = businessLockService.lock(mutexElement, ZERO);
        //如果没有上锁
        if (!isLocked) {
            //返回异常
            throw new SettlementException("该运单操作中请稍后重试");
        }
        Map<String, Object> map = this.queryWscWayBillAll(queryNo);
        List<PosCardDetailEntity> list = (List<PosCardDetailEntity>) map.get("posCardDetailEntity");
        WSCEntity wscEntity = (WSCEntity) map.get("N");
        if (!StringUtil.isNUllOrWhiteSpace(params.getSerialNo())) {
            this.payWscEntity(wscEntity, params);
            businessLockService.unlock(mutexElement);//解锁
            return;//支付待刷卡数据
        }
        //判断是否为新增
        if ("1".equals(params.getWayBillSource())) {
            this.addWSCEntity(params, map);
            businessLockService.unlock(mutexElement);//解锁
            return;//场景一：新增待刷卡
        }
        if ((null == list || list.size() == 0) && null == wscEntity) {
            this.insertWSCWayBill(params);
            businessLockService.unlock(mutexElement);//解锁
            return;//场景一：现金改银行卡 新增待刷卡
        }
        //获取待刷卡总金额
        Double totalAmount = this.sumTotalAmount(map);
        Double diff = params.getWaitSwipeAmount() - totalAmount;
        //待刷卡总金额发生变化
        this.amoutChange(params, diff, map);
        //更新待刷卡其他数据
        this.updateAllParams(params, oldWayBillNo, wayBillNo, queryNo);
        businessLockService.unlock(mutexElement);//解锁
        LOGGER.info("运单更改结束----");
    }

    /**
     * 根据交易流水号，运单号释放指定金额
     *
     * @param amount    释放金额
     * @param batchNo   交易流水号
     * @param waybillNo 运单号
     * @param invoiceType 交易明细单据类型 W1 待刷卡  W2 结清货款
     */
    @Override
    public void reversPosCardPosCard(BigDecimal amount, String batchNo, String waybillNo,String invoiceType) {
        this.validateParams(amount, "传入金额不能为空");
        this.validateParams(batchNo, "传入交易流水号不能为空");
        this.validateParams(waybillNo, "传入运单号不能为空");
        //获取明细 交易流水号
        PosCardDetailEntity posCardDetailEntity = this.getPosCardDetailEntity(waybillNo, batchNo,invoiceType).get(0);
        this.releaseAmount(posCardDetailEntity, new Double(amount.toString()));
    }

    /**
     * 支付待刷卡金额
     *
     * @param wscEntity 数据库未支付单据
     * @param params
     */
    private void payWscEntity(WSCEntity wscEntity, WSCEntity params) {
        //不存在待刷卡单据，不用支付
        if (null == wscEntity) {
            return;
        }
        Double alreadySwipeAmount = params.getAlreadySwipeAmount();
        Double waitSwipeAmount = wscEntity.getWaitSwipeAmount();
        Double payAmount = waitSwipeAmount <= alreadySwipeAmount ? waitSwipeAmount : alreadySwipeAmount;
        this.moneyParams(params, payAmount, payAmount);
        //设置一些参数相关
        params.setPaymentStatus("Y");
        params.setWayBillAmount(wscEntity.getWayBillAmount());
        Map<String, Object> mapInit = this.mapInit(params, wscEntity.getId(), null);
        this.updateWSCWayBillByID(mapInit);
        if (waitSwipeAmount > alreadySwipeAmount) {//待刷卡金额大于已刷卡金额，新增一条差额待刷卡
            this.moneyParams(wscEntity, waitSwipeAmount - alreadySwipeAmount, new Double("0"));
           //wscEntity.setWayBillSource("2");
            wscEntity.setWayBillSource("2");
            this.insertWSCWayBill(wscEntity);
        }
        //更新T+0明细
        PosCardDetailEntity posCardDetailEntity = this.getPosCardDetailEntity(params.getWayBillNo(), params.getSerialNo(), "W1").get(0);
        posCardDetailEntity.setOccupateAmount(new BigDecimal(payAmount + ""));
        //posCardDetailEntity.setUnVerifyAmount(posCardDetailEntity.getUnVerifyAmount().divide(new BigDecimal(payAmount + "")));
        posCardDetailEntity.setAmount(new BigDecimal(params.getWayBillAmount() + ""));
        posCardDetailEntity.setUnVerifyAmount(BigDecimal.ZERO);
        mapInit = this.mapInit(posCardDetailEntity, posCardDetailEntity.getId(), mapInit);
        this.updatePosCardDetailByID(mapInit);
        //更新T+0
        PosCardEntity posCardEntity = this.getPosCardEntity(params.getSerialNo());
        posCardEntity.setUsedAmount(posCardEntity.getUsedAmount().add(new BigDecimal(payAmount + "")));
        posCardEntity.setUnUsedAmount(posCardEntity.getUnUsedAmount().subtract(new BigDecimal(payAmount + "")));
        mapInit = this.mapInit(posCardEntity, posCardEntity.getId(), mapInit);
        this.updatePosCardByID(mapInit);
    }

    /**
     * 运单新增
     *
     * @param params
     * @param map
     */
    private void addWSCEntity(WSCEntity params, Map<String, Object> map) {
        List<WSCEntity> list = (List<WSCEntity>) map.get("Y");
        WSCEntity entity = (WSCEntity) map.get("N");
        if (null == entity && (null == list || list.size() == 0)) {
            this.insertWSCWayBill(params);
            return;
        }
        throw new SettlementException("存在待刷卡单据，不允许新增");
    }

    /**
     * 更新所有变更参数，与金钱无关的其他数据
     *
     * @param params
     * @param oldWayBillNo
     * @param wayBillNo
     * @param queryNo
     */
    private void updateAllParams(WSCEntity params, String oldWayBillNo, String wayBillNo, String queryNo) {
        //根据运单号更新其他与钱无关的参数
        WSCEntity wscEntity = new WSCEntity();
        wscEntity.setSendCustomerCode(params.getSendCustomerCode());
        wscEntity.setSendCustomerName(params.getSendCustomerName());
        wscEntity.setCreateBillOrgCode(params.getCreateBillOrgCode());
        wscEntity.setCreateBillOrgName(params.getCreateBillOrgName());
        wscEntity.setWayBillAmount(params.getWayBillAmount());
        wscEntity.setWayBillSource(params.getWayBillSource());
        wscEntity.setWayBillNo(wayBillNo);
        //与金钱无关的一些参数
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("newEntity", wscEntity);
        map.put("wayBillNo", queryNo);
        this.updateWSCWayBillByWayBillNo(map);
        if (oldWayBillNo != null) {//如果单号更改
            PosCardDetailEntity posCardDetailEntity = new PosCardDetailEntity();
            posCardDetailEntity.setInvoiceNo(wayBillNo);
            map.put("newEntity", posCardDetailEntity);
            map.put("invoiceNo", oldWayBillNo);
            try {
                this.updatePosCardDetailByWayBillNo(map);
            } catch (BusinessException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 待刷卡金额发生变化
     * d>0 金额增加
     * d<0 金额减少
     */
    private void amoutChange(WSCEntity params, Double d, Map<String, Object> map) {
        //获取未支付的待刷卡单据
        WSCEntity wc = (WSCEntity) map.get("N");
        if (wc == null) {//没有未支付的待刷卡单据
            if (d > 0.0) {//金额增加
                this.moneyParams(params, d, new Double("0"));//设置金额相关
                this.insertWSCWayBill(params);//插入数据
                return;//场景二：运单更改，金额增加，待刷卡单据未支付未0.
            } else if (d < 0.0) {//金额减少
                //根据金额循环已经刷卡金额释放金额
                this.releaseAmount((List<PosCardDetailEntity>) map.get("posCardDetailEntity"), d * (-1));
                return;
            }
        }
        if (wc != null) {//有待刷卡金额
            WSCEntity wscEntity = new WSCEntity();
            double b = wc.getWaitSwipeAmount() + d;
            //设置修改人相关
            wscEntity.setModifyUserCode(params.getModifyUserCode());
            wscEntity.setModifyUserName(params.getModifyUserName());
            wscEntity.setModifyTime(new Date());
            Map<String, Object> mapInit = this.mapInit(wscEntity, wc.getId(), null);
            d = b >= 0.0 ? b : new Double("0");
            if (b <= 0.0) {
                wscEntity.setActive("N");
            }
            this.moneyParams(wscEntity, d, new Double("0"));//设置金额相关
            this.updateWSCWayBillByID(mapInit);//场景三：运单更改，金额增加，有未支付的待刷卡单据
            if (b < 0.0) {
                this.releaseAmount((List<PosCardDetailEntity>) map.get("posCardDetailEntity"), new Double((-1) * b));//没有释放完的金额，从已支付中释放
            }
        }
    }

    /**
     * 根据金额循环已经刷卡金额释放金额
     * d为整且大于0
     */
    private void releaseAmount(List<PosCardDetailEntity> list, Double d) {
        for (PosCardDetailEntity entity : list) {
            //判断每一个待刷卡单据所能释放的金额
            Double alreadySwipeAmount = new Double(entity.getOccupateAmount().toString());//已刷卡金额
            if (d == 0.0) {
                return;
            }
            if (d >= alreadySwipeAmount) {
                this.releaseAmount(entity, alreadySwipeAmount);
                d -= alreadySwipeAmount;
            } else {
                this.releaseAmount(entity, d);
                return;
            }
        }
    }

    /**
     * b>0; 要释放的金额
     *
     * @param entity 已支付的待刷卡实体
     *               相关联的有PosCardDetail T+0明细释放
     *               相关联的有PosCard       T+0释放
     * @param b
     */
    private void releaseAmount(PosCardDetailEntity entity, Double b) {
        //判断各个待刷卡单据 该释放的金额数
        double alreadySwipeAmount = new Double(entity.getOccupateAmount().toString());
        double d = alreadySwipeAmount - b;
        //根据交易流水号和运单号查询明细里面金额
        //释放posCardDetailEntity里面占用金额
        entity.setOccupateAmount(entity.getOccupateAmount().subtract(new BigDecimal(b + "")));
        entity.setUnVerifyAmount(entity.getUnVerifyAmount().add(new BigDecimal(b + "")));
        if (d == 0.0) {
            entity.setIsDelete("Y");
        }
        Map<String, Object> map = mapInit(entity, entity.getId(), null);
        this.updatePosCardDetailByID(map);
        //根据交易流水号查询T+0
        PosCardEntity posCardEntity = getPosCardEntity(entity.getTradeSerialNo());
        posCardEntity.setUsedAmount(posCardEntity.getUsedAmount().subtract(new BigDecimal(b + "")));
        posCardEntity.setUnUsedAmount(posCardEntity.getUnUsedAmount().add(new BigDecimal(b + "")));
        map = mapInit(posCardEntity, posCardEntity.getId(), map);
        this.updatePosCardByID(map);
    }

    /**
     * 根据交易流水号查询T+0
     *
     * @param serialNo
     * @return
     */
    private PosCardEntity getPosCardEntity(String serialNo) {
        //释放posCard指定金额
        PosCardEntity posCardEntity = new PosCardEntity();
        posCardEntity.setFields("ID,USED_AMOUNT/100 USED_AMOUNT,UNUSED_AMOUNT/100 UNUSED_AMOUNT");
        posCardEntity.setActive("Y");
        posCardEntity.setTradeSerialNo(serialNo);
        List<PosCardEntity> posCardEntities = this.queryPosCard(posCardEntity);
        this.validateParams(posCardEntities, "交易流水号", false);
        //验证唯一
        if (posCardEntities.size() > 1) {
            throw new SettlementException("交易流水号只允许存在一条");
        }
        posCardEntity = posCardEntities.get(0);
        return posCardEntity;
    }

    /**
     * 根据交易流水号和运单号查询明细里面金额
     *
     * @param wayBillNo            运单号
     * @param serialNo             交易流水号
     * @param invoiceType           单据类型
     * @return
     */
    private List<PosCardDetailEntity> getPosCardDetailEntity(String wayBillNo, String serialNo,String invoiceType) {
        //查询出POS明细。
        PosCardDetailEntity posCardDetailEntity = new PosCardDetailEntity();
        posCardDetailEntity.setFields("ID,AMOUNT/100 AMOUNT,OCCUPATE_AMOUNT/100 OCCUPATE_AMOUNT,UNVERIFY_AMOUNT/100 UNVERIFY_AMOUNT,TRADE_SERIAL_NO");
        posCardDetailEntity.setInvoiceNo(wayBillNo);
        posCardDetailEntity.setTradeSerialNo(serialNo);
        posCardDetailEntity.setIsDelete("N");
        posCardDetailEntity.setInvoiceType(invoiceType);
        List<PosCardDetailEntity> posCardDetailEntities = this.queryPosCardDetail(posCardDetailEntity);
        //验证唯一
        if (null != serialNo) {
            this.validateParams(posCardDetailEntities, "交易明细不存在", false);
            if (posCardDetailEntities.size() > 1) {
                throw new SettlementException("交易流水号只允许存在一条");
            }
        }
//        posCardDetailEntity = posCardDetailEntities.get(0);
        return posCardDetailEntities;
    }

    /**
     * 与金钱有关的参数
     *
     * @param params
     */
    private void moneyParams(WSCEntity params, Double waitSwipeAmount, Double alreadySwipeAmount) {
        params.setWaitSwipeAmount(waitSwipeAmount);
        params.setAlreadySwipeAmount(alreadySwipeAmount);
    }

    /**
     * 获取待刷卡总金额
     */
    private Double sumTotalAmount(Map<String, Object> map) {
        Double b = new Double("0");
        List<PosCardDetailEntity> list = (List<PosCardDetailEntity>) map.get("posCardDetailEntity");
        if (list != null) {
            for (PosCardDetailEntity entity : list) {
                b += new Double(entity.getOccupateAmount().toString());
            }
        }
        WSCEntity wc = (WSCEntity) map.get("N");
        if (wc != null) {
            b += wc.getWaitSwipeAmount();
        }
        return b;
    }

    /**
     * 查询未删除的所有待刷卡单据
     */
    private Map<String, Object> queryWscWayBillAll(String queryNo) {
        WSCEntity query = new WSCEntity();
        query.setActive("Y");
        query.setWayBillNo(queryNo);
        String fields = "ID,WAYBILL_NO,WAYBILL_SOURCE,SEND_CUSTOMER_CODE,SEND_CUSTOMER_NAME,CREATE_BILL_ORG_CODE, CREATE_BILL_ORG_NAME,WAYBILL_AMOUNT/100 WAYBILL_AMOUNT," +
                "PAYMENT_STATUS,WAIT_SWIPE_AMOUNT/100 WAIT_SWIPE_AMOUNT,ALREADY_SWIPE_AMOUNT/100 ALREADY_SWIPE_AMOUNT,SERIAL_NO,CREATE_BILL_TIME";
        query.setFields(fields);
        List<WSCEntity> list = this.queryWSCWayBill(query);
        Map<String, Object> map = new HashMap<String, Object>();
        for (int i = 0; i < list.size(); i++) {
            WSCEntity entity = list.get(i);
            if ("N".equals(entity.getPaymentStatus())) {
                map.put("N", entity);
                list.remove(i);
                i--;
            } else {
                this.validateParams(entity.getSerialNo(), "已支付待刷卡交易流水", false);
            }
        }
        Collections.sort(list, new Comparator<WSCEntity>() {
            @Override
            public int compare(WSCEntity o1, WSCEntity o2) {
                return o1.getCreateBillTime().before(o2.getCreateBillTime()) ? -1 : 1;
            }
        });
        map.put("Y", list);
        //获取交易明细
        List<PosCardDetailEntity> posCardDetailEntity = this.getPosCardDetailEntity(queryNo, null, "W1");
        map.put("posCardDetailEntity", posCardDetailEntity);
        return map;
    }

    /**
     * 初始化Map
     */
    private Map<String, Object> mapInit(Object params, String id, Map<String, Object> map) {
        if (null == map) {
            map = new HashMap<String, Object>();
        }
        map.clear();
        map.put("newEntity", params);
        map.put("id", id);
        return map;
    }

    /**
     * 对待刷卡单据的一些常用操作----add by  309603  yangqiang
     */
    @Override
    public int insertWSCWayBill(WSCEntity params) {
        LOGGER.info("插入待刷卡单据开始----");
        params.setActive("Y");
        params.setPaymentStatus("N");
        params.setCreateTime(new Date());
        params.setAlreadySwipeAmount(new Double("0"));
        params.setNotes("运单开单，新增运单");
        //"发货客户名称"  "收货部门编号"   "收货部门名称" 设置默认值
        params.setSendCustomerCode(StringUtil.isNullOrEmpty(params.getSendCustomerCode()) ? " " : params.getSendCustomerCode());
        params.setSendCustomerName(StringUtil.isNullOrEmpty(params.getSendCustomerName()) ? " " : params.getSendCustomerName());
        params.setCreateBillOrgCode(StringUtil.isNullOrEmpty(params.getCreateBillOrgCode()) ? " " : params.getCreateBillOrgCode());
        params.setCreateBillOrgName(StringUtil.isNullOrEmpty(params.getCreateBillOrgName()) ? " " : params.getCreateBillOrgName());
        this.validateParams(params);
        if (params.getWaitSwipeAmount() == 0.0) {
            params.setActive("N");
        }
        int result = wscManageDao.insertWSCWayBill(params);
        if (result < 1) {
            throw new SettlementException("插入待刷卡失败");
        }
        LOGGER.info("插入待刷卡单据结束----");
        return result;
    }

    @Override
    public int updateWSCWayBillByID(Map params) {
        LOGGER.info("待刷卡单据开始----");
        this.validateParams(params.get("newEntity"), "更新实体");
        this.validateParams(params.get("id"), "ID");
        int result = wscManageDao.updateWSCWayBillByID(params);
        if (result < 1) {
            throw new SettlementException("更新待刷卡单据失败");
        }
        LOGGER.info("待刷卡单据结束----");
        return result;
    }

    @Override
    public int updateWSCWayBillByWayBillNo(Map params) {
        LOGGER.info("待刷卡单据开始----");
        this.validateParams(params.get("newEntity"), "更新实体");
        this.validateParams(params.get("wayBillNo"), "运单号");
        int result = wscManageDao.updateWSCWayBillByWayBillNo(params);
        LOGGER.info("待刷卡单据结束----");
        return result;
    }

    @Override
    public List<WSCEntity> queryWSCWayBill(WSCEntity params) {
        LOGGER.info("查询待刷卡单据开始----");
        List<WSCEntity> list = wscManageDao.queryWSCWayBill(params);
        LOGGER.info("查询待刷卡单据结束----");
        return list;
    }

    /**
     * 对T+0明细的一些常用操作----add by  309603  yangqiang
     */
    @Override
    public int insertPosCardDetail(PosCardDetailEntity params) {
        LOGGER.info("插入T+0明细单据开始----");
        params.setIsDelete("N");
        params.setCreateDate(new Date());
        int result = wscManageDao.insertPosCardDetail(params);
        if (result < 1) {
            throw new SettlementException("插入T+0明细失败");
        }
        LOGGER.info("插入T+0明细单据结束----");
        return result;
    }

    @Override
    public List<PosCardDetailEntity> queryPosCardDetail(PosCardDetailEntity params) {
        LOGGER.info("查询T+0明细单据开始----");
        List<PosCardDetailEntity> list = wscManageDao.queryPosCardDetail(params);
        LOGGER.info("查询T+0明细单据结束----");
        return list;
    }

    @Override
    public int updatePosCardDetailByID(Map params) {
        LOGGER.info("更新T+0明细单据开始----");
        this.validateParams(params.get("newEntity"), "更新实体");
        this.validateParams(params.get("id"), "ID");
        int result = wscManageDao.updatePosCardDetailByID(params);
        if (result < 1) {
            throw new SettlementException("更新POS刷卡明细失败");
        }
        LOGGER.info("更新T+0明细单据结束----");
        return result;
    }

    @Override
    public int updatePosCardDetailByWayBillNo(Map params) {
        LOGGER.info("更新T+0明细单据开始----");
        this.validateParams(params.get("newEntity"), "更新实体");
        this.validateParams(params.get("invoiceNo"), "运单号");
        int result = wscManageDao.updatePosCardDetailByWayBillNo(params);
        if (result < 1) {
            throw new SettlementException("更新T+0刷卡明细失败");
        }
        LOGGER.info("更新T+0明细单据结束----");
        return result;
    }

    /**
     * 对T+0的一些常用的操作-----add by 309603 yangqiang
     */

    @Override
    public int insertPosCard(PosCardEntity params) {
        LOGGER.info("插入T+0单据开始----");
        params.setCreateDate(new Date());
        int result = wscManageDao.insertPosCard(params);
        if (result < 1) {
            throw new SettlementException("插入T+0失败");
        }
        LOGGER.info("插入T+0单据结束----");
        return result;
    }

    @Override
    public List<PosCardEntity> queryPosCard(PosCardEntity params) {
        LOGGER.info("查询T+0单据开始----");
        List<PosCardEntity> list = wscManageDao.queryPosCard(params);
        LOGGER.info("查询T+0单据结束----");
        return list;
    }

    @Override
    public int updatePosCardByID(Map params) {
        LOGGER.info("更新T+0单据开始----");
        this.validateParams(params.get("newEntity"), "更新实体");
        this.validateParams(params.get("id"), "ID");
        int result = wscManageDao.updatePosCardByID(params);
        LOGGER.info("更新T+0单据结束----");
        if (result < 1) {
            throw new SettlementException("更新POS刷卡失败");
        }
        return result;
    }

    /**
     * 统一对非空字段校验
     * default
     *
     * @param params
     */
    private void validateParams(WSCEntity params) {
        //对数据库非空字段校验
        this.validateParams(params.getWayBillNo(), "运单号");
        this.validateParams(params.getWayBillSource(), "数据来源");
        this.validateParams(params.getCreateBillTime(), "开单时间");
        this.validateParams(params.getWayBillAmount(), "运单总金额");
        this.validateParams(params.getWaitSwipeAmount(), "待刷卡金额");
    }

    /**
     * 验证判空通用方法
     */
    private <T> void validateParams(T t, String str) {
        this.validateParams(t, str, true);
    }

    private <T> void validateParams(T t, String str, boolean b) {
        if (b) {
            str = str == null ? "传入参数有误" : str + "传入参数有误";
        } else {
            str = str == null ? "" : str;
        }
        if (null == t) {
            throw new SettlementException(str);
        }
        if (t instanceof List && ((List) t).isEmpty()) {
            throw new SettlementException(str);
        }
        if (t instanceof String && ((String) t).trim().length() == 0) {
            throw new SettlementException(str);
        }
    }
    /**
     * POS机刷卡长期未使用自动冻结
     * @author 231438
     * @date:2016年12月9日 下午4:27:21
     */
    @Override
    public void posCardAutoFrozen(){
    	 LOGGER.info("WSCManageService.posCardAutoFrozen开始：----");
    	 //查询参数实体
    	 PosCardEntity params = new PosCardEntity();
    	 //有效的
    	 params.setActive("Y");
    	 //冻结状态 --用来过滤全部冻结的
    	 params.setFrozenStatus(SettlementConstants.POS_CARD_FROZEN_STATUS_1);
    	 
    	 List<PosCardEntity> posCardList = wscManageDao.queryPosCardToFrozen(params);
    	 if(CollectionUtils.isEmpty(posCardList)){
    		 LOGGER.info("查询超过30天未使用的刷卡数据结果集为空，返回");
    		 LOGGER.info("WSCManageService.posCardAutoFrozen结束：----");
    		 return;
    	 }
    	 LOGGER.info("查询到刷卡时间大于30天，仍有未占用金额的结果有："+posCardList.size()+"个！");
    	 LOGGER.info("批量冻结刷卡时间大于30天未占用金额>0的POS刷卡数据开始：待冻结数据："+posCardList.size());
    	 //批量更新长期未使用的POS刷卡数据(数据库foreach里update)
    	 wscManageDao.batchUpdatePosCardToFrozen(posCardList);
    	 LOGGER.info("WSCManageService.posCardAutoFrozen结束：----");
    }
    public void setWscManageDao(IWSCManageDao wscManageDao) {
        this.wscManageDao = wscManageDao;
    }

    public void setBusinessLockService(IBusinessLockService businessLockService) {
        this.businessLockService = businessLockService;
    }

    public void setOrgAdministrativeInfoComplexService(IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
        this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
    }
}
