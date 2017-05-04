package com.deppon.foss.module.pickup.waybill.server.dao.impl;

import java.util.Date;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWayGXGOrderDao;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillGXGEntity;

public class WayGXGOrderDao extends iBatis3DaoImpl  implements IWayGXGOrderDao{
	private static final String NAMESPACE = "foss.pkp.WaybillGXGEntityMapper.";
	//GXG订单入库
	public int createGXGOrderbill(WaybillGXGEntity waybillGXGEntity) {
		    waybillGXGEntity.setCreateDate(new Date());
       		return this.getSqlSession().insert(NAMESPACE + "insert", waybillGXGEntity);
	}
	//唯一性校验条码号CUSTOMERLABLENUM
	public int checkGXGOrderbill(String customerLableNum) {
		
	        return (Integer) this.getSqlSession().selectOne(NAMESPACE + "check", customerLableNum);
	}
	
}