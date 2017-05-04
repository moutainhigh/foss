package com.deppon.foss.module.settlement.agency.server.dao.impl;

import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.agency.api.server.dao.IPackingRecAndPayInputDao;

/**
 * 包装其他应收应付输入界面
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:105762,date:2014-6-5 上午11:07:04,content:TODO </p>
 * @author 105762
 * @date 2014-6-5 上午11:07:04
 * @since 1.6
 * @version 1.0
 */
public class PackingRecAndPayInputDao extends iBatis3DaoImpl implements IPackingRecAndPayInputDao{

	private static final String NAMESPACE = "foss.stl.PackingRecAndPayInputDao.";// 命名空间路径

	/**
	 * <p>插入包装其他应收应付年月记录</p> 
	 * @author 105762
	 * @date 2014-6-5 上午11:16:16
	 * @param map
	 * @return 成功条数
	 */
	public int addPackingBillTime(Map<String,String> map) {
		return this.getSqlSession().insert(NAMESPACE + "addPackingBillTime", map);
	}
}
