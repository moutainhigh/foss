package com.deppon.foss.module.base.baseinfo.server.cache;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchExpressEntity;
import com.deppon.foss.util.common.FossTTLCache;

/**
 * 快递代理公司网点实体Cache类
 * 
 * @author WangPeng
 * @date 2013-07-19 2:09 PM
 *
 */
public class OuterBranchExpressEntityCache extends FossTTLCache<OuterBranchExpressEntity> {

	@Override
	public String getUUID() {
		return OUTERBRANCH_EXPRESS_CACHE_UUID;
	}

}
