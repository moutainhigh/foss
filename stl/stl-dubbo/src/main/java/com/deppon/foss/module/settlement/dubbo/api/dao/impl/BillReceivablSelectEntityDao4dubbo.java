package com.deppon.foss.module.settlement.dubbo.api.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import com.deppon.foss.module.settlement.dubbo.api.dao.IBillReceivablSelectEntityDao4dubbo;
import com.deppon.foss.module.settlement.dubbo.api.define.BillReceivableEntity;

/**
 * 
 * @author 327090
 *
 */
public class BillReceivablSelectEntityDao4dubbo extends BaseDao implements
		IBillReceivablSelectEntityDao4dubbo {
	
	/**
	 *  命名空间路径
	 */
	private static final String NAMESPACES = "foss.ecs.BillReceivableSelectEntityDao.";

	/**
	 * @author 327090
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillReceivableEntity> queryBySourceBillNOs(
			List<String> sourceBillNos, String sourceBillType, String active) {
		if (CollectionUtils.isEmpty(sourceBillNos)) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sourceBillNos", sourceBillNos);
		map.put("sourceBillType", sourceBillType);
		if (StringUtils.isNotEmpty(active)) {
			map.put("active", active);
		}
		return this.getSqlSession().selectList(NAMESPACES + "selectBySourceBillNos_4_dubbo", map);
	}
	

}
