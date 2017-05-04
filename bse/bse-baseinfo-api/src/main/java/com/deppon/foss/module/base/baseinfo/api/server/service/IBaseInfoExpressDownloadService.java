/**
 * 
 */
package com.deppon.foss.module.base.baseinfo.api.server.service;

import com.deppon.foss.base.util.ClientUpdateDataPack;
import com.deppon.foss.base.util.DataBundle;
import com.deppon.foss.framework.service.IService;

/**
 * @author 026123-foss-lifengteng
 *
 */
public interface IBaseInfoExpressDownloadService extends IService{

	/**
	 * @param clientInfo
	 * @return
	 */
	DataBundle downloadExpressCityData(ClientUpdateDataPack clientInfo);

}
