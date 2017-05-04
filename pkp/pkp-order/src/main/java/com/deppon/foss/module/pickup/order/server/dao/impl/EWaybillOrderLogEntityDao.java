package com.deppon.foss.module.pickup.order.server.dao.impl;

import java.util.Date;
import java.util.List;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.order.api.server.dao.IEWaybillOrderLogEntityDao;
import com.deppon.foss.module.pickup.order.api.shared.domain.EWaybillOrderLogEntity;
import com.deppon.foss.util.UUIDUtils;

/**
 * 调度订单日志实体DAO
 * @author 136334-foss-bailei
 * @date 014-10-1 上午9:28:19
 */

public class EWaybillOrderLogEntityDao extends iBatis3DaoImpl implements IEWaybillOrderLogEntityDao {

	public static final String NAMESPACE = "com.deppon.foss.module.pickup.order.api.shared.domain.EWaybillOrderLogEntity.";
	
	//电子运单订单日志实体增加记录
	public int addEWaybillOrderLogEntity(EWaybillOrderLogEntity logEntity){
		// 设置UUID
		logEntity.setId(UUIDUtils.getUUID());
		logEntity.setCreateTime(new Date());
		logEntity.setModifyTime(new Date());
		return this.getSqlSession().insert(NAMESPACE+"addEWaybillOrderLogEntitySelective", logEntity);
	}

	/**
	 * 根据电子运单失败记录更新相关记录
	 * @author 136334-foss-bailei
	 * @date 2014-9-28 14:32:29
	 * @param orderNo
	 * @return
	 */
	@Override
	public int updateEWaybillOrderLogEntitySelective(EWaybillOrderLogEntity logEntity) {
		logEntity.setModifyTime(new Date());
		return this.getSqlSession().update(NAMESPACE+"updateEWaybillOrderLogEntitySelective", logEntity);
	}

	/**
	 * 根据订单号进行查询电子运单失败记录表
	 * @author 136334-foss-bailei
	 * @date 2014-9-28 14:32:29
	 * @param orderNo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<EWaybillOrderLogEntity> selectEWaybillOrderByBasicParams(EWaybillOrderLogEntity logEntity) {
		return this.getSqlSession().selectList(NAMESPACE+"selectEWaybillOrderByBasicParams", logEntity);
	}
}