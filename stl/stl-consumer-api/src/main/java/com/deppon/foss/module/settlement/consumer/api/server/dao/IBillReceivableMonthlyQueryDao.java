package com.deppon.foss.module.settlement.consumer.api.server.dao;

import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.BillReceivableDto;

import java.util.List;

/**
 * 查询应收单月结实体
 * @author foss-youkun
 * @date 2016/1/8
 */
public interface IBillReceivableMonthlyQueryDao {
    /**
     * 按日期查询月结应收单
     *@author foss-youkun
     * @date 2016/1/8
     */
    List<BillReceivableEntity> queryBillReceivableByData(int offset,int start,BillReceivableDto dto);
    /**
     * 
     * <p>查询所有的月结应收单</p> 
     * @author 273272 唐俊
     * @date 2016-2-17 上午10:21:18
     * @param dto
     * @return
     * @see
     */
    List<BillReceivableEntity> queryBillReceivableByData(BillReceivableDto dto);

    /**
     * 按日期查询月结应收单的总记录数
     * @author foss-youkun
     * @date 2016/1/8
     * @return int
     */
    Long countBillReceivableByData(BillReceivableDto dto);

    /**
     * 查询总金额
     * @param dto
     * @return
     */
    String amountBillReceivableByParam(BillReceivableDto dto);
	/**
	 * <p>插入合伙人月结应收账单数据</p> 
	 * @author 273272 唐俊
	 * @date 2016-2-17 上午10:54:32
	 * @param list
	 * @see
	 */
	void insertBillReceivable(List<BillReceivableEntity> list);

    /**
     * 查询月结为始发提成,委托派费的应收单
     * @param dto
     * @return
     */
    List<BillReceivableEntity> queryBillRecivableByList(BillReceivableDto dto,int start,int limit);


    /**
     * 查询月结为始发提成,委托派费的应收单(不分页的)
     * @param dto
     * @return
     */
    List<BillReceivableEntity> queryNotPageBillRecivableByList(BillReceivableDto dto);

    /**
     * 查询月结为始发提成,委托派费的应收单总记录数
     * @param dto
     * @return
     */
    Long countBillRecivableByList(BillReceivableDto dto);
    
    /**
     * 根据运单号查询出所有的月结应收单记录
     * @param dto
     * @return
     */
    List<BillReceivableEntity> queryBillReceivableEntityListForExport(BillReceivableDto dto);
    
}
