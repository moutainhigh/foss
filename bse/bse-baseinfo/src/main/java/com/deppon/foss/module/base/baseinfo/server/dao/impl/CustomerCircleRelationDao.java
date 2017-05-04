package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.deppon.foss.module.base.baseinfo.api.server.dao.ICustomerCircleRelationDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerCircleEntity;
/**
 * 
 * 客户圈Dao实现
 * @author 308861 
 * @date 2017-1-3 上午10:34:08
 * @since
 * @version
 */
public class CustomerCircleRelationDao extends SqlSessionDaoSupport implements ICustomerCircleRelationDao{
	/**
     * mybatis 命名空间
     */
    private static final String NAMESPACE = "foss.bse.bse-baseinfo.customerCircleRelation.";

    /**
     * 
     * 根据 客户编码 精准查询开单时间 有效的客户圈的信息   
     * @author 308861 
     * @date 2017-2-7 下午5:17:08
     * @param custCode
     * @param date
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ICustomerCircleRelationDao#getByCustCode(java.lang.String, java.util.Date)
     */
	@Override
	public CustomerCircleEntity getByCustCode(String custCode,Date date) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("validDate", date);
		map.put("custCode", custCode);
		return (CustomerCircleEntity)getSqlSession().selectOne(NAMESPACE+"getByCustCode",map);
	}

	/**
	 * 
	 * 根据从客户圈编码查询当前时间主客户的有效客户圈信息  
	 * @author 308861 
	 * @date 2017-2-21 下午6:01:16
	 * @param custCircleCode
	 * @param date
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ICustomerCircleRelationDao#selectMainCust(java.lang.String, java.util.Date)
	 */
	@Override
	public CustomerCircleEntity selectMainCust(String custCircleCode,Date date) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("validDate", date);
		map.put("custCircleCode", custCircleCode);
		return (CustomerCircleEntity)getSqlSession().selectOne(NAMESPACE+"selectMainCust",map);
	}
}
