package com.deppon.foss.module.settlement.consumer.server.dao.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.consumer.api.server.dao.IVtsValidateAndSettlementDao;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.SettlementPayToVtsDto;
import com.deppon.foss.util.UUIDUtils;

/** 
 * foss对接vts财务校验及结清货款
 * @author foss结算-306579-guoxinru 
 * @date 2016-5-9 上午9:38:08    
 */
public class VtsValidateAndSettlementDao extends iBatis3DaoImpl implements
		IVtsValidateAndSettlementDao {
	
	/**
	 * 命名空间
	 */
	private static final String NAMESPACE = "foss.stl.VtsValidateAndSettlementDao.";

	/**
	 * 查询付款记录
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SettlementPayToVtsDto> searchRepaymentList(SettlementPayToVtsDto dto) {
		return getSqlSession().selectList(NAMESPACE + "searchRepaymentList", dto);
	}
	
	/**
	 * 新增付款信息
	 */
	@Override
	public String addPaymentInfo(SettlementPayToVtsDto dto) {
		String uuid = UUIDUtils.getUUID();
		dto.setId(uuid) ;
		getSqlSession().insert(NAMESPACE + "addPaymentInfo", dto);
		return uuid;
		
	}
	
	/**
	 * 根据运单号查询运单到付金额
	 * @param map
	 * @return
	 */
	@Override
	public BigDecimal queryToPayAmount(Map<String, String> map) {
		return (BigDecimal) this.getSqlSession().selectOne(NAMESPACE + "queryToPayAmount", map);
	}

	/**
	 * 354830 孙小雪
	 * 根据运单号查询实际承运表结清状态
	 * @param waybillNo
	 * @return
	 */
	@Override
	public String querySettlementStatus(Map<String, String> map) {
		return (String) this.getSqlSession().selectOne(NAMESPACE + "querySettlementStatus", map);
	}
}
