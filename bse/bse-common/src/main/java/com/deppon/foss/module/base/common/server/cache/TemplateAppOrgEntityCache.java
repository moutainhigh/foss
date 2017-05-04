/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */ 
package com.deppon.foss.module.base.common.server.cache;

import com.deppon.foss.module.base.common.api.shared.domain.TemplateAppOrgEntity;
import com.deppon.foss.util.common.FossTTLCache;


/**
 * 部门短信模板实体信息 Cache类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2013-4-12 下午5:35:09 </p>
 * @author 094463-foss-xieyantao
 * @date 2013-4-12 下午5:35:09
 * @since
 * @version
 */
public class TemplateAppOrgEntityCache extends FossTTLCache<TemplateAppOrgEntity>{

    @Override
    public String getUUID() {
	
	return TEMPLATEAPPORGENTITY_CACHE_UUID;
    }

}
