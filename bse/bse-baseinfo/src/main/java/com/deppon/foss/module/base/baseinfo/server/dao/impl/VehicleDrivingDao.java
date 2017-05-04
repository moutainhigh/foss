package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;



import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IVehicleDrivingDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.VehicleDrivingEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.VehicleDrivingQueryDto;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

public class VehicleDrivingDao extends SqlSessionDaoSupport implements IVehicleDrivingDao{

	private static final String NAMESPACE = "foss.bse.bse-baseinfo.vehicledrivingentity.";
	/**
	 * @author 323136
	 * 增加长途车队信息
	 */
	@Override
	public int addVehicleDriving(VehicleDrivingEntity entity, CurrentInfo info) {
		if(entity == null){
			throw new BusinessException("新增长途车队参数异常");
		}
		Date date = new Date();//当前时间
		entity.setId(UUIDUtils.getUUID());
		entity.setCreateUser(info.getEmpCode());//当前创建人
		entity.setModifyUser(info.getEmpCode());//当前修改人
		entity.setActive(FossConstants.ACTIVE);//激活有效
		entity.setCreateTime(date);
		entity.setModifyTime(new Date(NumberConstants.ENDTIME));//修改时间
		
		return getSqlSession().insert(NAMESPACE+"addVehicleDriving", entity);
	}
	/**
	 * @author 323136
	 * 修改(作废)长途车队信息
	 */
	@Override
	public int updateVehicleDriving(VehicleDrivingQueryDto queryDto,
			CurrentInfo info) {
		Date date = new Date();
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("modifyTime", date);
		map.put("modifyUser", info.getEmpCode());
		map.put("active", FossConstants.INACTIVE);
		map.put("ids", queryDto.getSelectedIdList());
		
		return getSqlSession().update(NAMESPACE+"updateOrDeleteVehicleDriving", map);
	}
	/**
	 * @author 323136
	 * 查询总条数
	 */
	@Override
	public long queryTotalByCondition(VehicleDrivingQueryDto queryDto) {
		
		return (Long) getSqlSession().selectOne(NAMESPACE+"queryTotalByCondition", queryDto);
	}
	
	/**
	 * @author 323136
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<VehicleDrivingEntity> queryVehicleDrivingByCondition(
			VehicleDrivingQueryDto queryDto, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return getSqlSession().selectList(NAMESPACE+"queryVehicleDrivingByCondition",queryDto,rowBounds );
	}
	/**
	 * @author 323136
	 * 根据长途车队编码和下辖部门编码查询
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<VehicleDrivingEntity> queryVehicleDrivingByFleetDep(
			String longHaulFleetCode, String departmentCode) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("longHaulFleetCode", longHaulFleetCode);
		map.put("departmentCode", departmentCode);
		return getSqlSession().selectList(NAMESPACE+"queryVehicleDrivingByFleetDep", map);
	}
	/**
	 * @author 323136
	 * 校验行车编码的唯一性
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<VehicleDrivingEntity> queryVehicleDrivingByFleetTra(
			String longHaulFleetCode, String trafficCode) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("longHaulFleetCode", longHaulFleetCode);
		map.put("trafficCode", trafficCode);
		return getSqlSession().selectList(NAMESPACE+"queryVehicleDrivingByFleetTra", map);
	}

}
