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
package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ICallProcedureDao;


/**
 * 调用存储过程DAO接口实现：查询组织信息，包含组织的所有上级层级
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2013-4-3 上午8:40:52 </p>
 * @author 094463-foss-xieyantao
 * @date 2013-4-3 上午8:40:52
 * @since
 * @version
 */
public class CallProcedureDao extends SqlSessionDaoSupport implements
	ICallProcedureDao {
    
    private static final String NAMESPACE = "foss.bse.bse-baseinfo.callProcedure.";

    /** 
     * <p>调用存储过程查询组织信息，包含组织的所有上级层级</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-4-3 上午8:40:52 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ICallProcedureDao#callOrgProcedure()
     */
    @Override
    public void callOrgProcedure() {
	this.getSqlSession().selectOne(NAMESPACE + "callOrgProcedure");
    }

}
