package com.deppon.foss.module.pickup.predeliver.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IHandoverBillVoDao;
import com.deppon.foss.module.pickup.predeliver.api.shared.vo.HandoverBillVo;

/** 
 * @ClassName: HandoverBillMatchAddressDao 
 * @Description:  已交单VoDao实现
 * @author 237982-foss-fangwenjun 
 * @date 2015-5-4 下午4:51:40 
 *  
 */
public class HandoverBillVoDao extends iBatis3DaoImpl implements IHandoverBillVoDao {
	
	/** 
	 *  交单匹配地址命名空间 
	 */ 
	private static final String HANDOVERBILL_NAMESPACE = "com.deppon.foss.module.pickup.predeliver.api.shared.vo.HandoverBillVoMapper.";

	/* (non-Javadoc)
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.dao.IHandoverBillVoDao#selectList()
	 */
	@Override
	public List<HandoverBillVo> selectList() {
		return super.getSqlSession().selectList(HANDOVERBILL_NAMESPACE + "selectList");
	}

	/* (non-Javadoc)
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.dao.IHandoverBillVoDao#updateResidence(java.util.Map)
	 */
	@Override
	public int updateResidence(Map<String, Object> paramMap) {
		return super.getSqlSession().update(HANDOVERBILL_NAMESPACE + "updateResidence", paramMap);
	}

	/* (non-Javadoc)
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.dao.IHandoverBillVoDao#updateIsUsed(java.util.Map)
	 */
	@Override
	public int updateIsUsed(String id, String isUsed) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("id", id);
		paramMap.put("isUsed", isUsed);
		return super.getSqlSession().update(HANDOVERBILL_NAMESPACE + "updateIsUsed", paramMap);
	}

	/* (non-Javadoc)
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.dao.IHandoverBillVoDao#updateVehicle(java.util.Map)
	 */
	@Override
	public int updateVehicle(Map<String, Object> paramMap) {
		return super.getSqlSession().update(HANDOVERBILL_NAMESPACE + "updateVehicle", paramMap);
	}

	@Override
	public List<String> selectBySmallZone(Map<String, Object> paramMap) {
		return super.getSqlSession().selectList(HANDOVERBILL_NAMESPACE + "selectWaybillNoBySmallZoneCode", paramMap);
	}

}
