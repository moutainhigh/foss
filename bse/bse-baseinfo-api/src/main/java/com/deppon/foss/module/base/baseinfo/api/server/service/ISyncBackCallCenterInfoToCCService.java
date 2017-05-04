package com.deppon.foss.module.base.baseinfo.api.server.service;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.CallCenterWaybillInfoEntity;


/**
 * 把经过处理的催运单信息由FOSS同步给CC系统
 * @author 132599-foss-shenweihua
 * @date 2014-7-22 下午3:50:25
 */
public interface ISyncBackCallCenterInfoToCCService {
	
	/**
     * 向CC系统反馈催运单的处理结果信息
     * @author 132599-foss-shenweihua
     * @param 
     * @date 2014-7-22 下午3:55:25
     * @return 
     */
	boolean syncBackCallCenterInfoToCC(CallCenterWaybillInfoEntity entity);
}
