package com.deppon.foss.module.pickup.waybill.server.dao.impl;

import org.apache.commons.lang3.StringUtils;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWoodenRequirementsPgDao;
import com.deppon.foss.module.pickup.waybill.shared.domain.WoodenRequirementsPgEntity;

/**
 * 存储临时的包装信息
 * @author 297064 2016-06-13
 */
public class WoodenRequirementsPgDao extends iBatis3DaoImpl implements IWoodenRequirementsPgDao {

	private static final String NAMESPACE = "foss.pkp.WoodenRequirementsPgMapper.";
	
	@Override
	public int insertSelective(WoodenRequirementsPgEntity entity) {
		// TODO Auto-generated method stub
		return this.getSqlSession().insert(NAMESPACE+"insertSelective", entity);
	}
	
	/**
	 * 根据WaybillNo更新对象，不包含字段（ID,WAYBILL_NO,CREATE_TIME）,modify_time在SQL中已经设置
	 */
	@Override
	public int updateSelectiveByWaybillNo(WoodenRequirementsPgEntity entity) {
		// TODO Auto-generated method stub
		if(null!=entity && StringUtils.isNotEmpty(entity.getWaybillNo())){
			return this.getSqlSession().update(NAMESPACE+"updateSelective", entity);
		}else{
			return 0;
		}
	}
	
	/**
	 * 根据运单号查找包装信息
	 */
	@Override
	public WoodenRequirementsPgEntity queryByWayBillNo(String waybillNo) {
		// TODO Auto-generated method stub、
		if(StringUtils.isEmpty(waybillNo)){
			return null;
		}
		Object select = this.getSqlSession().selectOne(NAMESPACE+"queryByWayBillNo",waybillNo);
		if(null!=select){
			return (WoodenRequirementsPgEntity)select;
		}
		return null;
	}
	
	
}
