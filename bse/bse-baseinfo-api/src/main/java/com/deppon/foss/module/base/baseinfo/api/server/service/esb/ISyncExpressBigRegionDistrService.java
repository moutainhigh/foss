package com.deppon.foss.module.base.baseinfo.api.server.service.esb;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressBigRegionDistrEntity;
/**
 * 
 * 快递大区-行政区域映射信息到cubc
 * @author 273311 
 * @date 2016-10-29 上午11:11:24
 * @since
 * @version
 */
public interface ISyncExpressBigRegionDistrService {
	
     void SyncExpressBigRegionDistr(List<ExpressBigRegionDistrEntity> entitys,String operateType);
}
