package com.deppon.foss.module.base.baseinfo.api.server.service;

import com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressTrainProgramDto;

/**
 * 
 *<p>Title: ISyncProgramInfoToGisService</p>
 * <p>Description: 同步快递支线班车方案信息给GIS  Web Service客户端服务接口实现</p>
 * <p>Company: Deppon</p>
 * @author    130566-ZengJunfan
 * @date       2014-5-14
 */
public interface ISyncProgramInfoToGisService {
	/**
	 * 
	 *<p>Title: syncProgramInfo</p>
	 *<p>同步快递支线班车方案信息给GIS</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-5-14上午10:46:26
	 * @param dto
	 * @return
	 */
	boolean syncProgramInfo(ExpressTrainProgramDto dto);
}
