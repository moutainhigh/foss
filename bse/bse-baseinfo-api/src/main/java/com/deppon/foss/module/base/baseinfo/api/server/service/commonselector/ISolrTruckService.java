package com.deppon.foss.module.base.baseinfo.api.server.service.commonselector;

import org.apache.solr.common.SolrDocumentList;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnTruckEntity;

public interface ISolrTruckService {
	/**
	 * solr服务查询方法
	 * @Title: truckSolrQuery 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param @param ownTruck
	 * @param @param key
	 * @param @param offset
	 * @param @param limit
	 * @param @return    设定文件 
	 * @return List<OwnTruckEntity>    返回类型 
	 * @throws 
	 * @user 310854-liuzhenhua
	 */
	public SolrDocumentList truckSolrQuery(OwnTruckEntity ownTruck,String key , int start, int limit);
}
