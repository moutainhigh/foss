package com.deppon.foss.module.settlement.common.server.dao.impl;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.common.api.server.dao.IBillPayableDEntityDao;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableDEntity;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 应付单明细 dao
 * @author hemingyu
 * @date 2016-01-21 16:07:21
 */
public class BillPayableDEntityDao extends iBatis3DaoImpl implements IBillPayableDEntityDao {

	private static final String NAMESPACES="foss.stl.BillPayableDEntityDao.";
	
	/**
	 * 添加一条应付单明细记录
     * @author hemingyu
     * @date 2016-01-21 16:07:21
	 * @param entity
	 * @return
	 */
	@Override
	public int add(BillPayableDEntity entity) {
		return this.getSqlSession().insert(NAMESPACES + "insert", entity);
	}

	/**
	 * 根据传入的一到多个应付单号,查询一到多条应付单明细信息
     * @author hemingyu
     * @date 2016-01-21 16:07:21
	 * @param payableNos
	 * @param active
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillPayableDEntity> queryByPayableNOs(List<String> payableNos, String active) {
		if (CollectionUtils.isEmpty(payableNos)) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("payableNos", payableNos);
		if (StringUtils.isEmpty(active)) {
            return null;
		}
		map.put("active", active);
		return this.getSqlSession().selectList(NAMESPACES + "selectByPayableNos", map);
	}

	/**
	 * 根据传入的一到多个来源单号，获取一到多条应付单明细信息
     * @author hemingyu
     * @date 2016-01-21 16:07:21
	 * @param sourceBillNos
	 * @param active
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillPayableDEntity> queryBySourceBillNOs(List<String> sourceBillNos, String active) {
		if (CollectionUtils.isEmpty(sourceBillNos)){
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sourceBillNos", sourceBillNos);
        if (StringUtils.isEmpty(active)) {
            return null;
        }
        map.put("active", active);
		return this.getSqlSession().selectList(NAMESPACES + "selectBySourceBillNos", map);
	}

}
