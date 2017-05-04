package com.deppon.foss.module.settlement.common.api.server.service;

import com.deppon.foss.module.settlement.common.api.shared.domain.PosCardDetailEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.PosCardEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.WSCEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by yaq on 2016/5/31.
 */
public interface IWSCManageService {
    /**
     * 快递补录时，生成已经支付的待刷卡数据
     * @param params
     */
    public void payWscEntityForESC(WSCEntity params);

    public void addPosCardAndDetail(PosCardEntity params);
    /**
     * 只更新运单号
     * @param params
     */
    public void changeWayBillNo(WSCEntity params);
    /**
     * 入口
     * @param params 最新的待刷卡数据  包含所有场景
     *               新增，金额增加，金额减少，作废，中止
     */
    public void changeWayBill(WSCEntity params);

    /**
     *根据交易流水号，运单号释放指定金额
     * @param amount       释放金额
     * @param batchNo      交易流水号
     * @param waybillNo    运单号
     * @param invoiceType  交易明细单据类型  W1 待刷卡 W2 结清货款
     */
    void reversPosCardPosCard(BigDecimal amount,String batchNo,String waybillNo,String invoiceType);

    /**
     * 新增待刷卡单据
     * @param params
     * @return
     */
    public int insertWSCWayBill(WSCEntity params);

    /**
     * 更新待刷卡单据 根据ID
     * @param params
     * @return
     */
    public int updateWSCWayBillByID(Map params);

    /**
     * 更新待刷卡单据 根据WayBillNo
     * @param params  map   key   newEntity    ==>   更新的实体
     *                      key   WayBillNo    ==>   数值
     * @return
     */
    public int updateWSCWayBillByWayBillNo(Map params);

    /**
     * 查询待刷卡单据  条件自定义
     * @param params  params 中fields 参数 即为所要查询的字段
     *                其余参数为条件
     * @return
     */
    public List<WSCEntity> queryWSCWayBill(WSCEntity params);

    /**
     * 查询T+0明细
     * @param params   params 中fields 参数 即为所要查询的字段
     *                其余参数为条件
     * @return
     */
    public int insertPosCardDetail(PosCardDetailEntity params);
    public List<PosCardDetailEntity> queryPosCardDetail(PosCardDetailEntity params);
    public int updatePosCardDetailByID(Map params);
    public int updatePosCardDetailByWayBillNo(Map params);
    /**
     * 查询T+0明细
     * @param params   params 中fields 参数 即为所要查询的字段
     *                其余参数为条件
     * @return
     */
    public int insertPosCard(PosCardEntity params);
    public List<PosCardEntity> queryPosCard(PosCardEntity params);
    public int updatePosCardByID(Map params);
    /**
     * POS机刷卡长期未使用自动冻结
     * @author 231438
     * @date:2016年12月9日 下午4:27:21
     */
    public void posCardAutoFrozen();
}
