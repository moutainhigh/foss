package com.deppon.foss.module.base.baseinfo.server.cache;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.BusinessPartnerExpressEntity;
import com.deppon.foss.util.common.FossTTLCache;

/**
 * 快递代理公司实体Cache类
 * 
 * @author WangPeng
 * @date 2013-07-19 2:09 PM
 *
 */
public class BusinessPartnerExpressEntityCache extends FossTTLCache<BusinessPartnerExpressEntity> {

	@Override
	public String getUUID() {
		return BUSINESSPARTNER_EXPRESS_CACHE_UUID;
	}

}
