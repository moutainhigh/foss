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
package com.deppon.foss.module.base.baseinfo.server.service.impl;

import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.base.baseinfo.api.server.dao.ICallProcedureDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICallProcedureService;


/**
 * 调用存储过程Serice接口实现：查询组织信息，包含组织的所有上级层级
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2013-4-2 下午5:29:51 </p>
 * @author 094463-foss-xieyantao
 * @date 2013-4-2 下午5:29:51
 * @since
 * @version
 */
public class CallProcedureService implements ICallProcedureService {
    
    /**
     * 调用存储过程DAO接口：查询组织信息，包含组织的所有上级层级.
     */
    private ICallProcedureDao callProcedureDao;
    
    /**
     * <p>调用存储过程查询组织信息，包含组织的所有上级层级</p>.
     *
     * @author 094463-foss-xieyantao
     * @date 2013-4-2 下午5:29:51
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ICallProcedureService#callOrgProcedure()
     */
    @Transactional
    @Override
    public void callOrgProcedure() {
	//调用存储过程查询组织信息，包含组织的所有上级层级
	callProcedureDao.callOrgProcedure();
    }
    
    /**
     * 设置 调用存储过程DAO接口：查询组织信息，包含组织的所有上级层级.
     *
     * @param callProcedureDao the callProcedureDao to set
     */
    public void setCallProcedureDao(ICallProcedureDao callProcedureDao) {
        this.callProcedureDao = callProcedureDao;
    }
    
    

}
