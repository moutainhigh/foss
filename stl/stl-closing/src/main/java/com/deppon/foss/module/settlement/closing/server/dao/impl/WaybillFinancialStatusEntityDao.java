package com.deppon.foss.module.settlement.closing.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.closing.api.server.dao.IWaybillFinancialStatusEntityDao;
import com.deppon.foss.module.settlement.closing.api.shared.domain.WaybillFinancialStatusEntity;

/**
 * 财务完结-运单财务状态实体DAO.
 *
 * @author 099995-foss-wujiangtao
 * @date 2012-12-3 上午11:39:26
 * @since
 * @version
 */
public class WaybillFinancialStatusEntityDao extends iBatis3DaoImpl implements IWaybillFinancialStatusEntityDao {
   
	/** The Constant NAMESPACES. */
	private static final String NAMESPACES = "foss.stl.WaybillFinancialStatusEntityDao.";// 命名空间路径
	
	/**
	 * 批量添加财务完结状态.
	 *
	 * @param list the list
	 * @return the int
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-12-3 上午11:14:43
	 */
	@Override
	public void addByBatch(List<WaybillFinancialStatusEntity> list) {
		
		for (WaybillFinancialStatusEntity entity : list) {
			this.getSqlSession().insert(NAMESPACES + "insertByBatch", entity);
		}
		
	}

	/**
	 * 批量更新财务完结状态.
	 *
	 * @param list the list
	 * @return the int
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-12-3 上午11:15:41
	 */
	@Override
	public void updateStatusByBatch(List<WaybillFinancialStatusEntity> list) {
		
		for (WaybillFinancialStatusEntity entity : list) {
			this.getSqlSession().update(NAMESPACES + "updateStatusByBatch", entity);
		}
	}

	/**
	 * 批量删除财务完结状态.
	 *
	 * @param list the list
	 * @return the int
	 * @author ibm-zhuwei
	 * @date 2012-12-5 下午2:00:58
	 */
	@Override
	public int deleteByBatch(List<WaybillFinancialStatusEntity> list) {

		return this.getSqlSession().delete(NAMESPACES + "deleteByBatch", list);
	}

	/**
	 * 通过运单查询财务完结状态.
	 *
	 * @param waybillNos the waybill nos
	 * @return the list
	 * @author ibm-zhuwei
	 * @date 2012-12-5 下午2:36:29
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<WaybillFinancialStatusEntity> queryByWaybillNos(
			List<String> waybillNos) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("waybillNos", waybillNos);
		
		return this.getSqlSession().selectList(NAMESPACES + "selectByWaybillNos", map);
	}

}
