package com.deppon.foss.module.transfer.scheduling.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.GoodsAreaEntity;

import com.deppon.foss.module.transfer.scheduling.api.server.dao.IExhibitForecastDao;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.ExhibitForecastEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.ExhibitForecastDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.ExhibitForecastForWorldDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.ExhibitWaybillDto;
import com.deppon.foss.util.define.FossConstants;


/**
 * 展会货货量统计
 * @author 200978  xiaobingcheng
 * 2014-11-26
 */
public class ExhibitForecastDao extends iBatis3DaoImpl implements IExhibitForecastDao {
	
	private static final String NAMESPACE = "foss.scheduling.ExhibitForecast.";
	

	/**
	 * 查询以scheduledFireTime开始之前day天的提货网点是驻地营业部未全部签收的展会货运单
	 * @Author: 200978  xiaobingcheng
	 * 2014-11-27
	 * @param day
	 * @param scheduledFireTime
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<ExhibitWaybillDto> queryExhibitWaybillOfNotSignAndStation(int day,Date scheduledFireTime){
		HashMap map = new HashMap();
		map.put("day", day);
		map.put("scheduledFireTime", scheduledFireTime);
		return this.getSqlSession().selectList(NAMESPACE + "queryExhibitWaybillOfNotSignAndStation", map);
	}
	
	/**
	 * 新增展会货信息
	 * @Author: 200978  xiaobingcheng
	 * 2014-12-3
	 * @param exhibitForecastEntity
	 */
	public void saveExhibitForecastEntity(ExhibitForecastEntity exhibitForecastEntity){
		this.getSqlSession().insert(NAMESPACE+"saveExhibitForecastEntity", exhibitForecastEntity);
	}
	
	/**
	 * 根据运单号查询有效的展会货统计数量
	 * @Author: 200978  xiaobingcheng
	 * 2014-12-3
	 * @param waybillNo
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public int queryOldExhibitCargoByWaybillNo(String waybillNo){
		HashMap map = new HashMap();
		map.put("waybillNo", waybillNo);
		map.put("active", FossConstants.YES);
		return (Integer) this.getSqlSession().selectOne(NAMESPACE+"queryOldExhibitCargoByWaybillNo", map);
	}
	
	/**
	 * 设置该运单的统计信息为无效，方便后面添加新的信息
	 * @Author: 200978  xiaobingcheng
	 * 2014-12-3
	 * @param waybillNo
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void cancleOldExhibitCargoByWaybillNo(String waybillNo){
		HashMap map = new HashMap();
		map.put("waybillNo", waybillNo);
		map.put("active", FossConstants.YES);
		this.getSqlSession().update(NAMESPACE+"cancleOldExhibitCargoByWaybillNo", map);
	}
	
	/**
	 * 查询展会货列表
	 * @Author: 200978  xiaobingcheng
	 * 2014-12-5
	 * @param dto
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<ExhibitForecastEntity> queryForecastExhibitList(ExhibitForecastDto dto,int start,int limit){
		RowBounds rb = new RowBounds(start, limit);
		Map map = new HashMap();
		map.put("org", dto.getTransferCenterCode());
		map.put("status", dto.getStatus());
		map.put("active", FossConstants.YES);
		return this.getSqlSession().selectList(NAMESPACE + "queryForecastExhibitList", map, rb);
	}
	
	/**
	 * 查询展会货总记录数
	 * @Author: 200978  xiaobingcheng
	 * 2014-12-5
	 * @param dto
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Long queryForecastExhibitListCount(ExhibitForecastDto dto){
		Map map = new HashMap();
		map.put("org", dto.getTransferCenterCode());
		map.put("status", dto.getStatus());
		map.put("active", FossConstants.YES);
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "queryForecastExhibitListCount", map);
	}
	
	/**
	 * 根据外场和货区类型查询货区
	 * @Author: 200978  xiaobingcheng
	 * 2014-12-6
	 * @param orgCode
	 * @param goodsAreaType
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<GoodsAreaEntity> queryGoodsAreaByOrgCode(String orgCode,String goodsAreaType){
		Map map = new HashMap();
		map.put("orgCode", orgCode);
		map.put("goodsAreaType", goodsAreaType);
		map.put("active", FossConstants.YES);
		return (List<GoodsAreaEntity>) this.getSqlSession().selectList(NAMESPACE + "queryGoodsAreaByOrgCode", map);
	}
	
	/**
	 * 查询全国外场展会货货量统计信息
	 * @Author: 200978  xiaobingcheng
	 * 2014-12-8
	 * @param transferCenterCode
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<ExhibitForecastForWorldDto> queryForecastExhibitForWorldList(String transferCenterCode){
		Map map = new HashMap();
		map.put("transferCenterCode", transferCenterCode);
		return this.getSqlSession().selectList(NAMESPACE + "queryForecastExhibitForWorldList", map);
	}
	
	/**
	 * 查询展会货货中 已经签收的记录
	 * @Author: 200978  xiaobingcheng
	 * 2014-12-18
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> querySignedAndActiveExhibit(){
		return this.getSqlSession().selectList(NAMESPACE+"querySignedAndActiveExhibit");
	}
	
}
