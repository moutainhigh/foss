package com.deppon.foss.module.pickup.waybill.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillPackBIDao;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPackBIEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

public class WaybillPackBIDao  extends iBatis3DaoImpl implements IWaybillPackBIDao {

	private static final String NAMESPACE = "foss.pkp.WaybillPackBIEntityMapper.";
	
	/**
	 * 
	 * 新增
	 * @author 043258-foss-zhaobin
	 * @date 2014-7-9 下午5:33:22
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillPackBIDao#addWaybillPackBIEntity(com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPackBIEntity)
	 */
	@Override
	public int addWaybillPackBIEntity(WaybillPackBIEntity waybillPackBIEntity) {
		waybillPackBIEntity.setId(UUIDUtils.getUUID());
		int result = this.getSqlSession().insert(NAMESPACE + "insertSelective", waybillPackBIEntity);
		return result > 0 ? result : 0;
	}
	
	/**
	 * 
	 * 删除
	 * @author 043258-foss-zhaobin
	 * @date 2014-7-9 下午5:34:12
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillPackBIDao#deleteByPrimaryKey(java.lang.String)
	 */
	@Override
	public int deleteByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * 
	 * 修改
	 * @author 043258-foss-zhaobin
	 * @date 2014-7-9 下午5:34:25
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillPackBIDao#updateByWaybillNo(com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPackBIEntity)
	 */
	@Override
	public int updateByWaybillNo(WaybillPackBIEntity waybillPackBIEntity) {
		int result = this.getSqlSession().update(
				NAMESPACE + "updateByWaybillNo", waybillPackBIEntity);
		return result > 0 ? result : 0;
	}

	/**
	 * 
	 * 查询
	 * @author 043258-foss-zhaobin
	 * @date 2014-7-9 下午5:34:38
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillPackBIDao#queryByWaybillNo(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public WaybillPackBIEntity queryByWaybillNo(String waybillNo) {
		// 参数Map
		Map<String, String> map = new HashMap<String, String>();
		map.put("waybillNo", waybillNo);
		map.put("active", FossConstants.YES);
		List<WaybillPackBIEntity> list = this.getSqlSession().selectList(NAMESPACE + "selectByWaybillNo", map);
		return CollectionUtils.isEmpty(list) ? null : list.get(0);
	}

}
