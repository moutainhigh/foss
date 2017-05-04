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

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.base.util.define.SymbolConstants;
import com.deppon.foss.framework.cache.provider.ITTLCacheProvider;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.common.api.server.dao.ISMSTempleteForDeptDao;
import com.deppon.foss.module.base.common.api.shared.domain.TemplateAppOrgEntity;


/**
 * 部门短信模板实体信息 Cache Provider类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2013-4-12 下午5:40:02 </p>
 * @author 094463-foss-xieyantao
 * @date 2013-4-12 下午5:40:02
 * @since
 * @version
 */
public class TemplateAppOrgEntityCacheProvider implements ITTLCacheProvider<TemplateAppOrgEntity> {
    
    /**
     * 部门短信模版Dao接口.
     */
    private ISMSTempleteForDeptDao smsTempleteForDeptDao;
    
    /**
     * 设置 部门短信模版Dao接口.
     *
     * @param smsTempleteForDeptDao the smsTempleteForDeptDao to set
     */
    public void setSmsTempleteForDeptDao(
    	ISMSTempleteForDeptDao smsTempleteForDeptDao) {
        this.smsTempleteForDeptDao = smsTempleteForDeptDao;
    }

    /**
     * <p>获取缓存数据</p>.
     *
     * @param key 
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2013-4-12 下午5:41:56
     * @see com.deppon.foss.framework.cache.provider.ITTLCacheProvider#get(java.lang.String)
     */
    @Override
    public TemplateAppOrgEntity get(String key) {
	if(StringUtil.isBlank(key)){
	    return null;
	}else {
	    //以冒号拆分字符
	    String[] strs = StringUtils.split(key, SymbolConstants.EN_COLON);
	    //根据短信模板虚拟编码、部门编码查询部门短信模板
	    return smsTempleteForDeptDao.queryAppOrgByVirtualCodeAndOrgCode(strs[NumberConstants.NUMERAL_ZORE],strs[NumberConstants.NUMERAL_ONE]);
	}
    }

}
