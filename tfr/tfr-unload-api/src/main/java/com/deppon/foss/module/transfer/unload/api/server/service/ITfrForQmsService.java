package com.deppon.foss.module.transfer.unload.api.server.service;

import com.deppon.foss.framework.service.IService;
/**
 * @author niuly
 * @function foss中转为QMS提供的总服务类
 * @date 2015年5月4日上午9:15:58
 */
public interface ITfrForQmsService extends IService{
	String getInfoFromTfrForQms(String errorType, String waybillNo, String standardCode);
}
