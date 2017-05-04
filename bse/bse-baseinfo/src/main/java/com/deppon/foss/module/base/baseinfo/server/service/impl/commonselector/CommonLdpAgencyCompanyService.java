package com.deppon.foss.module.base.baseinfo.server.service.impl.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonLdpAgencyCompanyDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonLdpAgencyCompanyService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BusinessPartnerExpressEntity;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;

/**
 * 公共选择器-快递代理公司Service
 * 
 * @author WangPeng
 * @date 2013-07-19 4:49 PM
 * 
 */
public class CommonLdpAgencyCompanyService implements
		ICommonLdpAgencyCompanyService {
	
	//注入快递代理代理公司Dao
	private ICommonLdpAgencyCompanyDao commonLdpAgencyCompanyDao;
	
    /**
     * 根据条件查询出快递代理公司list
     * 
     * @author WangPeng
     * @Date   2013-7-19 下午5:18:13
     * @param  entity
     * @param  limit
     * @param  start
     * @return List<BusinessPartnerExpressEntity>
     *
     */
	@Override
	public List<BusinessPartnerExpressEntity> queryLdpAgencyCompanysByCondtion(
			BusinessPartnerExpressEntity entity, int limit, int start) {
		if(null == entity){
			entity = new BusinessPartnerExpressEntity();
		}
		entity.setAgentCompanySort(DictionaryValueConstants.EXPRESS_DELIVERY_TYPE_LDP);
		return commonLdpAgencyCompanyDao.queryLdpAgencyCompanysByCondtion(entity, limit, start);
	}

	/**
	 * 记录返回的条数
	 * 
	 * @author WangPeng
	 * @Date   2013-7-19 下午5:20:07
	 * @param  entity
	 * @return Long 
	 *
	 */
	@Override
	public Long countRecordByCondition(BusinessPartnerExpressEntity entity) {
		if(null == entity){
			entity = new BusinessPartnerExpressEntity();
		}
		entity.setAgentCompanySort(DictionaryValueConstants.EXPRESS_DELIVERY_TYPE_LDP);
		return commonLdpAgencyCompanyDao.countRecordByCondition(entity);
	}

	/**
	 * @return the commonLdpAgencyCompanyDao
	 */
	public ICommonLdpAgencyCompanyDao getCommonLdpAgencyCompanyDao() {
		return commonLdpAgencyCompanyDao;
	}

	/**
	 * @param commonLdpAgencyCompanyDao the commonLdpAgencyCompanyDao to set
	 */
	public void setCommonLdpAgencyCompanyDao(ICommonLdpAgencyCompanyDao commonLdpAgencyCompanyDao) {
		this.commonLdpAgencyCompanyDao = commonLdpAgencyCompanyDao;
	}

}
