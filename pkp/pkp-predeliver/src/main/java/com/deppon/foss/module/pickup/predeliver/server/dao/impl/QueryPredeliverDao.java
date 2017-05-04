package com.deppon.foss.module.pickup.predeliver.server.dao.impl;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IQueryPredeliverDao;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.SendHandoverInfoDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.SendInfoSearchDto;


public class QueryPredeliverDao extends iBatis3DaoImpl implements IQueryPredeliverDao{
	/**
	 * 派送信息name space
	 */
	private static final String NAMESPACE_CONTRACT = "foss.pickup.predeliver.queryPredeliver.";
	
	/**
	 * 
	 * @根据查询条件返回派送信息查询集合
	 * @author zyr
	 * @2015-05-26
	 * @return List
	 * @param SendHandoverInfoDto  派送信息查询条件
	 * @param start  数据开始下标
	 * @param limit  获取数据长度
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<SendHandoverInfoDto> queryPredeliver(SendInfoSearchDto sendInfoSearchDto,int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(
				NAMESPACE_CONTRACT + "queryPredeliver", sendInfoSearchDto,
				rowBounds);
	}
	
	@Override
	public Long getPredeliverInfoCount(SendInfoSearchDto sendInfoSearchDto) {
		return (Long)this.getSqlSession().selectOne(NAMESPACE_CONTRACT + "getPredeliverInfoCount",sendInfoSearchDto);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<SendHandoverInfoDto> queryPredeliverInfo(SendInfoSearchDto sendInfoSearchDto) {
		return this.getSqlSession().selectList(
				NAMESPACE_CONTRACT + "queryPredeliver", sendInfoSearchDto);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<SendHandoverInfoDto> queryPredeliverWaybill(SendInfoSearchDto sendInfoSearchDto,int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(
				NAMESPACE_CONTRACT + "queryPredeliverWaybill", sendInfoSearchDto,
				rowBounds);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<SendHandoverInfoDto> queryPredeliverWaybillInfo(SendInfoSearchDto sendInfoSearchDto) {
		return this.getSqlSession().selectList(
				NAMESPACE_CONTRACT + "queryPredeliverWaybill", sendInfoSearchDto);
	}
	
	@Override
	public Long getPredeliverInfoCountWaybill(SendInfoSearchDto sendInfoSearchDto) {
		return (Long)this.getSqlSession().selectOne(NAMESPACE_CONTRACT + "getPredeliverInfoCountWaybill",sendInfoSearchDto);
	}

}
