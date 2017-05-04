package com.deppon.foss.module.base.baseinfo.server.cache;


import org.apache.commons.lang.StringUtils;

import com.deppon.foss.framework.cache.provider.ITTLCacheProvider;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ILdpAgencyCompanyDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BusinessPartnerExpressEntity;
import com.deppon.foss.util.define.FossConstants;

/**
 * 快递代理公司cache provider类
 * @author WangPeng
 * @date 2013-07-19 2:16 PM
 * 
 */
public class BusinessPartnerExpressEntityCacheProvider implements
		ITTLCacheProvider<BusinessPartnerExpressEntity> {

	private ILdpAgencyCompanyDao  ldpAgencyCompanyDao;
	@Override
	public BusinessPartnerExpressEntity get(String key) {
		if(StringUtils.isEmpty(key)){
			
			return null;
		}
		return ldpAgencyCompanyDao.queryEntityByCode(key,FossConstants.ACTIVE);
	}

	/**
	 * @param ldpAgencyCompanyDao the ldpAgencyCompanyDao to set
	 */
	public void setLdpAgencyCompanyDao(ILdpAgencyCompanyDao ldpAgencyCompanyDao) {
		this.ldpAgencyCompanyDao = ldpAgencyCompanyDao;
	}

}
