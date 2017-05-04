package com.deppon.foss.module.base.querying.server.action;

import com.deppon.foss.framework.server.web.action.AbstractAction;
/**
 * 单点登录 界面跳转 参数传递action
 * @author 130566
 *
 */
public class CcToFossSsoAction extends AbstractAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 880442759626058211L;
	/**
	 * 传递的电话记录单号
	 */
	private String serviceId;

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String execute(){
		
		
		return returnSuccess();
	}

	
	
}
