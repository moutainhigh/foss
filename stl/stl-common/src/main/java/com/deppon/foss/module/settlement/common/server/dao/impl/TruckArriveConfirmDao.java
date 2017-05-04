package com.deppon.foss.module.settlement.common.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.common.api.server.dao.ITruckArriveConfirmDao;
import com.deppon.foss.module.settlement.common.api.shared.domain.TruckArriveConfirmEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
/**
 * 车辆到达确认Dao
 * @author 073615
 *
 */
public class TruckArriveConfirmDao extends iBatis3DaoImpl implements ITruckArriveConfirmDao {

	private static final String NAMESPACE = "com.deppon.foss.module.settlement.common.server.dao.impl."; 
	
	@Override
	public int insertSelective(TruckArriveConfirmEntity record) {
		return getSqlSession().insert(NAMESPACE+"insertSelective", record);
	}

	/**
	 * 查询整车外请车到达确认凭证表
	 * @param dto @218392 张永雪 @date 2016-06-13 10:30:00
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<TruckArriveConfirmEntity> queryTruckConfirmByEntity(
			TruckArriveConfirmEntity dto) {
		if(dto == null){
			throw new SettlementException("尾款确认时候，查询整车外请车到达确认凭证表Dao层参数为空！");
		}
		String payableLastId = dto.getPayablelastId();//尾款应付单ID
		String conertType = dto.getConertType();//确认类型（确认TAC/反确认UAC）
			
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("payableLastId", payableLastId);
		map.put("conertType", conertType);
		return this.getSqlSession().selectList(NAMESPACE + "queryTruckConfirmByEntityByID" ,map);
	}

	/**
	 * 重新到达 整车外请车到达确认凭证表
	 * @param dto @218392 张永雪
	 * 确认类型 TAC 到达确认  UAC 反到达确认
	 */
	@Override
	public void updateTruckConfirmByEntity(TruckArriveConfirmEntity dto) {
		
		if(dto == null){
			throw new SettlementException("尾款确认时候，查询整车外请车到达确认凭证表Dao层参数为空！");
		}
		
		this.getSqlSession().update(NAMESPACE + "updateTruckConfirmByID", dto);
		
	}

}
