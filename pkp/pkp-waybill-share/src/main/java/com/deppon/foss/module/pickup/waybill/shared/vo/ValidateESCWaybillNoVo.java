package com.deppon.foss.module.pickup.waybill.shared.vo;

import java.util.Map;

import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;


/**
 * 校验快递单号 vo
 * @author 272311-sangwenhao
 *
 */
public class ValidateESCWaybillNoVo {

	private String systemName = WaybillConstants.FOSS ;
	
	private Map<String, String> map ;
	
	public String getSystemName() {
		return systemName;
	}
	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}
	public Map<String, String> getMap() {
		return map;
	}
	public void setMap(Map<String, String> map) {
		this.map = map;
	}
	
}
