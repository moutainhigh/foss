package com.deppon.foss.module.settlement.dubbo.api.dao;

import java.util.List;
import com.deppon.foss.module.settlement.dubbo.api.define.BillDepositReceivedEntity;


/**
 * 预收单公共DAO接口类
 * 
 * @author 088933-foss-zhangjiheng
 * @date 2012-10-18 上午10:57:44
 */
public interface IBillDepositReceivedEntityDao4dubbo {

    /**
     * 新增合伙人预收单
     * @author foss-guoxinru
     * @date 2016-1-12
     * @param entity 预收单
     * @return
     */
    int addPartner(BillDepositReceivedEntity entity);
    
    /**
     * 根据汇款编号查询合伙人预收单
     * @param remitNo 汇款编号
     * @param active 是否有效
     * @return List<BillDepositReceivedEntity>
     */
	List<BillDepositReceivedEntity> queryByRemitNo(String remitNo, String active);
}
