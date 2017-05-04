package com.deppon.foss.module.pickup.waybill.api.server.service;

import java.util.Map;

import com.deppon.foss.module.pickup.waybill.shared.vo.ESCValueAddVo;


public interface IWaybillValueAddServiceForECS {

	/**
	 * ESC系统查询增值服务信息
	 * @param params 请求参数
	 * @return map键值对 count：记录数；list:AppWayBillDetaillVo结果集
	 * @author 273279
	 * 2016.04.20
	 */
	Map<String,Object> queryWaybillInfosValueAddEcs(ESCValueAddVo escValueAddVo);
	
}
