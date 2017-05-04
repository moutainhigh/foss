package com.deppon.foss.module.settlement.common.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.common.api.server.dao.IGreenHandWrapWriteoffDao;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.GreenHandWrapWriteoffEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;

public class GreenHandWrapWriteoffDao extends iBatis3DaoImpl implements IGreenHandWrapWriteoffDao{
	
	// 命名空间路径
	private static final String NAMESPACES = "foss.stl.GreenHandWrapWriteoffDao.";
	
	
	@Override
	public int addDopInfo(GreenHandWrapWriteoffEntity greenHandWrapWriteoffEntity) {
			
		return this.getSqlSession().insert(NAMESPACES+"addGreenHandWrapWriteoffEntity", greenHandWrapWriteoffEntity);
	}
	
	/**
	 * @author 218392 zhangyongxue
	 * @date 2016-02-18 14:44:50
	 * 根据运单号查询始发未核销的应收单，来源单据子类型为W-开单的
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillReceivableEntity> queryByWaybillNOs(String waybillNo) {
		Map<String,String> map=new HashMap<String,String>();
		map.put("waybillNo", waybillNo);
		return this.getSqlSession().selectList(NAMESPACES + "selectByWaybillNo",map);
		
	}

    /**
     * @218392 zhangyongxue
     * @date 2016/02-21 14:28:20
     * 更新暂存表是否核销
     */
	@Override
	public int updateGreenHandWrapInfo(
			GreenHandWrapWriteoffEntity greenHandWrapWriteoffEntity) {
		if(greenHandWrapWriteoffEntity != null){
			return this.getSqlSession().update(NAMESPACES + "updateGreenHandWrapInfo", greenHandWrapWriteoffEntity); 
		}else{
			throw new SettlementException("传入修改暂存表是否核销实体参数为空!");
		}
		
	}

    /**
     * @author 218392 张永雪
     * 将暂存表的数据转储到 转储表
     * @param greenHandWrapWriteoffEntity
     * @return
     */
	@Override
	public int addDumpGreenHandFromWrap(
			GreenHandWrapWriteoffEntity greenHandWrapWriteoffEntity) {
		if(greenHandWrapWriteoffEntity == null){
			throw new SettlementException("将暂存表的数据转储到转储表时实体参数为空!");
		}
		return this.getSqlSession().insert(NAMESPACES + "addDumpGreenHandFromWrap", greenHandWrapWriteoffEntity);
	}

    /**
     * @author 218392 张永雪
     * 将转移走的数据从暂存表中删除
     * @param greenHandWrapWriteoffEntity
     * @return
     */
	@Override
	public int deleteWrapGreenHandInfo(
			GreenHandWrapWriteoffEntity greenHandWrapWriteoffEntity) {
		if(greenHandWrapWriteoffEntity == null){
			throw new SettlementException("将转移走的数据从暂存表中删除时实体参数为空!");
		}
		return this.getSqlSession().delete(NAMESPACES + "deleteWrapGreenHandInfo", greenHandWrapWriteoffEntity);
	}

    /**
     * @author 218392 张永雪
     * @date 2016-02-23 12:25:30
     * 根据单号查询未核销暂存表的信息
     */
	@SuppressWarnings("unchecked")
	@Override
	public List<GreenHandWrapWriteoffEntity> queryGreenHandWrapByWaybillNo(String waybillNo) {
		GreenHandWrapWriteoffEntity entity = new GreenHandWrapWriteoffEntity();
		entity.setWaybillNo(waybillNo);
		entity.setWriteoffStatus("N");
		return this.getSqlSession().selectList(NAMESPACES + "queryGreenHandWrapByWaybillNo", entity);
	}
	
	/**
	 * 更具运单号查询无效的应收单
	 * @Title: queryReceiveByWaybillNOs 
	 * @author： 269052 |zhouyuan008@deppon.com
	 * @date 2016-7-8 下午10:05:32
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillReceivableEntity> queryReceiveByWaybillNos(String waybillNo) {
		Map<String,String> map=new HashMap<String,String>();
		map.put("waybillNo", waybillNo);
		return this.getSqlSession().selectList(NAMESPACES + "queryReceiveByWaybillNos",map);
	}
	
	/**
	 * 查询到达应收单
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillReceivableEntity> queryDrReceiveByWaybillNos(
			String waybillNo) {
		Map<String,String> map=new HashMap<String,String>();
		map.put("waybillNo", waybillNo);
		return this.getSqlSession().selectList(NAMESPACES + "queryDestReceiveByWaybillNos",map);
	}

}
