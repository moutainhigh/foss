package com.deppon.foss.module.base.baseinfo.api.server.service.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.BusinessPartnerExpressEntity;

/**
 * 落地公司公共选择器
 * 
 * @author WangPeng
 * @date   2013-07-19 4:22 PM
 *
 */
public interface ICommonLdpAgencyCompanyService {
    /**
     * 根据传入对象查询符合条件所有落地公司信息 
     * 
     * @author WangPeng
     * @date 2013-07-19 4:22 PM
     * @param entity 落地公司实体
     * @param limit 每页最大显示记录数
     * @param start 开始记录数
     * @return 符合条件的实体列表
     */
    List<BusinessPartnerExpressEntity> queryLdpAgencyCompanysByCondtion(BusinessPartnerExpressEntity entity,int limit,int start);
    
    /**
     * 统计总记录数 
     * @author WangPeng
     * @date   2013-07-19 4:22 PM
     * @param  entity 落地公司实体
     * @return 符合条件的总记录数
     */
    Long countRecordByCondition(BusinessPartnerExpressEntity entity);
}
