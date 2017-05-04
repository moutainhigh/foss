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
package com.deppon.foss.module.pickup.pricing.server.dao.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IPartbussValueAddDao;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PartbussValueAddEntity;
import com.deppon.foss.util.define.FossConstants;

/**
 * 快递代理理公司增值服务Dao接口实现 
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-zdp,date:2013-7-18 下午3:28:34 </p>
 * @author 094463-foss-zdp
 * @date 2013-7-18 下午3:28:34
 * @since
 * @version
 */
public class PartbussValueAddDao extends SqlSessionDaoSupport implements IPartbussValueAddDao {
    
    private static final String NAMESPACE = "com.deppon.foss.module.pickup.pricing.ldpCompanyValueAddDao.";

    
    @Override
    @SuppressWarnings("unchecked")
    public PartbussValueAddEntity queryInfosByPartCode(String expressPartbussCode,
	    Date billDate,String districtCode) {
	 
	PartbussValueAddEntity entity=new PartbussValueAddEntity();
	entity.setActive(FossConstants.ACTIVE);
	entity.setBillDate(billDate);
	entity.setExpressPartbussCode(expressPartbussCode);
	entity.setDistrictCode(districtCode);
	List<PartbussValueAddEntity> list = this.getSqlSession().selectList(NAMESPACE + "queryInfosByPartCode",entity);
	if(CollectionUtils.isNotEmpty(list))
	{
	    return list.get(0);
	}else
	{
	    return null;
	}
    }

    

}
