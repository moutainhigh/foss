package com.deppon.foss.module.settlement.consumer.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.BillReceivableDto;

import java.util.List;

/**
 * 应收单月结查询服务
 * @author foss-youkun
 * @date 2016/1/8
 */
public interface IBillReceivableMonthlyQueryService extends IService{

    /**
     * 按日期查询月结应收单
     *@author foss-youkun
     * @date 2016/1/8
     */
    List<BillReceivableEntity> queryBillReceivableByData(int offset,int start,BillReceivableDto dto) throws SettlementException;
    /**
     * 
     * <p>查询所有的月结应收单</p> 
     * @author 273272 唐俊
     * @date 2016-2-17 上午10:18:36
     * @param dto
     * @return
     * @throws SettlementException
     * @see
     */
    List<BillReceivableEntity> queryBillReceivableByData(BillReceivableDto dto) throws SettlementException;

    /**
     * 按日期查询月结应收单的总记录数
     * @author foss-youkun
     * @date 2016/1/8
     * @param dto
     * @return
     */
    Long countBillReceivableByData(BillReceivableDto dto) throws SettlementException;

    /**
     * 查询总金额
     * @param dto
     * @return
     */
    String amountBillReceivableByParam(BillReceivableDto dto) throws SettlementException;

    /**
     * 查询月结为始发提成,委托派费的应收单
     * @param dto
     * @return
     */
    List<BillReceivableEntity> queryBillRecivableByList(BillReceivableDto dto,int start,int limit) throws SettlementException;


    /**
     * 查询月结为始发提成,委托派费的应收单(不分页的)
     * @param dto
     * @return
     */
    List<BillReceivableEntity> queryNotPageBillRecivableByList(BillReceivableDto dto) throws SettlementException;

    /**
     * 查询月结为始发提成,委托派费的应收单总记录数
     * @param dto
     * @return
     */
    Long countBillRecivableByList(BillReceivableDto dto)throws SettlementException;
    /**
	 * <p>插入合伙人月结应收账单数据</p> 
	 * @author 273272 唐俊
	 * @date 2016-2-17 上午10:44:46
	 * @param tempList
	 * @see
	 */
	void insertBillReceivable(List<BillReceivableEntity> tempList);
	/**
	 * 判断用户是否能查询合伙人营业部月结金额</p> 
	 * @author 273272 唐俊
	 * @date 2016-2-25 上午9:32:17
	 * @param dto
	 * @see
	 */
	void judgeDepartmentPermission(BillReceivableDto dto);
	/**
	 * 根据运单号查询出所有的月结应收单记录
	 * @return
	 * @throws SettlementException
	 */
	List<BillReceivableEntity> queryBillReceivableEntityListForExport(BillReceivableDto dto) throws SettlementException;
}
