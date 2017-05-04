package com.deppon.foss.module.settlement.common.api.server.dao;

import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.domain.TruckArriveConfirmEntity;


public interface ITruckArriveConfirmDao {
//    int deleteByPrimaryKey(String id);

//    int insert(TruckArriveConfirmEntity record);
    /**
     * 到达确认反到达确认插入
     * @param record 插入实体
     * @return 插入条数
     */
    int insertSelective(TruckArriveConfirmEntity record);
//
//    TruckArriveConfirmEntity selectByPrimaryKey(String id);
//
//    int updateByPrimaryKeySelective(TruckArriveConfirmEntity record);
//
//    int updateByPrimaryKey(TruckArriveConfirmEntity record);
    
	/**
	 * 查询整车外请车到达确认凭证表
	 * @param dto
	 * @return
	 */
	List<TruckArriveConfirmEntity> queryTruckConfirmByEntity(TruckArriveConfirmEntity dto);
	
	/**
	 * 重新到达 整车外请车到达确认凭证表
	 * @param dto
	 * 确认类型 TAC 到达确认  UAC 反到达确认
	 */
	void updateTruckConfirmByEntity(TruckArriveConfirmEntity dto);
    
}