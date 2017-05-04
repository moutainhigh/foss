package com.deppon.foss.module.settlement.consumer.server.service.impl;

import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.server.dao.IQueryPodInfoDao;
import com.deppon.foss.module.settlement.consumer.api.server.service.IQueryPodInfoService;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.PodDto;

/**
 * 签收类型查询的service层
 * @author 198704 weitao
 * @date 2014-08-23
 */
public class QueryPodInfoService implements IQueryPodInfoService {
	/**
	 * 注入DAO
	 */
	private IQueryPodInfoDao queryPodInfoDao;
	
	/**
	 * 查询签收记录的service层方法
	 */
	@Override
	public List<PodDto> queryPodInfo(PodDto dto, int start, int limit) {
		//验证前台传来的dto是否为空
		if(null==dto)
		{
			throw new SettlementException("内部错误，待确认的查询条件为空!");
		}else if("TD".equals(dto.getQueryType())){
			if(null==dto.getPodDate()){
				throw new SettlementException("签收记录起始时间不能为空");
			}
			if(null==dto.getPodEndDate()){
				throw new SettlementException("签收记录结束日期不能为空");
			}
		}else if("WB".equals(dto.getQueryType())){
			if(null==dto.getWaybillNos()){
				throw new SettlementException("单号不能为空");
			}	
		}
		// 返回分页查询的结果
		  return queryPodInfoDao.queryPodInfo(dto, start, limit);
	}
	
	/**
	 * 查询签收记录的总条数
	 */
	@Override
	public int queryPodInfoCount(PodDto dto) {
		//验证前台传来的dto是否为空
		if(null==dto)
		{
			throw new SettlementException("内部错误，待确认的查询条件为空!");
		}
		else
		{
		    // 返回分页查询的结果
		    return queryPodInfoDao.queryPodInfoCount(dto);
	    }
	}
	
	/**
	 * @param queryPodInfoDao
	 */
	public void setQueryPodInfoDao(IQueryPodInfoDao queryPodInfoDao) {
		this.queryPodInfoDao = queryPodInfoDao;
	}
}
