package com.deppon.foss.module.transfer.platform.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.platform.api.server.dao.ICarEfficiencyManageDao;
import com.deppon.foss.module.transfer.platform.api.shared.domain.CarEfficiencyEntity;


/**
* @description 车辆装卸车效率管理Dao
* @version 1.0
* @author 14022-foss-songjie
* @update 2014年5月5日 上午11:14:40
*/
public class CarEfficiencyManageDaoImpl extends iBatis3DaoImpl implements ICarEfficiencyManageDao {
	/** mapper 文件命名空间*/
	public static final String NAME_SPACE = "foss.platform.carEfficiencyManage.";
	
	/**
	* @description 长途装卸车效率查询
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.platform.api.server.dao.ICarEfficiencyManageDao#queryCarEfficiencyWayLong(java.util.Map)
	* @author 14022-foss-songjie
	* @update 2014年5月5日 上午11:15:15
	* @version V1.0
	*/
	@SuppressWarnings("unchecked")
	@Override
	public List<CarEfficiencyEntity> queryCarEfficiencyWayLong(
			CarEfficiencyEntity carEfficiency,int start, int limit) {
		if(start<=0 && limit <=0){
			return this.getSqlSession().selectList(NAME_SPACE + "queryCarEfficiencyWayLong", carEfficiency);
		}else{
			RowBounds rowBounds = new RowBounds(start, limit);
			return this.getSqlSession().selectList(NAME_SPACE + "queryCarEfficiencyWayLong", carEfficiency,rowBounds);
		}
	}
	
	
	
	
	/**
	* @description 长途装卸车效率查询 总记录数
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.platform.api.server.dao.ICarEfficiencyManageDao#queryCarEfficiencyWayLongCount(com.deppon.foss.module.transfer.platform.api.shared.domain.CarEfficiencyEntity)
	* @author 14022-foss-songjie
	* @update 2014年5月5日 上午11:49:23
	* @version V1.0
	*/
	@Override
	public int queryCarEfficiencyWayLongCount(CarEfficiencyEntity carEfficiency) {
		return (Integer) this.getSqlSession().selectOne(NAME_SPACE+"queryCarEfficiencyWayLongCount",carEfficiency);
	}



	/**
	* @description 短途装卸车效率查询
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.platform.api.server.dao.ICarEfficiencyManageDao#queryCarEfficiencyWayShort(java.util.Map)
	* @author 14022-foss-songjie
	* @update 2014年5月5日 上午11:15:23
	* @version V1.0
	*/
	@SuppressWarnings("unchecked")
	@Override
	public List<CarEfficiencyEntity> queryCarEfficiencyWayShort(
			CarEfficiencyEntity carEfficiency,int start, int limit) {
		if(start<=0 && limit <=0){
			return this.getSqlSession().selectList(NAME_SPACE+"queryCarEfficiencyWayShort", carEfficiency);
		}else{
			RowBounds rowBounds = new RowBounds(start, limit);
			return this.getSqlSession().selectList(NAME_SPACE+"queryCarEfficiencyWayShort", carEfficiency,rowBounds);
		}
		
	}



	
	/**
	* @description 短途装卸车效率查询 总记录数
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.platform.api.server.dao.ICarEfficiencyManageDao#queryCarEfficiencyWayShortCount(com.deppon.foss.module.transfer.platform.api.shared.domain.CarEfficiencyEntity)
	* @author 14022-foss-songjie
	* @update 2014年5月5日 上午11:49:26
	* @version V1.0
	*/
	@Override
	public int queryCarEfficiencyWayShortCount(CarEfficiencyEntity carEfficiency) {
		return (Integer) this.getSqlSession().selectOne(NAME_SPACE+"queryCarEfficiencyWayShortCount",carEfficiency);
	}



	/**
	* @description 相关的理货员
	* @param list
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年5月5日 下午2:44:24
	*/
	@SuppressWarnings("unchecked")
	@Override
	public List<CarEfficiencyEntity> queryLoaderListByTaskId(
			List<String> taskIdList) {
		return this.getSqlSession().selectList(NAME_SPACE+"queryLoaderListByTaskId",taskIdList);
	}
	
}
