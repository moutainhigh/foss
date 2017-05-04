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

import com.deppon.foss.framework.cache.provider.ITTLCacheProvider;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.common.api.server.dao.ISMSTempleteDao;
import com.deppon.foss.module.base.common.api.shared.domain.SMSTemplateEntity;


/**
 * 短信模板实体信息 Cache Provider类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2013-4-12 上午10:50:54 </p>
 * @author 094463-foss-xieyantao
 * @date 2013-4-12 上午10:50:54
 * @since
 * @version
 */
public class SMSTemplateEntityCacheProvider implements ITTLCacheProvider<SMSTemplateEntity>{
    
    /**
     * 短信模版Dao接口.
     */
    private ISMSTempleteDao sMSTempleteDao;
    
    /**
     * 设置短信模版Dao接口
     * @param sMSTempleteDao the sMSTempleteDao to set
     */
    public void setsMSTempleteDao(ISMSTempleteDao sMSTempleteDao) {
        this.sMSTempleteDao = sMSTempleteDao;
    }

    /**
     * <p>获取缓存数据</p>.
     *
     * @param key 
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2013-4-12 上午10:53:30
     * @see com.deppon.foss.framework.cache.provider.ITTLCacheProvider#get(java.lang.String)
     */
    @Override
    public SMSTemplateEntity get(String key) {
	if(StringUtil.isBlank(key)){
	    return null;
	}else {
	    return sMSTempleteDao.querySmsByCode(key);
	}
    }

}
