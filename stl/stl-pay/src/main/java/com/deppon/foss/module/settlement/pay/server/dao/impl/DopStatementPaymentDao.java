package com.deppon.foss.module.settlement.pay.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPaymentEntity;
import com.deppon.foss.module.settlement.pay.api.server.dao.IDopStatementPaymentDao;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillPaymentAddDto;

/**
 * 家装DAO
 * 
 * @ClassName: DopStatementPaymentDao
 * @author &269052 |zhouyuan008@deppon.com
 * @date 2015-12-11 下午1:58:22
 */

public class DopStatementPaymentDao extends iBatis3DaoImpl implements
		IDopStatementPaymentDao {
	public static final String NAMESPACES = "foss.stl.DopStatementPaymentDao.";

	/**
	 * 保存付款单明细
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addBillPaymentD(String paymentNum, String statementBillNo) {
		Map map = new HashMap();
		map.put("paymentNum", paymentNum);
		map.put("statementBillNo", statementBillNo);
		this.getSqlSession().insert(NAMESPACES + "addBillPaymentD", map);
	}
	
	
	
	/**
	 * 跟新应付单
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void payForBillPayable(Map map) {
		this.getSqlSession().update(NAMESPACES + "payForBillPayable", map);
	}
	
	/**
	 * 根据应付单号去查询部门和该部门的总金额
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillPaymentEntity> queryOrgAndAmountbyNo(
			List<BillPaymentAddDto> list) {
		return this.getSqlSession().selectList(NAMESPACES+"queryOrgAndAmountbyNo",list);
	}



	/**
	 * 保存明细
	 * @date 2016.3.4
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addBillPaymentDetails(String paymentNum, List<String> list) {
		Map map = new HashMap();
		map.put("paymentNum", paymentNum);
		map.put("payableNos", list);
		this.getSqlSession().insert(NAMESPACES + "addBillPaymentDetails", map);
	}


	/**
	 * 根据部门编号获取应付单号集合
	 *  @Title: queryOrgAndAmountbyNo
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<BillPaymentEntity> queryPayableByOrgCode(String orgCode,String statementBillNo) {
		Map map = new HashMap();
		map.put("orgCode", orgCode);
		map.put("statementBillNo", statementBillNo);
		return this.getSqlSession().selectList(NAMESPACES + "queryPayableByOrgCode", map);
	}
}
