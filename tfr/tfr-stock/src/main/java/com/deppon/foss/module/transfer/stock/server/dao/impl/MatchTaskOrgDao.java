package com.deppon.foss.module.transfer.stock.server.dao.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.stock.api.server.dao.IMatchTaskOrgDao;

/**
 * 根据运单 匹配任务部门   crm接口
 * @author 200978  xiaobingcheng
 * 2014-10-11
 */
public class MatchTaskOrgDao extends iBatis3DaoImpl implements IMatchTaskOrgDao {

	private static final String NAMESPACE = "tfr-stock.";
	
	/**
	 * 根据库存部门编码set查询，走货路径中最前面的哪个部门编码
	 * @Author: 200978  xiaobingcheng
	 * 2014-10-22
	 * @param stockSet
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String queryFirstOrgByStockOrgSet(Set<String> stockSet,String waybillNo){
		Map map = new HashMap();
		map.put("waybillNo", waybillNo);
		map.put("stockSet", stockSet);
		return (String) this.getSqlSession().selectOne(NAMESPACE+"queryFirstOrgByStockOrgSet",map);
	}
	
	/**
	 * 根据运单号 查询该运单是否已经全部签收
	 * @Author: 200978  xiaobingcheng
	 * 2014-10-22
	 * @param waybillNo
	 * @return
	 */
	public boolean checkWaybillIsSigned(String waybillNo){
		return (Boolean) this.getSqlSession().selectOne(NAMESPACE+"checkWaybillIsSigned",waybillNo);
	}
	
	
	
}
