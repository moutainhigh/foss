package com.deppon.foss.module.base.baseinfo.server.dao.impl.commonselector;


import java.util.List;
import org.apache.ibatis.session.RowBounds;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonInviteFossVehicleCostDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonVehicleDrivingDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.InviteCommonExpressageEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.InviteFossVehicleCostEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.VehicleDrivingEntity;


public class CommonInviteFossVehicleCostDao extends SqlSessionDaoSupport implements ICommonInviteFossVehicleCostDao{

	private static final String NAMESPACE = "foss.bse.bse-baseinfo.commoninvitefossvehiclecostentity.";
	
	/**
	 * @author 332219
	 * 
	 * 公共选择器查询总条数
	 */
	@Override
	public long queryCommonBusinessCount(InviteFossVehicleCostEntity entity) {
		
		return (Long) getSqlSession().selectOne(NAMESPACE+"queryCommonBusinessCount", entity);
	}

	/**
	 * @author 332219
	 * 公共选择器查询
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<InviteFossVehicleCostEntity> queryCommonInviteFossVehicleCostByBusiness(
			InviteFossVehicleCostEntity entity,int start, int limit){
		RowBounds rowBounds = new RowBounds(start,limit);
		return getSqlSession().selectList(NAMESPACE+"queryCommonInviteFossVehicleCostByBusiness",entity,rowBounds);
	}
	/**
	 * @author 332219
	 * 
	 * 公共选择器查询总条数
	 */
	@Override
	public long queryCommonRegionalCount(InviteFossVehicleCostEntity entity) {
		
		return (Long) getSqlSession().selectOne(NAMESPACE+"queryCommonRegionalCount", entity);
	}

	/**
	 * @author 332219
	 * 公共选择器查询
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<InviteFossVehicleCostEntity> queryCommonInviteFossVehicleCostByRegional(
			InviteFossVehicleCostEntity entity,int start, int limit){
		RowBounds rowBounds = new RowBounds(start,limit);
		return getSqlSession().selectList(NAMESPACE+"queryCommonInviteFossVehicleCostByRegional",entity,rowBounds);
	}
	
	/**
	 * @author 332219
	 * 
	 * 公共选择器查询总条数
	 */
	@Override
	public long queryCommonExpressageCount(InviteCommonExpressageEntity entity) {
		
		return (Long) getSqlSession().selectOne(NAMESPACE+"queryCommonExpressageCount", entity);
	}

	/**
	 * @author 332219
	 * 公共选择器查询
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<InviteCommonExpressageEntity> queryCommonInviteFossVehicleCostByExpressage(
			InviteCommonExpressageEntity entity,int start, int limit){
		RowBounds rowBounds = new RowBounds(start,limit);
		return getSqlSession().selectList(NAMESPACE+"queryCommonInviteFossVehicleCostByExpressage",entity,rowBounds);
	}
}
