package com.deppon.foss.module.base.baseinfo.server.dao.impl.commonselector;


import java.util.List;
import org.apache.ibatis.session.RowBounds;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonVehicleDrivingDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.VehicleDrivingEntity;


public class CommonVehicleDrivingDao extends SqlSessionDaoSupport implements ICommonVehicleDrivingDao{

	private static final String NAMESPACE = "foss.bse.bse-baseinfo.commonvehicledrivingentity.";
	
	/**
	 * @author 323136
	 * 
	 * 公共选择器查询总条数
	 */
	@Override
	public long queryVehicleDrivingCount(VehicleDrivingEntity entity) {
		
		return (Long) getSqlSession().selectOne(NAMESPACE+"queryVehicleDrivingCount", entity);
	}

	/**
	 * @author 323136
	 * 公共选择器查询
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<VehicleDrivingEntity> queryVehicleDriving(
			VehicleDrivingEntity entity, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start,limit);
		return getSqlSession().selectList(NAMESPACE+"queryVehicleDriving",entity,rowBounds);
	}

}
