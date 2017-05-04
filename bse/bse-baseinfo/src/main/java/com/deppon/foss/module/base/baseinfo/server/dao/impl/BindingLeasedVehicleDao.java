package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.List;
import org.apache.ibatis.session.RowBounds;
import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IBindingLeasedVehicleDao;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.BindingLeasedTruckDto;

/**
 * 订单 绑定车队的外请车查询
 * @ClassName: BindingLeasedVehicleDao 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author 310854-liuzhenhua
 * @date 2016-9-8 下午5:00:56 
 *
 */
public class BindingLeasedVehicleDao extends SqlSessionDaoSupport implements
		IBindingLeasedVehicleDao {

	private static final String NAMESPACE = ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX
			+ ".bindingLeasedtruck";
	
	@SuppressWarnings("unchecked")
	@Override
	public List<BindingLeasedTruckDto> queryBindingLeasedVehicleList(
			BindingLeasedTruckDto leasedTruck, int offset, int limit)
			throws BusinessException {
		// TODO Auto-generated method stub
		return getSqlSession().selectList(
				NAMESPACE + ".queryBindingLeasedVehicleList", leasedTruck,
				new RowBounds(offset, limit));
	}

	@Override
	public long queryBindingLeasedVehicleListTotal(
			BindingLeasedTruckDto leasedTruck) throws BusinessException {
		// TODO Auto-generated method stub
		long recordRount = 0;
		Object result = getSqlSession().selectOne(
				NAMESPACE + ".queryBindingLeasedVehicleTotal", leasedTruck);
		if (null != result) {
			recordRount = Long.parseLong(result.toString());
		}
		return recordRount;
	}

	@Override
	public String queryOrgNameByVehicleNo(String vehicleNo) {
		// TODO Auto-generated method stub
		try{
			return (String)getSqlSession().selectOne(
					NAMESPACE + ".queryOrgNameByVehicleNo", vehicleNo);
		}catch(BusinessException e){
			throw new BusinessException("通过车牌号获取绑定的顶级车队有误："+e.getMessage());
		}
	}

}
