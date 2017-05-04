package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ICusContractTaxDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusContractTaxEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerQueryConditionDto;

public class CusContractTaxDao extends SqlSessionDaoSupport implements ICusContractTaxDao{
	
	private static final String NAMESPACE = "foss.bse.bse-baseinfo.cusContractTax.";
	
	/**
	 * 新增客户发票标记信息
	 * 
	 * @author 132599-foss-shenweihua
	 * @date 2013-11-20 上午11:15:51
	 * @param entity
	 *            客户发票标记信息实体
	 * @return 1：成功；-1：失败
	 * @see
	 */
	@Override
	public int addCusContractTax(CusContractTaxEntity entity) {
		
		return this.getSqlSession().insert(NAMESPACE + "insert", entity);
	}
	
	/**
	 * 修改客户发票标记信息
	 * 
	 * @author 132599-foss-shenweihua
	 * @date 2013-11-20 上午11:17:51
	 * @param entity
	 *            客户发票标记信息实体
	 * @return 1：成功；-1：失败
	 * @see
	 */
	@Override
	public int updateCusContractTax(CusContractTaxEntity entity) {
		
		return this.getSqlSession().update(NAMESPACE + "update", entity);
	}
	
	/**
	 * <p>
	 * 根据crmId,最后一次修改时间查询客户发票标记信息是否存在
	 * </p>
	 * 
	 * @author 132599-foss-shenweihua
	 * @date 2013-11-20 上午11:18:51
	 * @param crmId
	 * @param lastupdatetime
	 * @return
	 * @see
	 */
	@Override
	public boolean queryCusContractTaxByCrmId(BigDecimal crmId,
			Date lastupdatetime) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("modifyDate", lastupdatetime);
		map.put("crmId", crmId);
		
		@SuppressWarnings("unchecked")
		List<CusContractTaxEntity> list = this.getSqlSession().selectList(NAMESPACE + "queryCusContractTaxByCrmId", map);
		return CollectionUtils.isNotEmpty(list);
	}

	/**
	 * <p>
	 * 根据客户编码,客户联系方式查询客户当前可以使用的发票标记信息
	 * </p>
	 * 
	 * @author 132599-foss-shenweihua
	 * @date 2013-11-22 下午午1:27:29
	 * @param condition
	 * @return
	 * @see
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CusContractTaxEntity> queryCurrentUseInvoiceMark(
			CustomerQueryConditionDto condition, Date date) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("condition", condition);
		map.put("date", date);
		return this.getSqlSession().selectList(NAMESPACE + "queryCurrentUseInvoiceMark", map);
	}

}
