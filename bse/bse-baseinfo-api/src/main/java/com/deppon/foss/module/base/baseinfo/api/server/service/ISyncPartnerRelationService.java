package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PartnerRelationEntity;



/**
 * 
 * 同步网点映射关系给PTP系统
 * @author 308861 
 * @date 2016-9-6 下午4:12:17
 * @since
 * @version
 */
public interface ISyncPartnerRelationService extends IService {
   
    /**
     * 
     * 合伙人网点映射关系同步给ptp 
     * @author 308861 
     * @date 2016-9-6 下午4:11:48
     * @param partnerRelationList
     * @see
     */
	void syncPartnerRelationDataToPtp(List<PartnerRelationEntity> partnerRelationList);

}
