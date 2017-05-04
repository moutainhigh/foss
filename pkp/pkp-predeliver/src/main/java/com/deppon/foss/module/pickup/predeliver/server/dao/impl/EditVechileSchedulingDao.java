package com.deppon.foss.module.pickup.predeliver.server.dao.impl;

import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IEditVechileSchedulingDao;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.EditVechileSchedulingEntity;

/** 
 * @ClassName: EditVechileSchedulingDao 
 * @Description: 车辆排班发生更改表Dao 实现
 * @author 237982-foss-fangwenjun 
 * @date 2015-5-18 上午10:53:04 
 *  
 */
public class EditVechileSchedulingDao extends iBatis3DaoImpl implements IEditVechileSchedulingDao {

	private final static String EDITSCHEDULING_NAMESPACE = "com.deppon.foss.module.pickup.predeliver.api.shared.domain.EditVechileSchedulingEntityMapper.";
	
	/* (non-Javadoc)
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.dao.IEditVechileSchedulingDao#insertOne(com.deppon.foss.module.pickup.predeliver.api.shared.domain.EditVechileSchedulingEntity)
	 */
	@Override
	public int insertOne(EditVechileSchedulingEntity editVechileScheduling) {
		return super.getSqlSession().insert(EDITSCHEDULING_NAMESPACE + "insertOne", editVechileScheduling);
	}
	
	@Override
	public int insertList(List<EditVechileSchedulingEntity> editVechileSchedulings) {
		return super.getSqlSession().insert(EDITSCHEDULING_NAMESPACE + "insertList", editVechileSchedulings);
	}

	/* (non-Javadoc)
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.dao.IEditVechileSchedulingDao#selectList()
	 */
	@Override
	public List<EditVechileSchedulingEntity> selectList() {
		return super.getSqlSession().selectList(EDITSCHEDULING_NAMESPACE + "selectList");
	}

	/* (non-Javadoc)
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.dao.IEditVechileSchedulingDao#deleteByEditDate()
	 */
	@Override
	public int deleteByEditDate() {
		return super.getSqlSession().delete(EDITSCHEDULING_NAMESPACE + "deleteByEditDate");
	}

	/* (non-Javadoc)
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.dao.IEditVechileSchedulingDao#updateIsUsed(java.util.Map)
	 */
	@Override
	public int updateIsUsed(Map<String, Object> paramMap) {
		return super.getSqlSession().update(EDITSCHEDULING_NAMESPACE + "updateIsUsed", paramMap);
	}

}
