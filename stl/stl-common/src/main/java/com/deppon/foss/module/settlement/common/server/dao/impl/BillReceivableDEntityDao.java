package com.deppon.foss.module.settlement.common.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableDEntity;
import com.deppon.foss.module.settlement.common.api.server.dao.IBillReceivableDEntityDao;


/**
 * 应收单明细 dao
 * @author 黄乐为
 * @date 2016-1-9 下午3:38:23
 */
public class BillReceivableDEntityDao extends iBatis3DaoImpl implements IBillReceivableDEntityDao {

	/**
	 * 命名空间
	 */
	private static final String NAMESPACES="foss.stl.BillReceivableDEntityDao.";
	
	/**
	 * 添加一条应收单明细记录
	 * @author 黄乐为
	 * @date 2016-1-9 下午3:54:33
	 * @param entity
	 * @return
	 */
	@Override
	public int add(BillReceivableDEntity entity) {
		return this.getSqlSession().insert(NAMESPACES + "insert", entity);
	}

	/**
	 * 根据传入的一到多个应收单号,查询一到多条应收单明细信息
	 * @author 黄乐为
	 * @date 2016-1-9 下午3:54:28
	 * @param receivableNos
	 * @param active
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillReceivableDEntity> queryByReceivableNOs(List<String> receivableNos, String active) {
		if (CollectionUtils.isEmpty(receivableNos)) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("receivableNos", receivableNos);
		if (StringUtils.isNotEmpty(active)) {
			map.put("active", active);
		}
		return this.getSqlSession().selectList(NAMESPACES + "selectByReceivableNos", map);
	}

	/**
	 * 根据传入的一到多个来源单号，获取一到多条应收单明细信息
	 * @author 黄乐为
	 * @date 2016-1-9 下午4:01:13
	 * @param sourceBillNos
	 * @param active
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillReceivableDEntity> queryBySourceBillNOs(List<String> sourceBillNos, String active) {
		if (CollectionUtils.isEmpty(sourceBillNos)){
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sourceBillNos", sourceBillNos);
		if (StringUtils.isEmpty(active)) {
			map.put("active", active);
		}
		return this.getSqlSession().selectList(NAMESPACES + "selectBySourceBillNos", map);
	}

	/**
	 * 根据应收单号查询应收单明细
	 * @author 尤坤
	 * @param receivableNo 应收单号
	 * @return
	 */
	@Override
	public List<BillReceivableDEntity> queryByReceivableNo(String receivableNo) {
		return this.getSqlSession().selectList(NAMESPACES + "selectByReceivableNo", receivableNo);
	}

}
