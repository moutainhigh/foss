package com.deppon.foss.module.base.querying.server.service;

import com.deppon.foss.module.base.querying.shared.dto.WaybillInfoToCcDto;


/**
 * 同步给CC接口信息 客户端
 * @author 130566
 *
 */
public interface ISyncWaybillInfoToCCService {
	/**
	 * 
	 *<p>同步运单号信息给CC系统</p>	
	 * @date 2014-8-5 上午9:52:47
	 * @author 130566-ZengJunfan
	 * @param dto
	 */
	boolean syncWaybillInfoToCC(WaybillInfoToCcDto dto);

}
