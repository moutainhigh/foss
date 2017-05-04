package com.deppon.foss.module.base.baseinfo.server.dao.impl.commonselector;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonExpressVehicleDao;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressVehicleDto;

public class CommonExpressVehicleDao extends SqlSessionDaoSupport implements ICommonExpressVehicleDao {

	/**
	 * 定义常量NAMESPACE命名空间
	 */
	private static final String NAMESPACE = "foss.bse.bse-baseinfo.comonExpressVehicle.";
	
	/**
	 * 根据条件查询快递车信息
	 * 
	 * @author WangPeng
	 * @Date   2013-7-30 上午8:50:59
	 * @param expressVeh
	 * @param limit
	 * @param start
	 * @return List<ExpressVehicleEntity>
	 *
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ExpressVehicleDto> queryExpressVehicleNoListBySelectiveCondition(
			ExpressVehicleDto expressVeh, int limit, int start) {
		RowBounds rowBounds = new RowBounds(start,limit);
		return this.getSqlSession().selectList(NAMESPACE + "queryAllExpressVehicles", expressVeh, rowBounds);
	}

	/**
	 * 查询记录总行数
	 * 
	 * @author WangPeng
	 * @Date   2013-7-30 上午8:51:04
	 * @param  expressVeh
	 * @return long
	 *
	 */
	@Override
	public long queryExpressVehicleNoRecordCountBySelectiveCondition(
			ExpressVehicleDto expressVeh) {
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "queryRecords", expressVeh);
	}

	/**
	 * 查询快递员列表
	 * 
	 * @author  WangPeng
	 * @Date    2013-7-30 下午4:28:19
	 * @param   entity
	 * @return List<ExpressVehicleDto>
	 *
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ExpressVehicleDto> queryExpressEmployeeListByCondition(ExpressVehicleDto entity) {
		return this.getSqlSession().selectList(NAMESPACE + "queryExpressEmpList", entity);
	}
}
