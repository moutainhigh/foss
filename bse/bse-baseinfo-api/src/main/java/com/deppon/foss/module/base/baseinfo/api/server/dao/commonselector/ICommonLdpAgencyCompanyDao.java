package com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.BusinessPartnerExpressEntity;

/**
 * 公共选择器-快递代理公司查询
 * 
 * @author WangPeng
 * @date   2013-07-13 4:30 PM
 *
 */
public interface ICommonLdpAgencyCompanyDao {

	/**
     * 根据传入对象查询符合条件所有快递代理公司信息 
     * @author WangPeng
     * @date   2013-07-13 4:30 PM
     * @param  entity 快递代理公司实体
     * @param  limit 每页最大显示记录数
     * @param  start 开始记录数
     * @return 符合条件的实体列表
     */
    List<BusinessPartnerExpressEntity> queryLdpAgencyCompanysByCondtion(BusinessPartnerExpressEntity entity,int limit,int start);
    
    /**
     * 统计总记录数 
     * @author WangPeng
     * @date   2013-07-13 4:30 PM
     * @param  entity 快递代理公司实体
     * @return 符合条件的总记录数
     */
    Long countRecordByCondition(BusinessPartnerExpressEntity entity);
}
