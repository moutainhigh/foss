
package com.deppon.foss.module.pickup.common.client.service.impl;

import java.util.ArrayList;
import java.util.List;
import com.deppon.foss.module.pickup.common.client.dao.IRegionArriveDao;
import com.deppon.foss.module.pickup.common.client.service.ISrvPriceArriveRegionOrgService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionOrgArriveEntity;
import com.google.inject.Inject;

/**
 * 
 * 
 * @ClassName: ISrvPriceArriveRegionOrgService
 * 
 * @Description: BUG-55198 到达区域与部门关系
 * 
 *               客户端在离线使用时，需要开单计算价格。 所以价格信息会提前下载到客户端。每次用户在登录时，
 *               会根据用户所在组织，查询下载和自己组织相关的信息。 这些信息包括：基础数据，价格，折扣等。
 *               以下主要描述与价格，折扣相关的数据的下载。 目前框架主要支持的是单表下载， 根据最后更新时间来增量更新数据。
 *               但是价格里面关系复杂，根据最后更新时间， 不能满足下载到正确的价格的需求。所以，在此方案中，
 *               会描述如何下载到准确的价格。并且是做增量下载。
 * 
 * @author deppon-157229-zxy
 * 
 * @date 2013-10-8 上午11:32:21
 * 
 * 
 */
public class SrvPriceArriveRegionOrgService implements
		ISrvPriceArriveRegionOrgService {

	@Inject
	private IRegionArriveDao regionArriveDao;
	
	@Override
	public void add(PriceRegionOrgArriveEntity entity) {
		regionArriveDao.addRegionOrg(entity);
	}

	@Override
	public void update(PriceRegionOrgArriveEntity entity) {
		regionArriveDao.updateRegionOrg(entity);
	}

	@Override
	public void saveOrUpdate(PriceRegionOrgArriveEntity entity) {
		if(!regionArriveDao.addRegionOrg(entity)){
			regionArriveDao.updateRegionOrg(entity);
		}
	}

	@Override
	public void deleteID(String id) {
		List<String> idLst = new ArrayList<String>();
		idLst.add(id);
		regionArriveDao.deleteRegionOrg(idLst);
	}

}
