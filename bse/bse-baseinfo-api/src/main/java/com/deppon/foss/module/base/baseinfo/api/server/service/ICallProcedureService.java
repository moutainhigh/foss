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
package com.deppon.foss.module.base.baseinfo.api.server.service;

import com.deppon.foss.framework.service.IService;


/**
 * 调用存储过程Serice接口：查询组织信息，包含组织的所有上级层级
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2013-4-2 下午3:45:00 </p>
 * @author 094463-foss-xieyantao
 * @date 2013-4-2 下午3:45:00
 * @since
 * @version
 */
public interface ICallProcedureService extends IService{
    
    /**
     * <p>调用存储过程查询组织信息，包含组织的所有上级层级</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-4-2 下午3:46:54
     * @see
     */
    void callOrgProcedure();

}
