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
package com.deppon.foss.module.pickup.pricing.server.service.impl;

import java.util.Date;

import org.apache.cxf.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.module.pickup.pricing.api.server.dao.IPartbussValueAddDao;
import com.deppon.foss.module.pickup.pricing.api.server.service.IPartbussValueAddService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PartbussValueAddEntity;


/**
 * 快递代理理公司增值服务Service接口 实现类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2013-7-22 上午11:27:36 </p>
 * @author 094463-foss-xieyantao
 * @date 2013-7-22 上午11:27:36
 * @since
 * @version
 */
public class PartbussValueAddService implements IPartbussValueAddService {
    
    /**
     * 日志.
     */
    @SuppressWarnings("unused")
    private static final Logger LOGGER = LoggerFactory.getLogger(PartbussValueAddService.class);
    
    IPartbussValueAddDao partbussValueAddDao;

    @Override
    public PartbussValueAddEntity queryInfosByPartCode(
	    String expressPartbussCode, Date billdate,String districtCode) {
	 if(billdate==null)
	 {
	     billdate=new Date();
	 }
	 if(StringUtils.isEmpty(expressPartbussCode))
	 {
	     return null;
	 }
	return partbussValueAddDao.queryInfosByPartCode(expressPartbussCode,billdate, districtCode);
    }

    
    public IPartbussValueAddDao getPartbussValueAddDao() {
        return partbussValueAddDao;
    }

    
    public void setPartbussValueAddDao(IPartbussValueAddDao partbussValueAddDao) {
        this.partbussValueAddDao = partbussValueAddDao;
    }
    
  
}
