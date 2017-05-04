package com.deppon.foss.module.base.baseinfo.server.cache;


import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.framework.cache.provider.ITTLCacheProvider;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ILdpAgencyDeptDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchExpressEntity;
import com.deppon.foss.util.define.FossConstants;

/**
 * 快递代理公司网点cache provider类
 * @author WangPeng
 * @date 2013-07-19 2:16 PM
 * 
 */
public class OuterBranchExpressEntityCacheProvider implements
		ITTLCacheProvider<List<OuterBranchExpressEntity>> {

	private ILdpAgencyDeptDao ldpAgencyDeptDao;
	@Override
	public List<OuterBranchExpressEntity> get(String key) {
		if(StringUtils.isEmpty(key)){
			
			return null;
		}
		return ldpAgencyDeptDao.queryLdpAgencyDeptsByComVirtualCode(key,FossConstants.ACTIVE);
	}

	/**
	 * @param ldpAgencyDeptDao the ldpAgencyDeptDao to set
	 */
	public void setLdpAgencyDeptDao(ILdpAgencyDeptDao ldpAgencyDeptDao) {
		this.ldpAgencyDeptDao = ldpAgencyDeptDao;
	}

}
