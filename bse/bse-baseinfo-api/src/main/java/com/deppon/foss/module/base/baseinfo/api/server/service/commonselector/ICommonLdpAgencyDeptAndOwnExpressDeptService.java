package com.deppon.foss.module.base.baseinfo.api.server.service.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.LdpOuterBranchsAndOrginfoEntity;

/**
 * 快递代理网点+公司快递点部公共选择器Service
 * @author WangPeng
 * @date   2013-08-01 8:53 AM
 *
 */
public interface ICommonLdpAgencyDeptAndOwnExpressDeptService {
	
	 /**
     * 根据传入对象查询符合条件所有网点信息 
     * 
     * @author WangPeng
     * @date  2013-08-01 8:53 AM
     * @param entity 快递代理网点和公司快递点部
     * @param limit 每页最大显示记录数
     * @param start 开始记录数
     * @return 符合条件的实体列表
     */
    List<LdpOuterBranchsAndOrginfoEntity> queryLdpAgencyDeptsByCondtion(LdpOuterBranchsAndOrginfoEntity entity,int limit,int start);
    
    /**
     * 统计总记录数 
     * 
     * @author WangPeng
     * @date   2013-08-01 8:53 AM
     * @param  entity 快递代理网点和公司快递点部
     * @return 符合条件的总记录数
     */
    Long countRecordByCondition(LdpOuterBranchsAndOrginfoEntity entity);
}
