package com.deppon.foss.module.base.baseinfo.api.server.service.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.LdpOuterBranchsAndOrginfoEntity;

public interface ICommonQueryLdpAndExpressAndOrgInfosService {

	/**
	 * 
     * 查询自有网点+快递代理网点+虚拟网点
     * @author WangPeng
     * @date 2013-11-05 10:33AM
     * 
     */
     List<LdpOuterBranchsAndOrginfoEntity> queryLdpAndExpressAndOrgInfoList(LdpOuterBranchsAndOrginfoEntity entity,
    		 int start, int limit);
     
     /**
 	  * 
      * 计数
      * @author WangPeng
      * @date 2013-11-05 10:33AM
      * 
      */
     Long recordRowCount(LdpOuterBranchsAndOrginfoEntity entity);
}
