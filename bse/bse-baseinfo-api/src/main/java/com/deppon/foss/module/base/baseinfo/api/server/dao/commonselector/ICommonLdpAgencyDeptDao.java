package com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchExpressEntity;

/**
 * 快递代理网点公共选择器查询
 * 
 * @author WangPeng
 * @date 2013-07-25 10:08 AM
 *
 */
public interface ICommonLdpAgencyDeptDao {
	
	 /**
     * 根据传入对象查询符合条件所有快递代理网点信息 
     * 
     * @author WangPeng
     * @date 2013-07-19 4:22 PM
     * @param entity 快递代理网点实体
     * @param limit 每页最大显示记录数
     * @param start 开始记录数
     * @return 符合条件的实体列表
     */
    List<OuterBranchExpressEntity> queryLdpAgencyDeptsByCondtion(OuterBranchExpressEntity entity,int limit,int start);
    
    /**
     * 统计总记录数 
     * @author WangPeng
     * @date   2013-07-19 4:22 PM
     * @param  entity 快递代理网点实体
     * @return 符合条件的总记录数
     */
    Long countRecordByCondition(OuterBranchExpressEntity entity);

}
