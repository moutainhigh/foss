package com.deppon.foss.module.pickup.sign.server.dao.impl;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.sign.api.server.dao.IClaimStatusDao;
import com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillSignResultEntity;

/**
 * 
* @Description: 查询更改理赔状态
* @author 330547---徐杰
* @date 2016-6-25
  @param 
  @return
 */

public class ClaimStatusDao extends iBatis3DaoImpl implements IClaimStatusDao {
	private static final String NAMESPACE = "foss.pkp.claimStatusMapper.";
	
	//设置理赔状态为Y,表示理赔中
	@Override
	public int setStatus(WaybillSignResultEntity entity) {
		return this.getSqlSession().update(NAMESPACE+"updateClaimStatusToY",entity);
		
	}
	
	//设置理赔状态为N,表示不在理赔中
	@Override
	public int cancelStatus(WaybillSignResultEntity entity) {
		return this.getSqlSession().update(NAMESPACE+"updateClaimStatusToN",entity);
		
	}
	
	//获取理赔状态,Y表示在理赔中,N表示不在理赔中
	@Override
	public String getStatus(WaybillSignResultEntity entity) {
		return (String) this.getSqlSession().selectOne(NAMESPACE+"queryClaimStatusByWaybillNo",entity);
		
	}

}
