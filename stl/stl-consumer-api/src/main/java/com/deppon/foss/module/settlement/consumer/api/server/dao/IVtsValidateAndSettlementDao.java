package com.deppon.foss.module.settlement.consumer.api.server.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.deppon.foss.module.settlement.consumer.api.shared.dto.SettlementPayToVtsDto;


/** 
 * foss对接vts财务校验及结清货款
 * @author foss结算-306579-guoxinru 
 * @date 2016-5-9 上午9:36:31    
 */
public interface IVtsValidateAndSettlementDao{

	/**
	 * 查询付款记录
	 */
	List<SettlementPayToVtsDto> searchRepaymentList(SettlementPayToVtsDto queryParam);

	/**
	 * 新增付款信息
	 */
	String addPaymentInfo(SettlementPayToVtsDto dto);

	/**
	 * 根据运单号查询运单到付金额
	 * @param map
	 * @return
	 */
	BigDecimal queryToPayAmount(Map<String, String> map);

	/**
	 * 354830 孙小雪
	 * 根据运单号查询实际承运表结清状态
	 * @param waybillNo
	 * @return
	 */
	String querySettlementStatus(Map<String, String> map);



}
