package com.deppon.foss.module.settlement.common.server.dao.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.common.api.server.dao.ICreditCustomerEntityDao;
import com.deppon.foss.module.settlement.common.api.shared.domain.CreditCustomerEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.CreditCustomerDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementExceptionType;

/**
 * 
 * 客户收支平衡表
 * 
 * @author dp-huangxb
 * @date 2012-10-19 下午3:56:42
 */
public class CreditCustomerEntityDao extends iBatis3DaoImpl implements ICreditCustomerEntityDao {

	/**
	 * 命名空间路径
	 */
	private static final String NAMESPACE = "foss.stl.CreditCustomerEntityDao.";

	/**
	 * 
	 * 新加客户收支平衡表
	 * 
	 * @author dp-huangxb
	 * @date 2012-10-19 下午3:32:00
	 * @param entity
	 *            客户收支平衡
	 * @return
	 * @see com.deppon.foss.module.settlement.common.api.server.dao.ICreditCustomerEntityDao#addCreditCustomer(com.deppon.foss.module.settlement.common.api.shared.domain.CreditCustomerEntity)
	 */
	@Override
	public int addCreditCustomer(CreditCustomerEntity entity) {
		return this.getSqlSession().insert(NAMESPACE + "addCreditCustomer",entity);
	}

	/** 
	 * 更新客户可用额度
	 * 
	 * @author dp-huangxb
	 * @date 2012-10-19 下午3:32:00
	 * @param entity
	 *            客户收支平衡
	 * @return
	 */
	@Override
	public int updateCreditCustomer(CreditCustomerEntity entity) {
		int result = 0;
		// 判断对象是否为空
		if (entity != null) {
			result = this.getSqlSession().update(NAMESPACE + "updateCreditCustomer", entity);
		} else {
			throw new SettlementException(SettlementExceptionType.PARAMS_OBJECT_ISNULL_ERROR);
		}
		return result;
	}

	/**
	 * 
	 * 按照客户编码更新客户已用额度信息
	 * 
	 * @author dp-huangxb
	 * @date 2012-10-20 下午4:53:49
	 * @param customerCode
	 *            客户编码
	 * @param amount
	 *            金额
	 * @return
	 */
	@Override
	public int updateUsedAmount(String customerCode, BigDecimal usedAmount) {
		// 判断对象是否为空
		if (StringUtils.isNotEmpty(customerCode) && usedAmount != null) {
			CreditCustomerEntity entity = new CreditCustomerEntity();
			entity.setCustomerCode(customerCode);
			entity.setUsedAmount(usedAmount);
			// 获取当前日期
			Date sysDate = new Date();
			//修改时间
			entity.setModifyTime(sysDate);
			//业务时间
			entity.setBusinessDate(sysDate);
			return  this.getSqlSession().update(NAMESPACE + "updateUsedAmount", entity);
		} else {
			throw new SettlementException(SettlementExceptionType.PARAMS_OBJECT_ISNULL_ERROR);
		}
	}

	/**
	 * 
	 * 按照客户编码更新客户超期欠款标记
	 * 
	 * @author dp-huangxb
	 * @date 2012-10-20 下午4:53:49
	 * @param customerCode
	 *            客户编码
	 * @param status
	 *            状态
	 * @param notes
	 *            备注
	 * @return
	 */
	@Override
	public int updateOverdueStatus(String customerCode, String isOverdue,String notes) {
		// 判断对象是否为空，客户编码不能为空
		CreditCustomerEntity entity = new CreditCustomerEntity();
		entity.setCustomerCode(customerCode);
		entity.setNotes(notes);
		return this.getSqlSession().update(NAMESPACE + "updateOverdueStatus", entity);
	}

	/**
	 * 
	 * 查询客户收支平衡表
	 * 
	 * @author dp-huangxb
	 * @date 2012-10-19 下午3:32:29
	 * @param customerNumber
	 *            客户编码
	 * @return
	 */
	@Override
	public CreditCustomerEntity queryByCustomerCode(String customerCode) {
		return (CreditCustomerEntity) this.getSqlSession().selectOne(NAMESPACE + "queryByCustomerCode", customerCode);
	}

	/**
	 * 
	 * 安客户编码查询客户的可用额度信息，是否存在超期欠款
	 * 
	 * @author dp-huangxb
	 * @date 2012-10-19 下午4:36:42
	 * @param customerNumber
	 *            客户编码
	 * @return
	 */
	@Override
	public CreditCustomerDto queryDebitByCustomerCode(String customerCode) {
		return (CreditCustomerDto) this.getSqlSession().selectOne(NAMESPACE + "queryDebitByCustomerCode", customerCode);
	}

	/**
	 * 获得客户总行数
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-23 下午4:45:16
	 * @return
	 * @see com.deppon.foss.module.settlement.common.api.server.dao.ICreditCustomerEntityDao#queryTotalRows()
	 */
	@Override
	public int queryTotalRows() {
		return (Integer) this.getSqlSession().selectOne(
				NAMESPACE + "queryTotalRows");
	}

	/**
	 * 通过分页获得相应的客户编码
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-23 下午4:44:07
	 * @param offset
	 *            偏移量
	 * @param limit
	 *            限制最大行
	 * @return
	 * @see com.deppon.foss.module.settlement.common.api.server.dao.ICreditCustomerEntityDao#getCustomerCodes(org.apache.ibatis.session.RowBounds)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CreditCustomerDto> queryCreditCustomerByPage(int offset, int limit) {
		RowBounds rowBounds = new RowBounds(offset, limit);
		return (List<CreditCustomerDto>) this.getSqlSession().selectList(
				NAMESPACE + "queryCreditCustomerByPage", null, rowBounds);
	}

	/**
	 * 查看客户是否在使用，以便CRM作废
	 * 
	 * @param customerCodes
	 *            编码编码
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> isCustomerInUseRCV(String active, Date acctDate,
			List<String> customerCodes){

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("active", active);
		params.put("acctDate", acctDate);
		params.put("customerCodes", customerCodes);

		return getSqlSession().selectList(NAMESPACE + "queryCustomerInUseRCV",
				params);
	}

	/**
	 * 查看客户是否在使用，以便CRM作废
	 * 
	 * @param customerCodes
	 *            编码编码
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> isCustomerInUsePAY(String active, Date acctDate,List<String> customerCodes){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("active", active);
		params.put("acctDate", acctDate);
		params.put("customerCodes", customerCodes);
		return getSqlSession().selectList(NAMESPACE + "queryCustomerInUsePAY",params);
	}

	/**
	 * 查看客户是否在使用，以便CRM作废
	 * 
	 * @param customerCodes
	 *            编码编码
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> isCustomerInUseDR(String active, Date acctDate,List<String> customerCodes){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("active", active);
		params.put("acctDate", acctDate);
		params.put("customerCodes", customerCodes);
		return getSqlSession().selectList(NAMESPACE + "queryCustomerInUseDR",params);
	}

    @SuppressWarnings("unchecked")
	@Override
    public List<CreditCustomerDto> queryCustomerDebitInfo(String customerCode) {
        return (List<CreditCustomerDto>) this.getSqlSession().selectList(NAMESPACE + "queryCustomerDebitInfo", customerCode);
    }
    
    @SuppressWarnings("unchecked")
   	@Override
       public List<CreditCustomerDto> queryCustomerDebit(String customerCode,String productId) {
    	Map<String, Object> params = new HashMap<String, Object>();
    	params.put("customerCode", customerCode);
    	params.put("productId", productId);
           return (List<CreditCustomerDto>) this.getSqlSession().selectList(
                   NAMESPACE + "queryCustomerDebit", params);
       }
}
