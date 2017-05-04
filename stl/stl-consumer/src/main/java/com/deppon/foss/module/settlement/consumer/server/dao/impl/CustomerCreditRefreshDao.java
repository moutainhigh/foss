package com.deppon.foss.module.settlement.consumer.server.dao.impl;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.consumer.api.server.dao.ICustomerCreditRefreshDao;

/**
 * 定时刷新月结额度为零的客户的财务标记
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:105762,date:2014-3-11 下午2:50:42,content:new </p>
 * @author 105762
 * @date 2014-3-11 下午2:50:42
 * @since
 * @version
 */
public class CustomerCreditRefreshDao extends iBatis3DaoImpl implements ICustomerCreditRefreshDao{
	
	public static final String NAMESPACE="foss.stl.CustomerCreditRefreshDao.";
	
	/**
	 * <p>更新月结额度为零客户的财务时间标记</p> 
	 * @author 105762
	 * @return 
	 * @date 2014-3-11 下午2:54:20
	 * @see
	 */
	@Override
	public int updateZeroCreditFinanceMark(){
		return this.getSqlSession().update(NAMESPACE+"updateZeroCreditFinanceMark");
	}
}
