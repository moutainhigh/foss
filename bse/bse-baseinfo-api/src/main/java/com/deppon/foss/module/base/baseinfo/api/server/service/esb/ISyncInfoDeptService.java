package com.deppon.foss.module.base.baseinfo.api.server.service.esb;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.InfoDeptEntity;
/**
 * 
 * 同步信息部
 * @author 273311 
 * @date 2016-10-29 下午3:43:37
 * @since
 * @version
 */
public interface ISyncInfoDeptService {
	
     void SyncInfoDept(List<InfoDeptEntity> entitys,String operateType);
}
