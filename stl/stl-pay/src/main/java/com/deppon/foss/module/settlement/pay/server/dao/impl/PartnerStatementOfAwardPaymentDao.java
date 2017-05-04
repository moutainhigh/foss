package com.deppon.foss.module.settlement.pay.server.dao.impl;

import java.util.HashMap;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.pay.api.server.dao.IPartnerStatementOfAwardPaymentDao;

/** 
 * 合伙人奖罚对账单管理付款DAO
 * @author foss结算-306579-guoxinru 
 * @date 2016-2-25 下午6:37:30    
 */
public class PartnerStatementOfAwardPaymentDao extends iBatis3DaoImpl implements IPartnerStatementOfAwardPaymentDao {
	
	public static final String NAMESPACES = "foss.stl.PartnerStatementOfAwardPaymentDao.";
	
	/** 
	 * 新增合伙人奖罚付款明细
	 * @author foss结算-306579-guoxinru 
	 * @date 2016-2-26  
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void addBillPaymentD(String paymentNum, String statementBillNo) {
		Map map = new HashMap();
		map.put("paymentNum", paymentNum);
		map.put("statementBillNo", statementBillNo);
		this.getSqlSession().insert(NAMESPACES + "addBillPaymentD", map);
		
	}

	/** 
	 * 更新付款单状态、付款金额、版本号
	 * @author foss结算-306579-guoxinru 
	 * @date 2016-2-26  
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void payForBillPayable(Map map) {
	 this.getSqlSession().update(NAMESPACES + "payForBillPayable", map);		
	}

	
	
}
