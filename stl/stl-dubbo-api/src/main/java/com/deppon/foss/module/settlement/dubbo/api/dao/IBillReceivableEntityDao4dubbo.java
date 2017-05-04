package com.deppon.foss.module.settlement.dubbo.api.dao;

import java.util.List;

import com.deppon.foss.module.settlement.dubbo.api.define.BillReceivableEntity;

/**
 * 应收单DAO
 * @author ibm-zhuwei
 * @date 2012-10-19 上午11:03:46
 */
public interface IBillReceivableEntityDao4dubbo {
    /**
     * 根据id与应收单号修改应收单扣款状态:扣款失败,扣款成功
     *@author 099995-foss-hemingyu
     * @date 2016-01-08 上午11:23:05
     * @param entity 应收单
     * @return
     * @see
     */
    int updateBillReceivableWithholdStatus(BillReceivableEntity entity);

    /*
    *@author 099995-foss-hemingyu
     * @date 2016-01-14 上午15:47:38
     * @param wayBillNo
     *            运单号
     * @param billType
     *            应收类型
     */
    List<BillReceivableEntity> selectByWayBillNoAndBillType(String wayBillNo,String billType);

}
