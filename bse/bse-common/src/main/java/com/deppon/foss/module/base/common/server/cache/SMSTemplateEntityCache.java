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

import com.deppon.foss.module.base.common.api.shared.domain.SMSTemplateEntity;
import com.deppon.foss.util.common.FossTTLCache;


/**
 * 短信模板实体信息 Cache类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2013-4-12 上午10:40:33 </p>
 * @author 094463-foss-xieyantao
 * @date 2013-4-12 上午10:40:33
 * @since
 * @version
 */
public class SMSTemplateEntityCache extends FossTTLCache<SMSTemplateEntity>{

    @Override
    public String getUUID() {
	
	return SMSTEMPLATE_CACHE_UUID;
    }

}
