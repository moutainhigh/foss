package com.deppon.foss.module.pickup.pickup.server.cache;

import com.deppon.foss.util.common.FossTTLCache;

public class TokenCache  extends FossTTLCache<String>{

	public String getUUID() {
		return GUILOGON_TOKEN_CACHE_UUID;
	}
}
