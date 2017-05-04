package com.deppon.foss.module.transfer.common.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.common.api.shared.dto.WKLoadTempDto;



/**
* @description 推送创建装车任务,完成装车任务给悟空
* @version 1.0
* @author 328864-foss-xieyang
* @update 2016年5月6日 下午3:08:12
*/
public interface IPushLoadToWkService extends IService {
	

	
	
	
	
	/**
	* @description 把创建装车任务推送给悟空系统
	* @return
	* @version 1.0
	* @author 328864-foss-xieyang
	* @update 2016年5月6日 下午3:04:07
	*/
	public int pushCreateLoadToWK(WKLoadTempDto dto);
	
	
	
	/**
	* @description 把完成创建装车任务推送给悟空
	* @return
	* @version 1.0
	* @author 328864-foss-xieyang
	* @update 2016年5月6日 下午3:04:04
	*/
	public int pushFinishCreateLoadToWK(WKLoadTempDto dto);
	
	
	
	
	public void ThreadsPool(Object obj);


}
